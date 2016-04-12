package ro.nubloca;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("                       Nu Bloca!");
        toolbar.setTitleTextColor(0xFFFFEA00);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        LinearLayout newsMenu = (LinearLayout) this.findViewById(R.id.linearLayout);

        newsMenu.setOnClickListener(new View.OnClickListener() {

            ImageView iv=(ImageView)findViewById(R.id.imageView5);
            @Override
            public void onClick(View v) {
                iv.setImageResource(R.drawable.car_small71);
                Toast.makeText(ThirdActivity.this, ":(", Toast.LENGTH_LONG).show();

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu1) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean("TOS", false);
            editor.apply();
            Toast.makeText(ThirdActivity.this, "TOS CLEAN", Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);
    }
}
