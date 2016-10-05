package com.icaynia.tangoii;

/**
 * Created by icaynia on 16. 10. 5..
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    // 안드로이드에서 SQLite 데이터 베이스를 쉽게 사용할 수 있도록 도와주는 클래스
    public MySQLiteOpenHelper(Context context, String name,
                              CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 최초에 데이터베이스가 없을경우, 데이터베이스 생성을 위해 호출됨
        // 테이블 생성하는 코드를 작성한다

        String sql = "create table tangoii(" +
                " id integer primary key autoincrement," +
                " parts text," +
                " word text," +
                " hiragana text," +
                " korean text," +
                " korean2 text," +
                " example text," +
                " exampleko text," +
                " example2 text," +
                " example2ko text," +
                " example3 text," +
                " example3ko text," +
                " example4 text," +
                " example4ko text," +
                " example5 text," +
                " example5ko text," +
                " showcount integer," +
                " passcount integer," +
                " regdate date" +
                "  );";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 데이터베이스의 버전이 바뀌었을 때 호출되는 콜백 메서드
        // 버전 바뀌었을 때 기존데이터베이스를 어떻게 변경할 것인지 작성한다
        // 각 버전의 변경 내용들을 버전마다 작성해야함
        String sql = "drop table mytable;"; // 테이블 드랍
        db.execSQL(sql);
        onCreate(db); // 다시 테이블 생성
    }
}