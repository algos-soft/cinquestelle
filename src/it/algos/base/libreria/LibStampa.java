package it.algos.base.libreria;

import com.wildcrest.j2printerworks.J2TablePrinter;
import com.wildcrest.j2printerworks.J2TextPrinter;
import it.algos.base.errore.Errore;
import it.algos.base.matrice.MatriceBase;
import it.algos.base.stampa.Printer;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.print.Pageable;
import java.util.ArrayList;

/**
 * Classe astratta con metodi statici. </p>
 * Questa classe astratta: <ul> <li>  </li> <li>  </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 26-mag-2008
 */
public class LibStampa {

    /**
     * Crea un Printer vuoto con le impostazioni di default.
     * <p/>
     *
     * @return il printer creato
     */
    public static Printer getDefaultPrinter() {
        return new Printer();
    }


    /**
     * Stampa una o più pagine di testo.
     * <p/>
     *
     * @param contenuto da stampare
     * @param titolo della pagina da stampare
     * @param labelDx titolo di destra
     *
     * @return vero se la stampa è confermata
     */
    private static boolean reportBase(Pageable contenuto, String titolo, String labelDx) {
        /* variabili e costanti locali di lavoro */
        boolean stampata = false;
        Printer printer;

        try {    // prova ad eseguire il codice

            /* crea e regola il Printer */

            printer = new Printer();
            printer.setCenterHeader(titolo);
            printer.setRightHeader(labelDx);
            printer.setCenterFooter("Pagina ### di @@@");
            printer.addPageable(contenuto);
            printer.showPrintPreviewDialog();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stampata;
    }


    /**
     * Stampa una o più pagine di testo.
     * <p/>
     *
     * @param testo (iniziale) da stampare
     * @param titolo della pagina da stampare
     * @param righe di contenuto della pagina da stampare
     * @param labelDx titolo di destra
     *
     * @return vero se la stampa è confermata
     */
    static boolean report(String testo, String titolo, ArrayList<String> righe, String labelDx) {
        /* variabili e costanti locali di lavoro */
        boolean stampata = false;
        J2TextPrinter txtPrinter;
        JTextComponent comp;

        try {    // prova ad eseguire il codice
            comp = getCompTesto(testo, righe);

            txtPrinter = new J2TextPrinter();
            txtPrinter.setMaximumPaginationGap(1.0);// non spezza mai le righe su due pagine
            txtPrinter.setTextComponent(comp);

            /* crea e regola il Printer */
            stampata = reportBase(txtPrinter, titolo, labelDx);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stampata;
    }


    /**
     * Stampa una o più pagine di testo.
     * <p/>
     *
     * @param testo (iniziale) da stampare
     * @param titolo della pagina da stampare
     * @param righe di contenuto della pagina da stampare
     *
     * @return vero se la stampa è confermata
     */
    static boolean report(String testo, String titolo, ArrayList<String> righe) {
        /* variabili e costanti locali di lavoro */
        boolean stampata = false;
        String labelDx = "";

        try {    // prova ad eseguire il codice
            if (righe != null && righe.size() > 0) {
                labelDx = righe.size() + " records";
            }// fine del blocco if

            stampata = report(testo, titolo, righe, labelDx);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stampata;
    }


    /**
     * Stampa una o più pagine di testo.
     * <p/>
     *
     * @param testo da stampare
     * @param titolo della pagina da stampare
     *
     * @return vero se la stampa è confermata
     */
    static boolean report(String testo, String titolo) {
        /* invoca il metodo sovrascritto della superclasse */
        return LibStampa.report(testo, titolo, (ArrayList<String>)null);

    }


    /**
     * Stampa una o più pagine di testo.
     * <p/>
     *
     * @param testo da stampare
     *
     * @return vero se la stampa è confermata
     */
    static boolean report(String testo) {
        /* invoca il metodo sovrascritto della superclasse */
        return LibStampa.report(testo, "", (ArrayList<String>)null);

    }


    /**
     * Stampa una o più pagine di testo.
     * <p/>
     * Il contenuto viene stampato come tabella <br>
     *
     * @param testo (iniziale) da stampare
     * @param titolo della pagina da stampare
     * @param matrice (righe, colonne) del contenuto
     * @param labelDx titolo di destra
     *
     * @return vero se la stampa è confermata
     */
    static boolean report(String testo, String titolo, MatriceBase matrice, String labelDx) {
        /* variabili e costanti locali di lavoro */
        boolean stampata = false;
        JTable tavola;
        J2TablePrinter tablePrinter;

        try {    // prova ad eseguire il codice

            tavola = new JTable(matrice.getDati(), matrice.getTitoliColonne());

            tablePrinter = new J2TablePrinter(tavola);
            tablePrinter.setMaximumPaginationGap(1.0);// non spezza mai le righe su due pagine

            /* crea e regola il Printer */
            stampata = reportBase(tablePrinter, titolo, labelDx);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stampata;
    }


    /**
     * Stampa una o più pagine di testo.
     * <p/>
     * Il contenuto viene stampato come tabella <br>
     *
     * @param testo (iniziale) da stampare
     * @param titolo della pagina da stampare
     * @param matrice (righe, colonne) del contenuto
     *
     * @return vero se la stampa è confermata
     */
    static boolean report(String testo, String titolo, MatriceBase matrice) {
        /* variabili e costanti locali di lavoro */
        boolean stampata = false;
        String labelDx = "";

        try {    // prova ad eseguire il codice
            if (matrice != null && matrice.getNumeroRighe() > 1) {
                labelDx = (matrice.getNumeroRighe() + 1) + " records";
            }// fine del blocco if

            stampata = report(testo, titolo, matrice, labelDx);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stampata;
    }


    /**
     * Contenuto della pagina.
     * <p/>
     *
     * @param testo (iniziale) da stampare
     * @param righe di contenuto della pagina da stampare
     *
     * @return componente da stampare
     */
    private static JTextComponent getCompTesto(String testo, ArrayList<String> righe) {
        /* variabili e costanti locali di lavoro */
        JTextComponent comp = null;
        StringBuffer buffer;
        String aCapo = "\n";
        String contenuto;

        try {    // prova ad eseguire il codice
            if (righe != null && righe.size() > 0) {
                buffer = new StringBuffer();

                buffer.append(testo);
                buffer.append(aCapo);
                buffer.append(aCapo);

                for (String riga : righe) {
                    buffer.append(riga);
                    buffer.append(aCapo);
                } // fine del ciclo for-each

                contenuto = buffer.toString();
                comp = new JTextArea(contenuto);
            } else {
                comp = new JTextField(testo);
            }// fine del blocco if-else
            comp.setBorder(null);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }


}// fine della classe
