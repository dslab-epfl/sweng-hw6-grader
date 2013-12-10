(ns sweng-hw6-grader.core
  (:use [compojure.core :only [defroutes GET]])
  (:require [ring.adapter.jetty :as jetty]
            [ring.util.response :as resp]
            [compojure.handler :as handler]))

; Some middleware to ignore trailing slashes in URLs

(defn with-uri-rewrite
  "Rewrites a request uri with the result of calling f with the
   request's original uri.  If f returns nil the handler is not called."
  [handler f]
  (fn [request]
    (let [uri (:uri request)
          rewrite (f uri)]
      (if rewrite
        (handler (assoc request :uri rewrite))
        nil))))

(defn- uri-snip-slash
  "Removes a trailing slash from all uris except \"/\"."
  [uri]
  (if (and (not (= "/" uri))
           (.endsWith uri "/"))
    (subs uri 0 (dec (count uri)))
    uri))

(defn ignore-trailing-slash
  "Makes routes match regardless of whether or not a uri ends in a slash."
  [handler]
  (with-uri-rewrite handler uri-snip-slash))

; The routes and app init logic

(defroutes routes
  (GET "/" [] (resp/redirect "/homework6contest"))
  (GET "/homework6contest" [] "<h2>SwEng 2013 HW6 Contest</h2><p>Coming soon...</p>"))

(def application
  (-> (handler/site routes)
      ignore-trailing-slash))

(defn start [port]
  (jetty/run-jetty application {:port port
                          :join? false}))
(defn -main []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))]
    (start port)))
