import Message from '../utils/message.js'
import Connection from './connection.js'
import Bus from './eventbus.js'
import DB from './database.js'

var app = null

export default {
  init(vm) {
    app = vm
  },
  addModel() {
    app.$prompt('Please input new Model name', 'Notice', {
      confirmButtonText: 'OK',
      cancelButtonText: 'Cancel',
      inputPattern: /^\w+$/,
      inputErrorMessage: 'The name can be only a-z, A-Z, 0-9 and _',
    }).then(({
      value
    }) => {
      Connection.request(Connection.REQ_MODEL_TEST, {
        exchangeName: DB.getCurrentExchange(),
        modelName: value
      }, () => {
        Bus.publish(Bus.UI_ADD_MODEL, value)
      }, (code, msg) => {
        Message.showError(msg)
      })
    }).catch(() => {
      Message.showInfo('Adding canceled')
    });
  },

  openModel(data) {
    Bus.publish(Bus.UI_OPEN_MODEL, data)
  }
}
