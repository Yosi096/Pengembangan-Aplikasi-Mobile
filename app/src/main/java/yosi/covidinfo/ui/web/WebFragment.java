package yosi.covidinfo.ui.web;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import yosi.covidinfo.R;


public class WebFragment extends Fragment {
    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_web, container, false);
        Toast.makeText(getActivity(),"Situs Nasional (situs resmi penanganan Covid-19 untuk masyarakat)",Toast.LENGTH_SHORT).show();
        webView = (WebView)root.findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true); //mengaktifkan javascript
        webView.loadUrl("https://covid19.go.id");
        getActivity().setTitle("InfoCov");
        return root;
    }
}
