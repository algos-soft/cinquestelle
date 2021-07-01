/**
 * Title:     NavInRigheOdg
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      22-giu-2009
 */
package it.algos.albergo.odg.odgaccessori;

import it.algos.albergo.camera.accessori.AccessoriModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;
import it.algos.base.query.ordine.Ordine;

/**
 * Navigatore nella scheda Riga Odg
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-giu-2009 ore 11:59
 */
public final class NavInRigheOdg extends NavigatoreLS {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param unModulo modulo di riferimento
     */
    public NavInRigheOdg(Modulo unModulo) {
        /* rimanda al costruttore della superclasse */
        super(unModulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        this.setNomeChiave(OdgAcc.Nav.navInRigheOdg.get());
        this.setUsaPannelloUnico(true);
        this.getLista().setOrdinabile(false);
        this.setNomeVista(OdgAcc.Vis.visInRigaOdg.get());
        this.addSchedaCorrente(new OdgAccScheda(this.getModulo()));
        this.setIconePiccole();
        this.setRigheLista(6);
        this.setUsaStampaLista(false);
        this.setUsaModifica(true);
        this.setUsaRicerca(false);
        this.setUsaFrecceSpostaOrdineLista(false);
        this.setUsaStatusBarLista(false);

    }


    @Override
    public void inizializza() {

        try { // prova ad eseguire il codice

            super.inizializza();

            /* modifica l'ordine dela lista */
            Campo campoOrd = AccessoriModulo.get().getCampoOrdine();
            this.getLista().setOrdine(new Ordine(campoOrd));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }
}// fine della classe