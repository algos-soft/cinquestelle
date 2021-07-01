/**
 * Title:     AlbPC
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      16-lug-2006
 */
package it.algos.albergo.pianodeicontialbergo;

import it.algos.albergo.Albergo;
import it.algos.albergo.pianodeicontialbergo.conto.AlbConto;
import it.algos.albergo.pianodeicontialbergo.mastro.AlbMastro;
import it.algos.albergo.pianodeicontialbergo.sottoconto.AlbSottoconto;
import it.algos.base.albero.Albero;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.font.FontFactory;
import it.algos.base.matrice.MatriceDoppia;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;

/**
 * Albero del piano dei conti
 */
public class AlberoPC extends Albero {

    private static final Font FONT_MASTRO = FontFactory.creaScreenFont(Font.BOLD);

    private static final Font FONT_CONTO = FontFactory.creaScreenFont(Font.ITALIC);

    private static final Font FONT_SOTTOCONTO = FontFactory.creaScreenFont(Font.PLAIN);


    /**
     * Costruttore completo senza parametri.<br>
     */
    public AlberoPC() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
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
        /* variabili e costanti locali di lavoro */
        RendererNodo renderer;

        try { // prova ad eseguire il codice

            /* rende il nodo root invisibile */
            this.getTree().setRootVisible(false);

            /* Assegna un renderer personalizzato per
             * disegnare le celle dell'albero. */
            renderer = new RendererNodo();
            this.setCellRenderer(renderer);

            /* aggiunge i nodi mastro e ricorsivamente tutti gli altri */
            aggiungiMastri(this.getModelloAlbero().getNodoRoot());

            /* espande completamente l'albero */
            this.expandAll();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Aggiunge i nodi mastro al nodo Root dell'albero.
     * <p/>
     *
     * @param nodoParente al quale aggiungere i mastri
     */
    private void aggiungiMastri(AlberoNodo nodoParente) {
        /* variabili e costanti locali di lavoro */
        Modulo modulo;
        Ordine ordine;
        MatriceDoppia matrice;
        AlberoNodo nodo;
        OggettoNodoPC oggettoNodo;

        int codice;
        Object valore;
        String descrizione;

        try {    // prova ad eseguire il codice
            modulo = Albergo.Moduli.AlbMastro();
            ordine = new Ordine();
            ordine.add(modulo.getCampoOrdine());
            matrice = modulo.query().valoriDoppi(AlbMastro.CAMPO_DESCRIZIONE, ordine);

            for (int k = 0; k < matrice.size(); k++) {

                codice = matrice.getCodiceAt(k + 1);
                valore = matrice.getValoreAt(k + 1);
                descrizione = (String)valore;

                oggettoNodo = new OggettoNodoPC();
                oggettoNodo.setTipo(TipoNodo.mastro);
                oggettoNodo.setCodice(codice);
                oggettoNodo.setDescrizione(descrizione);

                nodo = new AlberoNodo();
                nodo.setUserObject(oggettoNodo);
                nodoParente.add(nodo);

                this.aggiungiConti(nodo);

            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Aggiunge i nodi Conto a un nodo Mastro dell'albero.
     * <p/>
     *
     * @param nodoParente al quale aggiungere i mastri
     */
    private void aggiungiConti(AlberoNodo nodoParente) {
        /* variabili e costanti locali di lavoro */
        Modulo modulo;
        int codParente;
        Filtro filtro;
        Ordine ordine;
        MatriceDoppia matrice;
        AlberoNodo nodo;
        OggettoNodoPC oggettoNodo;

        int codice;
        Object valore;
        String descrizione;

        try {    // prova ad eseguire il codice

            modulo = Albergo.Moduli.AlbConto();
            oggettoNodo = (OggettoNodoPC)nodoParente.getUserObject();
            codParente = oggettoNodo.getCodice();
            filtro = FiltroFactory.crea(AlbConto.CAMPO_ALB_MASTRO, codParente);
            ordine = new Ordine();
            ordine.add(modulo.getCampoOrdine());
            matrice = modulo.query().valoriDoppi(AlbConto.CAMPO_DESCRIZIONE, filtro, ordine);

            for (int k = 0; k < matrice.size(); k++) {

                codice = matrice.getCodiceAt(k + 1);
                valore = matrice.getValoreAt(k + 1);
                descrizione = (String)valore;

                oggettoNodo = new OggettoNodoPC();
                oggettoNodo.setTipo(TipoNodo.conto);
                oggettoNodo.setCodice(codice);
                oggettoNodo.setDescrizione(descrizione);

                nodo = new AlberoNodo();
                nodo.setUserObject(oggettoNodo);
                nodoParente.add(nodo);

                this.aggiungiSottoconti(nodo);

            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Aggiunge i nodi Sottoconto a un nodo Conto dell'albero.
     * <p/>
     *
     * @param nodoParente al quale aggiungere i mastri
     */
    private void aggiungiSottoconti(AlberoNodo nodoParente) {
        /* variabili e costanti locali di lavoro */
        Modulo modulo;
        int codParente;
        Filtro filtro;
        Ordine ordine;
        MatriceDoppia matrice;
        AlberoNodo nodo;
        OggettoNodoPC oggettoNodo;

        int codice;
        Object valore;
        String descrizione;

        try {    // prova ad eseguire il codice

            modulo = Albergo.Moduli.AlbSottoconto();
            oggettoNodo = (OggettoNodoPC)nodoParente.getUserObject();
            codParente = oggettoNodo.getCodice();
            filtro = FiltroFactory.crea(AlbSottoconto.CAMPO_ALB_CONTO, codParente);
            ordine = new Ordine();
            ordine.add(modulo.getCampoOrdine());
            matrice = modulo.query().valoriDoppi(AlbSottoconto.CAMPO_DESCRIZIONE, filtro, ordine);

            for (int k = 0; k < matrice.size(); k++) {

                codice = matrice.getCodiceAt(k + 1);
                valore = matrice.getValoreAt(k + 1);
                descrizione = (String)valore;

                oggettoNodo = new OggettoNodoPC();
                oggettoNodo.setTipo(TipoNodo.sottoconto);
                oggettoNodo.setCodice(codice);
                oggettoNodo.setDescrizione(descrizione);

                nodo = new AlberoNodo();
                nodo.setUserObject(oggettoNodo);
                nodoParente.add(nodo);

            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna la font per i nodi di tipo Mastro.
     * <p/>
     */
    public static Font getFontMastro() {
        return FONT_MASTRO;
    }


    /**
     * Ritorna la font per i nodi di tipo Conto.
     * <p/>
     */
    public static Font getFontConto() {
        return FONT_CONTO;
    }


    /**
     * Ritorna la font per i nodi di tipo Sottoconto.
     * <p/>
     */
    public static Font getFontSottoconto() {
        return FONT_SOTTOCONTO;
    }


    /**
     * Renderer specifico per i nodi dell'albero
     */
    private class RendererNodo extends DefaultTreeCellRenderer {

        /**
         * Intercetto il metodo per ottenere informazioni
         * sul nodo e regolare il testo del renderer
         */
        public Component getTreeCellRendererComponent(JTree tree,
                                                      Object value,
                                                      boolean sel,
                                                      boolean expanded,
                                                      boolean leaf,
                                                      int row,
                                                      boolean hasFocus) {

            /* variabili e costanti locali di lavoro */
            Component comp;
            AlberoNodo nodo;
            Object oggetto;
            OggettoNodoPC oggettoNodo;
            TipoNodo tipoNodo = null;
            String testo = "";
            Font font = FONT_SOTTOCONTO;  //default

            comp = super.getTreeCellRendererComponent(tree,
                    value,
                    sel,
                    expanded,
                    leaf,
                    row,
                    hasFocus);

            try { // prova ad eseguire il codice
                nodo = (AlberoNodo)value;
                oggetto = nodo.getUserObject();
                if (oggetto != null) {
                    if (oggetto instanceof OggettoNodoPC) {
                        oggettoNodo = (OggettoNodoPC)oggetto;
                        tipoNodo = oggettoNodo.getTipo();
                        testo = oggettoNodo.getDescrizione();
                    }// fine del blocco if
                }// fine del blocco if


                if (tipoNodo != null) {
                    switch (tipoNodo) {
                        case mastro:
                            font = FONT_MASTRO;
                            break;
                        case conto:
                            font = FONT_CONTO;
                            break;
                        case sottoconto:
                            font = FONT_SOTTOCONTO;
                            break;
                        default: // caso non definito
                            break;
                    } // fine del blocco switch
                }// fine del blocco if

                /* regolo il testo per il renderer */
                this.setText(testo);
                this.setFont(font);


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return comp;

        }

    }   // fine della classe interna


    /**
     * Classe interna Enumerazione.
     */
    public enum TipoNodo {

        mastro(1, "mastro"),
        conto(2, "conto"),
        sottoconto(3, "sottoconto");

        /**
         * codice del record
         */
        private int codice;

        /**
         * titolo da utilizzare
         */
        private String descrizione;


        /**
         * Costruttore completo con parametri.
         *
         * @param codice del record
         * @param titolo utilizzato nei popup
         */
        TipoNodo(int codice, String titolo) {
            try { // prova ad eseguire il codice

                /* regola le variabili di istanza coi parametri */
                this.setCodice(codice);
                this.setDescrizione(titolo);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public static ArrayList<String> getElenco() {
            /* variabili e costanti locali di lavoro */
            ArrayList<String> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<String>();

                /* traverso tutta la collezione */
                for (TipoNodo tipo : TipoNodo.values()) {
                    lista.add(tipo.getDescrizione());
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        public int getCodice() {
            return codice;
        }


        private void setCodice(int codice) {
            this.codice = codice;
        }


        public String getDescrizione() {
            return descrizione;
        }


        private void setDescrizione(String descrizione) {
            this.descrizione = descrizione;
        }


    }// fine della Enumerazione


}// fine della classe
