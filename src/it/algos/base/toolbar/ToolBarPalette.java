package it.algos.base.toolbar;

import it.algos.base.errore.Errore;


/**
 * ToolBar della Palette.
 * <p/>
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author alex
 * @version 1.0  / 25-giu-2009 ore 11.30
 */
public final class ToolBarPalette extends ToolBarBase {

    /**
     * Costruttore completo con parametri.
     */
    public ToolBarPalette() {
        /* rimanda al costruttore della superclasse */
        super(null);

        /* regolazioni iniziali di riferimenti e variabili */
        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setMostraTesto(false);
        this.setFloatable(false);
        this.setBorder(null);
    } /* fine del metodo inizia */


}// fine della classe
