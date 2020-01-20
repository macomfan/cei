import Checker from './checker.js'
var app = null

export default {
  init(vm) {
    app = vm
  },

  showConfirm(title, msg, onOK, onCancel) {
    app.$confirm(msg, title, {
      confirmButtonText: 'Ok',
      cancelButtonText: 'Cancel',
      type: 'warning'
    }).then(() => {
      if (Checker.isVaildFunction(onOK)) {
        onOK()
      }
    }).catch(() => {
      if (Checker.isVaildFunction(onCancel)) {
        onCancel()
      }
    });
  }
}
