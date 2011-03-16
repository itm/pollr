package de.uniluebeck.itm.ep0.poll.shared;

import java.io.Serializable;
import java.util.List;

import de.uniluebeck.itm.ep0.poll.domain.XoPollInfo;

public class LoadPollsResult implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4768928141451553239L;

    private List<XoPollInfo> pollInfos;
    private Boolean isRemote;
    private String remotePollSystemURL;
    private String remoteLanguageCode;

    public void setPollInfos(List<XoPollInfo> pollInfos) {
        this.pollInfos = pollInfos;
    }

    public List<XoPollInfo> getPollInfos() {
        return pollInfos;
    }

    public void setIsRemote(Boolean isRemote) {
        this.isRemote = isRemote;
    }

    public Boolean getIsRemote() {
        return isRemote;
    }

    public void setRemotePollSystemURL(String remotePollSystemURL) {
        this.remotePollSystemURL = remotePollSystemURL;
    }

    public String getRemotePollSystemURL() {
        return remotePollSystemURL;
    }

    public void setRemoteLanguageCode(String remoteLanguageCode) {
        this.remoteLanguageCode = remoteLanguageCode;
    }

    public String getRemoteLanguageCode() {
        return remoteLanguageCode;
    }
    
}
