import axios from "axios"

export const instance = axios.create({
  baseURL: "http://127.0.0.1:8090", // api 的 base_url
  timeout: 15000, // request timeout
  headers: {
    'Content-Type': 'application/json'
  }
})

export default {
  getExchangInfo() {
    instance.get('/api')
      .then(function(response) {
        window.console.log(response);
      })
      .catch(function(error) {
        window.console.log(error);
      });
    window.console.log("getExchangInfo")
  }
}
