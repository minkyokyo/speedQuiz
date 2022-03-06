package com.example.itsdonespeedquiz;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuizrecordActivity extends AppCompatActivity {

    ListView list;
    String[] memos={
            " -두팀 대결 모드- \n 방탄소년단과 함께",
            " -두팀 대결 모드- \n 무한도전 멤버들이랑 >_<",
            " -두팀 대결 모드- \n 또 무도 멤버들!!",
            " -두팀 대결 모드- \n 무도 멤버들과 잼썼엉",
            " -기본 모드- \n 준하 형님과",
            " -기본 모드- \n 명수 형님과",
            " -기본 모드- \n 정현 누님과",
            " -두팀 대결모드- \n 런닝맨 멤버들과",
    };
    Integer [] images={
            R.drawable.img0,
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6,
            R.drawable.img7,
    };

    //ListView의 아이템 하나가 클릭되는 것을 감지하는 Listener객체 생성 (Button의 OnClickListener와 같은 역할)
    AdapterView.OnItemClickListener listener= new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Dialog dialog = new Dialog(QuizrecordActivity.this, android.R.style.Theme_Material_Light_Dialog);
            dialog.setContentView(R.layout.record_listview_popup);

            ImageView imgView = dialog.findViewById(R.id.popup_image);
            TextView recordTxt = dialog.findViewById(R.id.popup_text);
            Button pop_upBtn = dialog.findViewById(R.id.popup_button);

            recordTxt.setText(memos[position]);
            imgView.setImageResource(images[position]);
            //확인 버튼을 클릭했을 때 이벤트 구현
            pop_upBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();//대화상자 보여줌
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        CustomList adapter=new CustomList(QuizrecordActivity.this);
        list=(ListView)findViewById(R.id.recordList);
        list.setAdapter(adapter); //getView 호출
        list.setOnItemClickListener(listener); //
        registerForContextMenu(list);
    }

    public class CustomList extends ArrayAdapter<String> {
        private final Activity context;

        public CustomList(Activity context) {
            super(context, R.layout.record_item_list_adapter, memos);
            this.context=context;
        }
        @Override
        public View getView(int position, View view, ViewGroup parent){
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.record_item_list_adapter,null,true);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
            TextView text = (TextView) rowView.findViewById(R.id.memotxtvw);

            text.setText(memos[position]);
            imageView.setImageResource(images[position]);
            return rowView;
        }
    }

    //Context 메뉴로 등록한 View(여기서는 ListView)가 처음 만들어질 때 호출되는 메소드
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.listview_context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    //Context 메뉴로 등록한 View(여기서는 ListView)가 클릭되었을 때 자동으로 호출되는 메소드
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        int index= info.position; //AdapterView안에서 ContextMenu를 보여즈는 항목의 위치

        switch( item.getItemId() ){
            case R.id.modifybtn:
                Toast.makeText(this," Modify", Toast.LENGTH_SHORT).show();
                break;
            case R.id.deletebtn:
                Toast.makeText(this, " Delete", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }
}