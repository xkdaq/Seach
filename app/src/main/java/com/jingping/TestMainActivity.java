package com.jingping;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class TestMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("xxxxx","解密结果====="+decrypts("RmFyUExDYUFpMWkxK2oza3lzYmp6QVN2N2xCUFpiVjFHRHpTYVdyWkpNWT0="));
        Log.e("xxxxx","解密结果====="+decrypts("WWM1Y1gvdXRtZGJwMTNmMHNRR3RuVitwdjhEY1hmSHc="));
        Log.e("xxxxx","解密结果====="+decrypts("c3VzQzMrSDlJSEM4NzFpZTZDcmJJZz09"));
        Log.e("xxxxx","解密结果====="+decrypts("U3F3SWRxYnR1YnVyMXRkOW5YN2NrYnpTYlRWcno5blpJbUt2YjVWWEE3emxyMmpCS296TE13PT0="));
        Log.e("xxxxx","解密结果====="+decrypts("alJQOXB5aXBzdVk5V2UvMitMRU9YODhGb3A4czJEOG0="));
        Log.e("xxxxx","解密结果====="+decrypts("U3F3SWRxYnR1YnVyMXRkOW5YN2NrZlp2Q0xjMlFWMlR1WFoyT1B4M2Vjb04vbGJOV24wMEFRPT0="));
        Log.e("xxxxx","解密结果====="+decrypts("U3F3SWRxYnR1YnVyMXRkOW5YN2NrYnpTYlRWcno5blpiWDZRUTh4Z3NPbmxyMmpCS296TE13PT0="));
    }


    public static String decrypts(String encryptedText) {
        try {
            // 使用Base64解码密文
            byte[] t10 = Base64.getDecoder().decode(encryptedText);
            byte[] t11 = Base64.getDecoder().decode("aWp1c3g3bDQ="); //cash box
            //byte[] t11 = Base64.getDecoder().decode("YW1wbG9hbjY="); //AMP
            String str2 = new String(t10);
            try {
                Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                cipher.init(2, new SecretKeySpec(t11, "DES"));
                return new String(cipher.doFinal(Base64.getDecoder().decode(str2)),"utf-8");
            } catch (Exception e10) {
                e10.printStackTrace();
                return str2;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
