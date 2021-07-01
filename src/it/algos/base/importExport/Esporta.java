/**
 * Title:     Esporta
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      28.02.2006
 */
package it.algos.base.importExport;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.importExport.methods.ExportMethod;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Esporta i dati.
 * <p/>
 * Usa la configurazione di esportazione preparata in un oggetto ExportSettings.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 28.02.2006
 */
public class Esporta implements Runnable {

    /**
     * thread indipendente di esecuzione
     */
    private volatile Thread thread = null;

    /**
     * riferimento al wrapper contenente le impostazioni di esportazione
     */
    private ExportSettings settings = null;

    /**
     * oggetto dati da esportare
     */
    private Dati dati;

    /**
     * Numero di record esportati, aggiornato
     * costantemente durante l'esportazione
     */
    private int quanti;

    /**
     * flag che viene acceso quando l'operazione e' terminata
     */
    private boolean done;


    /**
     * Costruttore completo con parametri.
     * <p/>
     * Costruisce un nuovo esportatore utilizzando il setting fornito.
     * Il setting deve contenere le informazioni per ricavare i dati.
     *
     * @param settings le impostazioni della esportazione
     */
    public Esporta(ExportSettings settings) {
        this(null, settings);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo con parametri.
     * <p/>
     * Costruisce un nuovo esportatore utilizzando i dati e il setting forniti.
     *
     * @param dati da esportare
     * @param settings le impostazioni della esportazione
     */
    public Esporta(Dati dati, ExportSettings settings) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setDati(dati);
        this.setSettings(settings);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice

            /* recupera i dati da esportare (se non già forniti) */
            if (this.getDati() == null) {
                this.setDati(this.recuperaDati());
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Esegue l'esportazione.
     * <p/>
     */
    private void esegui() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Dati dati;
        FileOutputStream stream = null;

        try {        // prova ad eseguire il codice

            /* recupera i dati */
            dati = this.getDati();
            if (dati == null) {
                continua = false;
            }// fine del blocco if

            /* crea il file di uscita */
            if (continua) {
                stream = this.getFileOutputStream();
                if (stream == null) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* esporta i dati */
            if (continua) {
                this.esportaDati(dati, stream);
            }// fine del blocco if

            /* chiude il file di uscita */
            if (stream != null) {
                stream.flush();
                stream.close();
            }// fine del blocco if

            /**
             * chiude i dati
             * disabilitato alex 02-2009 - se passi dei dati che servono
             * ancora te li chiude e non va bene!
             */
//            if (dati != null) {
//                dati.close();
//            }// fine del blocco if

        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Esporta i dati.
     * <p/>
     *
     * @param dati da esportare
     * @param stream FileOutputStream sul quale scrivere
     *
     * @return true se riuscito
     */
    private boolean esportaDati(Dati dati, FileOutputStream stream) {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = true;
        Campo campo;
        String titolo;
        boolean usaNumeriRiga;
        Integer numeroRiga;
        ImportExport.ExportFormats formato;
        ExportMethod metodo;

        try {    // prova ad eseguire il codice

            /* recupera i dati di configurazione */
            formato = this.getSettings().getFormato();
            usaNumeriRiga = this.isUsaNumeriRiga();
            metodo = formato.getMetodo();

            /* assegna l'elenco dei campi al metodo */
            ArrayList<Campo> listaCampi = new ArrayList<Campo>();

            /**
             * se usa i numeri di riga aggiunge
             * un primo campo numerico
             */
            if (usaNumeriRiga) {
                campo = CampoFactory.intero("#");
                campo.inizializza();
                listaCampi.add(campo);
            }// fine del blocco if

            /* aggiunge i campi relativi ai dati */
            listaCampi.addAll(dati.getCampi());

            /* regola il titolo */
            if (this.getModuloRoot() != null) {
                metodo.setTitoloDati(this.getModuloRoot().getNomeModulo());
            }// fine del blocco if

            /* inizializza il metodo passando campi e stream */
            metodo.inizializza(listaCampi, stream);

            /* genera eventualmente i titoli colonna */
            if (this.isUsaTitoliColonna()) {
                ArrayList<String> titoli = new ArrayList<String>();

                /**
                 * se usa i numeri di riga aggiunge
                 * il titolo # all'inizio
                 */
                if (usaNumeriRiga) {
                    titoli.add("#");
                }// fine del blocco if

                for (int k = 0; k < this.getDati().getColumnCount(); k++) {
                    Campo unCampo = this.getDati().getCampo(k);
                    titolo = this.getTitoloColonna(unCampo);
                    titoli.add(titolo);
                } // fine del ciclo for

                /* delega la scrittura al metodo */
                metodo.writeTitles(titoli);

            }// fine del blocco if

            /* spazzola i dati e li esporta */
            for (int k = 0; k < dati.getRowCount(); k++) {

                /* incrementa la variabile che mantiene il
                 * totale dei record esportati */
                this.setQuantiEsportati(k + 1);

                /* crea una lista valori vuota */
                ArrayList<Object> listaValori = new ArrayList<Object>();

                /**
                 * se usa i numeri di riga aggiunge
                 * il numero di riga in testa
                 */
                if (usaNumeriRiga) {
                    numeroRiga = k + 1;
                    listaValori.add(numeroRiga);
                }// fine del blocco if

                /* aggiunge i valori dai dati */
                listaValori.addAll(dati.getValoriRiga(k));

                /* esporta una riga di dati */
                metodo.writeRow(listaValori);

            }

            /**
             * eventuali operazioni di chiusura del metodo di esportazione
             */
            metodo.close();

            /* accende il flag di operazione terminata */
            this.setDone(true);


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return eseguito;
    }


    /**
     * Restituisce il titolo di colonna da utilizzare per un dato campo.
     * <p/>
     *
     * @param campo da esportare
     *
     * @return titolo di colonna da utilizzare
     */
    private String getTitoloColonna(Campo campo) {
        /* variabili e costanti locali di lavoro */
        String titolo = "";
        ExportSettings.ModoTitoli modoTitoli;
        Modulo moduloLink;

        try {    // prova ad eseguire il codice

            modoTitoli = this.getSettings().getModoTitoli();
            switch (modoTitoli) {

                // se il campo è del modulo usa il nome
                // se il campo è di un altro modulo usa la chiave
                case auto:
                    if (this.getModuloRoot() != null) {
                        if (campo.getModulo().equals(this.getModuloRoot())) {
                            titolo = campo.getNomeInterno();
                        } else {
                            titolo = campo.getChiaveCampo();
                        }// fine del blocco if-else
                    } else {
                        titolo = campo.getNomeInterno();
                    }// fine del blocco if-else
                    break;

                /* sempre nome breve */
                case brevi:
                    titolo = campo.getNomeInterno();
                    break;

                /* sempre nome completo */
                case completi:
                    titolo = campo.getChiaveCampo();
                    break;

                // nomi brevi per i campi del modulo
                // nome del modulo per i campi linkati
                case linkSoloModulo:
                    if (this.getModuloRoot() != null) {
                        if (campo.getModulo().equals(this.getModuloRoot())) {
                            titolo = campo.getNomeInterno();
                        } else {
                            moduloLink = campo.getModulo();
                            titolo = moduloLink.getNomeModulo();
                        }// fine del blocco if-else
                    } else {
                        titolo = campo.getNomeInterno();
                    }// fine del blocco if-else
                    break;

                default: // caso non definito
                    break;
            } // fine del blocco switch

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return titolo;
    }


    /**
     * Recupera i dati da esportare.
     * <p/>
     *
     * @return l'oggetto dati da esportare
     */
    private Dati recuperaDati() {
        /* variabili e costanti locali di lavoro */
        Dati dati = null;
        Modulo modulo;
        ArrayList<Campo> campi;
        Filtro filtro;
        Ordine ordine;
        Query query;

        try {    // prova ad eseguire il codice

            modulo = this.getModuloRoot();
            campi = this.getCampi();
            filtro = this.getFiltro();
            ordine = this.getOrdine();
            query = new QuerySelezione(modulo);
            query.setCampi(campi);
            query.setFiltro(filtro);
            query.setOrdine(ordine);
            if (modulo != null) {
                dati = modulo.query().querySelezione(query, this.getConnessione());
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return dati;
    }


    /**
     * Ritorna il modulo root
     * <p/>
     *
     * @return il modulo root
     */
    private Modulo getModuloRoot() {
        return this.getSettings().getModuloRoot();
    }


    /**
     * Ritorna i campi.
     * <p/>
     *
     * @return l'elenco dei campi
     */
    private ArrayList<Campo> getCampi() {
        return this.getSettings().getCampi();
    }


    /**
     * Ritorna il filtro.
     * <p/>
     *
     * @return il filtro
     */
    private Filtro getFiltro() {
        return this.getSettings().getFiltro();
    }


    /**
     * Ritorna l'ordine.
     * <p/>
     *
     * @return l'ordine
     */
    private Ordine getOrdine() {
        return this.getSettings().getOrdine();
    }


    /**
     * Ritorna l'uso dei titoli di colonna
     * <p/>
     *
     * @return true se usa i titoli di colonna
     */
    private boolean isUsaTitoliColonna() {
        return this.getSettings().isUsaTitoliColonna();
    }


    /**
     * Ritorna l'uso dei numeri di riga
     * <p/>
     *
     * @return true se usa i numeri di riga
     */
    private boolean isUsaNumeriRiga() {
        return this.getSettings().isUsaNumeriRiga();
    }


    /**
     * Ritorna il path di esportazione.
     * <p/>
     *
     * @return il path di esportazione
     */
    private String getPath() {
        return this.getSettings().getPath();
    }


    /**
     * Ritorna l'output stream di esportazione.
     * <p/>
     *
     * @return l'output stream sul quale esportare
     */
    private FileOutputStream getFileOutputStream() {
        /* variabili e costanti locali di lavoro */
        FileOutputStream stream = null;
        String path;
        File file;

        try { // prova ad eseguire il codice

            path = this.getPath();
            if (Lib.Testo.isValida(path)) {
                file = new File(this.getPath());
                stream = new FileOutputStream(file);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stream;
    }


    /**
     * Recupera la connessione da utilizzare per la query.
     * <p/>
     * Utilizza la connessione specificata nei settings
     * In assenza, utilizza la connessione del modulo
     *
     * @return la connessione da utilizzare
     */
    private Connessione getConnessione() {
        /* variabili e costanti locali di lavoro */
        Connessione conn = null;

        try {    // prova ad eseguire il codice
            conn = this.getSettings().getConnessione();
            if (conn == null) {
                conn = this.getModuloRoot().getConnessione();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return conn;
    }


    /**
     * Ritorna il numero totale di record da esportare.
     * <p/>
     *
     * @return il numero di record da esportare
     */
    public int getQuantiTotali() {
        /* variabili e costanti locali di lavoro */
        int quanti = 0;
        Dati dati;

        try {    // prova ad eseguire il codice
            dati = this.getDati();
            if (dati != null) {
                quanti = dati.getRowCount();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return quanti;
    }


    /**
     * Metodo eseguito da Java per all'avvio di un thread.
     * <p/>
     * Si puo' lanciare direttamente dall'esterno
     * per eseguire nel thread corrente.
     */
    public void run() {
        try {    // prova ad eseguire il codice
            this.esegui();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Lancia un nuovo thread con questa classe.
     * <p/>
     * Java esegue il metodo run()
     */
    public void start() {
        try {    // prova ad eseguire il codice
            if (thread == null) {
                thread = new Thread(this, "Esporta");
                thread.start();
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    private ExportSettings getSettings() {
        return settings;
    }


    private void setSettings(ExportSettings settings) {
        this.settings = settings;
    }


    private Dati getDati() {
        return dati;
    }


    private void setDati(Dati dati) {
        this.dati = dati;
    }


    public int getQuantiEsportati() {
        return quanti;
    }


    private void setQuantiEsportati(int quanti) {
        this.quanti = quanti;
    }


    public boolean isDone() {
        return done;
    }


    private void setDone(boolean done) {
        this.done = done;
    }


} // fine della classe
