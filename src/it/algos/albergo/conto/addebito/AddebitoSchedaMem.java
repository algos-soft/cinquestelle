/**
 * Title:     AddebitoSchedaMem
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      14-mag-2007
 */
package it.algos.albergo.conto.addebito;

import it.algos.albergo.conto.PanAddebitiMem;
import it.algos.albergo.listino.Listino;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;

import java.util.Date;

/**
 * Scheda specifica dell'addebito all'interno del conto.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 9-mar-2006 ore 14.39.46
 */
public class AddebitoSchedaMem extends AddebitoScheda implements Addebito {

    /* oggetto gestore degli addebiti memoria */
    private PanAddebitiMem panAddebiti;


    /**
     * Costruttore completo
     * <p/>
     *
     * @param modulo di riferimento
     * @param panAddebiti oggetto gestore degli addebiti memoria
     */
    public AddebitoSchedaMem(Modulo modulo, PanAddebitiMem panAddebiti) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        this.setPanAddebiti(panAddebiti);

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
        Pannello panList;
        Pannello panQPI;
        Pannello panAddebito;

        try { // prova ad eseguire il codice

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

            /* crea la pagina e aggiunge campi e pannelli */
            pag = this.addPagina("generale");
            pag.add(panAddebito);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Ritorna la data dell'addebito corrente.
     * <p/>
     *
     * @return la data dell'addebito
     */
    protected Date getDataAddebito() {
        /* variabili e costanti locali di lavoro */
        Date data = null;
        PanAddebitiMem panAddebiti;

        try { // prova ad eseguire il codice
            panAddebiti = this.getPanAddebiti();
            if (panAddebiti != null) {
                data = panAddebiti.getDataAddebiti();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    /**
     * Ritorna la quantità di default per nuovo record.
     * <p/>
     * Se il codice listino è di tipo pensione per persona,
     * è pari al il numero di persone impostate nel Pannello Addebiti Memoria.
     * Altrimenti, è 1
     *
     * @return la quantità di default per nuovo record
     */
    protected int getQtaDefaultPerNuovoRecord() {
        /* variabili e costanti locali di lavoro */
        int qta = 1;
        PanAddebitiMem panAddebiti;
        Object oggetto;
        int codListino;
        boolean continua = true;


        try {    // prova ad eseguire il codice

            /* recupera il codice listino e controlla che sia diverso da zero */
            oggetto = this.getValore(Addebito.Cam.listino.get());
            codListino = Libreria.getInt(oggetto);
            if (codListino == 0) {
                continua = false;
            }// fine del blocco if

            /* controlla se il listino è di pensione */
            if (continua) {
                continua = Listino.AmbitoPrezzo.isPensione(codListino);
            }// fine del blocco if

            /* controlla se il listino è per persona */
            if (continua) {
                continua = Listino.TipoPrezzo.isPerPersona(codListino);
            }// fine del blocco if

            /* regola la quantità pari al numero di persone */
            if (continua) {
                panAddebiti = this.getPanAddebiti();
                if (panAddebiti != null) {
                    qta = panAddebiti.getNumPersone();
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return qta;
    }


    private PanAddebitiMem getPanAddebiti() {
        return panAddebiti;
    }


    private void setPanAddebiti(PanAddebitiMem panAddebiti) {
        this.panAddebiti = panAddebiti;
    }


}// fine della classe
