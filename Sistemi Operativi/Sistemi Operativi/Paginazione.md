**Supporto dell'indirizzo logico:** `p` numero di pagina logica e `d` offset della cella da inizio pagina
**Supporto dell'indirizzo fisico:** `f` numero di frame e `d` offset della cella da inizio frame

Il **binding** tra indirizzi logici e fisici può essere realizzato mediante la **Tabella delle Pagine**
Ogni tabella delle pagine è associata ad un particolare processo. Ogni riga associa a ogni pagina fisica la corrispondente pagina logica, l'offset rimane invariato.

## Realizzazione **della** tabella delle pagine
Ci sono dei problemi da considerare: la tabella può essere molto grande e la traduzione da indirizzo fisico a logico deve essere il più veloce possibile.

#### Varie soluzioni
- Su registri di CPU
	- Accesso veloce
	- Cambio di contesto pesante
	- Dimensione limitate della tabella
- In memoria centrale
	- Registro **PageTableBaseRegister (PTBR)** memorizza collocazione tabella in memoria
	- Per ogni operazione (load, store) ci sono due accessi in memoria (traduzione, operazione)
- Memoria centrale + cache:
	- **Translation Look-aside Buffers (TLB)** per velocizzare l'accesso

#### Translation Look-aside Buffers (TLB)
La tabella delle pagine è allocata in memoria centrale. Una parte delle pagine (solitamente quella relativa alle pagine accedute più di frequente/recente) è copiata in cache: TLB.
Inizialmente il TLB è vuoto, viene gradualmente riempito durante l'esecuzione.
L'obbiettivo è massimizzare l'**HIT-RATIO:** percentuale media di volta in cui una pagina viene trovata in TLB. Dipende dalla dimensione del TLB (Intel486: 98%)

## Protezione
La tabella delle pagine ha dimensione fissa e non sempre viene utilizzata completamente.
Come distinguere gli elementi significativi da quelli non utilizzati?
- **Bit di validità:** ogni elemento contiene un bit, che è 1 se la entry è valida, ovvero la pagina appartiene allo spazio logico del processo
- **Page Table Length Register:** contiene il numero degli elementi validi nella tabella
In aggiunta, per ogni entry possono essere uno o più **bit di protezione** per la modalità di accesso

## Paginazione a più livelli
Lo spazio di indirizzamento di un processo può essere molto esteso, quindi ci sarà un numero elevato di pagine e una tabella delle pagine di grandi dimensioni.
La soluzione è l'allocazione non contigua anche della tabella delle pagine, quindi si applica ancora la tecnica di paginazione alla tabella delle pagine.

Il primo livello (tabella esterna) contiene gli indirizzi delle tabelle delle pagine collegate al secondo livello (tabelle interne).
**Struttura dell'indirizzo logico:** `p1` indice di pagina nella tabella esterna, `p2` offset nella tabella interna, `d` offset cella all'interno della pagina fisica.
Una sola tabella grande viene divisa in più tabelle più piccole.

**Vantaggi:**
- Possibilità di indirizzare spazi logici di dimensioni elevate riducendo i problemi di allocazione
- Possibilità di mantenere in memoria soltanto le tabelle interne che servono

**Svantaggi:**
- Tempo di accesso più elevato, sono necessari più accessi in memoria  =>  Si usa TLB

## Tabella delle pagine invertita
Per limitare l'occupazione di memoria, si può usare un'unica struttura dati globale che ha un elemento per ogni frame. Ogni elemento della tabella rappresenta un frame e, se allocato, contiene il `pid` del processo a cui è assegnato e il numero di pagina logica `p`.
Per tradurre un indirizzo logico `<pid, p, d>` si ricerca nella tabella l'elemento che contiene la coppia `pid, p`, il cui indice rappresenta il numero del frame corrispondente.

**Problemi:**
- Tempo di ricerca molto alto
- Difficoltà di realizzazione della condivisione di pagine tra processi
