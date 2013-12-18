(ns user
  (:require [clojure.string :as string]
            [clojure.pprint :refer [pprint]]
            [clojure.repl :refer :all]
            [midje.repl :refer :all]
            [clojure.tools.namespace.repl :refer (refresh refresh-all)]))

(autotest)
