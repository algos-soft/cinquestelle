/**
 * Title:     MenuBarraFactory
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      19-mar-2004
 */
package it.algos.base.menu.barra;

import it.algos.base.errore.Errore;

/**
 * Factory per //TODO DESCRIZIONE SINTETICA DELLA CLASSE .
 * </p>
 * Questa classe astratta: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Factory Method</b> <br>
 * <li> Fornisce i metodi statici di creazione degli oggetti di questo
 * package <br>
 * <li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 19-mar-2004 ore 16.33.31
 */
public abstract class MenuBarraFactory extends Object {

    /**
     * Costruttore semplice senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public MenuBarraFactory() {
        /* rimanda al costruttore della superclasse */
        super();
    }// fine del metodo costruttore semplice


    /**
     * Crea un oggetto di questo package. <br>
     * <p/>
     * //@TODO il tipo di ritorno NON sara' Object
     * //@TODO il parametro potrebbe essere di tipo diverso o non esserci
     *
     * @param unParametro @TODO DESCRIZIONE DEL PARAMETRO
     *
     * @return unOggetto oggetto appena creato
     */
    public static MenuBarra lista(int unParametro) {
        /* variabili e costanti locali di lavoro */
        MenuBarra unaBarra = null;

        try {    // prova ad eseguire il codice

            unaBarra = new MenuBarraLista();

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unaBarra;
    }// fine del metodo

}// fine della classe
