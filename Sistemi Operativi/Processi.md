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

### [[Scheduling della CPU|Scheduling]] e Cambio di contesto
Lo [[Scheduling della CPU]] è fondamentale in ogni [[Sistema Multiprogrammato]] per massimizzare il tempo di utilizzo della CPU ed evitare che sia idle.

Il **Dispatcher** è la parte di SO che realizza il **context switch**.
Quando avviene un **cambio di contesto**, si usano i PCB per salvare e ripristinare gli stati dei processi.
![[Context switch.png]]

Lo **scheduler a breve termine** gestisce la **Coda dei processi pronti (ready queue)**, la cui strategia di gestione dipende dagli [[Algoritmi di Scheduling]] adottati dal SO. Questa coda contiene i PCB dei processi che si trovano in stato Ready.
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

### Interazioni tra processi
**Tipi di interazione:**
- **Cooperazione:** interazione prevedibile e desiderata, insita nella logica del programma concorrente. I processi cooperanti collaborano per il raggiungimento di un fine comune
- **Competizione:** interazione prevedibile ma non desiderata tra processi che interagiscono per sincronizzarsi nell'accesso a risorse comuni
- **Interferenza:** interazione non prevista e non desiderata, potenzialmente deleteria tra processi

Possiamo classificare i processi come:
- **Indipendenti:** se l'esecuzione di P1 non è influenzata da P2, e viceversa
- **[[Processi Interagenti|Interagenti]]**: se l'esecuzione di P1 è influenzata dall'esecuzione di P2, e/o viceversa.
	- **Cooperanti:** i processi interagiscono volontariamente per raggiungere obbiettivi comuni, fanno parte della stessa applicazione
	- **In competizione:** non necessariamente fanno parte della stessa applicazione, però interagiscono indirettamente per l'acquisizione di risorse comuni

**Supporto all'interazione**
L'interazione tra processi interagenti può essere realizzata mediante:
- **Memoria condivisa, modello ad ambiente globale, tra threads:** il SO consente ai threads di condividere variabili. L'interazione avviene tramite l'accesso a variabili condivise. Il SO prevede meccanismi per imporre dei **vincoli di sincronizzazione nell'accesso alle variabili condivise**
- **Scambio di messaggi, modello ad ambiente locale, tra processi pesanti:** i processi non condividono variabili e interagiscono mediante meccanismi di trasmissione/ricezione di messaggi. Il SO prevede meccanismi a supporto dello scambio di messaggi

### Operazioni sui processi
Ogni SO multiprogrammato prevede dei meccanismi per la gestione dei processi:
- [[Creazione di processi]]
- [[Terminazione di processi]]
- interazioni tra processi
Queste operazioni richiede il massimo livello di privilegio, quindi vanno eseguite in modo kernel 
=>  definizione di [[System Calls]]


