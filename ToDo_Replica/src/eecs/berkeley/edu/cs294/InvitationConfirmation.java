package eecs.berkeley.edu.cs294;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * Accessed by clicking on each pending incoming invites
 * @author FiL
 *
 */
public class InvitationConfirmation extends Activity {
	/** Called when the activity is first created. */
	TextView tv_sender_confirmation2;
	TextView tv_group_confirmation2;
	TextView tv_desc_confirmation2;
	TabHost th_invitation_confirmation;
	Button b_accept, b_reject;

	List<String> row;

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
		row = ToDo_Replica.dh.select_recv_invitation("sender", sender);

		tv_sender_confirmation2.setText(row.get(1));
		tv_group_confirmation2.setText(row.get(2));
		tv_desc_confirmation2.setText(row.get(3));

		b_accept = (Button) findViewById(R.id.b_accept_invitation);
		b_reject = (Button) findViewById(R.id.b_reject_invitation);

		b_accept.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Time time = new Time();
				String timestamp = Long.toString(time.normalize(false));
				ToDo_Replica.dh.update_recv_invitation(Integer.parseInt(row.get(0)), row.get(1), row.get(2), timestamp, row.get(4));

				/* Push changes to remote if applicable */
				List<String> newEntry = ToDo_Replica.dh.select_recv_invitation("groupz", row.get(2));				

				ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				NetworkInfo netInfo = connManager.getActiveNetworkInfo();

				if(netInfo == null) {
					Log.d("DEBUG", "--------------- No internet connection --------- ");
				}

				if (netInfo.isConnected()) {
					ServerConnection.pushRemote(newEntry, ServerConnection.RECV_INV_SERVER_UPDATE,
							ServerConnection.ACCEPT_REQUEST);
				}


				setResult(RESULT_OK);
				finish();
			}
		});
		
		b_reject.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Time time = new Time();
				String timestamp = Long.toString(time.normalize(false));
				ToDo_Replica.dh.update_recv_invitation(Integer.parseInt(row.get(0)), row.get(1), row.get(2), timestamp, row.get(4));

				/* Push changes to remote if applicable */
				List<String> newEntry = ToDo_Replica.dh.select_recv_invitation("groupz", row.get(2));				

				ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				NetworkInfo netInfo = connManager.getActiveNetworkInfo();

				if(netInfo == null) {
					Log.d("DEBUG", "--------------- No internet connection --------- ");
				}

				if (netInfo.isConnected()) {
					ServerConnection.pushRemote(newEntry, ServerConnection.RECV_INV_SERVER_UPDATE,
							ServerConnection.REJECT_REQUEST);
				}


				setResult(RESULT_OK);
				finish();
			}
		});
	}
}