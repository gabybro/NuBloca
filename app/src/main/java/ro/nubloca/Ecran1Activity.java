package ro.nubloca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import ro.nubloca.Networking.RequestTara;
import ro.nubloca.Networking.StandElem;
import ro.nubloca.extras.Global;


public class Ecran1Activity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    boolean Tos;
    private ProgressBar progressBar;
    String countryCode;
    StandElem standElem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Tos = (sharedpreferences.getBoolean("TOS", false));

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        countryCode = tm.getSimCountryIso();

        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_IN);

        createHandler();


    }

    private void createHandler() {
        Thread thread = new Thread() {
            public void run() {
                Looper.prepare();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        // Do Work
                        Gson gson = new Gson();
                        String json = sharedpreferences.getString("STANDELEM", "");
                        standElem = gson.fromJson(json, StandElem.class);
                        if (json.equals("")) {
                            //if (standElem.getTipNumar().size() == 0) {
                                RequestTara make = new RequestTara();
                                standElem = make.makePostRequestOnNewThread(Ecran1Activity.this, countryCode);
                                ((Global) getApplicationContext()).setStandElem(standElem);

                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                json = gson.toJson(standElem); // standElem - instance of StandElem
                                editor.putString("STANDELEM", json);
                                editor.putString("TARA"+standElem.getCod(), json);
                                editor.apply();

                           // }
                        }
                        ((Global) getApplicationContext()).setStandElem(standElem);
                        handler.removeCallbacks(this);
                        Looper.myLooper().quit();
                        if (!Tos) {
                            finish();
                            startActivity(new Intent(Ecran1Activity.this, Ecran3Activity.class));
                        } else {
                            finish();
                            startActivity(new Intent(Ecran1Activity.this, Ecran7Activity.class));
                        }
                    }
                }, 1000);

                Looper.loop();
            }
        };
        thread.start();
    }
}
