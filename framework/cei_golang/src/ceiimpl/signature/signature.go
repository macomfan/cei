package signature

import (
	"ceiimpl"
	"crypto/hmac"
	"crypto/sha256"
	"encoding/base64"
	"sort"
)

type Constant int

const (
	ASC Constant = iota
	DSC
	HOST
	METHOD
	TARGET
	UPPERCASE
	LOWERCASE
	NONE
)

func CombineStringArray(stringList []string, separator string) string {
	newString := ""
	for _, item := range stringList {
		if newString != "" {
			newString += separator
		}
		newString += item
	}
	return newString
}

func AppendStringArray(stringList []string, value string) []string {
	return append(stringList, value)
}

func Base64(input []byte) string {
	return base64.StdEncoding.EncodeToString(input)
}

func HmacSha256(message string, secret string) []byte {
	key := []byte(secret)
	h := hmac.New(sha256.New, key)
	h.Write([]byte(message))
	return h.Sum(nil)
}

type IntSlice [][]string

func (s IntSlice) Len() int { return len(s) }
func (s IntSlice) Swap(i, j int){ s[i], s[j] = s[j], s[i] }
func (s IntSlice) Less(i, j int) bool { return s[i][0] < s[j][0] }

func CombineQueryString(request *ceiimpl.RestfulRequest, sortMethod Constant, separator string) string {
	queryStrings := request.GetQueryString()
	switch sortMethod {
	case ASC:
		sort.Sort(IntSlice(queryStrings))
	case DSC:
		sort.Sort(sort.Reverse(IntSlice(queryStrings)))
	}
}