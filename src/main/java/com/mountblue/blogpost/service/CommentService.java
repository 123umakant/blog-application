package com.mountblue.blogpost.service;

import com.mountblue.blogpost.dto.CommentDto;
import com.mountblue.blogpost.model.Comment;
import com.mountblue.blogpost.repository.CommentRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CommentService {
    private final int COMMENT_ID = 0;
    private final int COMMENT = 1;
    private final int COMMENT_POSTID = 5;

    @Autowired
    CommentRepository commentRepository;

    public void createComment(CommentDto commentDto) {

        Comment comment = new Comment();
        comment.setPostId(commentDto.getPostId());
        comment.setComment(commentDto.getComment());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setCreatedAt(new Date());
        comment.setUpdatedAt(new Date());
        commentRepository.saveCommentData(comment);
    }

    public List<CommentDto> retrieveComments(long postId) {
           List<CommentDto> dto =new ArrayList<>();
        List<Comment> comments= commentRepository.retriveComments(postId);
        Iterator<Comment> itr = comments.iterator();
        while (itr.hasNext()){
            Comment comment = itr.next();
           CommentDto commentDto = new CommentDto();
           commentDto.setId(comment.getId());
           commentDto.setComment(comment.getComment());
           commentDto.setEmail(comment.getEmail());
           commentDto.setName(comment.getName());
           commentDto.setPostId(comment.getPostId());
           dto.add(commentDto);
        }
           return dto;

    }

    public Comment editComments(String postId, String commentId) {
        Comment comment = new Comment();
        List<Comment> list = commentRepository.getComments(postId, commentId);
        Iterator itr = list.iterator();

        while (itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            BigInteger id = (BigInteger) obj[COMMENT_ID];
            comment.setId(Long.parseLong(String.valueOf(id)));
            comment.setComment((String) obj[COMMENT]);
            BigInteger posId = (BigInteger) obj[COMMENT_POSTID];
            comment.setPostId(Long.parseLong(postId));

        }
        return comment;
    }

    public int deleteComment(CommentDto commentDto) {
            Comment comment = new Comment();
            if (commentDto.getId()<1){
                return 0;
            }
       return commentRepository.deleteComment(commentDto);
    }

    public int updateComment(CommentDto postCommentDto) {

        if (postCommentDto.getEmail() ==null && postCommentDto.getId()>0){
            return 0;
        }

        Comment postComment = new Comment();
        postComment.setId(postCommentDto.getId());
        if(postCommentDto.getComment()!=null)
        postComment.setComment(postCommentDto.getComment());
        if(postCommentDto.getName()!=null)
        postComment.setName(postCommentDto.getName());
        postComment.setEmail(postCommentDto.getEmail());
        postComment.setUpdatedAt(new Date());
        postComment.setPostId(postCommentDto.getPostId());

       return commentRepository.updateComment(postComment);
    }

}
