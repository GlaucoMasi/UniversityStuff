-- Esercizio 1
CREATE TABLE R (
	A INT NOT NULL PRIMARY KEY,
	C INT
)

CREATE TABLE S (
	B INT NOT NULL PRIMARY KEY,
	C INT NOT NULL
)

-- Q1) Garantire l’unicità dei valori di R.C
CREATE OR REPLACE TRIGGER R_C_UNIQUE
AFTER INSERT ON R
REFERENCING NEW_TABLE AS N
FOR EACH STATEMENT
BEGIN ATOMIC
	IF EXISTS (
		SELECT 	C
		FROM 	N
		WHERE C IS NOT NULL
		GROUP BY C
		HAVING COUNT(*) > 1
	) THEN 
		SIGNAL SQLSTATE '70001' ('R.C duplicato tra tuple inserite');
	END IF;

	IF EXISTS (
		SELECT 	C
		FROM 	R
		WHERE C IS NOT NULL
		GROUP BY C
		HAVING COUNT(*) > 1
	) THEN 
		SIGNAL SQLSTATE '70001' ('R.C inserito già presente nella tabella');
	END IF;
END

-- Q2) Imporre il vincolo di integrità referenziale su S.C
CREATE OR REPLACE TRIGGER S_C_FK
AFTER INSERT ON S
REFERENCING NEW_TABLE AS N
FOR EACH STATEMENT
BEGIN ATOMIC
	IF EXISTS (
		SELECT	*
		FROM	N
		WHERE	C IS NOT NULL
		AND		NOT EXISTS (
			SELECT 	*
			FROM	R r
			WHERE	r.C = n.C
		)
	) THEN
		SIGNAL SQLSTATE '70002' ('S.C referenzia R.C, deve esistere un valore corrispondente in R');
	END IF;
END

-- Q3) Gestire una politica di cancellazione in cascata su S quando vengono cancellate tuple di R
CREATE OR REPLACE TRIGGER S_ONDELETE_CASCADE
AFTER DELETE ON R
REFERENCING OLD AS OldRow
FOR EACH ROW
DELETE FROM S
WHERE	C = OldRow.C

-- Inserimenti validi
INSERT INTO R VALUES
	(1, 100),
  (2, NULL),
  (3, 200);
INSERT INTO S VALUES
	(10, 100),
  (20, 100),
  (30, 200);

-- Inserimento non valido: R.C duplicato
INSERT INTO R VALUES
  (4, 200);

-- Inserimenti multipli non validi: R.C duplicato
INSERT INTO R VALUES
  (4, 300),
  (5, 300)

-- Inserimento non valido: violazione del vincolo di foreign key
INSERT INTO S VALUES
  (40, 300);

-- Inserimenti multipli non validi: violazione del vincolo di foreign key
INSERT INTO S VALUES
  (40, 300),
  (50, 400)

-- Esempio di cancellazione in cascata
DELETE FROM R
WHERE  C >= 100;


-- Esercizio 2
CREATE TABLE PRODOTTI(
  PCODE 	 CHAR(5) NOT NULL PRIMARY KEY,  	
  DESCRIZIONE VARCHAR(20) NOT NULL,
	PREZZO 	 DEC(6,2) NOT NULL CHECK (PREZZO > 0)
);

CREATE TABLE ORDINI(
	ID 		 CHAR(5) NOT NULL PRIMARY KEY,
	NOMECLIENTE VARCHAR(20) NOT NULL,
	SPESESPEDIZIONE DEC(6,2) NOT NULL DEFAULT 7 CHECK (SPESESPEDIZIONE = 7), 
	TOTALE 	 DEC(6,2) NOT NULL CHECK (TOTALE >=0)
);	

CREATE TABLE VENDITE(
	PCODE 	 CHAR(5) NOT NULL REFERENCES PRODOTTI,
	ID 		 CHAR(5) NOT NULL REFERENCES ORDINI,
	QUANTITA INT NOT NULL CHECK (QUANTITA > 0),
	PRIMARY KEY (PCODE,ID)
);

CREATE OR REPLACE TRIGGER NuovoOrdine
BEFORE INSERT ON ORDINI
REFERENCING NEW AS N
FOR EACH ROW
SET N.TOTALE = N.SPESESPEDIZIONE;

