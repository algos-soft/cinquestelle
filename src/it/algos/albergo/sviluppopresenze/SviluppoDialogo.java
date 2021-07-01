/**
 * Title:     ContoDialogoRiepilogo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      16-lug-2006
 */
package it.algos.albergo.sviluppopresenze;

import com.wildcrest.j2printerworks.J2ComponentPrinter;
import com.wildcrest.j2printerworks.J2FlowPrinter;
import com.wildcrest.j2printerworks.J2Printer;
import com.wildcrest.j2printerworks.J2TextPrinter;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.memoria.dati.DatiMemoria;
import it.algos.base.dialogo.Dialogo;
import it.algos.base.dialogo.DialogoBase;
import it.algos.base.errore.Errore;
import it.algos.base.evento.lista.ListaSelAz;
import it.algos.base.evento.lista.ListaSelEve;
import it.algos.base.font.FontFactory;
import it.algos.base.importExport.Esporta;
import it.algos.base.importExport.ExportSettings;
import it.algos.base.libreria.Lib;
import it.algos.base.lista.Lista;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progressbar.OperazioneMonitorabile;
import it.algos.base.progressbar.ProgressBar;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.wrapper.DueDate;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.JButton;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Dialogo di analisi sviluppo presenze.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 09-feb-2009 ore 12.00.06
 */
public class SviluppoDialogo extends DialogoBase {

    /* pannello di display condizioni di analisi */
    private PanCondizioni panCondizioni = null;

    /* pannello per la presentazione del riassunto dei risultati della ricerca */
    private PanRiassunto panRiassunto = null;

    /* pannello per la presentazione del dettaglio dei risultati della ricerca */
    private PanDettaglio panDettaglio = null;

    /* Pannello per i comandi del dialogo */
    private PanComandi panComandi = null;

    /* Bottone Esegui Sviluppo */
    private JButton botEsegui;

    /* Modulo memoria che mantiene e visualizza i risultati */
    private ModuloRisultati moduloRisultati;

    /* Tipologia di suddivisione relativa all'ultima generazione dati */
    private Suddivisione suddivisioneCorrente;

    /**
     * Mappa dettagli dalla quale viene riempita la lista visualizzata
     * Aggiornata ogni volta che si effettua l'analisi
     */
    private MappaDettagli mappa;


