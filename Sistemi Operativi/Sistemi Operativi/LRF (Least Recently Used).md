È necessario registrare la sequenza temporale degli accessi alle pagine.

### Soluzioni
- **Time stamping:** l'elemento della tabella delle pagine contiene un campo che rappresenta l'istante dell'ultimo accesso alla pagina  =>  Costo della ricerca della pagine vittima
- **Stack:** l'accesso a una pagina provoca lo spostamento dell'elemento corrispondente in cima allo stack. La pagina RLU è in fondo  =>  Non c'è overhead di ricerca, ma gestione costosa

### LRU approssimato
Spesso si utilizzano versioni **semplificate** di RLU, ad esempio in Linux.
Ad esempio, introducendo un **bit di uso** associato alla pagina che viene settato a 1 quando la pagine viene acceduta. Periodicamente tutti i bit di uso vengono resettati.

In questo modo basta sostituire una pagina con bit di uso = 0. Inoltre, si può considerare anche il dirty bit per fare in modo di scegliere una pagina non aggiornata (dirty bit = 0).
