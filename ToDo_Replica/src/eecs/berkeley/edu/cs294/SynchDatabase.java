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

	/* TODO */
	
	/*
	 * Synchronize sent invitations in the local db w/ the server
	 */
	public static void SynchSentInvitations(ArrayList<MySentInvitation> sentInvitationList) {
		List<String> deletedRailsID = ToDo_Replica.dh.select_all_sent_invitations("sent_rails_id");
		
		for(Iterator<MySentInvitation> it = sentInvitationList.iterator(); it.hasNext();) {
			MySentInvitation currInvitation= it.next();
			
			int currID = Integer.parseInt(currInvitation.getRailsID());
			Time time = new Time();
			Assert.assertTrue(time.parse(currInvitation.getTimestamp()));
			long serverTimestamp = time.normalize(false);
			List<String> entry = ToDo_Replica.dh.select_sent_invitation("sent_rails_id", 
					Integer.toString(currID));
			
			/* The entry hasn't existed in the local db yet */
			if(entry.size() == 0) {
				Log.d("ServerDEBUG", "Server entry not inside local db yet");
				ToDo_Replica.dh.insert_sent_invitation(currInvitation.getRecipient(), 
						currInvitation.getGroup(), currInvitation.getStatus(), currInvitation.getDescription(),
						Long.toString(serverTimestamp), currInvitation.getRailsID());
				
				continue;
			}
			
			long dbTimestamp = Long.parseLong(entry.get(DatabaseHelper.TIMESTAMP_INDEX_S));
			Log.d("ServerDEBUG", "comparing " + currInvitation.getRailsID() + 
					" server timestamp: " + serverTimestamp + " local timestamp: " + dbTimestamp);
		
			deletedRailsID.remove(Integer.toString(currID));
			
			/* No need for any local database update */
			if(dbTimestamp >= serverTimestamp) {
				Log.d("ServerDEBUG", "Local Db has the last update");
				continue;
			}

			/* Need to update local database since changes in the server is more recent */
			else {
				String recipient = currInvitation.getRecipient(); 
				String group = currInvitation.getGroup();
				String status = currInvitation.getStatus();
				String description = currInvitation.getDescription();
				String timestamp = Long.toString(serverTimestamp);
				String railsID = currInvitation.getRailsID();
				int sent_id = Integer.parseInt(entry.get(DatabaseHelper.SENT_ID_INDEX_S));
				
				ToDo_Replica.dh.update_sent_invitation(sent_id, recipient, group, status, 
						description, timestamp, railsID);
			}
		}
		
		pruneLocalSentInvitations(deletedRailsID);
	}
	
	private static void pruneLocalSentInvitations(List<String> deletedRailsID)  {
		for(Iterator<String> it = deletedRailsID.iterator(); it.hasNext();) {
			String railsID = it.next();
			Log.d("ServerDEBUG", "deleting todo with id: " + railsID);
			ToDo_Replica.dh.delete_sent_invitation("sent_rails_id", railsID);
		}	
	}
	
	/*
	 * Synchronize received invitations in the local db w/ the server
	 */
	public static void SynchRecvInvitations(ArrayList<MyRecvInvitation> recvInvitationList) {
//		List<String> deletedRailsID = ToDo_Replica.dh.
//		select_all_recv_invitations("recv_rails_id");
//		
//		for(Iterator<MyRecvInvitation> it = recvInvitationList.iterator(); it.hasNext();) {
//			MyRecvInvitation currInvitation= it.next();
//			
//			int currID = Integer.parseInt(currInvitation.getRailsID());
//			Time time = new Time();
//			Assert.assertTrue(time.parse(currInvitation.getTimestamp()));
//			long serverTimestamp = time.normalize(false);
//			List<String> entry = ToDo_Replica.dh.select_sent_invitation("sent_rails_id", 
//					Integer.toString(currID));
//			
//			/* The entry hasn't existed in the local db yet */
//			if(entry.size() == 0) {
//				Log.d("ServerDEBUG", "Server entry not inside local db yet");
//				ToDo_Replica.dh.insert_sent_invitation(currInvitation.getRecipient(), 
//						currInvitation.getGroup(), currInvitation.getStatus(), currInvitation.getDescription(),
//						Long.toString(serverTimestamp), currInvitation.getRailsID());
//				
//				continue;
//			}
//			
//			long dbTimestamp = Long.parseLong(entry.get(DatabaseHelper.TIMESTAMP_INDEX_S));
//			Log.d("ServerDEBUG", "comparing " + currInvitation.getRailsID() + 
//					" server timestamp: " + serverTimestamp + " local timestamp: " + dbTimestamp);
//		
//			deletedRailsID.remove(Integer.toString(currID));
//			
//			/* No need for any local database update */
//			if(dbTimestamp >= serverTimestamp) {
//				Log.d("ServerDEBUG", "Local Db has the last update");
//				continue;
//			}
//
//			/* Need to update local database since changes in the server is more recent */
//			else {
//				String recipient = currInvitation.getRecipient(); 
//				String group = currInvitation.getGroup();
//				String status = currInvitation.getStatus();
//				String description = currInvitation.getDescription();
//				String timestamp = Long.toString(serverTimestamp);
//				String railsID = currInvitation.getRailsID();
//				int sent_id = Integer.parseInt(entry.get(DatabaseHelper.SENT_ID_INDEX_S));
//				
//				ToDo_Replica.dh.update_sent_invitation(sent_id, recipient, group, status, 
//						description, timestamp, railsID);
//			}
//		}
//		
//		pruneLocalSentInvitations(deletedRailsID);		
	}
	
	/*
	 * Synchronize groups in the local db w/ the server
	 */
	public static void SynchGroups(ArrayList<MyGroup> groupList) {
		
	}
	
	/*
	 * Synchronize todos in the local db w/ the server
	 */
	public static void SynchTodos(ArrayList<MyTodo> todoList) {
		
	}
	
	/*
	 * Synchronize group members in the local db w/ the server
	 */
	public static void SynchGroupMembers(ArrayList<MyGroupMember> groupMembers) {
		
	}
	
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
		
		pruneLocalTodos(deletedRailsID);				
	}
	
	private static void pruneLocalTodos(List<String> deletedRailsID)  {
		for(Iterator<String> it = deletedRailsID.iterator(); it.hasNext();) {
			String railsID = it.next();
			Log.d("ServerDEBUG", "deleting todo with id: " + railsID);
			ToDo_Replica.dh.delete_to_do("td_id", railsID);
		}	
	}
}