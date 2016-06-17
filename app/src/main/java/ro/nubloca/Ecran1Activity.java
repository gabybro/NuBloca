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

import ro.nubloca.Networking.GetRequestImg;
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
    int index;
    String countryCode,cc;
    int dim = 30;
    AllElem[] allelem;
    TipElem[] tipElem;
    StandElem standElem;
    byte[] baite;

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
        cc = tm.getSimCountryIso();

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

        makePostRequestOnNewThread(cc);

        ((Global) getApplicationContext()).setStandElem(standElem);
    }

    private void makePostRequestOnNewThread(String cc) {
        standElem = new StandElem();
        countryCode = cc;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    makePostRequest1();
                    makePostRequest2();
                    makePostRequest3();

                    for (index = 0; index < standElem.getSize(); index++) {
                        makePostRequest7();
                        makePostRequest4();
                    }
                    makePostRequest5();
                    makePostRequest6();
                    makePostRequest8();
                    makePostRequest9();

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

        standElem.setSize(response.size());
        List<StandElem.TipNumar> tipNumarLista = new ArrayList<>();
        for (int i = 0; i < response.size(); i++) {
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
        //url = "http://api.nubloca.ro/tipuri_inmatriculare/";
        /*{"identificare": {"user": {"app_code": "abcdefghijkl123456"},
           "resursa": {"id": [1,2,3,..]}},
           "cerute": ["id", "foto_background", "url_imagine"]}*/

        String url = "http://api.nubloca.ro/tipuri_inmatriculare/";
        JSONArray cerute = new JSONArray().put("id").put("foto_background").put("url_imagine");
        JSONObject resursa = new JSONObject();
        JSONArray id = new JSONArray();

        for (int i = 0; i < standElem.getSize(); i++) {
            id.put((standElem.getTipNumar()).get(i).getId());
        }
        resursa.put("id", id);

        result_string = elem.getRaspuns(Ecran1Activity.this, url, resursa, cerute);
        response = gson.fromJson(result_string, listeType);


        for (int i = 0; i < standElem.getSize(); i++) {
            for (int j = 0; j < standElem.getSize(); j++) {
                if (standElem.getTipNumar().get(i).getId() == response.get(j).getId()) {

                    standElem.getTipNumar().get(i).setFoto_background(response.get(i).getFoto_background());
                    standElem.getTipNumar().get(i).setUrl_imagine(response.get(i).getUrl_imagine());

                }
            }
        }



        /*[{id": 1,
            "foto_background": "1.jpg",
            "url_imagine": "147-1.png" }]*/

    }

    private void makePostRequest4() throws JSONException {
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

        int sizze = (standElem.getTipNumar()).get(index).getDemo_id_tip_element().length;
        for (int i = 0; i < sizze; i++) {
            id.put((standElem.getTipNumar()).get(index).getDemo_id_tip_loc(i));
        }
        resursa.put("id", id);
        result_string = elem.getRaspuns(Ecran1Activity.this, url, resursa, cerute);
        response = gson.fromJson(result_string, listeType);


        int[] idd = new int[sizze];
        int[] editabil = new int[sizze];
        int[] maxlength = new int[sizze];
        String[] tip = new String[sizze];
        String[] valori = new String[sizze];
        JSONArray valoriArr;
        String[][] lista_cod = new String[sizze][];

        for (int i = 0; i < sizze; i++) {

            for (int j = 0; j < response.size(); j++) {

                if ((standElem.getTipNumar()).get(index).getDemo_id_tip_loc(i) == response.get(j).getId()) {
                    editabil[i] = response.get(j).getEditabil_user();
                    maxlength[i] = response.get(j).getMaxlength();
                    tip[i] = response.get(j).getTip();
                    String s = new JSONArray(result_string).getJSONObject(j).getString("valori");
                    if (tip[i].equals("LISTA")) {
                        valoriArr = new JSONArray(s);
                        lista_cod = new String[sizze][valoriArr.length()];
                        for (int z = 0; z < valoriArr.length(); z++) {
                            lista_cod[i][z] = valoriArr.getJSONObject(z).getString("cod");
                        }
                    } else {
                        valori[i] = s;
                    }
                }
            }
        }

        standElem.getTipNumar().get(index).setTip_editabil(editabil);
        standElem.getTipNumar().get(index).setTip_maxlength(maxlength);
        standElem.getTipNumar().get(index).setTip_tip(tip);
        standElem.getTipNumar().get(index).setLista_cod(lista_cod);
        standElem.getTipNumar().get(index).setTip_valori(valori);

        /*[{"id": 5, // 6,
            "tip": "LISTA", // "CIFRE" // "LITERE"
            "editabil_user": 1, // 0
            "maxlength": 2,  //  9
            "valori":[{"id": 1,"cod": "CD"},{"id": 2,"cod": "CO"},{"id": 3,"cod": "TC"}]},  //  "^[0-9]{3}$"   ]*/


    }
    private void makePostRequest7() throws JSONException {
        String result_string;
        Gson gson = new Gson();
        Type listeType = new TypeToken<List<Response>>() {
        }.getType();
        List<Response> response;
        GetRequest elem = new GetRequest();
        //url = http://api.nubloca.ro/tipuri_inmatriculare_tipuri_elemente/;
        /*{"identificare": {"user": {"app_code": "abcdefghijkl123456"},
           "resursa": {"id": [1,2,3]}},
           "cerute": ["id","id_tip_element","ordinea","valoare_demo_imagine"]]}*/
        String url = "http://api.nubloca.ro/tipuri_inmatriculare_tipuri_elemente/";

        JSONObject resursa = new JSONObject();
        JSONArray cerute = new JSONArray().put("id").put("id_tip_element").put("valoare_demo_imagine").put("ordinea");
        JSONArray id = new JSONArray();

        int sizze = (standElem.getTipNumar()).get(index).getTip_size();
        for (int i = 0; i < sizze; i++) {
            id.put((standElem.getTipNumar()).get(index).getTip_idd_loc(i));
        }
        resursa.put("id", id);

        result_string = elem.getRaspuns(Ecran1Activity.this, url, resursa, cerute);
        response = gson.fromJson(result_string, listeType);
        int[] idd = new int[sizze];
        int[] id_tip_element = new int[sizze];
        int[] ordinea = new int[sizze];
        String[] valoare_demo_imagine = new String[sizze];

        for (int i = 0; i < sizze; i++) {

            for (int j = 0; j < response.size(); j++) {

                if ((standElem.getTipNumar()).get(index).getTip_idd()[i] == response.get(j).getId()) {
                    idd[i] = response.get(j).getId();
                    id_tip_element[i] = response.get(j).getId_tip_element();
                    ordinea[i] = response.get(j).getOrdinea();
                    valoare_demo_imagine[i] = response.get(j).getValoare_demo_imagine();
                }
            }
            standElem.getTipNumar().get(index).setDemo_id(idd);
            standElem.getTipNumar().get(index).setDemo_id_tip_element(id_tip_element);
            standElem.getTipNumar().get(index).setDemo_ordinea(ordinea);
            standElem.getTipNumar().get(index).setDemo_valoare(valoare_demo_imagine);


        }




        /*[{    "id": 1,
                "id_tip_element": 1,
                "ordinea": 1,
                "valoare_demo_imagine": "B"      }]*/
    }

    private void makePostRequest5() throws JSONException {
        //url4 = "http://api.nubloca.ro/imagini/";
        /*{"identificare": {"user": {"app_code": "abcdefghijkl123456"},
           "resursa": {"pentru": "tari", "tip": "steaguri", "nume": "147.png", "dimensiuni": [43]}}}*/

        //Accept:image/png
        String url = "http://api.nubloca.ro/imagini/";
        String numeSteag = standElem.getId() + ".png";

        JSONArray dimensiuni = new JSONArray().put(dim);
        JSONObject resursa = new JSONObject().put("pentru", "tari").put("tip", "steaguri").put("nume", numeSteag).put("dimensiuni", dimensiuni);

        GetRequestImg elem = new GetRequestImg();
        baite = elem.getRaspuns(Ecran1Activity.this, url, "image/png", resursa);
        standElem.setSteag(baite);
    }

    private void makePostRequest6() throws JSONException {
        //url1 = "http://api.nubloca.ro/imagini/";
        /*{            "identificare": {            "user": {                "app_code": "abcdefghijkl123456"            },
                        "resursa": {  "pentru": "tari",  "tip": "reprezentative",   "nume": "147.jpg" }}}*/
        //Content-Type:application/json
        //Accept:image/jpg
        String url = "http://api.nubloca.ro/imagini/";
        String numeSteag=standElem.getId()+".jpg";

        JSONObject resursa = new JSONObject().put("pentru", "tari").put("tip", "reprezentative").put("nume", numeSteag);

        GetRequestImg elem = new GetRequestImg();
        baite = elem.getRaspuns(Ecran1Activity.this, url, "image/jpeg", resursa);
        standElem.setImgReprezent(baite);
    }
    private void makePostRequest8() throws JSONException {
        //url4 = "http://api.nubloca.ro/imagini/";
         /*{        "identificare": {        "user": {            "app_code": "abcdefghijkl123456"        },
                    "resursa": {       "pentru": "numere",      "tip": "background",      "nume": "1.jpg" }}}*/
        //Content-Type:application/json
        //Accept:image/jpeg
        String url = "http://api.nubloca.ro/imagini/";
        JSONObject resursa = new JSONObject().put("pentru", "numere").put("tip", "background").put("nume", "1.jpg");
        GetRequestImg elemm = new GetRequestImg();
        baite = elemm.getRaspuns(Ecran1Activity.this, url, "image/jpeg", resursa);
        standElem.setBackgDemo(baite);
    }
    private void makePostRequest9() throws JSONException {
        //url4 = "http://api.nubloca.ro/imagini/";
         /*{	"identificare": {		"user": {			"app_code": "abcdefghijkl123456"		},
      	        "resursa": {          "pentru": "numere",          "tip": "tipuri",          "nume": "147-2.png"      	}   }}*/
        //Content-Type:application/json
        //Accept:image/png

        String url = "http://api.nubloca.ro/imagini/";
        JSONObject resursa = new JSONObject().put("pentru", "numere").put("tip", "tipuri").put("nume", "147-1.png");
        GetRequestImg elem = new GetRequestImg();
        baite = elem.getRaspuns(Ecran1Activity.this, url, "image/png", resursa);
        standElem.setPlateDemo(baite);
    }
}
