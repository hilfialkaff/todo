class AddStatusToTodo < ActiveRecord::Migration
  def self.up
    add_column :todos, :status, :integer
  end

  def self.down
    remove_column :todos, :status
  end
end
