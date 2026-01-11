-- 3.2)
create table RISULTATI (
	Nome varchar(20) not null,
	Gara char(5) not null,
	Punteggio INT not null,
	Posizione INT not null CHECK(Posizione > 0),
	constraint ID_RISULTATI_ID primary key (Nome, Gara)
);

-- 3.3)
INSERT INTO RISULTATI VALUES
	('Luca', 'A1235', 10, 1),
	('Marco', 'A1235', 7, 2),
	('Matteo', 'A1235', 3, 3),
	('Luca', 'B1235', 10, 3),
	('Marco', 'B1235', 15, 2),
	('Matteo', 'B1235', 25, 1);

CREATE OR REPLACE TRIGGER FIND_POS
BEFORE INSERT ON RISULTATI
REFERENCING NEW AS N
FOR EACH ROW
SET N.Posizione = (
	SELECT	COUNT(*)+1
	FROM	RISULTATI
	WHERE	Punteggio > N.Punteggio
	AND		Gara = N.Gara
);

CREATE OR REPLACE TRIGGER UPD_POS
AFTER INSERT ON RISULTATI
REFERENCING NEW AS N
FOR EACH ROW
UPDATE 	RISULTATI
SET		Posizione = Posizione+1
WHERE	Punteggio < N.Punteggio
AND		Gara = N.Gara;

INSERT INTO RISULTATI(Nome, Gara, Punteggio) VALUES
	('Giovanni', 'A1235', 8),
	('Giovanni', 'B1235', 100);
