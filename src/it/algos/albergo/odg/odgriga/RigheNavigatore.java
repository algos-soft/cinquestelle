package it.algos.albergo.odg.odgriga;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;

/**
 * Navigatore Righe ODG
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 24-Ott-2008
 */
public class RigheNavigatore extends NavigatoreLS {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param unModulo modulo di riferimento
     */
    public RigheNavigatore(Modulo unModulo) {
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

            this.setUsaPannelloUnico(true);
            this.setUsaToolBarLista(true);
            this.setRigheLista(16);

            this.setUsaNuovo(false);
            this.setUsaRicerca(false);
            this.setUsaSelezione(false);
            this.setUsaStampaLista(false);

            this.setNomeVista(OdgRiga.Vis.vistaRighe.get());

            this.addSchedaCorrente(new OdgRigaScheda(this.getModulo()));

            this.setPilotatoDaRigheMultiple(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Inizializzazione dell'oggetto.
     * <p/>
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        try { // prova ad eseguire il codice
            super.inizializza();
//            this.getLista().setOrdine(new Ordine(this.getModulo().getCampoChiave()));
//            this.getLista().setOrdinabile(false);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizializza


    @Override
    public int getValorePilota() {
        return super.getValorePilota();
    }


}