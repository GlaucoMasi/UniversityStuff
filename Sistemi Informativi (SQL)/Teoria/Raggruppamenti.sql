-- Funzioni aggregate - MIN, MAX, SUM, AVG, COUNT
-- Tutte le funzioni ignorano i valori NULL, compreso Count(<attributo>)
-- Fa eccezione Count(*) che conta tutte le righe
SELECT 
  MIN(Stipendio) AS Min_Stipendio,
  MAX(Stipendio) AS Max_Stipendio,
  SUM(DISTINCT Stipendio) AS Stipendio_Totale,                    -- Somma dei valori distinti
  AVG(CAST(Stipendio AS Decimal(6,2))) AS Stipendio_Medio1,       -- Conversione a Decimal per evitare arrotondamenti
  AVG(Stipendio/1.0) AS Stipendio_Medio2,                         -- Equivalente alla conversione a Decimal
  COUNT(Stipendio) AS Numero_Dipendenti
FROM    Dipendenti
WHERE   Reparto = 'Vendite';


-- Formazione di Raggruppamenti
SELECT
  Sede,
  COUNT(*) AS Numero_Dipendenti,
  AVG(Stipendio) AS Stipendio_Medio
FROM        Dipendenti
WHERE       Ruolo = 'Programmatore'
GROUP BY    Sede

-- Se si usa una espressione nella clausola GROUP BY, questa deve essere usata anche nella SELECT
-- Non è possibile usare alias introdotti nella SELECT all'interno della clausola GROUP BY
SELECT      500*(Stipendio/500) AS FasciaStip, COUNT(*) AS NImp
FROM        Impiegati
GROUP BY    Stipendio/500


-- Filtraggio di Raggruppamenti
-- Non è possibile usare alias introdotti nella SELECT all'interno della clausola HAVING
SELECT
  Sede,
  COUNT(*) AS Numero_Dipendenti,
  AVG(Stipendio) AS Stipendio_Medio
FROM        Dipendenti
WHERE       Ruolo = 'Programmatore'
GROUP BY    Sede
HAVING      AVG(Stipendio) > 3000