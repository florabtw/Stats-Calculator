package me.nickpierson.StatsCalculator.home;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nickpierson.me.StatsCalculator.R;
import com.thecellutioncenter.mvplib.ActionHandler;

public class HomeView extends ActionHandler {

	public enum Types {
		DESCRIPTIVE_BUTTON, PERM_COMB_BUTTON, MENU_CONTACT, MENU_RATE;
	}

	private LinearLayout view;
	private Activity activity;
	private Button btnPermComb;
	private Button btnDescriptive;

	public HomeView(Activity activity) {
		this.activity = activity;

		view = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.home, null);
		btnDescriptive = (Button) view.findViewById(R.id.home_btnDescriptive);
		btnPermComb = (Button) view.findViewById(R.id.home_btnPermComb);

		btnDescriptive.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				event(Types.DESCRIPTIVE_BUTTON);
			}
		});

		btnPermComb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				event(Types.PERM_COMB_BUTTON);
			}
		});
	}

	public LinearLayout getView() {
		return view;
	}

	public void showToast(String message) {
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
	}

	public void menuContact() {
		event(Types.MENU_CONTACT);
	}

	public void menuRate() {
		event(Types.MENU_RATE);
	}
}
