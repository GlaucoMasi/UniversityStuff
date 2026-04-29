Permette di organizzare i file all'interno del file system:
- una directory può contenere più file ed eventualmente altre directory
- è realizzata mediante una struttura dati che prevede un elemento per ogni file o directory in essa contenuto. Associa al nome di ogni file le informazioni che consentono di localizzarlo in memoria di massa

### Operazioni su directory
- Creazione/Cancellazione
- Aggiunta/Cancellazione di file
- Listing
- Attraversamento
- Ricerca

### Tipi di directory
La struttura può variare a seconda del SO.

##### Struttura a un livello
Una sola directory per ogni file system
![[Struttura a un livello.png]]
**Problematiche:**
- Unicità dei nomi
- Multiutenza

##### Struttura a due livelli
La directory principale contiene una directory per ogni utente, il secondo livello è formato da directory utenti a un livello
![[Struttura a due livelli.png]]

##### Struttura ad albero
Organizzazione gerarchica a N livelli
![[Struttura ad albero.png]]

##### Struttura a grafo aciclico ([[UNIX]])
Estende la struttura ad albero con la possibilità di inserire link differenti allo stesso file
![[Struttura a grafo aciclico.png]]