package ro.nubloca.Networking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ro.nubloca.Ecran20Activity;
import ro.nubloca.Ecran23Activity;
import ro.nubloca.Ecran26Activity;
import ro.nubloca.R;
import ro.nubloca.extras.CustomFontTitilliumRegular;
import ro.nubloca.extras.Global;

/**
 * Created by GABY_ on 20.06.2016.
 */

public class CustomAdapter extends ArrayAdapter<String> {
    StandElem standElemAdapter;
    Context contextAdapter;
    Activity mActivity;

    public CustomAdapter(Context context, String[] elemente, StandElem standElem, Activity mActivity1) {
        super(context, R.layout.raw_list, elemente);
        standElemAdapter = standElem;
        contextAdapter = context;
        mActivity = mActivity1;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        LayoutInflater dapter = LayoutInflater.from(getContext());
        View customView = dapter.inflate(R.layout.raw_list, parent, false);

        String singleFood = getItem(position);
        CustomFontTitilliumRegular textul = (CustomFontTitilliumRegular) customView.findViewById(R.id.text);
        final ImageView imaginea = (ImageView) customView.findViewById(R.id.radioButton);
        LinearLayout ll = (LinearLayout) customView.findViewById(R.id.linear1);
        LinearLayout lll = (LinearLayout) customView.findViewById(R.id.linear2);
        if (standElemAdapter.getPositionExemplu() == position) {
            lll.setBackgroundColor(Color.parseColor("#F0F0F0"));
        }
        textul.setText(singleFood);
        if (standElemAdapter.getSelected() == position) {
            imaginea.setImageResource(R.drawable.radio_press);
        } else {
            imaginea.setImageResource(R.drawable.radio);
        }



        if (ll != null) {
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    standElemAdapter.setSelected(position);
                    standElemAdapter.setPositionExemplu(-1);
                    ((Global) getContext()).setStandElem(standElemAdapter);
                    view.getContext().startActivity(new Intent(getContext(), Ecran20Activity.class));
                    mActivity.finish();              }
            });
        }


        if (lll != null) {

            lll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    standElemAdapter.setPositionExemplu(position);
                    view.getContext().startActivity(new Intent(getContext(), Ecran26Activity.class));
                }
            });
        }

        return customView;
    }
}
