<template>
  <div>
    <el-table :data="tableData" style="width: 800px" highlight-current-row @current-change="onCurrentChange">
      <el-table-column label="Members" width="600">
        <template slot="header">
          <span style="font-size: 15px;">Members</span>
        </template>
        <template slot-scope="scope">
          <!-- New Item template -->
          <div v-if="scope.row.status" class="table_new_item">
            <el-cascader placeholder="?" v-model="scope.row.type" :options="options" :props="{ expandTrigger: 'hover' }"
              filterable @change="handleChangeType" size="small"></el-cascader>
            <el-input placeholder="?" v-model="scope.row.name" size="small" class="input-with-select">
            </el-input>
            <el-button slot="append" icon="el-icon-check" size="small" @click="onConfirm(scope.row)"></el-button>
          </div>
          <!-- Display Item template -->
          <div v-else class="table_show_item">
            <div class="table_item_type">
              <span v-for="(item, index) in scope.row.type" :key="index" style="padding-left: 5px;">
                <el-tag disable-transitions v-show="index === 0">{{item}}</el-tag>
                <el-tag disable-transitions v-show="index === 1"><a href="#" @click.prevent="gotoModel(item)">{{item}}</a></el-tag>
              </span>
            </div>
            <div style="width: 20px;">&nbsp;</div>
            <div>
              <span>{{scope.row.name}}</span>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="Operation" width="200">
        <template slot="header">
          <span style="font-size: 15px;"></span>
        </template>
        <template slot-scope="scope">
          <el-button-group ref="buttonGroup" v-show="scope.row === currentRow && scope.row.status !== 1">
            <el-tooltip content="Edit" trigger="hover" placement="bottom">
              <el-button icon="el-icon-setting" @click="onEdit(scope.row)" size="small"></el-button>
            </el-tooltip>
            <el-tooltip content="Add after" trigger="hover" placement="bottom">
              <el-button icon="el-icon-plus" @click="onAdd(0)" size="small"></el-button>
            </el-tooltip>
            <el-tooltip content="Add before" trigger="hover" placement="bottom">
              <el-button icon="el-icon-plus" @click="onAdd(0)" size="small"></el-button>
            </el-tooltip>
            <el-tooltip content="Delete" trigger="hover" placement="bottom">
              <el-button icon="el-icon-delete" @click="onDelete(scope.row)" size="small"></el-button>
            </el-tooltip>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>
    <div class="table_footer">
      <el-button icon="el-icon-plus" @click="onAdd(0)" size="small">New Member</el-button>
      <el-button icon="el-icon-upload2" size="small" type="primary" @click="onSubmit">Submit</el-button>
    </div>
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
        tableData: [],
        changed: 0
      }
    },
    props: ['tableValue', 'tableName'],
    computed: {
      isSelected: function() {
        if (this.currentRow === null) {
          window.console.log("this.currentRow === null")
        } else {
          window.console.log("this.currentRow !== null")
        }
        //this.$refs.buttonGroup.render()
        return this.currentRow !== null
      }
    },
    methods: {
      // --- button event ---
      onEdit(row) {
        row.status = 1
      },
      onDelete(row) {
        this.tableData = this.tableData.filter((p) => p.name !== row.name)
      },
      onConfirm(row) {
        this.$emit('onUserChanged', this.tableName)
        row.status = 0
      },
      onSubmit() {
        var modelUpdated = {
          type: 'xModel',
          _name: this.tableValue,
          a_memberList: []
        }
        this.tableData.forEach(item => {
          var member = {
            _name: item.name,
            type: item.type[0]
          }
          if (item.type.length === 2) {
            member._refer = item.type[1]
          }
          modelUpdated.a_memberList.push(member)
        })
        window.console.log(modelUpdated)
        DB.submitModelUpdate(modelUpdated, () => {
          window.console.log("OK ~~~~~~")
        }) 
      },
      gotoModel(item) {
        window.console.log("======")
        window.console.log(item)
        OP.openModel(item)
      },
      displayType(type) {
        if (type.length === 1) return type[0]
        else if (type.length === 2) return type[0] + ' \\ ' + type[1]
        else return 'UNKNOWN'
      },


      handleChangeType(value) {
        window.console.log(value)
        if (value[0] === "model_new") {
          OP.addModel()
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
      },
      onModelListChange(data) {
        // To update the dropdown list
        this.options = StaticInfo.copyModelTypeOptions()
        this.options.forEach(item => {
          if (item.value === 'Model' || item.value === 'ModelArray') {
            var models = data
            models.forEach(model => {
              item.children.push({
                value: model,
                label: model
              })
            })
          }
        })
      },
      attachModel(model) {
        var memberList = model.a_memberList
        memberList.forEach((member) => {
          var value = {
            name: member._name,
            type: [],
            status: 0
          }
          value.type.push(member.type)
          if (Checker.isNotNull(member._refer)) {
            value.type.push(member._refer)
          }
          this.tableData.push(value);
        })
      },

    },
    mounted() {
      window.console.log("ModelView mounted " + this.tableValue)
      DB.bindModelList(this, this.onModelListChange)
      DB.queryModelDetail(this.tableValue, this.attachModel)
    },
    beforeDestroy() {
      DB.unbindAll(this)
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

  .table_show_item {
    display: flex;
    justify-content: flex-start;
    align-items: center;
  }

  .table_item_type {
    width: 250px;
    display: flex;
    justify-content: flex-end;
    align-items: center;
  }

  .table_new_item {
    width: 100%;
    display: flex;
    justify-content: flex-start;
    align-items: center;
  }

  .table_footer {
    width: 800px;
    display: flex;
    justify-content: flex-end;
    padding-top: 5px;
    align-items: center;
  }

  .table_header {
    width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  a {
    text-decoration: none;
  }

  a:link {
    text-decoration: none;
  }

  a:visited {
    text-decoration: none;
  }

  a:hover {
    text-decoration: none;
  }

  a:active {
    text-decoration: none;
  }

  a:focus {
    text-decoration: none;
  }
</style>
