La sincronizzazione permette di imporre vincoli sull'ordine di esecuzione delle operazioni dei processi **interagenti**. [[UNIX]] adotta il modello **ad ambiente locale**, la sincronizzazione viene realizzata mediante **segnali**, poiché i processi non condividono memoria.

### In generale
**Segnale:** **interruzione software** che notifica un evento **in modo asincrono** a uno processo destinatario (o più). Il processo destinatario rileva l'evento **istantaneamente**, qualunque sia il suo stato, interrompendo la sua esecuzione. [[System Calls#Interrompibilità delle System Calls|E se stava eseguendo una System Call?]]
**Esempi di Eventi:**
- generati da terminale (CTRL+C, ...)
- generati dal kernel in seguito ad eccezioni HW (violazione dei limiti di memoria, divisioni per 0, ...)
- generati dal kernel in seguito a condizioni SW (time-out, scrittura su pipe chiusa, ...)
- **generati da altri processi**

### Segnali [[UNIX]]
Un segnale può essere inviato:
- dal **kernel** a processi **utente**
- da un processo **utente** ad altri processi **utente**

Quando un processo riceve un segnale, può:
- gestire il segnale con una funziona **handler** definita dal programmatore
- eseguire un'azione predefinita dal SO, di default
- ignorare il segnale, nessuna reazione

Nei primi due casi, reagisce in modo **asincrono** al segnale:
- interruzione dell'esecuzione
- esecuzione dell'azione associata (handler o default)
- ritorno alla prossima istruzione del codice

Per ogni versione di UNIX esistono vari tipi di segnale (in Linux, 32 segnali), ognuno identificato da un intero.
Ogni segnale è associato ad un particolare evento e prevede una specifica **azione di default**
Ci si riferisce ai segnali con identificatori simbolici **SIGxxx**: SIGKILL, SIGSTOP, SIGUSR1, ...
L'associazione tra nome simboli e intero è specificata nell'header file `<signal.h>` e dipende dalla versione UNIX. Si possono vedere con `kill -l`, utile anche `man signal`

**Caso particolare:** esistono 2 segnali che non possono essere gestiti esplicitamente dai processi. **SIGKILL** e **SIGSTOP** non sono **intercettabili** o **ignorabili**, sono detti segnali **unblockable**. Qualunque processo che li riceve esegue sempre l'**azione di default**.

### [[System Calls]] per i segnali
- [[signal()]]: per gestire esplicitamente un segnale
- [[kill()]]: per inviare segnale ad un altro processo
- [[sleep()]]: per sospendere il processo
- [[alarm()]]: per impostare un timer al cui termine verrà inviato un segnale
- [[pause()]]: per sospendere il processo fino alla ricezione di un segnale qualunque

### Associazioni segnali/handler
Le associazioni segnali-azioni vengono registrate nella **User Structure** del processo.

=> il figlio, risultato di una [[fork()]] che copia la User Structure, eredita dal padre le informazioni relative alla gestione dei segnali. Successive [[signal()]] del figlio modificano solo la sua User Structure e non hanno effetto sulla gestione dei segnali del padre.

L'[[exec()]], invece, sostituisce codice e dati del processo, però mantiene il contenuto della User Structure, tranne le informazioni legate al codice, in cui sono presenti gli handler!
=> Il figlio ignora gli stessi segnali ignorati dal padre e risponde in modo default agli stessi segnali a cui il padre risponde in modo default, però **i segnali gestiti con handler prima di exec vengono riportati a default**.

Non sempre l'associazione segnale/handler è durevole:
- alcune implementazioni di UNIX prevedono che l'azione rimanga installata anche dopo la ricezione del segnale
- in altri invece, dopo l'attivazione dell'handler viene ripristinata l'azione di default. Per riagganciare il segnale all'handler è sufficiente prevedere una [[signal()]] dentro all'handler

### Affidabilità dei segnali
Cosa succede se il segnale arriva durante l'esecuzione dell'handler? Tre alternative:
- innestamento della routine di gestione
- perdita del segnale
- accodamento dei segnali (segnali **reliable**)

### Segnali in LINUX
- **Persistenza dell'handler:** alla fine dell'esecuzione di un handler definito dall'utente, il sistema si occupa di **reinstallarlo automaticamente**
- Se, durante l'esecuzione di un handler, **arriva un secondo segnale uguale** a quello che ha causato la sua esecuzione, il segnale viene **accodato** e gestito una volta terminato il primo handler
- I segnali che arrivano mentre c'è un segnale **uguale** accodato vengono **accorpati** in uno, in modo che un solo segnale venga consegnato al processo