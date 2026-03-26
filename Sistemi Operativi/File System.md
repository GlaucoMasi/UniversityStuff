Ogni sistema di elaborazione dispone di uno o più dispositivi per la memorizzazione persistente delle informazioni, chiamati [[memoria secondaria]].

Il compito del SO è fornire una visione **logica** **uniforme** della memoria secondaria, **indipendente** dal tipo e dal numero dei dispositivi. Deve:
- realizzare il concetto astratto di **file**, come unità di memorizzazione logica
- fornire una struttura astratta per l'organizzazione dei file (**cartella o directory**)

Inoltre si deve occupare di:
- **creazione/cancellazione** di file o directory
- manipolazione di file/directory
- associazione tra file e dispositivi di memorizzazione secondaria

**Filecentricità**: file, directory e dispositivi di I/O vengono presentati agli utenti e ai programmi in modo **uniforme**.

Il **file system** è quella componente del SO che fornisce i meccanismi di **accesso** e **memorizzazione** delle informazioni allocate in memoria di massa. Realizza i concetti **astratti** di:
- [[File]]: unità logica di memorizzazione
- [[Direttorio]]: insieme di file e direttori
- **Partizione**: insieme di file associato ad un particolare dispositivo fisico, o porzione di esso. Una singola unità di memorizzazione secondaria può contenere più partizioni.

# Organizzazione
La struttura di un file system può essere rappresentata da un insieme di componenti organizzate in vari livelli:
![[Organizzazione file system.png]]
- **Struttura logica:** presenta alla applicazione una visione **astratta** delle informazioni memorizzate, basata sul [[file]], [[Direttorio|directories]], partizioni, ..., e realizza le operazioni di gestione di file e directory: copia, cancellazione, spostamento, ...
- **Accesso:** definisce e realizza i meccanismi per accedere al **contenuto** dei file, in particolare:
	- Definisce l'unità di trasferimento da/verso file: **record logico**
	- Realizza i **metodi di accesso** (sequenziale, casuale, ad indice)
	- Realizza i meccanismi di **protezione**
- **Organizzazione fisica:** rappresentazione di file e directory sul dispositivo:
	- **Allocazione** dei file sul dispositivo, ovvero il mapping di record logici sui blocchi (unità di memorizzazione), secondo vari metodi di allocazione
	- **Rappresentazione della struttura logica** sul dispositivo
- **Dispositivo virtuale:** presenta una vista astratta del dispositivo, appare come una sequenza di blocchi, ognuno di dimensione data costante

### File System Mounting
Molti SO richiedono il mounting esplicito all'interno del file system prima di poter usare una nuova unità disco o una partizione.

# Accesso
### Operazioni sui file
Il SO consente l'accesso **on-line** ai file, se un processo modifica il contenuto di un file, tale cambiamento è immediatamente visibile a tutti gli altri processi.

**Tipiche Operazioni:**
- **Creazione:** allocazione di un file in memoria secondaria e inizialmente dei suoi attributi
- **Lettura** di record logici dal file
- **Scrittura:** inserimento di nuovi record logici all'interno di un file
- **Cancellazione:** eliminazione del file dal file system

Per evitare di dover localizzare gli indirizzi dei record logici, gli attributi del file come i diritti di accesso, e altro:
- Il SO mantiene in memoria una struttura che registra i file attualmente in uso (**aperti**). La **tabella dei file aperti** contiene per ogni file aperto il puntatore al file, la posizione sul disco, ...
- Viene fatto il **memory mapping** dei file aperti: i file aperti vengono temporaneamente copiati in memoria centrale per accesso più veloce
La tabella e il mapping vengono aggiornati alla apertura e chiusura dei file.

