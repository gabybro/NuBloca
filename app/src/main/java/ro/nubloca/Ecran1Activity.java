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

import ro.nubloca.Networking.RequestTara;
import ro.nubloca.extras.Global;


public class Ecran1Activity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    boolean Tos;
    private ProgressBar progressBar;
    int id_tara;
    String countryCode;

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


        RequestTara make = new RequestTara();
        ((Global) getApplicationContext()).setStandElem(make.makePostRequestOnNewThread(Ecran1Activity.this, countryCode));



    }

}
