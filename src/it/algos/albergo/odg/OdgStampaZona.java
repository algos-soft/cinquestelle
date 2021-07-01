/**
 * Title:     OdgStampaZona
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      27-giu-2009
 */
package it.algos.albergo.odg;

import com.wildcrest.j2printerworks.J2ComponentPrinter;
import com.wildcrest.j2printerworks.J2FlowPrinter;
import com.wildcrest.j2printerworks.VerticalGap;
import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.camera.accessori.Accessori;
import it.algos.albergo.camera.accessori.AccessoriModulo;
import it.algos.albergo.camera.composizione.CompoCamera;
import it.algos.albergo.camera.composizione.CompoCameraModulo;
import it.algos.albergo.camera.zona.Zona;
import it.algos.albergo.camera.zona.ZonaModulo;
import it.algos.albergo.odg.odgaccessori.OdgAcc;
import it.algos.albergo.odg.odgaccessori.OdgAccModulo;
import it.algos.albergo.odg.odgriga.OdgRiga;
import it.algos.albergo.odg.odgriga.OdgRigaModulo;
import it.algos.albergo.odg.odgtesta.Odg;
import it.algos.albergo.odg.odgtesta.OdgModulo;
import it.algos.albergo.odg.odgzona.OdgZona;
import it.algos.albergo.odg.odgzona.OdgZonaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.componente.VerticalLabel;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.font.FontFactory;
import it.algos.base.layout.tablelayout.TableLayout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.wrapper.Campi;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Oggetto J2FlowPrinter che stampa una singola Zona di un Odg
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 27-giu-2009 ore 14.09.24
 */
public final class OdgStampaZona extends J2FlowPrinter {

    /* chiave per la colonna dei titoli di riga (la prima) */
    private static final String KEY_COLONNA_TITOLI_RIGA = "titoli riga";

    /* chiave per la colonna dei totali delle righe accessori (l'ultima) */
    private static final String KEY_COLONNA_TOTALI_RIGA = "totali";

    /* chiave per la riga dei titoli di colonna (la prima) */
    private static final String KEY_RIGA_TITOLI_COLONNA = "titoli colonna";

    /* chiave per la riga di separazione tra le due parti (campi e accessori) */
    private static final String KEY_RIGA_SEP = "riga di separazione";

    /**
     * codice della Riga di Zona di riferimento
     */
    private int codRigaZona;

    /**
     * Mappa ordinata delle colonne
     * <p/>
     * La chiave è il codice camera per le colonne di camera o una stringa
     * per le colonne di altro tipo.
     * Il valore è un wrapper che mantiene il titolo visualizzato e la
     * posizione nella lista stessa.
     */
    private LinkedHashMap<Object, WrapColonna> mappaColonne;

    /**
     * Mappa ordinata delle righe
     * <p/>
     * La chiave è l'oggetto della enum Campi per i campi, o il codice
     * chiave dell'accessorio per le righe degli accessori
     * Il valore è un wrapper che mantiene il titolo visualizzato e la
     * posizione nella lista stessa.
     */
    private LinkedHashMap<Object, WrapRiga> mappaRighe;

    /**
     * Dati temporanei di lavoro
     */
    private Dati dati;

    /**
     * Pannello da stampare, registrato in questo J2ComponentPrinter
     */
    private JPanel pannello;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param codRigaZona codice della riga di zona di riferimento
     */
    public OdgStampaZona(int codRigaZona) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setCodRigaZona(codRigaZona);

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

            /* crea e registra la mappa delle colonne */
            this.creaMappaColonne();

            /* crea e registra la mappa delle righe */
            this.creaMappaRighe();

            /**
             * crea pannello con layout e lo registra
             */
            JPanel panGriglia = new JPanel();
            panGriglia.setOpaque(false);
            panGriglia.setLayout(this.creaLayout());
            this.setPannello(panGriglia);

            /**
             * crea un Component Printer con la griglia
             */
            J2ComponentPrinter compGriglia = new J2ComponentPrinter(panGriglia);
            compGriglia.setHorizontalPageRule(J2ComponentPrinter.BREAK_ON_COMPONENTS);
            compGriglia.setHorizontalAlignment(J2ComponentPrinter.LEFT);
            compGriglia.setVerticalPageRule(J2ComponentPrinter.TILE);

            /**
             * Aggiunge i vari componenti a questo Printer
             */
            this.addFlowable(this.creaPrinterTitolo());  // il titolo
            this.addFlowable(new VerticalGap(0.1));      // un gap fisso
            this.addFlowable(compGriglia);               // la griglia
            if (Lib.Testo.isValida(this.getTestoNote())) {
                this.addFlowable(new VerticalGap(0.2));      // un altro gap
                this.addFlowable(this.creaPrinterNote());    // le note
            }// fine del blocco if

            /* riempie le caselle dei titoli di riga e di colonna */
            this.riempiTitoli();

            /* crea e registra i dati di lavoro */
            this.setDati(this.creaDati());

            /* incasella i dati nella griglia del componente */
            this.incasellaDati();

            /* chiude i dati di lavoro */
            if (this.getDati() != null) {
                this.getDati().close();
            }// fine del blocco if

