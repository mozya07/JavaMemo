package jp.ac.jec.cm0107.android115;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CaedSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "CARD_DAB";
    public static final int version = 1;
    public  static final String TABLE_NAME = "CARD";

    private static final String FTS_VIRTUAL_TABLE = "FTS";


    public CaedSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "english TEXT, japanese TEXT)");

       sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (1,'apple', 'リンゴ')");
       sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (2,'banana', 'バナナ')");
      sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (3,'lemon', 'レモン')");

    }

    @Override
    public  void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    ////////
    /////////
    //下のwhileでデータベースから呼び出して、これの中に順々入れて行っている、
    //そして、これを使って画面に表示している
    public ArrayList<Card> getAllCard() {

        ArrayList<Card> ary = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        if (db == null) {
            return null;
        }

        try {
            // 取得したデータのカラムをString配列を用意する
            // 左から順にカラム要素インデックス０、１、２・・・となっている
            // 後にCursorオブジェクトから行データを取り出した時のカラムインデックスと紐付いている
            String[] column = new String[]{"japanese", "english", "_id"};

            Cursor cur = db.query(TABLE_NAME, column, null, null, null, null, null);

            // 取得したデータは全てCursorオブジェクトに入っているので中身を順次取り出す
            // Cursorの中に次の行のデータがあればmoveToNext()の戻り値はtrueが返る
            while (cur.moveToNext()) {// 行のデータがなくなるまでwhile文で回す
                Card tmp = new Card(cur.getString(0), cur.getString(1), cur.getInt(2));
                ary.add(tmp);

            }
            // Cursorオブジェクトは必ずcloseする
            cur.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // SQLiteDatabaseオブジェクトをcloseするのも忘れずに
            db.close();
        }
        return  ary;

    }




//    public boolean insertCard(Card newCard) {
//        long ret = 0;
//        SQLiteDatabase db = getWritableDatabase();
//        try {
//            ContentValues values = new ContentValues();
//            values.put("enblish", newCard.getEnglish());
//            values.put("japanese", newCard.getJapanese());
//        } finally {
//            db.close();
//        }
//        return ret != -1;
//    }


}



