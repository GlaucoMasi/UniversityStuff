Un processo (padre) può richiedere la creazione (**fork**) di un nuovo processo (figlio)
![[Creazione di processi.png]]

Quindi è possibile realizzare **gerarchie** di processi
![[Gerarchie di processi.png]]

**Aspetti caratteristici della Relazione padre-figlio:**
- Concorrenza:
	- Padre figlio procedono contemporaneamente
	- Il padre genera uno o più figli concorrenti e attende la loro terminazione
- Spazio di indirizzi:
	- Duplicato: lo spazio degli indirizzi del figlio è una copia di quello del padre (stesso codice, copia degli stessi dati, ...) come con [[fork()]] in UNIX
	- Differenziato: spazi degli indirizzi con codice e dati diversi come [[exec()]] dopo [[fork()]] in UNIX