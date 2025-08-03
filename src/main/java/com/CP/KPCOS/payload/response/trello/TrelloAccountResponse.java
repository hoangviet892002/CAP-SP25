package com.CP.KPCOS.payload.response.trello;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrelloAccountResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("aaId")
    private String aaId;

    @JsonProperty("activityBlocked")
    private boolean activityBlocked;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("initials")
    private String initials;

    @JsonProperty("avatarUrl")
    private String avatarUrl;

    @JsonProperty("avatarHash")
    private String avatarHash;

    @JsonProperty("avatarSource")
    private String avatarSource;

    @JsonProperty("confirmed")
    private boolean confirmed;

    @JsonProperty("memberType")
    private String memberType;

    @JsonProperty("status")
    private String status;

    @JsonProperty("url")
    private String url;

    @JsonProperty("bio")
    private String bio;

    @JsonProperty("bioData")
    private Object bioData;

    @JsonProperty("gravatarHash")
    private String gravatarHash;

    @JsonProperty("idBoards")
    private List<String> idBoards;

    @JsonProperty("idOrganizations")
    private List<String> idOrganizations;

    @JsonProperty("idEnterprise")
    private String idEnterprise;

    @JsonProperty("idEnterprisesDeactivated")
    private List<String> idEnterprisesDeactivated;

    @JsonProperty("idPremOrgsAdmin")
    private List<String> idPremOrgsAdmin;

    @JsonProperty("products")
    private List<String> products;

    @JsonProperty("limits")
    private TrelloLimitsResponse limits;

    @JsonProperty("prefs")
    private TrelloPrefsResponse prefs;

    @JsonProperty("dateLastImpression")
    private String dateLastImpression;

    @JsonProperty("dateLastActive")
    private String dateLastActive;

    @JsonProperty("marketingOptIn")
    private TrelloMarketingOptInResponse marketingOptIn;

    @JsonProperty("trophies")
    private List<String> trophies;

    @JsonProperty("oneTimeMessagesDismissed")
    private List<String> oneTimeMessagesDismissed;

    @JsonProperty("messagesDismissed")
    private List<Map<String, Object>> messagesDismissed;

    @JsonProperty("nonPublic")
    private Map<String, Object> nonPublic;

    @JsonProperty("nonPublicAvailable")
    private boolean nonPublicAvailable;

    @JsonProperty("loginTypes")
    private List<String> loginTypes;

    @JsonProperty("nodeId")
    private String nodeId;

    @JsonProperty("sessionType")
    private String sessionType;

    @JsonProperty("domainClaimed")
    private String domainClaimed;

    @JsonProperty("premiumFeatures")
    private List<String> premiumFeatures;

    @JsonProperty("isAaMastered")
    private boolean isAaMastered;

    @JsonProperty("ixUpdate")
    private String ixUpdate;

    @JsonProperty("aaBlockSyncUntil")
    private String aaBlockSyncUntil;

    @JsonProperty("aaEmail")
    private String aaEmail;

    @JsonProperty("aaEnrolledDate")
    private String aaEnrolledDate;

    @JsonProperty("credentialsRemovedCount")
    private int credentialsRemovedCount;

    @JsonProperty("idMemberReferrer")
    private String idMemberReferrer;

    @JsonProperty("uploadedAvatarHash")
    private String uploadedAvatarHash;

    @JsonProperty("uploadedAvatarUrl")
    private String uploadedAvatarUrl;

    @JsonProperty("idEnterprisesAdmin")
    private List<String> idEnterprisesAdmin;
}
