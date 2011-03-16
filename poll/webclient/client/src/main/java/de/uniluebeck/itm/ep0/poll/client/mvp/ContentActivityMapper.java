package de.uniluebeck.itm.ep0.poll.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;

import de.uniluebeck.itm.ep0.poll.client.PollAppGinjector;
import de.uniluebeck.itm.ep0.poll.client.activity.AddPollActivity;
import de.uniluebeck.itm.ep0.poll.client.activity.ShowPollsActivity;
import de.uniluebeck.itm.ep0.poll.client.activity.VoteActivity;
import de.uniluebeck.itm.ep0.poll.client.place.AddPollPlace;
import de.uniluebeck.itm.ep0.poll.client.place.ShowPollsPlace;
import de.uniluebeck.itm.ep0.poll.client.place.VotePlace;

public class ContentActivityMapper implements ActivityMapper {

    private PollAppGinjector injector;

    /**
     * AppActivityMapper associates each Place with its corresponding
     * {@link Activity}
     *
     * @param injector GIN injector to be passed to activities
     */
    @Inject
    public ContentActivityMapper(final PollAppGinjector injector) {
        super();
        this.injector = injector;
    }

    /**
     * Map each Place to its corresponding Activity.
     */
    public Activity getActivity(final Place place) {
        Activity mappedActivity = null;
        if (place instanceof AddPollPlace) {
            final AddPollActivity activity = injector.getAddPollActivity();
            activity.setPlace((AddPollPlace) place);
            mappedActivity = activity;
        } else if (place instanceof ShowPollsPlace) {
            final ShowPollsActivity activity = injector.getShowPollsActivity();
            activity.setPlace((ShowPollsPlace) place);
            mappedActivity = activity;
        } else if (place instanceof VotePlace) {
            final VoteActivity activity = injector.getVoteActivity();
            activity.setPlace((VotePlace) place);
            mappedActivity = activity;
        }
        return mappedActivity;
    }
}
