Sono processi leggeri che condividono codice e dati con altri thread ad essi associati. Ogni thread è caratterizzato da un PC, registri e stack **privati** --> concorrenza
**Thread = {PC, registri, stack, ...}**

**Task:** insieme di thread che riferiscono lo stesso codice (text) e gli stessi dati.
**Task = {thread1, thread2, ..., threadN, text, dati, risorse}**

Un **processo pesante** equivale a un task con un solo thread (**single-threaded process**)
![[Processi single o multi threaded.png]]

**Vantaggi:**
- Condivisione di memoria
- Minor costo di context switch, perché il PCB di un thread non contiene alcuna informazione relativa a codice e dati globali
**Svantaggi:**
- Minor protezione: thread appartenenti allo stesso task possono modificare dati gestiti da altri thread, è fondamentale impostare **vincoli di sincronizzazione**

**Diverse soluzioni adottate:**
![[Soluzioni threads.png]]

### Realizzazione di thread
Molti SO offrono l'implementazione di thread, che può essere:
- **A livello kernel (WinXP, Linux, MacOSX, ...):**
	- il SO gestisce direttamente i cambi di contesto
		- tra thread dello stesso task (trasferimento di registri)
		- tra task
	- il SO fornisce strumenti per la sincronizzazione per l'accesso di thread a variabili comuni
- **A livello utente (Java thread, ...):**
	- il passaggio da un thread al successivo (nello stesso task) non richiede interruzioni al SO (**maggiore rapidità**)
	- il SO vede solo processi pensati, quindi la sospensione di un thread causa la sospensione di tutti i thread del task
- **Soluzioni miste (Solaris, ...):**
	- thread realizzati a entrambi i livelli

### Interazioni tra processi
Possiamo classificare i processi come:
- **indipendenti:** se l'esecuzione di P1 non è influenzata da P2, e viceversa
- **interagenti:** se l'esecuzione di P1 è influenzata dall'esecuzione di P2, e/o viceversa

**Tipi di interazione:**
- **Cooperazione:** interazione prevedibile e desiderata, insita nella logica del programma concorrente. I processi cooperanti collaborano per il raggiungimento di un fine comune
- **Competizione:** interazione prevedibile ma non desiderata tra processi che interagiscono per sincronizzarsi nell'accesso a risorse comuni
- **Interferenza:** interazione non prevista e non desiderata, potenzialmente deleteria tra processi

**Supporto all'interazione**
L'interazione tra processi interagenti può essere realizzata mediante:
- **Memoria condivisa, modello ad ambiente globale, tra threads:** il SO consente ai threads di condividere variabili. L'interazione avviene tramite l'accesso a variabili condivise. Il SO prevede meccanismi per imporre dei **vincoli di sincronizzazione nell'accesso alle variabili condivise**
- **Scambio di messaggi, modello ad ambiente locale, tra processi pesanti:** i processi non condividono variabili e interagiscono mediante meccanismi di trasmissione/ricezione di messaggi. Il SO prevede meccanismi a supporto dello scambio di messaggi
