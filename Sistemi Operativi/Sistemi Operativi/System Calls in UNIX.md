**N.B.** Le system calls di UNIX sono attivabili attraverso chiamate a funzioni di librerie di C standard. Sono quindi funzioni di libreria che chiamano le system call corrispondenti.

### Per la gestione dei processi
Chiamate di sistema per:
- creazione di processi: [[fork()]]
- terminazione di processi: [[exit()]]
- sospensione in attesa della terminazione di figli: [[wait()]]
- sostituzione di codice e dati: [[exec()]]

### Per l'apertura di file
Chiamate di sistema per:
- apertura/creazione: [[open()]], [[creat()]]
- chiusura: [[close()]]
- lettura: [[read()]]
- scrittura: [[write()]]
- accesso "diretto": [[lseek()]]
- duplicazione di un elemento nella tabella dei file aperti di processo [[dup()]]

### Per la gestione di file
Chiamate di sistema per:
- cancellazione: [[unlink()]]
- linking: [[link()]]
- verifica dei diritti di accesso: [[access()]]
- verifica degli attributi: [[stat()]]
- modifica diritti di accesso: [[chmod()]]
- modifica proprietario: [[chown()]]

### Per la gestione di directory
Chiamate di sistema per:
- cambiare direttorio: [[chdir()]]
- apertura: [[opendir()]]
- chiusura: [[closedir()]]
- lettura: [[readdir()]]
- creazione: [[mkdir()]]

### Per la comunicazione con [[pipe]]
Vengono utilizzate le stesse syscalls che per i file.
In aggiunta: [[pipe()]]

### Per la comunicazione con [[fifo]]
Vengono utilizzate le stesse syscalls che per i file.
In aggiunta: [[mkfifo()]]

### Fallimento
In caso di fallimento, ogni system call ritorna un valore negativo.
UNIX prevede la variabile si sistema `errno`, alla quale il kernel assegna il codice di errore generato dall'ultima system call eseguita. Per interpretare il valore è possibile usare la funzione `perror("stringa")`, che stampa `"stringa"` seguita dalla descrizione testuale del codice di errore contenuto in `errno`.
La corrispondenza tra codici e descrizioni è contenuta in `<sys/errno.h>`

