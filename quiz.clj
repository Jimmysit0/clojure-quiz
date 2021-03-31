;; ns --- the namespace!
(ns quiz
  (:import (java.time Duration Instant)))

;; questions
(def quiz-data [{:question "What is the capital of France?"
                 :options #{"New york", "London", "Rio De Janeiro", "Paris"}
                 :correct-answer "Paris"}

                {:question "What is the answer to the meaning of life, the universe, and everything?"
                 :options #{"42", "79", "86", "1"}
                 :correct-answer "42"}

                {:question "How much is is 1+1?"
                 :options #{"27", "21000000", "2", "17"}
                 :correct-answer "2"}
                
                {:question "Which is the best operating system?"
                 :options #{"Windows", "Linux + OS", "macOS", "MeowgleOS"}
                 :correct-answer "Linux + OS"}

                {:question "What is the last letter of the alphabet?"
                 :options #{"x" "y", "z", "v"}
                 :correct-answer "z"}])

;; calculate the amount of correct answers
(def correct-answers (atom 0))

;; calculate the start time
(def start-time (Instant/now))

;; print the questions
(loop [questions (drop (rand-int (dec (count quiz-data))) quiz-data)]
  (if (seq questions)
    (let [the-question (first questions)]
      (println (:question the-question))

      ;; print the options
      (doseq [option (:options the-question)]
        (println "-" option))

      ;; get the user answer
      (let [user-answer (do (print "Enter answer: " ) (flush) (read-line))]

        ;; a bunch of conditions
        ;; see if the user answer is a valid option
        (cond
          (not (contains? (:options the-question) user-answer))
          (do (println "Select a valid answer you dummy")
              (recur questions))

          ;; see if the answer is right or not
          (not (= user-answer (:correct-answer the-question)))
          (do (println "You're wrong")
              (recur (rest questions)))

          (= user-answer (:correct-answer the-question))
          (do (swap! correct-answers inc)
              (println "You're right!")
              (recur (rest questions))))))))

;; calculate if the user passed or not
(let [passed (> (deref correct-answers) 2)]

  (if passed
    (println "You passed the quiz, congratulations!")
    (println "You failed the quiz, sorry mate!"))

  ;; final output
  (println "Okay, here are the results: You had" (deref correct-answers) "correct answers, you took" (.toSeconds (Duration/between start-time (Instant/now))) "seconds"))
