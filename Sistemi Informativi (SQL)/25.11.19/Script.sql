-- Esercizio 1
-- Creazione tabelle
create table CARBURANTI (
  Nome varchar(10) not null,
  constraint ID_CARBURANTI_ID primary key (Nome));

create table RIFORNIMENTI (
  id char(5) not null,
  Timestamp TIMESTAMP not null,
  Litri DEC(5, 2) not null,
  Nome varchar(10) not null,
  CodDistr char(3) not null,
  Numero INT not null,
  constraint ID_RIFORNIMENTI_ID primary key (id));

create table POMPE (
  CodDistr char(3) not null,
  Numero INT not null,
  constraint ID_POMPE_ID primary key (Numero, CodDistr));

create table PC (
  Nome varchar(10) not null,
  CodDistr char(3) not null,
  Numero INT not null,
  Prezzo DEC(5, 3) not null,
  LitriErogati DEC(8, 2) not null,
  constraint ID_PC_ID primary key (Nome, Numero, CodDistr));

-- Vincoli
alter table RIFORNIMENTI add constraint REF_RIFOR_PC_FK
  foreign key (Nome, Numero, CodDistr)
  references PC;

alter table PC add constraint REF_PC_POMPE_FK
  foreign key (Numero, CodDistr)
  references POMPE;

alter table PC add constraint EQU_PC_CARBU
  foreign key (Nome)
  references CARBURANTI;

-- Operazioni e Trigger
INSERT INTO POMPE VALUES ('D01', 1);
INSERT INTO CARBURANTI VALUES ('GAS');
INSERT INTO PC VALUES ('GAS', 'D01', 1, 1.55, 27250.16);

CREATE OR REPLACE TRIGGER UpdateTotLitri
AFTER INSERT ON RIFORNIMENTI
REFERENCING NEW AS N
FOR EACH ROW
UPDATE PC
SET LitriErogati = LitriErogati + N.Litri
WHERE (CodDistr, Numero, Nome) = (N.CodDistr, N.Numero, N.Nome);

INSERT INTO RIFORNIMENTI VALUES ('R0001', CURRENT TIMESTAMP, 27.35, 'GAS', 'D01', 1);
INSERT INTO RIFORNIMENTI VALUES ('R0002', CURRENT TIMESTAMP, 100, 'GAS', 'D01', 1);


-- Esercizio 2
-- Creazione tabelle
create table CAMERE (
  NomeAlbergo varchar(10) not null,
  Numero INT not null,
  Comune varchar(10) not null,
  PostiLetto INT not null,
  constraint ID_CAMERE_ID primary key (Comune, NomeAlbergo, Numero));

create table PRENOTAZIONI (
  NomeAlbergo varchar(10) not null,
  Numero INT not null,
  Comune varchar(10) not null,
  DataInizio date not null,
  DataFine date not null,
  NumPersone INT not null,
  CodiceCliente varchar(16) not null,
  constraint ID_PRENOTAZIONE_ID primary key (Comune, NomeAlbergo, Numero, DataInizio));

-- Vincoli
alter table PRENOTAZIONI add constraint REF_PRENO_CAMER
  foreign key (Comune, NomeAlbergo, Numero)
  references CAMERE;

-- Operazioni e Trigger
INSERT INTO CAMERE VALUES ('Jolly', 27, 'Bologna', 4);

CREATE OR REPLACE TRIGGER CheckPrenotazione
BEFORE INSERT ON PRENOTAZIONI
REFERENCING NEW AS N
FOR EACH ROW
WHEN (
	EXISTS (
		SELECT	*
		FROM	PRENOTAZIONI
		WHERE	DataInizio <= N.DataFine
		AND		DataFine >= N.DataInizio
		AND		(Comune, NomeAlbergo, Numero) = (N.Comune, N.NomeAlbergo, N.Numero)
	)
)
SIGNAL SQLSTATE '70001' ('Prenotazione in conflitto con una già esistente');

-- Inserimenti validi
INSERT INTO PRENOTAZIONI VALUES ('Jolly', 27, 'Bologna', '17.12.2020', '20.12.2020', 3, 'abcdef72a19g443t');
INSERT INTO PRENOTAZIONI VALUES ('Jolly', 27, 'Bologna', '15.12.2020', '24.12.2020', 4, 'ggalme83b26a995q');

-- Inserimento invalido
INSERT INTO PRENOTAZIONI VALUES ('Jolly', 27, 'Bologna', '14.12.2020', '16.12.2020', 3, 'aaaaaa72a19g443t');


-- Esercizio 3
-- Creazione tabelle
create table STATISTICHE (
  CodFattura char(5) not null,
  Durata INT not null,
  Variazione DEC(6, 3) not null,
  constraint ID_STATI_FATTU_ID primary key (CodFattura));

create table FATTURE (
  CodFattura char(5) not null,
  Numero INT not null,
  NomeCliente char(10) not null,
  Data DATE not null,
  Importo DEC(8, 2) not null,
  constraint ID_FATTURE_ID primary key (CodFattura),
  constraint SID_FATTU_PREVE_ID unique (Numero));

create table PREVENTIVI (
  Numero INT not null,
  NomeCliente char(10) not null,
  DescrizioneLavori varchar(50) not null,
  ImportoPrevisto DEC(8, 2) not null,
  DataPreventivo DATE not null,
  DataInizioLavori DATE not null,
  constraint ID_PREVENTIVI_ID primary key (Numero));

