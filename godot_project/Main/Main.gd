extends Control

@onready var join_button = %JoinButton
@onready var session_name = %SessionName
@onready var user_name = %UserName
@onready var session_pwd = %SessionPwd
@onready var jwt_backend_url = %JwtBackendURL

@onready var goom_container = %GOOMContainer

# Called when the node enters the scene tree for the first time.
func _ready():
	Goom.set_goom_container(goom_container)
	print_debug("MEH :", get_viewport().size)


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass


func _on_join_button_pressed():
	join()
	print_debug(goom_container.size)
#	Goom.connect_session()

func join():
	var sssn : String = session_name.value
	var usrn : String = user_name.value
	var pwd : String = session_pwd.value
	var burl : String = jwt_backend_url.value
	Goom.connect_session(burl, sssn, usrn, pwd)
	
