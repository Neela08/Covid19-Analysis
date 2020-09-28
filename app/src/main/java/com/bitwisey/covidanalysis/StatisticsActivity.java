package com.bitwisey.covidanalysis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.logging.Logger;

public class StatisticsActivity extends AppCompatActivity {

    PieChart pieChart;
    String country,cname="";
    WebView webView;
    int totalCase,totalRecovered,totalDeaths,active;
    float totalRecoveredPercentage,totalDeathPercentage, activePercentage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);


        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#cc6699")));

       // getSupportActionBar().setTitle(country);
        Window window = StatisticsActivity.this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(ContextCompat.getColor(StatisticsActivity.this,R.color.purple));




        totalCase=0;
        totalRecovered=0;
        totalDeaths=0;
        activePercentage=0;
        Intent i=getIntent();
         country=i.getStringExtra("countryName");
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>"+country+ "</font>"));
         Log.i("getName",country);
         totalCase=i.getIntExtra("totalCase",1);
         totalRecovered=i.getIntExtra("totalRecovered",1);

         totalDeaths=i.getIntExtra("totalDeaths",1);
         active=i.getIntExtra("active",1);
         if(totalRecovered==0 || totalCase==0 || totalDeaths==0){
             Toast.makeText(StatisticsActivity.this,"Data is not updated yet, try again after sometime.",Toast.LENGTH_SHORT).show();

         }
         else
         {
             totalRecoveredPercentage=((float) totalRecovered/(float)totalCase)*100;
             totalDeathPercentage=((float) totalDeaths/(float) totalCase)*100;
             activePercentage=((float) active/(float) totalCase)*100;
             // Log.i("recovered",Float.toString(totalRecoveredPercentage));
             pieChart=(PieChart)findViewById(R.id.piechart_1);
             setPieChart(totalRecoveredPercentage,totalDeathPercentage,activePercentage);
             webView = (WebView) findViewById(R.id.webView);
             //urlSettings(country.trim().toLowerCase());
             showStats(country.toLowerCase().trim());
         }



    }
    public void setPieChart(float first,float second,float third) {


        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(first,"Recovered"));
        yValues.add(new PieEntry(second,"Death"));
        yValues.add(new PieEntry(third,"Active"));


        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(getResources().getColor(R.color.purple),getResources().getColor(R.color.ash),getResources().getColor(R.color.cardview_dark_background ));
        PieData pieData = new PieData((dataSet));
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(R.color.myColor);


         pieChart.setCenterText("% of Total cases");

        pieChart.setData(pieData);

    }
    public String urlSettings(String name)
    {
        if (name.equals("world")) {

            name="analytics";
        }
       else if(name.equals("usa"))
        {
            name="country/"+"united-states/";
        }
        else if(name.equals("s. korea")){
            name="country/"+"south-korea/";

        }
        else
        {
            name="country/"+name.replaceAll(" ","-")+"/";
        }
        return name;

    }



   public void showStats(String countryName)
    { Log.i("passedName",countryName);

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());

       webView.loadUrl("https://www.coronatracker.com/"+urlSettings(countryName));
        Log.i("url","https://www.coronatracker.com/"+urlSettings(countryName));
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }



}

