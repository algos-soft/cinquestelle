package it.algos.albergo.stampeobbligatorie;

import it.algos.albergo.clientealbergo.MonitorDatiCliente;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreL;

import javax.swing.ListSelectionModel;

/**
 * Navigatore del modulo interno.
 * </p>
 */
public abstract class NavInterno extends NavigatoreL {

    /* monitor di stato del cliente selezionato */
    private MonitorDatiCliente monitorCliente;


    /**
     * Costruttore completo con parametri.
     *
     * @param modulo modulo di riferimento
     */
    public NavInterno(Modulo modulo) {
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
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice
            this.setUsaToolBarLista(false);
            this.setUsaStatusBarLista(false);
            this.setRigheLista(4);
            this.getLista().getTavola().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Inizializzazione dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche.<br>
     * Viene chiamato dall'inizializzazione del Modulo.<br>
     */
    public void inizializza() {

        try { // prova ad eseguire il codice
            super.inizializza();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizializza


    public void listaDoppioClick() {
        super.listaDoppioClick();
    }


    protected MonitorDatiCliente getMonitorCliente() {
        return monitorCliente;
    }


    protected void setMonitorCliente(MonitorDatiCliente monitorCliente) {
        this.monitorCliente = monitorCliente;
    }

} // fine della classe 'interna'
