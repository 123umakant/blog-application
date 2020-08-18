package com.mountblue.blogpost.service;

import com.mountblue.blogpost.model.Comment;
import com.mountblue.blogpost.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public void saveComment(Comment commentModal) {

        commentModal.setCreatedAt(new Date());
        commentModal.setUpdatedAt(new Date());

        commentRepository.saveCommentData(commentModal);

    }

    public String retriveComments(String postId) {
       String comment=null;
        List<Comment> list= commentRepository.retriveComments(postId);

        Iterator itr = list.iterator();
        while(itr.hasNext()){
            Object[] obj = (Object[]) itr.next();
            //now you have one array of Object for each row
             comment +=","+ String.valueOf(obj[1]); // don't know the type of column CLIENT assuming String

        }
       return comment;
    }
}
