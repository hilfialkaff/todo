package eecs.berkeley.edu.cs294;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

public class InvitationConfirmation extends Activity {
	/** Called when the activity is first created. */
	TextView tv_sender_confirmation2;
	TextView tv_group_confirmation2;
	TextView tv_desc_confirmation2;
	TabHost th_invitation_confirmation;
	Button b_accept, b_reject;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invitation_confirmation);
		
		th_invitation_confirmation = (TabHost) findViewById(R.id.th_invitation_confirmation);
		th_invitation_confirmation.setup();

		ToDo_Replica.dh = new DatabaseHelper(this);
		
		tv_sender_confirmation2 = (TextView) findViewById(R.id.tv_sender_confirmation2);
		tv_group_confirmation2 = (TextView) findViewById(R.id.tv_group_confirmation2);
		tv_desc_confirmation2 = (TextView) findViewById(R.id.tv_desc_confirmation2);
		
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			Log.w("debug", "extras null");
			return;
		}
		
		String sender = extras.getString("sender_select");
		List<String> row = ToDo_Replica.dh.select_recv_invitation("sender", sender);
		
		tv_sender_confirmation2.setText(row.get(0));
		tv_group_confirmation2.setText(row.get(1));
		tv_desc_confirmation2.setText(row.get(2));
		
		b_accept = (Button) findViewById(R.id.b_accept_invitation);
		b_reject = (Button) findViewById(R.id.b_reject_invitation);
	}
}