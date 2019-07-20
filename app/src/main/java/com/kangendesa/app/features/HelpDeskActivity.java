package com.kangendesa.app.features;

import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.kangendesa.app.R;
import com.kangendesa.app.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by agustinaindah on 27 Februari 2019
 */
public class HelpDeskActivity extends BaseActivity {

    String ShowOrHideWebViewInitialUse = "show";

    @BindView(R.id.wvHelpDesk)
    WebView wvHelpDesk;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        upArrow.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Pusat Bantuan");

        wvHelpDesk.setWebViewClient(new myWebClient());
        wvHelpDesk.getSettings().setJavaScriptEnabled(true);
        wvHelpDesk.getSettings().setDomStorageEnabled(true);
        wvHelpDesk.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        wvHelpDesk.loadUrl("http://kangendesa.com/bantuan/");
    }

    @Override
    protected int setView() {
        return R.layout.activity_help_desk_webview;
    }

    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            if (ShowOrHideWebViewInitialUse.equals("show")) {
                wvHelpDesk.setVisibility(wvHelpDesk.INVISIBLE);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
//            progressBar1.setVisibility(View.VISIBLE);
//            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            ShowOrHideWebViewInitialUse = "hide";
            progressBar1.setVisibility(View.GONE);

            view.setVisibility(wvHelpDesk.VISIBLE);
            super.onPageFinished(view, url);
        }
    }

    // To handle "Back" key press event for WebView to go back to previous screen.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wvHelpDesk.canGoBack()) {
            wvHelpDesk.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
