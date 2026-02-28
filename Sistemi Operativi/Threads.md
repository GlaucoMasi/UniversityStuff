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