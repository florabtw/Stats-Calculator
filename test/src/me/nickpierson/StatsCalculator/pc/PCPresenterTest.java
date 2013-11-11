package me.nickpierson.StatsCalculator.pc;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import me.nickpierson.StatsCalculator.utils.Constants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import com.thecellutioncenter.mvplib.ActionListener;
import com.thecellutioncenter.mvplib.DataActionListener;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class PCPresenterTest {

	protected PCView view;
	protected PCModel model;
	protected ArgumentCaptor<ActionListener> listener;
	protected ArgumentCaptor<DataActionListener> dataListener;

	@Before
	public void setup() {
		view = mock(PCView.class);
		model = mock(PCModel.class);

		listener = ArgumentCaptor.forClass(ActionListener.class);
		dataListener = ArgumentCaptor.forClass(DataActionListener.class);

		when(view.getNVal()).thenReturn("nval");
		when(view.getRVal()).thenReturn("rval");
		when(view.getNVals()).thenReturn("nval,nval");
	}

	public void setupPresenter() {
		PCPresenter.setup(model, view);
	}

	@Test
	public void viewIsInitializedWithDefaultValues() {
		setupPresenter();

		verify(model).clearResults();
		verify(view).showResults(makeEmptyResultsMap());
	}

	@Test
	public void whenAnyEditTextIsClicked_ThenKeypadIsShown() {
		setupPresenter();

		verify(view).addListener(listener.capture(), eq(PCView.Types.EDITTEXT_CLICKED));

		listener.getValue().fire();

		verify(view).showKeypad();
	}

	@Test
	public void whenCalculateButtonIsPressed_ThenPreviousResultsAreCleared() {
		setupPresenter();

		verify(view).addListener(listener.capture(), eq(PCView.Types.DONE_PRESSED));

		listener.getValue().fire();

		verify(model, times(2)).clearResults();
	}

	@Test
	public void whenCalculateButtonIsPressed_ThenModelValidatesInput() {
		setupPresenter();

		verify(view).addListener(listener.capture(), eq(PCView.Types.DONE_PRESSED));

		listener.getValue().fire();

		verify(model).validateInput(view.getNVal(), view.getRVal(), view.getNVals());
	}

	@Test
	public void whenNValueIsShownValid_ThenRelatedValuesAreDisplayed() {
		HashMap<Enum<?>, Integer> map = new HashMap<Enum<?>, Integer>();
		map.put(PCModel.Keys.N_VALUE, 6);
		when(model.factorial(6)).thenReturn(BigInteger.valueOf(720));
		setupPresenter();

		verify(model).addListener(dataListener.capture(), eq(PCModel.Types.VALID_N));

		dataListener.getValue().fire(map);

		verify(model).updateResult(Constants.N_FACT, "720");
	}

	@Test
	public void whenOnlyRValueIsShownValid_ThenRelatedValuesAreDisplayed() {
		HashMap<Enum<?>, Integer> map = new HashMap<Enum<?>, Integer>();
		map.put(PCModel.Keys.R_VALUE, 5);
		when(model.factorial(5)).thenReturn(BigInteger.valueOf(120));
		setupPresenter();

		verify(model).addListener(dataListener.capture(), eq(PCModel.Types.VALID_R));

		dataListener.getValue().fire(map);

		verify(model).updateResult(Constants.R_FACT, "120");
		verify(model, never()).updateResult(eq(Constants.N_FACT), any(String.class));
	}

	@Test
	public void whenRAndNValuesAreShownValid_ThenRelatedValuesAreDisplayed() {
		HashMap<Enum<?>, Integer> map = new HashMap<Enum<?>, Integer>();
		map.put(PCModel.Keys.N_VALUE, 8);
		map.put(PCModel.Keys.R_VALUE, 4);
		when(model.factorial(4)).thenReturn(BigInteger.valueOf(24));
		when(model.permutation(8, 4)).thenReturn(BigInteger.valueOf(1680));
		when(model.combination(8, 4)).thenReturn(BigInteger.valueOf(70));
		setupPresenter();

		verify(model).addListener(dataListener.capture(), eq(PCModel.Types.VALID_N));

		verify(model).addListener(dataListener.capture(), eq(PCModel.Types.VALID_R));
		dataListener.getValue().fire(map);

		verify(model).updateResult(Constants.N_PERM_R, "1,680");
		verify(model).updateResult(Constants.N_COMB_R, "70");
	}

	@Test
	public void whenNAndNValuesAreValid_ThenRelatedValuesAreDisplayed() {
		HashMap<Enum<?>, Object> map = new HashMap<Enum<?>, Object>();
		ArrayList<Integer> testNVals = new ArrayList<Integer>();
		testNVals.add(2);
		testNVals.add(3);
		map.put(PCModel.Keys.N_VALUE, 9);
		map.put(PCModel.Keys.N_VALUES, testNVals);
		when(model.indistinctPermutation(9, testNVals)).thenReturn(BigInteger.valueOf(30240));
		setupPresenter();

		verify(model).addListener(dataListener.capture(), eq(PCModel.Types.VALID_NS));

		dataListener.getValue().fire(map);

		verify(model).updateResult(Constants.INDISTINCT_PERM, "30,240");
	}

	@Test
	public void numbersOverOneBillionAreFormatted() {
		HashMap<Enum<?>, Integer> map = new HashMap<Enum<?>, Integer>();
		map.put(PCModel.Keys.N_VALUE, 13);
		BigInteger nFact = BigInteger.valueOf(6227020800L);
		when(model.factorial(13)).thenReturn(nFact);
		when(model.format(nFact)).thenReturn("6.2270208E9");
		setupPresenter();

		verify(model).addListener(dataListener.capture(), eq(PCModel.Types.VALID_N));

		dataListener.getValue().fire(map);

		verify(model).updateResult(Constants.N_FACT, "6.2270208E9");
	}

	@Test
	public void resultsAreShown_WhenModelIsDoneValidating() {
		HashMap<String, String> emptyResults = new HashMap<String, String>();
		emptyResults = makeEmptyResultsMap();
		HashMap<String, String> results = new HashMap<String, String>();
		results.put(Constants.R_FACT, "720");
		setMissingValuesToDefault(results);
		when(model.getResults()).thenReturn(emptyResults);
		setupPresenter();
		when(model.getResults()).thenReturn(results);

		verify(model).addListener(listener.capture(), eq(PCModel.Types.DONE_VALIDATING));

		listener.getValue().fire();

		verify(view).showResults(emptyResults);
		verify(view).showResults(results);
	}

	@Test
	public void PCPresenterHandlesInvalidInputCorrectly() {
		setupPresenter();

		verify(model).addListener(listener.capture(), eq(PCModel.Types.INPUT_OVER_MAX_VALUE));

		listener.getValue().fire();

		verify(view).showToast(Constants.MESSAGE_INPUT_OVER_MAX);
	}

	private HashMap<String, String> makeEmptyResultsMap() {
		HashMap<String, String> results = new HashMap<String, String>();
		for (String title : Constants.PC_TITLES) {
			results.put(title, Constants.PC_DEFAULT_RESULT_VALUE);
		}

		return results;
	}

	private void setMissingValuesToDefault(HashMap<String, String> results) {
		for (String title : Constants.PC_TITLES) {
			if (results.get(title) == null) {
				results.put(title, Constants.PC_DEFAULT_RESULT_VALUE);
			}
		}
	}
}
