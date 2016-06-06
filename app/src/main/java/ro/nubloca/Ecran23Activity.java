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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ro.nubloca.Networking.GetElemNr;
import ro.nubloca.Networking.GetRequest;
import ro.nubloca.Networking.GetTipNumar;
import ro.nubloca.Networking.Order;
import ro.nubloca.Networking.Response;
import ro.nubloca.extras.GlobalVar;


public class Ecran23Activity extends AppCompatActivity {

    int id_tara = 147;
    int id_shared=1;
    int  nume_tip_inmatriculare_id;
    String nume_tip_inmatriculare;
    String result_tari;
    int positionExemplu = -1;
    String country_select;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    String acc_lang, cont_lang;
    private ProgressDialog m_ProgressDialog = null;
    private ArrayList<Order> m_orders = null;
    private OrderAdapter m_adapter;
    private Runnable viewOrders;
    JSONArray jsonArray = null;
    int[] Ids_tipuri_inmatriculare_tipuri_elemente;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran23);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //TODO remove acc_lang, cont_lang;
        acc_lang = (sharedpreferences.getString("acc_lang", "en"));
        cont_lang = (sharedpreferences.getString("cont_lang", "ro"));

        positionExemplu = ((GlobalVar) this.getApplication()).getPositionExemplu();
        id_tara = ((GlobalVar) this.getApplication()).getId_tara();
        country_select = ((GlobalVar) this.getApplication()).getCountry_select();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        getDataThread();

        m_orders = new ArrayList<Order>();
        this.m_adapter = new OrderAdapter(this, R.layout.raw_list, m_orders);

        ListView lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(this.m_adapter);

        m_adapter.notifyDataSetChanged();

        viewOrders = new Runnable() {
            @Override
            public void run() {

                getOrders();
            }
        };
        Thread thread = new Thread(null, viewOrders, "MagentoBackground");
        thread.start();

        m_ProgressDialog = ProgressDialog.show(Ecran23Activity.this,
                "", "", true);


        TextView country = (TextView) findViewById(R.id.textViewCountry);
        country.setText(country_select);

        ImageView flag = (ImageView) findViewById(R.id.flag);
        ImageView bkg = (ImageView) findViewById(R.id.img);
        if (id_tara == 31) {
            flag.setBackgroundResource(R.drawable.flag_bg);
            bkg.setImageResource(R.drawable.bg);
        }

        if (flag != null)
            flag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Ecran23Activity.this, Ecran25Activity.class));
                }
            });

        ((GlobalVar) this.getApplication()).setId_shared(id_shared);
        ((GlobalVar) this.getApplication()).setNume_tip_inmatriculare_id(nume_tip_inmatriculare_id);
        ((GlobalVar) this.getApplication()).setName_tip_inmatriculare(nume_tip_inmatriculare);
        ((GlobalVar) this.getApplication()).setPositionExemplu(positionExemplu);
        ((GlobalVar) this.getApplication()).setIds_tipuri_inmatriculare_tipuri_elemente(Ids_tipuri_inmatriculare_tipuri_elemente);
    }

    private Runnable returnRes = new Runnable() {

        @Override
        public void run() {
            populate_order();

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


    private class OrderAdapter extends ArrayAdapter<Order> {

        private ArrayList<Order> items;

        public OrderAdapter(Context context, int textViewResourceId, ArrayList<Order> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.raw_list, null);
            }
            Gson gson = new Gson();
            Type listeType = new TypeToken<ArrayList<Response>>() {
            }.getType();
            final ArrayList<Response> response = (ArrayList<Response>) gson.fromJson(result_tari, listeType);
            if (response != null) {
                int id_name_tip_inmatriculare_phone = (sharedpreferences.getInt("nume_tip_inmatriculare_id", 0));
                if (id_name_tip_inmatriculare_phone == 0) {
                    nume_tip_inmatriculare_id=response.get(position).getId();
                    nume_tip_inmatriculare=response.get(position).getNume();
                }
                LinearLayout ll = (LinearLayout) v.findViewById(R.id.linear1);
                LinearLayout lll = (LinearLayout) v.findViewById(R.id.linear2);
                ImageView btn1 = (ImageView) v.findViewById(R.id.radioButton);
                if (positionExemplu == position) {
                    lll.setBackgroundColor(Color.parseColor("#F0F0F0"));
                }

                if (id_name_tip_inmatriculare_phone == (response.get(position).getId())) {
                    btn1.setImageResource(R.drawable.radio_press);

                }
                TextView tt = (TextView) v.findViewById(R.id.text);
                if (tt != null) {
                    tt.setText(response.get(position).getNume());
                }
                if (ll != null) {
                    ll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            nume_tip_inmatriculare_id=response.get(position).getId();
                            nume_tip_inmatriculare=response.get(position).getNume();
                            id_shared=response.get(position).getId();
                            startActivity(new Intent(Ecran23Activity.this, Ecran20Activity.class));
                            finish();
                        }
                    });
                }
                if (lll != null) {
                    lll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            positionExemplu = position;
                            Ids_tipuri_inmatriculare_tipuri_elemente= response.get(position).getIds_tipuri_inmatriculare_tipuri_elemente();
                            startActivity(new Intent(Ecran23Activity.this, Ecran26Activity.class));
                        }
                    });
                }
            }
            return v;
        }
    }


    private void populate_order() {
        try {
            jsonArray = new JSONArray(result_tari);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        m_orders = new ArrayList<Order>();

        JSONObject json_data = null;

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                json_data = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Order oz = new Order();
            try {
                oz.setOrderId(json_data.getInt("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                oz.setOrderName(json_data.getString("nume"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                oz.setOrderOrdinea(json_data.getInt("ordinea"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray arrayz = null;
            try {
                arrayz = json_data.getJSONArray("ids_tipuri_inmatriculare_tipuri_elemente");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            oz.setOrderIdsTip(arrayz);
            ////////////////////////////////////
            m_orders.add(oz);
        }

    }


    private void getDataThread() {


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                final GetTipNumar nr = new GetTipNumar();
                nr.setParam(id_tara, acc_lang, cont_lang);
                result_tari = nr.getRaspuns();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("array", nr.getRaspuns());
                editor.commit();


            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        /**
         * react to the user tapping the back/up icon in the action bar
         */
        startActivity(new Intent(Ecran23Activity.this, Ecran20Activity.class));
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Ecran23Activity.this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
