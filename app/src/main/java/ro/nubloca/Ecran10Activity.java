package ro.nubloca;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

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
    JSONObject js = new JSONObject();

   public String name, telefon, email, mesaj;


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

            name = nameField.getText().toString();
            telefon = telefonField.getText().toString();
            email = emailField.getText().toString();
            mesaj = mesajField.getText().toString();

            Context context = getApplicationContext();
            CharSequence text = "Thank You ";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text+name+"!", duration);
            toast.show();


        }

        return super.onOptionsItemSelected(item);
    }
    private void prepJsonSend(){

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
            js.put("identificare",jsonobject_one_one);
            js.put("cerute",jsonobject_Three);
            js.put("trimise",jsonobject_TWO);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
