# ReceiptPrinter

## Descrizione del progetto:

Il progetto contenuto nel repository è un progetto Maven che contiene un insieme di classi Java e dei relativi 
unit test.
Le funzionalità del progetto sono relative ad un gestore di ricevute e prodotti. Esse sono:

1. Inserimento di prodotti in un DB da linea di comando
2. Creazione e stampa di ricevute direttamente da linea di comando
3. Creazione di ricevute a partire da file .csv e stampa di esse su file di testo
4. Stampa di ricevute su linea di comando selezionando i prodotti dal DB
5. Inserire nuovi prodotti nel DB a partire da un file .csv

# DevOps

### Branch e Tag

Nel corso del lavoro, oltre al branch **master** sono stati creati altri due branch, **develop** e **release**, 
per permettere un'esecuzione mirata ed indipendente di solo alcune fasi della pipeline. Sul branch **master** è 
presente il lavoro finale di creazione della pipeline, infatti su di esso sono eseguiti tutti gli stage che sono stati 
sviluppati. I branch **develop** e **release** invece, presentano le due fasi di release e create-branch, 
oltre alla fase change-bugfix-version (in seguito abbandonata dalla pipeline del branch **master**). 
Essi sono stati usati per testare l'esecuzione dello stage di release.


Nell'elenco dei branch del repository è visibile anche un'insieme di branch e tag, 
i quali sono associati alle versioni rilasciate. Questi branch e tag sono generati automaticamente dagli stage che si 
occupano della release. Quest'ultima procedura è descritta in maniera più dettagliata nei paragrafi dedicati alla 
release.


### Image

All'inizio del file *.gitlab-ci.yml* è stata definita l'immagine Maven con cui realizzare i successivi comandi
negli stage.

### Variables

All'interno del file *.gitlab-ci.yml* sono state definite le variabili `MAVEN_OPTS` e `MAVEN_CLI_OPTS` che sono state
successivamente utilizzate negli stage per i comandi di Maven. 

È stata anche definita la variabile `SSH_GIT_URL` per specificare l'url del repository, necessario alla connessione 
con SSH per lo stage di *release*.

### Cache

È stato implementato un meccanismo di caching per permettere un'esecuzione più rapida delle varie fasi della pipeline.
La cache viene utilizzata per specificare un elenco di file e directory che devono essere memorizzati tra i vari
job.

La cache è stata impostata per essere condivisa dagli stage che appartengano allo stesso branch,
 tramite la variabile predefinita `"$CI_COMMIT_REF_SLUG"`, specificata tramite l'etichetta `key:`.
 
 
Tramite l'etichetta `paths:` è stato specificato quale path utilizzare per memorizzare i file nella cache.

### Stage

Gli stage che compongono la pipeline sono 7, e sono i seguenti:

    - build
    - verify
    - unit-test
    - integration-test
    - package
    - release
    - create-branch
    
I primi 6 sono esplicitamente richiesti dal testo dell'assignment, il settimo lo abbiamo creato in supporto allo stage
di *release*.


L'esecuzione della pipeline in ciascuna delle sue fasi viene definita all'interno del file *.gitlab-ci.yml*. 
Di seguito sono presentate in maggior dettaglio le varie fasi, e le relative istruzioni implementate. 
 
#### Stage di build

La fase di build è stata realizzata con Maven. Questa fase è stata effettuata tramite la seguente istruzione:
  
    - mvn $MAVEN_CLI_OPTS $MAVEN_OPTS compile

#### Stage di verify

Lo stage di verify è stato suddiviso in 2 parti:

###### checkstyle

La fase di checkstyle permette di verificare la correttezza del codice di un progetto Maven. 
È stata realizzata tramite l'istruzione:
  
    - mvn $MAVEN_CLI_OPTS $MAVEN_OPTS checkstyle:checkstyle
    
È stata utilizzata la `cache:` che fa uso anche della variabile `$CI_JOB_STAGE`, la quale racchiude il nome
dello stage della pipeline definito nel file *.gitlab-ci.yml*.


Inoltre, è stata definita l'etichetta `artifacts:` la quale serve a memorizzare il risultato dell'esecuzione 
del checkstyle nel caso in cui esso fallisca.

