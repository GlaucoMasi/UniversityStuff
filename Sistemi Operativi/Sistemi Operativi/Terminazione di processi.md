Ogni processo:
- è figlio di un altro processo
- può essere padre di altri processi
Il SO mantiene le informazioni relative alle relazioni di **parentela** nel [[Process Control Block|descrittore]].

Se un processo **termina**:
- Il padre deve poter rilevare il suo stato di terminazione
- I figli possono
	- terminare
	- continuare l'esecuzione assumendo un altro processo come "padre adottivo", come avviene su Unix

Un processo può terminare:
- **involontariamente:**
	- tentativi di **azioni illegali**
	- interruzione mediante **segnale**
	=> in UNIX avviene il salvataggio dell'immagine nel **file core**
- **volontariamente:**
	- esecuzione dell'ultima istruzione
	- chiamata alla funzione [[exit()]] in UNIX