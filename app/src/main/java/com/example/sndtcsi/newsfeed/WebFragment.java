package com.example.sndtcsi.newsfeed;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebFragment extends Fragment
{
    WebView wv;
    String title, url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_web,container,false);

        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            url = bundle.getString("url", "url");
            title = bundle.getString("title", "title");
        }

        if(title.matches("title"))
        {
            title = "";
        }

        getActivity().setTitle(title);

        wv = (WebView) rootView.findViewById(R.id.newsWebView);
        wv.setWebViewClient(new MyWebViewClient());
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        wv.getSettings().setDomStorageEnabled(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl(url);

        return rootView;
    }

    public class MyWebViewClient extends WebViewClient
    {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }
    }
}
