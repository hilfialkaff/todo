// credit: http://www.screaming-penguin.com/node/7742

package eecs.berkeley.edu.cs294;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DatabaseHelper {
	private static final String DATABASE_NAME = "noctis.db";
	private static final int DATABASE_VERSION = 1;

	public static final int NUM_ENTRIES = 7;
	public static final int GROUP_INDEX = 4;
	
	private static final String TABLE_NAME_TO_DO = "to_do";
	private static final String TABLE_NAME_GROUP = "assembly";
	private static final String INSERT_TO_DO = "insert into " + TABLE_NAME_TO_DO + " (td_id, title, place, note, tag, assembly, status, priority) values (NULL, ?, ?, ?, ?, ?, ?, ?)";
	private static final String INSERT_GROUP = "insert into " + TABLE_NAME_GROUP + " (g_id, name, member) values (NULL, ?, ?)";

	public static final String TITLE = "title";
	public static final String PLACE = "place"; 
	
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

	public long insert_to_do(String title, String place, String note, String tag, String assembly, String status, String priority) {
		this.insertStmt_to_do.clearBindings();
		this.insertStmt_to_do.bindString(1, title);
		this.insertStmt_to_do.bindString(2, place);
		this.insertStmt_to_do.bindString(3, note);
		this.insertStmt_to_do.bindString(4, tag);
		this.insertStmt_to_do.bindString(5, assembly);
		this.insertStmt_to_do.bindString(6, status);
		this.insertStmt_to_do.bindString(7, priority);
		
		
		return this.insertStmt_to_do.executeInsert();
	}
	
	public long update_to_do(int pk, String title, String place, String note, String tag, String assembly, String status, String priority) {
		ContentValues cv = new ContentValues();
		cv.put("title", title);
		cv.put("place", place);
		cv.put("note", note);
		cv.put("tag", tag);
		cv.put("assembly", assembly);
		cv.put("status", status);
		cv.put("priority", priority);
		String selection = "td_id = ?";
		return db.update(TABLE_NAME_TO_DO, cv, selection, new String[] {Integer.toString(pk)});
	}
	
	public long insert_group(String name, String member) {
		this.insertStmt_group.clearBindings();
		this.insertStmt_group.bindString(1, name);
		this.insertStmt_group.bindString(2, member);
		return this.insertStmt_group.executeInsert();
	}

	public void deleteAll_to_do() {
		this.db.delete(TABLE_NAME_TO_DO, null, null);
	}
	
	public void delete_to_do(int pk) {
		String selection = "td_id = ?";
		this.db.delete(TABLE_NAME_TO_DO, selection, new String[] {Integer.toString(pk)});
	}
	
	public void deleteAll_group() {
		this.db.delete(TABLE_NAME_GROUP, null, null);
	}
	
	public List<String[]> select_to_do_title_place() {
		List<String[]> list = new ArrayList<String[]>();
		Cursor cursor = this.db.query(TABLE_NAME_TO_DO, null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				list.add(new String[] {cursor.getString(cursor.getColumnIndex(TITLE)), cursor.getString(cursor.getColumnIndex(PLACE))});
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed())
			cursor.close();
		return list;
	}
	
	public List<String> selectAll_to_do(String col_name) {
		List<String> list = new ArrayList<String>();
		Cursor cursor = this.db.query(TABLE_NAME_TO_DO, new String[] {col_name}, null, null, null, null, "td_id asc");
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(cursor.getColumnIndex(col_name)));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}
	
	public List<String> select_to_do(String title) {
		List<String> list = new ArrayList<String>();
		String selection = "title" + " = '" + title + "'";
		Cursor cursor = this.db.query(TABLE_NAME_TO_DO, null, selection, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(cursor.getColumnIndex("title")));
				list.add(cursor.getString(cursor.getColumnIndex("place")));
				list.add(cursor.getString(cursor.getColumnIndex("note")));
				list.add(cursor.getString(cursor.getColumnIndex("tag")));
				list.add(cursor.getString(cursor.getColumnIndex("assembly")));
				list.add(cursor.getString(cursor.getColumnIndex("status")));
				list.add(cursor.getString(cursor.getColumnIndex("priority")));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}
	
	public List<String> select_to_do(int pk) {
		List<String> list = new ArrayList<String>();
		String selection = "td_id = " + pk;
		Cursor cursor = this.db.query(TABLE_NAME_TO_DO, null, selection, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(cursor.getColumnIndex("title")));
				list.add(cursor.getString(cursor.getColumnIndex("place")));
				list.add(cursor.getString(cursor.getColumnIndex("note")));
				list.add(cursor.getString(cursor.getColumnIndex("tag")));
				list.add(cursor.getString(cursor.getColumnIndex("assembly")));
				list.add(cursor.getString(cursor.getColumnIndex("status")));
				list.add(cursor.getString(cursor.getColumnIndex("priority")));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}
	
	public int select_primary_key(String title) {
		int pk = -1;
		String selection = "title" + " = '" + title + "'";
		Cursor cursor = this.db.query(TABLE_NAME_TO_DO, null, selection, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				pk = cursor.getInt(cursor.getColumnIndex("td_id"));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return pk;
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
			db.execSQL("CREATE TABLE " + TABLE_NAME_TO_DO + " (td_id INTEGER PRIMARY KEY, title TEXT, place TEXT, note TEXT, tag TEXT, assembly TEXT, status TEXT, priority TEXT)");
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
