-- 3.2)
create table APP (
	Nome varchar(20) not NULL PRIMARY KEY,
	Creatore varchar(20) not null
);

create table DOWNLOAD (
	URL varchar(20) not null,
	Nome varchar(20) not null,
	IDVersione char(5) not null,
	Numero INT not NULL DEFAULT 0,
	constraint ID_DOWNLOAD_ID primary key (Nome, IDVersione, URL),
	CONSTRAINT FKDOW_VER FOREIGN KEY (Nome, IDVersione) REFERENCES VERSIONE
);

create table VERSIONE (
	Nome varchar(20) not NULL REFERENCES APP,
	DataCreazione DATE not null,
	IDVersione char(5) not null,
	constraint ID_VERSIONE_ID primary key (Nome, IDVersione)
);

-- NB: oltre a quanto richiesto, non è necessario definire altri trigger che garantiscano l’integrità dei dati.
-- CHECK non supportato
--	alter table APP add constraint ID_APP_CHK
--	check(exists(select * from VERSIONE
--	   where VERSIONE.Nome = Nome));

-- 3.3)
INSERT INTO APP VALUES
	('YouTube', 'Google'),
	('Instagram', 'Meta');

INSERT INTO VERSIONE VALUES
	('YouTube', '05.05.2025', 'V1'),
	('YouTube', '07.05.2025', 'V2'),
	('YouTube', '09.05.2025', 'V3');

INSERT INTO DOWNLOAD VALUES
	('google.it', 'YouTube', 'V1', 4),
	('google.it', 'YouTube', 'V2', 2),
	('google.com', 'YouTube', 'V2', 100),
	('google.com', 'YouTube', 'V3', 59);

CREATE OR REPLACE TRIGGER InitDownloads
AFTER INSERT ON VERSIONE
REFERENCING NEW AS N
FOR EACH ROW
INSERT INTO DOWNLOAD(URL, Nome, IDVersione)
SELECT	DISTINCT Url, Nome, N.IDVersione
FROM	DOWNLOAD
WHERE	Nome = N.Nome;

INSERT INTO VERSIONE VALUES
	('YouTube', CURRENT DATE, 'V4');
