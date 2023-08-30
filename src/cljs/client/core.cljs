(ns client.core
  (:require
   ["react-dom/client" :as rdom]
   ["react-markdown$default" :as ReactMarkdown]
   [client.components :refer [footer]]
   [helix.core :refer [$]]
   [helix.dom :as d]
   [helix.hooks :as hooks]
   [promesa.core :as p])
  (:require-macros
   [app.lib :as l]))

(l/defnc home
  []
  (let [[{:keys [posts]} set-state] (hooks/use-state {:posts [{:title "Woa there buddy"
                                                               :text "This sure is taking a while to load."
                                                               :date (.getTime (js/Date.))}]})]
    (p/let [_response (js/fetch "/api/posts/")
            response (.json _response)
            data (js->clj response
                          :keywordize-keys true)]
      (set-state assoc :posts (:posts data)))
    (d/div
     (d/div
      {:class-name
       "flex min-h-[calc(100vh-68px)] justify-center bg-[#fbf8ef] dark:bg-[#292b2e] py-3 px-6"}
      (d/div
       {:class-name
        "max-w-3xl"}
       (d/h1
        {:class-name
         "mt-10 text-3xl font-semibold text-[#3a81c3] capitalize lg:text-4xl dark:text-[#4f97d7]"}
        "Just The Tips")
       (for [{:keys [title text date]} posts]
         (d/div
          {:class-name
           "mt-8 lg:mx-6 lg:items-center"}
          (d/h2
           {:class-name
            "text-sm text-[#2d9574] uppercase"}
           title)
          (d/div
           {:class-name
            "mt-3 text-sm text-[#655370] dark:text-[#b2b2b2] md:text-sm"}
           ($ ReactMarkdown {:className "react-markdown"} text))
          (d/p
           {:class-name
            "text-sm text-[#a094a2] dark:text-[#686868]"}
           (.toString date))))))
     ($ footer))))

(defn ^:export init []
  (defonce root (rdom/createRoot (js/document.getElementById "app")))
  (.render root ($ home)))
