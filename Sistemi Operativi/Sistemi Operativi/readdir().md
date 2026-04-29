Legge un elemento dal direttorio dato e lo memorizza all'indirizzo puntato da `descr`.
```C
struct dirent *descr;
descr = readdir (DIR *dir);
```
La funzione restituisce un valore di tipo puntatore a dirent:
- diverso da NULL se l'apertura ha avuto successo
- NULL in caso di errore

###### Struttura dirent
![[Struttura dirent.png]]
La stringa che parte da `d_name` rappresenta il nome del file (o direttorio) nel direttorio aperto, mentre `d_namelen` rappresenta la lunghezza del nome.