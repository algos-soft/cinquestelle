/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      28-gen-2004
 */
package it.algos.base.navigatore;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;

/**
 * Factory per la creazione di oggetti Navigatori.
 * </p>
 * Questa classe astratta factory: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Factory Method</b> </li>
 * <li> Fornisce i metodi statici per la creazione di Navigatori </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 28-gen-2004 ore 15.03.28
 */
public abstract class NavigatoreFactory {

    /**
     * Costruttore semplice senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Uso il modificatore <i>private<i/> perche' la classe e' astratta <br>
     */
    private NavigatoreFactory() {
        /* rimanda al costruttore della superclasse */
        super();
    }// fine del metodo costruttore semplice


    /**
     * Crea un navigatore base di tipo Lista-Scheda.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un Navigatore di tipo <code>NavigatoreLS</code> </li>
     * </ul>
     *
     * @param modulo il modulo di riferimento
     *
     * @return unNavigatore oggetto appena creato
     */
    private static Navigatore base(Modulo modulo) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore = null;

        try {    // prova ad eseguire il codice

            /* crea l'instanza del Navigatore */
            navigatore = new NavigatoreLS(modulo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return navigatore;
    }


    /**
     * Crea un navigatore normale di tipo Lista-Scheda.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un Navigatore di tipo <code>NavigatoreLS</code> </li>
     * <p/>
     * <li> di default il Navigatore usa una propria Finestra </li>
     * <li> di default il Navigatore usa il nome-chiave standard  </li>
     * </ul>
     *
     * @param modulo il modulo di riferimento
     *
     * @return unNavigatore oggetto appena creato
     */
    public static Navigatore normale(Modulo modulo) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore = null;

        try {    // prova ad eseguire il codice

            /* crea l'istanza del Navigatore */
            navigatore = base(modulo);

            /* regola alcuni parametri del Navigatore base */
            navigatore.setUsaFinestra(true);
            navigatore.setNomeChiave(Navigatore.NOME_CHIAVE_DEFAULT);
            navigatore.setUsaPreferito(modulo.getModello().isUsaCampoPreferito());

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return navigatore;
    }


    /**
     * Crea un navigatore normale di tipo Lista-Scheda.
     * <p/>
     * Questo metodo factory: <ul>
     * <li> crea un Navigatore di tipo <code>NavigatoreLS</code> </li>
     * <p/>
     * <li> di default il Navigatore usa una propria Finestra </li>
     * <li> di default il Navigatore usa il nome-chiave standard  </li>
     * </ul>
     * Se le impostazioni di default sono accettabili,
     * non occorrono altre regolazioni <br>
     * Se non serve la finestra, invocare il metodo <i>setUsaFinestra</i> <br>
     * Se si vuole regolare il nome del navigatore,
     * invocare il metodo <i>setNomeChiave</i> <br>
     *
     * @param modulo il modulo di riferimento
     *
     * @return unNavigatore oggetto appena creato
     */
    public static Navigatore listaScheda(Modulo modulo) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore = null;

        try {    // prova ad eseguire il codice

            /* crea l'instanza del Navigatore */
            navigatore = base(modulo);

            /* regola alcuni parametri del Navigatore base */
            navigatore.setUsaFinestra(true);
            navigatore.setNomeChiave(Navigatore.NOME_CHIAVE_DEFAULT);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return navigatore;
    }


    /**
     * Crea un navigatore di tipo Lista-Navigatore.
     * <p/>
     *
     * @param modulo modulo principale di riferimento
     * @param nomeModuloSlave nome del modulo per il navigatore slave
     * @param chiaveNavSlave nome chiave del navigatore slave (nel modulo slave)
     *
     * @return unNavigatore oggetto appena creato
     */
    public static Navigatore listaNavigatore(Modulo modulo,
                                             String nomeModuloSlave,
                                             String chiaveNavSlave) {

        /* variabili e costanti locali di lavoro */
        NavigatoreLN nav = null;
        Modulo moduloSlave;

        try {    // prova ad eseguire il codice

            moduloSlave = Progetto.getModulo(nomeModuloSlave);

            /* crea l'istanza */
            if (moduloSlave != null) {
                nav = new NavigatoreLN(modulo, moduloSlave, chiaveNavSlave);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }// fine del metodo


    /**
     * Crea un navigatore di tipo Lista-Navigatore.
     * <p/>
     * Usa il navigatore di default del modulo slave
     *
     * @param modulo modulo principale di riferimento
     * @param nomeModuloSlave nome del modulo per il navigatore slave
     *
     * @return unNavigatore oggetto appena creato
     */
    public static Navigatore listaNavigatore(Modulo modulo, String nomeModuloSlave) {
        return listaNavigatore(modulo, nomeModuloSlave, Navigatore.NOME_CHIAVE_DEFAULT);
    }// fine del metodo


    /**
     * Crea un navigatore di tipo solo Lista.
     * <p/>
     *
     * @param modulo modulo principale di riferimento
     *
     * @return unNavigatore oggetto appena creato
     */
    public static Navigatore lista(Modulo modulo) {
        /* variabili e costanti locali di lavoro */
        Navigatore navigatore = null;

        try {    // prova ad eseguire il codice

            /* crea l'instanza del Navigatore */
            navigatore = new NavigatoreL(modulo);

            /* regola alcuni parametri del Navigatore base */
            navigatore.setUsaFinestra(true);
            navigatore.setNomeChiave(Navigatore.NOME_CHIAVE_DEFAULT);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return navigatore;
    }// fine del metodo


    /**
     * Crea un navigatore di tipo solo Lista.
     * <p/>
     * Elimina la tool barra
     *
     * @param modulo modulo principale di riferimento
     *
     * @return unNavigatore oggetto appena creato
     */
    public static Navigatore listaNoTool(Modulo modulo) {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;

        try {    // prova ad eseguire il codice
            /* crea l'istanza */
            nav = NavigatoreFactory.lista(modulo);

            /* regola i parametri */
            nav.setUsaPannelloUnico(true);
            nav.getPortaleLista().setUsaStatusBar(false);
            nav.setUsaStampaLista(false);
            nav.setUsaProietta(false);
            nav.setUsaRicerca(false);
            nav.setUsaElimina(false);
            nav.setUsaSelezione(false);
            nav.setRigheLista(5);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }// fine del metodo


    /**
     * Crea un navigatore di tipo Navigatore-Navigatore.
     * <p/>
     *
     * @param modulo modulo principale di riferimento
     * @param chiaveNavMaster nome chiave del navigatore master (nel modulo principale)
     * @param nomeModuloSlave nome del modulo per il navigatore slave
     * @param chiaveNavSlave nome chiave del navigatore slave (nel modulo slave)
     *
     * @return unNavigatore oggetto appena creato
     */
    public static Navigatore navigatoreNavigatore(Modulo modulo,
                                                  String chiaveNavMaster,
                                                  String nomeModuloSlave,
                                                  String chiaveNavSlave) {

        /* variabili e costanti locali di lavoro */
        NavigatoreNN nav = null;
        Modulo moduloSlave;

        try {    // prova ad eseguire il codice

            moduloSlave = Progetto.getModulo(nomeModuloSlave);

            /* crea l'istanza */
            if (moduloSlave != null) {
                nav = new NavigatoreNN(modulo, chiaveNavMaster, moduloSlave, chiaveNavSlave);

                /* sun un navigatore-navigatore non avrebbe senso */
                nav.setUsaPannelloUnico(false);

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }// fine del metodo


    /**
     * Crea un navigatore di tipo Navigatore-Navigatore.
     * <p/>
     * Usa il navigatore di default del modulo slave
     *
     * @param modulo modulo principale di riferimento
     * @param chiaveNavMaster nome chiave del navigatore master (nel modulo principale)
     * @param nomeModuloSlave nome del modulo per il navigatore slave
     *
     * @return unNavigatore oggetto appena creato
     */
    public static Navigatore navigatoreNavigatore(Modulo modulo,
                                                  String chiaveNavMaster,
                                                  String nomeModuloSlave) {

        return navigatoreNavigatore(modulo,
                chiaveNavMaster,
                nomeModuloSlave,
                Navigatore.NOME_CHIAVE_DEFAULT);
    }// fine del metodo

}// fine della classe
