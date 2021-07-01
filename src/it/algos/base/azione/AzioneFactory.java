/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      23-dic-2004
 */
package it.algos.base.azione;

import it.algos.base.azione.aiuto.AzAbout;
import it.algos.base.azione.aiuto.AzHelp;
import it.algos.base.azione.aiuto.AzHelpProgrammatore;
import it.algos.base.azione.aiuto.AzUpdate;
import it.algos.base.azione.archivio.*;
import it.algos.base.azione.campo.*;
import it.algos.base.azione.componente.*;
import it.algos.base.azione.dialogo.AzAnnullaDialogo;
import it.algos.base.azione.dialogo.AzConfermaDialogo;
import it.algos.base.azione.dialogo.AzDialogo;
import it.algos.base.azione.finestra.AzAttivaFinestra;
import it.algos.base.azione.finestra.AzChiudeFinestra;
import it.algos.base.azione.finestra.AzFinChiudeProgramma;
import it.algos.base.azione.lista.*;
import it.algos.base.azione.mouse.AzMouseClick;
import it.algos.base.azione.mouse.AzMouseDoppioClick;
import it.algos.base.azione.record.AzPrimoRecord;
import it.algos.base.azione.record.AzRecordPrecedente;
import it.algos.base.azione.record.AzRecordSuccessivo;
import it.algos.base.azione.record.AzUltimoRecord;
import it.algos.base.azione.scheda.AzAnnullaModifiche;
import it.algos.base.azione.scheda.AzChiudeScheda;
import it.algos.base.azione.scheda.AzRegistraScheda;
import it.algos.base.azione.selezione.AzCaricaTutti;
import it.algos.base.azione.selezione.AzNascondeSelezionati;
import it.algos.base.azione.selezione.AzSoloSelezionati;
import it.algos.base.azione.testo.*;
import it.algos.base.errore.Errore;
import it.algos.base.progetto.Progetto;

/**
 * Factory per la creazione di azioni.
 * </p>
 * Questa classe astratta factory: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Factory Method</b> </li>
 * <li> Fornisce metodi <code>statici</code> per la  creazione degli oggetti di questo
 * package </li>
 * <li> Qui vengono elencate tutte le Azioni usate nel programma </li>
 * <li> Le Azioni vengono mantenute in una collezione chiave-valore di Progetto </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 23-dic-2004 ore 16.35.50
 */
public abstract class AzioneFactory implements Azione {

    /**
     * Progetto in esecuzione
     * (il progetto e' unico, il riferimento serve solo a recuperare l'istanza)
     */
    private static Progetto progetto = null;


