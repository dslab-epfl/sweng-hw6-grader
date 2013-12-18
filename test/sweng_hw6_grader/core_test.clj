(ns sweng-hw6-grader.core-test
  (:require [midje.sweet :refer :all]
            [clj-http.fake :as fake]
            [sweng-hw6-grader.test-utils :refer :all]
            [sweng-hw6-grader.core :refer :all]))

(with-fixtures
  (fact "It can display the homepage"
    (make-request "/homework6contest") => (contains {:body #"SwEng Homework 6 Contest"}))
  (fact "It displays a 404 for requests to inexisting files"
    (make-request "/foo") => (contains {:status 404})
    (make-request "/homework6contest/foo") => (contains {:status 404})))
