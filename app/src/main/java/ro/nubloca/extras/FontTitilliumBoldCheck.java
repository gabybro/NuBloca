package ro.nubloca.extras;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckedTextView;
import android.widget.Spinner;

public class FontTitilliumBoldCheck extends CheckedTextView {

    public FontTitilliumBoldCheck(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }
    public FontTitilliumBoldCheck(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
    public FontTitilliumBoldCheck(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    public void setTypeface(Typeface tf, int style) {
        if(!this.isInEditMode()){
            Typeface normalTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/TitilliumText22L005.otf");
            Typeface boldTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/TitilliumText22L005.otf");

            if (style == Typeface.BOLD) {
                super.setTypeface(boldTypeface/*, -1*/);
            } else {
                super.setTypeface(normalTypeface/*, -1*/);
            }
        }

    }
}