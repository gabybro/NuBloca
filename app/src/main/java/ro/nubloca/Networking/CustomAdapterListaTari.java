package ro.nubloca.Networking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;

import org.json.JSONException;

import ro.nubloca.Ecran20Activity;
import ro.nubloca.Ecran23Activity;
import ro.nubloca.Ecran25Activity;
import ro.nubloca.Ecran26Activity;
import ro.nubloca.Ecran3Activity;
import ro.nubloca.Ecran7Activity;
import ro.nubloca.R;
import ro.nubloca.extras.CustomFontTitilliumRegular;
import ro.nubloca.extras.Global;

/**
 * Created by GABY_ on 20.06.2016.
 */

public class CustomAdapterListaTari extends ArrayAdapter<String> {
    StandElem standElemAdapter, standElem1;
    int[] id;
    String[] code;
    String[] tara;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    Activity mActivity;

    public CustomAdapterListaTari(Context context, String[] elemente, int[] ids, String[] countryCode, StandElem standElem, Activity mActivity1) {
        super(context, R.layout.raw_list1, elemente);
        standElemAdapter = standElem;
        id = ids;
        code = countryCode;
        tara = elemente;
        mActivity = mActivity1;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        LayoutInflater dapter = LayoutInflater.from(getContext());
        View customView = dapter.inflate(R.layout.raw_list1, parent, false);

        String singleElem = getItem(position);
        CustomFontTitilliumRegular textul = (CustomFontTitilliumRegular) customView.findViewById(R.id.text1);
        final ImageView imaginea = (ImageView) customView.findViewById(R.id.radioButton1);

        textul.setText(singleElem);

        RelativeLayout rel = (RelativeLayout) customView.findViewById(R.id.rel_bar1);
        if (standElemAdapter.getId() == id[position]) {
            imaginea.setImageResource(R.drawable.radio_press);
        } else {
            imaginea.setImageResource(R.drawable.radio);
        }


        if (rel != null) {
            rel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // standElemAdapter.setSelected(position);
                    //  standElemAdapter.setPositionExemplu(-1);


                    // Do Work
                    RequestTara make = new RequestTara();
                    standElem1 = make.makePostRequestOnNewThread(getContext(), code[position]);


                    standElem1.setPositionExemplu(-1);

                    ((Global) mActivity.getApplicationContext()).setStandElem(standElem1);

                    ((Global) mActivity.getApplicationContext()).setPositionExemplu(-1);

                    Gson gson = new Gson();
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    String json = gson.toJson(standElem1);
                    editor.putString("STANDELEM", json);
                    editor.apply();


                    view.getContext().startActivity(new Intent(getContext(), Ecran23Activity.class));
                }
            });
        }


        return customView;
    }
}
