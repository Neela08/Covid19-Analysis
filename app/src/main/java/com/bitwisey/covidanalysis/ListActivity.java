package com.bitwisey.covidanalysis;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bitwisey.covidanalysis.Adapter.CustomAdapter;
import com.bitwisey.covidanalysis.Models.Countries;
import com.bitwisey.covidanalysis.Networking.Client;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Countries> country = new ArrayList<>();
    private CustomAdapter adapter;
    ProgressDialog progressDialog;
    MenuItem menuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#cc6699")));
        Window window = ListActivity.this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(ContextCompat.getColor(ListActivity.this,R.color.purple));

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(ListActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        showList();

    }
    public void showList()
    {
        progressDialog = new ProgressDialog(ListActivity.this);
        progressDialog.setMessage("Refreshing....");
        progressDialog.show();

        Call<List<Countries>> call=Client.getInstance().getMyApi().getCases();

        call.enqueue(new Callback<List<Countries>>() {
            @Override
            public void onResponse(Call<List<Countries>> call, Response<List<Countries>> response) {


                if (response.isSuccessful() && response.body() != null){
                    country=response.body();
                    adapter = new CustomAdapter(country, ListActivity.this);
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();
                    adapter.notifyDataSetChanged();
                    menuItem.setVisible(true);
                    initListener();
                }
                else
                {
                    progressDialog.dismiss();

                    Toast.makeText(ListActivity.this,"Couldn't find data or rate limit was pulled !",Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<List<Countries>> call, Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(ListActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
         menuItem = menu.findItem(R.id.action_search);
         menuItem.setVisible(false);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }
    private void initListener(){

        adapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Countries countries = country.get(position);
                Intent i=new Intent(ListActivity.this,StatisticsActivity.class);
                if(!country.isEmpty())
                {
                    i.putExtra("totalCase",countries.getTotalConfirmed());
                    i.putExtra("totalDeaths",countries.getTotalDeaths());
                    i.putExtra("totalRecovered",countries.getTotalRecovered());
                    i.putExtra("active",countries.getactive());
                    i.putExtra("countryName",countries.getCountry());
                  //  Toast.makeText(ListActivity.this,Integer.toString(countries.getTotalConfirmed()),Toast.LENGTH_SHORT).show();

                }
                startActivity(i);


                //Toast.makeText(ListActivity.this,countries.getCountry(),Toast.LENGTH_SHORT).show();

            }
        });

    }
}
