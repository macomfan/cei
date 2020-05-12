package test

import (
    "../../impl"
    "errors"
    "fmt"
)

type Order struct {
    OrderId int64
}

type PriceEntity struct {
    Price  float64
    Volume float64
}

type InfoEntity struct {
    Name      string
    InfoValue string
}

type SimpleInfo struct {
    Name   string
    Number int64
    Price  float64
    Status bool
}

type LastTrade struct {
    Price     float64
    Volume    float64
    Timestamp int64
    Symbol    string
}

type ModelValue struct {
    Name  string
    Value int64
}

type ModelInfo struct {
    Name  string
    Value ModelValue
}

type PriceList struct {
    Name   string
    Prices []PriceEntity
}

type InfoList struct {
    Name   string
    Values []InfoEntity
}

type TradeEntity struct {
    Price  float64
    Volume float64
}

type HistoricalTrade struct {
    Data []TradeEntity
}

type GetClient struct {
    option *impl.RestfulOptions
}

func NewGetClient(option *impl.RestfulOptions) *GetClient {
    inst := new(GetClient)
    if option != nil {
        inst.option = option
    } else {
        inst.option.Url = "http://127.0.0.1:8888"
    }
    return inst
}

func (inst *GetClient) GetSimpleInfo() (data SimpleInfo , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget("/restful/get/simpleInfo")
    request.SetMethod(impl.GET)
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    simpleInfoVar := SimpleInfo{}
    simpleInfoVar.Name = rootObj.GetString("Name")
    simpleInfoVar.Number = rootObj.GetInt64("Number")
    simpleInfoVar.Price = rootObj.GetFloat64("Price")
    simpleInfoVar.Status = rootObj.GetBool("Status")
    return simpleInfoVar, nil
}

func (inst *GetClient) GetModelInfo() (data ModelInfo , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget("/restful/get/modelInfo")
    request.SetMethod(impl.GET)
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    jsonChecker := new(impl.JsonChecker)
    jsonChecker.CheckEqual("aaa", "aa", rootObj)
    modelInfoVar := ModelInfo{}
    modelInfoVar.Name = rootObj.GetString("Name")
    obj := rootObj.GetObject("DataL1")
    obj0 := obj.GetObject("DataL2")
    modelValueVar := ModelValue{}
    obj1 := obj0.GetObject("Value")
    modelValueVar.Name = obj1.GetString("Name")
    modelValueVar.Value = obj1.GetInt64("Value")
    modelInfoVar.Value = modelValueVar
    return modelInfoVar, nil
}

func (inst *GetClient) GetPriceList() (data PriceList , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget("/restful/get/priceList")
    request.SetMethod(impl.GET)
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    priceListVar := PriceList{}
    priceListVar.Name = rootObj.GetString("Name")
    obj := rootObj.GetArray("Prices")
    for _, item := range obj.Array() {
        priceEntityVar := PriceEntity{}
        priceEntityVar.Price = item.GetFloat64("[0]")
        priceEntityVar.Volume = item.GetFloat64("[1]")
        priceListVar.Prices = append(priceListVar.Prices, priceEntityVar)
    }
    return priceListVar, nil
}

func (inst *GetClient) GetInfoList() (data InfoList , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget("/restful/get/infoList")
    request.SetMethod(impl.GET)
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    infoListVar := InfoList{}
    infoListVar.Name = rootObj.GetString("Name")
    obj := rootObj.GetArray("Values")
    for _, item := range obj.Array() {
        infoEntityVar := InfoEntity{}
        infoEntityVar.Name = item.GetString("Name")
        infoEntityVar.InfoValue = item.GetString("InfoValue")
        infoListVar.Values = append(infoListVar.Values, infoEntityVar)
    }
    return infoListVar, nil
}

func (inst *GetClient) GetTestArray() (data SimpleInfo , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget("/restful/get/testArray")
    request.SetMethod(impl.GET)
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    simpleInfoVar := SimpleInfo{}
    simpleInfoVar.Name = rootObj.GetString("[0]")
    simpleInfoVar.Number = rootObj.GetInt64("[1]")
    simpleInfoVar.Price = rootObj.GetFloat64("[2]")
    simpleInfoVar.Status = rootObj.GetBool("[3]")
    return simpleInfoVar, nil
}

