(ns touch-piano.core
  (:require [serial-port :as ser]
            [overtone.core :refer :all]))

(boot-external-server)


;Since the freesound.org api wasn't working I created my own overtone instrument, using the sounds from MISStereoPiano
(defn created-piano []
  (let [notes ["a" "ab" "b" "bb" "c" "d" "e" "eb" "f" "g" "gb"]
        all-notes (for [number (range 1 7) note notes]
                    (str note number))
        path "W:/arduino/clojure/touch-piano/resources/piano/med-"
        file-ending ".wav"
        all {}]
    (apply merge
           (for [note notes number (range 1 7)]
             {(keyword (str note number)) (sample (str path note number file-ending))}))))

(def piano (created-piano))

(ser/list-ports)
(def port-1 (ser/open "COM3" 57600))
(def port-2 (ser/open "COM5" 57600))


(defn chr->int [c]
  (->
   (char c)
   (str)
   (Integer.)))


(defn parse-int [s]
   (Integer. (re-find  #"\d+" s )))

(ser/on-byte port-1 right-hand)
(def spec-notes ["d4" "f4" "a4" "c5" "d5" "e5" "f5"])



(defn notes-2 []
  ["c4" "d4" "e4" "f4" "g4" "a4" "b4" "c5" "d5" "e5"])
(defn notes []
  ["c3" "d3" "e3" "f3" "g3" "a3" "b3" "c4" "d4" "e4"])

(ser/on-byte port-2 left-hand)
(ser/on-byte port-1 right-hand)


(defn left-hand
  ([input]
   (let [inp (chr->int input)
         index inp;(mod inp 10)
        ; force (/ inp 10)
         notes (notes)]
     (((keyword (notes index)) piano) 1 0 0 1))))

(defn right-hand
  ([input]
   (let [inp (chr->int input)
         index inp;index (mod inp 10)
         ;force (/ inp 10)
         notes (notes-2)]
     (((keyword (notes index)) piano) 1 0 0 1))))

;{nil 1, :buf 3, :rate 1, :start-pos 0, :loop? 0, :amp 1, :pan 10, :out-bus 0


(def settings (atom {:setting-mode nil :notes (notes)}))
