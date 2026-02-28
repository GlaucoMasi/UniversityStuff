In seguito ad una [[fork()]] i processi padre e figlio condividono il codice e lavorano su aree dati duplicate. In Unix è possibile **differenziare il codice** dei due processi mediante una system call della famiglia `exec()`.

`int execl(char *pathname, char *arg0, ..., char *argN, (char*)0)`;
- `pathname` è il nome (assoluto o relativo) dell'eseguibile da caricare
- `arg0` è il nome del programma
- `arg1, ..., argN` sono gli argomenti da passare al programma
- `(char *)0` è il puntatore nullo che, per convezione, **termina la lista degli argomenti**
In assenza di errori, è una chiamata **senza ritorno**. Se ritorna (il valore restituito è -1) significa che il codice non è stato sovrascritto perché la chiamata è fallita. La variabile `errno` assumerà il valore associato al particolare errore.

### Effetti della exec()
Il processo dopo exec():
- Mantiene la **stessa [[Process Structure]]**, eccetto per le informazioni relative al codice
- Ha **codice, dati globali, stack e heap nuovi**
- Riferisce un nuovo text
- Mantiene [[User Structure]], eccetto PC e informazioni legate al codice, e **stack del kernel**, infatti mantiene le stesse risorse e lo stesso environment

### Varianti di exec()
- `l` gli argomenti vengono specificati mediante una **lista** di parametri terminata da NULL, ad esempio `execl()`
- `p` il nome del file eseguibile viene ricercato nel **path** contenuto nell'ambiente del processo, ad esempio `execlp()`
- `v` gli argomenti da passare vengono specificati mediante un **vettore** di parametri, ad esempio `execv()`
- `e` la system call riceve anche un vettore (`envp[]`) che rimpiazza l'**environment** (path, directory corrente, ...), ad esempio `execle()`
**Esempio**: `int execve(char *pathname, char *argV[], char *env[]`

