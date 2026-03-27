Per verificare i diritti di un utente di accedere a un file:
`int access(char *pathname int amode);`
Il parametro `amode` rappresenta il diritto da verificare e può essere:
- 00: Existence
- 01: Execute access
- 02: Write access
- 04: Read access
Restituisce 0 in caso di successo (diritto verificato), -1 altrimenti.
`access` fa uso del **real uid** del processo, non dell'**effective uid**