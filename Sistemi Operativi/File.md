È un contenitore di informazioni, come programmi, oppure dati, testi, immagini, audio, video, ...
Ogni file è individuato da almeno un **nome simbolico** mediante il quale può essere riferito ed è caratterizzato da una serie di **attributi**.

### Attributi dei file
Solitamente:
- **tipo**
- **indirizzo**
- **dimensione**
- **data e ora**
In un SO multiutente anche:
- utente **proprietario**
- **protezione**

Gli attributi sono contenuti nel **Descrittore del file**, che è contenuto per ogni file in apposite strutture in memoria secondaria.

In alcuni SO, non UNIX, l'estensione inclusa nel nome di un file rappresenta il suo tiacceso a file|po.

### Metodi di accesso
Il metodo di **accesso** a file è **indipendente** dal tipo di dispositivo utilizzato e dalla tecnica di allocazione dei blocchi in memoria secondaria.
L'accesso a file può avvenire secondo varie modalità:
- **Accesso sequenziale (previsto da UNIX),** il file è una sequenza di record logici:
	- Per accedere ad un particolare record logico è necessario accedere prima ai precedenti
	- Le operazioni di accesso sono `readnext` e `writenext`
	- È necessario registrare la posizione corrente, il **puntatore al file**
	- Ogni operazioni di lettura o scrittura sposta il puntatore sull'elemento successivo
- **Accesso diretto,** il file è un insieme di record logici numerati
	- Si può accedere direttamente a un particolare record logico specificandone il numero
	- Le operazioni di accesso sono `read i` e `write i`
	- Utile quando si vuole accedere a grossi file per leggere/scrivere poche informazioni. Ad esempio nell'accesso a un database
- **Accesso a indice,** ad ogni file è associata una struttura dati contenente l'**indice delle informazioni contenute**
	- Per accedere a un record logico, si esegue una ricerca nell'indice utilizzando una chiave