package eecs.berkeley.edu.cs294;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EditContact extends ListActivity implements AdapterView.OnItemClickListener {
	static ResultAdapter adapter;
	public static HashMap<Integer, Contact> selected = new HashMap<Integer, Contact>();
	List<Integer> id = new ArrayList<Integer>();
	Button b_member_next;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contact);
		
		setTitle("Edit Member");
		
		getListView().setOnItemClickListener(this);
		adapter = new ResultAdapter(EditContact.this, R.layout.search_contact, AddGroup.candidate);
		setListAdapter(adapter);
		
		
		b_member_next = (Button) findViewById(R.id.b_member_next);
		b_member_next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {	
				Time time = new Time();
				String timestamp = Long.toString(time.normalize(false));
				String members = "";
				for(int i = 0; i < id.size(); i++) {
					members += selected.get(id.get(i)).getName() + " ";
				}
				System.out.println("members: " + members);
				Bundle temp = getIntent().getExtras();

				ToDo_Replica.dh.update_group(Integer.parseInt(temp.getString("g_id")), 
						temp.getString("name"), temp.getString("description"), members, 
						timestamp, null);
				
				/* Push changes to the remote if applicable */
				List<String> newEntry = ToDo_Replica.dh.select_group("name", 
						temp.getString("name"));
				ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				NetworkInfo netInfo = connManager.getActiveNetworkInfo();
				
				if(netInfo == null) {
					Log.d("DEBUG", "--------------- No internet connection --------- ");
				}

				if (netInfo.isConnected()) {
					ServerConnection.pushRemote(newEntry, 
							ServerConnection.GROUP_SERVER_UPDATE,
							ServerConnection.UPDATE_REQUEST);
				}

				setResult(RESULT_OK);
				finish();
			}
		});
		
	}

	@Override
	public void onBackPressed() {
		Intent setIntent = new Intent(Intent.ACTION_MAIN);
		setIntent.addCategory(Intent.CATEGORY_HOME);
		setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		finish();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		ImageView temp = ((ImageView) arg1.findViewById(R.id.iv_add_member));
		if(temp.getTag().toString().equalsIgnoreCase("normal") ||temp.getTag().toString().equalsIgnoreCase("minus")) {
			temp.setTag("add");
			selected.put(Integer.parseInt(((TextView)arg1.findViewById(R.id.tv_id)).getText().toString()),
					new Contact(((TextView)arg1.findViewById(R.id.tv_id)).getText().toString(), 
					((TextView)arg1.findViewById(R.id.tv_name)).getText().toString(), 
					((TextView)arg1.findViewById(R.id.tv_number)).getText().toString(),
					((TextView)arg1.findViewById(R.id.tv_email)).getText().toString()));
			temp.setBackgroundResource(R.drawable.member_add);
			id.add(Integer.parseInt(((TextView)arg1.findViewById(R.id.tv_id)).getText().toString()));
		}
		else if(temp.getTag().toString().equalsIgnoreCase("add")) {
			temp.setTag("minus");
			selected.remove(Integer.parseInt(((TextView)arg1.findViewById(R.id.tv_id)).getText().toString()));
			id.remove(Integer.parseInt(((TextView)arg1.findViewById(R.id.tv_id)).getText().toString()));
			temp.setBackgroundResource(R.drawable.member_minus);
		}
	}     

	class ResultAdapter extends ArrayAdapter<Contact> {
		private ArrayList<Contact> contact;
		private LayoutInflater li;

		public ResultAdapter(Context context, int textViewResourceId, ArrayList<Contact> contact) {
			super(context, textViewResourceId, contact);
			li = ((ListActivity) context).getLayoutInflater();
			this.contact = contact;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null)
				v = li.inflate(R.layout.search_contact, null);
			Contact c = contact.get(position);
			((TextView) v.findViewById(R.id.tv_id)).setText(c.getId());
			((TextView) v.findViewById(R.id.tv_name)).setText(c.getName());
			((TextView) v.findViewById(R.id.tv_number)).setText(c.getNumber());
			((TextView) v.findViewById(R.id.tv_email)).setText(c.getEmail());	
			((ImageView) v.findViewById(R.id.iv_add_member)).setTag("normal");
			return v;
		}
	}
}