package ro.nubloca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

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

import ro.nubloca.Networking.HttpBodyGet;

public class Ecran23Activity extends AppCompatActivity {
    JSONArray response_ids_inmatriculare = new JSONArray();
    String url = "http://api.nubloca.ro/tipuri_inmatriculare/";
    String url1 = "http://api.nubloca.ro/tipuri_elemente/";
    String result_string, result_string1;
    String name_tip_inmatriculare;
    int x = 0;
    boolean thread1=false;
    boolean thread2=false;
    int campuri=0;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    String acc_lang="en";
    String cont_lang="ro";
    int tip_inmat=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran23);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        acc_lang = (sharedpreferences.getString("acc_lang", "en"));
        cont_lang = (sharedpreferences.getString("cont_lang", "ro"));
        tip_inmat = (sharedpreferences.getInt("tip_inmat", 1));

        makePostRequestOnNewThread();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        View flag = (View) findViewById(R.id.flag);
        if(flag!=null)
        flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Ecran23Activity.this, Ecran25Activity.class));
            }
        });

        LinearLayout exemplu1 = (LinearLayout) findViewById(R.id.linear1);
        if(exemplu1!=null)
        exemplu1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Ecran23Activity.this, Ecran26Activity.class);
                myIntent.putExtra("campuri",campuri+"");
                myIntent.putExtra("name_tip_inmatriculare",name_tip_inmatriculare);
                startActivity(myIntent);

            }
        });

        LinearLayout relbar = (LinearLayout) findViewById(R.id.rel_bar1);
        if(relbar!=null)
        relbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton btn1 = (RadioButton) findViewById(R.id.radioButton2);
                if (btn1.isChecked()) {
                    btn1.setChecked(false);
                } else {
                    btn1.setChecked(true);
                }

                //send GET
                prelucrareRaspuns();

                if (result_string!=null) {
                    makePostRequestOnNewThread1();
                    if(result_string1!=null){
                    //Context context = getApplicationContext();
                    //int duration = Toast.LENGTH_SHORT;
                    //Toast toast = Toast.makeText(context, result_string1, duration);
                    //toast.show();
                    prelucrareRaspuns1();
                    }
                }
            }
        });

    }


    private void makePostRequestOnNewThread() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                makePostRequest();
            }
        });
        t.start();
        if(t.getState()!=Thread.State.TERMINATED){thread1=true; }
    }
    private void makePostRequestOnNewThread1() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                makePostRequest1();
            }
        });
        t.start();
        if(t.getState()!=Thread.State.TERMINATED){thread2=true; }

    }

    private void makePostRequest() {
        JSONObject jsonobject_identificare = new JSONObject();
        JSONArray jsonobject_cerute = new JSONArray();
        JSONObject js = new JSONObject();

        try {
            JSONObject jsonobject_one = new JSONObject();
            JSONObject jsonobject_resursa = new JSONObject();
            JSONArray jsonobject_id = new JSONArray();

            jsonobject_one.put("app_code", "abcdefghijkl123456");
            jsonobject_id.put(tip_inmat);
            jsonobject_resursa.put("id", jsonobject_id);

            jsonobject_identificare.put("user", jsonobject_one);
            jsonobject_identificare.put("resursa", jsonobject_resursa);
            jsonobject_cerute.put("id");
            jsonobject_cerute.put("nume");
            jsonobject_cerute.put("id_tara");
            jsonobject_cerute.put("foto_background");
            jsonobject_cerute.put("url_imagine");
            jsonobject_cerute.put("ids_tipuri_inmatriculare_tipuri_elemente");

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
            String result = EntityUtils.toString(response.getEntity());
            result_string = result;



        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }


    }

    private void prelucrareRaspuns() {
        JSONArray jsonArray1 = new JSONArray();
        try {
            jsonArray1 = new JSONArray(result_string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1 = (jsonArray1.getJSONObject(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //String s = jsonObject1.getClass().getName();
        try {
            response_ids_inmatriculare = jsonObject1.getJSONArray("ids_tipuri_inmatriculare_tipuri_elemente");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            name_tip_inmatriculare = jsonObject1.getString("nume");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        x = response_ids_inmatriculare.optInt(0);

        /*Toast toast = Toast.makeText(this, name_tip_inmatriculare, Toast.LENGTH_LONG);
        toast.show();*/



    }
    private void prelucrareRaspuns1() {

        JSONArray jsonArray1 = new JSONArray();
        try {
            jsonArray1 = new JSONArray(result_string1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1 = (jsonArray1.getJSONObject(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        campuri = jsonArray1.length();



        //int x = response_ids_inmatriculare.optInt(0);
        //Context context = getApplicationContext();
        /*Toast toast = Toast.makeText(this, String.valueOf(campuri), Toast.LENGTH_LONG);
        toast.show();*/



    }
    private void makePostRequest1() {
        JSONObject jsonobject_identificare = new JSONObject();
        JSONArray jsonobject_cerute = new JSONArray();
        JSONObject js = new JSONObject();


        try {
            JSONObject jsonobject_one = new JSONObject();
            JSONObject jsonobject_resursa = new JSONObject();
            //JSONArray jsonobject_id = new JSONArray();

            jsonobject_one.put("app_code", "abcdefghijkl123456");
            //jsonobject_id.put(1);
            jsonobject_resursa.put("id", response_ids_inmatriculare);

            jsonobject_identificare.put("user", jsonobject_one);
            jsonobject_identificare.put("resursa", jsonobject_resursa);
            jsonobject_cerute.put("id");
            jsonobject_cerute.put("tip");
            jsonobject_cerute.put("maxlength");


            js.put("identificare", jsonobject_identificare);
            js.put("cerute", jsonobject_cerute);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        HttpClient httpClient = new DefaultHttpClient();
        HttpBodyGet httpPost = new HttpBodyGet(url1);
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
            String result = EntityUtils.toString(response.getEntity());
            result_string1 = result;




        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }


    }

}
