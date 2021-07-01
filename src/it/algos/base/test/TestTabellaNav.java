/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      2-mar-2005
 */
package it.algos.base.test;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;

/**
 * Navigatore dei Menu.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 2-mar-2005 ore 09.57.34
 */
public final class TestTabellaNav extends NavigatoreLS implements TestTabella {

    /**
     * Costruttore completo con parametri.
     *
     * @param unModulo modulo di riferimento
     */
    public TestTabellaNav(Modulo unModulo) {
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
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        this.addScheda(new TestTabellaScheda(this.getModulo()));

//        this.setUsaFinestra(true);
//        this.setNomeChiave(Menu.NAVIGATORE_MENU);
//        this.setUsaPannelloUnico(true);

    }// fine del metodo inizia


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {
        super.inizializza();
        int a = 87;
    }


    public void avvia() {
        super.avvia();
        int a = 87;
    }

//    /**
//     * Azione ricerca.
//     * <p/>
//     * Apre una finestra di ricerca <br>
//     * Metodo invocato dal Gestore Eventi <br>
//     * Invoca il metodo delegato <br>
//     */
//    public void ricerca() {
//        Filtro filtro = null;
//
//        try { // prova ad eseguire il codice
//            filtro = new Filtro(CAMPO_COLORI,Operatore.MASCHERA,"____1___");
//            this.getLista().setFiltroCorrente(filtro);
//            this.getLista().avvia();
//        } catch (Exception unErrore) { // intercetta l'errore
//            new Errore(unErrore);
//        }// fine del blocco try-catch
//
//    }


}// fine della classe
