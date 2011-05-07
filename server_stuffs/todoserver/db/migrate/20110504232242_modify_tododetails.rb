class ModifyTododetails < ActiveRecord::Migration
  def self.up
    add_column :tododetails, :title, :string
    add_column :tododetails, :deadline, :string

    remove_column :tododetails, :todo, :string    
  end

  def self.down
    remove_column :tododetails, :title, :string
    remove_column :tododetails, :deadline, :string

    add_column :tododetails, :todo, :string
  end
end
