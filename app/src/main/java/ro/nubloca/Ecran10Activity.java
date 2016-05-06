package ro.nubloca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Ecran10Activity extends AppCompatActivity {
    public String stringResponse;
    public JSONObject jsonobject;

    public String url ="http://api.nubloca.ro/contact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran10);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        try {
            JSONObject app_data = new JSONObject();
            app_data.put("app_code", "asdaksjdhkahsdj");

            JSONObject user_data = new JSONObject();
            user_data.put("user", app_data);


            JSONObject trimise_data = new JSONObject();
            trimise_data.put("nume", "gabriel san");
            trimise_data.put("id_email", "13");
            trimise_data.put("id_numar_telefon", "14");
            trimise_data.put("mesaj", "event");

            JSONArray cerute_data = new JSONArray();
            cerute_data.put("id");
            cerute_data.put("data");
            cerute_data.put("status");

            jsonobject = new JSONObject();

            jsonobject.put("identificare", user_data);
            jsonobject.put("trimise", trimise_data);
            jsonobject.put("cerute", cerute_data);


        }catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue= Volley.newRequestQueue(this);

        // make a POST json request from the provided URL.
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,url, jsonobject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("respopne", response.toString());

                        // msgResponse.setText(response.toString());
                        // hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hideProgressDialog();
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        requestQueue.add(jsonObjReq);

    }





    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu3, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu1) {
            final EditText nameField = (EditText) findViewById(R.id.editText3);
            String name = nameField.getText().toString();
            Context context = getApplicationContext();
            CharSequence text = "Hello ";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text+name+"!", duration);
            toast.show();
        }

        return super.onOptionsItemSelected(item);
    }
}
