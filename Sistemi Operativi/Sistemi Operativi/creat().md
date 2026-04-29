`int creat(char nomeFile[], int mode);`
- `nomefile` è il nome del file (relativo o assoluto)
- `mode` specifica i 12 bit di protezione per il nuovo file
Il valore restituito è il **file descriptor** associato al file, o -1 in caso di errore.
Se ha successo il file viene aperto in scrittura e l'I/O viene posizionato sul primo elemento.