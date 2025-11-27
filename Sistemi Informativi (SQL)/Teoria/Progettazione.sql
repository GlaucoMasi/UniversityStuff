-- All'interno del ciclo di vita dei sistemi informativi, c'è la progettazione di basi dati
-- Solitamente prima si progetta la base di dati, perchè i dati sono condivisi da più applicazioni e sono più stabili, poi si progettano le applicazioni
-- Per progettare una base di dati bisogna seguire una Metodologia di Progettazione che prevede 3 fasi, ognuna basata su un modello che permette una rappresentazione formale:
-- 1. Progettazione Concettuale -> Schema concettuale: indipendente dal DBMS adottato, descrizione formale delle esigenze
-- 2. Progettazione Logica -> Schema logico: si considerano aspetti legati ai vincoli e all'efficienza
-- 3. Progettazione Fisica -> Schema fisico: stettamente dipendente dal DBMS adottato
-- Prima di procedere però è necessaria una fase di raccolta e analisi dei requisiti informativi, sui processi e sui vincoli di integrità

-- Per la progettazione concettuale usiamo DB-MAIN:
-- Virtuale 24/25: https://virtuale.unibo.it/pluginfile.php/2399902/mod_resource/content/0/L06.1.IntroduzioneDB-MAIN.pdf

-- DB-MAIN supporta una serie di strumenti per modificare uno schema concettuale o per trasformarlo in uno schema logico:
-- Virtuale 24/25: https://virtuale.unibo.it/pluginfile.php/2408577/mod_resource/content/0/L07.1.TrasformazioniDB-MAIN.pdf

-- Il modello concettuale più usato è il Modello Entity-Relationship (E/R) e ne esistono diversi dialetti e varianti grafiche

-- Meccanismi di astrazione:
-- 1. Classificazione: identifica classi di oggetti del mondo reale aventi proprietà comuni
-- 2. Aggregazione: definisce un nuovo concetto a partire da concetti componenti
-- 3. Generalizzazione: raggruppa classi con proprietà comuni in una superclasse più generale

-- Entità: classe di oggetti che possiedono caratteristiche comuni e che hanno esistenza "autonoma". Si rappresenta con un rettangolo
-- Associazione: legame logico tra entità. Si rappresenta con un rombo
-- Un'entità si può considerare equivalente ad una relazione
-- Anche le associazioni possono essere viste come relazioni, ma tra entità invece che tra valori atomici
-- L'insieme delle istanze di un'associazione è un sottoinsieme del prodotto cartesiano degli insiemi delle istanze delle entità coinvolte
-- Le associazioni possono essere anche di gradi superiori al secondo, possono esserci più associazioni tra le stesse entità e anche associazioni ad anello (simmetriche e non)

-- Attributo: priorietà elementare di un'entità o di un'associazione. Ogni attributo è definito su un dominio di valori
-- Un attributo associa ad ogni istanza di entità o associazione un valore del corrispondente dominio
-- Si possono anche definire attributi composti, ottenuti aggrendado più attributi semplici, il loro dominio sarà il prodotto cartesiano dei domini degli attributi componenti

-- Identificatore: permette l'individuazione univoca di ogni istanza di entità. Può essere semplice (un solo attributo) o composto (più attributi)
-- Ogni entità deve avere un identificatore, ma può averne anche più di uno. Deve valere la minimilità, ovvero non si possono togliere attributi senza perdere l'univocità

-- I vincoli possono essere impliciti (intrinseci al modello) o espliciti (definiti dal progettista)
-- Vincoli di cardinalità: associati ad ogni entità che participa ad un'associazione.
-- Indicano il numero minimo e massimo di istanze dell'associazione a cui può partecipare un'istanza dell'entità
-- Se un'entità ha valore massimo = 1, allora la sua chiave sarà chiave dell'associazione
-- Anche per gli attributi è possibile definire vincoli di cardinalità, di default (1, 1)
-- Gli attributi possono essere opzionali(min-card = 0), monovalore(max_card = 1) e multivalore/ripetuti(max-card = n).

-- Terminologia per associazione binaria:
-- Associazione 1 a 1: la cardinalità massima di entrambe le entità è 1
-- Associazione 1 a molti: la cardinalità massima di una entità è 1, dell'altra è n
-- Associazione molti a molti: la cardinalità massima di entrambe le entità è n
-- Partecipazione obbligatoria di E1 in A: la cardinalità minima di E1 in A è 1
-- Partecipazione opzionale di E1 in A: la cardinalità minima di E1 in A è 0

-- Identificatore esterne: c'è la possibilità di identificare un'entità tramite altre (una o più) entità con cui è in associazione
-- Ad esempio: uno Studente è identificato univocamente attraverso la matricola solo nel contesto della sua Università
-- Se una entità E1 è identificata estremamente attraverso un'associazione, allora max-card(E1, A) = 1. Se basta E2 per identificare E1, allora min-card(E2, A) = 1

