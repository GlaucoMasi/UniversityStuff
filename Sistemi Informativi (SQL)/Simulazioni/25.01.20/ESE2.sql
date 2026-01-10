-- 1) Per ogni socio, il numero di società in cui è socio singolo, includendo anche i soci che non sono mai soci singoli e ordinando il risultato per numero decrescente di società
SELECT 	s.NOME, COUNT(soc.IDSOC) AS NUM_SOC
FROM	SOCI s
LEFT JOIN	QUOTE q 	ON s.NOME = q.SOCIO
LEFT JOIN	SOCIETA soc 	ON	(q.IDSOC = soc.IDSOC AND q.NUMAZIONI = soc.TOTAZIONI)
GROUP BY	s.NOME
ORDER BY	NUM_SOC DESC;

-- 2) I dati delle società con più di un socio in cui la maggioranza delle azioni (> 50%) è posseduta da soci dello stesso stato della società
SELECT	*
FROM	SOCIETA soc
WHERE	EXISTS (
	SELECT	q1.IDSOC
	FROM	QUOTE q1
	WHERE	q1.IDSOC = soc.IDSOC
	GROUP BY	q1.IDSOC
	HAVING	COUNT(*) > 1
)
AND 0.5*soc.TOTAZIONI < (
	SELECT	SUM(q2.NUMAZIONI)
	FROM	QUOTE q2, SOCI s
	WHERE	q2.SOCIO = s.NOME
	AND		q2.IDSOC = soc.IDSOC
	AND		s.STATORESIDENZA = soc.STATO
);
