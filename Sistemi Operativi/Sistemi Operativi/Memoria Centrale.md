L'hardware è equipaggiato con un **unico** spazio di memoria accessibile direttamente da CPU e dispositivi. È compito del SO:
- **separare** gli spazi di indirizzi associati ai processi
- **allocare/deallocare** memoria ai processi
- la memoria **virtuale**: gestire spazi logici di indirizzi di dimensioni complessivamente **superiori** allo spazio fisico

In un [[Sistema Multiprogrammato]] l'esecuzione concorrente dei processi determina la necessità di mantenere **più processi in memoria centrale**.
Ci sono diversi aspetta da considerare, tra cui: velocità di accesso, grado di multiprogrammazione, grado di utilizzo della memoria e protezione delle aree di memoria assegnate a processi/kernel.

## Compiti del SO
- **Allocare** memoria ai processi
- **Deallocare** memoria
- **Protezione:** separare gli spazi di indirizzi associati ai diversi processi
- **Binding:** realizzare i collegamenti tra gli indirizzi logici specificati dai processi e le corrispondenti locazioni nella memoria fisica
- **[[Memoria Virtuale]]:** gestire spazi di indirizzi logici di dimensioni superiori allo spazio fisico

## Indirizzi logici e fisici
La Memoria Centrale è un vettore di celle, tutte della stessa dimensione, ognuna univocamente individuata da un **indirizzo fisico**. Le operazioni fondamentali sono **load/store** di dati e istruzioni.

Ad ogni processo è associato uno **spazio di indirizzamento logico \[0, max\]** che contiene il codice e i dati per la sua esecuzione.

Ci si può riferire a una cella con un indirizzo:
- **Fisico:** riferimento assoluto della cella in memoria a livello di HW
- **Logico:** riferimenti a informazioni nello spazio logico di indirizzamento del processo
- **Simbolico:** contenuto in ogni programma in forma **sorgente**. La compilazione lo trasforma in **logico**. È un riferimento a dati/istruzione medianti nomi simbolici, come nomi di variabili, funzioni, ...
![[Fasi di sviluppo di un programma.png]]

## Binding
Ogni indirizzo logico/simbolico viene fatto corrispondere un indirizzo fisico. L'associazione viene detta **binding**.
Può essere effettuato:
- **Statisticamente**
	- A tempo di compilazione. Il compilatore genera degli indirizzi assoluti
	- A tempo di caricamento. Il compilatore genera degli indirizzi relativi che vengono convertiti in indirizzi assoluti dal loader (codice rilocabile)
- **Dinamicamente**
	- A tempo di esecuzione. Durante l'esecuzione lo spazio di indirizzamento di un processo può essere spostato (rilocato) nella memoria fisica

## Caricamento dinamico
Talvolta è possibile caricare in memoria una funzione a runtime solo quando avviene la chiamata, in modo da ottimizzare la memoria. La funzione potrebbe essere usata da più processi simultaneamente. È compito del SO controllare l'accesso di un processo allo spazio di un altro processo e l'accesso di più processi agli stessi indirizzi.

# Tecniche di allocazione in memoria centrale
## Allocazione contigua
### A partizione singola
La parte di memoria disponibile per l'allocazione dei processi di utente non è partizionata. Quindi un solo processo alla volta può essere collocato in memoria.
Solitamente il SO risiede nella memoria bassa e si usa un **registro di relocazione (RL = max+1)** per garantire la correttezza degli accessi.
![[Allocazione contigua a partizione singola.png]]

### A partizione multipla
Qui c'è necessità di proteggere codice e dati di ogni accesso.
Ad ogni processo caricato viene associata un'area di memoria distinta.
#### Partizioni fisse (MFT Multiprogramming with Fixed number of Tasks)
La memoria fisica è suddivisa a priori in un **numero prefissato di partizioni.**
Possono avere dimensioni diverse, ma la loro dimensione è fissata a priori.Vantaggi
Quando un processo viene caricato, il SO cerca una partizione libera di dimensione sufficiente.