###### findbugs

La fase di findbugs permette la realizzazione di report relativi all'analisi statica del codice Java. Essi contengono
potenziali bug che potrebbero riguardare il codice sottoposto all'analisi.
Questa fase è stata realizzata tramite l'istruzione:

    - mvn $MAVEN_CLI_OPTS $MAVEN_OPTS site
    
Anche in questo caso è stata utilizzata l'etichetta `cache:`.


È stata anche definita l'etichetta `artifacts:`, la quale serve a memorizzare i report di findbugs.

#### Stage di unit-test

Nella fase di unit-test è stato verificato il corretto funzionamento delle classi
 che compongono il progetto. Per il testing è stato impiegato JUnit. La fase è stata realizzata tramite l'istruzione:

    - mvn $MAVEN_CLI_OPTS $MAVEN_OPTS test
    
Anche in questo stage è stata utilizzata l'etichetta `cache:`.

#### Stage di integration-test

Nella fase di integration-test è stata verificata la corretta interazione del progetto con il DB. 
Nel nostro caso sono state testate:

* Connessione al DB
* Restituzione dei prodotti contenuti nel DB
* Restituzione di uno specifico prodotto contenuto nel DB

Per il testing è stato impiegato JUnit. La fase è stata realizzata tramite l'istruzione:

    - mvn $MAVEN_CLI_OPTS $MAVEN_OPTS integration-test
    
Anche in questo stage è stata utilizzata l'etichetta `cache:`.

#### Modifiche al pom per gestire gli stage di unit-test e integration-test

Nel *pom.xml* è stato incluso il plugin **maven-surefire-plugin**. Tramite esso sono state definite due esecuzioni:

1. L'esecuzione degli unit-test attraverso l'**esclusione** di tutti i file con formato `**/*IntegrationTest.java`
2. L'esecuzione degli integration-test attraverso l'**inclusione** di tutti i file con 
formato `**/*IntegrationTest.java`

#### Stage di package

Nella fase di package è stato creato il file .jar del progetto. L'istruzione che lo realizza è la seguente: 
  
    - mvn $MAVEN_CLI_OPTS $MAVEN_OPTS package
    
Anche in questo stage è stata utilizzata l'etichetta `cache:`.


Tramite l'etichetta `artifacts:` viene specificato il path in cui memorizzare il .jar in caso di successo dello stage
di package.

#### Stage di release

Per la fase di release è stato utilizzato il `maven-release-plugin`. \
Inizialmente viene instaurata una connessione SSH, la quale serve a realizzare le operazioni di `push` sul repository.
Nel nostro caso, viene effettuato un commit per modificare nel file *pom.xml* l'identificativo della versione 
rilasciata.
 

Per instaurare la connessione, è stato necessario fornire la **chiave privata SSH** che è stata definita nelle variabili
 d'ambiente per la pipeline di GitLab con il nome `$SSH`.
Inoltre sono state fornite le credenziali di un utente attraverso le variabili `$PUSH_USER_NAME` e `$PUSH_USER_EMAIL`, 
le quali vengono utilizzate per associare un utente ai commit. 
Ad ogni release viene creato automaticamente un tag, tramite la notazione `v@{project.version}`
 specificata nel *pom.xml* attraverso il `maven-release-plugin`.


Una release del progetto viene creata con la seguente istruzione:

    - mvn $MAVEN_CLI_OPTS clean release:prepare -Dresume=false -DdryRun=false
     -Dmaven.test.skip=true -DscmCommentPrefix="Release pom [ci skip]"
     
Anche in questo stage è stata utilizzata l'etichetta `cache:`.

#### Stage di create-branch

Nella fase di create-branch viene creato un branch per la versione del progetto rilasciata.

 
Come nella fase di release, viene instaurata una connessione SSH, con lo scopo di creare appunto un nuovo branch ed 
effettuare il relativo `push` sul repository. È stato necessario definire una variabile `$version`, 
a cui viene assegnato il valore dell'ultima versione del progetto rilasciata.


La creazione e il relativo push del nuovo branch sono stati effettuati tramite le seguenti istruzioni:

    - git checkout -b $version

    - git push --set-upstream origin $version
    
