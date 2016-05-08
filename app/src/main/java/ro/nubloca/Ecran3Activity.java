package ro.nubloca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import ro.nubloca.extras.ObservableScrollView;
import ro.nubloca.extras.ScrollViewListener;

public class Ecran3Activity extends AppCompatActivity implements ScrollViewListener {

    private ObservableScrollView scrollView = null;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    public boolean tos_check = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran3);

        scrollView = (ObservableScrollView) findViewById(R.id.scrollView1);
        scrollView.setScrollViewListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        /*getSupportActionBar().setTitle("                 Termeni si conditii");
        toolbar.setTitleTextColor(0xFFFFFFFF);*/

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        /*TextView textCustomFont = (TextView) findViewById(R.id.editText2);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/TitilliumText22L003.otf");
        textCustomFont.setTypeface(myCustomFont);*/

       /* TextView textCustomFont1 = (TextView) findViewById(R.id.refuz);
        Typeface myCustomFont1 = Typeface.createFromAsset(getAssets(), "fonts/TitilliumText22L005.otf");
        textCustomFont1.setTypeface(myCustomFont1);

        TextView textCustomFont2 = (TextView) findViewById(R.id.acord);
        Typeface myCustomFont2 = Typeface.createFromAsset(getAssets(), "fonts/TitilliumText22L005.otf");
        textCustomFont2.setTypeface(myCustomFont2);*/



        View b1 = (View) findViewById(R.id.acord);
        b1.setOnClickListener(new View.OnClickListener() {
            //ecran 4, buton "De acord"
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                //verifica daca TOS-ul a fost citit
                if (tos_check) {
                    Toast.makeText(Ecran3Activity.this, "Thanks", Toast.LENGTH_LONG).show();
                    //TOS-ul a fost acceptat
                    editor.putBoolean("TOS", true);
                    editor.apply();
                    finish();
                    //merge la ecranul 7
                    startActivity(new Intent(Ecran3Activity.this, Ecran7Activity.class));
                } else {
                    //TOS-ul nu a fost citit
                    final RelativeLayout back_dim_layout = (RelativeLayout) findViewById(R.id.bac_dim_layout);
                    //background-ul negru
                    //******back_dim_layout.setVisibility(View.VISIBLE);
                    //******LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    //popup-ul de interogare ecran 4.2
                    //******View popupView = layoutInflater.inflate(R.layout.popup, null);
                    //******final PopupWindow popupWindow = new PopupWindow(
                    //******      popupView,
                    //******    Toolbar.LayoutParams.WRAP_CONTENT,
                    //******  Toolbar.LayoutParams.WRAP_CONTENT, true);
                    openAlert(v);

                }
                //Toast.makeText(Ecran3Activity.this, "Thanks", Toast.LENGTH_LONG).show();


            }
        });

       View b2 = (View) findViewById(R.id.refuz);
        b2.setOnClickListener(new View.OnClickListener() {
            //a fost apasat butonul "Refuz" din ecranul 4.1 si se trece la ecranul 5
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean("TOS", false);
                editor.apply();
                finish();
                startActivity(new Intent(Ecran3Activity.this, Ecran4Activity.class));


            }
        });


    }

    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        //verifica citirea TOS-ului
        View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

        if (diff == 0) {
            tos_check = true;
            //Toast.makeText(Ecran3Activity.this, "Jos", Toast.LENGTH_LONG).show();

        }


    }

    private void openAlert(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Ecran3Activity.this);

        LayoutInflater inflater = this.getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.popup, null);


        /*TextView textCustomFont3 = (TextView) dialogView.findViewById(R.id.popup_text);
        Typeface myCustomFont3 = Typeface.createFromAsset(getAssets(), "fonts/TitilliumText22L003.otf");
        textCustomFont3.setTypeface(myCustomFont3);*/

        /*TextView textCustomFont4 = (TextView) dialogView.findViewById(R.id.button1);
        Typeface myCustomFont4 = Typeface.createFromAsset(getAssets(), "fonts/TitilliumText22L005.otf");
        textCustomFont4.setTypeface(myCustomFont4);

        TextView textCustomFont5 = (TextView) dialogView.findViewById(R.id.button2);
        Typeface myCustomFont5 = Typeface.createFromAsset(getAssets(), "fonts/TitilliumText22L005.otf");
        textCustomFont5.setTypeface(myCustomFont5);*/

        View btnDa = (View) dialogView.findViewById(R.id.button2);
        View btnDismiss = (View) dialogView.findViewById(R.id.button1);


        alertDialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = alertDialogBuilder.create();

        // set positive button: Yes message
        btnDa.setOnClickListener(new Button.OnClickListener() {
            //a fost apasat butonul "Da"
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean("TOS", true);
                editor.apply();
                //se trece la ecranul 7
                finish();
                startActivity(new Intent(Ecran3Activity.this, Ecran7Activity.class));

            }
        });
        // set negative button: No message

        btnDismiss.setOnClickListener(new Button.OnClickListener() {
            //a fost apasat butonul "Da"
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        // show alert
        alertDialog.show();
    }
}