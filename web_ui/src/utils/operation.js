export default {
  addModel(exchangeName, vm) {
    if (vm === null || vm === undefined) {
      window.console.log("Error")
    }
    exchangeName = ''
    vm.$prompt('Please input new Model name', 'Notice', {
      confirmButtonText: 'OK',
      cancelButtonText: 'Cancel',
      inputPattern: /^\w+$/,
      inputErrorMessage: 'The name can be only a-z, A-Z, 0-9 and _',
    }).then(({
      value
    }) => {
      vm.$message({
        type: 'info',
        message: 'Adding Model: ' + value
      });
    }).catch(() => {
      vm.$message({
        type: 'info',
        message: 'Add canceled'
      });
    });
  }
}
