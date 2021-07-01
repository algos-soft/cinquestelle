/**
 * Title:     PanCondizioni
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      9-feb-2009
 */
package it.algos.albergo.sviluppopresenze;

import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.costante.CostanteColore;
import it.algos.base.dialogo.Dialogo;
import it.algos.base.dialogo.DialogoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.font.FontFactory;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.ricerca.CampoRicerca;
import it.algos.base.ricerca.Ricerca;
import it.algos.base.ricerca.RicercaBase;
import it.algos.base.wrapper.DueDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Pannello di presentazione e inserimento delle condizioni di analisi
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 9-feb-2009 ore 12.38.23
 */
class PanCondizioni extends PanDialogo {

    /* estremi del periodo di analisi */
    private DueDate periodo;

    /**
     * filtro che isola un set di prenotazioni da prendere
     * in considerazione (null per tutte)
     */
    private Filtro filtroPrenotazioni;

    /* Oggetto Ricerca Prenotazioni - lazy creation nel getter */
    private Ricerca ricercaPren;

    /* Pannello condizioni sulle Prenotazioni da considerare */
    private PanPrenotazioni panPrenotazioni;

    /* Pannello condizioni sul periodo di analisi */
    private PanPeriodo panPeriodo;

    /**
     * Costruttore completo con parametri. <br>
     *
     * @param dialogo dialogo di riferimento
     */
    public PanCondizioni(SviluppoDialogo dialogo) {
        /* rimanda al costruttore della superclasse */
        super(dialogo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice

            DueDate periodo = new DueDate();
            this.setPeriodo(periodo);
            this.creaBordo("Condizioni");

            this.setGapPreferito(0);

            PanPrenotazioni ppren = new PanPrenotazioni();
            this.setPanPrenotazioni(ppren);
            this.add(ppren);

            this.add(new JSeparator(SwingConstants.HORIZONTAL));

            PanPeriodo pperi = new PanPeriodo();
            this.setPanPeriodo(pperi);
            this.add(pperi);

            this.bloccaAltMax();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }// fine del metodo inizia


    /**
     * Verifica se le condizioni di analisi sono valide.
     * <p/>
     * Deve essere stato specificato o il filtro prenotazioni
     * o un periodo di analisi valido.
     *
     * @return true se le condizioni di analisi sono valide
     */
    public boolean isValido() {
        /* variabili e costanti locali di lavoro */
        boolean valido = false;

        try {    // prova ad eseguire il codice

            if (this.getFiltroPrenotazioni() != null) {
                valido = true;
            }// fine del blocco if

            if (!valido) {
                valido = this.isPeriodoValido();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Verifica se le condizioni di analisi della sezione Periodo sono valide.
     * <p/>
     * Deve essere stato specificato un periodo di analisi valido.
     *
     * @return true se le condizioni di analisi della sezione Periodo sono valide
     */
    private boolean isPeriodoValido() {
        return this.getPeriodo().isSequenza();
    }


    /**
     * Ritorna il filtro sulla Azienda attiva
     * <p/>
     *
     * @return il filtro Azienda
     */
    private Filtro getFiltroAzienda() {
        return PrenotazioneModulo.get().getFiltroAzienda();
    }


    /**
     * Ritorna il filtro sui Periodi corrispondente
     * alle condizioni correntemente impostate.
     * <p/>
     * <p/>
     * 1) Se è stata specificata una condizione sulle prenotazioni,
     * seleziona i periodi relativi alle prenotazioni specificate.
     * <p/>
     * 2) Se è stato specificato un intervallo valido, seleziona i periodi che
     * hanno almeno uno dei due estremi che ricade all'interno dell'intervallo.
     * <p/>
     * Il filtro finale è già comprensivo di filtro Azienda.
     *
     * @return il filtro sui Periodi
     */
    public Filtro getFiltroPeriodi() {
        /* variabili e costanti locali di lavoro */
        Filtro filtroPeriodi = null;
        Filtro filtro;

        try {    // prova ad eseguire il codice

            filtroPeriodi = new Filtro();

            filtro = this.getFiltroPrenotazioni();
            if (filtro != null) {
                filtroPeriodi.add(filtro);
            }// fine del blocco if

            filtro = this.getFiltroDate();
            if (filtro != null) {
                filtroPeriodi.add(filtro);
            }// fine del blocco if

            filtro = this.getFiltroAzienda();
            if (filtro != null) {
                filtroPeriodi.add(filtro);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtroPeriodi;
    }


    /**
     * Crea il filtro sulle date impostate
     * <p/>
     * seleziona i periodi che hanno almeno uno dei due estremi
     * che ricade all'interno dell'intervallo di date correntemente impostato
     *
     * @return il filtro periodi, null se l'intervallo non è impostato o non è valido
     */
    private Filtro getFiltroDate() {
        /* variabili e costanti locali di lavoro */
        Filtro ftot = null;
//        Filtro filtro;
//        Filtro f1, f2, f3;
//        Campo p1, p2;
        Date d1, d2;

        try {    // prova ad eseguire il codice

            if (this.isPeriodoValido()) {

                d1 = this.getPeriodo().getData1();
                d2 = this.getPeriodo().getData2();
                ftot = PeriodoModulo.getFiltroInteressati(d1, d2);

//                p1 = PeriodoModulo.get().getCampo(Periodo.Cam.arrivoPrevisto.get());
//                p2 = PeriodoModulo.get().getCampo(Periodo.Cam.partenzaPrevista.get());
//
//                d1 = this.getPeriodo().getData1();
//                d2 = this.getPeriodo().getData2();
//
//                /* l'inizio compreso tra le due date */
//                f1 = new Filtro();
//                filtro = FiltroFactory.crea(p1, Filtro.Op.MAGGIORE_UGUALE, d1);
//                f1.add(filtro);
//                filtro = FiltroFactory.crea(p1, Filtro.Op.MINORE_UGUALE, d2);
//                f1.add(filtro);
//
//                /* la fine compresa tra le due date */
//                f2 = new Filtro();
//                filtro = FiltroFactory.crea(p2, Filtro.Op.MAGGIORE_UGUALE, d1);
//                f2.add(filtro);
//                filtro = FiltroFactory.crea(p2, Filtro.Op.MINORE_UGUALE, d2);
//                f2.add(filtro);
//
//                /* inizia prima e finisce dopo (attraversa le due date) */
//                f3 = new Filtro();
//                filtro = FiltroFactory.crea(p1, Filtro.Op.MINORE_UGUALE, d1);
//                f3.add(filtro);
//                filtro = FiltroFactory.crea(p2, Filtro.Op.MAGGIORE_UGUALE, d2);
//                f3.add(filtro);
//
//                ftot = new Filtro();
//                ftot.add(f1);
//                ftot.add(Filtro.Op.OR, f2);
//                ftot.add(Filtro.Op.OR, f3);

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return ftot;
    }


    /**
     * Crea un oggetto Ricerca per le prenotazioni.
     * <p/>
     *
     * @return la ricerca creata
     */
    private Ricerca creaRicerca() {
        /* variabili e costanti locali di lavoro */
        RicercaBase ricerca = null;
        Campo campo;
        CampoRicerca crConfermata;
        CampoRicerca crOpzione;
        CampoRicerca crDisdetta;

        try {    // prova ad eseguire il codice

            Modulo mod = PrenotazioneModulo.get();
            ricerca = new RicercaBase(mod);

            /* aggiunge i campi di ricerca */
            campo = mod.getCampo(Prenotazione.Cam.dataPrenotazione);
            ricerca.addCampoRicerca(campo, true);

            campo = mod.getCampo(Prenotazione.Cam.confermata);
            crConfermata = ricerca.addCampoRicerca(campo);

            campo = mod.getCampo(Prenotazione.Cam.opzione);
            crOpzione = ricerca.addCampoRicerca(campo);

            campo = mod.getCampo(Prenotazione.Cam.disdetta);
            crDisdetta = ricerca.addCampoRicerca(campo);

            campo = mod.getCampo(Prenotazione.Cam.chiusa);
            ricerca.addCampoRicerca(campo);


            campo = mod.getCampo(Prenotazione.Cam.canale);
            ricerca.addCampoRicerca(campo);

            /* avvia e centra in finestra senza rendere visibile */
            ricerca.avvia(false);
            ricerca.getDialogo().pack();
            Lib.Gui.fitWindowToScreen(ricerca.getDialogo());
            Lib.Gui.centraWindow(ricerca.getDialogo(), null);

            /* regola le condizioni di ricerca di default */
//            crConfermata.getCampo1().setValore(true);
            crOpzione.getCampo2().setValore(true);
            crDisdetta.getCampo2().setValore(true);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return ricerca;
    }


    /**
     * Ritorna un testo riepilogativo delle condizioni
     * di analisi correntemente impostate.
     * <p/>
     * @return il testo delle condizioni
     */
    public String getTestoCondizioni () {
        /* variabili e costanti locali di lavoro */
        String testo  = "";

        try {    // prova ad eseguire il codice
            testo+="Prenotazioni considerate:\n";
            testo+=this.getPanPrenotazioni().getTesto();
            testo+="\n---------\n";
            testo+="Periodo di analisi: ";
            testo+=this.getPanPeriodo().getTesto();
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Svuota il modulo Risultati.
     * <p/>
     * Cancella tutti i record e aggiorna la lista
     */
    private void svuotaRisultati () {
        this.getDialogoMain().svuotaRisultati();
    }





    public DueDate getPeriodo() {
        return periodo;
    }


    private void setPeriodo(DueDate periodo) {
        this.periodo = periodo;
    }


    private Filtro getFiltroPrenotazioni() {
        return filtroPrenotazioni;
    }


    private void setFiltroPrenotazioni(Filtro filtroPrenotazioni) {
        this.filtroPrenotazioni = filtroPrenotazioni;
    }


    /**
     * Ritorna l'oggetto Ricerca Prenotazioni
     * <p/>
     * Se non esiste lo istanzia al momento e lo registra per le volte successive
     *
     * @return l'oggetto Ricerca Prenotazioni
     */
    private Ricerca getRicercaPren() {

        if (ricercaPren == null) {
            ricercaPren = this.creaRicerca();
        }// fine del blocco if

        /* valore di ritorno */
        return ricercaPren;
    }


    private PanPrenotazioni getPanPrenotazioni() {
        return panPrenotazioni;
    }


    private void setPanPrenotazioni(PanPrenotazioni panPrenotazioni) {
        this.panPrenotazioni = panPrenotazioni;
    }


    private PanPeriodo getPanPeriodo() {
        return panPeriodo;
    }


    private void setPanPeriodo(PanPeriodo panPeriodo) {
        this.panPeriodo = panPeriodo;
    }


    /**
     * Pannello sezione Prenotazioni
     * <p/>
     */
    private final class PanPrenotazioni extends PanSezione {

//        private JTextArea areaSpiega;


        public PanPrenotazioni() {
            super("Prenotazioni da considerare");
            this.inizia();
        }// fine del metodo costruttore completo


        /**
         * Regolazioni immediate di riferimenti e variabili. <br>
         * Metodo chiamato direttamente dal costruttore <br>
         */
        private void inizia() {

            /* aggiunge l'azione al bottone Modifica */
            this.getBottone().addActionListener(new AzBottone());
            this.getBottone().setText("Ricerca...");
            String tooltip = "Seleziona le prenotazioni da considerare nell'analisi";
            this.getBottone().setToolTipText(tooltip);

            setFiltroPrenotazioni(getRicercaPren().getFiltro());

            this.sincronizza();
        }


        /**
         * Sincronizzazione
         */
        private void sincronizza() {
            /* variabili e costanti locali di lavoro */
            String testo;
            Filtro filtro;

            try { // prova ad eseguire il codice

                if (getFiltroPrenotazioni() == null) {  // no filtro, tutte le prenotazioni

                    testo = "Tutte le prenotazioni";

                } else {  // c'è un filtro, conta quanti ne isola

                    filtro = new Filtro();
                    if (getFiltroAzienda() != null) {
                        filtro.add(getFiltroAzienda());
                    }// fine del blocco if
                    if (getFiltroPrenotazioni() != null) {
                        filtro.add(getFiltroPrenotazioni());
                    }// fine del blocco if
                    Modulo mod = PrenotazioneModulo.get();
                    int quanti = mod.query().contaRecords(filtro);

                    testo = getRicercaPren().getTestoRicerca();
                    testo += "\n[N. prenotazioni considerate: " + quanti + "]";


                }// fine del blocco if-else

                this.setTesto(testo);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Bottone modifica premuto nel pannello.
         * <p/>
         * Mostra una ricerca
         * Crea e registra il filtro
         */
        private void bottonePremuto() {
            /* variabili e costanti locali di lavoro */
            Ricerca ricerca;
            RicercaBase rb;

            try { // prova ad eseguire il codice

                /**
                 * non ri-avvia ma rende solo il dialogo visibile
                 * per non perdere i valori inseriti nei campi
                 */
                ricerca = getRicercaPren();
                rb = ricerca.getRicercaBase();
                rb.sincronizza();
                rb.getDialogo().setVisible(true);

                /* se confermato aggiorna il filtro prenotazioni */
                if (ricerca.isConfermato()) {
                    setFiltroPrenotazioni(ricerca.getFiltro());
                    svuotaRisultati();
                    this.sincronizza();
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Action listener del bottone
         */
        private final class AzBottone implements ActionListener {

            public void actionPerformed(ActionEvent event) {
                bottonePremuto();
            }
        } // fine della classe 'interna'


    } // fine della classe 'interna'

    /**
     * Pannello sezione Periodo
     * <p/>
     */
    private final class PanPeriodo extends PanSezione {

        public PanPeriodo() {
            super("Periodo da analizzare");
            this.inizia();
        }// fine del metodo costruttore completo


        /**
         * Regolazioni immediate di riferimenti e variabili. <br>
         * Metodo chiamato direttamente dal costruttore <br>
         */
        private void inizia() {
            /* variabili e costanti locali di lavoro */

            try { // prova ad eseguire il codice
                /* aggiunge l'azione al bottone Modifica */
                this.getBottone().addActionListener(new AzBottone());
                String tooltip = "Imposta il periodo da analizzare";
                this.getBottone().setToolTipText(tooltip);

                this.sincronizza();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Sincronizzazione
         */
        private void sincronizza() {
            /* variabili e costanti locali di lavoro */
            String testo;

            if (getPeriodo().isVuoto()) {
                testo = "Tutti i periodi";
            } else {
                if (isPeriodoValido()) {
                    testo = "dal "
                            + Lib.Data.getStringa(getPeriodo().getData1())
                            + " al "
                            + Lib.Data
                            .getStringa(getPeriodo().getData2());
                } else {
                    testo = "Periodo non valido!";
                }// fine del blocco if-else
            }// fine del blocco if-else

            this.setTesto(testo);
        }


        /**
         * Bottone modifica premuto nel pannello.
         * <p/>
         */
        private void bottonePremuto() {
            Dialogo dialogo;
            Campo campo1, campo2;

            try { // prova ad eseguire il codice
                dialogo = DialogoFactory.annullaConferma();
                dialogo.setTitolo("Periodo di analisi");

                campo1 = CampoFactory.data("dal");
                campo1.decora().eliminaEtichetta();
                campo1.decora().etichettaSinistra();

                campo2 = CampoFactory.data("al");
                campo2.decora().eliminaEtichetta();
                campo2.decora().etichettaSinistra();

                Pannello pan = PannelloFactory.orizzontale(null);
                pan.add(campo1);
                pan.add(campo2);
                campo1.setValore(getPeriodo().getData1());
                campo2.setValore(getPeriodo().getData2());
                dialogo.addPannello(pan);
                dialogo.avvia();
                if (dialogo.isConfermato()) {
                    getPeriodo().setData1(Libreria.getDate(campo1.getValore()));
                    getPeriodo().setData2(Libreria.getDate(campo2.getValore()));
                    svuotaRisultati();
                    this.sincronizza();
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Action listener del bottone
         */
        private final class AzBottone implements ActionListener {

            public void actionPerformed(ActionEvent event) {
                bottonePremuto();
            }
        } // fine della classe 'interna'


    } // fine della classe 'interna'

    /**
     * Pannello sezione condizioni
     * <p/>
     */
    private abstract class PanSezione extends PannelloFlusso {

        private String titolo;

        private JButton bottone;

        private JTextArea areaSpiega;


        /**
         * Costruttore
         * <p/>
         *
         * @param titolo titolo della sezione
         */
        public PanSezione(String titolo) {
            /* rimanda al costruttore della superclasse */
            super(Layout.ORIENTAMENTO_VERTICALE);

            this.titolo = titolo;

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
            /* variabili e costanti locali di lavoro */
            JLabel lblTitolo;
            JButton bot;
            JTextArea area;

            try { // prova ad eseguire il codice

                this.setUsaGapFisso(true);
                this.setGapPreferito(0);
                this.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));

                /* etichetta con titolo */
                lblTitolo = new JLabel(this.titolo);
                lblTitolo.setFont(FontFactory.creaScreenFont(10f));
                lblTitolo.setForeground(Color.BLUE);
                this.add(lblTitolo);

                /* pannello orizzontale con bottone e spiega */
                bot = new JButton("Modifica...");
                bot.setOpaque(false);
                Lib.Comp.setPreferredWidth(bot, 120);
                this.setBottone(bot);

                /* area spiega */
                area = new JTextArea();
                area.setEditable(false);
                area.setOpaque(false);
                area.setForeground(CostanteColore.ROSSO);
                this.setAreaSpiega(area);

                Pannello pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
                pan.setOpaque(false);
                pan.setAllineamento(Layout.ALLINEA_CENTRO);
                pan.add(Lib.Comp.createHorizontalFiller(10, 10, 10));
                pan.add(bot);
                pan.add(area);
                this.add(pan);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Ritorna il testo del pannello dati
         * <p/>
         * @return il testo del pannello dati
         */
        public String getTesto(){
            return this.getAreaSpiega().getText();
        }


        /**
         * Assegna il testo del pannello dati .
         * <p/>
         *
         * @param testo da assegnare
         */
        protected void setTesto(String testo) {
            this.getAreaSpiega().setText(testo);
        }


        protected JButton getBottone() {
            return bottone;
        }


        private void setBottone(JButton bottone) {
            this.bottone = bottone;
        }


        private JTextArea getAreaSpiega() {
            return areaSpiega;
        }


        private void setAreaSpiega(JTextArea areaSpiega) {
            this.areaSpiega = areaSpiega;
        }
    } // fine della classe 'interna'


}// fine della classe
