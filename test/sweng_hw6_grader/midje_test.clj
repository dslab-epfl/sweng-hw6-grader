(ns sweng-hw6-grader.midje-test
   (:require [midje.sweet :refer :all]))

; See https://github.com/marick/Midje/issues/257

(defmacro with-fixtures [ message & body ]
  `(with-state-changes [(before :facts (println ~message))]
    ~@body))

(facts "about my fixtures"
  (with-fixtures "Hi from inside facts"
    (fact "they should work" true => truthy)
    (fact "they should work" false => falsey)))

(with-fixtures "Hi from root"
  (fact "they should work" true => truthy)
  (fact "they should work" false => falsey))

(facts "about my fixtures"
  (with-state-changes [(before :facts (println "Hi from with-state-changes"))]
    (fact "they should work" true => truthy)
    (fact "they should work" false => falsey)))