-- Q1) Mantenere ORDINI quando si inseriscono nuovi prodotti nell’ordine
CREATE OR REPLACE TRIGGER InsertOrdine
AFTER INSERT ON VENDITE
REFERENCING NEW AS N
FOR EACH ROW
UPDATE	ORDINI
SET		TOTALE = TOTALE + N.QUANTITA * (
	SELECT	PREZZO
	FROM	PRODOTTI
	WHERE	PCODE = N.PCODE
)
WHERE	ID = N.ID;

-- Q2) Mantenere ORDINI quando si modifica (in più o in meno) la quantità di un prodotto
CREATE OR REPLACE TRIGGER UpdateOrdine
AFTER UPDATE ON VENDITE
REFERENCING OLD AS O NEW AS N
FOR EACH ROW
UPDATE	ORDINI
SET		TOTALE = TOTALE + (N.QUANTITA - O.QUANTITA) * (
	SELECT	PREZZO
	FROM	PRODOTTI
	WHERE	PCODE = O.PCODE
)
WHERE	ID = O.ID;

-- Q3) Mantenere ORDINI quando si eliminano prodotti dall’ordine 
CREATE OR REPLACE TRIGGER DeleteOrdine
AFTER DELETE ON VENDITE
REFERENCING OLD AS O
FOR EACH ROW
UPDATE	ORDINI
SET		TOTALE = TOTALE - O.QUANTITA * (
	SELECT	PREZZO
	FROM	PRODOTTI
	WHERE	PCODE = O.PCODE
)
WHERE	ID = O.ID;

-- Inserimento in ORDINI
INSERT INTO ORDINI(ID,NOMECLIENTE) VALUES
  ('11111', 'Paolino Paperino');

-- Inserimento in PRODOTTI
INSERT INTO PRODOTTI VALUES
  ('P0001', 'Panettone', 4.50);

-- Inserimento in VENDITE
INSERT INTO VENDITE VALUES
  ('P0001', '11111', 2);

-- Update di VENDITE 
UPDATE 	VENDITE 
SET   	QUANTITA = QUANTITA + 10
WHERE 	PCODE = 'P0001'
AND		ID = '11111';

-- Delete da VENDITE
DELETE FROM VENDITE 
WHERE 	PCODE = 'P0001'
AND		ID = '11111';


-- Esercizio 3
CREATE TABLE STUDENTI(
  MATR 	CHAR(6) NOT NULL PRIMARY KEY,
  NOME 	VARCHAR(30) NOT NULL,
  COGNOME VARCHAR(30) NOT NULL
);

CREATE TABLE CORSI (
  CODC 	CHAR(3) NOT NULL PRIMARY KEY,
	NOME 	VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE APPELLI (
  CODC 	CHAR(3) NOT NULL REFERENCES CORSI,
	DATA 	DATE NOT NULL, 
	LUOGO 	VARCHAR(30) NOT NULL,
	PRIMARY KEY (CODC, DATA)
);

CREATE TABLE ESAMI (
	MATR 	CHAR(6) NOT NULL REFERENCES STUDENTI,
  CODC 	CHAR(3) NOT NULL,
	DATA 	DATE NOT NULL, 
	VOTO 	INT NOT NULL CHECK (VOTO BETWEEN 0 AND 31),
	ACCETTATO CHAR(1) DEFAULT NULL CHECK (ACCETTATO = 'Y'),
	FOREIGN KEY (CODC, DATA) REFERENCES APPELLI,
	PRIMARY KEY (MATR, CODC, DATA)
);

CREATE TABLE VERBALIZZAZIONI (
	MATR 	CHAR(6) NOT NULL REFERENCES STUDENTI,
  CODC 	CHAR(3) NOT NULL,
	DATA 	DATE NOT NULL, 
	VOTO 	INT NOT NULL CHECK (VOTO BETWEEN 18 AND 31),
	FOREIGN KEY (CODC, DATA) REFERENCES APPELLI,
	PRIMARY KEY (MATR, CODC)
);

-- Insertimenti validi
INSERT INTO STUDENTI VALUES 
  ('765432', 'Alan', 'Turing');

INSERT INTO CORSI VALUES 
  ('SIT', 'Sistemi Informativi T'), 
  ('CET', 'Calcolatori Elettronici T');

INSERT INTO APPELLI VALUES 
	('SIT', '10.02.2024', 'LAB 4'),
  ('SIT', '22.06.2024', 'LAB 9'),
  ('CET', '20.01.2024', 'AULA 2.9'),
  ('CET', '14.07.2024', 'AULA 2.4');

INSERT INTO ESAMI VALUES
	('765432', 'SIT', '10.02.2024', 19, NULL),
 	('765432', 'SIT', '22.06.2024', 30, NULL),
 	('765432', 'CET', '20.01.2024', 15, NULL),
 	('765432', 'CET', '14.07.2024', 31, NULL);


-- Q1) Non deve essere possibile inserire direttamente in ESAMI il valore Accettato = 'Y'
CREATE OR REPLACE TRIGGER InserimentoEsameAccettato
BEFORE INSERT ON ESAMI
REFERENCING NEW AS N
FOR EACH ROW
WHEN (N.ACCETTATO IS NOT NULL)
SIGNAL SQLSTATE '70003' ('Non è possibile inserire direttamente un esame con valore Accettato = ''Y''');

