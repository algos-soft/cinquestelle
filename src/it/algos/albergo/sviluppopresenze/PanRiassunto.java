/**
 * Title:     PanCondizioni
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      9-feb-2009
 */
package it.algos.albergo.sviluppopresenze;

import it.algos.base.errore.Errore;

/**
 * Pannello per la presentazione del riassunto dei risultati della ricerca
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 9-feb-2009 ore 12.38.23
 */
class PanRiassunto extends PanDialogo {

    /**
     * Costruttore completo con parametri. <br>
     *
     * @param dialogo dialogo di riferimento
     */
    public PanRiassunto(SviluppoDialogo dialogo) {
        /* rimanda al costruttore della superclasse */
        super(dialogo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo



    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.creaBordo("Riassunto");
    }// fine del metodo inizia



}// fine della classe
