package it.algos.albergo.promemoria;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;

/**
 * Scheda specifiche del pacchetto Promemoria
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @version 1.0 / 14-mar-2009
 */
public final class PromemoriaScheda extends PromemoriaSchedaBase {

    /**
     * Costruttore completo con parametri.
     *
     * @param modulo di riferimento per la scheda
     */
    public PromemoriaScheda(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);
    }// fine del metodo costruttore completo



    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    @Override public void inizializza() {
        /* variabili e costanti locali di lavoro */
        Pannello placeHolder;

        try { // prova ad eseguire il codice
            super.inizializza();    //To change body of overridden methods use File | Settings | File Templates.

            placeHolder = this.getPlaceHolder();
            placeHolder.add(Cam.eseguito);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }
}// fine della classe}