-- Vincoli
alter table STATISTICHE add constraint ID_STATI_FATTU_FK
  foreign key (CodFattura)
  references FATTURE;

alter table FATTURE add constraint SID_FATTU_PREVE_FK
  foreign key (Numero)
  references PREVENTIVI;

-- Operazioni e Trigger
CREATE OR REPLACE TRIGGER SetClientName
BEFORE INSERT ON FATTURE
REFERENCING NEW AS N
FOR EACH ROW
SET N.NomeCliente = (
	SELECT	NomeCliente
	FROM	PREVENTIVI
	WHERE	Numero = N.Numero
);

CREATE OR REPLACE TRIGGER UpdateStats
AFTER INSERT ON FATTURE
REFERENCING NEW AS N
FOR EACH ROW
INSERT INTO STATISTICHE
SELECT 	N.CodFattura, 
		DAYS(N.Data)-Days(DataInizioLavori),
		((N.Importo-ImportoPrevisto)/ImportoPrevisto)*100
FROM	PREVENTIVI
WHERE	Numero = N.Numero;

INSERT INTO PREVENTIVI VALUES (215, 'Paperino', 'Rifacimento tetto', 15000, '26.11.2020', '01.12.2020');

INSERT INTO FATTURE (CodFattura, Numero, Data, Importo) VALUES ('F1234', 215, '14.03.2021', 18000);


-- Esercizio 4
-- Creazione Tabelle
create table COMMENTO (
  ID INT not null,
  Timestamp TIMESTAMP not null,
  Contenuto varchar(300) not null,
  Nickname varchar(10) not null,
  IDPartita INT not null,
  constraint ID_COMMENTO_ID primary key (ID));

create table PARTITE (
  IDPartita INT not null,
  Esito char(1) CHECK (Esito IN ('B', 'N', 'P')),
  PuntiVinceBianco INT not null,
  PuntiVinceNero INT not null,
  PuntiPatta INT not null,
  Nickname_B varchar(10) not null,
  Nickname_N varchar(10) not null,
  constraint ID_PARTITE_ID primary key (IDPartita));

create table UTENTI (
  Nickname varchar(10) not null,
  Password varchar(10) not null,
  Punteggio INT not null,
  constraint ID_UTENTI_ID primary key (Nickname));

-- Vincoli
alter table COMMENTO add constraint REF_COMME_UTENT_FK
  foreign key (Nickname)
  references UTENTI;

alter table COMMENTO add constraint REF_COMME_PARTI_FK
  foreign key (IDPartita)
  references PARTITE;

alter table PARTITE add constraint REF_PARTI_UTENT_1_FK
  foreign key (Nickname_B)
  references UTENTI;

alter table PARTITE add constraint REF_PARTI_UTENT_FK
  foreign key (Nickname_N)
  references UTENTI;

-- Operazioni e Trigger
INSERT INTO UTENTI VALUES
  ('Paperoga', 'nonricordo', 1600),
	('Archimede', 'ax$&()$P', 2430);

CREATE OR REPLACE TRIGGER CalcPoints
BEFORE INSERT ON PARTITE
REFERENCING NEW AS N
FOR EACH ROW
BEGIN ATOMIC
	DECLARE PuntiB INT;
	DECLARE PuntiN INT;
	SET PuntiB = (
		SELECT	Punteggio
		FROM	UTENTI
		WHERE	N.Nickname_B = Nickname
	);
	SET PuntiN = (
		SELECT	Punteggio
		FROM	UTENTI
		WHERE	N.Nickname_N = Nickname
	);
	IF (PuntiB <= PuntiN)
	THEN
		SET 
			N.PuntiVinceBianco = 5 + (PuntiN-PuntiB)/20,
			N.PuntiVinceNero = 1 + MIN(500/(PuntiN-PuntiB), 10);
	ELSE
		SET
			N.PuntiVinceNero = 5 + (PuntiB-PuntiN)/20,
			N.PuntiVinceBianco = 1 + MIN(500/(PuntiB-PuntiN), 10);
	END IF;
END

CREATE OR REPLACE TRIGGER UpdPoints
AFTER UPDATE OF Esito ON PARTITE
REFERENCING NEW AS N
FOR EACH ROW
IF (N.Esito = 'B')
THEN
	UPDATE	UTENTI
	SET		Punteggio = Punteggio + N.PuntiVinceBianco
	WHERE	Nickname = N.Nickname_B;
	UPDATE	UTENTI
	SET		Punteggio = Punteggio - N.PuntiVinceBianco
	WHERE	Nickname = N.Nickname_N;
ELSE
	UPDATE	UTENTI
	SET		Punteggio = Punteggio - N.PuntiVinceNero
	WHERE	Nickname = N.Nickname_B;
	UPDATE	UTENTI
	SET		Punteggio = Punteggio + N.PuntiVinceNero
	WHERE	Nickname = N.Nickname_N;
END IF

INSERT INTO PARTITE (IDPartita, PuntiPatta, Nickname_B, Nickname_N) VALUES (54276, 0, 'Paperoga', 'Archimede');

UPDATE 	PARTITE
SET		Esito = 'B'
WHERE	IDPartita = 54276;

INSERT INTO PARTITE (IDPartita, PuntiPatta, Nickname_B, Nickname_N) VALUES (54277, 0, 'Archimede', 'Paperoga');

UPDATE 	PARTITE
SET		Esito = 'N'
WHERE	IDPartita = 54277;
