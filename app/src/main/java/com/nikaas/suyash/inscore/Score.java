package com.nikaas.suyash.inscore;

public class Score {

    String team1;
    String team2;

    String score1;
    String score2;

    public Score(String team1, String team2, String score1, String score2){

        this.team1 = team1;
        this.team2 = team2;
        this.score1 = score1;
        this.score2 = score2;

    }

    public String getScore1() {
        return score1;
    }

    public String getScore2() {
        return score2;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }
}
