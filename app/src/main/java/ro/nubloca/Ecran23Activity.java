package ro.nubloca;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import ro.nubloca.Networking.HttpBodyGet;
import ro.nubloca.Networking.Order;


public class Ecran23Activity extends AppCompatActivity {
//public class Ecran23Activity extends ListActivity {

    JSONArray response_ids_inmatriculare = new JSONArray();
    String url = "http://api.nubloca.ro/tipuri_inmatriculare/";
    //String url1 = "http://api.nubloca.ro/tipuri_elemente/";
    String url1 = "http://api.nubloca.ro/tipuri_inmatriculare_tipuri_elemente/";
    String result_string, result_string1;
    String result_tari;
    String name_tip_inmatriculare;
    int x = 0;
    int campuri = 3;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    String acc_lang, cont_lang;
    int tip_inmat = 1;
    String maxLength1, maxLength2, maxLength3;
    //Boolean checkState;
    private ProgressDialog m_ProgressDialog = null;
    private ArrayList<Order> m_orders = null;
    private OrderAdapter m_adapter;
    private Runnable viewOrders;
    JSONArray jsonArray = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran23);
        Intent myIntent = getIntent();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        result_tari = (sharedpreferences.getString("array", null));
        acc_lang = (sharedpreferences.getString("acc_lang", "en"));
        cont_lang = (sharedpreferences.getString("cont_lang", "ro"));

        //TODO remove from Global Settings(admin)
        // checkState = (sharedpreferences.getBoolean("http_req", true));
        //tip_inmat = (sharedpreferences.getInt("tip_inmat", 1));


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        m_orders = new ArrayList<Order>();
        this.m_adapter = new OrderAdapter(this, R.layout.raw_list, m_orders);
        ListView lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(this.m_adapter);

        viewOrders = new Runnable() {
            @Override
            public void run() {
                getOrders();
            }
        };
        Thread thread = new Thread(null, viewOrders, "MagentoBackground");
        thread.start();
        m_ProgressDialog = ProgressDialog.show(Ecran23Activity.this,
                "Please wait...", "Retrieving data ...", true);


        populate_order();


        View flag = (View) findViewById(R.id.flag);
        if (flag != null)
            flag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Ecran23Activity.this, Ecran25Activity.class));
                }
            });

       /* LinearLayout exemplu1 = (LinearLayout) findViewById(R.id.linear1);
        if (exemplu1 != null)
            exemplu1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ImageView image = (ImageView) findViewById(R.id.chevron1);
                    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                    image.startAnimation(animation1);
                    makePostRequestOnNewThread();


                }
            });*/


       /* LinearLayout relbar = (LinearLayout) findViewById(R.id.rel_bar1);
        if (relbar != null)
            relbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageView image = (ImageView) findViewById(R.id.chevron1);
                    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                    image.startAnimation(animation1);
                    RadioButton btn1 = (RadioButton) findViewById(R.id.radioButton2);
                    if (btn1.isChecked()) {
                        btn1.setChecked(false);
                    } else {
                        btn1.setChecked(true);
                        //makePostRequestOnNewThread();

                        //finish();
                    }
                }
            });*/


    }

    private Runnable returnRes = new Runnable() {

        @Override
        public void run() {
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
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.raw_list, null);
            }
            final Order o = items.get(position);
            if (o != null) {
                LinearLayout ll = (LinearLayout) v.findViewById(R.id.linear1);
                LinearLayout lll = (LinearLayout) v.findViewById(R.id.linear2);
                final String x = o.getOrderId();
                TextView tt = (TextView) v.findViewById(R.id.text);
                if (tt != null) {
                    tt.setText(o.getOrderName());
                }
                if (ll != null) {
                    ll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Intent myIntent = new Intent(MainActivity.this, SecondActivity.class);
                            //startActivity(myIntent);
                            Toast.makeText(getBaseContext(), "asd" + x, Toast.LENGTH_SHORT).show();


                        }
                    });
                }
                if (lll != null) {
                    lll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //makePostRequestOnNewThread();
                            //startActivity(new Intent(Ecran23Activity.this, Ecran26Activity.class));
                            Intent myIntent = new Intent(Ecran23Activity.this, Ecran26Activity.class);
                            myIntent.putExtra("array", o.getOrderIdsTip());

                            //myIntent.putExtra("array", m_orders.toString());

                            startActivity(myIntent);
                        }
                    });
                }

                //TextView bt = (TextView) v.findViewById(R.id.bottomtext);

                //if(bt != null){
                //  bt.setText("Status: "+ o.getOrderId());
                //}
            }
            return v;
        }
    }

    private void makePostRequestOnNewThread() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                makePostRequest();
                //send data to shared pref <-- prev screen (Contact Auto)
                //SharedPreferences.Editor editor = sharedpreferences.edit();
                //editor.putInt("campuri", campuri);
                //editor.apply();
                handler.sendEmptyMessage(0);
                //send data to -->next screen (Exemplu)
                Intent myIntent = new Intent(Ecran23Activity.this, Ecran26Activity.class);
                // myIntent.putExtra("campuri", campuri + "");
