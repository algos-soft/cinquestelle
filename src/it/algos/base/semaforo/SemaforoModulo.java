/**
 * Title:     SemaforoModulo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      3-gen-2007
 */
package it.algos.base.semaforo;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.dialogo.Dialogo;
import it.algos.base.dialogo.DialogoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.utenti.UtentiModulo;
import it.algos.base.wrapper.CampoValore;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * SemaforoModulo
 * </p>
 * Questa classe concreta: <ul>
 * <li> Contiene tutti i riferimenti agli oggetti delle classi che servono
 * per gestire una parte del programma </li>
 * <li>  </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 27-dic-2006 ore 6.55.30
 */
public final class SemaforoModulo extends ModuloBase implements Semaforo {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = Semaforo.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = Semaforo.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = Semaforo.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito) <br>
     */
    public SemaforoModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(NOME_CHIAVE);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(NOME_PROGRAMMA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo <br>
     *
     * @param unNodo nodo dell'albero moduli
     */
    public SemaforoModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(NOME_CHIAVE, unNodo);

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
        /* selezione del modello (obbligatorio) */
        super.setModello(new SemaforoModello());

        /* regola il voce della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il voce di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

        /* è un modulo fisso */
        this.setModuloFisso(true);

    }// fine del metodo inizia


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * <br>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * un'istanza provvisoria del modulo <br>
     * Questa istanza viene usata solo per portarsi il percorso della
     * classe (implicito) ed il nome chiave (esplicito) <br>
     * La creazione definitiva del Modulo viene delegata alla classe
     * Progetto nel metodo creaModulo() <br>
     */
    protected void creaModuli() {
        super.creaModulo(new UtentiModulo());
    }// fine del metodo


    /**
     * Aggiunge i moduli (e le tabelle) al menu.
     * <br>
     * Aggiunge alla collezione moduli (di questo modulo), gli eventuali
     * moduli (o tabelle), che verranno poi inserite nel menu moduli e
     * tabelle, dalla classe Navigatore <br>
     * I moduli e le tabelle appaiono nei rispettivi menu, nell'ordine in
     * cui sono elencati in questo metodo <br>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * il nome-chiave del modulo <br>
     */
    protected void addModuliVisibili() {
    }// fine del metodo


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static SemaforoModulo get() {
        return (SemaforoModulo)ModuloBase.get(NOME_CHIAVE);
    }


    /**
     * Tenta di accendere un semaforo per l'utente corrente.
     * <p/>
     * - Cerca il semaforo nella tavola (se non esiste, lo crea ora);
     * - Se il semaforo è scaduto, lo spegne;
     * - Controlla se il semaforo è spento, ritentando
     * per il tempo massimo consentito;
     * - Registra i nuovi dati del semaforo;
     * - Ritorna true se riuscito.
     *
     * @param chiave del semaforo da accendere
     * @param ttl tempo di vita del semaforo in secondi
     * @param retrySec tempo di attesa massimo concesso in secondi
     * @param showMex true per mostrare un messaggio in caso di fallimento
     *
     * @return true se il semaforo è stato acceso
     */
    public boolean setSemaforo(String chiave, int ttl, int retrySec, boolean showMex) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        Sem semaforo;
        boolean fine = false;
        Timestamp startTime;
        Timestamp currTime;
        int elapsed;
        Dialogo dialogo;
        String testo;

        try {    // prova ad eseguire il codice

            /* Cerca il semaforo nella tavola (se non esiste, lo crea ora) */
            semaforo = new Sem(chiave);

            /* - Se il semaforo è acceso ma è scaduto, lo spegne */
            if (semaforo.isAcceso()) {
                if (semaforo.isScaduto()) {
                    semaforo.spegni();
                }// fine del blocco if
            }// fine del blocco if

            /* - Controlla se il semaforo è spento, ritentando
             *   per il tempo massimo consentito */
            startTime = Progetto.getTimestampCorrente();
            while (!fine) {
                if (semaforo.isSpento()) {
                    fine = true;
                    riuscito = true;
                } else {

                    /* rilegge dal database se non è trascorso il
                     * tempo massimo di attesa */
                    currTime = Progetto.getTimestampCorrente();
                    elapsed = Lib.Data.secondi(currTime, startTime);
                    if (elapsed >= retrySec) {
                        fine = true;
                        riuscito = false;
                    } else {
                        semaforo.aggiorna();
                        Thread.sleep(500);  // pausa di mezzo secondo per non sovraccaricare il server
                    }// fine del blocco if-else

                }// fine del blocco if-else

            }// fine del blocco while

            /* Accende il semaforo e lo registra sul database */
            if (riuscito) {
                semaforo.accendi(ttl);
                semaforo.registra();
            }// fine del blocco if

            /* Se non riuscito, se previsto mostra un messaggio */
            if (!riuscito) {
                if (showMex) {
                    testo = "Operazione non consentita.\n";
                    testo += "Il semaforo " + semaforo.getChiave();
                    testo += " e' attualmente impegnato dall'utente ";
                    testo += semaforo.getNomeUtente() + ".";
                    dialogo = DialogoFactory.conferma("Semaforo occupato");
                    dialogo.setMessaggio(testo);
                    Lib.Sist.beep();
                    dialogo.avvia();
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Tenta di accendere un semaforo per l'utente corrente.
     * <p/>
     * Usa il tempo di attesa di default
     *
     * @param chiave del semaforo da accendere
     * @param ttl tempo di vita del semaforo in secondi
     * @param showMex true per mostrare un messaggio in caso di fallimento
     *
     * @return true se il semaforo è stato acceso
     */
    public boolean setSemaforo(String chiave, int ttl, boolean showMex) {
        return this.setSemaforo(chiave, ttl, WAIT_DEFAULT, showMex);
    }


    /**
     * Tenta di accendere un semaforo per l'utente corrente.
     * <p/>
     * Usa il tempo di attesa di default
     *
     * @param chiave del semaforo da accendere
     * @param ttl tempo di vita del semaforo in secondi
     *
     * @return true se il semaforo è stato acceso
     */
    public boolean setSemaforo(String chiave, int ttl) {
        return this.setSemaforo(chiave, ttl, WAIT_DEFAULT, MEX_DEFAULT);
    }


    /**
     * Tenta di accendere un semaforo per l'utente corrente.
     * <p/>
     * Usa il tempo di attesa di default
     *
     * @param chiave del semaforo da accendere
     * @param showMex true per mostrare un messaggio in caso di fallimento
     *
     * @return true se il semaforo è stato acceso
     */
    public boolean setSemaforo(String chiave, boolean showMex) {
        return this.setSemaforo(chiave, TTL_DEFAULT, WAIT_DEFAULT, showMex);
    }


    /**
     * Tenta di accendere un semaforo per l'utente corrente.
     * <p/>
     * Usa il tempo di attesa di default
     * Usa il ttl di default
     *
     * @param chiave del semaforo da accendere
     *
     * @return true se il semaforo è stato acceso
     */
    public boolean setSemaforo(String chiave) {
        return this.setSemaforo(chiave, TTL_DEFAULT, WAIT_DEFAULT, MEX_DEFAULT);
    }


    /**
     * Spegne un semaforo.
     * <p/>
     * Cerca il semaforo sul database<br>
     * Se lo trova, pone il flag a False
     *
     * @param chiave del semaforo da spegnere
     */
    public void clearSemaforo(String chiave) {
        /* variabili e costanti locali di lavoro */
        int codice;

        try {    // prova ad eseguire il codice
            codice = this.query().valoreChiave(Semaforo.Cam.chiave.get(), chiave);
            if (codice > 0) {
                this.query().registraRecordValore(codice, Semaforo.Cam.flag.get(), false);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new SemaforoModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main


    /**
     * Classe 'interna'
     * </p>
     * Rappresentazione di un semaforo<br>
     * Ogni oggetto di questo tipo che viene creato ha un
     * corrispondente record sul database.
     */
    private final class Sem {

        /**
         * id del record
         */
        private int id;

        /**
         * chiave identificativa del semaforo
         */
        private String chiave;

        /**
         * flag indicante se il semaforo è acceso o spento
         */
        private boolean flag;

        /**
         * utente che ha creato il semaforo
         */
        private int utente;

        /**
         * timestamp di creazione del semaforo
         */
        private Timestamp time;

        /**
         * time to live del semaforo in secondi
         */
        private int ttl;


        /**
         * Costruttore completo con parametri
         * <p/>
         * Se il semaforo richiesto non esiste sul database lo crea ora.<br>
         * Se esiste, recupera i dati dal database
         *
         * @param chiave del semaforo
         */
        public Sem(String chiave) {
            /* rimanda al costruttore della superclasse */
            super();

            try { // prova ad eseguire il codice
                this.setChiave(chiave);
                this.regolaSemaforo();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }// fine del metodo costruttore completo


        /**
         * Crea un semaforo se non esiste e regola questo oggetto
         * con i dati letti dal database.
         * <p/>
         */
        private void regolaSemaforo() {
            /* variabili e costanti locali di lavoro */
            Dati dati;
            ArrayList<CampoValore> valori;
            CampoValore cv;

            try {    // prova ad eseguire il codice

                /* recupera i dati del semaforo dal database */
                dati = this.getDatiSemaforo();

                /* se non trovato, crea ora il nuovo record di semaforo
                 * e recupera nuovamente i dati */
                if (dati.getRowCount() == 0) {
                    dati.close(); // chiude i dati precedenti
                    valori = new ArrayList<CampoValore>();
                    cv = new CampoValore(Cam.chiave.get(), this.getChiave());
                    valori.add(cv);
                    query().nuovoRecord(valori);
                    dati = this.getDatiSemaforo();
                }// fine del blocco if

                /* regola questo oggetto in base ai dati */
                this.regolaSemaforo(dati);

                /* chiude l'oggetto dati */
                dati.close();

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Regola questo oggetto con i dati risultanti da una query.
         * <p/>
         *
         * @param dati risultanti dalla query sul database
         */
        private void regolaSemaforo(Dati dati) {
            /* variabili e costanti locali di lavoro */
            Campo campo;
            Object valore;

            try { // prova ad eseguire il codice

                campo = getCampoChiave();
                valore = dati.getValueAt(0, campo);
                this.setId((Integer)valore);

                campo = getCampo(Cam.flag.get());
                valore = dati.getValueAt(0, campo);
                this.setFlag((Boolean)valore);

                campo = getCampo(Cam.utente.get());
                valore = dati.getValueAt(0, campo);
                this.setUtente((Integer)valore);

                campo = getCampo(Cam.time.get());
                valore = dati.getValueAt(0, campo);
                this.setTime((Timestamp)valore);

                campo = getCampo(Cam.ttl.get());
                valore = dati.getValueAt(0, campo);
                this.setTtl((Integer)valore);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch


        }// fine del metodo costruttore completo


        /**
         * Recupera dal database i dati relativi a questo semaforo.
         * <p/>
         *
         * @return oggetto contenente i dati (chiuderlo dopo l'uso!)
         */
        private Dati getDatiSemaforo() {
            /* variabili e costanti locali di lavoro */
            Dati dati = null;
            String chiave;
            Filtro filtro;
            Query query;

            try {    // prova ad eseguire il codice

                /* recupera la chiave del semaforo */
                chiave = this.getChiave();

                /* crea un filtro per selezionare il record */
                filtro = FiltroFactory.crea(Cam.chiave.get(), chiave);

                /* crea una query per recuperare i dati */
                query = new QuerySelezione(getModulo());
                query.addCampo(getCampoChiave());
                query.addCampo(Cam.flag.get());
                query.addCampo(Cam.utente.get());
                query.addCampo(Cam.time.get());
                query.addCampo(Cam.ttl.get());
                query.setFiltro(filtro);

                /* esegue la query */
                dati = query().querySelezione(query);

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return dati;
        }


        /**
         * Controlla se questo semaforo è scaduto.
         * <p/>
         * Un semaforo è scaduto se il timestamp di creazione più il ttl
         * superano il timestamp corrente
         *
         * @return true se è scaduto
         */
        public boolean isScaduto() {
            /* variabili e costanti locali di lavoro */
            boolean scaduto = false;
            Timestamp creato;
            Timestamp scadenza;
            Timestamp corrente;
            int ttl;

            try {    // prova ad eseguire il codice
                creato = this.getTime();
                ttl = this.getTtl();
                scadenza = Lib.Data.add(creato, ttl);
                corrente = Progetto.getTimestampCorrente();
                if (scadenza.before(corrente)) {
                    scaduto = true;
                }// fine del blocco if
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return scaduto;
        }


        /**
         * Accende il semaforo.
         * <p/>
         *
         * @param ttl il tempo di vita del semaforo
         */
        public void accendi(int ttl) {
            try {    // prova ad eseguire il codice
                this.setFlag(true);
                this.setTime(Progetto.getTimestampCorrente());
                this.setTtl(ttl);
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Spegne il semaforo.
         * <p/>
         */
        public void spegni() {
            try {    // prova ad eseguire il codice
                this.setFlag(false);
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Controlla se il semaforo è spento.
         * <p/>
         *
         * @return true se è spento
         */
        public boolean isSpento() {
            return !this.isFlag();
        }


        /**
         * Controlla se il semaforo è acceso.
         * <p/>
         *
         * @return true se è acceso
         */
        public boolean isAcceso() {
            return this.isFlag();
        }


        /**
         * Aggiorna i dati del semaforo rileggendoli dal database.
         * <p/>
         */
        public void aggiorna() {
            /* variabili e costanti locali di lavoro */
            Dati dati;

            try {    // prova ad eseguire il codice
                dati = this.getDatiSemaforo();
                this.regolaSemaforo(dati);
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Registra i dati del semaforo sul database.
         * <p/>
         */
        public void registra() {
            /* variabili e costanti locali di lavoro */
            int codice;
            boolean flag;
            int utente;
            Timestamp time;
            int ttl;
            ArrayList<CampoValore> lista;

            try {    // prova ad eseguire il codice
                codice = this.getId();
                flag = this.isFlag();
                utente = this.getUtente();
                time = this.getTime();
                ttl = this.getTtl();

                lista = new ArrayList<CampoValore>();

                lista.add(new CampoValore(Cam.flag.get(), flag));
                lista.add(new CampoValore(Cam.utente.get(), utente));
                lista.add(new CampoValore(Cam.time.get(), time));
                lista.add(new CampoValore(Cam.ttl.get(), ttl));

                query().registraRecordValori(codice, lista);

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Restituisce il nome dell'utente proprietario del semaforo.
         * <p/>
         *
         * @return il nome dell'utente
         */
        public String getNomeUtente() {
            /* variabili e costanti locali di lavoro */
            String nome = "";
            int id;
            UtentiModulo modUtenti;

            try {    // prova ad eseguire il codice
                id = this.getUtente();
                modUtenti = Progetto.getModuloUtenti();
                nome = modUtenti.getNomeUtente(id);
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return nome;
        }


        private int getId() {
            return id;
        }


        private void setId(int id) {
            this.id = id;
        }


        private String getChiave() {
            return chiave;
        }


        private void setChiave(String chiave) {
            this.chiave = chiave;
        }


        private boolean isFlag() {
            return flag;
        }


        private void setFlag(boolean flag) {
            this.flag = flag;
        }


        private int getUtente() {
            return utente;
        }


        private void setUtente(int utente) {
            this.utente = utente;
        }


        private Timestamp getTime() {
            return time;
        }


        private void setTime(Timestamp time) {
            this.time = time;
        }


        private int getTtl() {
            return ttl;
        }


        private void setTtl(int ttl) {
            this.ttl = ttl;
        }
    } // fine della classe 'interna'

}// fine della classe
