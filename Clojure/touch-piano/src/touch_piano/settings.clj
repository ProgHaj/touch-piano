(ns touch-piano.settings.clj)

(def settings (atom {:setting-mode nil :notes (notes)}
                    :decisions []))
;reset! swap!

;Load from file settings?

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

;;1
;1-9 edits the selected button.
;;; 1-2 lower/higher note
;;; 3-4 lower/higher octave
;;; 5 play current note
;;; 6-9 accept

;;2

;;3
;modify scales. Can choose between existing major and minor scales

;;4
;adds a second note to the button.
;works like ;;1. 5 will play current note + old.

;;7
;1-9 save to save-file-N
;Saves all settings to Nth file.

;;8
;1-9 loads save-file-N
;Loads all settings from Nth file.

;;9
;resets the profile to default.

;;RETURNS TO NORMAL WITH :settings-mode nil and :decisions []
