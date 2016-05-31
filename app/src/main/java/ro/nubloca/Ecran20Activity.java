package ro.nubloca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.Display;
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
import java.util.List;
import java.util.regex.Pattern;

import ro.nubloca.Networking.GetRequest;
import ro.nubloca.Networking.Response;

public class Ecran20Activity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    int campuri = 3;
    int id_tara = 147;
    int order = 1;
    String acc_lang, cont_lang;
    int[] array;
    String result;
    String url = "http://api.nubloca.ro/tipuri_inmatriculare/";
    String url1 = "http://api.nubloca.ro/tipuri_inmatriculare_tipuri_elemente/";
    String url2 = "http://api.nubloca.ro/tipuri_elemente/";

    String name_tip_inmatriculare;
    String ElemNumere, ElemNumere1;
    int id0, id1, id2;
    String tip0, tip1, tip2;
    int editabil0, editabil1, editabil2;
    int maxlength0, maxlength1, maxlength2;
    JSONArray valori0, valori1, valori2;
    int ordinea0, ordinea1, ordinea2;
    EditText img0, img1, img2;
    JSONArray plate, plate1;
    String plate_text1, plate_text2, plate_text3;
    int[] ids_tipuri_inmatriculare_tipuri_elemente;
    int[] id_tip_element;
    String test;
    int idd;
    int id_shared = 0;
    List<Response> response3;
    List<Response>[] response2;
    JSONArray valoareArr;
    String valoare;
    String[] lista_cod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran20);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        /*acc_lang = (sharedpreferences.getString("acc_lang", "en"));
        cont_lang = (sharedpreferences.getString("cont_lang", "ro"));*/
        name_tip_inmatriculare = (sharedpreferences.getString("nume_tip_inmatriculare", "-"));
        id_tara = (sharedpreferences.getInt("id_tara", 147));
        id_shared = (sharedpreferences.getInt("id_shared", 0));
        //campuri = (sharedpreferences.getInt("campuri", 3));

        //ElemNumere = (sharedpreferences.getString("ElemNumere", "null"));
        //ElemNumere1 = (sharedpreferences.getString("ElemNumere1", "null"));


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


        /*if ((ElemNumere.equals("null")) || (ElemNumere1.equals("null"))) {
        } else {
            plate = new JSONArray();


            try {
                plate = new JSONArray(orderSort());
            } catch (JSONException e) {
                e.printStackTrace();
            }


            InputFilter filter = new InputFilter() {
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

            InputFilter filter1 = new InputFilter() {
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


            try {
                img0 = (EditText) findViewById(R.id.plate1);
                id0 = plate.getJSONObject(0).getInt("id");
                tip0 = plate.getJSONObject(0).getString("tip");
                editabil0 = plate.getJSONObject(0).getInt("editabil_user");
                maxlength0 = plate.getJSONObject(0).getInt("maxlength");


            } catch (JSONException e) {
                e.printStackTrace();
            }

            // img0.setText("");
            // img0.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxlength0)});
            if (tip0.equals("CIFRE")) {
                try {
                    String valori0 = plate.getJSONObject(0).getString("valori");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                img0.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                img0.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(maxlength0)});
            } else if (tip0.equals("LITERE")) {
                try {
                    String valori0 = plate.getJSONObject(0).getString("valori");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                img0.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                img0.setFilters(new InputFilter[]{filter1, new InputFilter.LengthFilter(maxlength0)});
            } else if (tip0.equals("LISTA")) {
                try {
                    valori0 = plate.getJSONObject(0).getJSONArray("valori");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String[] cod_valori = new String[valori0.length()];
                for (int i = 0; i < valori0.length(); i++) {
                    try {
                        cod_valori[i] = valori0.getJSONObject(i).getString("cod");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                img0.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxlength0)});
                mySpinner
                        .setAdapter(new ArrayAdapter<String>(Ecran20Activity.this, R.layout.raw_list_1,
                                cod_valori));
                img0.setVisibility(View.GONE);
                mySpinner.setVisibility(View.VISIBLE);

            }


            try {

                id1 = plate.getJSONObject(1).getInt("id");
                tip1 = plate.getJSONObject(1).getString("tip");
                editabil1 = plate.getJSONObject(1).getInt("editabil_user");
                maxlength1 = plate.getJSONObject(1).getInt("maxlength");

            } catch (JSONException e) {
                e.printStackTrace();
            }


            img1 = (EditText) findViewById(R.id.plate2);
            img1.setText("");

            img1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxlength1)});

            if (tip1.equals("CIFRE")) {
                try {
                    String valori1 = plate.getJSONObject(1).getString("valori");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                img1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                img1.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(maxlength1)});
            } else if (tip1.equals("LITERE")) {
                try {
                    String valori1 = plate.getJSONObject(1).getString("valori");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                img1.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                img1.setFilters(new InputFilter[]{filter1, new InputFilter.LengthFilter(maxlength1)});
            } else if (tip1.equals("LISTA")) {
                try {
                    valori1 = plate.getJSONObject(1).getJSONArray("valori");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String[] cod_valori = new String[valori1.length()];
                for (int i = 0; i < valori1.length(); i++) {
                    try {
                        cod_valori[i] = valori1.getJSONObject(i).getString("cod");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                img1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxlength1)});
                mySpinner
                        .setAdapter(new ArrayAdapter<String>(Ecran20Activity.this,
                                R.layout.raw_list_1,
                                cod_valori));
                img1.setVisibility(View.GONE);
                mySpinner.setVisibility(View.VISIBLE);
            }

            EditText img2 = (EditText) findViewById(R.id.plate3);
            if (campuri < 3) {


                img2.setVisibility(View.GONE);


            } else {
                img2.setText("", TextView.BufferType.EDITABLE);
                try {
                    id2 = plate.getJSONObject(2).getInt("id");
                    tip2 = plate.getJSONObject(2).getString("tip");
                    editabil2 = plate.getJSONObject(2).getInt("editabil_user");
                    maxlength2 = plate.getJSONObject(2).getInt("maxlength");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                img2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxlength2)});
                img2.setText("");
                if (tip2.equals("CIFRE")) {
                    try {
                        String valori2 = plate.getJSONObject(2).getString("valori");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    img2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                    img2.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(maxlength2)});
                } else if (tip2.equals("LITERE")) {
                    try {
                        String valori2 = plate.getJSONObject(2).getString("valori");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    img2.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                    img2.setFilters(new InputFilter[]{filter1, new InputFilter.LengthFilter(maxlength2)});
                } else if (tip2.equals("LISTA")) {
                    try {
                        valori2 = plate.getJSONObject(2).getJSONArray("valori");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String[] cod_valori = new String[valori2.length()];
                    for (int i = 0; i < valori2.length(); i++) {
                        try {
                            cod_valori[i] = valori2.getJSONObject(i).getString("cod");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    img2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxlength2)});
                    mySpinner
                            .setAdapter(new ArrayAdapter<String>(Ecran20Activity.this,
                                    R.layout.raw_list_1,
                                    cod_valori));

                    img2.setVisibility(View.GONE);
                    mySpinner.setVisibility(View.VISIBLE);
                }
            }
        }*/

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
                    //makePostRequestOnNewThread();
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
            if (response2[i].get(0).getMaxlength() < 3) {
                nrUML += 3;
            } else {
                int xx = response2[i].get(0).getMaxlength();
                nrUML += response2[i].get(0).getMaxlength();
            }

        }
        valSPI = divLength / (nrUML * 6 + 3 * nrSPL + nrSPI);
        valSPL = 3 * valSPI;
        valUML = 6 * valSPI;


        if (campuri == 1) {

            if (response2[0].get(0).getTip().equals("LISTA")) {

                Spinner mySpinner = (Spinner) findViewById(R.id.my_spinner);
                mySpinner
                        .setAdapter(new ArrayAdapter<String>(Ecran20Activity.this, R.layout.raw_list_1,
                                lista_cod));
                ViewGroup.LayoutParams params = mySpinner.getLayoutParams();

                if (response2[0].get(0).getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = response2[0].get(0).getMaxlength() * Math.min(valMaxUML, valUML);
                }
                mySpinner.setLayoutParams(params);
                mySpinner.setVisibility(View.VISIBLE);
            } else {
                TextView field1 = (TextView) findViewById(R.id.plate1);
                field1.setVisibility(View.VISIBLE);
                field1.setText("");

                ViewGroup.LayoutParams params = field1.getLayoutParams();

                if (response2[0].get(0).getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = response2[0].get(0).getMaxlength() * Math.min(valMaxUML, valUML);
                }

                field1.setLayoutParams(params);
            }

        }
        if (campuri == 2) {
            if (response2[0].get(0).getTip().equals("LISTA")) {

                Spinner mySpinner = (Spinner) findViewById(R.id.my_spinner);
                mySpinner
                        .setAdapter(new ArrayAdapter<String>(Ecran20Activity.this, R.layout.raw_list_1,
                                lista_cod));
                ViewGroup.LayoutParams params = mySpinner.getLayoutParams();

                if (response2[0].get(0).getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = response2[0].get(0).getMaxlength() * Math.min(valMaxUML, valUML);
                }

                mySpinner.setLayoutParams(params);
                mySpinner.setVisibility(View.VISIBLE);
            } else if ((response2[0].get(0).getTip().equals("CIFRE")) || (response2[0].get(0).getTip().equals("LITERE"))) {
                TextView field1 = (TextView) findViewById(R.id.plate1);
                field1.setVisibility(View.VISIBLE);
                field1.setText("");

                ViewGroup.LayoutParams params = field1.getLayoutParams();

                if (response2[0].get(0).getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = response2[0].get(0).getMaxlength() * Math.min(valMaxUML, valUML);
                }

                field1.setLayoutParams(params);
            }
            if (response2[1].get(0).getTip().equals("LISTA")) {

                Spinner mySpinner = (Spinner) findViewById(R.id.my_spinner);
                mySpinner
                        .setAdapter(new ArrayAdapter<String>(Ecran20Activity.this, R.layout.raw_list_1,
                                lista_cod));
                ViewGroup.LayoutParams params = mySpinner.getLayoutParams();

                if (response2[1].get(0).getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = response2[1].get(0).getMaxlength() * Math.min(valMaxUML, valUML);
                }

                mySpinner.setLayoutParams(params);
                mySpinner.setVisibility(View.VISIBLE);
            } else if ((response2[1].get(0).getTip().equals("CIFRE")) || (response2[1].get(0).getTip().equals("LITERE"))) {
                TextView field2 = (TextView) findViewById(R.id.plate2);
                field2.setVisibility(View.VISIBLE);
                field2.setText("");

                ViewGroup.LayoutParams params = field2.getLayoutParams();

                if (response2[1].get(0).getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = response2[1].get(0).getMaxlength() * Math.min(valMaxUML, valUML);
                }

                field2.setLayoutParams(params);
            }
        }
        if (campuri == 3) {
            if (response2[0].get(0).getTip().equals("LISTA")) {

                Spinner mySpinner = (Spinner) findViewById(R.id.my_spinner);
                mySpinner
                        .setAdapter(new ArrayAdapter<String>(Ecran20Activity.this, R.layout.raw_list_1,
                                lista_cod));
                ViewGroup.LayoutParams params = mySpinner.getLayoutParams();

                if (response2[0].get(0).getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = response2[0].get(0).getMaxlength() * Math.min(valMaxUML, valUML);
                }

                mySpinner.setLayoutParams(params);
                mySpinner.setVisibility(View.VISIBLE);
            } else if ((response2[0].get(0).getTip().equals("CIFRE")) || (response2[0].get(0).getTip().equals("LITERE"))) {
                TextView field1 = (TextView) findViewById(R.id.plate1);
                field1.setVisibility(View.VISIBLE);
                field1.setText("");

                ViewGroup.LayoutParams params = field1.getLayoutParams();

                if (response2[0].get(0).getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = response2[0].get(0).getMaxlength() * Math.min(valMaxUML, valUML);
                }

                field1.setLayoutParams(params);
            }
            if (response2[1].get(0).getTip().equals("LISTA")) {

                Spinner mySpinner = (Spinner) findViewById(R.id.my_spinner);
                mySpinner
                        .setAdapter(new ArrayAdapter<String>(Ecran20Activity.this, R.layout.raw_list_1,
                                lista_cod));
                ViewGroup.LayoutParams params = mySpinner.getLayoutParams();

                if (response2[1].get(0).getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = response2[1].get(0).getMaxlength() * Math.min(valMaxUML, valUML);
                }
                mySpinner.setLayoutParams(params);
                mySpinner.setVisibility(View.VISIBLE);
            } else if ((response2[1].get(0).getTip().equals("CIFRE")) || (response2[1].get(0).getTip().equals("LITERE"))) {
                TextView field2 = (TextView) findViewById(R.id.plate2);
                field2.setText("");
                field2.setVisibility(View.VISIBLE);

                ViewGroup.LayoutParams params = field2.getLayoutParams();
                if (response2[1].get(0).getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = response2[1].get(0).getMaxlength() * Math.min(valMaxUML, valUML);
                }
                field2.setLayoutParams(params);
            }
            if (response2[2].get(0).getTip().equals("LISTA")) {

                Spinner mySpinner = (Spinner) findViewById(R.id.my_spinner);
                mySpinner
                        .setAdapter(new ArrayAdapter<String>(Ecran20Activity.this, R.layout.raw_list_1,
                                lista_cod));
                ViewGroup.LayoutParams params = mySpinner.getLayoutParams();

                if (response2[2].get(0).getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = response2[2].get(0).getMaxlength() * Math.min(valMaxUML, valUML);
                }

                mySpinner.setLayoutParams(params);
                mySpinner.setVisibility(View.VISIBLE);
            } else if ((response2[2].get(0).getTip().equals("CIFRE")) || (response2[2].get(0).getTip().equals("LITERE"))) {
                TextView field3 = (TextView) findViewById(R.id.plate3);
                field3.setText("");
                field3.setVisibility(View.VISIBLE);

                ViewGroup.LayoutParams params = field3.getLayoutParams();

                if (response2[2].get(0).getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = response2[2].get(0).getMaxlength() * Math.min(valMaxUML, valUML);
                }

                field3.setLayoutParams(params);
            }
        }
        if (campuri == 4) {

        }
        if (campuri == 5) {

        }

            /*Toast toast = Toast.makeText(this, campuri+"",Toast.LENGTH_LONG);
            toast.show();*/

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
        Toast toast = Toast.makeText(this, test, Toast.LENGTH_LONG);
        toast.show();
    }

    private void makePostRequest0() throws JSONException {

        /*[
        {
            "id": 1
        }
        ]*/

        GetRequest elemm = new GetRequest();

        JSONArray cerute = new JSONArray().put("id");
        JSONArray idTara = new JSONArray().put(id_tara);
        JSONArray ordinea = new JSONArray().put(order);
        JSONObject resursa = new JSONObject();
        resursa.put("id_tara", idTara);
        resursa.put("ordinea", ordinea);


        String result_string = elemm.getRaspuns(Ecran20Activity.this, url, resursa, cerute);

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

        /*[
        {
            "id": 2,
                "nume": "Temporară",
                "ids_tipuri_inmatriculare_tipuri_elemente":[4,5]
        }
        ]*/

        id_shared = (sharedpreferences.getInt("id_shared", 0));

        GetRequest elemm = new GetRequest();

        JSONArray cerute = new JSONArray().put("id").put("nume").put("ids_tipuri_inmatriculare_tipuri_elemente");
        JSONArray idTara = new JSONArray().put(id_shared);
        JSONObject resursa = new JSONObject();
        resursa.put("id", idTara);

        String result_string = elemm.getRaspuns(Ecran20Activity.this, url, resursa, cerute);

        Gson gson = new Gson();
        Type listeType = new TypeToken<List<Response>>() {
        }.getType();
        List<Response> response = (List<Response>) gson.fromJson(result_string, listeType);
        name_tip_inmatriculare = response.get(0).getNume();
        ids_tipuri_inmatriculare_tipuri_elemente = response.get(0).getIds_tipuri_inmatriculare_tipuri_elemente();
        campuri = ids_tipuri_inmatriculare_tipuri_elemente.length;
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("name_tip_inmatriculare", name_tip_inmatriculare);
        editor.commit();
    }

    private void makePostRequest1() throws JSONException {

        /*[
        {
            "id": 1,
            "id_tip_element": 1,
            "ordinea": 1
        }
        ]*/

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
        //Todo orinea
        //ordinea

        id_tip_element = new int[response.size()];
        for (int i = 0; i < response.size(); i++) {
            int j = response.get(i).getId_tip_element();
            id_tip_element[i] = j;
        }

    }

    private void makePostRequest2() throws JSONException {

        /*[
        {
            "id": 1,
                "tip": "LISTA",
                "editabil_user": 1,
                "maxlength": 2,
                "valori":[{"id": 2161, "cod": "AB" }, {"id": 2163, "cod": "AG"…]
        },
            {
                "id": 2,
                    "tip": "CIFRE",
                    "editabil_user": 1,
                    "maxlength": 3,
                    "valori": "^[0-9]{2,3}$"
            }
            ]*/

        GetRequest elemm = new GetRequest();

        JSONArray cerute = new JSONArray().put("id").put("tip").put("editabil_user").put("maxlength").put("valori");
        JSONObject resursa = new JSONObject();
        String result_string=null;
        Gson gson = new Gson();
        Type listeType = new TypeToken<List<Response>>() {
        }.getType();
        for (int i = 0; i < id_tip_element.length; i++) {
            JSONArray id = new JSONArray();
            id.put(id_tip_element[i]);
            resursa.put("id", id);
            result_string = elemm.getRaspuns(Ecran20Activity.this, url2, resursa, cerute);
            response3 = (List<Response>) gson.fromJson(result_string, listeType);
            response2[i]= response3;
        }





        //test = response.get(0).getTip();
        for (int i = 0; i < response2.length; i++) {
            int yy = response2[i].get(0).getMaxlength();
            int vv = yy;
            String s = new JSONArray(result_string).getJSONObject(0).getString("valori");
            if (response2[i].get(0).getTip().equals("LISTA")) {
                valoareArr = new JSONArray(s);
                lista_cod = new String[valoareArr.length()];
                for (int j = 0; j < valoareArr.length(); j++) {
                    JSONObject elem = valoareArr.getJSONObject(j);
                    lista_cod[j] = elem.getString("cod");
                }
            } else {
                valoare = s;
            }


        }


        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("id_shared", 0);
        editor.commit();

    }

    private String orderSort() {

        JSONArray blate = null;
        JSONArray blate1 = null;
        try {
            blate = new JSONArray(ElemNumere);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            blate1 = new JSONArray(ElemNumere1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray rez = null;
        try {
            rez = new JSONArray(ElemNumere1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (ElemNumere.equals("null") || ElemNumere1.equals("null")) {
            Toast toast = Toast.makeText(this, "asd", Toast.LENGTH_SHORT);
            toast.show();
        } else {

            for (int i = 0; i < blate1.length(); i++) {

                for (int j = 0; j < blate1.length(); j++) {
                    try {
                        if ((blate1.getJSONObject(i).getInt("id")) == (blate.getJSONObject(j).getInt("id"))) {
                            //rez.getJSONObject(i).put("ordinea", 1);
                            rez.getJSONObject(i).put("ordinea", blate.getJSONObject(j).getInt("ordinea"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        JSONArray rezz = new JSONArray();
        if (rez.length() == 2) {
            try {
                if (rez.getJSONObject(0).getInt("ordinea") < rez.getJSONObject(1).getInt("ordinea")) {
                    rezz = rez;
                } else {
                    rezz.put(rez.getJSONObject(1));
                    rezz.put(rez.getJSONObject(0));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (rez.length() == 3) {
            try {
                if ((rez.getJSONObject(0).getInt("ordinea") < rez.getJSONObject(1).getInt("ordinea")) && (rez.getJSONObject(0).getInt("ordinea") < rez.getJSONObject(2).getInt("ordinea"))) {
                    rezz.put(rez.getJSONObject(0));
                    if (rez.getJSONObject(1).getInt("ordinea") < rez.getJSONObject(2).getInt("ordinea")) {
                        rezz.put(rez.getJSONObject(1));
                        rezz.put(rez.getJSONObject(2));
                    } else {
                        rezz.put(rez.getJSONObject(2));
                        rezz.put(rez.getJSONObject(1));
                    }
                } else if ((rez.getJSONObject(0).getInt("ordinea") > rez.getJSONObject(1).getInt("ordinea")) && (rez.getJSONObject(0).getInt("ordinea") < rez.getJSONObject(2).getInt("ordinea"))) {
                    rezz.put(rez.getJSONObject(1));
                    rezz.put(rez.getJSONObject(0));
                    rezz.put(rez.getJSONObject(2));
                } else if ((rez.getJSONObject(0).getInt("ordinea") < rez.getJSONObject(1).getInt("ordinea")) && (rez.getJSONObject(0).getInt("ordinea") > rez.getJSONObject(2).getInt("ordinea"))) {
                    rezz.put(rez.getJSONObject(2));
                    rezz.put(rez.getJSONObject(0));
                    rezz.put(rez.getJSONObject(1));
                } else if (rez.getJSONObject(2).getInt("ordinea") < rez.getJSONObject(1).getInt("ordinea")) {
                    rezz.put(rez.getJSONObject(2));
                    rezz.put(rez.getJSONObject(1));
                    rezz.put(rez.getJSONObject(0));
                } else {
                    rezz.put(rez.getJSONObject(1));
                    rezz.put(rez.getJSONObject(2));
                    rezz.put(rez.getJSONObject(0));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /*Toast toast = Toast.makeText(this, rezz.toString(), Toast.LENGTH_LONG);
        toast.show();*/
        return rezz.toString();
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
            //EditText img0, img1, img2;

            /*if (campuri==2){
            text = img0.getText().toString().toUpperCase() + " " + img1.getText().toString();}
            else {
            text = img0.getText().toString().toUpperCase() + " " + img1.getText().toString() + " " + img2.getText().toString();}*/
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            //finish();
        }

        return super.onOptionsItemSelected(item);
    }

}