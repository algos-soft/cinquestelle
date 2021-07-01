/**
 * Title:     ExportSettings
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      13-mar-2006
 */
package it.algos.base.importExport;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.ordine.Ordine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Wrapper per incapsulare le informazioni di necessarie
 * per una operazione di esportazione dati.
 *
 * Questo oggetto può essere modificato in un dialogo invocandone il metodo edit().
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  13-mar-2006
 */
public final class ExportSettings implements Serializable {

    /**
     * il modulo root dell'esportazione
     */
    private Modulo moduloRoot;

    /**
     * i campi da esportare
     */
    private ArrayList<Campo> campi;

    /**
     * se nascondere i campi fissi nel dialogo
     */
    private boolean nascondiCampiFissi;

    /**
     * la sorgente di esportazione
     */
    private ImportExport.Sorgente sorgente;

    /**
     * flag titoli colonna
     */
    private boolean usaTitoliColonna;

    /**
     * flag numeri di riga
     */
    private boolean usaNumeriRiga;

    /**
     * il formato di esportazione
     */
    private ImportExport.ExportFormats formato;

    /**
     * il file destinazione
     */
    private String path;

    /**
     * filtro da utilizzare
     */
    private Filtro filtro = null;

    /**
     * ordine da utilizzare
     */
    private Ordine ordine = null;

    /**
     * connessione da utilizzare
     */
    private Connessione connessione = null;

    /**
     * Modalità di esportazione dei titoli colonna
     */
    private ModoTitoli modoTitoli;


    /**
     * Costruttore completo senza parametri.<br>
     */
    public ExportSettings() {

        this(null);

    }// fine del metodo costruttore completo


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param moduloRoot modulo di riferimento
     */
    public ExportSettings(Modulo moduloRoot) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setModuloRoot(moduloRoot);

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

