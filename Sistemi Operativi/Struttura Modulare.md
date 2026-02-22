Ogni componente presenta una **interfaccia** ben definita.
**Esempi:** Sistemi Stratificati:
- ogni livello realizza un insieme di funzionalità che vengono offerte al livello superiore mediante una opportuna interfaccia
- ogni livello utilizza le funzionalità offerte dal livello sottostante per realizzare altre funzionalità

**Vantaggi:**
- Astrazione: ogni livello fornisce una visione astratta del sistema sottostante ([[Macchine Virtuali]])
- Modularità: facile sviluppare, verificare e modificare un livello in modo indipendente dagli altri
**Svantaggi:**
- L'organizzazione gerarchica tra le componenti non è sempre possibile
- L'obbligo ad attraversare tutti i livelli diminuisce l'efficienza
**Soluzione:** Limitare il numero dei livelli!