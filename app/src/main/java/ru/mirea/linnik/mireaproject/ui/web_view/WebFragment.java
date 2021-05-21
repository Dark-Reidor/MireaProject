package ru.mirea.linnik.mireaproject.ui.web_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ru.mirea.linnik.mireaproject.R;

public class WebFragment extends Fragment {
    WebView webView;
    EditText editTextLink;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_web_view, container, false);
        webView = root.findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://mail.ru/?utm_source=portal&utm_medium=portal_navigation&utm_campaign=mail.ru");
        editTextLink = root.findViewById(R.id.link_search);

        View.OnClickListener homePageClickListener = view -> webView.loadUrl("https://mail.ru/?utm_source=portal&utm_medium=portal_navigation&utm_campaign=mail.ru");
        root.findViewById(R.id.home_page).setOnClickListener(homePageClickListener);

        View.OnClickListener searchClickListener = view -> webView.loadUrl(editTextLink.getText().toString());
        root.findViewById(R.id.search_option).setOnClickListener(searchClickListener);
        return root;
    }
}