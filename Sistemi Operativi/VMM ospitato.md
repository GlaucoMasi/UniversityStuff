Il VMM viene installato come un'applicazione sopra l'OS esistente, che opera come utente e accede all'HW tramite system calls del SO su cui viene istallato.
- Più semplice da installare
- Può fare riferimento al SO per la gestione delle periferiche e utilizzare altri servizi (scheduling, gestione delle risorse, ...)
- Peggiori performance
![[VMM ospitato.png]]

**Prodotti:** VirtualBox, User Mode Linux, Microsoft Virtual Server