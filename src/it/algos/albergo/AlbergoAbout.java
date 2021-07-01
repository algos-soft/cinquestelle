package it.algos.albergo;

import it.algos.base.errore.Errore;
import it.algos.base.finestra.AboutDialogo;

/**
 * Dialogo specifico di informazioni del programma albergo.
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 17-mag-2008 ore  08:53
 */
public final class AlbergoAbout extends AboutDialogo {

    /**
     * Costruttore completo senza parametri.
     */
    public AlbergoAbout() {
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
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


}// fine della classe