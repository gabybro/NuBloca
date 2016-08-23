package ro.nubloca;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ro.nubloca.Networking.CustomAdapter;
import ro.nubloca.Networking.CustomAdapterListaTari;
import ro.nubloca.Networking.GetRequest;
import ro.nubloca.Networking.GetRequestImg;
import ro.nubloca.Networking.RequestTara;
import ro.nubloca.Networking.Response;
import ro.nubloca.Networking.StandElem;
import ro.nubloca.extras.CustomFontTitilliumRegular;
import ro.nubloca.extras.Global;

public class Ecran25Activity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    int id_tara = 147;
    JSONArray jsonArray = new JSONArray();
    String result_string;
    String url = "http://api.nubloca.ro/tari/";
    private ProgressDialog m_ProgressDialog = null;
    ProgressDialog progress;
    private ArrayList<Response> m_orders = null;
    private Runnable viewOrders;
    String countryCode;
    StandElem standElem;
    int index;
    byte[] baite;
    int dim = 30;
    int[] ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran25);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        progress = new ProgressDialog(this, R.style.ProgressDialog);
        progress.setCancelable(false);
        progress.setProgressStyle(android.R.style.Widget_ProgressBar_Large);

        //sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //id_tara = ((Global) this.getApplication()).getId_tara();
        standElem = ((Global) getApplicationContext()).getStandElem();


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    makeRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Type listeType = new TypeToken<List<Response>>() {
        }.getType();
        List<Response> response = (List<Response>) gson.fromJson(result_string, listeType);

        String[] values = new String[response.size()];
        ids = new int[response.size()];
        String[] code = new String[response.size()];
        for (int i = 0; i < response.size(); i++) {
            values[i] = response.get(i).getNume();
            ids[i] = response.get(i).getId();
            code[i] = response.get(i).getCod();
        }


        ListAdapter customAdapter = new CustomAdapterTari(this, values, code);
        ListView lv = (ListView) findViewById(R.id.list1);
        lv.setAdapter(customAdapter);


    }

    private void makeRequest() throws JSONException {
        //url = "http://api.nubloca.ro/tari/";
        /*{"identificare": {"user": {"app_code": "abcdefghijkl123456"},
            "resursa": {"status": ["ACTIV"]}},
            "cerute": ["id","nume","ids_tipuri_inmatriculare","cod"]}*/


        GetRequest elem = new GetRequest();
        JSONArray cerute = new JSONArray().put("id").put("nume").put("ids_tipuri_inmatriculare").put("cod");
        JSONArray jsonarray = new JSONArray().put("ACTIV");
        JSONObject resursa = new JSONObject().put("status", jsonarray);
        result_string = elem.getRaspuns(Ecran25Activity.this, url, resursa, cerute);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }


    public class CustomAdapterTari extends ArrayAdapter<String> {

        String[] code;
        String[] tara;


        public CustomAdapterTari(Context context, String[] elemente, String[] countryCode) {
            super(context, R.layout.raw_list1, elemente);

            code = countryCode;
            tara = elemente;


        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            LayoutInflater dapter = LayoutInflater.from(getContext());
            View customView = dapter.inflate(R.layout.raw_list1, parent, false);

            String singleElem = getItem(position);
            CustomFontTitilliumRegular textul = (CustomFontTitilliumRegular) customView.findViewById(R.id.text1);
            final ImageView imaginea = (ImageView) customView.findViewById(R.id.radioButton1);

            textul.setText(singleElem);

            LinearLayout rel = (LinearLayout) customView.findViewById(R.id.linear);
            if (standElem.getId() == ids[position]) {
                imaginea.setImageResource(R.drawable.radio_press);
            } else {
                imaginea.setImageResource(R.drawable.radio);
            }


            if (rel != null) {
                rel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //createHandler(position);


                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedpreferences.edit();


                        //if (sharedpreferences.getString("TARA" + ids[position], "").equals("")) {
                        if (sharedpreferences.getString("TARA" + code[position], "").equals("")) {
                            /*progress = ProgressDialog.show(Ecran25Activity.this, "dialog title",
                                    "dialog message", true);*/
                            progress.show();
                            createHandler(position);
                        } else {

                            //String json1 = sharedpreferences.getString("TARA" + ids[position], "");
                            String json1 = sharedpreferences.getString("TARA" + code[position], "");
                            standElem = gson.fromJson(json1, StandElem.class);
                            editor.putString("STANDELEM", json1);
                            editor.commit();
                            standElem.setPositionExemplu(-2);
                            ((Global) getApplicationContext()).setStandElem(standElem);
                            finish();
                            startActivity(new Intent(Ecran25Activity.this, Ecran23Activity.class));


                        }


                    }
                });
            }


            return customView;
        }

        private void createHandler(int pos) {
            final int position = pos;
            Thread thread = new Thread() {
                public void run() {
                    Looper.prepare();

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Gson gson = new Gson();
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            RequestTara make = new RequestTara();
                            standElem = make.makePostRequestOnNewThread(Ecran25Activity.this, code[position]);
                            String json = gson.toJson(standElem);
//                            editor.putString("TARA" + standElem.getId(), json);
                            editor.putString("TARA" + standElem.getCod(), json);
                            editor.putString("STANDELEM", json);
                            editor.commit();
                            handler.removeCallbacks(this);
                            Looper.myLooper().quit();
                            progress.dismiss();
                            ((Global) getApplicationContext()).setStandElem(standElem);
                            standElem.setPositionExemplu(-2);
                            startActivity(new Intent(Ecran25Activity.this, Ecran23Activity.class));
                            finish();
                        }
                    }, 1000);

                    Looper.loop();
                }
            };
            thread.start();
        }


    }
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        Intent myIntent = new Intent(getApplicationContext(), Ecran23Activity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }*/
}
