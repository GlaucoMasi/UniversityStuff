-- Creazione tabelle
CREATE TABLE MODELLI (
	MODELLO VARCHAR(20) NOT NULL PRIMARY KEY,
	MARCA VARCHAR(20) NOT NULL,
	CILINDRATA INT NOT NULL CHECK (CILINDRATA > 0),
	ALIMENTAZIONE VARCHAR(10) NOT NULL,
	VELMAX INT NOT NULL CHECK (VELMAX > 0),
	PREZZOLISTINO DEC(8,2) NOT NULL CHECK (PREZZOLISTINO > 0)
)

CREATE TABLE RIVENDITORI (
	CODR CHAR(5) NOT NULL PRIMARY KEY,
	CITTA VARCHAR(20) NOT NULL
)

CREATE TABLE AUTO (
	TARGA CHAR(7) NOT NULL PRIMARY KEY,
	MODELLO VARCHAR(20) NOT NULL REFERENCES MODELLI,
	CODR CHAR(5) NOT NULL REFERENCES RIVENDITORI,
	PREZZOVENDITA DEC(8,2) NOT NULL CHECK (PREZZOVENDITA > 0),
	KM INT NOT NULL CHECK (KM >= 0),
	ANNO INT NOT NULL CHECK (ANNO >= 1900),
	VENDUTA CHAR(2) CHECK (VENDUTA = 'SI')
)

-- Insert dati
INSERT INTO RIVENDITORI VALUES 
('RIV01', 'Venezia'),
('RIV02','Bologna'),
('RIV03','Bologna'),
('RIV04','Rimini')

INSERT INTO MODELLI VALUES 
('Agila', 'Opel', 998, 'Benzina', 180, 12000.00),
('Aventador', 'Lamborghini', 6498, 'Benzina', 350, 432729.00),
('Ghibli', 'Maserati', 3799, 'Benzina', 326, 150000.00),
('Stratos', 'Lancia', 2419, 'Benzina', 230, 130000.00)

INSERT INTO AUTO VALUES 
('AG123AG', 'Agila', 'RIV03', 10500.00, 50000, 2018, NULL),
('AG234AG', 'Agila', 'RIV03', 9000.00, 70000, 2018, NULL),
('AV456AV', 'Aventador', 'RIV02',430000.00, 0, 2021, NULL),
('AV567AV', 'Aventador', 'RIV02', 400000.00, 0, 2022, 'SI'),
('GH789GH', 'Ghibli', 'RIV01', 90000.00, 0, 2020, 'SI'),
('GH890GH', 'Ghibli', 'RIV02', 100000.00, 30000, 2023, NULL),
('GH901GH', 'Ghibli','RIV03', 70000.00, 50000, 2020, 'SI'),
('ST123ST', 'Stratos', 'RIV04', 80000.00, 15000, 2010, 'SI'),
('ST234ST', 'Stratos','RIV04', 95000.00, 70000, 2022, 'SI')

-- Q1) Le Maserati ancora in vendita a Bologna a un prezzo inferiore al 70% del listino
SELECT 	m.*, a.*
FROM 	MODELLI m, RIVENDITORI r, AUTO a
WHERE 	a.MODELLO = m.MODELLO
AND		a.CODR = r.CODR
AND		r.CITTA = 'Bologna'
AND		m.MARCA = 'Maserati'
AND		a.VENDUTA IS NULL
AND		a.PREZZOVENDITA < m.PREZZOLISTINO*0.7

-- Q2) Il prezzo medio di un auto a benzina con cilindrata (cc) < 1000, almeno 5 anni di vita e meno di 80000 Km
SELECT	AVG(a.PREZZOVENDITA) AS PREZZOMEDIO
FROM	MODELLI m, AUTO a
WHERE	a.MODELLO = m.MODELLO
AND		m.ALIMENTAZIONE = 'Benzina'
AND		m.CILINDRATA < 1000
AND		a.ANNO+5 <= YEAR(CURRENT DATE)
AND		a.KM < 80000

-- Q3) Per ogni modello con velocità massima > 180 Km/h, il prezzo più basso a Bologna
SELECT	a.MODELLO, MIN(a.PREZZOVENDITA) AS PREZZOMIN_BOLOGNA
FROM 	MODELLI m, RIVENDITORI r, AUTO a
WHERE 	a.MODELLO = m.MODELLO
AND		a.CODR = r.CODR
AND		r.CITTA = 'Bologna'
AND		m.VELMAX > 180
GROUP BY 	a.MODELLO

-- Q4) Il numero di auto complessivamente trattate e vendute in ogni città
SELECT 	r.CITTA, COUNT(*) AS NUMERO_TRATTATE, COUNT(a.VENDUTA) AS NUMERO_VENDUTE
FROM	RIVENDITORI r, AUTO a
WHERE	r.CODR = a.CODR
GROUP BY r.CITTA

-- Q5) I rivenditori che hanno ancora in vendita almeno il 20% delle auto complessivamente trattate, ordinando il risultato per città e quindi per codice rivenditore
SELECT 	r.CITTA, r.CODR
FROM	RIVENDITORI r, AUTO a
WHERE	r.CODR = a.CODR
GROUP BY r.CITTA, r.CODR
HAVING	COUNT(a.VENDUTA) <= 0.8*COUNT(*)
ORDER BY r.CITTA, r.CODR

-- Q6) I rivenditori che hanno disponibili auto di modelli mai venduti prima da loro
SELECT 	DISTINCT a.CODR
FROM	AUTO a
GROUP BY a.MODELLO, a.CODR
HAVING	COUNT(a.VENDUTA) = 0

-- Q7) Per ogni rivenditore, il numero di auto vendute, solo se il prezzo medio di tali auto risulta minore di 90000 Euro
SELECT 	a.CODR, COUNT(*) AS NUMERO_VENDITE
FROM	AUTO a
WHERE	a.VENDUTA = 'SI'
GROUP BY a.CODR
HAVING	AVG(a.PREZZOVENDITA) < 90000

-- Q8) Per ogni auto A, il numero di auto vendute a un prezzo minore di quello di A
SELECT	a1.TARGA, COUNT(a2.TARGA) AS NUMERO_VENDITE
FROM	AUTO a1
LEFT JOIN AUTO a2
ON		a1.PREZZOVENDITA > a2.PREZZOVENDITA
AND		a2.VENDUTA = 'SI'
GROUP BY a1.TARGA

-- Q9) Per ogni anno e ogni modello, il rapporto medio tra prezzo di vendita e prezzo di listino, considerando un minimo di 2 auto vendute
SELECT	a.ANNO, a.MODELLO, AVG(a.PREZZOVENDITA/m.PREZZOLISTINO) AS RAPPORTO_MEDIO
FROM 	AUTO a, MODELLI m
WHERE	a.MODELLO = m.MODELLO
GROUP BY a.ANNO, a.MODELLO
HAVING	COUNT(a.VENDUTA) >= 2

-- Q10) Per ogni modello, i rivenditori che non ne hanno mai trattata una di quel modello 
SELECT	m.MODELLO, r.CODR 
FROM 	MODELLI m
CROSS JOIN RIVENDITORI r
LEFT JOIN AUTO a
ON (a.MODELLO = m.MODELLO AND a.CODR = r.CODR)
GROUP BY 	m.MODELLO, r.CODR
HAVING	COUNT(a.TARGA) = 0
