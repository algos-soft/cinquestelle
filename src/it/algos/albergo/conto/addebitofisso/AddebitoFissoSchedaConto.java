/**
 * Title:     AddebitoSchedaConto
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      21-apr-2006
 */
package it.algos.albergo.conto.addebitofisso;

import it.algos.albergo.conto.addebito.Addebito;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.scheda.SchedaBase;

/**
 * Scheda specifica dell'addebito all'interno del conto.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 9-mar-2006 ore 14.39.46
 */
public final class AddebitoFissoSchedaConto extends SchedaBase {


    /**
     * Costruttore completo
     * <p/>
     *
     * @param modulo di riferimento
     */
    public AddebitoFissoSchedaConto(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        try { // prova ad eseguire il codice
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
    }


    /**
     * .
     * <p/>
     */
    public void inizializza() {
        super.inizializza();
    }


    /**
     * Crea le pagine della scheda.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaPagine() {
        Pagina pag;
        Pannello panSott;
        Pannello panQP;
        Pannello panAdd;
        Pannello panDate;
        Pannello panFlag;
        Pannello panPer;
        Pannello pan;

        try { // prova ad eseguire il codice

            panSott = PannelloFactory.orizzontale(this);
            panSott.add(Addebito.Cam.listino.get());

            panQP = PannelloFactory.orizzontale(this);
            panQP.add(Addebito.Cam.quantita.get());
            panQP.add(Addebito.Cam.prezzo.get());
            panQP.add(Addebito.Cam.importo.get());

            panAdd = PannelloFactory.verticale(this);
            panAdd.creaBordo("addebito");
            panAdd.add(panSott);
            panAdd.add(panQP);

            panDate = PannelloFactory.orizzontale(this);
            panDate.add(AddebitoFisso.Cam.dataInizioValidita);
            panDate.add(AddebitoFisso.Cam.dataFineValidita);

            panFlag = PannelloFactory.orizzontale(this);
            panFlag.add(AddebitoFisso.Cam.dataSincro);

            panPer = PannelloFactory.verticale(this);
            panPer.creaBordo("periodo");
            panPer.add(panDate);
            panPer.add(panFlag);

            pag = this.addPagina("generale");
            pan = PannelloFactory.orizzontale(this);
            pan.add(panAdd);
            pan.add(panPer);
            pag.add(pan);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


}// fine della classe
