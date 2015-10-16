(ns touch-piano.settings)
(require '[clojure.string :as str])

(def decisions (atom []))
(def notes (atom {}))
(def settings (atom {:setting-mode nil :notes notes
                     :decisions decisions}))

(def note-number {"c" 0
                  "db" 1
                  "d" 2
                  "eb" 3
                  "e" 4
                  "f" 5
                  "gb" 6
                  "g" 7
                  "ab" 8
                  "a" 9
                  "bb" 10
                  "b" 11})
(def number-note ["c" "db" "d" "eb" "e" "f" "gb" "g" "ab" "a" "bb" "b"])

(defn note->number [note]
  (let [note (name note)]
    (+ (* (Integer. (str (.charAt note (dec (.length note))))) 12)
       (note-number
        (if (= 3 (.length note))
          (subs note 0 2)
          (subs note 0 1))))))

(defn number->note [number]
  (keyword (str (number-note (mod number 12)) (int (/ number 12)))))



(defn load-notes [file]
  (reset! notes (map #(hash-map (keyword (str %2)) %1) (str/split (slurp file) #"\s") (range 7))))
;;"./src/touch_piano/notes.settings1"

(defn change-settings []
  (if (:setting-mode @settings)
    (do (reset! decisions [])
        (swap! settings assoc :setting-mode nil))
    (swap! settings assoc :setting-mode :activated)))


;slurp and spit for saving, ez pz


;Index 0 = activate settings, deactivate settings.
;aborts decisions.


(defn first-menu [input]
  (if (= 0 input)
    (change-settings)
    (swap! decisions #(into % [input]))))

;;Smartare att bara ha en decision 2 och spara värdet istället. Samma på alla utom de sista... kom ihåg alternativ 1 och 2 för sista steget.
(defn second-menu [input]
  (if (= 0 input)
    (change-settings)
    (swap! decisions conj input)))


;index 1-9 = first press = what to do,
;1 modify button
;2 modify scale
;3 add note to button
;4 record?
;5 change-profile->previous
;6 change-profile->next
;7 save profile
;8 load profile
;9 reset profile


(defn lower-note [button & rest]
  (swap! notes assoc (keyword button) (if rest
                                        (notes button))))
;;FIXA OVAN, Ändra från sparade A4 osv till nummer! Låt olika noter vara representerade av nummer.
(defn raise-note [button & rest]
  2)
(defn play-note [button]
  3)
(defn save-note [button]
  4)


;;1
;1-9 edits the selected button.
;;; 1-2 lower/higher note
;;; 3-4 lower/higher octave
;;; 5 play current note
;;; 6-9 accept
(defn third-menu-1
  "Modify button"
  [input choice]
  (do
    (change-settings)
    (let [button choice]
      (cond (= 1 input) (lower-note button)
            (= 2 input) (raise-note button)
            (= 3 input) (lower-note button 8)
            (= 4 input) (raise-note button 8)
            (= 5 input) (play-note button)
            (>= 6 input) (save-note button)))))



(comment
  (defn third-menu-2
  ""
  (if (=))))


(defn third-menu-3 [input]
  (let [chosen =] )
  (cond (= 0 input) ()
        (= 1 input) ()
        (= 2 input) ()
        (= 3 input) ()
        (= 4 input) ()
        (= 5 input) ()
        (>= 6 input) ()))
;;2

;;3
;modify scales. Can choose between existing major and minor scales

;;4
;adds a second note to the button.
;works like ;;1. 5 will play current note + old.

;;7
;1-9 save to save-file-N
;Saves all settings to Nth file.
(defn settings-load []
  (spit (str "./settings-" (last (:decisions settings)))))

;;8
;1-9 loads save-file-N
;Loads all settings from Nth file.
(defn settings-load []
  (slurp "./settings-" (last (:decisions settings))))

;;9
;resets the profile to default.

;;RETURNS TO NORMAL WITH :settings-mode nil and :decisions []

;Load from file settings?
(defn decide [input]
  (cond (= 0 (count decisions)) (first-menu input)
        (= 1 (count decisions)) (second-menu input)
        (= 2 (count decisions)) ((symbol (str "third-menu-"(@decisions 0))) input (@decisions 1))))