    /**
     * Costruttore completo senza parametri.
     * <p/>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public AzioneFactory() {
        /* rimanda al costruttore della superclasse */
        super();
    }// fine del metodo costruttore completo


    /**
     * Aggiunge la singola azione alla collezione.
     * <p/>
     * Viene mantenuta una collezione di azioni nel <code>Singleton</code> della
     * classe Progetto <br>
     * Se l'azione esisteva gi&agrave gia', non viene aggiunta <br>
     */
    private static void add(Azione unAzione) {
        /* invoca il metodo delegato della classe */
        progetto.addAzione(unAzione);
    } /* fine del metodo */


    /**
     * Aggiunge una azione specifica alla collezione.
     * <p/>
     * Viene mantenuta una collezione di azioni nel <code>Singleton</code> della
     * classe Progetto <br>
     * Se l'azione esisteva gi&agrave gia', non viene aggiunta <br>
     */
    public static Azione addSpecifica(Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Progetto progetto = null;

        try { // prova ad eseguire il codice
            progetto = Progetto.getIstanza();

            /* invoca il metodo delegato della classe */
            progetto.addAzione(unAzione);

            /* inizializza l'azione */
            unAzione.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unAzione;
    }


    /**
     * Crea tutte le azioni.
     * <p/>
     * Metodo invocato dal ciclo inizia del Progetto
     * <p/>
     * Nota: Progetto � un Singleton, quindi sembrerebbe superfluo passarlo come
     * parametro. Per� questo metodo viene invocato dal costruttore di Progetto,
     * quindi il metodo Progetto.getIstanza() <bold>non>/bold> funziona ancora <br>
     *
     * @param unProgetto singleton di progetto
     */
    public static void crea(Progetto unProgetto) {
        /* recupera il riferimento all'istanza singola del Progetto */
        progetto = unProgetto;

        try { // prova ad eseguire il codice

            /* gruppo del menu archivio */
            add(new AzAnnullaModifiche());
            add(new AzChiudeNavigatore());
            add(new AzChiudeProgramma());
            add(new AzChiudeScheda());
            add(new AzEliminaRecord());
            add(new AzRimuoviRecord());
            add(new AzModificaRecord());
            add(new AzNuovoRecord());
            add(new AzAggiungiRecord());
            add(new AzDuplicaRecord());
            add(new AzPreferenze());
            add(new AzContatori());
            add(new AzSemafori());
            add(new AzUtenti());
            add(new AzRegistraDatiDefault());
            add(new AzRegistraScheda());
            add(new AzRicerca());
            add(new AzProietta());
            add(new AzPreferito());
            add(new AzStampa());

            /* gruppo di gestione dei campi della Scheda*/
            add(new AzCalcolaCampo());
            add(new AzCalcolaLabel());
            add(new AzCarattere());
            add(new AzEnter());
            add(new AzEscape());
            add(new AzEntrataCampo());
            add(new AzUscitaCampo());

            /* gruppo dei componenti */
            add(new AzItemModificato());
            add(new AzEntrataPopup());
            add(new AzUscitaPopup());
            add(new AzPopupModificato());
            add(new AzSelezioneModificata()); // JList

            /* gruppo di gestione del dialogo */
            add(new AzAnnullaDialogo());
            add(new AzConfermaDialogo());
            add(new AzDialogo());

            /* gruppo della finestra */
            add(new AzAttivaFinestra());
            add(new AzChiudeFinestra());
            add(new AzFinChiudeProgramma());
//            add(new AzEmergenza());

            /* gruppo del menu help */
            add(new AzAbout());
            add(new AzUpdate());
            add(new AzHelp());
            add(new AzHelpProgrammatore());

            /* gruppo di spostamenti nella Lista */
            add(new AzColonna());
            add(new AzEnd());
            add(new AzFrecce());
            add(new AzHome());
            add(new AzListaCarattere());
            add(new AzListaClick());
            add(new AzListaDoppioClick());
            add(new AzListaEnter());
            add(new AzListaReturn());
            add(new AzPagine());
            add(new AzRigaGiu());
            add(new AzRigaSu());
            add(new AzTitolo());
            add(new AzEntrataCella());
            add(new AzUscitaCella());

            /* gruppo del mouse */
            add(new AzMouseClick());
            add(new AzMouseDoppioClick());

            /* gruppo di spostamento della scheda */
            add(new AzRecordPrecedente());
            add(new AzPrimoRecord());
            add(new AzRecordSuccessivo());
            add(new AzUltimoRecord());

            /* gruppo di selezione della lista */
            add(new AzCaricaTutti());
            add(new AzSoloSelezionati());
            add(new AzNascondeSelezionati());
            add(new AzSalvaSelezione());
            add(new AzCaricaSelezione());

            /* gruppo del testo */
            add(new AzAnnullaOperazione());
            add(new AzCopiaTesto());
//            add(new AzEliminaTesto());
            add(new AzIncollaTesto());
            add(new AzMostraAppunti());
            add(new AzSelezionaTutto());
            add(new AzTagliaTesto());

            /* gruppo della lista */
            add(new AzImporta());
            add(new AzEsporta());
            add(new AzBackup());
            add(new AzRestore());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

}// fine della classe
