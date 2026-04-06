Usata per spostate l'I/O pointer
`int lseek(int fd, int offset, int origine);`
- `fd` è il file descriptor del file
- `offset` è lo spostamento, in byte, rispetto all'origine
- `origine` può valere:
	- 0: inizio file (SEEK_SET)
	- 1: posizione corrente (SEEK_CUR)
	- 2: fine file (SEEK_END)
In caso di successo restituisce un intero positivo che rappresenta la nuova posizione.

Si può usare anche per calcolare la lunghezza di un file!
`L = lseek(fd, 0, 2);`