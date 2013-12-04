(ns sweng-hw6-grader.core
  (:use [compojure.core :only [defroutes GET]])
  (:require [ring.adapter.jetty :as jetty]
            [compojure.handler :as handler]))

(defroutes routes
  (GET "/" [] "<h2>SwEng 2013 HW6 Contest</h2><p>Coming soon...</p>"))

(def application (handler/site routes))

(defn start [port]
  (jetty/run-jetty application {:port port
                          :join? false}))
(defn -main []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))]
    (start port)))
