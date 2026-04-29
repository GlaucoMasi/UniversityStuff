I processi possono inviare segnali ad altri processi con questa [[System Calls|system call]].
`int kill(int pid, int sig);`
- `sig` è l'intero (o il nome simbolico) che individua il segnale da gestire
- il parametro `pid` specifica il destinatario del segnale:
	- `pid > 0`: l'intero è il pid dell'unico processo destinatario
	- `pid = 0`: il segnale è spedito a tutti i processi appartenenti al **gruppo** del mittente (tutti i processi appartenenti alla stessa gerarchia del mittente)
	- `pid < -1`: il segnale è spedito a tutti i process con **groupId** uguale al valore assoluto di pid (tutti i processi appartenenti alla gerarchia di cui pid è la radice)
	- `pid = -1`: vari comportamenti possibili