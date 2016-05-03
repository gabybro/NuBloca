package ro.nubloca;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Ecran4Activity extends AppCompatActivity {
    Button button, button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_ecran4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*getSupportActionBar().setTitle("                 Termeni si conditii");
        toolbar.setTitleTextColor(0xFFFFFFFF);*/
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        /*TextView textCustomFont = (TextView) findViewById(R.id.editText);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/TitilliumText22L003.otf");
        textCustomFont.setTypeface(myCustomFont);*/

       /* TextView textCustomFont1 = (TextView) findViewById(R.id.button);
        Typeface myCustomFont1 = Typeface.createFromAsset(getAssets(), "fonts/TitilliumText22L005.otf");
        textCustomFont1.setTypeface(myCustomFont1);

        TextView textCustomFont2 = (TextView) findViewById(R.id.button3);
        Typeface myCustomFont2 = Typeface.createFromAsset(getAssets(), "fonts/TitilliumText22L005.otf");
        textCustomFont2.setTypeface(myCustomFont2);*/

       View button = (View) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Ecran4Activity.this, Ecran3Activity.class));

            }
        });

       View button1 = (View) findViewById(R.id.button3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

    }


}
