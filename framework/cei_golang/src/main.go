package main

import (
	"./impl"
	"encoding/json"
	"fmt"
	"sort"
)

func JsonToMapDemo() {
	jsonStr := `
	{
			"name": "jqw",
			"age": 18,
			"data": [
				{"aaa": "aaa"}
			]
	}
	`
	var mapResult map[string]interface{}
	err := json.Unmarshal([]byte(jsonStr), &mapResult)
	if err != nil {
		fmt.Println("JsonToMapDemo err: ", err)
	}
	//fmt.Println(mapResult)
}

type IntSlice [][]string

func (s IntSlice) Len() int           { return len(s) }
func (s IntSlice) Swap(i, j int)      { s[i], s[j] = s[j], s[i] }
func (s IntSlice) Less(i, j int) bool { return s[i][0] < s[j][0] }


func main() {
	fmt.Println(impl.GetNow("%Y:%M:%DT%h:%m:%s"))

	var queryString [][]string
	queryString = append(queryString, []string{"symbol", "symbol"})
	queryString = append(queryString, []string{"prod", "prod"})
	sort.Sort(sort.Reverse(IntSlice(queryString)))

	//pattern := "\\{.*?}"
	//	//r := regexp.MustCompile(pattern)
	//	//fmt.Println(r.FindAllString("aaaaaaxxxzz",-1)) //Hello World!
	//fmt.Println(utils.FillString("aaa{bbb}ccc{ddd}eee", 123, 456))
	//
	//
	//testWS := testws.NewTestWS()
	//testWS.Start()
	//testWS.Send()
	//
	//JsonToMapDemo()
	//jsonStr := `
	//{
	//		"name": "jqw",
	//		"age": 18,
	//		"data": {"aaa": "aaa"},
	//		"object_array": [{"d":1}, {"d":2}],
	//		"array":["1","2"]
	//
	//}`
	//json := ceiimpl.ParseFromString(jsonStr)
	//fmt.Println(json.GetInt64("age"))
	//data := json.GetObject("data")
	//fmt.Println(data.GetString("aaa"))
	//obj := json.GetObjectArray("object_array")
	//fmt.Println(obj[0].GetInt64("d"))
	//strarr := json.GetStringArray("array")
	//fmt.Println(strarr[1])
	//// testClient := cei.NewTestClient(nil)
	//// timestamp := testClient.GetTimestamp()
	//// fmt.Println(timestamp.Ts)
	//
	//// j := new(json.JsonWrapper)
	//// j.Test()
	//
	//// tmp := make([]string, 3)
	//// fmt.Println(len(tmp))
	//// tmp[0] = "123"
	//// fmt.Println(tmp[1])
	//
	//// tmp1 := make([]int, 0, 3)
	//// tmp1 = append(tmp1, 1)
	//// tmp1 = append(tmp1, 2)
	//// tmp1 = append(tmp1, 3)
	//// tmp1 = append(tmp1, 4)
	//// fmt.Println(len(tmp1))
	//// fmt.Println(cap(tmp1))

}
