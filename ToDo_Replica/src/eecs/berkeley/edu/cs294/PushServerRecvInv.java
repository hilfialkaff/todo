package eecs.berkeley.edu.cs294;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.util.Log;

public class PushServerRecvInv extends Activity {

	/*
	 * Called when user accepts an invitation. Propagate updates to the server.
	 */
	public static int accept(List<String> entry) {
		/* TODO: user id database? */
		
		HttpClient httpClient = new DefaultHttpClient();
		String xmlResponse;
		String url = ServerConnection.homeurl + ServerConnection.users_link + 
		ToDo_Replica.dh.select_user().get(DatabaseHelper.USER_RAILS_ID_INDEX_U) + 
		ServerConnection.my_recv_invs_link + entry.get(DatabaseHelper.RECV_RAILS_ID_INDEX_R) +
		ServerConnection.accept_link +  
		"?inv_id=" + entry.get(DatabaseHelper.RECV_ID_INDEX_R) + "&user_name=" + 
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
	
	/*
	 * Called when user rejects an invitation. Propagate updates to the server.
	 */
	public static int reject(List<String> entry) {
		/* TODO: user id database? */
		
		HttpClient httpClient = new DefaultHttpClient();
		String xmlResponse;
		String url = ServerConnection.homeurl + ServerConnection.users_link + 
		ToDo_Replica.dh.select_user().get(DatabaseHelper.USER_RAILS_ID_INDEX_U) + 
		ServerConnection.my_recv_invs_link + entry.get(DatabaseHelper.RECV_RAILS_ID_INDEX_R) +
		ServerConnection.reject_link + 
		"?inv_id=" + entry.get(DatabaseHelper.RECV_ID_INDEX_R) + "&user_name=" + 
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