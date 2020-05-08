from impl.restfulrequest import RestfulRequest
from impl.websocketoptions import WebSocketOptions
import re
from impl.jsonwrapper import JsonWrapper
import decimal
from impl.restfuloptions import RestfulOptions
from exchanges import test
from exchanges import huobipro


# from exchanges.huobipro import *

def format_str(fff, *args):
    return fff.format(*args)


if __name__ == '__main__':

    try:
        iii = bool(0)
    except BaseException as e:
        print(e)

    # print(format_str("/websocket/{0}/{1}", "chan",111))
    data = '''
        {
      "status": "ok",
      "ch": "market.ethusdt.depth.step0",
      "ts": 1550218546616,
      "tick": {
        "bids": [
          [
            122.920000000001230000,
            2.746800000000000000
          ],
    	  [
            120.300000000000000000,
            494.745900000000000000
          ]
        ],
        "asks": [
          [
            122.940000000000000000,
            67.554900000000000000
          ],
    	  [
            124.620000000000000000,
            50.000000000000000000
          ]
        ],
        "ts": 1550218546020,
        "version": 100416549839
      }
    } 
    '''

    client = test.GetClient()
    info = client.get_simple_info()
    info = client.get_price_list()
    print(info)

    # option = RestfulOptions()
    # option.apiKey = "AAA"
    # option.secretKey = "aaa"
    #
    # postClient = test.PostClient()
    # simpleInfo = postClient.post_inputs(111, 333.333, 123, False)
    # print(simpleInfo.name)
    #
    # ws_client = test.WSClient()

    # def on_connect(connection):
    #     print("onconnect callback")
    #
    #
    # ws_client.open("event", "test", on_connect)
    #
    #
    # def on_echo1(data):
    #     print("echo1")
    #
    #
    # ws_client.request_echo("abc", 123.123, 123, False, on_echo1)
    #
    #
    # def on_echo2(data):
    #     print("echo2")
    #
    #
    # ws_client.request_echo("abc", 123.123, 123, False, on_echo2)
    #
    #
    # def on_echo3(data):
    #     print("echo3")
    #
    #
    # ws_client.request_echo("abc", 123.123, 123, False, on_echo3)
    #
    #
    # def on_second1(data):
    #     print("on_second1")
    #
    #
    # ws_client.subscribe_second1(on_second1)
    vvv = str(None)
    print(vvv)
    key = "[1]"
    m = re.match("^\\[[0-9]*]$", key)
    if m is not None:
        key = key[m.pos + 1:m.endpos - 1]
    json1 = JsonWrapper()
    key_aa = json1.get_array_or_none("aa")
    json2 = JsonWrapper.parse_from_string(data)
    print(json2.get_int("ts"))
    print(json2.to_json_string())
    new_json = JsonWrapper()
    new_json.add_json_string("aa", "aa")
    new_json.add_json_number("bb", decimal.Decimal("123.0000000456891234567"))
    new_json2 = JsonWrapper()
    new_json2.add_json_string("aa", "aa")
    new_json2.add_json_number("bb", 111.222)
    new_json.add_json_object("json2", new_json2)
    print(new_json.to_json_string())
    # ws = TestWSClient()

    # def suf(msg):
    #     print("callback " + msg)

    # ws.sub("aaa", suf)
    # ws.start()
    # ws.send("mmmmmm")

    # option = RestfulOptions()
    # marketClient = MarketClient()
    # data = marketClient.get_candlestick("btcusdt", "1min", 5)
    # print(data)
    #
    # data = marketClient.get_last_trade("btcusdt")
    # print(data)

    # asyncio.run(main())
    # place_order(None, None)
    # lll = list()
    # lll.append()
    # SignatureTool.Constant.ASC = "ccc"
    #
    #
    # request = RestfulRequest()
    # request.set_method(RestfulRequest.Method.GET)
    # response = RestfulConnection.query(request)
    # json_wrapper = response.get_json()
    # exchange_name = json_wrapper.get_string("exchangeName")
    #
    # request.add_query_string("aaa", 1)
    # request.add_query_string("bbb", 2)
    # request.add_query_string("ccc", 3)
    # print(request.build_query_string())
    # #RestfulConnection.query(request)
    # client = Test.MarketClient()
    # options = RestfulOptions()
    # options.api_key = "ABC"
    # options.secret_key = "abc"
    # testClient = TestClient(options)
    # timestamp = testClient.get_timestamp()
    # hClient = HuobiProClient()
    # bClient = BinanceClient()
    #
    # print('Test ts: ' + str(timestamp.ts))
    # print('H ts: ' + str(hClient.get_timestamp().ts))
    # print('B ts: ' + str(bClient.get_timestamp().ts))
    pass
