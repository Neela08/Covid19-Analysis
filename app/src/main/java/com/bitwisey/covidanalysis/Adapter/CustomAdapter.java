package com.bitwisey.covidanalysis.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.bitwisey.covidanalysis.Models.Countries;
import com.bitwisey.covidanalysis.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements Filterable {

    private List<Countries> countries;
    private Context context;
    private OnItemClickListener onItemClickListener;
    List<Countries> countryList;

    public CustomAdapter(List<Countries> countries, Context context) {
        this.countries = countries;
        this.context = context;

        countryList = new ArrayList<Countries>();
        countryList.addAll(countries);


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_row, parent, false);
        return new MyViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
        final MyViewHolder holder = holders;
        Countries model = countries.get(position);
        if(position==0)
        {
            holder.countryName.setText(model.getCountry());
        }
        else
        {
            holder.countryName.setText(position+". Country: "+model.getCountry());
        }


        holder.newConfirmed.setText("Today new Cases: "+Integer.toString(model.getNewConfirmed()));
        holder.totalConfirmed.setText("Total Confirmed: "+Integer.toString(model.getTotalConfirmed()));
        holder.newDeaths.setText("Today new Deaths: "+Integer.toString(model.getNewDeaths()));
        holder.totalDeaths.setText("Total Deaths: "+Integer.toString(model.getTotalDeaths()));
        holder.active.setText("Total Active: "+Integer.toString(model.getactive()));
        holder.totalrecovered.setText("Total Recovered: "+Integer.toString(model.getTotalRecovered()));

    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {

        //Automatic on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Countries> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(countryList);
            } else {
                for (Countries c: countries) {
                    if (c.getCountry().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(c);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        //Automatic on UI thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            countries.clear();
            countries.addAll((Collection<? extends Countries>) filterResults.values);
            notifyDataSetChanged();
        }
    };
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        TextView countryName,newConfirmed,totalConfirmed,newDeaths,totalDeaths,active,totalrecovered;

        OnItemClickListener onItemClickListener;

        public MyViewHolder(View itemView, OnItemClickListener onItemClickListener) {

            super(itemView);

            itemView.setOnClickListener(this);

            itemView.setOnClickListener(this);
            countryName = itemView.findViewById(R.id.country);
            totalConfirmed = itemView.findViewById(R.id.TotalConfirmed);
            newConfirmed = itemView.findViewById(R.id.NewConfirmed);
            newDeaths = itemView.findViewById(R.id.NewDeaths);
            totalDeaths = itemView.findViewById(R.id.TotalDeaths);
            active = itemView.findViewById(R.id.active);
            totalrecovered=itemView.findViewById(R.id.TotalRecovered);


            this.onItemClickListener = onItemClickListener;

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}

