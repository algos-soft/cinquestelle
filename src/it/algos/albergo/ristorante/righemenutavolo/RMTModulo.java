/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      4-feb-2005
 */
package it.algos.albergo.ristorante.righemenutavolo;

import it.algos.albergo.ristorante.Ristorante;
import it.algos.albergo.ristorante.righemenuordini.RMO;
import it.algos.albergo.ristorante.righemenuordini.RMOModulo;
import it.algos.albergo.ristorante.tavolo.TavoloModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;

/**
 * RigheMenuTavolo - Contenitore dei riferimenti agli oggetti del package.
 * <p/>
 * Questa classe concreta: <ul>
 * <li> Contiene tutti i riferimenti agli oggetti delle classi che servono
 * per gestire una parte del programma </li>
 * <li> Regola il riferimento al Modello specifico (obbligatorio) </li>
 * <li> Regola i titoli di Menu e Finestra del Navigatore </li>
 * <li> Regola eventualmente alcuni aspetti specifici del Navigatore </li>
 * <li> Crea altri eventuali <strong>Moduli</strong> indispensabili per il
 * funzionamento di questo modulo </li>
 * <li> Rende visibili nel Menu gli altri moduli </li>
 * <li> Regola eventuali funzionalit&agrave; specifiche del Navigatore </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 4-feb-2005 ore 17.18.33
 */
public final class RMTModulo extends ModuloBase {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = RMT.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = RMT.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = RMT.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default) <br>
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public RMTModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(NOME_CHIAVE);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(NOME_PROGRAMMA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo.
     *
     * @param unNodo nodo dell'albero moduli
     */
    public RMTModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(NOME_CHIAVE, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* selezione del modello (obbligatorio) */
        super.setModello(new RMTModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

        /* regola il flag (le tabelle usano finestre piu' piccole) (facoltativo) */
        super.setTabella(true);
    }// fine del metodo inizia


    public boolean inizializza() {
        return super.inizializza();
    } // fine del metodo


    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Regola il Navigatore di default e crea altri Navigatori.
     */
    protected void creaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore = null;

        try { // prova ad eseguire il codice

            /*
             * Navigatore specifico di tipo LS
             * Usato come Master per il navigatore NN che e' nel campo del menu.
             * chiave: NAV_IN_MENU
             */
            navigatore = new RMTNavInMenu(this);
            this.addNavigatoreCorrente(navigatore);

            /*
            * Navigatore specifico di tipo NN
            * inserito nel campo della scheda del menu
            * usa come Master il navigatore NAV_IN_MENU di RMT
            * usa come Slave il navigatore NAV_RMO_RTO di RMO
            * chiave: NAV_RMT_RMO
            */
            Modulo moduloSlave = Progetto.getModulo(Ristorante.MODULO_RIGHE_MENU_ORDINI);
            navigatore = new RMTNavComande(this, RMT.NAV_IN_MENU, moduloSlave, RMO.NAV_RMO_RTO);
            this.addNavigatoreCorrente(navigatore);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

//    protected void regolaNavigatori() {
//        /* variabili e costanti locali di lavoro */
//        Navigatore navigatore = null;
//
//        try { // prova ad eseguire il codice
//            /*
//             * Navigatore specifico di tipo NN
//             * inserito nel campo della scheda del menu
//             * usa come Master il navigatore NAV_IN_MENU di RMT
//             * usa come Slave il navigatore NAV_RMO_RTO di RMO
//             * chiave: NAV_RMT_RMO
//             */
//            Modulo moduloSlave = Progetto.getModulo(Ristorante.MODULO_RIGHE_MENU_ORDINI);
//            navigatore = new RMTNavComande(this, RMT.NAV_IN_MENU, moduloSlave, RMO.NAV_RMO_RTO);
//            this.addNavigatoreCorrente(navigatore);
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//    }


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * </p>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * un'istanza provvisoria del modulo <br>
     * Questa istanza viene usata solo per portarsi il percorso della
     * classe (implicito) ed il nome chiave (esplicito) <br>
     * La creazione definitiva del Modulo viene delegata alla classe
     * Progetto nel metodo creaModulo() <br>
     */
    protected void creaModuli() {
        try { // prova ad eseguire il codice

            super.creaModulo(new TavoloModulo());
            super.creaModulo(new RMOModulo());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Aggiunge i moduli (e le tabelle) al menu.
     * </p>
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
            super.addModuloVisibile(Ristorante.MODULO_TAVOLO);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static RMTModulo get() {
        return (RMTModulo)ModuloBase.get(RMTModulo.NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali (quasi mai) parametri in ingresso
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new RMTModulo();

        /*
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    }// fine del metodo main

}// fine della classe
