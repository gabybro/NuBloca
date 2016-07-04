package ro.nubloca;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import ro.nubloca.extras.Global;

public class Ecran28Activity extends AppCompatActivity {
    Drawable upArrow = null;
    public String url = "http://api.nubloca.ro/propuneri_mesaje/";
    JSONArray jsonobject_Three = new JSONArray();
    JSONObject jsonobject_TWO = new JSONObject();
    JSONObject jsonobject_one_one = new JSONObject();
    JSONObject js = new JSONObject();
    String mesajText;
    String acc_lang = "en";
    String cont_lang = "ro";
    int resp=0;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran28);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        final EditText mesajEdit = (EditText) findViewById(R.id.editText);


        LinearLayout lin = (LinearLayout) findViewById(R.id.trimite);
        if (lin != null)
            lin.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    mesajText = mesajEdit.getText().toString();

                    prepJsonSend();
                    makePostRequestOnNewThread();


                    Toast toast = Toast.makeText(Ecran28Activity.this, result, Toast.LENGTH_LONG);
                    toast.show();
                }
            });


    }



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

    private void prepJsonSend() {

        try {
            JSONObject jsonobject_one = new JSONObject();
            jsonobject_one.put("app_code", "abcdefghijkl123456");
            jsonobject_one_one.put("user", jsonobject_one);

            jsonobject_TWO.put("id_instalare", 12);
            jsonobject_TWO.put("pentru_id_tara", 147);
            jsonobject_TWO.put("mesaj", mesajText);



            js.put("identificare", jsonobject_one_one);

            js.put("trimise", jsonobject_TWO);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void makePostRequestOnNewThread() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                makePostRequest();
                //handler.sendEmptyMessage(0);

            }
        });
        t.start();
    }

    private void makePostRequest() {

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
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


    }


}

