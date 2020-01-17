export default {
  isNull(value) {
    return value === null || value === undefined
  },
  
  isNotNull(value, canBeIgnore) {
    var res = !this.isNull(value)
    if (!res && !canBeIgnore) {
      // TODO
    }
    return res
  },

  isEmpty(value) {
    if (value === null || value === undefined) {
      return true
    }
    if (value instanceof Array) {
      return value.length === 0
    } else if (value instanceof Object) {
      return Object.keys(value).length === 0
    } else if (value instanceof String) {
      return value === ''
    }
    return false
  },
  
  isNotEmpty(value) {
    return !this.isEmpty(value)
  },

  isVaildFunction(func, canBeIgnore) {
    if (this.isNotNull(func, canBeIgnore)) {
      var res = (func instanceof Function)
      if (!res && !canBeIgnore) {
        // TODO
      }
      return res
    }
    return false
  }
}
