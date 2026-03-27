`int write(int fd, char *buf, int n);`
- `fd` è il file descriptor del file
- `buf` è l'area da cui trasferire i byte letti
- `n` è il numero di caratteri da leggere
In caso di successo restituisce un intero positivo ≤ n, il numero di caratteri letti effettivamente.
L'I/O pointer viene spostato avanti di questo numero.