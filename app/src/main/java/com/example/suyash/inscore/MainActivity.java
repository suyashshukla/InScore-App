package com.example.suyash.inscore;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    static String dataURL = "http://cricapi.com/api/cricket?apikey=RGYaXlSsxQZpCtU6Z8o9jXngLuq1";

    @Override
    protected void onRestart() {
        super.onRestart();

        CricketTask cricketTask = new CricketTask();
        cricketTask.execute();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CricketTask cricketTask = new CricketTask();
        cricketTask.execute();

        //Intents for important Cricketing Links


        // For ICC
        ImageView icc = findViewById(R.id.icc);

        icc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("http://www.icc-cricket.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);


                if (intent.resolveActivity(getPackageManager()) != null)
                    startActivity(intent);

            }
        });


        //For ESPN
        ImageView espn = findViewById(R.id.espn);

        espn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);

                Uri uri = Uri.parse("http://www.espncricinfo.com");

                intent.setData(uri);

                if (intent.resolveActivity(getPackageManager()) != null)
                    startActivity(intent);

            }
        });

        //For Cricbuzz

        ImageView cricbuzz = findViewById(R.id.cricbuzz);

        cricbuzz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);

                Uri uri = Uri.parse("http://www.cricbuzz.com");

                intent.setData(uri);

                if (intent.resolveActivity(getPackageManager()) != null)
                    startActivity(intent);

            }
        });

    }



    public void updateData(String s) {
        TextView score = findViewById(R.id.score);

        ProgressBar progressBar = findViewById(R.id.pbar);

        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.INVISIBLE);


        score.setText(s);




    }


    private class CricketTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {

            if(s==null)
                s="No Internet Connection";


            Log.v("MainActivity", "Value of String is " + s);

            StringBuilder stringBuilder = new StringBuilder();

            String words[] = s.split(" ");

            for (int i = 0; i < words.length; i++) {
                words[i].trim();

                if(words[i].equals("Sri"))
                    words[i]="\n"+words[i];
                if (!words[i].equals("v") && !words[i].equals("&amp;") && !words[i].equals("*"))
                    stringBuilder.append(words[i]+" ");
                if(words[i].equals("India") || words[i].equals("Lanka"))
                    stringBuilder.append("\n");




            }


            updateData(stringBuilder.toString());
        }


        @Override
        protected String doInBackground(String... strings) {
            
            try {

                return CricUtils.getJSON(dataURL);

            } catch (IOException e) {

                return null;

            }


        }
    }
}


    /**
     * Created by Suyash on 12/4/2017.
     */

