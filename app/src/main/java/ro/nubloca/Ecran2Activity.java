package ro.nubloca;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.regex.Pattern;

import ro.nubloca.Networking.StandElem;
import ro.nubloca.extras.FontTitilliumBold;
import ro.nubloca.extras.Global;

public class Ecran2Activity extends AppCompatActivity {
    StandElem standElem;
    int selected = 0;
    int index;
    int campuri = 3;
    InputFilter filter, filter1;
    FontTitilliumBold field;
    byte[] baite;
    int dim = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        standElem = ((Global) getApplicationContext()).getStandElem();
        selected = ((Global) getApplicationContext()).getSelected();
        index = standElem.getSelected();
        campuri = standElem.getTipNumar().get(index).getTip_size();

        showElements();

        View btn2 = (View) findViewById(R.id.alegeTipInmatriculare);
        if (btn2 != null)
            btn2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //standElem.setPositionExemplu(-1);
                    //((Global) getApplicationContext()).setStandElem(standElem);
                    //finish();
                    ((Global) getApplicationContext()).setIntent("Ecran2Activity");
                    startActivity(new Intent(Ecran2Activity.this, Ecran23Activity.class));
                    /*Intent i = new Intent(getApplicationContext(), Ecran23Activity.class);
                    i.putExtra("intent","Ecran2Activity");
                    startActivity(i);*/

                }
            });

    }

    private void showElements() {

        campuri = standElem.getTipNumar().get(index).getTip_size();

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
            // calculam lunngimea inputului

            if (standElem.getTipNumar().get(index).getTip_maxlength()[i] < 3) {

                nrUML += 3;
            } else {
                nrUML += standElem.getTipNumar().get(index).getTip_maxlength()[i];
            }

        }
        valSPI = divLength / (nrUML * 6 + 3 * nrSPL + nrSPI);
        valSPL = 3 * valSPI;
        valUML = 6 * valSPI;
        int valRealUml = Math.min(valMaxUML, valUML);
        int minTrei = 0;


        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_plate_holder);
        linearLayout.setPadding(valSPL, 0, valSPL, 0);
        filter = new InputFilter() {
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

        filter1 = new InputFilter() {
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

        int[] ordinea = new int[campuri];
        for (int i = 0; i < campuri; i++) {
            ordinea[i] = standElem.getTipNumar().get(index).getDemo_ordinea()[i];
        }
        Arrays.sort(ordinea);
        int[] sortOrd = ordinea;

        int first_focus = 0;

        for (int xx = 0; xx < sortOrd.length; xx++) {
            for (int i = 0; i < campuri; i++) {
                if (standElem.getTipNumar().get(index).getDemo_ordinea()[i] == sortOrd[xx]) {


                    if (standElem.getTipNumar().get(index).getTip_maxlength()[i] < 3) {
                        minTrei = standElem.getTipNumar().get(index).getTip_maxlength()[i] + 1;
                    } else {
                        minTrei = standElem.getTipNumar().get(index).getTip_maxlength()[i];
                    }

                    if (standElem.getTipNumar().get(index).getTip_tip()[i].equals("LISTA")) {
                        first_focus++;
                        if (i != 0) {
                            TextView field1 = new TextView(this);
                            field1.setHeight(0);
                            field1.setWidth(valSPI);
                            linearLayout.addView(field1);
                        }
                        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT, 1f);
                        //Spinner mySpinner = new Spinner (new ContextThemeWrapper(this, R.style.spinner_style), null, 0);
                        Spinner mySpinner = new Spinner(this);

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Ecran2Activity.this, R.layout.raw_list_1, standElem.getTipNumar().get(index).getLista_cod().get(i));
                        mySpinner.setAdapter(adapter);



                        //mySpinner.setAdapter(new ArrayAdapter<String>(Ecran20Activity.this, R.layout.raw_list_1, standElem.getTipNumar().get(index).getLista_cod().get(i)));

                        params.width = minTrei * valRealUml;
                        mySpinner.setLayoutParams(params);
                        linearLayout.addView(mySpinner);


                    } else {
                        field = new FontTitilliumBold(this);
                        field.setId(i);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT, 1f);

                        if (i != 0) {
                            params.setMargins(valSPI, 0, 0, 0);
                        }

                        /*if ((i + 1) < campuri) {
                            if (standElem.getTipNumar().get(index).getTip_tip()[i + 1].equals("LISTA")) {
                                params.setMargins(valSPI, 0, valSPI, 0);
                            }
                        }*/
                        field.setLayoutParams(params);
                        field.setWidth(valRealUml * minTrei);
                        field.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
                        field.setTextSize(25);
                        field.setTextColor(getResources().getColor(R.color.text_layout_custom));
                        field.setBackgroundResource(R.drawable.plate_border);

                        if (standElem.getTipNumar().get(index).getTip_tip()[i].equals("CIFRE")) {
                            if (standElem.getTipNumar().get(index).getTip_editabil()[i] == 0) {
                                first_focus++;
                                field.setText(standElem.getTipNumar().get(index).getTip_valori()[i].replace("[", "").replace("]", ""));

                                field.setWidth(valRealUml * standElem.getTipNumar().get(index).getTip_maxlength()[i]);
                                field.setBackgroundResource(R.drawable.plate_border_white);
                                field.setEnabled(false);
                            }

                            field.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                            field.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(standElem.getTipNumar().get(index).getTip_maxlength()[i])});
                            field.setSelected(false);
                        }
                        if (standElem.getTipNumar().get(index).getTip_tip()[i].equals("LITERE")) {
                            if (standElem.getTipNumar().get(index).getTip_editabil()[i] == 0) {
                                first_focus++;
                                field.setText(standElem.getTipNumar().get(index).getTip_valori()[i].replace("[", "").replace("]", ""));
                                field.setEnabled(false);
                                field.setWidth(valRealUml * standElem.getTipNumar().get(index).getTip_maxlength()[i]);
                                field.setBackgroundResource(R.drawable.plate_border_white);
                            }
                            field.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                            field.setFilters(new InputFilter[]{filter1, new InputFilter.LengthFilter(standElem.getTipNumar().get(index).getTip_maxlength()[i])});
                            field.setSelected(false);
                        }
                        field.setSelected(false);
                        if ((first_focus == 0) && (standElem.getTipNumar().get(index).getTip_editabil()[i] == 1)) {
                            field.requestFocus();
                            first_focus++;
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        }

                        /*if ((standElem.getTipNumar().get(index).getTip_editabil()[i] == 1) && (!standElem.getTipNumar().get(index).getTip_tip()[i].equals("LISTA"))) {
                            //if((standElem.getTipNumar().get(index).getTip_editabil()[i] == 1)){
                            field.setText(camp[i]);
                            *//*Toast toast = Toast.makeText(this, "sds", Toast.LENGTH_LONG);
                            toast.show();*//*
                        }*/
                        linearLayout.addView(field);


                    }
                }
            }
        }
        // name_tip_inmatriculare = taraElem.getTip_nume();

        TextView tip_inmatriculare_nume = (TextView) findViewById(R.id.nume_tip_inmatriculare);
        tip_inmatriculare_nume.setText(standElem.getTipNumar().get(index).getNume());
        ImageView image = (ImageView) findViewById(R.id.flag1);
        baite = standElem.getSteag();
        Bitmap bmp = BitmapFactory.decodeByteArray(baite, 0, baite.length);
        image.setImageBitmap(bmp);
        image.getLayoutParams().height = convDp(dim);
        image.getLayoutParams().width = convDp(dim);
        image.requestLayout();
        image.setVisibility(View.VISIBLE);


    }



    public boolean onCreateOptionsMenu(Menu menu) {
        View btn = this.findViewById(R.id.btn_ok);
        if (btn != null) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Ecran2Activity.this, Ecran16Activity.class));
                }
            });
        }
        return true;
    }

    int convDp(float sizeInDp) {
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (sizeInDp * scale + 0.5f);
        return dpAsPixels;
    }


    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(Ecran2Activity.this, Ecran7Activity.class));
        super.onBackPressed();
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        Intent myIntent = new Intent(getApplicationContext(), Ecran7Activity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }
}
