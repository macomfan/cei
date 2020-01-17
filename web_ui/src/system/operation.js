import Message from '../utils/message.js'
import Connection from './connection.js'
import Bus from './eventbus.js'

export default {
  addModel(exchangeName, vm) {
    if (vm === null || vm === undefined) {
      window.console.log("Error")
    }
    exchangeName = ''
    vm.$prompt('Please input new Model name', 'Notice', {
      confirmButtonText: 'OK',
      cancelButtonText: 'Cancel',
      inputPattern: /^\w+$/,
      inputErrorMessage: 'The name can be only a-z, A-Z, 0-9 and _',
    }).then(({
      value
    }) => {
      //Message.showInfo('Adding Model: ' + value)
      Connection.request(Connection.REQ_MODEL_TEST, {
        name: value
      }, () => {
        Bus.publish(Bus.UI_ADD_MODEL, value)
      }, (code, msg) => {
        Message.showError(msg)
      })
    }).catch(() => {
      Message.showInfo('Adding canceled')
    });
  }
}
