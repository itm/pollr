package de.uniluebeck.itm.ep0.poll.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class AddPollPlace extends Place {

    private String language;

    public AddPollPlace() {
        this.language = null;
    }

    public AddPollPlace(final String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public static class Tokenizer implements PlaceTokenizer<AddPollPlace> {

        public String getToken(final AddPollPlace place) {
            return place.getLanguage() != null ? String.valueOf(place
                    .getLanguage()) : "";
        }

        public AddPollPlace getPlace(final String token) {
            return new AddPollPlace("".equals(token) ? null : token);
        }
    }
}
