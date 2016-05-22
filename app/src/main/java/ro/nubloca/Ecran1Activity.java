package ro.nubloca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import org.json.JSONArray;

import ro.nubloca.Networking.GetTipNumar;

public class Ecran1Activity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    boolean Tos;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    int id_tara;
    String url = "http://api.nubloca.ro/tipuri_inmatriculare/";
    String acc_lang, cont_lang, result;
    JSONArray response_ids_inmatriculare;
    String name_tip_inmatriculare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        id_tara = (sharedpreferences.getInt("id_tara", 147));
        Tos = (sharedpreferences.getBoolean("TOS", false));
        acc_lang = (sharedpreferences.getString("acc_lang", "en"));
        cont_lang = (sharedpreferences.getString("cont_lang", "ro"));


        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_IN);


        makePostRequestOnNewThread();



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 3s = 3000ms
                if (!Tos) {
                    finish();
                    //merge la ecranul 4 (citirea TOS-ului)
                    startActivity(new Intent(Ecran1Activity.this, Ecran3Activity.class));
                } else {
                    finish();
                    //merge la ecranul 7(ecranul principal)
                    startActivity(new Intent(Ecran1Activity.this, Ecran7Activity.class));
                }
            }
        }, 3000);


    }



    private void makePostRequestOnNewThread() {
        final GetTipNumar nr = new GetTipNumar();
        nr.setParam(id_tara, acc_lang, cont_lang);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //handler.sendEmptyMessage(0);
                String name_tip_inmatriculare_phone = (sharedpreferences.getString("nume_tip_inmatriculare", "default"));
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("array", nr.getRaspuns());

                if (name_tip_inmatriculare_phone.equals("default")) {
                    editor.putString("nume_tip_inmatriculare", nr.getNumeUsed());
                    editor.putInt("nume_tip_inmatriculare_id", nr.getIdNumeUsed());
                }
                editor.putString("ids_tipuri_inmatriculare_tipuri_elemente", nr.getIdsUsed());
                editor.apply();
            }
        });
        t.start();
    }
}
