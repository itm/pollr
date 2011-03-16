package de.uniluebeck.itm.ep0.poll.client.ui;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import de.uniluebeck.itm.ep0.poll.domain.XoOptionList;

import java.util.List;

public interface VoteOptionListView extends BasicView<VoteOptionListView.Presenter>, IsWidget {

    void setOptionListName(String optionListName);

    String getOptionListName();

    HasWidgets getVoteTable();

    int getVoteTableRowCount();

    void setVoteTableWidget(int row, int column, Widget widget);

    public interface Presenter extends BasicPresenter<VoteOptionListView> {

        void setOptionList(XoOptionList optionList);

        XoOptionList getOptionList();

        void renderOptionList();

        void initHeader();

        void addVoteRow();
    }
}