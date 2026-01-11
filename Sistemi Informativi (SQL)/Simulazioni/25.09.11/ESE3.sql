-- 3.2)
create table PRODOTTI (
  Nome varchar(20) not NULL PRIMARY KEY,
  PrezzoBase DEC(8, 2) not NULL CHECK(PrezzoBase > 0)
);

create table PREZZI (
  IdNegozio char(5) not null,
  Nome varchar(20) not NULL REFERENCES PRODOTTI,
  Prezzo DEC(8, 2) not NULL CHECK(Prezzo > 0),
  Data DATE not null,
  primary key (IdNegozio, Nome, Data)
);

-- 3.3)
INSERT INTO PRODOTTI VALUES
	('Latte', 5),
	('Biscotti', 3);

CREATE OR REPLACE TRIGGER CHECK_PRICE
BEFORE INSERT ON PREZZI
REFERENCING NEW AS N
FOR EACH ROW
WHEN (ABS(1 - N.Prezzo/(
	SELECT	PrezzoBase
	FROM	Prodotti
	WHERE	Nome = N.Nome
)) > 0.2)
SIGNAL SQLSTATE '70000' ('Il prezzo differisce troppo dal prezzo base!');

INSERT INTO PREZZI VALUES ('A0000', 'Latte', 5.1, CURRENT DATE);
INSERT INTO PREZZI VALUES ('A0001', 'Latte', 4.9, CURRENT DATE);
INSERT INTO PREZZI VALUES ('A0000', 'Latte', 100, CURRENT DATE);
INSERT INTO PREZZI VALUES ('A0001', 'Latte', 0.5, CURRENT DATE);
