class DropTododetails < ActiveRecord::Migration
  def self.up
    drop_table :tododetails
  end

  def self.down
    raise ActiveRecord::IrreversibleMigration
  end
end
