package websocket

type WebsocketEvent struct {
	when func(*WebsocketMessage) bool
	callback func(*WebsocketMessage)
}

func NewWebsocketEvent() *WebsocketEvent {
	inst := new(WebsocketEvent)
	return inst
}

func (inst *WebsocketEvent) Register(when func(*WebsocketMessage) bool, callback func(*WebsocketMessage)) {
	inst.when = when
	inst.callback = callback
}

func (inst *WebsocketEvent) Check(msg *WebsocketMessage) bool {
	return inst.when(msg)
}

func (inst *WebsocketEvent) Invoke(msg *WebsocketMessage) {
	inst.callback(msg)
}