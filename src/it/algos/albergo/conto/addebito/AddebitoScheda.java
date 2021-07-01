/**
 * Title:     AddebitoSchedaConto
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      21-apr-2006
 */
package it.algos.albergo.conto.addebito;

import it.algos.albergo.listino.ListinoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.scheda.SchedaBase;

import java.util.Date;

/**
 * Scheda specifica degli addebiti.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 9-mar-2006 ore 14.39.46
 */
public class AddebitoScheda extends SchedaBase implements Addebito {


    /**
     * Costruttore completo
     * <p/>
     *
     * @param modulo di riferimento
     */
    public AddebitoScheda(Modulo modulo) {
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
     * Crea le pagine della scheda.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaPagine() {
        Pagina pag;
        Pannello panDC;
//        Pannello panCC;
        Pannello panList;
        Pannello panQPI;
        Pannello panAddebito;

        try { // prova ad eseguire il codice

            /* pannello data / conto */
            panDC = this.getPanDataConto();

            /* pannello listino */
            panList = PannelloFactory.orizzontale(this);
            panList.add(Addebito.Cam.listino.get());

            /* pannello quantita / prezzo / totale */
            panQPI = PannelloFactory.orizzontale(this);
            panQPI.add(Addebito.Cam.quantita.get());
            panQPI.add(Addebito.Cam.prezzo.get());
            panQPI.add(Addebito.Cam.importo.get());

            /* pannello addebito */
            panAddebito = PannelloFactory.verticale(this);
            panAddebito.creaBordo("addebito");
            panAddebito.add(panList);
            panAddebito.add(panQPI);
            panAddebito.add(Addebito.Cam.note.get());

//            /* pannello camera / cliente */
//            panCC = PannelloFactory.orizzontale(this);
//            panCC.creaBordo("camera/cliente (facoltativo)");
//            panCC.add(Addebito.Cam.camera.get());
//            panCC.add(Addebito.Cam.cliente.get());

            /* crea la pagina e aggiunge campi e pannelli */
            pag = this.addPagina("generale");
            pag.add(panDC);
            pag.add(panAddebito);
//            pag.add(panCC);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Ritorna il pannello con Data e Conto.
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @return il pannello contenente Data e Conto
     */
    protected Pannello getPanDataConto() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;

        try {    // prova ad eseguire il codice
            pan = PannelloFactory.orizzontale(this);
            pan.add(Addebito.Cam.data.get());
            pan.add(Addebito.Cam.conto.get());
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Sovrascritto dalle sottoclassi
     */
    @Override protected void eventoMemoriaModificata(Campo campo) {
        /* variabili e costanti locali di lavoro */
        int codListino;
        double prezzo;
        Object oggetto;
        Date dataAddebito;
        int qta;

        /* se si modifica il riferimento al listino, suggerisce il prezzo */
        if (campo.equals(this.getCampo(Cam.listino.get()))) {

            /* recupera la data dell'addebito */
            dataAddebito = this.getDataAddebito();

            /* recupera il codice del listino */
            oggetto = this.getValore(Cam.listino.get());
            codListino = Libreria.getInt(oggetto);

            /* chiede il prezzo al listino */
            prezzo = ListinoModulo.getPrezzo(codListino, dataAddebito);

            /* registra il prezzo nel campo */
            this.setValore(Cam.prezzo.get(), prezzo);

            /* se nuovo record, regola la quantità di default */
            if (this.isNuovoRecord()) {
                qta = this.getQtaDefaultPerNuovoRecord();
                this.setValore(Cam.quantita.get(), qta);
            }// fine del blocco if


        }// fine del blocco if
    }


    /**
     * Ritorna la data dell'addebito corrente.
     * <p/>
     *
     * @return la data dell'addebito
     */
    protected Date getDataAddebito() {
        /* variabili e costanti locali di lavoro */
        Date data = null;
        Object oggetto;

        try {    // prova ad eseguire il codice
            oggetto = this.getValore(Cam.data.get());
            data = Libreria.getDate(oggetto);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    /**
     * Ritorna la quantità di default per nuovo record.
     * <p/>
     *
     * @return la quantità di default per nuovo record
     */
    protected int getQtaDefaultPerNuovoRecord() {
        return 0;
    }


}// fine della classe
