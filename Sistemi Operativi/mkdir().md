`int mkdir(char *pathname, int mode);`
Restituisce 0 in caso di successo, altrimenti un valore negativo.
In caso di successo, crea e inizializza il direttorio con il nome e i diritti specificati. Vengono inoltre creati sempre i file `.` (link al direttorio corrente) e `..` (link al direttorio padre).