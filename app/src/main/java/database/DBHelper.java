package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "historyDb";
    public static final String TABLE_EXPRESSIONS = "expressions";
    public static final String TABLE_MAIN = "main";

    public static final String KEY_EXPRESSION = "_expression";
    public static final String KEY_RESULT = "_result";

    public static final String KEY_INPUT_FIELD = "_inputField";
    public static final String KEY_RESULT_FIELD = "_resultField";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_EXPRESSIONS + "(" +
                KEY_EXPRESSION + " text," + KEY_RESULT + " text" + ")");
        db.execSQL("create table " + TABLE_MAIN + "(" +
                KEY_INPUT_FIELD + " text," + KEY_RESULT_FIELD + " text" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + db);
        onCreate(db);
    }

    // insert data in DB
    public boolean insertDataInExpressions(String expression, String result) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_EXPRESSION, expression);
        contentValues.put(KEY_RESULT, result);
        long answer = db.insert(TABLE_EXPRESSIONS, null, contentValues);
        return answer != -1;
    }

    public boolean insertDataInMain(String input, String result) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_INPUT_FIELD, input);
        contentValues.put(KEY_RESULT_FIELD, result);
        long answer = db.insert(TABLE_MAIN, null, contentValues);
        return answer != -1;
    }

    public Cursor getDataFromExpressions() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select * from " + TABLE_EXPRESSIONS, null);
    }

    public Cursor getDataFromMain() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select * from " + TABLE_MAIN, null);
    }

    public void deleteExpressionsBase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_EXPRESSIONS);
    }

    public void deleteMainBase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_MAIN);
    }
}
