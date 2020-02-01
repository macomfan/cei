<template>
  <div class="menu-bar">

    <div class="bar-item-left">
      <p>Exchange</p>
      <el-select style="padding-left: 5px;" ref="nameInput" v-model="exchangeIndex" filterable placeholder="Name" size="small"
        @change="onSelectExchange">
        <el-option v-for="(item, index) in exchangeList" :key="index" :label="item" :value="index">
          <span>{{ item }}</span>
        </el-option>
      </el-select>
    </div>
    <div class="bar-item-right">
    </div>

  </div>

</template>

<script>
  //import api from '../utils/api.js'
  import DB from '../system/database.js'
  import Checker from '../utils/checker.js'

  export default {
    name: 'MainMenuBar',
    data() {
      return {
        exchangeIndex: '',
        exchangeList: [],
      }
    },
    methods: {
      onSelectExchange() {
        if (Checker.isNotEmpty(this.exchangeList)) {
          DB.updateCurrentExchange(this.exchangeList[this.exchangeIndex])
        }
      },

    },
    mounted() {
      DB.bindExchangeList(this, (data) => {
        this.exchangeList = data
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
    background-color: #EEEEEE;
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
