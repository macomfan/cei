package websocket


type WebsocketConnection struct {
	event []*WebsocketEvent
}


func NewWebSocketConnection() *WebsocketConnection {
	inst := new(WebsocketConnection)
	return inst
}

func (inst *WebsocketConnection) RegisterEvent(event *WebsocketEvent) {
	inst.event = append(inst.event, event)
}

func (inst *WebsocketConnection) OnMessage() {
	msg := new(WebsocketMessage)
	for _, value := range inst.event {
		if value.Check(msg) {
			value.Invoke(msg)
		}
	 }
}



