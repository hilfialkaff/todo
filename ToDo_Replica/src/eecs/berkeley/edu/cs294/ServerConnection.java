package eecs.berkeley.edu.cs294;

/* Should not have android.sax.element */
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ServerConnection extends Activity {

	/*
	 * Check if phone is connected to the internet
	 */
	public boolean isConnected() {
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connManager.getActiveNetworkInfo();

		if(netInfo == null) {
			Log.d("ServerDEBUG", "--------------- No internet connection --------- ");
			return false;
		}

		if (netInfo.isConnected()) {
			Log.d("ServerDEBUG", "------------- Connected to internet -------------");
			return true;
		}

		return false;
	}

	/*
	 * Pull server changes to local and synchronize it with the local database 
	 */
	public static void pullRemote() {
		HttpClient httpClient = new DefaultHttpClient();
		String xmlResponse;

		try
		{
			// For localhost use ip 10.0.2.2
			String url = "http://10.0.2.2:3000/posts?format=xml";
			Log.d( "ServerDEBUG", "performing get " + url );

			HttpGet method = new HttpGet( new URI(url) );
			HttpResponse response = httpClient.execute(method);
			if ( response != null )
			{
				xmlResponse = getResponse(response.getEntity());
				Log.d( "ServerDEBUG", "received " + xmlResponse);
				todoList = parseXMLString(xmlResponse);
			}
			else
			{
				Log.d( "ServerDEBUG", "got a null response" );
			}
		} catch (IOException e) {
			Log.e( "Error", "IOException " + e.getMessage() );
		} catch (URISyntaxException e) {
			Log.e( "Error", "URISyntaxException " + e.getMessage() );
		}
	}

	/*
	 * Push local changes to the server
	 */
	public static void pushRemote(List<String> oldEntry, List<String> newEntry) {

		if(oldEntry == null) {
			Log.d("ServerDEBUG", "old: null");
		}
		else {
			Log.d("ServerDEBUG", "old: " + oldEntry.toString());
		}	

		if(newEntry == null) {
			Log.d("ServerDEBUG", "new: null");
		}
		else {
			Log.d("ServerDEBUG", "new: " + newEntry.toString());
		}

		// TODO: push to remote database
	}

	/*
	 * Get an xml response from the server
	 */
	private static String getResponse( HttpEntity entity )
	{
		String response = "";

		try
		{
			int length = ( int ) entity.getContentLength();
			StringBuffer sb = new StringBuffer( length );
			InputStreamReader isr = new InputStreamReader( entity.getContent(), "UTF-8" );
			char buff[] = new char[length];
			int cnt;
			while ( ( cnt = isr.read( buff, 0, length - 1 ) ) > 0 )
			{
				sb.append( buff, 0, cnt );
			}

			response = sb.toString();
			isr.close();
		} catch ( IOException ioe ) {
			ioe.printStackTrace();
		}

		return response;
	}

	/*
	 * Parse the XML from the server into a list of TODOs
	 * 
	 * TODO: Shouldn't be any hardcoding; Need to discuss w/ the actual server implementation
	 */
	private static ArrayList<MyTodo> parseXMLString(String xmlString) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource();

			is.setCharacterStream(new StringReader(xmlString));
			Document doc = builder.parse(is);
			NodeList nodes = doc.getElementsByTagName("post");
			todoList = new ArrayList<MyTodo>();
			
			Log.d("ServerDEBUG", "Begin iterating nodes");

			/* Iterating each todo node in the xml */
			for (int i = 0; i < nodes.getLength(); i++) {
				Log.d("ServerDEBUG", "iteration " + i);

				Node item = nodes.item(i);
				NodeList properties = item.getChildNodes();
				todoList.add(new MyTodo());

				/* Iterating each field (ie: name, content, etc) in a todo */
				for (int j=0;j<properties.getLength();j++){

					Node property = properties.item(j);
					String name = property.getNodeName();

					if (name.equalsIgnoreCase("name")){
						todoList.get(i).setTodoName((property.getFirstChild().getNodeValue()));
					} else if (name.equalsIgnoreCase("title")){
						todoList.get(i).setTodoTitle((property.getFirstChild().getNodeValue()));
					} else if (name.equalsIgnoreCase("content")){
						todoList.get(i).setTodoContent((property.getFirstChild().getNodeValue()));
					}
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		/* For debugging purpose */
		for(Iterator<MyTodo> it = todoList.iterator(); it.hasNext();) {
			MyTodo todo = it.next();
			Log.d("ServerDEBUG", "name: " + todo.getTodoName() + 
					" title: " + todo.getTodoTitle() + 
					" content: " + todo.getTodoContent());
		}
		
		return null;
	}

	private static ArrayList<MyTodo> todoList;
}
