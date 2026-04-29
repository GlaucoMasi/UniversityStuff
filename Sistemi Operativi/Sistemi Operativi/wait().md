`int wait(int *status);`
Consente al padre di rilevare lo stato di terminazione di un figlio.

Il **parametro status** è l'indirizzo della variabile in cui viene memorizzato lo stato di terminazione del figlio.
Il **risultato** prodotto dalla wait() è il PID del processo terminato, oppure un codice di errore negativo.

### Effetti della wait
- Il processo che la chiama può avere figli:
	- Se tutti i figli non sono ancora terminati, il processo si **sospende** in attesa della terminazione del primo di essi
	- Se almeno un figlio è già terminato ed il suo stato non è ancora stato rilevato (cioè il figlio è in stato Zombie), wait() ritorna immediatamente con il suo stato di terminazione
- Se non esiste neanche un figlio, wait() è **sospensiva** e ritorna un codice di errore negativo

### Rilevazione dello stato
Nell'ipotesi che lo status sia un **intero a 16 bit (2 bytes)**:
- se il **byte meno significativo** di status è zero, il più significativo rappresenta lo stato di terminazione ([[Terminazione di processi|terminazione volontaria]], ad esempio con exit)
- in caso contrario, il **byte meno significativo** di status, non nullo, descrive il segnale che ha terminato il figlio ([[Terminazione di processi|terminazione involontaria]])

È necessario conoscere la rappresentazione di status.
Lo standard POSIX.1 prevede delle **macro** definite nell'header file `<sys/wait.h>` per l'analisi dello stato di terminazione. In particolare:
- `WIFEXITED(status)` restituisce vero se il processo figlio è terminato volontariamente. In questo caso `WEXITSTATUS(status)` restituisce lo stato
- `WIFSIGNALED(status)` restituisce vero se il processo figlio è stato terminato involontariamente. In questo caso `WTERMSIGN(status)` restituisce il numero del segnale che ha causato la terminazione