func (inst *GetClient) InputsByGet(name string, number int64, price float64, status bool) (data SimpleInfo , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget("/restful/get/inputsByGet")
    request.SetMethod(impl.GET)
    request.AddQueryString("Name", name)
    request.AddQueryString("Number", impl.ToString(number))
    request.AddQueryString("Price", impl.ToFloat64(price))
    request.AddQueryString("Status", impl.ToBool(status))
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    simpleInfoVar := SimpleInfo{}
    simpleInfoVar.Name = rootObj.GetString("Name")
    simpleInfoVar.Number = rootObj.GetInt64("Number")
    simpleInfoVar.Price = rootObj.GetFloat64("Price")
    simpleInfoVar.Status = rootObj.GetBool("Status")
    return simpleInfoVar, nil
}

func (inst *GetClient) Url(input string) (data string , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    request.SetTarget(fmt.Sprintf("/restful/get/url/%s", input))
    request.SetMethod(impl.GET)
    response := impl.RestfulQuery(request)
    return response.GetString(), nil
}


type PostClient struct {
    option *impl.RestfulOptions
}

func NewPostClient(option *impl.RestfulOptions) *PostClient {
    inst := new(PostClient)
    if option != nil {
        inst.option = option
    } else {
        inst.option.Url = "http://127.0.0.1:8888"
    }
    return inst
}

func (inst *PostClient) PostInputs(this string, price float64, number int64, status bool) (data SimpleInfo , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    postMsg := impl.JsonWrapper{}
    postMsg.AddJsonString("Name", this)
    postMsg.AddJsonFloat64("Price", price)
    postMsg.AddJsonInt64("Number", number)
    postMsg.AddJsonBool("Status_1", status)
    request.SetTarget("/restful/post/echo")
    request.SetMethod(impl.POST)
    request.SetPostBody(postMsg.ToJsonString())
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    simpleInfoVar := SimpleInfo{}
    simpleInfoVar.Name = rootObj.GetString("Name")
    simpleInfoVar.Number = rootObj.GetInt64("Number")
    simpleInfoVar.Price = rootObj.GetFloat64("Price")
    simpleInfoVar.Status = rootObj.GetBool("Status_1")
    return simpleInfoVar, nil
}

func (inst *PostClient) Authentication(name string, number int64) (data SimpleInfo , exception error) {
    defer func() {
        if err := recover(); err != nil {
            exception = errors.New(err.(string))
        }
    }()
    request := impl.NewRestfulRequest(inst.option)
    postMsg := impl.JsonWrapper{}
    postMsg.AddJsonInt64("Number", number)
    request.SetTarget("/restful/post/authentication")
    request.SetMethod(impl.POST)
    request.AddQueryString("Name", name)
    request.SetPostBody(postMsg.ToJsonString())
    response := impl.RestfulQuery(request)
    rootObj := impl.ParseJsonFromString(response.GetString())
    simpleInfoVar := SimpleInfo{}
    simpleInfoVar.Name = rootObj.GetString("Name")
    simpleInfoVar.Number = rootObj.GetInt64("Number")
    simpleInfoVar.Price = rootObj.GetFloat64("Price")
    return simpleInfoVar, nil
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
        inst.option.Url = "ws://127.0.0.1:8888"
    }
    return inst
}

