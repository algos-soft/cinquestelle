/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 2 feb 2006
 */

package it.algos.albergo.storico5stelle;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.listino.Listino;
import it.algos.albergo.tabelle.azienda.Azienda;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.gestione.anagrafica.Anagrafica;

import javax.swing.SwingConstants;

/**
 * Tracciato record della tavola Storico 5Stelle.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 15 set 2008
 */
public final class StoricoModello extends ModelloAlgos implements Storico {

    /**
     * nome della tavola di archivio collegata (facoltativo) <br>
     * i nomi delle tavole sono sempre minuscoli <br>
     * se vuoto usa il nome del modulo <br>
     */
    private static final String TAVOLA_ARCHIVIO = NOME_TAVOLA;


    /**
     * Costruttore completo senza parametri.
     */
    public StoricoModello() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* regola il nome della tavola dalla costante */
        super.setTavolaArchivio(TAVOLA_ARCHIVIO);
    }


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

            /* campo cliente */
            unCampo = CampoFactory.comboLinkSel(Storico.Cam.cliente);
            unCampo.setNomeModuloLinkato(ClienteAlbergo.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(Anagrafica.Cam.soggetto.get());
            unCampo.setNomeCampoValoriLinkato(Anagrafica.Cam.soggetto.get());
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.setLarghezza(150);
            unCampo.setRidimensionabile(false);
            unCampo.setUsaNuovo(false);
            unCampo.setRicercabile(true);

            unCampo.addColonnaCombo(ClienteAlbergo.Cam.dataNato.get());
            unCampo.addColonnaCombo(ClienteAlbergo.Cam.capogruppo.get());

            this.addCampo(unCampo);


            /* campo cognome */
            unCampo = CampoFactory.testo(Storico.Cam.cognome);
            unCampo.setLarLista(130);
            unCampo.setLarScheda(160);
            unCampo.setAbilitato(false);
            this.addCampo(unCampo);

            /* campo nome */
            unCampo = CampoFactory.testo(Storico.Cam.nome);
            unCampo.setLarLista(130);
            unCampo.setLarScheda(160);
            unCampo.setAbilitato(false);
            this.addCampo(unCampo);

            /* campo data di nascita */
            unCampo = CampoFactory.data(Storico.Cam.datanascita);
            unCampo.setInit(null);
            unCampo.setAbilitato(false);
            this.addCampo(unCampo);

            /* campo data entrata (con arrivo o cambio) */
            unCampo = CampoFactory.data(Storico.Cam.entrata);
            unCampo.setLarLista(90);
            unCampo.setInit(null);
            unCampo.setRicercabile(true);
            unCampo.setAbilitato(false);
            this.addCampo(unCampo);

            /* campo data uscita (con partenza o cambio) */
            unCampo = CampoFactory.data(Storico.Cam.uscita);
            unCampo.setLarLista(90);
            unCampo.setInit(null);
            unCampo.setRicercabile(true);
            unCampo.setAbilitato(false);
            this.addCampo(unCampo);

            /* campo camera */
            unCampo = CampoFactory.comboLinkSel(Storico.Cam.camera);
            unCampo.setNomeModuloLinkato(Camera.NOME_MODULO);
            unCampo.setNomeCampoValoriLinkato(Camera.Cam.camera.get());
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.setUsaNuovo(false);
            unCampo.setRicercabile(true);
            unCampo.setLarghezza(80);
            this.addCampo(unCampo);

            /* campo nome camera (da 5stelle) */
            unCampo = CampoFactory.testo(Storico.Cam.stringacamera);
            unCampo.setAbilitato(false);
            unCampo.setLarghezza(80);            
            this.addCampo(unCampo);

            /* campo check bambino */
            unCampo = CampoFactory.checkBox(Storico.Cam.bambino);
            unCampo.setTestoComponente("bambino");
            unCampo.setLarLista(35);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo link tipo di pensione */
            unCampo = CampoFactory.comboInterno(Storico.Cam.pensione);
            unCampo.setValoriInterni(Listino.PensioniPeriodo.values());
            unCampo.setLarScheda(60);
            unCampo.setLarLista(40);
            unCampo.setAllineamentoLista(SwingConstants.CENTER);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo stringa tipo di pensione (da 5stelle) */
            unCampo = CampoFactory.testo(Storico.Cam.stringapensione);
            unCampo.setAbilitato(false);
            this.addCampo(unCampo);

            /* campo numero di ps */
            unCampo = CampoFactory.intero(Storico.Cam.ps);
            unCampo.setRicercabile(true);
            unCampo.setAbilitato(false);
            this.addCampo(unCampo);

            /* campo cambio in entrata  */
            unCampo = CampoFactory.checkBox(Storico.Cam.cambioEntrata);
            unCampo.setLarLista(30);
            unCampo.setLarScheda(150);
            unCampo.setTestoComponente("cambio entrata");
            unCampo.setModificabileLista(false);
            unCampo.setRidimensionabile(false);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo cambio in uscita  */
            unCampo = CampoFactory.checkBox(Storico.Cam.cambioUscita);
            unCampo.setLarLista(30);
            unCampo.setLarScheda(150);
            unCampo.setTestoComponente("cambio uscita");
            unCampo.setModificabileLista(false);
            unCampo.setRidimensionabile(false);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo azienda */
            unCampo = CampoFactory.comboLinkPop(Storico.Cam.azienda);
            unCampo.setNomeModuloLinkato(Azienda.NOME_MODULO);
            unCampo.setNomeCampoValoriLinkato(Azienda.CAMPO_SIGLA);
            unCampo.setLarLista(60);
            unCampo.setLarScheda(80);
            unCampo.setUsaNonSpecificato(false);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Eventuale creazione di viste aggiuntive, oltre alla vista base di default <br>
     * Costruisce degli ArrayList di riferimenti ordinati (oggetti <code>Vista</code>)
     * per individuare i campi che voglio vedere nelle liste alternative ed
     * aggiuntive a quella standard (costruita in automatico nella superclasse) <br>
     * Gli array vengono creati coi campi di questo modello, oppure con
     * viste di altri moduli, oppure con campi di altri modelli <br>
     * Viene chiamato <strong>dopo</strong> che nella sottoclasse sono stati
     * costruiti tutti i campi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.progetto.Progetto#preparaModuli()
     * @see #regolaViste
     */
    protected void creaViste() {

        try { // prova ad eseguire il codice
            /* crea la vista specifica (un solo campo) */
            super.addVista(VISTA_SIGLA, CAMPO_SIGLA);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }



} // fine della classe