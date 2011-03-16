package de.uniluebeck.itm.ep0.poll.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.user.client.Window;

public class VotePlace extends Place {

    private String uuid;
    private String remoteURL;
    private String remoteLanguageCode;

    public VotePlace() {

    }

    public VotePlace(final String uuid, final String remoteURL,
            final String remoteLanguageCode) {
        this.uuid = uuid;
        this.remoteURL = remoteURL;
        this.remoteLanguageCode = remoteLanguageCode;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public void setRemoteURL(String remoteURL) {
        this.remoteURL = remoteURL;
    }

    public String getRemoteURL() {
        return remoteURL;
    }

    public void setRemoteLanguageCode(String remoteLanguageCode) {
        this.remoteLanguageCode = remoteLanguageCode;
    }

    public String getRemoteLanguageCode() {
        return remoteLanguageCode;
    }

    public static class Tokenizer implements PlaceTokenizer<VotePlace> {

        public String getToken(final VotePlace place) {
            return "pollUuid=" + place.getUuid() + "&remoteURL="
                    + place.getRemoteURL() + "&remoteLanguageCode="
                    + place.getRemoteLanguageCode();
        }

        public VotePlace getPlace(final String token) {
            String uuid = "";
            String remoteURL = "";
            String remoteLanguageCode = "";
            String[] params = token.split("\\&");

            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    if (keyValue[0].equals("pollUuid")) {
                        uuid = keyValue[1];
                    }
                    if (keyValue[0].equals("remoteURL")) {
                        remoteURL = keyValue[1];
                    }
                    if (keyValue[0].equals("remoteLanguageCode")) {
                        remoteLanguageCode = keyValue[1];
                    }
                }
            }

            return new VotePlace(uuid, remoteURL, remoteLanguageCode);
        }
    }
}