func (inst *WSClient) Open(channel string, name string, onConnect func (data impl.WebSocketConnection)) {
    onPingEvent := impl.NewWebSocketEvent(true)
    onPingEvent.SetTrigger(func(msg *impl.WebSocketMessage) bool {
        rootObj := impl.ParseJsonFromString(msg.GetString())
        jsonChecker := new(impl.JsonChecker)
        jsonChecker.CheckEqual("op", "ping", rootObj)
        return jsonChecker.Complete()
    })
    onPingEvent.SetEvent(func(connection *impl.WebSocketConnection, msg *impl.WebSocketMessage)  {
        ts := impl.GetNow("Unix_ms")
        jsonResult := impl.JsonWrapper{}
        jsonResult.AddJsonString("op", "pong")
        jsonResult.AddJsonString("ts", ts)
        connection.Send(jsonResult.ToJsonString())
    })
    inst.connection.RegisterEvent(onPingEvent)
    inst.connection.SetOnConnect(func(connection *impl.WebSocketConnection)  {
        login := impl.JsonWrapper{}
        login.AddJsonString("op", "login")
        obj := impl.JsonWrapper{}
        obj.AddJsonString("Name", name)
        obj0 := impl.JsonWrapper{}
        obj0.AddJsonFloat64("[]", ##STR##_1)
        obj0.AddJsonFloat64("[]", ##STR##_2)
        connection.Send(login.ToJsonString())
        onConnect(connection)
    })
    inst.connection.Connect(fmt.Sprintf("/websocket/%s", channel))
}

func (inst *WSClient) Close(onClose func (data impl.WebSocketConnection)) {
    inst.connection.SetOnClose(func(connection *impl.WebSocketConnection)  {
        onClose(connection)
    })
    inst.connection.Close()
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
        obj := rootObj.GetObject("param")
        simpleInfoVar.Name = obj.GetString("Name")
        simpleInfoVar.Number = obj.GetInt64("Number")
        simpleInfoVar.Price = obj.GetFloat64("Price")
        simpleInfoVar.Status = obj.GetBool("Status")
        onEcho(simpleInfoVar)
    })
    inst.connection.RegisterEvent(onEchoEvent)
    jsonResult := impl.JsonWrapper{}
    jsonResult.AddJsonString("op", "echo")
    obj := impl.JsonWrapper{}
    obj.AddJsonString("Name", name)
    obj.AddJsonFloat64("Price", price)
    obj.AddJsonInt64("Number", number)
    obj.AddJsonBool("Status", status)
    inst.connection.Send(jsonResult.ToJsonString())
}

func (inst *WSClient) SubscribeSecond1(onSecond1 func (data SimpleInfo)) {
    onSecond1Event := impl.NewWebSocketEvent(true)
    onSecond1Event.SetTrigger(func(msg *impl.WebSocketMessage) bool {
        rootObj := impl.ParseJsonFromString(msg.GetString())
        jsonChecker := new(impl.JsonChecker)
        jsonChecker.CheckEqual("ch", "Second1", rootObj)
        return jsonChecker.Complete()
    })
    onSecond1Event.SetEvent(func(connection *impl.WebSocketConnection, msg *impl.WebSocketMessage)  {
        rootObj := impl.ParseJsonFromString(msg.GetString())
        simpleInfoVar := SimpleInfo{}
        simpleInfoVar.Name = rootObj.GetString("Name")
        simpleInfoVar.Number = rootObj.GetInt64("Number")
        simpleInfoVar.Price = rootObj.GetFloat64("Price")
        simpleInfoVar.Status = rootObj.GetBool("Status")
        onSecond1(simpleInfoVar)
    })
    inst.connection.RegisterEvent(onSecond1Event)
    jsonResult := impl.JsonWrapper{}
    jsonResult.AddJsonString("op", "sub")
    jsonResult.AddJsonString("name", "Second1")
    inst.connection.Send(jsonResult.ToJsonString())
}


func restfulAuth(request *impl.RestfulRequest, option *impl.RestfulOptions) {
    timestamp := impl.GetNow("%Y:%M:%DT%h:%m:%s")
    request.AddQueryString("AccessKeyId", option.ApiKey)
    request.AddQueryString("SignatureMethod", "HmacSHA256")
    request.AddQueryString("SignatureVersion", "2")
    request.AddQueryString("Timestamp", timestamp)
    queryString := impl.CombineQueryString(request, impl.ASC, "&")
    method := impl.GetRequestInfo(request, impl.METHOD, impl.UPPERCASE)
    host := impl.GetRequestInfo(request, impl.HOST, impl.NONE)
    target := impl.GetRequestInfo(request, impl.TARGET, impl.NONE)
    buffer := impl.StringWrapper{}
    buffer.AppendStringItem(method)
    buffer.AppendStringItem(host)
    buffer.AppendStringItem(target)
    buffer.AppendStringItem(queryString)
    buffer.CombineStringItems("", "", "\\n")
    hmacsha256 := impl.HMACSHA256(buffer.ToString(), option.SecretKey)
    result := impl.Base64Encode(hmacsha256)
    request.AddQueryString("Signature", result)
}

