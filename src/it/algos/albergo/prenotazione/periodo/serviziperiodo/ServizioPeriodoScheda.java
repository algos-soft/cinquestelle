package it.algos.albergo.prenotazione.periodo.serviziperiodo;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.scheda.SchedaDefault;

/**
 * Scheda specifica di un Periodo
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 24-ott-2007 ore 16.42.46
 */
public final class ServizioPeriodoScheda extends SchedaDefault implements ServizioPeriodo {


    /**
     * Costruttore completo senza parametri.
     */
    public ServizioPeriodoScheda(Modulo modulo) {
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
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setUsaStatusBar(false);
        this.setMargine(Pagina.Margine.piccolo);
    }// fine del metodo inizia


    /**
     * Crea le pagine.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override
    protected void creaPagine() {
        /* variabili e costanti locali di lavoro */
        Pagina pagina;

        try { // prova ad eseguire il codice
            /* aggiunge  una pagina al libro con il set di default */
            pagina = this.addPagina("generale");

            /* aggiunge i campi */
            pagina.add(Cam.servizio);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


}// fine della classe
