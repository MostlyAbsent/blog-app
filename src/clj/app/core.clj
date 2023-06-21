(ns app.core
  (:require [ring.adapter.jetty :as ring-jetty]
            [reitit.ring :as ring]
            [ring.util.response :as r]
            [muuntaja.core :as m]
            [app.db :as db]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [clojure.java.io :as io])
  (:gen-class))

(defn index []
  (slurp (io/resource "public/index.html")))

(defn posts [_]
  (r/response {:posts (db/get-posts)}))

(def app
  (ring/ring-handler
   (ring/router
    ["/"
     ["api/"
      ["posts/" posts]]
     ["assets/*" (ring/create-resource-handler {:root "public/assets"})]
     ["" {:handler (fn [_] {:body (index) :status 200})}]]
    {:data {:muuntaja m/instance
            :middleware [muuntaja/format-middleware]}})))

(defn start []
  (ring-jetty/run-jetty #'app {:port 3000
                               :join? false}))

(defn -main []
  (start))
