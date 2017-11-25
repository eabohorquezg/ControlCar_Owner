package unal.edu.co.controlcarowner.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import unal.edu.co.controlcarowner.R;

public class ExplorerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer);

        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");

        setTitle(title);
        setUrl(url);
    }

    private void setUrl(String url) {
        WebView webview = (WebView) findViewById(R.id.webExplorer);
        //webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(url);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
