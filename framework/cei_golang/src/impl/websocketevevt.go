package impl

type WebSocketEvent struct {
	persistence bool
	trigger     func(msg *WebSocketMessage) bool
	callback    func(connection *WebSocketConnection, msg *WebSocketMessage)
}

func NewWebSocketEvent(persistence bool) *WebSocketEvent {
	inst := new(WebSocketEvent)
	inst.persistence = persistence
	return inst
}

func (inst *WebSocketEvent) isPersistence() bool {
	return inst.persistence
}

func (inst *WebSocketEvent) check(msg *WebSocketMessage) bool {
	return inst.trigger(msg)
}

func (inst *WebSocketEvent) invoke(connection *WebSocketConnection, msg *WebSocketMessage) {
	inst.callback(connection, msg)
}

func (inst *WebSocketEvent) SetTrigger(trigger func(msg *WebSocketMessage) bool) {
	inst.trigger = trigger
}

func (inst *WebSocketEvent) SetEvent(callback func(connection *WebSocketConnection, msg *WebSocketMessage)) {
	inst.callback = callback
}
