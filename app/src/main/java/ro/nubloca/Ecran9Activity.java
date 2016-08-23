package ro.nubloca;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ro.nubloca.extras.GlobalVar;

public class Ecran9Activity extends AppCompatActivity {
    String urlShare1 = ((GlobalVar) this.getApplication()).urlShare;

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
        //final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        //upArrow.setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_ATOP);
        //getSupportActionBar().setHomeAsUpIndicator(upArrow);

        /*TextView ajutor_btn = (TextView) this.findViewById(R.id.textView91);
        if (ajutor_btn != null) {
            ajutor_btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Ecran9Activity.this, Ecran13Activity.class));
                }
            });
        }*/
        RelativeLayout ajutor_btn = (RelativeLayout) this.findViewById(R.id.rel_bar1);
        if (ajutor_btn != null) {
            ajutor_btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Ecran9Activity.this, Ecran11Activity.class));
                    /*Intent intent = new Intent(getBaseContext(), Ecran11Activity.class);
                    intent.putExtra("id_parinte", 0);
                    startActivity(intent);*/
                }
            });
        }
        RelativeLayout contact_btn = (RelativeLayout) this.findViewById(R.id.rel_bar2);
        if (contact_btn != null) {
            contact_btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Ecran9Activity.this, Ecran10Activity.class));
                }
            });
        }
        RelativeLayout term_si_cond_btn = (RelativeLayout) this.findViewById(R.id.rel_bar3);
        if (term_si_cond_btn != null) {
            term_si_cond_btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Ecran9Activity.this, Ecran12Activity.class));

                }
            });
        }

        ImageView imageV = (ImageView) this.findViewById(R.id.imageView);
        if (imageV != null) {
            imageV.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, urlShare1);
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, "nuBloca"));

                }
            });
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {


        return true;
    }
}
