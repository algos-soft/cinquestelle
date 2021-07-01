/**
 * Title:     TestModulo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      22-apr-2004
 */
package it.algos.base.testbase;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.progetto.Progetto;

/**
 * TestModulo - Modulo di test per provare le funzionalita di <code>Base</code>.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Contiene tutti i riferimenti agli oggetti delle classi che servono
 * per gestire una parte del programma </li>
 * <li>  </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 22-apr-2004 ore 12.20.24
 */
public final class TestModulo extends ModuloBase {

    /**
     * nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_MODULO = "testmodulo";

    /**
     * nome del programma che compare in basso (se vuoto, usa il default) <br>
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = NOME_MODULO;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito) <br>
     */
    public TestModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(NOME_MODULO);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo <br>
     *
     * @param unNodo nodo dell'albero moduli
     */
    public TestModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(NOME_MODULO, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* flag - (facoltativo) - le tabelle usano finestre piu' piccole */
//        super.isTabella(true);

        /* regola la modalita di inizio della gestione (modulo e gestore) (facoltativo) */
//        super.setModalitaInizio(INIZIO_NORMALE);

        /* selezione del modello (obbligatorio) */
        super.setModello(new TestModello());

        /* selezione del gestore (facoltativo) */
//        super.setGestore(new xxxGestore());

        /* selezione della scheda (facoltativo)
         * oltre alla prima si possono regolare qui n schede */
//        super.addScheda(new xxxScheda2());

        /* selezione del gestore stato lista (facoltativo) */
//        super.setGestoreStatoLista(new xxxGestoreStatoLista());

        /* selezione del gestore stato scheda (facoltativo) */
//        super.setGestoreStatoScheda(new xxxGestoreStatoScheda());

        /* selezione del gestore degli aiuti (facoltativo) */
//        super.setHelp(new xxxHelp());
    }// fine del metodo inizia


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * <br>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * un'istanza provvisoria del modulo <br>
     * Questa istanza viene usata solo per portarsi il percorso della
     * classe (implicito) ed il nome chiave (esplicito) <br>
     * La creazione definitiva del Modulo viene delegata alla classe
     * Progetto nel metodo creaModulo() <br>
     */
    protected void creaModuli() {
        try { // prova ad eseguire il codice

//            super.creaModulo(new xxxModulo()); //TODO facoltativo

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Aggiunge i moduli (e le tabelle) al menu.
     * <br>
     * Aggiunge alla collezione moduli (di questo modulo), gli eventuali
     * moduli (o tabelle), che verranno poi inserite nel menu moduli e
     * tabelle, dalla classe Navigatore <br>
     * I moduli e le tabelle appaiono nei rispettivi menu, nell'ordine in
     * cui sono elencati in questo metodo <br>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * il nome-chiave del modulo <br>
     */
    protected void addModuliVisibili() {
        try { // prova ad eseguire il codice

//            super.addModuloVisibile(UN_NOME_MODULO); //TODO facoltativo

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Main method
     * </p>
     *
     * @param argomenti eventuali (quasi mai) parametri in ingresso
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new TestModulo();

        /*
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    }// fine del metodo main

}// fine della classe
