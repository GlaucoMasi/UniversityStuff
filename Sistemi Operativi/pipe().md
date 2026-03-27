Per creare una [[pipe]].
`int pipe(int fd[2]);`
`fd` è il puntatore a un vettore di 2 **file descriptor**, che verranno inizializzati dalla syscall in caso di successo. `fd[0]` rappresenta il lato di lettura, `fd[1]` quello di scrittura.
Restituisce 0 se ha successo, un valore negativo in caso di fallimento.