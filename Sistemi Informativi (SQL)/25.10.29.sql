-- Esercizio 1
-- Q1) Determinare per ogni table (TYPE = ‘T’) , il numero di foreign key, ignorando quelle autoreferenziali (ed escludendo le tabelle con 0 foreign key)
-- e ordinare per valori decrescenti (a parità, per nome di schema e di tabella)
SELECT 	TABSCHEMA, TABNAME, PARENTS-SELFREFS AS NUM_FK
FROM	SYSCAT.TABLES
WHERE	TYPE = 'T'
AND		PARENTS-SELFREFS > 0
ORDER BY NUM_FK DESC, TABSCHEMA, TABNAME

-- Q2) Mostrare gli schemi con almeno 5 table o view, ordinando in senso decrescente per numero totale di oggetti
SELECT  TABSCHEMA, COUNT(TABNAME) AS NUM_OBJS
FROM	SYSCAT.TABLES
WHERE	TYPE IN ('T', 'V')
GROUP BY	TABSCHEMA
HAVING		COUNT(TABNAME) >= 5
ORDER BY	NUM_OBJS DESC

-- Q3) Per ogni vista di SYSCAT, determinare da quanti oggetti di ciascun tipo dipende
SELECT	TABNAME, BTYPE, COUNT(*) AS NUM_OBJS
FROM	SYSCAT.TABDEP
WHERE	TABSCHEMA = 'SYSCAT'
GROUP BY	TABNAME, BTYPE

-- Q4) Senza usare l’attributo TABLES.COLCOUNT, né viste, determinare la table (TYPE = ‘T’) con il maggior numero di colonne
WITH	TABLECOLUMNS (TABSCHEMA, TABNAME, NUM_COLS) AS (
	SELECT 	t.TABSCHEMA, t.TABNAME, COUNT(c.COLNAME)
	FROM	SYSCAT.TABLES t
	JOIN	SYSCAT.COLUMNS c
	ON		(t.TABSCHEMA, t.TABNAME) = (c.TABSCHEMA, c.TABNAME)
	WHERE	t.TYPE = 'T'
	GROUP BY	t.tabschema, t.TABNAME	
)
SELECT 	*
FROM	TABLECOLUMNS
WHERE	NUM_COLS = (
	SELECT 	MAX(NUM_COLS)
	FROM	TABLECOLUMNS
)

-- Q5) Per ogni tipo di dato (COLUMNS.TYPENAME), il n. di oggetti in cui quel tipo è il più usato
WITH
TYPECOUNT(TABSCHEMA, TABNAME, TYPENAME, CNT_TYPE) AS (
	SELECT 	TABSCHEMA, TABNAME, TYPENAME, COUNT(*)
	FROM	SYSCAT.COLUMNS
	GROUP BY TABSCHEMA, TABNAME, TYPENAME
),
TYPEWINS(TABSCHEMA, TABNAME, TYPENAME) AS (
	SELECT	TABSCHEMA, TABNAME, TYPENAME
	FROM 	TYPECOUNT t1
	WHERE 	t1.CNT_TYPE >= ALL (
		SELECT	t2.CNT_TYPE
		FROM	TYPECOUNT t2
		WHERE 	(t1.TABSCHEMA, t1.TABNAME) = (t2.TABSCHEMA, t2.TABNAME)
	)
)
SELECT	TYPENAME, COUNT(*) AS NUM_TABLES
FROM	TYPEWINS
GROUP BY TYPENAME
ORDER BY NUM_TABLES DESC

-- Q6) Determinare la table (TYPE = 'T') non definita in uno schema di sistema (cioè diverso da SYS...)
-- da cui sono (direttamente) dipendenti il maggior numero di viste
WITH VISTECOUNT(TABSCHEMA, TABNAME, NUM_VISTE) AS (
	SELECT 	BSCHEMA, BNAME, COUNT(TABNAME)
	FROM	SYSCAT.TABDEP
	WHERE	BTYPE = 'T'
	AND		BSCHEMA NOT LIKE 'SYS%'
	GROUP BY BSCHEMA, BNAME
)
SELECT	TABSCHEMA, TABNAME, NUM_VISTE
FROM	VISTECOUNT
WHERE	NUM_VISTE = (
	SELECT 	MAX(NUM_VISTE)
	FROM	VISTECOUNT
)

-- Q7) Determinare i tipi di dato che compaiono nella definizione di tutte le viste (TYPE = 'V') dello schema DB2INST1
SELECT	DISTINCT c1.TYPENAME
FROM	SYSCAT.COLUMNS c1
WHERE	NOT EXISTS (
	SELECT	*
	FROM	SYSCAT.TABLES t
	WHERE	t.TYPE = 'V'
	AND		t.TABSCHEMA = 'DB2INST1'
	AND		NOT EXISTS (
		SELECT	*
		FROM	SYSCAT.COLUMNS c2
		WHERE	(c2.TABSCHEMA, c2.TABNAME, c2.TYPENAME) = (t.TABSCHEMA, t.TABNAME, c1.TYPENAME)
	)
)

