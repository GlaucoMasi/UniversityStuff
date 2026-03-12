Nei SO reali, spesso si combinano diversi algoritmi di scheduling.
**Esempio: Multiple Level Feedback Queues**
- Più code, ognuna per un tipo di job diverso (batch, interactive, CPU-bound, ...)
- Ogni coda ha una diversa priorità  -->  Scheduling delle code con priorità
- Ogni coda viene gestita con un algoritmo di scheduling distinto, eventualmente diverso
- I processi possono muoversi da una coda all'altra in base alla loro storia:
	- Passaggio da priorità bassa ad alta se è in attesa da molto (**promotion**)
	- Passaggio da priorità alta a bassa se ha già utilizzato molto CPU time (**aging**)

**Esempio reale**
![[Multi Level Feedback Queue.png]]