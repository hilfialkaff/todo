package eecs.berkeley.edu.cs294;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

public class PushServerSentInv extends Activity {

	/*
	 * Create a new sent invitation in the server. Called when a user create a corresponding
	 * one locally
	 */
	public static int create(List<String> entry) {
		/* TODO: user id database */
		
		String url = ServerConnection.homeurl + ServerConnection.users_link + 
		ToDo_Replica.dh.select_user().get(DatabaseHelper.USER_RAILS_ID_INDEX_U) + 
		ServerConnection.my_sent_invs_link;
		
		Log.d("ServerDEBUG", "POST to " + url);
		
		DefaultHttpClient client = new DefaultHttpClient();
		
		HttpPost registerRequest = new HttpPost(url);
		
		JSONObject sentInvitation = new JSONObject();
		JSONObject details = new JSONObject();

		/* Setting up the packet to be sent to server */
		try {
			String group_id = entry.get(DatabaseHelper.GROUPZ_ID_INDEX_S);
			List<String> group = ToDo_Replica.dh.select_group("g_id", group_id); 
			String group_name = entry.get(DatabaseHelper.NAME_INDEX_G);
				
			details.put("group", entry.get(DatabaseHelper.GROUPZ_ID_INDEX_S));
			details.put("recipient", entry.get(DatabaseHelper.RECIPIENT_ID_INDEX_S));
			details.put("description", entry.get(DatabaseHelper.DESCRIPTION_INDEX_S));
			details.put("status", "Pending");
			
			sentInvitation.put("sent-invitation", details);

			Log.d("ServerDEBUG", "Sent Invitation Register JSON = "+ 
					sentInvitation.toString());

			StringEntity se = new StringEntity(group.toString());

			registerRequest.setEntity(se);
			registerRequest.setHeader("Content-Type","application/json");
			registerRequest.setHeader("Accept", "application/json");
			
		} catch (UnsupportedEncodingException e) {
			Log.e("Error",""+e);
			e.printStackTrace();	
			return -1;
		
		} catch (JSONException js) {
			js.printStackTrace();	
			return -1;
		}

		/* Sending... */
		HttpResponse response = null;
		try {
			response = client.execute(registerRequest);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			Log.e("ClientProtocol",""+e);
			return -1;
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("IO",""+e);
			return -1;
		}

		/* Parsing the response from the server */
		HttpEntity entity = response.getEntity();
		String stringResponse = ServerConnection.getResponse(entity);
		
		if (entity != null) {
			try {
				entity.consumeContent();
			} catch (IOException e) {
				Log.e("IO E",""+e);
				e.printStackTrace();
				return -1;
			}
		}	

		Log.d("ServerDEBUG", "response: " + stringResponse);
		
		try {
			JSONObject jObject = new JSONObject(stringResponse);
			JSONObject sentInvitationObject = jObject.getJSONObject("sent-invitation");
			String railsID = sentInvitationObject.getString("id");
			
			int pk = Integer.parseInt(entry.get(DatabaseHelper.SENT_ID_INDEX_S));
			ToDo_Replica.dh.update_group(pk, null, null, null, null, railsID);
		} catch (Exception e) {
			Log.e("JSON E", ""+e);
			e.printStackTrace();
			return -1;
		}

		return 0;
	}
	
	/*
	 * Deletes a sent invitation in the server. Called when a user deletes a corresponding
	 * one locally
	 */
	public static int delete(List<String> entry) {
/* TODO: user id database? " */
		
		String url = ServerConnection.homeurl + ServerConnection.users_link + 
		ToDo_Replica.dh.select_user().get(DatabaseHelper.USER_RAILS_ID_INDEX_U) + 
		ServerConnection.my_sent_invs_link + entry.get(DatabaseHelper.SENT_RAILS_ID_INDEX_S);
		
		Log.d("ServerDEBUG", "DELETE to " + url);
		
		DefaultHttpClient client = new DefaultHttpClient();
		
		HttpDelete deleteRequest = new HttpDelete(url);
		HttpResponse response = null;

		Log.d("ServerDEBUG", "DELETE to " + url);
		
		/* Sending... */
		try {
			response = client.execute(deleteRequest);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			Log.e("ClientProtocol",""+e);
			return -1;
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("IO",""+e);
			return -1;
		}

		/* Parsing the response from the server */
		HttpEntity entity = response.getEntity();
		String stringResponse = ServerConnection.getResponse(entity);
		if (entity != null) {
			try {
				entity.consumeContent();
			} catch (IOException e) {
				Log.e("IO E",""+e);
				e.printStackTrace();
				return -1;
			}
		}

		Log.d("ServerDEBUG", "response: " + stringResponse);

		return 0;
	}		
}