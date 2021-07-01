package it.algos.albergo.stampeobbligatorie.notifica;

import com.wildcrest.j2printerworks.J2PanelPrinter;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.clientealbergo.indirizzoalbergo.IndirizzoAlbergo;
import it.algos.albergo.clientealbergo.indirizzoalbergo.IndirizzoAlbergoModulo;
import it.algos.albergo.clientealbergo.tabelle.autorita.Autorita;
import it.algos.albergo.clientealbergo.tabelle.autorita.AutoritaModulo;
import it.algos.albergo.clientealbergo.tabelle.tipodocumento.TipoDocumento;
import it.algos.albergo.clientealbergo.tabelle.tipodocumento.TipoDocumentoModulo;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampe;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampeModulo;
import it.algos.albergo.tabelle.azienda.Azienda;
import it.algos.albergo.tabelle.azienda.AziendaModulo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.font.FontFactory;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.indirizzo.tabelle.citta.Citta;
import it.algos.gestione.indirizzo.tabelle.citta.CittaModulo;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.util.Date;
import java.util.Vector;

/**
 * Modello dati di una Scheda di notifica.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 23-mag-2008 ore 16.21.46
 */
public class SchedaNotifica extends J2PanelPrinter {

    private final static float DIM_TITOLO = 12;

    private final static float DIM_NORMALE = 10;

    private final static float DIM_TABELLA = 10;

    private final static float DIM_PICCOLO = 9;

    private final static int GAP_DUE = 30;

    /**
     * codice del record di notifica
     */
    private int codNotifica;

    /**
     * connessione da utilizzare per le query
     */
    private Connessione conn;


    /**
     * Costruttore completo senza parametri.
     * <p/>
     *
     * @param codNotifica codice del record di notifica
     * @param conn        connessione da utilizzare
     */
    public SchedaNotifica(int codNotifica, Connessione conn) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice

