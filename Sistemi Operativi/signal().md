Ogni processo può gestire esplicitamente un segnale con questa [[System Calls|system call]].
`void (* signal(int sig, void (*func)()))(int)`
- `sig` è l'intero che individua il segnale da gestire
- il parametro `func` è un **puntatore a una funzione** che indica l'azione da associare al segnale. `func` può:
	- puntare alla routine di gestione (handler)
	- valere `SIG_IGN` (nel caso di segnale ignorato)
	- valere `SIG_DFL` (nel caso di azione default)
- `signal()` ritorna un puntatore a una funzione:
	- al precedente gestore del segnale
	- la costante `SIG_ERR` in caso di errore

**Caratteristiche dell'handler:**
- deve prevedere un parametro formale di tipo int che rappresenta il numero del segnale effettivamente ricevuto. Utile per utilizzare un solo handler per più segnali.
- non deve restituire alcun risultato
``` C
void handler(int signum) {
	...
	return;
}
```

Si può usare il comando di shell `kill -signal_num pid` per inviare segnali ai processi.

**Esempio SIGCHILD**
```C
#include <signal.h>
#include <stdio.h>

void handler (int signum) { 
	int S, pid;
	wait(&S);
	if ((char)S==0)
		printf("term. volontaria con stato %d", S>>8);
	else
		printf("term. involontaria per segnale %d\n",(char)S);
}

main() { 
	int PID, i;
	signal(SIGCHLD,handler);
	PID=fork();
	if (PID>0) { /* padre */
		for (i=0; i<10000000; i++) /* attività del padre..*/
			printf("il padre sta lavorando...\n");
		exit(0); }
	else { /* figlio */
		signal(SIGCHLD,SIG_DFL); // RESET
		for (i=0; i<1000; i++); /* attività del figlio..*/
			exit(1); }
}
```


