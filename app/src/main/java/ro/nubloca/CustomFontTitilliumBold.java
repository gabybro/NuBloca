package ro.nubloca;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by GABY_ on 22.04.2016.
 */
public class CustomFontTitilliumBold extends TextView {

    public CustomFontTitilliumBold(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public CustomFontTitilliumBold(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public CustomFontTitilliumBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/TitilliumText22L005.otf", context);
        setTypeface(customFont);
    }
}