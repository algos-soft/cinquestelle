package it.algos.albergo.arrivipartenze;

import com.wildcrest.j2printerworks.J2PanelPrinter;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.clientealbergo.tabelle.parente.ParentelaModulo;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.componente.WrapTextArea;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.font.FontFactory;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.gestione.anagrafica.Anagrafica;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.Font;
import java.util.ArrayList;

/**
 * Superclasse per i wrapper di stampa delle prenotazione in arrivo e partenza.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 26-giu-2008
 */
public abstract class ArrivoPartenzaWrap extends J2PanelPrinter {

    /** font di stampa normale */
    private Font font = FontFactory.creaPrinterFont();

    /** font di stampa bold */
    private Font fontBold = FontFactory.creaPrinterFont(Font.BOLD);


    /**
     * Wrapper di una prenotazione in arrivo.
     */
    private PrenotazionePeriodiWrap wrap;

    /**
     * Larghezza del pannello esterno.
     */
    private static final int LARGHEZZA = 580;


    /**
     * Costruttore completo con parametri.
     *
     * @param wrapIn con prenotazione e periodi interessati
     */
    public ArrivoPartenzaWrap(final PrenotazionePeriodiWrap wrapIn) {
        /* rimanda al costruttore della superclasse */
        super();


        try { // prova ad eseguire il codice
            /* regola le variabili di istanza coi parametri */
            this.setWrap(wrapIn);

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    } // fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        JPanel pannello;

        try {    // prova ad eseguire il codice
            pannello = this.creaPan();
            this.setPanel(pannello);

            this.setHorizontalAlignment(LEFT);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    } // fine del metodo inizia


    /**
     * Pannello principale per un periodo in arrivo/partenza.
     *
     * @return pannello grafico da inserire nello stampabile
     */
    private JPanel creaPan() {
        /* variabili e costanti locali di lavoro */
        JPanel pannello = null;
        boolean continua;
        Pannello pan;
        String cliente;
        int inset = 10;

        try { // prova ad eseguire il codice

            cliente = this.getCliente();
            cliente = " " + cliente + " ";
            continua = (Lib.Testo.isValida(cliente));

            if (continua) {
                pan = PannelloFactory.verticale(null);
                pan.setUsaGapFisso(true);
                pan.setGapPreferito(5);
                Border bordo0 = BorderFactory.createEmptyBorder(inset, inset, inset, inset);
                Border bordo1 = BorderFactory.createTitledBorder(cliente);
                Border bordo = BorderFactory.createCompoundBorder(bordo1, bordo0);
                pan.getPanFisso().setBorder(bordo);

                /* caparra della prenotazione */
                this.addCaparra(pan);

                /* periodi */
                this.addPeriodi(pan);

                /* note della prenotazione */
                this.addNotaPrenotazione(pan);

                /* larghezza fissa, altezza variabile */
                pan.setPreferredWidth(LARGHEZZA);

                pannello = pan.getPanFisso();
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pannello;
    }


    /**
     * Aggiunge al pannello la eventuale caparra della prenotazione.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param pan pannello della singola prenotazione
     */
    protected void addCaparra(final Pannello pan) {
    }


    /**
     * Aggiunge al pannello tutti i periodi.
     *
     * @param pan pannello della singola prenotazione
     */
    private void addPeriodi(final Pannello pan) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        ArrayList<Integer> periodi = null;
        WrapTextArea area;
        int lar = LARGHEZZA;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (pan != null);

            if (continua) {
                periodi = this.getPeriodi();
                continua = (periodi != null) && (periodi.size() > 0);
            } // fine del blocco if

            if (continua) {
                for (int cod : periodi) {
                    area = this.addPeriodo(cod);
                    area.setWidth(lar);
                    area.setOptimalHeight();
                    pan.add(area);
                } // fine del ciclo for-each
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge al pannello il singolo periodio.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param codPeriodo interessato
     *
     * @return area di testo flessibile
     */
    protected WrapTextArea addPeriodo(final int codPeriodo) {
        return null;
    }


    /**
     * Aggiunge al pannello le eventuali note della prenotazione.
     *
     * @param pan pannello della singola prenotazione
     */
    private void addNotaPrenotazione(final Pannello pan) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        String note;
        WrapTextArea area;
        int lar = LARGHEZZA;

        try { // prova ad eseguire il codice
            note = this.getNotaPrenotazione();
            continua = (Lib.Testo.isValida(note));

            if (continua) {
                area = new WrapTextArea();
                area.setFont(getFontStampa());
                area.setText("Note: " + note);
                area.setWidth(lar);
                area.setOptimalHeight();

                pan.add(area);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Recupera il codice chiave della prenotazione dal wrapper.
     *
     * @return codice chiave della prenotazione
     */
    protected int getPrenotazione() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        PrenotazionePeriodiWrap unWrap;

        try { // prova ad eseguire il codice
            unWrap = this.getWrap();

            if (unWrap != null) {
                codice = unWrap.getPrenotazione();
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Recupera i codici dei periodi dal wrapper.
     *
     * @return codici chiave dei periodi
     */
    private ArrayList<Integer> getPeriodi() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Integer> periodi = null;
        PrenotazionePeriodiWrap unWrap;

        try { // prova ad eseguire il codice
            unWrap = this.getWrap();

            if (unWrap != null) {
                periodi = unWrap.getPeriodi();
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return periodi;
    }


    /**
     * Recupera nome e cognome del cliente che ha effettuato la prenotazione.
     *
     * @return nome e cognome del cliente
     */
    private String getCliente() {
        /* variabili e costanti locali di lavoro */
        String cliente = "";
        boolean continua;
        Modulo modPrenot = null;
        Modulo modCliente = null;
        int codPrenot;
        int codCliente;

        try { // prova ad eseguire il codice
            codPrenot = this.getPrenotazione();
            continua = (codPrenot > 0);

            if (continua) {
                modPrenot = PrenotazioneModulo.get();
                modCliente = ClienteAlbergoModulo.get();
                continua = (modPrenot != null && modCliente != null);
            } // fine del blocco if

            if (continua) {
                codCliente = modPrenot.query().valoreInt(Prenotazione.Cam.cliente.get(), codPrenot);
                cliente = modCliente.query().valoreStringa(Anagrafica.Cam.soggetto.get(),
                        codCliente);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cliente;
    }


    /**
     * Recupera la nota della prenotazione.
     *
     * @return nota della prenotazione
     */
    private String getNotaPrenotazione() {
        /* variabili e costanti locali di lavoro */
        String nota = "";
        boolean continua;
        Modulo modPrenot = null;
        int codPrenot;

        try { // prova ad eseguire il codice
            codPrenot = this.getPrenotazione();
            continua = (codPrenot > 0);

            if (continua) {
                modPrenot = PrenotazioneModulo.get();
                continua = (modPrenot != null);
            } // fine del blocco if

            if (continua) {
                nota = modPrenot.query().valoreStringa(Prenotazione.Cam.note.get(), codPrenot);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nota;
    }

    /**
     * Recupera i nomi delle persone corrispondenti a un elenco di presenze.
     * <p>
     * I nomi sono ordinati prima per adulto/bambino e poi per ordine di partentela
     *
     * @param filtroPresenze filtro per selezionare le presenze
     * @return matrice di nomi delle persone
     */
    protected String[] getPersone(Filtro filtroPresenze) {
        /* variabili e costanti locali di lavoro */
        String[] persone = null;
        Modulo modPresenza;
        Modulo modClienti;
        Modulo modParentela;
        Campo campoRisultato;
        Campo campoOrdineAdulto;
        Campo campoOrdineParentela;
        Ordine ordine;
        Query query;
        Dati dati;

        try { // prova ad eseguire il codice

            modPresenza = PresenzaModulo.get();
            modClienti = ClienteAlbergoModulo.get();
            modParentela = ParentelaModulo.get();
            campoRisultato = modClienti.getCampo(Anagrafica.Cam.soggetto.get());

            /* ordine: prima gli adulti poi per ordine di parentela*/
            campoOrdineAdulto = modPresenza.getCampo(Presenza.Cam.bambino);
            campoOrdineParentela = modParentela.getCampoOrdine();
            ordine = new Ordine();
            ordine.add(campoOrdineAdulto);
            ordine.add(campoOrdineParentela);

            query = new QuerySelezione(modPresenza);
            query.addCampo(campoRisultato);
            query.setFiltro(filtroPresenze);
            query.setOrdine(ordine);

            dati = modPresenza.query().querySelezione(query);
            persone = new String[dati.getRowCount()];
            for (int k = 0; k < dati.getRowCount(); k++) {
                persone[k] = dati.getStringAt(k,campoRisultato);
            } // fine del ciclo for

            dati.close();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return persone;
    }



    /**
     * Font unificato di stampa.
     *
     * @return font
     */
    protected Font getFontStampa() {
        return font;
    }

    /**
     * Font unificato di stampa bold.
     *
     * @return font
     */
    protected Font getFontStampaBold() {
        return fontBold;
    }



    /**
     * metodo getter.
     *
     * @return riferimento al wrapper di prenotazione e periodi
     */
    protected PrenotazionePeriodiWrap getWrap() {
        return wrap;
    }


    /**
     * metodo setter.
     *
     * @param wrapIn riferimento al wrapper di prenotazione e periodi
     */
    protected void setWrap(final PrenotazionePeriodiWrap wrapIn) {
        this.wrap = wrapIn;
    }

}
