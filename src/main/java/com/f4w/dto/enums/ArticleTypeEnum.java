package com.f4w.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author admin
 */

@Getter
@ToString
@RequiredArgsConstructor
public enum ArticleTypeEnum {
    VIDEO(0, "视频"),
    ARTICLE(1,"文章"),
    TOP_VIDEO(2, "头条视频"),
    TOP_ARTICLE(3, "头条文章"),
    MINIAPP_VIDEO(4, "小程序视频"),
    TOP_MINIAPP_VIDEO(5, "小程序头条视频");

    private final Integer code;
    private final String description;
}
