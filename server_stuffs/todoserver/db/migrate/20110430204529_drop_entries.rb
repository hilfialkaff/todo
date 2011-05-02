class DropEntries < ActiveRecord::Migration
  def self.up
    drop_table :entries
  end

  def self.down
    raise ActiveRecord::IrreversibleMigration
  end
end