        /* impostazioni di default di un nuovo setting */
        this.setNascondiCampiFissi(true);
        this.setCampi(new ArrayList<Campo>());
        this.setSorgente(ImportExport.Sorgente.selezione);
        this.setUsaTitoliColonna(true);
        this.setModoTitoli(ModoTitoli.auto);
        this.setUsaNumeriRiga(false);
        this.setFormato(ImportExport.ExportFormats.tabText);
        this.setPath("");

    }


    /**
     * Scrive questo oggetto serializzato su un file.
     * <p/>
     *
     * @param file sul quale scrivere
     *
     * @return true se riuscito
     */
    public boolean serializza(File file) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        FileOutputStream out;
        ObjectOutputStream stream;

        try {    // prova ad eseguire il codice
            out = new FileOutputStream(file);
            stream = new ObjectOutputStream(out);
            stream.writeObject(this);
            stream.flush();
            stream.close();
            riuscito = true;
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Crea un oggetto di questa classe da un file serializzato.
     * <p/>
     *
     * @param file dal quale leggere
     *
     * @return l'oggetto creato
     */
    public static ExportSettings deserializza(File file) {
        /* variabili e costanti locali di lavoro */
        ExportSettings settings = null;
        FileInputStream in;
        ObjectInputStream stream;

        try {    // prova ad eseguire il codice
            in = new FileInputStream(file);
            stream = new ObjectInputStream(in);
            settings = (ExportSettings)stream.readObject();
            stream.close();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return settings;
    }


    /**
     * The writeObject method is responsible for writing the
     * state of the object for its particular class so that the
     * corresponding readObject method can restore it
     * <p/>
     *
     * @param out stream di uscita
     *
     * @throws IOException eccezione
     */
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        /* variabili e costanti locali di lavoro */
        String stringa;
        ArrayList<Campo> campi;
        ArrayList<String> nomiCampo;
        Object oggetto;

        try { // prova ad eseguire il codice

            /* modulo root */
            stringa = this.getModuloRoot().getNomeChiave();
            out.writeObject(stringa);

            /* elenco campi */
            campi = this.getCampi();
            nomiCampo = new ArrayList<String>();
            for (Campo campo : campi) {
                stringa = campo.getChiaveCampo();
                nomiCampo.add(stringa);
            }
            stringa = Lib.Testo.getStringaVirgola(nomiCampo);
            out.writeObject(stringa);

            /* flag nascondi campi fissi */
            oggetto = this.isNascondiCampiFissi();
            out.writeObject(oggetto);

            /* sorgente di esportazione */
            oggetto = this.getSorgente();
            out.writeObject(oggetto);

            /* flag titoli colonna */
            oggetto = this.isUsaTitoliColonna();
            out.writeObject(oggetto);

            /* flag numeri di riga */
            oggetto = this.isUsaNumeriRiga();
            out.writeObject(oggetto);

            /* formato di esportazione */
            oggetto = this.getFormato();
            out.writeObject(oggetto);

            /* percorso di esportazione */
            oggetto = this.getPath();
            out.writeObject(oggetto);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * The readObject method is responsible for reading from
     * the stream and restoring the classes fields.
     * <p/>
     *
     * @param in stream di entrata
     *
     * @throws IOException            eccezione
     * @throws ClassNotFoundException eccezione
     */
    private void readObject(java.io.ObjectInputStream in) throws
            IOException,
            ClassNotFoundException {
        /* variabili e costanti locali di lavoro */
        Object oggetto;
        String stringa;
        Modulo modulo;
        ArrayList<String> listaStringhe;
        ArrayList<Campo> listaCampi;
        Campo campo;

        try { // prova ad eseguire il codice

            /* modulo root */
            oggetto = in.readObject();
            stringa = (String)oggetto;
            modulo = Progetto.getModulo(stringa);
            this.setModuloRoot(modulo);

            /* elenco campi */
            oggetto = in.readObject();
            stringa = (String)oggetto;
            listaStringhe = Libreria.creaListaVirgola(stringa);
            listaCampi = new ArrayList<Campo>();
            for (String chiave : listaStringhe) {
                campo = Progetto.getCampo(chiave);
                if (campo != null) {
                    listaCampi.add(campo);
                }// fine del blocco if
            }
            this.setCampi(listaCampi);

            /* flag nascondi campi fissi */
            oggetto = in.readObject();
            this.setNascondiCampiFissi((Boolean)oggetto);

            /* sorgente di esportazione */
            oggetto = in.readObject();
            this.setSorgente((ImportExport.Sorgente)oggetto);

            /* flag titoli colonna */
            oggetto = in.readObject();
            this.setUsaTitoliColonna((Boolean)oggetto);

            /* flag numeri di riga */
            oggetto = in.readObject();
            this.setUsaNumeriRiga((Boolean)oggetto);

            /* formato di esportazione */
            oggetto = in.readObject();
            this.setFormato((ImportExport.ExportFormats)oggetto);

            /* percorso di esportazione */
            oggetto = in.readObject();
            this.setPath((String)oggetto);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Edita i valori di questo oggetto in un dialogo modale.
     * <p/>
     * @return true se confermato
     */
    public boolean edit () {
        /* variabili e costanti locali di lavoro */
        boolean confermato  = false;
        DialogoEdit dialogo;
        PanEditExportSettings panEdit;

        try {    // prova ad eseguire il codice

            /* crea un pannello specializzato con i settings correnti */
            panEdit = new PanEditExportSettings(this);

            /* crea e avvia un dialogo con il pannello */
            dialogo = new DialogoEdit(panEdit);
            dialogo.avvia();

            /* se confermato scrive i nuovi vaori nei settings e ritorna true */
            if (dialogo.isConfermato()) {
                this.writeSettings(panEdit);
                confermato = true;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return confermato;
    }


    /**
     * Registra i dati di un editing in questo oggetto.
     * <p/>
     * @param panEdit l'oggetto di editing
     */
    private void  writeSettings(PanEditExportSettings panEdit) {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice
            this.setPath(panEdit.getPath());
            this.setFormato(panEdit.getFormato());
            this.setUsaTitoliColonna(panEdit.isUsaTitoliColonna());
            this.setUsaNumeriRiga(panEdit.isUsaNumeriRiga());
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }





    public Modulo getModuloRoot() {
        return moduloRoot;
    }


    /**
     * Assegna il riferimento al modulo
     * <p/>
     *
     * @param moduloRoot modulo di riferimento
     */
    public void setModuloRoot(Modulo moduloRoot) {
        this.moduloRoot = moduloRoot;
    }


    public ArrayList<Campo> getCampi() {
        return campi;
    }


    /**
     * Assegna l'elenco dei campi da esportare
     * <p/>
     *
     * @param campi elenco dei campi
     * possono essere del modulo di riferimento o di moduli linkati
     */
    public void setCampi(ArrayList<Campo> campi) {
        this.campi = campi;
    }


    public boolean isNascondiCampiFissi() {
        return nascondiCampiFissi;
    }


    /**
     * Se nascondere i campi fissi qiando si presenta il dialogo
     * <p/>
     *
     * @param flag true per nascondere i campi fissi nel dialogo
     */
    public void setNascondiCampiFissi(boolean flag) {
        this.nascondiCampiFissi = flag;
    }


    public ImportExport.Sorgente getSorgente() {
        return sorgente;
    }


    /**
     * Opzione Esporta tutti i record / Esporta la selezione nel dialogo
     * <p/>
     *
     * @param sorgente dell'esportazione
     */
    public void setSorgente(ImportExport.Sorgente sorgente) {
        this.sorgente = sorgente;
    }


    public boolean isUsaTitoliColonna() {
        return usaTitoliColonna;
    }


    /**
     * Se esportare i titoli colonna
     * <p/>
     *
     * @param flag true per esportare i titoli colonna
     */
    public void setUsaTitoliColonna(boolean flag) {
        this.usaTitoliColonna = flag;
    }


    public boolean isUsaNumeriRiga() {
        return usaNumeriRiga;
    }


    /**
     * Se esportare i numeri di riga
     * <p/>
     *
     * @param flag true per esportare i numeri di riga
     */
    public void setUsaNumeriRiga(boolean flag) {
        this.usaNumeriRiga = flag;
    }


    public ImportExport.ExportFormats getFormato() {
        return formato;
    }


    /**
     * Il formato di esportazione
     * <p/>
     *
     * @param formato da utilizzare (default = Text)
     */
    public void setFormato(ImportExport.ExportFormats formato) {
        this.formato = formato;
    }


    public String getPath() {
        return path;
    }


    /**
     * Il percorso del file di esportazione
     * <p/>
     *
     * @param path di esportazione
     */
    public void setPath(String path) {
        this.path = path;
    }


    public Filtro getFiltro() {
        return filtro;
    }


    /**
     * Il filtro da utilizzare per identificare i record da esportare
     * <p/>
     *
     * @param filtro da utilizzare
     */
    public void setFiltro(Filtro filtro) {
        this.filtro = filtro;
    }


    public Ordine getOrdine() {
        return ordine;
    }


    /**
     * L'ordine da utilizzare
     * <p/>
     *
     * @param ordine da utilizzare
     */
    public void setOrdine(Ordine ordine) {
        this.ordine = ordine;
    }


    public Connessione getConnessione() {
        return connessione;
    }


    /**
     * La connessione da utilizzare
     * <p/>
     *
     * @param connessione da utilizzare
     * Se non specificato usa quella del modulo
     */
    public void setConnessione(Connessione connessione) {
        this.connessione = connessione;
    }


    public ModoTitoli getModoTitoli() {
        return modoTitoli;
    }


    /**
     * La modalità di esportazione dei titoli colonna
     * <p/>
     *
     * @param modoTitoli modalità di esportazione dei titoli, da ExportSettings.ModoTitoli
     * auto = nomi brevi per i campi del modulo, nomi completi per i campi linkati (default)
     * brevi = sempre nomi brevi
     * completi = sempre nomi completi
     * linkSoloModulo = nomi brevi per i campi del modulo, nome del modulo per i campi linkati
     */
    public void setModoTitoli(ModoTitoli modoTitoli) {
        this.modoTitoli = modoTitoli;
    }


    /**
     * Dialogo di editing di questi settings.
     * </p>
     */
    private final class DialogoEdit extends DialogoAnnullaConferma {

        /* pannello di editing fornito */
        private PanEditExportSettings panEdit;

        /**
         * Costruttore completo.
         * <p>
         * @param pan pannelo di editing degli ExportSettings
         */
        public DialogoEdit(PanEditExportSettings pan) {
            /* rimanda al costruttore della superclasse */
            super();

            try { // prova ad eseguire il codice

                /* regolazioni iniziali di riferimenti e variabili */
                this.setPanEdit(pan);

                /* inizio*/
                this.inizia();

            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch
        }// fine del metodo costruttore completo


        /**
         * Regolazioni immediate di riferimenti e variabili.
         * <p>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            /* variabili e costanti locali di lavoro */

            try { // prova ad eseguire il codice
                this.setTitolo("Impostazioni di esportazione");
                this.addPannello(this.getPanEdit());
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        @Override
        public boolean isConfermabile() {
            /* variabili e costanti locali di lavoro */
            boolean confermabile=false;

            try { // prova ad eseguire il codice
                confermabile = super.isConfermabile();
                if (confermabile) {
                    confermabile = this.getPanEdit().isValido();
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return confermabile;
        }


        private PanEditExportSettings getPanEdit() {
            return panEdit;
        }


        private void setPanEdit(PanEditExportSettings panEdit) {
            this.panEdit = panEdit;
        }
    } // fine della classe 'interna'


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Modalità di esportazione dei titoli di colonna
     */
    public enum ModoTitoli {

        // nomi brevi per i campi del modulo, nomi completi per i campi linkati
        auto("Auto", 1),

        //  sempre nomi brevi
        brevi("Brevi", 2),

        //  sempre nomi completi
        completi("Completi", 3),

        // nomi brevi per i campi del modulo, nome del modulo per i campi linkati
        linkSoloModulo("Nome modulo per linkati", 4);

        /**
         * voce da utilizzare
         */
        private String titolo;

        /**
         * valore per il database
         */
        private int codice;


        /**
         * Costruttore completo con parametri.
         *
         * @param titolo utilizzato nei popup
         * @param codice utilizzato nel database
         */
        ModoTitoli(String titolo, int codice) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setTitolo(titolo);
                this.setCodice(codice);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public static ArrayList<ModoTitoli> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<ModoTitoli> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<ModoTitoli>();

                /* traverso tutta la collezione */
                for (ModoTitoli tipo : values()) {
                    lista.add(tipo);
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        public String toString() {
            return this.getTitolo();
        }


        private String getTitolo() {
            return titolo;
        }


        private void setTitolo(String titolo) {
            this.titolo = titolo;
        }


        public int getCodice() {
            return codice;
        }


        private void setCodice(int codice) {
            this.codice = codice;
        }


        /**
         * Ritorna il voce per un dato codice
         * <p/>
         *
         * @param codice da cercare
         *
         * @return il voce dell'elemento con tale codice
         */
        public static String getTitolo(int codice) {
            /* variabili e costanti locali di lavoro */
            String titolo = "";
            int unCodice;

            for (ModoTitoli tipo : values()) {
                unCodice = tipo.getCodice();
                if (unCodice == codice) {
                    titolo = tipo.getTitolo();
                    break;
                }// fine del blocco if

            }

            /* valore di ritorno */
            return titolo;
        }


        /**
         * Ritorna l'elemento della enum che ha un dato codice
         * <p/>
         *
         * @param codice da cercare
         *
         * @return l'elemento con il codice specificato
         */
        public static ModoTitoli getValore(int codice) {
            /* variabili e costanti locali di lavoro */
            ModoTitoli elemento = null;
            int unCodice = 0;

            for (ModoTitoli elem : values()) {
                unCodice = elem.getCodice();
                if (unCodice == codice) {
                    elemento = elem;
                    break;
                }// fine del blocco if
            }

            /* valore di ritorno */
            return elemento;
        }


    }// fine della classe


}// fine della classe