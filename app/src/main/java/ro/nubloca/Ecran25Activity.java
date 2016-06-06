package ro.nubloca;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ro.nubloca.Networking.GetTari;
import ro.nubloca.Networking.Order;
import ro.nubloca.extras.GlobalVar;

public class Ecran25Activity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    int id_tara = 147;
    String acc_lang, cont_lang;
    JSONArray jsonArray = new JSONArray();
    String tarr;
    private ProgressDialog m_ProgressDialog = null;
    private ArrayList<Order> m_orders = null;
    private OrderAdapter m_adapter;
    private Runnable viewOrders;
    //id_tara ;
    String nume_tip_inmatriculare;
    int nume_tip_inmatriculare_id;
    String country_select;
    int positionExemplu;

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
        id_tara = (sharedpreferences.getInt("id_tara", 147));
        acc_lang = (sharedpreferences.getString("acc_lang", "en"));
        cont_lang = (sharedpreferences.getString("cont_lang", "ro"));

        final GetTari tari = new GetTari();
        tari.setParam(acc_lang, cont_lang);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                tarr = tari.getRaspuns();
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        m_orders = new ArrayList<Order>();
        this.m_adapter = new OrderAdapter(this, R.layout.raw_list1, m_orders);

        /*Toast toast = Toast.makeText(this, tarr, Toast.LENGTH_LONG);
        toast.show();*/

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

        ((GlobalVar) this.getApplication()).setId_tara(id_tara);
        ((GlobalVar) this.getApplication()).setName_tip_inmatriculare(nume_tip_inmatriculare);
        ((GlobalVar) this.getApplication()).setNume_tip_inmatriculare_id(nume_tip_inmatriculare_id);
        ((GlobalVar) this.getApplication()).setCountry_select(country_select);
        ((GlobalVar) this.getApplication()).setPositionExemplu(positionExemplu);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    private Runnable returnRes = new Runnable() {

        @Override
        public void run() {
            // getDataThread();
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
                v = vi.inflate(R.layout.raw_list1, null);
            }
            final Order o = items.get(position);
            ImageView iiv = (ImageView) v.findViewById(R.id.radioButton1);
            RelativeLayout rel = (RelativeLayout) v.findViewById(R.id.rel_bar1);
            if (o != null) {
                if ((id_tara == o.getOrderId()) && (iiv != null)) {

                    iiv.setImageResource(R.drawable.radio_press);
                } else {
                    iiv.setImageResource(R.drawable.radio);
                }

                if (rel != null)
                    rel.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (id_tara != o.getOrderId()) {


                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                id_tara= o.getOrderId();
                                editor.putInt("id_tara", id_tara);
                                editor.commit();
                                nume_tip_inmatriculare="default";
                                nume_tip_inmatriculare_id=0;
                                country_select=o.getOrderName();
                                positionExemplu= -1;
                                startActivity(new Intent(Ecran25Activity.this, Ecran23Activity.class));
                                finish();
                            }
                        }
                    });
                TextView tt = (TextView) v.findViewById(R.id.text1);
                if (tt != null) {
                    tt.setText(o.getOrderName());
                }
            }
            return v;
        }
    }


    private void populate_order() {

        try {
            jsonArray = new JSONArray(tarr);
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

            m_orders.add(oz);
        }

    }

}
