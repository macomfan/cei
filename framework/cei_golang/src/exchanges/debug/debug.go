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

func (inst *WSClient) Open(channel, name string) {
    inst.connection.Connect(fmt.Sprintf("/websocket/%s", channel))
}

func (inst *WSClient) RequestEcho(name string, price float64, number int64, status bool, onEcho func (data SimpleInfo)) {
    onEchoEvent := impl.NewWebSocketEvent(false)
    onEchoEvent.SetTrigger(func(msg *impl.WebSocketMessage) bool {
        rootObj := impl.ParseJsonFromString(msg.GetString())
        jsonChecker := new(impl.JsonChecker)
        jsonChecker.CheckEqual("op", "echo", rootObj)
        obj := rootObj.GetObject("param")
        jsonChecker.CheckEqual("Name", name, obj)
        return jsonChecker.Complete()
    })
    onEchoEvent.SetEvent(func(connection *impl.WebSocketConnection, msg *impl.WebSocketMessage)  {
        rootObj := impl.ParseJsonFromString(msg.GetString())
        simpleInfoVar := SimpleInfo{}
        simpleInfoVar.Name = rootObj.GetString("Name")
        simpleInfoVar.Number = rootObj.GetInt64("Number")
        simpleInfoVar.Price = rootObj.GetFloat64("Price")
        simpleInfoVar.Status = rootObj.GetBool("Status")
        onEcho(simpleInfoVar)
    })
    inst.connection.RegisterEvent(onEchoEvent)
    json := impl.JsonWrapper{}
    json.AddJsonString("op", "echo")
    obj := impl.JsonWrapper{}
    obj.AddJsonString("Name", name)
    obj.AddJsonFloat64("Price", price)
    obj.AddJsonInt64("Number", number)
    obj.AddJsonBool("Status", status)
}


