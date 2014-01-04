package me.nickpierson.StatsCalculator.basic;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;

import me.nickpierson.StatsCalculator.utils.Constants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.app.Activity;

import com.thecellutioncenter.mvplib.ActionListener;
import com.thecellutioncenter.mvplib.DataActionListener;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class BasicPresenterTest {

	protected BasicView view;
	protected BasicModel model;

	protected ArgumentCaptor<ActionListener> listener;
	protected ArgumentCaptor<DataActionListener> dataListener;
	protected Activity activity;

	@Before
	public void setup() {
		view = mock(BasicView.class);
		model = mock(BasicModel.class);
		activity = mock(Activity.class);

		listener = ArgumentCaptor.forClass(ActionListener.class);
		dataListener = ArgumentCaptor.forClass(DataActionListener.class);

		when(model.getEmptyResults()).thenReturn(makeEmptyResults());
	}

	public void setupPresenter() {
		BasicPresenter.setup(activity, model, view);
	}

	@Test
	public void viewIsInitializedByPresenter() {
		setupPresenter();

		verify(model).getEmptyResults();
		verify(view).showResults(model.formatResults(model.getEmptyResults()));
	}

	@Test
	public void whenEditTextIsClicked_ThenKeypadIsShown() {
		setupPresenter();

		verify(view).addListener(listener.capture(), eq(BasicView.Types.EDITTEXT_CLICKED));

		listener.getValue().fire();

		verify(view).showKeypad();
	}

	@Test
	public void whenDoneIsClicked_ThenInputIsValidated() {
		when(view.getInput()).thenReturn("15,32x4,17.9");
		setupPresenter();

		verify(view).addListener(listener.capture(), eq(BasicView.Types.DONE_PRESSED));

		listener.getValue().fire();

		verify(model).validateInput(view.getInput());
	}

	@Test
	public void whenValidInput_ThenInputIsShown() {
		ArrayList<Double> testResults = new ArrayList<Double>();
		HashMap<Enum<?>, ArrayList<Double>> testMap = new HashMap<Enum<?>, ArrayList<Double>>();
		testMap.put(BasicModel.Keys.VALIDATED_LIST, testResults);
		setupPresenter();

		verify(model).addListener(dataListener.capture(), eq(BasicModel.Types.VALID_INPUT));

		dataListener.getValue().fire(testMap);

		verify(view, times(2)).showResults(model.formatResults(model.calculateResults(testResults)));
	}

	@Test
	public void whenInvalidNumber_ThenEmptyResultsAreShownAndToastIsDisplayed() {
		HashMap<Enum<?>, Object> testMap = new HashMap<Enum<?>, Object>();
		int errorPosition = 5;
		String errorText = "errrrerr";
		testMap.put(BasicModel.Keys.INVALID_POSITION, errorPosition);
		testMap.put(BasicModel.Keys.INVALID_TEXT, errorText);
		setupPresenter();

		verify(model).addListener(dataListener.capture(), eq(BasicModel.Types.INVALID_INPUT));

		dataListener.getValue().fire(testMap);

		verify(view).showErrorToast(errorPosition);
		verify(view).selectInput(errorText);
	}

	@Test
	public void whenSaveListMenuIsClicked_ThenPopupIsDisplayed() {
		setupPresenter();

		verify(view).addListener(listener.capture(), eq(BasicView.Types.MENU_SAVE));

		listener.getValue().fire();

		verify(view).showSaveListPopup();
	}

	@Test
	public void whenSaveButtonIsClicked_ThenListIsSaved() {
		String name = "test name";
		HashMap<Enum<?>, String> testMap = new HashMap<Enum<?>, String>();
		testMap.put(BasicView.Keys.LIST_NAME, name);
		setupPresenter();

		verify(view).addListener(dataListener.capture(), eq(BasicView.Types.SAVE_LIST));

		dataListener.getValue().fire(testMap);

		verify(model).saveList(name, view.getInput());
	}

	@Test
	public void whenSaveIsSuccessful_ThenToastIsDisplayed() {
		setupPresenter();

		verify(model).addListener(listener.capture(), eq(BasicModel.Types.SAVE_SUCCESSFUL));

		listener.getValue().fire();

		verify(view).showToast(Constants.SAVE_SUCCESSFUL);
	}

	@Test
	public void whenSaveIsNotSuccessful_ThenToastIsDisplayed() {
		setupPresenter();

		verify(model).addListener(listener.capture(), eq(BasicModel.Types.SAVE_FAILED));

		listener.getValue().fire();

		verify(view).showToast(Constants.SAVE_FAILED);
	}

	@Test
	public void whenLoadOrDeleteListMenuClicked_ThenPopupIsDisplayed() {
		when(model.getSavedLists()).thenReturn(new String[] { "what's up?", "not much" });
		setupPresenter();

		verify(view).addListener(listener.capture(), eq(BasicView.Types.MENU_LOAD_OR_DELETE));

		listener.getValue().fire();

		verify(view).showLoadListPopup(model.getSavedLists());
	}

	@Test
	public void whenLoadOrDeleteListMenuClickedAndNoListsAvailable_ThenNothingHappens() {
		when(model.getSavedLists()).thenReturn(null);
		setupPresenter();

		verify(view).addListener(listener.capture(), eq(BasicView.Types.MENU_LOAD_OR_DELETE));

		listener.getValue().fire();

		verify(model).getSavedLists();
		verify(view, never()).showLoadListPopup(model.getSavedLists());
	}

	@Test
	public void whenLoadListIsClicked_ThenListIsLoadedAndKeypadShown() {
		when(view.isKeyPadVisible()).thenReturn(false);
		String listName = "BobTheBuilder";
		when(model.loadList(listName)).thenReturn("1,2,3,4,5");
		HashMap<Enum<?>, String> testMap = new HashMap<Enum<?>, String>();
		testMap.put(BasicView.Keys.LIST_NAME, listName);
		setupPresenter();

		verify(view).addListener(dataListener.capture(), eq(BasicView.Types.LOAD_LIST));

		dataListener.getValue().fire(testMap);

		verify(view).setInputText(model.loadList(listName));
		verify(view).showKeypad();
	}

	@Test
	public void whenDeleteListIsClicked_ThenListIsDeletedAndPopupIsShownAgain() {
		when(model.getSavedLists()).thenReturn(new String[] { "another", "list" });
		String listName = "CatDog";
		HashMap<Enum<?>, String> testMap = new HashMap<Enum<?>, String>();
		testMap.put(BasicView.Keys.LIST_NAME, listName);
		setupPresenter();

		verify(view).addListener(dataListener.capture(), eq(BasicView.Types.DELETE_LIST));

		dataListener.getValue().fire(testMap);

		verify(model).deleteList(listName);
		verify(view).showLoadListPopup(model.getSavedLists());
	}

	@Test
	public void whenErrorLoadingList_ThenToastIsDisplayed() {
		setupPresenter();

		verify(model).addListener(listener.capture(), eq(BasicModel.Types.LOAD_ERROR));

		listener.getValue().fire();

		verify(view).showToast(Constants.LIST_LOAD_ERROR);
	}

	@Test
	public void whenErrorDeletingList_ThenToastIsDisplayed() {
		setupPresenter();

		verify(model).addListener(listener.capture(), eq(BasicModel.Types.DELETE_ERROR));

		listener.getValue().fire();

		verify(view).showToast(Constants.LIST_DELETE_ERROR);
	}

	private HashMap<String, Double> makeEmptyResults() {
		HashMap<String, Double> results = new HashMap<String, Double>();
		for (String key : Constants.BASIC_TITLES) {
			results.put(key, Double.NaN);
		}

		return results;
	}
}
