(ns sweng-hw6-grader.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.util.response :as resp]
            [compojure.core :as compojure]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [sweng-hw6-grader.views :as views]
            [sweng-hw6-grader.resources :as resources]
            [sweng-hw6-grader.scores :as scores]))

; Some middleware to ignore trailing slashes in URLs

(defn- with-uri-rewrite
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

(defn- ignore-trailing-slash
  "Makes routes match regardless of whether or not a uri ends in a slash."
  [handler]
  (with-uri-rewrite handler uri-snip-slash))

; Middleware to add content-type and encoding

(defn- with-content-type
  "Adds a default content type"
  [handler]
  (fn [request]
    (let [response (handler request)]
      (resp/content-type response "text/html; charset=utf-8"))))

; The routes and app init logic

(compojure/defroutes routes
  (compojure/GET "/" [] (resp/redirect "/homework6contest"))
  (compojure/GET "/homework6contest" [] (resources/resources))
  (compojure/GET "/homework6contest/about" [] (views/about))
  (compojure/GET "/homework6contest/:resource" [resource] (scores/score resource))
  (route/not-found (views/four-oh-four)))

(def application
  (-> (handler/site routes)
      ignore-trailing-slash
      with-content-type))

(defn start [port]
  (jetty/run-jetty application {:port port
                          :join? false}))
(defn -main []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8080"))]
    (start port)))