-- Secondo l'astrazione di generalizzazione si può creare una superclasse a partire da più sottoclassi
-- Ogni sottoclasse eredita attributi e associazione della superclasse, quindi ogni attributo va definito nell'entità più generale in cui è presente
-- Le generalizzazione può essere:
-- 1. Totale: la superclasse è l'unione delle sottoclassi
-- 2. Parziale: la superclasse contiene anche istanze che non appartengono a nessuna sottoclasse e istanza che appartengono a più sottoclassi
-- e anche:
-- 1. Esclusiva: le sottoclassi sono tra loro disgiunte
-- 2. Sovrapposta: le sottoclassi possono avere istanze in comune
-- Quindi sono possibili le quattro combinazioni

-- Subset: caso particolare di gerarchia in cui si evidenzia una sola classe specializzata


-- ======================================================================
-- ==========================   RIASSUNTO   =============================
-- ======================================================================
-- Uso dei meccanismi di astrazione:
-- 1. Classificazione: definizione di entità (dalle istanze) e attributi (dai valori)
-- 2. Aggregazione: definizione di entità (dagli attributi), associazioni (da entità e attributi) e attributi composti
-- 3. Generalizzazione: definizione di gerarchie di generalizzazione

-- Notazione grafica alla slide 19:
-- Virtuale 24/25: https://virtuale.unibo.it/pluginfile.php/2694128/mod_resource/content/0/06.2.ER.avanzato.pdf

-- Utilità del modello E/R:
-- 1. Documentazione: rappresenta in modo chiaro e intuitivo i requisiti informativi
-- 2. Reverse engineering: si può fornire la descrizione in E/R di un database esistente
-- 3. Integrazione di sistemi: è possibile usare il modello E/R come linguaggio comune in cui rappresentare DB eterogenei

-- Limiti del modello E/R:
-- 1. I nomi dei concetti possono non essere sufficienti per comprenderne il significato
-- 2. Non tutti i vincoli di integrità sono esprimibili in uno schema E/R
-- Quindi utile integrare con una documentazione appropriata

-- Business Rules: asserzioni che definisca o vincoli qualche aspetto rilevante del SI. Ne esistono tre tipi:
-- 1. Descrizione di un concetto: Linguaggio naturale
-- 2. Vincolo di integrità: <concetto> [non] deve <espressione su concetti>
-- 3. Derivazione: <concetto> si ottiene <operazioni su concetti>


-- ======================================================================
-- ====================   PATTERN DI PROGETTAZIONE   ====================
-- ======================================================================
-- Le aree di parcheggio: Un campeggio è diviso in più aree, ognuna caratterizzata da una certa tariffa
-- Soluzione: Le aree sono istanze della stessa entità

-- Dallo stesso medico, non lo stesso giorno: Un paziente può essere visitato più volte da medici diversi nello stesso giorno, ma non più volte dallo stesso medico nello stesso giorno
-- Soluzione: L'associazione tra Paziente e Medico viene trasformata in un'entità (reificazione) con identificazione esterna

-- Non faccio più di una lezione al giorno: Le lezioni di un corso si tengono in diverse aule, ma un corso non ha mai due o più lezioni nello stesso giorno
-- Soluzione: L'associazione Lezione (Corso, Aula, Giorno) viene reificata e identificata esternamente dalla coppia (Corso, Giorno)

-- L'orario dei treni, i ritardi e le prenotazioni: Si vuole mantenere l'orario dei treni, i ritardi e gestire le prenotazioni dei clienti
-- Soluzione: Non bisogna mischiare aspetti statici e dinamici. Si crea l'entità Tratta (Stazioni e Orari) e l'entità TrattaGiornaliera (Tratta, Data e Ritardi) identificata esternamente

-- Si prende in prestito una copia, non un libro: Si vogliono mantenere informazioni sui libri, sui prestiti e sui danni apportati ai volumi
-- Soluzione: Si creano le entità Libro, Utente e CopieLibro. CopieLibro è identificata esternamente tramite Libro. L'associazione Prestito unisce CopieLibro e Utenti e ha suoi attributi


-- ======================================================================
-- ===================   PROGETTAZIONE CONCETTUALE   ====================
-- ======================================================================
-- Un concetto non è di per sè un'entità, un'associazione, un attributo o altro, ma dipende dal contesto applicativo
-- Regole guida:
-- 1. Entità: ha proprietà significative e descrive oggetti con esistenza autonoma
-- 2. Attributo: è semplice e non ha proprietà
-- 3. Associazione: correla due o più concetti
-- 4. Generalizzazione/Specializzazione: è un caso più generale/particolare di un altro

