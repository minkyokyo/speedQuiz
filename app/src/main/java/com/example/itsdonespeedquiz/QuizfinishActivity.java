package com.example.itsdonespeedquiz;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class QuizfinishActivity extends AppCompatActivity {
    String Resulttime;
    int answerCount;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Title bar를 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //WordActivity가 보낸 남은 시간과 정답개수를 받아온다.
        Intent intent = getIntent();
        Resulttime = intent.getStringExtra("resultTime");
        answerCount = intent.getIntExtra("answerCount",0);

        switch (MainActivity.selected_mode){
            //기본모드 결과 보여주고 end.
            case MainActivity.DEFAULT_MODE:
                setContentView(R.layout.activity_finsih_default);
                defaultFinishLogic();
                break;
            // 두팀모드 결과 보여주기
            //---------------첫번째 팀의 퀴즈가 끝났을 경우
            case MainActivity.FIRST_TEAM_MODE:
                setContentView(R.layout.activity_finish_competition_mid);
                //-------첫번째 팀에 대한 정보를 저장------------
                MainActivity.TEAM[0].answerCount=answerCount;
                MainActivity.TEAM[0].restTime=Resulttime;
                competitionFirstteamFinishLogic();
                break;
            //---------------두번째 팀의 퀴즈가 끝났을 경우
            case MainActivity.SECOND_TEAM_MODE:
                setContentView(R.layout.activity_finish_competition);

                //-------두번째 팀에 대한 정보를 저장------------
                MainActivity.TEAM[1].answerCount=answerCount;
                MainActivity.TEAM[1].restTime=Resulttime;
                competitionFinishLogic();
                break;
            default:
                break;
        }
    }

    //기본모드 퀴즈가 끝났을 경우의 로직.
    public void defaultFinishLogic(){
        View resultView = (View)findViewById(R.id.result_layout);
        TextView resulttime = (TextView) resultView.findViewById(R.id.ResultTime);
        TextView correctCount = (TextView) resultView.findViewById(R.id.correctCount);

        //뷰에 남은시간과 정답 개수를 보여준다.
        resulttime.setText(Resulttime); //남은 시간 설정
        correctCount.setText(Integer.toString(answerCount));
    }

    //첫번째팀과 두번째 팀이 모두 끝났을 경우,
    public void competitionFinishLogic(){
        View Team1resultView = (View)findViewById(R.id.result_layout1);
        View Team2resultView = (View)findViewById(R.id.result_layout2);
        TextView Team1resulttime = (TextView) Team1resultView.findViewById(R.id.ResultTime);
        TextView Team1correctCount = (TextView) Team1resultView.findViewById(R.id.correctCount);
        TextView Team1Name = (TextView) findViewById(R.id.team1txt);

        TextView Team2resulttime = (TextView) Team2resultView.findViewById(R.id.ResultTime);
        TextView Team2correctCount = (TextView) Team2resultView.findViewById(R.id.correctCount);
        TextView Team2Name = (TextView) findViewById(R.id.team2txt);

        TextView winnerTxt = (TextView) findViewById(R.id.winnertxt);

        //TEAM 배열에 저장해놓은 첫번째 팀의 정보를 뷰에 전시
        Team1Name.setText(MainActivity.TEAM[0].name);
        Team1correctCount.setText(Integer.toString(MainActivity.TEAM[0].answerCount));
        Team1resulttime.setText(MainActivity.TEAM[0].restTime);

        //TEAM 배열에 저장해놓은 두번째 팀의 정보를 뷰에 전시
        Team2Name.setText(MainActivity.TEAM[1].name);
        Team2correctCount.setText(Integer.toString(MainActivity.TEAM[1].answerCount));
        Team2resulttime.setText(MainActivity.TEAM[1].restTime);

        //-------------------누가 승리했는지 판별하는 조건문--------------------------
        if(MainActivity.TEAM[0].answerCount>MainActivity.TEAM[1].answerCount){
            winnerTxt.setText(MainActivity.TEAM[0].name+" 승리!!");
        }
        else if(MainActivity.TEAM[0].answerCount<MainActivity.TEAM[1].answerCount){
            winnerTxt.setText(MainActivity.TEAM[1].name+" 승리!!");
        }
            //정답개수가 같으면 시간으로 판별한다.
        else{
            //resTime의 형태가 0:00:000 이라서 :을 없애준다.
            String Time1 = MainActivity.TEAM[0].restTime.replaceAll(":","");
            String Time2 = MainActivity.TEAM[1].restTime.replaceAll(":","");
            int intTime1 = Integer.parseInt(Time1);
            int intTime2 = Integer.parseInt(Time2);
            if( intTime1> intTime2){
                winnerTxt.setText(MainActivity.TEAM[0].name+" 승리!!");
            }
            else if(intTime1< intTime2){
                winnerTxt.setText(MainActivity.TEAM[1].name+" 승리!!");
            }
            else{
                winnerTxt.setText("동점!!!");
            }
        }

    }

    //첫번째팀이 끝났을 경우,
    public void competitionFirstteamFinishLogic(){
        View resultView = (View)findViewById(R.id.result_layout);
        TextView resulttime = (TextView) resultView.findViewById(R.id.ResultTime);
        TextView correctCount = (TextView) resultView.findViewById(R.id.correctCount);

        resulttime.setText(Resulttime); //Layout에 남은 시간 을 보여줌
        correctCount.setText(Integer.toString(answerCount)); //Layout에 정답 개수를 보여줌

        //버튼에 대한 이벤트 처리
        Button button = (Button)findViewById(R.id.team2startbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Mode를 두번쨰 팀 모드로 바꿔준다.
                MainActivity.selected_mode=MainActivity.SECOND_TEAM_MODE;
                //다시 WordActivity를 진행한다.
                Intent intent = new Intent(QuizfinishActivity.this, QuizwordActivity.class);
                intent.putExtra("time",MainActivity.TEAM[1].settingTime); //설정 시간을 전달해준다.
                startActivity(intent);
                finish();
            }
        });
    }

    //홈으로 버튼에 대한 이벤트 처리
    public void ongohomebtnClick(View view) {
        MainActivity.selected_mode=-1;
        Intent intent = new Intent(QuizfinishActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
