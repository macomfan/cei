import Vue from 'vue';

export default {
  bus: new Vue(),
  ON_API_ERROR: "OnAPIError",
  ON_CURRNENT_EXCHANGE_CHANGE: "OnCurExChange",
  ON_EXCHANGE_DATA_CHANGE: "OnExDataChange",
  ON_CONNECT_SWITCH_CHANGE: "OnConnectSwitch",
  ON_LINE_STATUS_CHANGE: "OnLineStatusChange",

  publish: function(msg, data) {
    //window.console.log("[BUS] " + msg)
    this.bus.$emit(msg, data);
  },
  subscribe: function(msg, func) {
    this.bus.$on(msg, func);
  }
}
