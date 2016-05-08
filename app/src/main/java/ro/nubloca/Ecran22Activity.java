package ro.nubloca;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Ecran22Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran22);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LinearLayout vezi = (LinearLayout) findViewById(R.id.linear1);
        vezi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Ecran22Activity.this, Ecran27Activity.class));
            }
        });

        LinearLayout relbar = (LinearLayout) findViewById(R.id.rel_bar1);
        relbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton btn1 = (RadioButton) findViewById(R.id.radioButton2);
                if (btn1.isChecked()) {
                    btn1.setChecked(false);
                }else{
                    btn1.setChecked(true);
                }

            }
        });


        TextView prop = (TextView) findViewById(R.id.propune_mesaj);
        prop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Ecran22Activity.this, Ecran28Activity.class));
            }
        });
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu1, menu);
        /*View button = (View) this.findViewById(R.id.toolbar_title);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/

        return true;
    }
}
