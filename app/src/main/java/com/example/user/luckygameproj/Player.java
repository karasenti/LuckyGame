package com.example.user.luckygameproj;

/**
 * Created by User on 14-Jul-16.
 */
public class Player {
    private String PlayKey;
    private String date;
    private String time;
    private int score;

    public Player() {
    }

    public Player(String date, String time, int score) {
        this.date = date;
        this.time = time;
        this.score = score;
    }

    public String getPlayKey() {
        return PlayKey;
    }

    public void setPlayKey(String playKey) {
        this.PlayKey = playKey;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return date + " " + time + " result : " +  score ;
    }
}
