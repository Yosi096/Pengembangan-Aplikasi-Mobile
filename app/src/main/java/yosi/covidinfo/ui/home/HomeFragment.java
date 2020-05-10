package yosi.covidinfo.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import yosi.covidinfo.R;

public class HomeFragment extends Fragment {
    private TextView total_konfirmasi, total_sembuh, total_meninggal,meninggal_today,kasus_today,last_update;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "STAY AT HOME", Toast.LENGTH_SHORT).show();
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //call view
        total_konfirmasi = root.findViewById(R.id.total_konfirmasi2);
        total_sembuh = root.findViewById(R.id.sembuh2);
        total_meninggal = root.findViewById(R.id.meninggal2);
        kasus_today = root.findViewById(R.id.todayCases);
        meninggal_today = root.findViewById(R.id.todayDeaths);
        progressBar = root.findViewById(R.id.progress_circular_home);

        //Action Bar Title
        getActivity().setTitle("InfoCov");
        last_update = root.findViewById(R.id.last_update);
        //call volley
        getData();
        return root;
    }

    private String getDate(long milliSecond){
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss aaa");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSecond);
        return formatter.format(calendar.getTime());
    }

    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://corona.lmao.ninja/v2/all";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    total_konfirmasi.setText(jsonObject.getString("cases"));
                    total_sembuh.setText(jsonObject.getString("recovered"));
                    total_meninggal.setText(jsonObject.getString("deaths"));
                    kasus_today.setText(jsonObject.getString("todayCases"));
                    meninggal_today.setText(jsonObject.getString("todayDeaths"));
                    last_update.setText("Last Updated"+"\n"+getDate(jsonObject.getLong("updated")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.d("Error Response", error.toString());
            }
        });

        queue.add(stringRequest);
    }
}
