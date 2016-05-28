package ro.nubloca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class Ecran20Activity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    int campuri = 3;
    int id_tara = 147;
    String acc_lang, cont_lang;
    int[] array;
    String result;
    String url = "http://api.nubloca.ro/tipuri_inmatriculare/";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran20);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        acc_lang = (sharedpreferences.getString("acc_lang", "en"));
        cont_lang = (sharedpreferences.getString("cont_lang", "ro"));
        name_tip_inmatriculare = (sharedpreferences.getString("nume_tip_inmatriculare", "-"));
        campuri = (sharedpreferences.getInt("campuri", 3));
        ElemNumere = (sharedpreferences.getString("ElemNumere", "null"));
        ElemNumere1 = (sharedpreferences.getString("ElemNumere1", "null"));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        Spinner mySpinner = (Spinner) findViewById(R.id.my_spinner);

        TextView tip_inmatriculare_nume = (TextView) findViewById(R.id.nume_tip_inmatriculare);
        tip_inmatriculare_nume.setText(name_tip_inmatriculare);

        if ((ElemNumere.equals("null")) || (ElemNumere1.equals("null"))) {
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
        }

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
                    //finish();

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