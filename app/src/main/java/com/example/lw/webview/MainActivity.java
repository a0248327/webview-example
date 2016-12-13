package com.example.lw.webview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private WebView webView;
    private Button goButton, backButton;
    private EditText urlEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();
        //控件设置监听事件
        setViewListener();

    }

    private void initView() {
        goButton = (Button) findViewById(R.id.goButton);
        backButton = (Button) findViewById(R.id.backButton);
        urlEditText = (EditText) findViewById(R.id.urlEditText);
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);//设置可以设置JavaScript脚本
        webView.setWebChromeClient(new WebChromeClient());//处理JavaScript对话框
        //处理各种通知与请求事件，如果不使用该句代码，将使用内置浏览器访问网页
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // handler.cancel(); // 默认的处理方式，WebView变成空白页
                handler.proceed(); // 接受证书
                // handleMessage(Message msg); // 其他处理
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://tarif.manz.at");
        Toast.makeText(MainActivity.this, "Loading..." + urlEditText.getText().toString(), Toast.LENGTH_LONG).show();
    }

    private void setViewListener() {
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (urlEditText.getText().toString().equals("")) {
                    showDialog();
                } else {
                    openBrower();

                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.goBack();
            }
        });

        urlEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (!urlEditText.getText().toString().equals("")) {
                        openBrower();
                        return true;
                    } else {

                        showDialog();
                    }
                }
                return false;
            }
        });
    }

    //显示提示对话框
    private void showDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Android Webbrowser")
                .setMessage("Please enter a website: ")
                .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("WebView", "Enter");
                    }
                }).show();
    }

    //用于浏览页面
    private void openBrower() {
        webView.loadUrl(urlEditText.getText().toString());
        Toast.makeText(MainActivity.this, "Loading..." + urlEditText.getText().toString(), Toast.LENGTH_LONG).show();
    }
}
