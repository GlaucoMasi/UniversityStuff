[[Processi UNIX]]

**Processo = programma in esecuzione**
Il **programma** è un'entità **passiva**: un insieme di byte contenente le istruzioni da eseguire.
Il **processo** è un'entità **dinamica**:
- è l'unità di **lavoro/esecuzione** nel SO, ogni attività è rappresentata da un processo
- è l'**istanza** di un programma in **esecuzione**
**Processo = programma + contesto di esecuzione**

### Gestione dei processi
In un [[Sistema Multiprogrammato]] più processi possono essere presenti **simultaneamente**.
Per permettere l'esecuzione **concorrente** di più processi, il SO deve:
- creare/terminare i processi
- sospendere/ripristinare i processi
- permettere sincronizzazione/comunicazione tra processi

### Concetto di processo com entità attiva
Il processo è rappresentato da:
- **codice** (text) del programma eseguito
- **dati**: le variabili globali
- **program counter**
- altri **registri** di CPU
- **stack**: parametri, variabili locali a funzioni/procedure
Ed ad esso sono associate delle **risorse**:
- file aperti
- connessioni di rete utilizzate
- altre dispositivi di I/O in uso
- ...
**Processo = {PC, registri, stack, text, dati, risorse}**

### Stati di un processo
Un processo si può trovare in vari **stati**:
- **Init:** stato transitorio durante il quale il processo viene caricato in memoria e il SO inizializza i dati che lo rappresentano
- **Ready:** il processo è pronto per acquisire la CPU
- **Running:** il processo sta utilizzando la CPU
- **Waiting:** il processo è sospeso in attesa di un evento
- **Terminated:** stato transitorio relativo alla fase di terminazione e deallocazione del processo di memoria
![[Stati di un processo.png]]

In un sistema **monoprocessore** e **multiprogrammato**
- al massimo un processo si trova nello stato running
- più processi possono contemporaneamente trovarsi negli stati ready e waiting

### Rappresentazione dei processi
Ad ogni processo viene associata una struttura dati (**descrittore**): [[Process Control Block]] (**PCB**).

### Scheduling e Cambio di contesto
Quando avviene un **cambio di contesto**, si usano i PCB per salvare e ripristinare gli stati dei processi.
![[Context switch.png]]

Lo **scheduler a breve termine** gestisce la **Coda dei processi pronti (ready queue)**, la cui strategia di gestione dipende dalle politiche (algoritmi) di scheduling adottate dal SO. Questa coda contiene i PCB dei processi che si trovano in stato Ready.
Tra le altre strutture dati necessarie ci sono ad esempio le **code di waiting**, una per ogni tipo di attesa (dispositivi I/O, timer, ...), che contengono i PCB dei processi in stato Waiting in attesa di uno specifico tipo di evento.
![[Code di Scheduling.png]]

Il **cambio di contesto**:
- richiede scrittura/lettura verso/da memoria centrale delle info nei PCB dei processi interessati
- può richiedere onerosi trasferimento da/verso la memoria secondaria, per allocare/deallocare gli spazi di indirizzi dei processi

**Overhead generato da un SO:** tempo di CPU che il sistema operativo impiega per svolgere i propri compiti
Durante il tempo di overhead la CPU non può essere usata dai processi di utente.
**Misura dell'overhead**: $\displaystyle Overhead\% = \frac{Tempo_{OS} \cdot 100}{Tempo_{Utente}}$
Le operazioni di scheduling determinano un overhead che dipende da:
- frequenza di cambio di contesto
- costo dei trasferimenti da/verso la memoria
- **dimensione PCB**
Esistono SO che prevedono **processi leggeri** ([[Threads]]) che hanno la proprietà di **condividere codice e dati** con altri processi.
=> dimensione PCB ridotta => riduzione overhead

### Operazioni sui processi
Ogni SO multiprogrammato prevede dei meccanismi per la gestione dei processi:
- [[Creazione di processi]]
- [[Terminazione di processi]]
- interazioni tra processi
Queste operazioni richiede il massimo livello di privilegio, quindi vanno eseguite in modo kernel 
=>  definizione di [[System Calls]]

