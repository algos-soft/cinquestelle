/**
 * Title:     ListaDefault
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      15-set-2004
 */
package it.algos.base.lista;

import it.algos.base.errore.Errore;
import it.algos.base.portale.PortaleLista;

/**
 * Presentazione di dati sotto forma di elenco.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Costituisce un'implementazione di default della classe astratta </li>
 * <li> Non implementa nessuna funzionalita, se non quella di poter creare
 * un oggetto concreto della classe </li>
 * <li> Serve come scheletro da sostituire per un'eventuale implementazione
 * specifica </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 15-set-2004 ore 16.01.57
 */
public class ListaDefault extends ListaBase {

    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public ListaDefault() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param unPortale portale che contiene questa lista
     */
    public ListaDefault(PortaleLista unPortale) {
        /* rimanda al costruttore della superclasse */
        super(unPortale);

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
    }// fine del metodo inizia


}// fine della classe
