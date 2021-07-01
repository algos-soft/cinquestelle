/**
 * Title:        CampoListaDefault.java
 * Package:      it.algos.base.campo.lista
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 23 luglio 2003 alle 8.53
 */

package it.algos.base.campo.lista;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Regola le funzionalita di gestione di una colonna a video nella Lista <br>
 * B - Classe concreta che implementa la superclasse astratta senza
 * ulteriori funzionalita' <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  23 luglio 2003 ore 8.53
 */
public final class CampoListaDefault extends CampoListaBase {

    /**
     * larghezza di default delle colonne
     */
    private static final int LARGHEZZA_COLONNA_DEFAULT = 80;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CampoListaDefault() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CampoListaDefault(Campo unCampoParente) {
        /** rimanda al costruttore della superclasse */
        super(unCampoParente);

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /** Regolazioni di default*/
        this.setPresenteVistaDefault(false);
        this.setLarghezzaColonna(LARGHEZZA_COLONNA_DEFAULT);
        this.setRidimensionabile(true);
    } /* fine del metodo inizia */


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     */
    public void inizializza() {
        /* invoca il metodo sovrascritto della superclasse */
        super.inizializza();
    } /* fine del metodo */


    /**
     * Regolazioni di avvio, ogni volta che questo oggetto deve 'ripartire', per
     * essere sicuri che sia 'pulito'
     * Metodo chiamato da altre classi
     * Viene eseguito tutte le volte che necessita
     */
    public void avviaCampoLista() {
    } /* fine del metodo */

}// fine della classe
