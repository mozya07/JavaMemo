package jp.ac.jec.cm0107.android115;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    private ArrayList<Card> ary = new ArrayList<Card>(); // 複数枚のカード情報
    private int pos; // 現在表示しているカードの位置

    public  static final String TABLE_NAME = "CARD";
    private int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // データベースから単語データを取得
        CaedSQLiteOpenHelper helper = new CaedSQLiteOpenHelper(this);
        ary = helper.getAllCard();

        // ダミーデータ
//        ary.add(new Card("ペン", "pen", 1));
//        ary.add(new Card("朝", "morning", 2));
//        ary.add(new Card("昼", "lunch", 3));
//        ary.add(new Card("リンゴ", "apple", 4));
//        ary.add(new Card("バナナ", "banana", 5));
//        ary.add(new Card("学校", "school", 6));
         pos = 0;


        try {
            SQLiteDatabase database = helper.getReadableDatabase();
            Cursor cursor = database.query(TABLE_NAME,null,null,null,null,null,null);
            if(cursor != null){
                number = cursor.getCount();
            }
            //Log.i("debugMessage",cursor);
        }finally {
            helper.close();
        }


        Button btnPrev = (Button)findViewById(R.id.btnPrev);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtjapanese = (TextView)findViewById(R.id.txtjapanese);
                String japanese = txtjapanese.getText().toString();
                if (japanese.equals("バナナ")) {
                    btnPrev.setEnabled(false);
                    pos--;
                    dispOneCard();
                } else {
                    pos--;
                    dispOneCard();
                }

            }
        });

        btnPrev.setEnabled(false);


        Button btnNext = (Button)findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // try catc の処理
                try {
                    pos++;
                    dispOneCard();
                    btnPrev.setEnabled(true);
                } catch (Exception e) {
                    Toast.makeText(TestActivity.this, "お疲れ様でした", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        Button close = (Button)findViewById(R.id.closebtn);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();

            }
        });



        Button btnAnswer = (Button)findViewById(R.id.btnAnswer);
        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView txtjapanese = (TextView)findViewById(R.id.txtjapanese);
                if (txtjapanese.getVisibility() == (View.VISIBLE)) {
                    txtjapanese.setVisibility(View.INVISIBLE);
                } else  {
                    txtjapanese.setVisibility(View.VISIBLE);
                }


            }
        });
        dispOneCard();


    }

    private void dispOneCard() {
        Card temp = ary.get(pos); // 現在位置のカード情報を取得する
        TextView txtNumber = (TextView)findViewById(R.id.txtNumber);
        txtNumber.setText(temp.getId() + "問目/全" + number + "問中");
        TextView txtEnglish = (TextView)findViewById(R.id.txtEnglish);
        txtEnglish.setText(temp.getEnglish()); // 英語を表示させる
        TextView txtjapanese = (TextView)findViewById(R.id.txtjapanese);
        txtjapanese.setText(temp.getJapanese()); // 日本語を表示させる
        txtjapanese.setVisibility(View.INVISIBLE); // 初期状態では、日本語を非表示させる


    }




}
