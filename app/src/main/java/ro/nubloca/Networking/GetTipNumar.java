package ro.nubloca.Networking;

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


public class GetTipNumar {


    String url = "http://api.nubloca.ro/tipuri_inmatriculare/";
    String acc_lang, cont_lang;

    private String result;
    private int id_tara;
    private String nume;
    private String ids_used;
    private int id_nume;


    public void setParam(int id_tara, String acc_lang, String cont_lang) {
        this.acc_lang = acc_lang;
        this.cont_lang = cont_lang;
        this.id_tara = id_tara;
    }

    public String getNumeUsed() {
        return nume;
    }

    public int getIdNumeUsed() {
        return id_nume;
    }

    public String getIdsUsed() {
        return ids_used;
    }

    public String getRaspuns() {

        makeRequest();

        /*Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                //handler.sendEmptyMessage(0);
            }
        });
        t.start();*/
        return result;
    }

    public void makeRequest() {

        JSONObject jsonobject_identificare = new JSONObject();
        JSONArray jsonobject_cerute = new JSONArray();
        JSONObject js = new JSONObject();

        try {
            JSONObject jsonobject_one = new JSONObject();
            JSONObject jsonobject_resursa = new JSONObject();
            JSONArray jsonobject_id = new JSONArray();

            //TODO get app_code from phone
            jsonobject_one.put("app_code", "abcdefghijkl123456");

            jsonobject_id.put(id_tara);
            jsonobject_resursa.put("id_tara", jsonobject_id);
            jsonobject_identificare.put("user", jsonobject_one);
            jsonobject_identificare.put("resursa", jsonobject_resursa);
            jsonobject_cerute.put("id");
            jsonobject_cerute.put("nume");
            jsonobject_cerute.put("ids_tipuri_inmatriculare_tipuri_elemente");
            jsonobject_cerute.put("ordinea");
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


        JSONArray raspuns = null;
        try {
            raspuns = new JSONArray(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < raspuns.length(); i++) {
            JSONObject jsonObject1 = new JSONObject();
            try {
                jsonObject1 = (raspuns.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            int ordinea = 0;
            try {
                ordinea = jsonObject1.getInt("ordinea");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (ordinea == 1) {
                //String s = jsonObject1.getClass().getName();
                try {
                    ids_used = jsonObject1.getJSONArray("ids_tipuri_inmatriculare_tipuri_elemente").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    nume = jsonObject1.getString("nume");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    id_nume = jsonObject1.getInt("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


    }


}

