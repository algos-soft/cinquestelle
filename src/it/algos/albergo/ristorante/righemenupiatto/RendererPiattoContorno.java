/**
 * Title:        RendererPiatto.java
 * Description:  Renderer specifico
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 11 gennaio 2003 alle 10.25
 */
package it.algos.albergo.ristorante.righemenupiatto;

import it.algos.albergo.ristorante.LibRistorante;
import it.algos.albergo.ristorante.Ristorante;
import it.algos.albergo.ristorante.piatto.Piatto;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.tavola.renderer.RendererBase;

import javax.swing.JTable;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

/**
 * Renderer per il piatto nelle liste.
 * <p/>
 * Associato al campo Codice RMP<br>
 * Fa vedere piatto e contorno insieme<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  11 gennaio 2003 ore 10.25
 */
public class RendererPiattoContorno extends RendererBase {

    /* modulo RMP */
    private static Modulo moduloRMP = Progetto.getModulo(Ristorante.MODULO_RIGHE_PIATTO);


    /**
     * Costruttore completo
     */
    public RendererPiattoContorno() {
        /** rimanda al costruttore della superclasse */
        super(null);

        /* regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    } /* fine del metodo inizia */


    /**
     * Effettua il rendering di un valore.
     * <p/>
     *
     * @param objIn valore in ingresso
     *
     * @return valore in uscita
     *         In entrata arriva il codic della rigaMenuPiatto
     *         In uscita usa il nome del piatto, la congiunzione e il nome del contorno
     */
    public Object rendValue(Object objIn) {
        /* variabili e costanti locali di lavoro */
        Object objOut = null;
        int codiceRMP = 0;
        int codPiatto = 0;
        int codContorno = 0;
        String nome = "";


        try {    // prova ad eseguire il codice

            /* recupero il codice della riga */
            codiceRMP = Libreria.getInt(objIn);

            /* recupero il nome del piatto e del contorno */
            if (codiceRMP > 0) {
                codPiatto = this.codicePiatto(codiceRMP);
                codContorno = this.codiceContorno(codiceRMP);
                nome = LibRistorante.stringaPiattoContorno(codPiatto, codContorno);
            }// fine del blocco if

            /* mando di sopra il valore ottenuto */
            objOut = nome;

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return objOut;
    }


    /**
     * Colora il componente di rosso se e' un piatto non comandabile.
     * <p/>
     */
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int column) {

        /* variabili e costanti locali di lavoro */
        Component comp = null;
        int codiceRiga = 0;
        boolean piattoComandabile = false;
        Modulo moduloPiatto = null;
        Modulo moduloRMP = null;
        Campo campoComandabile = null;
        Dati dati = null;
        Object valore = null;

        try { // prova ad eseguire il codice

            comp = super.getTableCellRendererComponent(table,
                    value,
                    isSelected,
                    hasFocus,
                    row,
                    column);

            moduloRMP = Progetto.getModulo(Ristorante.MODULO_RIGHE_PIATTO);
            moduloPiatto = Progetto.getModulo(Ristorante.MODULO_PIATTO);
            campoComandabile = moduloPiatto.getCampo(Piatto.CAMPO_COMANDA);
            codiceRiga = Libreria.objToInt(value);
            QuerySelezione query = new QuerySelezione(moduloRMP);
            query.addCampo(campoComandabile);
            Filtro filtro = FiltroFactory.codice(moduloRMP, codiceRiga);
            query.setFiltro(filtro);
            dati = moduloRMP.query().querySelezione(query);
            if (dati.getRowCount() > 0) {
                valore = dati.getValueAt(0, 0);
            }// fine del blocco if
            dati.close();

            piattoComandabile = Libreria.objToBool(valore);

            if (piattoComandabile == false) {
                comp.setForeground(Color.WHITE);
            } else {
                comp.setForeground(Color.black);
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }


    /**
     * Recupera il codice del piatto relativo a una rigaMenuPiatto.
     * <p/>
     *
     * @param codiceRMP il codice della riga
     *
     * @return il codice del piatto
     */
    private int codicePiatto(int codiceRMP) {
        return codicePiattoContorno(codiceRMP, true);
    }


    /**
     * Recupera il codice del contorno relativo a una rigaMenuPiatto.
     * <p/>
     *
     * @param codiceRMP il codice della riga
     *
     * @return il codice del contorno
     */
    private int codiceContorno(int codiceRMP) {
        return codicePiattoContorno(codiceRMP, false);
    }


    /**
     * Recupera il codice del piatto o del contorno relativo a una
     * rigaMenuPiatto.
     * <p/>
     *
     * @param codiceRMP il codice della riga
     * @param flag true per il piatto, false per il contorno
     *
     * @return il codice del piatto o del contorno
     */
    private int codicePiattoContorno(int codiceRMP, boolean flag) {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        String nomeCampo = null;
        Filtro filtro = null;
        ArrayList lista = null;
        Object valore = null;

        try {    // prova ad eseguire il codice

            /* determina il nome del campo da cercare*/
            if (flag) {
                nomeCampo = RMP.CAMPO_PIATTO;
            } else {
                nomeCampo = RMP.CAMPO_CONTORNO;
            }// fine del blocco if-else

            /* recupera il valore */
            filtro = FiltroFactory.codice(moduloRMP, codiceRMP);
            lista = moduloRMP.query().valoriCampo(nomeCampo, filtro);
            if (lista.size() > 0) {
                valore = lista.get(0);
                if (valore != null) {
                    if (valore instanceof Integer) {
                        codice = ((Integer)valore).intValue();
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


}