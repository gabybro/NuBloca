package ro.nubloca;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
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

public class Ecran13Activity extends AppCompatActivity {
    int id_response;
    String titlu_response;
    int id_parinte_response;
    String text_response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran13);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

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

        TextView tv = (TextView) findViewById(R.id.textView1);
        tv.setText(titlu_response);

        TextView tv1 = (TextView) findViewById(R.id.textView2);
        //tv1.setText(text_response);
        tv1.setText(Html.fromHtml(text_response));


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
        JSONArray cerute = new JSONArray().put("id").put("titlu").put("id_parinte").put("text");
        JSONArray id = new JSONArray().put(8);
        JSONObject resursa = new JSONObject().put("id", id);

        result_string = elem.getRaspuns(this, url, resursa, cerute);
        response = gson.fromJson(result_string, listeType);

        id_response = response.get(0).getId();
        titlu_response = response.get(0).getTitlu();
        id_parinte_response = response.get(0).getIdParinte();
        text_response = response.get(0).getText();


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

    /*@Override
    public void onBackPressed() {
        startActivity(new Intent(Ecran13Activity.this, Ecran20Activity.class));
        finish();
        super.onBackPressed();
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                Ecran13Activity.this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}
