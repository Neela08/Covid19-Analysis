package com.bitwisey.covidanalysis;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bitwisey.covidanalysis.Adapter.CustomAdapter;
import com.bitwisey.covidanalysis.Models.Countries;
import com.bitwisey.covidanalysis.Networking.Client;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
   String number="16263";
   TextView wv1,wv2,wv3,wv4,bv1,bv2,bv3;int rec,con,de;
   ProgressDialog  progressDialog;
   List<Countries> country;
    private static final int REQUEST_CALL=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#cc6699")));
        Window window = MainActivity.this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.purple));
        wv1=(TextView)findViewById(R.id.tv1);
        wv2=(TextView)findViewById(R.id.tv2);
        wv3=(TextView)findViewById(R.id.tv3);
        bv1=(TextView)findViewById(R.id.bd1);
        bv2=(TextView)findViewById(R.id.bd2);
        bv3=(TextView)findViewById(R.id.bd3);
        country=new ArrayList<>();

        getInfo();

    }

    public void stats(View view) {
        Intent i=new Intent(MainActivity.this,ListActivity.class);
        startActivity(i);
    }

    public void symptom(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://livecoronatest.com/chat.php?city=Unnamed%20Road&lat=23.820264&lng=90.417367&addr_dist=Unknown&addr_div=Unknown"));
        startActivity(browserIntent);
    }

    public void call(View view) {
        makephonecall(number);

    }
    private void makephonecall(String num){

        if(num.trim().length()>0)
        {
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }
            else
            {
                String dial="tel:"+num;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
        else
        {
            Toast.makeText(this,"Couldnt make call",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CALL)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                makephonecall(number);
            }
            else{
                Toast.makeText(this,"permission denied",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void getInfo()
    {




            Call<List<Countries>> call=Client.getInstance().getMyApi().getCases();

            call.enqueue(new Callback<List<Countries>>() {
                @Override
                public void onResponse(Call<List<Countries>> call, Response<List<Countries>> response) {


                    if (response.isSuccessful() && response.body() != null){

                        country=response.body();
                        wv1.setText(Integer.toString(country.get(0).getTotalConfirmed()));
                        wv2.setText(Integer.toString(country.get(0).getTotalRecovered()));
                        wv3.setText(Integer.toString(country.get(0).getTotalDeaths()));
                        searchBangladesh(response.body());
                        bv1.setText(Integer.toString(con));
                        bv2.setText(Integer.toString(rec));
                        bv3.setText(Integer.toString(de));


                    }
                    else
                    {

                        Toast.makeText(MainActivity.this,"Couldn't find data or rate limit was pulled !",Toast.LENGTH_SHORT).show();

                    }


                }

                @Override
                public void onFailure(Call<List<Countries>> call, Throwable t) {


                    Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });

    }
    public void searchBangladesh(List<Countries> list)
    {
       for(int i=0;i<list.size();i++)
       {
           if(list.get(i).getCountry().equals("Bangladesh"))
           {
               con=list.get(i).getTotalConfirmed();
               rec=list.get(i).getTotalRecovered();
               de=list.get(i).getTotalDeaths();
               break;
           }
       }
    }
}
