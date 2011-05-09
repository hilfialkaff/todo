class RecvInvitationsController < ApplicationController
  def accept
    @user = User.find_by_name(params[:user_name])
    if @user.nil?
      # TODO: @user.errors.add "User doesn't exist"

      respond_to do |format|
        format.html { redirect_to(users_url, :notice => 'fail') }
        format.xml  { render :head => :ok }
        format.json  { render :head => :ok }
      end
    end

    @invitation = @user.recv_invitations.find(params[:inv_id])

    @sender_inv = User.find_by_name(@invitation.sender).sent_invitations.find_by_recipient(params[:user_name])
    if @sender_inv.nil? == false:
      @sender_inv.update_attributes(:status => "Accepted")
    end

    @group = Group.find_by_name(@invitation.group)
    @user.groups << @group

    @user.recv_invitations.delete(@invitation)

    respond_to do |format|
      format.html { redirect_to(@user, :notice => 'success') }
      format.xml  { render :head => :ok }
      format.json  { render :head => :ok }
    end
  end

  def reject
    @user = User.find_by_name(params[:user_name])
    if @user.nil?
       # TODO: @user.errors.add "User doesn't exist"

      respond_to do |format|
        format.html { redirect_to(@user, :notice => 'fail') }
        format.xml  { render :head => :ok }
        format.json  { render :head => :ok }
      end
    end

    @invitation = @user.recv_invitations.find(params[:inv_id])

    @sender_inv = User.find_by_name(@invitation.sender).sent_invitations.find_by_recipient(params[:user_name])
    if @sender_inv.nil? == false:
      @sender_inv.update_attributes(:status => "Rejected")
    end

    @user.recv_invitations.delete(@invitation)

    respond_to do |format|
      format.html { redirect_to(@user, :notice => 'success') }
      format.xml  { render :head => :ok }
      format.json  { render :head => :Ok }
    end
  end

  def index
    @recv_invitations = User.find(params[:user_id]).recv_invitations.all
 
    respond_to do |format|
      format.xml { render :xml => @recv_invitations }
      format.json { render :json => @recv_invitations }
    end
  end
end
