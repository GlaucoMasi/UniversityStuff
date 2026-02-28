`void exit(int status)`
Prevede un **parametro (status)** mediante il quale il processo può comunicare al padre informazioni sul suo stato di terminazione.
È sempre una chiamata **senza ritorno**.

### Effetti della exit()
- Chiusura dei file aperti non condivisi
- Terminazione del processo:
	- Se il processo ha **figli in esecuzione**, il processo **init** adotta i figli dopo la terminazione del padre, quindi viene assegnato il valore 1 come processo padre nelle [[Process Structure]] di tutti i figli
	- Se il processo **termina prima che il padre ne rilevi lo stato di terminazione** con la system call [[wait()]], il processo passa nello stato [[Processi UNIX#Stati di un processo UNIX|Zombie]]
Quando termina un processo adottato dal processo **init**, **init** ne rileva automaticamente il suo stato di terminazione, quindi i processi figli di **init** non permangono nello stato di Zombie.