package com.mountblue.blogpost.service;

import com.mountblue.blogpost.model.Comment;
import com.mountblue.blogpost.repository.CommentRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CommentService {
    private final int COMMENT_INDEX = 1;
    private final int COMMENT_NAME = 4;
    private final int COMMENT_EMAIL = 3;

    @Autowired
    CommentRepository commentRepository;

    public void saveComment(Comment commentModal) {
        commentModal.setCreatedAt(new Date());
        commentModal.setUpdatedAt(new Date());
        commentRepository.saveCommentData(commentModal);
    }

    public String retriveComments(String postId) {
        String comment = null;
        String id = null;
        List<Comment> list = commentRepository.retriveComments(postId);

        Iterator itr = list.iterator();

        while (itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            comment += "," + String.valueOf(obj[COMMENT_INDEX]);
            id += "," + String.valueOf(obj[0]);
        }

        return comment + "&" + id;
    }

    public Comment editComments(String postId, String commentId) {
        Comment comment = new Comment();
        List<Comment> list = commentRepository.getComments(postId, commentId);
        Iterator itr = list.iterator();

        while (itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            BigInteger id = (BigInteger) obj[0];
            comment.setId(Long.parseLong(String.valueOf(id)));
            comment.setComment((String) obj[1]);
            BigInteger posId = (BigInteger) obj[5];
            comment.setPostId(Long.parseLong(postId));

        }
        return comment;
    }

    public void deleteComments(String postId) {
        long id = Long.parseLong(postId);
        String query = "delete from Comment where post_id=" + postId;
        commentRepository.deleteCommentData(query);
    }

    public void updateComment(Comment postComment) {

        commentRepository.updateComment(postComment);

    }

    public Date findComment(String commentId) {

       return commentRepository.findComment(commentId);
    }
}