            this.setCodNotifica(codNotifica);
            this.setConnessione(conn);

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni iniziali di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal
     * costruttore (init) <br>
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
    }// fine del metodo inizia


    /**
     * Pannello principale.
     *
     * @return pannello grafico da inserire nello stampabile
     */
    private JPanel creaPan() {
        /* variabili e costanti locali di lavoro */
        JPanel pannello = null;
        Pannello pan;

        try { // prova ad eseguire il codice

            pan = PannelloFactory.verticale(null);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(5);

            /* intestazione albergo */
            this.addIntestazione(pan);

            /* titolo scheda */
            this.addTitolo(pan);

            /* informazioni sul capogruppo */
            this.addCapoGruppo(pan);

            /* altri membri del gruppo (se esistono) */
            if (NotificaLogica.isEsistonoMembri(codNotifica)) {
                /* titolo degli altri membri del gruppo */
                this.addTitoloMembri(pan);

                /* informazioni sugli altri membri del gruppo */
                this.addMembriGruppo(pan);
            }// fine del blocco if

            /* testo dell'autorizzazione */
            this.addAutorizzazione(pan);

            /* testo della firma */
            this.addFirma(pan);

            /* testo della consegna */
            this.addConsegna(pan);

            /* testo del bollo */
            this.addBollo(pan);

            pannello = pan.getPanFisso();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pannello;
    }




    /**
     * Aggiunge al pannello l'intestazione della pagina.
     *
     * @param pan pannello della scheda di notifica
     */
    private void addIntestazione(final Pannello pan) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        JTextArea area;
        Modulo modAzienda;
        EstrattoBase estratto;
        String testo = "";

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(pan));

            /* riga vuota di separazione */
            if (continua) {
                pan.add(this.getGap(20));
            } // fine del blocco if

            if (continua) {
                modAzienda = AziendaModulo.get();
                estratto = modAzienda.getEstratto(Azienda.Est.intestazione, this.getCodAzienda());
                testo = estratto.getStringa();
            } // fine del blocco if

            if (continua) {

                area = new JTextArea(testo);
                area.setAlignmentX(SwingConstants.CENTER);
                Lib.Comp.sbloccaLarMax(area);
                area.setFont(FontFactory.creaPrinterFont(DIM_TITOLO));
                pan.add(area);

            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge al pannello il titolo della scheda.
     *
     * @param pan pannello della scheda di notifica
     */
    private void addTitolo(final Pannello pan) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        JLabel label;
        String testo = "";
        String sep = "/";

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(pan));

            /* riga vuota di separazione */
            if (continua) {
                pan.add(this.getGap(60));
            } // fine del blocco if

            if (continua) {
                testo = "SCHEDA DI NOTIFICA N° ";
                testo += getProgressivo();
                testo += sep;
                testo += Lib.Data.getAnnoCorrente();
            } // fine del blocco if

            if (continua) {
                label = new JLabel(testo);
                label.setFont(FontFactory.creaPrinterFont(DIM_NORMALE));
                pan.add(label);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge al pannello le informazioni sul capogruppo.
     *
     * @param pan pannello della scheda di notifica
     */
    private void addCapoGruppo(final Pannello pan) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        String tagArrivo = "Data di arrivo: ";
        String tagNome = "Cognome e nome: ";
        String tagNato = "Nato a: ";
        String tagCittadinanza = "Cittadinanza: ";
        String tagResidenza = "Residente in: ";
        String tagTipoDoc = "Documento tipo: ";
        String tagDataDoc = "Rilasciato il: ";
        String nome = "";
        String cognome = "";
        int linkCitta;
        int linkIndi;
        int linkTipoDoc;
        int linkAutDoc;
        String nato = "";
        Date nascita = null;
        String cittadinanza = "";
        String residente = "";
        String tipoDoc = "";
        String numDoc = "";
        Date dataDoc = null;
        String autoritaDoc = "";
        int codCliente = 0;
        Modulo mod = null;
        Modulo modCitta = null;
        Modulo modIndi = null;
        Modulo modTipoDoc = null;
        Modulo modAutDoc = null;
        EstrattoBase estIndi;
        JTable tavola = null;
        DefaultTableModel modello = null;
        Vector<String> titoli;
        TableColumnModel modelloColonne;
        int numColonne = 2;
        Vector<String> riga;
        Connessione conn = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(pan));

            if (continua) {
                conn = this.getConnessione();
            }// fine del blocco if


            if (continua) {
                tavola = new JTable();
                tavola.setAutoCreateColumnsFromModel(true);
                tavola.setFont(FontFactory.creaPrinterFont(DIM_TABELLA));
            } // fine del blocco if

            if (continua) {
                modello = new DefaultTableModel();
                modello.setColumnCount(numColonne);
                titoli = new Vector<String>();
                titoli.add("");
                titoli.add("");
                modello.addColumn(titoli);
            } // fine del blocco if

            if (continua) {
                tavola.setModel(modello);
                modelloColonne = tavola.getColumnModel();
                this.fixColonna(modelloColonne, 0, 120);
                this.fixColonna(modelloColonne, 1, 400);
            } // fine del blocco if

            if (continua) {
                mod = ClienteAlbergoModulo.get();
                continua = (mod != null);
            } // fine del blocco if

            if (continua) {
                modCitta = CittaModulo.get();
                continua = (modCitta != null);
            } // fine del blocco if

            if (continua) {
                modIndi = IndirizzoAlbergoModulo.get();
                continua = (modIndi != null);
            } // fine del blocco if

            if (continua) {
                modTipoDoc = TipoDocumentoModulo.get();
                continua = (modTipoDoc != null);
            } // fine del blocco if

            if (continua) {
                modAutDoc = AutoritaModulo.get();
                continua = (modAutDoc != null);
            } // fine del blocco if

            if (continua) {
                codCliente = this.getCliente();
                continua = (codCliente > 0);
            } // fine del blocco if

            if (continua) {
                nome = mod.query().valoreStringa(Anagrafica.Cam.nome.get(), codCliente, conn);
                cognome = mod.query().valoreStringa(Anagrafica.Cam.cognome.get(), codCliente, conn);

                linkCitta = mod.query().valoreInt(ClienteAlbergo.Cam.luogoNato.get(), codCliente, conn);
                nato = modCitta.query().valoreStringa(Citta.Cam.citta.get(), linkCitta, conn);

                nascita = mod.query().valoreData(ClienteAlbergo.Cam.dataNato.get(), codCliente, conn);


                linkIndi = mod.query().valoreInt(ClienteAlbergo.Cam.indirizzoInterno.get(),
                        codCliente, conn);
                estIndi = modIndi.getEstratto(IndirizzoAlbergo.Est.indirizzoNotifica, linkIndi, conn);
                if (estIndi != null) {
                    residente = estIndi.getStringa();
                } // fine del blocco if
                cittadinanza = modIndi.getEstStr(IndirizzoAlbergo.Est.cittadinanza, linkIndi, conn);

                linkTipoDoc = mod.query().valoreInt(ClienteAlbergo.Cam.tipoDoc.get(), codCliente, conn);
                tipoDoc = modTipoDoc.query().valoreStringa(TipoDocumento.Cam.descrizione.get(),
                        linkTipoDoc, conn);

                numDoc = mod.query().valoreStringa(ClienteAlbergo.Cam.numDoc.get(), codCliente, conn);
                dataDoc = mod.query().valoreData(ClienteAlbergo.Cam.dataDoc.get(), codCliente, conn);

                linkAutDoc = mod.query().valoreInt(ClienteAlbergo.Cam.autoritaDoc.get(),
                        codCliente, conn);
                autoritaDoc = modAutDoc.query().valoreStringa(Autorita.Cam.descrizione.get(),
                        linkAutDoc, conn);
            } // fine del blocco if

            if (continua) {
                riga = new Vector<String>();
                riga.add(tagArrivo);
                riga.add(getData());
                modello.addRow(riga);
            } // fine del blocco if

            if (continua) {
                riga = new Vector<String>();
                riga.add(tagNome);
                riga.add(cognome + " " + nome);
                modello.addRow(riga);
            } // fine del blocco if

            if (continua) {
                riga = new Vector<String>();
                riga.add(tagNato);
                riga.add(nato + " il " + Lib.Data.getDataBreve(nascita));
                modello.addRow(riga);
            } // fine del blocco if

            if (continua) {
                riga = new Vector<String>();
                riga.add(tagCittadinanza);
                riga.add(cittadinanza);
                modello.addRow(riga);
            } // fine del blocco if

            if (continua) {
                riga = new Vector<String>();
                riga.add(tagResidenza);
                riga.add(residente);
                modello.addRow(riga);
            } // fine del blocco if

            if (continua) {
                riga = new Vector<String>();
                riga.add(tagTipoDoc);
                riga.add(tipoDoc + " n. " + numDoc);
                modello.addRow(riga);
            } // fine del blocco if

            if (continua) {
                riga = new Vector<String>();
                riga.add(tagDataDoc);
                riga.add(Lib.Data.getDataBreve(dataDoc) + " da " + autoritaDoc);
                modello.addRow(riga);
            } // fine del blocco if

            if (continua) {
                pan.add(tavola);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge al pannello il titolo dei membri.
     *
     * @param pan pannello della scheda di notifica
     */
    private void addTitoloMembri(final Pannello pan) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        JLabel label;
        String testo;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(pan));

            /* riga vuota di separazione */
            if (continua) {
                pan.add(this.getGap(60));
            } // fine del blocco if

            if (continua) {
                testo = "ALTRI COMPONENTI DEL NUCLEO FAMILIARE";
                label = new JLabel(testo);
                label.setFont(FontFactory.creaPrinterFont(DIM_NORMALE));
                pan.add(label);
            } // fine del blocco if

            if (continua) {
                testo = "(cognome e nome, luogo e data di nascita)";
                label = new JLabel(testo);
                label.setFont(FontFactory.creaPrinterFont(DIM_PICCOLO));
                pan.add(label);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge al pannello le informazioni sui membri del gruppo.
     *
     * @param pan pannello della scheda di notifica
     */
    private void addMembriGruppo(final Pannello pan) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int codNotifica = 0;
        int[] codici = null;
        JTable tavola = null;
        DefaultTableModel modello = null;
        Vector<String> titoli;
        TableColumnModel modelloColonne;
        int numColonne = 4;
        int k = 0;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(pan));

            if (continua) {
                tavola = new JTable();
                tavola.setAutoCreateColumnsFromModel(true);
                tavola.setFont(FontFactory.creaPrinterFont(DIM_TABELLA));
            } // fine del blocco if

            if (continua) {
                modello = new DefaultTableModel();
                modello.setColumnCount(numColonne);
                titoli = new Vector<String>();
                titoli.add("#");
                titoli.add("cognome e nome");
                titoli.add("luogo di nascita");
                titoli.add("data di nascita");
                modello.addColumn(titoli);
            } // fine del blocco if

            if (continua) {
                tavola.setModel(modello);
                modelloColonne = tavola.getColumnModel();
                this.fixColonna(modelloColonne, 0, 40);
                this.fixColonna(modelloColonne, 1, 200);
                this.fixColonna(modelloColonne, 2, 150);
                this.fixColonna(modelloColonne, 3, 100);
            } // fine del blocco if

            if (continua) {
                codNotifica = this.getCodNotifica();
                continua = (codNotifica > 0);
            } // fine del blocco if

            if (continua) {
                codici = NotificaLogica.getCodAltriMembri(codNotifica);
                continua = (codici != null) && (codici.length > 0);
            } // fine del blocco if

            if (continua) {
                for (int cod : codici) {
                    k++;
                    modello.addRow(getMembroGruppo(k, cod));
                } // fine del ciclo for-each
            } // fine del blocco if

            if (continua) {
                pan.add(tavola);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private void fixColonna(TableColumnModel modelloColonne, int num, int lar) {
        /* variabili e costanti locali di lavoro */
        TableColumn colonna;

        try { // prova ad eseguire il codice
            colonna = modelloColonne.getColumn(num);
            colonna.setMinWidth(lar);
            colonna.setMaxWidth(lar);
            colonna.setResizable(false);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge al pannello le informazioni su un singolo membro del gruppo.
     *
     * @param codCliente dei membri del gruppo
     */
    private Vector<String> getMembroGruppo(int cont, int codCliente) {
        /* variabili e costanti locali di lavoro */
        Vector<String> riga = null;
        boolean continua;
        String nome = "";
        String cognome = "";
        int linkCitta;
        String nato = "";
        Date nascita = null;
        Modulo mod = null;
        Modulo modCitta = null;
        Connessione conn = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (codCliente > 0);

            if (continua) {
                conn = this.getConnessione();
            }// fine del blocco if


            if (continua) {
                mod = ClienteAlbergoModulo.get();
                continua = (mod != null);
            } // fine del blocco if

            if (continua) {
                modCitta = CittaModulo.get();
                continua = (modCitta != null);
            } // fine del blocco if

            if (continua) {
                nome = mod.query().valoreStringa(Anagrafica.Cam.nome.get(), codCliente, conn);
                cognome = mod.query().valoreStringa(Anagrafica.Cam.cognome.get(), codCliente, conn);

                linkCitta = mod.query().valoreInt(ClienteAlbergo.Cam.luogoNato.get(), codCliente, conn);
                nato = modCitta.query().valoreStringa(Citta.Cam.citta.get(), linkCitta, conn);

                nascita = mod.query().valoreData(ClienteAlbergo.Cam.dataNato.get(), codCliente, conn);
            } // fine del blocco if

            if (continua) {
                riga = new Vector<String>();
                riga.add(cont + "");
                riga.add(cognome + " " + nome);
                riga.add(nato);
                riga.add(Lib.Data.getDataBreve(nascita));
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riga;
    }


    /**
     * Controlla se questa scheda è valida.
     * <p/>
     * E' valida se ha tutti i dati necessari per la stampa
     *
     * @return true se è valida
     */
    public boolean isValida() {
        /* variabili e costanti locali di lavoro */
        boolean valida = false;

        try {    // prova ad eseguire il codice
            ;
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valida;
    }


    /**
     * @param pan pannello della scheda di notifica
     */
    private String addAutorizzazione(final Pannello pan) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;
        JTextArea area;
        JPanel pannelloElastico;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(pan));

            /* riga vuota di separazione */
            if (continua) {
                pan.add(this.getGap(160));
            } // fine del blocco if

//            if (continua) {
//                pannelloElastico = new JPanel();
//                Lib.Comp.sbloccaAltezza(pannelloElastico);
//                pan.add(pannelloElastico);
//            } // fine del blocco if

            if (continua) {
                testo = "Ai sensi della legge 675/96, ";
                testo += "ricevuta l'informativa sul trattamento dei miei dati personali, ";
                testo += "autorizzo l'albergo ";
                testo += "\nad inviare al mio domicilio periodica documentazione ";
                testo += "sugli aggiornamenti delle tariffe e delle offerte praticate.";

                area = new JTextArea(testo);
                area.setWrapStyleWord(true);
                area.setFont(FontFactory.creaPrinterFont(DIM_PICCOLO));
                pan.add(area);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * @param pan pannello della scheda di notifica
     */
    private String addFirma(final Pannello pan) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;
        JLabel label;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(pan));

            /* riga vuota di separazione */
            if (continua) {
                pan.add(this.getGap(GAP_DUE));
            } // fine del blocco if

            if (continua) {
                testo = "Firma del dichiarante  _____________________________";
                label = new JLabel(testo);
                label.setFont(FontFactory.creaPrinterFont(DIM_NORMALE));
                pan.add(label);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * @param pan pannello della scheda di notifica
     */
    private String addConsegna(final Pannello pan) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;
        JLabel label;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(pan));

            /* riga vuota di separazione */
            if (continua) {
                pan.add(this.getGap(GAP_DUE));
            } // fine del blocco if

            if (continua) {
                testo = "GIORNO DELLA CONSEGNA ALL'UFFICIO DI P.S.  ______________";
                label = new JLabel(testo);
                label.setFont(FontFactory.creaPrinterFont(DIM_NORMALE));
                pan.add(label);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * @param pan pannello della scheda di notifica
     */
    private String addBollo(final Pannello pan) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;
        JLabel label;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(pan));

            /* riga vuota di separazione */
            if (continua) {
                pan.add(this.getGap(GAP_DUE));
            } // fine del blocco if

            if (continua) {
                testo = "BOLLO DELL'UFF. P.S.  _______________  ";
                testo += "FIRMA DEL FUNZIONARIO  _________________________";
                label = new JLabel(testo);
                label.setFont(FontFactory.creaPrinterFont(DIM_NORMALE));
                pan.add(label);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    private int getProgressivo() {
        /* variabili e costanti locali di lavoro */
        int progressivo = 0;
        boolean continua;
        int codNotifica;
        Modulo mod = null;
        Connessione conn = null;

        try { // prova ad eseguire il codice
            codNotifica = this.getCodNotifica();
            continua = (codNotifica > 0);

            if (continua) {
                conn = this.getConnessione();
            }// fine del blocco if


            if (continua) {
                mod = NotificaModulo.get();
                continua = (mod != null);
            } // fine del blocco if

            if (continua) {
                progressivo = mod.query().valoreInt(Notifica.Cam.progressivo.get(), codNotifica, conn);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return progressivo;
    }


    private String getData() {
        /* variabili e costanti locali di lavoro */
        String data = "";
        boolean continua;
        int codTesta = 0;
        Date arrivo = null;
        Modulo modNotifica = null;
        Modulo modTesta = null;
        Connessione conn = null;
        int codNotifica;

        try { // prova ad eseguire il codice
            codNotifica = this.getCodNotifica();
            continua = (codNotifica > 0);

            if (continua) {
                conn = this.getConnessione();
            }// fine del blocco if

            if (continua) {
                modNotifica = NotificaModulo.get();
                continua = (modNotifica != null);
            } // fine del blocco if

            if (continua) {
                modTesta = TestaStampeModulo.get();
                continua = (modTesta != null);
            } // fine del blocco if

            if (continua) {
                codTesta = modNotifica.query().valoreInt(Notifica.Cam.linkTesta.get(), codNotifica, conn);
                continua = (codTesta > 0);
            } // fine del blocco if

            if (continua) {
                arrivo = modTesta.query().valoreData(TestaStampe.Cam.data.get(), codTesta, conn);
                continua = (Lib.Data.isValida(arrivo));
            } // fine del blocco if

            if (continua) {
                data = Lib.Data.getDataBreve(arrivo);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    private int getCliente() {
        /* variabili e costanti locali di lavoro */
        int codCliente = 0;
        boolean continua;
        int codNotifica;
        Modulo mod = null;
        Connessione conn;

        try { // prova ad eseguire il codice
            codNotifica = this.getCodNotifica();
            continua = (codNotifica > 0);

            if (continua) {
                mod = NotificaModulo.get();
                continua = (mod != null);
            } // fine del blocco if

            if (continua) {
                conn = this.getConnessione();
                codCliente = mod.query().valoreInt(Notifica.Cam.linkCliente.get(), codNotifica, conn);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codCliente;
    }


    private int getCodNotifica() {
        return codNotifica;
    }


    private void setCodNotifica(int codNotifica) {
        this.codNotifica = codNotifica;
    }


    private Connessione getConnessione() {
        return conn;
    }


    private void setConnessione(Connessione conn) {
        this.conn = conn;
    }


    private JPanel getGap(int gap) {
        /* variabili e costanti locali di lavoro */
        JPanel pannello = null;

        try { // prova ad eseguire il codice
            pannello = new JPanel();
            Lib.Comp.setPreferredHeigth(pannello, gap);
            Lib.Comp.bloccaAltezza(pannello);
            pannello.setOpaque(false);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pannello;
    }


    /**
     * Ritorna il codice dell'azienda di riferimento.
     * <p/>
     *
     * @return il codice dell'azienda
     */
    private int getCodAzienda() {
        /* variabili e costanti locali di lavoro */
        int codAzienda = 0;
        Connessione conn;
        int codNotifica;
        Modulo modRighe;
        Modulo modTesta;
        int codTesta;

        try {    // prova ad eseguire il codice
            conn = this.getConnessione();
            codNotifica = this.getCodNotifica();
            modRighe = NotificaModulo.get();
            modTesta = TestaStampeModulo.get();
            codTesta = modRighe.query().valoreInt(Notifica.Cam.linkTesta.get(), codNotifica, conn);
            codAzienda = modTesta.query().valoreInt(TestaStampe.Cam.azienda.get(), codTesta, conn);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codAzienda;
    }


}// fine della classe