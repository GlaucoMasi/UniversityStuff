First-Come-First-Served: la coda viene gestita in modo FIFO. L'algoritmo è **non pre-emptive** e i processi sono schedulati secondo **l'ordine di arrivo** nella coda.

**Problema:** non si può modificare l'ordine dei processi
- Bel caso di processi in attesa dietro a processi con lunghi CPU burst, il tempo di attesa è alto
- Possibilità dell'**effetto convoglio**: scarso grado di utilizzo della CPU se molti processi I/O bound seguono un processo CPU bound.
![[Effetto Convoglio nel FCFS.png]]