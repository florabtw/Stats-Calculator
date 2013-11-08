package me.nickpierson.StatsCalculator.basic;

import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nickpierson.me.StatsCalculator.R;

public class BasicAdapter extends ArrayAdapter<String> {

	private int resource;
	private HashMap<String, String> results;

	public BasicAdapter(Context context, int resource) {
		super(context, resource);
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder view;

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(resource, null);

			view = new ViewHolder();
			view.tvTitle = (TextView) convertView.findViewById(R.id.basic_tvResultTitle);
			view.tvAnswer = (TextView) convertView.findViewById(R.id.basic_tvResultAnswer);

			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}

		String title = getItem(position);
		view.tvTitle.setText(title);
		view.tvAnswer.setText(results.get(title));

		return convertView;
	}

	static class ViewHolder {
		TextView tvTitle;
		TextView tvAnswer;
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
