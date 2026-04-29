Molte architetture CPU prevedono più [[Modi di Funzionamento]].

Ogni processore offre un insieme di istruzioni macchina (ISA). Nel set di istruzioni vi è il sottoinsieme delle **istruzioni privilegiate**, che possono essere eseguite soltanto se il sistema si trova in **kernel mode**.

In una architettura **Dual Mode** il SO esegue in modo kernel, ogni programma/utente in user mode. Se un programma utente tenta l'esecuzione di una istruzione privilegiata, l'hardware lo impedisce e può essere generato un **trap**.
Se i programmi necessitano di operazioni privilegiate, lanciano le [[system calls]].