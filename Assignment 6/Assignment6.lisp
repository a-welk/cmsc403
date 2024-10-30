;Alex Welk - CMSC403 Assignment 6 - 10/26/22
;Apologies if the formatting is messy, common lisp has managed to confuse me like no other language
(defun myList ()
	(4(7 22) "art" ("math" (8) 99) 100)' 
	)

(defun leapYear ()
	(labels
		(
			(
				nextYear
				(yearList year end)
				(cond
					(
						( > year end)
							yearList)
					(
						(and(= (mod year 100) 0) (/=(mod year 400) 0)
							(nextYear yearList (+ year 4) 2018)
							)
						(nextYear(nconc yearList (list year)) (+year 4) 2018)
						)
					)
				))
		(nextYear '() 1800 2018)
		)
	)

;function to find list average
(defun avg (aList)
(cond
	((not aList)
		NIL
		)
	( T
		(progn
			;helper function to help resursively keep track of the total sum and size
			(defun helperFunc(partList thisSum size)
				(cond
					(
						(not partList)
							(/ thisSum size)
						)
					( T
						(helperFunc(rest partList)(+ thiSum (first partList)) (+ size 1))
					))
				)
			helperFunc(aList 0 0)
			)

		)
	)
	)

;function confirming that the passed parameter is of dataType
(defun isType(dataType)
	#'(lamba (x) (typep x dataType))
	)

;function that creates the union of two given lists
defun(union- (11 12)
(cond
(
	(not 12)
	11
	)
(
	(not(member (first 12) 11))
	(append (union- 11 (rest 12)) (list(first 12)))
	)

	(T
		(union- 11 (rest 12)))
	)
	)

(defun clean (function aList)
(cond
	((not aList)
		NIL)

	(
		(funcall function (first aList))
		(cons(first aList) (clean function (rest aList)))
		)

	(T
		(clean function (rest aList)))
	)
	)

(defun taxCalculator (limit rate values)
	(mapcar #'(lambda (x)
			(cond
				(
					(> x limit)
					(* x rate))
				(T 
					x)
				)
		)
		values
	)
	)

(defmacro threeWayBranch (x y z)
(labels
((executeList
	(aList)
	(cond
		(
			(not aList)
			NIL)
		(T
			(progn
				(eval(first aList))
				(executeList (rest aList))
				))
		)
	))
(cond
	((< x y) (executeList(first z)))
	((< x y ) (executeList(second z)))
	(T 
		(executeList(third z)))
	)
	)
	)