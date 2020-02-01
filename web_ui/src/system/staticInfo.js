var modelTypeOptions = [{
  value: 'String',
  label: 'String'
}, {
  value: 'Boolean',
  label: 'Boolean'
}, {
  value: 'Number',
  label: 'Number',
  children: [{
    value: 'Integer',
    label: 'Integer'
  }, {
    value: 'Long',
    label: 'Long'
  }, {
    value: 'Decimal',
    label: 'Decimal'
  }]
}, {
  value: 'Array',
  label: 'Array',
  children: [{
    value: 'StringArray',
    label: 'String'
  }, {
    value: 'BooleanArray',
    label: 'Boolean'
  }, {
    value: 'IntegerArray',
    label: 'Integer'
  }, {
    value: 'LongArray',
    label: 'Long'
  }, {
    value: 'DecimalArray',
    label: 'Decimal'
  }]
}, {
  value: 'Model',
  label: 'Model',
  children: []
}, {
  value: 'ModelArray',
  label: 'ModelArray',
  children: []
}, {
  value: 'model_new',
  label: 'New Model...'
}]


export default {
  copyModelTypeOptions() {
    return JSON.parse(JSON.stringify(modelTypeOptions))
  }
}
