-- 1) I dati dei prodotti con prezzo maggiore di 50€ per i quali il numero di reclami rimborsati è maggiore di quelli non rimborsati (con esito definito o meno)
SELECT 	p.PRODID, p.CATEGORIA, p.PREZZO
FROM	PRODOTTI p, RECLAMI r
LEFT JOIN	(
	SELECT	*
	FROM	ESITI e1
	WHERE	e1.ESITO = 'RIMBORSO'
) e
ON	r.RID = e.RID
WHERE	p.PRODID = r.PRODID 
AND		p.PREZZO > 50
GROUP BY	p.PRODID, p.CATEGORIA, p.PREZZO
HAVING		0.5*COUNT(r.RID) < COUNT(e.ESITO);

-- 2) Per ogni categoria il prodotto per il quale è passato il numero minimo di giorni (>= 0) tra un reclamo e l’altro
WITH PROD_TEMPO(ProdId, Categoria, Prezzo, Tempo) AS (
	SELECT	p.PRODID, p.CATEGORIA, p.PREZZO, DAYS(r2.DATA)-DAYS(r1.DATA)
	FROM	PRODOTTI p, RECLAMI r1, RECLAMI r2
	WHERE	p.PRODID = r1.PRODID
	AND		p.PRODID = r2.PRODID
	AND		r1.RID <> r2.RID 
	AND		r1.DATA <= r2.DATA
)
SELECT	p.PRODID, p.CATEGORIA, p.PREZZO
FROM	PROD_TEMPO p
WHERE	p.TEMPO = (
	SELECT	MIN(p1.TEMPO)
	FROM	PROD_TEMPO p1
	WHERE	p.CATEGORIA = p1.CATEGORIA
);
