package com.nikaas.suyash.inscore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

public class ScoreAdapter extends ArrayAdapter<Score> {

    int resource;
    Context context;

    public ScoreAdapter(@NonNull Context context, int resource) {
        super(context, resource);

        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null)
            convertView = LayoutInflater.from(context).inflate(resource,parent,false);

        Score score = getItem(position);

        TextView team1 = convertView.findViewById(R.id.team1);
        TextView team2 = convertView.findViewById(R.id.team2);

        ImageView score1 = convertView.findViewById(R.id.score1);
        ImageView score2 = convertView.findViewById(R.id.score2);

        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;

        int color1 = colorGenerator.getColor(score.getTeam1());
        int color2 = colorGenerator.getColor(score.getTeam2());

        TextDrawable textDrawable1 = TextDrawable.builder()
                .beginConfig()
                .fontSize(30)
                .withBorder(5)
                .endConfig()
                .buildRoundRect(score.getScore1(),color1,5);

        TextDrawable textDrawable2 = TextDrawable.builder()
                .beginConfig()
                .fontSize(30)
                .withBorder(5)
                .endConfig()
                .buildRoundRect(score.getScore2(),color2,5);

        team1.setText(score.getTeam1());
        team2.setText(score.getTeam2());

        score1.setImageDrawable(textDrawable1);
        score2.setImageDrawable(textDrawable2);

        return convertView;
    }
}
