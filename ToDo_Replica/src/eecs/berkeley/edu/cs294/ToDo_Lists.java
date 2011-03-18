package eecs.berkeley.edu.cs294;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class ToDo_Lists extends Activity {
	/** Called when the activity is first created. */
	TableLayout tl_todo_lists;
	private DatabaseHelper dh;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		tl_todo_lists = (TableLayout) findViewById(R.id.tl_todo_lists);
		this.dh = new DatabaseHelper(this);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		
		List<String> titles = this.dh.selectAll_to_do_title();
		
		for (String title : titles) {
			TableRow row = new TableRow(this);		
			row.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			
			LinearLayout ll_title = new LinearLayout(this);
			ll_title.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			
			TextView tv_title = new TextView(this);
			tv_title.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			tv_title.setText(title);
			
			View ruler = new View(this);
			ruler.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 1));
			
			ll_title.addView(tv_title);
			row.addView(ll_title);

			tl_todo_lists.addView(row);
			tl_todo_lists.addView(ruler);
			Log.w("DEBUG", "ONCEEEE");
		}
		
	}
}