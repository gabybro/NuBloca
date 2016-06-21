package ro.nubloca;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;


public class Ecran99Activity extends AppCompatActivity {
    public String acc_lang = "en";
    public String cont_lang = "ro";
    public int tip_inmat = 1;
    int id_tara = 147;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    EditText mEdit1, mEdit2, mEdit3, edit_id_tara;
    CheckBox check;
    boolean checkState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //Tos = (sharedpreferences.getBoolean("TOS", false));


        setContentView(R.layout.activity_ecran99);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        Button ok = (Button) findViewById(R.id.ok);
        Button clear = (Button) findViewById(R.id.button4);

        checkState = (sharedpreferences.getBoolean("http_req", false));
        acc_lang = (sharedpreferences.getString("acc_lang", "en"));
        cont_lang = (sharedpreferences.getString("cont_lang", "ro"));
        tip_inmat = (sharedpreferences.getInt("tip_inmat", 1));
        id_tara = (sharedpreferences.getInt("id_tara", 147));

        check = (CheckBox) findViewById(R.id.checkbox);
        mEdit1 = (EditText) findViewById(R.id.accept_lang_sel);
        mEdit2 = (EditText) findViewById(R.id.content_lang_sel);
        mEdit3 = (EditText) findViewById(R.id.tip_inmatriculare_sel);
        edit_id_tara = (EditText) findViewById(R.id.id_tara);


        mEdit1.setText(acc_lang);
        mEdit2.setText(cont_lang);
        mEdit3.setText(String.valueOf(tip_inmat));
        edit_id_tara.setText(String.valueOf(id_tara));


        check.setChecked(checkState);

        check.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                if (isChecked) {
                    editor.putBoolean("http_req", check.isChecked());
                    editor.apply();

                } else {
                    editor.putBoolean("http_req", false);
                    editor.apply();
                }

            }
        });
        if (ok != null) {
            ok.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {


                            acc_lang = mEdit1.getText().toString();
                            cont_lang = mEdit2.getText().toString();
                            tip_inmat = Integer.parseInt(mEdit3.getText().toString());
                            id_tara = Integer.parseInt(edit_id_tara.getText().toString());


                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("acc_lang", acc_lang);
                            editor.putString("cont_lang", cont_lang);
                            editor.putInt("tip_inmat", tip_inmat);
                            editor.putInt("id_tara", id_tara);
                            editor.apply();
                            finish();
                        }
                    });
        }
        if (clear != null) {
            clear.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {

                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.clear();
                            editor.commit();
                        }
                    });
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }
}
