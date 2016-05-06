package ro.nubloca;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Ecran9Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran9);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView ajutor_btn = (TextView) this.findViewById(R.id.textView91);
        ajutor_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ecran9Activity.this, Ecran13Activity.class));

            }
        });

        RelativeLayout btn1 = (RelativeLayout) this.findViewById(R.id.rel_bar1);

        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ecran9Activity.this, Ecran11Activity.class));

            }
        });
        RelativeLayout btn2 = (RelativeLayout) this.findViewById(R.id.rel_bar2);

        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ecran9Activity.this, Ecran10Activity.class));

            }
        });

        RelativeLayout btn3 = (RelativeLayout) this.findViewById(R.id.rel_bar3);

        btn3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ecran9Activity.this, Ecran12Activity.class));

            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu) {



        return true;
    }
}
