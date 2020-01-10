package json

import (
	"encoding/json"
	"fmt"
	"github.com/buger/jsonparser"
)

type JsonWrapper struct {
	data []byte
}

func NewJsonWrapper(data []byte) *JsonWrapper {
	instance := new(JsonWrapper)
	instance.data = data
	return instance
}

func (t *JsonWrapper) GetString(key string) string {
	fmt.Println("get json string")
	res, _ := jsonparser.GetString(t.data, key)
	return res
}

func (t *JsonWrapper) GetInt64(key string) int64 {
	fmt.Println("get json int64")
	res, _ := jsonparser.GetInt(t.data, key)
	return res
}

func (t *JsonWrapper) GetObject(key string) *JsonWrapper {
	fmt.Println("get json object")
	res, _ := jsonparser.Get(t.data, key)
	return NewJsonWrapper(data)
}

func (this *JsonWrapper) Test() {
	data := []byte(`{
		"person": {
		  "name": {
			"first": "Leonid",
			"last": "Bugaev",
			"fullName": "Leonid Bugaev"
		  },
		  "github": {
			"handle": "buger",
			"followers": 109
		  },
		  "avatars": [
			{ "url": "https://avatars1.githubusercontent.com/u/14009?v=3&s=460", "type": "thumbnail" }
		  ]
		},
		"company": {                  
		  "name": "Acme"
		}
	}`)
	res, _ := jsonparser.GetString(data, "person", "name", "fullName")

	testarr := [2]int{1,2}
	map1 := make(map[string]interface{})
	map1["1"] = "hello"
	map1["2"] = 123.123
	map1["3"] = testarr
	//return []byte
	str, err := json.Marshal(map1)

	if err != nil {
		fmt.Println(err)
	}
	fmt.Println("map to json", string(str))
	fmt.Println(res)
}
