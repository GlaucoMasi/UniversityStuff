**Processo = programma in esecuzione**
Il programma è un'entità **passiva**: un insieme di byte contenente le istruzioni da eseguire.
Il processo è un'entità **dinamica**:
- è l'unità di **lavoro/esecuzione** nel sistema, ogni attività è rappresentata da un processo
- è l'**istanza** di un programma in **esecuzione**
**Processo = programma + contesto di esecuzione**

### Gestione dei processi
In un [[Sistema Multiprogrammato]] più processi possono essere presenti **simultaneamente**.
Il SO deve:
- creare/terminare i processi
- sospendere/ripristinare i processi
- permettere sincronizzazione/comunicazione tra processi