-- Un DBMS si dice attivo quando dispone di un sottosistema integrato per definire e gestire regole
-- I trigger sono un caso specifico di regole di tipo ECA (Evento, Condizione, Azione)
-- Un trigger viene associato ad una tabella e si attiva automaticamente quando si verifica un evento specifico su di essa
-- Se all'attivazione sussiste una condizione specifica, viene eseguita un'azione definita
-- Vengono utilizzati per gestire vincoli di integrità, calcolare dati derivate, gestire eccezioni, ecc.
CREATE TRIGGER EmpSalaryTooHigh
AFTER UPDATE OF Salary ON Employee          -- Fa riferimento ad una singola tablle (target) e aspetta un evento (INSERT, DELETE, UPDATE)
REFERENCING NEW AS N OLD AS o
FOR EACH ROW
WHEN (N.Salary > (                          -- Condizione da verificare
    SELECT Salary FROM Employee
    WHERE EmpCode = N.EmpManager
))
UPDATE Employee                             -- Azione da eseguire
SET Salary = O.Salary
WHERE EmpCode = N.EmpCode;

-- Più istruzioni vengono eseguite in maniera atomica
CREATE TRIGGER EmpSalaryTooHigh
AFTER UPDATE OF Salary ON Employee
REFERENCING NEW AS N OLD AS o
FOR EACH ROW
WHEN (N.Salary > 200000)
BEGIN ATOMIC
    UPDATE Employee
    BEGIN ATOMI
    SET Salary = O.Salary
    WHERE EmpCode = N.EmpCode;

    INSERT INTO SalaryAudit(EmpCode, OldSalary, NewSalary)
    VALUES (N.EmpCode, O.Salary, N.Salary);
END;

-- Un trigger può attivarsi BEFORE o AFTER l'evento. Un trigger BEFORE può modificare i dati prima che vengano scritti nel DB, mentre un trigger AFTER può reagire alla modifica
-- Se l'evento coinvolge più tuple, si può specificare FOR EACH ROW (per ogni riga coinvolta) o FOR EACH STATEMENT (una sola volta per l'intera operazione)
-- Si possono utilizzare le clausole REFERENCING NEW AS e OLD AS per fare riferimento ai valori nuovi e vecchi delle righe coinvolte nell'evento
-- Similare per OLD_TABLE e NEW_TABLE, tabelle ipotetiche che contengono tutte le tuple coinvolge, prima e dopo la modifica


-- Un before trigger agisce necessariamente FOR EACH ROW, quindi si dice che è un row trigger
-- Inoltre non può includere azioni che modifichino il DB, ad eccezione della tupla in esame
-- Quindi non può neanche attivare altri trigger, come enfatizzato dal NO CASCADE, che si può omettere
CREATE TRIGGER CheckEmpSalary
NO CASCADE BEFORE INSERT ON Employee
REFERENCING NEW AS NewEmp
FOR EACH ROW
WHERE (NewEmp.Salary > (
    SELECT Salary FROM Employee
    WHERE EmpCode = NewEmp.EmpManager
))
SIGNAL SQLSTATE '70000'

-- Le azioni di un before trigger possono essere:
-- 1. SELECT
-- 2. SIGNAL: per generare un'eccezione e bloccare l'operazione che ha attivato il trigger
-- 3. SET: per modificare i valori della tupla in esame
CREATE TRIGGER EmpMinSalary
NO CASCADE BEFORE INSERT ON Employee
REFERENCING NEW AS NewEmp
FOR EACH ROW
SET 
NewEmp.Salary = (
    SELECT MIN(Salary) FROM Employee
    WHERE Dept = NewEmp.Dept
),
NewEmp.EmpManager = (
    SELECT MgrCode From Department
    WHERE DeptCode = NewEmp.Dept
)


-- Un after trigger può includere le azioni: SELECT, INSERT, DELETE, UPDATE, SIGNAL
-- Ad esempio, per calcolare dati derivati
CREATE TRIGGER UpdateQtaResidua
AFTER INSERT ON Vendite
REFERENCING NEW AS NuovaVendita
FOR EACH ROW
UPDATE Giacenze
SET QtaResidua = Qtaresidua - NuovaVendita.Quantita
WHERE CodProd = NuovaVendita.CodProd

-- I trigger si possono anche attivare in cascata
-- Se un trigger attiva più trigger, vengono eseguiti nell'ordine in cui sono stati definiti
-- Inoltre è importante fare attenzione a cicli infiniti e ai trigger che ri-attivano se stessi. DB2 pone un limite massimo di 16 livelli di ricorsione
CREATE TRIGGER InsertQtaDaOrdinare
AFTER UPDATE ON Giacenze
REFERENCING NEW AS NG
FOR EACH ROW
WHEN (NG.QtaResidua < NG.ScortaMinima)
INSERT INTO CARENZE
VALUES (NG.CodProd,NG.SCortaMinima-NG.QtaResidua)


-- Per condizioni più complesse, si possono usare i costrutti IF .. THEN .. [ELSE ...] END IF
CREATE TRIGGER DUEAZIONI
NO CASCADE BEFORE INSERT ON Employee
REFERENCING NEW AS N
FOR EACH ROW
IF (N.CodImp > 100) THEN
    SIGNAL SQLSTATE '80000' ('Codice invalido');                -- Punto e virgola obbligatorio
ELSE 
    SET N.Salary = (
        SELECT MIN(Salary) FROM Employee
        WHERE Dept = N.Dept
);                                                              -- Punto e virgola obbligatorio
END IF


-- Nelle preferenze dell'editor SQL (tasto destro del mouse)
-- -> SQL Processing -> Global settings -> Ignore native delimiter -> Lasciare la riga bianca come statement delimiter
-- Per creare un trigger usare CREATE OR REPLACE TRIGGER <NomeTrigger> e per eliminarlo usare DROP TRIGGER <NomeTrigger>
-- Se si cancella la table per cui è definito un trigger, questo non risulta più visibile e bisogna cancellarlo esplicitamente con DROP TRIGGER