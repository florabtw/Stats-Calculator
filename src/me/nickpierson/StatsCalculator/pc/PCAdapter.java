package me.nickpierson.StatsCalculator.pc;

import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nickpierson.me.StatsCalculator.R;

public class PCAdapter extends ArrayAdapter<String> {

	private int resource;
	private HashMap<String, String> results;

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

		view.tvTitle.setText(title);

		view.tvResult.setText(result);

		return convertView;
	}

	static class ViewHolder {
		TextView tvTitle;
		TextView tvResult;
	}

	public void addMultiple(String... items) {
		for (String item : items) {
			add(item);
		}
	}

	public HashMap<String, String> getResults() {
		return results;
	}

	public void setResults(HashMap<String, String> results) {
		this.results = results;
	}
}
