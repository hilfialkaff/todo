Todoserver::Application.routes.draw do
  resources :users do
    resources :groups do
      resources :tododetails do
      end
    end

    resources :sent_invitations do
    end

    resources :recv_invitations do
    end
  end

  resources :groups do
    resources :tododetails
  end

  get "users/:id/unsubscribe" => "users#unsubscribe"

  get "/users/:user_id/invitations/:inv_id/accept" => "recv_invitations#accept"
  get "/users/:user_id/invitations/:inv_id/reject" => "recv_invitations#reject"

#  scope :users do
#    get 'unsubscribe/:id' => 'users#unsubscribe', :as => :unsubscribe

#    get "/users/:user_id/invitations/:inv_id/accept" => "recv_invitations#accept", :as => :accept
#    get "/users/:user_id/invitations/:inv_id/reject" => "recv_invitations#reject", :as => :reject
#  end


  get "home/index"

  # You can have the root of your site routed with "root"
  # just remember to delete public/index.html.
  root :to => "home#index"

  # See how all your routes lay out with "rake routes"

end
