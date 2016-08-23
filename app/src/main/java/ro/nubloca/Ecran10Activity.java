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
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.Map;

import ro.nubloca.Networking.HttpBodyGet;

public class Ecran10Activity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    String acc_lang = "en";
    String cont_lang = "ro";
    public String url = "http://api.nubloca.ro/contact/";
    JSONArray jsonobject_Three = new JSONArray();
    JSONObject jsonobject_TWO = new JSONObject();
    JSONObject jsonobject_one_one = new JSONObject();
    JSONObject js = new JSONObject();
    Drawable upArrow = null;

    public String name, telefon, email, mesaj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran10);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        acc_lang = (sharedpreferences.getString("acc_lang", "en"));
        cont_lang = (sharedpreferences.getString("cont_lang", "ro"));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu3, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu1) {

            final EditText nameField = (EditText) findViewById(R.id.editText3);
            final EditText telefonField = (EditText) findViewById(R.id.editText4);
            final EditText emailField = (EditText) findViewById(R.id.editText5);
            final EditText mesajField = (EditText) findViewById(R.id.editText6);

            name = nameField.getText().toString();
            telefon = telefonField.getText().toString();
            email = emailField.getText().toString();
            mesaj = mesajField.getText().toString();

            Toast toast = Toast.makeText(Ecran10Activity.this, "Thank You " + name + "!", Toast.LENGTH_SHORT);
            toast.show();
            prepJsonSend();
            makePostRequestOnNewThread();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void prepJsonSend() {

        try {
            JSONObject jsonobject_one = new JSONObject();
            jsonobject_one.put("app_code", "abcdefghijkl123456");
            jsonobject_one_one.put("user", jsonobject_one);
            jsonobject_TWO.put("nume", name);
            jsonobject_TWO.put("id_email", 15);
            jsonobject_TWO.put("id_numar_telefon", 14);
            jsonobject_TWO.put("mesaj", mesaj);
            jsonobject_Three.put("id");
            jsonobject_Three.put("data");
            jsonobject_Three.put("status");
            js.put("identificare", jsonobject_one_one);
            js.put("cerute", jsonobject_Three);
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
        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }
    }
}
