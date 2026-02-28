**N.B.** Le system calls di UNIX sono attivabili attraverso chiamate a funzioni di librerie di C standard. Sono quindi funzioni di libreria che chiamano le system call corrispondenti.

### Per la gestione dei processi
Chiamate di sistema per:
- creazione di processi: [[fork()]]
- terminazione di processi: [[exit()]]
- sospensione in attesa della terminazione di figli: [[wait()]]
- sostituzione di codice e dati: [[exec()]]

### Fallimento
In caso di fallimento, ogni system call ritorna un valore negativo.
UNIX prevede la variabile si sistema `errno`, alla quale il kernel assegna il codice di errore generato dall'ultima system call eseguita. Per interpretare il valore è possibile usare la funzione `perror("stringa")`, che stampa `"stringa"` seguita dalla descrizione testuale del codice di errore contenuto in `errno`.
La corrispondenza tra codici e descrizioni è contenuta in `<sys/errno.h>`

