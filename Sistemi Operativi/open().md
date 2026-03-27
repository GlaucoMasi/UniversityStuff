`int open(char nomeFile[], int flag, [int mode]);`
- `nomefile` è il nome del file (relativo o assoluto)
- `flag` esprime il modo di accesso, ad esempio `O_RDONLY` o `O_WRONLY`
- `mode` è un parametro richiesto soltanto se l'apertura determina la creazione del file (flag `O_CREAT`), in quel caso specifica i bit di protezione
Il valore restituito è il **file descriptor** associato al file, o -1 in caso di errore.
L'I/O pointer viene posizionato sul primo elemento, o sull'ultimo in caso di `O_APPEND`

Ai tre modi di apertura (lettura, scrittura e append) si possono abbinare altri modi, mediante il connettore |.
Ad esempio alla scrittura si possono abbinare `O_CREAT` o `O_TRUNC`, che tronca la lunghezza a 0.
C'è anche la possibilità di **alternare** operazioni di **lettura e scrittura** con `O_RDWR`