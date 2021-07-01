/**
 * Title:     SemaforoModello
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      3-gen-2007
 */
package it.algos.base.semaforo;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.utenti.Utenti;
import it.algos.base.wrapper.CampoValore;

import java.util.ArrayList;

/**
 * Tracciato record della tavola Semafori.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea il <strong>tracciato record</strong> (Abstract Data Types) di una
 * tavola </li>
 * <li> Mantiene il nome della tavola di archivo dove sono registrati tutti i
 * dati (records) del modello </li>
 * <li> Crea i <strong>campi</strong> di questo modello (oltre a quelli base
 * della superclasse) </li>
 * <li> Un eventuale file di dati iniziali va regolato come percorso e nomi dei
 * campi presenti </li>
 * <li> Eventuali <strong>moduli e tabelle</strong> vanno creati nel metodo <code>
 * regolaModuli</code> </li>
 * <li> Regola i titoli delle finestre lista e scheda
 * <li> Ogni campo viene creato con un costruttore semplice con solo le piu'
 * comuni informazioni; le altre vengono regolate con chiamate successive </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 27-dic-2006 ore 14.00.47
 */
public final class SemaforoModello extends ModelloAlgos implements Semaforo {

    /**
     * nome della tavola di archivio collegata (facoltativo) <br>
     * i nomi delle tavole sono sempre minuscoli <br>
     * se vuoto usa il nome del modulo <br>
     */
    private static final String TAVOLA_ARCHIVIO = NOME_TAVOLA;


    /**
     * Costruttore completo senza parametri.<br>
     */
    public SemaforoModello() {
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
        /* regola il nome della tavola dalla costante */
        super.setTavolaArchivio(TAVOLA_ARCHIVIO);

        /* attiva il trigger per nuovo record */
        this.setTriggerNuovoAttivo(true);

    }// fine del metodo inizia


    public boolean inizializza(Modulo unModulo) {
        boolean riuscito;
        riuscito = super.inizializza(unModulo);

        /* valore di ritorno */
        return riuscito;
    } /* fine del metodo */


    /**
     * Creazione dei campi.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Creazione dei campi record di questo modello <br>
     * I campi verranno visualizzati nell'ordine di inserimento <br>
     * Ogni campo viene creato con un costruttore semplice con solo le piu'
     * comuni informazioni; le altre vengono regolate con chiamate successive <br>
     * Invoca il metodo sovrascritto della superclasse <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.progetto.Progetto#preparaModuli
     * @see it.algos.base.modello.ModelloAlgos#creaCampi
     * @see it.algos.base.campo.base.CampoFactory
     * @see it.algos.base.campo.video.decorator.VideoFactory
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo chiave semaforo (indicizzato)*/
            unCampo = CampoFactory.testo(Cam.chiave);
            unCampo.getCampoDB().setIndicizzato(true);
            unCampo.decora().obbligatorio();
            this.addCampo(unCampo);

            /* campo flag semaforo */
            unCampo = CampoFactory.checkBox(Cam.flag);
            this.addCampo(unCampo);

            /* campo utente proprietario */
            unCampo = CampoFactory.comboLinkPop(Cam.utente);
            unCampo.setNomeModuloLinkato(Utenti.NOME_MODULO);
            unCampo.setNomeCampoValoriLinkato(Utenti.CAMPO_NOME);
            unCampo.setNomeColonnaListaLinkata(Utenti.CAMPO_NOME);
            unCampo.decora().obbligatorio();
            this.addCampo(unCampo);

            /* campo timestamp di attivazione */
            unCampo = CampoFactory.timestamp(Cam.time.get());
            unCampo.decora().obbligatorio();
            this.addCampo(unCampo);

            /* campo time to live (ttl) in secondi */
            unCampo = CampoFactory.intero(Cam.ttl);
            this.addCampo(unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione di un nuovo semaforo
     * <p/>
     * Regola i campi Utente, Flag, Ora di attivazione
     */
    protected boolean nuovoRecordAnte(ArrayList<CampoValore> lista, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        CampoValore cv;
        Campo campo;

        try { // prova ad eseguire il codice

            /* Campo Utente, è sempre l'utente corrente */
            campo = this.getCampo(Cam.utente.get());
            cv = Lib.Camp.getCampoValore(lista, campo);
            if (cv == null) {
                cv = new CampoValore(campo);
                lista.add(cv);
            }// fine del blocco if
            cv.setValore(Progetto.getIdUtenteCorrente());

            /* Campo Flag, è sempre spento per un nuovo record */
            campo = this.getCampo(Cam.flag.get());
            cv = Lib.Camp.getCampoValore(lista, campo);
            if (cv == null) {
                cv = new CampoValore(campo);
                lista.add(cv);
            }// fine del blocco if
            cv.setValore(false);

            /* Campo Ora di attivazione, è il timestamp corrente */
            campo = this.getCampo(Cam.time.get());
            cv = Lib.Camp.getCampoValore(lista, campo);
            if (cv == null) {
                cv = new CampoValore(campo);
                lista.add(cv);
            }// fine del blocco if
            cv.setValore(Progetto.getTimestampCorrente());


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return true;
    } // fine del metodo
}// fine della classe
