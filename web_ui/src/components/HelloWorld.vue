<template>
  <div>
    <el-form  :model="form" label-width="60psx">
      <el-form-item label="审批人">
        <el-input v-model="form.name" placeholder="审批人"></el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
    data() {
      return {
        form: {
          name: '',
          region: ''
        }
      }
    },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
