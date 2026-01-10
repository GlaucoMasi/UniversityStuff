# Riassunto Simboli Algebra Relazionale

## 1. Operatori Fondamentali
Questi sono i 6 operatori che definiscono la base dell'algebra relazionale.

| Simbolo | Nome | Sintassi | Descrizione |
| :---: | :--- | :--- | :--- |
| **$\sigma$** | **Selezione** | $\sigma_{condizione}(R)$ | Filtra le **righe** (tuple) che soddisfano la condizione $F$. La condizione usa operatori logici (AND, OR) e di confronto ($=, <>, <, >, \le, \ge$). |
| **$\pi$** | **Proiezione** | $\pi_{colonne}(R)$ | Seleziona un sottoinsieme di **colonne** (attributi) ed elimina i duplicati. |
| **$\rho$** | **Ridenominazione** | $\rho_{Y \leftarrow X}(R)$ | Cambia i nomi degli attributi nello schema della relazione (utile per i self-join). |
| **$\cup$** | **Unione** | $R_1 \cup R_2$ | Unisce le tuple di due relazioni (devono avere lo stesso schema). Rimuove i duplicati. |
| **$-$** | **Differenza** | $R_1 - R_2$ | Restituisce le tuple presenti in $R_1$ ma **non** in $R_2$ (devono avere lo stesso schema). |
| **$\bowtie$** | **Join Naturale** | $R_1 \bowtie R_2$ | Combina le tuple basandosi sull'uguaglianza degli attributi con lo stesso nome. |

---

## 2. Operatori Derivati
Questi operatori possono essere espressi combinando quelli fondamentali, ma sono scorciatoie molto utili.

| Simbolo | Nome | Sintassi | Descrizione |
| :---: | :--- | :--- | :--- |
| **$\cap$** | **Intersezione** | $R_1 \cap R_2$ | Restituisce le tuple presenti sia in $R_1$ che in $R_2$. Equivale a $R_1 - (R_1 - R_2)$ oppure a $R_1 \bowtie R_2$ con alcune differenze in presenza di valori nulli. |
| **$\bowtie_F$** | **Theta-Join** | $R_1 \bowtie_F R_2$ | Prodotto cartesiano seguito da una selezione: $\sigma_F(R_1 \times R_2)$. |
| **$\div$** | **Divisione** | $R_1 \div R_2$ | Restituisce un insieme di tuple con schema $R_1$ tale che, facendo il prodotto cartesiano con $R_2$, si ottiene una relazione contenuta in $R_1$. Viene usata per le le interrogazioni *universali* (es. studenti con *tutti* gli esami). Equivale a $\pi_{A}(R_1) - \pi_{A}((\pi_{A}(R_1) \bowtie R_2) - R_1)$ |

---

## 3. Outer Joins (Gestione dei NULL)
Servono per mantenere nel risultato anche le righe che non trovano corrispondenza (dette tuple *dangling*), riempiendo i vuoti con `NULL`.

| Simbolo | Nome | Descrizione |
| :---: | :--- | :--- |
| **$=\bowtie$** | **Left Outer Join** | Mantiene tutte le righe dell'operando a **sinistra** ($R_1$), aggiungendo NULL a destra se non c'è match. |
| **$\bowtie=$** | **Right Outer Join** | Mantiene tutte le righe dell'operando a **destra** ($R_2$), aggiungendo NULL a sinistra se non c'è match. |
| **$=\bowtie=$** | **Full Outer Join** | Mantiene tutte le righe di entrambi, riempiendo con NULL dove manca la corrispondenza. |