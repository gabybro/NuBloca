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


public class Ecran23Activity extends AppCompatActivity {

    int id_tara = 147;
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
    String elem1, elem2;
    String url3 = "http://api.nubloca.ro/tipuri_inmatriculare_tipuri_elemente/";
    String url4 = "http://api.nubloca.ro/tipuri_elemente/";
    int[] id_tip_element;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran23);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        acc_lang = (sharedpreferences.getString("acc_lang", "en"));
        cont_lang = (sharedpreferences.getString("cont_lang", "ro"));
        positionExemplu = (sharedpreferences.getInt("positionExemplu", -1));
        id_tara = (sharedpreferences.getInt("id_tara", 147));
        country_select = (sharedpreferences.getString("country_select", "Romania"));


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
                    //finish();
                }
            });


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
        // private List<Response> items;

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

            // final Order o = items.get(position);

            //RadioButton btn1 = (RadioButton) findViewById(R.id.radioButton);
            //if (btn1 != null){ btn1.setChecked(true);}
            //if (o != null) {
            if (response != null) {
                int id_name_tip_inmatriculare_phone = (sharedpreferences.getInt("nume_tip_inmatriculare_id", 0));
                if (id_name_tip_inmatriculare_phone == 0) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt("nume_tip_inmatriculare_id", response.get(position).getId());
                    // editor.putInt("nume_tip_inmatriculare_id", o.getOrderId());
                    editor.putString("nume_tip_inmatriculare", response.get(position).getNume());
                    //editor.putString("nume_tip_inmatriculare", o.getOrderName());
                    editor.commit();
                }
                LinearLayout ll = (LinearLayout) v.findViewById(R.id.linear1);
                LinearLayout lll = (LinearLayout) v.findViewById(R.id.linear2);
                ImageView btn1 = (ImageView) v.findViewById(R.id.radioButton);
                if (positionExemplu == position) {
                    //ImageView image = (ImageView) v.findViewById(R.id.chevron);
                    //Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                    //image.startAnimation(animation);
                    lll.setBackgroundColor(Color.parseColor("#F0F0F0"));
                }

                //if (id_name_tip_inmatriculare_phone == (o.getOrderId())) {
                if (id_name_tip_inmatriculare_phone == (response.get(position).getId())) {
                    //Toast.makeText(getBaseContext(), "asd", Toast.LENGTH_SHORT).show();

                    //btn1.setBackgroundColor(Color.parseColor("#ff00ff"));
                    btn1.setImageResource(R.drawable.radio_press);

                }
                /*if (aaa.equals("default")) {
                    if (btn1 != null){ btn1.setChecked(true);}
                    boolean x = true;
                }*/

                // final int x = o.getOrderId();
                final int x = response.get(position).getId();
                TextView tt = (TextView) v.findViewById(R.id.text);
                if (tt != null) {
                    // tt.setText(o.getOrderName());
                    tt.setText(response.get(position).getNume());
                }
                if (ll != null) {
                    ll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            /*editor.putInt("nume_tip_inmatriculare_id", o.getOrderId());
                            editor.putString("nume_tip_inmatriculare", o.getOrderName());
                            editor.putString("getOrderIdsTip", o.getOrderIdsTip().toString());
                            editor.putInt("campuri", o.getOrderIdsTip().length());*/
                            editor.putInt("nume_tip_inmatriculare_id", response.get(position).getId());
                            editor.putString("nume_tip_inmatriculare", response.get(position).getNume());
                            //editor.putString("getOrderIdsTip", response.get(position).getIds_tipuri_inmatriculare_tipuri_elemente().toString());
                            //editor.putInt("campuri", response.get(position).getIds_tipuri_inmatriculare_tipuri_elemente().length);
                            editor.putInt("id_shared", response.get(position).getId());
                            final GetRequest elem = new GetRequest();
//TODO id_shared
                            /*Thread t = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    JSONObject resursa = new JSONObject();
                                    try {
                                        resursa.put("id", response.get(position).getIds_tipuri_inmatriculare_tipuri_elemente());
                                        // resursa.put("id",o.getOrderIdsTip().toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    JSONArray cerute = new JSONArray();
                                    cerute.put("id");
                                    cerute.put("id_tip_element");
                                    cerute.put("ordinea");
                                    elem1 = elem.getRaspuns(Ecran23Activity.this, url3, resursa, cerute);
                                }
                            });
                            t.start();
                            try {
                                t.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Gson gson1 = new Gson();
                            Type listeType1 = new TypeToken<ArrayList<Response>>() {
                            }.getType();*/
                            //final ArrayList<Response> response = (ArrayList<Response>) gson1.fromJson(elem1, listeType1);
                            //final GetRequest elemm = new GetRequest();
                            //Toast toast= Toast.makeText(Ecran23Activity.this, response.get(position).getId()+"", Toast.LENGTH_LONG);
                            //toast.show();


                            /*Thread t1 = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    JSONObject resursa = new JSONObject();

                                    populate_order1();
                                    try {
                                        resursa.put("id", id_tip_element);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    JSONArray cerute = new JSONArray();
                                    cerute.put("id");
                                    cerute.put("tip");
                                    cerute.put("editabil_user");
                                    cerute.put("maxlength");
                                    cerute.put("valori");
                                    elem2 = elemm.getRaspuns(Ecran23Activity.this, url4, resursa, cerute);
                                }
                            });
                            t1.start();
                            try {
                                t1.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }*/

                            //editor.putString("ElemNumere", elem1);
                            //editor.putString("ElemNumere1", elem2);


                            editor.commit();
                            startActivity(new Intent(Ecran23Activity.this, Ecran20Activity.class));
                            finish();
                        }
                    });
                }
                if (lll != null) {
                    lll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putInt("positionExemplu", position);
                            /*editor.putString("getOrderIdsTip", o.getOrderIdsTip().toString());
                            editor.putString("nume_tip_inmatriculare", o.getOrderName());*/
                            editor.putString("getOrderIdsTip", response.get(position).getIds_tipuri_inmatriculare_tipuri_elemente().toString());
                            editor.putString("nume_tip_inmatriculare", response.get(position).getNume());
                            editor.commit();
                            startActivity(new Intent(Ecran23Activity.this, Ecran26Activity.class));
                        }
                    });
                }
            }
            return v;
        }
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            /*if (checkState == true) {
                Context context = getApplicationContext();
                CharSequence text = "request done!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();*/
        }
    };


    private void populate_order1() {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(elem1);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONObject json_data = null;
        int yy;
        yy = jsonArray.length();
        id_tip_element = new int[yy];
        for (int i = 0; i < yy; i++) {
            try {
                json_data = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Order oz = new Order();

            try {
                int xx;
                xx = json_data.getInt("id_tip_element");
                id_tip_element[i] = xx;

            } catch (JSONException e) {
                e.printStackTrace();
            }

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
            /*int size = 0;
            size = arrayz.length();
            int[] numbers = new int[size];
            for (int j = 0; j < size; ++j) {
                numbers[j] = arrayz.optInt(j);
            }*/
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
                //handler.sendEmptyMessage(0);
                result_tari = nr.getRaspuns();

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("array", nr.getRaspuns());
                //editor.putString("nume_tip_inmatriculare", nr.getNumeUsed());

                //editor.putString("ids_tipuri_inmatriculare_tipuri_elemente", nr.getIdsUsed());
                //editor.apply();
                //populate_order();
                //editor.putString("array", arr);
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
        startActivity(new Intent(Ecran23Activity.this, Ecran20Activity.class));
        finish();
        super.onBackPressed();
    }
}
