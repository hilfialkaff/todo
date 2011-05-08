package eecs.berkeley.edu.cs294;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

public class PushServerGroup extends Activity {

	/*
	 * Create a new group in the server. Called when a user create a corresponding one locally.
	 */
	public static int create(List<String> entry) {
		String url = ServerConnection.homeurl + ServerConnection.users_link + 
		ToDo_Replica.dh.select_user().get(DatabaseHelper.USER_RAILS_ID_INDEX_U) + 
		ServerConnection.groups_link;
		
		Log.d("ServerDEBUG", "POST to " + url);
		
		DefaultHttpClient client = new DefaultHttpClient();
		
		HttpPost registerRequest = new HttpPost(url);
		
		JSONObject group = new JSONObject();
		JSONObject details = new JSONObject();

		/* Setting up the packet to be sent to server */
		try {
			details.put("name", entry.get(DatabaseHelper.NAME_INDEX_G));
			details.put("description", entry.get(DatabaseHelper.DESCRIPTION_INDEX_G));
			
			group.put("group", details);

			Log.d("ServerDEBUG", "Group Register JSON = "+ group.toString());

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
			JSONObject groupObject = jObject.getJSONObject("group");
			String railsID = groupObject.getString("id");
			
			int pk = Integer.parseInt(entry.get(DatabaseHelper.GROUP_ID_INDEX_G));
			ToDo_Replica.dh.update_group(pk, null, null, null, null, railsID);
		} catch (Exception e) {
			Log.e("JSON E", ""+e);
			e.printStackTrace();
			return -1;
		}

		return 0;
	}
	
	/*
	 * Updates a group in the server. Called when a user updates a corresponding one locally.
	 */
	public static int update(List<String> entry) {
		String url = ServerConnection.homeurl + ServerConnection.users_link + 
		ToDo_Replica.dh.select_user().get(DatabaseHelper.USER_RAILS_ID_INDEX_U) + 
		ServerConnection.groups_link + entry.get(DatabaseHelper.GROUP_RAILS_ID_INDEX_G);  
		
		Log.d("ServerDEBUG", "PUT to " + url);
		
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPut putRequest = new HttpPut(url);
		
		JSONObject group = new JSONObject();
		JSONObject details = new JSONObject();

		/* Setting up the packet to be sent to server */
		try {
			details.put("name", entry.get(DatabaseHelper.NAME_INDEX_G));
			details.put("description", entry.get(DatabaseHelper.DESCRIPTION_INDEX_G));
			
			group.put("group", details);

			Log.d("ServerDEBUG", "Group JSON = "+ group.toString());

			StringEntity se = new StringEntity(group.toString());

			putRequest.setEntity(se);
			putRequest.setHeader("Content-Type","application/json");
			putRequest.setHeader("Accept", "application/json");
			
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
			response = client.execute(putRequest);
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

	/*
	 * Deletes a group in the server. Called when a user deletes a corresponding one locally.
	 */
	public static int delete(List<String> entry) {
		String url = ServerConnection.homeurl + ServerConnection.users_link + 
		ToDo_Replica.dh.select_user().get(DatabaseHelper.USER_RAILS_ID_INDEX_U) + 
		ServerConnection.groups_link + entry.get(DatabaseHelper.GROUP_RAILS_ID_INDEX_G);
		
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
	
	/*
	 * Unsubscribe from a group in the server.
	 */
	public static int unsubscribe(List<String> entry) {
		HttpClient httpClient = new DefaultHttpClient();
		String xmlResponse;
		String url = ServerConnection.homeurl + ServerConnection.users_link + 
		ToDo_Replica.dh.select_user().get(DatabaseHelper.USER_RAILS_ID_INDEX_U) + 
		ServerConnection.unsubscribe_link + "?group_name=" + 
		entry.get(DatabaseHelper.NAME_INDEX_G) + "&user_name=" + 
		ToDo_Replica.dh.select_user().get(DatabaseHelper.NAME_INDEX_U);

		try
		{	
			Log.d("ServerDEBUG", "performing get " + url );

			HttpGet method = new HttpGet( new URI(url) );
			HttpResponse response = httpClient.execute(method);
			if ( response != null )
			{
				xmlResponse = ServerConnection.getResponse(response.getEntity());
				Log.d("ServerDEBUG", "got response: " + xmlResponse);
			}
			else
			{
				Log.d( "ServerDEBUG", "got a null response" );
				return -1;
			}
		} catch (IOException e) {
			Log.e( "Error", "IOException " + e.getMessage() );
			return -1;
		} catch (URISyntaxException e) {
			Log.e( "Error", "URISyntaxException " + e.getMessage() );
			return -1;
		}
		
		return 0;
	}	
}