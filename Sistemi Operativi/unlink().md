Per cancellare un file, o decrementare il numero dei suoi link.
`int unlink(char *name)`
- `name` è il nome del file
Ritorna 0 in caso di successo, -1 in caso di fallimento.
In generale, decrementa di 1 il numero di link del file nell'[[i-node]], eliminando il nome specificato dalla struttura logica del [[File System di UNIX]]. Se il numero di link risulta 0, di fatto lo cancella.