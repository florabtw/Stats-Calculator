package me.nickpierson.StatsCalculator.pc;

import java.util.HashMap;

import me.nickpierson.StatsCalculator.utils.MyConstants;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SubscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nickpierson.me.StatsCalculator.R;

public class PCAdapter extends ArrayAdapter<String> {

	private int resource;
	private HashMap<String, String> results = new HashMap<String, String>();

	public PCAdapter(Context context, int resource) {
		super(context, resource);
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder view;

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(resource, null);

			view = new ViewHolder();
			view.tvTitle = (TextView) convertView.findViewById(R.id.pc_tvResultsTitle);
			view.tvResult = (TextView) convertView.findViewById(R.id.pc_tvResultsResult);

			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}

		String title = getItem(position);
		String result = results.get(title);

		if (title.equals(MyConstants.PC_TITLES[2])) {
			view.tvTitle.setText(subscriptChars(title, 0, 2));
		} else if (title.equals(MyConstants.PC_TITLES[3])) {
			view.tvTitle.setText(subscriptChars(title, 0, 2));
		} else if (title.equals(MyConstants.PC_TITLES[4])) {
			view.tvTitle.setText(subscriptChars(title, 6, 9, 12));
		} else {
			view.tvTitle.setText(title);
		}

		view.tvResult.setText(result);

		return convertView;
	}

	static class ViewHolder {
		TextView tvTitle;
		TextView tvResult;
	}

	private SpannableStringBuilder subscriptChars(String title, int... pos) {
		SpannableStringBuilder string = new SpannableStringBuilder(title);
		for (int p : pos) {
			subscriptText(string, p, p + 1);
		}
		return string;
	}

	private void subscriptText(SpannableStringBuilder nsTitle, int start, int end) {
		nsTitle.setSpan(new SubscriptSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		nsTitle.setSpan(new RelativeSizeSpan(.6f), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	}

	@Override
	public void addAll(String... items) {
		for (String item : items) {
			add(item);
		}
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	public HashMap<String, String> getResults() {
		return results;
	}

	public void setResults(HashMap<String, String> results) {
		this.results = results;
	}
}
