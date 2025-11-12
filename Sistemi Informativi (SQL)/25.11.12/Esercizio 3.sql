-- Prima parte: script SQL generato da DB-Main
create table AUTO (
  Targa char(7) not null,
  Km INT not null,
  Anno INT not null,
  PrezzoRichiesto INT not null,
  Produttore varchar(30) not null,
  Nome varchar(30) not null,
  constraint ID_AUTO_ID primary key (Targa));

create table VENDITORE (
  Nome varchar(30) not null,
  Cognome varchar(30) not null,
  Codice char(16) not null,
  constraint ID_VENDITORE_ID primary key (Codice));

create table MODELLO (
  Produttore varchar(30) not null,
  Nome varchar(20) not null,
  constraint ID_MODELLO_ID primary key (Produttore, Nome));

create table ESPERTO (
  Produttore varchar(30) not null,
  Nome varchar(30) not null,
  Codice char(16) not null,
  constraint ID_ESPERTO_ID primary key (Codice, Produttore, Nome));

create table VENDITA (
  Targa char(7) not null,
  PrezzoEffettivo DEC(8, 2) not null,
  Codice char(16) not null,
  constraint FKVEN_AUT_ID primary key (Targa));

alter table AUTO add constraint FKMA_FK
  foreign key (Produttore, Nome)
  references MODELLO;

alter table ESPERTO add constraint FKESP_VEN
  foreign key (Codice)
  references VENDITORE;

alter table ESPERTO add constraint FKESP_MOD_FK
  foreign key (Produttore, Nome)
  references MODELLO;

alter table VENDITA add constraint FKVEN_VEN_FK
  foreign key (Codice)
  references VENDITORE;

alter table VENDITA add constraint FKVEN_AUT_FK
  foreign key (Targa)
  references AUTO;


-- Seconda parte
-- Q1) Definire una vista che, per ogni venditore, fornisca il numero di auto vendute per ogni modello
CREATE VIEW NUMVENDITE AS
	SELECT	v.Codice, a.PRODUTTORE, a.NOME, COUNT(v.TARGA) AS NUMVENDUTE
	FROM	VENDITA v, AUTO a
	WHERE	v.TARGA = a.TARGA
	GROUP BY v.Codice, a.PRODUTTORE, a.NOME;

-- Q2) Definire un trigger che vieti di vendere auto a un prezzo maggiore di quello richiesto
CREATE OR REPLACE TRIGGER PrezzoVenditaMaggioreRichiesto
BEFORE INSERT ON VENDITA
REFERENCING NEW AS N
FOR EACH ROW
WHEN (
	N.PrezzoEffettivo > (
		SELECT	PrezzoRichiesto
		FROM	Auto
		WHERE	Targa = N.Targa
	)
)
SIGNAL SQLSTATE '70001' ('Prezzo di vendita superiore a quello richiesto');

-- Q3) Definire un trigger che, all’atto dell’inserimento di una nuova auto, calcoli il prezzo richiesto come media di quelli di altre auto dello stesso modello
-- (se non ce ne sono lasciare il valore fornito in input, ma se questo non è presente segnalare errore)
CREATE OR REPLACE TRIGGER CalcoloPrezzoRichiesto
BEFORE INSERT ON Auto
REFERENCING NEW AS N
FOR EACH ROW
	IF(
		EXISTS (
			SELECT	Targa
			FROM	Auto
			WHERE	(Produttore, Nome) = (N.Produttore, N.Nome)
		)
	) THEN	SET N.PrezzoRichiesto = (
			SELECT	AVG(PrezzoRichiesto)
			FROM	AUTO
			WHERE	(Produttore, Nome) = (N.Produttore, N.Nome)
	);
	ELSE
		IF( 
			N.PrezzoRichiesto IS NULL
		) THEN 	SIGNAL SQLSTATE '70002' ('Prezzo richiesto non fornito e nessuna auto dello stesso modello presente');
		END IF;
	END IF
