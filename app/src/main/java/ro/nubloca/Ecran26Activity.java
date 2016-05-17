package ro.nubloca;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import ro.nubloca.extras.CustomFontTitilliumBold;

public class Ecran26Activity extends AppCompatActivity {
    int camp=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran26);
        Intent myIntent = getIntent();
        String campuri = myIntent.getStringExtra("campuri");
        String name_tip_inmatriculare = myIntent.getStringExtra("name_tip_inmatriculare");
        String maxlength1 = myIntent.getStringExtra("Campul unu");
        String maxlength2 = myIntent.getStringExtra("Campul doi");
        String maxlength3 = myIntent.getStringExtra("Campul trei");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        camp = Integer.parseInt(campuri);


        if (camp==2){
            View field3 = (View)findViewById(R.id.field3);
            field3.setVisibility(View.GONE);
            CustomFontTitilliumBold field1 = (CustomFontTitilliumBold)findViewById(R.id.field1);
            field1.setText(maxlength1.toString());
            CustomFontTitilliumBold field2 = (CustomFontTitilliumBold)findViewById(R.id.field2);
            field2.setText(maxlength2.toString());
        }
        else {
            CustomFontTitilliumBold field1 = (CustomFontTitilliumBold)findViewById(R.id.field1);
            field1.setText(maxlength1.toString());
            CustomFontTitilliumBold field2 = (CustomFontTitilliumBold)findViewById(R.id.field2);
            field2.setText(maxlength2.toString());
            CustomFontTitilliumBold field3 = (CustomFontTitilliumBold)findViewById(R.id.field3);
            field3.setText(maxlength3.toString());
        }
        /*Toast toast = Toast.makeText(this, name_tip_inmatriculare+" "+campuri, Toast.LENGTH_LONG);
        toast.show();*/

        TextView tip_inmatriculare_nume = (TextView)findViewById(R.id.nume_tip_inmatriculare);
        tip_inmatriculare_nume.setText(name_tip_inmatriculare);


    }


}
