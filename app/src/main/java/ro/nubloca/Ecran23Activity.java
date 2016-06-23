package ro.nubloca;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ro.nubloca.Networking.CustomAdapter;
import ro.nubloca.Networking.Response;
import ro.nubloca.Networking.StandElem;
import ro.nubloca.extras.CustomFontTitilliumRegular;
import ro.nubloca.extras.Global;


public class Ecran23Activity extends AppCompatActivity {

    int positionExemplu = -1;
    String country_select;
    private ProgressDialog m_ProgressDialog = null;
    private ArrayList<Response> m_orders = null;

    private Runnable viewOrders;
    int dim1 = 50;
    int dim;
    byte[] baite, baite1;
    ProgressBar progressBar;
    StandElem standElem;
    boolean active = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran23);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.VISIBLE);

        positionExemplu = ((Global) this.getApplication()).getPositionExemplu();
        standElem = ((Global) getApplicationContext()).getStandElem();
        country_select = standElem.getNume();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        String[] values = new String[standElem.getSize()];
        for (int i = 0; i < standElem.getSize(); i++) {
            values[i] = standElem.getTipNumar().get(i).getNume();
        }


        ListAdapter customAdapter = new CustomAdapter(this, values);
        ListView lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(customAdapter);


        TextView country = (TextView) findViewById(R.id.textViewCountry);
        country.setText(country_select);

        ImageView flag = (ImageView) findViewById(R.id.flag);
        ImageView bkg = (ImageView) findViewById(R.id.img);

        baite = standElem.getImgReprezent();
        Bitmap bmp = BitmapFactory.decodeByteArray(baite, 0, baite.length);
        bkg.setImageBitmap(bmp);

        baite1 = standElem.getSteag();
        Bitmap bmp1 = BitmapFactory.decodeByteArray(baite1, 0, baite1.length);
        //Bitmap bMapScaled = Bitmap.createScaledBitmap(bmp1, convDp(dim1 * 1.7), convDp(dim1), true);

        //flag.setImageBitmap(bMapScaled);
        flag.setImageBitmap(bmp1);

        /*final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        animation.setDuration(1500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in

        final ImageView arw = (ImageView) findViewById(R.id.arrow);
        arw.startAnimation(animation);*/

        RelativeLayout relBkg = (RelativeLayout) findViewById(R.id.rel_bkg);

        if (relBkg != null)
            relBkg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Ecran23Activity.this, Ecran25Activity.class));
                    //finish();
                }
            });

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(Ecran23Activity.this, Ecran20Activity.class));
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();
                Ecran23Activity.this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    int convDp(double sizeInDp) {
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (sizeInDp * scale + 0.5f);
        return dpAsPixels;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            progressBar.setVisibility(View.GONE);


        }


    };


    public class CustomAdapter extends ArrayAdapter<String> {


        public CustomAdapter(Context context, String[] elemente) {
            super(context, R.layout.raw_list, elemente);


        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            LayoutInflater dapter = LayoutInflater.from(getContext());
            View customView = dapter.inflate(R.layout.raw_list, parent, false);

            String singleFood = getItem(position);
            CustomFontTitilliumRegular textul = (CustomFontTitilliumRegular) customView.findViewById(R.id.text);
            final ImageView imaginea = (ImageView) customView.findViewById(R.id.radioButton);
            LinearLayout ll = (LinearLayout) customView.findViewById(R.id.linear1);
            LinearLayout lll = (LinearLayout) customView.findViewById(R.id.linear2);
            if (standElem.getPositionExemplu() == position) {
                lll.setBackgroundColor(Color.parseColor("#F0F0F0"));
            }
            textul.setText(singleFood);
            if (standElem.getSelected() == position) {
                imaginea.setImageResource(R.drawable.radio_press);
            } else {
                imaginea.setImageResource(R.drawable.radio);
            }


            if (ll != null) {
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        standElem.setSelected(position);
                        standElem.setPositionExemplu(-1);
                        ((Global) getApplicationContext()).setStandElem(standElem);
                        startActivity(new Intent(Ecran23Activity.this, Ecran20Activity.class));
                        finish();
                    }
                });
            }


            if (lll != null) {

                lll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        standElem.setPositionExemplu(position);
                        startActivity(new Intent(Ecran23Activity.this, Ecran26Activity.class));
                    }
                });
            }

            return customView;
        }
    }
}
