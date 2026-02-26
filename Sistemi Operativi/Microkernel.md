Mentre nella [[struttura monolitica]] tutto il [[Kernel]] coincide con l'OS, nei sistemi a Microkernel la struttura del nucleo è ridotta a poche funzionalità di base (gestione essenziale di CPU e memoria), il resto (file system, ...) è rappresentato da processi che eseguono in modo user.

Ha caratteristiche simili alla [[struttura modulare]]:
- affidabilità: separazione tra componenti
- possibilità di estensioni e personalizzazioni
- scarsa efficienza: molte chiamate a system call anche per le operazioni più basilari

**Esempi:** Minix, Mach, Hurd, BeOS/Haiku
Interessante la storia di GNU/Hurd

### Kernel Ibridi
Sono microkernel che integrano a livello kernel funzionalità non essenziali.
**Esempi:** MSWinXP, XNU (macOS)


