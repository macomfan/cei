package utils

import (
	"errors"
	"regexp"
	"strconv"
)

var paramNotMatch = errors.New("param not match")

func findReferenceStrings(objStr string) []string {
	r := regexp.MustCompile("\\{.*?}")
	return r.FindAllString(objStr, -1)
}

func ToStringOrDefault(value interface{}) string {
	if value == nil {
		return ""
	} else {
		return ToString(value).(string)
	}
}

func ToString(value interface{}) interface{} {
	if value == nil {
		return nil
	}
	switch v := value.(type) {
	case string:
		return v
	case bool:
		return strconv.FormatBool(v)
	case uint64:
		return strconv.FormatUint(v, 10)
	case int64:
		return strconv.FormatInt(v, 10)
	case float64:
		return strconv.FormatFloat(v, 'f', -1, 64)
	default:
		return nil
	}
}

func ToInt64OrDefault(value interface{}) int64 {
	if value == nil {
		return 0
	} else {
		return ToInt64(value).(int64)
	}
}

func ToInt64(value interface{}) interface{} {
	if value == nil {
		return nil
	}
	switch v := value.(type) {
	case string:
		if res, err := strconv.ParseInt(v, 10, 64); err == nil {
			return res
		} else {
			return nil
		}
	case bool:
		if v {
			return 1
		} else {
			return 0
		}
	case uint64:
		return int64(v)
	case int64:
		return v
	case float64:
		return int64(v)
	default:
		return nil
	}
}

func ToFloat64OrDefault(value interface{}) float64 {
	if value == nil {
		return 0
	} else {
		return ToFloat64(value).(float64)
	}
}

func ToFloat64(value interface{}) interface{} {
	if value == nil {
		return nil
	}
	switch v := value.(type) {
	case string:
		if res, err := strconv.ParseFloat(v, 64); err == nil {
			return res
		} else {
			return nil
		}
	case bool:
		if v {
			return 1
		} else {
			return 0
		}
	case uint64:
		return float64(v)
	case int64:
		return float64(v)
	case float64:
		return v
	default:
		return nil
	}
}

func ToBoolOrDefault(value interface{}) bool {
	if value == nil {
		return false
	} else {
		return ToBool(value).(bool)
	}
}

func ToBool(value interface{}) interface{} {
	if value == nil {
		return nil
	}
	switch v := value.(type) {
	case string:
		if res, err := strconv.ParseBool(v); err == nil {
			return res
		} else {
			return nil
		}
	case bool:
		return v
	case uint64:
	case int64:
	case float64:
		if v == 0 {
			return true
		} else {
			return false
		}
	default:
		return nil
	}
	return nil
}
