package ro.nubloca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import java.util.Collection;

import ro.nubloca.Networking.HttpBodyGet;
import ro.nubloca.extras.CustomFontTitilliumBold;

public class Ecran26Activity extends AppCompatActivity {
    int camp = 3;
    String acc_lang, cont_lang;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    int[] array;
    String url = "http://api.nubloca.ro/tipuri_inmatriculare_tipuri_elemente/";
    String result_string;
    int campuri=3;
    String nume_tip_inmatriculare;

    String[] id_x, id_tip_element_x, valoare_demo_imagine_x, ordinea_x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran26);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        acc_lang = (sharedpreferences.getString("acc_lang", "en"));
        cont_lang = (sharedpreferences.getString("cont_lang", "ro"));
        Intent myIntent = getIntent();
        /*
        String campuri = myIntent.getStringExtra("campuri");
        String name_tip_inmatriculare = myIntent.getStringExtra("name_tip_inmatriculare");
        String maxlength1 = myIntent.getStringExtra("Campul unu");
        String maxlength2 = myIntent.getStringExtra("Campul doi");
        String maxlength3 = myIntent.getStringExtra("Campul trei");*/

        nume_tip_inmatriculare = myIntent.getStringExtra("nume_tip_inmatriculare");
        Bundle extras = getIntent().getExtras();
        array = extras.getIntArray("array");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // camp = Integer.parseInt(campuri);
        //Toast toast = Toast.makeText(this, Integer.toString(arrayB[0]), Toast.LENGTH_LONG);
        //toast.show();
        //TextView tip_inmatriculare_nume = (TextView)findViewById(R.id.nume_tip_inmatriculare);
        //tip_inmatriculare_nume.setText(name_tip_inmatriculare);

        makePostRequestOnNewThread();

        /*Toast toast = Toast.makeText(this, result_string, Toast.LENGTH_LONG);
        toast.show();*/
    }

    private void makePostRequestOnNewThread() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                makePostRequest();

                handler.sendEmptyMessage(0);
            }
        });
        t.start();
    }

    private void makePostRequest() {
        JSONObject jsonobject_identificare = new JSONObject();
        JSONArray jsonobject_cerute = new JSONArray();
        JSONObject js = new JSONObject();

        try {
            JSONObject jsonobject_one = new JSONObject();
            JSONObject jsonobject_resursa = new JSONObject();
            JSONArray jsonobject_id = new JSONArray();

            //TODO get app_code from phone
            jsonobject_one.put("app_code", "abcdefghijkl123456");
            //TODO replace tip_inmapt with onClick id
            //jsonobject_id.put(array.toString());

            for (int k = 0; k < array.length; k++) {
                jsonobject_id.put(k, array[k]);
            }
            jsonobject_resursa.put("id", jsonobject_id);
            jsonobject_identificare.put("user", jsonobject_one);
            jsonobject_identificare.put("resursa", jsonobject_resursa);
            jsonobject_cerute.put("id");
            jsonobject_cerute.put("id_tip_element");
            jsonobject_cerute.put("valoare_demo_imagine");
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
            String result = EntityUtils.toString(response.getEntity());
            result_string = result;
            prelucrareRaspuns();

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
        campuri = jsonArray1.length();
        String[] id= new String [campuri];
        String[] id_tip_element= new String [campuri];
        String[] valoare_demo_imagine= new String [campuri];
        String[] ordinea= new String [campuri];


        for(int i=0; i<campuri; i++)
        {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = (jsonArray1.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //String s = jsonObject1.getClass().getName();
            try {
                id[i] = jsonObject.getString("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                id_tip_element[i] = jsonObject.getString("id_tip_element");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                valoare_demo_imagine[i] = jsonObject.getString("valoare_demo_imagine");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                ordinea[i] = jsonObject.getString("ordinea");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        id_x=id;
        id_tip_element_x=id_tip_element;
        valoare_demo_imagine_x=valoare_demo_imagine;
        ordinea_x=ordinea;

    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

                /*Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, result_string, duration);
                toast.show();*/
            generateField();
        }
    };

    private void generateField() {

            TextView tip_inmatriculare_nume = (TextView)findViewById(R.id.nume_tip_inmatriculare);
            tip_inmatriculare_nume.setVisibility(View.VISIBLE);
            tip_inmatriculare_nume.setText(nume_tip_inmatriculare);
            CustomFontTitilliumBold field1 = (CustomFontTitilliumBold)findViewById(R.id.field1);
            field1.setVisibility(View.VISIBLE);
            field1.setText(valoare_demo_imagine_x[0].toString());
            CustomFontTitilliumBold field2 = (CustomFontTitilliumBold)findViewById(R.id.field2);
            field2.setVisibility(View.VISIBLE);
            field2.setText(valoare_demo_imagine_x[1].toString());
        if (campuri == 3) {
            CustomFontTitilliumBold field3 = (CustomFontTitilliumBold) findViewById(R.id.field3);
            field3.setVisibility(View.VISIBLE);
            field3.setText(valoare_demo_imagine_x[2].toString());
        }

    }

}
