Due processi possono cooperare, per perseguire un obbiettivo comune, o competere, se entrami necessitano la stessa risorsa per la quale sono stati stabiliti **vincoli di accesso**, come vincoli di **Mutua Esclusione** nell'accesso alla risorsa.
In entrambi i casi è necessario disporre di **strumenti di sincronizzazione**.

Ad esempio:
- Nella **cooperazione:**
	- Per imporre un particolare ordine cronologico alle azioni eseguite dai processi interagenti
	- Per garantire che le operazioni di comunicazione avvengano secondo un ordine prefissato
	- Per notificare l'avvenimento di un evento
- Nella **competizione:**
	- Per garantire il corrPasted image 20260312112040etto coordinamento nell'accesso a risorse condivise

### Sincronizzazione nel modello ad ambiente globale
Nel modello ad ambiente globale i processi (**threads**) possono **condividere variabili**.
In questo ambiente:
- **Cooperazione:** lo scambio di messaggi avviene mediante strutture dati condivise
- **Competizione:** le risorse sono rappresentate da variabili condivise. Ad esempio un oggetto condiviso non può essere acceduto da più di un processo alla volta.
In entrambi i casi è necessario **sincronizzare** i processi per coordinarli nell'accesso alla memoria condivisa.
Il **SO** definisce alcuni strumenti nella memoria comune che consentono ad ogni processi di aspettare il verificarsi di condizioni di riattivare processi di attesa. Ad esempio: **semafori, locks, conditions, ...**

### Sincronizzazione nel modello ad ambiente locale
[[UNIX]] adotta questo modello.
Nel modello ad ambiente locale i processi (**pesanti**) **non condividono memoria**.
- Gli accessi alle risorse condivise vengono controllati e coordinati dal SO
- La sincronizzazione avviene mediante meccanismi offerti dal SO che consentono la notifica di **eventi** asincroni tra processi, ad esempio **[[Segnali in UNIX]]**
![[Segnali UNIX.png]]
