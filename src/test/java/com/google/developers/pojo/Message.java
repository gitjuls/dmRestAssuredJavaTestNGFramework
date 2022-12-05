package com.google.developers.pojo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message extends Payload{
    @JsonProperty("id")
    private String id;
    @JsonProperty("threadId")
    private String threadId;
    @JsonProperty("labelIds")
    private List<String> labelIds = new ArrayList<>();
    @JsonProperty("payload")
    private Payload payload;

}