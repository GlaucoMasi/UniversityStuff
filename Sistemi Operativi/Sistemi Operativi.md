[[UNIX]]
### SO e Hardware
Il SO è un programma (o un insieme di programmi) che fa da **intermediario** tra l'utente e l'hardware del computer:
- fornisce una visione **astratta e semplificata** dell'HW
- **gestisce** in modo efficace ed efficiente le **risorse** del sistema
- mette a disposizione un ambiente di **esecuzione e sviluppo** per i programmi degli utenti
![[Sistema Operativo base.png]]

Interfaccia i programmi con le risorse HW mappandole in **risorse logiche**, accessibili attraverso interfacce:
- CPU  -->  [[Processi]]
- [[Memoria Secondaria]]  -->  [[File System]]
- [[Memoria Centrale]]  -->  [[Memoria Virtuale]]
- [[Dispositivi di I∕O]]  -->  ...
- [[Interfacce di rete]]  -->  ...

### Aspetti importanti di un SO
- **Architettura:** organizzazione, componenti e relazioni tra componenti
- **Efficienza:** massimizzare l'utilizzo delle risorse disponibili, minimizzando i tempi di esecuzione
- **Condivisione:** quali risorse condividere risorse tra utenti e/o programmi, e come
- **Estendibilita:** possibilità/semplicità di aggiungere funzionalità al sistema
- **Protezione e Sicurezza:** impedire interferenze tra programmi/utenti e attacchi dalla rete
- **Affidabilità/Tolleranza ai guasti:** probabilità di malfunzionamenti e reazione del SO, a livello di SW e HW
- **Conformità a standard:** portabilità, estendibilità, apertura

### Cenni storici
- **Prima generazione:** basata su valvole termoioniche, **non** è presente il sistema operativo.
- **Sistemi batch semplici:** i job sono aggregati in **batch** con esigenze simili, da eseguire in modo sequenziale. Il [[Monitor]] gestisce l'avvicendamento dei job. In memoria risiede il SO e al massimo un solo job. Non c'è interazione con l'utente e la CPU rimane **inutilizzata** mentre il job è in attesa di un evento.
- **Sistemi batch semplici con Spooling:** viene introdotto un **disco** impiegato come buffer per memorizzare in anticipo i programmi, leggere in anticipo i dati, memorizzare temporaneamente i risultati e caricare codice e dati del job successivo. Così si può sovrapporre l'I/O di un job con l'elaborazione di un altro job.
- **Sistemi batch multiprogrammati:** sul disco viene precaricato un **pool** di job, il SO seleziona un insieme di job e li carica in memoria centrale (**long-term scheduling**), il SO ne seleziona uno a cui assegnare la CPU (**short-term scheduling**) e se il job corrente si pone in attesa, il sistema operativo riassegna la CPU (**context switch**) salvando le informazioni contenute nei registri della CPU relative allo stato di esecuzione del job corrente e caricando le informazioni relative allo stato nel nuovo job. Dato che nella memoria centrale saranno caricati più job, è necessario introdurre una protezione.
- **Sistemi time-sharing:** nascono dalla necessità di introdurre **interattività con l'utente** e **multi-utenza**. Il SO presenta diverse [[macchine virtuali]] agli utenti e **interrompe** l'esecuzione di un job dopo un intervallo di tempo prefissato (**time slice**) riassegnando la CPU. Questi sistemi necessitano i concetti di [[memoria virtuale]], per la gestione e [[protezione]] della memoria, scheduling della CPU, sincronizzazione e comunicazione tra i job, per avere interazione e prevenire deadlocks, e accesso **on-line** al [[file system]].

### Architettura hardware
![[Architettura di un sistema di elaborazione.png]]
**Funzionamento a interruzioni**: le varie componenti (HW e SW) interagiscono con il SO mediante [[Interruzioni]] 

### Componenti
- Gestione dei [[processi]]
- Gestione della [[memoria centrale]]
- Gestione di [[memoria secondaria]] e [[file system]]
- Gestione dei [[Dispositivi di I∕O]]
- [[Protezione]] e [[Sicurezza]]
- [[Interfaccia utente e programmatore]]
Le componenti non sono indipendenti tra loro, ma **interagiscono**.

### Organizzazione delle varie componenti
Ci sono varie soluzioni:
- [[Struttura Monolitica]]
- [[Struttura Modulare]]
- [[Microkernel]] (e Kernel Ibridi)
- [[Macchine virtuali]]