-- Strategie di progettazione:
-- 1. Top-Down: si parte da uno schema iniziale molto astratto ma completo, che viene raffinato per arrivare allo schema finale
-- 2. Bottom-Up: si sviluppano semplici schemi parziali ma dettagliati per le varia specifiche, poi integrati tra loro
-- 3. Inside-Out: si parte dai concetti più importante, che poi vengono espansi aggiungendo quelli correlati

-- Di fatto si utilizza una strategia ibrida:
-- 1. Si identificano i concetti importanti e si realizza uno schema scheletro che contiene quelli più importanti
-- 2. Sulla base di questo si può decomporre
-- 3. Infine si raffina, si espande e si integra

-- Qualità di uno schema concettuale:
-- 1. Correttezza: non deve presentare errori sintattici o semantici
-- 2. Completezza: tutti i dati di interesse devono essere specificati
-- 3. Leggibilità: riguarda anche aspetti prettamente estetici
-- 4. Minimalità: talvolta è permessa la presenza di elementi ridondanti nello schema, per favorire l'esecuzione di certe operazioni


-- ======================================================================
-- ======================   PROGETTAZIONE LOGICA   ======================
-- ======================================================================
-- L'obbiettivo della progettazione logica è trasformare lo schema concettuale in uno schema logico

-- Il lavoro si divide in due fasi:
-- 1. Progettazione fedele: lo schema finale deve essere equivalente a quello iniziale dal punto di vista della capacità informativa
-- 2. Progettazione efficiente: si cerca di ottimizzare lo schema logico per migliorare l'efficienza delle operazioni più frequenti

-- Progettazione fedele:
-- Sat(DB) indica l'insieme delle istanze legali di uno schema, quindi Sat(DBconc) = Sat(DBrel)
-- La progettazione logica di fatto definisce un mapping M che trasforma ogni istanza legale dello schema concettuale in una istanza legale dello schema relazionale
-- Diciamo che la progettazione preserva l'informazione se M è totale e iniettiva

-- Questa definizione in realtà non basta, perchè potrebbero esistere infinite istanze legali nello schema relazione che non lo sono in quello concettuale
-- Quindi in realtà una progettazione garantisce l'equivalenza se preserva l'informazione
-- e per ogni istanza legale dello schema relazionale esiste una istanza legale dello schema concettuale che viene mappata in essa
-- Questo di fatto equivale a dire che esiste una biiezione tra gli i due insiemi di istanze legali
-- La definizione data può essere usata localmente, quindi la traduzione viene operata attraverso una sequenza di traduzioni semplici,
-- per ognuna delle quali è facile dare regole che garantiscano l'equivalenza
-- Riassumento, abbiamo regole che preservano l'informazione (sulla struttura) e regole che garantiscono l'equivalenza (sui vincoli)

-- Traduzioni di schemi E/R semplici:
-- 1. Entità e associazioni, ma non gerarchie
-- 2. Ogni entità ha un singolo identificatore, ed è interno
-- 3. Non ci sono attributi ripetuti

-- Regole di traduzione:
-- 1. Entità: Ogni entità diventa una relazione con gli stessi attributi. La chiave primaria coincide con l'identificatore dell'entità.
-- Se un attributo è opzionale, si usa l'asterisco per indicare la possibilità di valori nulli
-- 2. Associazione: Ogni associazione diventa una relazione con gli stessi attributi, a cui si aggiungono gli identificatori di tutte le entità che collega.
-- Gli identificatori delle entità collegate costituiscono una superchiave. La chiave primaria dipende dalle cardinalità massime delle entità coinvolte
-- 3. Foreign Key: Spesso conviene cambiare i nomi delle primary key referenziate, per essere più espressivi
-- 4. Attributi composti: Gli attributi composti vengono tradotti suddividendoli ricorsivamente nelle loro componenti.
-- È consigliabile usare un prefisso comune per gli attributi che si ottengono nella relazione finale
-- 5. Entità con identificazione esterna: Nel caso di entità identificate esternamente, si importa l'identificatore dell'entità identificante. Se ci sono più identificazioni
-- esterne in cascata, bisogna partire dalle entità non identificate esternamente e propagare gli indentificatori così ottenuti
-- 6. Entità con più identificatori: Per scegliere quale identificatore usare come chiave primaria, si considerano: assenza di opzionalità, semplicità e utilizzo
-- nelle operazioni più frequenti ed importanti. Se nessuno soddisfa questi requisiti, si può introdurre un nuovo attributo codice
-- 7. Gerarchie: Il modello non può rappresentare direttamente le generalizzazioni, quindi vengono sostituite con entità e associazioni.
-- Ci sono tre strategie principali (più altre soluzioni intermedie o ibride):
-- a) Collasso verso l'alto: si accorpano le entità figlie nel genitore
-- b) Collasso verso il basso: si accorpano le entità genitore nelle figlie
-- c) Traduzione indipendente: la generalizzazione viene sostituita da una associazione
-- 8. Attributi multivalore: In ogni caso bisogna creare una nuova entità, ma ci sono due possibilità:
-- a) Creare una nuova entità che contiene i valori semplici dell'attributo ed è identificata esternamente dall'entità originale
-- b) Creare una nuova entità che contiene i valori semplici dell'attributo ed è indetificata solo da questi

