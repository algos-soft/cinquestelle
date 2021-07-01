/**
 * Copyright:    Copyright (c) 2002, 2008
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi
 * @version 1.0  /
 * Creato:       13-giu-2008 ore 22.09
 */
package it.algos.base.stampa;

import com.wildcrest.j2printerworks.J2Printer14;
import com.wildcrest.j2printerworks.PrintingEventHandler;
import it.algos.base.errore.Errore;
import it.algos.base.progetto.Progetto;

import javax.swing.JButton;
import javax.swing.JToolBar;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Estensione della classe J2Printer con supporto della cancellazione della stampa
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author alex
 * @version 1.0  /  13-giu-2008 ore 22.09
 */
public class Printer extends J2Printer14 {

    /**
     * Flag di controllo avvenuta cancellazione della stampa
     */
    private boolean canceled = false;

    /**
     * Elenco dei componenti originali contenuti nella toolbar del dialogo di preview.
     * Questo elenco viene memorizzato all'inizio.
     */
    private Component[] componentiOriginaliToolbar;


    /**
     * Costruttore completo senza parametri.<br>
     */
    public Printer() {
        /* rimanda al costruttore della superclasse */
        super(Progetto.getPrintLicense());

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
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
        Date data;
        SimpleDateFormat formatter;
        String testo;
        JToolBar jtb;
        Component[] comps;

        try { // prova ad eseguire il codice
            this.setSeparatePrintThread(false);
            this.addPropertyChangeListener(new EventHandlerStampa());
            this.setTopMargin(0.2);
            this.setBottomMargin(0.4);
            this.setLeftMargin(0.4);
            this.setRightMargin(0.3);
            this.setLeftHeader(Progetto.getIstanza().getNomeProgramma());
            this.setCenterHeader("");
            this.setRightHeader("");

            /* left footer */
            data = new Date(System.currentTimeMillis());
            formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
            testo = formatter.format(data);
            this.setLeftFooter(testo);

            /* center footer */
            this.setCenterFooter("Pagina ### di @@@");

            /* right footer */
            this.setRightFooter("");

            /* mette da parte l'elenco originale dei componenti della toolbar del preview */
            jtb = this.getPrintPreviewToolBar();
            comps = jtb.getComponents();
            this.setComponentiOriginaliToolbar(comps);

//            /* regola il dialogo di Previev */
//            this.regolaPreview();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Elimina completamente l'header.
     * <p/>
     */
    public void removeHeader() {
        this.setLeftHeader("");
        this.setCenterHeader("");
        this.setRightHeader("");
    }


    /**
     * Elimina completamente il footer.
     * <p/>
     */
    public void removeFooter() {
        this.setLeftFooter("");
        this.setCenterFooter("");
        this.setRightFooter("");
    }


    /**
     * Ritorna i componenti di navigazione della toolbar del dialogo di preview.
     * <p/>
     * Frecce avanti/indietro, numero di pagina
     *
     * @return l'elenco dei componenti di navigazione
     */
    protected Component[] getComponentiNavigazione() {
        /* variabili e costanti locali di lavoro */
        Component[] compsOut = new Component[8];
        Component[] compsAll;

        try {    // prova ad eseguire il codice
            compsAll = this.getComponentiOriginaliToolbar();

            compsOut[0] = compsAll[4];
            compsOut[1] = compsAll[5];
            compsOut[2] = compsAll[6];
            compsOut[3] = compsAll[7];
            compsOut[4] = compsAll[8];
            compsOut[5] = compsAll[9];
            compsOut[6] = compsAll[10];
            compsOut[7] = compsAll[11];

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return compsOut;
    }


    /**
     * Ritorna i componenti di zooming del dialogo di preview.
     * <p/>
     * Lente +, Lente -, percentuale...
     *
     * @return l'elenco dei componenti di zooming
     */
    protected Component[] getComponentiZoom() {
        /* variabili e costanti locali di lavoro */
        Component[] compsOut = new Component[3];
        Component[] compsAll;

        try {    // prova ad eseguire il codice
            compsAll = this.getComponentiOriginaliToolbar();

            compsOut[0] = compsAll[13];
            compsOut[1] = compsAll[14];
            compsOut[2] = compsAll[15];

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return compsOut;
    }


    /**
     * Ritorna il bottone di stampa del preview.
     * <p/>
     *
     * @return il bottone di stampa
     */
    protected JButton getBottoneStampa() {
        /* variabili e costanti locali di lavoro */
        JButton bot = null;
        Component comp;
        Component[] compsAll;

        try {    // prova ad eseguire il codice
            compsAll = this.getComponentiOriginaliToolbar();
            if (compsAll.length > 0) {
                comp = compsAll[0];
                if (comp != null) {
                    if (comp instanceof JButton) {
                        bot = (JButton)comp;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return bot;
    }


    /**
     * Ritorna il bottone di chiusura del preview.
     * <p/>
     *
     * @return il bottone di chiusura
     */
    protected JButton getBottoneChiudi() {
        /* variabili e costanti locali di lavoro */
        JButton bot = null;
        Component comp;
        Component[] compsAll;

        try {    // prova ad eseguire il codice
            compsAll = this.getComponentiOriginaliToolbar();
            if (compsAll.length > 20) {
                comp = compsAll[20];
                if (comp != null) {
                    if (comp instanceof JButton) {
                        bot = (JButton)comp;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return bot;
    }


    /**
     * Forza la rigenerazione della toolbar del dialogo di preview allo stato originale.
     * <p/>
     */
    protected void resetPreviewToolbar() {
        /* variabili e costanti locali di lavoro */
        boolean flag;

        try {    // prova ad eseguire il codice
            flag = this.isCrossPlatformDialogs(); // memorizzo valore
            this.setCrossPlatformDialogs(!flag);  // inverto valore (forza rigenerazione)
            this.getPrintPreviewToolBar();  // richiamo la toolbar se no non la ricrea
            this.setCrossPlatformDialogs(flag); // ripristino il valore originale
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Chiude il dialogo di preview.
     * <p/>
     */
    protected void chiudiPreview() {
        /* variabili e costanti locali di lavoro */
        JButton bot;

        try {    // prova ad eseguire il codice

            /* invoca l'azione predefinita */
            bot = this.getBottoneChiudi();
            if (bot != null) {
                bot.doClick();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Controlla se la stampa è stata cancellata
     * <p/>
     *
     * @return true se è stata cancellata
     */
    public boolean isCanceled() {
        return canceled;
    }


    private void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }


    private Component[] getComponentiOriginaliToolbar() {
        return componentiOriginaliToolbar;
    }


    private void setComponentiOriginaliToolbar(Component[] componentiOriginaliToolbar) {
        this.componentiOriginaliToolbar = componentiOriginaliToolbar;
    }


    /**
     * Gestore degli eventi di stampa
     * <p/>
     * Intercetta gli eventi e invoca gli specifici metodi delegati
     * che possono essere sovrascritti dalle sottoclassi
     */
    private class EventHandlerStampa extends PrintingEventHandler {

        public void printingStart() {
            evePrintingStart();
        }


        public void printingDone() {
            evePrintingDone();
        }


        public void printDialogOK() {
            evePrintDialogOK();
        }


        public void printDialogCanceled() {
            evePrintDialogCanceled();
        }


        public void pageSetupDialogOK() {
            evePageSetupDialogOK();
        }


        public void pageSetupDialogCanceled() {
            evePageSetupDialogCanceled();
        }


        public void pageStart(int pageNum) {
            evePageStart(pageNum);
        }


        public void printPreviewStart() {
            evePrintPreviewStart();
        }


        public void printPreviewDone() {
            evePrintPreviewDone();
        }
    }// fine della classe interna


    protected void evePrintingStart() {
    }


    protected void evePrintingDone() {
    }


    protected void evePrintDialogOK() {
    }


    protected void evePrintDialogCanceled() {
        setCanceled(true);
    }


    protected void evePageSetupDialogOK() {
    }


    protected void evePageSetupDialogCanceled() {
        setCanceled(true);
    }


    protected void evePageStart(int pageNum) {
    }


    protected void evePrintPreviewStart() {
    }


    protected void evePrintPreviewDone() {
    }


}
