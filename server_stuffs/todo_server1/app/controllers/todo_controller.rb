class TodoController < ApplicationController
 def create
    @group = Group.find(params[:group_id])
    @group = @group.todos.create(params[:todo])
    redirect_to group_path(@group)
  end
end
