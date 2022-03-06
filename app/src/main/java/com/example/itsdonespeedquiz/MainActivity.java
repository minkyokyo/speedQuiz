/*
기본모드는 카테고리 선택
두팀 대결은 내가 알아서 해주는 형식 ( 가족끼리 즐기게 )
*/


package com.example.itsdonespeedquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //앱의 모드 세가지를 상수로 선언한다.
    // DEFAULT_MODE는 기본모드.
    // FRIST_TEAM_MODE는 두팀 대결 모드에서 첫번째 팀에 대한 모드.
    // SECOND_TEAM_MODE는 두팀 대결 모드에서 두번째 팀에 대한 모드.
    static final int DEFAULT_MODE = 0;
    static final int FIRST_TEAM_MODE = 1;
    static final int SECOND_TEAM_MODE = 2;
    
    //앱의 모드를 모든 클래스에서 접근키 위해 STATIC으로 선언한다.
    static int selected_mode;
    
    //1팀과 2팀의 정보를 담을 배열을 STATIC으로 선언한다.
    static Team[] TEAM; //1팀과 2팀이 들어갈 배열

    final Button [] btn = new Button[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] btnID={R.id.modebtn1,R.id.modebtn2,R.id.rankingbtn,R.id.outbtn};

        //버튼객체를 xml로부터 받아온다.
        for(int i=0;i<4;i++){   
            btn[i]=(Button)findViewById(btnID[i]);
        }

        btn[0].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 기본모드 설정 화면으로 이동
                Intent intent = new Intent(MainActivity.this, QuizsettingActivity.class);
                //모드를 기본모드로 바꾸어줌
                selected_mode=DEFAULT_MODE;
                startActivity(intent);
            }
        });

        btn[1].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 두팀 대결모드 설정 화면으로 이동
                Intent intent = new Intent(MainActivity.this, QuizsettingActivity.class);
                //모드를 첫번째 팀에 대한 모드로 바꾸어줌
                selected_mode=FIRST_TEAM_MODE;
                startActivity(intent);
            }
        });

        btn[2].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 기록 화면으로 이동
                Intent intent = new Intent(MainActivity.this, QuizrecordActivity.class);
                startActivity(intent);
            }
        });

        btn[3].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //대화박스
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("종료하시겠습니까?");
                // 앱 종료
                alertDialogBuilder.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveTaskToBack(true); // 태스크를 백그라운드로 이동
                                finishAndRemoveTask(); // 액티비티 종료 + 태스크 리스트에서 지우기
                                System.exit(0);
                            }
                        });
                //No 할시 아무일도 안일어남.
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


    }
}