    /**
     * Costruttore completo con parametri
     * <p/>
     */
    public SviluppoDialogo() {
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
        String titolo = "Sviluppo prenotazioni";

        try { // prova ad eseguire il codice

            /* regola il dialogo */
            this.getDialogo().setModal(false);
            this.setTitolo(titolo);

            /* crea il modulo risultati */
            this.creaModuloRisultati();

            /* crea graficamente il dialogo */
            this.creaDialogo();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea e registra il modulo memoria per i risultati.
     * <p/>
     */
    private void creaModuloRisultati() {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try {    // prova ad eseguire il codice
            ArrayList<Campo> campi = new ArrayList<Campo>();

            campo = CampoFactory.intero(Nomi.chiave.get());
            campo.setTitoloColonna("Chiave");
            campo.setVisibileVistaDefault(false);
            campi.add(campo);

            campo = CampoFactory.testo(Nomi.sigla.get());
            campo.setVisibileVistaDefault(true);
            campo.setTitoloColonna("Sigla");
            campo.setLarLista(80);
            campi.add(campo);

            campo = CampoFactory.testo(Nomi.descrizione.get());
            campo.setVisibileVistaDefault(true);
            campo.setTitoloColonna("Descrizione");
            campo.setLarLista(180);
            campi.add(campo);

            campo = CampoFactory.intero(Nomi.pres_ad.get());
            campo.setVisibileVistaDefault(true);
            campo.setTitoloColonna("Pres. Ad.");
            campo.setTotalizzabile(true);
            campo.setLarLista(70);
            campi.add(campo);

            campo = CampoFactory.intero(Nomi.pres_ba.get());
            campo.setVisibileVistaDefault(true);
            campo.setTitoloColonna("Pres. Ba.");
            campo.setTotalizzabile(true);
            campo.setLarLista(70);
            campi.add(campo);

            campo = CampoFactory.intero(Nomi.pres_tot.get());
            campo.setVisibileVistaDefault(true);
            campo.setTitoloColonna("Pres. Tot");
            campo.setTotalizzabile(true);
            campo.setLarLista(70);
            campi.add(campo);

            campo = CampoFactory.percentuale(Nomi.percent_pres.get());
            campo.setVisibileVistaDefault(true);
            campo.setTitoloColonna("%pre");
            campo.setLarLista(40);
            campi.add(campo);

            campo = CampoFactory.reale(Nomi.valore.get());
            campo.setVisibileVistaDefault(true);
            campo.setTitoloColonna("Valore");
            campo.setTotalizzabile(true);
            campo.setLarLista(70);
            campi.add(campo);

            campo = CampoFactory.percentuale(Nomi.percent_val.get());
            campo.setVisibileVistaDefault(true);
            campo.setTitoloColonna("%val");
            campo.setLarLista(40);
            campi.add(campo);

            ModuloRisultati mod = new ModuloRisultati(campi);
            this.setModuloRisultati(mod);

            mod.avvia();

            /* aggiunge un listener per le modifiche di selezione nella lista */
            Navigatore nav = mod.getNavigatoreDefault();
            Lista lista = nav.getLista();
            lista.addListener(new AzioneModificaSelezione());
            /* aggiunge un listener per il cambio di modello dati della tavola */
            lista.getTavola().getModel().addTableModelListener(new TableModelChange());


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Crea, registra i componenti e li aggiunge al dialogo.
     * <p/>
     */
    private void creaDialogo() {
        /* variabili e costanti locali di lavoro */
        JButton bot;

        try {    // prova ad eseguire il codice

            /* crea e registra i pannelli */
            this.setPanCondizioni(new PanCondizioni(this));
            this.setPanRiassunto(new PanRiassunto(this));
            this.setPanDettaglio(new PanDettaglio(this));
            this.setPanComandi(new PanComandi(this));

            /* crea il bottone Esegui */
            bot = new JButton("Esegui");
            bot.addActionListener(new AzBottoneEsegui());
            bot.setOpaque(false);
            this.setBotEsegui(bot);

            /* aggiunge i componenti al dialogo */
            this.addComponente(this.getPanCondizioni());
//            this.addComponente(this.getBotEsegui());
//            this.addComponente(this.getPanRiassunto());
            this.addComponente(this.getPanDettaglio());
            this.addComponente(this.getPanComandi());

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Esegue l'analisi dei periodi selezionati e aggiorna il risultato.
     * <p/>
     */
    protected void eseguiAnalisi() {
        /* variabili e costanti locali di lavoro */
        Filtro filtroPeriodi;
        Suddivisione sudd;
        DueDate periodo;

        try { // prova ad eseguire il codice

            OperazioneMonitorabile op;

            filtroPeriodi = this.getPanCondizioni().getFiltroPeriodi();
            sudd = this.getPanDettaglio().getSuddivisione();
            this.setSuddivisioneCorrente(sudd); // registra l'ultima suddivisione usata
            periodo = this.getPanCondizioni().getPeriodo();
            ProgressBar pb = this.getPanDettaglio().getProgressBar();
            op = new OpAnalisi(this, filtroPeriodi, sudd, periodo, pb);
            op.avvia(); // start new thread

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Svuota il modulo Risultati.
     * <p/>
     * Cancella tutti i record e aggiorna la lista
     */
    public void svuotaRisultati() {
        try {    // prova ad eseguire il codice
            ModuloRisultati mod = getModuloRisultati();
            mod.svuotaRisultati();
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Controlla se l'analisi è eseguibile.
     * <p/>
     * Utilizzato per abilitare il bottone Esegui
     *
     * @return true se eseguibile
     */
    private boolean isEseguibile() {
        return this.getPanCondizioni().isValido();
    }


    /**
     * Controlla se esistono dei dati nella tabella dei risultati.
     * <p/>
     * Utilizzato per abilitare i bottoni
     *
     * @return true se esistono dei dati
     */
    public boolean isEsistonoDati() {
        return this.getModuloRisultati().isEsistonoDati();
    }


    /**
     * Ritorna il numero di righe correntemente selezionate nella tabella dei risultati.
     * <p/>
     *
     * @return true se esistono dei dati
     */
    public int getQuanteRigheSelezionate() {
        return this.getModuloRisultati().getQuanteRigheSelezionate();
    }


    @Override
    public void sincronizza() {
        super.sincronizza();

        try { // prova ad eseguire il codice

            /* abilitazione bottone Esegui */
            this.getBotEsegui().setEnabled(this.isEseguibile());

            /* sincronizzazione degli sottocomponenti che la richiedono */
            this.getPanComandi().sincronizza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Stampa.
     * <p/>
     * Invocato dal bottone
     */
    public void stampa() {
        /* variabili e costanti locali di lavoro */
        Lista lista;
        J2Printer printer;
        J2FlowPrinter fpTot;
        J2FlowPrinter fp;
        J2ComponentPrinter gap = new J2ComponentPrinter(Lib.Comp.createVerticalFiller(10, 10, 10));

        try { // prova ad eseguire il codice

            lista = this.getModuloRisultati().getLista();

            /* crea e regola il Printer */
            printer = Lib.Stampa.getDefaultPrinter();
            printer.setCenterHeader("Sviluppo prenotazioni");

            /* crea un flow printer */
            fpTot = new J2FlowPrinter();

            /* aggiunge un blocco di testo con le condizioni di ricerca */
            String testo = this.getPanCondizioni().getTestoCondizioni();
            J2TextPrinter txtp = new J2TextPrinter(testo, FontFactory.creaPrinterFont(9f));
            fpTot.addFlowable(txtp);

            /* aggiunge un distanziatore */
            fpTot.addFlowable(gap);

            /* aggiunge la lista dei risultati */
            fp = lista.getFlowPrinterLista();
            fpTot.addFlowable(fp);

            /* aggiunge il flow printer completo */
            printer.addPageable(fpTot);

            /* stampa */
            printer.showPrintPreviewDialog();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Esporta.
     * <p/>
     * Invocato dal bottone
     */
    public void esporta() {
        /* variabili e costanti locali di lavoro */
        Dati dati;
        Dati dati2 = null;
        ExportSettings settings;
        Esporta exporter;
        boolean continua = true;
        boolean dati2creato = false;

        try { // prova ad eseguire il codice

            /* recupera i dati e controlla che esistano */
            dati = this.getModuloRisultati().getDatiRisultati();
            if (dati == null) {
                new MessaggioAvviso("Non ci sono dati da esportare");
                continua = false;
            }// fine del blocco if-else

            /* crea un nuovo Dati2 con le solo colonne desiderate */
            if (continua) {

                Modulo mod = this.getModuloRisultati();
                ArrayList<Campo> campi = new ArrayList<Campo>();
                campi.add(mod.getCampo(Nomi.sigla.get()));
                campi.add(mod.getCampo(Nomi.descrizione.get()));
                campi.add(mod.getCampo(Nomi.pres_ad.get()));
                campi.add(mod.getCampo(Nomi.pres_ba.get()));
                campi.add(mod.getCampo(Nomi.pres_tot.get()));
//                campi.add(mod.getCampo(Nomi.percent_pres.get()));
                campi.add(mod.getCampo(Nomi.valore.get()));
//                campi.add(mod.getCampo(Nomi.percent_val.get()));
                dati2 = new DatiMemoria(campi, dati.getRowCount());
                dati2creato = true;

                Object val;
                for (int row = 0; row < dati.getRowCount(); row++) {
                    for (Campo campo : campi) {
                        val = dati.getValueAt(row, campo);
                        dati2.setValueAt(row, campo, val);
                    }
                } // fine del ciclo for
            }// fine del blocco if

            /* presenta i settings e se confermato esporta */
            if (continua) {
                settings = new ExportSettings();
                if (settings.edit()) {
                    exporter = new Esporta(dati2, settings);
                    exporter.run();
                    Lib.Sist.beep();
                    new MessaggioAvviso("Terminato.");
                }// fine del blocco if
            }// fine del blocco if

            /* se ha creato dati2 lo deve chiudere */
            if (dati2creato) {
                dati2.close();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Grafico presenze.
     * <p/>
     * Invocato dal bottone
     */
    public void graficoPresenze() {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            /* crea un dataset per il grafico */
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            Dati dati = this.getModuloRisultati().getDatiRisultati();
            Integer chiave;
            String label;
            int adulti, bambini;
            KeyAndLabel kl;
            for (int k = 0; k < dati.getRowCount(); k++) {
                chiave = dati.getIntAt(k, Nomi.chiave.get());
                label = dati.getStringAt(k, Nomi.sigla.get());
                kl = new KeyAndLabel(chiave,label);
                adulti = dati.getIntAt(k, Nomi.pres_ad.get());
                bambini = dati.getIntAt(k, Nomi.pres_ba.get());
                dataset.setValue(adulti, "Adulti", kl);
                dataset.setValue(bambini, "Bambini", kl);
            } // fine del ciclo for

            /* crea e regola l'oggetto chart */
            String titolo = "Sviluppo prenotazioni";
            JFreeChart chart = ChartFactory.createStackedBarChart(
                    titolo,
                    this.getSuddivisioneCorrente().getLabel(),
                    "Presenze",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true, true, false);
            chart.setBackgroundPaint(null);

            /* ruota le label dell'asse X di 45° */
            CategoryPlot plot = (CategoryPlot)chart.getPlot();
            CategoryAxis xAxis = plot.getDomainAxis();
            xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

            /* mostra il grafico in un dialogo dedicato */
            Dialogo dialogo = new DialogoChart(chart);
            dialogo.setTitolo("Grafico Presenze");
            dialogo.avvia();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Grafico valori.
     * <p/>
     * Invocato dal bottone
     */
    public void graficoValori() {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            /* crea un dataset per il grafico */
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            Dati dati = this.getModuloRisultati().getDatiRisultati();
            Integer chiave;
            String label;
            double valore;
            KeyAndLabel kl;
            for (int k = 0; k < dati.getRowCount(); k++) {
                chiave = dati.getIntAt(k, Nomi.chiave.get());
                label = dati.getStringAt(k, Nomi.sigla.get());
                kl = new KeyAndLabel(chiave,label);
                valore = dati.getDoubleAt(k, Nomi.valore.get());
                dataset.setValue(valore, "Valore", kl);
            } // fine del ciclo for

            /* crea e regola l'oggetto chart */
            String titolo = "Sviluppo prenotazioni";
            JFreeChart chart = ChartFactory.createStackedBarChart(
                    titolo,
                    this.getSuddivisioneCorrente().getLabel(),
                    "Valore",
                    dataset,
                    PlotOrientation.VERTICAL,
                    false, true, false);
            chart.setBackgroundPaint(null);

            /* ruota le label dell'asse X di 45° */
            CategoryPlot plot = (CategoryPlot)chart.getPlot();
            CategoryAxis xAxis = plot.getDomainAxis();
            xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

            /* mostra il grafico in un dialogo dedicato */
            Dialogo dialogo = new DialogoChart(chart);
            dialogo.setTitolo("Grafico Valori");
            dialogo.avvia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Mostra.
     * <p/>
     * Invocato dal bottone
     */
    public void mostra() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo modRisultati = this.getModuloRisultati();
        Lista lista = modRisultati.getLista();
        MappaDettagli mappa;
        RigaDettaglio riga;
        ArrayList<Integer> codici;
        ArrayList<Integer> codTotali = new ArrayList<Integer>();
        ArrayList<Integer> codPrenotazioni=new ArrayList<Integer>();
        Filtro filtro=null;

        try { // prova ad eseguire il codice

            /* recupera la mappa dettagli */
            mappa = this.getMappaDettagli();
            continua = (mappa != null);

            /**
             * spazzola le chiavi, e per ognuna recupera dalla mappa
             * i codici periodo originali.
             * Raccoglie tutti i codici univoci nella lista codTotali
             */
            if (continua) {
                int[] kRighe = lista.getChiaviSelezionate();
                for (int kRiga : kRighe) {
                    int kSudd = modRisultati.query().valoreInt(Nomi.chiave.get(), kRiga);
                    riga = mappa.get(kSudd);
                    codici = riga.getPeriodiOrigine();
                    for (int cod : codici) {
                        if (!codTotali.contains(cod)) {
                            codTotali.add(cod);
                        }// fine del blocco if
                    }
                }
                continua = (codTotali.size() > 0);
            }// fine del blocco if

            /**
             * Recupera i codici delle corrispondenti prenotazioni
             * e crea una lista univoca
             */
            if (continua) {
                Modulo modPeriodo = PeriodoModulo.get();
                for (int codPeri : codTotali) {
                    int codPren = modPeriodo.query()
                            .valoreInt(Periodo.Cam.prenotazione.get(), codPeri);
                    if (!codPrenotazioni.contains(codPren)) {
                        codPrenotazioni.add(codPren);
                    }// fine del blocco if
                }
                continua = (codPrenotazioni.size() > 0);
            }// fine del blocco if

            /**
             * Crea un filtro per selezionare le prenotazioni
             */
            if (continua) {
                int[] interi = new int[codPrenotazioni.size()];
                for (int k = 0; k < codPrenotazioni.size(); k++) {
                    interi[k] = codPrenotazioni.get(k);
                } // fine del ciclo for
                filtro = FiltroFactory.elenco(PrenotazioneModulo.get(), interi);
            }// fine del blocco if

            /**
             * Visualizza la lista delle prenotazioni
             */
            if (continua) {
//                Modulo modPren = PrenotazioneModulo.get();
//                Lista listaPren = nav.getLista();
//                listaPren.setFiltroCorrente(filtro);
//                listaPren.caricaSelezione();
//                nav.getPortaleNavigatore().getFinestra().getFinestraBase().toFront();
//                nav.avvia();

                Modulo modPren = PrenotazioneModulo.get();
                Navigatore nav = modPren.getNavigatoreDefault();
                Lista listaPren = nav.getLista();
                nav.avvia();
                listaPren.setFiltroCorrente(filtro);
                nav.aggiornaLista();
                nav.apriNavigatore();

            }// fine del blocco if


            int a = 87;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Action listener del bottone Esegui
     */
    private final class AzBottoneEsegui implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            eseguiAnalisi();
        }
    } // fine della classe 'interna'

    /**
     * Azione modifica di selezione nella lista dei risultati.
     */
    private class AzioneModificaSelezione extends ListaSelAz {

        /**
         * @param unEvento evento che causa l'azione da eseguire
         */
        public void listaSelAz(ListaSelEve unEvento) {
            sincronizza();
        }
    } // fine della classe interna

    private class TableModelChange implements TableModelListener {

        public void tableChanged(TableModelEvent e) {
            sincronizza();
        }
    }

    /**
     * Classe rappresentante l'identificazione univoca di una colonna
     * del grafico e la sua etichetta.
     * </p>
     * La chiave unica viene usata per l'identificazione della colonna
     * Il metodo toString() viene utilizzato per visualizzazione dell'etichetta
     */
    private class KeyAndLabel implements Comparable {

        private String label;
        private int chiave;

        /**
         * Costruttore completo con parametri.
         * <p>
         * @param chiave numerica della colonna
         * @param label da visualizzare come etichetta
         */
        public KeyAndLabel(int chiave, String label) {
            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili di istanza coi parametri */
            this.setChiave(chiave);
            this.setLabel(label);

        }// fine del metodo costruttore completo


        /**
         * Comparatore.
         * <p>
         * Usa la chiave per comparare i valori
         * */
        public int compareTo(Object altro) throws ClassCastException {
            /* variabili e costanti locali di lavoro */
            int risultato;
            KeyAndLabel unAltro;
            Integer altraKey;
            Integer questaKey;

            if (altro!=null) {
                if (altro instanceof KeyAndLabel) {
                    unAltro = (KeyAndLabel)altro;
                    altraKey = unAltro.getChiave();
                    questaKey = this.getChiave();
                    risultato = questaKey.compareTo(altraKey);
                } else {
                    throw new ClassCastException("Comparazione di tipi diversi.");
                }// fine del blocco if-else
            } else {
                throw new ClassCastException("Comparazione con nullo.");
            }// fine del blocco if-else

            /* valore di ritorno */
            return risultato;

        }


        private String getLabel() {
            return label;
        }


        private void setLabel(String label) {
            this.label = label;
        }


        private int getChiave() {
            return chiave;
        }


        private void setChiave(int chiave) {
            this.chiave = chiave;
        }

        @Override
        public String toString() {
            return this.getLabel();
        }
    } // fine della classe 'interna'



    private PanCondizioni getPanCondizioni() {
        return panCondizioni;
    }


    private void setPanCondizioni(PanCondizioni panCondizioni) {
        this.panCondizioni = panCondizioni;
    }


    private PanRiassunto getPanRiassunto() {
        return panRiassunto;
    }


    private void setPanRiassunto(PanRiassunto panRiassunto) {
        this.panRiassunto = panRiassunto;
    }


    private PanDettaglio getPanDettaglio() {
        return panDettaglio;
    }


    private void setPanDettaglio(PanDettaglio panDettaglio) {
        this.panDettaglio = panDettaglio;
    }


    private PanComandi getPanComandi() {
        return panComandi;
    }


    private void setPanComandi(PanComandi panComandi) {
        this.panComandi = panComandi;
    }


    private JButton getBotEsegui() {
        return botEsegui;
    }


    private void setBotEsegui(JButton botEsegui) {
        this.botEsegui = botEsegui;
    }


    public ModuloRisultati getModuloRisultati() {
        return moduloRisultati;
    }


    private void setModuloRisultati(ModuloRisultati moduloRisultati) {
        this.moduloRisultati = moduloRisultati;
    }


    private Suddivisione getSuddivisioneCorrente() {
        return suddivisioneCorrente;
    }


    private void setSuddivisioneCorrente(Suddivisione suddivisioneCorrente) {
        this.suddivisioneCorrente = suddivisioneCorrente;
    }


    private MappaDettagli getMappaDettagli() {
        return mappa;
    }


    public void setMappaDettagli(MappaDettagli mappa) {
        this.mappa = mappa;
    }
}// fine della classe