package de.uniluebeck.itm.ep0.poll.client.ui.custom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;

import de.uniluebeck.itm.ep0.poll.api.PollClientServiceAsync;
import de.uniluebeck.itm.ep0.poll.client.ui.BasicPresenter;
import de.uniluebeck.itm.ep0.poll.client.ui.util.ExceptionEvent;
import de.uniluebeck.itm.ep0.poll.client.ui.util.StacktraceUtil;
import de.uniluebeck.itm.ep0.poll.domain.XoLocalizedString;

public class LocalizePresenter implements BasicPresenter<LocalizableTextBox> {

    private LocalizableTextBox view;
    private LocalizePopUp popUp;
    private PollClientServiceAsync service;
    private EventBus eventBus;
    /* State information for the Pop-Up */
    private final List<ListBox> listBoxes = new ArrayList<ListBox>();
    private final List<TextBox> textBoxes = new ArrayList<TextBox>();
    private final Stack<ListBoxSelection> selections = new Stack<ListBoxSelection>();
    private Map<String, String> lang2loc = null;
    private Map<String, String> loc2lang = null;
    private String[] langSorted = null;

    public class ListBoxSelection {

        private ListBox listBox;
        private String selectedValue;

        ListBoxSelection() {
        }

        ListBoxSelection(final ListBox listBox, final String value) {
            this.listBox = listBox;
            selectedValue = value;
        }

        public ListBox getListBox() {
            return listBox;
        }

        public void setListBox(final ListBox listBox) {
            this.listBox = listBox;
        }

        public String getSelectedValue() {
            return selectedValue;
        }

        public void setSelectedValue(final String selectedValue) {
            this.selectedValue = selectedValue;
        }
    }

    @Inject
    public LocalizePresenter(final LocalizableTextBox view,
                             final LocalizePopUp popUp, final PollClientServiceAsync service,
                             final EventBus eventBus) {
        this.view = view;
        this.popUp = popUp;
        this.service = service;
        this.eventBus = eventBus;
        bind();
    }

    private void bind() {
        this.view.setPresenter(this);
        this.popUp.setPresenter(this);
    }

    public void showPopUp() {
        updateView();
        popUp.show();
        popUp.setPosition(view.getTextBox().getAbsoluteLeft(), view
                .getTextBox().getAbsoluteTop()
                + view.getTextBox().getOffsetHeight());
    }

    public void getLanguages() {
        // Set up the callback object.

        final AsyncCallback<Map<String, String>> callback = new AsyncCallback<Map<String, String>>() {
            public void onFailure(final Throwable caught) {
                eventBus.fireEvent(new ExceptionEvent(caught.getMessage(), StacktraceUtil.stacktraceToString(caught)));
            }

            public void onSuccess(final Map<String, String> result) {
                // set up two maps: Language->Locale and Locale->Language 
                setLang2loc(result);
                setLoc2lang(new HashMap<String, String>(result.size()));
                for (String key : result.keySet()) {
                    getLoc2lang().put(getLang2loc().get(key), key);
                }
                // set up a sorted string array for the language selection boxes
                setLangSorted(new String[result.size()]);
                getLang2loc().keySet().toArray(getLangSorted());
                Arrays.sort(getLangSorted());
                for (; getSelections().size() > 0; getSelections().pop()) {
                    final ListBoxSelection lbs = getSelections().peek();
                    fillLanguagesBoxSync(lbs.getListBox(),
                            lbs.getSelectedValue());
                }
            }
        };

        // Make the call to the poll-client service.
        service.getLanguages(callback);
    }

    public LocalizableTextBox getView() {
        return view;
    }

    private void fillLanguagesBox(final ListBox box, final String selectedValue) {
        if (null != lang2loc) {
            fillLanguagesBoxSync(box, selectedValue);
        } else {
            selections.add(new ListBoxSelection(box, selectedValue));
            getLanguages();
        }
    }

    public void updateObject() {
        final Map<String, String> localizations = new HashMap<String, String>(
                listBoxes.size());
        for (int i = 0; i < listBoxes.size(); i++) {
            final ListBox lb = listBoxes.get(i);
            final TextBox tb = textBoxes.get(i);
            if (null != tb.getValue() && !"".equals(tb.getValue())) {
                localizations.put(
                        lang2loc.get(lb.getValue(lb.getSelectedIndex())),
                        tb.getValue());
            }
        }
        view.getValue().setLocalizedValues(localizations);

        view.setBoxText(view.getValue().getLocalizedValue(
                LocaleInfo.getCurrentLocale().getLocaleName()));
    }

    public void updateView() {
        listBoxes.clear();
        textBoxes.clear();

        popUp.getLanguageTable().clear();

        if (null == view.getValue()) {
            view.setValue(new XoLocalizedString());
        }

        int numRows = popUp.getLanguageTable().getRowCount();

        for (String key : view.getValue().getLocalizedValues().keySet()) {
            final ListBox lb = new ListBox();
            listBoxes.add(lb);
            fillLanguagesBox(lb, key);
            final TextBox tb = new TextBox();
            textBoxes.add(tb);
            tb.setText(view.getValue().getLocalizedValues().get(key));
            popUp.getLanguageTable().setWidget(numRows, 0, lb);
            popUp.getLanguageTable().setWidget(numRows, 1, tb);
            numRows++;
        }
        addRow();
    }

    public void addRow() {
        final int numRows = popUp.getLanguageTable().getRowCount();

        final ListBox dropBox = new ListBox(false);
        final TextBox tb = new TextBox();

        fillLanguagesBox(dropBox, null);

        popUp.getLanguageTable().setWidget(numRows, 0, dropBox);
        popUp.getLanguageTable().setWidget(numRows, 1, tb);
        listBoxes.add(dropBox);
        textBoxes.add(tb);
        // flexTable.getFlexCellFormatter().setRowSpan(0, 1, numRows + 1);
    }

    public void fillLanguagesBoxSync(final ListBox box,
                                     final String selectedValue) {
        for (int j = 0; j < langSorted.length; j++) {
            box.insertItem(langSorted[j], j);
            if (null != selectedValue) {
                final int index = Arrays.binarySearch(langSorted,
                        loc2lang.get(selectedValue));
                if (index >= 0) {
                    box.setSelectedIndex(index);
                }
            }
        }
    }

    public XoLocalizedString getXoLocalizedString() {
        return view.getValue();
    }

    public Map<String, String> getLang2loc() {
        return lang2loc;
    }

    public void setLang2loc(final Map<String, String> lang2loc) {
        this.lang2loc = lang2loc;
    }

    public Map<String, String> getLoc2lang() {
        return loc2lang;
    }

    public void setLoc2lang(final Map<String, String> loc2lang) {
        this.loc2lang = loc2lang;
    }

    public String[] getLangSorted() {
        return langSorted;
    }

    public void setLangSorted(final String[] langSorted) {
        this.langSorted = langSorted;
    }

    public Stack<ListBoxSelection> getSelections() {
        return selections;
    }
}
