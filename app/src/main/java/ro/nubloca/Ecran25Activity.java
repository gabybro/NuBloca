package ro.nubloca;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ro.nubloca.Networking.GetTari;
import ro.nubloca.Networking.Order;

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
                //handler.sendEmptyMessage(0);
                tarr=tari.getRaspuns();

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


      /*  if (id_tara == 147) {
            ImageView iv = (ImageView) findViewById(R.id.radioButton1);
            iv.setImageResource(R.drawable.radio_press);

        } else if (id_tara == 31) {
            ImageView iv = (ImageView) findViewById(R.id.radioButton2);
            iv.setImageResource(R.drawable.radio_press);

        }*/


        /*RelativeLayout rel = (RelativeLayout) this.findViewById(R.id.rel_bar1);
        if (rel != null)
            rel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (id_tara != 147) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putInt("id_tara", 147);
                        editor.putString("nume_tip_inmatriculare", "default");
                        editor.putInt("nume_tip_inmatriculare_id", 0);
                        editor.commit();
                        startActivity(new Intent(Ecran25Activity.this, Ecran23Activity.class));
                        finish();
                    }
                }
            });*/

        /*RelativeLayout rel1 = (RelativeLayout) this.findViewById(R.id.rel_bar2);
        if (rel1 != null)
            rel1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (id_tara != 31) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putInt("id_tara", 31);
                        editor.putString("nume_tip_inmatriculare", "default");
                        editor.putInt("nume_tip_inmatriculare_id", 0);
                        editor.commit();
                        startActivity(new Intent(Ecran25Activity.this, Ecran23Activity.class));
                        finish();
                    }
                }
            });*/

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
            //RadioButton btn1 = (RadioButton) findViewById(R.id.radioButton);
            //if (btn1 != null){ btn1.setChecked(true);}
            if (o != null) {
                //int id_name_tip_inmatriculare_phone = (sharedpreferences.getInt("nume_tip_inmatriculare_id", 0));
                /*if (id_name_tip_inmatriculare_phone==0){
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt("nume_tip_inmatriculare_id", o.getOrderId());
                    editor.putString("nume_tip_inmatriculare", o.getOrderName());
                    editor.commit();
                }*/
               // LinearLayout ll = (LinearLayout) v.findViewById(R.id.linear1);
               // LinearLayout lll = (LinearLayout) v.findViewById(R.id.linear2);
               // ImageView btn1 = (ImageView) v.findViewById(R.id.radioButton);
               /* if(positionExemplu==position){
                    ImageView image = (ImageView) v.findViewById(R.id.chevron);
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                    image.startAnimation(animation);
                }*/

                /*if (id_name_tip_inmatriculare_phone==(o.getOrderId())) {
                    //Toast.makeText(getBaseContext(), "asd", Toast.LENGTH_SHORT).show();
                    //ll.setBackgroundColor(Color.parseColor("#00ffff"));
                    //btn1.setBackgroundColor(Color.parseColor("#ff00ff"));
                    btn1.setImageResource(R.drawable.radio_press);

                }*/
                /*if (aaa.equals("default")) {
                    if (btn1 != null){ btn1.setChecked(true);}
                    boolean x = true;
                }*/

                //final int x = o.getOrderId();
                TextView tt = (TextView) v.findViewById(R.id.text1);
                if (tt != null) {
                    tt.setText(o.getOrderName());
                }
               // if (ll != null) { }
               // if (lll != null) {  //set onclick
               //           }

                //TextView bt = (TextView) v.findViewById(R.id.bottomtext);

                //if(bt != null){
                //  bt.setText("Status: "+ o.getOrderId());
                //}
            }
            return v;
        }
    }


    private void populate_order() {
        //result_tari = (sharedpreferences.getString("array", null));
        //getDataThread();

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

            ////////////////////////////////////
            m_orders.add(oz);
        }

    }

}
