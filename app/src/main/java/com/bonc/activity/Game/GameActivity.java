package com.bonc.activity.Game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bonc.activity.Others.Cards;
import com.bonc.activity.Others.Team;
import com.bonc.activity.R;
import com.bonc.activity.SQL.SQLite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private int teamCount;
    private int currentTeam;
    private ArrayList<Team> teams;
    private ImageButton visibleButton,falseButton,trueButton;
    private TextView taskView,wordView,scoreView,currentView,nextView,chronometer;
    private LinearLayout GameBoarderLayout;
    private CountDownTimer countDownTimer;
    private  Cards currentCard;
    private long timeLeftInMillisecond = 150000;// 5perc;
    private long fulltimeInMillisecond = 300000;
    private boolean timerIsRunning = false;
    ArrayList<Cards> usedCards;
    private int roundCount;
    private AlertDialog kozbevagasDialog;
    private Button startTimerButton;
    private TextView blueView, redView,greenView,yellowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Init();
    }

    private void NextAndCurrentTextView(){
        currentView.setBackgroundColor(teams.get(0).getTeamColorID());
        nextView.setBackgroundColor(teams.get(1).getTeamColorID());

    }
    private void  Init(){
        Intent intent = getIntent();
        this.teamCount = intent.getIntExtra("teamCount",0);
        Log.i("TEAM Count = ", String.valueOf(this.teamCount));
        this.teams = getCurrentTeams();
        currentTeam = 0;
        usedCards = new ArrayList<>();
        timeLeftInMillisecond = fulltimeInMillisecond;

        // Layout init
        currentView = findViewById(R.id.game_current_team);
        nextView = findViewById(R.id.game_next_team);
        scoreView = (TextView) findViewById(R.id.game_score_view);
        taskView = findViewById(R.id.game_task_view);
        wordView = findViewById(R.id.game_word_view);
        visibleButton = findViewById(R.id.game_taskvisibility_button);
        falseButton = findViewById(R.id.answer_false_button);
        trueButton = findViewById(R.id.answer_true_button);
        chronometer = findViewById(R.id.game_chromometer);
        GameBoarderLayout = findViewById(R.id.board_layout);
        startTimerButton = findViewById(R.id.game_start_timer);

        LetsPlay();

        this.falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Senki nem  kap pontot
                NextCurrentTeam();
                LetsPlay();
            }
        });
        this.trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ha a countDown > 60 mp akkor közbevágás gyanús, vagyis
                if (timeLeftInMillisecond <= 60000){
                    AlertDialogKozbevagas();

                }else{
                    // A jelenlegi csapat kap pontot
                    teams.get(currentTeam).setScore(teams.get(currentTeam).getScore() + currentCard.getPoint());
                    NextCurrentTeam();
                    NextAndCurrentTextView();
                    LetsPlay();


                }
                StartStopTimer();
            }
        });
        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartStopTimer();
            }
        });
    }
    private void NextCurrentTeam(){
        int nextTeam = 1;
        int nextColor;
        int currentColor = this.teams.get(this.currentTeam).getTeamColorID();
        currentView.setBackgroundColor(currentColor);
        if (currentTeam == teams.size()-1){ // Ha utolsó, akkor currentTeam = 0
            currentTeam = 0;
            nextColor = teams.get(nextTeam).getTeamColorID();
            nextView.setBackgroundColor(nextColor);
        }else{
            currentTeam++;
            if (currentTeam == teams.size()-1){ //Ha a jelnlegi az utolsó, akkor a következő az első
                nextTeam = 0;
                nextColor = teams.get(nextTeam).getTeamColorID();
                nextView.setBackgroundColor(nextColor);
            }else{
                nextTeam = currentTeam + 1;
                nextColor = teams.get(nextTeam).getTeamColorID();
                nextView.setBackgroundColor(nextColor);
            }


        }
;


    }
    private void AlertDialogKozbevagas(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View view = getLayoutInflater().inflate(R.layout.kozbevagas_eredmeny,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                builder.setView(view);
                  blueView = (TextView) view.findViewById(R.id.kozbevagas_kek);
                  redView = (TextView)view.findViewById(R.id.kozbevagas_piros);
                  greenView = (TextView)view.findViewById(R.id.kozbevagas_zold);
                  yellowView = (TextView)view.findViewById(R.id.kozbevagas_sarga);

                                if (teamCount == 2){
                                    greenView.setVisibility(View.INVISIBLE);
                                    yellowView.setVisibility(View.INVISIBLE);
                                }else if(teamCount == 3){
                                    greenView.setVisibility(View.VISIBLE);
                                    yellowView.setVisibility(View.INVISIBLE);
                                }else if(teamCount == 4){
                                    greenView.setVisibility(View.VISIBLE);
                                    yellowView.setVisibility(View.VISIBLE);
                                }

                                blueView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        teams.get(0).setScore(teams.get(0).getScore() + currentCard.getPoint());
                                        kozbevagasDialog.dismiss();
                                        NextCurrentTeam();
                                        LetsPlay();
                                    }
                                });
                                redView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        teams.get(1).setScore(teams.get(1).getScore() + currentCard.getPoint());
                                        kozbevagasDialog.dismiss();
                                        NextCurrentTeam();
                                        LetsPlay();
                                    }
                                });
                                greenView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        teams.get(2).setScore(teams.get(2).getScore() + currentCard.getPoint());
                                        kozbevagasDialog.dismiss();
                                        NextCurrentTeam();
                                        LetsPlay();
                                    }
                                });
                                yellowView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        teams.get(3).setScore(teams.get(3).getScore() + currentCard.getPoint());
                                        kozbevagasDialog.dismiss();
                                        NextCurrentTeam();
                                        LetsPlay();
                                    }
                                });

                kozbevagasDialog = builder.create();
                kozbevagasDialog.show();
            }
        });

    }
    private void LetsPlay() {
        timeLeftInMillisecond = fulltimeInMillisecond;
        updateTimer();
        SQLiteDatabase liteDatabase = new SQLiteDatabase(GameActivity.this);
        ArrayList<Cards> cards = liteDatabase.getCards();
        NextAndCurrentTextView();
        int rndNumber = checkedCards(cards.size());
         currentCard = cards.get(rndNumber);
        Log.i("Random number", String.valueOf(rndNumber));
        //task,word,score
        taskView.setText(task());
        wordView.setText(currentCard.getWord());
        scoreView.setText(String.valueOf(currentCard.getPoint()));
        usedCards.add(roundCount,currentCard);
            //Következő csapat
    }
    private String task(){
        String[] a = {"Rajzolás","Mutogatás","Körülírás"};
        int rnd = new Random().nextInt((2)+1);
        return a[rnd];
    }
    private int checkedCards(int cardsSize){
        int min = 0;
        int max = cardsSize;
        Log.i("Cards_Size", String.valueOf(max));
        int rndNumber = new Random().nextInt((max-min)+1)+min;
        Log.i("Random number", String.valueOf(rndNumber));
        if (usedCards.size() > 0 && usedCards.size() != cardsSize) {
            for (int i = 0; i < usedCards.size(); i++) {
                if (usedCards.get(i).getId() == rndNumber){
                    checkedCards(cardsSize);
                }
            }
            return rndNumber;
        }else if(usedCards.size() == cardsSize){
            usedCards.clear();
            usedCards = new ArrayList<>();
            return rndNumber;
        }else{
            return 0;
        }
    }

    private ArrayList<Team> TeamList(){
        return new Team().getColorsList(this);
    }
    private ArrayList<Team> getCurrentTeams(){
        ArrayList<Team> allTeam = TeamList();
        ArrayList<Team> currentTeams = new ArrayList<>();
        for (int i = 0; i < teamCount;i++){
            currentTeams.add(i,allTeam.get(i));
        }
        return currentTeams;
    }
    private void StartStopTimer(){
        if (timerIsRunning){
            PauseTimer();
            startTimerButton.setText(getString(R.string.start));
        }else{
            startTimer();
            startTimerButton.setText(getString(R.string.pause));
        }
    }

    private void startTimer() {
        this.countDownTimer = new CountDownTimer(timeLeftInMillisecond,1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillisecond = l;
                updateTimer();
            }



            @Override
            public void onFinish() {
                timeLeftInMillisecond = 300000;
            }
        }.start();
        timerIsRunning = true;
    }
    private void updateTimer() {
        int minutes = (int) timeLeftInMillisecond / 60000;
        int seconds = (int) timeLeftInMillisecond % 60000 / 1000;
        String timeLeft = "" + minutes;
        timeLeft += ":";
        if(seconds < 10) timeLeft += "0";
        timeLeft += seconds;
        chronometer.setText(timeLeft);
        if (minutes < 1){
            int red = getColor(R.color.red);
            chronometer.setTextColor(red);
        }else{
            int black = getColor(R.color.main_text_color);
            chronometer.setTextColor(black);
        }

    }

    private void PauseTimer(){
        countDownTimer.cancel();
        timerIsRunning = false;

    }
    private void resumeTimer(){
        timeLeftInMillisecond = fulltimeInMillisecond; //https://www.youtube.com/watch?v=zmjfAcnosS0
    }
}