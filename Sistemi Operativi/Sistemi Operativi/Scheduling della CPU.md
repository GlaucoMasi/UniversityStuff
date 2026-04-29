[[Scheduling in UNIX]]

Fondamentale nei [[Sistema Multiprogrammato|sistemi multiprogrammati]], per massimizzare l'utilizzo della CPU commutandone l'uso tra i vari processi.
Coinvolge due parti del Sistema Operativo: lo **Scheduler** e il **Dispatcher**.
**SCHEDULER = POLITICHE**
**DISPATCHER = MECCANISMI**

Ci sono due tipi di scheduling:
- **long-term scheduling:** scegli quali processi caricare in [[Memoria Centrale]]
- **short-term scheduling:** sceglie tra i processi pronti il prossimo a cui assegnare l'uso della CPU. Questi processi si trovano nella **ready queue**, che contiene i loro [[Process Control Block|descrittori]] ed è realizzante mediante [[Algoritmi di Scheduling|politiche di scheduling]]
![[Code di Scheduling.png]]

### CPU burst & I/O burst
Ogni processo alterna:
- **CPU burst:** fasi in cui viene impiegata soltanto la CPU senza interruzioni dovute a operazioni di I/O
- **I/O burst:** fasi in cui il processo effettua I/O da/verso un dispositivo. In queste fasi la CPU non viene utilizzata, quindi in un sistema multiprogrammato lo short-term scheduler riassegna la CPU ad un altro processo

In base alle caratteristiche dei programmi eseguiti dai processi è possibile classificarli in:
- **I/O bound**
- **CPU bound**
In base a quelle delle due fasi appare più prevalentemente.


