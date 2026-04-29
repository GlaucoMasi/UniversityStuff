`int read(int fd, char *buf, int n);`
- `fd` è il file descriptor del file
- `buf` è l'area in cui trasferire i byte letti
- `n` è il numero di caratteri da leggere
In caso di successo restituisce un intero positivo ≤ n, il numero di caratteri letti effettivamente.
L'I/O pointer viene spostato avanti di questo numero.
È previsto un carattere End-Of-File (EOF), da tastiera Ctrl+D, la syscall read(...) restituisce 0 se è stato raggiunto l'EOF.