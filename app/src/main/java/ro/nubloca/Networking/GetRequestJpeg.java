package ro.nubloca.Networking;

import android.content.Context;
import android.content.SharedPreferences;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class GetRequestJpeg {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    private String  acc_lang, cont_lang, app_code;
    byte[] result;
    public byte[] getRaspuns(Context context, String url, JSONObject resursa) {

        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
  /*      acc_lang = (sharedpreferences.getString("acc_lang", "en"));
        cont_lang = (sharedpreferences.getString("cont_lang", "ro"));*/
        app_code = (sharedpreferences.getString("app_code", "abcdefghijkl123456"));

        JSONObject jsonobject_identificare = new JSONObject();

        JSONObject js = new JSONObject();

        try {
            JSONObject jsonobject_one = new JSONObject();
            JSONObject jsonobject_resursa = new JSONObject();
            JSONArray jsonobject_id = new JSONArray();

            //TODO get app_code from phone
            jsonobject_one.put("app_code", app_code);
            jsonobject_identificare.put("user", jsonobject_one);
            jsonobject_identificare.put("resursa", resursa);

            js.put("identificare", jsonobject_identificare);



        } catch (JSONException e) {
            e.printStackTrace();
        }


        HttpClient httpClient = new DefaultHttpClient();
        HttpBodyGet httpPost = new HttpBodyGet(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Accept", "image/jpeg");
  /*      httpPost.setHeader("Content-Language", cont_lang);
        httpPost.setHeader("Accept-Language", acc_lang);*/


        //Encoding POST data
        try {

            httpPost.setEntity(new ByteArrayEntity(js.toString().getBytes("UTF8")));

        } catch (UnsupportedEncodingException e) {
            // log exception
            e.printStackTrace();
        }

        //making POST request.
        try {
            HttpResponse response = httpClient.execute(httpPost);
            result = EntityUtils.toByteArray(response.getEntity());

        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }

        return result;
    }

}
