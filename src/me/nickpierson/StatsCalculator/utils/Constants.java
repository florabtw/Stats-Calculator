package me.nickpierson.StatsCalculator.utils;

public class Constants {

	public static final String SIZE = "Size";
	public static final String SUM = "Sum";
	public static final String ARITH_MEAN = "Arithmetic Mean";
	public static final String GEO_MEAN = "Geometric Mean";
	public static final String MEDIAN = "Median";
	public static final String MODE = "Mode";
	public static final String RANGE = "Range";
	public static final String SAMPLE_VAR = "Sample Variance";
	public static final String POP_VAR = "Population Variance";
	public static final String SAMPLE_DEV = "Sample Std. Deviation";
	public static final String POP_DEV = "Population Std. Deviation";
	public static final String COEFF_VAR = "Coefficient of Variation";
	public static final String SKEWNESS = "Skewness";
	public static final String KURTOSIS = "Kurtosis";

	public static final String[] BASIC_TITLES = { SIZE, SUM, ARITH_MEAN, GEO_MEAN, MEDIAN, MODE, RANGE, SAMPLE_VAR, POP_VAR, SAMPLE_DEV, POP_DEV, COEFF_VAR,
			SKEWNESS, KURTOSIS };

	public static final String N_FACT = "n!";
	public static final String R_FACT = "r!";
	public static final String N_PERM_R = "nPr";
	public static final String N_COMB_R = "nCr";
	public static final String INDISTINCT_PERM = "n! \u00F7\nn1!n2!n3!...";

	public static final String[] PC_TITLES = { N_FACT, R_FACT, N_PERM_R, N_COMB_R, INDISTINCT_PERM };

	public static final String RESULTS_KEY = "RESULTS";
	public static final String KEYPAD_KEY = "KEYPAD";
	public static final String SCROLL_POSITION_KEY = "SCROLL_POSITION";

	public static final String PC_DEFAULT_RESULT_VALUE = "n/a";
	public static final String DECIMAL_FORMAT_LARGE = "0.##########E0";
	public static final int DECIMAL_PLACES_LARGE = 10;

	public static final int PC_MAX_INPUT = 1000;
	public static final int MAX_FREQUENCY = 100000;
	public static final int MAX_PLAIN_FORMAT = 1000000000;

	public static final String MESSAGE_INPUT_OVER_MAX = "Input over 1000 is not allowed.";
	public static final String DESCRIPTIVE_NUMBER_ERROR = "Invalid input at item #%s";
	public static final String SAVE_SUCCESSFUL = "List was saved successfully";
	public static final String SAVE_FAILED = "List was NOT able to be saved!";
	public static final String LIST_LOAD_ERROR = "Error while loading list. Please try again later.";
	public static final String LIST_DELETE_ERROR = "Error while deleting list. Please try again later.";

	public static final String DEVELOPER_EMAIL = "nkcrpn@gmail.com";
	public static final String EMAIL_SUBJECT = "[Contact] Stats Calculator";
	public static final String EMAIL_CHOOSER_TITLE = "Send email...";
	public static final String RATE_ERROR = "Error while trying to open Play Store.";

}
