package yosi.covidinfo.ui.country;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import yosi.covidinfo.R;

public class CountryFragment extends Fragment {
    RecyclerView rvCovidCountry;
    ProgressBar progressBar;
    private static final String TAG = CountryFragment.class.getSimpleName();
    List<Country> countries;
    AdapterCountry adapterCountry;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_country, container, false);

        setHasOptionsMenu(true);

        // call view
        rvCovidCountry = root.findViewById(R.id.country_dunia);
        progressBar = root.findViewById(R.id.progress_circular_country);
        rvCovidCountry.setLayoutManager(new LinearLayoutManager(getActivity()));


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvCovidCountry.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line_divider));
        rvCovidCountry.addItemDecoration(dividerItemDecoration);

        //call list
        countries = new ArrayList<>();

        //call volley method
        getDataFromServer();
        return root;
    }

    private void showRecyclerView(){
        adapterCountry = new AdapterCountry(countries, getActivity());
        rvCovidCountry.setAdapter((adapterCountry));

//        Menampilan detail setiap item yang ada pada recylerview
        ItemClickSupport.addTo(rvCovidCountry).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedCountry(countries.get(position));
            }
        });
    }

    private void showSelectedCountry(Country country){
        Intent covidCovidCountryDetail = new Intent(getActivity(), CovidCountryDetail.class);
        covidCovidCountryDetail.putExtra("Covid_19", country);
        startActivity(covidCovidCountryDetail);
    }

    private void getDataFromServer() {
        String url = "https://corona.lmao.ninja/v2/countries";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                if (response!=null) {
                    Log.e(TAG, "onResponse: " + response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);
                            // Extra JSONObject inside JSONObject
                            JSONObject countryInfo = data.getJSONObject("countryInfo");

                            countries.add(new Country(data.getString("country"), data.getString("cases"),
                                    data.getString("todayCases"), data.getString("deaths"), data.getString("todayDeaths"),
                                    data.getString("recovered"),data.getString("active"),
                                    data.getString("critical"), data.getString("continent"),countryInfo.getString("flag")
                            ));
                        }
                        //Action bar title
                        getActivity().setTitle(jsonArray.length()+" countries");
                        showRecyclerView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.e(TAG, "onResponse: "+error);
                    }
                });

        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(getActivity());
        searchView.setQueryHint("Search...");
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapterCountry!=null){
                    adapterCountry.getFilter().filter(newText);
                }
                return true;
            }
        });
        searchItem.setActionView(searchView);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
