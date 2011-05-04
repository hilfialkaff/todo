class RecvInvitationsController < ApplicationController
  def accept
    @user = User.find(params[:user_id])
    if @user.nil?
      error.add "User doesn't exist"
    end

    @invitation = @user.recv_invitations.find(params[:inv_id])

    @sender_inv = User.find_by_name(@invitation.from).sent_invitations
    @sender_inv.find_by_to(@user.name).update_attributes(:status => "Accepted")

    @group = Group.find_by_name(@invitation.group)
    @user.groups << @group

    @user.recv_invitations.delete(@invitation)
    redirect_to user_path(@user)
  end

  def reject
    @user = User.find(params[:user_id])
    if @user.nil?
      error.add "User doesn't exist"
    end

    @invitation = @user.recv_invitations.find(params[:inv_id])
    @sender_inv = User.find_by_name(@invitation.from).sent_invitations
    @sender_inv.find_by_to(@user.name).update_attributes(:status => "Rejected")

    @user.recv_invitations.delete(@invitation)
    redirect_to user_path(@user)
  end
end
