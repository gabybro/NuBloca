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


public class GetRequest {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    private String result, acc_lang, cont_lang;

    public String getRaspuns(Context context, String url, JSONObject resursa, JSONArray cerute) {

        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        acc_lang = (sharedpreferences.getString("acc_lang", "en"));
        cont_lang = (sharedpreferences.getString("cont_lang", "ro"));

        JSONObject jsonobject_identificare = new JSONObject();
        JSONArray jsonobject_cerute = new JSONArray();
        JSONObject js = new JSONObject();

        try {
            JSONObject jsonobject_one = new JSONObject();
            JSONObject jsonobject_resursa = new JSONObject();
            JSONArray jsonobject_id = new JSONArray();

            //TODO get app_code from phone
            jsonobject_one.put("app_code", "abcdefghijkl123456");
            jsonobject_identificare.put("user", jsonobject_one);

            //jsonobject_id.put("ACTIV");
            //jsonobject_resursa.put("status", jsonobject_id);

            jsonobject_identificare.put("resursa", resursa);

            //jsonobject_cerute.put("id");
            //jsonobject_cerute.put("nume");
            //jsonobject_cerute.put("ids_tipuri_inmatriculare_tipuri_elemente");
            //jsonobject_cerute.put("ordinea");
            for (int i = 0; i < cerute.length(); i++) {
                jsonobject_cerute.put(cerute.get(i));
            }


            js.put("identificare", jsonobject_identificare);
            js.put("cerute", jsonobject_cerute);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        HttpClient httpClient = new DefaultHttpClient();
        HttpBodyGet httpPost = new HttpBodyGet(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Content-Language", cont_lang);
        httpPost.setHeader("Accept-Language", acc_lang);


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
            result = EntityUtils.toString(response.getEntity());

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

