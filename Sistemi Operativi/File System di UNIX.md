# Struttura logica
![[Struttura logica UNIX.png]]
- **Omogeneità/filecentricità:** tutto è file
- **Categorie di file:**
	- File ordinari
	- Direttori: rappresentati da file contenti un insieme di **record logici** con la struttura: nome relativo, i-number, relativi ai file contenuti nel direttorio
	- File **speciali:** rappresentano dispositivi fisici, nel direttorio /dev
- Ogni file può avere **uno o più** nomi **simbolici**, o **link**, ma vi è associato **uno e un solo descrittore** ([[i-node]]), univocamente identificato da un intero (**i-number**)
- La struttura logica del FS è rappresentata da un **grafo aciclico diretto**

# Organizzazione fisica
Il file system **Ext** (Extended file system) è il capostipite di una famiglia di file systems ampiamente diffusi nei sistemi della famiglia UNIX.
#### Caratteristiche dell'Ext:
- Il [[File System#Metodi di allocazione|Metodo di Allocazione]] è ad **indice**, a più livelli di indirizzamento
- Formattazione del dispositivo di memorizzazione secondaria in blocchi fisici (512-4096 bytes)
- La superficie del disco è partizionata in 4 regioni:
	- **Boot Block:** contiene le procedure di inizializzazione del sistema, da eseguire al bootstrap
	- **SuperBlock:** fornisce i limiti delle 4 regione e i puntatori alle liste dei blocchi liberi e degli [[i-node]] liberi
	- **Data Blocks:** disponibile per la memorizzazione dei file, contiene i blocchi allocati e quelli liberi, organizzati in una lista collegata
	- **i-List:** contiene la lista di tutti i descrittori ([[i-node]]) dei file, direttori e dispositivi presenti nela file system. L'accesso avviene attraverso l'i-number

# Indirizzamento
L'allocazione del file non è su blocchi fisicamente contigui, nell'[[i-node]] sono contenuti **puntatori a blocchi**, 13 ad esempio, dei quali:
- **Indirizzamento diretto:** i primi 10 indirizzi si riferiscono a blocchi di dati
- **1 Livello di indirettezza:** L'11esimo si riferisce ad un blocco contenente indirizzi di blocchi dati
- **2 e 3 Livelli di indirettezza:** 12esimo e 13esimo
![[Indirizzamento.png]]
**Esempio hp:** blocchi da 512 byte (0.5 KB) e indirizzi a 32 bit (4 byte)
10 blocchi di dati accessibili direttamente, 128 con indirezione singola, 128\*128 con indirezione doppia e 128\*128\*128 con indirezione tripla.
Quindi la dimensione massima del file è 1GB+8MB+64KB+5KB.
L'accesso a file di **piccole dimensione** è **più veloce** rispetto al caso di file grandi

# File system Linux
#### Prime versioni:
- Indirizzibilità 64MB per file e filesystem
- Nomi dei file a 16 caratteri
- Gestione inefficiente dei blocchi (bitmap di blocchi e inode liberi)
#### Ext1:
- Indirizzibilità 1GB per file, 4GB per filesystem (1GB/4GB)
- Gestione dei blocchi liberi con liste

#### Ext2:
- Indirizzibi###lità 16GB/4T
- **Flessibilità:** possibilità di stabilire dimensione del blocco e i-list
- **Efficienza:** preallocazione dei blocchi e **gruppi di blocchi**
##### Block groups:
Il file system è organizzato fisicamente in **gruppi di blocchi**, ognuno organizzato così:
![[Block groups.png]]
Località di inode e relativi file e dei blocchi allocati a uno stesso file per tempi di accesso ridotti. Replicazione del superblock per **tolleranza ai guasti**.
#### Ext3 e Ext4: estensioni Ext2 per tolleranza ai guasti

### Virtual File System
Linux prevede l'integrazione con filesystem diversi da Ext, grazie al VFS:
- Intercetta ogni syscall relativa all'accesso al file system e se necessario provvede al collegamento con file system esterni Per la gestione di directory
- I filesystems sono mappati su internal objects nel kernel, ognuno dei quali include informazioni specifiche sulla risorsa che rappresenta
![[VFS.png]]

# Accesso a File
L'accesso è sequenziale. C'è un **I/O Pointer** che registra la posizione corrente e c'è assenza di strutturazione. Un file è come una sequenza di bytes.
Ci sono vari modi di accesso, sempre subordinato all'operazione di apertura.

### Tabella dei file aperti di processo e file descriptor
- A ogni processo è associata una tabella dei file aperti di dimensione limitata
- Ogni elemento è individuato da un indice intero, il **file descriptor**
- I file descriptor 0, 1, 2 individuano rispettivamente lo standard input, output e error
- La tabella dei file aperti del processo è allocata nella sua **user structure**
### Strutture dati del Kernel
Oltre alla Tabella dei file aperti **di processo**, il SO usa due strutture dati **globali**:
- **Tabella dei file attivi:** per ogni file aperto, contiene una copia del suo i-node
- **Tabella dei file aperti di sistema:** ha un elemento per ogni **operazione di apertura** relativa a file aperti (e non ancora chiusi), ogni elemento contiene:
	- L'I/O Pointer
	- Un riferimento all'i-node nella tabella dei file attivi
![[Strutture dati accesso file.png]]

##### Condivisione tra padre e figlio
Il processo figlio copia la User Structure del padre, quindi accederà ai file aperti dal padre con il suo stesso I/O, modificandolo anche per lui!

### Caratteristiche delle syscall per Lettura e Scrittura di File
- Accesso mediante **file descriptor**
- Ogni operazione agisce **sequenzialmente** sul file
- **Atomicità** della singola operazione
- Operazione **sincrone**, attesa del completamento
- Possibilità di **alternare** operazioni di **lettura e scrittura** con `O_RDWR`
**System calls per l'Apertura di File:** [[System Calls in UNIX#Per l'apertura di file|System Calls]]
**System calls per la Gestione di File:** [[System Calls in UNIX#Per la gestione di file|System Calls]]

### Caratteristiche delle syscall per l'Accesso ai direttori
Come per i file, lettura e scrittura di un direttorio possono avvenire solo dopo l'apertura.
Una volta aperto, il direttorio può essere acceduto in:
- **lettura,** da tutti i processi con il diritto di lettura sul direttorio
- **scrittura,** solo il kernel può scrivere sul direttorio
L'operazione di apertura restituisce un puntatore a **DIR** (analogo a **FILE**), un tipo di dato astratto predefinito che consente di riferire un direttorio aperto.
**System calls per la Gestione di Directory:** [[System Calls in UNIX#Per la gestione di directory|System Calls]]