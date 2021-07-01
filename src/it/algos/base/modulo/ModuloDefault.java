/**
 * Title:        ModuloDefault.java
 * Package:      it.algos.base.modulo
 * Description:  Abstract Data Types per la struttura dei dati di un modulo
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 20 luglio 2003 alle 19.50
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.modulo;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloDefault;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe concreta e' responsabile di:<br>
 * A - Costruire un nuovo tipo di dato (record in pascal), per ogni modulo <br>
 * B - Serve come contenitore di tutte le informazioni necessarie e sufficenti
 * per avviare ed utilizzare un modulo <br>
 * Mantiene come attributo il nome del modulo <br>
 * Mantiene i riferimenti a tutte le classi non standard <br>
 * C - Una applicazione e' un modulo che parte per primo <br>
 * D - Tipicamente si costruisce un oggetto di questa classe, si regolano qui
 * tutte le variabili ed i riferimenti (compreso quello al gestore
 * principale), poi si invoca il metodo <code>lancia</code> di questa
 * classe (col parametro obbligatorio di inizio codificato) ed a questo
 * punto ci sono tutte le informazioni che servono e viene invocato il
 * metodo lancia della classe gestore <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  20 luglio 2003 ore 19.50
 */
public final class ModuloDefault extends ModuloBase {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * nome di questo modulo (obbligatorio)
     */
    private static final String NOME_MODULO = "modulodefault";

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = NOME_MODULO;


    //-------------------------------------------------------------------------
    // Variabili statiche della classe         (private)      (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Riferimenti ad altri oggetti    (private,protette)  (instance variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Variabili degli oggetti (private,protette,pubbliche)(instance variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito) <br>
     */
    public ModuloDefault() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(NOME_MODULO);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo <br>
     *
     * @param unNodo nodo dell'albero moduli
     */
    public ModuloDefault(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(NOME_MODULO, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo costruttore completo */


    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati chiamati dal costruttore prima di tornare alla sottoclasse
    //-------------------------------------------------------------------------
    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /** selezione del modello (obbligatorio) */
        super.setModello(new ModelloDefault());
    } /* fine del metodo inizia */


    //-------------------------------------------------------------------------
    // Metodi protetti chiamati dalla sottoclasse subito dopo il costruttore
    //-------------------------------------------------------------------------
    /**
     * Regolazioni iniziali, dopo che sono stati regolati dalla sottoclasse
     * i parametri indispensabili (tra cui il riferimento al modulo)
     * Metodo chiamato dalla classe che crea questo oggetto
     * Viene eseguito una sola volta
     */
    public boolean inizializza() {
        /** invoca il metodo sovrascritto della superclasse */
        return super.inizializza();
    } /* fine del metodo */


    /**
     * Regolazioni di avvio, ogni volta che questo oggetto deve 'ripartire', per
     * essere sicuri che sia 'pulito'
     * Metodo chiamato da altre classi
     * Viene eseguito tutte le volte che necessita
     */
    public void avvia() {
        /** invoca il metodo sovrascritto della superclasse */
        super.avvia();
    } /* fine del metodo */


    //-------------------------------------------------------------------------
    // Metodi privati degli oggetti (usati nella classe)     (instance methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi protetti degli oggetti (usati nelle sottoclassi)    (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi che verranno sovrascritti nelle sottoclassi          (overriding)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi che sovrascrivono metodi della superclasse           (overriding)
    //-------------------------------------------------------------------------
    /**
     * crea tutti i moduli che sono 'conosciuti' da questo modulo, che
     * quindi dipende dalla esistenza di questi moduli che sono tutti
     * e solo quelli che il modulo puo' utilizzare
     * aggiunge i singoli moduli alla collezione moduli del progetto
     * ATTENZIONE - l'aggiunta di se stesso puo' essere fatta PRIMA
     * o DOPO aver creato gli altri moduli che dipendono da questo modulo
     * o che sono propedeutici a questo modulo
     */
    public void creaModuliFigli() {
    } /* fine del metodo */


    /**
     * aggiunge alla collezione moduli (di questo modulo), gli eventuali
     * moduli (o tabelle), che verranno poi inserite nel menu moduli e
     * tabelle, dalla classe FinestraListaOld <br>
     * i moduli e le tabelle appaiono nei rispettivi menu, nell'ordine in
     * cui sono elencati in questo metodo <br>
     * la collezione moduli accetta oggetti di tipo Stringa
     * che sono il nome dell'oggetto di classe Modulo, conservato nella
     * collezione moduliProgramma della classe statica Flag
     */
    public void regolaModuliVisibili() {
    } /* fine del metodo */


    //-------------------------------------------------------------------------
    // Metodi pubblici degli oggetti (usati in altre classi)      (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali                    (setter)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di gestione interni, invocati dai metodi azione     (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi azione                                         (instance methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Classi azione interne degli eventi                         (inner class)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodo principale di partenza del programma                       (main)
    //-------------------------------------------------------------------------
    /**
     * Main method
     *
     * @param argomenti eventuali (quasi mai) parametri in ingresso
     */
    public static void main(String[] argomenti) {
        /** crea una istanza di se stessa */
//        new ModuloDefault(new ModuloCreatore(Modulo.MAIN));

        /** regola il nome del programma (se vuoto non cambia il default)
         * regola l'indirizzo del database - opzionale (se vuoto usa le preferenze)
         * inizializza tutti i sotto-moduli nello stesso ordine in cui sono stati inseriti
         * lancia il modulo iniziale del programma (obbligatorio) */
//        ProgettoFactory.lancia(NOME_PROGRAMMA, NOME_MODULO);
    } /* fine del metodo main */
    //-------------------------------------------------------------------------
}// fine della classe it.algos.base.modulo.ModuloDefault.java
//-----------------------------------------------------------------------------
