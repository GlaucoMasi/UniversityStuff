Per duplicare un elemento della [[File System di UNIX#Tabella dei file aperti di processo e file descriptor|Tabella dei file aperti di un processo]].
`int dup(int fd)`
La chiamata a `dup` **copia** l'elemento `fd` nella tabella dei file aperti **nella prima posizione libera della tabella**, ovvero quella con indice minimo tra le disponibili.
Restituisce il nuovo file descriptor o -1 in caso di errore.