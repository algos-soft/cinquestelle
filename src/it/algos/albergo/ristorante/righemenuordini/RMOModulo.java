/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      22-feb-2005
 */
package it.algos.albergo.ristorante.righemenuordini;

import it.algos.albergo.ristorante.Ristorante;
import it.algos.albergo.ristorante.righemenupiatto.RMPModulo;
import it.algos.albergo.ristorante.righemenutavolo.RMTModulo;
import it.algos.albergo.ristorante.righetavoloordini.RTO;
import it.algos.albergo.ristorante.righetavoloordini.RTOModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.NavigatoreFactory;
import it.algos.base.progetto.Progetto;

/**
 * RMO (righemenuordini)
 * <p/>
 * Modulo di incrocio tra RMP (i piatti in menu) e RMT (i tavoli in menu).
 * Ogni tavolo ordina N piatti.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 22-feb-2005 ore 10.17.12
 */
public final class RMOModulo extends ModuloBase {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = RMO.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = RMO.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = RMO.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default) <br>
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public RMOModulo() {
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
    public RMOModulo(AlberoNodo unNodo) {
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
        super.setModello(new RMOModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

        super.setTabella(true);
    }// fine del metodo inizia


    public boolean inizializza() {
        return super.inizializza();
    } // fine del metodo


    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Regola il Navigatore di default <br>
     * Crea altri Navigatori (oltre a quello standard) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void creaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;

        try { // prova ad eseguire il codice

            /*
             * Navigatore specifico di tipo LS
             * Usato come Master nel navigatore NAV_RMO_RTO di RMO
             * chiave: NAV_IN_MENU
             */
            nav = new RMONavInMenu(this);
            this.addNavigatoreCorrente(nav);

            /*
             * Navigatore standard di tipo NN
             * usato come Slave nel Navigatore NAV_RMT_RMO di RMT
             * usa come Master NAV_IN_MENU di RMO
             * usa come Slave NAV_IN_MENU di RTO
             * chiave: NAV_RMO_RTO
             */
            nav = NavigatoreFactory.navigatoreNavigatore(this,
                    RMO.NAV_IN_MENU,
                    Ristorante.MODULO_RIGHE_ORDINI,
                    RTO.NAV_IN_MENU);
            nav.setOrizzontale(true);
            nav.setNomeChiave(RMO.NAV_RMO_RTO);
            nav.setRigheLista(10);  // viene applicato a master e slave
            this.addNavigatoreCorrente(nav);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * </p>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * un'istanza provvisoria del modulo <br>
     * Questa istanza viene usata solo per portarsi il percorso della
     * classe (implicito) ed il nome chiave (esplicito) <br>
     * La creazione definitiva del Modulo viene delegata alla classe
     * Progetto nel metodo creaModulo() <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.progetto.Progetto#lanciaIstanza
     * @see it.algos.base.modulo.ModuloBase#creaModulo
     */
    protected void creaModuli() {
        try { // prova ad eseguire il codice

            super.creaModulo(new RTOModulo());

            super.creaModulo(new RMTModulo());
            super.creaModulo(new RMPModulo());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge i moduli (e le tabelle) al menu.
     * </p>
     * Metodo invocato dal ciclo inizializza <br>
     * Aggiunge alla collezione moduli (di questo modulo), gli eventuali
     * moduli (o tabelle), che verranno poi inserite nel menu moduli e
     * tabelle, dalla classe Navigatore <br>
     * I moduli e le tabelle appaiono nei rispettivi menu, nell'ordine in
     * cui sono elencati in questo metodo <br>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * il nome-chiave del modulo <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.modulo.ModuloBase#addModuloVisibile
     * @see it.algos.base.finestra.FinestraBase#creaMenuModuli
     */
    protected void addModuliVisibili() {
        try { // prova ad eseguire il codice

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static RMOModulo get() {
        return (RMOModulo)ModuloBase.get(RMOModulo.NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali (quasi mai) parametri in ingresso
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new RMOModulo();

        /*
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    }// fine del metodo main

}// fine della classe
