package impl

import (
	"strings"
)

type StringWrapper struct {
	items  []string
	result strings.Builder
}

func (inst *StringWrapper) AddStringArray(array []string, trim bool) {
	for _, value := range array {
		inst.items = append(inst.items, value)
	}
}

func (inst *StringWrapper) AppendStringItem(value string) {
	inst.items = append(inst.items, value)
}

func (inst *StringWrapper) CombineStringItems(prefix, suffix, separator string) {

}

func (inst *StringWrapper) ToString() string {
	return inst.result.String()
}
