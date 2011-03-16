package de.uniluebeck.itm.ep0.poll.client.ui;

import java.util.Date;

import com.google.inject.Inject;

import de.uniluebeck.itm.ep0.poll.client.PollApp;
import de.uniluebeck.itm.ep0.poll.domain.XoDateOption;
import de.uniluebeck.itm.ep0.poll.domain.XoLocalizedString;
import de.uniluebeck.itm.ep0.poll.domain.XoOption;
import de.uniluebeck.itm.ep0.poll.domain.XoTextOption;

public class OptionPresenter implements OptionView.Presenter {

    private OptionView view;

    @Inject
    public OptionPresenter(final OptionView view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    public OptionView getView() {
        return view;
    }

    public XoOption getOption() {
        XoOption xoOption = null;

        final XoLocalizedString text = view.getTextBox().getValue();
        final Date date = view.getDateBox().getValue();
        final XoLocalizedString description = view.getDescriptionBox().getValue();

        if (date != null) {
            xoOption = new XoDateOption(date, description);
        } else if (text != null && !text.getLocalizedValues().isEmpty()) {
            xoOption = new XoTextOption(text, description);
        }

        return xoOption;
    }

    public void setNumber(final int number) {
        view.setNumber(number);
    }

    public void setOptionType(final PollApp.OptionType optionType) {
        view.setOptionType(optionType);
    }
}
