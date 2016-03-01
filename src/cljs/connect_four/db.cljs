(ns connect-four.db
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [schema.core :as s]
            [matchbox.reagent :as mr]
            [matchbox.core :as m]))


(def firebase-uri "https://connect-four-cljs.firebaseio.com")

(def rooms (m/connect firebase-uri :rooms))

(s/defschema GithubAuthObj
  {
   :provider s/Str
   :uid s/Str})

(s/defschema GithubProfile
  {
   :avatar_url s/Str
   :bio (s/maybe s/Str)
   :blog (s/maybe  s/Str)
   :company (s/maybe  s/Str)
   :created_at s/Str
   :email s/Str
   :events_url s/Str
   :followers s/Int
   :followers_url s/Str
   :following s/Int
   :following_url s/Str
   :gists_url s/Str
   :gravatar_id s/Str
   :hireable s/Bool
   :html_url s/Str
   :id s/Int
   :location (s/maybe  s/Str)
   :login s/Str
   :name s/Str
   :organizations_url s/Str
   :public_gists s/Int
   :public_repos s/Int
   :received_events_url s/Str
   :repos_url s/Str
   :site_admin s/Bool
   :starred_url s/Str
   :subscriptions_url s/Str
   :type s/Str
   :updated_at s/Str
   :url s/Str
   (s/optional-key  :subscriptoins_url) (s/maybe s/Str)
   })

(s/defschema GithubUserInfo
  {
   :accessToken s/Str
   :cachedUserProfile GithubProfile
   :displayName s/Str
   :email s/Str
   :id s/Str
   :profileImageURL s/Str
   :username s/Str})

(s/defschema GithubAuth
  {
   :auth GithubAuthObj
   :expires s/Int
   :github GithubUserInfo
   :provider s/Str
   :token s/Str
   :uid   s/Str})

(s/defschema Slot
  (s/enum :empty :red :blue))

(s/defschema Board
  [[Slot]])

(s/defschema GameState
  {:turn s/Int
   :error s/Str
   :id   s/Str
   :player (s/enum :red :blue)
   (s/optional-key :winner) (s/enum :red :blue)
   :board Board})

(s/defschema Room
  {:name s/Str
   :id   s/Str
   :game GameState
   (s/optional-key  :players) [s/Str]})

(s/defschema RoomsState
  {s/Str Room})

(s/defschema AppState
  {(s/optional-key  :game) GameState
   :active-panel (s/enum :rooms-panel :game-panel
                         :home-panel :about-panel)

   (s/optional-key :auth) GithubAuth
   (s/optional-key  :game-id) s/Str
   (s/optional-key  :rooms) RoomsState
   :user-id s/Str})

(s/defn a-row [] :- [Slot]
  (into [] (take 7 (repeat :empty))))

(s/defn a-board [] :- Board
  (into [] (take 6 (repeat (a-row )))))

(s/defn empty-game [id :- s/Str] :- GameState
  {:player :blue
   :turn 0
   :id id
   :error ""
   :board (a-board)})

(s/def default-db :- AppState
  {:active-panel :home-panel
   :user-id "Test"})
