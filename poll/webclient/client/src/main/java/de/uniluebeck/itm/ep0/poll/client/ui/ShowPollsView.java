package de.uniluebeck.itm.ep0.poll.client.ui;

import com.google.gwt.user.client.ui.IsWidget;

import de.uniluebeck.itm.ep0.poll.shared.LoadPollsResult;

public interface ShowPollsView extends BasicView<ShowPollsPresenter>, IsWidget {

    void setPollInfos(LoadPollsResult pollInfos);

    void setPresenter(ShowPollsPresenter presenter);

    public interface Presenter extends BasicPresenter<ShowPollsView> {
        
        void loadPolls(String url, String languageCode);
    }
}
