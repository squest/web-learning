(ns ring-alfa.core
  (:require
    [immutant.web :as web]
    [reitit.ring :as ring]
    [reitit.core :as reitit]
    [ring.middleware.defaults :refer :all]
    [ring.middleware.params :refer :all]
    [immutant.util :as log]))

(defn config []
  {:port 4000
   :path "/"
   :host "localhost"})

(defn handler [_]
  {:status 200
   :body "Hello World!"})

(defn routes []
  (reitit/router
    [["/" {:get handler}]
     ["/ping" {:get (fn [_] {:status 200 :body "pong"})}]]))

(defonce system (atom nil))

(defn server-setup []
  (let [site-config (assoc-in site-defaults [:security :anti-forgery] false)]
    (-> (wrap-params (routes))
        (wrap-defaults site-config))))

(defn server-bare []
  (web/run (ring/ring-handler (routes)) (config)))

(defn server-start []
  (reset! system (web/run (server-setup) (config))))

(defn server-stop []
  (web/stop @system))








