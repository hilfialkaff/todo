package eecs.berkeley.edu.cs294;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.RawContacts;
import android.database.Cursor;

public class AddGroup extends Activity {
	/** Called when the activity is first created. */
	private String name, members;
	ArrayAdapter<String> adapter;
	
	EditText et_name, et_members;
	//AutoCompleteTextView actv_tag;
	Button b_submit;
	
	private DatabaseHelper dh;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_group);
		this.dh = new DatabaseHelper(this);
		
		et_name = (EditText) findViewById(R.id.et_name);
		et_members = (EditText) findViewById(R.id.et_members); //AutoComplete instead?
		
		//actv_tag = (AutoCompleteTextView) findViewById(R.id.actv_tag);
		
		b_submit = (Button) findViewById(R.id.b_submitgroup);
		
		b_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				name = et_name.getText().toString();
				members = et_members.getText().toString();
				dh.insert_group(name, members);
				setResult(RESULT_OK);
				finish();
			}
		});
		
		String[] projection = new String[] {Contacts.DISPLAY_NAME};
	}
}