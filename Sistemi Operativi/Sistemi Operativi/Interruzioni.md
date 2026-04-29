Le varie componenti (HW e SW) interagiscono con il SO mediante **interruzione asincrone** (**interrupt**).
Ogni interruzione è causata da un evento e ad ogni interruzione è associata una routine di servizio (**handler**) per la gestione dell'evento.
Alla ricezione di un'interruzione il SO interrompe l'esecuzione, salva lo stato in memoria, attiva l'handler e poi ripristina lo stato salvato. Si può individuare la routine di servizio utilizzando un **vettore delle interruzioni**

### Tipologie di interruzioni
- **Hardware:** i dispositivi inviano segnali alla CPU per notificare particolari eventi
- **Software (trap):** generate da programmi in esecuzione
	- Quando tentano l'esecuzione di **operazioni non lecite**
	- Quando richiedono **l'esecuzione di servizi** al SO ([[System Calls]])