class SentInvitationsController < ApplicationController
  def create
    @sender = User.find(params[:user_id])
    @sent_inv = params[:sent_invitations]

    @receiver = User.find_by_name(params[:sent_invitation][:to])

    @receiver.recv_invitations.create(:group => params[:sent_invitation][:group], :description => params[:sent_invitation][:description], :from => @sender.name)
    @sender.sent_invitations.create(:group => params[:sent_invitation][:group], :description => params[:sent_invitation][:description], :to => @receiver.name, :status => "Pending" )

    redirect_to user_path(@sender)
  end

  def destroy
    @user = User.find(params[:user_id])
    @sent_inv = @user.sent_invitations.find(params[:id])
    @sent_inv.destroy

    redirect_to user_path(@user)
  end

end
