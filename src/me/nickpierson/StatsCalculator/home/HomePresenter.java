package me.nickpierson.StatsCalculator.home;

import me.nickpierson.StatsCalculator.utils.Constants;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.thecellutioncenter.mvplib.ActionListener;

public class HomePresenter {

	protected static void setup(final Activity activity, HomeModel model, final HomeView view) {
		view.addListener(new ActionListener() {

			@Override
			public void fire() {
				Uri emailUri = Uri.fromParts("mailto", Constants.DEVELOPER_EMAIL, null);
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, emailUri);
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, Constants.EMAIL_SUBJECT);
				activity.startActivity(Intent.createChooser(emailIntent, Constants.EMAIL_CHOOSER_TITLE));
			}
		}, HomeView.Types.MENU_CONTACT);

		view.addListener(new ActionListener() {

			@Override
			public void fire() {
				Uri uri = Uri.parse("market://details?id=" + activity.getApplicationContext().getPackageName());
				Intent rateAppIntent = new Intent(Intent.ACTION_VIEW, uri);

				try {
					activity.startActivity(rateAppIntent);
				} catch (Exception e) {
					view.showToast(Constants.RATE_ERROR);
				}
			}
		}, HomeView.Types.MENU_RATE);
	}
}
