package yosi.covidinfo.ui.country;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import yosi.covidinfo.R;

public class CovidCountryDetail extends AppCompatActivity {
    TextView countryName,totalCases, totalCasesToday, totalDeath, totalDeathToday, totalRecovered, totatlActive, totalCritical, totalContinent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_country_detail);

        // manggil view
        countryName = findViewById((R.id.DetailCountryName));
        totalCases = findViewById(R.id.totalCases2);
        totalCasesToday = findViewById(R.id.totalCasesToday);
        totalDeath = findViewById(R.id.totalDeath);
        totalDeathToday = findViewById(R.id.totalDeathToday);
        totalRecovered = findViewById(R.id.totalRecovered);
        totatlActive = findViewById(R.id.totalActive);
        totalCritical = findViewById(R.id.totalCritical);
        totalContinent = findViewById(R.id.totalContinent);

        // manggil Country
        Country country = getIntent().getParcelableExtra("Covid_19");
        countryName.setText(country.getmCovidCountry());
        totalCases.setText(country.getmCases());
        totalCasesToday.setText(country.getmTodayCases());
        totalDeath.setText(country.getmDeaths());
        totalDeathToday.setText(country.getmTodayDeaths());
        totalRecovered.setText(country.getmRecovered());
        totatlActive.setText(country.getmActive());
        totalCritical.setText(country.getmCritical());
        totalContinent.setText(country.getmContinent());
    }
}
