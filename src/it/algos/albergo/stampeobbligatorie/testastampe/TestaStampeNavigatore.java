package it.algos.albergo.stampeobbligatorie.testastampe;

import it.algos.albergo.stampeobbligatorie.PannelloObbligatorie;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreL;

import javax.swing.ListSelectionModel;

/**
 * Navigatore per la lista dei dati di testata delle stampe obbligatorie
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 9-lug-2008 ore 9.51.20
 */
public final class TestaStampeNavigatore extends NavigatoreL implements TestaStampe {


    private PannelloObbligatorie pannello;



    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param unModulo modulo di riferimento
     */
    public TestaStampeNavigatore(Modulo unModulo) {
        /* rimanda al costruttore della superclasse */
        super(unModulo);

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
        try { // prova ad eseguire il codice

            this.setUsaToolBarLista(false);
            this.getLista().getTavola().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            this.getLista().setOrdinabile(false);
            this.setRigheLista(18);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    public PannelloObbligatorie getPannello() {
        return pannello;
    }


    public void setPannello(PannelloObbligatorie pannello) {
        this.pannello = pannello;
    }
}