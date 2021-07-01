/**
 * Title:     FinestraDialogo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      14-dic-2004
 */
package it.algos.base.finestra;

import it.algos.base.errore.Errore;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA CLASSE .
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 14-dic-2004 ore 11.56.23
 */
public final class FinestraDialogo extends FinestraBase {


    /**
     * Costruttore completo senza parametri. <br>
     */
    public FinestraDialogo() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * <p/>
     * Metodo chiamato dalla classe che crea questo oggetto dopo che sono
     * stati regolati dalla sottoclasse i parametri indispensabili <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Chiede al Modulo la lista di eventuali altri moduli e tabelle <br>
     * Regola i menu <br>
     * Cancella gli eventuali menu non utilizzati <br>
     */
    public void inizializza() {
        /* invoca il metodo sovrascritto della superclasse */
        super.inizializza();

        this.setTitle("ci penso");
    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     */
    public void avvia() {
        /* invoca il metodo sovrascritto della superclasse */
        super.avvia();
    }// fine del metodo avvia

}// fine della classe
