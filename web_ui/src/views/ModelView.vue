<template>
  <div style="width: 600px;">
  <el-button @click="add">add</el-button>
  <el-table
    ref="filterTable"
    :data="tableData"
    style="width: 100%">
    <el-table-column
      prop="date"
      label="日期"
      sortable
      width="180"
      column-key="date"
    >
      <template slot-scope="scope">
        <el-date-picker
          v-if="scope.row.status"
          v-model="scope.row.date"
          type="date"
          placeholder="选择日期">
        </el-date-picker>
        <span v-else>{{scope.row.date}}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="name"
      label="姓名"
      width="180">
        <template slot-scope="scope">
          <el-input v-if="scope.row.status" v-model="scope.row.name"></el-input>
          <span v-else>{{scope.row.name}}</span>
        </template>
    </el-table-column>
    <el-table-column
      prop="address"
      label="地址"
      :formatter="formatter">
    </el-table-column>
    <el-table-column
      prop="tag"
      label="标签"
      width="100"
      :filters="[{ text: '家', value: '家' }, { text: '公司', value: '公司' }]"
      :filter-method="filterTag"
      filter-placement="bottom-end">
      <template slot-scope="scope">
        <el-tag
          :type="scope.row.tag === '家' ? 'primary' : 'success'"
          disable-transitions>{{scope.row.tag}}</el-tag>
      </template>
    </el-table-column>
  </el-table>
  
  
  <el-table
    ref="filterTable"
    :data="tableData"
    style="width: 100%">
    <el-table-column
      prop="date"
      label="日期"
      width="500"
      column-key="date"
    >
      <template slot-scope="scope">
          <el-input placeholder="请输入内容" v-model="scope.row.name" v-if="scope.row.status" class="input-with-select">
            <el-select v-model="select" slot="prepend" placeholder="请选择" >
              <el-option label="String" value="1"></el-option>
              <el-option label="Int" value="2"></el-option>
              <el-option label="Long" value="3"></el-option>
              <el-option label="StringArray" value="4"></el-option>
            </el-select>
            <el-button slot="append" icon="el-icon-check"></el-button>
          </el-input>
        <span v-else>{{scope.row.date}}</span>
      </template>
    </el-table-column>
  </el-table>
  </div>

</template>

<script>
  export default {
     data() {
       return {
         select: '',
         tableData: [{
           date: '2016-05-02',
           name: '王小虎',
           address: '上海市普陀区金沙江路 1518 弄',
           tag: '家'
         }, {
           date: '2016-05-04',
           name: '王小虎',
           address: '上海市普陀区金沙江路 1517 弄',
           tag: '公司'
         }, {
           date: '2016-05-01',
           name: '王小虎',
           address: '上海市普陀区金沙江路 1519 弄',
           tag: '家'
         }, {
           date: '2016-05-03',
           name: '王小虎',
           address: '上海市普陀区金沙江路 1516 弄',
           tag: '公司'
         }]
       }
     },
     methods: {
       resetDateFilter() {
         this.$refs.filterTable.clearFilter('date');
       },
       clearFilter() {
         this.$refs.filterTable.clearFilter();
       },
       formatter(row) {
         return row.address;
       },
       filterTag(value, row) {
         return row.tag === value;
       },
       filterHandler(value, row, column) {
         const property = column['property'];
         return row[property] === value;
       },
       add() {
           this.tableData.map(item=>{
             if(item.status){
               item.status = 0;
             }
             return item;
           })
         
         this.tableData.push({ date: '2019-4-01', name: '默认数据', address: '上海', tag: '公司', status: 1 }); }
     }
  }
</script>

<style>
    .el-select .el-input {
      width: 130px;
    }
    .input-with-select .el-input-group__prepend {
      background-color: #fff;
    }
</style>
