(ns randomstock.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(def tickers
  (clojure.string/split-lines (slurp "./resources/tickers.txt")))

(def ticker-quantity
  (count tickers))

(defn get-single-random-ticker
  "Select a ticker at random from the list of tickers provided with the website."
  []
  (let [choice (take 1 (random-sample (/ ticker-quantity) tickers))]
    (if (and choice (< 0 (count choice)))
      choice
      (get-single-random-ticker))))

(defn get-random-tickers
  "Returns <quantity> number of unique tickers. Maxes out at 100 or the total number of tickers available, whichever one is smaller. Returns a comma-delimited string of tickers."
  [str-quantity]
  (try 
    (let [quantity (Integer/parseInt str-quantity)
          max-ticker-count (if (< 100 ticker-quantity)
                             100
                             ticker-quantity)]
      (if (> quantity max-ticker-count)
        (get-random-tickers max-ticker-count)
        (clojure.string/join ", " (take quantity (shuffle tickers)))))
    (catch java.lang.NumberFormatException e "Bad input, try an in integer in the text box.")))


(defroutes app-routes
  (GET "/" [] (slurp "./resources/public/index.html"))
  ;(GET "/random-stock" [] "AAPL")
  (GET "/random-stock/:quantity" [quantity] (get-random-tickers quantity))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
