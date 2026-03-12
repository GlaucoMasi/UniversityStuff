Ad ogni processo viene assegnata una priorità:
- lo **scheduler** seleziona il processo pronto con priorità massima
- processi con uguale priorità vengono trattati in modo [[FCFS]]

Le **priorità** possono essere definite:
- **internamente:** attribuite dal SO in base a politiche interne
- **esternamente:** criteri esterni al SO (ad esempio [[Scheduling in UNIX|nice]] in UNIX)
Le priorità possono essere **costanti** o **variare dinamicamente**.

**Problema:** starvation dei processi
Si verifica quando uno o più processi di priorità bassa vengono lasciati indefinitamente nella coda dei processi pronti, perché sopraggiunge sempre un processo pronto di priorità più alta.

**Soluzione:** modifica dinamica delle priorità dei processi
Ad esempio:
- la priorità decresce al crescere del tempo di CPU già utilizzato (**feedback negativo o aging**)
- la priorità cresce dinamicamente con il tempo di attesa del processo (**feedback positivo o promotion**)
