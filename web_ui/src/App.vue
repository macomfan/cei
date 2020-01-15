<template>
  <!--  <div id="app">
    <el-row>
      <el-col :span="24">
        <div style="text-align: left;">Welcome to CEI</div>
      </el-col>
      <el-row>
        <el-col :span="24" style="text-align: left;">
          <MainMenuBar></MainMenuBar>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="2">
          <NavBar></NavBar>
        </el-col>
        <el-col :span="20">
          <HelloWorld msg="Welcome to Your Vue.js App" />
        </el-col>
      </el-row>
    </el-row>
    <table style="width: 100%;">
      <tr>WWW</tr>
      <tr style="background-color: #409EFF; height: 1px;"></tr>
      <tr>222</tr>
    </table>
  </div> -->
  <div id="app" style="width: 100%;">
    <table style="width: 100%;">
      <tr>
        <td style="background-color: aliceblue;">
          <div style="margin: 20px; font-size: 20px; float: left;">Welcome to CEI</div>
        </td>
      </tr>
      <tr style="height: 1px; background-color: #2C3E50;">
        <td></td>
      </tr>
      <tr>
        <td style="background-color:#EEEEEE">
          <MenuBar :exchangeList="exchangeList"></MenuBar>
        </td>
      </tr>
      <tr>
        <td>
          <table style="margin-top: 5px;">
            <tr>
              <td style="vertical-align: top;">
                <NavBar :exchangeList="exchangeList"></NavBar>
              </td>
              <td style="width: 1px; background-color: #EEEEEE;"></td>
              <td style="width: 1000px; vertical-align: top;">
                <MainView></MainView>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>

  </div>

</template>

<script>
  import MainView from './components/MainView.vue'
  import MenuBar from './components/MenuBar.vue'
  import NavBar from './components/NavBar.vue'
  import Bus from './utils/eventbus.js'
  import API from './utils/api.js'
  import Notification from './utils/notification.js'
  import {Connection} from './utils/connection.js'

  export default {
    name: 'app',
    data() {
      return {
        exchangeList: []
      }
    },
    components: {
      MainView,
      MenuBar,
      NavBar
    },
    mounted() {
      if (typeof(WebSocket) === "undefined") {
        this.$notify.error({
          title: 'Error',
          message: 'Your browser does not support Websocket!',
          duration: 0
        });
        return;
      }
      this.conn = new Connection()
      this.conn.onOpen = () => {
        this.conn.request("aaa", () => {
          window.console.log("Request error")
        })
      }
      this.conn.open()

      window.console.log("APP mounted")
      Bus.subscribe(Bus.ON_API_ERROR, (data) => {
        Notification.showError(this, 'Error', 'API invoking error', 0)
        window.console.log(data)
      })
      API.getExchangSummary(data => {
        this.exchangeList = data.exchanges
      })
    }
  }
</script>

<style>
  #app {
    font-family: Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;
  }
</style>
