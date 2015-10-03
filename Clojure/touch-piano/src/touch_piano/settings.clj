(ns touch-piano.settings.clj)

(def decisions (atom []));

(def settings (atom {:setting-mode nil :notes (notes)}
                    :decisions decisions))
;reset! swap!

(defn change-settings [input]
  (if (:setting-mode setting)
    nil
    :activated))

;Load from file settings?
(defn decide [input]
  (cond (= 0 (count decisions)) (menu-1 input)
        (= 1 (count decisions)) ((:decisions 0) input)
        (= 2 (count decisions)) ((symbol (str (@decisions 0) "-last")) input)))
;gulp and spit for saving, ez pz


;Index 0 = activate settings, deactivate settings.
;aborts decisions.

;index 1-9 = first press = what to do,
;1 modify button
;2 
;3 modify scale
;4 add note to button 
;5 
;6 
;7 save profile
;8 load profile
;9 reset profile
(defn menu-1 [input]
  (if (= 0 input)
    (change-settings)
    (swap! decisions #(into % [(symbol (str "menu-1-" input))]))))

;;Smartare att bara ha en decision 2 och spara värdet istället. Samma på alla utom de sista... kom ihåg alternativ 1 och 2 för sista steget.
(defn menu-1-1 [input]
  (if (= 0 input)
    (change-settings)
    (swap! decisions conj input)))

;;1
;1-9 edits the selected button.
;;; 1-2 lower/higher note
;;; 3-4 lower/higher octave
;;; 5 play current note
;;; 6-9 accept
(defn menu-1-1-last [input choice]
  (if (= 0 input)
    (change-settings)
    (quote fixfixifix)))




(defn menu-1-2 [input]
  (let chosen = )
  (cond (= 0 input) ()
        (= 1 input)
        (= 2 input)
        (= 3 input)
        (= 4 input)
        (= 5 input)
        (>= 6 input)))
;;2

;;3
;modify scales. Can choose between existing major and minor scales

;;4
;adds a second note to the button.
;works like ;;1. 5 will play current note + old.

;;7
;1-9 save to save-file-N
;Saves all settings to Nth file.
(def settings-load (spit (str "./settings-" (last (:decisions settings)))))

;;8
;1-9 loads save-file-N
;Loads all settings from Nth file.
(def settings-load (gulp "./settings-" (last (:decisions settings))))

;;9
;resets the profile to default.

;;RETURNS TO NORMAL WITH :settings-mode nil and :decisions []
