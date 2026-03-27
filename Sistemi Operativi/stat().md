Per leggere gli attributi di un file:
`int stat(const char *path, struct stat *buf);`
- `path` rappresenta il nome del file
- `buf` è il puntatore a una struttura di tipo `stat`, in cui vengono restituiti gli attributi del file
Ritorna 0 in caso di successo, -1 in caso di errore.

###### Struttura stat
![[Struttura stat.png]]
Esistono delle macro e costanti per interpretare il valore di st_mode, ad esempio:
- `S_ISREG(mode)`: è un file regolare?
- `S_ISDIR(mode)`: è una directory?
- `S_ISCHR(mode)`: è un dispositivo a caratteri (file speciale)?
- `S_ISBLK(mode)`: è un dispositivo a blocchi (file speciale?