Ogni sistema di elaborazione dispone di uno o più dispositivi per la memorizzazione persistente delle informazioni, chiamati [[memoria secondaria]].

Il compito del SO è fornire una visione **logica** **uniforme** della memoria secondaria, **indipendente** dal tipo e dal numero dei dispositivi. Deve:
- realizzare il concetto astratto di **file**, come unità di memorizzazione logica
- fornire una struttura astratta per l'organizzazione dei file (**cartella o directory**)

Inoltre si deve occupare di:
- **creazione/cancellazione** di file o directory
- manipolazione di file/directory
- associazione tra file e dispositivi di memorizzazione secondaria

**Filecentricità**: file, directory e dispositivi di I/O vengono presentati agli utenti e ai programmi in modo **uniforme**.