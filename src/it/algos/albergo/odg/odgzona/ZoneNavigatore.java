package it.algos.albergo.odg.odgzona;

import it.algos.albergo.camera.zona.ZonaModulo;
import it.algos.albergo.odg.OdgLogica;
import it.algos.base.azione.Azione;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;
import it.algos.base.query.ordine.Ordine;

/**
 * Navigatore Righe ODG
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 24-Ott-2008
 */
public class ZoneNavigatore extends NavigatoreLS {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param unModulo modulo di riferimento
     */
    public ZoneNavigatore(Modulo unModulo) {
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
            this.setUsaStatusBarLista(false);

            this.setUsaNuovo(false);
            this.setUsaRicerca(false);
            this.setUsaSelezione(false);
            this.setUsaElimina(false);


            this.setNomeVista(OdgZona.Vis.vistaZone.get());
            this.addSchedaCorrente(new OdgZonaScheda(this.getModulo()));

            this.setRigheLista(4);

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

            Campo campo = ZonaModulo.get().getCampoOrdine();
            this.getLista().setOrdine(new Ordine(campo));

            Azione azione = this.getPortaleLista().getAzione(Azione.STAMPA);
            azione.setTooltip("Stampa l'Ordine del Giorno delle zone selezionate");

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizializza


    @Override
    public void stampaLista() {
        OdgLogica.stampaZoneOdg(this.getLista().getChiaviSelezionate());
    }
    
}