package ro.nubloca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ro.nubloca.Networking.AllElem;
import ro.nubloca.Networking.GetRequest;

import ro.nubloca.Networking.Response;


import ro.nubloca.Networking.StandElem;
import ro.nubloca.Networking.TipElem;
import ro.nubloca.extras.Global;

public class Ecran1Activity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    boolean Tos;
    private ProgressBar progressBar;
    //private int progressStatus = 0;
    //private Handler handler = new Handler();
    int id_tara;

    String countryCode;

    AllElem[] allelem;
    TipElem[] tipElem;
    StandElem standElem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        id_tara = (sharedpreferences.getInt("id_tara", 147));
        Tos = (sharedpreferences.getBoolean("TOS", false));

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        countryCode = tm.getSimCountryIso();

        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_IN);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!Tos) {
                    finish();
                    startActivity(new Intent(Ecran1Activity.this, Ecran3Activity.class));
                } else {
                    finish();
                    startActivity(new Intent(Ecran1Activity.this, Ecran7Activity.class));
                }
            }
        }, 3000);

        makePostRequestOnNewThread();
    }

    private void makePostRequestOnNewThread() {


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    makePostRequest1();
                    makePostRequest2();
                  //  makePostRequest3();


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

    }


    private void makePostRequest1() throws JSONException {
        String result_string;
        Gson gson = new Gson();
        Type listeType = new TypeToken<List<Response>>() {
        }.getType();
        List<Response> response;
        GetRequest elem = new GetRequest();
        /*{ "identificare": {"user": {"app_code": "abcdefghijkl123456"},
            "resursa": {"cod": ["ro"]}},
            "cerute": ["id","url_steag","nume"]}*/

        String url = "http://api.nubloca.ro/tari/";
        JSONArray cerute = new JSONArray().put("id").put("url_steag").put("nume");
        JSONArray cod = new JSONArray().put(countryCode);
        JSONObject resursa = new JSONObject().put("cod", cod);

        result_string = elem.getRaspuns(Ecran1Activity.this, url, resursa, cerute);
        response = gson.fromJson(result_string, listeType);

        standElem = new StandElem();
        standElem.setCod(countryCode);
        standElem.setId(response.get(0).getId());
        standElem.setUrl_steag(response.get(0).getUrl_steag());
        standElem.setNume(response.get(0).getNume());
        standElem.setOrdinea(1);

        //taraElem = response;
       /* [{
            id: 147,
            url_steag: "147.png",
            nume: "România"
        }]*/
    }

    private void makePostRequest2() throws JSONException {
        String result_string;
        Gson gson = new Gson();
        Type listeType = new TypeToken<List<Response>>() {
        }.getType();
        List<Response> response;
        GetRequest elem = new GetRequest();
        //url = "http://api.nubloca.ro/tipuri_inmatriculare/";
        /*{"identificare": {"user": {"app_code": "abcdefghijkl123456"},
           "resursa": {"id_tara": [147]}},
           "cerute": ["id", "nume", "ordinea", "ids_tipuri_inmatriculare_tipuri_elemente"]}*/

        String url = "http://api.nubloca.ro/tipuri_inmatriculare/";
        JSONArray idTara = new JSONArray().put(standElem.getId());
        JSONObject resursa = new JSONObject().put("id_tara", idTara);
        JSONArray cerute = new JSONArray().put("id").put("nume").put("ordinea").put("ids_tipuri_inmatriculare_tipuri_elemente");

        result_string = elem.getRaspuns(Ecran1Activity.this, url, resursa, cerute);
        response = gson.fromJson(result_string, listeType);


        List<StandElem.TipNumar> tipNumarLista = new ArrayList<>();
        for (int i=0; i<response.size(); i++){
            StandElem.TipNumar tipNumar = new StandElem.TipNumar();

            tipNumar.setId(response.get(i).getId());
            tipNumar.setNume(response.get(i).getNume());
            tipNumar.setOrdinea(response.get(i).getOrdinea());
            tipNumar.setTip_size(response.get(i).getIds_tipuri_inmatriculare_tipuri_elemente().length);
            tipNumar.setTip_idd(response.get(i).getIds_tipuri_inmatriculare_tipuri_elemente());

            tipNumarLista.add(tipNumar);
            }
        standElem.setTipNumar(tipNumarLista);


        /*[{"id": 9,
            "nume": "Standard",
            "ordinea": 1,
            "ids_tipuri_inmatriculare_tipuri_elemente":[19,20,21] }] */

       // Arrays.sort(standElem.tipNumar);
    }

    private void makePostRequest3() throws JSONException {
        String result_string;
        Gson gson = new Gson();
        Type listeType = new TypeToken<List<Response>>() {
        }.getType();
        List<Response> response;
        GetRequest elem = new GetRequest();
        //url = http://api.nubloca.ro/tipuri_elemente/;
        /*{ "identificare": {"user": {"app_code": "abcdefghijkl123456"},
            "resursa":{"id":[5,6,6]}},
            "cerute":[    "id",    "tip",  "editabil_user",     "maxlength",   "valori"]   }*/

        String url = "http://api.nubloca.ro/tipuri_elemente/";

        JSONArray cerute = new JSONArray().put("id").put("tip").put("editabil_user").put("maxlength").put("valori");
        JSONObject resursa = new JSONObject();
        JSONArray id = new JSONArray();



        for (int i = 0; i < (standElem.getTipNumar()).get(i).getTip_size(); i++) {
            id.put((standElem.getTipNumar()).get(i).getId());
        }
        resursa.put("id", id);

        result_string = elem.getRaspuns(Ecran1Activity.this, url, resursa, cerute);
        response = gson.fromJson(result_string, listeType);
        JSONArray valoareArr;
        String[] lista_cod;
        tipElem = new TipElem[response.size()];
        for (int i = 0; i < tipElem.length; i++) {
            tipElem[i] = new TipElem();
            for (int j = 0; j < response.size(); j++) {
                if (tipElem[i].getId_tip_element() == response.get(j).getId()) {
                    tipElem[i].setEditabil_user(response.get(j).getEditabil_user());
                    tipElem[i].setMaxlength(response.get(j).getMaxlength());
                    tipElem[i].setTip(response.get(j).getTip());
                    String s = new JSONArray(result_string).getJSONObject(j).getString("valori");

                    if (tipElem[i].getTip().equals("LISTA")) {
                        valoareArr = new JSONArray(s);
                        tipElem[i].setValoriArray(valoareArr);
                        lista_cod = new String[valoareArr.length()];
                        tipElem[i].setLista_cod(lista_cod);
                        for (int z = 0; z < valoareArr.length(); z++) {
                            lista_cod[z] = valoareArr.getJSONObject(z).getString("cod");
                        }
                    } else {
                        tipElem[i].setValoriString(s);
                    }
                }
            }
        }



        /*[{"id": 5, // 6,
            "tip": "LISTA", // "CIFRE" // "LITERE"
            "editabil_user": 1, // 0
            "maxlength": 2,  //  9
            "valori":[{"id": 1,"cod": "CD"},{"id": 2,"cod": "CO"},{"id": 3,"cod": "TC"}]},  //  "^[0-9]{3}$"   ]*/

        //((Global) getApplicationContext()).setTaraElem(taraElem);
        //((Global) getApplicationContext()).setAllelem(allelem);
       // ((Global) getApplicationContext()).setTipElem(tipElem);
    }

}
