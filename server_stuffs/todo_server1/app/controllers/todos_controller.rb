class TodosController < ApplicationController
 def create
    @group = Group.find(params[:group_id])
    @group = @group.todos.create(params[:todos])
    redirect_to group_path(@group)
  end
end
