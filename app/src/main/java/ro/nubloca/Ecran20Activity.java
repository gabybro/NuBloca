package ro.nubloca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class Ecran20Activity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    int campuri=3;
    int id_tara=147;
    String acc_lang, cont_lang;
    int[] array;
    String result;
    String url="http://api.nubloca.ro/tipuri_inmatriculare/";
    String name_tip_inmatriculare;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran20);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        acc_lang = (sharedpreferences.getString("acc_lang", "en"));
        cont_lang = (sharedpreferences.getString("cont_lang", "ro"));
        name_tip_inmatriculare = (sharedpreferences.getString("nume_tip_inmatriculare", "-"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        //Intent myIntent = getIntent();
        //nume_tip_inmatriculare = myIntent.getStringExtra("nume_tip_inmatriculare");
        //Bundle extras = getIntent().getExtras();
        //array = extras.getIntArray("array");

        TextView tip_inmatriculare_nume = (TextView)findViewById(R.id.nume_tip_inmatriculare);
        tip_inmatriculare_nume.setText(name_tip_inmatriculare);

        //View plate3 = (View)findViewById(R.id.plate3);
        //if(campuri==2) {
        //   plate3.setVisibility(View.GONE);
        //}

        View btn3 = (View) this.findViewById(R.id.textView24);
        if(btn3!=null)
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ecran20Activity.this, Ecran24Activity.class));

            }
        });

        View btn5 = (View) this.findViewById(R.id.textView23);
        if (btn5!=null)
        btn5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ecran20Activity.this, Ecran13Activity.class));

            }
        });

        View btn2 = (View) this.findViewById(R.id.relativ2);
        if(btn2!=null)
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //makePostRequestOnNewThread();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt("positionExemplu", -1);
                editor.commit();
                startActivity(new Intent(Ecran20Activity.this, Ecran23Activity.class));
                //finish();

            }
        });


        LinearLayout btn1 = (LinearLayout) this.findViewById(R.id.lin_bar1);
        if(btn1!=null)
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ecran20Activity.this, Ecran22Activity.class));
            }
        });


    }
  /*  private void makePostRequestOnNewThread() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                makePostRequest();
                //handler.sendEmptyMessage(0);
                Intent myIntent = new Intent(Ecran20Activity.this, Ecran23Activity.class);
                myIntent.putExtra("array", result);
                startActivity(myIntent);

            }
        });
        t.start();
    }*/

    /*private void makePostRequest() {
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
            //prelucrareRaspuns();

        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }


    }*/

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu5, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu1) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}