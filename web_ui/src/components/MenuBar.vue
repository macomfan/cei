<template>
  <div class="menu-bar">

    <div class="bar-item-left">
      <p>Exchange &nbsp;</p>
      <el-select style="padding-left: 5px;" ref="nameInput" v-model="exchangeIndex" filterable placeholder="Name" size="small"
        @change="onSelectExchange">
        <el-option v-for="(item, index) in exchangeList" :key="index" :label="item.name" :value="index">
        </el-option>
      </el-select>
    </div>
    <div class="bar-item-right">
      <el-switch v-model="connected" active-color="#13ce66" inactive-color="#7c0000" @change="onConnectedSwitchChange">
      </el-switch>
    </div>

  </div>

</template>

<script>
  //import api from '../utils/api.js'
  import Bus from '../utils/eventbus.js'

  export default {
    name: 'MainMenuBar',
    props: {
      exchangeList: {
        type: Array
      }
    },
    data() {
      return {
        exchangeIndex: '',
        connected: false
      }
    },
    methods: {
      onSelectExchange() {
        Bus.publish(Bus.ON_CURRNENT_EXCHANGE_CHANGE, this.exchangeIndex)
      },

      onConnectedSwitchChange() {
        window.console.log("test")
        window.console.log(Bus.ON_CURRNENT_EXCHANGE_CHANGE)
        window.console.log(Bus.ON_ABC)
        //Bus.publish(Bus.ON_CONNECT_SWITCH_CHANGE, this.connected)
      }
    },
    mounted() {
      Bus.subscribe(Bus.ON_LINE_STATUS_CHANGE, (status) => {
        this.connected = status
      })
      window.console.log("MainMenuBar mounted")
    }
  }
</script>

<style>
  .menu-bar {
    height: 40px;
    display: flex;
    flex: 1;
    justify-content: space-between;
    align-items: center;
  }

  .bar-item-left {
    width: 100px;
    display: flex;
    flex: 1;
    align-items: center;
    padding-left: 5px;
  }

  .bar-item-right {
    width: 100px;
    display: flex;
    flex: 1;
    justify-content: flex-end;
    align-items: center;
    padding-right: 5px;
  }

  .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
  }
</style>
