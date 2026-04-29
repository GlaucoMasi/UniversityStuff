# Shell UNIX — Riassunto Completo

---

## Indice
1. [Basi della shell](#1-basi-della-shell)
2. [Comandi filtro](#2-comandi-filtro)
3. [Ridirezione e Piping](#3-ridirezione-e-piping)
4. [Metacaratteri ed espansione](#4-metacaratteri-ed-espansione)
5. [Variabili](#5-variabili)
6. [expr e Backtick](#6-expr-e-backtick)
7. [Passi di sostituzione e inibizione](#7-passi-di-sostituzione-e-inibizione)
8. [Shell Scripting — File comandi](#8-shell-scripting--file-comandi)
9. [Variabili speciali](#9-variabili-speciali)
10. [Input/Output](#10-inputoutput)
11. [test e confronti](#11-test-e-confronti)
12. [Strutture di controllo](#12-strutture-di-controllo)
13. [Esempi completi](#13-esempi-completi)
14. [Extra: sintassi e comandi utili non nelle slide](#14-extra-sintassi-e-comandi-utili-non-nelle-slide)

---

## 1. Basi della shell

La shell è un **interprete comandi evoluto**: legge comandi da stdin o da file comandi e li esegue.

```
loop forever
  <LOGIN>
  do
    <ricevi comando>
    <interpreta comando>
    <esegui comando>
  while (! <EOF>)
  <LOGOUT>
end loop
```

- Shell preferita di ogni utente: impostata con `chsh`, salvata in `/etc/passwd`
- Linux usa **bash** (`/bin/bash`)
- Uscita: `exit`, `logout`, `CTRL+D` (EOF), `CTRL+C` (SIGINT)
- Comandi principali in `/bin`

### Tipi di comandi

| Tipo | Descrizione | Esempi |
|------|-------------|--------|
| **builtin** | Eseguiti nel processo shell stesso | `echo`, `cd`, `shift`, `exit` |
| **external** | Shell crea un processo figlio con fork+exec | `cat`, `ls`, `cut`, `expr`, `grep` |

```bash
type <comando>      # stampa se il comando è builtin o external
help                # lista tutti i builtin
```

> **Nota**: ogni comando external crea una shell figlia. Lo script stesso è un comando external.

---

## 2. Comandi filtro

I comandi UNIX si comportano come **filtri**: stdin → elaborazione → stdout/stderr.

| Comando | Sintassi                              | Descrizione                                                |
| ------- | ------------------------------------- | ---------------------------------------------------------- |
| `grep`  | `grep [-options] [pattern] [file...]` | Ricerca righe che contengono il pattern                    |
| `tee`   | `tee [file...]`                       | "Sdoppia" l'input: scrive su file E su stdout              |
| `sort`  | `sort [-n] [-r] [file...]`            | Ordina le righe (`-n` numerico, `-r` inverso)              |
| `rev`   | `rev [file...]`                       | Rovescia ogni riga carattere per carattere                 |
| `cut`   | `cut [-d delim] [-f n] [file...]`     | Estrae colonne/campi                                       |
| `head`  | `head [-n N] [file...]`               | Prime N righe                                              |
| `tail`  | `tail [-n N] [file...]`               | Ultime N righe                                             |
| `wc`    | `wc [-l] [-w] [-c] [file...]`         | Conta righe (`-l`), parole (`-w`), caratteri (`-c`)        |
| `ps`    | `ps aux`                              | Lista processi (tutti gli utenti, inclusi senza terminale) |
| `sleep` | `sleep Ns`                            | Dorme N secondi                                            |
| `expr`  | `expr val1 op val2`                   | Valuta espressioni aritmetiche                             |

### awk

```bash
awk '<pattern> { action }' [<file>...]
```

| Esempio | Effetto |
|---------|---------|
| `awk '/str/ { print }' file1` | Equivalente a `grep str file1` |
| `awk '/^In/ { print }' file1` | Stampa righe che iniziano con "In" |
| `awk '/^In/ { print $2 }' file1` | Stampa solo la 2a parola delle righe che iniziano con "In" |
| `awk -F';' '{ print $4}' file1` | Usa `;` come delimitatore, stampa il 4° campo |
| `ls -la \| awk '{ print $3"***"$1}'` | Stampa 3° e 1° campo separati da `***` |

> **Nota**: awk usa lo spazio come delimitatore implicito e gestisce correttamente spazi multipli.

---

## 3. Ridirezione e Piping

### Ridirezione

```bash
comando < file_input          # stdin da file (aperto in lettura)
comando > file_output         # stdout su file (creato o sovrascritto)
comando >> file_output        # stdout in append su file
```

> **Strano**: `> file1` da solo (senza comando) crea un file vuoto o svuota un file esistente.

Esempi:
```bash
ls -l > file             # 'file' conterrà l'output di ls -l
sort < file > file2      # ordina 'file', risultato in 'file2'
echo ciao >> file        # aggiunge "ciao" in fondo a 'file'
```

### Piping

```bash
comando1 | comando2
```

In UNIX la pipe è un **costrutto parallelo**: i due comandi girano contemporaneamente, non ci sono file temporanei (a differenza di DOS).

Esempi:
```bash
who | wc -l                              # conta utenti collegati
ls -l | grep ^d | rev | cut -d' ' -f1 | rev   # nomi delle sotto-directory
```

> **Spiegazione pipeline**: `ls -l` → `grep ^d` filtra solo le directory (il primo char di `ls -l` è il tipo) → `rev` rovescia ogni riga → `cut -d' ' -f1` estrae la prima colonna → `rev` raddrizza i nomi.

---

## 4. Metacaratteri ed espansione

### Wild card (glob)

| Metacarattere | Significato |
|--------------|-------------|
| `*` | Qualsiasi stringa di zero o più caratteri in un nome di file |
| `?` | Un qualunque singolo carattere in un nome di file |
| `[zfc]` | Un carattere nell'insieme. Range: `[a-d]` |
| `#` | Commento fino a fine linea |
| `\` | **Escape**: il carattere successivo non è interpretato come speciale |

Esempi:
```bash
ls ese*                  # file che iniziano con "ese"
ls [a-p,1-7]*[c,f,d]?   # iniziale tra a-p o 1-7, penultimo char c/f/d
cat esempio.txt > out\*.txt   # crea file di nome letterale "out*.txt"
ls *\**                  # file che contengono * nel nome
```

---

## 5. Variabili

Le variabili shell sono **STRINGHE** (sempre).

```bash
VAR=valore          # assegnamento (NO spazi attorno a =!)
echo $VAR           # accesso al valore
```

> **ATTENZIONE**: `VAR = valore` con spazi è un ERRORE (la shell interpreta `VAR` come comando).
> Il newline è significativo: `name=Daniela echo hello $name!` NON è uguale a mettere i due comandi su righe separate.

```bash
# Esempio SBAGLIATO:
X=1+3
echo $X     # stampa "1+3", non 4 !!!

# I nomi sono case sensitive:
Y=2
x=$Y        # x vale "2"
X=pippo     # X vale "pippo" (diverso da x!)
```

### Variabili di ambiente

```bash
set                          # mostra tutte le variabili di ambiente
PATH=$PATH:/usr/local/bin    # aggiunge /usr/local/bin al PATH
```

Variabili predefinite notevoli: `PATH`, `HOME`, `USER`, `SHELL`, `TERM`, `PWD`, `PPID`, `UID`

---

## 6. expr e Backtick

### expr

```bash
expr val1 op val2    # gli spazi sono OBBLIGATORI
expr 1 + 3           # → 4
```

> **Trabocchetto**: `echo risultato: expr 1 + 3` stampa letteralmente `risultato: expr 1 + 3`, non `risultato: 4`!
> Serve il backtick per eseguire il comando e sostituire l'output.

### Backtick `` ` ``

`` `comando` `` o `$(comando)` — esegue il comando e sostituisce il risultato nel testo.

```bash
echo risultato: `expr 5 + 1`   # → "risultato: 6"
D=`expr $A + $B`               # D contiene il valore numerico
```

> **Come digitarlo**: AltGr + `'` oppure AltGr + 96. Su MacOS: option+9.

---

## 7. Passi di sostituzione e inibizione

### Ordine dei passi (prima dell'esecuzione)

```
R → ridirezione input/output
1 → sostituzione comandi (backtick)      `pwd` → /temp
2 → sostituzione variabili e parametri   $HOME → /home/staff/AnnaC
3 → sostituzione metacaratteri           ?lu*o* → plutone flusso elusore ...
```

### Inibizione dell'espansione

| Carattere | Effetto |
|-----------|---------|
| `\` | Il carattere successivo è trattato come normale |
| `' '` | Apici singoli: blocca QUALSIASI espansione |
| `" "` | Doppi apici: blocca espansione **tranne** `$`, `\`, `` ` `` |

Esempi:
```bash
A=1+2
B=`expr 1 + 2`
# A contiene la stringa "1+2", B contiene la stringa "3"

rm '*$var*'         # rimuove file di nome letterale *$var* (nessuna espansione)
rm "*$var*"         # rimuove file che iniziano con * e contengono <valore di var>

echo "<`pwd`>"      # stampa </Users/AnnaC>   (backtick funziona dentro "")
echo '<`pwd`>'      # stampa <`pwd`>          (apici singoli bloccano tutto)
```

---

## 8. Shell Scripting — File comandi

### Struttura base

```bash
#!/bin/bash
# Prima riga: shebang — indica al SO quale interprete usare
# Se assente, usa la shell preferita dell'utente (/etc/passwd)
```

> **Strano**: `#` è commento per la shell, ma `#!` è una **direttiva per il SO** (shebang).
> Il SO legge `#!` e usa l'interprete indicato per eseguire lo script.

### Rendere eseguibile

```bash
chmod u+x script.sh
./script.sh arg1 arg2
```

---

## 9. Variabili speciali

### Variabili posizionali

```bash
./script arg1 arg2 ... argN
```

| Variabile       | Valore                                                                |
| --------------- | --------------------------------------------------------------------- |
| `$0`            | Nome del comando (script) stesso                                      |
| `$1`, `$2`, ... | Argomenti posizionali                                                 |
| `$*`            | Tutti gli argomenti ($1 $2 ...) come unica stringa                    |
| `$#`            | Numero di argomenti (**$0 escluso!**)                                 |
| `$$`            | PID del processo in esecuzione                                        |
| `$?`            | Valore di uscita dell'ultimo comando eseguito (0=successo, >0=errore) |
| `$@`            | Tutti gli argomenti come una lista separata                           |

> **Nota su `$$`**: `echo $$` dalla shell stampa sempre lo stesso PID (echo è builtin, gira nella stessa shell). `./script` mostra un PID diverso a ogni invocazione perché viene creata una nuova shell figlia con `fork`.

> **Nota su `$?`**: convenzione **opposta** al C! `0 = true/successo`, `non-zero = false/errore`.

### shift

```bash
shift    # sposta tutti gli argomenti di una posizione a sinistra ($1 viene perso, $0 invariato)
```

Esempio:
```bash
# Prima di shift: $0=script, $1=-w, $2=/usr/bin
# Dopo shift:     $0=script, $1=/usr/bin
```

### set per riassegnare le posizionali

```bash
set Pluto Pippo Paperino    # riassegna: $1=Pluto, $2=Pippo, $3=Paperino
                             # $0 NON può essere riassegnato
```

---

## 10. Input/Output

```bash
read var1 var2 var3         # legge da stdin, assegna per posizione
echo stringa $var espansa   # scrive su stdout con espansione variabili
```

---

## 11. test e confronti

### Sintassi

```bash
test [expression]           # forma lunga
[ expression ]              # forma compatta (stessa semantica, spazi OBBLIGATORI)
[[ expression ]]            # forma estesa (keyword del linguaggio)
```

> **ATTENZIONE agli spazi**: `[a=b]` genera `[a=b]: command not found`. Ci vogliono spazi: `[ a = b ]`.

### Test su file

```bash
test -f <file>      # vero se file esiste ed è un file normale
test -d <file>      # vero se è una directory
test -r <file>      # vero se è leggibile (-w scrivibile, -x eseguibile)
```

### Test su stringhe

```bash
test -z <stringa>             # vero se stringa è NULLA (lunghezza zero)
test <stringa>                # vero se stringa è NON NULLA
test <str1> = <str2>          # uguaglianza (spazi attorno a = obbligatori!)
test <str1> != <str2>         # diversità
```

### Test su numeri interi

```bash
test <n1> -gt <n2>    # n1 > n2
test <n1> -lt <n2>    # n1 < n2
test <n1> -le <n2>    # n1 <= n2
test <n1> -ge <n2>    # n1 >= n2
test <n1> -ne <n2>    # n1 ≠ n2
test <n1> -eq <n2>    # n1 = n2
```

### Combinazioni logiche

Con `test` o `[ ]`:
```bash
test $1 = si -a $2 -le 24    # AND
test $1 = si -o $2 -le 24    # OR
```

Con `[[ ]]` si usano gli operatori `&&` e `||`:
```bash
[[ $1 = si && $2 -le 24 ]]
```

### Vantaggi di `[[ ]]` rispetto a `[ ]`

1. **Metacaratteri sicuri**: `[[ $a = res* ]]` confronta $a con il pattern `res*` (senza espandere glob nel filesystem). Con `[ $a = res* ]` invece `res*` viene espanso come glob → se ci sono più file match → errore `[: too many arguments`.
2. **Operatori logici**: `&&` e `||` invece di `-a` e `-o`
3. **Regex**: operatore `=~` per confronto con espressioni regolari
4. **`>` e `<`** per confrontare stringhe (non vengono interpretati come ridirezione I/O)

```bash
[[ $a =~ ^[0-9]+$ ]]         # vero se $a è un intero positivo o zero
[[ $a =~ ^[1-9][0-9]*$ ]]    # vero se $a è un intero positivo, non zero
```

> **`[ ]` è un comando builtin**, `[[ ]]` è una **keyword** del linguaggio (come `if`, `for`, ...).

---

## 12. Strutture di controllo

### if

```bash
if <condizioni>
then
    <comandi>
[elif <condizioni>
then
    <comandi>]
[else
    <comandi>]
fi
```

Forma compatta (condizione e then sulla stessa riga):
```bash
if <condizioni>; then
    <comandi>
fi
```

> **ATTENZIONE**: le parole chiave (`then`, `else`, `elif`, `fi`) devono essere a capo **oppure** dopo il separatore `;`.

> **Strano**: la condizione dell'`if` può essere una **lista di comandi**. In tal caso, `if` controlla il valore di uscita dell'**ultimo** comando della lista.

```bash
if ls; cat u; then    # esegue ls E cat u; controlla l'esito di cat u
    echo ok
else
    echo error
fi
```

La negazione si fa con `!`:
```bash
if [ ! -d $1 ]; then echo "$1 non è una directory"; fi
```

### case

```bash
case <var> in
    <pattern-1>)
        <comandi>;;
    <pattern-i> | <pattern-j>)   # OR tra pattern
        <comandi>;;
    *)                            # default (qualsiasi valore)
        <comandi>;;
esac
```

> Si possono usare **metacaratteri** per il pattern matching nei case.

Esempio:
```bash
read risposta
case $risposta in
    S* | s* | Y* | y* ) echo "risposta positiva";;
    * )                  echo "risposta negativa";;
esac
```

Esempio con `$#` (numero argomenti):
```bash
case $# in
    1) cat >> $1;;
    2) cat < $1 >> $2;;
    *) echo "uso: append [in_file] out_file"; exit 1;;
esac
```

### for (ciclo enumerativo)

```bash
for <var> [in <list>]
do
    <comandi>
done
```

> Se si scrive solo `for i` (senza `in`), itera su `$*` (i parametri posizionali `$1 $2 $3 ...`).

Esempi:
```bash
for i in *                        # ripete per ogni file nel dir corrente
for i in `ls s*`                  # ripete per ogni file che inizia per s
for i in `cat file1`              # ripete per ogni parola in file1
for i in 0 1 2 3 4 5 6           # ripete per i=0,1,...,6
for i in 1 2 3 4 5; do cp $1 "$1*$i$2"; done   # 5 copie
```

### while / until

```bash
while <condizione o lista-comandi>
do
    <comandi>
done

until <condizione o lista-comandi>   # come while ma inverte la condizione
do
    <comandi>
done
```

Si ripete se l'ultimo comando della condizione termina con stato=0 (successo).

```bash
OK=0
while [ $OK -lt 10 ]
do
    ...
    OK=`expr $OK + 1`
    sleep 5s
done
```

### Uscite anomale dai cicli

```bash
break       # esce dal ciclo (come in C)
continue    # passa all'iterazione successiva
return      # ritorna dalla funzione
exit [N]    # termina lo script con codice N (0=successo, altri=errore)
```

---

## 13. Esempi completi

### Es. 1 — ps_monitor.sh `[N]`

Mostra tutti i processi (o i primi N se passato argomento).

```bash
#!/bin/bash
if [ -z $1 ]       # -z: vero se $1 è stringa vuota (argomento assente)
then
    ps aux
else
    ps aux | head -n `expr $1 + 1`   # +1 perché c'è la riga di intestazione
fi
```

> **Nota**: `head -n` con backtick su `expr` — il `+1` gestisce la riga di intestazione di `ps aux`.

---

### Es. 2 — lines_counter.sh `<directory> [up|down]`

Elenca i file con numero di linee, ordinati.

```bash
#!/bin/bash
if [ $# -ne 2 ]
then
    echo "SINTASSI: lines_counter.sh <directory> [up|down]"
    exit 1
fi
if [ -d $1 ]
then
    if [ $2 = "up" ]
    then
        wc -l $1/* | sort -n     # sort numerico crescente
    elif [ $2 = "down" ]
    then
        wc -l $1/* | sort -nr    # sort numerico decrescente (-r = reverse)
    else
        echo "ERROR: 'up' or 'down'"
        exit 2
    fi
else
    echo "$1 should be an existent directory"
    exit 2
fi
```

> `wc -l $1/*` — l'espansione glob `$1/*` genera la lista di tutti i file in `$1`, poi `wc -l` conta le righe di ognuno.

---

### Es. 3 — backup.sh `<nomefile> <nomebackup>`

```bash
#!/bin/bash
if [ $# -ne 2 ]
then
    echo "USAGE: backup.sh <filename> <backupstring>"
    exit 1
fi
if [ -d $1 ]
then
    cp -R $1 "$1_$2"         # copia ricorsiva directory → <nome>_<backup>
elif [ -f $1 ]
then
    for i in 1 2 3 4 5
    do
        cp $1 "$1*$i$2"      # 5 copie: nome*1backup, nome*2backup, ...
    done                     # "" evitano espansione di * ma NON di $
else
    echo "$1 should be a valid directory or file"
fi
```

> **Importante**: `"$1*$i$2"` — i doppi apici inibiscono l'espansione di `*` (glob) ma **non** di `$` (variabile). Quindi `*` resta letterale e `$i`, `$2` vengono espansi.

---

### Es. 4 — directory monitor `./esempio D`

Monitora ogni 5 secondi se cambiano i file in una directory (10 iterazioni).

```bash
#!/bin/bash
if [ $# -ne 1 ] ; then echo Sintassi! ; exit; fi
if [ ! -d $1 ]; then echo "$1 non è una directory!"; exit; fi

echo 0 > loop.$$.tmp     # file temporaneo con nome univoco (usa il PID)
OK=0
while [ $OK -lt 10 ]
do
    new=`ls -l "$1" | wc -l`    # conta file (ls -l, non wc -w, per gestire nomi con spazi)
    new=`expr $new - 1`          # toglie la riga "total N" di ls -l
    old=`cat loop.$$.tmp`
    if [ $new -ne $old ]
    then
        echo $new > loop.$$.tmp
        echo "in $1 ci sono $new file"
    fi
    OK=`expr $OK + 1`
    sleep 5s
done
rm loop.$$.tmp
```

> **Trucchi notevoli**:
> - `loop.$$.tmp` — usa `$$` (PID) per rendere il nome del file temporaneo unico, evitando conflitti se lo script gira più volte in parallelo.
> - `ls -l "$1" | wc -l` invece di `ls "$1" | wc -w` — gestisce correttamente nomi di file con spazi.
> - `expr $new - 1` — rimuove la riga di intestazione `total N` di `ls -l`.
> - `"$1"` con doppi apici — evita problemi se il nome della directory contiene spazi.

---

## Riepilogo "cose strane" da ricordare

| Comportamento | Spiegazione |
|--------------|-------------|
| `X=1+3; echo $X` → `1+3` | Le variabili sono **stringhe**, non numeri |
| `VAR = valore` → errore | Nell'assegnamento **non ci vogliono spazi** |
| `echo risultato: expr 1+3` → stampa letteralmente | `expr` è un comando, serve il backtick per catturarne l'output |
| `expr 1+3` → errore | `expr` richiede gli **spazi** tra operatori e operandi |
| In `test`: `0 = true`, `>0 = false` | **Opposto** a C! (uniformità con exit code dei comandi) |
| `[a=b]` → `command not found` | Le `[` vogliono **spazi** dopo `[` e prima di `]` |
| `[ $a = res* ]` → potenziale errore | `res*` viene espanso come glob → errore con più match |
| `[[ $a = res* ]]` → corretto | `[[` non espande glob nel pattern |
| `echo $$` → pid stabile | `echo` è builtin → gira nella stessa shell, `$$` non cambia |
| `./script` → pid diverso ogni volta | Crea una nuova shell figlio → nuovo `$$` |
| `> file1` da solo | Crea file vuoto o svuota un file esistente |
| `for i` (senza `in`) | Itera su `$*` (i parametri posizionali dello script) |
| `if ls; cat u; then` | La condizione può essere una **lista di comandi**; `if` controlla l'uscita dell'**ultimo** |
| `echo "<\`pwd\`>"` → `/Users/AnnaC` | Il backtick funziona dentro i **doppi** apici |
| `echo '<\`pwd\`>'` → `` <`pwd`> `` | Gli **apici singoli** bloccano tutto senza eccezioni |
| `name=Daniela echo hello $name!` | NON uguale a due righe separate; il newline è **significativo** |
| `#!` vs `#` | `#` è commento per la shell; `#!` è direttiva per il **SO** (shebang) |
| `$#` conta gli argomenti | `$0` è **escluso** dal conteggio |

---

## 14. Extra: sintassi e comandi utili non nelle slide

---

### 14.1 Aritmetica con `$(( ))`

Alternativa moderna e **built-in** a `expr` — non richiede un processo figlio.

```bash
a=5
b=3
echo $(( a + b ))        # 8
echo $(( a * b ))        # 15
echo $(( a % b ))        # 2  (modulo)
echo $(( a ** 2 ))       # 25 (potenza)
c=$(( a + b ))           # assegnamento diretto, senza backtick
(( c++ ))                # incremento in-place
(( c += 10 ))            # incremento di 10
```

> `(( expr ))` restituisce 0 (true) se l'espressione è non-zero, 1 (false) se zero — utile come condizione di `if`.
> ```bash
> if (( a > b )); then echo "a è maggiore"; fi
> ```

---

### 14.2 Sostituzione di comando moderna: `$( )`

Equivalente ai backtick ma **annidabile** e più leggibile.

```bash
risultato=$(expr 5 + 1)          # uguale a `expr 5 + 1`
files=$(ls *.txt)
today=$(date +%Y-%m-%d)

# I backtick non si annidano bene:
# `echo \`pwd\``   ← difficile
# Con $() è semplice:
outer=$(echo $(pwd))
```

---

### 14.3 Ridirezione di stderr

```bash
comando 2> errori.txt          # stderr su file
comando > out.txt 2> err.txt   # stdout e stderr su file separati
comando > out.txt 2>&1         # stderr reindirizzato verso stdout (entrambi su file)
comando 2>/dev/null            # scarta gli errori (dispositivo null)
comando > /dev/null 2>&1       # scarta tutto (stdout e stderr)
```

> **Ordine importante**: `2>&1 > file` è sbagliato. `> file 2>&1` è corretto: prima si reindirizza stdout, poi si fa puntare stderr al (nuovo) stdout.

---

### 14.4 Esecuzione condizionale con `&&` e `||`

```bash
cmd1 && cmd2      # esegue cmd2 SOLO se cmd1 ha successo ($?=0)
cmd1 || cmd2      # esegue cmd2 SOLO se cmd1 fallisce ($?≠0)
```

Utile per controlli compatti:
```bash
[ -d "$dir" ] || mkdir "$dir"           # crea la dir se non esiste
[ $# -eq 2 ] || { echo "Uso: $0 a b"; exit 1; }
cd /tmp && rm -f *.tmp                  # rm solo se cd ha successo
```

---

### 14.5 Manipolazione di stringhe con `${ }`

#### Lunghezza
```bash
s="hello"
echo ${#s}        # 5
```

#### Sottostringa
```bash
s="abcdefgh"
echo ${s:2}       # "cdefgh"  (da posizione 2 in poi)
echo ${s:2:3}     # "cde"     (da posizione 2, lunghezza 3)
```

#### Rimozione di prefisso/suffisso
```bash
f="path/to/file.txt"
echo ${f#*/}       # "to/file.txt"   (rimuove prefisso più corto che fa match con */)
echo ${f##*/}      # "file.txt"      (rimuove prefisso più LUNGO → basename)
echo ${f%.txt}     # "path/to/file"  (rimuove suffisso più corto)
echo ${f%/*}       # "path/to"       (rimuove ultimo /... → dirname)
```

#### Sostituzione
```bash
s="hello world"
echo ${s/world/bash}     # "hello bash"   (prima occorrenza)
echo ${s//l/L}           # "heLLo worLd"  (tutte le occorrenze)
```

#### Valori di default
```bash
echo ${VAR:-"default"}    # se VAR è vuota/unset, usa "default" (VAR resta invariata)
echo ${VAR:="default"}    # se VAR è vuota/unset, usa "default" E assegna VAR
echo ${VAR:?"errore"}     # se VAR è vuota/unset, stampa "errore" ed esce
echo ${VAR:+"altro"}      # se VAR è settata e non vuota, usa "altro"
```

---

### 14.6 Brace expansion `{ }`

```bash
echo {a,b,c}.txt            # a.txt b.txt c.txt
echo file{1..5}.txt         # file1.txt file2.txt ... file5.txt
echo {A..Z}                 # A B C ... Z
mkdir -p progetto/{src,bin,doc,test}   # crea più directory in una volta
cp file.txt{,.bak}          # copia file.txt in file.txt.bak
```

---

### 14.7 Funzioni

```bash
nome_funzione() {
    local var_locale="valore"    # 'local' limita la variabile alla funzione
    echo "Argomenti: $1 $2"
    return 0                     # valore di ritorno (intero, come exit)
}

# Chiamata:
nome_funzione arg1 arg2
echo "Exit: $?"
```

> Le funzioni condividono le variabili con lo script chiamante (scope globale) a meno di `local`.
> `return` imposta `$?` come `exit`, ma non termina lo script.

---

### 14.8 Esecuzione in background e `wait`

```bash
comando &               # lancia in background, stampa il PID
pid=$!                  # $! = PID dell'ultimo processo mandato in background
wait                    # attende che tutti i processi background finiscano
wait $pid               # attende solo quel processo specifico
```

Esempio — elaborazione parallela:
```bash
processo1.sh &
processo2.sh &
wait
echo "Entrambi finiti"
```

---

### 14.9 Here-document `<<`

Permette di passare un blocco di testo come stdin a un comando, senza file temporanei.

```bash
cat << EOF
Questa è
una stringa
su più righe
EOF

# Con <<- si possono usare tab per indentare (i tab iniziali vengono rimossi):
cat <<- EOF
    riga indentata con tab
    altra riga
EOF

# Con apici singoli sul delimitatore si inibisce l'espansione:
cat << 'EOF'
$VAR non viene espansa qui
EOF
```

---

### 14.10 Comando `find`

```bash
find <path> [opzioni]
```

| Esempio | Effetto |
|---------|---------|
| `find . -name "*.txt"` | Tutti i `.txt` ricorsivamente da qui |
| `find . -name "*.txt" -type f` | Solo file (non directory) |
| `find . -type d` | Solo directory |
| `find . -maxdepth 1 -type f` | Solo file nella dir corrente (non ricorsivo) |
| `find . -newer file.txt` | File più recenti di `file.txt` |
| `find . -size +1M` | File più grandi di 1 MB |
| `find . -name "*.log" -delete` | Trova e **cancella** |
| `find . -name "*.sh" -exec chmod u+x {} \;` | Esegue un comando su ogni file trovato |

---

### 14.11 Comando `sed` (stream editor)

```bash
sed 's/pattern/sostituzione/' file      # sostituisce prima occorrenza per riga
sed 's/pattern/sostituzione/g' file     # sostituisce tutte le occorrenze (global)
sed -n '/pattern/p' file                # stampa solo le righe che matchano (come grep)
sed '3d' file                           # cancella la riga 3
sed '1,5d' file                         # cancella le righe da 1 a 5
sed -i 's/vecchio/nuovo/g' file         # modifica il file IN-PLACE
```

Esempio utile: rimuovere spazi iniziali e finali:
```bash
echo "  hello  " | sed 's/^[[:space:]]*//;s/[[:space:]]*$//'
```

---

### 14.12 Comando `export`

Le variabili definite in una shell sono **locali** a quella shell. I processi figli le ereditano solo se esportate.

```bash
VAR=valore
export VAR            # ora i processi figli vedono VAR
export VAR=valore     # definizione ed export in una riga

env                   # mostra tutte le variabili esportate (ambiente)
```

---

### 14.13 `printf` — output formattato

Più potente di `echo`, non ha comportamenti variabili tra sistemi.

```bash
printf "Nome: %s, Età: %d\n" "Mario" 30    # Nome: Mario, Età: 30
printf "%05d\n" 42                          # 00042  (zero-padding)
printf "%.2f\n" 3.14159                     # 3.14
```

---

### 14.14 `xargs` — costruisce comandi dall'input

```bash
ls *.txt | xargs wc -l              # conta righe di tutti i .txt
find . -name "*.tmp" | xargs rm     # cancella tutti i .tmp trovati
echo "a b c" | xargs -n1 echo       # stampa un argomento per riga
find . -name "*.sh" | xargs -I{} cp {} /backup/   # {} è il placeholder
```

---

### 14.15 `trap` — gestione segnali

Permette di eseguire comandi quando lo script riceve un segnale o termina.

```bash
trap "rm -f /tmp/miofile.tmp; echo 'Pulizia eseguita'" EXIT   # sempre alla fine
trap "echo 'Interrotto!'; exit 1" INT                          # su CTRL+C (SIGINT)
trap '' TERM                                                    # ignora SIGTERM
trap - INT                                                      # ripristina default
```

Uso classico: pulizia file temporanei garantita:
```bash
tmpfile=$(mktemp /tmp/script.XXXXXX)
trap "rm -f $tmpfile" EXIT
# ... usa $tmpfile liberamente, verrà rimosso anche in caso di errore
```

---

### 14.16 `getopts` — parsing delle opzioni

```bash
while getopts "hn:v" opt; do
    case $opt in
        h) echo "Uso: $0 [-h] [-n nome] [-v]"; exit 0;;
        n) NOME="$OPTARG";;      # OPTARG contiene il valore dell'opzione
        v) VERBOSE=1;;
        ?) echo "Opzione non valida"; exit 1;;
    esac
done
shift $(( OPTIND - 1 ))    # rimuove le opzioni, $@ contiene solo gli argomenti rimanenti
```

> Il `:` dopo una lettera (es. `n:`) indica che quell'opzione richiede un argomento.

---

### 14.17 Array

```bash
arr=(uno due tre quattro)
echo ${arr[0]}           # "uno"
echo ${arr[@]}           # tutti gli elementi
echo ${#arr[@]}          # numero di elementi
arr+=(cinque)            # aggiunge un elemento
unset arr[2]             # rimuove l'elemento in posizione 2

# Iterare:
for el in "${arr[@]}"; do
    echo "$el"
done
```

---

### 14.18 IFS — separatore di campo

`IFS` (Internal Field Separator) determina come la shell divide le stringhe in parole. Default: spazio, tab, newline.

```bash
IFS=':'
for parte in $PATH; do
    echo "$parte"         # stampa ogni directory del PATH su riga separata
done
unset IFS                 # ripristina il default

# Lettura di CSV:
IFS=',' read -r col1 col2 col3 <<< "mario,rossi,30"
echo "$col1 / $col2 / $col3"   # mario / rossi / 30
```

---

### Riepilogo extra: confronto rapido tra forme equivalenti

| Vecchio stile (slide) | Stile moderno | Note |
|-----------------------|---------------|------|
| `` `comando` `` | `$(comando)` | `$()` è annidabile |
| `expr $a + $b` | `$(( a + b ))` | `$(())` è builtin, più veloce |
| `test -d $f` | `[ -d "$f" ]` | Preferire `"$f"` con virgolette |
| `[ ]` | `[[ ]]` | `[[ ]]` più sicuro con metacaratteri |
| `if [ $? -eq 0 ]` | `if comando` | Testare direttamente l'exit code |
