Contiene le informazioni necessarie al sistema per gestire il processo, a prescindere dallo stato in cui si trova. Risiede sempre in memoria centrale.

Tra le altre, contiene le seguente informazioni:
- Stato
- Puntatori alle aree dati e stack
- Riferimento indiretto al codice, ovvero il riferimento all'elemento della **Text Table** associato al codice del processo
- Informazioni di scheduling (priorità, tempo di CPU, ...)
- Riferimento al processo padre attraverso il **PID**
- Informazioni relative alla **gestione di segnali**
- Puntatore al processo successivo nella [[Code di Scheduling.png|coda di processi]]
- Puntatore alla [[User Structure]]

Ad ogni Process Structure è associato un valore intero non negativo che rappresenta l'identificatore unico del processo: **PID (Process Identifier)**

Tutte le Process Structures sono organizzate in un vettore gestito dal SO: **Process Table**