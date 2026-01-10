CREATE TABLE PRODOTTI (
  ProdID INT NOT NULL PRIMARY KEY,
  Categoria VARCHAR(50) NOT NULL,
  Prezzo DECIMAL(6,2) NOT NULL
);

CREATE TABLE RECLAMI (
  RID INT NOT NULL PRIMARY KEY,
  ProdID INT NOT NULL,
  Data DATE NOT NULL,
  Cliente VARCHAR(50) NOT NULL,
  Motivo VARCHAR(255) NOT NULL,
  FOREIGN KEY (ProdID) REFERENCES PRODOTTI(ProdID)
);

CREATE TABLE ESITI (
  RID INT NOT NULL PRIMARY KEY,
  DataEsito DATE NOT NULL,
  Esito VARCHAR(20) NOT NULL,
  Rimborso DECIMAL(6,2),
  FOREIGN KEY (RID) REFERENCES RECLAMI(RID),
  CHECK (Esito IN ('RIMBORSO', 'RESPINTO', 'ALTRO'))
);

INSERT INTO PRODOTTI (ProdID, Categoria, Prezzo) VALUES
	(1, 'Elettronica', 120.00),
	(2, 'Elettronica', 800.00),
	(3, 'Abbigliamento', 40.00),
	(4, 'Casa', 25.00),
	(5, 'Giardinaggio', 60.00);

INSERT INTO RECLAMI (RID, ProdID, Data, Cliente, Motivo) VALUES
	(101, 1, '2024-01-10', 'Mario Rossi', 'Difetto accensione'),
	(102, 1, '2024-02-05', 'Luigi Verdi', 'Graffio scocca'),
	(103, 1, '2024-02-20', 'Anna Bianchi', 'Mancano cavi'),
	(104, 2, '2023-05-15', 'Giovanni Neri', 'Non funziona'),
	(105, 3, '2024-03-01', 'Elena Gialli', 'Taglia errata'),
	(106, 4, '2024-06-10', 'Paolo Blu', 'Rotto trasporto'),
	(107, 5, '2024-01-15', 'Sara Viola', 'Arrivato secco'),
	(108, 5, '2024-01-18', 'Luca Marrone', 'Manca pezzo');

INSERT INTO ESITI (RID, DataEsito, Esito, Rimborso) VALUES
	(101, '2024-01-12', 'RIMBORSO', 120.00),
	(102, '2024-02-06', 'RIMBORSO', 60.00),
	(103, '2024-02-25', 'RESPINTO', NULL),
	(105, '2024-03-05', 'RIMBORSO', 35.00),
	(106, '2024-06-12', 'RESPINTO', NULL),
	(107, '2024-01-20', 'RIMBORSO', 60.00);
