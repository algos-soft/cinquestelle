/**
 * Title:        RendererComanda.java
 * Description:  Renderer specifico
 * Copyright:    Copyright (c) 2002, 2005
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 03-03-2005 alle 18.25
 */
package it.algos.albergo.ristorante.righemenuordini;

import it.algos.albergo.ristorante.LibRistorante;
import it.algos.albergo.ristorante.Ristorante;
import it.algos.albergo.ristorante.righemenupiatto.RMP;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.tavola.renderer.RendererBase;

import javax.swing.JTable;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

/**
 * Renderer per il piatto nelle comande (RMO).
 * <p/>
 * Associato al campo Codice RMO<br>
 * Fa vedere piatto e contorno insieme<br>
 * Se il record ha un link a RMP, visualizza i dati da RMP.<br>
 * Altrimenti visualizza i dati da Piatto.<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  il 03-03-2005 alle 18.25
 */
public class RMORendererComanda extends RendererBase {

    /* modulo RMO */
    private static Modulo moduloRMO = Progetto.getModulo(Ristorante.MODULO_RIGHE_MENU_ORDINI);

    /* modulo RMP */
    private static Modulo moduloRMP = Progetto.getModulo(Ristorante.MODULO_RIGHE_PIATTO);


    /**
     * Costruttore completo
     */
    public RMORendererComanda() {
        /** rimanda al costruttore della superclasse */
        super(null);

        /** regolazioni iniziali di riferimenti e variabili */
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
     *         <p/>
     *         In entrata arriva il codice della RMO<br>
     *         In uscita usa il nome del piatto, la congiunzione e il nome del contorno<br>
     *         Se il campo di link a RMP e' valorizzato, usa i dati da RMP<br>
     *         Altrimenti usa i dati da Piatto.<br>
     */
    public Object rendValue(Object objIn) {
        /* variabili e costanti locali di lavoro */
        Object objOut = null;
        int codiceRMO = 0;
        int codPiatto = 0;
        int codContorno = 0;
        int linkRMP = 0;
        String cPiattoExtra = null;
        String cContornoExtra = null;
        Object valore = null;
        String nome = "";

        try { // prova ad eseguire il codice

            /* recupero il codice della riga */
            codiceRMO = Libreria.objToInt(objIn);

            /* recupero il valore del link a RMP */
            if (codiceRMO > 0) {
                valore = moduloRMO.query().valoreCampo(RMO.CAMPO_RIGA_MENU_PIATTO, codiceRMO);
                linkRMP = Libreria.objToInt(valore);

                if (linkRMP != 0) { // record linkato a RMP
                    codPiatto = this.codicePiatto(linkRMP);
                    codContorno = this.codiceContorno(linkRMP);
                } else {  // non linkato a RMP - piatto extra
                    cPiattoExtra = RMO.CAMPO_PIATTO_EXTRA;
                    if (moduloRMO.isEsisteCampo(cPiattoExtra)) {
                        valore = moduloRMO.query().valoreCampo(cPiattoExtra, codiceRMO);
                        codPiatto = Libreria.objToInt(valore);
                    }// fine del blocco if
                    cContornoExtra = RMO.CAMPO_CONTORNO_EXTRA;
                    if (moduloRMO.isEsisteCampo(cContornoExtra)) {
                        valore = moduloRMO.query().valoreCampo(cContornoExtra, codiceRMO);
                        codContorno = Libreria.objToInt(valore);
                    }// fine del blocco if
                }// fine del blocco if-else

                /* costruisco il nome completo */
                nome = LibRistorante.stringaPiattoContorno(codPiatto, codContorno);

            }// fine del blocco if

            /* mando di sopra il valore ottenuto */
            objOut = nome;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return objOut;
    }


    /**
     * Colora il componente di rosso se e' un piatto extra.
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
        int linkRMP = 0;
        Object valore = null;

        try { // prova ad eseguire il codice

            comp = super.getTableCellRendererComponent(table,
                    value,
                    isSelected,
                    hasFocus,
                    row,
                    column);

            codiceRiga = Libreria.objToInt(value);
            valore = moduloRMO.query().valoreCampo(RMO.CAMPO_RIGA_MENU_PIATTO, codiceRiga);
            linkRMP = Libreria.objToInt(valore);

            /* se non ha link a RMP (extra) colora di rosso */
            if (linkRMP == 0) {
                comp.setForeground(Color.red);
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