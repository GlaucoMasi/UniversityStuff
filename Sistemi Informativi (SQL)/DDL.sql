-- Tipi di Dato
/*
    SMALLINT - Intero a 2 bit
    INT o INTEGER - Intero a 4 bit
    DEC(p, s) o DECIMAL(p, s) - Decimale con precisione p (numero totale di cifre) e scala s (numero di cifre dopo la virgola)
    CHAR(n) - Stringa di lunghezza fissa di n caratteri
    VARCHAR(n) - Stringa di lunghezza variabile fino a n caratteri
    REAL - Numero reale a 32 bit, precisione singola
    DOUBLE o FLOAT - Numero a virgola mobile 64 bit, doppia precisione
    DATE - Data dd.MM.yyyy o dd/MM/yyyy
    TIME - Ora hh:mm:ss
    TIMESTAMP - Data e ora yyyy-MM-dd.hh.mm.ss.nnnnnn
*/


-- Vincoli di Colonna
/*
    NOT NULL - La colonna non può contenere valori nulli
    DEFAULT 123 - Imposta un valore predefinito se non viene fornito alcun valore
    UNIQUE - I valori nella colonna devono essere unici -> NOT NULL obbligatorio
    PRIMARY KEY - Vincolo di primary key per singola colonna -> NOT NULL obbligatorio
    REFERENCES AltraTabella(Attributo) oppure AltraTabella - Vincolo di chiave esterna
    CHECK (<condizione>) - Impone una condizione sui valori della colonna
    CONSTRAINT NomeVincolo <vincolo> - Assegna un nome al vincolo
*/


-- Vincoli di Tabella
/*
    PRIMARY KEY (Attributo1, Attributo2, ...) - Vincolo di primary key per più colonne
    UNIQUE (Attributo1, Attributo2, ...) - Vincolo di unicità per più colonne
    FOREIGN KEY (Attributo1, ...) REFERENCES AltraTabella(Attributo1, ...) - Vincolo di chiave esterna per più colonne
    CHECK (<condizione>) - Impone una condizione sui valori della tabella
    CONSTRAINT NomeVincolo <vincolo> - Assegna un nome al vincolo
*/


-- Creazione di Tabelle
CREATE TABLE Students (
    Matricola   char(5)         NOT NULL PRIMARY KEY,
    CF          char(16)        NOT NULL UNIQUE,
    Cognome     varchar(30)     NOT NULL,
    DataNascita date            NOT NULL,
    Email       varchar(100)    DEFAULT 'guest'
);

CREATE TABLE Esami (
    Matricola   char(5)         NOT NULL REFERENCES Studenti(Matricola),
    CodCorso    int             NOT NULL REFERENCES Corsi,
    Voto        smallint        NOT NULL CONSTRAINT VotoValido      CHECK ((Voto >= 18) AND (Voto <=30)),
    Lode        char(2)         NOT NULL                            CHECK ((Lode = 'SI') OR (Lode = 'NO')),
    PRIMARY KEY (Matricola,CodCorso),
    CONSTRAINT LodeValida CHECK ((Voto = 30) OR (Lode = 'NO'))
);


-- Cancellazione di Tabelle
DROP TABLE Esami;
DROP TABLE Students;


-- Modifica di Tabelle
-- !! Per aggiungere un nuovo attributo NOT NULL, è necessario fornire un valore DEFAULT !!
ALTER TABLE Studenti (
    ADD COLUMN          Data            date
    ADD CONSTRAINT      DataNascita     CHECK (DataNascita <= CURRENT_DATE)
    DROP CONSTRAINT     CognomeValido
    DROP UNIQUE(Cognome,Matricola);
    ADD COLUMN          Istruzione      char(10)    NOT NULL    DEFAULT 'Laurea'
);