            /* incasella i totali */
            this.incasellaTotali();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }// fine del metodo inizia


    /**
     * Crea il printer con il titolo della stampa.
     * <p/>
     *
     * @return il printer creato
     */
    private J2ComponentPrinter creaPrinterTitolo() {
        /* variabili e costanti locali di lavoro */
        J2ComponentPrinter printer = null;

        try {    // prova ad eseguire il codice

            int codZona = this.getCodZona();
            String nomeZona = ZonaModulo.get().query().valoreStringa(Zona.Cam.sigla.get(), codZona);
            Date data = OdgModulo.get().query().valoreData(Odg.Cam.data.get(), this.getCodOdg());
            String nomeGiorno = Lib.Data.getGiorno(data);
            nomeGiorno = Lib.Testo.primaMaiuscola(nomeGiorno);
            String strData = nomeGiorno + " " + Lib.Data.getDataEstesa(data);
            String titolo = "Zona: " + nomeZona + " - " + strData;

            JLabel label = new JLabel(titolo);
            label.setFont(FontFactory.creaPrinterFont(Font.BOLD, 20f));
            printer = new J2ComponentPrinter(label);
            printer.setHorizontalAlignment(J2ComponentPrinter.LEFT);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return printer;
    }


    /**
     * Crea il printer con le note generali.
     * <p/>
     *
     * @return il printer creato
     */
    private J2ComponentPrinter creaPrinterNote() {
        /* variabili e costanti locali di lavoro */
        J2ComponentPrinter printer = null;

        try {    // prova ad eseguire il codice

            /* crea una JTextArea con le note */
            JTextArea area = new JTextArea(this.getTestoNote());
            area.setFont(FontFactory.creaPrinterFont(Font.BOLD, 16f));

            /* crea il Printer con l'area */
            printer = new J2ComponentPrinter(area);
            printer.setVerticalPageRule(J2ComponentPrinter.SHRINK_TO_FIT);
            printer.setHorizontalAlignment(J2ComponentPrinter.LEFT);
            printer.setMaximumPaginationGap(0.0);  //comprime un casino prima di saltare pagina

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return printer;
    }


    /**
     * Ritorna il testo delle note.
     * <p/>
     *
     * @return il testo delle note
     */
    private String getTestoNote() {
        /* variabili e costanti locali di lavoro */
        String note = "";

        try {    // prova ad eseguire il codice
            note = OdgZonaModulo.get()
                    .query()
                    .valoreStringa(OdgZona.Cam.note.get(), this.getCodRigaZona());
            if (Lib.Testo.isValida(note)) {
                note = "Note: " + note;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return note;
    }


    /**
     * Crea e registra la mappa delle colonne.
     * <p/>
     */
    private void creaMappaColonne() {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<Object, WrapColonna> mappa;
        Modulo modCamera = CameraModulo.get();

        try {    // prova ad eseguire il codice

            /* crea e registra la mappa */
            mappa = new LinkedHashMap<Object, WrapColonna>();
            this.setMappaColonne(mappa);

            /* aggiunge la prima colonna (titoli di riga) */
            this.addColonna(KEY_COLONNA_TITOLI_RIGA, "", TipiColonna.titoliRiga);

            /* aggiunge tutte le camere della zona dalla tabella camere */
            Ordine ordine = new Ordine(modCamera.getCampo(Camera.Cam.camera));
            Filtro filtro = FiltroFactory.crea(Camera.Cam.linkZona.get(), this.getCodZona());
            QuerySelezione query = new QuerySelezione(modCamera);
            query.setFiltro(filtro);
            query.setOrdine(ordine);
            Campo campoChiave = modCamera.getCampoChiave();
            query.addCampo(campoChiave);
            query.addCampo(Camera.Cam.camera);
            Dati dati = modCamera.query().querySelezione(query);
            for (int k = 0; k < dati.getRowCount(); k++) {
                int cod = dati.getIntAt(k, campoChiave);
                String nome = dati.getStringAt(k, Camera.Cam.camera.get());
                this.addColonna(cod, nome, TipiColonna.camera);
            } // fine del ciclo for
            dati.close();

            /* aggiunge l'ultima colonna (totali accessori) */
            this.addColonna(KEY_COLONNA_TOTALI_RIGA, "Tot", TipiColonna.totali);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea e registra la mappa delle righe.
     * <p/>
     */
    private void creaMappaRighe() {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<Object, WrapRiga> mappa;

        try {    // prova ad eseguire il codice

            /* crea e registra la mappa */
            mappa = new LinkedHashMap<Object, WrapRiga>();
            this.setMappaRighe(mappa);

            /* aggiunge la prima riga (titoli di colonna) */
            this.addRiga(KEY_RIGA_TITOLI_COLONNA, "", TipiRiga.titoliColonna);

            /**
             * aggiunge le righe della parte superiore (i campi)
             * chiave: l'elemento della enum Campi
             * stringa nel wrapper: il titolo del campo
             */
            this.addRigaCampo(OdgRiga.Cam.fermata);
            this.addRigaCampo(OdgRiga.Cam.partenza);
            this.addRigaCampo(OdgRiga.Cam.arrivo);
            this.addRigaCampo(OdgRiga.Cam.cambio);
            this.addRigaCampo(OdgRiga.Cam.cambioDa);
            this.addRigaCampo(OdgRiga.Cam.cambioA);
            this.addRigaCampo(OdgRiga.Cam.parteDomani);
            this.addRigaCampo(OdgRiga.Cam.cambiaDomani);
            this.addRigaCampo(OdgRiga.Cam.chiudere);
            this.addRigaCampo(OdgRiga.Cam.dafare);


            /* aggiunge una riga vuota */
            this.addRiga(KEY_RIGA_SEP, " ", TipiRiga.separazione);

            /* aggiunge la riga Composizione */
            Campi campo = OdgRiga.Cam.composizione;
            String titolo = campo.getEtichettaScheda();
            this.addRiga(campo, titolo, TipiRiga.composizione);

            /**
             * aggiunge le righe degli accessori (dalla tabella)
             * chiave: il codice dell'accessorio
             * stringa nel wrapper: il nome dell'accessorio
             */
            Modulo modAcc = AccessoriModulo.get();
            Ordine ordine = new Ordine(modAcc.getCampoOrdine());
            QuerySelezione query = new QuerySelezione(modAcc);
            query.setOrdine(ordine);
            Campo campoChiave = modAcc.getCampoChiave();
            query.addCampo(campoChiave);
            query.addCampo(Accessori.Cam.descrizione);
            Dati dati = modAcc.query().querySelezione(query);
            for (int k = 0; k < dati.getRowCount(); k++) {
                int cod = dati.getIntAt(k, campoChiave);
                String nome = dati.getStringAt(k, Accessori.Cam.descrizione.get());
                this.addRiga(cod, nome, TipiRiga.accessorio);
            } // fine del ciclo for
            dati.close();

            /* aggiunge una riga vuota */
            this.addRiga("vuota2", " ", TipiRiga.separazione);

            /**
             * Aggiunge la riga delle note
             */
            this.addRiga(OdgRiga.Cam.note, "", TipiRiga.note);


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge una riga di campo standard.
     * <p/>
     * @param campo da aggiungere
     */
    private void addRigaCampo(Campi campo) {
        String titolo = campo.getEtichettaScheda();
        this.addRiga(campo, titolo, TipiRiga.campo);
    }





    /**
     * Aggiunge una colonna alla mappa delle colonne.
     * <p/>
     * Il wrapper con la posizione e la stringa viene costruito internamente.
     *
     * @param key la chiave per la colonna
     * @param stringa valore di testo per il titolo colonna
     * @param tipo tipologia di colonna
     */
    private void addColonna(Object key, String stringa, TipiColonna tipo) {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<Object, WrapColonna> mappa;
        WrapColonna wrapper;
        int pos;
        try {    // prova ad eseguire il codice
            mappa = this.getMappaColonne();
            pos = mappa.size();
            wrapper = new WrapColonna(pos, stringa, tipo);
            mappa.put(key, wrapper);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge una riga alla mappa delle righe.
     * <p/>
     * Il wrapper con la posizione e la stringa viene costruito internamente.
     *
     * @param key la chiave per la riga
     * @param stringa valore di testo per il titolo riga
     * @param tipo tipologia di riga
     */
    private void addRiga(Object key, String stringa, TipiRiga tipo) {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<Object, WrapRiga> mappa;
        WrapRiga wrapper;
        int pos;
        try {    // prova ad eseguire il codice
            mappa = this.getMappaRighe();
            pos = mappa.size();
            wrapper = new WrapRiga(pos, stringa, tipo);
            mappa.put(key, wrapper);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea il layout per il pannello in base alle
     * mappe di righe e colonne.
     * <p/>
     *
     * @return il layout creato
     */
    private TableLayout creaLayout() {
        /* variabili e costanti locali di lavoro */
        TableLayout layout = null;
        ArrayList<Double> lista;
        double[] colsizes;
        double[] rowsizes;

        try {    // prova ad eseguire il codice

            lista = new ArrayList<Double>();
            for (WrapColonna wrapper : this.getMappaColonne().values()) {
                int lar = wrapper.getTipo().getLarghezza();
                lista.add((double)lar);
            }
            colsizes = Lib.Array.toDoubleArray(lista);

            lista = new ArrayList<Double>();
            for (WrapRiga wrapper : this.getMappaRighe().values()) {
                int alt = wrapper.getTipo().getAltezza();
                lista.add((double)alt);
            }
            rowsizes = Lib.Array.toDoubleArray(lista);

            double[][] sizes = {colsizes, rowsizes};
            layout = new TableLayout(sizes);


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return layout;
    }


    /**
     * Crea l'oggetto dati con tutte le righe della zona.
     * <p/>
     *
     * @return l'oggetto Dati creato
     */
    private Dati creaDati() {
        /* variabili e costanti locali di lavoro */
        Dati dati = null;
        Query query;
        Modulo modRighe = OdgRigaModulo.get();

        try {    // prova ad eseguire il codice

            Filtro filtro = FiltroFactory.crea(OdgRiga.Cam.zona.get(), this.getCodRigaZona());
            Campo campo = CameraModulo.get().getCampo(Camera.Cam.camera);
            Ordine ordine = new Ordine(campo);
            query = new QuerySelezione(modRighe);
            query.setFiltro(filtro);
            query.setOrdine(ordine);

            /**
             * aggiunge alla query i campi desiderati:
             * - alcuni campi di controllo
             * - più tutti quelli da visualizzare
             */
            query.addCampo(modRighe.getCampoChiave());
            query.addCampo(OdgRiga.Cam.camera);
            for (Campi cam : this.getCampiRighe()) {
                query.addCampo(cam);
            }
            // trattato a parte perché non è in sequenza ma dopo gli accessori
            query.addCampo(OdgRiga.Cam.note);

            dati = modRighe.query().querySelezione(query);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return dati;
    }


    /**
     * Riempie le caselle dei titoli di riga e di colonna,
     * <p/>
     */
    private void riempiTitoli() {
        this.riempiTitoliColonna();
        this.riempiTitoliRiga();
    }


    /**
     * Riempie le caselle dei titoli di colonna,
     * <p/>
     */
    private void riempiTitoliColonna() {
        /* variabili e costanti locali di lavoro */
        WrapRiga wrapRiga;
        WrapColonna wrapColonna;

        try {    // prova ad eseguire il codice

            /* recupera la posizione della riga dei titoli di colonna */
            wrapRiga = this.getMappaRighe().get(KEY_RIGA_TITOLI_COLONNA);
            int posRowTitoliColonna = wrapRiga.getPosizione();

            /* recupera la posizione della colonna dei titoli di riga */
            wrapColonna = this.getMappaColonne().get(KEY_COLONNA_TITOLI_RIGA);
            int posColTitoliRiga = wrapColonna.getPosizione();

            /**
             * spazzola la mappa dei titoli colonna, crea i componenti
             * e li aggiunge in posizione
             */
            LinkedHashMap<Object, WrapColonna> mappa = this.getMappaColonne();
            for(Object colKey : mappa.keySet()){
                WrapColonna wrapCol = mappa.get(colKey);
                if (wrapCol!=null) {
                    int posCol = wrapCol.getPosizione();
                    // se è la colonna di titoli di riga non crea nulla
                    if (posCol!= posColTitoliRiga) {
                        String nome = wrapCol.getTesto();
                        this.addCellaCampo(colKey, KEY_RIGA_TITOLI_COLONNA, TipoDati.titolocolonna, nome);
                    }
                }// fine del blocco if
            }



        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Riempie le caselle dei titoli di riga,
     * <p/>
     */
    private void riempiTitoliRiga() {
        /* variabili e costanti locali di lavoro */
        try {    // prova ad eseguire il codice

            /* recupera l'indice della colonna dei titoli */
            WrapColonna wrapper = this.getMappaColonne().get(KEY_COLONNA_TITOLI_RIGA);
            int posCol = wrapper.getPosizione();

            /**
             * spazzola la mappa dei titoli riga, crea i componenti
             * e li aggiunge in posizione
             */
            JPanel pan = this.getPannello();
            LinkedHashMap<Object, WrapRiga> mappa = this.getMappaRighe();
            for (WrapRiga wrapRow : mappa.values()) {
                int posRow = wrapRow.getPosizione();
                String titolo = wrapRow.getTesto();
                String constraint = this.getConstraint(posCol, posRow);
                JLabel label = new JLabel(titolo);

                /* font a seconda del tipo di riga */
                TipiRiga tipo = wrapRow.getTipo();
                Font font;
                float size = 16;
                switch (tipo) {
                    case accessorio:
                        font = FontFactory.creaPrinterFont(Font.PLAIN,size);
                        break;
                    default : // ogni altro caso
                        font = FontFactory.creaPrinterFont(Font.BOLD,size);
                        break;
                } // fine del blocco switch

                label.setFont(font);
                pan.add(label, constraint);
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Spazzola i dati, crea i relativi componenti da visualizzare,
     * e li incasella nella griglia del componente.
     * <p/>
     */
    private void incasellaDati() {
        /* variabili e costanti locali di lavoro */
        Campi campo;
        Object valore;
        int numero;
        String stringa;
        Modulo modCamera = CameraModulo.get();
        Modulo modCompo = CompoCameraModulo.get();

        try {    // prova ad eseguire il codice

            Dati dati = this.getDati();
            for (int k = 0; k < dati.getRowCount(); k++) {

                campo = OdgRiga.Cam.camera;
                int codCamera = dati.getIntAt(k, campo.get());

                campo = OdgRiga.Cam.fermata;
                valore = dati.getValueAt(k, campo.get());
                this.addCellaCampo(codCamera, campo, TipoDati.booleano, valore);
                // se true incrementa totale riga
                if (Libreria.getBool(valore)) {
                    this.getMappaRighe().get(campo).addQuantita(1);
                }// fine del blocco if

                campo = OdgRiga.Cam.arrivo;
                valore = dati.getValueAt(k, campo.get());
                this.addCellaCampo(codCamera, campo, TipoDati.booleano, valore);

                campo = OdgRiga.Cam.partenza;
                valore = dati.getValueAt(k, campo.get());
                this.addCellaCampo(codCamera, campo, TipoDati.booleano, valore);

                campo = OdgRiga.Cam.cambio;
                valore = dati.getValueAt(k, campo.get());
                this.addCellaCampo(codCamera, campo, TipoDati.booleano, valore);

                campo = OdgRiga.Cam.cambioDa;
                numero = dati.getIntAt(k, campo.get());   // codice della camera
                stringa = modCamera.query().valoreStringa(Camera.Cam.camera.get(), numero);
                this.addCellaCampo(codCamera, campo, TipoDati.testo, stringa);

                campo = OdgRiga.Cam.cambioA;
                numero = dati.getIntAt(k, campo.get());   // codice della camera
                stringa = modCamera.query().valoreStringa(Camera.Cam.camera.get(), numero);
                this.addCellaCampo(codCamera, campo, TipoDati.testo, stringa);

                campo = OdgRiga.Cam.parteDomani;
                valore = dati.getValueAt(k, campo.get());
                this.addCellaCampo(codCamera, campo, TipoDati.booleano, valore);

                campo = OdgRiga.Cam.cambiaDomani;
                valore = dati.getValueAt(k, campo.get());
                this.addCellaCampo(codCamera, campo, TipoDati.booleano, valore);

                campo = OdgRiga.Cam.chiudere;
                valore = dati.getValueAt(k, campo.get());
                this.addCellaCampo(codCamera, campo, TipoDati.booleano, valore);

                campo = OdgRiga.Cam.dafare;
                valore = dati.getValueAt(k, campo.get());
                this.addCellaCampo(codCamera, campo, TipoDati.booleano, valore);
                // se true incrementa totale riga
                if (Libreria.getBool(valore)) {
                    this.getMappaRighe().get(campo).addQuantita(1);
                }// fine del blocco if

                campo = OdgRiga.Cam.composizione;
                numero = dati.getIntAt(k, campo.get());   // codice della composizione
                stringa = modCompo.query().valoreStringa(CompoCamera.Cam.sigla.get(), numero);
                this.addCellaCampo(codCamera, campo, TipoDati.composizione, stringa);

                /* incasella le note verticali*/
                campo = OdgRiga.Cam.note;
                valore = dati.getValueAt(k, campo.get());
                this.addCellaCampo(codCamera, campo, TipoDati.testoVert, valore);

                /* incasella gli accessori */
                int codRiga = dati.getIntAt(k, OdgRigaModulo.get().getCampoChiave());
                this.incasellaAccessori(codRiga);


            } // fine del ciclo for

            
            /**
             * Aggiunge la cella "Tot" alla riga della composizione
             */
            this.addCellaCampo(KEY_COLONNA_TOTALI_RIGA, OdgRiga.Cam.composizione, TipoDati.titolocolonna, "Tot");


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Incasella nella griglia gli accessori di una data riga.
     * <p/>
     *
     * @param codRiga codice della riga di Odg
     */
    private void incasellaAccessori(int codRiga) {
        /* variabili e costanti locali di lavoro */
        Dati dati;
        Query query;
        Filtro filtro;
        Modulo modOdgAcc = OdgAccModulo.get();
        Modulo modAcc = AccessoriModulo.get();

        try {    // prova ad eseguire il codice

            /* recupera il codice della camera */
            int codCamera = OdgRigaModulo.get()
                    .query()
                    .valoreInt(OdgRiga.Cam.camera.get(), codRiga);

            /**
             * Crea una mappa con gli accessori usati dalla riga
             * - la chiave è il codice dell'accessorio
             * - il valore è la quantità
             */
            HashMap<Integer, Integer> mappaAcc = new HashMap<Integer, Integer>();
            filtro = FiltroFactory.crea(OdgAcc.Cam.rigaOdg.get(), codRiga);
            query = new QuerySelezione(modOdgAcc);
            query.setFiltro(filtro);
            query.addCampo(OdgAcc.Cam.accessorio);
            query.addCampo(OdgAcc.Cam.quantita);
            dati = modOdgAcc.query().querySelezione(query);

            for (int k = 0; k < dati.getRowCount(); k++) {
                int codAccessorio = dati.getIntAt(k, OdgAcc.Cam.accessorio.get());
                int quantita = dati.getIntAt(k, OdgAcc.Cam.quantita.get());
                mappaAcc.put(codAccessorio, quantita);
            } // fine del ciclo for
            dati.close();

            /**
             * Spazzola tutti gli accessori in tabella
             * Per ognuno vede se è nella mappa di quelli usati
             * Se lo è crea una cella con la quantità
             * Se non lo è crea una cella vuota
             */
            int[] codiciAcc = modAcc.query().valoriChiave();
            for (int codAcc : codiciAcc) {
                Object ogg = mappaAcc.get(codAcc);
                if (ogg != null) {
                    int qta = Libreria.getInt(ogg);
                    this.addCellaCampo(codCamera, codAcc, TipoDati.numero, qta);
                    this.getMappaRighe()
                            .get(codAcc)
                            .addQuantita(qta);  // aggiorna il totale nella riga
                } else {
                    this.addCellaCampo(codCamera, codAcc, TipoDati.testo, "");
                }// fine del blocco if-else
            }


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Incasella nella griglia i totali.
     * <p/>
     */
    private void incasellaTotali() {
        try { // prova ad eseguire il codice

            LinkedHashMap<Object, WrapRiga> mappaRighe = this.getMappaRighe();
            for (Object rowKey : mappaRighe.keySet()) {
                WrapRiga wrapRow = mappaRighe.get(rowKey);

                TipiRiga tipo;
                tipo = wrapRow.getTipo();

                boolean creaCella=false;
                switch (tipo) {
                    case accessorio:
                        creaCella=true;
                        break;
                    case campo:
                        creaCella=true;
                        break;
                    default : // caso non definito
                        break;
                } // fine del blocco switch

                if (creaCella) {
                    int qta = wrapRow.getQuantita();
                    if (qta > 0) {
                        this.addCellaCampo(KEY_COLONNA_TOTALI_RIGA, rowKey, TipoDati.totale, qta);
                    } else {
                        this.addCellaCampo(KEY_COLONNA_TOTALI_RIGA, rowKey, TipoDati.testo, "");
                    }// fine del blocco if-else
                }// fine del blocco if

            }
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea una cella con un valore e la colloca al proprio posto nella griglia.
     * <p/>
     *
     * @param colKey chiave per recuperare la colonna nella mappa
     * @param rowKey chiave per recuperare la riga nella mappa
     * @param tipoDati del valore recuperato
     * @param valore da rappresentare
     */
    private void addCellaCampo(Object colKey, Object rowKey, TipoDati tipoDati, Object valore) {
        /* variabili e costanti locali di lavoro */
        JComponent comp = null;
        WrapColonna wrapCol;
        WrapRiga wrapRow;
        int posCol;
        int posRow;
        String constraint;

        try {    // prova ad eseguire il codice

            switch (tipoDati) {
                case booleano:
                    comp = new CompCellaBool((Boolean)valore);
                    break;
                case numero:
                    comp = new CompCellaString(valore.toString());
                    break;
                case testo:
                    comp = new CompCellaString((String)valore);
                    break;
                case testoVert:
                    comp = new CompCellaVert((String)valore);
                    break;
                case composizione:
                    comp = new CompCellaCompo((String)valore);
                    break;
                case titolocolonna:
                    comp = new CompCellaTitColonna((String)valore);
                    break;
                case totale:
                    comp = new CompCellaTotale(valore.toString());
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

            wrapCol = this.getMappaColonne().get(colKey);
            if (wrapCol != null) {
                posCol = wrapCol.getPosizione();
                wrapRow = this.getMappaRighe().get(rowKey);
                if (wrapRow != null) {
                    posRow = wrapRow.getPosizione();
                    constraint = this.getConstraint(posCol, posRow);
                    this.getPannello().add(comp, constraint);
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    private enum TipoDati {

        testo, booleano, numero, testoVert, composizione, titolocolonna, totale
    }


    /**
     * Ritorna una constraint per il TableLayout
     * dati gli indici di colonna e riga.
     * <p/>
     *
     * @param col indice della colonna
     * @param row indice della riga
     *
     * @return la stringa di constraint
     */
    private String getConstraint(int col, int row) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";

        try {    // prova ad eseguire il codice
            stringa = "" + col + ", " + row;
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna il codice della zona alla quale la
     * corrente Riga di Zona si riferisce.
     * <p/>
     *
     * @return il codice della Zona
     */
    private int getCodZona() {
        return OdgZonaModulo.get().query().valoreInt(OdgZona.Cam.zona.get(), this.getCodRigaZona());
    }


    /**
     * Ritorna il codice dell'Odg al quale la
     * corrente Riga di Zona si riferisce.
     * <p/>
     *
     * @return il codice dell'Odg
     */
    private int getCodOdg() {
        return OdgZonaModulo.get().query().valoreInt(OdgZona.Cam.odg.get(), this.getCodRigaZona());
    }


    /**
     * Ritorna i campi corrispondenti alle righe da stampare.
     * <p/>
     * Definisce anche l'ordine di presentazione delle righe.
     *
     * @return l'elenco dei campi desiderati nelle righe
     */
    private Campi[] getCampiRighe() {
        /* variabili e costanti locali di lavoro */
        Campi[] array = new Campi[0];
        ArrayList<Campi> lista = new ArrayList<Campi>();

        try {    // prova ad eseguire il codice
            lista.add(OdgRiga.Cam.fermata);
            lista.add(OdgRiga.Cam.arrivo);
            lista.add(OdgRiga.Cam.partenza);
            lista.add(OdgRiga.Cam.cambio);
            lista.add(OdgRiga.Cam.cambioDa);
            lista.add(OdgRiga.Cam.cambioA);
            lista.add(OdgRiga.Cam.parteDomani);
            lista.add(OdgRiga.Cam.cambiaDomani);
            lista.add(OdgRiga.Cam.chiudere);
            lista.add(OdgRiga.Cam.dafare);
            lista.add(OdgRiga.Cam.composizione);

            array = new Campi[lista.size()];
            for (int i = 0; i < lista.size(); i++) {
                array[i] = lista.get(i);
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return array;
    }


    private int getCodRigaZona() {
        return codRigaZona;
    }


    private void setCodRigaZona(int codRigaZona) {
        this.codRigaZona = codRigaZona;
    }


    private LinkedHashMap<Object, WrapColonna> getMappaColonne() {
        return mappaColonne;
    }


    private void setMappaColonne(LinkedHashMap<Object, WrapColonna> mappaColonne) {
        this.mappaColonne = mappaColonne;
    }


    private LinkedHashMap<Object, WrapRiga> getMappaRighe() {
        return mappaRighe;
    }


    private void setMappaRighe(LinkedHashMap<Object, WrapRiga> mappaRighe) {
        this.mappaRighe = mappaRighe;
    }


    private Dati getDati() {
        return dati;
    }


    private void setDati(Dati dati) {
        this.dati = dati;
    }


    private JPanel getPannello() {
        return pannello;
    }


    private void setPannello(JPanel pannello) {
        this.pannello = pannello;
    }


    /**
     * Componente per rappresentare una cella di titolo colonna
     * <p/>
     */
    private class CompCellaTitColonna extends JLabel {

        /**
         * Crea una label per il titolo di colonna
         * <p/>
         *
         * @param titolo titolo da visualizzare
         */
        public CompCellaTitColonna(String titolo) {
            /* rimanda al costruttore della superclasse */
            super();

            this.setText(titolo);
            this.setOpaque(true);
            this.setBackground(new Color(40,40,40));
            this.setForeground(Color.white);
            this.setHorizontalAlignment(SwingConstants.CENTER);
            this.setBorder(BorderFactory.createLineBorder(Color.white));
            this.setFont(FontFactory.creaPrinterFont(Font.BOLD, 16f));


        }// fine del metodo costruttore completo

    } // fine della classe 'interna'

    /**
     * Componente per rappresentare una cella di testo standard
     * <p/>
     */
    private class CompCellaString extends JLabel {

        /**
         * Crea una label per il titolo di colonna
         * <p/>
         *
         * @param titolo titolo da visualizzare
         */
        public CompCellaString(String titolo) {
            /* rimanda al costruttore della superclasse */
            super();

            this.setText(titolo);
            this.setFont(FontFactory.creaPrinterFont(Font.BOLD, 16f));

            this.setOpaque(false);
            this.setHorizontalAlignment(SwingConstants.CENTER);
            this.setBorder(BorderFactory.createLineBorder(Color.black));

        }// fine del metodo costruttore completo

    } // fine della classe 'interna'


    /**
     * Componente per rappresentare una cella dei totali
     * <p/>
     */
    private class CompCellaTotale extends JLabel {

        /**
         * Crea una label per il titolo di colonna
         * <p/>
         *
         * @param testo da visualizzare
         */
        public CompCellaTotale(String testo) {
            /* rimanda al costruttore della superclasse */
            super();

            this.setText(testo);
            this.setFont(FontFactory.creaPrinterFont(Font.BOLD, 16f));
            this.setOpaque(true);
            this.setBackground(new Color(40,40,40));
            this.setForeground(Color.white);
            this.setHorizontalAlignment(SwingConstants.CENTER);
            this.setBorder(BorderFactory.createLineBorder(Color.white));


        }// fine del metodo costruttore completo

    } // fine della classe 'interna'


//    /**
//     * Componente per rappresentare una cella di Composizione Camera
//     * <p/>
//     */
//    private class CompCellaCompo extends JLabel {
//
//        /**
//         * Crea una label per il titolo di colonna
//         * <p/>
//         *
//         * @param titolo titolo da visualizzare
//         */
//        public CompCellaCompo(String titolo) {
//            /* rimanda al costruttore della superclasse */
//            super();
//
//            this.setText(titolo);
//            this.setOpaque(true);
//            this.setBackground(new Color(40,40,40));
//            this.setForeground(Color.white);
//            this.setFont(FontFactory.creaPrinterFont(Font.BOLD, 11f));
//            this.setHorizontalAlignment(SwingConstants.CENTER);
//            this.setBorder(BorderFactory.createLineBorder(Color.white));
//
//        }// fine del metodo costruttore completo
//
//    } // fine della classe 'interna'

    /**
     * Componente per rappresentare una cella di Composizione Camera
     * <p/>
     */
    private class CompCellaCompo extends VerticalLabel {

        /**
         * Crea una label per il titolo di colonna
         * <p/>
         *
         * @param titolo titolo da visualizzare
         */
        public CompCellaCompo(String titolo) {
            /* rimanda al costruttore della superclasse */
            super(false);

            this.setText(titolo);
            this.setOpaque(true);
            this.setBackground(new Color(40,40,40));
            this.setForeground(Color.white);
            this.setFont(FontFactory.creaPrinterFont(Font.BOLD, 14f));
            this.setHorizontalAlignment(SwingConstants.CENTER);
            this.setBorder(BorderFactory.createLineBorder(Color.white));

        }// fine del metodo costruttore completo

    } // fine della classe 'interna'


    /**
     * Componente per rappresentare una cella di testo verticale
     * <p/>
     */
    private class CompCellaVert extends VerticalLabel {

        /**
         * Crea una label per il titolo di colonna
         * <p/>
         *
         * @param titolo titolo da visualizzare
         */
        public CompCellaVert(String titolo) {
            /* rimanda al costruttore della superclasse */
            super(true);

            this.setText(titolo);
            this.setFont(FontFactory.creaPrinterFont(Font.PLAIN, 17f));            
            this.setOpaque(false);
            this.setHorizontalAlignment(SwingConstants.LEADING);

            /**
             * Patch per bug su XP/Java5 al 06-2009
             * devo aumentare l'altezza del componente verticale
             * se non il testo viene tagliato con i puntini in fondo
             */
            int w = this.getPreferredSize().width;
            int h = this.getPreferredSize().height;
            Dimension dim =new Dimension(w, h+30);
            this.setPreferredSize(dim);
            
        }// fine del metodo costruttore completo

    } // fine della classe 'interna'




    /**
     * Componente per rappresentare una cella booleana
     * accesa o spenta.
     * <p/>
     * L'indicatore è un quadratino nero centrato.
     */
    private class CompCellaBool extends JPanel {

        /**
         * Crea un pannello con un indicatore booleano
         * <p/>
         *
         * @param flag di accensione
         */
        public CompCellaBool(boolean flag) {
            /* rimanda al costruttore della superclasse */
            super();

            this.setBorder(BorderFactory.createLineBorder(Color.black));
            this.setOpaque(false);
            if (flag) {
                this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
                JPanel panIn = new JPanel();
                panIn.setOpaque(true);
                panIn.setBackground(Color.black);
                panIn.setPreferredSize(new Dimension(8, 8));
                Lib.Comp.bloccaDim(panIn);
                this.add(Box.createVerticalGlue());
                this.add(panIn);
                this.add(Box.createVerticalGlue());
            }// fine del blocco if
        }// fine del metodo costruttore completo

    } // fine della classe 'interna'

    /**
     * Wrapper per le informazioni di una colonna della mappa.
     * </p>
     */
    private class WrapColonna {

        private int posizione;

        private String testo;

        private TipiColonna tipo;


        /**
         * Costruttore completo con parametri. <br>
         *
         * @param posizione posizione nella mappa
         * @param testo da visualizzare nel titolo
         * @param tipo tipologia di colonna
         */
        public WrapColonna(int posizione, String testo, TipiColonna tipo) {
            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili di istanza coi parametri */
            this.posizione = posizione;
            this.testo = testo;
            this.tipo = tipo;
        }// fine del metodo costruttore completo


        public int getPosizione() {
            return posizione;
        }


        public String getTesto() {
            return testo;
        }


        public TipiColonna getTipo() {
            return tipo;
        }

    } // fine della classe 'interna'

    /**
     * Wrapper per le informazioni di una riga della mappa.
     * </p>
     */
    private class WrapRiga {

        private int posizione;

        private String testo;

        private TipiRiga tipo;

        /**
         * usato per le righe di tipo accessorio per
         * accumulare le quantità e fare i totali
         */
        private int quantita = 0;


        /**
         * Costruttore completo con parametri. <br>
         *
         * @param posizione posizione nella mappa
         * @param testo da visualizzare nel titolo della riga
         * @param tipo tipologia di riga
         */
        public WrapRiga(int posizione, String testo, TipiRiga tipo) {
            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili di istanza coi parametri */
            this.posizione = posizione;
            this.testo = testo;
            this.tipo = tipo;
        }// fine del metodo costruttore completo


        /**
         * Aggiunge una quantità alla riga
         * <p/>
         *
         * @param quantita la quantità da aggiungere
         */
        public void addQuantita(int quantita) {
            this.quantita = this.quantita + quantita;
        }


        public int getPosizione() {
            return posizione;
        }


        public String getTesto() {
            return testo;
        }


        public TipiRiga getTipo() {
            return tipo;
        }


        public int getQuantita() {
            return quantita;
        }

    } // fine della classe 'interna'

    /**
     * Codifica dei tipi di colonna della stampa
     * <p/>
     */
    private enum TipiColonna {

        titoliRiga(150),   // colonna contenente i titoli di riga
        camera(40),   // colonna contenente una camera
        totali(50);    // colonna contenente i totali

        /**
         * larghezza del tipo di colonna.
         */
        private int larghezza;


        /**
         * Costruttore completo con parametri.
         *
         * @param larghezza larghezza del tipo di colonna
         */
        TipiColonna(int larghezza) {
            this.larghezza = larghezza;
        }


        public int getLarghezza() {
            return larghezza;
        }

    }

    /**
     * Codifica dei tipi di riga della stampa
     * <p/>
     */
    private enum TipiRiga {

        titoliColonna(26),   // riga contenete i titoli di colonna
        campo(22),   // riga contenente le informazioni di campo
        note((int)TableLayout.PREFERRED),   // riga contenente le note verticali
        accessorio(22),   // riga contenente le informazioni su un accessorio
        separazione(10),    // riga di separazione
        composizione(100);    // riga della composizione


        /**
         * altezza del tipo di riga.
         */
        private int altezza;


        /**
         * Costruttore completo con parametri.
         *
         * @param altezza altezza del tipo di riga
         */
        TipiRiga(int altezza) {
            this.altezza = altezza;
        }


        public int getAltezza() {
            return altezza;
        }
    }


}// fine della classe