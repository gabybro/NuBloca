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

public class Ecran26Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran26);
        Intent myIntent = getIntent();
        String campuri = myIntent.getStringExtra("campuri");
        String name_tip_inmatriculare = myIntent.getStringExtra("name_tip_inmatriculare");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        Toast toast = Toast.makeText(this, name_tip_inmatriculare, Toast.LENGTH_LONG);
        toast.show();

        TextView tip_inmatriculare_nume = (TextView)findViewById(R.id.nume_tip_inmatriculare);
        tip_inmatriculare_nume.setText(name_tip_inmatriculare);


    }


}
