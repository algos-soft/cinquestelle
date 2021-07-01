/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      13-giu-2006
 */
package it.algos.albergo.conto.addebitofisso;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;

/**
 * Business logic per AddebitoFisso.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Gestisce la business-logic della GUI </li>
 * <li> Riceve le invocazioni da una classe di tipo <code>Gestore</code> </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 13-giu-2006 ore 13.55.52
 */
public final class AddebitoFissoNavConto extends NavigatoreLS {

    /**
     * Costruttore completo con parametri.
     *
     * @param unModulo modulo di riferimento
     */
    public AddebitoFissoNavConto(Modulo unModulo) {
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
        this.setNomeChiave(AddebitoFisso.NAVIGATORE_CONTO);
        this.setNomeVista(AddebitoFisso.Vis.vistaConto.get());
        this.addScheda(new AddebitoFissoSchedaConto(this.getModulo()));
        this.setUsaPannelloUnico(true);
        this.setUsaFrecceSpostaOrdineLista(true);
        this.setUsaTotaliLista(false);
        this.setUsaRicerca(false);
        this.setRigheLista(12);
    }// fine del metodo inizia

//    /**
//     * Sovrascrive l'azione nuovo record nella lista.
//     * <p/>
//     * Presenta il dialogo di preparazione addebiti fissi <br>
//     */
//    @Override
//    protected int nuovoRecord() {
//        /* variabili e costanti locali di lavoro */
//        AddebitoFissoLogica logica;
//        int codConto;
//
//        try { // prova ad eseguire il codice
//            codConto = this.getValorePilota();
//            logica = (AddebitoFissoLogica)this.getModulo().getLogicaSpecifica();
//            logica.aggiungiAddebitiConto(codConto);
//            this.aggiornaLista();
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return 0;
//    }


}// fine della classe
