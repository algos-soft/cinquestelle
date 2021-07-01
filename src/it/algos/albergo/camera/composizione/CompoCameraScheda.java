package it.algos.albergo.camera.composizione;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.scheda.SchedaDefault;

/**
 * Scheda specifica della Composizione Camera.
 * </p>
 * <p/>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author Gac
 * @version 1.0 / 9-apr-2009 ore 09:51
 */
public final class CompoCameraScheda extends SchedaDefault implements CompoCamera {

    /**
     * Costruttore completo con parametri.
     *
     * @param modulo di riferimento per la scheda
     */
    public CompoCameraScheda(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

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
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


}// fine della classe