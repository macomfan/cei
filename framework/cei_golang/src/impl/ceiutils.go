package impl

import (
	"crypto/hmac"
	"crypto/sha256"
	"encoding/base64"
	"fmt"
	"net/url"
	"sort"
	"strings"
	"time"
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

func GetNow(format string) string {
	if format == "" {
		return ToString(time.Now().Unix()).(string)
	} else if format == "Unix_s" {
		return ToString(time.Now().UnixNano() / 1000 / 1000).(string)
	} else if format == "Unix_ms" {
		return ToString(time.Now().UnixNano() / 1000).(string)
	}
	now := time.Now().UTC()
	timeStr := format
	timeStr = strings.Replace(timeStr, "%Y", fmt.Sprintf("%d", now.Year()), -1)
	timeStr = strings.Replace(timeStr, "%M", fmt.Sprintf("%02d", now.Month()), -1)
	timeStr = strings.Replace(timeStr, "%D", fmt.Sprintf("%02d", now.Day()), -1)
	timeStr = strings.Replace(timeStr, "%h", fmt.Sprintf("%02d", now.Hour()), -1)
	timeStr = strings.Replace(timeStr, "%m", fmt.Sprintf("%02d", now.Minute()), -1)
	timeStr = strings.Replace(timeStr, "%s", fmt.Sprintf("%02d", now.Second()), -1)
	timeStr = strings.Replace(timeStr, "%ms", fmt.Sprintf("%02d", now.Nanosecond()/1000), -1)
	return timeStr
}

type IntSlice [][]string

func (s IntSlice) Len() int           { return len(s) }
func (s IntSlice) Swap(i, j int)      { s[i], s[j] = s[j], s[i] }
func (s IntSlice) Less(i, j int) bool { return s[i][0] < s[j][0] }

func CombineQueryString(request *RestfulRequest, sortMethod Constant, separator string) string {
	queryStrings := request.GetQueryString()
	switch sortMethod {
	case ASC:
		sort.Sort(IntSlice(queryStrings))
	case DSC:
		sort.Sort(sort.Reverse(IntSlice(queryStrings)))
	case NONE:
	// Do nothing
	default:
		panic("Unknown sort type")
	}

	res := ""
	for i, v := range queryStrings {
		for j, _ := range v {
			if res == "" {
				res += separator
			}
			res += queryStrings[i][j]
		}
	}
	return res
}

func GetRequestInfo(request *RestfulRequest, method Constant, convert Constant) string {
	res := ""
	switch method {
	case HOST:
		if urlStruct, ok := url.Parse(request.GetURL()); ok == nil {
			res = urlStruct.Host
		}
	case TARGET:
		res = request.GetTarget()
	case METHOD:
		res = string(request.GetMethod())
	default:
		panic("Unknown method")
	}
	switch convert {
	case UPPERCASE:
		res = strings.ToUpper(res)
	case LOWERCASE:
		res = strings.ToLower(res)
	case NONE:
	default:
		panic("Unknown convert")
	}
	return res
}

func HMACSHA256(input string, key string) []byte {
	hash := hmac.New(sha256.New, []byte(key))
	hash.Write([]byte(input))
	return hash.Sum(nil)
}

func Base64Encode(input []byte) string {
	return base64.StdEncoding.EncodeToString(input)
}
