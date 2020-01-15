import Vue from 'vue';

export default {
  bus: new Vue(),
  ON_API_ERROR: "OnAPIError",
  ON_CURRNENT_EXCHANGE_CHANGE: "OnCurExChange",
  ON_EXCHANGE_DATA_CHANGE: "OnExDataChange",

  publish: function(msg, data) {
    //window.console.log("[BUS] " + msg)
    this.bus.$emit(msg, data);
  },
  subscribe: function(msg, func) {
    this.bus.$on(msg, func);
  }
}
