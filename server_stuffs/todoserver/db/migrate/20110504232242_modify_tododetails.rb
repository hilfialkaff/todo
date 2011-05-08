class ModifyTododetails < ActiveRecord::Migration
  def self.up
    add_column :tododetails, :title, :string
    add_column :tododetails, :deadline, :string

    remove_column :tododetails, :todo
  end

  def self.down
    remove_column :tododetails, :title
    remove_column :tododetails, :deadline

    add_column :tododetails, :todo, :string
  end
end
