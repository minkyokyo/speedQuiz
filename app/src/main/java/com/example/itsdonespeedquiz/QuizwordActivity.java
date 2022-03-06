package com.example.itsdonespeedquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itsdonespeedquiz.Appservice.MusicService;

public class QuizwordActivity extends AppCompatActivity {
    Button[] btn = new Button[2];
    TextView textView, restwordCountTxtVw,timeTxtVw;
    int answerCount, arrayIndex,mode;
    String[] words;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        //Title bar를 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //setting Activity에서 보낸 time을 가져온다.
        Intent intent = getIntent();
        String time = intent.getStringExtra("time");

        //Mode에 따라 word를 가져온다.
        switch(MainActivity.selected_mode){
            case MainActivity.DEFAULT_MODE:
                // 기본모드는 Word를 인텐트로 받는다.
                words=intent.getStringArrayExtra("words");
                break;
            case MainActivity.FIRST_TEAM_MODE:
                //두팀 대결모드는 Words를 따로 저장해두었다가 받는다. (인텐트로 받지 x)
                words=MainActivity.TEAM[0].words;
                break;
            case MainActivity.SECOND_TEAM_MODE:
                words=MainActivity.TEAM[1].words;
                break;
        }

        textView = (TextView) findViewById(R.id.wordTxtVw);
        restwordCountTxtVw = (TextView) findViewById(R.id.quizTxtVw);
        timeTxtVw = (TextView) findViewById(R.id.timeTxtVw);

        //처음 단어 설정
        textView.setText(words[0]);

        //음악 서비스를 실행한다.
        startService(new Intent(this, MusicService.class));
        //textView.setText(words[0]);

        int[] btnID = {R.id.passbtn, R.id.Okbtn};

        //버튼객체를 xml로부터 받아온다.
        for (int i = 0; i < 2; i++) {
            btn[i] = (Button) findViewById(btnID[i]);
        }

        //passbtn 온 클릭 이벤트 -> 다음 단어가 오고, 정답개수는 증가시키지 않는다.
        btn[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    arrayIndex += 1;
                    restwordCountTxtVw.setText("( "+String.valueOf(arrayIndex + 1) + "/10 )");
                    textView.setText(words[arrayIndex]);

                    //arrayIndex가 10이 되었을 경우는 끝난 경우니 예외처리를 통해 intent를 넘긴다
                }catch(Exception ArrayIndexOutOfBoundsExcetion) {
                    restwordCountTxtVw.setText("( 10/10 )");
                    Intent intent = new Intent(QuizwordActivity.this, QuizfinishActivity.class);
                    intent.putExtra("answerCount", answerCount); //정답개수를 넘긴다.
                    intent.putExtra("resultTime", timeTxtVw.getText()); //남은 시간을 넘긴다.
                    stopService(new Intent(QuizwordActivity.this, MusicService.class));
                    startActivity(intent);
                    finish();
                }
            }
        });

        //Okbtn 온 클릭 이벤트 -> 다음단어가 오고, 정답 개수를 증가시킨다.
        btn[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    answerCount += 1;
                    arrayIndex += 1; //정답 개수 증가
                    restwordCountTxtVw.setText(" ("+String.valueOf(arrayIndex + 1) + "/10 ) ");
                    textView.setText(words[arrayIndex]);

                    //arrayIndex가 10이 되었을 경우는 끝난 경우니 예외처리를 통해 intent를 넘긴다
                }catch (Exception ArrayIndexOutOfBoundsExcetion){
                    restwordCountTxtVw.setText("( 10/10 )");
                    Intent intent = new Intent(QuizwordActivity.this, QuizfinishActivity.class);
                    intent.putExtra("answerCount", answerCount); //정답개수를 넘긴다.
                    intent.putExtra("resultTime",timeTxtVw.getText()); //남은 시간을 넘긴다.
                    stopService(new Intent(QuizwordActivity.this, MusicService.class));
                    startActivity(intent);
                    finish();
                }
            }
        });

        //timer 셋팅
        String conversionTime = time;
        countDown(conversionTime);
    }

    //timer 실행
    public void countDown(String time) {
        long conversionTime = 0;
        //1000단위가 1초
        //60000단위가 1분

        String getMin = time.substring(0,1);
        conversionTime = Long.valueOf(getMin) * 60 * 1000;

        new CountDownTimer(conversionTime, 100) {
            public void onTick(long millisUntilFinished) {
                // 분 단위
                long getMin = millisUntilFinished-(millisUntilFinished/(60*1000));
                String min = String.valueOf(getMin / (60 * 1000));
                //초 단위
                String second = String.valueOf((getMin%(60*1000))/(1000));
                // 밀리세컨드 단위
                String millis = String.valueOf((getMin%(60*1000))%(1000));

                // 초가 한자리면 0을 붙인다
                if (second.length() == 1) {
                    second = "0" + second;
                }

                // 밀리세컨드가 한자리면 0을 붙인다
                if (second.length() == 1) {
                    millis = "0" + millis;
                }

                timeTxtVw.setText(min + ":" + second + ":" + millis);

            }

            public void onFinish() {
                // TODO : 타이머가 모두 종료될때 어떤 이벤트를 진행할지
                Intent intent = new Intent(QuizwordActivity.this, QuizfinishActivity.class);
                // QuizfinsihACtivity에 mode의 정보, 남은 시간, 정답개수를 도 함께 보냄.
                intent.putExtra("answerCount", answerCount);
                intent.putExtra("resultTime","0:00:000");
                stopService(new Intent(QuizwordActivity.this, MusicService.class));
                startActivity(intent);
                finish();
            }
        }.start();
    }
}
