package it.algos.albergo.conto.movimento;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;

/**
 * Navigatore degli sconti all'interno del conto.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 27-set-2007 ore 13.55.52
 */
public class MovimentoNavConto extends NavigatoreLS {

    /**
     * Costruttore completo con parametri.
     *
     * @param unModulo modulo di riferimento
     */
    public MovimentoNavConto(Modulo unModulo) {
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
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        this.setNomeChiave(Movimento.Nav.movimentiConto.get());
        this.setNomeVista(Movimento.Vis.vistaConto.get());
        this.addScheda(new MovimentoSchedaConto(this.getModulo()));
        this.setRigheLista(8);
        this.setAggiornamentoTotaliContinuo(true);
        this.setUsaPannelloUnico(true);
        this.setUsaRicerca(false);
        this.setUsaStampaLista(false);
        this.setUsaStatusBarLista(false);

    }// fine del metodo inizia


}// fine della classe
