import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CalcAES {
    private final String key = "sougouappno.0001";

    public String enc(String str) throws Exception {
        return enc(str, key);
    }

    public String enc(String str, String key) throws Exception {
        Cipher localCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        localCipher.init(1, new SecretKeySpec(key.getBytes(), "AES"), getIv());
        return new String(Base64.getEncoder().withoutPadding().encode(localCipher.doFinal(str.getBytes("UTF-8"))), "UTF-8");
    }

    public String enc(byte[] content) throws Exception {
        Cipher localCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        localCipher.init(1, new SecretKeySpec(key.getBytes(), "AES"), getIv());
        return new String(Base64.getEncoder().withoutPadding().encode(localCipher.doFinal(content)), "UTF-8");
    }

    public String dec(String str) throws Exception {
        return dec(str, key);
    }

    public String dec(String str, String key) throws Exception {
        Cipher localCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        localCipher.init(2, new SecretKeySpec(key.getBytes(), "AES"), getIv());
        return new String(localCipher.doFinal(Base64.getDecoder().decode(str)), "UTF-8");
    }

    public IvParameterSpec getIv() {
        return new IvParameterSpec(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
    }

    public static void main(String[] args) {
        CalcAES obj = new CalcAES();
        try {
//            System.out.println(obj.enc("{\"mid\":\"eae2353490067655296\",\"xid\":\"c7676cec3fd8f1122452c6202d1af0d162ff\",\"imsi\":\"\",\"userinfo\":{\"network\":1,\"model\":\"Nexus 5\",\"screen_width\":1080,\"distribution\":\"3053\"},\"timeMillis\":\"1485085960073\",\"os\":\"android\",\"req_ver\":3,\"ver\":\"5101\",\"os_ver\":22,\"location\":\"116.331,39.993\",\"action\":1,\"end_stream_id\":1,\"channel\":{\"id\":1,\"name\":\"热门\",\"subid\":-1},\"needcatlist\":false}"));
//            System.out.println(obj.dec("+qB35kmTbFGstMYlxUzq/jGhenOfi/w+gjjPIZhXUlkpFwVWu37a5Xfd9HkrqhnJen4JxArXCi+PCVIZXkIA8HU7tKc9OPQZ+cm8XRbYSf/3sV0aLvmYoiMZV7sStlr9Yq8nJKxKVsQVSG/kD0sLvDM4W+ChAn1g5rvbS3duM6cKglcUpO8+RlFJHxnfyIbztK1Lhy3vqI7T15wlAcx4qY1dj8G/j0vQW7T0STfUGfl1ZZrC9PScmGgzNYy5QF89qYXo9gHl/Aag7sabPa3TkiVOi/1nArNMVT95h2DOTb2hzIuMF7bUG1o3UxMCoWWms4dtYbiaUApGGsZGefpMTlZzSRUbY53ovdxxbhnXP9n02ZiPgB9w24avOZ+O6xh5/12O/Xnetc7alZe1ay7mYMiSNp8HGus9yedYCNe8rzPB9Tai/EllDIMepZpXOwWCM+PXEe2zvTqZ/sd7uWHq8shhlHaL4FfsHSLShUiA1/kPBYeUmL/JX6+AeWQH45aKLJRts8g/nvcdlAuS8+5yxGmXNVLrDGaIH6glup41aU32udRIJgNH1L8MigehSCUL"));
            System.out.println(obj.dec("ofveMQzBoOy5vOS+h92oAKBPnX0s5urTnujwKxOgYVc7dWN2kX77hbaqWnQ9M41LeNFETuwIQkRFZqID4x3d6xyjH2egbtsUlXg6E/Ztbw4REPnRFJKzM29N6nnhQmo5k+hMrQsDLYGfBPlXk9dT+wY6G+BdxRDhjXQoRqEPC9e6aPiYBSjwRhRqPwW2EmHXhxqqVDPk1rzf0XpS4yrNbATg6J4r5YRrXyBm0O/XZEtXUn0ZKItEP6keG0KqCQOTWQyVbpO/STApAA0QNwjmEK6LjTnlcFlX8IzXVIpLFa3OGQu9kuMhiK4I4hEhmvKLFLAfuWDyXxaZ1rqnPPaFzHPXM+aERfHKIctjTkJB9CWFFeevKfns3A4+vXtzPUIbKWoG8WUCKQ8FqqXjVAOxXDbmKu8Ji/ST9EhMSVLvsng="));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
