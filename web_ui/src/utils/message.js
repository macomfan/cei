export default {
  showInfo(vm, message) {
      vm.$message({
        type: 'info',
        message: message
      });
  }
}
