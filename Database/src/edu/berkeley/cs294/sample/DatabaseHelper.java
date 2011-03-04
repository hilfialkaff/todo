// credit: http://www.screaming-penguin.com/node/7742

package edu.berkeley.cs294.sample;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DatabaseHelper {
	private static final String DATABASE_NAME = "noctis.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String TABLE_NAME_TO_DO = "to_do";
	private static final String TABLE_NAME_GROUP = "assembly";
	private static final String INSERT_TO_DO = "insert into " + TABLE_NAME_TO_DO + " (td_id, g_id, name, place, tag, description) values (?, ?, ?, ?, ?, ?)";
	private static final String INSERT_GROUP = "insert into " + TABLE_NAME_GROUP + " (g_id, name, member) values (?, ?, ?)";
	
	private Context context;
	private SQLiteDatabase db;
	private SQLiteStatement insertStmt_to_do, insertStmt_group;

	public DatabaseHelper(Context context) {
		this.context = context;
		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
		this.insertStmt_to_do = this.db.compileStatement(INSERT_TO_DO);
		this.insertStmt_group = this.db.compileStatement(INSERT_GROUP);
	}

	public long insert_to_do(int td_id, int g_id, String name, String place, String tag, String description) {
		this.insertStmt_to_do.clearBindings();
		this.insertStmt_to_do.bindString(1, Integer.toString(td_id));
		this.insertStmt_to_do.bindString(2, Integer.toString(g_id));
		this.insertStmt_to_do.bindString(3, name);
		this.insertStmt_to_do.bindString(4, place);
		this.insertStmt_to_do.bindString(5, tag);
		this.insertStmt_to_do.bindString(6, description);
		return this.insertStmt_to_do.executeInsert();
	}
	
	public long insert_group(int g_id, String name, String member) {
		this.insertStmt_group.clearBindings();
		this.insertStmt_group.bindString(1, Integer.toString(g_id));
		this.insertStmt_group.bindString(2, name);
		this.insertStmt_group.bindString(3, member);
		return this.insertStmt_group.executeInsert();
	}

	public void deleteAll_to_do() {
		this.db.delete(TABLE_NAME_TO_DO, null, null);
	}
	
	public void deleteAll_group() {
		this.db.delete(TABLE_NAME_GROUP, null, null);
	}

	public List<String> selectAll_to_do_name() {
		List<String> list = new ArrayList<String>();
		Cursor cursor = this.db.query(TABLE_NAME_TO_DO, new String[] {"name"}, null, null, null, null, "name desc");
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}
	
	public List<String> selectAll_group_name() {
		List<String> list = new ArrayList<String>();
		Cursor cursor = this.db.query(TABLE_NAME_GROUP, new String[] {"name"}, null, null, null, null, "name desc");
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

	private static class OpenHelper extends SQLiteOpenHelper {
		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + TABLE_NAME_TO_DO + " (td_id INTEGER PRIMARY KEY, g_id INTEGER, name TEXT, place TEXT, tag TEXT, description TEXT)");
			db.execSQL("CREATE TABLE " + TABLE_NAME_GROUP + " (g_id INTEGER PRIMARY KEY, name TEXT, member TEXT)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("debug", "noctis reset");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TO_DO);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_GROUP);
			onCreate(db);
		}
	}
}
