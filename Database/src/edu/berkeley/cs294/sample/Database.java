//credit: http://www.screaming-penguin.com/node/7742

package edu.berkeley.cs294.sample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class Database extends Activity {
	/** Called when the activity is first created. */

	private TextView output;
	private DatabaseHelper dh;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		this.output = (TextView) this.findViewById(R.id.out_text);
		
		this.dh = new DatabaseHelper(this);
		
		this.dh.deleteAll_group();
		this.dh.insert_group(1, "Number", "1,2,3");
		this.dh.insert_group(2, "World", "Faith, Hope, Love");
		this.dh.insert_group(3, "OS", "Mac, Unix, Windows");     
		
		List<String> names = this.dh.selectAll_group_name();
		StringBuilder sb = new StringBuilder();
		sb.append("Group Name(s) in Database:\n");
		for (String name : names) {
			sb.append(name + "\n");
		}
		Log.d("debug", "names size - " + names.size());
		this.output.setText(sb.toString());
	}
}