**Problemi:**
- Frammentazione interna: spazi piccoli vuoti e la **partizione** viene sottoutilizzata
- Grado di multiprogrammazione limitata
- Dimensione massima dello spazio di indirizzamento limitata

#### Partizioni variabili (MVT Multiprogramming with Variable number of Tasks)
Ogni partizione è creata **dinamicamente** e **dimensionata** alla necessità.

**Risolve tutti i problemi di MFT**
**Problemi:**
- Diverse politiche per la scelta dell'area in cui allocare
- Frammentazione esterna: la **memoria libera** è divisa in piccole aree
**Soluzione frammentazione: Compattazione Periodica**

## Allocazione non contigua
### [[Paginazione]]
Partizionamento dello spazio fisico di memoria in **pagine (frame)** di dimensione costante e limitata, sulle quali mappare porzioni dei processi da allocare.
**Spazio fisico:** insieme di frame di dimensione costante Df prefissata
**Spazio logico:** insieme di pagine di dimensione uguale a Df

**Vantaggi:**
- Pagine logiche contigue possono essere allocate su pagine fisiche non contigue, quindi non esiste alcun tipo di **frammentazione esterna**
- Le pagine sono di dimensione limitata, quindi per ogni processo la **frammentazione interna** è limitata dalla dimensione del frame
- È possibile caricare in memoria un sottoinsieme delle pagine logiche di un processo

### [[Segmentazione]]
Tecnica di allocazione della memoria centrale basata sul partizionamento dello spazio logico in parti (**segmenti**) caratterizzate da nomi e lunghezze proprie. L'allocazione è dinamica, hanno dimensione variabile e vengono allocati a runtime.

### Segmentazione paginata
Le due tecniche possono essere combinate: spazio logico segmentato (specialmente per motivi di protezione) e ogni segmento diviso in pagine

**Vantaggi:**
- Eliminazione della frammentazione esterna
- Non necessario mantenere in memoria l'intero segmento, si caricano solo le pagine necessarie

**Strutture dati:**
- Tabella dei segmenti
- Una tabella delle pagine per ogni segmento

# In Linux
Linux adotta una gestione basata su **segmentazione paginata**
Ci sono vari tipi di segmento: code, data, task state registers (registri per il context switch), ...
I segmenti sono paginati con **paginazione a tre livelli**.

# Partizioni e Protezione
La protezione è realizzata a livello HW mediante:
- Registro di Rilocazione RR
- Registro Limite RL
Ad ogni processo P è associata una coppia di valori <Vrr, Vrl>, caricata poi in memoria.
![[Partizioni e Protezione.png]]

# Dimensione memoria < Spazi logici
La memoria disponibile può non essere sufficiente ad accogliere codice e dati di un processo.
### Overlay
L'obiettivo è mantenere in memoria istruzioni i dati che vengono utilizzati più frequentemente o che sono necessari nella fase corrente.
Codice e dati di un processo vengono **suddivisi dal programmatore** in parti separate (**overlay**) che vengono caricate e scaricate dinamicamente dal **gestore di overlay**, di solito esterno al SO.

Ad esempio, se un codice ha come componenti distinte:
- Tabella dei simboli
- Sottoprogrammi comuni ai due passi
- Codice passo 1
- Codice passo 2
E non fosse possibile caricare tutto contemporaneamente, vengono creati 2 overlay da caricare in sequenza. Entrambi contengono la Tabella dei simboli, i Sottoprogrammi comuni e il Gestore overlay, mentre contengono codice dei due diversi passi.


# [[Memoria Virtuale]]
Metodo di gestione della memoria che consente l'esecuzione di processi non completamente allocati in memoria, in modo completamente **trasparente** per il programmatore e per i processi.

A differenza delle tecniche precedenti automatizza tutto, delegandolo a SO e hardware.
Permette di aumentare il grado di multiprogrammazione, superare il vincolo di dimensione della RAM, ridurre operazioni di I/O e fornire un'astrazione.

La memoria virtuale rappresenta un’evoluzione rispetto alle tecniche tradizionali: consente l’esecuzione efficiente e trasparente di processi più grandi della memoria fisica, svincolando il programmatore dalla gestione esplicita della memoria.