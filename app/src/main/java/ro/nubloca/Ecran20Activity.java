package ro.nubloca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import ro.nubloca.Networking.AllElem;
import ro.nubloca.Networking.GetRequest;
import ro.nubloca.Networking.Response;
import ro.nubloca.extras.GlobalVar;

public class Ecran20Activity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    int campuri = 3;
    int id_tara = 147;
    int order = 1;
    String url = "http://api.nubloca.ro/tipuri_inmatriculare/";
    String url1 = "http://api.nubloca.ro/tipuri_inmatriculare_tipuri_elemente/";
    String url2 = "http://api.nubloca.ro/tipuri_elemente/";

    String name_tip_inmatriculare;
    int[] ids_tipuri_inmatriculare_tipuri_elemente;
    int[] id_tip_element;
    int idd;
    int id_shared = 0;
    List<Response> response2;
    JSONArray valoareArr;
    String[] lista_cod;
    AllElem[] allelem;
    InputFilter filter, filter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran20);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name_tip_inmatriculare = ((GlobalVar) this.getApplication()).getName_tip_inmatriculare();
        id_shared = ((GlobalVar) this.getApplication()).getId_shared();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        TextView tip_inmatriculare_nume = (TextView) findViewById(R.id.nume_tip_inmatriculare);
        //TODO after req

        makePostRequestOnNewThread();

        tip_inmatriculare_nume.setText(name_tip_inmatriculare);

        showElements();


        View btn3 = (View) this.findViewById(R.id.textView24);
        if (btn3 != null)
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Ecran20Activity.this, Ecran24Activity.class));

                }
            });

        View btn5 = (View) this.findViewById(R.id.textView23);
        if (btn5 != null)
            btn5.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Ecran20Activity.this, Ecran13Activity.class));

                }
            });

        View btn2 = (View) this.findViewById(R.id.relativ2);
        if (btn2 != null)
            btn2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt("positionExemplu", -1);
                    editor.commit();
                    startActivity(new Intent(Ecran20Activity.this, Ecran23Activity.class));
                    finish();

                }
            });


        LinearLayout btn1 = (LinearLayout) this.findViewById(R.id.lin_bar1);
        if (btn1 != null)
            btn1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Ecran20Activity.this, Ecran22Activity.class));
                }
            });


    }

    private void showElements() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int divLength = size.x;
        int height = size.y;

        int nrUML = 0;
        int nrSPI = campuri - 1;
        int nrSPL = 2;

        int valUML = 0;
        int valSPL = 0;
        int valSPI = 0;

        int valMaxUML = divLength / 10;

        for (int i = 0; i < campuri; i++) {
            // calculam lunngimea inputului
            if (allelem[i].getMaxlength() < 3) {
                int rr = allelem.length;
                int oo = allelem[i].getMaxlength();
                nrUML += 3;
            } else {
                int xx = allelem[i].getMaxlength();
                nrUML += allelem[i].getMaxlength();
            }

        }
        valSPI = divLength / (nrUML * 6 + 3 * nrSPL + nrSPI);
        valSPL = 3 * valSPI;
        valUML = 6 * valSPI;
        int valRealUml = Math.min(valMaxUML, valUML);
        int minTrei = 0;


        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_contact1);
        linearLayout.setPadding(valSPL, 0, valSPL, 0);
        filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; ++i) {
                    if (!Pattern.compile("[1234567890]*").matcher(String.valueOf(source.charAt(i))).matches()) {
                        return "";
                    }
                }

                return null;
            }
        };

        filter1 = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; ++i) {
                    if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*").matcher(String.valueOf(source.charAt(i))).matches()) {
                        return "";
                    }
                }

                return null;
            }
        };

        for (int i = 0; i < campuri; i++) {
            if (allelem[i].getMaxlength() < 3) {             minTrei = 3;
            } else {                minTrei = allelem[i].getMaxlength();            }

            if (allelem[i].getTip().equals("LISTA")) {
                ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT, 1f);
                Spinner mySpinner = new Spinner (this);
                mySpinner.setAdapter(new ArrayAdapter<String>(Ecran20Activity.this, R.layout.raw_list_1, lista_cod));
                mySpinner.setBackgroundResource(R.drawable.plate_border);
                params.width = minTrei * valRealUml;
                mySpinner.setLayoutParams(params);
                linearLayout.addView(mySpinner);

            } else {
                EditText field = new EditText(this);
                field.setId(i);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT, 1f);

                if (i != 0) {
                    params.setMargins(valSPI, 0, 0, 0);
                }
                if ((i+1)<allelem.length){
                    if (allelem[i+1].getTip().equals("LISTA")) {params.setMargins(valSPI, 0, valSPI, 0);}
                }
                field.setLayoutParams(params);
                field.setWidth(valRealUml * minTrei);
                field.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
                field.setTextSize(30);
                field.setBackgroundResource(R.drawable.plate_border);

                if (allelem[i].getTip().equals("CIFRE")) {
                    if (allelem[i].getEditabil_user()==0) {
                        field.setText(allelem[i].getValoriString().replace("[", "").replace("]", ""));
                        field.setEnabled(false);
                        field.setBackgroundResource(R.drawable.plate_border_white);
                    }
                    field.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                    field.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(allelem[i].getMaxlength())});
                }
                if (allelem[i].getTip().equals("LITERE")) {
                    if (allelem[i].getEditabil_user()==0) {
                        field.setText(allelem[i].getValoriString().replace("[", "").replace("]", ""));
                        field.setEnabled(false);
                        field.setBackgroundResource(R.drawable.plate_border_white);
                    }
                    field.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                    field.setFilters(new InputFilter[]{filter1, new InputFilter.LengthFilter(allelem[i].getMaxlength())});
                }

                linearLayout.addView(field);
            }
        }

    }

    private void makePostRequestOnNewThread() {

        Thread t0 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    if (id_shared == 0) {
                        makePostRequest0();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //handler.sendEmptyMessage(0);
            }
        });
        t0.start();
        try {
            t0.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    makePostRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //handler.sendEmptyMessage(0);
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    makePostRequest1();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //handler.sendEmptyMessage(0);
            }
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    makePostRequest2();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //handler.sendEmptyMessage(0);
            }
        });
        t2.start();
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Toast toast = Toast.makeText(this, test, Toast.LENGTH_LONG);
        //toast.show();
    }

    private void makePostRequest0() throws JSONException {
        /*{
            "identificare": {
                "user": {"app_code": "abcdefghijkl123456"},
                "resursa": {"id_tara": [147],"ordinea":[1]}},
            "cerute": ["id"	]
        }*/

        GetRequest elemm = new GetRequest();
        id_tara = ((GlobalVar) this.getApplication()).getId_tara();
        JSONArray cerute = new JSONArray().put("id");
        JSONArray idTara = new JSONArray().put(id_tara);
        JSONArray ordinea = new JSONArray().put(order);
        JSONObject resursa = new JSONObject();
        resursa.put("id_tara", idTara);
        resursa.put("ordinea", ordinea);


        String result_string = elemm.getRaspuns(Ecran20Activity.this, url, resursa, cerute);
        /*[{
            "id": 1
        }]*/

        Gson gson = new Gson();
        Type listeType = new TypeToken<List<Response>>() {
        }.getType();
        List<Response> response = (List<Response>) gson.fromJson(result_string, listeType);
        idd = response.get(0).getId();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("id_shared", idd);
        editor.commit();
    }

    private void makePostRequest() throws JSONException {
        /*{
            "identificare": {
                "user": {"app_code": "abcdefghijkl123456"},
                "resursa": {"id":[2]}},
            "cerute": ["id","nume","ids_tipuri_inmatriculare_tipuri_elemente"]
        }*/

        GetRequest elemm = new GetRequest();
        JSONArray cerute = new JSONArray().put("id").put("nume").put("ids_tipuri_inmatriculare_tipuri_elemente");
        JSONArray idTara = new JSONArray().put(id_shared);
        JSONObject resursa = new JSONObject();
        resursa.put("id", idTara);

        String result_string = elemm.getRaspuns(Ecran20Activity.this, url, resursa, cerute);
        /*[{
            "id": 2,
                "nume": "Temporară",
                "ids_tipuri_inmatriculare_tipuri_elemente":[4,5]
        }]*/
        Gson gson = new Gson();
        Type listeType = new TypeToken<List<Response>>() {
        }.getType();
        List<Response> response = (List<Response>) gson.fromJson(result_string, listeType);
        name_tip_inmatriculare = response.get(0).getNume();
        ids_tipuri_inmatriculare_tipuri_elemente = response.get(0).getIds_tipuri_inmatriculare_tipuri_elemente();
        campuri = ids_tipuri_inmatriculare_tipuri_elemente.length;

        ((GlobalVar) this.getApplication()).setName_tip_inmatriculare(name_tip_inmatriculare);

    }

    private void makePostRequest1() throws JSONException {
        /*{
            "identificare": {
                "user": {"app_code": "abcdefghijkl123456"},
                "resursa": {"id": [1,2,3]}},
            "cerute":["id","id_tip_element","ordinea","valoare_demo_imagine"]
        }*/


        GetRequest elemm = new GetRequest();
        JSONObject resursa = new JSONObject();
        JSONArray arr = new JSONArray();
        JSONArray cerute = new JSONArray().put("id").put("id_tip_element").put("ordinea");
        for (int i = 0; i < ids_tipuri_inmatriculare_tipuri_elemente.length; i++) {

            arr.put(ids_tipuri_inmatriculare_tipuri_elemente[i]);
        }

        resursa.put("id", arr);

        String result_string = elemm.getRaspuns(Ecran20Activity.this, url1, resursa, cerute);

        Gson gson = new Gson();
        Type listeType = new TypeToken<List<Response>>() {
        }.getType();
        List<Response> response = (List<Response>) gson.fromJson(result_string, listeType);
        allelem = new AllElem[response.size()];
        for (int i = 0; i < response.size(); i++) {
            allelem[i] = new AllElem();
            allelem[i].setId(response.get(i).getId());
            allelem[i].setId_tip_element(response.get(i).getId_tip_element());
            allelem[i].setOrdinea(response.get(i).getOrdinea());
            allelem[i].setValoare_demo_imagine(response.get(i).getValoare_demo_imagine());
        }
         /*[{
            "id": 1,
                "id_tip_element": 1,
                "ordinea": 1
                valoare_demo_imagine": "B"
        },{
            "id": 2,
                "id_tip_element": 2,
                "ordinea": 2
                valoare_demo_imagine": 255
        },{
            "id": 3,
                "id_tip_element": 3,
                "ordinea": 3
                valoare_demo_imagine": 255
        }]*/
        //Todo orinea
        //ordinea

        id_tip_element = new int[response.size()];
        for (int i = 0; i < response.size(); i++) {
            int j = response.get(i).getId_tip_element();
            id_tip_element[i] = j;
        }

    }

    private void makePostRequest2() throws JSONException {
       /* {
            "identificare": {
                "user": {"app_code": "abcdefghijkl123456"},
                "resursa":{"id":[5,6,6]}},
            "cerute":[
                "id",
                "tip",
                "editabil_user",
                "maxlength",
                "valori"]
        }*/

        GetRequest elemm = new GetRequest();

        JSONArray cerute = new JSONArray().put("id").put("tip").put("editabil_user").put("maxlength").put("valori");
        JSONObject resursa = new JSONObject();
        String result_string = null;
        Gson gson = new Gson();
        Type listeType = new TypeToken<List<Response>>() {
        }.getType();
        JSONArray id = new JSONArray();
        for (int i = 0; i < id_tip_element.length; i++) {
            id.put(id_tip_element[i]);
        }
        resursa.put("id", id);
        result_string = elemm.getRaspuns(Ecran20Activity.this, url2, resursa, cerute);
        response2 = (List<Response>) gson.fromJson(result_string, listeType);

        for (int i = 0; i < allelem.length; i++) {
            for (int j = 0; j < response2.size(); j++) {
                if (allelem[i].getId_tip_element() == response2.get(j).getId()) {
                    allelem[i].setEditabil_user(response2.get(j).getEditabil_user());
                    allelem[i].setMaxlength(response2.get(j).getMaxlength());
                    allelem[i].setTip(response2.get(j).getTip());
                    String s = new JSONArray(result_string).getJSONObject(j).getString("valori");

                    if (allelem[i].getTip().equals("LISTA")) {
                        valoareArr = new JSONArray(s);
                        allelem[i].setValoriArray(valoareArr);
                        lista_cod = new String[valoareArr.length()];
                        for (int z = 0; z < valoareArr.length(); z++) {
                            lista_cod[z] = valoareArr.getJSONObject(z).getString("cod");
                        }
                    } else {
                        allelem[i].setValoriString(s);
                    }
                }
            }
        }

        Arrays.sort(allelem);

        /*[{
            "id": 5,
            "tip": "LISTA",
            "editabil_user": 1,
            "maxlength": 2,
            "valori":[{"id": 1,"cod": "CD"},{"id": 2,"cod": "CO"},{"id": 3,"cod": "TC"}]},
            {
            "id": 6,
            "tip": "CIFRE",
            "editabil_user": 1,
            "maxlength": 3,
            "valori": "^[0-9]{3}$"
            }]*/


        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("id_shared", 0);
        editor.commit();

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu5, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu1) {
            Context context = getApplicationContext();
            CharSequence text = "request done!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            //finish();
        }

        return super.onOptionsItemSelected(item);
    }

    int convDp(float sizeInDp) {
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (sizeInDp * scale + 0.5f);
        return dpAsPixels;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Ecran20Activity.this, Ecran7Activity.class));
        finish();
        super.onBackPressed();
    }

}