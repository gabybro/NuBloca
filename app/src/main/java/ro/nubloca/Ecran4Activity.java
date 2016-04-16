package ro.nubloca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Ecran4Activity extends AppCompatActivity implements ScrollViewListener {

    private ObservableScrollView scrollView = null;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    public boolean tos_check = false;
    Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran4);

        scrollView = (ObservableScrollView) findViewById(R.id.scrollView1);
        scrollView.setScrollViewListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("                 Termeni si conditii");
        toolbar.setTitleTextColor(0xFFFFFFFF);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        b1 = (Button) findViewById(R.id.acord);
        b1.setOnClickListener(new View.OnClickListener() {
            //ecran 4, buton "De acord"
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                //verifica daca TOS-ul a fost citit
                if (tos_check) {
                    Toast.makeText(Ecran4Activity.this, "Thanks", Toast.LENGTH_LONG).show();
                    //TOS-ul a fost acceptat
                    editor.putBoolean("TOS", true);
                    editor.apply();
                    finish();
                    //merge la ecranul 7
                    startActivity(new Intent(Ecran4Activity.this, Ecran7Activity.class));
                } else {
                    //TOS-ul nu a fost citit
                    final RelativeLayout back_dim_layout = (RelativeLayout) findViewById(R.id.bac_dim_layout);
                    //background-ul negru
                    back_dim_layout.setVisibility(View.VISIBLE);
                    LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    //popup-ul de interogare ecran 4.2
                    View popupView = layoutInflater.inflate(R.layout.popup, null);
                    final PopupWindow popupWindow = new PopupWindow(
                            popupView,
                            Toolbar.LayoutParams.WRAP_CONTENT,
                            Toolbar.LayoutParams.WRAP_CONTENT, true);

                    Button btnDismiss = (Button) popupView.findViewById(R.id.button1);
                    btnDismiss.setOnClickListener(new Button.OnClickListener() {
                        //a fost apasat butonul "Nu" si popup-ul dispare
                        @Override
                        public void onClick(View v) {
                            back_dim_layout.setVisibility(View.GONE);
                            popupWindow.dismiss();
                        }
                    });


                    Button btnDa = (Button) popupView.findViewById(R.id.button2);
                    btnDa.setOnClickListener(new Button.OnClickListener() {
                        //a fost apasat butonul "Da"
                        @Override
                        public void onClick(View v) {

                            back_dim_layout.setVisibility(View.GONE);
                            popupWindow.dismiss();
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putBoolean("TOS", true);
                            editor.apply();
                            //se trece la ecranul 7
                            finish();
                            startActivity(new Intent(Ecran4Activity.this, Ecran7Activity.class));

                        }
                    });


                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                }
                //Toast.makeText(Ecran4Activity.this, "Thanks", Toast.LENGTH_LONG).show();


            }
        });

        b2 = (Button) findViewById(R.id.refuz);
        b2.setOnClickListener(new View.OnClickListener() {
            //a fost apasat butonul "Refuz" din ecranul 4.1 si se trece la ecranul 5
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean("TOS", false);
                editor.apply();
                finish();
                startActivity(new Intent(Ecran4Activity.this, Ecran5Activity.class));


            }
        });


    }

    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        //verifica citirea TOS-ului
        View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

        if (diff == 0) {
            tos_check = true;
            //Toast.makeText(Ecran4Activity.this, "Jos", Toast.LENGTH_LONG).show();

        }


    }


}