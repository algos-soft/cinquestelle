/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 2 feb 2006
 */

package it.algos.albergo.camera;

import it.algos.albergo.camera.composizione.CompoCamera;
import it.algos.albergo.camera.righecameracompo.RCC;
import it.algos.albergo.camera.zona.Zona;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.interfaccia.Generale;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.vista.Vista;

import java.util.ArrayList;

/**
 * Tracciato record della tavola Camera.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea il <strong>tracciato record</strong> (Abstract Data Types) di una
 * tavola </li>
 * <li> Crea i <strong>campi</strong> di questo modello (oltre a quelli base
 * della superclasse) nel metodo <code>creaCampi</code> </li>
 * <li> Ogni campo viene creato con un costruttore semplice con solo le piu'
 * comuni informazioni; le altre vengono regolate con chiamate successive </li>
 * <li> Crea eventuali <strong>viste</strong> della <code>Lista</code>
 * (oltre a quella base) nel metodo <code>creaViste</code> </li>
 * <li> Regola eventualmente i valori delle viste nel metodo <code>regolaViste</code> </li>
 * <li> Crea eventuali <strong>set</strong> della <code>Scheda</code>
 * (oltre a quello base) nel metodo <code>creaSet</code> </li>
 * <li> Regola eventualmente i valori dei set nel metodo <code>regolaSet</code> </li>
 * <li> Regola eventualmente i valori da inserire in un <code>nuovoRecord</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 2 feb 2006
 */
public final class CameraModello extends ModelloAlgos implements Camera {

    /**
     * nome della tavola di archivio collegata (facoltativo) <br>
     * i nomi delle tavole sono sempre minuscoli <br>
     * se vuoto usa il nome del modulo <br>
     */
    private static final String TAVOLA_ARCHIVIO = NOME_TAVOLA;

    /**
     * Testo della colonna della Lista come appare nella Vista
     */
    private static final String COLONNA_SIGLA = TITOLO_TABELLA;


    /**
     * Testo della legenda sotto il campo sigla nella scheda
     */
    private static final String LEGENDA_SIGLA = Generale.LEGENDA_SIGLA;


    /**
     * Costruttore completo senza parametri.
     */
    public CameraModello() {
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
        Filtro filtro;
        int lar = 150;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo link zona (piano) */
            unCampo = CampoFactory.comboLinkPop(Cam.linkZona);
            unCampo.setNomeModuloLinkato(Zona.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(Zona.Cam.sigla.get());
            unCampo.setNomeCampoValoriLinkato(Zona.Cam.sigla.get());
            unCampo.setLarScheda(lar);
            this.addCampo(unCampo);

            /* campo nome della camera */
            unCampo = CampoFactory.testo(Cam.camera);
            unCampo.setLarghezza(60);
            this.addCampo(unCampo);

            /* campo navigatore sub-lista composizione */
            unCampo = CampoFactory.navigatore(Cam.righeCompo, RCC.NOME_MODULO, RCC.Nav.navCamera);
            this.addCampo(unCampo);

            /* campo link composizione standard */
            unCampo = CampoFactory.comboLinkPop(Cam.composizione);
            unCampo.setNomeModuloLinkato(CompoCamera.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(CompoCamera.Cam.sigla.get());
            unCampo.setNomeCampoValoriLinkato(CompoCamera.Cam.sigla.get());
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.getCampoDati().setNuovoIniziale(true);
            unCampo.setLarScheda(lar);
            unCampo.decora().estrattoSotto(CompoCamera.Est.descrizione);
            this.addCampo(unCampo);

            /* campo booleano escludi dal planning */
            unCampo = CampoFactory.checkBox(Camera.Cam.escludiplanning);
            unCampo.setLarScheda(160);
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


    /**
     * Regolazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Eventuale regolazione delle caratteristiche specifiche di ogni copia dei
     * campi delle viste; le variazioni modificano <strong>solo</strong> le copie <br>
     * Viene chiamato <strong>dopo</strong> che nella superclasse sono state
     * <strong>clonate</strong> tutte le viste <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see #creaViste
     */
    protected void regolaViste() {
        /* variabili e costanti locali di lavoro */
        Vista unaVista;
        Campo unCampo;

        try { // prova ad eseguire il codice
            unaVista = this.getVista(VISTA_SIGLA);
            unCampo = unaVista.getCampo(CAMPO_SIGLA);
            unCampo.getCampoLista().setRidimensionabile(false);
            unCampo.setTitoloColonna(COLONNA_SIGLA);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Costruisce un ArrayList di riferimenti ordinati (oggetti Campo) per
     * individuare i campi che voglio vedere nella scheda di default.
     * <p/>
     * Viene chiamato DOPO che nella sottoclasse sono stati costruiti tutti i campi
     */
    @Override protected ArrayList<Campo> creaSetDefault() {
        return super.creaSetDefault();    //To change body of overridden methods use File | Settings | File Templates.
    }
} // fine della classe
