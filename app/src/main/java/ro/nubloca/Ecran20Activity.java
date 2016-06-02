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
import android.widget.RelativeLayout;
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
    List<Response> response2;
    JSONArray valoareArr;
    String valoare;
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

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        /*acc_lang = (sharedpreferences.getString("acc_lang", "en"));
        cont_lang = (sharedpreferences.getString("cont_lang", "ro"));*/
        name_tip_inmatriculare = (sharedpreferences.getString("nume_tip_inmatriculare", "-"));
        id_tara = (sharedpreferences.getInt("id_tara", 147));
        id_shared = (sharedpreferences.getInt("id_shared", 0));
        //campuri = (sharedpreferences.getInt("campuri", 3));


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





           /*  filter = new InputFilter() {
                @Override
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    for (int i = start; i < end; ++i) {
                        if (!Pattern.compile("[1234567890]*").matcher(String.valueOf(source.charAt(i))).matches()) {
                            return "";
                        }
                    }

                    return null;
                }
            };*/

             /*filter1 = new InputFilter() {
                @Override
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    for (int i = start; i < end; ++i) {
                        if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*").matcher(String.valueOf(source.charAt(i))).matches()) {
                            return "";
                        }
                    }

                    return null;
                }
            };*/

 /*
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
                    //params.setMargins(convDp(valSPI), 0, 0, 0);
                    params.setMargins(valSPI, 0, 0, 0);
                }
                if ((i+1)<allelem.length){
                    if (allelem[i+1].getTip().equals("LISTA")) {params.setMargins(valSPI, 0, valSPI, 0);}
                }
                field.setLayoutParams(params);

                //field.setWidth(convDp(valRealUml * minTrei));
                field.setWidth(valRealUml * minTrei);
                //field.setMaxWidth(valRealUml * minTrei);
                field.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
                field.setTextSize(30);
                //field.setText(convDp(1080)+"");
                //field.setText(field.getWidth()+"");
                field.setBackgroundResource(R.drawable.plate_border);

                if (allelem[i].getTip().equals("CIFRE")) {
                    field.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                    field.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(allelem[i].getMaxlength())});
                }
                if (allelem[i].getTip().equals("LITERE")) {
                    field.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                    field.setFilters(new InputFilter[]{filter1, new InputFilter.LengthFilter(allelem[i].getMaxlength())});
                }
                //TextView f= (TextView)findViewById(100+i);
                //f.setText("sdsd");

                linearLayout.addView(field);
            }
        }


        /*if (campuri == 1) {

            if (allelem[0].getTip().equals("LISTA")) {

                Spinner mySpinner = (Spinner) findViewById(R.id.my_spinner);
                mySpinner
                        .setAdapter(new ArrayAdapter<String>(Ecran20Activity.this, R.layout.raw_list_1,
                                lista_cod));
                ViewGroup.LayoutParams params = mySpinner.getLayoutParams();

                if (allelem[0].getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = allelem[0].getMaxlength() * Math.min(valMaxUML, valUML);
                }
                mySpinner.setLayoutParams(params);
                mySpinner.setVisibility(View.VISIBLE);
            } else {
                TextView field1 = (TextView) findViewById(R.id.plate1);
                field1.setVisibility(View.VISIBLE);
                field1.setText("");

                ViewGroup.LayoutParams params = field1.getLayoutParams();

                if (allelem[0].getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = allelem[0].getMaxlength() * Math.min(valMaxUML, valUML);
                }

                field1.setLayoutParams(params);
            }

        }
        if (campuri == 2) {

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

            if (allelem[0].getTip().equals("LISTA")) {

                Spinner mySpinner = (Spinner) findViewById(R.id.my_spinner);
                mySpinner
                        .setAdapter(new ArrayAdapter<String>(Ecran20Activity.this, R.layout.raw_list_1,
                                lista_cod));
                ViewGroup.LayoutParams params = mySpinner.getLayoutParams();

                if (allelem[0].getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = allelem[0].getMaxlength() * Math.min(valMaxUML, valUML);
                }

                mySpinner.setLayoutParams(params);
                mySpinner.setVisibility(View.VISIBLE);
            } else if ((allelem[0].getTip().equals("CIFRE")) || (allelem[0].getTip().equals("LITERE"))) {
                TextView field1 = (TextView) findViewById(R.id.plate1);

                if (allelem[0].getTip().equals("CIFRE")) {
                    field1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                    field1.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(allelem[0].getMaxlength())});
                }
                if (allelem[0].getTip().equals("LITERE")) {
                    field1.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                    field1.setFilters(new InputFilter[]{filter1, new InputFilter.LengthFilter(allelem[0].getMaxlength())});
                }

                field1.setVisibility(View.VISIBLE);
                //field1.setText("");

                ViewGroup.LayoutParams params = field1.getLayoutParams();

                if (allelem[0].getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = allelem[0].getMaxlength() * Math.min(valMaxUML, valUML);
                }

                field1.setLayoutParams(params);
            }
            if (allelem[1].getTip().equals("LISTA")) {

                Spinner mySpinner = (Spinner) findViewById(R.id.my_spinner);
                mySpinner
                        .setAdapter(new ArrayAdapter<String>(Ecran20Activity.this, R.layout.raw_list_1,
                                lista_cod));
                ViewGroup.LayoutParams params = mySpinner.getLayoutParams();

                if (allelem[1].getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = allelem[1].getMaxlength() * Math.min(valMaxUML, valUML);
                }

                mySpinner.setLayoutParams(params);
                mySpinner.setVisibility(View.VISIBLE);
            } else if ((allelem[1].getTip().equals("CIFRE")) || (allelem[1].getTip().equals("LITERE"))) {
                TextView field2 = (TextView) findViewById(R.id.plate2);
                if (allelem[1].getTip().equals("CIFRE")) {
                    field2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                    field2.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(allelem[1].getMaxlength())});
                }
                if (allelem[1].getTip().equals("LITERE")) {
                    field2.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                    field2.setFilters(new InputFilter[]{filter1, new InputFilter.LengthFilter(allelem[1].getMaxlength())});
                }

                field2.setVisibility(View.VISIBLE);
                //field2.setText("");

                ViewGroup.LayoutParams params = field2.getLayoutParams();

                if (allelem[1].getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = allelem[1].getMaxlength() * Math.min(valMaxUML, valUML);
                }

                field2.setLayoutParams(params);
            }
        }
        if (campuri == 3) {
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

            if (allelem[0].getTip().equals("LISTA")) {

                Spinner mySpinner = (Spinner) findViewById(R.id.my_spinner);
                mySpinner
                        .setAdapter(new ArrayAdapter<String>(Ecran20Activity.this, R.layout.raw_list_1,
                                lista_cod));
                ViewGroup.LayoutParams params = mySpinner.getLayoutParams();

                if (allelem[0].getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = allelem[0].getMaxlength() * Math.min(valMaxUML, valUML);
                }

                mySpinner.setLayoutParams(params);
                mySpinner.setVisibility(View.VISIBLE);
            } else if ((allelem[0].getTip().equals("CIFRE")) || (allelem[0].getTip().equals("LITERE"))) {
                TextView field1 = (TextView) findViewById(R.id.plate1);

                if (allelem[0].getTip().equals("CIFRE")) {
                    field1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                    field1.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(allelem[0].getMaxlength())});
                }
                if (allelem[0].getTip().equals("LITERE")) {
                    field1.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                    field1.setFilters(new InputFilter[]{filter1, new InputFilter.LengthFilter(allelem[0].getMaxlength())});
                }

                field1.setVisibility(View.VISIBLE);
                //field1.setText("");

                ViewGroup.LayoutParams params = field1.getLayoutParams();

                if (allelem[0].getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = allelem[0].getMaxlength() * Math.min(valMaxUML, valUML);
                }

                field1.setLayoutParams(params);
            }
            if (allelem[1].getTip().equals("LISTA")) {

                Spinner mySpinner = (Spinner) findViewById(R.id.my_spinner);
                mySpinner
                        .setAdapter(new ArrayAdapter<String>(Ecran20Activity.this, R.layout.raw_list_1,
                                lista_cod));
                ViewGroup.LayoutParams params = mySpinner.getLayoutParams();

                if (allelem[1].getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = allelem[1].getMaxlength() * Math.min(valMaxUML, valUML);
                }
                mySpinner.setLayoutParams(params);
                mySpinner.setVisibility(View.VISIBLE);
            } else if ((allelem[1].getTip().equals("CIFRE")) || (allelem[1].getTip().equals("LITERE"))) {
                TextView field2 = (TextView) findViewById(R.id.plate2);

                if (allelem[1].getTip().equals("CIFRE")) {
                    field2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                    field2.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(allelem[1].getMaxlength())});
                }
                if (allelem[1].getTip().equals("LITERE")) {
                    field2.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                    field2.setFilters(new InputFilter[]{filter1, new InputFilter.LengthFilter(allelem[1].getMaxlength())});
                }

               // field2.setText("");
                field2.setVisibility(View.VISIBLE);

                ViewGroup.LayoutParams params = field2.getLayoutParams();
                if (allelem[1].getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = allelem[1].getMaxlength() * Math.min(valMaxUML, valUML);
                }
                field2.setLayoutParams(params);
            }
            String xdf = allelem[2].getTip();
            String dfg = xdf;
            if (allelem[2].getTip().equals("LISTA")) {

                Spinner mySpinner = (Spinner) findViewById(R.id.my_spinner);
                mySpinner
                        .setAdapter(new ArrayAdapter<String>(Ecran20Activity.this, R.layout.raw_list_1,
                                lista_cod));
                ViewGroup.LayoutParams params = mySpinner.getLayoutParams();

                if (allelem[2].getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = allelem[2].getMaxlength() * Math.min(valMaxUML, valUML);
                }

                mySpinner.setLayoutParams(params);
                mySpinner.setVisibility(View.VISIBLE);
            } else if ((allelem[2].getTip().equals("CIFRE")) || (allelem[2].getTip().equals("LITERE"))) {
                EditText field3 = (EditText) findViewById(R.id.plate3);
                if (allelem[2].getTip().equals("CIFRE")) {
                    field3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                    field3.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(allelem[2].getMaxlength())});
                }
                if (allelem[2].getTip().equals("LITERE")) {
                    field3.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                    field3.setFilters(new InputFilter[]{filter1, new InputFilter.LengthFilter(allelem[2].getMaxlength())});
                }
                field3.setVisibility(View.VISIBLE);

                ViewGroup.LayoutParams params = field3.getLayoutParams();

                if (allelem[2].getMaxlength() < 3) {
                    params.width = 3 * Math.min(valMaxUML, valUML);
                } else {
                    params.width = allelem[2].getMaxlength() * Math.min(valMaxUML, valUML);
                }

                field3.setLayoutParams(params);
            }
        }
        if (campuri == 4) {

        }
        if (campuri == 5) {

        }*/

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


        id_shared = (sharedpreferences.getInt("id_shared", 0));
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
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("name_tip_inmatriculare", name_tip_inmatriculare);
        editor.commit();
    }

    private void makePostRequest1() throws JSONException {
        /*{
            "identificare": {
                "user": {"app_code": "abcdefghijkl123456"},
                "resursa": {"id": [1,2,3]}},
            "cerute":["id","id_tip_element","ordinea"]
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
        }
         /*[{
            "id": 1,
                "id_tip_element": 1,
                "ordinea": 1
        },{
            "id": 2,
                "id_tip_element": 2,
                "ordinea": 2
        },{
            "id": 3,
                "id_tip_element": 3,
                "ordinea": 3
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

        int fgh = allelem.length;
        int xxx = fgh;
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

        //test = response.get(0).getTip();

       /* if (id_tip_element.length>response2.size()){
            int suma =0;
            int zuma=0;
            int dif = id_tip_element.length-response2.size();
            for(int i=0; i <id_tip_element.length;i++){
                suma= suma + id_tip_element[i];
            }
            for(int i=0; i <response2.size();i++){
                zuma= zuma + response2.get(i).getId();
            }
            int elem_lipsa=0;
            elem_lipsa=(suma-zuma)/dif;
            Response x= new Response();
            for(int i=0; i<response2.size(); i++){
                if (elem_lipsa==response2.get(i).getId()){
                    x = response2.get(i);

                }
            }
            response2.add(response2.size(), x);

        }
        int qq = id_tip_element.length;
        int ww = response2.size();
        int ff = ww;

        for (int i = 0; i < response2.size(); i++) {
            int yy = response2.get(i).getMaxlength();
            int vv = yy;
            String s = new JSONArray(result_string).getJSONObject(i).getString("valori");
            if (response2.get(i).getTip().equals("LISTA")) {
                valoareArr = new JSONArray(s);
                lista_cod = new String[valoareArr.length()];
                for (int j = 0; j < valoareArr.length(); j++) {
                    JSONObject elem = valoareArr.getJSONObject(j);
                    lista_cod[j] = elem.getString("cod");
                }
            } else {
                valoare = s;
            }


        }*/


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

    int convDp(float sizeInDp) {
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (sizeInDp * scale + 0.5f);
        return dpAsPixels;
    }

}