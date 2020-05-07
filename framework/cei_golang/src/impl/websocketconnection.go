package impl

import (
	"github.com/gorilla/websocket"
	"log"
)

const (
	IDLE = iota
	CONNECT
	CLOSE
)

type WebSocketCallback struct {
	invoke func(connection *WebSocketConnection)
}

type WebSocketConnection struct {
	ws        *websocket.Conn
	option    *WebSocketOptions
	onConnect func(connection *WebSocketConnection)
	onClose   func(connection *WebSocketConnection)
	events    []*WebSocketEvent
	status    int
	Channel   chan int
}

func NewWebSocketConnection(option *WebSocketOptions) *WebSocketConnection {
	instance := new(WebSocketConnection)
	instance.option = option
	instance.status = IDLE
	return instance
}

func (inst *WebSocketConnection) startEventLoop() {
	for {
		_, message, err := inst.ws.ReadMessage()
		if err != nil {
			log.Println("read: err", err)
			return
		}
		log.Printf("recv: %s", message)
		inst.onMessage(message)

	}
}

func (inst *WebSocketConnection) newWebSocketMessage(data []byte) *WebSocketMessage {
	msg := new(WebSocketMessage)
	return msg
}

func (inst *WebSocketConnection) onMessage(data []byte) {
	msg := inst.newWebSocketMessage(data)
	for i := 0; i < len(inst.events); i++ {
		event := inst.events[i]
		if res := event.check(msg); res {
			event.callback(inst, msg)
			if !event.isPersistence() {
				inst.events = append(inst.events[:i], inst.events[i+1:]...)
				i-- // maintain the correct index
			}
		}
	}
}

func (inst *WebSocketConnection) SetOnConnect(onConnect func(connection *WebSocketConnection)) {
	inst.onConnect = onConnect
}

func (inst *WebSocketConnection) SetOnClose(onClose func(connection *WebSocketConnection)) {
	inst.onClose = onClose
}

func (inst *WebSocketConnection) Send(message string) {
	if inst.status != CONNECT {
		return
	}
	inst.ws.WriteMessage(websocket.TextMessage, []byte(message))
}

func (inst *WebSocketConnection) RegisterEvent(event *WebSocketEvent) {
	inst.events = append(inst.events, event)
}

func (inst *WebSocketConnection) Connect(target string) {
	c, _, err := websocket.DefaultDialer.Dial("ws://127.0.0.1:8888/websocket/event", nil)
	inst.ws = c
	if err != nil {
		log.Fatal("dial:", err)
	}
	log.Println("Success")
	if inst.onConnect != nil {
		inst.onConnect(inst)
	}
	go inst.startEventLoop()

	//interrupt := make(chan os.Signal, 1)
	//signal.Notify(interrupt, os.Interrupt)
	//
	//inst.ws = c
	//defer c.Close()
	//done := make(chan struct{})
	//go func() {
	//	defer close(done)
	//	for {
	//		_, message, err := c.ReadMessage()
	//		if err != nil {
	//			log.Println("read:", err)
	//			return
	//		}
	//		log.Printf("recv: %s", message)
	//	}
	//}()
	//
	//ticker := time.NewTicker(time.Second)
	//defer ticker.Stop()
	//
	//for {
	//	select {
	//	case <-done:
	//		return
	//	case t := <-ticker.C:
	//		err := c.WriteMessage(websocket.TextMessage, []byte(t.String()))
	//		log.Println("write: Message")
	//		if err != nil {
	//			log.Println("write:", err)
	//			return
	//		}
	//	case <-interrupt:
	//		log.Println("interrupt")
	//
	//		// Cleanly close the connection by sending a close message and then
	//		// waiting (with timeout) for the server to close the connection.
	//		err := c.WriteMessage(websocket.CloseMessage, websocket.FormatCloseMessage(websocket.CloseNormalClosure, ""))
	//		if err != nil {
	//			log.Println("write close:", err)
	//			return
	//		}
	//		select {
	//		case <-done:
	//		case <-time.After(time.Second):
	//		}
	//		return
	//	}
	//}
}
