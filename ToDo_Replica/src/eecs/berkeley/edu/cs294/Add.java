package eecs.berkeley.edu.cs294;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Add extends Activity {
	/** Called when the activity is first created. */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		
		EditText et_title = (EditText) findViewById(R.id.et_title);
		EditText et_place = (EditText) findViewById(R.id.et_place);
		EditText et_note = (EditText) findViewById(R.id.et_note);
		AutoCompleteTextView actv_tag = (AutoCompleteTextView) findViewById(R.id.actv_tag);
		Spinner s_group = (Spinner) findViewById(R.id.s_group);
		Spinner s_status = (Spinner) findViewById(R.id.s_status);
		Button b_submit = (Button) findViewById(R.id.b_submit);
	}
}