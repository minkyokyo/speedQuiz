package com.example.itsdonespeedquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuizsettingActivity extends AppCompatActivity {
    String settingTime="000";
    String[] words;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        switch (MainActivity.selected_mode){
            case MainActivity.DEFAULT_MODE: //기본모드 설정화면을 보여준다.
                setContentView(R.layout.activity_setting_default);
                //----Logic 시작.
                defaultModeLogic();
                break;
            case MainActivity.FIRST_TEAM_MODE: //두팀 모드 설정화면을 보여준다.
                setContentView(R.layout.activity_setting_competition);
                //-----Team에 대한 객체를 생성하여 메모리에 올려준다.
                MainActivity.TEAM=new Team[2];
                MainActivity.TEAM[0] = new Team();
                MainActivity.TEAM[1] = new Team();
                //----Logic 시작.
                competitionModeLogic();
                break;
            default:
                break;
        }
    }

    //두팀 대결 모드 로직.
    public void competitionModeLogic(){
        View timeView = (View)findViewById(R.id.time_select_layout);
        RadioGroup radioGroup = (RadioGroup) timeView.findViewById(R.id.timeRadiogroup);
        EditText team1edit = (EditText) findViewById(R.id.team1Edittxt);
        EditText team2edit = (EditText) findViewById(R.id.team2Edittxt);
        Button button= (Button) findViewById(R.id.startBtn);
        RadioGroupListener listener = new RadioGroupListener();
        radioGroup.setOnCheckedChangeListener(listener);

        // 1팀 단어 설정 - 추후에 DB나 파일읽기로 업그레이드
        MainActivity.TEAM[0].words= new String[]{
                "거북이", "노트북","소","빙수","커피","쿠키","아이돌","트로트","점쟁이","사주팔자"
        };
        // 2팀 단어 설정 - 추후에 DB나 파일읽기로 업그레이드
        MainActivity.TEAM[1].words= new String[]{
                "피의게임", "맥도날드","돼지","지구온난화","광운대","카카오톡","스티브잡스","스테이크","소파","이유식"
        };

        //------시작 버튼에 대한 온클릭 이벤트 처리----------
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //시간을 설정 안했을 시 처리해준다.
                if(settingTime=="000"){
                    Toast.makeText(getApplicationContext(), "시간 설정을 해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //--------------Team에 대한 정보를 저장한다.---------------
                MainActivity.TEAM[0].name=team1edit.getText().toString(); //1팀 이름을 저장한다.
                MainActivity.TEAM[1].name=team2edit.getText().toString(); //2팀 이름을 저장한다.
                MainActivity.TEAM[0].settingTime=settingTime; //셋팅 타임을 저장한다
                MainActivity.TEAM[1].settingTime=settingTime;// 셋팅 타임을 저장한다.
                //-------------Mode 설정
                MainActivity.selected_mode=MainActivity.FIRST_TEAM_MODE;
                Intent intent = new Intent(QuizsettingActivity.this,QuizwordActivity.class);
                intent.putExtra("time",settingTime); //셋팅한 시간을 전달한다
                startActivity(intent);
                finish();
            }
        });
    }

    //기본 모드 로직.
    public void defaultModeLogic(){
        View timeView = (View)findViewById(R.id.time_select_layout);
        RadioGroup radioGroup = (RadioGroup) timeView.findViewById(R.id.timeRadiogroup);
        Button button= (Button) findViewById(R.id.startBtn);
        RadioGroupListener listener = new RadioGroupListener();
        radioGroup.setOnCheckedChangeListener(listener);

        //단어 설정 - 추후에 DB나 파일읽기로 업그레이드
       words= new String[]{
                "크리스마스", "주차장","칼국수","다이어리","백신","넷플릭스","토끼","삼성","애덤스미스","오발탄"
        };
       //버튼에 대한 이벤트 처리, 기본모드는 인텐트로 모든 정보를 전달한다.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //시간을 설정 안했을 시 처리해준다.
                if(settingTime=="000"){
                    Toast.makeText(getApplicationContext(), "시간 설정을 해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(QuizsettingActivity.this,QuizwordActivity.class);
                intent.putExtra("time",settingTime); //셋팅한 시간을 전달한다
                intent.putExtra("words",words); //스피드 퀴즈에 쓰일 단어를 전달한다.
                startActivity(intent);
                finish();
            }
        });
    }

    //RadioGroup의 이벤트처리 메서드는 중복되어서 사용되기 때문에 따로 선언해주었다.
    class RadioGroupListener implements RadioGroup.OnCheckedChangeListener{
        public void onCheckedChanged(RadioGroup radioGroup, int i){
            View timeView = (View)findViewById(R.id.time_select_layout);
            RadioGroup radioGroup1 = (RadioGroup) timeView.findViewById(R.id.timeRadiogroup);
            int radiobtn = radioGroup.getCheckedRadioButtonId();
            switch (radiobtn) {
                case R.id.radioBtn1:
                    settingTime="100";
                    break;
                case R.id.radioBtn2:
                    settingTime="200";
                    break;
                case R.id.radioBtn3:
                    settingTime="300";
                    break;
                default:
                    break;
            }
        }
    }
}