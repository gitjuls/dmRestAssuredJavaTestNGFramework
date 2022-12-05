package com.google.developers.pojo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfile {
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("emailAddress")
    private String emailAddress;
    @JsonProperty("messagesTotal")
    private Integer messagesTotal;
    @JsonProperty("threadsTotal")
    private Integer threadsTotal;
    @JsonProperty("historyId")
    private String historyId;

}
