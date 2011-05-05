package eecs.berkeley.edu.cs294;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import android.app.Activity;
import android.text.format.Time;
import android.util.Log;

/*
 * This class takes care of synchronizing the local database with the remote one
 */
public class SynchDatabase extends Activity {
	
	/*
	 * Synchronize local database w/ changes from the server
	 */
	public static void synchDb(ArrayList<MyTodo> todoList) {
		List<String> deletedRailsID = ToDo_Replica.dh.select_all_to_do("railsID");
		
		for(Iterator<MyTodo> it = todoList.iterator(); it.hasNext();) {
			MyTodo currTodo = it.next();
			
			int currID = Integer.parseInt(currTodo.getTodoRailsId());
			Time time = new Time();
			Assert.assertTrue(time.parse(currTodo.getTodoTimestamp()));
			long serverTimestamp = time.normalize(false);
			List<String> entry = ToDo_Replica.dh.select_to_do("to_do_rails_id", Integer.toString(currID));
			
			/* The entry hasn't existed in the local db yet */
			if(entry.size() == 0) {
				Log.d("ServerDEBUG", "Server entry not inside local db yet");
				ToDo_Replica.dh.insert_to_do(currTodo.getTodoTitle(), currTodo.getTodoPlace(), 
						currTodo.getTodoNote(), currTodo.getTodoTag(), Integer.parseInt(currTodo.getTodoGroupId()), 
						currTodo.getTodoStatus(), currTodo.getTodoPriority(), 
						Long.toString(serverTimestamp), currTodo.getTodoDeadline(), currTodo.getTodoRailsId());
				
				continue;
			}
			
			long dbTimestamp = Long.parseLong(entry.get(DatabaseHelper.TIMESTAMP_INDEX_T));
			Log.d("ServerDEBUG", "comparing " + currTodo.getTodoTitle() + 
					" server timestamp: " + serverTimestamp + " local timestamp: " + dbTimestamp);
		
			deletedRailsID.remove(Integer.toString(currID));
			
			/* No need for any local database update */
			if(dbTimestamp >= serverTimestamp) {
				Log.d("ServerDEBUG", "Local Db has the last update");
				continue;
			}

			/* Need to update local database since changes in the server is more recent */
			else {
				String title = currTodo.getTodoTitle();
				String place = currTodo.getTodoPlace();
				String note = currTodo.getTodoNote();
				String tag = currTodo.getTodoTag();
				int group = Integer.parseInt(currTodo.getTodoGroupId());
				String status = currTodo.getTodoStatus();
				String priority = currTodo.getTodoPriority();
				String dateStr = Long.toString(serverTimestamp);
				String deadline = currTodo.getTodoDeadline();
				int pk = Integer.parseInt(entry.get(DatabaseHelper.TD_ID_INDEX_T));
				
				ToDo_Replica.dh.update_to_do(pk, title, place, note, tag, group, status, priority, deadline, dateStr, null);
			}
		}
		
		pruneLocalDb(deletedRailsID);				
	}
	
	private static void pruneLocalDb(List<String> deletedRailsID)  {
		for(Iterator<String> it = deletedRailsID.iterator(); it.hasNext();) {
			String railsID = it.next();
			Log.d("ServerDEBUG", "deleting todo with id: " + railsID);
			ToDo_Replica.dh.delete_to_do(railsID);
		}	
	}
}