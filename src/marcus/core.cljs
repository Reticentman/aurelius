(ns marcus.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [secretary.core :as s :include-macros true :refer [defroute]]
            [goog.events :as events]
            [schema.core :as schema])
  (:import goog.History
           goog.history.EventType))

(enable-console-print!)
(def ENTER_KEY 13)

;; =====================================================================
;; Routing

;; (s/set-config! :prefix "#")
;; (def history (History.))
;; (.setEnabled history true)

;; =====================================================================
;; Quote data handling and main app

(def data-schema
  "A schema for the validating the data content."
  {:chapter schema/Int
   :entry   schema/Int
   :en_US   schema/Str})

(def data
  [{:chapter 1 :entry 1 :en_US "text_a"}
   {:chapter 2 :entry 2 :en_US "text_b"}
   {:chapter 3 :entry 3 :en_US "text_c"}
   {:chapter 4 :entry 4 :en_US "text_d"}
   {:chapter 5 :entry 5 :en_US "text_e"}
   {:chapter 6 :entry 6 :en_US "text_f"}
   {:chapter 7 :entry 7 :en_US "text_g"}
   {:chapter 8 :entry 8 :en_US "text_h"}])

(defn get-data []
  "Gets the data and validates it with Schema."
  (schema/validate
   data-schema
   (rand-nth data)))

;; Define the app state after the data are curated.
(def app-state (atom (get-data)))

;; =====================================================================
;; The View Functions
;;
;; I break this up into separate functions because it will make it
;; easier to build future functionality, such as viewing the Greek text,
;; or viewing other translations and comments.

(defn entry-view [{:keys [entry] :as data}]
  (dom/span #js {:className "entry"} entry))

(defn chapter-view [{:keys [chapter] :as data}]
  (dom/span #js {:className "chapter"} chapter))

(defn blockquote-view [{:keys [text] :as data}]
  (dom/blockquote #js {:className "en_US"} text))

(defn marcus-view [{:keys [chapter entry en_US] :as data}]
  (reify
    om/IRender
    (render [this]
            (dom/div nil
                     (dom/header #js {:className "quote-header"}
                                 (dom/span #js {:className "chapter"} chapter)
                                 (dom/span #js {:className "entry"} entry))
                     (dom/blockquote #js {:className "en_US"} en_US)))))


(om/root marcus-view app-state
         {:target (. js/document (getElementById "marcus"))})

