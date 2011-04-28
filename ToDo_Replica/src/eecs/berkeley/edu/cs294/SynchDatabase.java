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
		List<String> deletedRailsID = ToDo_Replica.dh.selectAll_to_do("railsID");
		
		for(Iterator<MyTodo> it = todoList.iterator(); it.hasNext();) {
			MyTodo currTodo = it.next();
			
			int currID = Integer.parseInt(currTodo.getTodoRailsID());
			Time time = new Time();
			Assert.assertTrue(time.parse(currTodo.getTodoTimestamp()));
			long serverTimestamp = time.normalize(false);
			List<String> entry = ToDo_Replica.dh.select_to_do_railsID(Integer.toString(currID));
			
			/* The entry hasn't existed in the local db yet */
			if(entry.size() == 0) {
				Log.d("ServerDEBUG", "Server entry not inside local db yet");
				ToDo_Replica.dh.insert_to_do(currTodo.getTodoName(), currTodo.getTodoPlace(), 
						currTodo.getTodoNote(), currTodo.getTodoTag(), currTodo.getTodoGroup(), 
						currTodo.getTodoStatus(), currTodo.getTodoPriority(), 
						Long.toString(serverTimestamp), currTodo.getTodoRailsID());
				
				continue;
			}
			
			long dbTimestamp = Long.parseLong(entry.get(DatabaseHelper.TIMESTAMP_INDEX));
			Log.d("ServerDEBUG", "comparing " + currTodo.getTodoName() + 
					" server timestamp: " + serverTimestamp + " local timestamp: " + dbTimestamp);
		
			deletedRailsID.remove(Integer.toString(currID));
			
			/* No need for any local database update */
			if(dbTimestamp >= serverTimestamp) {
				Log.d("ServerDEBUG", "Local Db has the last update");
				continue;
			}

			/* Need to update local database since changes in the server is more recent */
			else {
				String title = currTodo.getTodoName();
				String place = currTodo.getTodoPlace();
				String note = currTodo.getTodoNote();
				String tag = currTodo.getTodoTag();
				String group = currTodo.getTodoGroup();
				String status = currTodo.getTodoStatus();
				String priority = currTodo.getTodoPriority();
				String dateStr = Long.toString(serverTimestamp);
				int pk = Integer.parseInt(entry.get(DatabaseHelper.TD_ID_INDEX));
				
				ToDo_Replica.dh.update_to_do(pk, title, place, note, tag, group, status, priority, dateStr, null);
			}
		}
		
		pruneLocalDb(deletedRailsID);				
	}
	
	private static void pruneLocalDb(List<String> deletedRailsID)  {
		for(Iterator<String> it = deletedRailsID.iterator(); it.hasNext();) {
			String railsID = it.next();
			Log.d("ServerDEBUG", "deleting todo with id: " + railsID);
			ToDo_Replica.dh.delete_to_do_railsID(railsID);
		}	
	}
}