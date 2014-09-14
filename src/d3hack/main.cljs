(ns d3hack.main
  (:require-macros [cljs.core.async.macros :refer (go)])
  (:require [d3hack.http :refer (json-get)]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)

(defn thing
  [cursor owner]
  (reify
    om/IDidMount
    (did-mount [_]
      (go
        (let [response (<! (json-get "http://localhost:8000/ch.json"))
              json (js/JSON.parse (:body response))]
          (om/update! cursor :data json))))
    om/IRenderState
    (render-state [_ _]
      (if-let [data (:data cursor)]
        (let [path-fn (-> (js/d3.geo.path) (.projection nil))
              country (-> data .-objects .-country)
              cantons (-> data .-objects .-cantons)
              municps (-> data .-objects .-municipalities)]
          (dom/svg
           #js {:width 960 :height 500}
           (dom/path #js {:className "country"
                          :d (path-fn
                              (-> js/topojson (.feature data country)))})
           (dom/path #js {:className "canton-boundaries"
                          :d (path-fn
                              (-> js/topojson
                                  (.mesh data cantons (fn [a b] (not= a b)))))})
           (dom/path #js {:className "municipality-boundaries"
                          :d (path-fn
                              (-> js/topojson
                                  (.mesh data municps (fn [a b] (not= a b)))))})))
        (dom/div #js {} "Found nothing")))))

(om/root thing {} {:target (js/document.getElementById "thing")})
