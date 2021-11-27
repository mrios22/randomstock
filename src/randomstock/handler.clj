(ns randomstock.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(def tickers
  (clojure.string/split-lines (slurp "./resources/tickers.txt")))

(def ticker-quantity
  (count tickers))

(defn get-random-ticker
  "Select a ticker at random from the list of tickers provided with the website."
  []
  (let [choice (take 1 (random-sample (/ ticker-quantity) tickers))]
    (if (and choice (< 0 (count choice)))
      choice
      (get-random-ticker))))


(defroutes app-routes
  (GET "/" [] (slurp "./resources/public/index.html"))
  (GET "/random-stock" [] (get-random-ticker))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
