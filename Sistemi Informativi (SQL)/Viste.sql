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


-- Common Table Expressions: sono viste temporanee come le table expressions, ma possono essere usate come se fossero delle viste normali
WITH SediStip(Sede, TotStip) AS (
    SELECT  Sede, SUM(Stipendio)
    FROM    Imp
    GROUP BY Sede
)
SELECT  Sede
FROM    SediStip
WHERE   TotStip = (
    SELECT  MAX(TotStip)
    FROM    SediStip
)


-- Le Common Table Expressions possono essere ricorsive
-- Considerando la tabella Genitori(Figlio, Genitore), trovare tutti gli antenati di un certo individuo
-- Non si può esprimere in algebra relazionale, perchè non si sa a priori quante generazioni ci sono
-- Ad ogni iterazione, il DBMS aggiunge le tuple che risultano dal join tra Genitori e le sole tuple aggiunte ad Antenati al passo precedente
WITH Antenati(Persona, Avo) AS (
    (SELECT  Figlio, Genitore              -- Subquery base
    FROM    Genitori)     
    UNION ALL
    SELECT  G.Figlio, A.Avo
    FROM    Genitori G, Antenati A
    WHERE   G.Genitore = A.Persona         -- Subquery ricorsiva
)
SELECT  Avo
FROM    Antenati
WHERE   Persona = 'Anna'

-- Informazione sui percorsi: lunghezza
WITH Antenati(Persona, Avo, Lunghezza) AS (
    (SELECT  Figlio, Genitore, 1           -- Subquery base
    FROM    Genitori)     
    UNION ALL
    SELECT  G.Figlio, A.Avo, A.Lunghezza+1
    FROM    Genitori G, Antenati A
    WHERE   G.Genitore = A.Persona         -- Subquery ricorsiva
)
SELECT  *
FROM    Antenati
WHERE   Persona = 'Anna'

-- Informazione sui percorsi: costo
WITH Percorsi(Composto, Componente, Qty) AS (
    (SELECT Composto, Componente, Qta
    FROM   Parti)
    UNION ALL
    (SELECT H.Composto, P.SubParte, H.Qty * P.Qta
    FROM   Parti P, Percorsi H
    WHERE  H.Composto = P.Componente)
)
SELECT  Composto, Componente, SUM(Qty) AS QtaTot
FROM    Percorsi
GROUP BY Composto, Componente

-- Limitazioni sulla ricorsione
WITH Percorsi(Da, A, Lungh, TotKm) AS (
    (
        SELECT Da, A, 1, Km
        FROM Siti
    )UNION ALL(
        SELECT P.Da, S.A, P.Lungh+1, P.TotKm+S.Km
        FROM Siti S, Percorsi P
        WHERE P.A = S.From
        AND P.Da <> S.To            -- Evita l'inserimento di dati inutili
        AND P.Lungh+1 <= 3          -- Limitazione sulla lunghezza
        AND P.TotKm+S.Km <= 17      -- Limitazione sul costo
    )
)
SELECT *
FROM Percorsi