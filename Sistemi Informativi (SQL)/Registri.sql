-- Uno special register è un'area di memoria che DB2 usa per memorizzare informazioni utilizzabili dalle istruzioni
-- Alcuni registri notevoli sono:
-- 1. CURRENT USER
-- 2. CURRENT DATE
-- 3. CURRENT TIME
-- 4. CURRENT TIMESTAMP
-- 5. SESSION SCHEMA

-- Si può usare l'istruzione VALUES per visualizzare il contenuto di uno special register
VALUES CURRENT USER;

VALUES (CURRENT DATE, HOUR(CURRENT TIME));

-- Coalesce è una funzione che restituisce il primo valore non nullo tra quelli passati come argomenti
VALUES (COALESECE(30, 20), COALESCE(NULL, 25));
-- => (30, 25)