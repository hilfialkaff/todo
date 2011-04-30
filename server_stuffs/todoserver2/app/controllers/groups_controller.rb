class GroupsController < ApplicationController
  def create
    @user = User.find(params[:user_id])
    @group = @user.groups.create(params[:group])
    redirect_to user_path(@user)
  end

  def destroy
    @user = User.find(params[:user_id])
    @group = @user.groups.find(params[:id])
    @group.destroy
    redirect_to user_path(@user)
  end

end
