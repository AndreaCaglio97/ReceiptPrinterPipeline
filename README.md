# ReceiptPrinter

## Assignment realizzato da un team di due sviluppatori:

* Andrea Caglio 816762
* Valeria Fedel (n.ro matricola in fase di assegnamento)

Link al repository del progetto: https://gitlab.com/a.caglio5/2019_assignment1_receiptprinter 

#### Cambio di repository:

Ci siamo resi conto che il progetto contenuto nel repository che abbiamo creato in precedenza, possedeva delle dipendenze non necessarie.
Per questo motivo abbiamo creato questo nuovo repository, in cui abbiamo caricato lo stesso progetto senza le dipendenze non occorrenti.
Il link del vecchio repository è il seguente:
https://gitlab.com/a.caglio5/2019_assignment1_productservletmvn

## Descrizione progetto:

Il progetto contenuto nel repository è un progetto Maven che contiene un insieme di classi Java e dei relativi unit test.
Le funzionalità del progetto sono relative ad un gestore di ricevute e prodotti. Esse sono:

1. Inserimento di prodotti in un DB da linea di comando
2. Creazione e stampa di ricevute direttamente da linea di comando
3. Creazione di ricevute a partire da file .csv e stampa di esse su file di testo
4. Stampa di ricevute su linea di comando selezionando i prodotti dal DB
5. Inserire nuovi prodotti nel DB a partire da un file .csv

# DevOps

## Branch

Oltre al branch *master* sono stati creati altri due branch, *develop* e *release*, per permettere un'esecuzione mirata ed indipendente di solo alcune fasi della pipeline.
Il branch *master* presenta tutte le fasi della pipeline, e rappresenta quindi il lavoro finale di creazione della pipeline. I branch *develop* e *release* invece presentano le due fasi di release e create-branch, oltre alla fase change-bugfix-version (in seguito abbandonata nella pipeline del branch *master*). Esse sono state usate per testare in maggior dettaglio l'esecuzione della pipeline a partire dallo stage di release. 


## Continuous Integration and Continuous Deployment (CI/CD)

### Cache

È stata implementato un meccanismo di caching per permettere un'esecuzione più rapida delle varie fasi della pipeline. La cache è condivisa da tutti gli stage eseguiti che appartengono allo stesso branch. 

### Stage

Gli stage che implementano la pipeline CI/CD sono 7, e sono i seguenti:

    - build
    - verify
    - unit-test
    - integration-test
    - package
    - release
    - create-branch
    
L'esecuzione della pipeline in ciascuna delle sue fasi viene definita all'interno del file .gitlab-ci.yml. 
Di seguito sono presentate in maggior dettaglio le varie fasi, e le relative istruzioni che implementano. 
    
#### Stage di build

La fase di build è realizzata con Maven, per creare un'istanza eseguibile del progetto, e per scaricare le dipendenze. Questa fase è realizzata tramite la seguente istruzione:
  
    - mvn $MAVEN_CLI_OPTS $MAVEN_OPTS compile

#### Stage di verify

La fase di verify permette di verificare la correttezza del codice di un progetto Maven. Viene realizzata tramite l'istruzione:
  
    - mvn $MAVEN_CLI_OPTS $MAVEN_OPTS checkstyle:checkstyle

#### Stage di unit-test

Nella fase di unit-test è verificata la correttezza e il corretto funzionamento delle singole classi che compongono il progetto. Per il testing è stato impiegato JUnit. La fase è realizzata tramite l'istruzione:

    - mvn $MAVEN_CLI_OPTS $MAVEN_OPTS test

#### Stage di integration-test

Nella fase di integration-test viene verificato il corretto funzionamento del progetto anche nell'interazione tra le varie componenti. Viene quindi verificata la corretta connessione del programma al DB MySQL. Per il testing è stato impiegato JUnit. La fase è realizzata tramite l'istruzione:

    - mvn $MAVEN_CLI_OPTS $MAVEN_OPTS integration-test

#### Stage di package

Nella fase di package viene creato un file .jar del progetto. L'istruzione che la realizza è la seguente: 
  
    - mvn $MAVEN_CLI_OPTS $MAVEN_OPTS package
    
#### Stage di release

La fase di release viene realizzata tramite il Release Plugin di Maven. 
È stata inizialmente instaurata una connessione SSH, per permettere ad un utente di effettuare modifiche sul file POM.xml sull'ultima versione rilasciata. Per instaurare la connessione, è necessario fornire ad un utente i permessi per operare su file POM.xml. Sono perciò state passate le credenziali GitLab di un utente, ed un valore di chiave privata SSH, salvando questi valori nelle impostazioni del repository su GitLab. 
Una release del progetto viene in seguito creata con la seguente istruzione:

    - mvn $MAVEN_CLI_OPTS clean release:prepare -Dresume=false -DdryRun=false -Dmaven.test.skip=true -DscmCommentPrefix="Release pom [ci skip]"

#### Stage di create-branch

Nella fase di create-branch viene creato un branch per ciascuna versione rilasciata del progetto. Come nella fase di release viene instaurata una connessione SSH, per permettere di effettuare modifiche sulla struttura del repository. Viene in seguito definita una variabile "version", a cui viene assegnato il valore dell'ultima release rilasciata. La creazione del nuovo branch si effettua tramite l'istruzione:

    - git push --set-upstream origin $version
