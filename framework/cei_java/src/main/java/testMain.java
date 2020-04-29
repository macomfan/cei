import cn.ma.cei.exchanges.huobipro;
import cn.ma.cei.exchanges.test;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;

public class testMain {
    public static void main(String[] args) {
//        test.WSClient wsClient = new test.WSClient();
//        wsClient.connect("request", "echo");
//        wsClient.requestEcho("bbbb", new BigDecimal("111"), 111L, false, (echo)->{
//            System.err.println(echo.name);
//        });
        huobipro.WSClient wsClient = new huobipro.WSClient();
        wsClient.open();
        wsClient.requestCandlestick("btcusdt","1min", data->{
            System.err.println(data.amount.toPlainString());
        });
    }
}
