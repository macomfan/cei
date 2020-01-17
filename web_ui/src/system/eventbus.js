import Vue from 'vue';

export default {
  bus: new Vue(),
  ON_API_ERROR: "OnAPIError",
  
  /**
   * @param {Object} msg
   * @param {Object} data
   */
  ON_CURRNENT_EXCHANGE_CHANGE: "OnCurExChange",
  ON_EXCHANGE_DATA_CHANGE: "OnExDataChange",
  ON_CONNECT_SWITCH_CHANGE: "OnConnectSwitch",
  ON_LINE_STATUS_CHANGE: "OnLineStatusChange",
  
  ON_EXCHANGE_LIST_REFRESH: "OnExListRef",
  ON_EXCHANGE_INFO_REFRESH: "OnExInfoRef",
  
  UI_ADD_MODEL: "UIAddModel",  // param: modelName

  publish: function(msg, data) {
    window.console.log("[BUS] " + msg)
    this.bus.$emit(msg, data);
  },
  subscribe: function(msg, func) {
    this.bus.$on(msg, func);
    this.bus.$o
  }
}
