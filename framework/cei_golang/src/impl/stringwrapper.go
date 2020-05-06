package impl

import "strings"

type StringWrapper struct {
	items  []string
	result strings.Builder
}

func (inst *StringWrapper) AppendStringItem(value string) {

}

func (inst *StringWrapper) CombineStringItems(separator string) {

}

func (inst *StringWrapper) ToString() string {
	return inst.result.String()
}
