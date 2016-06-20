package ro.nubloca;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

import ro.nubloca.Networking.GetRequest;
import ro.nubloca.Networking.GetRequestImg;
import ro.nubloca.Networking.RequestTara;
import ro.nubloca.Networking.Response;
import ro.nubloca.Networking.StandElem;
import ro.nubloca.extras.Global;

public class Ecran25Activity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    int id_tara = 147;
    JSONArray jsonArray = new JSONArray();
    String result_string;
    String url = "http://api.nubloca.ro/tari/";
    private ProgressDialog m_ProgressDialog = null;
    private ArrayList<Response> m_orders = null;
    private ResponseAdapter m_adapter;
    private Runnable viewOrders;
    String countryCode;
    StandElem standElem;
    int index;
    byte[] baite;
    int dim = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran25);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
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

        m_orders = new ArrayList<Response>();
        this.m_adapter = new ResponseAdapter(this, R.layout.raw_list1, m_orders);

        ListView lv = (ListView) findViewById(R.id.list1);
        lv.setAdapter(this.m_adapter);


        viewOrders = new Runnable() {
            @Override
            public void run() {

                getOrders();
            }
        };
        Thread thread = new Thread(null, viewOrders, "MagentoBackground");
        thread.start();

        m_ProgressDialog = ProgressDialog.show(Ecran25Activity.this,
                "Please wait...", "Retrieving data ...", true);


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

    private Runnable returnRes = new Runnable() {

        @Override
        public void run() {

            Gson gson = new Gson();
            Type listeType = new TypeToken<List<Response>>() {
            }.getType();
            List<Response> response = (List<Response>) gson.fromJson(result_string, listeType);

            m_orders = new ArrayList<Response>();

            for (int i = 0; i < response.size(); i++) {
                m_orders.add(response.get(i));
            }

            if (m_orders != null && m_orders.size() > 0) {
                m_adapter.notifyDataSetChanged();
                for (int i = 0; i < m_orders.size(); i++)
                    m_adapter.add(m_orders.get(i));
            }
            m_ProgressDialog.dismiss();
            m_adapter.notifyDataSetChanged();
        }
    };

    private void getOrders() {

        runOnUiThread(returnRes);
    }


    private class ResponseAdapter extends ArrayAdapter<Response> {

        private ArrayList<Response> items;

        public ResponseAdapter(Context context, int textViewResourceId, ArrayList<Response> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.raw_list1, null);
            }
            final Response o = items.get(position);
            ImageView iiv = (ImageView) v.findViewById(R.id.radioButton1);
            RelativeLayout rel = (RelativeLayout) v.findViewById(R.id.rel_bar1);
            if (o != null) {
                if ((standElem.getId() == o.getId()) && (iiv != null)) {

                    iiv.setImageResource(R.drawable.radio_press);
                } else {
                    iiv.setImageResource(R.drawable.radio);
                }

                if (rel != null)
                    rel.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (standElem.getId() != o.getId()) {
                                ((Global) getApplicationContext()).setId_tara(o.getId());
                                ((Global) getApplicationContext()).setNume_tip_inmatriculare("standard");
                                ((Global) getApplicationContext()).setNume_tip_inmatriculare_id(0);
                                ((Global) getApplicationContext()).setCountry_select(o.getNume());
                                ((Global) getApplicationContext()).setPositionExemplu(-1);
                                countryCode = o.getCod();
                                RequestTara make = new RequestTara();
                                standElem = make.makePostRequestOnNewThread(Ecran25Activity.this, countryCode);
                                ((Global) getApplicationContext()).setStandElem(standElem);

                                Gson gson = new Gson();
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                String json = gson.toJson(standElem);
                                editor.putString("STANDELEM", json);
                                editor.apply();

                                startActivity(new Intent(Ecran25Activity.this, Ecran23Activity.class));
                                finish();
                            }
                        }
                    });
                TextView tt = (TextView) v.findViewById(R.id.text1);
                if (tt != null) {
                    tt.setText(o.getNume());
                }
            }
            return v;
        }
    }

}
