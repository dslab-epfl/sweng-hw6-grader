(ns sweng-hw6-grader.scores
  (:require [clojure.string :as string]
            [sweng-hw6-grader.views :as views]
            [sweng-hw6-grader.tidbits :as tidbits]))

(defn- get-timemachine [metrics resource]
  (let [metrics-str (string/join "," (map name metrics))
        query (format "/timemachine?metrics=%s&resource=%s" metrics-str resource)
        response (tidbits/sg query)]
    (-> response :body first :cells)))

(defn- initial-issues [cells]
  (:v (first cells)))

(defn- final-issues [cells]
  (:v (last cells)))

(defn- compute-score [metrics initial final]
  (let [blocker-issues (nth final (.indexOf metrics :blocker_violations))
        critical-issues (nth final (.indexOf metrics :critical_violations))
        initial-issues (nth initial (.indexOf metrics :violations))
        final-issues (nth final (.indexOf metrics :violations))
        blocker-score (max 0 (- 10 (* 10 blocker-issues)))
        critical-score (max 0 (- 20 (* 2 critical-issues)))
        improvement (/ (* 100.0 (- initial-issues final-issues)) initial-issues)
        improvement-score (min 20.0 (* (/ 2.0 3.0) improvement))
        score (+ blocker-score critical-score improvement-score)]
    score))

(defn- sonarqube-score [resource]
  (let [metrics [:violations :blocker_violations :critical_violations
                 :major_violations :minor_violations :info_violations
                 :false_positive_issues]
        cells (get-timemachine metrics resource)
        initial (initial-issues cells)
        final   (final-issues cells)
        score   (compute-score metrics initial final)]
    (views/sonarqube-score-view resource metrics initial final score)))

(defn- issues-score [resource]
  [:h2 "Your GitHub issues will appear here shortly"])

(defn score [resource]
  (try
    (views/score-view resource
                      (sonarqube-score resource)
                      (issues-score resource))
    (catch Exception e (views/four-oh-four))))

