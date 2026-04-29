Contiene le informazioni necessarie al SO per la gestione del processo, quando è **residente in memoria** (quindi non swapped):
- copia dei registri di CPU (PC, ...)
- informazioni sulle risorse allocate (file aperti)
- informazioni sulla gestione di segnali (puntatori a handlers, ...)
- ambiente di processo: direttorio corrente, utente, gruppo, argc/argv, path, ...

Quando il processo viene swappato queste informazioni vengono scritte sul disco, mentre è necessario che la [[Process Structure]] rimanga sempre in RAM.