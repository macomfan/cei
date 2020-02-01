import Bus from './eventbus.js'
import Connection from './connection.js'
import Notification from '../utils/notification.js'
import Checker from '../utils/checker.js'

/**
 * ["exchangA", "exchangeB", "exchangeC"]
 */
var exchangeList = null

function updateExchangeList(data) {
  window.console.log(data)
  exchangeList = data
  Bus.publish(Bus.ON_EXCHANGE_LIST_REFRESH)
}

/**
 * {
 * "name"   :"exchangNameA",
 * "models" :["nameA","nameB"],
 * "clients":[
 *            {"name"     : "clientNameA",
 *             "interface": ["intfA", "intfB"]},
 *            {"name"     : "clientNameB",
 *             "interface": ["intfA", "intfB"]},
 *           ]
 * }
 */
var exchangeInfo = null

function updateExchangeInfo(data) {
  exchangeInfo = data
  exchangeInfo.models = exchangeInfo.models.sort()
  Bus.publish(Bus.ON_EXCHANGE_INFO_REFRESH)
}

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

var exchangeDetail = []

var currentExchange = ''

function callError(error, data) {
  if (Checker.isVaildFunction(error)) {
    error(data)
  }
}

export default {
  ON_EXCHANGE_LIST_UPDATE: 'exlistupt',

  init() {
    Connection.request(Connection.REQ_INIT, {}, (data) => {
      window.console.log("Request ok" + data.exchanges)
      updateExchangeList(data.exchanges)
    }, () => {
      window.console.log("Request error")
    })

    Bus.subscribe(Bus.ON_CURRNENT_EXCHANGE_CHANGE, this, () => {
      Connection.request(Connection.REQ_EXCHANG_INFO, {
        exchangeName: currentExchange
      }, (data) => {
        updateExchangeInfo(data)
      }, (error) => {
        Notification.showError('error', error, 3000)
      })
    })
  },

  getCurrentExchange() {
    return currentExchange
  },

  queryExchangeDetail(func, error) {
    if (Checker.isNotNull(exchangeDetail[currentExchange])) {
      // Found
      func(exchangeDetail[currentExchange])
    } else {
      // Not found, request it
      Connection.request(Connection.REQ_EXCHANGE_QUERY, {
        exchangeName: currentExchange
      }, (data) => {
        exchangeDetail[currentExchange] = data
        func(data)
      }, (errMsg) => {
        callError(error, errMsg)
      })
    }
  },

  queryModelDetail(modelName, func, error) {
    this.queryExchangeDetail((data) => {
      if (Checker.isNull(data.a_modelList)) {
        callError(error, 'Cannot get model list')
        return
      }
      var model = data.a_modelList.find((p) => p._name === modelName)
      if (Checker.isNull(model)) {
        callError(error, 'Cannot get model list for ' + modelName)
        return
      }
      func(model)
    }, (errMsg) => {
      callError(error, errMsg)
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
  
  submitModelUpdate(modelUpdated, result) {
    Connection.request(Connection.REQ_MODEL_UPDATE, {
      exchangeName: currentExchange,
      data: modelUpdated
    }, () => {
      result()
    }, () => {
      
    })
  },

  unbindAll(inst) {
    Bus.unsubscribeAll(inst)
  }
}
