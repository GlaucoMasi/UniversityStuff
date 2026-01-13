-- 2.1) I dettagli di ogni prodotto per il quale la quantità complessivamente venduta a prezzo non scontato è maggiore di 5, e il valore di tale quantità
SELECT	p.CODP, p.CATEGORIA, p.PREZZO, SUM(v.QUANTITA) AS VENDITE_TOTALI
FROM	Prodotti p
JOIN	Vendite v
ON 		p.CODP = v.CODP
LEFT JOIN	Sconti i
ON		v.DATA BETWEEN i.INIZIO AND i.FINE
WHERE	i.SCONTO IS NULL
GROUP BY p.CODP, p.CATEGORIA, p.PREZZO
HAVING	SUM(v.QUANTITA) > 5;

-- 2.2) Per ogni categoria, il prodotto che ha complessivamente incassato di più nei periodi di sconto, con il relativo incasso
WITH VENDITE_PRODOTTO(CODP, CATEGORIA, PREZZO, VENDITE_TOTALI) AS (
	SELECT	p.CODP, p.CATEGORIA, p.PREZZO, SUM(1.0*v.QUANTITA*p.PREZZO*(100-i.SCONTO)/100)
	FROM	Prodotti p, Vendite v, Sconti i
	WHERE 	p.CODP = v.CODP
	AND		v.DATA BETWEEN i.INIZIO AND i.FINE
	GROUP BY p.CODP, p.CATEGORIA, p.PREZZO
)
SELECT	a.CATEGORIA, a.CODP, a.VENDITE_TOTALI AS INCASSO
FROM	VENDITE_PRODOTTO a
WHERE	a.VENDITE_TOTALI = (
	SELECT	MAX(b.VENDITE_TOTALI)
	FROM	VENDITE_PRODOTTO b
	WHERE	a.CATEGORIA = b.CATEGORIA
);
