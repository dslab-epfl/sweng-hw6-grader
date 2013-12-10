(ns sweng-hw6-grader.resources
  (:require [sweng-hw6-grader.views :as views]
            [sweng-hw6-grader.tidbits :as tidbits]))

(defn resources []
  (views/resources (tidbits/get-sonar-resources)))
