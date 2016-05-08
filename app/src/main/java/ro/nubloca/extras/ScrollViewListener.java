package ro.nubloca.extras;

import ro.nubloca.extras.ObservableScrollView;

public interface ScrollViewListener {
    void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);

}
