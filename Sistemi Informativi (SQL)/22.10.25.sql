-- Q1) Il numero dei dipartimenti con almeno 7 dipendenti
SELECT 	COUNT(DEPTNO) AS NUM_DIP_GE_7_EMP	
FROM 	DEPARTMENT d 
WHERE	7 <= (
	SELECT	COUNT(e.EMPNO)
	FROM 	EMPLOYEE e
	WHERE 	e.WORKDEPT = d.DEPTNO
)

-- Q2) I dati dei dipendenti che lavorano in un dipartimento con almeno 7 dipendenti
SELECT	*
FROM 	EMPLOYEE e 
WHERE 	e.WORKDEPT IN (
	SELECT 	DEPTNO
	FROM 	DEPARTMENT d 
	WHERE	7 <= (
		SELECT	COUNT(e.EMPNO)
		FROM 	EMPLOYEE e
		WHERE 	e.WORKDEPT = d.DEPTNO
	)
)
ORDER BY e.EMPNO ASC

-- Q3) I dati del dipartimento con il maggior numero di dipendenti
SELECT 	d.*
FROM	DEPARTMENT d
JOIN (
	SELECT 	e.WORKDEPT, COUNT(e.EMPNO) AS NUM_EMP
	FROM 	EMPLOYEE e
	GROUP BY 	e.WORKDEPT
) AS SUBQUERY
ON	d.DEPTNO = SUBQUERY.WORKDEPT
WHERE	SUBQUERY.NUM_EMP >= ALL (
	SELECT 	COUNT(EMPNO) AS NUM_EMP
	FROM	EMPLOYEE
	GROUP BY 	WORKDEPT
)

-- Q4) Il nome delle regioni e il totale delle vendite per ogni regione con un totale di vendite maggiore di 30, ordinando per totale vendite decrescente
SELECT	s.REGION, SUM(s.SALES) AS TOT_SALES
FROM	SALES s
GROUP BY 	s.REGION
HAVING		SUM(s.SALES) > 30
ORDER BY 	SUM(s.SALES) DESC

-- Q5) Lo stipendio medio dei dipendenti che non sono manager di nessun dipartimento
SELECT 	CAST(AVG(e.SALARY*1.0) AS DECIMAL(10, 2)) AS AVG_SALARY
FROM 	EMPLOYEE e
WHERE 	e.EMPNO NOT IN (
	SELECT	d.MGRNO
	FROM 	DEPARTMENT d 
	WHERE 	d.MGRNO IS NOT NULL
)

-- Q6) I dipartimenti che non hanno dipendenti il cui cognome inizia per ‘L’
SELECT 	d.*
FROM	DEPARTMENT d
WHERE 	d.DEPTNO NOT IN (
	SELECT 	d.DEPTNO 
	FROM	DEPARTMENT d 
	JOIN 	EMPLOYEE e
	ON		d.DEPTNO = e.WORKDEPT
	WHERE 	e.LASTNAME LIKE 'L%'
)

-- Q7) I dipartimenti e il rispettivo massimo stipendio per tutti i dipartimenti aventi un salario medio minore del salario medio
-- calcolato considerando i dipendenti di tutti gli altri dipartimenti
SELECT	e.WORKDEPT, MAX(e.SALARY) AS MAX_SALARY
FROM 	EMPLOYEE e
GROUP BY 	e.WORKDEPT
HAVING	AVG(e.SALARY*1.0) < (
	SELECT	AVG(e1.SALARY*1.0)
	FROM 	EMPLOYEE e1
	WHERE 	e1.WORKDEPT != e.WORKDEPT 
)

-- Q8) Per ogni dipartimento determinare lo stipendio medio per ogni lavoro per il quale il livello di educazione medio
-- è maggiore di quello dei dipendenti dello stesso dipartimento che fanno un lavoro differente
SELECT	e.WORKDEPT, e.JOB, CAST(AVG(e.SALARY*1.0) AS DECIMAL(10, 2)) AS AVG_SALARY
FROM 	EMPLOYEE e
GROUP BY 	e.WORKDEPT, e.JOB 
HAVING 		AVG(e.EDLEVEL*1.0) > (
	SELECT 	AVG(e1.EDLEVEL*1.0)
	FROM	EMPLOYEE e1
	WHERE 	e1.JOB != e.JOB
	AND 	e1.WORKDEPT = e.WORKDEPT
)

-- Q9) Lo stipendio medio dei dipendenti che non sono addetti alle vendite
SELECT	CAST(AVG(e.SALARY*1.0) AS DECIMAL(10, 2)) AS AVG_SALARY
FROM 	EMPLOYEE e
WHERE 	e.JOB != 'SALESREP'

-- Q10) Per ogni regione, i dati del dipendente che ha il maggior numero di vendite (SUM(SALES)) in quella regione
SELECT	DISTINCT s.REGION, e.*
FROM 	EMPLOYEE e 
JOIN	SALES s
ON		e.LASTNAME = s.SALES_PERSON
WHERE (
	SELECT 	SUM(s1.SALES)
	FROM 	SALES s1
	WHERE 	s1.REGION = s.REGION
	GROUP BY 	s1.SALES_PERSON
	HAVING 	s1.SALES_PERSON = s.SALES_PERSON
) >= ALL (
	SELECT 	SUM(s1.SALES)
	FROM 	SALES s1
	WHERE 	s1.REGION = s.REGION
	GROUP BY 	s1.SALES_PERSON
	HAVING 	s1.SALES_PERSON != s.SALES_PERSON
)

-- Q11) I codici dei dipendenti che svolgono un’attività per la quale ogni tupla di EMPPROJACT riguarda un periodo minore di 200 giorni
SELECT 	DISTINCT e.EMPNO 
FROM	EMPPROJACT e
WHERE 	e.ACTNO IN (
	SELECT 	e1.ACTNO
	FROM	EMPPROJACT e1
	GROUP BY	e1.ACTNO
	HAVING	 	MAX(DAYS(e1.EMENDATE)-DAYS(e1.EMSTDATE)) < 200
)

-- Q12) Le attività, e il codice del relativo dipartimento, svolte da dipendenti di un solo dipartimento
SELECT 	DISTINCT prog.ACTNO, empl.WORKDEPT 
FROM	EMPPROJACT prog
JOIN	EMPLOYEE empl
ON		prog.EMPNO = empl.EMPNO
WHERE 	NOT EXISTS (
	SELECT 	prog1.ACTNO
	FROM	EMPPROJACT prog1
	JOIN	EMPLOYEE empl1
	ON		prog1.EMPNO = empl1.EMPNO
	AND 	empl1.WORKDEPT != empl.WORKDEPT
	AND 	prog1.ACTNO = prog.ACTNO
)
ORDER BY 	ACTNO ASC, WORKDEPT ASC
