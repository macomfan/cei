package impl

import (
	"strings"
)

type StringWrapper struct {
	items  []string
	result strings.Builder
}

func NewStringWrapper() *StringWrapper {
	inst := new(StringWrapper)
	return inst
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
	inst.result.Reset();
	for _, item := range inst.items {
		if inst.result.Len() != 0 {
			inst.result.WriteString(separator);
		}
		inst.result.WriteString(prefix + item + suffix);
	}
}

func (inst *StringWrapper) ToString() string {
	return inst.result.String()
}
