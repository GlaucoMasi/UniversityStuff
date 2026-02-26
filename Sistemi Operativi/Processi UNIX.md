Il processo in UNIX è un **processo pesante** con codice **rientrante**:
- dati **non** condiviso
- codice **condivisibile**

Adotta il **Modello ad Ambiente Locale**, o a scambio di messaggi: ogni processo ha un proprio spazio di indirizzamento locale e non condiviso. L'unica eccezione è che il codice può essere condiviso.

### Gerarchie nei processi UNIX
![[Gerarchie nei processi UNIX.png]]

### Stati di un processo UNIX
![[Stati di un processo UNIX.png]]
Modifiche/Aggiunte rispetto al caso generale:
- Sleeping: analogo allo stadio waiting
- **Zombie:** il processo è terminato, ma è in attesa che il padre ne rilevi lo stato di terminazione
- 