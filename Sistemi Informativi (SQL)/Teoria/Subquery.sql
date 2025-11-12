SELECT  Codice
FROM    Impiegati
WHERE   SEDE <> 'S01'
AND     (Ruolo, Stipendio) IN(
  SELECT  Ruolo, Stipendio
  FROM    Impiegati
  WHERE   SEDE = 'S01'
)


-- Subquery scalari
-- Si possono usare operatori di confronto dato che la subquery restituisce non più di una tupla
-- Spesso si può sfruttare la presenza di vincoli per garantire che una query sia scalare
SELECT  Codice
FROM    Impiegati
WHERE   Stipendio = (
  SELECT  MIN(Stipendio)
  FROM    Impiegati
)


-- Subquery nel Caso Generale
-- La forma "= ANY" equivale a "IN"
SELECT  Responsabile
FROM    Sedi
WHERE   Sede <operatore> ANY (
  SELECT  Sede
  FROM    Impiegati
  WHERE   Stipendio > 1000
)

SELECT  Responsabile
FROM    Sedi
WHERE   Sede <operatore> ALL (
  SELECT  Sede
  FROM    Impiegati
  WHERE   Stipendio > 1000
)


-- Quantificatore Esistenziale
-- Utile se la subquery è correlata, ovvero fa riferimento ad alias definiti da un blocco esterno
-- Il predicato EXISTS è vero se la subquery restituisce almeno una tupla, NOT EXISTS se non ne restituisce nessuna
SELECT  Sede
FROM    Sedi S
WHERE   EXISTS (
  SELECT *
  FROM    Impiegati
  WHERE   Ruolo = 'Programmatore'
  AND     Sede = S.Sede
)

-- Spesso ci si può ricondurre ad una query piatta
SELECT  DISTINCT Sede
FROM    Sedi S, Impiegati I
WHERE   I.Ruolo = 'Programmatore'
AND     S.Sede = I.Sede


-- Divisione Relazione
-- Invece di trovare "le sedi in cui sono presenti tutti i ruoli"
-- Spesso è più comodo trovare "le sedi in cui non esiste un ruolo che non è presente"
SELECT  Sede
FROM    Sedi S
WHERE   NOT EXISTS (
  SELECT  Ruolo
  FROM    Impiegati I1
  WHERE   NOT EXISTS (
    SELECT  (Sede, Ruolo)
    FROM    Impiegati I2
    WHERE   I2.Sede = S.Sede
    AND     I2.Ruolo = I1.Ruolo
  )
)


-- Subquery per Aggiornamento dei Dati
DELETE  FROM    Impiegati
WHERE   Sede IN (
  SELECT  Sede
  FROM    Sedi
  WHERE   Citta = 'Bologna'
)

UPDATE  Impiegati
SET     Stipendio = 1.1*Stipendio
WHERE   Sede IN (
  SELECT  S.Sede
  FROM    Sedi S, Progetti P
  WHERE   S.Citta = P.Citta
  AND     P.CodiceProgetto = 'P02'
)