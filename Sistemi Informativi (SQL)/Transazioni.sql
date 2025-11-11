-- Una transazione è un'unità logica di elaborazione che corrisponde ad una serie di operazioni fisiche elementari sul DB
-- Proprietà ACID che deve valere per ogni transazione:
-- Atomicity: Tutte le operazioni vengono completate con successo oppure nessuna di esse deve essere applicata
-- Consistency: Una transazione porta il database da uno stato consistente ad un altro stato consistente, non vengono violati vincoli di integrità
-- Isolation: Le operazioni di una transazione sono isolate dalle operazioni di altre transazioni concorrenti
-- Durability: Una volta che una transazione è stata completata con successo, le modifiche apportate al database sono permanenti, anche in caso di guasti di sistema
-- Il Logging & Recovery Manager si fa carico di Atomicity e Durability, il Concurrency Manager garantisce l'Isolation e il DDL Compiler genera parte dei controlli per la Consistency

-- Esempio di transazione
SELECT  *
FROM    Department
INSERT INTO     Department(DeptNo,DeptName,AdmrDept)
VALUES  ('X00','nuovo dept 1','A00')
SAVEPOINT pippo ON ROLLBACK RETAIN CURSORS                              -- Crea un punto di salvataggio all'interno della transazione
SELECT  *
FROM    Department
INSERT INTO     Department(DeptNo,DeptName,AdmrDept)
VALUES  ('Y00','nuovo dept 2','A00')
SELECT  *
FROM    Department
ROLLBACK WORK TO SAVEPOINT pippo                                        -- Annulla tutte le modifiche apportate dopo il punto di salvataggio pippo
SELECT  *
FROM    Department
COMMIT WORK                                                             -- Conferma tutte le modifiche apportate dalla transazione

/*
Le transazioni possono essere usate per assicurarsi che vengano rispettati i vincoli degli schemi E/R in cui un’entità partecipa
con cardinalità minima uno ad un'associazione, ed entità e associazione sono tradotte separatamente
INSERT INTO Squadra VALUES (‘BolognaFC1909')
...
INSERT INTO Giocatore(NomeSquadra,CF)
VALUES (‘BolognaFC1909’, ‘ABCDEF12H45’)
...
COMMIT
*/

-- L'esecuzione di più transizioni può essere
-- Seriale: le transazioni vengono eseguite una dopo l'altra senza sovrapposizioni
-- Concorrente: si chiama anche 'interleaved execution', le transazioni vengono eseguite in modo sovrapposto per ridurre il tempo medio e mandare una mentre l'altra è in attesa di I/O

-- Problematiche in caso di violazione dell'Isolation:
-- 1. Lost Update: Due transazioni leggono lo stesso dato e lo aggiornano, ma l'aggiornamento di una sovrascrive quello dell'altra
-- 2. Dirty Read: Una transazione legge un dato modificato da un'altra transazione non ancora confermata, che poi viene annullata
-- 3. Unrepeatable Read: Una transazione legge lo stesso dato due volte, ma tra le due letture un'altra transazione lo modifica
-- 4. Phantom Read: Una transazione legge un insieme di righe, ma tra due esecuzioni un'altra transazione inserisce o elimina righe che soddisfano la condizione della query

-- Diversi livelli di isolamento espongono più o meno alle problematiche ma migliorano anche le prestazioni:
-- Serializable, Repeatable Read (RR) : Previene tutte le problematiche
-- Repetable Read, Read Stability (RS): Previene Unrepeatable Read, Dirty Read e Lost Update
-- Read Committed, Curosor Stability (CS): Previene Dirty Read e Lost Update
-- Uncommitted Read (UR): Previene solo Lost Update
-- Il livello di default è CS, per cambiarlo, prima di connettersi al DB, si usa l'istruzione:
-- CHANGE ISOLATION TO [RR | RS | CS | UR]