//contacts: http://www.higherpass.com/Android/Tutorials/Working-With-Android-Contacts/

package eecs.berkeley.edu.cs294;

import java.util.ArrayList;

import junit.framework.Assert;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

public class AddGroup extends Activity {
	/** Called when the activity is first created. */
	private String name, members = "";
	ArrayAdapter<String> adapter;

	EditText et_name;
	Button b_add_members;
	Button b_submit, b_add_contact;

	public static ArrayList<Contact> candidate = new ArrayList<Contact>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_group);
		ToDo_Replica.dh = new DatabaseHelper(this);

		et_name = (EditText) findViewById(R.id.et_name);
		
		b_submit = (Button) findViewById(R.id.b_submitgroup);
		b_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				name = et_name.getText().toString();	
				Time time = new Time();
				String timestamp = Long.toString(time.normalize(false));
				
				ToDo_Replica.dh.insert_group(name, "", members, timestamp, Integer.toString(0));
				setResult(RESULT_OK);
				finish();
			}
		});

		b_add_contact = (Button) findViewById(R.id.b_add_contact);
		b_add_contact.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), AddContact.class);
				startActivityForResult(intent, 0);
			}
		});

		ContentResolver cr = getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String id, name, number = "", email = "";
				id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
				name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", new String[]{id}, null);
					Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null); 
					while (pCur.moveToNext()) {
						number = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					} 
					while (emailCur.moveToNext()) { 
						email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
					}
					pCur.close();
					emailCur.close();
					candidate.add(new Contact(id, name, number, email));
				}
			}
		}
	}
}