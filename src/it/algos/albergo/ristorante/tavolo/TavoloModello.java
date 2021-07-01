/**
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 27 marzo 2003 alle 7.21
 */

package it.algos.albergo.ristorante.tavolo;

import it.algos.albergo.ristorante.lingua.Lingua;
import it.algos.albergo.ristorante.sala.Sala;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.vista.Vista;

import java.util.ArrayList;

/**
 * Tracciato record della tavola Tavolo.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Mantiene il nome della tavola di archivo dove sono registrati tutti i
 * dati (records) del modello </li>
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
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  27 marzo 2003 ore 7.21
 */
public final class TavoloModello extends ModelloAlgos implements Tavolo {

    /**
     * nome della tavola di archivio collegata (facoltativo)
     * (se vuoto usa il nome del modulo)
     */
    private static final String TAVOLA_ARCHIVIO = NOME_TAVOLA;

    /**
     * Testi usati nelle spiegazioni dei campi (facoltativo)
     */
    private static final String LEGENDA_SALA = "nome della sala in cui e' situato il tavolo";

    private static final String LEGENDA_TAVOLO = "numero identificativo del tavolo";

    private static final String LEGENDA_COPERTI =
            "n. di coperti con cui e' apparecchiato il tavolo";

    private static final String LEGENDA_CLIENTE = "nome del cliente - note";

    /**
     * Titolo della colonna nelle viste esterne (facoltativo)
     */
    private static final String COLONNA_TAVOLO = "tav.";


    /**
     * Costruttore completo senza parametri.
     */
    public TavoloModello() {
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
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* regola il nome della tavola dalla costante */
        super.setTavolaArchivio(TAVOLA_ARCHIVIO);
    }// fine del metodo inizia


    /**
     * Creazione dei campi.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Creazione dei campi record di questo modello <br>
     * I campi verranno visualizzati nell'ordine di inserimento <br>
     * Ogni campo viene creato con un costruttore semplice con solo le piu'
     * comuni informazioni; le altre vengono regolate con chiamate successive <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.campo.base.CampoFactory
     * @see it.algos.base.campo.video.decorator.VideoFactory;
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        /* invoca il metodo sovrascritto della superclasse */
        super.creaCampi();

        try { // prova ad eseguire il codice

            /* campo numero tavolo */
            unCampo = CampoFactory.intero(Cam.numtavolo);
            unCampo.decora().obbligatorio();
            unCampo.setVisibileVistaDefault();
//            unCampo.decora().obbligatorio();
            unCampo.decora().legenda(LEGENDA_TAVOLO);
            this.addCampo(unCampo);

            /* campo sala */
            unCampo = CampoFactory.comboLinkPop(Cam.sala);
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.setVisibileVistaDefault();
            unCampo.setNomeModuloLinkato(Sala.NOME_MODULO);
            unCampo.setNomeVistaLinkata(Sala.VISTA_DESCRIZIONE);
            unCampo.setNomeCampoValoriLinkato(Sala.CAMPO_DESCRIZIONE);
            unCampo.getCampoDB().setNomeEstratto(Sala.CAMPO_DESCRIZIONE, 0);
            unCampo.decora().legenda(LEGENDA_SALA);
            unCampo.setRicercabile(true);
            unCampo.setUsaNuovo(true);
            this.addCampo(unCampo);

            /* campo numero coperti del tavolo */
            unCampo = CampoFactory.intero(Cam.numcoperti);
            unCampo.setVisibileVistaDefault();
            unCampo.setLarLista(50);      // per la larghezza del titolo
            unCampo.decora().legenda(LEGENDA_COPERTI);
            this.addCampo(unCampo);

            /* campo flag tavolo occupato */
            unCampo = CampoFactory.checkBox(Cam.occupato);
            unCampo.setVisibileVistaDefault();
            unCampo.setTitoloColonna("occ");
            unCampo.setModificabileLista(true);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo nome cliente */
            unCampo = CampoFactory.testo(Cam.nomecliente);
            unCampo.setVisibileVistaDefault();
            unCampo.decora().legenda(LEGENDA_CLIENTE);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo flag mezza pensione */
            unCampo = CampoFactory.checkBox(Cam.mezzapensione);
            unCampo.setVisibileVistaDefault();
            unCampo.setTitoloColonna("1/2p");
            unCampo.setTestoComponente("mezza pensione");
            unCampo.setLarScheda(120);
            this.addCampo(unCampo);

            /* campo pasto in caso di mezza pensione */
            unCampo = CampoFactory.radioMetodo(Cam.pasto.get());
            unCampo.setVisibileVistaDefault();
            unCampo.getCampoDB().setNomeModuloValori(Lingua.NOME_MODULO);
            unCampo.getCampoDB().setNomeMetodoValori("getNomiPasto");
            this.addCampo(unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }/* fine del blocco try-catch */
    }/* fine del metodo */


