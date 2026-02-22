Rese disponibili dalla **API**, rappresentano l'**[[Interfaccia programmatore]]**:
- usate dai processi per richiedere al SO l'esecuzione di un servizio
- in grado di eseguire istruzioni privilegiate, fa il passaggio da modo user a modo kernel

Alla chiamata di una system call:
- Un'[interruzione](Interruzioni) software viene inviata al SO
- Lo stato del programma corrente viene salvato (PC, registri, bit di modo, ...) e il controllo viene trasferito al SO
- Il SO esegue in modo kernel l'operazione richiesta
- Al termine, il controllo ritorna al programma chiamante in modo user e viene ripristinato lo stato iniziale
![[System call.png]]

Ci sono diverse **classi di system calls**:
- gestione dei processi
- gestione di file e di dispositivi
- gestione di informazioni di sistema
- comunicazione/sincronizzazione tra processi

**Programma di sistema = programma che chiama system call**