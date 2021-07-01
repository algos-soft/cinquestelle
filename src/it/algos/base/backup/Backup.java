/**
 * Title:     Backup
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25-gen-2007
 */
package it.algos.base.backup;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.Db;
import it.algos.base.database.DbFactory;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.dialogo.Dialogo;
import it.algos.base.dialogo.DialogoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibTesto;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.modifica.QueryInsert;
import it.algos.base.query.selezione.QuerySelezione;
import org.jdesktop.swingworker.SwingWorker;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Oggetto che implementa le funzionalità di backup e restore.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 25-gen-2007
 */
public class Backup implements PropertyChangeListener {

    /* dialogo di impostazione dell'operazione */
    private DialogoBR dialogo;

    /* database da utilizzare per il backup / restore */
    private Db database;

    /* Connessione al database di backup / restore */
    private Connessione connessione;

    /* monitor di progresso dell'operazione */
    private ProgressMonitor progressMonitor;

    /* task di esecuzione */
    private BRTask task;

    /* elenco di oggetti tavola con campi, da backuppare
    * la chiave è il nome della tavola */
    private LinkedHashMap<String, TavolaBackup> tavoleBackup;


    /**
     * Costruttore completo.
     * <p/>
     */
    public Backup() {
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

        try { // prova ad eseguire il codice

            /* Crea e registra l'elenco di oggetti da backuppare.*/
            this.creaTavoleBackup();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea e registra l'elenco di oggetti da backuppare.
     * <p/>
     */
    private void creaTavoleBackup() {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<String, TavolaBackup> mappa = new LinkedHashMap<String, TavolaBackup>();
        TavolaBackup tb = null;
        ArrayList<Modulo> moduli;
        ArrayList<Campo> campi;
        String tavola;

        try {    // prova ad eseguire il codice

            moduli = Progetto.getModuliPreorder();
            for (Modulo modulo : moduli) {
                tavola = modulo.getTavola();

                /* crea una nuova TavolaBackup se non già esistente */
                tb = mappa.get(tavola);
                if (tb == null) {
                    tb = new TavolaBackup(tavola, modulo);
                    mappa.put(tavola, tb);
                }// fine del blocco if-else

                /* aggiunge tutti i campi fisici del modulo */
                campi = modulo.getCampiFisici();
                for (Campo campo : campi) {
                    tb.addCampo(campo);
                }

            }

            /* registra la mappa di oggetti */
            this.setTavoleBackup(mappa);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Effettua un backup di progetto.
     * <p/>
     * Presenta il dialogo di impostazione
     * Esegue l'operazione
     *
     * @return true se eseguito
     */
    public boolean backup() {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = false;
        DialogoBackup dialogo;

        try { // prova ad eseguire il codice
            dialogo = new DialogoBackup(this);
            this.setDialogo(dialogo);
            if (dialogo.isConfermato()) {
                this.lanciaBackup();
                eseguito = true;
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return eseguito;

    }


    /**
     * Effettua un restore di progetto.
     * <p/>
     * Presenta il dialogo di impostazione
     * Esegue l'operazione
     *
     * @return true se eseguito
     */
    public boolean restore() {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = false;
        DialogoRestore dialogo;
        Dialogo dialogoConferma;
        String testo;

        try { // prova ad eseguire il codice
            dialogo = new DialogoRestore(this);
            this.setDialogo(dialogo);
            if (dialogo.isConfermato()) {
                testo = "Questa procedura elimina i dati correnti\n";
                testo += "e li sostituisce con quelli del backup!";
                dialogoConferma = DialogoFactory.annullaConferma("Attenzione!");
                dialogoConferma.setMessaggio(testo);
                dialogoConferma.avvia();
                if (dialogoConferma.isConfermato()) {
                    this.lanciaRestore();
                    eseguito = true;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return eseguito;
    }


    /**
     * Lancio effettivo del backup.
     * <p/>
     * Crea un ProgressMonitor
     * Lancia il task di backup
     */
    private void lanciaBackup() {
        /* variabili e costanti locali di lavoro */
        ProgressMonitor pm;
        BRTask task;

        try {    // prova ad eseguire il codice

            /* crea un Progress Monitor e lo registra.
               ("x" serve per occupare il posto per la nota variabile
               se non lo metto fin dall'inizio il dialogo di progresso
               viene costruito con dimensioni imprecise) */
            pm = new ProgressMonitor(null, "Backup in corso", "x", 0, 100);
            this.setProgressMonitor(pm);
            pm.setProgress(0);

            /* crea il task di backup, lo registra e lo lancia
              l'esecuzione di questo thread continua */
            task = new BackupTask();
            this.setTask(task);
            task.addPropertyChangeListener(this);
            task.execute();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Lancio effettivo del restore.
     * <p/>
     */
    private void lanciaRestore() {
        /* variabili e costanti locali di lavoro */
        ProgressMonitor pm;
        BRTask task;

        try {    // prova ad eseguire il codice

            /* crea un Progress Monitor e lo registra.
               ("x" serve per occupare il posto per la nota variabile
               se non lo metto fin dall'inizio il dialogo di progresso
               viene costruito con dimensioni imprecise) */
            pm = new ProgressMonitor(null, "Restore in corso", "x", 0, 100);
            this.setProgressMonitor(pm);
            pm.setProgress(0);

            /* crea il task di restore, lo registra e lo lancia
              l'esecuzione di questo thread continua */
            task = new RestoreTask();
            this.setTask(task);
            task.addPropertyChangeListener(this);
            task.execute();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea e registra il database di backup.
     * <p/>
     *
     * @return true se riuscito
     */
    private boolean creaDbBackup() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua = true;
        Db db;

        try {    // prova ad eseguire il codice

            /**
             * Crea un database HSQLDB cached alla posizione
             * specificata nel dialogo e con il nome
             * uguale al nome del database di Progetto
             */
            if (continua) {
                db = DbFactory.crea(Db.SQL_HSQLDB);
                db.setNomeDatabase(this.getNomeDatabase());
                db.setPercorsoDati(this.getPercorsoBackup());
                db.setHost("localhost");
                db.setLogin("sa");
                db.setPassword("");
                db.setModoFunzionamento(Db.MODO_STAND_ALONE);
                db.setTipoTavole(Db.TAVOLE_CACHED);

                /* inizializza il database */
                db.inizializza();

                /* registra il database */
                this.setDatabase(db);

            }// fine del blocco if

            /* valore di ritorno */
            if (continua) {
                riuscito = true;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Crea e registra il database di restore.
     * <p/>
     *
     * @return true se riuscito
     */
    private boolean creaDbRestore() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua = true;
        Db db;

        try {    // prova ad eseguire il codice

            /**
             * Crea un database HSQLDB cached alla posizione
             * specificata nel dialogo e con il nome
             * uguale al nome del database di Progetto
             */
            if (continua) {
                db = DbFactory.crea(Db.SQL_HSQLDB);
                db.setNomeDatabase(this.getNomeDatabase());
                db.setPercorsoDati(this.getDialogo().getPercorso());
                db.setHost("localhost");
                db.setLogin("sa");
                db.setPassword("");
                db.setModoFunzionamento(Db.MODO_STAND_ALONE);
                db.setTipoTavole(Db.TAVOLE_CACHED);

                /* inizializza il database */
                db.inizializza();

                /* registra il database */
                this.setDatabase(db);

            }// fine del blocco if

            /* valore di ritorno */
            if (continua) {
                riuscito = true;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Crea e apre una connessione al database di backup e la registra.
     * <p/>
     *
     * @return true se riuscito
     */
    private boolean creaConnessione() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        Connessione conn;

        try {    // prova ad eseguire il codice
            conn = this.getDatabase().getConnessione();
            this.setConnessione(conn);
            riuscito = conn.open();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Ritorna il percorso completo specifico di questo backup.
     * <p/>
     * E' il percorso della cartella nella quale verrà registrato il backup.
     * E' composto dal percorso base selezionato dall'utente
     * piu' una parte automatica costituita da nome database, data e ora
     * nella forma NOMEDATABASE-AAAAMMGG-HHMMSS
     *
     * @return il percorso completo di backup.
     */
    private String getPercorsoBackup() {
        /* variabili e costanti locali di lavoro */
        String percorso = null;
        String percorsoBase = null;
        DialogoBR dialogo;
        String nomeDB;
        Integer anno;
        String stringaAnno;
        Integer mese;
        String stringaMese;
        Integer giorno;
        String stringaGiorno;
        Integer ora;
        String stringaOra = "";
        Integer minuti;
        String stringaMinuti = "";
        Integer secondi;
        String stringaSecondi = "";

        try {    // prova ad eseguire il codice

            /* percorso di base (dal dialogo) */
            dialogo = this.getDialogo();
            percorsoBase = dialogo.getPercorso();

            nomeDB = this.getNomeDatabase();

            anno = Lib.Data.getAnnoCorrente();
            stringaAnno = anno.toString();

            mese = Lib.Data.getNumeroMeseCorrente();
            stringaMese = mese.toString();
            stringaMese = this.pad2(stringaMese);

            giorno = Lib.Data.getNumeroGiornoCorrente();
            stringaGiorno = giorno.toString();
            stringaGiorno = this.pad2(stringaGiorno);

            ora = Lib.Data.getNumeroOreCorrente();
            stringaOra = ora.toString();
            stringaOra = this.pad2(stringaOra);

            minuti = Lib.Data.getNumeroMinutiCorrente();
            stringaMinuti = minuti.toString();
            stringaMinuti = this.pad2(stringaMinuti);

            secondi = Lib.Data.getNumeroSecondiCorrente();
            stringaSecondi = secondi.toString();
            stringaSecondi = this.pad2(stringaSecondi);

            percorso = percorsoBase;
            percorso += "/";
            percorso += nomeDB;
            percorso += "-";
            percorso += stringaAnno;
            percorso += stringaMese;
            percorso += stringaGiorno;
            percorso += "-";
            percorso += stringaOra;
            percorso += stringaMinuti;
            percorso += stringaSecondi;

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return percorso;
    }


    /**
     * Formatta la stringa a 2 caratteri con zeri iniziali.
     * <p/>
     *
     * @param stringa da formattare
     *
     * @return stringa formattata
     */
    private String pad2(String stringa) {
        /* variabili e costanti locali di lavoro */
        String stringaOut = null;

        try {    // prova ad eseguire il codice
            stringaOut = Lib.Testo.pad(stringa, '0', 2, LibTesto.Posizione.inizio);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringaOut;
    }


    /**
     * Ritorna il nome del database di backup/restore.
     * <p/>
     *
     * @return il nome del database
     */
    public String getNomeDatabase() {
        /* variabili e costanti locali di lavoro */
        String nome = null;

        try {    // prova ad eseguire il codice
            nome = Progetto.getNomePrimoModulo();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nome;
    }


    /**
     * Ritorna l'elenco dei moduli da backuppare.
     * <p/>
     *
     * @return l'elenco ordinato dei moduli da backuppare
     */
    private ArrayList<Modulo> getModuliBackup() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Modulo> lista = new ArrayList<Modulo>();
        Modulo modulo;

        try {    // prova ad eseguire il codice
            LinkedHashMap<String, TavolaBackup> mappa;
            mappa = getTavoleBackup();
            for (TavolaBackup tb : mappa.values()) {
                modulo = tb.getModulo();
                lista.add(modulo);
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Invocato quando cambia la property "progress" del task
     * di backup o restore.
     * <p/>
     * Regola il monitor di progresso.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        /* variabili e costanti locali di lavoro */
        ProgressMonitor pm;
        BRTask task;
        int progress;
        Modulo modulo;
        String nome = "";

        if (evt.getPropertyName().equals("progress")) {
            pm = this.getProgressMonitor();
            task = this.getTask();
            progress = (Integer)evt.getNewValue();
            pm.setProgress(progress);

            modulo = task.getCurrModulo();
            if (modulo != null) {
                nome = modulo.getNomeChiave();
            }// fine del blocco if

            String message = String.format(nome + " %d%%.\n", progress);
            pm.setNote(message);
            if (pm.isCanceled() || task.isDone()) {

                if (pm.isCanceled()) {
                    task.cancel(true);
                }// fine del blocco if-else

            }

        }

    }


    /**
     * Task astratta per il processo di backup / restore
     */
    private abstract class BRTask extends SwingWorker<Void, Void> {

        /* numero totale di records da backuppare */
        private int totRecord;

        /* contatore di record elaborati */
        private int currRecord = 0;

        /* modulo correntemente in backup */
        private Modulo currModulo;


        @Override
        public Void doInBackground() {

            try { // prova ad eseguire il codice

                /* azzera l'indicatore di prograsso */
                this.setProgress(0);


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            return null;
        }


        @Override
        public void done() {
            if (!this.isCancelled()) {
                Lib.Sist.beep();
                new MessaggioAvviso("Terminato.");
            }// fine del blocco if
        }


        protected int getTotRecord() {
            return totRecord;
        }


        protected void setTotRecord(int totRecord) {
            this.totRecord = totRecord;
        }


        protected int getCurrRecord() {
            return currRecord;
        }


        protected void setCurrRecord(int currRecord) {
            this.currRecord = currRecord;
        }


        public Modulo getCurrModulo() {
            return currModulo;
        }


        protected void setCurrModulo(Modulo currModulo) {
            this.currModulo = currModulo;
        }

    }


    /**
     * Task per il processo di backup
     */
    private class BackupTask extends BRTask {


        @Override
        public Void doInBackground() {
            /* variabili e costanti locali di lavoro */
            boolean continua = true;
            boolean dbAperto = false;
            boolean connAperta = false;

            super.doInBackground();

            try {

                /* determina il numero totale di record da backuppare */
                if (continua) {
                    this.setTotRecord(this.getQuantiRecord());
                }// fine del blocco if

                /* crea il database di backup */
                if (continua) {
                    continua = creaDbBackup();
                    if (continua) {
                        dbAperto = true;
                    }// fine del blocco if
                }// fine del blocco if

                /* apre la connessione */
                if (continua) {
                    continua = creaConnessione();
                    if (continua) {
                        connAperta = true;
                    }// fine del blocco if
                }// fine del blocco if

                /* esegue il backup di tutti i moduli */
                if (continua) {
                    this.backupModuli();
                    this.setProgress(100);  //indica che è terminato
                }// fine del blocco if

                /* chiude la connessione */
                if (connAperta) {
                    getConnessione().close();
                }// fine del blocco if

                /* chiude il database */
                if (dbAperto) {
                    getDatabase().shutdown();
                }// fine del blocco if

            } catch (Exception unErrore) {
                Errore.crea(unErrore);
            }
            return null;
        }


        /**
         * Ritorna il numero totale di record da backuppare.
         * <p/>
         *
         * @return il numero totale di record da backuppare
         */
        private int getQuantiRecord() {
            /* variabili e costanti locali di lavoro */
            int quanti = 0;
            ArrayList<Modulo> moduli;

            try {    // prova ad eseguire il codice
                moduli = getModuliBackup();
                for (Modulo modulo : moduli) {
                    quanti += modulo.query().contaRecords();
                }
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return quanti;
        }


        /**
         * Esegue il backup di tutti i moduli.
         * <p/>
         *
         * @return true se riuscito
         */
        private boolean backupModuli() {
            /* variabili e costanti locali di lavoro */
            boolean continua = true;
            LinkedHashMap<String, TavolaBackup> mappa;

            try {    // prova ad eseguire il codice

                /* esegue il backup per ogni tavola */
                mappa = getTavoleBackup();
                for (TavolaBackup tb : mappa.values()) {

                    /* controlla se il task è cancellato */
                    continua = !this.isCancelled();

                    /* effettua il backup della tavola */
                    if (continua) {
                        continua = this.backupTavola(tb);
                    }// fine del blocco if

                    /* controllo di interruzione */
                    if (!continua) {
                        break;
                    }// fine del blocco if

                }

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return continua;
        }


        /**
         * Esegue il backup di una tavola.
         * <p/>
         *
         * @param tb TavolaBackup cui eseguire il backup
         *
         * @return true se riuscito
         */
        private boolean backupTavola(TavolaBackup tb) {
            /* variabili e costanti locali di lavoro */
            boolean riuscito = false;
            boolean continua = true;
            Connessione conn;
            ArrayList<Campo> campi;
            Modulo modulo;

            try {    // prova ad eseguire il codice

                conn = getConnessione();
                modulo = tb.getModulo();

                /* registra il modulo correntemente in backup */
                if (continua) {
                    this.setCurrModulo(modulo);
                }// fine del blocco if

                /* crea la tavola col campo chiave */
                if (continua) {
                    continua = conn.creaTavola(modulo);
                }// fine del blocco if

                /* crea tutti gli altri campi */
                if (continua) {
                    campi = tb.getListaCampi();
                    for (Campo campo : campi) {
                        if (!campo.equals(modulo.getCampoChiave())) {
                            continua = conn.creaColonna(campo);
                            if (!continua) {
                                break;
                            }// fine del blocco if
                        }// fine del blocco if
                    }// fine del blocco for
                }// fine del blocco if

                /* copia i dati */
                if (continua) {
                    continua = this.backupDati(tb);
                }// fine del blocco if

                /* regola il valore di ritorno */
                if (continua) {
                    riuscito = true;
                }// fine del blocco if

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return riuscito;
        }


        /**
         * Copia i dati dal database dei dati al database di backup.
         * <p/>
         *
         * @param tb TavolaBackup per la quale copiare i dati
         *
         * @return true se riuscito
         */
        private boolean backupDati(TavolaBackup tb) {
            /* variabili e costanti locali di lavoro */
            boolean riuscito = false;
            boolean continua = true;
            Modulo modulo;
            Query query;
            Query queryInsert;
            ArrayList<Campo> campi;
            Dati dati;
            Object valore;
            Connessione connBackup;
            int quanti;

            try {    // prova ad eseguire il codice

                /* recupera il modulo di riferimento */
                modulo = tb.getModulo();

                /* recupera la connessione al db di backup */
                connBackup = getConnessione();

                /* recupera un oggetto dati con tutti i campi e tutti i record
                 * ordinati per codice */
                campi = tb.getListaCampi();
                query = new QuerySelezione(modulo);
                query.setCampi(campi);
                query.setFiltro(modulo.getFiltroBackup());
                query.addOrdine(modulo.getCampoChiave());
                dati = modulo.query().querySelezione(query);

                /* crea una query di inserimento riutilizzabile
                 * contenente tutti i campi */
                queryInsert = new QueryInsert(modulo);
                for (Campo campo : campi) {
                    queryInsert.addCampo(campo);
                }

                /* spazzola i dati e li scrive sul DB di backup */
                for (int k = 0; k < dati.getRowCount(); k++) {

                    if (!this.isCancelled()) {

                        /* riempie i valori della query */
                        for (Campo campo : campi) {
                            valore = dati.getValueAt(k, campo);
                            queryInsert.setValore(campo, valore);
                        }

                        /* esegue la query direttamente sulla connessione
                         * (non passa dai trigger del Modello) */
                        quanti = connBackup.queryModifica(queryInsert);

                        /* verifica il risultato */
                        if (quanti == 1) {
                            this.setCurrRecord(this.getCurrRecord() + 1);
                            int progress = (this.getCurrRecord() * 100) / this.getTotRecord();
                            this.setProgress(progress);
                        } else {
                            continua = false;
                            break;
                        }// fine del blocco if-else

                    } else {
                        continua = false;
                        break;
                    }// fine del blocco if-else

                } // fine del ciclo for

                /* chiude i dati di lettura */
                dati.close();

                /* regola il valore di ritorno */
                if (continua) {
                    riuscito = true;
                }// fine del blocco if


            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return riuscito;
        }

    }   // fine della classe task di backup


    /**
     * Task per il processo di restore
     */
    private class RestoreTask extends BRTask {


        @Override
        public Void doInBackground() {
            /* variabili e costanti locali di lavoro */
            boolean continua = true;
            boolean dbAperto = false;
            boolean connAperta = false;

            super.doInBackground();

            try {

                /* crea il database dal quale effettuare il restore */
                if (continua) {
                    continua = creaDbRestore();
                    if (continua) {
                        dbAperto = true;
                    }// fine del blocco if
                }// fine del blocco if

                /* apre la connessione */
                if (continua) {
                    continua = creaConnessione();
                    if (continua) {
                        connAperta = true;
                    }// fine del blocco if
                }// fine del blocco if

                /* esegue il restore di tutte le tavole */
                if (continua) {
                    this.restoreTavole();
                    this.setProgress(100);  //indica che è terminato
                }// fine del blocco if

                /* chiude la connessione */
                if (connAperta) {
                    getConnessione().close();
                }// fine del blocco if

                /* chiude il database */
                if (dbAperto) {
                    getDatabase().shutdown();
                }// fine del blocco if

                /* Rinfresca le liste di tutti i navigatori del Progetto.*/
                if (continua) {
                    this.refreshNavigatori();
                }// fine del blocco if


            } catch (Exception unErrore) {
                Errore.crea(unErrore);
            }
            return null;
        }


        /**
         * Rinfresca le liste di tutti i navigatori correnti
         * <p/>
         */
        private void refreshNavigatori() {
            /* variabili e costanti locali di lavoro */
            ArrayList<Modulo> moduli;
            Navigatore nav;

            try {    // prova ad eseguire il codice
                /* se eseguito, rinfresca le liste dei navigatori */
                moduli = Progetto.getModuliPreorder();
                for (Modulo modulo : moduli) {
                    nav = modulo.getNavigatoreCorrente();
                    nav.aggiornaLista();
                }
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Esegue il restore di tutte le tavole.
         * <p/>
         *
         * @return true se riuscito
         */
        private boolean restoreTavole() {
            /* variabili e costanti locali di lavoro */
            boolean continua = true;
            ArrayList<TavolaBackup> tavole;
            Modulo modulo;
            int quanti;
            Connessione connBackup;

            try {    // prova ad eseguire il codice

                /* crea un elenco delle tavole di progetto
                * che sono presenti nel database di backup -
                * (effettuerà il restore solo per queste tavole)
                * (Potrebbe darsi che alcune tavole non facessero
                * parte del progetto all'epoca del backup) */
                tavole = this.getTavoleRestoreEffettive();

                /* conta il numero totale di records da recuperare
                 * e lo registra nella variabile */
                connBackup = getConnessione();
                quanti = 0;
                for (TavolaBackup tb : tavole) {
                    modulo = tb.getModulo();
                    quanti += connBackup.contaRecords(modulo);
                }
                this.setTotRecord(quanti);

                /* esegue il restore per ogni tavola
                 * procedendo dal basso verso l'alto */
                for (TavolaBackup tb : tavole) {

                    /* controlla se il task è cancellato */
                    continua = !this.isCancelled();

                    /* effettua il restore del modulo */
                    if (continua) {
                        modulo = tb.getModulo();
                        this.setCurrModulo(modulo);
                        this.restoreDati(tb);
                    } else {
                        break;
                    }// fine del blocco if-else

                }

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return continua;
        }


        /**
         * Ritorna un elenco degli oggetti TavolaBackup che hanno
         * una corrispondente tavola nel database di backup
         * <p/>
         *
         * @return l'elenco dei moduli di backup/restore che hanno una
         *         corrispondente tavola sul DB di backup
         */
        private ArrayList<TavolaBackup> getTavoleRestoreEffettive() {
            /* variabili e costanti locali di lavoro */
            ArrayList<TavolaBackup> tbEffettive = new ArrayList<TavolaBackup>();
            LinkedHashMap<String, TavolaBackup> mappa;
            String tavola;
            Connessione connBackup;

            try {    // prova ad eseguire il codice
                connBackup = getConnessione();
                mappa = getTavoleBackup();
                for (TavolaBackup tb : mappa.values()) {
                    tavola = tb.getTavola();
                    if (connBackup.isEsisteTavola(tavola)) {
                        tbEffettive.add(tb);
                    }// fine del blocco if
                }

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return tbEffettive;
        }


        /**
         * Ritorna un elenco dei campi di una TavolaBackup
         * che sono effettivamente presenti nel database di backup
         * <p/>
         *
         * @param tb TavolaBackup da controllare
         *
         * @return l'elenco dei campi della tavola per i quali esiste
         *         una corrispondente colonna sulla tavola del DB di backup
         */
        private ArrayList<Campo> getCampiRestoreEffettivi(TavolaBackup tb) {
            /* variabili e costanti locali di lavoro */
            ArrayList<Campo> campiEffettivi = new ArrayList<Campo>();
            ArrayList<Campo> campiPrevisti;
            Connessione connBackup;

            try {    // prova ad eseguire il codice
                connBackup = getConnessione();
                campiPrevisti = tb.getListaCampi();
                for (Campo campo : campiPrevisti) {
                    if (connBackup.isEsisteCampo(campo)) {
                        campiEffettivi.add(campo);
                    }// fine del blocco if
                }
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return campiEffettivi;
        }


        /**
         * Copia i dati dal database di backup al database corrente.
         * <p/>
         * Se incontra un errore procede con il record successivo
         *
         * @param tb oggetto TavolaBackup per la quale copiare i dati
         *
         * @return true se riuscito senza errori
         */
        private boolean restoreDati(TavolaBackup tb) {
            /* variabili e costanti locali di lavoro */
            boolean riuscito = true;
            Modulo modulo;
            Query query;
            Query queryInsert;
            ArrayList<Campo> campi;
            Dati dati;
            Object valore;
            Connessione connBackup;
            Connessione connDati;
            int quanti;
            Filtro filtro;

            try {    // prova ad eseguire il codice

                /* recupera il modulo di riferimento */
                modulo = tb.getModulo();

                /* recupera la connessione al db di backup */
                connBackup = getConnessione();

                /* recupera la connessione al db dei dati correnti */
                connDati = modulo.getConnessione();

                /* disattiva l'integrità referenziale */
                connDati.setReferentialIntegrity(false);

                /* svuota la tavola sul DB in uso */
                filtro = modulo.getFiltroRestore();
                connDati.eliminaRecords(modulo, filtro);

                /* recupera l'elenco dei campi della tavola che sono presenti
                 * su entrambi i database
                 * (il database corrente potrebbe avere campi
                 * diversi rispetto all'epoca del backup, dei campi
                 * potrebbero essere stati aggiunti, altri eliminati)*/
                campi = this.getCampiRestoreEffettivi(tb);

                /* recupera dal db di backup un oggetto dati
                 * con tutti i campi effettivi e tutti i record,
                 * ordinati per codice */
                query = new QuerySelezione(modulo);
                query.setCampi(campi);
                query.addOrdine(modulo.getCampoChiave());
                dati = connBackup.querySelezione(query);

                /* crea una query di inserimento riutilizzabile
                 * contenente tutti i campi */
                queryInsert = new QueryInsert(modulo);
                for (Campo campo : campi) {
                    queryInsert.addCampo(campo);
                }

                /* spazzola i dati e li scrive sul DB corrente */
                for (int k = 0; k < dati.getRowCount(); k++) {

                    if (!this.isCancelled()) {

                        /* riempie i valori della query */
                        for (Campo campo : campi) {
                            valore = dati.getValueAt(k, campo);
                            queryInsert.setValore(campo, valore);
                        }

                        /* esegue la query direttamente sulla connessione
                         * (non passa dai trigger del Modello) */
                        quanti = 0;
                        quanti = connDati.queryModifica(queryInsert);
                        this.setCurrRecord(this.getCurrRecord() + 1);
                        int progress = (this.getCurrRecord() * 100) / this.getTotRecord();
                        this.setProgress(progress);

                        /* se c'è stato un errore spegne il flag riuscito
                         * ma procede ugualmente con i successivi */
                        if (quanti != 1) {
                            riuscito = false;
                        }// fine del blocco if

                    } else {  // interrotto
                        riuscito = false;
                        break;
                    }// fine del blocco if-else

                } // fine del ciclo for

                /* chiude i dati di lettura */
                dati.close();

                /* riattiva l'integrità referenziale */
                connDati.setReferentialIntegrity(true);


            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return riuscito;
        }


    } // fine della classe task di restore


    /**
     * Oggetto rappresentante una tavola con i campi.
     * </p>
     * Costruito in base ai moduli che utilizzano la tavola.<br>
     * Comprende i campi di tutti i moduli che utilizzano la tavola<br>
     * Di solito l'elenco campi coincide con i campi del Modulo, ma può
     * essere diverso se più moduli usano la tavola.
     */
    private final class TavolaBackup {

        /* nome della tavola */
        private String tavola;

        /* modulo di riferimento */
        private Modulo modulo;

        /* elenco di campi - la chiave è il nome interno del campo */
        private LinkedHashMap<String, Campo> campi;


        /**
         * Costruttore completo.
         * <p/>
         *
         * @param tavola di riferimento
         * @param modulo di riferimento
         */
        public TavolaBackup(String tavola, Modulo modulo) {
            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili di istanza coi parametri */
            this.setTavola(tavola);
            this.setModulo(modulo);

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
            LinkedHashMap<String, Campo> campi;

            try { // prova ad eseguire il codice

                /* crea una mappa di campi vuota */
                campi = new LinkedHashMap<String, Campo>();
                this.setCampi(campi);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Aggiunge un campo.
         * <p/>
         * Il campo viene aggiunto solo se non è già esistente.
         *
         * @param campo da aggiungere
         */
        public void addCampo(Campo campo) {
            /* variabili e costanti locali di lavoro */
            LinkedHashMap<String, Campo> campi;
            String chiave;
            Campo unCampo;

            try {    // prova ad eseguire il codice
                chiave = campo.getNomeInterno();
                campi = this.getCampi();
                unCampo = campi.get(chiave);
                if (unCampo == null) {
                    campi.put(chiave, campo);
                }// fine del blocco if
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Ritorna la lista dei campi.
         * <p/>
         *
         * @return la lista dei campi
         */
        public ArrayList<Campo> getListaCampi() {
            /* variabili e costanti locali di lavoro */
            ArrayList<Campo> lista = new ArrayList<Campo>();
            LinkedHashMap<String, Campo> mappa;

            try {    // prova ad eseguire il codice
                mappa = this.getCampi();
                for (Campo campo : mappa.values()) {
                    lista.add(campo);
                }
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        private Modulo getModulo() {
            return modulo;
        }


        private void setModulo(Modulo modulo) {
            this.modulo = modulo;
        }


        private String getTavola() {
            return tavola;
        }


        private void setTavola(String tavola) {
            this.tavola = tavola;
        }


        private LinkedHashMap<String, Campo> getCampi() {
            return campi;
        }


        private void setCampi(LinkedHashMap<String, Campo> campi) {
            this.campi = campi;
        }


    } // fine della classe 'interna'


    private DialogoBR getDialogo() {
        return dialogo;
    }


    private void setDialogo(DialogoBR dialogo) {
        this.dialogo = dialogo;
    }


    private Db getDatabase() {
        return database;
    }


    private void setDatabase(Db database) {
        this.database = database;
    }


    private Connessione getConnessione() {
        return connessione;
    }


    private void setConnessione(Connessione connessione) {
        this.connessione = connessione;
    }


    private ProgressMonitor getProgressMonitor() {
        return progressMonitor;
    }


    private void setProgressMonitor(ProgressMonitor progressMonitor) {
        this.progressMonitor = progressMonitor;
    }


    private BRTask getTask() {
        return task;
    }


    private void setTask(BRTask task) {
        this.task = task;
    }


    private LinkedHashMap<String, TavolaBackup> getTavoleBackup() {
        return tavoleBackup;
    }


    private void setTavoleBackup(LinkedHashMap<String, TavolaBackup> tavoleBackup) {
        this.tavoleBackup = tavoleBackup;
    }
}// fine della classe
