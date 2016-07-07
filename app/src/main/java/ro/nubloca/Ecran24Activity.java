package ro.nubloca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.LinkedList;

import ro.nubloca.Networking.StandElem;
import ro.nubloca.extras.CustomFontTitilliumBold;
import ro.nubloca.extras.CustomFontTitilliumRegular;
import ro.nubloca.extras.Global;

public class Ecran24Activity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    String[] listNumere, listDate;
    int[] click;
    ListAdapter customAdapter = null;
    Drawable upArrow = null;
    boolean interfata = false;
    boolean all_select = false;
    ListView lv;
    public boolean box_checked=false;
    CustomFontTitilliumBold btn_sterge;
    int summ=0;
    ImageView boxTop;
    int dim = 30;
    StandElem standElem;
    byte[] arrayaa;
    int selectedItems = 0;
    CustomFontTitilliumRegular itemSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran24);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btn_sterge = (CustomFontTitilliumBold) this.findViewById(R.id.sterge);
        itemSelected = (CustomFontTitilliumRegular) this.findViewById(R.id.item_selected);
        boxTop = (ImageView) findViewById(R.id.box);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(Color.parseColor("#fcd116"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        standElem = ((Global) getApplicationContext()).getStandElem();


        int size_LSNumere = sharedpreferences.getInt("SizeLSNumere", 0);
        /*for(int j=0;j<size_LSNumere;j++){
            linkedlist.add(sharedpreferences.getString("LSNumere"+j, ""));
        }*/


        //TODO list in ecran 20
        //code change with date
        if (size_LSNumere > 0) {

            listNumere = new String[size_LSNumere];

            for (int i = 0; i < size_LSNumere; i++) {
                listNumere[i] = sharedpreferences.getString("LSNumere" + i, "");
            }

            customAdapter = new CustomAdapterNumere(this, listNumere);
            lv = (ListView) findViewById(R.id.list);
            lv.setAdapter(customAdapter);

        }



    }

    public class CustomAdapterNumere extends ArrayAdapter<String> {


        public CustomAdapterNumere(Context context, String[] elemente) {
            super(context, R.layout.raw_list24, elemente);
            click = new int[elemente.length];
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {

            LayoutInflater dapter = LayoutInflater.from(getContext());

            if (interfata) {
                final View customView = dapter.inflate(R.layout.raw_list_interfata24, parent, false);
                String singleElem = getItem(position);


                String beforeFirstDot = singleElem.split("\\.")[0];
                String afterFirstDot = singleElem.split("\\.")[1];
                String arrayFlag = singleElem.split("\\.")[4];


                CustomFontTitilliumRegular textul = (CustomFontTitilliumRegular) customView.findViewById(R.id.text);
                CustomFontTitilliumRegular date = (CustomFontTitilliumRegular) customView.findViewById(R.id.date);
                ImageView image = (ImageView) customView.findViewById(R.id.flag);

                textul.setText(beforeFirstDot);

                LinearLayout layout = (LinearLayout) customView.findViewById(R.id.linear);
                final ImageView imaginea = (ImageView) customView.findViewById(R.id.radioButton);


                textul.setText(beforeFirstDot);
                date.setText(afterFirstDot);

                byte[] arrayaa = Base64.decode(arrayFlag, Base64.DEFAULT);
                Bitmap bmp = BitmapFactory.decodeByteArray(arrayaa, 0, arrayaa.length);
                image.setImageBitmap(bmp);
                image.getLayoutParams().height = convDp(dim);
                image.getLayoutParams().width = convDp(dim);
                image.requestLayout();

                if (all_select) {
                    for (int i = 0; i < click.length; i++) {
                        imaginea.setImageResource(R.drawable.abc_btn_press);
                        click[position] = 1;

                    }
                    selectedItems = click.length;
                    //btn_sterge.setText(R.string.istoric_numere_sterge);
                }
                if (layout != null) {
                    layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (click[position] == 0) {
                                imaginea.setImageResource(R.drawable.abc_btn_press);
                                click[position] = 1;
                                btn_sterge.setText(R.string.istoric_numere_sterge);
                                int summa=0;
                                for (int j=0;j<click.length;j++) {
                                    summa+=click[j];
                                }

                                if (summa==click.length){
                                    boxTop.setBackgroundResource(R.drawable.abc_btn_press_yellow);
                                }
                                selectedItems = summa;
                                String sel = selectedItems +" "+ getString(R.string.items_selected);
                                itemSelected.setText(sel);

                            } else {
                                imaginea.setImageResource(R.drawable.abc_btn);
                                boxTop.setBackgroundResource(R.drawable.abc_btn_yellow);
                                click[position] = 0;
                                int summa=0;
                                for (int j=0;j<click.length;j++) {
                                    summa+=click[j];
                                }
                                if (summa==0){
                                    btn_sterge.setText(R.string.istoric_numere_renunta);
                                }

                                selectedItems = summa;
                                String sel = selectedItems +" "+ getString(R.string.items_selected);
                                itemSelected.setText(sel);
                            }

                        }
                    });
                }


                return customView;
            } else {

                final View customView = dapter.inflate(R.layout.raw_list24, parent, false);

                final String singleElem = getItem(position);
                String beforeFirstDot = singleElem.split("\\.")[0];
                String afterFirstDot = singleElem.split("\\.")[1];
                String arrayFlag = singleElem.split("\\.")[4];

                final ImageView imaginea = (ImageView) customView.findViewById(R.id.radioButton);
                CustomFontTitilliumRegular textul = (CustomFontTitilliumRegular) customView.findViewById(R.id.text);
                CustomFontTitilliumRegular date = (CustomFontTitilliumRegular) customView.findViewById(R.id.date);

                ImageView image = (ImageView) customView.findViewById(R.id.flag);


                textul.setText(beforeFirstDot);
                date.setText(afterFirstDot);

                byte[] arrayaa = Base64.decode(arrayFlag, Base64.DEFAULT);
                Bitmap bmp = BitmapFactory.decodeByteArray(arrayaa, 0, arrayaa.length);
                image.setImageBitmap(bmp);
                image.getLayoutParams().height = convDp(dim);
                image.getLayoutParams().width = convDp(dim);
                image.requestLayout();




                LinearLayout layout = (LinearLayout) customView.findViewById(R.id.linear);
                imaginea.setImageResource(R.drawable.radio);

                if (layout != null) {
                    layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            imaginea.setImageResource(R.drawable.radio_press);
                            ((Global) getApplicationContext()).setNumarSelected(singleElem);
                            finish();
                            startActivity(new Intent(Ecran24Activity.this, Ecran20Activity.class));
                        }
                    });
                }
                return customView;
            }


        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        final CustomFontTitilliumBold btn_edit = (CustomFontTitilliumBold) this.findViewById(R.id.edit);
        final CustomFontTitilliumRegular title = (CustomFontTitilliumRegular) this.findViewById(R.id.toolbar_title);




        if (listNumere != null) {
            btn_edit.setVisibility(View.VISIBLE);

            if (btn_edit != null)
                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        all_select = false;
                        btn_edit.setVisibility(View.GONE);
                        boxTop.setVisibility(View.VISIBLE);
                        String sel = selectedItems +" "+ getString(R.string.items_selected);
                        itemSelected.setText(sel);
                        itemSelected.setVisibility(View.VISIBLE);
                        btn_sterge.setText(R.string.istoric_numere_renunta);
                        btn_sterge.setVisibility(View.VISIBLE);
                        title.setVisibility(View.GONE);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

                        boxTop.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (box_checked==false){
                                    btn_sterge.setText(R.string.istoric_numere_sterge);
                                    boxTop.setBackgroundResource(R.drawable.abc_btn_press_yellow);
                                    box_checked = true;
                                    all_select = true;
                                    customAdapter = new CustomAdapterNumere(getApplication(), listNumere);
                                    lv.setAdapter(customAdapter);
                                    selectedItems=listNumere.length;
                                    String sel = selectedItems +" "+ getString(R.string.items_selected);
                                    itemSelected.setText(sel);

                                }else {
                                    boxTop.setBackgroundResource(R.drawable.abc_btn_yellow);
                                    btn_sterge.setText(R.string.istoric_numere_renunta);
                                    box_checked = false;
                                    all_select = false;
                                    customAdapter = new CustomAdapterNumere(getApplication(), listNumere);
                                    lv.setAdapter(customAdapter);
                                    String sel = 0 +" "+ getString(R.string.items_selected);
                                    itemSelected.setText(sel);

                                }
                            }
                        });


                        interfata = true;
                        customAdapter = new CustomAdapterNumere(getApplication(), listNumere);
                        lv.setAdapter(customAdapter);

                    }
                });


        if (btn_sterge != null)
            btn_sterge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    itemSelected.setVisibility(View.GONE);
                    selectedItems = 0;
                    title.setVisibility(View.VISIBLE);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                    btn_sterge.setVisibility(View.GONE);
                    if (boxTop != null) {
                        boxTop.setVisibility(View.GONE);
                    }

                    int size_LSNumere = sharedpreferences.getInt("SizeLSNumere", 0);
                    if (size_LSNumere > 0) {

                        int sum = 0;

                        for (int i : click) {
                            sum += i;
                        }

                        String[] trans = new String[listNumere.length - sum];
                        int j = 0;
                        for (int i = 0; i < listNumere.length; i++) {
                            if (click[i] == 0) {
                                trans[j] = listNumere[i];
                                j++;
                            }
                        }
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        for (int i = 0; i < listNumere.length; i++) {
                            editor.remove("LSNumere" + i);
                        }
                        editor.putInt("SizeLSNumere", trans.length);
                        editor.commit();

                        listNumere = new String[trans.length];
                        listNumere = trans;

                        for (int i = 0; i < listNumere.length; i++) {
                            editor.putString("LSNumere" + i, listNumere[i]);
                        }
                        editor.commit();

                        interfata = false;

                        customAdapter = new CustomAdapterNumere(getApplication(), listNumere);
                        lv.setAdapter(customAdapter);

                        if (listNumere.length>0) {
                            btn_edit.setVisibility(View.VISIBLE);
                        }
                    }


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
        //startActivity(new Intent(Ecran24Activity.this, Ecran20Activity.class));
        //finish();
        super.onBackPressed();
    }
    /*@Override
    public boolean onNavigateUp () {
        onBackPressed();
        return true;
    }*/
    @Override
    public Intent getSupportParentActivityIntent() {
        Intent intent = super.getSupportParentActivityIntent();
        return intent;
    }
    /*public boolean onOptionsItemSelected(MenuItem item){
        finish();
        Intent myIntent = new Intent(getApplicationContext(), Ecran20Activity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                Ecran24Activity.this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}