class SentInvitationsController < ApplicationController
  def create
    @sender = User.find(params[:user_id])
    @sent_inv = params[:sent_invitations]

    @receiver = User.find_by_name(params[:sent_invitation][:recipient])

    @receiver.recv_invitations.create(:group => params[:sent_invitation][:group], :description => params[:sent_invitation][:description], :sender => @sender.name)
    @sender.sent_invitations.create(:group => params[:sent_invitation][:group], :description => params[:sent_invitation][:description], :recipient => @receiver.name, :status => "Pending" )

    redirect_to user_path(@sender)
  end

  def destroy
    @user = User.find(params[:user_id])
    @sent_inv = @user.sent_invitations.find(params[:id])
    @sent_inv.destroy

    redirect_to user_path(@user)
  end

  def index
    @sent_invitations = User.find(params[:user_id]).sent_invitations.all

    respond_to do |format|
      format.xml { render :xml => @sent_invitations }
      format.json { render :json => @sent_invitations }
    end
  end
end
