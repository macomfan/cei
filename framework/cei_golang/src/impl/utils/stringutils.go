package utils

import (
	"errors"
	"regexp"
	"strconv"
	"strings"
)

var paramNotMatch = errors.New("param not match")

func findReferenceStrings(objStr string) []string {
	r := regexp.MustCompile("\\{.*?}")
	return r.FindAllString(objStr, -1)
}

func ToString(value interface{}) string {
	switch v := value.(type) {
	case string:
		return string(v)
	case bool:
		return strconv.FormatBool(v)
	case uint8:
		return strconv.FormatUint(uint64(v), 10)
	case uint16:
		return strconv.FormatUint(uint64(v), 10)
	case uint32:
		return strconv.FormatUint(uint64(v), 10)
	case uint64:
		return strconv.FormatUint(v, 10)
	case uint:
		return strconv.FormatUint(uint64(v), 10)
	case int8:
		return strconv.FormatInt(int64(v), 10)
	case int16:
		return strconv.FormatInt(int64(v), 10)
	case int32:
		return strconv.FormatInt(int64(v), 10)
	case int64:
		return strconv.FormatInt(int64(v), 10)
	case int:
		return strconv.FormatInt(int64(v), 10)
	case float32:
		return strconv.FormatFloat(float64(v), 'f', -1, 64)
	case float64:
		return strconv.FormatFloat(float64(v), 'f', -1, 64)
	default:
		// TODO not supported
		return ""
	}
}

func FillString(objStr string, args ...interface{}) (string, error) {
	refList := findReferenceStrings(objStr)
	if len(refList) != len(args) {
		return "", paramNotMatch
	}
	newStr := objStr
	for index, refStr := range refList  {
		newStr = strings.Replace(newStr, refStr, ToString(args[index]), 1)
	}
	return newStr, nil
}
