/**
 * Title:     NavNostreBanche
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      29-mar-2007
 */
package it.algos.gestione.tabelle.contibanca;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;

/**
 * Navigatore degli indirizzi dentro a una scheda anagrafica.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 3-feb-2005 ore 12.28.15
 */
public final class NavNostriConti extends NavigatoreLS implements ContiBanca {


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param modulo il modulo di riferimento
     */
    public NavNostriConti(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        /* regola le variabili di istanza coi parametri */

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
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;

        try { // prova ad eseguire il codice

            this.setTitoloFinestra("Nostri conti");
            this.setNomeVista(Vis.contiNostri.toString());
            this.addSchedaCorrente(new ContiBancaScheda(this.getModulo()));
            this.setUsaPannelloUnico(true);
            this.setUsaPreferito(true);
            this.setUsaFrecceSpostaOrdineLista(true);

            /* filtro base per selezionare solo i conti nostri
             * (quelli che non hanno un link ad Anagrafica) */
            filtro = FiltroFactory.crea(Cam.soggetto.get(), 0);
            this.setFiltroBase(filtro);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }// fine del metodo inizia


    public void inizializza() {
        super.inizializza();
    }// fine del metodo inizializza


}// fine della classe
