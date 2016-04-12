package ro.nubloca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity implements ScrollViewListener{

    private ObservableScrollView scrollView = null;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    public boolean tos_check = false;
    Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        scrollView = (ObservableScrollView) findViewById(R.id.scrollView1);
        scrollView.setScrollViewListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("                 Termeni si conditii");
        toolbar.setTitleTextColor(0xFFFFFFFF);


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        b1=(Button)findViewById(R.id.acord);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                if (tos_check) {
                    Toast.makeText(SecondActivity.this, "Thanks", Toast.LENGTH_LONG).show();

                    editor.putBoolean("TOS", true);
                    editor.apply();
                    finish();
                    startActivity(new Intent(SecondActivity.this, ThirdActivity.class));
                }
                else {
                    final RelativeLayout back_dim_layout = (RelativeLayout) findViewById(R.id.bac_dim_layout);
                    back_dim_layout.setVisibility(View.VISIBLE);
                    Toast.makeText(SecondActivity.this, "Nu ati citit tot textul!", Toast.LENGTH_LONG).show();
                    LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.popup, null);
                    final PopupWindow popupWindow = new PopupWindow(
                            popupView,
                            Toolbar.LayoutParams.WRAP_CONTENT,
                            Toolbar.LayoutParams.WRAP_CONTENT, true);

                    Button btnDismiss = (Button)popupView.findViewById(R.id.button1);
                    btnDismiss.setOnClickListener(new Button.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            back_dim_layout.setVisibility(View.GONE);
                            popupWindow.dismiss();
                        }});


                    Button btnDa = (Button)popupView.findViewById(R.id.button2);
                    btnDa.setOnClickListener(new Button.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            back_dim_layout.setVisibility(View.GONE);
                            popupWindow.dismiss();
                            //go to ecran5
                            startActivity(new Intent(SecondActivity.this, Ecran5Activity.class));


                        }});



                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                }
                //Toast.makeText(SecondActivity.this, "Thanks", Toast.LENGTH_LONG).show();


            }
        });

        b2=(Button)findViewById(R.id.refuz);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean("TOS", false);
                editor.apply();
                Toast.makeText(SecondActivity.this, ":(", Toast.LENGTH_LONG).show();
                //finish();
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });


    }

    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {

        View view = (View) scrollView.getChildAt(scrollView.getChildCount()-1);
        int diff = (view.getBottom()-(scrollView.getHeight()+scrollView.getScrollY()));

        if( diff == 0 ){
            tos_check=true;
            Toast.makeText(SecondActivity.this, "Jos", Toast.LENGTH_LONG).show();

        }


    }


}