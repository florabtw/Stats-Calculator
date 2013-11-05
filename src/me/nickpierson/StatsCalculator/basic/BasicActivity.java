package me.nickpierson.StatsCalculator.basic;

import me.nickpierson.StatsCalculator.utils.KeypadActivity;
import me.nickpierson.StatsCalculator.utils.MyConstants;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.nickpierson.me.StatsCalculator.R;

public abstract class BasicActivity extends ActionBarActivity implements KeypadActivity {

	protected BasicModel model;
	protected BasicView view;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putDoubleArray(MyConstants.RESULTS_KEY, model.getResults());
		outState.putBoolean(MyConstants.KEYPAD_KEY, view.isKeyPadVisible());
		outState.putInt(MyConstants.SCROLL_POSITION_KEY, view.getScrollPosition());
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			double[] results = savedInstanceState.getDoubleArray(MyConstants.RESULTS_KEY);
			model.setResults(results);
			view.showResults(results);

			view.setScrollPosition(savedInstanceState.getInt(MyConstants.SCROLL_POSITION_KEY));

			if (savedInstanceState.getBoolean(MyConstants.KEYPAD_KEY)) {
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
		if (itemId == R.id.settings_save_list) {
			view.menuSaveList();
			return true;
		} else if (itemId == R.id.settings_load_list) {
			view.menuLoadList();
			return true;
		} else if (itemId == R.id.settings_reference) {
			view.menuReference();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void keypadPress(View button) {
		view.keypadPress((Button) button);
	}

	@Override
	public void backSpace(View button) {
		view.backspace();
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
