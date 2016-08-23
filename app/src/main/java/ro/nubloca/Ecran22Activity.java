package ro.nubloca;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import ro.nubloca.extras.CustomFontTitilliumRegular;
import ro.nubloca.extras.Global;

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
        Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        String[] values = {"Masinile mele","Notificari","Istoric conversatii","Despre","Masinile mele"};

        ListAdapter customAdapter = new CustomAdapter(this, values);
        ListView lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(customAdapter);




        LinearLayout vezi = (LinearLayout) findViewById(R.id.linear1);
        if (vezi != null)
            vezi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Ecran22Activity.this, Ecran27Activity.class));
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



    public class CustomAdapter extends ArrayAdapter<String> {


        public CustomAdapter(Context context, String[] elemente) {
            super(context, R.layout.raw_list22, elemente);


        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            LayoutInflater dapter = LayoutInflater.from(getContext());
            View customView = dapter.inflate(R.layout.raw_list22, parent, false);

            String singleFood = getItem(position);
            CustomFontTitilliumRegular textul = (CustomFontTitilliumRegular) customView.findViewById(R.id.text);
            final ImageView imaginea = (ImageView) customView.findViewById(R.id.radioButton);
            LinearLayout ll = (LinearLayout) customView.findViewById(R.id.linear1);
            LinearLayout lll = (LinearLayout) customView.findViewById(R.id.linear2);

            textul.setText(singleFood);



            if (ll != null) {
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
            }


            if (lll != null) {
                lll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
            }

            return customView;
        }
    }

    @Override
    public void onBackPressed() {
        //finish();
        //startActivity(new Intent(Ecran22Activity.this, Ecran20Activity.class));
        super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                Ecran22Activity.this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