///                myIntent.putExtra("name_tip_inmatriculare", name_tip_inmatriculare);
                //             myIntent.putExtra("Campul unu", maxLength1);
                //           myIntent.putExtra("Campul doi", maxLength2);
                //         myIntent.putExtra("Campul trei", maxLength3);
                startActivity(myIntent);

            }
        });
        t.start();
    }

    /*private void makePostRequestOnNewThread1() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                makePostRequest1();
            }
        });
        t.start();

    }*/

    private void makePostRequest() {
        JSONObject jsonobject_identificare = new JSONObject();
        JSONArray jsonobject_cerute = new JSONArray();
        JSONObject js = new JSONObject();

        try {
            JSONObject jsonobject_one = new JSONObject();
            JSONObject jsonobject_resursa = new JSONObject();
            JSONArray jsonobject_id = new JSONArray();

            //TODO get app_code from phone
            jsonobject_one.put("app_code", "abcdefghijkl123456");
            //TODO replace tip_inmapt with onClick id
            jsonobject_id.put(tip_inmat);
            jsonobject_resursa.put("id", jsonobject_id);

            jsonobject_identificare.put("user", jsonobject_one);
            jsonobject_identificare.put("resursa", jsonobject_resursa);
            jsonobject_cerute.put("id");
            jsonobject_cerute.put("nume");
            jsonobject_cerute.put("id_tara");
            jsonobject_cerute.put("foto_background");
            jsonobject_cerute.put("url_imagine");
            jsonobject_cerute.put("ids_tipuri_inmatriculare_tipuri_elemente");

            js.put("identificare", jsonobject_identificare);
            js.put("cerute", jsonobject_cerute);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        HttpClient httpClient = new DefaultHttpClient();
        HttpBodyGet httpPost = new HttpBodyGet(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Content-Language", cont_lang);
        httpPost.setHeader("Accept-Language", acc_lang);


        //Encoding POST data
        try {

            httpPost.setEntity(new ByteArrayEntity(js.toString().getBytes("UTF8")));

        } catch (UnsupportedEncodingException e) {
            // log exception
            e.printStackTrace();
        }

        //making POST request.
        try {
            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity());
            result_string = result;
            prelucrareRaspuns();

        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }


    }

    private void prelucrareRaspuns() {
        JSONArray jsonArray1 = new JSONArray();
        try {
            jsonArray1 = new JSONArray(result_string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1 = (jsonArray1.getJSONObject(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //String s = jsonObject1.getClass().getName();
        try {
            response_ids_inmatriculare = jsonObject1.getJSONArray("ids_tipuri_inmatriculare_tipuri_elemente");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            name_tip_inmatriculare = jsonObject1.getString("nume");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        x = response_ids_inmatriculare.optInt(0);
        //makePostRequestOnNewThread1();
        makePostRequest1();

        /*Toast toast = Toast.makeText(this, name_tip_inmatriculare, Toast.LENGTH_LONG);
        toast.show();*/


    }

    private void makePostRequest1() {
        JSONObject jsonobject_identificare = new JSONObject();
        JSONArray jsonobject_cerute = new JSONArray();
        JSONObject js = new JSONObject();


        try {
            JSONObject jsonobject_one = new JSONObject();
            JSONObject jsonobject_resursa = new JSONObject();

            //TODO get app_code from phone
            jsonobject_one.put("app_code", "abcdefghijkl123456");

            jsonobject_resursa.put("id", response_ids_inmatriculare);
            jsonobject_identificare.put("user", jsonobject_one);
            jsonobject_identificare.put("resursa", jsonobject_resursa);
            jsonobject_cerute.put("id");
            jsonobject_cerute.put("id_tip_element");
            jsonobject_cerute.put("valoare_demo_imagine");
            jsonobject_cerute.put("ordinea");


            js.put("identificare", jsonobject_identificare);
            js.put("cerute", jsonobject_cerute);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        HttpClient httpClient = new DefaultHttpClient();
        HttpBodyGet httpPost = new HttpBodyGet(url1);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Content-Language", cont_lang);
        httpPost.setHeader("Accept-Language", acc_lang);

        try {

            httpPost.setEntity(new ByteArrayEntity(js.toString().getBytes("UTF8")));

        } catch (UnsupportedEncodingException e) {
            // log exception
            e.printStackTrace();
        }

        //making POST request.
        try {
            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity());
            result_string1 = result;
            prelucrareRaspuns1();


        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }


    }

    private void prelucrareRaspuns1() {

        JSONArray jsonArray1 = new JSONArray();
        try {
            jsonArray1 = new JSONArray(result_string1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1 = (jsonArray1.getJSONObject(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        campuri = jsonArray1.length();
        if (campuri >= 2) {
            try {
                JSONObject ob1 = new JSONObject();
                ob1 = jsonArray1.getJSONObject(0);
                String max1;
                max1 = ob1.getString("valoare_demo_imagine");
                maxLength1 = max1;
                //maxLength1=Integer.parseInt(jsonArray1.getJSONObject(0).getString("maxlength"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONObject ob2 = new JSONObject();
                ob2 = jsonArray1.getJSONObject(1);
                String max2;
                max2 = ob2.getString("valoare_demo_imagine");
                maxLength2 = max2;
                //maxLength2=Integer.parseInt(jsonArray1.getJSONObject(1).getString("maxlength"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (campuri == 3) {
                try {
                    JSONObject ob3 = new JSONObject();
                    ob3 = jsonArray1.getJSONObject(2);
                    String max3;
                    max3 = ob3.getString("valoare_demo_imagine");
                    maxLength3 = max3;
                    //maxLength2=Integer.parseInt(jsonArray1.getJSONObject(2).getString("maxlength"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
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
                oz.setOrderId(json_data.getString("id"));
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

            ///////////////////////
            JSONArray arrayz = null;
            try {
                arrayz = json_data.getJSONArray("ids_tipuri_inmatriculare_tipuri_elemente");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            int size = 0;
            size = arrayz.length();
            int[] numbers = new int[size];
            for (int j = 0; j < size; ++j) {
                numbers[j] = arrayz.optInt(j);
            }
            oz.setOrderIdsTip(numbers);
            ////////////////////////////////////
            m_orders.add(oz);
        }

    }

}
