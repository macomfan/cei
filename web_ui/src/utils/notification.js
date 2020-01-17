var app = null

export default {
  init(vm) {
    app = vm
  },
  
  showError(title, message, duration) {
    app.$notify.error({
      title: title,
      message: message,
      duration: duration
    })
  },
  showSuccess(title, message, duration) {
    app.$notify.success({
      title: title,
      message: message,
      duration: duration
    })
  }
}
