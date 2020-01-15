<template>
  <!--  <el-row>
    <el-col :span="8">
      <el-form :inline="true" :model="form" class="demo-form-inline">
        <el-form-item label="Search">
          <el-input v-model="form.name" size="small"></el-input>
        </el-form-item>
        <el-form-item label="In">
          <el-select v-model="form.region" placeholder="In" size="small">
            <el-option label="Exchanges" value="Exchanges"></el-option>
            <el-option label="Models" value="Models"></el-option>
            <el-option label="Clients" value="Clients"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="onclick">Apply</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row> -->
  <table>
    <tr>
      <!--      <td>
        <el-dropdown>
          <el-button size="small">
            Setting<i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>host...</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </td> -->
      <td>Exchange</td>
      <td style="width: 150px;">
        <el-select ref="nameInput" v-model="exchangeIndex" filterable placeholder="Name" size="small" @change="onSelectExchange">
          <el-option v-for="(item, index) in exchangeList" :key="index" :label="item.name" :value="index">
          </el-option>
        </el-select>
      </td>
      <!--      <td style="width: 50px;">
        <el-popover placement="top" width="150px" v-model="visible" trigger="manual" ref="selectBtn">
          <p>The input is null</p>
          <div style="text-align: right; margin: 0">
            <el-button type="primary" size="mini" @click="visible = false">ok</el-button>
          </div>
          <el-button slot="reference" @click="onSelectExchange" size="small">Select</el-button>
        </el-popover>
      </td> -->
    </tr>
  </table>

</template>

<script>
  //import api from '../utils/api.js'
  import Bus from '../utils/eventbus.js'

  export default {
    name: 'MainMenuBar',
    props: {
      exchangeList : {
        type: Array
      }
    },
    data() {
      return {
        exchangeIndex: ''
      }
    },
    methods: {
      onSelectExchange() {
        Bus.publish(Bus.ON_CURRNENT_EXCHANGE_CHANGE, this.exchangeIndex)
      },
      
      refreshNav() {
        
      }
    },
    mounted() {
      window.console.log("MainMenuBar mounted")
    }
  }
</script>

<style>
  .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
  }
</style>
