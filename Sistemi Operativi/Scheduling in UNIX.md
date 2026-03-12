L'obbiettivo è **privilegiare** i processi **interattivi**.
### Scheduling MLFQ (Multilevel Feedback Queue):
- Più livelli di priorità (circa 160): la priorità è rappresentata da un intero. Più grande è il suo valore, più bassa è la priorità
- Viene definito un valore di riferimento **pzero**:
	- Priorità ≥ **pzero**: processi di utente ordinari
	- Priorità < **pzero**: processi di sistema
- Ad ogni livello è associata una coda, gestita [[Round Robin]] e con quanto di tempo 100ms
- Aggiornamento **dinamico** delle priorità: ad ogni secondo viene ricalcolata la priorità di ogni processo
- La priorità di un processo decresce al crescere del tempo di CPU già utilizzato:
	- **feedback negativo/aging**
	- solitamente i processi interattivi usano poco la CPU, così vengono favoriti
- L'utente può decrescere la priorità di un job con il comando `nice`