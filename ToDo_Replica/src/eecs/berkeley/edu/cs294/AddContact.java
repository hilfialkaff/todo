package eecs.berkeley.edu.cs294;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AddContact extends ListActivity implements AdapterView.OnItemClickListener {
	ListView lv_contacts;
	static ResultAdapter adapter;
	public static ArrayList<Contact> selected = new ArrayList<Contact>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contact);

		getListView().setOnItemClickListener(this);
		adapter = new ResultAdapter(AddContact.this, R.layout.search_contact, AddGroup.candidate);
		setListAdapter(adapter);
	}

	@Override
	public void onBackPressed() {
		Intent setIntent = new Intent(Intent.ACTION_MAIN);
		setIntent.addCategory(Intent.CATEGORY_HOME);
		setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		for(Iterator<Contact> it = selected.iterator(); it.hasNext();) {
			Contact temp = it.next();
			Log.w("debug", temp.getName() + " " + temp.getNumber() + " " + temp.getEmail());
		}
		finish();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(((TextView)arg1.findViewById(R.id.tv_contact_click)).getVisibility() == View.INVISIBLE) {
			((TextView)arg1.findViewById(R.id.tv_contact_click)).setVisibility(View.VISIBLE);
			selected.add(new Contact(((TextView)arg1.findViewById(R.id.tv_name)).getText().toString(), ((TextView)arg1.findViewById(R.id.tv_number)).getText().toString(), ((TextView)arg1.findViewById(R.id.tv_email)).getText().toString()));
		}
		else {
			((TextView)arg1.findViewById(R.id.tv_contact_click)).setVisibility(View.INVISIBLE);
			selected.remove(new Contact(((TextView)arg1.findViewById(R.id.tv_name)).getText().toString(), ((TextView)arg1.findViewById(R.id.tv_number)).getText().toString(), ((TextView)arg1.findViewById(R.id.tv_email)).getText().toString()));
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
			((TextView) v.findViewById(R.id.tv_name)).setText(c.getName());
			((TextView) v.findViewById(R.id.tv_number)).setText(c.getNumber());
			((TextView) v.findViewById(R.id.tv_email)).setText(c.getEmail());			
			return v;
		}
	}
}
