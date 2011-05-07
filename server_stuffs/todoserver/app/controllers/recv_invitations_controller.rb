class RecvInvitationsController < ApplicationController
  def accept
    @user = User.find_by_name(params[:user_name])
    if @user.nil?
      error.add "User doesn't exist"
    end

    @invitation = @user.recv_invitations.find(params[:inv_id])

    @sender_inv = User.find_by_name(@invitation.sender).sent_invitations
    @sender_inv.find_by_recipient(@user.name).update_attributes(:status => "Accepted")

    @group = Group.find_by_name(@invitation.group)
    @user.groups << @group

    @user.recv_invitations.delete(@invitation)
    redirect_to user_path(@user)
  end

  def reject
    @user = User.find_by_name(params[:user_name])
    if @user.nil?
      error.add "User doesn't exist"
    end

    @invitation = @user.recv_invitations.find(params[:inv_id])
    @sender_inv = User.find_by_name(@invitation.sender).sent_invitations
    @sender_inv.find_by_recipient(@user.name).update_attributes(:status => "Rejected")

    @user.recv_invitations.delete(@invitation)

    redirect_to user_path(@user)
  end

  def index
    @recv_invitations = User.find(params[:user_id]).recv_invitations.all
 
    respond_to do |format|
      format.xml { render :xml => @recv_invitations }
      format.json { render :json => @recv_invitations }
    end
  end
end
