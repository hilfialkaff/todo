class CommentsController < ApplicationController

  # before_filter :authenticate, :only => :destroy

  # GET /comments
  # GET /comments.xml
  def index
    @comments = Comment.all
    
    respond_to do |format|
      format.html
      format.xml { render :xml => @comments }
    end
  end

  def create
    @post = Post.find(params[:post_id])
    @comment = @post.comments.create(params[:comment])
    redirect_to post_path(@post)
  end

  def destroy
    @post = Post.find(params[:post_id])
    @comment = @post.comments.find(params[:id])
    @comment.destroy
    redirect_to post_path(@post)
  end
end
