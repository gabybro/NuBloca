package ro.nubloca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import ro.nubloca.Networking.GetRequest;
import ro.nubloca.extras.CustomFontTitilliumBold;

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
        upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.abc_ic_ab_back_mtrl_am_alpha, null);
        upArrow.setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);



        final EditText mesajEdit = (EditText) findViewById(R.id.editText);
        mesajEdit.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mesajEdit.setSingleLine(true);

        TextView textview = (TextView) findViewById(R.id.text);
        textview.setTypeface(textview.getTypeface(), Typeface.ITALIC);


        LinearLayout lin = (LinearLayout) findViewById(R.id.trimite);
        if (lin != null)
            lin.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    mesajText = mesajEdit.getText().toString();
                    if (!mesajText.equals("")){
                        makeRequest();
                        if(result==null){
                            //dialogBox

                            openAlert(v);
                        }else{
                            mesajEdit.setText("");
                            Toast toast = Toast.makeText(Ecran28Activity.this, R.string.toast_msg, Toast.LENGTH_LONG);
                            toast.show();
                            View view = getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                finish();
                                startActivity(new Intent(Ecran28Activity.this, Ecran282Activity.class));
                            }
                        }

                    }
                }
            });
        mesajEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                   // Log.i(TAG,"Enter pressed");
                    mesajText = mesajEdit.getText().toString();
                    if (!mesajText.equals("")) {
                        makeRequest();
                        if (result == null) {
                            //dialogBox
                            openAlert(v);
                        } else {
                            mesajEdit.setText("");
                            Toast toast = Toast.makeText(Ecran28Activity.this, R.string.toast_msg, Toast.LENGTH_LONG);
                            toast.show();
                            finish();
                            startActivity(new Intent(Ecran28Activity.this, Ecran282Activity.class));
                        }
                    }
                }
                return false;
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
                result = elem.getRaspuns(Ecran28Activity.this, url, resursa, cerute, mesajText);
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void openAlert(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Ecran28Activity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup28, null);
        CustomFontTitilliumBold okBtn = (CustomFontTitilliumBold) dialogView.findViewById(R.id.button);
        alertDialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = alertDialogBuilder.create();

        okBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();

            }
        });


        alertDialog.show();
    }
}

