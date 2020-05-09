package debug

import (
    "../../impl"
    "fmt"
)

type SimpleInfo struct {
    Name   string
    Number int64
    Price  float64
    Status bool
}

type WSClient struct {
    option     *impl.WebSocketOptions
    connection *impl.WebSocketConnection
}

func NewWSClient(option *impl.WebSocketOptions) *WSClient {
    inst := new(WSClient)
    if option != nil {
        inst.option = option
    } else {
        inst.option.Url = "http://127.0.0.1:8888"
    }
    return inst
}

type ArgsOpen struct {
    Channel string
    Name    string
}

func (inst *WSClient) Open(args ArgsOpen) {
    inst.connection.Connect(fmt.Sprintf("/websocket/%s", args.Channel))
}

type ArgsRequestEcho struct {
    Name   string
    Price  float64
    Number int64
    Status bool
}

func (inst *WSClient) RequestEcho(args ArgsRequestEcho, onEcho func (data SimpleInfo)) {
    onEchoEvent := impl.NewWebSocketEvent(false)
    inst.connection.RegisterEvent(onEchoEvent)
    json := impl.JsonWrapper{}
    json.AddJsonString("op", "echo")
    obj := impl.JsonWrapper{}
    obj.AddJsonString("Name", args.Name)
    obj.AddJsonFloat64("Price", args.Price)
    obj.AddJsonInt64("Number", args.Number)
    obj.AddJsonBool("Status", args.Status)
}


