package me.nickpierson.StatsCalculator.basic;

import java.util.HashMap;

import me.nickpierson.StatsCalculator.utils.KeypadActivity;
import me.nickpierson.StatsCalculator.utils.Constants;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nickpierson.me.StatsCalculator.R;

public abstract class BasicActivity extends ActionBarActivity implements KeypadActivity {

	protected BasicModel model;
	protected BasicView view;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(Constants.RESULTS_KEY, view.getResults());
		outState.putBoolean(Constants.KEYPAD_KEY, view.isKeyPadVisible());
		outState.putInt(Constants.SCROLL_POSITION_KEY, view.getScrollPosition());
		super.onSaveInstanceState(outState);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			HashMap<String, String> results = (HashMap<String, String>) savedInstanceState.getSerializable(Constants.RESULTS_KEY);
			view.showResults(results);

			view.setScrollPosition(savedInstanceState.getInt(Constants.SCROLL_POSITION_KEY));

			if (savedInstanceState.getBoolean(Constants.KEYPAD_KEY)) {
				view.showKeypad();
			}
		}
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.basic, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.menu_save_list) {
			view.menuSaveList();
			return true;
		} else if (itemId == R.id.menu_load_list) {
			view.menuLoadList();
			return true;
		} else if (itemId == R.id.menu_reference) {
			view.menuReference();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void donePress(View button) {
		view.donePress();
	}

	@Override
	public void onBackPressed() {
		if (view.isKeyPadVisible()) {
			view.showResults();
		} else {
			super.onBackPressed();
		}
	}
}
