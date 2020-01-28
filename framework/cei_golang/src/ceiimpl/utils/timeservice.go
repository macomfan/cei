package utils

import (
	"fmt"
	"strings"
	"time"
)

func GetNow(format string) string {
	now := time.Now()
	if format == "s" {
		return ToString(now.Unix())
	} else if format == "ms" {
		return ToString(now.UnixNano() / 1e6 )
	} else if format == "ns" {
		return ToString(now.UnixNano())
	} else {
		timeStr := format
		timeStr = strings.Replace(timeStr, "{yyyy}", fmt.Sprintf("%d", now.Year()), -1)
		timeStr = strings.Replace(timeStr, "{mm}", fmt.Sprintf("%d", now.Month()), -1)
		timeStr = strings.Replace(timeStr, "{dd}", fmt.Sprintf("%d", now.Day()), -1)
		timeStr = strings.Replace(timeStr, "{HH}", fmt.Sprintf("%02d",now.Hour()), -1)
		timeStr = strings.Replace(timeStr, "{MM}", fmt.Sprintf("%02d", now.Minute()), -1)
		timeStr = strings.Replace(timeStr, "{SS}", fmt.Sprintf("%02d", now.Second()), -1)
		return timeStr
	}
}
