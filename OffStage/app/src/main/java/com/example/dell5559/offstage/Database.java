package com.example.dell5559.offstage;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Database {

    HttpURLConnection connection;

    public Database(String url) {
        try {
            URL url1 = new URL(url);
            connection = (HttpURLConnection) url1.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encodeData(String key,String value){

        String str = null;
        try {
            str = URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    public void writedb(String postdata){

        try {
        OutputStream os = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(postdata);
        writer.flush();
        writer.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String readdb(){

        String result = "";
       try {
           InputStream is = connection.getInputStream();
           BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
           result = "";
           String line;

           while ((line = reader.readLine()) != null) {
               result += line;
           }
           Log.e("readdb===>","  ==================== ");
           Log.e("readdb===>",result);
           reader.close();
           is.close();
       }catch (Exception e){
           e.printStackTrace();
       }

        return result;
    }

    public String convertImage(Bitmap FixBitmap){

        byte[] byteArray;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byteArray = byteArrayOutputStream.toByteArray();
        String ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return ConvertImage;

    }

    public void disconnect(){
        connection.disconnect();
    }

}
