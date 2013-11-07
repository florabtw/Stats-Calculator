package me.nickpierson.StatsCalculator.pc;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.nickpierson.StatsCalculator.utils.MyConstants;
import android.util.SparseArray;

import com.thecellutioncenter.mvplib.DataActionHandler;

public class PCModel extends DataActionHandler {

	public enum Types {
		VALID_N, VALID_R, VALID_NS, DONE_VALIDATING, INPUT_OVER_MAX_VALUE;
	}

	public enum Keys {
		N_VALUE, R_VALUE, N_VALUES;
	}

	HashMap<String, String> results = new HashMap<String, String>();

	SparseArray<BigInteger> factCache = new SparseArray<BigInteger>();

	public void validateInput(String nVal, String rVal, String nVals) {
		HashMap<Enum<?>, Object> returnValues = new HashMap<Enum<?>, Object>();

		if (isValid(nVal)) {
			int n = convert(nVal);
			returnValues.put(Keys.N_VALUE, n);
			dataEvent(Types.VALID_N, returnValues);

			if (isValidList(nVals, n)) {
				addNList(nVals, returnValues);
				dataEvent(Types.VALID_NS, returnValues);
			}
		}

		if (isValid(rVal)) {
			int r = convert(rVal);
			returnValues.put(Keys.R_VALUE, r);
			dataEvent(Types.VALID_R, returnValues);
		}

		event(Types.DONE_VALIDATING);
	}

	private boolean isValid(String string) {
		return string.length() != 0 && !isTooBig(string);
	}

	protected boolean isTooBig(String input) {
		try {
			int n = Integer.parseInt(input);

			if (n > MyConstants.PC_MAX_INPUT) {
				event(Types.INPUT_OVER_MAX_VALUE);
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			return true;
		}
	}

	private boolean isValidList(String list, int n) {
		if (list.length() == 0) {
			return false;
		}

		String[] nVals = list.split(",");
		int sum = 0;

		for (String val : nVals) {
			if (val.length() == 0) {
				continue;
			}

			if (!isValidListItem(val)) {
				return false;
			}
			sum += convert(val);
		}

		if (sum > n) {
			return false;
		}

		return true;
	}

	private boolean isValidListItem(String string) {
		return !isTooBig(string);
	}

	private int convert(String nVal) {
		return Integer.valueOf(nVal);
	}

	private ArrayList<Integer> convertList(String string) {
		ArrayList<Integer> result = new ArrayList<Integer>();

		String[] nVals = string.split(",");
		for (String val : nVals) {
			if (val.length() == 0) {
				continue;
			}

			result.add(convert(val));
		}

		return result;
	}

	private void addNList(String nVals, HashMap<Enum<?>, Object> returnValues) {
		ArrayList<Integer> nList = new ArrayList<Integer>();
		nList = convertList(nVals);
		returnValues.put(Keys.N_VALUES, nList);
	}

	public BigInteger factorial(int num) {
		BigInteger cachedValue = factCache.get(num);
		if (cachedValue != null) {
			return cachedValue;
		}

		if (num < 0) {
			return BigInteger.ZERO;
		}

		BigInteger answer = BigInteger.ONE;

		for (int i = num; i > 1; i--) {
			answer = answer.multiply(BigInteger.valueOf(i));
		}

		factCache.put(num, answer);
		return answer;
	}

	public BigInteger permutation(int n, int r) {
		if (n < r) {
			return BigInteger.ZERO;
		}

		return factorial(n).divide(factorial(n - r));
	}

	public BigInteger combination(int n, int r) {
		if (n < r) {
			return BigInteger.ZERO;
		}

		return factorial(n).divide(factorial(r).multiply(factorial(n - r)));
	}

	public BigInteger indistinctPermutation(int n, List<Integer> nVals) {
		BigInteger denom = BigInteger.ONE;
		for (int val : nVals) {
			denom = denom.multiply(factorial(val));
		}

		return factorial(n).divide(denom);
	}

	// TODO: Test
	public void updateResult(String key, String result) {
		results.put(key, result);
	}

	// TODO: Test
	public void clearResults() {
		results.clear();
	}

	public HashMap<String, String> getResults() {
		return results;
	}

	public String format(BigInteger number) {
		/* Works for all numbers 1 billion and greater */
		String numString = number.toString();
		String result = numString.substring(0, 8);
		int length = numString.length() - 1;

		int firstHidden = Integer.valueOf(numString.substring(8, 9));
		if (firstHidden >= 5) {
			Long increment = Long.valueOf(result) + 1;
			if (increment.toString().length() == 9) {
				length++;
			}

			result = increment.toString().substring(0, 8);
		}

		return result.substring(0, 1) + '.' + result.substring(1, 8) + "E" + length;
	}
}
