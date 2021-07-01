/**
 * Title:     ModuloTemp
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      11-mag-2007
 */
package it.algos.base.modulo;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.Db;
import it.algos.base.database.DbFactory;
import it.algos.base.database.sql.implem.hsqldb.DbSqlHsqldb;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modello.Modello;
import it.algos.base.modello.ModelloAlgos;

import java.util.ArrayList;

/**
 * Modulo con registrazione dei dati su database HSQLDB temporaneo in memoria<br>
 * Può essere costruito in ogni momento<br>
 * Il Database è distinto dal database di progetto, pertanto non si possono
 * visualizzare in lista campi linkati.
 * E' invece possibile creare campi linkati ed usarli in scheda, perché i valori
 * vengono caricati con query separata.
 * Questo Modulo comprende già un modello dati<br>
 * Nel costruttore vanno passati il nome del modulo e i campi da aggiungere al
 * modello (oltre ai campi standard che vengono aggiunti automaticamente).
 * <p/>
 * Attenzione! al termine dell'utilizzo, chiamare sempre il metodo chiude() del
 * modulo per chiudere la connessione e spegnere il database!
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  11-mag-2007
 */
public class ModuloMemoria extends ModuloBase {

    /**
     * campi specifici del modulo (oltre ai campi standard)
     */
    private ArrayList<Campo> campiSpecifici;

    /**
     * mostra il campo ordinamento
     */
    private boolean isOrdinamentoVisibile;

    /**
     * flag - se tutti i campi specifici devono essere visibili nella vista di default
     */
    private boolean tuttiVisibili;


    /**
     * Costruttore completo <br>
     *
     * @param nome nome interno del modulo
     * @param campi campi specifici del modulo (oltre ai campi standard)
     * @param visibili true rende automaticamente visibili tutti i campi specifici
     * false mantiene la visibilità dei singoli campi in ingresso
     */
    public ModuloMemoria(String nome, ArrayList<Campo> campi, boolean visibili) {

        super(nome, null);

        this.setCampiSpecifici(campi);
        this.setTuttiVisibili(visibili);

        /**
         * regolazioni iniziali di riferimenti e variabili
         */
        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }/* fine del blocco try-catch */

    }/* fine del metodo costruttore completo */


    /**
     * Costruttore completo
     * <p/>
     * Assegna un nome automatico casuale al modulo
     * Rende visibili in vista default tutti i campi specifici
     *
     * @param nome nome interno del modulo
     * @param campi campi specifici del modulo (oltre ai campi standard)
     */
    public ModuloMemoria(String nome, ArrayList<Campo> campi) {
        this(nome, campi, true);
    }/* fine del metodo costruttore completo */


    /**
     * Costruttore completo
     * <p/>
     * Assegna un nome automatico casuale al modulo
     *
     * @param campi campi specifici del modulo (oltre ai campi standard)
     * @param visibili true rende automaticamente visibili tutti i campi specifici
     * false mantiene la visibilità dei singoli campi in ingresso
     */
    public ModuloMemoria(ArrayList<Campo> campi, boolean visibili) {
        this(Lib.Testo.getTestoRandom(10), campi, visibili);
    }/* fine del metodo costruttore completo */


    /**
     * Costruttore completo
     * <p/>
     * Usa un nome automatico casuale per il modulo
     * Rende visibili in vista default tutti i campi specifici
     *
     * @param campi campi specifici del modulo (oltre ai campi standard)
     */
    public ModuloMemoria(ArrayList<Campo> campi) {
        this(campi, true);
    }/* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        DbSqlHsqldb db;
        Modello modello;

        try { // prova ad eseguire il codice

            /* crea e assegna un database temporaneo in memoria al modulo */
            db = (DbSqlHsqldb)DbFactory.crea(Db.SQL_HSQLDB);
            db.setModoFunzionamento(Db.MODO_STAND_ALONE);
            db.setTipoAccessoDati(Db.ACCESSO_DATI_MEMORIA);
            db.setNomeDatabase(this.getNomeChiave());
            db.setLogin(db.getLoginDefault());
            db.setPassword(db.getPasswordDefault());
            this.setDb(db);

            /* crea e assegna il modello dati */
            modello = new ModelloMemoria();
            this.setModello(modello);

            this.setModoAvvio(ModoAvvio.senzaGui);

            this.setCampoOrdineVisibile(false);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }/* fine del metodo inizia */


    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Anche eventuale creazione <br>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Regola il Navigatore di default <br>
     * Crea altri Navigatori (oltre a quello standard) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override
    protected void creaNavigatori() {
        this.getNavigatoreDefault().setUsaStatusBarLista(false);
    }


    private ArrayList<Campo> getCampiSpecifici() {
        return campiSpecifici;
    }


    private void setCampiSpecifici(ArrayList<Campo> campiSpecifici) {
        this.campiSpecifici = campiSpecifici;
    }


    private boolean isCampoOrdineVisibile() {
        return isOrdinamentoVisibile;
    }


    public void setCampoOrdineVisibile(boolean ordinamentoVisibile) {
        isOrdinamentoVisibile = ordinamentoVisibile;
    }


    private boolean isTuttiVisibili() {
        return tuttiVisibili;
    }


    private void setTuttiVisibili(boolean tuttiVisibili) {
        this.tuttiVisibili = tuttiVisibili;
    }


    /**
     * Modello dati per il modulo
     * </p>
     */
    protected class ModelloMemoria extends ModelloAlgos {

        /**
         * Costruttore completo con parametri. <br>
         */
        public ModelloMemoria() {
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

            /* regola e aggiunge i campi */
            this.regolaCampi();

            /* non usa l'integrità referenziale per eventuali campi linkati */
            this.setUsaIntegritaReferenziale(false);
        }


        public boolean inizializza(Modulo unModulo) {
            return super.inizializza(unModulo);
        } /* fine del metodo */


        private void regolaCampi() {
            /* variabili e costanti locali di lavoro */
            ArrayList<Campo> specifici;

            try { // prova ad eseguire il codice

                /* se richiesto, rende visibili in lista i campi specifici */
                if (isTuttiVisibili()) {
                    specifici = getCampiSpecifici();
                    for (Campo campo : specifici) {
                        campo.setVisibileVistaDefault();
                    }
                }// fine del blocco if


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        } /* fine del metodo */


        protected void creaCampi() {
            /* variabili e costanti locali di lavoro */
            ArrayList<Campo> specifici;

            try { // prova ad eseguire il codice
                super.creaCampi();

                if (isCampoOrdineVisibile()) {
                    this.getCampoOrdine().setVisibileVistaDefault();
                }// fine del blocco if

                /* aggiuge i campi specifici di questo modulo */
                specifici = getCampiSpecifici();
                for (Campo campo : specifici) {
                    this.addCampo(campo);
                }

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        } /* fine del metodo creazioneCampiBase */


        /**
         * Recupero dei dati standard.
         * <p/>
         * Non viene eseguito per un modulo Memoria.
         */
        public void recuperaDatiStandard() {
        } // fine del metodo

    } // fine della classe 'interna'

} // fine della classe
