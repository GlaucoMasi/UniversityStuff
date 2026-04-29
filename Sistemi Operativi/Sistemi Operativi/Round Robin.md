Algoritmo **pre-emptive** tipicamente usato in [[Sistemi Operativi#Cenni storici|Sistemi time sharing]].
- Ready queue gestita come una coda FIFO circolare ([[FCFS]])
- Ad ogni processo viene allocata la CPU per un intervallo di tempo costante (**time slice** o quanto di tempo)
Può essere visto come una estensione di [[FCFS]] con **pre-emption periodica**.

L'obbiettivo principale è la minimizzazione del tempo di risposta, quindi e adeguato per sistemi interrativi. Inoltre tutti i processi sono trattati ugualmente, quindi non c'è rischio di [[Scheduling con Priorità|starvation]].

**Problemi:**
- Dimensionamento del quanto di tempo:
	- Troppo piccolo => overhead eccessivo
	- Troppo grande => tempi di risposta più alti, diventa [[FCFS]]
- Trattamento equo di tutti i processi:
	- Possibilità di degrado delle prestazioni del SO