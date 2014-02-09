package me.nickpierson.StatsCalculator.pc;

import java.util.HashMap;

import me.nickpierson.StatsCalculator.utils.DefaultAdapter;
import android.app.Activity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SubscriptSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nickpierson.me.StatsCalculator.R;
import com.thecellutioncenter.mvplib.DataActionHandler;

public abstract class PCView extends DataActionHandler {

	public enum Types {
		DONE_PRESSED, EDITTEXT_CLICKED
	}

	protected LinearLayout view;
	protected ListView lvResults;
	protected EditText etNVal;
	protected EditText etRVal;
	protected EditText etNVals;
	protected ImageButton btnBackspace;
	protected Activity activity;
	private Toast toast;
	private TableLayout tlKeypad;
	protected FrameLayout flFrame;
	private TextView tvNsTitle;
	protected DefaultAdapter resultsAdapter;

	public PCView(Activity activity, DefaultAdapter adapter) {
		this.activity = activity;
		view = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.perm_comb, null);
		tlKeypad = (TableLayout) LayoutInflater.from(activity).inflate(R.layout.keypad, null);
		flFrame = (FrameLayout) view.findViewById(R.id.pc_flFrame);
		tvNsTitle = (TextView) view.findViewById(R.id.pc_ns_title);
		resultsAdapter = adapter;
		btnBackspace = (ImageButton) tlKeypad.findViewById(R.id.keypad_backspace);
		Button btnMultiply = (Button) tlKeypad.findViewById(R.id.keypad_times);
		Button btnNegative = (Button) tlKeypad.findViewById(R.id.keypad_negative);
		Button btnDecimal = (Button) tlKeypad.findViewById(R.id.keypad_decimal);

		subscriptNsTitle();

		etNVal = (EditText) view.findViewById(R.id.pc_etNVal);
		etRVal = (EditText) view.findViewById(R.id.pc_etRVal);
		etNVals = (EditText) view.findViewById(R.id.pc_etNVals);

		setEditTextClickListener(etNVal);
		setEditTextClickListener(etRVal);
		setEditTextClickListener(etNVals);

		btnMultiply.setEnabled(false);
		btnNegative.setEnabled(false);
		btnDecimal.setEnabled(false);
	}

	private void subscriptNsTitle() {
		SpannableStringBuilder nsTitle = new SpannableStringBuilder(tvNsTitle.getText());
		subscriptText(nsTitle, 1, 2);
		subscriptText(nsTitle, 4, 5);
		subscriptText(nsTitle, 7, 8);
		tvNsTitle.setText(nsTitle);
	}

	private void subscriptText(SpannableStringBuilder nsTitle, int start, int end) {
		nsTitle.setSpan(new SubscriptSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		nsTitle.setSpan(new RelativeSizeSpan(.6f), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	}

	private void setEditTextClickListener(EditText editText) {
		editText.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				event(Types.EDITTEXT_CLICKED);
				return false;
			}
		});
	}

	public void showResults(HashMap<String, String> results) {
		resultsAdapter.setResults(results);
		resultsAdapter.notifyDataSetChanged();
		showResults();
	}

	public void showKeypad() {
		flFrame.removeAllViews();
		flFrame.addView(tlKeypad);
	}

	public void showToast(String message) {
		try {
			toast.getView().isShown();
			toast.setText(message);
		} catch (Exception e) {
			toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
		}
		toast.show();
	}

	public void donePress() {
		event(Types.DONE_PRESSED);
	}

	public boolean isKeypadVisible() {
		return tlKeypad.isShown();
	}

	public String getNVal() {
		return etNVal.getText().toString();
	}

	public String getRVal() {
		return etRVal.getText().toString();
	}

	public String getNVals() {
		return etNVals.getText().toString();
	}

	public HashMap<String, String> getResults() {
		return resultsAdapter.getResults();
	}

	public int getScrollPosition() {
		return lvResults.getFirstVisiblePosition();
	}

	public void setScrollPosition(int pos) {
		lvResults.setSelectionFromTop(pos, 0);
	}

	public View getView() {
		return view;
	}

	public abstract void showResults();
}
