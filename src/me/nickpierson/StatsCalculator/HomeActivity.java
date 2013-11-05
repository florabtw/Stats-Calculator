package me.nickpierson.StatsCalculator;

import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.nickpierson.me.StatsCalculator.R;

public class HomeActivity extends ActionBarActivity {

	protected HomeModel model;
	protected HomeView view;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.home_contact_developer) {
			view.menuContact();
			return true;
		} else if (itemId == R.id.home_rate_app) {
			view.menuRate();
			return true;
		} else {
			return false;
		}
	}
}