### Struttura interna dei file
Le applicazioni vedono il contenuto di ogni file come un insieme di **record logici**.
Al livello più basso (**dispositivo virtuale**) ogni dispositivo di memorizzazione secondaria viene partizionato in **blocchi**, o **record fisici**.
#### Blocchi & record logici
**Record logico:** unità di trasferimento logico nelle operazioni di accesso al file.
**Blocco:** unità di trasferimento fisico nelle operazioni di I/O da/verso il dispositivo.
Uno dei compiti del file system è stabilire una corrispondenza tra record logici e blocchi.
**La dimensione dei blocchi è fissa, quella dei record è variabile**.
Di solito la dimensione del blocco è **molto maggiore** della dimensione del record logico.
![[Blocchi & record logici.png]]

Il metodo di [[File#Metodi di Accesso|accesso a file]] è **indipendente** dal tipo di dispositivo utilizzato e dalla tecnica di allocazione dei blocchi in memoria secondaria.

# Protezione
Il proprietario/creatore di un file dovrebbe avere la possibilità di controllare quali azioni sono consentite sul file e da parte di chi. I diritti che si possono avere su un file includono, ad esempio: Read, Write, Execute, Append, Delete, List, e altri.

#### Liste di accesso e gruppi (es. UNIX)
**Modalità di accesso:** read, write, execute (RWX)
Ci sono tre classi di utenti: user, group e others access
L'amministratore può creare gruppi, con nomi unici, e modificare gli utenti di un gruppo
Dato un file o una directory, si devono definire le regole di accesso desiderate
**Cambio gruppo:** `chgrp G game`  -->  Il gruppo del file game è G
**Modifica diritti di accesso:** `chmod 761 game` 
=> Il proprietario può fare tutto, il gruppo può leggere e scrivere, gli altri solo eseguire

# Organizzazione fisica
Il SO si occupa della realizzazione del file system sui dispositivi di memorizzazione secondaria. È necessario allocare i blocchi fisici, gestire lo spazio libero e realizzare dei descrittori.
### Metodi di allocazione
Il **blocco** è l'unità di allocazione sul disco. Ogni blocco contiene un insieme di record logici contigui.
Tecnica più comuni per l'allocazione:
- **Allocazione contigua,** ogni file è mappato su un insieme di blocchi fisicamente contigui:
	- **Vantaggi:**
		- Costo della ricerca di un blocco
		- Possibilità di accesso sequenziale e diretto
	- **Svantaggi:** 
		- Individuazione dello **spazio libero** per l'allocazione di un nuovo file
		- **Frammentazione esterna,** rimangono zone sempre più piccole man mano che si riempie il disco, necessitando azioni di **compattazione**
		- **Aumento dinamico** delle dimensioni di file
- **Allocazione a lista concatenata,** i blocchi di un file sono organizzati in una linked list
	- **Vantaggi:**
		- Non c'è **frammentazione esterna**
		- Minor costo di allocazione
	- **Svantaggi:**
		- Errore in caso di **link danneggiato**
		- **Maggior occupazione,** poiché bisogna allocare anche i puntatori
		- **Costo della ricerca** di un blocco, bisogna seguire la lista
	- **Tabella di allocazione dei file (FAT):** per ogni disco/partizione c'è una tabella (FAT) in cui ogni indice rappresenta un blocco fisico e contiene il puntatore al blocco successivo, fino a quello che punta a EOF. In questo modo il puntatore non è dentro al blocco stesso. Spesso questa tabella è in RAM per accessi veloci.
- **Allocazione a indice,** a ogni file è associato un blocco (**blocco indice**) in cui sono contenuti tutti gli indirizzi dei blocchi su cui è allocato il file
	- **Vantaggi:**
		- Stessi dell'allocazione a lista
		- Possibilità di **accesso diretto**
		- Maggiore velocità di accesso
	- **Svantaggi:**
		- Possibile scarso utilizzo dei **blocchi indice**

In generale, gli aspetti caratterizzanti sono:
- Grado di utilizzo della memoria
- Tempo di accesso medio al blocco
- Realizzazione dei metodi di accesso
Alcuni SO utilizzano più metodi di allocazione.
Ad esempio, allocazione contigua per file piccoli e a indice per grandi.