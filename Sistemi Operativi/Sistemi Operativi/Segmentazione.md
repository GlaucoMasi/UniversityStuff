Non è stabilito un ordine tra i segmenti e ogni segmento è allocato in memoria in modo contiguo.
Ad ogni segmento è possibile applicare diritti di accesso specifici e ad ogni segmento il SO associa un intero attraverso il quale lo si può riferire.
La divisione spesso è **semantica**, ad esempio: codice, stack, dati e heap
Ogni indirizzo logico è costituito dalla coppia segment, offset.

### Supporto HW alla segmentazione
**Tabella dei segmenti:** ha una entry per ogni segmento, che ne descrive l'allocazione in memoria fisica mediante la coppia `base, limite`. La tabella è indirizzata dal registro STBR.

Una tabella globale diventerebbe grandissima, si crea una tabella per processo.
#### Realizzazione:
- Su registri di CPU
- In memoria, mediante Segment Table Base Register e Segment Table Length Register
- Memoria + cache, caricando solo l'insieme dei segmenti usati più recentemente

Di fatto è un'**estensione** dell'allocazione a **partizioni variabili**
Da una partizione per processo a più partizioni (segmenti) per processo.
Anche in questo caso, però, c'è il rischio di **frammentazione esterna**.
**Soluzioni:** allocazione dei segmenti con tecniche (best fit, worst fit, ...) e **compattazione**