class RemoveDeliveredFromTododetails < ActiveRecord::Migration
  def self.up
    remove_column :tododetails, :delivered
  end

  def self.down
    add_column :tododetails, :delivered, :string
  end
end