-- Chiave primaria nei diversi tipi di Associazione:
-- Molti a Molti -> unione degli identificatori delle entità coinvolte
-- Uno a Molti -> identificatore dell'entità con cardinalità massima 1
-- Uno a Uno -> identificatore di una delle due entità coinvolte, in base alla loro importanza relativa. L'altro diventa una chiave alternativa

-- Alternative per associazioni uno a molte: si può utilizzare una traduzione più compatta, inglobando l'associazione nell'entità con cardinalità massima 1
-- Questo equivale ad aggiungere i dati dell'entità con cardinalità massima n come attributi dell'entità con cardinalità massima 1
-- Il vantaggio che se ne trae è una semplificazione delle query e una riduzione del numero di join, che porta a migliori prestazioni
-- Bisogna però fare attenzione se l'entità con cardinalità massima 1 ha partecipazione opzionale nell'associazione, perchè in questo caso si possono avere valori nulli
-- Questo è tollerabile se questi casi sono relativamente pochi, sennò c'è uno spreco inutile di spazio
-- Questa soluzione si può applicare anche alle associazioni uno a molti ad anello

-- Alternative per associazioni uno a uno: anche in questo caso si può inglobare l'associazione in una delle due entità coinvolte
-- La scelta di quale entità inglobare e di quale mantenere dipende dall'importanza relativa delle due entità e dalla partecipazione obbligatoria o opzionale
-- Se una delle due entità ha partecipazione opzionale nell'associazione questa soluzione è sconsigliata per spreco di spazio
-- Nella scelta della chiave primaria bisogna considerare le cardinalità minime, introducendo un codice se entrambe le chiavi possono avere valori nulli
-- Questa soluzione si può applicare anche alle associazioni uno a uno ad anello

-- Problematica relativa all'equivalenza: Per tradurre un'associazione A a cui partecipa un'entità E, in cui min-card(E, A) = 1 e max-card(E, A) = n,
-- Non possiamo inglobare l'associazione in E, perchè E può partecipare a più istanze di A. Quindi il DDL non può controllare che ogni istanza di E partecipi ad almeno una istanza di A

-- Collasso verso l'alto:
-- Tutti gli attributi che apparivano nelle entità figlie ora sono opzionali nella entità genitore. Inoltre si aggiunge un attributo tipo.
-- È necessario anche un vincolo che sincronizzi i valori di Tipo con quelli degli attributi specifici delle sottoclassi. In base alla copertura della gerarchia cambia l'attributo selettore:
-- 1. Totale Esclusiva: Tipo ha N valori
-- 2. Parziale Esclusiva: Tipo ha N+1 valori (uno per ogni sottoclasse + uno per le istanze non appartenenti a nessuna sottoclasse)
-- 3. Sovrapposta: Tipo è un insieme di flag booleane, uno per ogni sottoclasse. Se la copertura è totale, almeno un flag deve essere true
-- Le associazioni connesse alle entità figlie vengono collegate al genitore e le cardinalità minime diventano tutte 0

-- Collasso verso il basso:
-- Vengono create N relazioni, una per ogni entità figlia, che contengono gli attributi specifici di ogni sottoclasse e gli attributi della superclasse
-- Se la gerarchia è esclusiva, il vincolo viene perso perchè il DDL non ha modo di garantire che che una istanza della superclasse appaia in una sola delle sottoclassi
-- Non si può fare se la copertura non è completa, mentre introduce ridondanza se la copertura non è esclusiva

-- Traduzione indipendente:
-- Si generano N+1 relazioni, una per la superclasse e una per ogni sottoclasse. Nella superclasse viene aggiunto un attributo selettore.
-- La superclasse partecipa all'associazione con cardinalità 0-1, mentre le sottoclassi partecipano con cardinalità 1-1
-- Anche in questo caso se la gerarchia è esclusiva il vincolo viene perso. Si può utilizzare l'attributo Tipo per aggiungere un vincolo di CHECK alle sottoclassi

-- Soluzione ibrida:
-- Spesso conviene usare soluzioni miste, ad esempio applicando il collasso verso l'alto per le sottoclassi più piccole
-- e la traduzione indipendente per quelle più grandi che sono coinvolte in associzioni con altre entità