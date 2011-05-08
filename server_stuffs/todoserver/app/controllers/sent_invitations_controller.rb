class SentInvitationsController < ApplicationController
  def create
    @sender = User.find(params[:user_id])
    @sent_inv = params[:sent_invitations]

    @receiver = User.find_by_name(params[:recipient])

    @receiver.recv_invitations.create(:group => params[:group], :description => params[:description], :sender => @sender.name)
    @created_inv = @sender.sent_invitations.create(:group => params[:group], :description => params[:description], :recipient => @receiver.name, :status => "Pending" )

    respond_to do |format|
      format.html { redirect_to(@sender, :notice => 'Invitation was successfully created.') }
      format.xml  { render :xml => @created_inv, :status => :created }
      format.json  { render :json => @created_inv, :status => :created }

      if true == false:
        format.html { render :action => "new" }
        format.xml  { render :xml => @created_inv.errors, :status => :unprocessable_entity }
        format.json  { render :json => @created_inv.errors, :status => :unprocessable_entity }
      end
    end
  end

  def destroy
    @user = User.find(params[:user_id])
    @sent_inv = @user.sent_invitations.find(params[:id])

    @sent_inv.destroy

    respond_to do |format|
      format.html { redirect_to(@user) }
      format.xml  { head :ok }
      format.json { head :ok }
    end
  end

  def index
    @sent_invitations = User.find(params[:user_id]).sent_invitations.all

    respond_to do |format|
      format.xml { render :xml => @sent_invitations }
      format.json { render :json => @sent_invitations }
    end
  end
end
