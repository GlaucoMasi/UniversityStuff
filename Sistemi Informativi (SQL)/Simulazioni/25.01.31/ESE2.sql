-- 1) I dati delle società in cui la quota percentuale di azioni possedute da soci dello stesso stato della società e la quota posseduta da tutti gli altri soci differiscono di meno del 20%
SELECT 	soc.IDSOC, soc.STATO, soc.TOTAZIONI 
FROM	SOCIETA soc, SOCI s, QUOTE q
WHERE	soc.IDSOC = q.IDSOC
AND		s.NOME = q.SOCIO
AND		s.STATORESIDENZA = soc.STATO
GROUP BY	soc.IDSOC, soc.STATO, soc.TOTAZIONI
HAVING	SUM(q.NUMAZIONI) > 0.4*soc.TOTAZIONI
AND		SUM(q.NUMAZIONI) < 0.6*soc.TOTAZIONI;

-- 2) Le coppie di (nomi di) soci che in tutte le società in comune con almeno 3 soci possiedono almeno il 20% delle azioni a testa
WITH SOCIETASPEC(IDSOC, TOTAZIONI) AS (
	SELECT	soc.IDSOC, soc.TOTAZIONI
	FROM	SOCIETA soc, QUOTE q
	WHERE	soc.IDSOC = q.IDSOC
	GROUP BY soc.IDSOC, soc.TOTAZIONI
	HAVING	COUNT(q.SOCIO) >= 3
)
SELECT 	q1.SOCIO AS SOCIO1, q2.SOCIO AS SOCIO2
FROM	SOCIETASPEC s, QUOTE q1, QUOTE q2
WHERE	q1.SOCIO < q2.SOCIO
AND		s.IDSOC = q1.IDSOC
AND		s.IDSOC = q2.IDSOC
GROUP BY	q1.SOCIO, q2.SOCIO
HAVING	MIN(q1.NUMAZIONI*1.0/s.TOTAZIONI) >= 0.2
AND		MIN(q2.NUMAZIONI*1.0/s.TOTAZIONI) >= 0.2;