-- Inserimento non valido
INSERT INTO ESAMI VALUES
  ('765432', 'SIT', '15.07.2024', 30, 'Y');

-- Q2) Non deve essere possibile, per uno stesso studente, accettare 2 o più voti di più esami di uno stesso corso
CREATE OR REPLACE TRIGGER SingoloEsameAccettato
AFTER UPDATE OF ACCETTATO ON ESAMI
REFERENCING NEW_TABLE AS NT
FOR EACH STATEMENT
WHEN (
	EXISTS (
		SELECT	COUNT(*)
		FROM	ESAMI e
		JOIN (
			SELECT 	DISTINCT MATR, CODC
			FROM	NT
			WHERE	ACCETTATO = 'Y'
		) j
		ON (j.MATR, j.CODC) = (e.MATR, e.CODC)
		WHERE e.ACCETTATO = 'Y'
		GROUP BY e.MATR, e.CODC
		HAVING	COUNT(*) > 1
	)
)
SIGNAL SQLSTATE '70004' ('Ogni studente può accettare un solo esame per corso');
-- La soluzione è più complessa del necessario perchè controlla tutta la tabella.
-- Normalmente sarebbe necessario, ma sotto definisco un trigger che elimina tutte le altre coppie (studente, corso)
-- nel momento in cui un voto viene accettato e verbalizzato, quindi basterebbe controllare le righe inserite
-- Fare attenzione anche all'ordine in cui vengono definiti, i due trigger per la verbalizzazione runnano solo dopo quelli che controllano

-- Inserimento valido
UPDATE 	ESAMI
SET 	ACCETTATO = 'Y'
WHERE	(MATR, CODC, DATA) = ('765432', 'SIT', '2024-02-10');

-- Inserimento non valido
UPDATE 	ESAMI
SET 	ACCETTATO = 'Y'
WHERE	(MATR, CODC, DATA) = ('765432','SIT', '2024-06-22');

-- Inserimenti multiplici non validi
UPDATE 	ESAMI
SET 	ACCETTATO = 'Y'
WHERE	(MATR, CODC) = ('765432', 'CET');

-- Q3) Quando si accetta un Voto si inseriscono con un trigger in VERBALIZZAZIONI i dati corrispondenti all'esame scelto
CREATE OR REPLACE TRIGGER Verbalizzazione
AFTER UPDATE OF ACCETTATO ON ESAMI
REFERENCING NEW AS N
FOR EACH ROW
WHEN (N.ACCETTATO = 'Y')
INSERT INTO VERBALIZZAZIONI
VALUES (N.MATR, N.CODC, N.DATA, N.VOTO);

-- Q4) Dopo la verbalizzazione di un voto tutti gli esami relativi alla relativa coppia (studente, corso) vanno cancellati da ESAMI mediante un trigger
CREATE OR REPLACE TRIGGER DeleteEsami
AFTER UPDATE OF ACCETTATO ON ESAMI
REFERENCING NEW AS N
FOR EACH ROW
DELETE FROM ESAMI
WHERE	(MATR, CODC) = (N.MATR, N.CODC);

-- Inserimento valido
UPDATE 	ESAMI
SET 	ACCETTATO = 'Y'
WHERE	(MATR, CODC, DATA) = ('765432', 'CET', '2024-07-14');
