package impl

type WebSocketEvent struct {
	when func(message *WebsocketMessage) bool
	callback func(*WebsocketMessage)
}

func NewWebSocketEvent() *WebSocketEvent {
	inst := new(WebSocketEvent)
	return inst
}

func (inst *WebSocketEvent) Register(when func(*WebsocketMessage) bool, callback func(*WebsocketMessage)) {
	inst.when = when
	inst.callback = callback
}

func (inst *WebSocketEvent) Check(msg *WebsocketMessage) bool {
	return inst.when(msg)
}

func (inst *WebSocketEvent) Invoke(msg *WebsocketMessage) {
	inst.callback(msg)
}