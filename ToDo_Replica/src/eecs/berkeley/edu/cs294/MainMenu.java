package eecs.berkeley.edu.cs294;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MainMenu extends PreferenceActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesName("global");
        addPreferencesFromResource(R.layout.global_preferences);
	}
}