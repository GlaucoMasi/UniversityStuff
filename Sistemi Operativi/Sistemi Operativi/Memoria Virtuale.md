La dimensione della memoria può rappresentare un vincolo sulla dimensione dei processi e sul grado di multiprogrammazione.

Vorremo un SO che consenta la presenza di più processi in memoria, indipendentemente dalla dimensione dello spazio disponibile e svincoli il grado di multiprogrammazione dalla dimensione effettiva della memoria.

Con le [[Memoria centrale|tecniche viste fin'ora]] l'intero spazio logico di un ogni processo viene allocato in memoria **oppure** l'overlay permette il caricamento dinamico, ma con la realizzazione a carico del programmatore.

La **Memoria virtuale** è un metodo di gestione della memoria che consente l'esecuzione di processi non completamente allocati in memoria, in modo completamente **trasparente** per il programmatore e per i processi.

È normale che possa capitare che la memoria virtuale superi quella fisica, ad esempio perché tutti processi condividono lo stesso kernel. Non è che ognuno ne riceve una copia, ma tutti nella loro memoria virtuale hanno un'area che punta all'area di memoria fisica del kernel code.

### Vantaggi:
- Dimensione spazio logico degli indirizzi non vincolata dall'estensione della memoria
- Grado di multiprogrammazione indipendente dalla dimensione della memoria
- Efficienza: caricamento di un processo e swapping richiedono meno I/O
- Astrazione: il programmatore non si occupa dei vincoli relativi alla dimensione della memoria

# Realizzazione: Paginazione su Richiesta
- Tutte le pagine di ogni processo risiedono in [[Memoria Secondaria]] (backing store)
- Durante l'esecuzione alcune di esse vengono trasferite, all'occorrenza, da backing store a in memoria centrale, e viceversa

**Pager:** modulo del SO che realizza i trasferimenti delle pagine ("swapper" di pagine).
Nella **Paginazione su Richiesta** c'è un **pager lazy**: trasferisce in memoria centrale una pagina soltanto se ritenuta assolutamente necessaria.

L'esecuzione di un processo può richiedere swap-in del processo:
- **Pager:** gestisce i trasferimenti di singole pagine
- **Swapper:** gestisce i trasferimenti di interi processi
Prima di eseguire lo swap-in di un processo, il pager può prevedere le pagine di cui probabilmente il processo avrà bisogno e iniziare il caricamento

### HW necessario
- **Tabella delle pagine** (PTBR, PTLR, e/o TLB, ...)
- **Memoria secondaria** e strutture necessarie per la sua gestione (es. dischi veloci)

Ogni elemento della tabella delle pagine contiene **1 bit di validità**, il cui valore indica:
- 1  -->  La pagine è presente in memoria centrale
- 0  -->  La pagina è in memori secondaria oppure è invalida, ovvero non appartiene allo spazio logico del processo
Nella traduzione di ogni indirizzo si consulta la tabella delle pagine, se il bit di validità della pagina richiesta è 0, scatta un'**interruzione al SO: page fault**.

### Trattamento del page fault
- **Salvataggio** del contesto di esecuzione del processo (registri, stato, tabella delle pagine)
- **Verifica** del motivo del page fault
	- Riferimento illegale (violazione delle politiche di protezione)  =>  Terminazione processo
	- Riferimento legare  =>  La pagina è in memoria secondaria
- **Copia** della pagine in un frame libero
- **Aggiornamento** della tabella delle pagine
- **Ripristino** del contesto del processo, esecuzione dell'istruzione interrotta dal page fault

## Sovrallocazione  =>  Sostituzione di pagine
In seguito al page fault può darsi che non ci siano frame liberi per caricare una pagina.
**Soluzione:** sostituzione di una pagine "vittima" allocata in memoria con la pagina da caricare.
- Individuazione della vittima
- Salvataggio di Pvitt su disco
- Caricamento di Pnew nel frame liberato
- Aggiornamento tabelle
- Ripresa del processo

In generale, la **sostituzione** richiederebbe 2 trasferimento da/verso il disco, per scaricare la vittima e per carica la pagina nuova.
È possibile che la vittima non sia stata modificata e/o che sia una pagina di codice (read-only), in questo caso si risparmia un trasferimento.
#### Dirty bit
Ogni elemento della tabella delle pagine ha anche un bit di modifica che, se settato, indica che la pagina ha subito almeno un **aggiornamento** da quando è stata caricata in memoria.

### Algoritmi di sostituzione della pagine vittima
La finalità di ogni algoritmo di sostituzione è sostituire quelle pagine la cui probabilità di essere accedute a breve termine è bassa.

##### LUF (Least Frequently Used)
Sostituita la pagina che è stata usata meno frequentemente (in un intervallo di tempo prefissato).
È necessario associato un **contatore** degli accessi ad ogni pagina
=>  La vittima è quella con minimo valore del contatore

##### FIFO
Sostituita la pagina da più tempo caricata in memoria (indipendentemente dal suo uso).
È necessario memorizzare la cronologia dei caricamenti con timestamping o coda

##### [[LRF (Least Recently Used)]]
Di solito preferibile per principio di località
È necessario registrare la sequenza degli accessi. Viene inserito un overhead per ogni accesso.
=>  La vittima è quella che è stata usata meno recentemente

## Page fetching e Trashing
La versione pura della paginazione su domanda carica una pagina soltanto se strettamente necessaria. C'è il rischio di **trashing:** più tempo impiegato per la paginazione che per l'esecuzione.
![[Trashing.png]]


### Località dei programmi
Si è osservato che un processo, in una certa fase di esecuzione:
- Usa solo un sottoinsieme relativamente piccolo delle sue pagine logiche
- Il sottoinsieme di pagine effettivamente utilizzate varie lentamente nel tempo

**Località spaziale:** alta probabilità di accesso a locazione vicine (nello spazio logico/virtuale) a locazioni appena accedute (es. codice sequenziale, elementi di un vettore)
**Località temporale:** alta probabilità di accesso a locazioni accedute di recente (es. cicli)
La località temporale viene sfruttata dall'algoritmo **LRU**

### Working set
Per contrastare il trashing si usano tecniche di gestione della memoria che si basano su **pre-paginazione:** si prevede il set di pagine di cui il processo da caricare ha bisogno per la prossima fase di esecuzione, il **working set**.
Il working set può essere individuato in base a criteri di **località temporale**.

Dato un intero Δ, il working set in un processo P, all'istante t, è l'insieme di pagine Δ(t) indirizzate da P nei più recenti d trasferimenti.
Δ definisce la finestra del working set.

Al caricamento di un processo viene carico il suo working set iniziale.
Poi il working set viene aggiornato dinamicamente, mantenendo all'istante t le pagine usate dal processo nell'ultima finestra Δ(t) e sostituendo quelle esterne.
=> **Riduzione del numero di page fault**

Il parametro Δ caratterizza il working set poiché esprime l'estensione della finestra dei riferimenti:
- Δ piccolo: working set insufficiente a garantire località (alto numero di page fault)
- Δ grande: allocazione di pagine non necessarie
Ad ogni istante, si può individuare:
![[Richiesta totale di frame.png]]
Dove WSSi è la dimensione corrente del working set di ogni processo Pi.
Se m è il numero totale di frame liberi:
- D < m: può esserci spazio per allocazione di nuovi processi
- D > m: è necessario lo swapping di uno o più processi

# UNIX
## Prime versioni
**Tecnica di allocazione contigua dei segmenti:** first fit sia per l'allocazione in memoria centrale, che in memoria secondaria (swap-out)

#### Problemi:
- Frammentazione esterna
- Stretta influenza dimensione spazio fisico sulla gestione dei processi in multiprogrammazione
- Crescita dinamica dello spazio di possibilità di riallocazione di processi già caricati in memoria

## In assenza di memoria virtuale: swapping
In assenza di memoria virtuale lo **swapper** ricopre il ruolo chiave di gestione delle contese di memoria da parte di diversi processi, periodicamente (es. ogni 4s) viene attivato per provvedere a swap-in e swap-out di processi:
- **swap-out** di processi inattivi (sleeping), ingombranti o da più tempo in memoria
- **swap-in** di processi piccoli o da più tempo swapped

## Versioni attuali
- Segmentazione paginata
- Memoria virtuale tramite paginazione su richiesta
L'allocazione in ogni segmento non è continua, le pagine di un segmento sono sparse in RAM:
- Frammentazione esterna risolta
- Frammentazione interna trascurabile, limitata dalla piccola dimensione delle pagine

#### Pre-paginazione:
Usa frame liberi per pre-caricare pagine non strettamente necessarie.
Quando avviene un page fault, se la pagina è già in un frame libero, basta modificare la tabella delle pagine e la lista dei frame liberi

#### Core map:
Struttura dati interna al kernel che descrive lo stato di allocazione dei frame e che viene consultata in caso di page fault

#### Page fault (bit validità = 0)
- Riferimento illegale  ==>  Terminazione
- Pagina non caricata  ==>  Caricamento in frame libero e aggiornamento TdP
- Pagine pre-caricata  ==>  Aggiornamento Tabella delle Pagine

#### Algoritmo di Sostituzione: LRU modificato o "algoritmo di seconda chance"
Ad ogni pagina viene associato un bit di uso:
- Al momento del caricamento è inizializzato a 0
- Quando la pagina è acceduta, viene settato
- Nella fase di ricerca di una vittima:
	- bit di uso a 1  ==>  viene messo a 0
	- bit di uso a 0  ==>  scelta come vittima

Sostituzione della vittima:
- La pagina viene resa invalida
- Il fame selezionato viene inserito nella lista dei frame liberi
- La pagina viene copiata in memoria secondaria se il dirty bit non c'è oppure è a 1

L'algoritmo di sostituzione viene fatto dal pager **pagedaemon** (pid = 2)

#### Sostituzione delle pagine
Attivata quando il numero di totale di frame liberi è ritenuto insufficiente.
Portata avanti dal **pagedaemon**

**Parametri:**
- **lotsfree:** numero minimo di frame liberi per evitare sostituzione di pagine
- **minfree:** numero minimo di frame liberi per evitare swapping dei processi
- **desfree:** numero desiderato di frame liberi
**lotsfree > desfree > minfree**

#### Scheduling, Paginazione e Swapping
Lo scheduler attiva:
- l'algoritmo di **sostituzione** se **frame liberi < lotsfree**
- lo **swapper** (al massimo ogni secondo), se il sistema di paginazione è in sovraccarico:
	- **frame liberi < minfree**
	- **numero medio di frame liberi per unità di tempo < desfree**
Il SO evita che **pagedaemon** usi più del 10% del CPU time attivandolo al massimo ogni 250ms

# Linux
- Allocazione basata su segmentazione paginata
- Paginazione a più (2 o 3) livelli
- Allocazione contigua dei moduli di codice caricati dinamicamente
- Memoria virtuale, senza working set

Alcune aree riservate al sistema operativo per scopi specifici:
- **Area codice kernel:** locked, non subiscono paginazione
- **Kernel cache:** heap del kernel, locked
- **Area moduli gestiti dinamicamente:** allocazione con buddy list (contigua dei singoli moduli)
- **Buffer cache:** gestione I/O su dispositivi a blocchi
- **Inode cache:** copia degli inode utilizzati recentemente ([[File System|Tabella file attivi]])
- **Page cache:** pagine non più utilizzate in attesa di sostituzione
- ...
Il resto della memoria è utilizzato per i processi utente

### Spazio di indirizzamento
Ad ogni processo Linux possono essere allocati fino a 4GB:
- 3GB al massimo possono essere utilizzati per lo spazio di indirizzamento virtuale
- 1GB riservato al kernel, accessibile quando il processo esegue in kernel mode

### Paginazione
- Paginazione a tre livelli
- Realizzato per processori Alpha, in alcune architetture i i livelli si riducono a 2 (es. Intel)

Non viene utilizzata la tecnica del working set.
Viene mantenuto un insieme di pagine libere utilizzabili dai processi (page cache).
Analogamente a UNIX, una volta al secondo viene controllato ci siano sufficienti pagine libere, altrimenti viene liberata una pagina occupata.

# MS Windows XP
Paginazione con **clustering delle pagine:**
- In caso di page fault, viene caricato tutto un **gruppo di pagine attorno** a quella mancante, il cosiddetto **page cluster**
- Ogni processo ha un working set **minimo**, numero minimo di pagine sicuramente mantenute in memoria, e un working set **massimo**, numero massimo di pagine mantenibili in memoria
- Qualora la memoria fisica libera scenda sotto una **soglia**, il SO automaticamente ristabilisce la quota desiderata di frame liberi (**working set trimming**), eliminando le pagine appartenenti a processi che ne hanno in eccesso rispetto al working set minimo