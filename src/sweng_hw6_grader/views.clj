(ns sweng-hw6-grader.views
  (:require [hiccup.page :as hiccup]))


(defn layout [title body]
  (hiccup/html5
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
     (hiccup/include-css "//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css")
     [:title title]]
    [:body
     [:div.container
      [:h1 "SwEng 2013 Homework 6 Contest"]
      body
      (hiccup/include-js "https://code.jquery.com/jquery.js")
      (hiccup/include-js "//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js")]]))


(def issue-titles {:violations "Issues"
                   :blocker_violations "Blocker"
                   :critical_violations "Critical"
                   :major_violations "Major"
                   :minor_violations "Minor"
                   :info_violations "Info"
                   :false_positive_issues "False positives"})

(defn score-table [metrics initial final]
  [:table
   [:tr (map (fn [h] [:th h]) (concat [""] (map issue-titles metrics)))]
   [:tr [:th "Initial"]
        (map (fn [d] [:td d]) initial)]
   [:tr [:th "Final"]
        (map (fn [d] [:td d]) final)]])

(defn score-view [resource metrics initial final score]
  (layout (str "Scores for " resource)
          [:div
           [:h2 "Issues"]
           (score-table metrics initial final)
           [:h2 "Score"]
           [:p "Your current score is " (format "%.0f" score) " points."]]))


(defn resources [res]
  (layout "Homework 6 Contest"
          [:div
           [:p "Choose your project"]
           [:ul
            (map (fn [r] [:li [:a {:href (str "/homework6contest/" r)} r]]) res)]]))


(defn four-oh-four []
  {:status 404
   :body (layout "Not found..." [:p "Sorry, the thing you're looking for isn't here."])})
