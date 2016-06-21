package ro.nubloca;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import ro.nubloca.Networking.AllElem;
import ro.nubloca.Networking.GetRequest;
import ro.nubloca.Networking.GetRequestImg;
import ro.nubloca.Networking.Response;
import ro.nubloca.Networking.StandElem;
import ro.nubloca.extras.CustomFontTitilliumBold;
import ro.nubloca.extras.CustomFontTitilliumRegular;
import ro.nubloca.extras.Global;

public class Ecran26Activity extends AppCompatActivity {

    String result_string;
    int campuri = 3;
    String nume_tip_inmatriculare;
    String get_order_ids_tip;
    private ProgressDialog pd;
    ProgressDialog dialog;
    ProgressBar progressBar;

    AllElem[] allelem;
    int[] id_tip_element, Ids_tipuri_inmatriculare_tipuri_elemente;
    List<Response> response2, response3;
    int id_exemplu;
    int dim = 30;
    String url = "http://api.nubloca.ro/tipuri_inmatriculare_tipuri_elemente/";
    String url2 = "http://api.nubloca.ro/tipuri_elemente/";
    String url3 = "http://api.nubloca.ro/tipuri_inmatriculare/";
    String url4 = "http://api.nubloca.ro/imagini/";
    String numeSteag;
    byte[] baite, baite1, baite2;
    StandElem standElem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran26);

        standElem = ((Global) getApplicationContext()).getStandElem();

        id_exemplu = standElem.getPositionExemplu();


        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //get_order_ids_tip = ((Global) this.getApplication()).getGet_order_ids_tip();
        //nume_tip_inmatriculare = ((Global) this.getApplication()).getNume_tip_inmatriculare();
        //Ids_tipuri_inmatriculare_tipuri_elemente=((Global) this.getApplication()).getIds_tipuri_inmatriculare_tipuri_elemente();
        //id_exemplu=((Global) this.getApplication()).getId_exemplu();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //makePostRequestOnNewThread();
        showElements();


    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            progressBar.setVisibility(View.GONE);
            //showElements();


        }


    };

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Ecran26Activity.this, Ecran23Activity.class));
        finish();
        super.onBackPressed();
    }

    private void showElements() {
        campuri = standElem.getTipNumar().get(id_exemplu).getTip_size();
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
            if (standElem.getTipNumar().get(id_exemplu).getTip_maxlength()[i] < 3) {
                nrUML += 3;
            } else {
                nrUML += standElem.getTipNumar().get(id_exemplu).getTip_maxlength()[i];
            }

        }
        valSPI = divLength / (nrUML * 6 + 3 * nrSPL + nrSPI);
        valSPL = 3 * valSPI;
        valUML = 6 * valSPI;
        int valRealUml = Math.min(valMaxUML, valUML);
        int minTrei = 0;

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearPlate1);
        linearLayout.setPadding(valSPL, 0, valSPL, 0);
        RelativeLayout relativeLayout1 = (RelativeLayout) findViewById(R.id.relative1);
        relativeLayout1.setVisibility(View.VISIBLE);

        int[] ordinea = new int[campuri];
        for (int i = 0; i < campuri; i++) {
            ordinea[i] = standElem.getTipNumar().get(id_exemplu).getDemo_ordinea()[i];
        }
        Arrays.sort(ordinea);
        int[] sortOrd = ordinea;

        for (int xx = 0; xx < sortOrd.length; xx++) {
            for (int i = 0; i < campuri; i++) {
                if (standElem.getTipNumar().get(id_exemplu).getDemo_ordinea()[i] == sortOrd[xx]) {

                    if (standElem.getTipNumar().get(id_exemplu).getTip_maxlength()[i] < 3) {
                        minTrei = standElem.getTipNumar().get(id_exemplu).getTip_maxlength()[i] + 1;
                        if (standElem.getTipNumar().get(id_exemplu).getTip_editabil()[i] == 0) {
                            minTrei = standElem.getTipNumar().get(id_exemplu).getTip_maxlength()[i];
                        }
                    } else {
                        minTrei = standElem.getTipNumar().get(id_exemplu).getTip_maxlength()[i];
                    }


                    CustomFontTitilliumBold field = new CustomFontTitilliumBold(this);
                    field.setId(i);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT, 1f);

                    if (i != 0) {
                        params.setMargins(valSPI, 0, 0, 0);
                    }

                    field.setLayoutParams(params);
                    field.setWidth(valRealUml * minTrei);
                    field.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
                    field.setTextSize(30);
                    field.setBackgroundResource(R.drawable.plate_border);
                    field.setText(standElem.getTipNumar().get(id_exemplu).getDemo_valoare()[i]);
                    if (standElem.getTipNumar().get(id_exemplu).getTip_editabil()[i] == 0) {
                        field.setBackgroundResource(R.drawable.plate_border_white);
                    }
                    linearLayout.addView(field);
                    CustomFontTitilliumBold tip_inmatriculare_nume = (CustomFontTitilliumBold) findViewById(R.id.nume_tip_inmatriculare);
                    tip_inmatriculare_nume.setVisibility(View.VISIBLE);
                    tip_inmatriculare_nume.setText(standElem.getTipNumar().get(id_exemplu).getNume());
                    CustomFontTitilliumRegular text1 = (CustomFontTitilliumRegular) findViewById(R.id.textView20);
                    text1.setVisibility(View.VISIBLE);
                    ImageView image = (ImageView) findViewById(R.id.imageView9);

                    baite = standElem.getSteag();
                    Bitmap bmp = BitmapFactory.decodeByteArray(baite, 0, baite.length);
                    image.setImageBitmap(bmp);
                    image.getLayoutParams().height = convDp(dim);
                    image.getLayoutParams().width = convDp(dim);
                    image.requestLayout();
                    image.setVisibility(View.VISIBLE);

                    baite1 = standElem.getBackgDemo();
                    ImageView image1 = (ImageView) findViewById(R.id.car_exemplu);
                    Bitmap bmp1 = BitmapFactory.decodeByteArray(baite1, 0, baite1.length);
                    image1.setImageBitmap(bmp1);
                    image1.requestLayout();

                    baite2 = standElem.getPlateDemo();
                    ImageView image2 = (ImageView) findViewById(R.id.plate);
                    Bitmap bmp2 = BitmapFactory.decodeByteArray(baite2, 0, baite2.length);
                    Bitmap bMapScaled = Bitmap.createScaledBitmap(bmp2, convDp(430 / 2), convDp(103 / 2), true);

                    image2.setImageBitmap(bMapScaled);

                }
            }
        }
//////
    }

    int convDp(float sizeInDp) {
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (sizeInDp * scale + 0.5f);
        return dpAsPixels;
    }


}
