class AddPriorityToTododetails < ActiveRecord::Migration
  def self.up
    add_column :tododetails, :priority, :string
  end

  def self.down
    remove_column :tododetails, :priority
  end
end
