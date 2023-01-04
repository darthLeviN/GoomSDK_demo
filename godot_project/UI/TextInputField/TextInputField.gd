@tool
extends PanelContainer

@onready var inputn = %Input
@onready var labeln = %Label

@export var label : String = "label" :
	set(v):
		label = v
		if(is_instance_valid(labeln)):
			labeln.text = label

@export var value : String :
	set(v):
		value = v
		if(is_instance_valid(inputn)):
			inputn.text = value
	get:
		if(Engine.is_editor_hint()):
			return value
		
		var ret : String = inputn.text
		if ret == "":
			ret = inputn.placeholder_text
		return ret

@export var placeholder : String :
	set(v):
		placeholder = v
		if(is_instance_valid(inputn)):
			inputn.placeholder_text = placeholder

func _ready():
	labeln.text = label
	inputn.text = value
	inputn.placeholder_text = placeholder

func get_value() -> String:
	return inputn.text
