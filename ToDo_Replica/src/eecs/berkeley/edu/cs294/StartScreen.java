package eecs.berkeley.edu.cs294;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StartScreen extends Activity {
	EditText et_start_name;
	EditText et_start_number;
	EditText et_start_email;
	EditText et_start_password;
	Button b_start_sign_up;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_screen);
		
		et_start_name = (EditText) findViewById(R.id.et_start_name);
		et_start_number = (EditText) findViewById(R.id.et_start_number);
		et_start_email = (EditText) findViewById(R.id.et_start_email);
		et_start_password = (EditText) findViewById(R.id.et_start_password);
		b_start_sign_up = (Button) findViewById(R.id.b_start_sign_up);

		TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE); 
		et_start_number.setText(mTelephonyMgr.getLine1Number());
		
		Account[] accounts = AccountManager.get(this).getAccounts();
		if(accounts.length > 0)
			et_start_email.setText(accounts[0].name);


		b_start_sign_up.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.err.println(et_start_name.getText().length() + " " + et_start_number.getText().length() + " " + et_start_email.getText().length() + " " + et_start_password.getText().length());
				if(et_start_name.getText().length() == 0 || et_start_number.getText().length() == 0 || et_start_email.getText().length() == 0 || et_start_password.getText().length() == 0) {
					AlertDialog.Builder dialog = new AlertDialog.Builder(StartScreen.this);
					dialog.setTitle("Warning");
					dialog.setMessage("Please enter all the entries");
					dialog.show();
				}
				else{
					ToDo_Replica.dh.insert_user(et_start_name.getText().toString(), et_start_number.getText().toString(), et_start_email.getText().toString(), et_start_password.getText().toString(), "");
					finish();
				}
			}
	});
}
}