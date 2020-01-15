export default {
  isNotNull(value, canBeIgnore) {
    var res = (value !== null || value !== undefined) ? true : false
      if (!res && !canBeIgnore) {
        // TODO
      }
    return res
  },
  
  isVaildFunction(func, canBeIgnore) {
    if (this.isNotNull(func, canBeIgnore)) {
      var res = (func instanceof Function) ? true : false
      if (!res && !canBeIgnore) {
        // TODO
      }
      return res
    }
    return false
  }
}
