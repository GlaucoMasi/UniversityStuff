-- 3.2)
create table AZIONI (
  Nome varchar(20) not NULL PRIMARY KEY,
  Valore DEC(8, 2) not NULL CHECK(Valore > 0)
);

create table PORTAFOGLI (
  PID char(10) not NULL PRIMARY KEY,
  ValoreTotale DEC(8, 2) not NULL CHECK(ValoreTotale > 0),
  WARNING SMALLINT not NULL DEFAULT 0 CHECK(WARNING IN (0, 1))
);

create table AP (
  Nome varchar(20) not NULL REFERENCES AZIONI,
  PID char(10) not NULL REFERENCES PORTAFOGLI,
  Quantita INT not NULL CHECK(Quantita > 0),
  constraint ID_AP_ID primary key (PID, Nome)
);

-- 3.3)
INSERT INTO AZIONI VALUES 
	('Amazon', 10.5),
	('Tesla', 13.5);

INSERT INTO PORTAFOGLI(PID, ValoreTotale) VALUES
	('AAAAAAAAA0', 375),
	('AAAAAAAAA1', 210),
	('AAAAAAAAA2', 135);
	
INSERT INTO AP VALUES
	('Amazon', 'AAAAAAAAA0', 10),
	('Tesla', 'AAAAAAAAA0', 20),
	('Amazon', 'AAAAAAAAA1', 20),
	('Tesla', 'AAAAAAAAA2', 10);

CREATE OR REPLACE TRIGGER UPD_AZIONE
AFTER UPDATE ON AZIONI
REFERENCING NEW AS N OLD AS O
FOR EACH ROW
UPDATE PORTAFOGLI p
SET 	p.ValoreTotale = p.ValoreTotale + (N.Valore - O.Valore) * (
	SELECT 	Quantita
	FROM	AP
	WHERE	Nome = N.Nome
	AND		PID = p.PID
)
WHERE	p.PID IN (
	SELECT	PID
	FROM	AP
	WHERE	Nome = N.Nome
);

CREATE OR REPLACE TRIGGER SET_WARNING
AFTER UPDATE OF ValoreTotale ON PORTAFOGLI
REFERENCING NEW AS N OLD AS O
FOR EACH ROW
WHEN (ABS(N.ValoreTotale - O.ValoreTotale) > 1000)
UPDATE	PORTAFOGLI
SET		Warning = 1
WHERE	PID = N.PID;

UPDATE AZIONI
SET		Valore = 1000
WHERE	Nome = 'Amazon';
