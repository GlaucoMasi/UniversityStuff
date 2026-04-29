`int close(int fd);`
- `fd` è il **file descriptor** del file da chiudere
Restituisce 0 in caso di successo, < 0 in caso di errore.

Se ha successo:
- Il file viene memorizzato sul disco
- Viene eliminato l'elemento di indice `fd` dalla [[File System di UNIX#Tabella dei file aperti di processo e file descriptor|Tabella dei file aperti del processo]]
- Vengono **eventualmente** eliminati gli elementi corrispondenti nella [[File System di UNIX#Strutture dati del Kernel|Strutture dati del Kernel]]