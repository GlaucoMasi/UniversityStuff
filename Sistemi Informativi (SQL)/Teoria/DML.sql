-- Selezionando da più tabelle senza specificare il tipo di join si ottiene un prodotto cartesiano
-- La keyword DISTINCT elimina i duplicati nei risultati di una query
-- Si posso ottenere risultati di espressioni come output
-- Senza specificare il WHERE -> equivalente a CROSS JOIN
-- Specificando il predicato di JOIN con WHERE -> equivalente a INNER JOIN
SELECT  DISTINCT    A1, 
	(A2 + A3) AS Somma,
	CAST(A4 AS Decimal(4,2))/3 AS Media,
	A5 CONCAT ' ' CONCAT A6 AS NomeCognome
FROM    R1 E, R2 F, R3 G            -- Pseudonomini per le tabelle, indispensabili in caso di self-join
WHERE   A1 = 10                     -- Predicati di JOIN
	AND A2 LIKE '%abc_'             -- % = stringa arbitraria, _ = singolo carattere arbitrario
	AND A3 BETWEEN 5 AND 15
	AND A4 IN (1,3,5,7,9)
	AND A5 IS NOT NULL
ORDER BY A1 DESC, Somma ASC         -- Ordinamento discendente per A1, ascendente per Somma in caso di parità


-- Tipi di Join
-- JOIN o INNER JOIN - Restituisce solo le righe con corrispondenza in entrambe le tabelle
SELECT a.column, b.column
FROM tableA a
INNER JOIN tableB b
	ON a.id = b.id;

-- CROSS JOIN - Restituisce il prodotto cartesiano di entrambe le tabelle, equivalente a non specificare il tipo di join
SELECT a.column, b.column
FROM tableA a
CROSS JOIN tableB b

-- LEFT JOIN - Restituisce tutte le righe dalla tabella di sinistra e le righe corrispondenti dalla tabella di destra
SELECT a.column, b.column
FROM tableA a
LEFT JOIN tableB b
	ON a.id = b.id;

-- RIGHT JOIN - Restituisce tutte le righe dalla tabella di destra e le righe corrispondenti dalla tabella di sinistra
SELECT a.column, b.column
FROM tableA a
RIGHT JOIN tableB b
	ON a.id = b.id;

-- FULL JOIN - Restituisce tutte le righe quando c'è una corrispondenza in una delle due tabelle
SELECT a.column, b.column
FROM tableA a
FULL JOIN tableB b
	ON a.id = b.id;

-- SELF JOIN - Restituisce righe da una tabella unite a se stessa
SELECT a.column, b.column
FROM tableA a
JOIN tableA b
	ON a.id = b.related_id;


-- Operatori Insiemistici
/*
	UNION - Unisce i risultati di due query eliminando i duplicati
	UNION ALL - Unisce i risultati di due query mantenendo i duplicati
	INTERSECT - Restituisce solo le righe comuni a entrambe le query
	INTERSECT ALL - Restituisce tutte le righe comuni a entrambe le query, mantenendo i duplicati
	EXCEPT - Restituisce le righe della prima query che non sono presenti nella seconda
	EXPECT ALL - Restituisce tutte le righe della prima query che non sono presenti nella seconda, mantenendo i duplicati
*/


-- Aggiornamento dei Dati - Gli attributi non inclusi assumono valore NULL o DEFAULT
-- INSERT
INSERT INTO Studenti (Matricola, CF, Cognome, DataNascita, Email)
VALUES  ('A1234', 'RSSMRA85M01H501Z', 'Rossi', '1985-01-01', 'luca@gmail.com'),
		('B5678', 'VRDLGI90B02F205X', 'Verdi', '1990-02-02', 'marco@gmail.com');

INSERT INTO StudentiSenzaEmail
SELECT  Matricola, Cognome, Nome
FROM    Studenti
WHERE   Email IS NULL;

-- DELETE
DELETE FROM Studenti
WHERE   Matricola = 'B5678';

-- UPDATE
UPDATE  Studenti
SET     Docente = 'Bianchi'
WHERE   Matricola = 'A1234';


-- Politiche di Reazione
-- Per tutelare l'integrità referenziale tra tabelle collegate da vincoli di chiave esterna in caso di operazioni di DELETE o UPDATE
-- Quindi si definiscono nei vincoli di chiave esterna, dopo il REFERENCES
-- Non specificando nessuna politica di reazione, l'operazione che violerebbe l'integrità referenziale viene bloccata
/*
	ON DELETE CASCADE - Elimina le righe figlie quando la riga padre viene eliminata
	ON DELETE SET NULL - Imposta a NULL le chiavi esterne delle righe figlie quando la riga padre viene eliminata
	ON DELETE SET DEFAULT - Imposta al valore di default le chiavi esterne delle righe figlie quando la riga padre viene eliminata
	ON DELETE NO ACTION - Non consente l'eliminazione della riga padre se esistono righe figlie collegate (comportamento predefinito)
	ON UPDATE CASCADE - Aggiorna le chiavi esterne delle righe figlie quando la chiave primaria della riga padre viene aggiornata
	ON UPDATE SET NULL - Imposta a NULL le chiavi esterne delle righe figlie quando la chiave primaria della riga padre viene aggiornata
	ON UPDATE SET DEFAULT - Imposta al valore di default le chiavi esterne delle righe figlie quando la chiave primaria della riga padre viene aggiornata
	ON UPDATE NO ACTION - Non consente l'aggiornamento della chiave primaria della riga padre se esistono righe figlie collegate (comportamento predefinito)
*/