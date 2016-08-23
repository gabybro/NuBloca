package ro.nubloca;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import ro.nubloca.Networking.GetRequest;
import ro.nubloca.Networking.Response;

public class Ecran11Activity extends AppCompatActivity {
    int value = 0;
    int[] id_response;
    String[] titlu_response;
    int[] id_parinte_response;
    String[] text_response;
    int[] ordinea_response;
    int[] copii_response;
    int size=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran11);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        //upArrow.setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_ATOP);
        //getSupportActionBar().setHomeAsUpIndicator(upArrow);

        /*Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getInt("id_parinte");
        }*/


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    makePostRequest();

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

        /*LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(this);
        //textView.setText( titlu_response[0]);
        textView.setText("asd");
        linearLayout.addView(textView);*/

//        for (int i=0;i<size;i++){

        RelativeLayout relLay = (RelativeLayout) findViewById(R.id.relLayout);
        //ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT, 1f);

        LinearLayout rV = new LinearLayout(this);
        rV.setBackgroundResource(R.drawable.border3);
        TextView tV = new TextView(this);
        tV.setText(titlu_response[0]);
        rV.addView(tV);



        LinearLayout rV1 = new LinearLayout(this);
        rV1.setBackgroundResource(R.drawable.border3);
        TextView tV1 = new TextView(this);
        tV1.setText(titlu_response[1]);
        rV1.addView(tV1);

        relLay.addView(rV);
        relLay.addView(rV1);

//        }

    }


    private void makePostRequest() throws JSONException {
        String result_string;
        Gson gson = new Gson();
        Type listeType = new TypeToken<List<Response>>() {
        }.getType();
        List<Response> response;
        GetRequest elem = new GetRequest();
        /*{ "identificare": {"user": {"app_code": "abcdefghijkl123456"},
            "resursa": {"id": [8]}},
            "cerute": ["id","titlu","id_parinte","text"]}*/

        String url = "http://api.nubloca.ro/help/";
        JSONArray cerute = new JSONArray().put("id").put("titlu").put("ordinea").put("id_parinte").put("copii");
        JSONArray id = new JSONArray();
        /*if (value == 0) {
            id.put(null);
        } else {
             id.put(value);
        }*/
        id.put(null);
        JSONObject resursa = new JSONObject().put("id_parinte", id);
        result_string = elem.getRaspuns(this, url, resursa, cerute);
        response = gson.fromJson(result_string, listeType);

        size = response.size();

        id_response = new int[size];
        titlu_response = new String[size];
        ordinea_response = new int[size];
        id_parinte_response = new int[size];
        copii_response = new int[size];

        for (int i = 0; i < size; i++) {
            id_response[i] = response.get(i).getId();
            titlu_response[i] = response.get(i).getTitlu();
            ordinea_response[i] = response.get(i).getOrdinea();
            id_parinte_response[i] = response.get(i).getIdParinte();
            copii_response[i] = response.get(i).getCopii();
            //text_response[i] = response.get(i).getText();
        }

       /* [{
            id: 8,
            titlu: "Number register",
            id_parinte: 5,
            text": "Insert HTML\r\n<p>Number register</p>\r\nTest"
        }]*/

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }
}
