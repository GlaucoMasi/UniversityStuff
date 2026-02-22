Mentre nella [[struttura monolitica]] tutto il [[Kernel]] coincide con l'OS, nei sistemi a Microkernel la struttura del nucleo è ridotta a poche funzionalità di base, il resto è rappresentato da processi che eseguono in modo user.

Ha caratteristiche simili alla [[struttura modulare]]:
- affidabilità: separazione tra componenti
- possibilità di estensioni e personalizzazioni
- scarsa efficienza: molte chiamate a system call

**Esempi:** Minix, Mach, Hurd, BeOS/Haiku
