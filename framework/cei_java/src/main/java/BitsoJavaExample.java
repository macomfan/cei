import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.HttpURLConnection;

public class BitsoJavaExample {
    public static void main(String[] args) throws Exception {
        String bitsoKey = "BITSO API KEY";
        String bitsoSecret = "BITSO API SECRET";
        long nonce = System.currentTimeMillis();
        String HTTPMethod = "GET";
        String RequestPath = "/v3/balance/";
        String JSONPayload = "";

        // Create the signature
        String message = nonce + HTTPMethod + RequestPath + JSONPayload;
        String signature = "";
        byte[] secretBytes = bitsoSecret.getBytes();
        SecretKeySpec localMac = new SecretKeySpec(secretBytes, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(localMac);
        byte[] arrayOfByte = mac.doFinal(message.getBytes());
        BigInteger localBigInteger = new BigInteger(1, arrayOfByte);
        signature = String.format("%0" + (arrayOfByte.length << 1) + "x", new Object[] { localBigInteger });

        String authHeader = String.format("Bitso %s:%s:%s", bitsoKey, nonce, signature);
        String url = "https://api.bitso.com/v3/balance/";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Bitso Java Example");
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", authHeader);

        // Send request
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response.toString());
    }
}