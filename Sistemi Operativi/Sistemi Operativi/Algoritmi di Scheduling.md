Si possono classificare in due categorie:
- **non pre-emptive (senza prelazione):** la CPU rimane allocata al processo running finché esso non si sospende volontariamente o non termine
- **pre-emptive (con prelazione):** un processo running può essere sospeso preventivamente e la CPU riassegnata. Questo è fondamentale nei [[Sistemi Operativi#Cenni storici|Sistemi Operativi a divisione di tempo]]

### Criteri di Scheduling
I diversi algoritmi di scheduling si analizzano in base ad alcuni indicatori di performance:
- **Utilizzo della CPU:** percentuale media di utilizzo della CPU
- **Througput:** numero di processi completati per unità di tempo
- **Tempo di Attesa (di un processo):** tempo totale percorso nella ready queue
- **Turnaround (di un processo):** tempo tra la sottomissione del job e il suo completamento
- **Tempo di Risposta (di un processo):** tempo tra la sottomissione e l'inizio della prima risposta, non dipende dalla velocità dei dispositivi di I/O

**In generale:**
devono essere **massimizzati:**
- Utilizzo della CPU
- Throughput
e minimizzati:
- Turnaround (nei sistemi batch)
- Tempo di Attesa
- Tempo di Risposta (nei sistemi interrativi)

Non tutti possono essere soddisfatti contemporaneamente, la scelta dipende anche dal tipo di SO:
- nei sistemi batch bisogna massimizzare throughput e minimizzare turnaround
- nei sistemi interattivi bisogna minimizzare il tempo di attesa e il tempo medio di risposta dei processi
### Alcuni Algoritmi
- [[FCFS]]
- [[SJF]]
- [[Scheduling con Priorità]]
- [[Round Robin]]
- [[Approcci misti]]