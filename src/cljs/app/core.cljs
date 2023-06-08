(ns app.core
  (:require
   ["react-dom/client" :as rdom]
   ["react-markdown$default" :as ReactMarkdown]
   [helix.core :refer [$]]
   [helix.dom :as d]
   [helix.hooks :as hooks]
   [promesa.core :as p])
  (:require-macros
   [app.lib :as l]))

(l/defnc app
  []
  (let [[{:keys [posts]} set-state] (hooks/use-state {:posts []})]
    (p/let [_response (js/fetch "/api/posts/")
            response (.json _response)
            data (js->clj response
                          :keywordize-keys true)]
      (set-state assoc :posts (:posts data)))
    (d/body
     {:class-name
      "bg-white dark:bg-gray-900 min-h-screen"}
     (d/section
      {:class-name
         "grid grid-cols-3"}
      (d/div
       {:class-name
        "container px-6 py-10 mx-auto center col-start-2 col-end-2"}
       (d/h1
        {:class-name
         "text-3xl font-semibold text-gray-800 capitalize lg:text-4xl dark:text-white"}
        "Just The Tip"
        (for [{:keys [title text date]} posts]
          (d/div
           {:class-name
            "mt-8 lg:-mx-6 lg:items-center"}
           (d/h2
            {:class-name
             "text-sm text-blue-500 uppercase"}
            title)
           (d/p
            {:class-name
             "mt-3 text-sm text-gray-500 dark:text-gray-300 md:text-sm"}
            ($ ReactMarkdown text))
           (d/p
            {:class-name
             "text-sm text-gray-500 dark:text-gray-400"}
            (.toString date))))))))))

(defn ^:export init []
  (defonce root (rdom/createRoot (js/document.getElementById "app")))
  (.render root ($ app)))
