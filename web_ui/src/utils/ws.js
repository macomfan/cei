import Checker from './checker.js'

function Request(message, func) {
  this.func = func
  this.name = message
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
  OnOpen: undefined,
  OnClose: undefined,
  OnError: undefined,
  
  REQ_OK: 'reqOk',
  REQ_TIMEOUT:'',

  init: function() {
    this.socket = new WebSocket(this.url)
    // 监听socket连接
    this.socket.onopen = () => {
      if (Checker.isVaildFunction(this.OnOpen)) {
        this.OnOpen()
      }
    }
    // 监听socket错误信息
    this.socket.onerror = function() {
    }

    this.socket.onclose = function() {
    }

    // 监听socket消息
    this.socket.onmessage = this.getMessage

    setInterval(() => {
      this.eachSecond()
    }, 1000)
  },
  
  eachSecond: function() {
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
  },
  request: function(message, func) {
    var request = new Request(message, func)
    var id = new Date().getTime()
    this.requestDict[id] = request
    return request
  },

  getMessage: function(msg) {
    window.console.log(msg.data)
  },
  send: function(params) {
    this.socket.send(params)
  },
  close: function() {
    window.console.log("socket已经关闭")
  }
}
