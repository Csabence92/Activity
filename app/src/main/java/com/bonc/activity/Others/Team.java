package com.bonc.activity.Others;

import android.content.Context;
import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.bonc.activity.R;

import java.util.ArrayList;

public class Team  {


    public int getTeamColorID() {
        return teamColorID;
    }

    public void setTeamColorID(int teamColorID) {
        this.teamColorID = teamColorID;
    }

    public int getTeamOrder() {
        return teamOrder;
    }

    public void setTeamOrder(int teamOrder) {
        this.teamOrder = teamOrder;
    }

    private int teamColorID;
    private int teamOrder;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private int score;


    public Team(int teamOrder, int teamColorID,int score){
        this.teamOrder = teamOrder;
        this.teamColorID = teamColorID;
        this.score = score;
    }
    public Team(){}

    public ArrayList<Team> getColorsList(Context mContext){
        ArrayList<Team> list = new ArrayList<>();
        list.add(0, new Team(1,getBlueColor(mContext),score));
        list.add(1, new Team(2,getRedColor(mContext),score));
        list.add(2, new Team(4,getYellowColor(mContext),score));
        return list;
    }
    private int getBlueColor(Context mContext){
       return ContextCompat.getColor(mContext,R.color.blue);
    }
    private int getRedColor(Context mContext){
        return ContextCompat.getColor(mContext,R.color.red);
    }
    private int getGreenColor(Context mContext){
        return ContextCompat.getColor(mContext,R.color.green);
    }
    private int getYellowColor(Context mContext){
        return ContextCompat.getColor(mContext,R.color.yellow);
    }
}
