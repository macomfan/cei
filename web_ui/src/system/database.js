import Bus from './eventbus.js'
import Connection from './connection.js'
import Notification from '../utils/notification.js'
import Checker from '../utils/checker.js'

/**
 * ["exchangA", "exchangeB", "exchangeC"]
 */
var exchangeList = null

/**
 * {
 * "name"   :"exchangNameA",
 * "models" :["nameA","nameB"],
 * "clients":{
 *           "name"     : "clientName",
 *           "interface": ["intfA", "intfB"]
 *           }
 * }
 */
var exchangeInfo = null

// function CEIMember(name) {
//   this.name = name
//   this.type = ''
//   this.defValue = ''
// }

// function CEIModel(name) {
//   this.name = name
//   this.members = []
// }
  

// function CEIExchange (name) {
//   this.name = name
//   this.clients = []
//   this.models = []
  
//   this.lookupModel = function(modelName) {
//     if (Checker.isEmpty(this.models)) {
//       return null
//     }
//     this.models.forEach((model) => {
//       if (model.name === name) {
//         return model
//       }
//     })
//     return null
//   }
// }

// var exchangeDetail = []

var currentExchange = ''

export default {
  ON_EXCHANGE_LIST_UPDATE: 'exlistupt',

  init() {
    Connection.request(Connection.REQ_INIT, {}, (data) => {
      window.console.log("Request ok" + data.exchanges)
      this.updateExchangeList(data.exchanges)
    }, () => {
      window.console.log("Request error")
    })
    
    Bus.subscribe(Bus.ON_CURRNENT_EXCHANGE_CHANGE, this, (data) => {
      Connection.request(Connection.REQ_EXCHANG_INFO, {
        name: data
      }, (data) => {
        this.updateExchangeInfo(data)
      }, (error) => {
        Notification.showError('error', error, 3000)
      })
    })
  },

  updateCurrentExchange(data) {
    currentExchange = data
    Bus.publish(Bus.ON_CURRNENT_EXCHANGE_CHANGE)
  },
  
  bindCurrentExchange(inst, func) {
    func(currentExchange)
    Bus.subscribe(Bus.ON_CURRNENT_EXCHANGE_CHANGE, inst, () => {
      func(currentExchange)
    })
  },

  /**
   * @param ["exchangA", "exchangeB", "exchangeC"]
   */
  updateExchangeList(data) {
    window.console.log(data)
    exchangeList = data
    Bus.publish(Bus.ON_EXCHANGE_LIST_REFRESH)
  },
  
  updateExchangeInfo(data) {
    exchangeInfo = data
    Bus.publish(Bus.ON_EXCHANGE_INFO_REFRESH)
  },
  
  /**
   * @param func(["exchangA", "exchangeB", "exchangeC"])
   */
  bindExchangeList(inst, func) {
    if (!Checker.isVaildFunction(func)) {
      return
    }
    func(exchangeList)
    Bus.subscribe(Bus.ON_EXCHANGE_LIST_REFRESH, inst, () => {
      func(exchangeList)
    })
  },
  
  /**
   * @param func({
   *              "name"   :"exchangNameA",
   *              "models" :["nameA","nameB"],
   *              "clients":{
   *                "name"     : "clientName",
   *                "interface": ["intfA", "intfB"]
   *                }
   *              })
   */
  bindExchangeInfo(inst, func) {
    if (!Checker.isVaildFunction(func)) {
      return
    }
    window.console.log("bindExchangeInfo")
    func(exchangeInfo)
    Bus.subscribe(Bus.ON_EXCHANGE_INFO_REFRESH, inst, () => {
      func(exchangeInfo)
    })
  },

  /**
   * @return ["nameA","nameB"]
   */
  bindModelList(inst, func) {
    if (!Checker.isVaildFunction(func)) {
      return
    }
        
    if (Checker.isNotNull(exchangeInfo) && Checker.isNotNull(exchangeInfo.models)) {
      func(exchangeInfo.models)
    }
    
    Bus.subscribe(Bus.ON_EXCHANGE_INFO_REFRESH, inst, () => {
      func(exchangeInfo.models)
    })
  },
  
  // bindModelData(func, modelName) {
    
  // }
  
  unbindAll(inst) {
    Bus.unsubscribeAll(inst)
  }
}
