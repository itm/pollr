package de.uniluebeck.itm.ep0.poll.client.ui;

import java.util.Iterator;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import de.uniluebeck.itm.ep0.poll.client.PollApp;
import de.uniluebeck.itm.ep0.poll.domain.XoLocalizedString;
import de.uniluebeck.itm.ep0.poll.domain.XoOption;
import de.uniluebeck.itm.ep0.poll.domain.XoOptionList;

public class OptionListPresenter implements OptionListView.Presenter {

    private OptionListView view;
    private PollApp.OptionType optionType;

    @Inject
    public OptionListPresenter(final OptionListView view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    public XoOptionList getOptionList() {
        final XoLocalizedString name = view.getNameBox().getValue();
        final XoOptionList xoOptionList = new XoOptionList(name);

        OptionView.Presenter optionPresenter;
        XoOption xoOption = null;
        for (final Iterator<Widget> i = view.getOptionTable().iterator(); i.hasNext();) {
            optionPresenter = ((OptionView) i.next()).getPresenter();
            xoOption = optionPresenter.getOption();
            if (null != xoOption) {
                xoOptionList.getOptions().add(xoOption);
            }
        }

        return xoOptionList;
    }

    public void setNumber(final int number) {
        view.setNumber(number);
    }

    public PollApp.OptionType getOptionType() {
        return optionType;
    }

    public void setOptionType(final PollApp.OptionType optionType) {
        this.optionType = optionType;
    }

    public OptionListView getView() {
        return view;
    }
}
