<template>
  <div>
    <div class="block">
      <el-cascader :options="options" :props="{ expandTrigger: 'hover' }" filterable size="small" @change="handleChange"></el-cascader>
    </div>
  </div>
</template>

<script>
  import StaticInfo from '../system/staticInfo.js'
  import DB from '../system/database.js'
  import OP from '../system/operation.js'

  export default {
    data() {
      return {
        options: []
      }
    },
    methods: {
      handleChange(value) {
        window.console.log(value)
        if (value[0] === "model_new") {
          OP.addModel("") 
        }
      }
    },
    mounted() {
      DB.bindModelList(this, (data) => {
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
</style>
