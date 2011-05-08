//contacts: http://www.higherpass.com/Android/Tutorials/Working-With-Android-Contacts/

package eecs.berkeley.edu.cs294;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

public class EditGroup extends Activity {
	/** Called when the activity is first created. */
	ArrayAdapter<String> adapter;

	EditText et_name, et_description;
	Button b_add_members;
	Button b_submit, b_add_contact;

	EditText et_group_name, et_group_description;
	Button b_group_next;
	
	List<String> group;

	public static ArrayList<Contact> candidate = new ArrayList<Contact>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_group);

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			Log.w("debug", "extras null");
			return;
		}
		
		group = ToDo_Replica.dh.select_group("name", extras.getString("group_select"));
		
		et_group_name = (EditText) findViewById(R.id.et_group_name);
		et_group_description = (EditText) findViewById(R.id.et_group_description);
		b_group_next = (Button) findViewById(R.id.b_group_next);
		
		et_group_name.setText(group.get(1));
		et_group_description.setText(group.get(2));

		b_group_next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(et_group_name.getText().length() == 0 || et_group_description.getText().length() == 0) {
					AlertDialog.Builder dialog = new AlertDialog.Builder(EditGroup.this);
					dialog.setTitle("Warning");
					dialog.setMessage("Please enter all the entries");
					dialog.show();
				}
				else {
					Intent intent = new Intent(v.getContext(), EditContact.class);
					intent.putExtra("name", et_group_name.getText().toString());
					intent.putExtra("description", et_group_description.getText().toString());
					intent.putExtra("g_id", group.get(0));
					startActivityForResult(intent, 0);
				}
			}
		});
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		if (resultCode == Activity.RESULT_OK) {
			setResult(RESULT_OK);
			finish();
		}
	}

}