package sketch.m_hospital.com.m_hospital.Fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.biubiubiu.justifytext.library.JustifyTextView;
import sketch.m_hospital.com.m_hospital.R;

/**
 * Created by Developer on 7/26/17.
 */

public class FragH_Health_expanded extends Fragment {
    Context context;
    String description ;
    JustifyTextView text ;TextView blog_name;

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_h_health_expanded, container, false);
        TextView tooltext=getActivity().findViewById(R.id.text_toolbar);
        //tooltext.setText(getString(R.string.HealthBlogDescription));
        tooltext.setText(getString(R.string.HealthBlogDescription));
        text = view.findViewById(R.id.text);
        blog_name = view.findViewById(R.id.blog_name);
        description=getArguments().getString("description");
        Spanned s = Html.fromHtml(description,Html.FROM_HTML_MODE_LEGACY);
        Spanned s1 = Html.fromHtml(description);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            text.setText(s.toString());
        } else {
            text.setText(s1.toString());
        }



        blog_name.setText(getArguments().getString("name"));
        return view;
    }

}