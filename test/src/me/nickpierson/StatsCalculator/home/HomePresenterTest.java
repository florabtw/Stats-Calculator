package me.nickpierson.StatsCalculator.home;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import me.nickpierson.StatsCalculator.utils.Constants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.thecellutioncenter.mvplib.ActionListener;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class HomePresenterTest {

	protected HomeModel model;
	protected HomeView view;
	protected Activity activity;
	protected ArgumentCaptor<ActionListener> listener;

	@Before
	public void setup() {
		model = mock(HomeModel.class);
		view = mock(HomeView.class);
		activity = mock(Activity.class);

		listener = ArgumentCaptor.forClass(ActionListener.class);
	}

	public void setupPresenter() {
		HomePresenter.setup(activity, model, view);
	}

	@Test
	public void whenContactDeveloperMenuItemSelected_ThenEmailIntentIsShown() {
		Uri emailUri = Uri.fromParts("mailto", Constants.DEVELOPER_EMAIL, null);
		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, emailUri);
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, Constants.EMAIL_SUBJECT);
		Intent testIntent = Intent.createChooser(emailIntent, Constants.EMAIL_CHOOSER_TITLE);

		setupPresenter();

		verify(view).addListener(listener.capture(), eq(HomeView.Types.MENU_CONTACT));

		listener.getValue().fire();

		verify(activity).startActivity(testIntent);
	}

	@Test
	public void whenRateThisAppMenuItemIsSelected_ThenUserIsDirectedToAmazonStore() {
		when(activity.getApplicationContext()).thenReturn(mock(Context.class));
		when(activity.getApplicationContext().getPackageName()).thenReturn("StatsCalculator");
		Uri uri = Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=" + activity.getApplicationContext().getPackageName());
		Intent rateAppIntent = new Intent(Intent.ACTION_VIEW, uri);

		setupPresenter();

		verify(view).addListener(listener.capture(), eq(HomeView.Types.MENU_RATE));

		listener.getValue().fire();

		verify(activity).startActivity(rateAppIntent);
	}

	@Test
	public void whenRateThisAppMenuItemIsSelectedWithNoAmazonStore_ThenUserIsShownError() {
		when(activity.getApplicationContext()).thenReturn(mock(Context.class));
		when(activity.getApplicationContext().getPackageName()).thenReturn("StatsCalculator");
		Uri uri = Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=" + activity.getApplicationContext().getPackageName());
		Intent rateAppIntent = new Intent(Intent.ACTION_VIEW, uri);
		doThrow(new ActivityNotFoundException()).when(activity).startActivity(rateAppIntent);

		setupPresenter();

		verify(view).addListener(listener.capture(), eq(HomeView.Types.MENU_RATE));

		listener.getValue().fire();

		verify(view).showToast(Constants.RATE_ERROR);
	}
}