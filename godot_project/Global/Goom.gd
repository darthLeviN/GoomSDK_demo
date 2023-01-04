extends Control

var httprqn : HTTPRequest
var goomsdksg
var waiting_to_connect : bool = false
var request_dict := {}
var goom_container : Control


func _ready():
	httprqn = HTTPRequest.new()
	add_child(httprqn)
	httprqn.connect("request_completed", http_request_completed)
	if Engine.has_singleton("GoomSDKVideoPlugin"):
		goomsdksg = Engine.get_singleton("GoomSDKVideoPlugin")
		goomsdksg.init()
		goom_container_resized()
	else:
		push_error("GOOMSDK SIGNLETON NOT FOUND")

func http_request_completed(result: int, response_code: int, headers: PackedStringArray, body: PackedByteArray):
	if !waiting_to_connect:
		return
	waiting_to_connect = false
	if response_code != HTTPClient.RESPONSE_OK:
		notify_client("invalid http reponse code : %d" % response_code, true)
		return
	var parsed_body = JSON.parse_string(body.get_string_from_utf8())
	if !(parsed_body is Dictionary)|| !(parsed_body.has("signature")):
		notify_client("invalid http response body : %s" % parsed_body, true)
		return
	var signature = parsed_body.get("signature")
	if !(signature is String):
		notify_client("invalid signature %s" % signature, true)
		return
	print_debug("valid http response : ", parsed_body)
	goomsdk_connect_session(signature, request_dict["sessionName"], request_dict["userIdentity"],
		request_dict["pwd"])
	
func goomsdk_connect_session(jwt : String, session_name : String, user_name : String, session_password : String):
	if(!goomsdksg):
		notify_client("INVALID GOOMSDK SINGLETON", true)
		return
	goomsdksg.connect_session(jwt, session_name, user_name, session_password)
	

func connect_session(backend_url : String, session_name : String, username : String, session_password : String):
	request_dict = {
		"userIdentity" : username,
		"role" : 1,
		"sessionName" : session_name,
		"pwd" : session_password
	}
	waiting_to_connect = true
	httprqn.cancel_request()
	httprqn.request(backend_url, ["Content-Type: application/json"], false, HTTPClient.METHOD_POST, 
		JSON.stringify(request_dict))

func notify_client(message : String, error : bool = false):
	if goomsdksg:
		if(error):
			push_error(message)
		else:
			print_debug(message)
		goomsdksg.godotToast(message)

func set_goom_container(new_container : Control):
	if is_instance_valid(goom_container):
		if goom_container.is_connected("resized", goom_container_resized):
			goom_container.disconnect("resized", goom_container_resized)
	goom_container = new_container
	if is_instance_valid(goom_container):
		if !goom_container.is_connected("resized", goom_container_resized):
			goom_container.connect("resized", goom_container_resized)
	goom_container_resized()
		

func goom_container_resized():
	if !is_instance_valid(goomsdksg):
#		print_debug("AA")
		return
	if is_instance_valid(goom_container):
		
		var cdict := {
			"main" : {
				"top" : int(goom_container.global_position.y),
				"left" : int(goom_container.global_position.x),
				"width" : int(goom_container.size.x),
				"height" : int(goom_container.size.y)
			}
		}
#		print_debug(vpratio.y, ", ", cdict)
		goomsdksg.configureLayout(cdict)
#		print_debug("A")
	

func set_goom_visibility(visible : bool):
	pass
