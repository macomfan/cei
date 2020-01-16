import Bus from './eventbus.js'
import Connection from './connection.js'
import Notification from '../utils/notification.js'

export default {
  app: null,

  init(app) {
    this.app = app
    Bus.subscribe(Bus.ON_CURRNENT_EXCHANGE_CHANGE, (index) => {
      Connection.request('ExInfo', {
        name: app.exchangeList[index]
      }, (data) => {
        Bus.publish(Bus.ON_EXCHANGE_REFRESH, data)
      }, (error) => {
        Notification.showError(app, 'error', error, 3000)
      })
    })
  }
}