    /**
     * Creazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Eventuale creazione di viste aggiuntive, oltre alla vista base di default <br>
     * Costruisce degli ArrayList di riferimenti ordinati (oggetti <code>Vista</code>) per
     * individuare i campi che voglio vedere nelle liste alternative ed
     * aggiuntive a quella standard (costruita in automatico nella superclasse) <br>
     * Gli array vengono creati coi campi di questo modello, oppure con
     * viste di altri moduli, oppure con campi di altri modelli <br>
     * Viene chiamato <strong>dopo</strong> che nella sottoclasse sono stati costruiti tutti i campi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaViste() {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> unArray;

        try { // prova ad eseguire il codice

            /* crea la vista specifica */
            super.addVista(VISTA_TAVOLO, Cam.numtavolo.get());

            /* crea la vista specifica (piu' campi - uso un array) */
            unArray = new ArrayList<String>();
            unArray.add(Cam.sala.get());
            unArray.add(Cam.numtavolo.get());
            super.addVista(VISTA_SALA_TAVOLO, unArray);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Eventuale regolazione delle caratteristiche specifiche di ogni copia
     * dei campi delle viste; le variazioni modificano <strong>solo</strong> le copie <br>
     * Viene chiamato <strong>dopo</strong> che nella superclasse sono state
     * <strong>clonate</strong> tutte le viste <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void regolaViste() {
        /* variabili e costanti locali di lavoro */
        Vista unaVista = null;
        Campo unCampo = null;
        Campo campoSalaDescrizione = null;
        Campo campoNumTavolo = null;
        Modulo moduloSala = null;

        try { // prova ad eseguire il codice

            /* aggiunge un sub-ordine per numero di tavolo
             * ai campi della vista di default */
            unaVista = this.getVistaDefault();
            campoNumTavolo = this.getCampo(Tavolo.Cam.numtavolo);
            moduloSala = Progetto.getModulo(Sala.NOME_MODULO);
            campoSalaDescrizione = moduloSala.getCampo(Sala.CAMPO_DESCRIZIONE);
            unaVista.getCampo(campoSalaDescrizione).addOrdinePubblico(campoNumTavolo);
            unaVista.getCampo(Tavolo.Cam.numcoperti).addOrdinePrivato(campoNumTavolo);
            unaVista.getCampo(Tavolo.Cam.occupato).addOrdinePrivato(campoNumTavolo);
            unaVista.getCampo(Tavolo.Cam.nomecliente).addOrdinePrivato(campoNumTavolo);
            unaVista.getCampo(Tavolo.Cam.mezzapensione).addOrdinePrivato(campoNumTavolo);
            unaVista.getCampo(Tavolo.Cam.pasto).addOrdinePrivato(campoNumTavolo);

            // todo in sviluppo
            /* Assegna un renderer al campo Occupato per non far vedere
             * alcune colonne se il tavolo non e' occupato */
            unCampo = unaVista.getCampo(this.getCampo(Tavolo.Cam.numtavolo));
//            unCampo.getCampoDati().setRenderer(new RendererLista(unCampo));

            /* regola il titolo della colonna tavolo
             * nella vista VISTA_TAVOLO */
            unaVista = this.getVista(VISTA_TAVOLO);
            unCampo = unaVista.getCampo(Cam.numtavolo);
            unCampo.setTitoloColonna(COLONNA_TAVOLO);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


} // fine della classe