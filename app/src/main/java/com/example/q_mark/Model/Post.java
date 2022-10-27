package com.example.q_mark.Model;

public class Post {
    private String postID,postImage,postedDate,postDescription,postedBy,university;
    private long postedAt,postLike,commentCount;

    public Post() {
    }

    public Post(String postID, String postImage, String postedDate, String postDescription, long postedAt, String university) {
        this.postID = postID;
        this.postImage = postImage;
        this.postedDate = postedDate;
        this.postDescription = postDescription;
        this.postedAt = postedAt;
        this.university= university;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String postID) {
        this.university = university;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public long getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(long postedAt) {
        this.postedAt = postedAt;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }


    public long getPostLike() {
        return postLike;
    }

    public void setPostLike(long postLike) {
        this.postLike = postLike;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }
}
