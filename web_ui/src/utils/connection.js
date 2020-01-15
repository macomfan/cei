import Checker from './checker.js'

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

function Request(message, func) {
  this.func = func
  this.name = message
  this.errorFunc = undefined
  this.error = function(errorFunc) {
    Checker.isVaildFunction(errorFunc)
    this.errorFunc = errorFunc
  }
}

export function Connection() {
  this.url = "ws://127.0.0.1:8090"
  this.requestTimeout = 10
  this.socket = null
  this.requestDict = new Array()
  this.connectStatus = ConnectStatus.CLOSED
  this.onOpen = null
  this.onClose = null
  this.onError = null
  
  this.open = function() {
    if (this.connectStatus === ConnectStatus.CLOSED) {
      this.connectStatus = ConnectStatus.CONNECTING
      this.socket = new WebSocket(this.url)
      this.socket.onopen = () => {
        this.connectStatus = ConnectStatus.CONNECTED
        invoke(this.onOpen)
      }
      this.socket.onclose = () => {
        this.connectStatus = ConnectStatus.CLOSED
        invoke(this.onClose)
      }
      this.socket.onerror = () => {
        invoke(this.onError)
      }
      setInterval(() => {
        this.eachSecond()
      }, 1000)
    } else {
      return;
    }
  }
  
  this.request = function(message, func) {
    var request = new Request(message, func)
    var id = new Date().getTime()
    this.requestDict[id] = request
    return request
  }
  
  this.eachSecond = function() {
    var ts = new Date().getTime()
    for (var key in this.requestDict) {
      if (ts - key > this.requestTimeout * 1000) {
        var request = this.requestDict[key]
        delete this.requestDict[key]
        if (Checker.isVaildFunction(request.errorFunc)) {
          request.errorFunc()
        }
      }
    }
  }
}