package com.solera.global.qa.template.rest.behavior.data.responsedata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoData {

    private int albumId;
    private int id;
    private String title;
    private String url;
    private String thumbnailUrl;
}