package ro.nubloca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
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
import ro.nubloca.Networking.GetRequestImg;
import ro.nubloca.Networking.Response;
import ro.nubloca.extras.Global;

public class Ecran20Activity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    int campuri = 3;
    int id_tara = 147;
    int order = 1;
    String url = "http://api.nubloca.ro/tipuri_inmatriculare/";
    String url1 = "http://api.nubloca.ro/tipuri_inmatriculare_tipuri_elemente/";
    String url2 = "http://api.nubloca.ro/tipuri_elemente/";
    String url3 = "http://api.nubloca.ro/tipuri_inmatriculare/";
    String url4 = "http://api.nubloca.ro/imagini/";
    int dim=30;
    byte[] baite;

    String name_tip_inmatriculare;
    int[] ids_tipuri_inmatriculare_tipuri_elemente;
    int[] id_tip_element;
    int idd;
    int id_shared = 0;
    List<Response> response2,response3;
    JSONArray valoareArr;
    String[] lista_cod;
    AllElem[] allelem;
    InputFilter filter, filter1;
    String numeSteag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran20);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //name_tip_inmatriculare = (sharedpreferences.getString("nume_tip_inmatriculare", "-"));

        id_tara = ((Global) this.getApplication()).getId_tara();
        id_shared=((Global) this.getApplication()).getId_shared();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);



        //TODO after req

        makePostRequestOnNewThread();



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
                    ((Global) getApplicationContext()).setPositionExemplu(-1);
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
        //linearLayout.setPadding(convDp(valSPL), 0, convDp(valSPL), 0);
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
        TextView tip_inmatriculare_nume = (TextView) findViewById(R.id.nume_tip_inmatriculare);
        tip_inmatriculare_nume.setText(name_tip_inmatriculare);
        ImageView image = (ImageView) findViewById(R.id.imageView9);
        Bitmap bmp = BitmapFactory.decodeByteArray(baite, 0, baite.length);
        image.setImageBitmap(bmp);
        image.getLayoutParams().height=convDp(dim);
        image.getLayoutParams().width=convDp(dim);
        image.requestLayout();
        image.setVisibility(View.VISIBLE);

    }

    private void makePostRequestOnNewThread() {

        Thread t0 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    if (id_shared == 0) {
                        makePostRequest0();
                    }
                    makePostRequest1();
                    makePostRequest2();
                    makePostRequest3();
                    makePostRequest4();
                    makePostRequest5();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        t0.start();
        try {
            t0.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void makePostRequest0() throws JSONException {
        //url = "http://api.nubloca.ro/tipuri_inmatriculare/";
        /*{"identificare": {"user": {"app_code": "abcdefghijkl123456"},
           "resursa": {"id_tara": [147],"ordinea":[1]}},
           "cerute": ["id"	]}*/

        GetRequest elemm = new GetRequest();
        JSONArray idTara = new JSONArray().put(id_tara);
        JSONArray ordinea = new JSONArray().put(order);
        JSONObject resursa = new JSONObject().put("id_tara", idTara).put("ordinea", ordinea);
        JSONArray cerute = new JSONArray().put("id");
        String result_string = elemm.getRaspuns(Ecran20Activity.this, url, resursa, cerute);

        /*[{            "id": 1        }]*/

        Gson gson = new Gson();
        Type listeType = new TypeToken<List<Response>>() {
        }.getType();
        List<Response> response = (List<Response>) gson.fromJson(result_string, listeType);
        idd = response.get(0).getId();
        ((Global) getApplicationContext()).setId_shared(idd);
    }

    private void makePostRequest1() throws JSONException {
        //url = "http://api.nubloca.ro/tipuri_inmatriculare/";
        /*{"identificare": {"user": {"app_code": "abcdefghijkl123456"},
           "resursa": {"id":[2]}},
           "cerute": ["id","nume","ids_tipuri_inmatriculare_tipuri_elemente"]}*/

        id_shared = ((Global) getApplicationContext()).getId_shared();
        GetRequest elemm = new GetRequest();
        JSONArray cerute = new JSONArray().put("id").put("nume").put("ids_tipuri_inmatriculare_tipuri_elemente");
        JSONArray idTara = new JSONArray().put(id_shared);
        JSONObject resursa = new JSONObject().put("id", idTara);

        String result_string = elemm.getRaspuns(Ecran20Activity.this, url, resursa, cerute);
        /*[{            "id": 2,
                        "nume": "Temporară",
                        "ids_tipuri_inmatriculare_tipuri_elemente":[4,5]        }]*/
        Gson gson = new Gson();
        Type listeType = new TypeToken<List<Response>>() {
        }.getType();
        List<Response> response = (List<Response>) gson.fromJson(result_string, listeType);
        name_tip_inmatriculare = response.get(0).getNume();
        ids_tipuri_inmatriculare_tipuri_elemente = response.get(0).getIds_tipuri_inmatriculare_tipuri_elemente();
        campuri = ids_tipuri_inmatriculare_tipuri_elemente.length;
        ((Global) getApplicationContext()).setName_tip_inmatriculare(name_tip_inmatriculare);
    }

    private void makePostRequest2() throws JSONException {
        //url1 = "http://api.nubloca.ro/tipuri_inmatriculare_tipuri_elemente/";
        /*{"identificare": {"user": {"app_code": "abcdefghijkl123456"},
           "resursa": {"id": [1,2,3]}},
           "cerute":["id","id_tip_element","ordinea","valoare_demo_imagine"]}*/

        JSONArray arr = new JSONArray();
        for (int i = 0; i < ids_tipuri_inmatriculare_tipuri_elemente.length; i++) {
            arr.put(ids_tipuri_inmatriculare_tipuri_elemente[i]);
        }

        JSONObject resursa = new JSONObject().put("id", arr);
        JSONArray cerute = new JSONArray().put("id").put("id_tip_element").put("ordinea");
        GetRequest elem = new GetRequest();
        String result_string = elem.getRaspuns(Ecran20Activity.this, url1, resursa, cerute);

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
         /*[{   "id": 1, // 2  // etc..
                "id_tip_element": 1,
                "ordinea": 1  // 2  //  etc..
                valoare_demo_imagine": "B" // 255 // 255        }]*/
        //Todo orinea
        //ordinea

        id_tip_element = new int[response.size()];
        for (int i = 0; i < response.size(); i++) {
            int j = response.get(i).getId_tip_element();
            id_tip_element[i] = j;
        }

    }

    private void makePostRequest3() throws JSONException {
        //url2 = "http://api.nubloca.ro/tipuri_elemente/";
       /* {"identificare": {"user": {"app_code": "abcdefghijkl123456"},
           "resursa":{"id":[5,6,6]}},
           "cerute":["id", "tip", "editabil_user", "maxlength", "valori"]  }*/

        GetRequest elemm = new GetRequest();
        Type listeType = new TypeToken<List<Response>>() {
        }.getType();
        JSONArray id = new JSONArray();
        for (int i = 0; i < id_tip_element.length; i++) {
            id.put(id_tip_element[i]);
        }
        JSONObject resursa = new JSONObject().put("id", id);
        JSONArray cerute = new JSONArray().put("id").put("tip").put("editabil_user").put("maxlength").put("valori");

        String result_string = elemm.getRaspuns(Ecran20Activity.this, url2, resursa, cerute);
        Gson gson = new Gson();
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

        /*[{"id": 5,  //  6
            "tip": "LISTA",  //  "CIFRE"   //   "LITERE"
            "editabil_user": 1,   //   0
            "maxlength": 2,
            "valori":[{"id": 1,"cod": "CD"},{"id": 2,"cod": "CO"},{"id": 3,"cod": "TC"}]},  //  "^[0-9]{3}$"   }]*/

        ((Global) getApplicationContext()).setId_shared(0);

    }

    private void makePostRequest4() throws JSONException {
        //url3 = http://api.nubloca.ro/tipuri_inmatriculare/;
        /*{"identificare":{"user":{"app_code": "abcdefghijkl123456"},
           "resursa":{"id": [1]}},
           "cerute": ["id", "nume", "id_tara", "foto_background", "url_imagine", "ids_tipuri_inmatriculare_tipuri_elemente"]  }*/

        GetRequest elemm = new GetRequest();
        Gson gson = new Gson();
        Type listeType = new TypeToken<List<Response>>() {
        }.getType();
        JSONArray id = new JSONArray().put(id_shared);
        JSONObject resursa = new JSONObject().put("id", id);
        JSONArray cerute = new JSONArray().put("id").put("nume").put("id_tara").put("foto_background").put("url_imagine").put("ids_tipuri_inmatriculare_tipuri_elemente");

        String result_string = elemm.getRaspuns(Ecran20Activity.this, url3, resursa, cerute);
        response3 = (List<Response>) gson.fromJson(result_string, listeType);

        numeSteag=response3.get(0).getId_tara()+".png";
        /*[{"id": 1,
            "nume": "Permanentă",
            "id_tara": 147,
            "foto_background": "1.jpg",
            "url_imagine": "147-1.png",
            "ids_tipuri_inmatriculare_tipuri_elemente":[1,2,3]}]*/

    }

    private void makePostRequest5() throws JSONException {
        //url4 = "http://api.nubloca.ro/imagini/";
        /*{"identificare": {"user": {"app_code": "abcdefghijkl123456"},
           "resursa": {"pentru": "tari", "tip": "steaguri", "nume": "147.png", "dimensiuni": [43]}}}*/

        //Accept:image/png

        JSONArray dimensiuni = new JSONArray().put(dim);
        JSONObject resursa = new JSONObject().put("pentru", "tari").put("tip", "steaguri").put("nume", numeSteag).put("dimensiuni", dimensiuni);

        GetRequestImg elem = new GetRequestImg();
        baite = elem.getRaspuns(Ecran20Activity.this, url4, "image/png", resursa);
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