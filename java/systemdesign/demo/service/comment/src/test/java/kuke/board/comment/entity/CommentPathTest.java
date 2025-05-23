package kuke.board.comment.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CommentPathTest {

    @Test
    void createChildComponentTest(){

        //00000 생성
        createChildComponentTest(CommentPath.create(""), null, "00000");

        //00000 생성
        //00001 생성
        createChildComponentTest(CommentPath.create(""), "00000", "00001");

        //00000
        //      abcdz
        //          zzzzz
        //              zzzzz
        //      abce0   생성
        createChildComponentTest(CommentPath.create("0000z"), "0000zabcdzzzzzzzzzzz", "0000zabce0");
    }

    void createChildComponentTest(CommentPath commentPath, String descTopPath, String expectChildPath){
        CommentPath childCommentPath = commentPath.createChildCommentPath(descTopPath);
        assertThat(childCommentPath.getPath()).isEqualTo(expectChildPath);
    }

    @Test
    void createChildCommentPathIfMaxDepthTest(){
        assertThatThrownBy(() ->
                CommentPath.create("zzzzz".repeat(5)).createChildCommentPath(null))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void createChildCommentPathIfChunkOverflowTest(){
        CommentPath commentPath = CommentPath.create("");

        assertThatThrownBy(() ->
                commentPath.createChildCommentPath("zzzzz"))
                .isInstanceOf(IllegalStateException.class);
    }
}