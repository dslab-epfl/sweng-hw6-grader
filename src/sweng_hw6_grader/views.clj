(ns sweng-hw6-grader.views
  (:require [hiccup.page :as hiccup]))


(defn layout [title body]
  (hiccup/html5
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
     (hiccup/include-css "//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css")
     (hiccup/include-css "/homework6contest/public/css/style.css")
     [:title title]]
    [:body
     [:div.container
     [:div {:class "navbar navbar-default" :role "navigation"}
      [:div.container
       [:div.navbar-header
        [:button {:type "button" :class "navbar-toggle" :data-toggle "collapse" :data-target ".navbar-collapse"}
         [:span.sr-only "Toggle navigation"]
         [:span.icon-bar]
         [:span.icon-bar]
         [:span.icon-bar]]
        [:a.navbar-brand {:href "/homework6contest"} "SwEng Homework 6 Contest"]]
       [:div {:class "collapse navbar-collapse"}
        [:ul {:class "nav navbar-nav"}
         [:li.active [:a {:href "/homework6contest"} "Home"]]
         [:li [:a {:href "/homework6contest/about"} "About"]]]]]]
     [:div.container
      [:h1 "SwEng 2013 Homework 6 Contest"]
      body
      (hiccup/include-js "https://code.jquery.com/jquery.js")
      (hiccup/include-js "//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js")]]]))


(def issue-titles {:violations "Issues"
                   :blocker_violations "Blocker"
                   :critical_violations "Critical"
                   :major_violations "Major"
                   :minor_violations "Minor"
                   :info_violations "Info"
                   :false_positive_issues "False positives"})

(defn score-table [metrics initial final]
  [:table.scores
   [:tr (map (fn [h] [:th h]) (concat [""] (map issue-titles metrics)))]
   [:tr [:th "Initial"]
        (map (fn [d] [:td d]) initial)]
   [:tr [:th "Final"]
        (map (fn [d] [:td d]) final)]])

(defn score-view [resource sonarqube-score issues-score]
  (layout (str "Scores for " resource)
          [:div sonarqube-score issues-score]))

(defn sonarqube-score-view [resource metrics initial final score]
  [:div
   [:h2 "SonarQube Issues"]
   (score-table metrics initial final)
   [:h3 "Score"]
   [:p "Your current score for the SonarQube assignment is " (format "%.0f" score) " points."]])


(defn resources [res]
  (layout "Homework 6 Contest"
          [:div
           [:p "Choose your project"]
           [:ul
            (map (fn [r] [:li [:a {:href (str "/homework6contest/" r)} r]]) res)]]))


(defn about []
  (layout "Homework 6 Contest: About"
          [:div
           [:h2 "About"]
           [:p "A grading script for EPFL's SwEng Course, 2013 edition."]
           [:p "Copyright &copy; 2013 Jonas Wagner"]
           [:p "This script is released under the GNU Affero General Public License"]
           [:p "See " [:a {:href "https://github.com/dslab-epfl/sweng-hw6-grader"} "the source on GitHub"]]]))


(defn four-oh-four []
  {:status 404
   :body (layout "Not found..." [:p "Sorry, the thing you're looking for isn't here."])})
