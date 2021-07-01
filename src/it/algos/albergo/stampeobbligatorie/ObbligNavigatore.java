package it.algos.albergo.stampeobbligatorie;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreL;

import javax.swing.ListSelectionModel;

/**
 * Navigatore Stampe Obbligatorie
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 24-Ott-2008
 */
public abstract class ObbligNavigatore extends NavigatoreL {

    /* Pannello Obbligatorie di riferimento */
    private PannelloObbligatorie panObbligatorie;

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param unModulo modulo di riferimento
     */
    public ObbligNavigatore(Modulo unModulo) {
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

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Inizializzazione dell'oggetto.
     * <p>
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        try { // prova ad eseguire il codice
            super.inizializza();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizializza


    /**
     * Invocato quando si apre la scheda cliente e si esce registrando.
     * <p/>
     */
    public void clienteModificato () {
        /* variabili e costanti locali di lavoro */
        PannelloObbligatorie po;

        try { // prova ad eseguire il codice
            po=this.getPanObbligatorie();
            if (po!=null) {
                po.clienteModificato();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private PannelloObbligatorie getPanObbligatorie() {
        return panObbligatorie;
    }


    public void setPanObbligatorie(PannelloObbligatorie panObbligatorie) {
        this.panObbligatorie = panObbligatorie;
    }
}