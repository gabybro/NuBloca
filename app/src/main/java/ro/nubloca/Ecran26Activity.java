package ro.nubloca;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import ro.nubloca.Networking.GetRequest;
import ro.nubloca.Networking.Response26;
import ro.nubloca.extras.CustomFontTitilliumBold;

public class Ecran26Activity extends AppCompatActivity {


    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;

    String url = "http://api.nubloca.ro/tipuri_inmatriculare_tipuri_elemente/";
    String result_string;
    int campuri = 3;
    String nume_tip_inmatriculare;
    String get_order_ids_tip;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran26);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        get_order_ids_tip = (sharedpreferences.getString("getOrderIdsTip", null));


        Intent myIntent = getIntent();
        nume_tip_inmatriculare = myIntent.getStringExtra("nume_tip_inmatriculare");



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        makePostRequestOnNewThread();

        /*Toast toast = Toast.makeText(this, result_string, Toast.LENGTH_LONG);
        toast.show();*/
    }

    private void makePostRequestOnNewThread() {

        pd = ProgressDialog.show(this, "..", "getting data", true,
                false);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                makePostRequest();

                handler.sendEmptyMessage(0);
            }
        });
        t.start();
    }

    private void makePostRequest() {


        GetRequest elemm = new GetRequest();

        JSONObject resursa = new JSONObject();

        JSONArray jsonArray=null;
        try {
            jsonArray = new JSONArray(get_order_ids_tip);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {

            resursa.put("id", jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray cerute = new JSONArray();

        cerute.put("id");
        cerute.put("id_tip_element");
        cerute.put("valoare_demo_imagine");
        cerute.put("ordinea");

        result_string = elemm.getRaspuns(Ecran26Activity.this, url, resursa, cerute);


    }



    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            pd.dismiss();

            Gson gson = new Gson();
            Type listeType = new TypeToken<List<Response26>>(){}.getType();
            List<Response26> response26s = (List<Response26>) gson.fromJson(result_string, listeType);
            campuri= response26s.size();

            TextView tip_inmatriculare_nume = (TextView) findViewById(R.id.nume_tip_inmatriculare);
            tip_inmatriculare_nume.setVisibility(View.VISIBLE);
            tip_inmatriculare_nume.setText(nume_tip_inmatriculare);
            CustomFontTitilliumBold field1 = (CustomFontTitilliumBold) findViewById(R.id.field1);
            field1.setVisibility(View.VISIBLE);
            field1.setText(response26s.get(0).getValoare_demo_imagine());
            CustomFontTitilliumBold field2 = (CustomFontTitilliumBold) findViewById(R.id.field2);
            field2.setVisibility(View.VISIBLE);
            field2.setText(response26s.get(1).getValoare_demo_imagine());
            if (campuri == 3) {
                CustomFontTitilliumBold field3 = (CustomFontTitilliumBold) findViewById(R.id.field3);
                field3.setVisibility(View.VISIBLE);
                field3.setText(response26s.get(2).getValoare_demo_imagine());
            }
        }

    };


    @Override
    public void onBackPressed() {
        // code here to show dialog
        Intent myIntent = new Intent(Ecran26Activity.this, Ecran23Activity.class);
        startActivity(myIntent);
        finish();
        super.onBackPressed();  // optional depending on your needs
    }
}
