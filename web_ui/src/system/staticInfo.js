var modelTypeOptions = [{
  value: 'string',
  label: 'String'
}, {
  value: 'boolean',
  label: 'Boolean'
}, {
  value: 'number',
  label: 'Number',
  children: [{
    value: 'integer',
    label: 'Integer'
  }, {
    value: 'long',
    label: 'Long'
  }, {
    value: 'decimal',
    label: 'Decimal'
  }]
}, {
  value: 'array',
  label: 'Array',
  children: [{
    value: 'string',
    label: 'String'
  }, {
    value: 'boolean',
    label: 'Boolean'
  }, {
    value: 'integer',
    label: 'Integer'
  }, {
    value: 'long',
    label: 'Long'
  }, {
    value: 'decimal',
    label: 'Decimal'
  }]
}, {
  value: 'model',
  label: 'Model',
  children: []
}, {
  value: 'model_array',
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
