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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import java.lang.reflect.Type;
import java.util.List;

import ro.nubloca.Networking.GetRequest;
import ro.nubloca.Networking.Response;
import ro.nubloca.extras.Global;

public class Ecran28Activity extends AppCompatActivity {
    Drawable upArrow = null;
    String mesajText;
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
                    makeRequest();
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


    private void makeRequest() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                 /*{
            "identificare": {            "user": {                "admin": "abcdefghijkl123456"            }        },
            "trimise": {            "id_instalare": 12,                    "pentru_id_tara": 147,                    "mesaj": "Mesaj"        }
        }*/
                String url = "http://api.nubloca.ro/propuneri_mesaje/";
                GetRequest elem = new GetRequest();
                JSONArray cerute = new JSONArray();
                JSONObject resursa = new JSONObject();
                result = elem.getRaspuns(Ecran28Activity.this, url, resursa, cerute, "POST");
            }
        });
        t.start();
    }

}

