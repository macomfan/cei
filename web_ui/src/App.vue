<template>
  <div id="app" class="window">
    <div class="header">
      <span style="font-size: 20px;">Welcome to CEI</span>
      <span style="align-self: flex-end; font-size: 10px;">ver: 0.0.1</span>
    </div>
    <div class="h-line"></div>
    <div>
      <MenuBar :exchangeList="exchangeList"></MenuBar>
    </div>
    <div class="h-line"></div>
    <div class="main-view">
      <NavBar style="width: 300px;"></NavBar>
      <div class="v-line"></div>
      <MainView :exchangeList="exchangeList"></MainView>
    </div>
  </div>

</template>

<script>
  import MainView from './components/MainView.vue'
  import MenuBar from './components/MenuBar.vue'
  import NavBar from './components/NavBar.vue'
  import Bus from './system/eventbus.js'
  import Notification from './utils/notification.js'
  import Connection from './system/connection.js'
  import Logic from './system/logic.js'

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
    methods: {
      initWebsocket() {
        if (!Connection.isSupport()) {
          Notification.showError(this, 'Error', 'Your browser does not support Websocket!', 0)
          return
        }

        Connection.onOpen = () => {
          window.console.log("Connected")
          Notification.showSuccess(this, "Success", "Connect successfully", 3000)
          Bus.publish(Bus.ON_LINE_STATUS_CHANGE, true)
          Connection.request(Connection.REQ_INIT, {}, (data) => {
            window.console.log("Request ok" + data.exchanges)
            Logic.init(this)
            this.exchangeList = data.exchanges
          }, () => {
            window.console.log("Request error")
          })
        }
        Connection.onClose = () => {
          window.console.log("Closed")
          Notification.showError(this, 'Error', 'API connection failure', 3000)
          Bus.publish(Bus.ON_LINE_STATUS_CHANGE, false)
        }
        Connection.open()
      }
    },
    mounted() {
      this.initWebsocket()
      window.console.log("APP mounted")
    },
    beforeDestroy() {
      Connection.close()
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

  .window {
    display: flex;
    flex-direction: column;
    height: 100%;
  }

  .header {
    height: 40px;
    background-color: aliceblue;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-left: 20px;
    padding-right: 20px;
    padding-bottom: 5px;
    padding-top: 5px;
  }

  .h-line {
    background-color: #dadece;
    height: 1px;
    width: 100%;
  }

  .v-line {
    background-color: #EEEEEE;
    width: 2px;
    margin-left: 5px;
    margin-right: 5px;
  }

  .main-view {
    width: 100%;
    flex-grow: 1;
    display: flex;
    height: 100%;
    align-items: stretch;
    padding-top: 2px;
  }
</style>
