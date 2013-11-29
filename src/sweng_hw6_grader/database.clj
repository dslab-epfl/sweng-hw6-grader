(ns sweng-hw6-grader.database
  (:use korma.db))

(defdb db (postgres {:db "swenghw6grader"}))
