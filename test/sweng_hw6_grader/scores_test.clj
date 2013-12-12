(ns sweng-hw6-grader.scores-test
  (:require [midje.sweet :refer :all]
            [clj-http.fake :as fake]
            [sweng-hw6-grader.http-test :refer :all]
            [sweng-hw6-grader.scores :refer :all]))

(fake/with-fake-routes fake-routes
  (facts "About the scores"
    (fact "It can show the scores for the master project"
      (make-request "/homework6contest/sweng-2013-team-master") =>
        (contains {:body #"Your current score is 11 points."}))))
