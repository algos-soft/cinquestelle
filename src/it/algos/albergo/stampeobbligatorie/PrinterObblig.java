package it.algos.albergo.stampeobbligatorie;

import it.algos.albergo.AlbergoPref;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.font.FontFactory;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibFile;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.stampa.Printer;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Estensione della classe Printer con gestione di una toolbar di anteprima personalizzata
 * e gestione di stampa di controllo / definitiva, utilizzata nellle Stampe Obbligatorie
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author alex
 * @version 1.0  /  15-ott-2008 ore 12.09
 */
public class PrinterObblig extends Printer {

    /**
     * flag - true se ha chiuso il dialogo di Preview premendo il pulsante Stampa.
     */
    private boolean eseguiStampa;

    /**
     * flag - prima stampa o Ristampa
     */
    private boolean primaStampa;

    /**
     * Bottone Stampa
     */
    private JButton botStampa;

    /**
     * Bottone Destinazione pdf
     */
    private JButton botDestPdf;


    /* radiobottone Stampa di Controllo */
    private JRadioButton radioStampaControllo;

    /* radiobottone Stampa Definitiva */
    private JRadioButton radioStampaDefinitiva;

    /* check box Uscita su Stampante */
    private JCheckBox chkStampante;

    /* check box Uscita su Pdf */
    private JCheckBox chkPdf;


