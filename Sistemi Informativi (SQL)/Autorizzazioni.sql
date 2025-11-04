-- Ogni operazione deve essere autorizzata
-- I privilegi vengono concessi e revocati con le istruzioni GRANT e REVOKE
-- Un utente che ha ricevuto un certo privilegio può concederlo ad altri utenti solo se è stato autorizzato a farlo


/* Formato GRANT per assegnare privilegi su schemi
GRANT <privilegio>
ON SCHEMA <nome schema>
TO { <lista di utenti e gruppi> | PUBLIC }
[ WITH GRANT OPTION ]
*/

-- La clausola WITH GRANT OPTION autorizza l'utente a passare l'autorità ad altri utenti
-- I privilegi possibili sono:
-- 1. CREATEIN: creare oggetti nello schema
-- 2. ALTERIN: modificare oggetti nello schema
-- 3. DROPIN: eliminare oggetti dallo schema


/* Formato GRANT per assegnare privilegi su tables e views
GRANT { ALL | <lista di privilegi>}
ON [ TABLE ] <nome table>
TO { <lista di utenti e gruppi> | PUBLIC }
[ WITH GRANT OPTION ]
*/

-- I privilegi possibili sono:
-- 1. CONTROL: comprende tutti i privilegi e consente di concederli ad altri utenti. Può essere concesso solo da qualcuno con autorità SYSADM o DBADM
-- 2. ALTER
-- 3. DELETE
-- 4. INDEX
-- 5. INSERT
-- 6. REFERENCES: consente di creare vincoli di chiave esterna che fanno riferimento alla tabella. Sennò si potrebbe bloccare ogni cancellazione sulla tabella referenziata
-- 7. SELECT
-- 8. UPDATE

-- Si può concedere un privilegio anche su un singolo attributo di una tabella o view
GRANT SELECT, UPDATE(A1, A2)
ON TABLE R1
TO USER USER1, USER USER2
WITH GRANT OPTION


/* Formato REVOKE per revocare privilegi su tables e views
REVOKE { ALL | <lista di privilegi>}
ON [ TABLE ] <nome table>
FROM { <lista di utenti e gruppi> | PUBLIC }
*/

-- Per eseguire REVOKE, l'utente deve avere il privilegio CONTROL sulla tabella o view oppure l'autorità SYSAMD o DBADM
-- Non è possibile revocare un privilegio su un singolo attributo di una tabella o view, bisogna revocarli tutti e concendere di nuovo quelli desiderati