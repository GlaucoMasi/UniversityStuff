`unsigned int alarm(unsigned int N);`
- imposta un timer che dopo N secondi invierà al processo il segnale SIGARLM
- ritorna 0 se non vi erano time-out impostati in precedenza, oppure il numero di secondi mancante allo scadere del time-out precedente
**Non è sospensiva** e l'azione di **default** associata è la **terminazione**.