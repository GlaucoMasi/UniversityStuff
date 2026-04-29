L'interazione può avvenire mediante due meccanismi:
- **[[Comunicazione tra Processi|Comunicazione]]:** scambio di informazioni tra processi interagenti
- **[[Sincronizzazione tra Processi|Sincronizzazione]]:** imposizione di vincoli temporali sull'esecuzione dei processi.
  Ad esempio, l'esecuzione X di P1 può essere eseguita soltanto dopo l'istruzione Y di P2

**Realizzazione dell'interazione,** in base al modello di processo:
- **modello ad ambiente locale (processi pesanti):** non c'è condivisone di variabili
	- La **comunicazione** avviene attraverso scambio di messaggi
	- La **sincronizzazione** avviene attraverso scambio di eventi (es. segnali)
	Entrambi vengono realizzati dal SO
- **modello ad ambiente globale (threads):** possibilità di scambiare variabili
	- La **sincronizzazione** avviene con strumenti di sincronizzazione (es. lock, semafori, ...)
	- La **comunicazione** avviene mediante variabili condivise + strumenti di sincronizzazione
	Solo i meccanismi di **sincronizzazione** vengono realizzati dal SO. La **comunicazione** viene realizzata dal programmatore mediante opportuni meccanismi.

