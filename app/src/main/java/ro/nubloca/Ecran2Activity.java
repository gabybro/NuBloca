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
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.regex.Pattern;

import ro.nubloca.Networking.GetRequest;
import ro.nubloca.Networking.StandElem;
import ro.nubloca.extras.FontTitilliumBold;
import ro.nubloca.extras.Global;

public class Ecran2Activity extends AppCompatActivity {
    String result;
    StandElem standElem;
    int selected = 0;
    int index;
    int campuri = 3;
    InputFilter filter, filter1;
    FontTitilliumBold field;
    byte[] baite;
    int dim = 30;
    JSONObject obj = new JSONObject();

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
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
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

    private void makeRequest(JSONObject jsonob) {

        obj = jsonob;

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


                HttpClient httpClient = new DefaultHttpClient();

                String url = "http://api.nubloca.ro/numere/";

                HttpPost httpPost = new HttpPost(url);
                httpPost.setHeader("Content-Type", "application/json");
                /*httpPost.setHeader("Content-Language", "ro");
                httpPost.setHeader("Accept-Language", "ro");*/


                try {

                    httpPost.setEntity(new ByteArrayEntity(obj.toString().getBytes("UTF8")));

                } catch (UnsupportedEncodingException e) {
                    // log exception
                    e.printStackTrace();
                }

                try {
                    HttpResponse response = httpClient.execute(httpPost);
                    result = EntityUtils.toString(response.getEntity());

                } catch (ClientProtocolException e) {
                    // Log exception
                    e.printStackTrace();
                } catch (IOException e) {
                    // Log exception
                    e.printStackTrace();
                }


            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getInputJson() throws JSONException {
        /*{
            "identificare": {            "user": {                "app_code": "abcdefghijkl123456"            }        },
            "trimise": {"id_tip": 1,
                        "elemente": [
                            {"id_tip_element": 1,
                             "valoare": 2173},
                            {"id_tip_element": 2,
                             "valoare": "01"},
                            {"id_tip_element": 3,
                             "valoare": "JTL"}]}}*/

        JSONObject prepJson=new JSONObject();
        JSONObject prepIdent=new JSONObject();
        JSONObject prepElem=new JSONObject();

        JSONObject prepUser=new JSONObject();
        prepUser.put("app_code", "abcdefghijkl123456" );
        prepIdent.put("user",prepUser);

        int id = standElem.getTipNumar().get(index).getId();
        prepElem.put("id_tip",id);
        JSONArray elemente = new JSONArray();

        LinearLayout layout = (LinearLayout) findViewById(R.id.linear_plate_holder);
        int count = layout.getChildCount();
        View v = null;

        //

        for (int i = 0; i < count; i++) {
            JSONObject json_elem = new JSONObject();
            v = layout.getChildAt(i);
            if (v instanceof Spinner) {
                //iddd = ((Spinner) v).getId();

                int id_elem = standElem.getTipNumar().get(index).getTip_idd_loc(i);
                json_elem.put("id_tip_element",id_elem);
                int id_select_spinner = ((Spinner) v).getId();
                //json_elem.put("valoare",id_select_spinner);
                json_elem.put("valoare",2165);
            } else {
                int id_elem = standElem.getTipNumar().get(index).getTip_idd_loc(i);
                json_elem.put("id_tip_element",id_elem);
                String input = ((TextView) v).getText().toString();
                json_elem.put("valoare",input);

            }
            elemente.put(json_elem);
        }
            prepElem.put("elemente",elemente);

        prepJson.put("identificare",prepIdent);
        prepJson.put("trimise",prepElem);
        return prepJson;

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        View btn = this.findViewById(R.id.btn_ok);
        if (btn != null) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONObject js = new JSONObject();
                    String s="";
                    try {
                        s = getInputJson().toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //startActivity(new Intent(Ecran2Activity.this, Ecran16Activity.class));
                    Toast toast = Toast.makeText(getApplication(), s, Toast.LENGTH_LONG);
                    toast.show();
                    try {
                        js = getInputJson();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    makeRequest(js);
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
