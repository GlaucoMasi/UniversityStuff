-- 3.2)
create table PRODOTTI (
  CodProdotto char(5) not NULL PRIMARY KEY,
  Prezzo DEC(8, 2) not NULL CHECK(Prezzo > 0),
  NumReclami INT not NULL DEFAULT 0 CHECK(NumReclami >= 0)
);

create table ORDINI (
  IdOrdine char(5) not NULL PRIMARY KEY,
  Importo DEC(8, 2) not NULL CHECK(Importo > 0)
);

create table OP (
  IdOrdine char(5) not NULL REFERENCES ORDINI,
  CodProdotto char(5) not NULL REFERENCES PRODOTTI ON DELETE CASCADE,
  Quantita INT not NULL CHECK(Quantita > 0),
  primary key (CodProdotto, IdOrdine)
);

create table RECLAMI (
  Motivo varchar(50) not null,
  CodProdotto char(5) not null,
  IdOrdine char(5) not NULL,
  primary key (CodProdotto, IdOrdine),
  foreign key (CodProdotto, IdOrdine) references OP ON DELETE CASCADE
);

-- CHECK non supportato
-- alter table ORDINI add constraint ID_ORDINI_CHK
--      check(exists(select * from OP
--                   where OP.IdOrdine = IdOrdine));

-- 3.3)
INSERT INTO PRODOTTI(CodProdotto, Prezzo) VALUES
	('A0000', 10),
	('A0001', 15),
	('A0002', 5);

INSERT INTO ORDINI VALUES
	('B0000', 30),
	('B0001', 25),
	('B0002', 10)
	
INSERT INTO OP VALUES
	('B0000', 'A0000', 1),
	('B0000', 'A0001', 1),
	('B0000', 'A0002', 1),
	('B0001', 'A0000', 1),
	('B0001', 'A0001', 1),
	('B0002', 'A0001', 1);

CREATE OR REPLACE TRIGGER UPD_TOT
AFTER INSERT ON RECLAMI
REFERENCING NEW AS N
FOR EACH ROW
UPDATE	PRODOTTI
SET		NumReclami = NumReclami+1
WHERE	CodProdotto = N.CodProdotto;

CREATE OR REPLACE TRIGGER DEL_PROD
AFTER UPDATE OF NumReclami ON PRODOTTI
REFERENCING NEW AS N
FOR EACH ROW
WHEN (N.NumReclami >= 3)
DELETE	PRODOTTI
WHERE	CodProdotto = N.CodProdotto;

INSERT INTO RECLAMI VALUES ('Rotto', 'A0001', 'B0000');
INSERT INTO RECLAMI VALUES ('Rotto', 'A0001', 'B0001');
INSERT INTO RECLAMI VALUES ('Rotto', 'A0001', 'B0002');
