package jp.ac.jec.cm0107.android115;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    public static final String DB_NAME = "CARD_DAB";
    public static final int version = 1;
    public static final String TABLE_NAME = "CARD";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


//        List<String> foo = new ArrayList<String>();
//
//        foo.add("apple");
//        foo.add("banana");
//        foo.add("lemon");

        TextView text = (TextView) findViewById(R.id.addtxt);
        SharedPreferences sp = getSharedPreferences("android109", Context.MODE_PRIVATE);
        String memo = sp.getString("MEMO", "");
        text.setText(memo.replaceAll(",", "\n"));


        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editenglish = findViewById(R.id.editEnglish);
                EditText editjapanese = findViewById(R.id.editJapanese);

                String editenglishs = editenglish.getText().toString();
                String editjapaneses = editjapanese.getText().toString();






                if (isExistWord(editenglishs) == true) {
                    Toast.makeText(EditActivity.this, "重複した値です", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!editenglishs.equals("") && !editjapaneses.equals("")) {


                    TextView txt = (TextView) findViewById(R.id.addtxt);
                    // メモが書かれている場所の内容を取得
                    String memo = txt.getText().toString();
                    if (memo.length() <= 0) { // はじめのメモ？
                        memo = "・英語：" + editenglish.getText().toString() + "、日本語：" + editjapanese.getText().toString();
                    } else { // 改行を加えて分割
                        memo = memo + "\n" + "・英語：" + editenglish.getText().toString() + "、日本語：" + editjapanese.getText().toString();
                    }
                    txt.setText(memo); // 追加後のメモを表示（setでセット）
                    editenglish.setText(""); // 追加したので、入力領域をクリア（空文字にする）
                    editjapanese.setText(""); // 追加したので、入力領域をクリア（空文字にする）
                   // Toast.makeText(EditActivity.this, "追加しました", Toast.LENGTH_SHORT).show();


//                    foo.add(editenglishs);
//                    foo.add(editjapaneses);
                    ////////////////
                    int number = 0;
                    Log.i("debugMessage", "add Click");
                    // Log.i("debugMessage","add Click" + foo);
                    CaedSQLiteOpenHelper helper = new CaedSQLiteOpenHelper(EditActivity.this);
                    try {
                        SQLiteDatabase database = helper.getReadableDatabase();
                        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
                        if (cursor != null) {
                            number = cursor.getCount();
                        }
                        //Log.i("debugMessage",cursor);
                    } finally {
                        helper.close();
                    }

                    number++;
                    String sql = "INSERT INTO " + TABLE_NAME + " VALUES (" + number + ",'" + editenglishs + "', '" + editjapaneses + "')";
                    Log.i("debugMessage", "sql = " + sql);

                    try {
                        SQLiteDatabase database = helper.getWritableDatabase();
                        database.execSQL(sql);
                    } finally {
                        helper.close();
                    }

                    // 一覧の保存
                    String memoo = txt.getText().toString().replaceAll("\n", ",");

                    // MODE_PRIVATE 自アプリからのみ、読み書きかできる
                    // MODE_WORLD_READABLE 他アプリからでも、読むことができる
                    // MODE_WORLD_WRITABLE 他アプリからでも、読み書きができる
                    SharedPreferences sp = getSharedPreferences("android109", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edtr = sp.edit();
                    edtr.putString("MEMO", memoo);
                    edtr.commit();


                    Toast.makeText(EditActivity.this, "追加し保存しました", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(EditActivity.this, "値を入力して下さい", Toast.LENGTH_SHORT).show();
                    Log.i("debugMessage", "data = NULL");
                }


            }
        });


        Button delete = (Button) findViewById(R.id.deletebtn);
        delete.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String sql = "drop table CARD;";
                CaedSQLiteOpenHelper helper = new CaedSQLiteOpenHelper(EditActivity.this);
                try {
                    SQLiteDatabase database = helper.getReadableDatabase();
                    database.execSQL(sql);
                    Toast.makeText(EditActivity.this, "全削除しました", Toast.LENGTH_SHORT).show();

                    helper.onCreate(database); // クリエイトを呼び出す

//    foo.clear(); // 配列の要素削除
//    foo.add("apple");
//    foo.add("banana");
//    foo.add("lemon");


                    TextView addtxt = (TextView) findViewById(R.id.addtxt);
                    addtxt.setText(""); // 全削除する

                    TextView txt = (TextView) findViewById(R.id.addtxt); // 全削除を保存する
                    String memo = txt.getText().toString().replaceAll("\n", ",");

                    // MODE_PRIVATE 自アプリからのみ、読み書きかできる
                    // MODE_WORLD_READABLE 他アプリからでも、読むことができる
                    // MODE_WORLD_WRITABLE 他アプリからでも、読み書きができる
                    SharedPreferences sp = getSharedPreferences("android109", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edtr = sp.edit();
                    edtr.putString("MEMO", memo);
                    edtr.commit();

                } catch (Exception e) {
                    Toast.makeText(EditActivity.this, "エラーが起こりました", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });


        Button closebtn = (Button) findViewById(R.id.btnClose);
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

//   private void wordcheck (String editenglishs) {
//
////       else{
////           System.out.println("Apple - Not Found!");
//
//       // 正常動作する書き方
//       CaedSQLiteOpenHelper helper = new CaedSQLiteOpenHelper(EditActivity.this);
//       SQLiteDatabase database = helper.getReadableDatabase();
//       String sql= "SELECT title, body FROM notes WHERE body LIKE '%' || ? || '%' ESCAPE '$'";
//       String searchWord = editenglishs;
//       Cursor cursor = database.rawQuery(sql, new String[] { searchWord });
//      if (editenglishs.equals(cursor))  {
//          Toast.makeText(EditActivity.this, "重複した値です", Toast.LENGTH_SHORT).show();
//          return;
//      }
//    }


//    public void onCheck(View view) { // btn
//        CaedSQLiteOpenHelper helper = new CaedSQLiteOpenHelper(this);
//        try {
//            SQLiteDatabase database = helper.getReadableDatabase();
//            String sql =  "SELECT * FROM " + TABLE_NAME + " WHERE english = 'book'";
//
//
//            Cursor cursor = database.query(TABLE_NAME,null,null,null,null,null,null);
//            if(cursor != null){
//                int count = cursor.getCount();
//                Log.i("debugMessage", String.valueOf(count));
//            }
//
//            //Log.i("debugMessage",cursor);
//        }finally {
//            helper.close();
//        }
//    }
    boolean isExistWord(String xisitWord) {
        CaedSQLiteOpenHelper helper = new CaedSQLiteOpenHelper(this);
        try {
            String[] column = new String[]{"english"};
            SQLiteDatabase database = helper.getReadableDatabase();
            Cursor cursor = database.query(TABLE_NAME, column, "english = ?", new String[]{xisitWord}, null, null, null);
            if (cursor.moveToNext()) {
                // moveToNext() = 値があればtrueで値がなければfalseになる
                // 1行ずつ見ていく
                // 値があれば中の処理が実行され、なければfalseを返し、処理を抜ける
                Log.v("Android115","データ件数：" + cursor.getCount() + "件");
                cursor.close();
                return true;
            }
        } finally {
            helper.close();
        }


        return false;
    }

//    boolean isExistWord(String xisitWord) {
//        CaedSQLiteOpenHelper helper = new CaedSQLiteOpenHelper(this);
//        try {
//            SQLiteDatabase database = helper.getReadableDatabase();
//            String exist = "SELECT english TEXT FROM CARD WHERE english = " + xisitWord + ";";
//           database.execSQL(exist);
//
//
////            Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
////            if (cursor != null) {
////                int count = cursor.getCount();
////                Log.i("debugMessage", String.valueOf(count));
////            }
//
//            //Log.i("debugMessage",cursor);
//        } finally {
//            helper.close();
//        }
//         return true;
//    }


}

