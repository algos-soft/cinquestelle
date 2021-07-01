package it.algos.albergo.stampeobbligatorie.notifica;

import it.algos.albergo.stampeobbligatorie.ModuloInterno;
import it.algos.albergo.stampeobbligatorie.ObbligNavigatore;
import it.algos.albergo.stampeobbligatorie.notifica.Notifica.Cam;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.util.Operatore;
import it.algos.base.errore.Errore;
import it.algos.base.evento.lista.ListaDoppioClicAz;
import it.algos.base.evento.lista.ListaDoppioClicEve;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.Lista;
import it.algos.base.lista.ListaBase;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;

import java.util.ArrayList;

/**
 * Navigatore delle Schede di Notifica
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 9-lug-2008 ore 9.51.20
 */
final class NotificaNavigatore extends ObbligNavigatore implements Notifica {

    /* modulo interno per la gestione della sottolista dei singoli clienti */
    private ModuloInterno moduloInterno;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param unModulo modulo di riferimento
     */
    public NotificaNavigatore(Modulo unModulo) {
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
            this.setRigheLista(10);
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
        Navigatore navInterno;
        Lista listaInterna;

        try { // prova ad eseguire il codice

            super.inizializza();

            /* crea il modulo interno con il navigatore e lo aggiunge al portale */
            this.creaModuloInterno();
            navInterno = this.getNavInterno();
            this.getPortaleNavigatore().add(navInterno.getPortaleNavigatore());

            /**
             * aggiunge un listener al doppio clic sulla lista del navigatore interno
             * quando si fa doppio clic sulla lista del nav interno rilancia l'evento
             * doppio clic sulla lista di questo navigatore
             */
            listaInterna = navInterno.getLista();
            if (listaInterna !=null) {
                listaInterna.addListener(new AzDoppioClicListaInterna());
            }// fine del blocco if


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

            /* campo link al record di Notifica (non visibile) */
            campo = CampoFactory.intero(NotificaModuloInterno.Nomi.linkNotifica.get());
            campo.setVisibileVistaDefault(false);
            campi.add(campo);

            /* campo codice cliente (non visibile) */
            campo = CampoFactory.intero(NotificaModuloInterno.Nomi.codcliente.get());
            campo.setVisibileVistaDefault(false);
            campi.add(campo);

            /* campo soggetto */
            campo = CampoFactory.testo(NotificaModuloInterno.Nomi.soggetto.get());
            campo.setTitoloColonna("cliente");
            campo.setVisibileVistaDefault(true);
            campi.add(campo);

            /* campo check di controllo validità dati anagrafici */
            campo = CampoFactory.testo(NotificaModuloInterno.Nomi.checkdati.get());
            campo.setVisibileVistaDefault(true);
            campo.setTitoloColonna("ok");
            campo.setLarLista(40);
            campo.setRidimensionabile(false);
            campo.setRenderer(new NotificaRendererInfoSingolo(campo));
            campi.add(campo);

            mem = new NotificaModuloInterno(campi, this);
            this.setModuloInterno(mem);
            mem.avvia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }




    /**
     * Modifica della selezione di una lista </li>
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato della classe <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void selezioneModificata() {
        /* variabili e costanti locali di lavoro */
        ModuloInterno mod;

        try { // prova ad eseguire il codice
            super.selezioneModificata();

            /* aggiorna la lista del navigatore interno */
            mod = this.getModuloInterno();
            mod.aggiornaLista();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna il codice del cliente correntemente selezionato.
     * <p/>
     * La lista interna ha la precedenza.
     * Se nella lista interna non ce n'è uno solo selezionato, guarda nella lista esterna
     * @return il codice del cliente correntemente selezionato
     */
    public int getCodClienteSelezionato() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        Lista listaInterna;
        Lista listaEsterna;
        int chiave;
        Modulo modulo;

        try {    // prova ad eseguire il codice

            listaInterna = this.getNavInterno().getLista();
            chiave = listaInterna.getChiaveSelezionata();
            if (chiave>0) {
                modulo = this.getModuloInterno();
                codice = modulo.query().valoreInt(NotificaModuloInterno.Nomi.codcliente.get(), chiave);
            } else {
                listaEsterna = this.getLista();
                chiave = listaEsterna.getChiaveSelezionata();
                if (chiave>0) {
                    codice = NotificaLogica.getCodCapoGruppo(chiave);
                }// fine del blocco if
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }





    /**
     * Sincronizza il Navigatore.
     * <p/>
     * Controlla lo stato di tutti i suoi componenti <br>
     * Elabora la sua business logic <br>
     * Regola la GUI di conseguenza <br>
     */
    @Override
    public void sincronizza() {
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
    private Navigatore getNavInterno() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;
        Modulo mod;

        try {    // prova ad eseguire il codice
            mod = this.getModuloInterno();
            if (mod != null) {
                nav = mod.getNavigatoreCorrente();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }

    /**
     * @return array dei wrapper di tutti i gruppi arrivati
     */
    public WrapGruppoArrivato[] getGruppiArrivati(){
    	
    	ArrayList<WrapGruppoArrivato> listaGruppi = new ArrayList<WrapGruppoArrivato>();
    	
        Lista listaArrivi = this.getLista();
        int[] keysNotifica = listaArrivi.getChiaviVisualizzate();
        for(int idNotifica : keysNotifica){
        	
            int[] codici = NotificaLogica.getCodMembri(idNotifica);
            int codCapo = NotificaLogica.getCodCapoGruppo(idNotifica);
            int codPeriodo = NotificaModulo.get().query().valoreInt(Cam.linkPeriodo.get(), idNotifica);


        	WrapGruppoArrivato wrapper = new WrapGruppoArrivato(codCapo, codPeriodo, idNotifica);

            for(int codCli : codici){
            	if (codCli!=codCapo) {
                	wrapper.addMembro(codCli);
				}
            }

            listaGruppi.add(wrapper);
        	
        	
        }
    	
    	return listaGruppi.toArray(new WrapGruppoArrivato[listaGruppi.size()]);
    }

    private ModuloInterno getModuloInterno() {
        return moduloInterno;
    }


    private void setModuloInterno(ModuloInterno moduloInterno) {
        this.moduloInterno = moduloInterno;
    }

    /**
     * Azione di doppio clic su un record nella lista slave.
     * <p/>
     */
    private class AzDoppioClicListaInterna extends ListaDoppioClicAz {

        /**
         * Esegue l'azione
         * <p/>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void listaDoppioClicAz(ListaDoppioClicEve unEvento) {
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
    } // fine della classe interna




}
