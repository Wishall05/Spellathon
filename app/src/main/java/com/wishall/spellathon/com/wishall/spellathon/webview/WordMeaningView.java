package com.wishall.spellathon.com.wishall.spellathon.webview;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.wishall.spellathon.R;

/**
 * Created by vipandey on 07/07/17.
 */

public class WordMeaningView extends Activity{
    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_meaning_view);

        Bundle bundle = getIntent().getExtras();
        String word = bundle.getString("WORD");

        webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.dictionary.com/browse/"+word+"?s=t");

    }
}
