package ro.nubloca;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
        if (vezi != null)
            vezi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Ecran22Activity.this, Ecran27Activity.class));
                }
            });

        LinearLayout relbar = (LinearLayout) findViewById(R.id.rel_bar1);
        if (relbar != null)
            relbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageView image = (ImageView) findViewById(R.id.chevron1);
                    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                    image.startAnimation(animation1);
                    RadioButton btn1 = (RadioButton) findViewById(R.id.radioButton2);
                    if (btn1.isChecked()) {
                        btn1.setChecked(false);
                    } else {
                        btn1.setChecked(true);
                    }

                }
            });


        TextView prop = (TextView) findViewById(R.id.propune_mesaj);
        if (prop != null)
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Ecran22Activity.this, Ecran20Activity.class));
        finish();
        super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();
                Ecran22Activity.this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
