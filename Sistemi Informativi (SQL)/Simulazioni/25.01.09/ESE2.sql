-- 1) Per ogni comune, considerando solo gli ordini spediti lo stesso giorno in cui è stato fatto l’ordine e che sono stati consegnati, l’importo medio di un ordine
SELECT	c.COMUNE, AVG(o.IMPORTO) AS AVG_IMPORTO
FROM	CLIENTI c
JOIN 	ORDINI o	ON c.CODCL = o.CODCL 
JOIN	TRACKING t	ON o.IDORD = t.IDORD
WHERE	o.DATA = t.DATA
AND		t.STATO = 'spedito'
AND 	EXISTS ( 
	SELECT 	*
	FROM	TRACKING t1
	WHERE	t1.IDORD = o.IDORD
	AND		t1.STATO = 'consegnato'
)
GROUP BY c.COMUNE;

-- 2) L’identificativo degli ordini per cui la durata di un passaggio di stato (da ‘spedito’ ad ‘arrivato’ o da ‘arrivato’ a ‘consegnato’) è stata massima
-- (se l’ordine non è stato consegnato il passaggio da ‘arrivato’ a ‘consegnato’ non va considerato, idem se un ordine non è ‘arrivato’)
WITH Passaggio(IDORD, Stato1, Stato2, Data1, Data2) AS (
	SELECT	a.IDORD, a.STATO, b.STATO, a.DATA, b.DATA
	FROM	TRACKING a, TRACKING b
	WHERE 	a.IDORD = b.IDORD
	AND (
		(a.STATO = 'spedito' AND b.STATO = 'arrivato')
	  	OR
		(a.STATO = 'arrivato' AND b.STATO = 'consegnato')
	)
)
SELECT  p1.IDORD
FROM	Passaggio p1
WHERE	DAYS(p1.DATA2) - DAYS(p1.DATA1) >= ALL (
	SELECT 	DAYS(p2.DATA2) - DAYS(p2.DATA1)
	FROM	Passaggio p2
);
