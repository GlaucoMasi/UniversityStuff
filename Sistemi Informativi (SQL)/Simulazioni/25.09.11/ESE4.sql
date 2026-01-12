-- 4.1)
create table E3 (
  K1E2 INT not null,
  K3 INT not null,
  C INT not null,
  primary key (K1E2, K3)
);

create table E1 (
  K1 INT not NULL PRIMARY KEY,
  A INT not null,
  B INT,
  Tipo SMALLINT not NULL CHECK(Tipo IN (1, 2)),
  K1E2 INT,
  K3 INT,
  CHECK((Tipo = 1 AND B IS NULL) OR (Tipo = 2 AND B IS NOT NULL)),
  check((K1E2 is not null and K3 is not null) or (K1E2 is null and K3 is null)),
  foreign key (K1E2, K3) references E3
);

alter table E3 add constraint REF_E3_E1
  foreign key (K1E2)
  references E1;

-- 4.2)
CREATE OR REPLACE TRIGGER CHECK_R2E2
BEFORE INSERT ON E3
REFERENCING NEW AS N
FOR EACH ROW
WHEN (
	EXISTS (
		SELECT	*
		FROM	E1
		WHERE	K1 = N.K1E2
		AND		Tipo = 1
	)
)
SIGNAL SQLSTATE '70001' ('La tupla inserita in E3 fa riferimento tramite R2 ad una tupla non di tipo 2');

CREATE OR REPLACE TRIGGER PUNTOC_E1
BEFORE INSERT ON E1
REFERENCING NEW AS N
FOR EACH ROW
WHEN (
	EXISTS (
		SELECT	*
		FROM	E1
		WHERE	K1 = N.K1E2
		AND		B > 10
	)
)
SIGNAL SQLSTATE '70001' ('La tupla inserita in E1 viola il punto c')
