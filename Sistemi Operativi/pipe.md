La pipe è un **canale di comunicazione** tra processi della stessa gerarchia:
- **Unidirezionale:** accessibile da un estremo in lettura e dall'altro in scrittura
- **Molti-a-molti:**
	- Più processi possono spedire messaggi attraverso la stessa pipe
	- Più processi possono ricevere messaggi attraversi la stessa pipe
- **Capacità limitata:** la pipe è in grado di gestire l'accodamento di un numero limitato di messaggi, gestiti in modo FIFO. Il limite è stabilito dalla dimensione della pipe. 
  =>  **Comunicazione asincrona**
- **OMOGENEITÀ con i FILE:** accesso con le stesse [[System Calls in UNIX#Per la comunicazione con pipe|syscalls]]

# Comunicazione attraverso pipe
La comunicazione tra processi è **indiretta**, ovvero senza naming **esplicito**: modello **mailbox**.
Nonostante sia bidirezionale, se ben disciplinata può consentire comunicazione **bidirezionale** tra processi, perché lo stesso processo può sia depositare che prelevare messaggi dalla pipe.

##### Sincronizzazione automatica:
read e write da/verso pipe possono essere **sospensive** se la pipe è vuota o piena.

##### Comunicazione solo nella stessa gerarchia:
Il riferimento al canale è un **file descriptor**, che viene ereditato dal figlio in caso di fork(), quindi possono scambiarsi messaggi mediante pipe solo due processi che hanno un antenato in comune, ovvero che appartengono alla stessa gerarchia.

# Chiusura di pipe
Ogni processo può chiudere un estremo della pipe con una [[close()]].
Ma un estremo viene **effettivamente** chiuso solo quando tutti i processi che ne hanno visibilità hanno compiuto una [[close()]] su di esso.
Se un processo:
- tenta una **lettura** da una pipe vuota il cui lato di scrittura è **effettivamente** chiuso, [[read()]] ritorna 0
- tenta una **scrittura** da una pipe il cui lato di lettura è **effettivamente** chiuso, [[write()]] ritorna -1 ed il segnale `SIGPIPE` viene inviato al processo (broken pipe)

# Svantaggi della pipe
- Consente la comunicazione solo tra processi in relazione di parentela
- Non è persistente, viene distrutta quando terminano tutti i processi che la usano