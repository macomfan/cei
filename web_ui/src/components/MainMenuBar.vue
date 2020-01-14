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
      <td>
        <el-dropdown>
          <el-button size="small">
            Setting<i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>host...</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </td>
      <td>Filter</td>
      <td style="width: 150px;">
        <el-input ref="nameInput" v-model="name" size="small"></el-input>
      </td>
      <td>In</td>
      <td style="width: 150px;">
        <el-select v-model="region" placeholder="In" size="small">
          <el-option label="Exchanges" value="Exchanges"></el-option>
          <el-option label="Models" value="Models"></el-option>
          <el-option label="Clients" value="Clients"></el-option>
        </el-select>
      </td>
      <td style="width: 50px;">
        <el-popover placement="top" width="150px" v-model="visible" trigger="manual">
          <p>The input is null</p>
          <div style="text-align: right; margin: 0">
            <el-button type="primary" size="mini" @click="visible = false">ok</el-button>
          </div>
          <el-button slot="reference" @click="onAddFilter" size="small">Apply</el-button>
        </el-popover>
      </td>
      <td>
        <el-tag size="medium" :key="index" v-for="(filter, index) in appliedFilters" closable :disable-transitions="false"
          @close="onCloseFilter(index)">
          {{filter}}
        </el-tag>
      </td>
    </tr>
  </table>

</template>

<script>
  //import api from '../utils/api.js'

  export default {
    name: 'MainMenuBar',
    data() {
      return {
        visible: false,
        name: '',
        region: '',
        appliedFilters: []
      }
    },
    methods: {
      onAddFilter() {

        if (this.name) {
          this.visible = false
          this.appliedFilters.push(this.name)
          this.name = ''
        } else {
          this.visible = true
        }
        this.$refs.nameInput.focus()
        
      },
      onCloseFilter(index) {
        window.console.log(index)
      }
    }
  }
</script>

<style>
  .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
  }
</style>
