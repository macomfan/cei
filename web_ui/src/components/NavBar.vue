<template>
  <div style="width: 250px;">
    <table>
      <tr>
        <td>
          <el-input v-model="filterString" placeholder="Filter" size="small"></el-input>
        </td>
      </tr>
      <tr>
        <td>
          <el-tree :data="navList" node-key="id" :expand-on-click-node="false" empty-text="No Exchanged selected"
            @node-click="onNodeClick">
            <span class="custom-tree-node" slot-scope="{ node, data }">
              <span>{{ node.label }}</span>
              <span>
                <el-link v-show="isShowPlus(data.name)" :underline="false" type="success" @click="onAdd(data)">Add</el-link>
                <el-link v-show="isShowSub(data.name)" :underline="false" type="danger">Del</el-link>
              </span>
            </span>
          </el-tree>
        </td>
      </tr>
    </table>

  </div>

</template>

<script>
  import Bus from '../utils/eventbus.js'
  import OP from '../utils/operation.js'

  export default {
    props: {
      exchangeList: {
        type: Array
      }
    },
    currentExchangeIndex: -1,

    data() {
      return {
        navList: [],
        filterString: "",
        isShow: false
      }
    },
    methods: {
      onNodeClick() {

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
          OP.addModel('', this)
        }
      },
      refreshNavList() {
        this.navList = []
        var tmp = this.exchangeList[this.currentExchangeIndex]
        var item = new Object()
        item.name = 'ROOT_' + tmp.name
        item.label = tmp.name
        item.children = []
        if (tmp.models !== null && tmp.models !== undefined) {
          var modelsNode = new Object()
          modelsNode.name = 'MODELS_' + tmp.name
          modelsNode.label = 'Models'
          modelsNode.children = []
          tmp.models.forEach((model) => {
            var modelItem = new Object()
            modelItem.name = model
            modelItem.label = model
            modelsNode.children.push(modelItem)
          })

          item.children.push(modelsNode)
        }
        this.navList.push(item)
      }
    },
    mounted() {
      Bus.subscribe(Bus.ON_CURRNENT_EXCHANGE_CHANGE, (index) => {
        this.currentExchangeIndex = index
        this.refreshNavList()
      })
      Bus.subscribe(Bus.ON_EXCHANGE_DATA_CHANGE, (index) => {
        this.currentExchangeIndex = index
        this.refreshNavList()
      })
      window.console.log("NarBar mounted")
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
    height: 40px !important;
  }
</style>
