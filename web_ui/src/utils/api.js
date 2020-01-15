import axios from 'axios'
import Bus from './eventbus.js'


const instance = axios.create({
  baseURL: "http://127.0.0.1:8090", // api 的 base_url
  timeout: 15000, // request timeout
  headers: {
    'Content-Type': 'application/json'
  }
})

export default {
  getExchangSummary(func) {
    instance.get('/api/getExchangeSummary')
      .then(response => {
        window.console.log(response.data)
        func(response.data)
      })
      .catch(error => {
        Bus.publish(Bus.ON_API_ERROR, error)
      });
    window.console.log("getExchangInfo")
  }
  
  // addModel(exchangeName, modelName, func) {
    
  // }
}
