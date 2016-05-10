package ro.nubloca;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Ecran10Activity extends AppCompatActivity {
    public String url = "http://api.nubloca.ro/contact/";
    JSONArray jsonobject_Three = new JSONArray();
    JSONObject jsonobject_TWO = new JSONObject();
    JSONObject jsonobject_one_one = new JSONObject();

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




    }







    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu3, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu1) {
            final EditText nameField = (EditText) findViewById(R.id.editText3);
            final EditText telefonField = (EditText) findViewById(R.id.editText4);
            final EditText emailField = (EditText) findViewById(R.id.editText5);
            final EditText mesajField = (EditText) findViewById(R.id.editText6);

            String name = nameField.getText().toString();
            String telefon = telefonField.getText().toString();
            String email = emailField.getText().toString();
            String mesaj = mesajField.getText().toString();

            Context context = getApplicationContext();
            CharSequence text = "Thank You ";
            int duration = Toast.LENGTH_SHORT;


        JSONObject js = new JSONObject();
        try {
            JSONObject jsonobject_one = new JSONObject();

            jsonobject_one.put("app_code", "abcdefghijkl123456");

            jsonobject_one_one.put("user", jsonobject_one);


            jsonobject_TWO.put("nume", name);
            jsonobject_TWO.put("id_email", 15);
            jsonobject_TWO.put("id_numar_telefon", 14);
            jsonobject_TWO.put("mesaj", mesaj);


            jsonobject_Three.put("id");
            jsonobject_Three.put("data");
            jsonobject_Three.put("status");


            //js = new JSONObject("{\"identificare\":{\"user\":{\"app_code\":\"abcdefghijkl123456\"}},\"trimise\":{\"nume\":\"gaby dog\",\"id_email\":15,\"id_numar_telefon\":14,\"mesaj\":\"Mesaj\"},\"cerute\":[\"id\",\"data\",\"status\"]}");

            js.put("identificare",jsonobject_one_one);
            js.put("cerute",jsonobject_Three);
            js.put("trimise",jsonobject_TWO);


        } catch (JSONException e) {
            e.printStackTrace();
        }




        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("gogo", response.toString());


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("dog", "Error: " + error.getMessage());

            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Accept-Language", "en");
                headers.put("Content-Language", "fr");


                return headers;
            }

        };







            Toast toast = Toast.makeText(context, text+name+"!", duration);
            toast.show();

            queue.add(jsonObjReq);
        }

        return super.onOptionsItemSelected(item);
    }
}
