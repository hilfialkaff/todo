package eecs.berkeley.edu.cs294;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class ToDo_Replica extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final boolean customTitle = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.main);

		if (customTitle)
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);

		//final TextView tv_custom_title = (TextView) findViewById(R.id.tv_custom_title);
		//if (tv_custom_title != null)

		//final ImageButton ib_custom_search = (ImageButton) findViewById(R.id.ib_custom_search);
		//if (ib_custom_search != null)

		final ImageButton ib_custom_add = (ImageButton) findViewById(R.id.ib_custom_add);
		if (ib_custom_add != null) {
			ib_custom_add.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(v.getContext(), Add.class);
					startActivityForResult(intent, 3);
				}
			});
		}

		Button b_maps = (Button) findViewById(R.id.b_maps);
		b_maps.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), Maps.class);
				startActivityForResult(intent, 0);
			}
		});
		Button b_todo_lists = (Button) findViewById(R.id.b_todo_lists);
		b_todo_lists.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), ToDo_Lists.class);
				startActivityForResult(intent, 1);
			}
		});
		Button b_groups = (Button) findViewById(R.id.b_groups);
		b_groups.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), Groups.class);
				startActivityForResult(intent, 2);
			}
		});

	}
}