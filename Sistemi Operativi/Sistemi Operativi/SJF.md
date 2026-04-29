Shortest Job First: cerca di risolvere i problemi dell'algoritmo [[FCFS]] ordinato i jobs in base alla **lunghezza stimata del prossimo CPU-burst**, partendo dal **più corto**.
Si può dimostrare che questo algoritmo **minimizza il tempo di attesa medio**.

Può essere **non pre-emptive**, oppure **pre-emptive** se dinamicamente si sostituisce il job attivo se nella coda ne entra un altro con CPU burst minore del CPU burst rimasto al processo running.

**Problema:** è difficile stimare la lunghezza del prossimo CPU burst di un processo, solitamente si fa utilizzando il passato.

### Stimare la lunghezza di un CPU burst
**Approccio approssimato:** stimare la lunghezza del CPU burst in funzione dei predenti CPU bursts di quel processo.

Tecnica usata frequentemente: **exponential averaging**
![[Exponential Averaging.png]]
Sviluppando l'espressione si nota che ogni termine ha meno peso rispetto al precedente.
Nei casi limite:
- a = 0  -->  la storia recente non conta
- a =1  -->  conta solo l'ultimo valore reale
![[Stimare la lunghezza di CPU burst.png]]