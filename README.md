# ReceiptPrinter

## Assignment realizzato da un team di due sviluppatori:

* Andrea Caglio 816762
* Valeria Fedel (n.ro di matricola in attesa di assegnamento)

Link al repository del progetto: https://gitlab.com/a.caglio5/2019_assignment1_receiptprinter 

#### Cambio di repository:

Ci siamo resi conto che il progetto contenuto nel repository che abbiamo creato in precedenza, possedeva delle dipendenze non necessarie.
Per questo motivo abbiamo creato questo nuovo repository, in cui abbiamo caricato lo stesso progetto senza le dipendenze non occorrenti.
Il link del vecchio repository è il seguente:
https://gitlab.com/a.caglio5/2019_assignment1_productservletmvn

## Descrizione progetto:

Il progetto contenuto nel repository è un progetto maven che contiene un insieme di classi Java e dei relativi unit test.
Le funzionalità del progetto sono relative ad un gestore di ricevute e prodotti. Esse sono:

1. Inserimento di prodotti in un DB da linea di comando
2. Creazione e stampa di ricevute direttamente da linea di comando
3. Creazione di ricevute a partire da file .csv e stampa di esse su file di testo
4. Stampa di ricevute su linea di comando selezionando i prodotti dal DB
5. Inserire nuovi prodotti nel DB a partire da un file .csv

# DevOps

#### Stage di build

Realizzata build con maven all'interno del file .gitlab-ci.yml tramite la seguente istruzione:
  
    - mvn compile


#### Stage di unit-test

Realizzata fase di unit-test con maven all'interno del file .gitlab-ci.yml tramite la seguente istruzione:
  
    - mvn test
    
#### Stage di package

Realizzata fase di package con maven all'interno del file .gitlab-ci.yml tramite la seguente istruzione:
  
    - mvn package
