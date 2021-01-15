package com.bonc.activity.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bonc.activity.R;
import com.bonc.activity.operators.ClickListener;

public class TeamSelectActivity extends AppCompatActivity {
    private String blueTeam = "blue";
    private String redTeam = "red";
    private String greenTeam = "green";
    private String yellowTeam = "yellow";
    private int teamCount;
    private Button minusButton, plusButton,playButton;
    private EditText teamCountEditText;
    private TextView blueView,redView,greenView,yellowView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_select);
        Init();

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamSelectActivity.this,GameActivity.class);
                teamCount = Integer.parseInt(teamCountEditText.getText().toString());
                intent.putExtra("teamCount",teamCount);
                startActivity(intent);
            }
        });
    }
    private void Init(){
        minusButton = findViewById(R.id.team_select_minus);
        plusButton = findViewById(R.id.team_select_plus);
        teamCountEditText = findViewById(R.id.team_select_count);
        playButton = findViewById(R.id.select_team_play_button);
        teamCountEditText.setText("2");

        minusButton.setOnClickListener(new ClickListener((EditText) teamCountEditText));
        plusButton.setOnClickListener(new ClickListener((EditText) teamCountEditText));
        blueView = findViewById(R.id.select_team_blue_team);
        redView = findViewById(R.id.select_team_red_team);
        greenView = findViewById(R.id.select_team_green_team);
        yellowView = findViewById(R.id.select_team_yellow_team);
        teamCountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                teamCount = Integer.parseInt(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
                // kék,piros,zöld,sárga
                if (s.toString().equals("2")){
                    blueView.setVisibility(View.VISIBLE);
                    redView.setVisibility(View.VISIBLE);
                    greenView.setVisibility(View.INVISIBLE);
                    yellowView.setVisibility(View.INVISIBLE);
                }else if(s.toString().equals("3")){
                    blueView.setVisibility(View.VISIBLE);
                    redView.setVisibility(View.VISIBLE);
                    greenView.setVisibility(View.VISIBLE);
                    yellowView.setVisibility(View.INVISIBLE);
                }else if (s.toString().equals("4")){
                    blueView.setVisibility(View.VISIBLE);
                    redView.setVisibility(View.VISIBLE);
                    greenView.setVisibility(View.VISIBLE);
                    yellowView.setVisibility(View.VISIBLE);
                }else{
                    if (Integer.parseInt(s.toString()) > 4) {
                        teamCountEditText.setText("4");
                    }else if(Integer.parseInt(s.toString()) < 2){
                        teamCountEditText.setText("2");
                    }
                }
                teamCount = Integer.parseInt(s.toString());

            }
        });
    }
}