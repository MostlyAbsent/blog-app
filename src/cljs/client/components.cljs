(ns client.components
  (:require
   ["lucide-react" :refer [Github]]
   [helix.core :refer [$]]
   [helix.dom :as d])
  (:require-macros
   [app.lib :as l]))

(l/defnc footer []
  (d/footer
   {:class-name
    "flex bottom-0 left-0 z-20 w-full p-4 bg-[#fbf8ef] border-t border-[#b3b9be] shadow md:flex md:items-center md:justify-between md:p-6 dark:bg-[#292b2e] dark:border-[#5d4d7a]"}
   (d/div
    {:class-name
     "grid grid-cols-2 gap-8 sm:gap-6 sm:grid-cols-3"}
    (d/div
     {:class-name
      "flex mt-4 space-x-6 sm:justify-center sm:mt-0"}
     (d/a
      {:href
       "https://github.com/MostlyAbsent"
       :target
       "_blank"}
      ($ Github
         {:stroke
          "#2d9574"}))
     (d/a
      {:href
       "https://github.com/MostlyAbsent"
       :target
       "_blank"
       :class-name
       "text-[#655370] sm:text-center dark:text-[#b2b2b2]"}
      "MostlyAbsent")))
   (d/span
    {:class-name
     "text-sm text-[#655370] sm:text-center dark:text-[#b2b2b2]"}
    "© 2023 Jacob Doran")))
