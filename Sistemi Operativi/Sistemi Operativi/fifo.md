Come realizzare la [[Comunicazione tra Processi]] non appartenenti alla stessa gerarchia?
La **fifo** è una [[pipe]] con un **nome nel file system**:
- Unidirezionale, FIFO
- Persistenza e Visibilità potenzialmente globale
- Ha un proprietario, un insieme di diritti ed una lunghezza
- È creata dalla system call [[mkfifo()]]
- È aperta e acceduta con le stesse system call dei file
- Una volta creata, può essere acceduta come tutti i file.