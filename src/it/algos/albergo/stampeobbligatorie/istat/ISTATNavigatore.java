package it.algos.albergo.stampeobbligatorie.istat;

import it.algos.albergo.stampeobbligatorie.ModuloInterno;
import it.algos.albergo.stampeobbligatorie.ObbligNavigatore;
import it.algos.albergo.stampeobbligatorie.PannelloObbligatorie;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampeNavigatore;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.evento.lista.ListaDoppioClicAz;
import it.algos.base.evento.lista.ListaDoppioClicEve;
import it.algos.base.evento.lista.ListaSelAz;
import it.algos.base.evento.lista.ListaSelEve;
import it.algos.base.lista.Lista;
import it.algos.base.lista.ListaBase;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;

import javax.swing.JLabel;
import java.util.ArrayList;

/**
 * Navigatore ISTAT
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 1-Ott-2008
 */
public final class ISTATNavigatore extends ObbligNavigatore {


    /* modulo interno per la gestione delle sottoliste arrivati e partiti */
    private ModuloInterno moduloInterno;

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param unModulo modulo di riferimento
     */
    public ISTATNavigatore(Modulo unModulo) {
        /* rimanda al costruttore della superclasse */
        super(unModulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice
            this.setRigheLista(12);
            this.setUsaStatusBarLista(false);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Inizializzazione dell'oggetto.<br>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche.<br>
     * Viene chiamato dall'inizializzazione del Modulo.<br>
     * Puo' essere chiamato piu' volte.<br>
     * Se l'inizializzazione ha successo imposta il flag inizializzato a true.<br>
     * Il flag puo' essere successivamente modificato dalle sottoclassi se non
     * riescono a portare a termine la propria inizializzazione specifica.<br>
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        Pannello panNav;
        Lista lista;

        try { // prova ad eseguire il codice

            super.inizializza();

            /* crea il modulo interno con i due navigatori (arrivati e partiti) */
            this.creaModuloInterno();

            /* Aggiunge i listeners agli oggetti */
            this.aggiungiListeners();

            /* crea un pannello con i due navigatori affiancati orizzontalmente */
            panNav = this.creaPanNavClienti();

            /* aggiunge il pannello al Portale Navigatore */
            this.getPortaleNavigatore().add(panNav);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizializza


    /**
     * Crea e registra il modulo interno con relativo navigatore.
     * <p/>
     */
    private void creaModuloInterno () {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campi = new ArrayList<Campo>();
        Campo campo;
        ModuloInterno mem;

        try { // prova ad eseguire il codice

            /* campo link al record di ISTAT (non visibile) */
            campo = CampoFactory.intero(ISTATModuloInterno.Nomi.linkRigaISTAT.get());
            campo.setVisibileVistaDefault(false);
            campi.add(campo);

            /* campo codice cliente (non visibile) */
            campo = CampoFactory.intero(ISTATModuloInterno.Nomi.codcliente.get());
            campo.setVisibileVistaDefault(false);
            campi.add(campo);

            /* campo soggetto */
            campo = CampoFactory.testo(ISTATModuloInterno.Nomi.soggetto.get());
            campo.setTitoloColonna("cliente");
            campo.setVisibileVistaDefault(true);
            campi.add(campo);

            /* campo flag booleano true per arrivato false per partito (non visibile)*/
            campo = CampoFactory.checkBox(ISTATModuloInterno.Nomi.arrivato.get());
            campo.setVisibileVistaDefault(false);
            campi.add(campo);

            /* campo check di controllo validità dati anagrafici */
            campo = CampoFactory.testo(ISTATModuloInterno.Nomi.checkdati.get());
            campo.setVisibileVistaDefault(true);
            campo.setTitoloColonna("ok");
            campo.setLarLista(40);
            campo.setRidimensionabile(false);
            campo.setRenderer(new ISTATRendererCheck(campo));
            campi.add(campo);

            mem = new ISTATModuloInterno(campi, this);
            this.setModuloInterno(mem);
            mem.avvia();



        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea un pannello contenente i navigatori dei clienti Arrivati e Partiti
     * affiancati orizzontalmente.
     * <p/>
     * @return
     */
    private Pannello creaPanNavClienti () {
        /* variabili e costanti locali di lavoro */
        Pannello panNav  = null;
        Pannello pan;

        try {    // prova ad eseguire il codice

            panNav = PannelloFactory.orizzontale(null);
            panNav.setUsaGapFisso(true);
            panNav.setGapPreferito(0);


            pan = this.creaPanNavigatore(this.getNavArrivati()," arrivati");
            panNav.add(pan);

            pan = this.creaPanNavigatore(this.getNavPartiti()," partiti");
            panNav.add(pan);


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return panNav;
    }


    /**
     * Crea un pannello contenente un navigatore con titolo
     * <p/>
     * @param nav il navigatore
     * @param titolo il titolo
     * @return il pannello con titolo e navigatore
     */
    private Pannello creaPanNavigatore (Navigatore nav, String titolo) {
        /* variabili e costanti locali di lavoro */
        JLabel labelTitolo;
        Pannello panNav;
        Pannello panAll=null;


        try { // prova ad eseguire il codice

            panAll = PannelloFactory.verticale(null);
            panAll.setUsaGapFisso(true);
            panAll.setGapPreferito(0);

            labelTitolo = new JLabel(titolo);
            panNav = nav.getPortaleNavigatore();

            panAll.add(labelTitolo);
            panAll.add(panNav);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return panAll;
    }


    /**
     * Aggiunge i listeners agli oggetti.
     * <p/>
     */
    private void aggiungiListeners () {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try {    // prova ad eseguire il codice
            /**
             * aggiunge un listener per il cambio di selezione nella lista arrivati
             */
            lista = this.getNavArrivati().getLista();
            if (lista!=null) {
                lista.addListener(new AzSelListaArrivati());
                lista.addListener(new AzDoppioClicArrivati());
            }// fine del blocco if

            /**
             * aggiunge un listener per il cambio di selezione nella lista arrivati
             */
            lista = this.getNavPartiti().getLista();
            if (lista!=null) {
                lista.addListener(new AzSelListaPartiti());
                lista.addListener(new AzDoppioClicPartiti());
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }





    /**
     * Modifica della selezione di una lista </li>
     * <p/>
     */
    public void selezioneModificata() {

        /* variabili e costanti locali di lavoro */
        ModuloInterno mod;

        try { // prova ad eseguire il codice

            super.selezioneModificata();

            /* aggiorna la lista dei navigatori interni */
            mod = this.getModuloInterno();
            mod.aggiornaLista();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Sincronizza il Navigatore.
     * <p/>
     * Controlla lo stato di tutti i suoi componenti <br>
     * Elabora la sua business logic <br>
     * Regola la GUI di conseguenza <br>
     */
    @Override public void sincronizza() {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            super.sincronizza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna il Navigatore del modulo interno.
     * <p/>
     *
     * @return il Navigatore del modulo interno
     */
    private Navigatore getNavArrivati() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;
        Modulo mod;

        try {    // prova ad eseguire il codice
            mod = this.getModuloInterno();
            if (mod != null) {
                nav = mod.getNavigatore(ISTATModuloInterno.Nav.arrivati.get());
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }

    /**
     * Ritorna il Navigatore del modulo interno.
     * <p/>
     *
     * @return il Navigatore del modulo interno
     */
    private Navigatore getNavPartiti() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;
        Modulo mod;

        try {    // prova ad eseguire il codice
            mod = this.getModuloInterno();
            if (mod != null) {
                nav = mod.getNavigatore(ISTATModuloInterno.Nav.partiti.get());
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


    /**
     * Ritorna il codice del cliente correntemente selezionato.
     * <p/>
     * Il cliente può essere selezionato in uno dei 2
     * sotto-navigatori (Arrivati e Partiti)
     * @return il codice del cliente correntemente selezionato
     */
    public int getCodClienteSelezionato() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        Lista listaArrivati;
        Lista listaPartiti;
        int chiave;
        Modulo modulo;

        try {    // prova ad eseguire il codice

            /* recupera il codice chiave della riga selezionata
            * prima prova nella lista degli arrivati poi dei partiti
            * (le due liste non possono essere selezionate contemporaneamente) */
            listaArrivati = this.getNavArrivati().getLista();
            chiave = listaArrivati.getChiaveSelezionata();
            if (chiave<=0) {
                listaPartiti = this.getNavPartiti().getLista();
                chiave = listaPartiti.getChiaveSelezionata();
            }// fine del blocco if

            /* recupera il codice cliente */
            if (chiave>0) {
                modulo = this.getModuloInterno();
                codice = modulo.query().valoreInt(ISTATModuloInterno.Nomi.codcliente.get(), chiave);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Doppio clic su un record della lista Arrivati.
     * <p/>
     */
    private void doppioClicArrivati () {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try { // prova ad eseguire il codice
            /* forza la lista di questo navigatore a rilanciare l'evento */
            lista = getLista();
            if (lista!=null) {
                lista.fire(ListaBase.Evento.doppioClick);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }

    /**
     * Doppio clic su un record della lista Partiti.
     * <p/>
     */
    private void doppioClicPartiti () {
        /* variabili e costanti locali di lavoro */
        Lista lista;

        try { // prova ad eseguire il codice
            /* forza la lista di questo navigatore a rilanciare l'evento */
            lista = getLista();
            if (lista!=null) {
                lista.fire(ListaBase.Evento.doppioClick);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }

    /**
     * Cambio di selezione nella lista Arrivati.
     * <p/>
     */
    private void selezioneArrivati () {
        /* variabili e costanti locali di lavoro */
        PannelloObbligatorie panObb;

        try { // prova ad eseguire il codice

            /* elimina la selezione nella lista Partiti */
            this.getNavPartiti().getLista().eliminaSelezione();

            /* invia un comando di sincronizzazione al Pannello Obbligatorie proprietario */
            panObb = this.getPannelloObbligatorie();
            if (panObb!=null) {
                panObb.sincronizza();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

    /**
     * Cambio di selezione nella lista Partiti.
     * <p/>
     */
    private void selezionePartiti() {
        /* variabili e costanti locali di lavoro */
        PannelloObbligatorie panObb;

        try { // prova ad eseguire il codice
            /* elimina la selezione nella lista Arrivati */
            this.getNavArrivati().getLista().eliminaSelezione();

            /* invia un comando di sincronizzazione al Pannello Obbligatorie proprietario */
            panObb = this.getPannelloObbligatorie();
            if (panObb!=null) {
                panObb.sincronizza();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna il pannello Obbligatorie proprietario di questo Navigatore.
     * <p/>
     * @return il pannello proprietario
     */
    private PannelloObbligatorie getPannelloObbligatorie () {
        /* variabili e costanti locali di lavoro */
        PannelloObbligatorie pan = null;
        Navigatore navMaster;
        TestaStampeNavigatore navTesta;

        try {    // prova ad eseguire il codice

            navMaster = this.getNavPilota();
            if ((navMaster != null) && (navMaster instanceof TestaStampeNavigatore)) {
                navTesta = (TestaStampeNavigatore)navMaster;
                pan = navTesta.getPannello();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }




    private ModuloInterno getModuloInterno() {
        return moduloInterno;
    }


    private void setModuloInterno(ModuloInterno moduloInterno) {
        this.moduloInterno = moduloInterno;
    }




    /**
     * Cambio del record selezionato nella lista Arrivati.
     * </p>
     */
    private class AzSelListaArrivati extends ListaSelAz {
        public void listaSelAz(ListaSelEve unEvento) {
            selezioneArrivati();
        }
    } // fine della classe 'interna'


    /**
     * Cambio del record selezionato nella lista Partiti.
     * </p>
     */
    private class AzSelListaPartiti extends ListaSelAz {
        public void listaSelAz(ListaSelEve unEvento) {
            selezionePartiti();
        }
    } // fine della classe 'interna'


    /**
     * Azione di doppio clic su un record nella lista slave.
     * <p/>
     */
    private class AzDoppioClicArrivati extends ListaDoppioClicAz {
        public void listaDoppioClicAz(ListaDoppioClicEve unEvento) {
            doppioClicArrivati();
        }
    } // fine della classe interna


    /**
     * Azione di doppio clic su un record nella lista slave.
     * <p/>
     */
    private class AzDoppioClicPartiti extends ListaDoppioClicAz {
        public void listaDoppioClicAz(ListaDoppioClicEve unEvento) {
            doppioClicPartiti();
        }
    } // fine della classe interna



}