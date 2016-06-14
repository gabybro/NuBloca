package ro.nubloca;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ro.nubloca.Networking.GetRequest;
import ro.nubloca.Networking.GetRequestImg;
import ro.nubloca.Networking.Response;
import ro.nubloca.extras.Global;


public class Ecran23Activity extends AppCompatActivity {


    int id_tara = 147;
    String result_tari;
    int positionExemplu = -1;
    String country_select;
    private ProgressDialog m_ProgressDialog = null;
    private ArrayList<Response> m_orders = null;
    private ResponseAdapter m_adapter;
    private Runnable viewOrders;
    JSONArray jsonArray = null;
    String url = "http://api.nubloca.ro/tipuri_inmatriculare/";
    String url1 = "http://api.nubloca.ro/imagini/";
    int id_name_tip_inmatriculare_phone = 0;
    int[] Ids_tipuri_inmatriculare_tipuri_elemente;
    String nume_tip_inmatriculare = "standard";
    String numeSteag;
    int dim1 =75;
    int dim;
    byte[] baite, baite1;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran23);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.VISIBLE);

        positionExemplu = ((Global) this.getApplication()).getPositionExemplu();

        id_tara = ((Global) this.getApplication()).getId_tara();

        country_select = ((Global) this.getApplication()).getCountry_select();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        getDataThread();

        m_orders = new ArrayList<Response>();
        this.m_adapter = new ResponseAdapter(this, R.layout.raw_list, m_orders);

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
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        m_ProgressDialog = ProgressDialog.show(Ecran23Activity.this,
                "", "", true);

        //progressBar.setVisibility(View.GONE);
        TextView country = (TextView) findViewById(R.id.textViewCountry);
        country.setText(country_select);

        ImageView flag = (ImageView) findViewById(R.id.flag);
        ImageView bkg = (ImageView) findViewById(R.id.img);


        Bitmap bmp = BitmapFactory.decodeByteArray(baite, 0, baite.length);
        bkg.setImageBitmap(bmp);

        Bitmap bmp1 = BitmapFactory.decodeByteArray(baite1, 0, baite1.length);
        //Bitmap bMapScaled = Bitmap.createScaledBitmap(bmp1,0, convDp(dim/2), true);

        //flag.setImageBitmap(bMapScaled);
        flag.setImageBitmap(bmp1);

        final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        animation.setDuration(1500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in

        final ImageView arw = (ImageView) findViewById(R.id.arrow);
        arw.startAnimation(animation);

        RelativeLayout relBkg = (RelativeLayout) findViewById(R.id.rel_bkg);


        if (relBkg != null)
            relBkg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Ecran23Activity.this, Ecran25Activity.class));
                    //finish();
                }
            });
        //handler.sendEmptyMessage(0);

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
                v = vi.inflate(R.layout.raw_list, null);
            }
            Gson gson = new Gson();
            Type listeType = new TypeToken<ArrayList<Response>>() {
            }.getType();
            ImageView btn1 = (ImageView) v.findViewById(R.id.radioButton);
            final ArrayList<Response> response = (ArrayList<Response>) gson.fromJson(result_tari, listeType);
            if (response != null) {
                id_name_tip_inmatriculare_phone = ((Global) getApplicationContext()).getNume_tip_inmatriculare_id();
                if (id_name_tip_inmatriculare_phone == 0) {
                    ((Global) getApplicationContext()).setNume_tip_inmatriculare_id(response.get(0).getId());
                    ((Global) getApplicationContext()).setNume_tip_inmatriculare(response.get(0).getNume());
                    ((Global) getApplicationContext()).setId_shared(response.get(0).getId());
                    //btn1.setImageResource(R.drawable.radio_press);
                }
                LinearLayout ll = (LinearLayout) v.findViewById(R.id.linear1);
                LinearLayout lll = (LinearLayout) v.findViewById(R.id.linear2);

                if (positionExemplu == position) {
                    lll.setBackgroundColor(Color.parseColor("#F0F0F0"));
                }


                /*if (id_name_tip_inmatriculare_phone == position) {
                    btn1.setImageResource(R.drawable.radio_press);
                }*/

                final int x = response.get(position).getId();
                TextView tt = (TextView) v.findViewById(R.id.text);
                if (tt != null) {
                    tt.setText(response.get(position).getNume());
                }
                if (ll != null) {
                    ll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((Global) getApplicationContext()).setNume_tip_inmatriculare_id(response.get(position).getId());
                            ((Global) getApplicationContext()).setName_tip_inmatriculare(response.get(position).getNume());
                            ((Global) getApplicationContext()).setId_shared(response.get(position).getId());
                            startActivity(new Intent(Ecran23Activity.this, Ecran20Activity.class));
                            finish();
                        }
                    });
                }
                if (lll != null) {
                    lll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            ((Global) getApplicationContext()).setPositionExemplu(position);

                            nume_tip_inmatriculare = response.get(position).getNume();
                            Ids_tipuri_inmatriculare_tipuri_elemente = response.get(position).getIds_tipuri_inmatriculare_tipuri_elemente();

                            ((Global) getApplicationContext()).setId_exemplu(response.get(position).getId());
                            ((Global) getApplicationContext()).setIds_tipuri_inmatriculare_tipuri_elemente(Ids_tipuri_inmatriculare_tipuri_elemente);
                            ((Global) getApplicationContext()).setNume_tip_inmatriculare(nume_tip_inmatriculare);
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


        m_orders = new ArrayList<Response>();

        JSONObject json_data = null;

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                json_data = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Response oz = new Response();
            try {
                oz.setId(json_data.getInt("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                oz.setNume(json_data.getString("nume"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                oz.setOrdinea(json_data.getInt("ordinea"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray arrayz = null;
            try {
                arrayz = json_data.getJSONArray("ids_tipuri_inmatriculare_tipuri_elemente");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            int arrayy[] = new int[arrayz.length()];
            for (int xx = 0; xx < arrayz.length(); xx++) {
                try {
                    arrayy[i] = arrayz.getInt(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            oz.setIds_tipuri_inmatriculare_tipuri_elemente(arrayy);

            m_orders.add(oz);
            //handler.sendEmptyMessage(0);
        }

    }

    private void getDataThread() {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                makePostRequest1();
                makePostRequest2();
                makePostRequest3();
               // handler.sendEmptyMessage(0);
                //progressBar.setVisibility(View.GONE);
            }


        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void makePostRequest3() {
        //url1 = "http://api.nubloca.ro/imagini/";
        /*{            "identificare": {            "user": {                "app_code": "abcdefghijkl123456"            },
                        "resursa": {  "pentru": "tari",  "tip": "steaguri",   "nume": "147.png",    "dimensiuni": [43]   }}}*/
        //Content-Type:application/json
        //Accept:image/png
        numeSteag=id_tara+".png";

        JSONObject resursa = null;
        try {
            resursa = new JSONObject().put("pentru", "tari").put("tip", "steaguri").put("nume", numeSteag);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dim = convDp(dim1);
        JSONArray dimensiuni = new JSONArray().put(dim);
        try {
            resursa.put("dimensiuni", dimensiuni);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GetRequestImg elem = new GetRequestImg();
        baite1 = elem.getRaspuns(Ecran23Activity.this, url1, "image/png", resursa);
    }

    private void makePostRequest2() {
        //url1 = "http://api.nubloca.ro/imagini/";
        /*{            "identificare": {            "user": {                "app_code": "abcdefghijkl123456"            },
                        "resursa": {  "pentru": "tari",  "tip": "reprezentative",   "nume": "147.jpg" }}}*/
        //Content-Type:application/json
        //Accept:image/jpg
        numeSteag=id_tara+".jpg";

        JSONObject resursa = null;
        try {
            resursa = new JSONObject().put("pentru", "tari").put("tip", "reprezentative").put("nume", numeSteag);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GetRequestImg elem = new GetRequestImg();
        baite = elem.getRaspuns(Ecran23Activity.this, url1, "image/jpeg", resursa);
    }

    private void makePostRequest1() {
        //url = "http://api.nubloca.ro/tipuri_inmatriculare/";
        /*{"identificare": {"user": { "app_code": "abcdefghijkl123456" },
           "resursa": {"id_tara": [147]}},
           "cerute": ["id", "nume", "ids_tipuri_inmatriculare_tipuri_elemente", "ordinea"]}*/


        final GetRequest nr = new GetRequest();
        JSONArray jsonobject_id = new JSONArray().put(id_tara);
        JSONObject resursa = new JSONObject();
        try {
            resursa = new JSONObject().put("id_tara", jsonobject_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray cerute = new JSONArray().put("id").put("nume").put("ids_tipuri_inmatriculare_tipuri_elemente").put("ordinea");

        Gson gson = new Gson();
        Type listeType = new TypeToken<List<Response>>() {
        }.getType();

        result_tari = nr.getRaspuns(Ecran23Activity.this, url, resursa, cerute);
        //response = (List<Response>) gson.fromJson(result_tari, listeType);

        ((Global) getApplicationContext()).setArray(result_tari);


    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(Ecran23Activity.this, Ecran20Activity.class));
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();
                Ecran23Activity.this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    int convDp(float sizeInDp) {
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (sizeInDp * scale + 0.5f);
        return dpAsPixels;
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            progressBar.setVisibility(View.GONE);



        }


    };
}
