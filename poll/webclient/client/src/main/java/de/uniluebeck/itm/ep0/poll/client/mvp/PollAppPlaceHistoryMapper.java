package de.uniluebeck.itm.ep0.poll.client.mvp;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

import de.uniluebeck.itm.ep0.poll.client.place.AddPollPlace;
import de.uniluebeck.itm.ep0.poll.client.place.ShowPollsPlace;
import de.uniluebeck.itm.ep0.poll.client.place.VotePlace;

/**
 * PlaceHistoryMapper interface is used to attach all places which the
 * PlaceHistoryHandler should be aware of. This is done via the @WithTokenizers
 * annotation or by extending PlaceHistoryMapperWithFactory and creating a
 * separate TokenizerFactory.
 */
@WithTokenizers({AddPollPlace.Tokenizer.class, ShowPollsPlace.Tokenizer.class, VotePlace.Tokenizer.class})
public interface PollAppPlaceHistoryMapper extends PlaceHistoryMapper {
}
