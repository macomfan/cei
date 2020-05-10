package impl

type WebSocketMessage struct {
	text      string
	byteValue []byte
}

func NewWebSocketMessageByString(text string) *WebSocketMessage {
	msg := new(WebSocketMessage)
	msg.text = text
	return msg
}

func (inst *WebSocketMessage) GetString() string {
	return inst.text
}

func (inst *WebSocketMessage) GetBytes() []byte {
	return inst.byteValue
}

func (inst *WebSocketMessage) Upgrade(value string) {
	inst.text = value
}
