package ro.nubloca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

public class Ecran1Activity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    boolean Tos;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Tos = (sharedpreferences.getBoolean("TOS", false));
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);

                        }
                    });
                    try {
                        // Sleep for 20 milliseconds.
                        //Just to display the progress slowly
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //verifica TOS-ul
                if (!Tos) {
                    finish();
                    //merge la ecranul 4 (citirea TOS-ului)
                    startActivity(new Intent(Ecran1Activity.this, Ecran4Activity.class));
                } else {
                    finish();
                    //merge la ecranul 7(ecranul principal)
                    startActivity(new Intent(Ecran1Activity.this, Ecran7Activity.class));
                }
            }
        }).start();


    }


}
