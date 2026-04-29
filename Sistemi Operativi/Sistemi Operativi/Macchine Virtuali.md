Sono la logica evoluzione dell'[[Struttura Modulare|approccio a livelli]]. Virtualizzano **sia l'hardware che il kernel** del SO.
Creano l'illusione che su una stessa macchina fisica si possa disporre di elaboratori multipli con:
- un proprio processore
- una propria memoria virtuale
- le proprie risorse
- il proprio kernel SO

L'obbiettivo è disaccoppiare il comportamento delle risorse hardware e software di un sistema di elaborazione viste dall'utilizzatore dalla loro realizzazione fisica.
Ciò si ottiene introducendo **un livello di indirezione** tra la vista logica e quella fisica della risorse.
![[Virtualizzazione.png]]

Il software che realizza la virtualizzazione è il **Virtual Machine Monitor (VMM o hypervisor)**, garantisce l'isolamento tra le VM e la stabilità del sistema.
![[VMs.png]]

**Host:** piattaforma di base sulla quale si realizzano le macchine virtuali. Comprende la macchina fisica, l'eventuale sistema operativo e il VMM.

**Guest:** la macchina virtuale, comprende applicazioni e sistema operativo.
[[VMM di sistema]] vs [[VMM ospitato]]

### Vantaggi della virtualizzazione
- Più SO sulla stessa macchina fisica, più ambienti di esecuzione eterogenei per lo stesso utente:
	- Legacy sistems
	- Possibilità di eseguire applicazioni concepite per un particolare SO
- Isolamento degli ambienti di esecuzione, ogni VM definisce un ambiente di esecuzione separato (sandbox):
	- Possibilità di effettuare testing preservando l'integrità degli altri ambienti e del VMM
	- Sicurezza, eventuali attacchi da parte di malware o spyware sono confinati alla singola VM
- Consolidamento HW, possibilità di concentrare più macchine (ad esempio server) su un'unica architettura HW
	- Abbattimento costi HW
	- Abbattimento costi amministrazione
- Gestione facilitata delle macchine, si può facilmente:
	- Creare MVs (virtual appliances)
	- Amministrare Mvs (reboot, recompilazione kernel, ...) 
	- Migrare a caldo le MVs tra macchina fisiche:
		- Manutenzione dell'HW senza interrompere i servizi forniti dalle MVs
		- Disaster recovery
		- Workload balancing


