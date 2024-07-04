package com.jsy.clonegram.dto;

import com.jsy.clonegram.dao.Comment;
import lombok.Data;


@Data
public class HomePostDto {
    private Long postId;
    private String creatorName;
    private String creatorPic;
    private String postPic;
    private String caption;
    private Long likeyCount;
    private Comment randComment;
}
