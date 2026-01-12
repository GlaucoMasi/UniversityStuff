-- 4.1)
create table E2 (
  B INT not null,
  K1 INT not NULL PRIMARY KEY,
  A INT not null
);

create table E3 (
  C INT not null,
  K1 INT not NULL PRIMARY KEY,
  A INT not null
);

create table E4 (
  K4 INT not NULL PRIMARY KEY,
  D INT not null,
  K1_E2 INT REFERENCES E2,
  K1_E3 INT REFERENCES E3,
  CHECK((K1_E2 IS NULL AND K1_E3 IS NOT NULL) OR (K1_E2 IS NOT NULL AND K1_E3 IS NULL))
);

-- 4.2)
CREATE OR REPLACE TRIGGER C_E4
BEFORE INSERT ON E4
REFERENCING NEW AS N
FOR EACH ROW
WHEN (
	EXISTS (
		SELECT	*
		FROM	E2 e2, E3 e3
		WHERE	e2.K1 = COALESCE(N.K1_E2, N.K1_E3)
		AND		e3.K1 = COALESCE(N.K1_E2, N.K1_E3)
	)
)
SIGNAL SQLSTATE '70000' ('La tupla inserita in E4 è associata ad una istanza di E1 che è istanza sia di E2 che di E3');

CREATE OR REPLACE TRIGGER C_E2
BEFORE INSERT ON E2
REFERENCING NEW AS N
FOR EACH ROW
WHEN (
	EXISTS (
		SELECT	*
		FROM	E4
		WHERE	K1_E3 = N.K1
	)
)
SIGNAL SQLSTATE '70001' ('Esiste già una istanza in E3 con lo stesso K1 associata ad una istanza di E4');

CREATE OR REPLACE TRIGGER C_E3
BEFORE INSERT ON E3
REFERENCING NEW AS N
FOR EACH ROW
WHEN (
	EXISTS (
		SELECT	*
		FROM	E4
		WHERE	K1_E2 = N.K1
	)
)
SIGNAL SQLSTATE '70002' ('Esiste già una istanza in E2 con lo stesso K1 associata ad una istanza di E4');
