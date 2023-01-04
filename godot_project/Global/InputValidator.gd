extends Node

enum {
	ZOOM_SESSION_NAME,
	ZOOM_USER_NAME,
	ZOOM_SESSION_PASSWORD,
	ZOOM_BACKEND_URL,
}

func validateText(input : String, type : int) -> bool:
	match(type):
		[ZOOM_SESSION_NAME, ZOOM_SESSION_PASSWORD, ZOOM_USER_NAME]:
			return input.is_valid_identifier()
			return true
		ZOOM_BACKEND_URL:
			return true
		_:
			return false
