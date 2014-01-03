package me.nickpierson.StatsCalculator.utils;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DefaultAdapter extends ArrayAdapter<String> {

	private int resource;
	private HashMap<String, String> results;
	private int titleId;
	private int resultId;

	public DefaultAdapter(Context context, int resource, int titleId, int resultId) {
		super(context, resource);
		this.resource = resource;
		this.titleId = titleId;
		this.resultId = resultId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder view;

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(resource, null);

			view = new ViewHolder();
			view.tvTitle = (TextView) convertView.findViewById(titleId);
			view.tvResult = (TextView) convertView.findViewById(resultId);

			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}

		String title = getItem(position);
		view.tvTitle.setText(title);
		view.tvResult.setText(results.get(title));

		return convertView;
	}

	static class ViewHolder {
		TextView tvTitle;
		TextView tvResult;
	}

	public HashMap<String, String> getResults() {
		return results;
	}

	public void setResults(HashMap<String, String> results) {
		this.results = results;
	}

	public void addMultiple(String... items) {
		for (String item : items) {
			add(item);
		}
	}

	public void addMultiple(ArrayList<String> items) {
		for (String item : items) {
			add(item);
		}
	}
}
