Il processo in [[UNIX]] è un **processo pesante** con codice **rientrante**:
- dati **non** condiviso
- codice **condivisibile**

Adotta il **Modello ad Ambiente Locale**, o a scambio di messaggi: ogni processo ha un proprio spazio di indirizzamento locale e non condiviso. L'unica eccezione è che il codice può essere condiviso.

### Gerarchie nei processi UNIX
![[Gerarchie nei processi UNIX.png]]

### Stati di un processo UNIX
![[Stati di un processo UNIX.png]]
Modifiche/Aggiunte rispetto al caso generale:
- Sleeping: analogo allo stadio waiting
- **Zombie:** il processo è terminato, ma è in attesa che il padre ne rilevi lo stato di terminazione
- [[Swapping|Swapped]]: il processo (o parte di esso) è temporaneamente trasferito in [[memoria secondaria]]

### Rappresentazione dei processi UNIX
Il codice dei processi è **rientrante**, quindi:
- Codice e dati sono separati (modello a codice puro)
- Il SO gestisce una struttura dati globale in cui sono contenuti i puntatori ai codici utilizzati dai processi, la **text table**
- L'elemento della text table si chiama **text structure** e contiene per ogni codice:
	- Il puntatore al codice, che fa riferimento alla memoria secondaria se il processo è [[Swapping|swapped]]
	- Il numero di processi che lo condividono

Il [[Process Control Block]]/Descrittore del processo in [[UNIX]] è rappresentato da 2 strutture dati distinte:
- [[Process Structure]]: informazioni necessarie al sistema per gestire il processo, a prescindere dallo stato in cui si trova. Risiede sempre in memoria centrale
- [[User Structure]]: informazioni necessarie solo se il processo è residente in memoria centrale (e se è running. In caso di swap può essere trasferita e il riferimento nella [[Process Structure]] cambierà) 

### Immagine di un processo UNIX
L'immagine di un processo è **l'insieme delle aree di memoria e strutture dati** associate al processo.

**Protezione:** non tutta l'immagine è accessibile in modo user, c'è una parte kernel e una parte utente (accessibile solo dal processo in questione in modalità user).

**Presenza in memoria centrale:** ogni processo può essere soggetto a [[Swapping]], in questo caso non tutta l'immagine può essere trasferita in memoria. L'immagine si divide in parte swappable e parte non swappable.
![[Immagine di un processo UNIX.png]]

**Componenti dell'Immagine:**
- [[Process Structure]] (kernel, residente)
- [[User Structure]] (kernel, swappable)
- Text: elemento della Text Table (kernel, residente)
- Area di Codice: codice del programma (user, swappable)
- Area dati globali di utente (user, swappable)
- stack, heap dell'utente (user, swappable)
- stack del kernel: stack associato al processo per le syscalls (kernel, swappable)

**System calls per la gestione dei processi:** [[System Calls in UNIX#Per la gestione dei processi|System Calls]]

### Inizializzazione dei processi UNIX
La prima shell si chiama **shell di login**
![[Inizializzazione dei processi UNIX.png]]

Dopo il login, sarà la shell a forkarsi per ogni nuovo processo.
![[Generazione di Processi.png]]

Ogni utente può interagisce con lo shell mediante l'invocazione di comandi. Ogni comando è presente nel file system come file eseguibile (in `/bin`). Per ogni comando, la shell genera un processo figlio dedicato alla sua esecuzione.
![[Interazione mediante shell.png]]

Ci sono due comportamenti possibili:
- **Esecuzione in foreground:** il padre si pone in attesa della terminazione del figlio, ad esempio `ls -l`
- **Esecuzione in background:** il padre continua l'esecuzione **concorrentemente** al figlio, ad esempio `ls -l &` 
![[Foreground vs Background.png]]