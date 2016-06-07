package ro.nubloca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import ro.nubloca.Networking.GetRequest;
import ro.nubloca.Networking.Response;
import ro.nubloca.extras.Global;

public class Ecran1Activity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    boolean Tos;
    private ProgressBar progressBar;
    //private int progressStatus = 0;
    //private Handler handler = new Handler();
    int id_tara;
    String url = "http://api.nubloca.ro/tari/";
    String countryCode;
    String rez;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        id_tara = (sharedpreferences.getInt("id_tara", 147));
        Tos = (sharedpreferences.getBoolean("TOS", false));

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        countryCode = tm.getSimCountryIso();

        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_IN);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!Tos) {
                    finish();
                    startActivity(new Intent(Ecran1Activity.this, Ecran3Activity.class));
                } else {
                    finish();
                    startActivity(new Intent(Ecran1Activity.this, Ecran7Activity.class));
                }
            }
        }, 3000);

        makePostRequestOnNewThread();
    }

    private void makePostRequestOnNewThread() {


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    makePostRequest();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //handler.sendEmptyMessage(0);
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Toast toast= Toast.makeText(this, id_tara+"", Toast.LENGTH_LONG);
        //toast.show();
    }


    private void makePostRequest() throws JSONException {

        GetRequest elemm = new GetRequest();

        JSONArray cerute = new JSONArray().put("id").put("url_steag");
        JSONArray cod = new JSONArray().put(countryCode);
        JSONObject resursa = new JSONObject().put("cod", cod);

        String result_string = elemm.getRaspuns(Ecran1Activity.this, url, resursa, cerute);

        Gson gson = new Gson();
        Type listeType = new TypeToken<List<Response>>() {
        }.getType();
        List<Response> response = (List<Response>) gson.fromJson(result_string, listeType);
        id_tara = response.get(0).getId();
        //id_tara=147;
        //SharedPreferences.Editor editor = sharedpreferences.edit();
        //editor.putInt("id_tara", id_tara);
        //editor.putInt("id_tara", 147);
        //editor.commit();
        ((Global) getApplicationContext()).setId_tara(id_tara);
    }
}
