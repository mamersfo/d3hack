(ns d3hack.http
  (:require [cljs-http.client :as client]
            [cljs-http.core :as http]))

(defn wrap-request
  [request]
  (-> request
      client/wrap-query-params
      client/wrap-method
      client/wrap-url))

(def json-request (wrap-request http/request))

(defn json-get
  [url & [req]]
  (json-request
   (merge req {:method :get :url url})))
