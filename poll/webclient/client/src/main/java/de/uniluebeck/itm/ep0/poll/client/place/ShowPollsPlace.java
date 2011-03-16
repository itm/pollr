package de.uniluebeck.itm.ep0.poll.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ShowPollsPlace extends Place {

    public ShowPollsPlace() {
    }

    public static class Tokenizer implements PlaceTokenizer<ShowPollsPlace> {

        // @Override
        public String getToken(final ShowPollsPlace place) {
            return "";
        }

        // @Override
        public ShowPollsPlace getPlace(final String token) {
            return new ShowPollsPlace(token);
        }

    }

    public ShowPollsPlace(final String token) {
    }

}
