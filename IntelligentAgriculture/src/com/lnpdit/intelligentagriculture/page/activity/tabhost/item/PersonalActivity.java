package com.lnpdit.intelligentagriculture.page.activity.tabhost.item;
import com.lnpdit.intelligentagriculture.R;
import com.lnpdit.intelligentagriculture.base.component.BaseActivity;
import com.lnpdit.intelligentagriculture.customview.FocusedtrueTV;

import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;

public class PersonalActivity extends BaseActivity{
	/** Called when the activity is first created. */
    private WebView webview;
    private FocusedtrueTV tv_title;
    Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_personal);

        initView();
        setUI();
    }

    private void initView() {
        this.isParentActivity = false;

        webview = (WebView) findViewById(R.id.webview);
        tv_title = (FocusedtrueTV) findViewById(R.id.tv_title);
        tv_title.setText("个人");
  }  
    public void setUI() {
        webview.loadUrl("http://backlogin.guzhang.tv/agreement/index.html");
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}