`unsigned int sleep(unsigned int N)`
- provoca una sospensione del processo per N secondi (al massimo)
- se il processo riceve un segnale durante il periodo di sospensione, **viene risvegliato prematuramente**
- ritorna 0 se la sospensione non è stata interrotta da segnali, oppure N-Ns se il risveglio è stato causato da un segnale al tempo Ns