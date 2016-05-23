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
import android.widget.EditText;
import android.widget.LinearLayout;
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
    String ElemNumere;
    int id0, id1, id2;
    String tip0, tip1, tip2;
    int maxlength0, maxlength1, maxlength2;
    EditText img0, img1, img2;


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


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        //Intent myIntent = getIntent();
        //nume_tip_inmatriculare = myIntent.getStringExtra("nume_tip_inmatriculare");
        //Bundle extras = getIntent().getExtras();
        //array = extras.getIntArray("array");


        /*Context context = getApplicationContext();
        CharSequence text = "request done!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, ElemNumere, duration);
        toast.show();*/


        TextView tip_inmatriculare_nume = (TextView) findViewById(R.id.nume_tip_inmatriculare);
        tip_inmatriculare_nume.setText(name_tip_inmatriculare);

        JSONArray plate = new JSONArray();
        try {
            plate = new JSONArray(ElemNumere);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONObject obj0, obj1, obj2;
        img2 = (EditText) findViewById(R.id.plate3);

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
            id0 = plate.getJSONObject(0).getInt("id");
            tip0 = plate.getJSONObject(0).getString("tip");
            maxlength0 = plate.getJSONObject(0).getInt("maxlength");
            img0 = (EditText) findViewById(R.id.plate1);
            if (tip0.equals("CIFRE")) {
                img0.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                img0.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(maxlength0)});
            } else if (tip0.equals("LITERE")) {
                img0.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                img0.setFilters(new InputFilter[]{filter1, new InputFilter.LengthFilter(maxlength0)});
            } else img0.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxlength0)});
            img0.setText("");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            id1 = plate.getJSONObject(1).getInt("id");
            tip1 = plate.getJSONObject(1).getString("tip");
            maxlength1 = plate.getJSONObject(1).getInt("maxlength");
            img1 = (EditText) findViewById(R.id.plate2);

            if (tip1.equals("CIFRE")) {
                img1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                img1.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(maxlength1)});
            } else if (tip1.equals("LITERE")) {
                img1.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                img1.setFilters(new InputFilter[]{filter1, new InputFilter.LengthFilter(maxlength1)});
            } else {
                img1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxlength1)});
            }

            img1.setText("");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (campuri < 3) {

            img2.setVisibility(View.GONE);


        } else {
            try {
                id2 = plate.getJSONObject(2).getInt("id");
                tip2 = plate.getJSONObject(2).getString("tip");
                maxlength2 = plate.getJSONObject(2).getInt("maxlength");
                img2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxlength2)});
                if (tip2.equals("CIFRE")) {
                    img2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                    img2.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(maxlength1)});
                } else if (tip2.equals("LITERE")) {
                    img2.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                    img2.setFilters(new InputFilter[]{filter1, new InputFilter.LengthFilter(maxlength1)});
                } else
                    img2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxlength1)});
                img2.setText("");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //View plate3 = (View)findViewById(R.id.plate3);
        //if(campuri==2) {
        //   plate3.setVisibility(View.GONE);
        //}

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


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu5, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu1) {
            Context context = getApplicationContext();
            CharSequence text = "request done!";
            text = img0.getText().toString().toUpperCase()+" "+img1.getText().toString()+" "+img2.getText().toString();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}