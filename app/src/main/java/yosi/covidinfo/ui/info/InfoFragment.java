package yosi.covidinfo.ui.info;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import yosi.covidinfo.LoginActivity;
import yosi.covidinfo.R;
import yosi.covidinfo.RegisterActivity;

public class InfoFragment extends Fragment {
    ProgressBar progressBar;
    RelativeLayout relativeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_info, container, false);
        relativeLayout = root.findViewById(R.id.info_lay);
        Button btn_log = (Button) root.findViewById(R.id.btn_log);
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), LoginActivity.class);
                startActivity(in);
            }
        });
        getActivity().setTitle("InfoCov");
        return root;
    }
}