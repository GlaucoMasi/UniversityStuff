CREATE VIEW ProgSedi(CodProg, CodSede) AS
    SELECT  P.CodProg, S.Sede
    FROM    Prog P, Sedi S
    WHERE   P.Citta = S.Citta

SELECT *
FROM    ProgSedi
WHERE   CodProg = 'P01'


-- Le viste possono essere utilizzate per: semplificare query complesse, evitare modifiche dello schema logico
-- e creare una visione personalizzata del DB per gli utenti
-- Oppure anche per controllare l'accesso ai dati sensibili

-- La sede che ha il massimo numero di impiegati
-- Senza viste:
SELECT      I.Sede
FROM        Imp I
GROUP BY    I.Sede
HAVING      COUNT(*) >= ALL (
    SELECT COUNT(*)
    FROM Imp I1
    GROUP BY I1.Sede
)

-- Con viste:
CREATE VIEW NumImp(Sede, Nimp) AS
    SELECT Sede, COUNT(*)
    FROM Imp
    GROUP BY Sede

SELECT Sede
FROM NumImp
WHERE Nimp = (
    SELECT MAX(NImp)
    FROM NumImp
)

-- Per ogni valore di stipendio medio, il numero di sedi che pagano tale stipendio
CREATE VIEW StipSedi(Sede, AvgStip) AS
    SELECT  Sede, AVG(Stipendio)
    FROM    Imp I
    GROUP BY Sede

SELECT  AvgStip, COUNT(*) AS NumSedi
FROM    StipSedi
GROUP BY AvgStip


-- Aggiornamenti su viste
-- Affinchè una vista sia aggiornabile, deve essere invertibile
-- Ci sono delle restrizioni, una vista non è aggiornabile se il suo blocco più esterno contiene:
-- 1. GROUP BY
-- 2. Funzioni aggregate
-- 3. DISTINCT
-- 4. JOIN

-- Viste equivalenti possono avere aggiornabilità diverse
CREATE VIEW ImpBO(CodImp, Nome, Sede, Ruolo, Stipendio) AS
    SELECT  I.*
    FROM    Imp I
    JOIN    Sedi S
    ON      (I.Sede = S.Sede)
    WHERE   S.Citta = 'Bologna'

CREATE VIEW ImpBO(CodImp, Nome, Sede, Ruolo, Stipendio) AS
    SELECT  I.*
    FROM    Imp I
    WHERE   I.Sede IN (
        SELECT S.Sede FROM Sedi S
        WHERE S.Citta = 'Bologna'
    )


-- Non ha senso inserire una tupla in una vista che però non la restituirebbe (inserire nella vista ImpBO un impiegato con sede a Roma)
-- Si può evitare che sia possibile con la clausola WITH CHECK OPTION
CREATE VIEW ImpBO(CodImp, Nome, Sede, Ruolo, Stipendio) AS
    SELECT  I.*
    FROM    Imp I
    JOIN    Sedi S
    ON      (I.Sede = S.Sede)
    WHERE   S.Citta = 'Bologna'
WITH CHECK OPTION
-- Se la vista è definita in termini di un'altra vista, con la clausola il check è per tutte le viste coinvolte
-- a meno che non si usi la clausola WITH LOCAL CHECK OPTION (di default WITH CASCADED CHECK OPTION)
-- La clausola LOCAL impedisce che il check vada automaticamente alle viste più esterne, ma se essere hanno la clausola il controllo viene effettuato comunque


-- Table Expressions: all'interno della clausola FROM si può definire una subquery che definisce dinamicamente una tabella derivata
-- Possono essere usate per query che richiedono più passi di aggregazione
-- Per ogni sede, lo stipendio massimo e quanti impiegati lo percepiscono
SELECT  SM.Sede, SM.MaxStip, COUNT(*) AS NumImpWMaxStip
FROM    Imp I, (
    SELECT Sede, MAX(Stipendio)
    FROM Imp
    GROUP BY Sede) AS SM(Sede, MaxStip)
WHERE   I.Sede = SM.Sede
AND     I.Stipendio = SM.MaxStip
GROUP BY SM.Sede, SM.MaxStip

-- Utilizzando la parola TABLE si può anche creare una table expression correlata ad una tabella esterna
-- Per ogni sede, la somma degli stipendi pagati agli analisti
SELECT S.Sede, Stip.TotStip
FROM Sedi S, TABLE(
    SELECT SUM(Stipendio)
    FROM Imp I
    WHERE I.Sede = S.Sede
    AND I.Ruolo = 'Analista'
) AS Stip(TotStip)