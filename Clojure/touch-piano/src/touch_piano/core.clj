(ns touch-piano.core
  (:require [serial-port :as ser]
            [overtone.core :refer :all]
            ))

(boot-external-server)
;(require '[touch-piano.settings :as settings])
(use '[touch-piano.settings])

;Since the freesound.org api wasn't working I created my own overtone instrument, using the sounds from MISStereoPiano
(defn created-piano []
  (let [notes ["a" "ab" "b" "bb" "c" "d" "e" "eb" "f" "g" "gb"]
        all-notes (for [number (range 1 7) note notes]
                    (str note number))
        path "resources/piano/med-"
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

;(ser/on-byte port-1 right-hand)
;(def spec-notes ["d4" "f4" "a4" "c5" "d5" "e5" "f5"])


(comment
  (defn notes-2 []
    ["c4" "d4" "e4" "gb4" "g4" "a4" "b4" "c5" "d5" "e5"])
  (defn notes []
    ["d2" "g2" "a2" "c3" "d3" "e3" "g3" "a3" "e4" "f4"]))


(defn play-multiple [coll]
  (loop [coll coll]
    (if (empty? coll)
      nil
      (do
        (((keyword (first coll)) piano))
        (recur (rest coll))))))

;{nil 0, :buf 15, :rate 0, :start-pos 0, :loop? 0, :amp 0, :pan 0, :out-bus 0}

(defn play-chord [note]
  (let [start note
        chord [start
               (number->note (+ (note->number start) 4))
               (number->note (+ (note->number start) 7))]]
    (play-multiple chord)))

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

(ser/on-byte port-2 left-hand)
(ser/on-byte port-1 right-hand)


(def settings (atom {:setting-mode nil :notes (notes)}))
