package cei.func;
import static org.junit.Assert.assertEquals;
import cei.exchanges.test;
import cei.impl.RestfulOptions;
import org.junit.Test;

import java.math.BigDecimal;

public class testMain {

    @Test
    public void testGet() {
        test.GetClient client = new test.GetClient();
        test.SimpleInfo simpleInfo = client.getSimpleInfo();
        assertEquals("test", simpleInfo.name);
        assertEquals(123L, simpleInfo.number.longValue());
        assertEquals("123.456", simpleInfo.price.toPlainString());
        assertEquals(true, simpleInfo.status);

        test.ModelInfo modelInfo = client.getModelInfo();
        assertEquals("ModelInfo", modelInfo.name);
        assertEquals("ModelValue", modelInfo.value.name);
        assertEquals(123L, modelInfo.value.value.longValue());

        test.PriceList priceList = client.getPriceList();
        assertEquals("PriceList", priceList.name);
        assertEquals(2, priceList.prices.size());
        assertEquals("123.456", priceList.prices.get(0).price.toPlainString());
        assertEquals("0.1", priceList.prices.get(0).volume.toPlainString());
        assertEquals("456.789", priceList.prices.get(1).price.toPlainString());
        assertEquals("0.2", priceList.prices.get(1).volume.toPlainString());

        test.InfoList infoList = client.getInfoList();
        assertEquals("InfoList", infoList.name);
        assertEquals(3, infoList.values.size());
        assertEquals("Value1", infoList.values.get(0).infoValue);
        assertEquals("Value2", infoList.values.get(1).infoValue);
        assertEquals("Value3", infoList.values.get(2).infoValue);

        test.SimpleInfo simpleInfo1 = client.getSimpleInfo();
        assertEquals("test", simpleInfo1.name);
        assertEquals(123L, simpleInfo1.number.longValue());
        assertEquals("123.456", simpleInfo1.price.toPlainString());
        assertEquals(true, simpleInfo1.status);


        test.SimpleInfo simpleInfo2 = client.inputsByGet("aaa", 111L, new BigDecimal("222.222"), false);
        assertEquals("aaa", simpleInfo2.name);
        assertEquals(111L, simpleInfo2.number.longValue());
        assertEquals("222.222", simpleInfo2.price.toPlainString());
        assertEquals(false, simpleInfo2.status);

        String res = client.url("testURL");
        assertEquals("testURL", res);
    }

    @Test
    public void testPost() {
        RestfulOptions option = new RestfulOptions();
        option.apiKey= "AAA";
        option.secretKey = "aaa";

        test.PostClient postClient = new test.PostClient(option);
        test.SimpleInfo simpleInfo = postClient.postInputs("post", new BigDecimal("333.333"), 345L, false);
        assertEquals("post", simpleInfo.name);
        assertEquals(345L, simpleInfo.number.longValue());
        assertEquals("333.333", simpleInfo.price.toPlainString());
        assertEquals(false, simpleInfo.status);

        //postClient.authentication("AUTH", 123L);
    }
}
