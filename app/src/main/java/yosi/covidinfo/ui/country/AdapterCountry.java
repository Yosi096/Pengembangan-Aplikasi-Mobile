package yosi.covidinfo.ui.country;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import yosi.covidinfo.R;

public class AdapterCountry extends RecyclerView.Adapter<AdapterCountry.ViewHolder> implements Filterable {
    private List<Country> covidCountries;
    private List<Country> covidCountriesFull;
    private Context context;

    public AdapterCountry(List<Country> covidCountries, Context context) {
        this.covidCountries = covidCountries;
        this.context = context;
        covidCountriesFull = new ArrayList<>(covidCountries);
    }

    @NonNull
    @Override
    public AdapterCountry.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_covid_country, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Country country = covidCountries.get(position);
        holder.tvTotalCases.setText(country.getmCases());
        holder.tvCountryName.setText(country.getmCovidCountry());

        //Glide
        Glide.with(context)
                .load(country.getmFlag())
                .apply(new RequestOptions().override(240,160))
                .into(holder.imgCountryFlag);
    }

    @Override
    public int getItemCount() {
        return covidCountries.size();
    }

    @Override
    public Filter getFilter() {
        return covidCountriesFilter;
    }

    private Filter covidCountriesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Country> filteredCovidCountry = new ArrayList<>();
            if (constraint==null||constraint.length()==0){
                filteredCovidCountry.addAll(covidCountriesFull);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Country itemCovidCountry:covidCountriesFull){
                    if (itemCovidCountry.getmCovidCountry().toLowerCase().contains(filterPattern)){
                        filteredCovidCountry.add(itemCovidCountry);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredCovidCountry;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            covidCountries.clear();
            covidCountries.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTotalCases, tvCountryName;
        ImageView imgCountryFlag;
        public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTotalCases = itemView.findViewById(R.id.tvTotalCases);
                tvCountryName = itemView.findViewById(R.id.tvCountryName);
                imgCountryFlag = itemView.findViewById(R.id.imgCountryFlag);
        }
    }
}
