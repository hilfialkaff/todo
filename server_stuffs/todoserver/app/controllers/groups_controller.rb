class GroupsController < ApplicationController
  # GET /groups
  # GET /groups.xml
  def index
    if params[:user_id].nil? == false:
      @groups = User.find(params[:user_id]).groups

      respond_to do |format|
        format.html # index.html.erb
        format.xml  { render :xml => @groups }
        format.json  { render :json => @groups }
      end
    else
      @groups = Group.all

      respond_to do |format|
        format.html # index.html.erb
        format.xml  { render :xml => @groups }
      end
    end
  end

  # GET /groups/1
  # GET /groups/1.xml
  def show
    @group = Group.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @group }
      format.json { render :json => @group } 
    end
  end

  # GET /groups/new
  # GET /groups/new.xml
  def new
    @group = Group.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @group }
      format.json { render :json => @group } 
    end
  end

  # GET /groups/1/edit
  def edit
    @group = Group.find(params[:id])
  end

  # POST /groups
  # POST /groups.xml
  def create
    @user = User.find(params[:user_id])
    @group = @user.groups.new(params[:group])

    respond_to do |format|
      if @group.save
        format.html { redirect_to(@user, :notice => 'Group was successfully created.') }
        format.xml  { render :xml => @group, :status => :created, :location => @group }
        format.json  { render :json => @group, :status => :created, :location => @group }

      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @group.errors, :status => :unprocessable_entity }
        format.json  { render :json => @group.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /groups/1
  # PUT /groups/1.xml
  def update
    @group = Group.find(params[:id])

    respond_to do |format|
      if @group.update_attributes(params[:group])
        format.html { redirect_to(@group, :notice => 'Group was successfully updated.') }
        format.xml  { head :ok }
        format.json { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @group.errors, :status => :unprocessable_entity }
        format.json { render :json => @group.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /groups/1
  # DELETE /groups/1.xml
  def destroy
    @group = Group.find(params[:id])
    @group.destroy

    respond_to do |format|
      format.html { redirect_to(groups_url) }
      format.xml  { head :ok }
      format.json { head :ok }
    end
  end
end