-- Q8) La coppia di nomi di table che compaiono più frequentemente insieme in uno stesso schema
WITH COUPLE_FREQ (TABNAME1, TABNAME2, FREQ) AS(
	SELECT 	t1.TABNAME, t2.TABNAME, COUNT(*)
	FROM	SYSCAT.TABLES t1, SYSCAT.TABLES t2
	WHERE	t1.TABSCHEMA = t2.TABSCHEMA
	AND		t1.TABNAME < t2.TABNAME
	AND		t1.TYPE = 'T'
	AND		t2.TYPE = 'T'
	GROUP BY	(t1.TABNAME, t2.TABNAME)
)
SELECT	*
FROM	COUPLE_FREQ
WHERE	FREQ = (
	SELECT 	MAX(FREQ)
	FROM	COUPLE_FREQ
)

-- Q9) Per ogni nome di table (TYPE = 'T'), quante volte è ripetuta su SIT_STUD, fornendo i timestamp di creazione minimo e massimo
-- e ordinando per valori decrescenti di ripetizione, ma solo per table ripetute almeno 4 volte
SELECT 	TABNAME, COUNT(*) AS NUM_RIPET, MIN(CREATE_TIME) AS MIN_TIME, MAX(CREATE_TIME) AS MAX_TIME
FROM	SYSCAT.TABLES
WHERE	TYPE = 'T'
GROUP BY	TABNAME
HAVING		COUNT(*) >= 4
ORDER BY	NUM_RIPET DESC


-- Esercizio 2
-- Q1) CAT_SAMENAME(TABNAME, SAMEBEFORE, SAMEAFTER)
-- per ogni table o view nel proprio schema mostra il numero di (altre) table o view con lo stesso nome nel DB SIT_STUD create prima (SAMEBEFORE) e dopo (SAMEAFTER).
CREATE VIEW CAT_SAMENAME(TABNAME, SAMEBEFORE, SAMEAFTER) AS
	WITH MINE AS (
		SELECT	a.TABNAME, a.CREATE_TIME
		FROM	SYSCAT.TABLES a
		WHERE	a.TABSCHEMA = CURRENT USER
		AND		a.TYPE IN ('T', 'V')
	)
	SELECT	m.TABNAME, BEFORE.NUM, AFTER.NUM
	FROM	MINE m
	LEFT JOIN (
		SELECT	m1.TABNAME, COUNT(*) AS NUM
		FROM	MINE m1, SYSCAT.TABLES t1
		WHERE	m1.TABNAME = t1.TABNAME
		AND		t1.TYPE IN ('T', 'V')
		AND		t1.TABSCHEMA <> CURRENT USER
		AND		m1.CREATE_TIME > t1.CREATE_TIME
		GROUP BY m1.TABNAME
	) BEFORE ON m.TABNAME = BEFORE.TABNAME
	LEFT JOIN (
		SELECT	m2.TABNAME, COUNT(*) AS NUM
		FROM	MINE m2, SYSCAT.TABLES t2
		WHERE	m2.TABNAME = t2.TABNAME
		AND		t2.TYPE IN ('T', 'V')
		AND		t2.TABSCHEMA <> CURRENT USER
		AND		m2.CREATE_TIME < t2.CREATE_TIME
		GROUP BY m2.TABNAME
	) AFTER ON m.TABNAME = AFTER.TABNAME


-- Q2) CAT_TABDIFF(MYTABNAME, YOURSCHEMA, DIFFCOLUMN, DIFF)
-- il catalogo, per ogni table o view del proprio schema e definita con lo stesso nome anche in altri schemi, mostra le eventuali differenze esistenti
-- sui nomi degli attributi definiti. Ogni tupla riporta:
-- 1. il nome di una table del proprio schema,
-- 2. il nome di un altro schema in cui si trova una table con lo stesso nome
-- 3. il nome di una colonna in cui le 2 table differiscono
-- 4. DIFF = '+' se la colonna è definita nell'altra table e non nella propria, DIFF = '-' altrimenti
CREATE VIEW CAT_TABDIFF(MYTABNAME, YOURSCHEMA, DIFFCOLUMN, DIFF) AS
		SELECT 	c1.TABNAME, t1.TABSCHEMA, c1.COLNAME, '-'
		FROM 	SYSCAT.COLUMNS c1, SYSCAT.TABLES t1
		WHERE	c1.TABSCHEMA = CURRENT USER
		AND		t1.TABSCHEMA <> CURRENT USER
		AND		c1.TABNAME = t1.TABNAME
		AND NOT EXISTS (
			SELECT 	*
			FROM	SYSCAT.COLUMNS c3
			WHERE	(c3.TABSCHEMA, c3.TABNAME) = (t1.TABSCHEMA, t1.TABNAME)
			AND		c3.COLNAME = c1.COLNAME
		)
	UNION ALL
		SELECT 	c2.TABNAME, c2.TABSCHEMA, c2.COLNAME, '+'
		FROM 	SYSCAT.COLUMNS c2, SYSCAT.TABLES t2
		WHERE	c2.TABSCHEMA <> CURRENT USER
		AND		t2.TABSCHEMA = CURRENT USER
		AND		c2.TABNAME = t2.TABNAME
		AND NOT EXISTS (
			SELECT 	*
			FROM	SYSCAT.COLUMNS c3
			WHERE	(c3.TABSCHEMA, c3.TABNAME) = (t2.TABSCHEMA, t2.TABNAME)
			AND		c3.COLNAME = c2.COLNAME
		)
