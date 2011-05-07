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

	// public static final int NUM_ENTRIES = 7;

	/* Indexes of the various entries in the todo table */
	public static final int TITLE_INDEX_T = 0;
	public static final int PLACE_INDEX_T = 1;
	public static final int NOTE_INDEX_T = 2;
	public static final int TAG_INDEX_T = 3;
	public static final int GROUP_ID_INDEX_T = 4;
	public static final int STATUS_INDEX_T = 5;
	public static final int PRIORITY_INDEX_T = 6;
	public static final int TIMESTAMP_INDEX_T = 7;
	public static final int DEADLINE_T = 8;
	public static final int TO_DO_RAILS_ID_INDEX_T = 9;
	public static final int TD_ID_INDEX_T = 10;
	
	/* Indexes of the various entries in the group table */
	public static final int NAME_INDEX_G = 0;
	public static final int DESCRIPTION_INDEX_G = 1;
	public static final int MEMBER_INDEX_G = 2;
	public static final int GROUP_RAILS_ID_INDEX_G = 3;
	
	/* Indexes of the various entries in the member table */
	public static final int NAME_INDEX_M = 0;
	public static final int NUMBER_INDEX_M = 1;
	public static final int EMAIL_INDEX_M = 2;
	public static final int GROUP_ID_INDEX_M = 3;
	public static final int MEMBER_RAILS_ID_INDEX_M = 4;
	
	/* Tables that exists in the database */
	private static final String TABLE_NAME_TO_DO = "to_do_table";
	private static final String TABLE_NAME_GROUP = "group_table";
	private static final String TABLE_NAME_MEMBER = "member_table";
	private static final String TABLE_NAME_USER = "user_table";
	private static final String TABLE_NAME_SENT_INVITATION = "sent_invitation_table";
	private static final String TABLE_NAME_RECV_INVITATION = "recv_invitation_table";
	private static final String TABLE_NAME_MAP_GROUP_T0_DO = "map_group_to_do";
	private static final String TABLE_NAME_MAP_GROUP_MEMBER = "map_group_member";
	
	private static final String INSERT_TO_DO = "insert into " + TABLE_NAME_TO_DO + " (td_id, title, place, note, tag, group_id, status, priority, timestamp, deadline, to_do_rails_id) values (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String INSERT_GROUP = "insert into " + TABLE_NAME_GROUP + " (g_id, name, description, member, group_rails_id) values (NULL, ?, ?, ?, ?)";
	private static final String INSERT_MEMBER = "insert into " + TABLE_NAME_MEMBER + " (m_id, name, number, email, group_id, member_rails_id) values (NULL, ?, ?, ?, ?, ?)";
	private static final String INSERT_USER = "insert into " + TABLE_NAME_USER + " (name, number, email, password) values (?, ?, ?, ?)";
	private static final String INSERT_SENT_INVITATION = "insert into " + TABLE_NAME_SENT_INVITATION + " (sent_id, recipient, groupz, status, sent_rails_id) values (NULL, ?, ?, ?, ?)";
	private static final String INSERT_RECV_INVITATION = "insert into " + TABLE_NAME_RECV_INVITATION + " (recv_id, sender, groupz, recv_rails_id) values (NULL, ?, ?, ?)";
	private static final String INSERT_MAP_GROUP_TO_DO = "insert into " + TABLE_NAME_MAP_GROUP_T0_DO + " (map_group, map_to_do) values (?, ?)";
	private static final String INSERT_MAP_GROUP_MEMBER = "insert into " + TABLE_NAME_MAP_GROUP_MEMBER + " (map_group, map_member) values (?, ?)";
	
	public static final String TITLE = "title";
	public static final String PLACE = "place"; 

	private Context context;
	private SQLiteDatabase db;
	private SQLiteStatement insertStmt_to_do, insertStmt_group, insertStmt_member, insertStmt_sent, insertStmt_recv, insertStmt_map_group_to_do, insertStmt_map_group_member, insertStmt_user;

	public DatabaseHelper(Context context) {
		this.context = context;
		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
		this.insertStmt_to_do = this.db.compileStatement(INSERT_TO_DO);
		this.insertStmt_group = this.db.compileStatement(INSERT_GROUP);
		this.insertStmt_member = this.db.compileStatement(INSERT_MEMBER);
		this.insertStmt_user = this.db.compileStatement(INSERT_USER);
		this.insertStmt_sent = this.db.compileStatement(INSERT_SENT_INVITATION);
		this.insertStmt_recv = this.db.compileStatement(INSERT_RECV_INVITATION);
		this.insertStmt_map_group_to_do = this.db.compileStatement(INSERT_MAP_GROUP_TO_DO);
		this.insertStmt_map_group_member = this.db.compileStatement(INSERT_MAP_GROUP_MEMBER);
	}
	
	public long insert_user(String name, String number, String email, String password) {	
		this.insertStmt_user.clearBindings();
		this.insertStmt_user.bindString(1, name);
		this.insertStmt_user.bindString(2, number);
		this.insertStmt_user.bindString(3, email);
		this.insertStmt_user.bindString(4, password);
		return this.insertStmt_user.executeInsert();
	}
	
	public List<String> select_user() {
		List<String> list = new ArrayList<String>();
		Cursor cursor = this.db.query(TABLE_NAME_USER, null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(cursor.getColumnIndex("name")));
				list.add(cursor.getString(cursor.getColumnIndex("number")));
				list.add(cursor.getString(cursor.getColumnIndex("email")));
				list.add(cursor.getString(cursor.getColumnIndex("password")));
			} while (cursor.moveToNext());
		}
		if (cursor != null || !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}
	
	public long insert_to_do(String title, String place, String note, String tag, int group_id, String status, String priority, String timestamp, String deadline, String to_do_rails_id) {	

		this.insertStmt_to_do.clearBindings();
		this.insertStmt_to_do.bindString(1, title);
		this.insertStmt_to_do.bindString(2, place);
		this.insertStmt_to_do.bindString(3, note);
		this.insertStmt_to_do.bindString(4, tag);
		this.insertStmt_to_do.bindLong(5, group_id);
		this.insertStmt_to_do.bindString(6, status);
		this.insertStmt_to_do.bindString(7, priority);
		this.insertStmt_to_do.bindString(8, timestamp);
		this.insertStmt_to_do.bindString(9, deadline);
		this.insertStmt_to_do.bindString(10, to_do_rails_id);

		Log.d("DbDEBUG", "INSERT title: " + title + " place: " + place + " note: " + note + 
				" tag: " + tag + " group_id: " + group_id + " status: " + status + 
				" priority: " + priority + " timestamp: " + timestamp + " deadline: " + deadline +  " to_do_rails_id: " + to_do_rails_id);
		
		return this.insertStmt_to_do.executeInsert();
	}

	public long update_to_do(int td_id, String title, String place, String note, String tag, int group_id, String status, String priority, String timestamp, String deadline, String to_do_rails_id) {
		ContentValues cv = new ContentValues();
		if(title != null) {
			cv.put("title", title);
		}
		if(place != null) {
			cv.put("place", place);
		}
		if(note != null) {
			cv.put("note", note);
		}
		if(tag != null) {
			cv.put("tag", tag);
		}
		if(group_id >= 1) {
			cv.put("group_id", group_id);
		}
		if(status != null) {
			cv.put("status", status);
		}
		if(priority != null) {
			cv.put("priority", priority);
		}
		if(timestamp != null) {
			cv.put("timestamp", timestamp);
		}
		if(deadline != null) {
			cv.put("deadline", deadline);
		}
		if(to_do_rails_id != null) {
			cv.put("to_do_rails_id", to_do_rails_id);
		}

		Log.d("DbDEBUG", "UPDATE title: " + title + " place: " + place + " note: " + note + 
				" tag: " + tag + " group_id: " + group_id + " status: " + status + 
				" priority: " + priority + " timestamp: " + timestamp + " deadline: " + deadline +  " to_do_rails_id: " + to_do_rails_id);

		String selection = "td_id = ?";
		return db.update(TABLE_NAME_TO_DO, cv, selection, new String[] {Integer.toString(td_id)});
	}
	
	public void delete_all_to_do() {
		this.db.delete(TABLE_NAME_TO_DO, null, null);
	}

	public void delete_to_do(int td_id) {
		String selection = "td_id = ?";
		this.db.delete(TABLE_NAME_TO_DO, selection, new String[] {Integer.toString(td_id)});
	}

	public void delete_to_do(String to_do_rails_id) {
		String selection = "to_do_rails_id = ?";
		this.db.delete(TABLE_NAME_TO_DO, selection, new String[] {to_do_rails_id});
	}
	
	public List<String> select_all_to_do(String column) {
		List<String> list = new ArrayList<String>();
		Cursor cursor = this.db.query(TABLE_NAME_TO_DO, new String[] {column}, null, null, null, null, "td_id asc");
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(cursor.getColumnIndex(column)));
			} while (cursor.moveToNext());
		}
		if (cursor != null || !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}
	
	public List<String[]> select_to_do_title_place() {
		List<String[]> list = new ArrayList<String[]>();
		Cursor cursor = this.db.query(TABLE_NAME_TO_DO, null, null, null, null, null, "td_id asc");
		if (cursor.moveToFirst()) {
			do {
				list.add(new String[] {cursor.getString(cursor.getColumnIndex(TITLE)), cursor.getString(cursor.getColumnIndex(PLACE))});
			} while (cursor.moveToNext());
		}
		if (cursor != null || !cursor.isClosed())
			cursor.close();
		return list;
	}

	

	public List<String> select_to_do(String column, String value) {
		List<String> list = new ArrayList<String>();
		String selection = column + " = '" + value + "'";
		Cursor cursor = this.db.query(TABLE_NAME_TO_DO, null, selection, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(cursor.getColumnIndex("title")));
				list.add(cursor.getString(cursor.getColumnIndex("place")));
				list.add(cursor.getString(cursor.getColumnIndex("note")));
				list.add(cursor.getString(cursor.getColumnIndex("tag")));
				list.add(cursor.getString(cursor.getColumnIndex("group_id")));
				list.add(cursor.getString(cursor.getColumnIndex("status")));
				list.add(cursor.getString(cursor.getColumnIndex("priority")));
				list.add(cursor.getString(cursor.getColumnIndex("timestamp")));
				list.add(cursor.getString(cursor.getColumnIndex("deadline")));
				list.add(cursor.getString(cursor.getColumnIndex("to_do_rails_id")));
				list.add(cursor.getString(cursor.getColumnIndex("td_id")));
			} while (cursor.moveToNext());
		}
		if (cursor != null || !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

	public List<String> select_to_do(int td_id) {
		List<String> list = new ArrayList<String>();
		String selection = "td_id = " + td_id;
		Cursor cursor = this.db.query(TABLE_NAME_TO_DO, null, selection, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(cursor.getColumnIndex("title")));
				list.add(cursor.getString(cursor.getColumnIndex("place")));
				list.add(cursor.getString(cursor.getColumnIndex("note")));
				list.add(cursor.getString(cursor.getColumnIndex("tag")));
				list.add(cursor.getString(cursor.getColumnIndex("group_id")));
				list.add(cursor.getString(cursor.getColumnIndex("status")));
				list.add(cursor.getString(cursor.getColumnIndex("priority")));
				list.add(cursor.getString(cursor.getColumnIndex("timestamp")));
				list.add(cursor.getString(cursor.getColumnIndex("deadline")));
				list.add(cursor.getString(cursor.getColumnIndex("to_do_rails_id")));
			} while (cursor.moveToNext());
		}
		if (cursor != null || !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}
	
	public int select_to_do_primary_key(String title) {
		int td_id = -1;
		String selection = "title" + " = '" + title + "'";
		Cursor cursor = this.db.query(TABLE_NAME_TO_DO, null, selection, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				td_id = cursor.getInt(cursor.getColumnIndex("td_id"));
			} while (cursor.moveToNext());
		}
		if (cursor != null || !cursor.isClosed()) {
			cursor.close();
		}
		return td_id;
	}
	
	public long insert_group(String name, String description, String member, String group_rails_id) {
		this.insertStmt_group.clearBindings();
		this.insertStmt_group.bindString(1, name);
		this.insertStmt_group.bindString(2, description);
		this.insertStmt_group.bindString(3, member);
		this.insertStmt_group.bindString(4, group_rails_id);
		return this.insertStmt_group.executeInsert();
	}
	
	public void deleteAll_group() {
		this.db.delete(TABLE_NAME_GROUP, null, null);
	}

	public List<String> select_all_group_name() {
		List<String> list = new ArrayList<String>();
		Cursor cursor = this.db.query(TABLE_NAME_GROUP, new String[] {"name"}, null, null, null, null, "name asc");
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(cursor.getColumnIndex("name")));
			} while (cursor.moveToNext());
		}
		if (cursor != null || !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

	public long insert_sent(int sent_id, String recipient, String groupz, int status) {
		this.insertStmt_sent.clearBindings();
		this.insertStmt_sent.bindLong(1, sent_id);
		this.insertStmt_sent.bindString(2, recipient);
		this.insertStmt_sent.bindString(3, groupz);
		this.insertStmt_sent.bindLong(4, status);
		return this.insertStmt_sent.executeInsert();
	}
	
	public long insert_recv(int recv_id, String sender, String groupz) {
		this.insertStmt_recv.clearBindings();
		this.insertStmt_recv.bindLong(1, recv_id);
		this.insertStmt_recv.bindString(2, sender);
		this.insertStmt_recv.bindString(3, groupz);
		return this.insertStmt_recv.executeInsert();
	}

	private static class OpenHelper extends SQLiteOpenHelper {
		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + TABLE_NAME_TO_DO + " (td_id INTEGER PRIMARY KEY, title TEXT, place TEXT, note TEXT, tag TEXT, group_id TEXT, status TEXT, priority TEXT, timestamp TEXT, deadline TEXT, to_do_rails_id TEXT)");
			db.execSQL("CREATE TABLE " + TABLE_NAME_GROUP + " (g_id INTEGER PRIMARY KEY, name TEXT, description TEXT, member TEXT, group_rails_id TEXT)");
			db.execSQL("CREATE TABLE " + TABLE_NAME_MEMBER + " (m_id INTEGER PRIMARY KEY, name TEXT, number TEXT, email TEXT, group_id INTEGER, member_rails_id TEXT)");			
			db.execSQL("CREATE TABLE " + TABLE_NAME_USER + " (name TEXT, number TEXT, email TEXT, password TEXT)");			
			db.execSQL("CREATE TABLE " + TABLE_NAME_SENT_INVITATION + " (sent_id INTEGER PRIMARY KEY, recipient TEXT, groupz TEXT, status INTEGER, sent_rails_id TEXT)");
			db.execSQL("CREATE TABLE " + TABLE_NAME_RECV_INVITATION + " (recv_id INTEGER PRIMARY KEY, sender TEXT, groupz TEXT, recv_rails_id TEXT)");
			db.execSQL("CREATE TABLE " + TABLE_NAME_MAP_GROUP_T0_DO + " (map_group INTEGER, map_to_do INTEGER, FOREIGN KEY(map_group) REFERENCES garden(g_id), FOREIGN KEY(map_to_do) REFERENCES plot(td_id))");
			db.execSQL("CREATE TABLE " + TABLE_NAME_MAP_GROUP_MEMBER + " (map_group INTEGER, map_member INTEGER, FOREIGN KEY(map_group) REFERENCES garden(g_id), FOREIGN KEY(map_member) REFERENCES plot(m_id))");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("debug", "noctis reset");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TO_DO);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_GROUP);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MEMBER);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SENT_INVITATION);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_RECV_INVITATION);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MAP_GROUP_T0_DO);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MAP_GROUP_MEMBER);
			onCreate(db);
		}
	}
}
