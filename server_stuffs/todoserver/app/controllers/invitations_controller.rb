class InvitationsController < ApplicationController
  def create
    @user = User.find(params[:user_id])
    @invitation = @user.invitations.create(params[:invitation])

    redirect_to user_path(@user)
  end
end
