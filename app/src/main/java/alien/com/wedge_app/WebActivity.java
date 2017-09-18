package alien.com.wedge_app;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    private String rdID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);


//        rdID = getIntent().getExtras().getString("rfid");

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        ;
        webView.getSettings().setDomStorageEnabled(true);

        // Add the custom WebViewClient class
        //webView.setWebViewClient(new CustomWebViewClient());

        // Add the javascript interface
        // webView.addJavascriptInterface(new JavaScriptInterface(), "interface");

        // Load the example html file to the WebView
        // webView.loadUrl("http://www.google.com");
        // webView.loadUrl("http://10.5.1.23:7070/sensornetRFID45/html5/iappat/login/html/at.html#1");
        webView.loadUrl("http://10.5.1.23:7070/sensornetRFID45/html5/iappat/ui/html/common/servermenu.html");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                CookieSyncManager.getInstance().sync();

                final String password = "password";

                final String js = "javascript:" +
                        "document.getElementById('tags_txt').value = '" + password + "';";

                if (Build.VERSION.SDK_INT >= 19) {
                    view.evaluateJavascript(js, new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {

                        }
                    });
                } else {
                    view.loadUrl(js);
                }
            }
        });
    }
/*
    *//**
     * Onclick callback method for Button.
     *
     * @param view
     *//*
    public void onButtonClick(View view) {
        //webView.loadUrl("javascript:callFromApp(" + rdID + ");");
        webView.loadUrl("javascript:callFromApp(" + rdID + ");");
    }

    *//**
     * Onclick callback method for Button.
     *
     * @param view
     *//*
    public void onFunctionValue(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript("javascript:callFromAppWithReturn();", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    Toast.makeText(WebActivity.this, s, Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(WebActivity.this, "evaluateJavascript call require api level 19 (KitKat)", Toast.LENGTH_LONG).show();
        }
    }

    *//**
     * CustomWebViewClient is used to add a custom hook to the url loading.
     *//*
    private class CustomWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            // If the url to be loaded starts with the custom protocol, skip
            // loading and do something else
            if (url.startsWith("tanelikorri://")) {

                Toast.makeText(WebActivity.this, "Custom protocol call", Toast.LENGTH_LONG).show();

                return true;
            }

            return false;
        }
    }

    *//**
     * JavaScriptInterface is the interface class for the application code calls. All public methods
     * annotated with {@link android.webkit.JavascriptInterface JavascriptInterface } in this class
     * can be called from JavaScript.
     *//*
    private class JavaScriptInterface {

        @JavascriptInterface
        public void callFromJS() {
            Toast.makeText(WebActivity.this, "JavaScript interface call", Toast.LENGTH_LONG).show();
        }
    }*/
}
