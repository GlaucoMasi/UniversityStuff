Il PCB contiene tutte le informazioni relativa al processo:
- Stato del processo
- Contenuto dei registri di CPU (tra cui PC)
- Informazioni relative all'I/O (risorse allocate, file aperti, ...)
- Informazioni per gestore di memoria (registri base, limite, ...)
- Informazioni di [[Scheduling della CPU|scheduling]] (priorità, ...)
- Informazioni di accounting (tempo di CPU utilizzato, ...)
- ...
Molti di questi dati sono sotto forma di puntatori a zone di memoria specifiche, come per lo stack.
Il SO gestisce i PCB di tutti i processi, organizzandoli in opportune strutture dati (ad esempio code) in base al loro stato.

In [[UNIX]], è formato da:
- [[Process Structure]]
- [[User Structure]]