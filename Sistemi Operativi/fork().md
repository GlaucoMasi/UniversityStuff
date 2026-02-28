`int fork(void);`
Consente a un processo di generare un processo figlio:
- padre e figlio condividono lo stesso codice
- il figlio eredita una **copia dei dati** (di utente e di kernel) del padre

Non richiede **parametri**.
**Restituisce** un intero che:
- per il processo creato vale 0
- per il processo padre è un valore **positivo** che rappresenta il PID del figlio
- è un valore **negativo** in caso di errore
Deve restituire a padre e figlio perchè il SO crea una copia esatta del processo padre, quindi è come se entrambi avessero chiamato fork(). Serve per distinguere chi è padre e chi è figlio.

### Effetti della fork()
- Allocazione di una nuova [[Process Structure]] nella Process Table associata al processo figlio e sua inizializzazione
- Allocazione di una nuova [[User Structure]] nella quale viene **copiata** la [[User Structure]] del padre 
- Allocazione dei segmenti di **dati e stack** del figlio nei quali vengono **copiati** dati e stack del padre
- Aggiornamento del riferimento text al codice eseguito, viene incrementato il contatore dei processi che lo stanno usando
![[Effetti della forkI().png]]

### Relazione padre-figlio dopo una fork()
- **Concorrenza:** padre e figlio procedono contemporaneamente
- **Spazio degli indirizzi duplicato:** ogni variabile del figlio è inizializzata con il valore assegnatole dal padre prima della fork()
- **[[User Structure]] duplicata:**
	- le risorse allocate al padre (file aperti, ...) prima della generazione sono condivise con i figli
	- le informazioni per la **gestione** dei segnali sono le stesse per padre e figlio (associazioni segnali-headers)
	- il figlio nasce con lo stesso PC del padre, quindi **la prima istruzione** eseguita dal figlio è quella che **segue immediatamente** fork()

**Esecuzione differenziate del padre e del figlio**
```C
...
if(fork() == 0) {
	// Codice eseguito dal figlio
} else {
	// Codice eseguito dal padre
}
...
```