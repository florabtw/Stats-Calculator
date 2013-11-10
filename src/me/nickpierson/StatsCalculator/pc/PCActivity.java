package me.nickpierson.StatsCalculator.pc;

import java.util.HashMap;

import me.nickpierson.StatsCalculator.utils.KeypadActivity;
import me.nickpierson.StatsCalculator.utils.MyConstants;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public abstract class PCActivity extends ActionBarActivity implements KeypadActivity {

	protected PCModel model;
	protected PCView view;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(MyConstants.RESULTS_KEY, view.getResults());
		outState.putBoolean(MyConstants.KEYPAD_KEY, view.isKeypadVisible());
		super.onSaveInstanceState(outState);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			view.showResults((HashMap<String, String>) savedInstanceState.getSerializable(MyConstants.RESULTS_KEY));

			if (savedInstanceState.getBoolean(MyConstants.KEYPAD_KEY)) {
				view.showKeypad();
			}
		}
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public void donePress(View button) {
		view.donePress();
	}

	@Override
	public void onBackPressed() {
		if (view.isKeypadVisible()) {
			view.showResults();
		} else {
			super.onBackPressed();
		}
	}
}
