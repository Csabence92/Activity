package com.bonc.activity.operators;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.bonc.activity.Game.GameActivity;
import com.bonc.activity.Game.TeamSelectActivity;
import com.bonc.activity.R;

public class ClickListener implements View.OnClickListener {
    private EditText editText;
    private Activity activity;
    private int teamCount;
    public ClickListener(){}
    public ClickListener(EditText editText){
        this.editText = editText;
    }
    public ClickListener(Activity activity){
        this.activity = activity;
    }
    public ClickListener(Activity activity, int teamCount){
        this.activity = activity;
        this.teamCount = teamCount;
        Log.i("TeamCount", String.valueOf(this.teamCount));
    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.team_select_minus :
                int minCount = Integer.parseInt(editText.getText().toString())-1;
                editText.setText(String.valueOf(minCount));
                break;
            case R.id.team_select_plus :
                int plusCount = Integer.parseInt(editText.getText().toString())+1;
                editText.setText(String.valueOf(plusCount));
                break;
            case R.id.main_play :
                intent = new Intent(activity, TeamSelectActivity.class);
                activity.startActivity(intent);
                break;
            case  R.id.select_team_play_button :
                intent= new Intent(activity, GameActivity.class);
                intent.putExtra("teamCount",this.teamCount);
                this.activity.startActivity(intent);
                break;
        }
    }
}
