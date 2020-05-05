package impl

import (
	_ "github.com/gorilla/websocket"
)


type WebsocketConnection struct {
	event []*WebSocketEvent
}


func NewWebsocketConnection() *WebsocketConnection {
	inst := new(WebsocketConnection)
	return inst
}

func (inst *WebsocketConnection) RegisterEvent(event *WebSocketEvent) {
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



