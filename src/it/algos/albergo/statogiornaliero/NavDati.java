/**
 * Title:     TabTotali
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      5-giu-2009
 */
package it.algos.albergo.statogiornaliero;

import it.algos.base.errore.Errore;
import it.algos.base.lista.Lista;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreL;

/**
 * Navigatore che mostra la lista dei dati nel dialogo
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 5-giu-2009
 */
class NavDati extends NavigatoreL {

    /* dialogo di riferimento */
    private StatoGiornaliero dialogo;

    public NavDati(Modulo unModulo, StatoGiornaliero dialogo) {
        super(unModulo);


        try { // prova ad eseguire il codice
            this.setDialogo(dialogo);
            this.setRigheLista(18);
            this.setUsaToolBarLista(false);
            this.setUsaStatusBarLista(false);
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch




    }



    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice
//            Lista lista = this.getLista();
//            lista.setTavola(new StatoTavola(lista));
//            int a = 87;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    @Override
    protected Lista creaLista() {
        int d =87;
        return new StatoGiornalieroLista();
    }


    @Override
    protected boolean modificaRecord() {

        /* gira il messaggio al dialogo */
        this.getDialogo().doppioClic();

        /* valore di ritorno */
        return true;
    }


    private StatoGiornaliero getDialogo() {
        return dialogo;
    }


    private void setDialogo(StatoGiornaliero dialogo) {
        this.dialogo = dialogo;
    }
}// fine della classe