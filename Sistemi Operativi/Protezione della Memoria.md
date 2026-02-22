In un sistema multiprogrammato o time sharing, ogni job ha un suo spazio di indirizzi e non deve poter accedere ad aree di memoria esterne al proprio spazio.

**Es. Architettura Intel IA32**
2 bit di protezione --> 4 modi/ring

Il modo di esecuzione corrente, chiamato **Current Privilege Level (CPL)**, è indicato dai 2 bit meno significativi del registro **CS**. 
Ring 0  = modo kernel, Ring 3 = modo user. Windows e Linux utilizzano solo questi due ring.

Ad ogni area di memoria (segmento) viene assegnato un livello di privilegio **PL**.
Se **CPL > PL** c'è una violazione!

