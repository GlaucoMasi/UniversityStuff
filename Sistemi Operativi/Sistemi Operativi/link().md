Per aggiungere un link a un file esistente.
`int link(char *oldName, char *newName)`
- Incrementa il numero dei link nell'[[i-node]]
- Aggiorna il direttorio aggiungendo un nuovo elemento
- Ritorna 0 in caso di successo, -1 in caso di fallimento. Fallisce se `oldName` non esiste, `newName` esiste già o se appartengono a file system diversi, in questo caso bisogna usare `symlink`.