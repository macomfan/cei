export default {
  showError(vm, title, message, duration) {
    vm.$notify.error({
      title: title,
      message: message,
      duration: duration
    })
  },
  showSuccess(vm, title, message, duration) {
    vm.$notify.success({
      title: title,
      message: message,
      duration: duration
    })
  }
}
