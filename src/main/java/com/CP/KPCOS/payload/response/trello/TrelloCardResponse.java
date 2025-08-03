package com.CP.KPCOS.payload.response.trello;

import com.CP.KPCOS.entity.trello.TrelloCardEntity;
import com.CP.KPCOS.entity.trello.TrelloTokenEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TrelloCardResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("badges")
    private Badges badges;

    @JsonProperty("checkItemStates")
    private List<Object> checkItemStates;

    @JsonProperty("closed")
    private boolean closed;

    @JsonProperty("dueComplete")
    private boolean dueComplete;

    @JsonProperty("dateLastActivity")
    private String dateLastActivity;

    @JsonProperty("desc")
    private String desc;

    @JsonProperty("descData")
    private DescData descData;

    @JsonProperty("due")
    private String due;

    @JsonProperty("dueReminder")
    private String dueReminder;

    @JsonProperty("email")
    private String email;

    @JsonProperty("idBoard")
    private String idBoard;

    @JsonProperty("idChecklists")
    private List<String> idChecklists;

    @JsonProperty("idList")
    private String idList;

    @JsonProperty("idMembers")
    private List<String> idMembers;

    @JsonProperty("idMembersVoted")
    private List<String> idMembersVoted;

    @JsonProperty("idShort")
    private Integer idShort;

    @JsonProperty("idAttachmentCover")
    private String idAttachmentCover;

    @JsonProperty("labels")
    private List<Object> labels;

    @JsonProperty("idLabels")
    private List<String> idLabels;

    @JsonProperty("manualCoverAttachment")
    private boolean manualCoverAttachment;

    @JsonProperty("name")
    private String name;

    @JsonProperty("nodeId")
    private String nodeId;

    @JsonProperty("pinned")
    private boolean pinned;

    @JsonProperty("pos")
    private Long pos;

    @JsonProperty("shortLink")
    private String shortLink;

    @JsonProperty("shortUrl")
    private String shortUrl;

    @JsonProperty("start")
    private String start;

    @JsonProperty("subscribed")
    private boolean subscribed;

    @JsonProperty("url")
    private String url;

    @JsonProperty("cover")
    private Cover cover;

    @JsonProperty("isTemplate")
    private boolean isTemplate;

    @JsonProperty("cardRole")
    private String cardRole;

    @JsonProperty("mirrorSourceId")
    private String mirrorSourceId;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Badges {
        @JsonProperty("attachments")
        private Integer attachments;

        @JsonProperty("fogbugz")
        private String fogbugz;

        @JsonProperty("checkItems")
        private Integer checkItems;

        @JsonProperty("checkItemsChecked")
        private Integer checkItemsChecked;

        @JsonProperty("checkItemsEarliestDue")
        private String checkItemsEarliestDue;

        @JsonProperty("comments")
        private Integer comments;

        @JsonProperty("description")
        private boolean description;

        @JsonProperty("due")
        private String due;

        @JsonProperty("dueComplete")
        private boolean dueComplete;

        @JsonProperty("lastUpdatedByAi")
        private boolean lastUpdatedByAi;

        @JsonProperty("start")
        private String start;

        @JsonProperty("externalSource")
        private String externalSource;

        @JsonProperty("attachmentsByType")
        private AttachmentsByType attachmentsByType;

        @JsonProperty("location")
        private boolean location;

        @JsonProperty("votes")
        private Integer votes;

        @JsonProperty("maliciousAttachments")
        private Integer maliciousAttachments;

        @JsonProperty("viewingMemberVoted")
        private boolean viewingMemberVoted;

        @JsonProperty("subscribed")
        private boolean subscribed;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class AttachmentsByType {
        @JsonProperty("trello")
        private TrelloAttachments trello;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class TrelloAttachments {
        @JsonProperty("board")
        private Integer board;

        @JsonProperty("card")
        private Integer card;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class DescData {
        @JsonProperty("emoji")
        private Object emoji;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Cover {
        @JsonProperty("idAttachment")
        private String idAttachment;

        @JsonProperty("color")
        private String color;

        @JsonProperty("idUploadedBackground")
        private String idUploadedBackground;

        @JsonProperty("size")
        private String size;

        @JsonProperty("brightness")
        private String brightness;

        @JsonProperty("scaled")
        private List<ScaledImage> scaled;

        @JsonProperty("edgeColor")
        private String edgeColor;

        @JsonProperty("idPlugin")
        private String idPlugin;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ScaledImage {
        @JsonProperty("id")
        private String id;

        @JsonProperty("_id")
        private String _id;

        @JsonProperty("scaled")
        private boolean scaled;

        @JsonProperty("url")
        private String url;

        @JsonProperty("bytes")
        private Integer bytes;

        @JsonProperty("height")
        private Integer height;

        @JsonProperty("width")
        private Integer width;
    }

    /**
     * Convert TrelloCardResponse to TrelloCardEntity
     */
    public TrelloCardEntity toEntity(TrelloTokenEntity trelloToken) {
        TrelloCardEntity entity = new TrelloCardEntity();
        entity.setTrelloCardId(this.id);
        entity.setTrelloBoardId(this.idBoard);
        entity.setTrelloListId(this.idList);
        entity.setName(this.name);
        entity.setTrelloId(trelloToken.getTrelloId());

        // Extract image URL from cover (use the largest scaled image for better quality)
        if (this.cover != null && this.cover.getScaled() != null && !this.cover.getScaled().isEmpty()) {
            // Get the largest image (last in the scaled array typically has highest resolution)
            ScaledImage largestImage = this.cover.getScaled().get(this.cover.getScaled().size() - 1);
            entity.setImage(largestImage.getUrl());
        }
        return entity;
    }
}
