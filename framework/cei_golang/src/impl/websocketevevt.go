package impl

type WebSocketEvent struct {
	persistence bool
	trigger     func(msg *WebSocketMessage) bool
	callback    func(connection *WebSocketConnection, msg *WebSocketMessage)
}

func NewWebSocketEvent(persistence bool) *WebSocketEvent {
	inst := new(WebSocketEvent)
	return inst
}

//func (inst *WebSocketEvent) Register(when func(*WebSocketMessage) bool, callback func(*WebSocketMessage)) {
//	inst.when = when
//	inst.callback = callback
//}

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
