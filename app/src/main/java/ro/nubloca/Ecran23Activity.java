package ro.nubloca;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Ecran23Activity extends AppCompatActivity {
    public String url = "http://api.nubloca.ro/tipuri_inmatriculare/";
    JSONObject js = new JSONObject();
    JSONObject jsonobject_identificare = new JSONObject();
    JSONArray jsonobject_cerute = new JSONArray();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran23);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        View flag = (View) findViewById(R.id.flag);
        flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Ecran23Activity.this, Ecran25Activity.class));
            }
        });

        LinearLayout exemplu1 = (LinearLayout) findViewById(R.id.linear1);
        exemplu1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ecran23Activity.this, Ecran26Activity.class));

            }
        });

        LinearLayout relbar = (LinearLayout) findViewById(R.id.rel_bar1);
        relbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton btn1 = (RadioButton) findViewById(R.id.radioButton2);
                if (btn1.isChecked()) {
                    btn1.setChecked(false);
                } else {
                    btn1.setChecked(true);
                }

                //send GET
                sendJsonRequest();
            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu) {

        /*View button = (View) this.findViewById(R.id.toolbar_title);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/

        return true;
    }

    private void sendJsonRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);
        prepJsonSend();

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("identificare", jsonobject_identificare);
        params.put("cerute", jsonobject_cerute);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("gogo", response.toString());
                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, response.toString(), duration);
                        toast.show();
                        //parseJSONResponse(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d("dog", "Error: " + error.getMessage());

            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Accept-Language", "en");
                headers.put("Content-Language", "fr");


                return headers;
            }


        };
        queue.add(jsonObjReq);
    }


    private void prepJsonSend() {

        try {
            JSONObject jsonobject_one = new JSONObject();
            JSONObject jsonobject_resursa = new JSONObject();
            JSONArray jsonobject_id = new JSONArray();

            jsonobject_one.put("app_code", "abcdefghijkl123456");
            jsonobject_id.put(1);
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

    }
}
