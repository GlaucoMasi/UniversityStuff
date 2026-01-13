-- 3.2)
create table PRODOTTI (
  PID varchar(20) not NULL PRIMARY KEY,
  Prezzo INT not NULL CHECK(Prezzo > 0),
  NumCat INT not NULL DEFAULT 0
);

create table PC (
  CatID varchar(20) not null,
  PID varchar(20) not NULL REFERENCES PRODOTTI,
  Pertinenza SMALLINT not NULL CHECK(Pertinenza BETWEEN 1 AND 10),
  OK SMALLINT not NULL CHECK(OK IN (0, 1)),
  primary key (PID, CatID)
);

-- 3.3)
INSERT INTO PRODOTTI(PID, PREZZO) VALUES
	('Pane', 10),
	('TV', 100),
	('Latte', 5);

CREATE OR REPLACE TRIGGER SET_OK
BEFORE INSERT ON PC
REFERENCING NEW AS N
FOR EACH ROW
IF (N.Pertinenza > 5) THEN
	SET N.OK = 1;
ELSE
	SET N.OK = 0;
END IF

CREATE OR REPLACE TRIGGER INCREASE_NumCat
AFTER INSERT ON PC
REFERENCING NEW AS N
FOR EACH ROW
UPDATE	PRODOTTI
SET		NumCat = NumCat+1
WHERE	PID = N.PID;

INSERT INTO PC(CatID, PID, Pertinenza) VALUES
	('Cibo', 'Pane', 9),
	('Cibo', 'TV', 2);

INSERT INTO PC(CatID, PID, Pertinenza) VALUES
	('a', 'Pane', 3),
	('b', 'Pane', 3);
