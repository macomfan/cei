import Checker from '../utils/checker.js'

var ConnectStatus = {
  CONNECTED: 1,
  CLOSED: 2,
  CONNECTING: 3
};

function invoke(func, data) {
  if (Checker.isVaildFunction(func)) {
    func(data)
  }
}

function processRequestResponse(ws, msg) {
  window.console.log("processRequestResponse")
  var id = msg.id
  var request = ws.requestDict[id]
  if (Checker.isNull(request)) {
    window.console.error("Request lost")
    return
  }
  if (Checker.isVaildFunction(request.func)) {
    request.func(msg.data)
  }
  window.console.log("processRequestResponse end")
  delete ws.requestDict[id]
}

function send(ws, msg) {
  if (msg instanceof Request) {
    var request = {type: 'req', msg: msg.name, id: msg.id, param: msg.param}
     window.console.log("[WS] Send: " + JSON.stringify(request))
    ws.socket.send(JSON.stringify(request))
  }
}

function eachSecond(ws) {
  var ts = new Date().getTime()
  for (var key in ws.requestDict) {
    if (ts - key > ws.requestTimeout * 1000) {
      var request = ws.requestDict[key]
      window.console.log("Checking")
      delete ws.requestDict[key]
      if (Checker.isVaildFunction(request.errorFunc)) {
        request.errorFunc()
      }
    }
  }
}

function Request(message, param, func) {
  this.func = func
  this.name = message
  this.param = param
  this.id = 0
  this.errorFunc = undefined
  this.error = function(errorFunc) {
    Checker.isVaildFunction(errorFunc)
    this.errorFunc = errorFunc
  }
}

export default {
  url: "ws://127.0.0.1:8090",
  requestTimeout: 10,
  socket: null,
  requestDict: new Array(),
  connectStatus: ConnectStatus.CLOSED,
  onOpen: null,
  onClose: null,
  onError: null,
  
  REQ_INIT: 'init',
  REQ_EXCHANG_INFO: 'ExInfo',
  REQ_MODEL_INFO: 'ModInfo',
  REQ_MODEL_TEST: 'ModTest',
  REQ_MODEL_ADD: 'ModAdd',

  isSupport: function() {
    return !(typeof(WebSocket) === "undefined")
  },

  open: function() {
    if (this.connectStatus === ConnectStatus.CLOSED) {
      this.connectStatus = ConnectStatus.CONNECTING
      this.socket = new WebSocket(this.url)
      this.socket.onopen = () => {
        this.connectStatus = ConnectStatus.CONNECTED
        invoke(this.onOpen)
        setInterval(() => {
          eachSecond(this)
        }, 1000)
      }
      this.socket.onclose = () => {
        this.connectStatus = ConnectStatus.CLOSED
        invoke(this.onClose)
      }
      this.socket.onerror = () => {
        invoke(this.onError)
      }
      this.socket.onmessage = (msg) => {
        window.console.log("[WS] Rec: "+ msg.data)
        var data = JSON.parse(msg.data)
        if (Checker.isNull(data.status) || Checker.isNull(data.data)) {
          window.console.error("Received unexpected message")
          return
        }
        if(Checker.isNotNull(data.id)) {
          processRequestResponse(this, data)
        } else if (Checker.isNotNull(data.ts)) {
          // TODO
        } else {
          window.console.error("Received unexpected message")
        }
        

      }
    } else {
      return;
    }
  },
  
  close: function() {
    if (this.connectStatus === ConnectStatus.CONNECTED) {
      this.socket.close()
    }
  },

  request: function(message, param, response, error) {
    if (this.connectStatus !== ConnectStatus.CONNECTED) {
      // TODO
      return
    }
    var request = new Request(message, param, response)
    request.errorFunc = error
    request.id = new Date().getTime()
    this.requestDict[request.id] = request
    send(this, request)
    return request
  }
}
