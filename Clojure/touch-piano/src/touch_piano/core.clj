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


(defn chr->int [c]
  (-> (char c)
      (str)
      (Integer.)))

(ser/on-byte port-1 right-hand)
(def spec-notes ["d4" "f4" "a4" "c5" "d5" "e5" "f5"])

(defn right-hand
  ([input spec-notes]
   (let [index (chr->int input)
         notes ["c" "d" "e" "f" "g" "a" "b"]
         oktav 4]
     (((keyword (str (notes index) oktav)) piano))))
  ([input]
   (let [index (chr->int input)
         notes spec-notes]
     (((keyword (notes index)) piano)))))


(ser/on-byte port-1 left-hand) ;Not implemented, add global volatile that enters setting.



