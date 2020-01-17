<template>
  <div>
    <el-button-group>
      <el-popover content="New memeber" trigger="hover" placement="bottom">
        <el-button icon="el-icon-edit" slot="reference" @click="onAdd(0)"></el-button>
      </el-popover>
      <el-popover :content="'Add before ' + currentName()" trigger="hover" placement="bottom">
        <el-button icon="el-icon-share" :disabled="currentRow === null" slot="reference"></el-button>
      </el-popover>
      <el-popover :content="'Add after ' + currentName()" trigger="hover" placement="bottom">
        <el-button icon="el-icon-delete" :disabled="currentRow === null" slot="reference"></el-button>
      </el-popover>
    </el-button-group>
    <el-table :data="tableData" style="width: 100%" highlight-current-row @current-change="onCurrentChange">
      <el-table-column label="Members" width="500">
        <template slot-scope="scope">
          <div v-if="scope.row.status">
            <el-cascader placeholder="?" :options="options" :props="{ expandTrigger: 'hover' }" filterable @change="handleChangeType"></el-cascader>
            <el-input placeholder="?" v-model="scope.row.name" class="input-with-select">
              <el-button slot="append" icon="el-icon-check"></el-button>
            </el-input>
          </div>

          <span v-else>
            <el-tag disable-transitions>{{scope.row.type}}</el-tag>
            <span>{{scope.row.name}}</span>
          </span>

        </template>
      </el-table-column>
    </el-table>
  </div>

</template>

<script>
  import Checker from '../utils/checker.js'
  import StaticInfo from '../system/staticInfo.js'
  import DB from '../system/database.js'
  import OP from '../system/operation.js'
  
  export default {
    data() {
      return {
        options: [],
        currentRow: null,
        currentMemeber: '',
        tableData: [{
          name: '王小虎',
          type: '家',
          status: 0
        }, {
          name: '王小虎',
          type: '公司',
          status: 0
        }, {
          name: '王小虎',
          type: '家',
          status: 0
        }, {
          name: '王小虎',
          type: '公司',
          status: 0
        }]
      }
    },
    methods: {
      handleChangeType(value) {
        window.console.log(value)
        if (value[0] === "model_new") {
          OP.addModel("", this) 
        }
      },
      currentName() {
        if (Checker.isNotNull(this.currentRow)) {
          return this.currentRow.name
        }
        return 'error'
      },
      onCurrentChange(val) {
        this.currentRow = val
      },
      onAdd() {
        this.tableData.push({
          name: '',
          type: '',
          status: 1
        })
      }
      
    },
    mounted() {
      DB.bindModelList((data) => {
        this.options = StaticInfo.copyModelTypeOptions()
        this.options.forEach(item => {
          if (item.value === 'model') {
            var models = data
            models.forEach(model => {
              item.children.push({value: model, label: model})
            })
          }
        })
      })
    }
  }
</script>

<style>
  .el-cascader .el-input {
    width: 200px !important;
  }
  .input-with-select .el-input-group__prepend {
    background-color: #fff;
  }
</style>
