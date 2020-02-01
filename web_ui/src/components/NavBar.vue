<template>
  <div style="nav-bar">
    <div>
      <el-input v-model="filterString" placeholder="Filter" size="small"></el-input>
    </div>
    <div>
      <el-tree :data="navList" node-key="id" default-expand-all :expand-on-click-node="false" empty-text="No Exchanged selected"
        @node-click="onNodeClick">
        <span class="custom-tree-node" slot-scope="{ node, data }">
          <span>{{ node.label }}</span>
          <span>
            <el-link v-show="isShowPlus(data.name)" :underline="false" type="success" @click="onAdd(data)">Add</el-link>
            <el-link v-show="isShowSub(data.name)" :underline="false" type="danger">Del</el-link>
          </span>
        </span>
      </el-tree>
    </div>
  </div>

</template>

<script>
  var MODEL_TYPE = 'Models'
  
  import OP from '../system/operation.js'
  import Checker from '../utils/checker.js'
  import DB from '../system/database.js'

  export default {
    name: 'NavBar',
    data() {
      return {
        navList: [],
        filterString: "",
        isShow: false,
        exchangeInfo: {}
      }
    },
    methods: {
      onNodeClick(data) {
        if (data.type === MODEL_TYPE) {
          window.console.log(data)
          DB.queryModelDetail(data.name, (data) => {
            OP.openModel(data._name)
          }, (errmsg) => {
            window.console.log("Error onNodeClick " + errmsg)
          })
        }
      },
      isShowPlus(name) {
        if (name.indexOf('ROOT_') === -1 && name.indexOf("MODELS_") !== -1) {
          return true
        }
      },
      isShowSub(name) {
        if (name.indexOf('ROOT_') === -1 && name.indexOf("MODELS_") === -1) {
          return true
        }
      },
      onAdd(data) {
        if (data.name.indexOf('MODELS_') !== -1) {
          OP.addModel('')
        }
      },
      refreshNavList() {
        if (Checker.isNull(this.exchangeInfo)) {
          return
        }
        this.navList = []
        window.console.log("MMM ")
        window.console.log(this.exchangeInfo)
        // Models
        if (Checker.isNotNull(this.exchangeInfo.models)) {
          var modelsNode = new Object()
          modelsNode.name = 'MODELS_' + this.exchangeInfo.name
          modelsNode.label = MODEL_TYPE
          modelsNode.children = []
          this.exchangeInfo.models.forEach((model) => {
            var modelItem = new Object()
            modelItem.name = model
            modelItem.label = model
            modelItem.type = 'Models'
            modelsNode.children.push(modelItem)
          })
          this.navList.push(modelsNode)
        }
        // Clients
        if (Checker.isNotNull(this.exchangeInfo.clients)) {
          var clientsNode = new Object()
          clientsNode.name = 'CLIENTS_' + this.exchangeInfo.name
          clientsNode.label = 'Clients'
          clientsNode.children = []
          this.exchangeInfo.clients.forEach((client) => {
            var clientNode = new Object()
            clientNode.name = client.name
            clientNode.label = client.name
            clientNode.children = []
            client.interfaces.forEach((intf) => {
              var interfaceNode = new Object()
              interfaceNode.name = intf
              interfaceNode.label = intf
              clientNode.children.push(interfaceNode)
            })
            clientsNode.children.push(clientNode)
          })
          this.navList.push(clientsNode)
        }
      }
    },
    mounted() {
      window.console.log("NarBar mounted")
      DB.bindExchangeInfo(this, (data) => {
        this.exchangeInfo = data
        window.console.log("ON_EXCHANGE_INFO_REFRESH " + this.exchangeInfo)
        this.refreshNavList()
      })
    }
  }
</script>

<style>
  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 5px;
  }

  .el-tree-node__content {
    height: 30px !important;
  }

  .nav-bar {
    width: 900px !important;
    display: flex;
    align-items: flex-start;
    flex-direction: column;
  }
</style>
