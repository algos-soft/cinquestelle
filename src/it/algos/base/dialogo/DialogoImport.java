package it.algos.base.dialogo;
/**
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      13-apr-2007
 */

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pref.Pref;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.FtpWrap;

import java.util.ArrayList;

/**
 * Dialogo per creare/importare dati in un modulo vuoto.
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 15-apr-2007 ore 19.59.27
 */
public abstract class DialogoImport extends DialogoAnnullaConferma {

    private boolean mostraDialogo;

    protected final static String SEP = ";";

    protected final static String TAG = "----";

    private FtpWrap ftp;

    private Scelta scelta;


    /**
     * Costruttore completo.
     *
     * @param modulo di riferimento
     */
    public DialogoImport(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

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
    }// fine del metodo inizia


    /**
     * Regolazione iniziale del dialogo, senza avviarlo.
     * <p/>
     * Metodo invocato dal bottone specifico:
     * nella toolbar della lista o nel menu Archivio <br>
     * Metodo invocato dal ciclo inizia <br>
     *
     * @param mess specifico
     *
     * @return campo di selezione modalità di importazione
     */
    protected Campo regolaDialogo(String mess) {
        /* variabili e costanti locali di lavoro */
        Campo campoScelta = null;
        String titolo;
        String messaggio;
        String azione = "Importa";

        try { // prova ad eseguire il codice
            /* testi */
            titolo = "Importazione";

            messaggio = mess;
            messaggio += "\n";
            messaggio += "\nSeleziona una modalità di importazione";
            messaggio += "\n";

            this.setTitolo(titolo);
            this.getBottoneConferma().setText(azione);
            this.setMessaggio(messaggio);

            /* dialogo di scelta tipo stampa */
            campoScelta = CampoFactory.radioInterno("scelta");
            campoScelta.decora().eliminaEtichetta();
            campoScelta.setValoriInterni(Scelta.getLista());
            campoScelta.setUsaNonSpecificato(false);
            campoScelta.decora().obbligatorio();
            campoScelta.setValore(Scelta.completo.ordinal() + 1);
            this.addCampo(campoScelta);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campoScelta;
    }


    /**
     * Creazione dialogo.
     * <p/>
     * Metodo invocato dal bottone specifico:
     * nella toolbar della lista o nel menu Archivio <br>
     * Metodo invocato dal ciclo inizia <br>
     *
     * @param mess specifico
     */
    protected void creaDialogo(String mess) {
        /* variabili e costanti locali di lavoro */
        Campo campoScelta;
        int scelta;

        try { // prova ad eseguire il codice
            /* Regolazione iniziale del dialogo, senza avviarlo */
            campoScelta = this.regolaDialogo(mess);

            this.avvia();

            /* esegue l'operazione */
            if (this.isConfermato()) {
                scelta = (Integer)campoScelta.getValore();
                this.setScelta(Scelta.getScelta(scelta));
                this.crea();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea tutti i records.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     * <p/>
     * Recupera i dati dalla pagina ISO 4217 di wikipedia <br>
     */
    protected void crea() {
        /* variabili e costanti locali di lavoro */
        Scelta scelta;

        try { // prova ad eseguire il codice
            scelta = this.getScelta();
            if (scelta == Scelta.cancellaCompleto) {
                this.eliminaRecords();
            }// fine del blocco if

            this.creaRecords();
            this.caricaRecords();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea tutti i records, solo se la tavola è vuota.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     */
    protected void creaCondizionato() {
        try { // prova ad eseguire il codice
            if (this.getModulo().query().contaRecords() == 0) {
                this.setScelta(Scelta.modifica);
                this.crea();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Elimina tutti i records esistenti.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     */
    private void eliminaRecords() {
        /* variabili e costanti locali di lavoro */
        Modulo mod;

        try { // prova ad eseguire il codice

            mod = this.getModulo();
            mod.query().eliminaRecords();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea tutti i records.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaRecords() {
    }


    /**
     * Crea un singolo record.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     *
     * @param mod modulo di riferimento per la query
     * @param campi valore da registrare
     * @param campoCheck di controllo per l'esistenza del record
     * @param valore di controllo per l'esistenza del record
     */
    protected void creaRecord(Modulo mod,
                              ArrayList<CampoValore> campi,
                              String campoCheck,
                              Object valore) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int cod;
        Scelta scelta;
        boolean esiste;

        try { // prova ad eseguire il codice
            scelta = this.getScelta();
            continua = (scelta != null);

            if (continua) {
                switch (scelta) {
                    case modifica:
                        cod = mod.query().valoreChiave(campoCheck, valore);
                        if (cod != 0) {
                            mod.query().registraRecordValori(cod, campi);
                        }// fine del blocco if-else
                        break;
                    case aggiunta:
                        esiste = mod.query().isEsisteRecord(campoCheck, valore);
                        if (!esiste) {
                            mod.query().nuovoRecord(campi);
                        }// fine del blocco if
                        break;
                    case completo:
                        cod = mod.query().valoreChiave(campoCheck, valore);
                        if (cod == 0) {
                            mod.query().nuovoRecord(campi);
                        } else {
                            mod.query().registraRecordValori(cod, campi);
                        }// fine del blocco if-else
                        break;
                    case cancellaCompleto:
                        mod.query().nuovoRecord(campi);
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea un singolo record.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     *
     * @param mod modulo di riferimento per la query
     * @param campi valore da registrare
     * @param filtro di controllo per l'esistenza del record
     */
    protected void creaRecord(Modulo mod, ArrayList<CampoValore> campi, Filtro filtro) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int cod;
        Scelta scelta;
        boolean manca;

        try { // prova ad eseguire il codice
            scelta = this.getScelta();
            continua = (scelta != null);

            if (continua) {
                switch (scelta) {
                    case modifica:
                        cod = mod.query().valoreChiave(filtro);
                        if (cod != 0) {
                            mod.query().registraRecordValori(cod, campi);
                        }// fine del blocco if-else
                        break;
                    case aggiunta:
                        manca = mod.query().nessunRecord(filtro);
                        if (manca) {
                            mod.query().nuovoRecord(campi);
                        }// fine del blocco if
                        break;
                    case completo:
                        cod = mod.query().valoreChiave(filtro);
                        if (cod == 0) {
                            mod.query().nuovoRecord(campi);
                        } else {
                            mod.query().registraRecordValori(cod, campi);
                        }// fine del blocco if-else
                        break;
                    case cancellaCompleto:
                        mod.query().nuovoRecord(campi);
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Visualizza tutti i records nella lista.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     */
    protected void caricaRecords() {
        /* variabili e costanti locali di lavoro */
        Modulo mod;

        try { // prova ad eseguire il codice

            mod = this.getModulo();
            mod.getNavigatoreCorrente().getLista().caricaTutti();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    protected boolean isMostraDialogo() {
        return mostraDialogo;
    }


    protected void setMostraDialogo(boolean mostraDialogo) {
        this.mostraDialogo = mostraDialogo;
    }


    protected Scelta getScelta() {
        return scelta;
    }


    protected void setScelta(Scelta scelta) {
        this.scelta = scelta;
    }


    protected FtpWrap getFtp() {
        return ftp;
    }


    protected void setFtp(FtpWrap ftp) {
        this.ftp = ftp;
    }


    /**
     * Classe interna Enumerazione.
     */
    public enum Scelta {

        aggiunta("Solo aggiunta dei record mancanti", false),
        modifica("Solo modifica dei record esistenti", false),
        completo("Aggiunta e modifica", false),
        cancellaCompleto("Cancella tutti i record e ricarica", true),;

        /**
         * voce da utilizzare
         */
        private String titolo;

        /**
         * flag programmatore
         */
        private boolean programmatore;


        /**
         * Costruttore completo con parametri.
         *
         * @param titolo utilizzato nei popup
         * @param programmatore flag programmatore
         */
        Scelta(String titolo, boolean programmatore) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setTitolo(titolo);
                this.setProgrammatore(programmatore);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Ritorna una lista contenente tutti gli elementi della Enumerazione.
         * <p/>
         */
        public static ArrayList<Scelta> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<Scelta> lista = null;
            boolean programmatore;

            try { // prova ad eseguire il codice
                /* recupera il flag */
                programmatore = Pref.Cost.prog.is();

                /* crea la lista */
                lista = new ArrayList<Scelta>();

                /* spazzola tutta la Enum */
                for (Scelta unaScelta : values()) {
                    if (programmatore) {
                        lista.add(unaScelta);
                    } else {
                        if (!unaScelta.isProgrammatore()) {
                            lista.add(unaScelta);
                        }// fine del blocco if
                    }// fine del blocco if-else
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        /**
         * Ritorna un singolo elemento.
         * <p/>
         * La posizione inizia da 1 <br>
         */
        public static Scelta getScelta(int pos) {
            /* variabili e costanti locali di lavoro */
            Scelta scelta = null;

            try { // prova ad eseguire il codice

                /* spazzola tutta la Enum */
                for (Scelta unaScelta : values()) {
                    if ((unaScelta.ordinal() + 1) == pos) {
                        scelta = unaScelta;
                        break;
                    }// fine del blocco if

                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return scelta;
        }


        /**
         * Sovrascrive toString per tornare la descrizione.
         * <p/>
         */
        public String toString() {
            return this.getTitolo();
        }


        private String getTitolo() {
            return titolo;
        }


        private void setTitolo(String titolo) {
            this.titolo = titolo;
        }


        private boolean isProgrammatore() {
            return programmatore;
        }


        private void setProgrammatore(boolean programmatore) {
            this.programmatore = programmatore;
        }
    }// fine della classe

}// fine della classe
