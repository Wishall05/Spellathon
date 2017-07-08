package com.wishall.spellathon.com.wishall.spellathon.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * Created by vipandey on 07/07/17.
 */

public class WordClickListener implements OnClickListener {

    private Context context;

    public WordClickListener(final Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, WordMeaningView.class);
        Bundle bundle = new Bundle();
        //Add your data from getFactualResults method to bundle
        bundle.putString("WORD", ((TextView)view).getText().toString());
        //Add the bundle to the intent
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
