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

public class PushServerUser extends Activity {

	/*
	 * Create a new user in the server. Called when a user registers himself.
	 */
	public static int create(List<String> entry) {
		String url = ServerConnection.homeurl + ServerConnection.users_link;
		
		Log.d("ServerDEBUG", "POST to " + url);
		
		DefaultHttpClient client = new DefaultHttpClient();
		
		HttpPost registerRequest = new HttpPost(url);
		
		JSONObject user = new JSONObject();
		JSONObject details = new JSONObject();

		/* Setting up the packet to be sent to server */
		try {
			details.put("name", entry.get(DatabaseHelper.NAME_INDEX_U));
			details.put("number", entry.get(DatabaseHelper.NUMBER_INDEX_U));
			details.put("email", entry.get(DatabaseHelper.EMAIL_INDEX_U));

			user.put("user", details);

			Log.d("ServerDEBUG", "User Register JSON = "+ user.toString());

			StringEntity se = new StringEntity(user.toString());

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
			JSONObject tododetailObject = jObject.getJSONObject("user");
			String railsID = tododetailObject.getString("id");
			
			ToDo_Replica.dh.update_user(null, null, null, null, railsID);
			
		} catch (Exception e) {
			Log.e("JSON E", ""+e);
			e.printStackTrace();
			return -1;
		}

		return 0;
	}
	
	/*
	 * Updates user information in the server. Called when a user updates a corresponding one 
	 * locally.
	 */
	public static int update(List<String> entry) {
		String url = ServerConnection.homeurl + ServerConnection.users_link + 
		ToDo_Replica.dh.select_user().get(DatabaseHelper.USER_RAILS_ID_INDEX_U); 
		
		Log.d("ServerDEBUG", "PUT to " + url);
		
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPut putRequest = new HttpPut(url);
		
		JSONObject tododetail= new JSONObject();
		JSONObject details = new JSONObject();

		/* Setting up the packet to be sent to server */
		try {
			details.put("name", "name31");
			details.put("number", "number31");
			details.put("email", "email31");

			tododetail.put("tododetail", details);

			Log.d("ServerDEBUG", "Event JSON = "+ tododetail.toString());

			StringEntity se = new StringEntity(tododetail.toString());

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
	 * Called when user wants to uninstall the application.
	 */
	public static int delete(List<String> entry) {
		String url = ServerConnection.homeurl + ServerConnection.users_link + 
		ToDo_Replica.dh.select_user().get(DatabaseHelper.USER_RAILS_ID_INDEX_U); 
		
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