    /**
     * Costruttore completo
     * <p/>
     *
     * @param primaStampa true per prima stampa false per ristampa
     */
    public PrinterObblig(boolean primaStampa) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setPrimaStampa(primaStampa);

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
        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Modifica la toolbar del dialogo di preview.
     * <p/>
     */
    private void modificaPreviewToolbar() {
        /* variabili e costanti locali di lavoro */
        JToolBar jtb;
        JRadioButton rb;
        JCheckBox cb;
        ButtonGroup group;
        Component[] comps;
        JButton myButton;
        Pannello pan;
        Icon icona;
        Font font;

        try {    // prova ad eseguire il codice


            jtb = this.getPrintPreviewToolBar();
            jtb.removeAll();

            //Create the radio buttons.

            /**
             * Se Prima Stampa, usa i radiobottoni Tipo di Stampa
             */
            if (this.isPrimaStampa()) {

                /* Crea e registra i radiobottoni tipo di stampa */
                rb = new JRadioButton("Stampa di controllo");
                this.setRadioStampaControllo(rb);
                rb.addActionListener(new AzRadio());

                rb = new JRadioButton("Stampa definitiva");
                this.setRadioStampaDefinitiva(rb);
                rb.addActionListener(new AzRadio());

                /* Raggruppa logicamente i radiobotttoni */
                group = new ButtonGroup();
                group.add(this.getRadioStampaControllo());
                group.add(this.getRadioStampaDefinitiva());

                /* Crea un pannello con i radiobottoni e lo aggiunge alla toolbar */
                pan = PannelloFactory.verticale(null);
                pan.setGapFisso(0);
                pan.add(this.getRadioStampaControllo());
                pan.add(this.getRadioStampaDefinitiva());
                jtb.add(pan.getPanFisso());

                jtb.add(new JSeparator(SwingConstants.VERTICAL));

            }// fine del blocco if

            /**
             * Crea e registra i checkboxes uscita della stampa
             */
            cb = new JCheckBox("su stampante");
            this.setChkStampante(cb);
            cb.addActionListener(new AzCheck());

            cb = new JCheckBox("su pdf");
            this.setChkPdf(cb);
            cb.addActionListener(new AzCheck());

            Pannello panPdf = PannelloFactory.orizzontale(null);
            panPdf.setAllineamento(Layout.ALLINEA_BASSO);
            myButton = new JButton("dove...");
            this.setBotDestPdf(myButton);
            myButton.addActionListener(new AzDestPdf());
            panPdf.add(this.getChkPdf());
            panPdf.add(myButton);

            /* Crea un pannello con i checkboxes e lo aggiunge alla toolbar */
            pan = PannelloFactory.verticale(null);
            pan.setGapFisso(0);
            pan.add(this.getChkStampante());
            pan.add(panPdf);
            jtb.add(pan.getPanFisso());

            jtb.add(new JSeparator(SwingConstants.VERTICAL));

            /* Crea il bottone Stampa, lo registra e lo aggiunge alla toolbar */
            myButton = new JButton("Stampa");
            this.setBotStampa(myButton);
            font = FontFactory.creaScreenFont(Font.BOLD, 13);
            myButton.setFont(font);
            Color colore = this.getBottoneChiudi().getForeground();
            myButton.setForeground(colore);

            myButton.addActionListener(new AzStampa());
            icona = Lib.Risorse.getIconaBase("Print24");
            myButton.setIcon(icona);
            jtb.add(myButton);

            jtb.add(new JSeparator(SwingConstants.VERTICAL));

            /* Recupera il bottone Chiudi, lo chiama Annulla e lo aggiunge alla toolbar */
            myButton = this.getBottoneChiudi();
            icona = Lib.Risorse.getIconaBase("chiudischeda24");
            myButton.setIcon(icona);
            myButton.setText("Annulla");
            font = FontFactory.creaScreenFont(Font.BOLD, 13);
            myButton.setFont(font);
            jtb.add(myButton);

            jtb.add(new JSeparator(SwingConstants.VERTICAL));

            /* rimette i componenti di navigazione */
            comps = this.getComponentiNavigazione();
            for (Component comp : comps) {
                jtb.add(comp);
            }

            jtb.add(new JSeparator(SwingConstants.VERTICAL));

            /* rimette i componenti di zoom */
            comps = this.getComponentiZoom();
            for (Component comp : comps) {
                jtb.add(comp);
            }

            /* valori di default per l'output */
            this.setOutputStampante(true);
            this.setOutputPdf(false);

            /* prima sincronizzazione */
            this.sincronizza();


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Bottone Stampa premuto.
     * <p/>
     */
    private void stampa() {
        /* variabili e costanti locali di lavoro */
        boolean continua=true;
        boolean contr;
        boolean def;
        boolean stam;
        boolean pdf;
        String pathPdf;

        try {    // prova ad eseguire il codice

            /* se prima stampa, deve essere selezionato Definitiva o Controllo */
            if (this.isPrimaStampa()) {
                contr = this.isControllo();
                def = this.isDefinitiva();
                if ((!contr) && (!def)) {
                    new MessaggioAvviso("Occorre specificare se è Stampa di Controllo o Stampa Definitiva.");
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* deve essere selezionato almeno un tipo di uscita */
            if (continua) {
                stam = this.isOutputStampante();
                pdf = this.isOutputPdf();
                if ((!stam) && (!pdf)) {
                    new MessaggioAvviso("Occorre selezionare almeno un tipo di uscita (Stampante o .pdf).");
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /**
             * se è stata selezionata l'uscita Pdf la cartella
             * destinazione deve essere specificata
             */
            if (continua) {
                if (this.isOutputPdf()) {
                    pathPdf = AlbergoPref.Albergo.pathPdfPS.str();
                    if (Lib.Testo.isVuota(pathPdf)) {
                        new MessaggioAvviso("Occorre specificare la directory di destinazione dei file .pdf.");
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if


            /**
             * se è stata selezionata l'uscita Pdf la cartella
             * destinazione specificata deve essere esistente
             */
            if (continua) {
                if (this.isOutputPdf()) {
                    pathPdf = AlbergoPref.Albergo.pathPdfPS.str();
                    Lib.File.isEsisteDir(pathPdf);
                    if (!Lib.File.isEsisteDir(pathPdf)) {
                        new MessaggioAvviso("La directory di destinazione dei file .pdf non esiste.");
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if


            /**
             * Chiude il dialogo di preview
             * Accende il flag di richiesta esecuzione stampa.
             */
            if (continua) {
                this.setEseguiStampa(true);
                this.chiudiPreview();
            }// fine del blocco if



        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Tipo di Stampa modificata.
     * <p/>
     * E' stato acceso o spento un radiobottone Tipo di Stampa (controllo o definitiva)
     */
    private void tipoStampaModificata() {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice

            /* output di default per stampa di controllo */
            if (this.isControllo()) {
                this.setOutputStampante(true);
                this.setOutputPdf(false);
            }// fine del blocco if

            /* output di default per stampa definitiva */
            if (this.isDefinitiva()) {
                this.setOutputStampante(true);
                this.setOutputPdf(false);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Edita la directory di destinazione dei file pdf in un dialogo.
     * <p/>
     */
    private void editDestinazionePdf() {
        /* variabili e costanti locali di lavoro */
        DialogoEditPdf dialogo;
        String path;

        try {    // prova ad eseguire il codice

            dialogo = new DialogoEditPdf();
            dialogo.avvia();

            /* registra la preferenza */
            if (dialogo.isConfermato()) {
                path = dialogo.getPath();
                AlbergoPref.Albergo.pathPdfPS.getWrap().setValore(path);
                AlbergoPref.registra();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }





    /**
     * Ritorna true se la stampa è definitiva.
     * <p/>
     *
     * @return true se la stampa è definitiva
     */
    public boolean isDefinitiva() {
        /* variabili e costanti locali di lavoro */
        boolean definitiva = false;
        JRadioButton rb;

        try {    // prova ad eseguire il codice
            rb = this.getRadioStampaDefinitiva();
            definitiva = rb.isSelected();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return definitiva;
    }


    /**
     * Ritorna true se la stampa è di controllo.
     * <p/>
     *
     * @return true se la stampa è definitiva
     */
    public boolean isControllo() {
        /* variabili e costanti locali di lavoro */
        boolean controllo = false;
        JRadioButton rb;

        try {    // prova ad eseguire il codice
            rb = this.getRadioStampaControllo();
            controllo = rb.isSelected();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return controllo;
    }


    /**
     * Ritorna true se l'opzione Output su Stampante è accesa.
     * <p/>
     * @return true se è accesa
     */
    public boolean isOutputStampante() {
        /* variabili e costanti locali di lavoro */
        boolean accesa = false;
        JCheckBox check;

        try {    // prova ad eseguire il codice
            check = this.getChkStampante();
            accesa = check.isSelected();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return accesa;
    }



    /**
     * Assegna un valore all'opzione Uscita su Stampante.
     * <p/>
     * @param flag per accendere o spegnere
     */
    private void setOutputStampante(boolean flag) {
        /* variabili e costanti locali di lavoro */
        JCheckBox cb;

        try {    // prova ad eseguire il codice
            cb = this.getChkStampante();
            cb.setSelected(flag);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna true se l'opzione Output su Pdf è accesa.
     * <p/>
     * @return true se è accesa
     */
    public boolean isOutputPdf() {
        /* variabili e costanti locali di lavoro */
        boolean accesa = false;
        JCheckBox check;

        try {    // prova ad eseguire il codice
            check = this.getChkPdf();
            accesa = check.isSelected();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return accesa;
    }


    /**
     * Assegna un valore all'opzione Uscita su Pdf.
     * <p/>
     * @param flag per accendere o spegnere
     */
    private void setOutputPdf(boolean flag) {
        /* variabili e costanti locali di lavoro */
        JCheckBox cb;

        try {    // prova ad eseguire il codice
            cb = this.getChkPdf();
            cb.setSelected(flag);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Sincronizzazioni eseguite ogni volta che cambia qualcosa.
     * <p/>
     */
    private void sincronizza () {
        /* variabili e costanti locali di lavoro */
        JButton bot;

        try {    // prova ad eseguire il codice

            /* abilitazione bottone Destinazione pdf
            * solo se il check pdf è spuntato */
            bot = this.getBotDestPdf();
            bot.setEnabled(this.isOutputPdf());

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

}





    /**
     * All'inizio della preview modifica la toolbar
     * <p/>
     * La toolbar viene svuotata e ricostruita con solo ciò che serve
     */
    protected void evePrintPreviewStart() {
        this.modificaPreviewToolbar();
    }


    public boolean isEseguiStampa() {
        return eseguiStampa;
    }


    private void setEseguiStampa(boolean eseguiStampa) {
        this.eseguiStampa = eseguiStampa;
    }


    public boolean isPrimaStampa() {
        return primaStampa;
    }


    private void setPrimaStampa(boolean primaStampa) {
        this.primaStampa = primaStampa;
    }


    private JButton getBotStampa() {
        return botStampa;
    }


    private void setBotStampa(JButton botStampa) {
        this.botStampa = botStampa;
    }


    private JButton getBotDestPdf() {
        return botDestPdf;
    }


    private void setBotDestPdf(JButton botDestPdf) {
        this.botDestPdf = botDestPdf;
    }


    private JRadioButton getRadioStampaControllo() {
        return radioStampaControllo;
    }


    private void setRadioStampaControllo(JRadioButton radioStampaControllo) {
        this.radioStampaControllo = radioStampaControllo;
    }


    private JRadioButton getRadioStampaDefinitiva() {
        return radioStampaDefinitiva;
    }


    private void setRadioStampaDefinitiva(JRadioButton radioStampaDefinitiva) {
        this.radioStampaDefinitiva = radioStampaDefinitiva;
    }


    private JCheckBox getChkStampante() {
        return chkStampante;
    }


    private void setChkStampante(JCheckBox chkStampante) {
        this.chkStampante = chkStampante;
    }


    private JCheckBox getChkPdf() {
        return chkPdf;
    }


    private void setChkPdf(JCheckBox chkPdf) {
        this.chkPdf = chkPdf;
    }


    /**
     * Alla fine della preview ripristina la toolbar modificata
     * <p/>
     * La toolbar è un singleton e va ripristinata com'era prima se no tutte
     * le altre preview risultano con toolbar modificata.
     */
    protected void evePrintPreviewDone() {
        this.resetPreviewToolbar();
    }


    /**
     * Azione bottone Stampa premuto
     * </p>
     */
    private final class AzStampa implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            stampa();
            sincronizza();
        }
    } // fine della classe 'interna'

    /**
     * Azione Radio Bottone premuto
     * </p>
     */
    private final class AzRadio implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            tipoStampaModificata();
            sincronizza();
        }
    } // fine della classe 'interna'

    /**
     * Azione Check Box premuto
     * </p>
     */
    private final class AzCheck implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            sincronizza();
        }
    } // fine della classe 'interna'


    /**
     * Azione bottone Destinazione Pdf
     * </p>
     */
    private final class AzDestPdf implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            editDestinazionePdf();
            sincronizza();
        }
    } // fine della classe 'interna'



    /**
     * Dialogo di editing del path dei pdf
     * </p>
     */
    private final class DialogoEditPdf extends DialogoAnnullaConferma {

        /* campo - percorso della cartella dei pdf */
        private static final String NOME_CAMPO_PATH_PDF = "Directory per i file pdf";


        /**
         * Costruttore completo senza parametri.<br>
         */
        public DialogoEditPdf() {
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
            Campo campoPath;
            JButton bottone;
            Pannello pan2;

            try { // prova ad eseguire il codice

                this.setTitolo("Posizione dei file .pdf");

                campoPath = CampoFactory.testo(NOME_CAMPO_PATH_PDF);
                campoPath.setLarghezza(300);
                campoPath.setAbilitato(false);
                this.addCampoCollezione(campoPath);

                /* crea il bottone Seleziona path pdf*/
                bottone = new JButton("Seleziona...");
                bottone.setOpaque(false);
                bottone.setFocusable(false);
                bottone.addActionListener(new AzSeleziona());

                /* costruzione grafica */
                pan2 = PannelloFactory.orizzontale(this);
                pan2.setUsaGapFisso(true);
                pan2.setGapPreferito(10);
                pan2.setAllineamento(Layout.ALLINEA_BASSO);
                pan2.add(campoPath);
                pan2.add(bottone);

                this.addPannello(pan2);

                /* registra il path attuale dalle Preferenze */
                this.setPath(AlbergoPref.Albergo.pathPdfPS.str());


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Seleziona il path per il pdf.
         * <p/>
         */
        private void selezionaPath() {
            /* variabili e costanti locali di lavoro */
            File file;
            String path;

            try {    // prova ad eseguire il codice
                file = LibFile.getDir("Seleziona la cartella");
                if (file != null) {
                    path = file.getPath();
                    this.setPath(path);
                }// fine del blocco if

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

        }


        /**
         * Ritorna il path correntemente presente nel dialogo.
         * <p/>
         *
         * @return il path presente
         */
        private String getPath() {
            return this.getString(NOME_CAMPO_PATH_PDF);
        }


        /**
         * Assegna un path al dialogo.
         * <p/>
         *
         * @param path da assegnare
         */
        private void setPath(String path) {
            this.setValore(NOME_CAMPO_PATH_PDF, path);
        }


        /**
         * Azione bottone seleziona path pdf
         * </p>
         */
        private final class AzSeleziona implements ActionListener {

            public void actionPerformed(ActionEvent event) {
                selezionaPath();
            }
        } // fine della classe 'interna'



    } // fine della classe 'interna'


}