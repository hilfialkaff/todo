package eecs.berkeley.edu.cs294;

/* Should not have android.sax.element */
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.util.Log;

public class PullConnectionHelper extends Activity {
	/*
	 * Get a list of updates in the invitations sent.
	 */
	public static ArrayList<MySentInvitation> pullSentInvitations() {
		HttpClient httpClient = new DefaultHttpClient();
		String xmlResponse;
		ArrayList<MySentInvitation> sentInvitationList = new ArrayList<MySentInvitation>();
		String url = ServerConnection.homeurl + ServerConnection.users_link + 
		ToDo_Replica.dh.select_user().get(DatabaseHelper.USER_RAILS_ID_INDEX_U) +
		ServerConnection.my_sent_invs_link + "?format=xml";

		try
		{	
			Log.d("ServerDEBUG", "performing get " + url );

			HttpGet method = new HttpGet( new URI(url) );
			HttpResponse response = httpClient.execute(method);
			if ( response != null )
			{
				xmlResponse = ServerConnection.getResponse(response.getEntity());
				sentInvitationList = parseSentInvitationsXML(xmlResponse);
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
		
		return sentInvitationList;
	}
	
	/*
	 * Get a list of updates in the invitations received.
	 */
	public static ArrayList<MyRecvInvitation> pullRecvInvitations() {
		HttpClient httpClient = new DefaultHttpClient();
		String xmlResponse;
		ArrayList<MyRecvInvitation> recvInvitationList = new ArrayList<MyRecvInvitation>();
		String url = ServerConnection.homeurl + ServerConnection.users_link + 
		ToDo_Replica.dh.select_user().get(DatabaseHelper.USER_RAILS_ID_INDEX_U) + 
		ServerConnection.my_recv_invs_link + "?format=xml";

		try
		{	
			Log.d("ServerDEBUG", "performing get " + url );

			HttpGet method = new HttpGet( new URI(url) );
			HttpResponse response = httpClient.execute(method);
			if ( response != null )
			{
				xmlResponse = ServerConnection.getResponse(response.getEntity());
				recvInvitationList = parseRecvInvitationsXML(xmlResponse);
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
		
		return recvInvitationList;
	}

	/*
	 * Get a list of updates in group's information.
	 */
	public static ArrayList<MyGroup> pullGroups() {
		HttpClient httpClient = new DefaultHttpClient();
		String xmlResponse;
		ArrayList<MyGroup> groupsList = new ArrayList<MyGroup>();
		String url = ServerConnection.homeurl + ServerConnection.users_link + 
		ToDo_Replica.dh.select_user().get(DatabaseHelper.USER_RAILS_ID_INDEX_U) + 
		ServerConnection.my_groups_link;

		try
		{	
			Log.d("ServerDEBUG", "performing get " + url );

			HttpGet method = new HttpGet( new URI(url) );
			HttpResponse response = httpClient.execute(method);
			if ( response != null )
			{
				xmlResponse = ServerConnection.getResponse(response.getEntity());
				groupsList = parseGroupsXML(xmlResponse);
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
		
		return groupsList;
	}
	
	/*
	 * Get a list of updates of todos in each group.
	 */
	public static ArrayList<MyTodo> pullTodos(ArrayList<MyGroup> groupList) {
		HttpClient httpClient = new DefaultHttpClient();
		ArrayList<MyTodo> todoList = new ArrayList<MyTodo>();
		
		for(Iterator<MyGroup> it = groupList.iterator(); it.hasNext();) {
			String groupID = it.next().getRailsID();		
			String xmlResponse;
			String url = ServerConnection.homeurl + ServerConnection.groups_link + groupID + 
			ServerConnection.group_todos_link;

			try
			{	
				Log.d("ServerDEBUG", "performing get " + url );

				HttpGet method = new HttpGet( new URI(url) );
				HttpResponse response = httpClient.execute(method);
				if ( response != null )
				{
					xmlResponse = ServerConnection.getResponse(response.getEntity());
					todoList.addAll(parseTodoXML(xmlResponse));
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

		/* For debugging purpose */
		for(Iterator<MyTodo> it = todoList.iterator(); it.hasNext();) {
			MyTodo todo = it.next();
			todo.printMembers();
		}
		
		return todoList;
	}

	/*
	 * Get a list of updates of members in each group.
	 */
	public static ArrayList<MyGroupMember> pullGroupMembers(ArrayList<MyGroup> groupList) {
		HttpClient httpClient = new DefaultHttpClient();
		ArrayList<MyGroupMember> groupMemberList = new ArrayList<MyGroupMember>();
		
		for(Iterator<MyGroup> it = groupList.iterator(); it.hasNext();) {
			String groupID = it.next().getRailsID();		
			String xmlResponse;

			String url = ServerConnection.homeurl + ServerConnection.groups_link + groupID + 
			ServerConnection.group_members_link;

			try
			{	
				Log.d("ServerDEBUG", "performing get " + url );

				HttpGet method = new HttpGet( new URI(url) );
				HttpResponse response = httpClient.execute(method);
				if ( response != null )
				{
					xmlResponse = ServerConnection.getResponse(response.getEntity());
					groupMemberList.addAll(parseGroupMemberXML(xmlResponse));
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
		
		/* For debugging purpose */
		for(Iterator<MyGroupMember> it = groupMemberList.iterator(); it.hasNext();) {
			MyGroupMember groupMember = it.next();
			groupMember.printMembers();
		}
		
		return groupMemberList;
	}

	/*
	 * Parse the XML for the user's sent invitations data structure
	 */
	private static ArrayList<MySentInvitation> parseSentInvitationsXML(String xmlString) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		ArrayList<MySentInvitation> sentInvitationList = new ArrayList<MySentInvitation>();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource();

			is.setCharacterStream(new StringReader(xmlString));
			Document doc = builder.parse(is);
			NodeList nodes = doc.getElementsByTagName("sent-invitation");

			Log.d("ServerDEBUG", "Begin iterating nodes");

			/* Iterating each sent invitation node in the xml */
			for (int i = 0; i < nodes.getLength(); i++) {
				Node item = nodes.item(i);
				NodeList properties = item.getChildNodes();
				sentInvitationList.add(new MySentInvitation());

				/* Iterating each field (ie: group_name, recipient, etc) in each sent 
				 * invitation */
				for (int j=0;j<properties.getLength();j++){

					Node property = properties.item(j);
					String name = property.getNodeName();

					if(property.getFirstChild() == null) {
						continue;
					}
					
					else if (name.equalsIgnoreCase("group")){
						sentInvitationList.get(i).setGroup((property.getFirstChild().getNodeValue()));
					} 
					
					else if (name.equalsIgnoreCase("updated-at")){
						String timestamp = property.getFirstChild().getNodeValue();
						timestamp = timestamp.replaceAll("[:-]", ""); // Hack to eliminate UTC's parser's dumbness
						sentInvitationList.get(i).setTimestamp(timestamp);
					}
					
					else if (name.equalsIgnoreCase("id")){
						sentInvitationList.get(i).setRailsID((property.getFirstChild().getNodeValue()));
					}
					 
					else if (name.equalsIgnoreCase("recipient")){
						sentInvitationList.get(i).setRecipient((property.getFirstChild().getNodeValue()));
					}
					
					else if (name.equalsIgnoreCase("user-id")){
						sentInvitationList.get(i).setUserID((property.getFirstChild().getNodeValue()));
					} 
					
					else if (name.equalsIgnoreCase("description")){
						sentInvitationList.get(i).setDescription((property.getFirstChild().getNodeValue()));
					}
					
					else if (name.equalsIgnoreCase("status")){
						sentInvitationList.get(i).setStatus((property.getFirstChild().getNodeValue()));
					}
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		/* For debugging purpose */
		for(Iterator<MySentInvitation> it = sentInvitationList.iterator(); it.hasNext();) {
			MySentInvitation sentInvitation = it.next();
			sentInvitation.printMembers();
		}

		return sentInvitationList;
	}
	
	/*
	 * Parse the XML for user's received invitations data structure
	 */
	private static ArrayList<MyRecvInvitation> parseRecvInvitationsXML(String xmlString) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		ArrayList<MyRecvInvitation> recvInvitationList = new ArrayList<MyRecvInvitation>();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource();

			is.setCharacterStream(new StringReader(xmlString));
			Document doc = builder.parse(is);
			NodeList nodes = doc.getElementsByTagName("recv-invitation");

			Log.d("ServerDEBUG", "Begin iterating nodes");

			/* Iterating each received invitation node in the xml */
			for (int i = 0; i < nodes.getLength(); i++) {
				Node item = nodes.item(i);
				NodeList properties = item.getChildNodes();
				recvInvitationList.add(new MyRecvInvitation());

				/* Iterating each field (ie: group_name, recipient, etc) in each received 
				 * invitation */
				for (int j=0;j<properties.getLength();j++){

					Node property = properties.item(j);
					String name = property.getNodeName();

					if(property.getFirstChild() == null) {
						continue;
					}
					
					else if (name.equalsIgnoreCase("group")){
						recvInvitationList.get(i).setGroup((property.getFirstChild().getNodeValue()));
					} 
					
					else if (name.equalsIgnoreCase("sender")){
						recvInvitationList.get(i).setSender((property.getFirstChild().getNodeValue()));
					}
					
					else if (name.equalsIgnoreCase("updated-at")){
						String timestamp = property.getFirstChild().getNodeValue();
						timestamp = timestamp.replaceAll("[:-]", ""); // Hack to eliminate UTC's parser's dumbness
						recvInvitationList.get(i).setTimestamp(timestamp);
					}
					
					else if (name.equalsIgnoreCase("id")){
						recvInvitationList.get(i).setRailsID((property.getFirstChild().getNodeValue()));
					}
					
					else if (name.equalsIgnoreCase("user-id")){
						recvInvitationList.get(i).setUserID((property.getFirstChild().getNodeValue()));
					} 
					
					else if (name.equalsIgnoreCase("description")){
						recvInvitationList.get(i).setDescription((property.getFirstChild().getNodeValue()));
					}
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		/* For debugging purpose */
		for(Iterator<MyRecvInvitation> it = recvInvitationList.iterator(); it.hasNext();) {
			MyRecvInvitation recvInvitation = it.next();
			recvInvitation.printMembers();
		}

		return recvInvitationList;
	}
	
	/*
	 * Parse the XML for user's groups data structure
	 */
	private static ArrayList<MyGroup> parseGroupsXML(String xmlString) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		ArrayList<MyGroup> groupList= new ArrayList<MyGroup>();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource();

			is.setCharacterStream(new StringReader(xmlString));
			Document doc = builder.parse(is);
			NodeList nodes = doc.getElementsByTagName("group");

			Log.d("ServerDEBUG", "Begin iterating nodes");

			/* Iterating each group node in the xml */
			for (int i = 0; i < nodes.getLength(); i++) {
				Node item = nodes.item(i);
				NodeList properties = item.getChildNodes();
				groupList.add(new MyGroup());

				/* Iterating each field (ie: name, timestamp, etc) in each group node */
				for (int j=0;j<properties.getLength();j++){

					Node property = properties.item(j);
					String name = property.getNodeName();

					if(property.getFirstChild() == null) {
						continue;
					}
					
					else if (name.equalsIgnoreCase("name")){
						groupList.get(i).setName((property.getFirstChild().getNodeValue()));
					} 
					
					else if (name.equalsIgnoreCase("updated-at")){
						String timestamp = property.getFirstChild().getNodeValue();
						timestamp = timestamp.replaceAll("[:-]", ""); // Hack to eliminate UTC's parser's dumbness
						groupList.get(i).setTimestamp(timestamp);
					}
					
					else if (name.equalsIgnoreCase("id")){
						groupList.get(i).setRailsID((property.getFirstChild().getNodeValue()));
					}
					
					else if (name.equalsIgnoreCase("description")){
						groupList.get(i).setDescription((property.getFirstChild().getNodeValue()));
					}
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		/* For debugging purpose */
		for(Iterator<MyGroup> it = groupList.iterator(); it.hasNext();) {
			MyGroup group = it.next();
			group.printMembers();
		}

		return groupList;
	}
	
	/*
	 * Parse the XML for each group's todo data structure
	 */
	private static ArrayList<MyTodo> parseTodoXML(String xmlString) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		ArrayList<MyTodo> todoList= new ArrayList<MyTodo>();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource();

			is.setCharacterStream(new StringReader(xmlString));
			Document doc = builder.parse(is);
			NodeList nodes = doc.getElementsByTagName("tododetail");

			Log.d("ServerDEBUG", "Begin iterating nodes");

			/* Iterating each tododetail node in the xml */
			for (int i = 0; i < nodes.getLength(); i++) {
				Node item = nodes.item(i);
				NodeList properties = item.getChildNodes();
				todoList.add(new MyTodo());

				/* Iterating each field (ie: name, timestamp, etc) in each todo node */
				for (int j=0;j<properties.getLength();j++){

					Node property = properties.item(j);
					String name = property.getNodeName();

					if(property.getFirstChild() == null) {
						continue;
					}
					
					else if (name.equalsIgnoreCase("title")){
						todoList.get(i).setTodoTitle((property.getFirstChild().getNodeValue()));
					} 
					
					else if (name.equalsIgnoreCase("updated-at")){
						String timestamp = property.getFirstChild().getNodeValue();
						timestamp = timestamp.replaceAll("[:-]", ""); // Hack to eliminate UTC's parser's dumbness
						todoList.get(i).setTodoTimestamp(timestamp);
					}
					
					else if (name.equalsIgnoreCase("priority")){
						todoList.get(i).setTodoPriority((property.getFirstChild().getNodeValue()));
					}
					
					else if (name.equalsIgnoreCase("tag")){
						todoList.get(i).setTodoTag((property.getFirstChild().getNodeValue()));
					}
					
					else if (name.equalsIgnoreCase("group-id")){
						todoList.get(i).setTodoGroupId((property.getFirstChild().getNodeValue()));
					}
					
					else if (name.equalsIgnoreCase("id")){
						todoList.get(i).setTodoRailsID((property.getFirstChild().getNodeValue()));
					}
					
					else if (name.equalsIgnoreCase("deadline")){
						todoList.get(i).setTodoDeadline((property.getFirstChild().getNodeValue()));
					}
					
					else if (name.equalsIgnoreCase("note")){
						todoList.get(i).setTodoNote((property.getFirstChild().getNodeValue()));
					}
					
					else if (name.equalsIgnoreCase("Place")){
						todoList.get(i).setTodoPlace((property.getFirstChild().getNodeValue()));
					}
					
					else if (name.equalsIgnoreCase("status")){
						todoList.get(i).setTodoStatus((property.getFirstChild().getNodeValue()));
					}
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return todoList;
	}

	/*
	 * Parse the XML for each group's members data structure
	 */
	private static ArrayList<MyGroupMember> parseGroupMemberXML(String xmlString) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		ArrayList<MyGroupMember> groupMemberList= new ArrayList<MyGroupMember>();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource();

			is.setCharacterStream(new StringReader(xmlString));
			Document doc = builder.parse(is);
			NodeList nodes = doc.getElementsByTagName("user");

			Log.d("ServerDEBUG", "Begin iterating nodes");

			/* Iterating each group member node in the xml */
			for (int i = 0; i < nodes.getLength(); i++) {
				Node item = nodes.item(i);
				NodeList properties = item.getChildNodes();
				groupMemberList.add(new MyGroupMember());

				/* Iterating each field (ie: name, number, etc) in each group member node */
				for (int j=0;j<properties.getLength();j++){

					Node property = properties.item(j);
					String name = property.getNodeName();

					if(property.getFirstChild() == null) {
						continue;
					}
					
					if (name.equalsIgnoreCase("name")){
						groupMemberList.get(i).setName(property.getFirstChild().getNodeValue());
					} 
					
					else if (name.equalsIgnoreCase("number")){
						groupMemberList.get(i).setNumber((property.getFirstChild().getNodeValue()));
					}
					
					else if (name.equalsIgnoreCase("updated-at")){
						String timestamp = property.getFirstChild().getNodeValue();
						timestamp = timestamp.replaceAll("[:-]", ""); // Hack to eliminate UTC's parser's dumbness
						groupMemberList.get(i).setTimestamp(timestamp);
					}
					
					else if (name.equalsIgnoreCase("email")){
						groupMemberList.get(i).setEmail((property.getFirstChild().getNodeValue()));
					}
					
					else if (name.equalsIgnoreCase("group-id")){
						groupMemberList.get(i).setGroupID((property.getFirstChild().getNodeValue()));
					}
					
					else if (name.equalsIgnoreCase("id")){
						groupMemberList.get(i).setRailsID((property.getFirstChild().getNodeValue()));
					}
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return groupMemberList;
	}
}