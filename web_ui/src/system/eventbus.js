import Vue from 'vue';
import Checker from '../utils/checker.js'

var subscription = []

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
  UI_OPEN_MODEL: "UIOpenModel",  // param: modelList

  publish: function(msg, data) {
    this.bus.$emit(msg, data);
  },
  subscribe: function(msg, inst, func) {
    if (Checker.isNull(inst) || !Checker.isVaildFunction(func)) {
      window.console.log(inst)
      throw "[BUS] inst is null or not a object"
    }
    subscription.push([inst, msg, func])
    this.bus.$on(msg, func);
  },
  unsubscribeAll: function(inst) {
    var tmp = []
    subscription.forEach((item) => {
      if (item[0] !== inst) {
        tmp.push(item)
      } else {
        window.console.log("###off")
        this.bus.$off(item[1], item[2])
      }
    })
    subscription = tmp
  }
}
