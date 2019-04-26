package com.nikaas.suyash.inscore;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView list;
    ScoreAdapter scoreAdapter;
    CarouselView carouselView;
    FloatingActionButton refresh;


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
        setContentView(R.layout.activity_list);

        final int pager[] = {R.drawable.wallpeper,R.drawable.pl,R.drawable.cwc};

        refresh = findViewById(R.id.refresh);

        refresh.setImageResource(R.drawable.ic_refresh_black_24dp);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new CricketTask().execute();

            }
        });

        carouselView = findViewById(R.id.carusel_view);
        carouselView.setPageCount(pager.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(pager[position]);
            }
        });


        list = findViewById(R.id.cricket_list);
        scoreAdapter = new ScoreAdapter(this,R.layout.item_scores);

        list.setAdapter(scoreAdapter);

        CricketTask cricketTask = new CricketTask();
        cricketTask.execute();

        //Intents for important Cricketing Links


        // For ICC
//        ImageView icc = findViewById(R.id.icc);
//
//        icc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Uri uri = Uri.parse("http://www.icc-cricket.com");
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//
//
//                if (intent.resolveActivity(getPackageManager()) != null)
//                    startActivity(intent);
//
//            }
//        });
//
//
//        //For ESPN
//        ImageView espn = findViewById(R.id.espn);
//
//        espn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//
//                Uri uri = Uri.parse("http://www.espncricinfo.com");
//
//                intent.setData(uri);
//
//                if (intent.resolveActivity(getPackageManager()) != null)
//                    startActivity(intent);
//
//            }
//        });
//
//        //For Cricbuzz
//
//        ImageView cricbuzz = findViewById(R.id.cricbuzz);
//
//        cricbuzz.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//
//                Uri uri = Uri.parse("http://www.cricbuzz.com");
//
//                intent.setData(uri);
//
//                if (intent.resolveActivity(getPackageManager()) != null)
//                    startActivity(intent);
//
//            }
//        });

    }



    public void updateData(ArrayList<String> listData) {

        for(int i = 0;i<listData.size();i++){

            String str = listData.get(i);

            String arr[] = str.split("v");

            String one = arr[0].trim();
            String two = arr[1].trim();

            Map<String,String> data1 = new HashMap<>();
            Map<String,String> data2 = new HashMap<>();

            if(one.contains("/")) {
                if (one.contains("*")) {
                    data1 = getScore(one, 2);
                } else {
                    data1 = getScore(one, 1);
                }
            }
            else{
                data1.put("team",one);
                data1.put("score","0/0");
            }

            if(two.contains("/")) {
                if (two.contains("*")) {
                    data2 = getScore(two, 2);
                } else {
                    data2 = getScore(two, 1);
                }
            }
            else{
                data2.put("team",two);
                data2.put("score","0/0");
            }

            Score score = new Score(data1.get("team"),data2.get("team"),data1.get("score"),data2.get("score"));

            scoreAdapter.add(score);
            scoreAdapter.notifyDataSetChanged();
        }



//        TextView score = findViewById(R.id.score);
//
//        ProgressBar progressBar = findViewById(R.id.pbar);
//
//        progressBar.setIndeterminate(true);
//        progressBar.setVisibility(View.INVISIBLE);
//
//
//        score.setText(s);




    }

    public Map<String,String> getScore(String str, int offset){

        String temp[] = str.split(" ");


        String team = "";
        String score;

        if(offset==2)
        score = temp[temp.length - offset] + temp[temp.length - (offset - 1)];
        else
            score = temp[temp.length - 1];

        int index = 0;

        while (index!=temp.length-offset){
            team = team.concat(temp[index++]);
        }

        Map<String,String> map = new HashMap<>();

        map.put("team",team);
        map.put("score",score);

        return map;

    }


    private class CricketTask extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            try {
                return CricUtils.getJSON(dataURL);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);

            updateData(strings);
        }
    }
}


    /**
     * Created by Suyash on 12/4/2017.
     */

