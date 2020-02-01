
var app = null

export default {
  init(vm) {
    app = vm
  },
  
  showInfo(message) {
    app.$message({
      type: 'info',
      message: message
    });
  },

  showError(message) {
    app.$message.error(message);
  },
  
  showSuccess(message) {
    app.$message({
      type: 'success',
      message: message
    });
  }
}
