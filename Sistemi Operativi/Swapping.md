Lo scheduler a medio termine (**swapper**) gestisce i trasferimenti dei processi in due casi:
- **Swap out:** da [[memoria centrale]] a [[memoria secondaria]] (dispositivo di swap). Si applica preferibilmente ai processi bloccati (Sleeping), prendendo in considerazione:
	- tempo di attesa -->  se un processo è in attesa da tanto ci resterà probabilmente
	- tempo di permanenza in memoria  -->  se un processo è appena stato portato in memoria, non ha senso rimandarlo indietro
	- dimensione del processo  -->  è più efficiente swappare un processo grande che tanti piccoli
- **Swap in:** da [[memoria secondaria]] a [[memoria centrale]]. Si applica preferibilmente ai processi più corti