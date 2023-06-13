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

(l/defnc footer []
  (d/footer
   {:class-name
    "flex bottom-0 left-0 z-20 w-full p-4 bg-[#fbf8ef] border-t border-[#b3b9be] shadow md:flex md:items-center md:justify-between md:p-6 dark:bg-[#292b2e] dark:border-[#5d4d7a]"}
   (d/span
    {:class-name
     "text-sm text-[#655370] sm:text-center dark:text-[#b2b2b2]"}
    "© 2023 Jacob Doran")))

(l/defnc app
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
        "Just The Tips"
        (for [{:keys [title text date]} posts]
          (d/div
           {:class-name
            "mt-8 lg:mx-6 lg:items-center"}
           (d/h2
            {:class-name
             "text-sm text-[#2d9574] uppercase"}
            title)
           (d/p
            {:class-name
             "mt-3 text-sm text-[#655370] dark:text-[#b2b2b2] md:text-sm"}
            ($ ReactMarkdown text))
           (d/p
            {:class-name
             "text-sm text-[#a094a2] dark:text-[#686868]"}
            (.toString date)))))))
     ($ footer))))

(defn ^:export init []
  (defonce root (rdom/createRoot (js/document.getElementById "app")))
  (.render root ($ app)))
