Facciamo riferimento al **Modello ad Ambiente Locale:**
- Non c'è memoria condivisa
- I processi possono interagire (in cooperazione o competizione):
	- **Comunicazione:** mediante scambio di messaggi
	- **Sincronizzazione:** mediante scambio di eventi (o segnali)

Il **Sistema Operativo** offre meccanismi a supporto della comunicazione tra processi.
Prende il nome di **Inter Process Communication (IPC)**.
**Operazioni necessarie:** send, receive
Lo scambio di messaggi avviene mediante un canale di comunicazione.

### Aspetti caratterizzanti
- **Tipo di comunicazione:** diretta o indiretta, simmetrica o asimmetrica, bufferizzata o no
- **Caratteristiche del canale:** monodirezionale o bidirezionale, uno-a-uno, uno-a-molti, molti-a-uno o molti-a-molti, capacità, modalità di creazione automatica o non automatica
- **Caratteristiche del messaggio:** dimensione, tipo

### Naming
Come viene specificata la destinazione del messaggio?
- **Comunicazione diretta:** al messaggio viene associato l'identificatore del processo destinatario (naming esplicito) con `send(Proc, msg)`. 
  Il canale è creato autonomamente tra i due processi che devono conoscersi reciprocamente:
	- canale punto-a-punto/uno-a-uno
	- canale bidirezionale
	- per ogni coppia esiste un solo canale
  La comunicazione **diretta** può essere:
	- **Simmetrica:** il destinatario fa il naming esplicito del mittente
	- **Asimmetrica:** il destinatario non è obbligato a conoscere l'identificatore del mittente, viene raccolto da una variabile
- **Comunicazione indiretta:** il messaggio viene indirizzato a una mailbox ,dalla quale il destinatario lo preleverà, con`send(Mailbox, msg)`.
  I processi non sono tenuti a conoscersi reciprocamente. Inoltre per ogni coppia di processi possono esistere più canali, uno per ogni mailbox condivisa. Il canale è rappresentato dalla mailbox e non viene creato automaticamente. Può essere utilizzato da più processi:
	- mailbox molti-a-molti
	- porta, mailbox molti-a-uno

### Buffering del canale
Ogni canale è caratterizzato da una capacità (≥ 0) che esprime il massimo numero di messaggi che è in grado di contenere contemporaneamente. La gestione è secondo politica FIFO.
- **Capacità nulla:** non vi è accodamento, il mittente e il destinatario devono sincronizzarsi nell'inviare e ricevere il messaggio: **comunicazione sincrona**. A questo scopo send e receive possono essere **sospensive**.
![[Buffering capacità nulla.png]]
- **Capacita non nulla, ma limitata:** esiste un limite N alla dimensione della coda.
	- Se la coda non è piena il nuovo messaggio viene posto in fondo
	- Se la coda è piena **la send è sospensiva**
	- Se la coda è vuota **la receive può essere sospensiva**
- **Capacità illimitata:** lunghezza della coda teoricamente infinita, non fattibile nella realtà. In questo caso non ci sarebbe possibilità di sospensione

### Tipologie di send
- **Send sincrona**, nei canali a capacità nulla la send è sospensiva
	- **Send con sincronizzazione estesa:** particolare tipo di comunicazione sincrona, in cui il mittente si sospende fino a che il destinatario non restituisce una reply al messaggio inviato. Utile nel caso in cui il messaggio richieda l'esecuzione di un servizio, ad esempio Remote Procedure Call (RPC)
![[Send con sincronizzazione estesa.png]]
- **Send asincrona,** nei canali con capacità non nulla la send non è sospensiva, il mittente deposita il messaggio nel canale e continua la sua esecuzione

### Comunicazione tra processi [[UNIX]]
I processi UNIX non possono condividere memoria, modello ad ambiente locale. La comunicazione può avvenire:
- Mediante **condivisione di file**, ma è necessario realizzare la sincronizzazione tra processi
- Attraverso specifici strumenti di **Inter Process Communication:**
	- Tra processi sulla stessa macchina:
		- [[fifo]], per qualunque insieme di processi
		- [[pipe]], tra processi della stessa gerarchia
	- Tra processi in nodi diversi della stessa rete:
		-  **socket:** comunicazione in ambiente distribuito

