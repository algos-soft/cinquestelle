package it.algos.albergo.stampeobbligatorie.istat;

import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.stampeobbligatorie.ModuloInterno;
import it.algos.albergo.stampeobbligatorie.ObbligNavigatore;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.lista.Lista;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.wrapper.SetValori;
import it.algos.gestione.anagrafica.Anagrafica;

import java.util.ArrayList;

/**
 * Modulo per visualizzare i clienti arrivari e partiti
 * nell'ISTAT.
 * </p>
 */
public final class ISTATModuloInterno extends ModuloInterno {

    /**
     * Costruttore completo
     * <p/>
     * Usa un nome automatico casuale per il modulo
     * Rende visibili in vista default tutti i campi specifici
     *
     * @param campi campi specifici del modulo (oltre ai campi standard)
     * @param navMaster navigatore proprietario
     */
    public ISTATModuloInterno(ArrayList<Campo> campi, ISTATNavigatore navMaster) {

        super(campi, navMaster);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice

            this.getModello().setUsaCampoPreferito(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public boolean prepara() {
        /* variabili e costanti locali di lavoro */
        boolean preparato = false;

        try { // prova ad eseguire il codice

            preparato = super.prepara();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        return preparato;
    }


    public boolean inizializza() {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            super.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return true;
    } // fine del metodo


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
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        try { // prova ad eseguire il codice


            /* navigatore per elencare i clienti arrivati */
            nav = new ISTATNavInterno(this);
            nav.setNomeChiave(Nav.arrivati.get());
//            nav.setNomeVista(Vis.arrivati.get());
            nav.setFiltroBase(FiltroFactory.creaVero(Nomi.arrivato.get()));
            this.addNavigatore(nav);

            /* navigatore per elencare i clienti partiti */
            nav = new ISTATNavInterno(this);
            nav.setNomeChiave(Nav.partiti.get());
//            nav.setNomeVista(Vis.partiti.get());
            nav.setFiltroBase(FiltroFactory.creaFalso(Nomi.arrivato.get()));
            this.addNavigatore(nav);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Aggiorna la lista di dettaglio in base alla
     * selezione corrente della lista Master.
     * <p/>
     */
    public void aggiornaLista() {
        /* variabili e costanti locali di lavoro */
        Lista lista;
        int quante;
        int codTesta;
        int[] codici;
        Navigatore nav;

        try {    // prova ad eseguire il codice

            /* svuota tutto */
            this.query().eliminaRecords();

            /* crea i records relativi alla riga di testa selezionata */
            lista = this.getListaMaster();
            quante = lista.getQuanteRigheSelezionate();
            if (quante == 1) {

                codTesta = lista.getChiaveSelezionata();

                codici = ISTATLogica.getCodArrivati(codTesta);
                this.creaRecords(codTesta, codici, true);

                codici = ISTATLogica.getCodPartiti(codTesta);
                this.creaRecords(codTesta, codici, false);

            }// fine del blocco if

            /* aggiorna la lista */
            nav = this.getNavArrivati();
            nav.aggiornaLista();
            nav.getLista().eliminaSelezione();
            nav = this.getNavPartiti();
            nav.aggiornaLista();
            nav.getLista().eliminaSelezione();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Crea i record per le persone arrivate o partite.
     * <p/>
     *
     * @param codRigaIstat codice chave della riga di riferimento
     * @param codici elenco dei codici cliente
     * @param arrivati true per arrivati false per partiti
     */
    private void creaRecords(int codRigaIstat, int[] codici, boolean arrivati) {
        /* variabili e costanti locali di lavoro */
        Modulo modCliente;
        String cognome;
        String nome;
        String soggetto;
        SetValori sv;

        try {    // prova ad eseguire il codice
            modCliente = ClienteAlbergoModulo.get();

            for (int cod : codici) {

                /* costruisce la stringa soggetto */
                cognome = modCliente.query().valoreStringa(Anagrafica.Cam.cognome.get(), cod);
                nome = modCliente.query().valoreStringa(Anagrafica.Cam.nome.get(), cod);
                soggetto = cognome + " " + nome;

                /* crea il record */
                sv = new SetValori(this);
                sv.add(Nomi.linkRigaISTAT.get(), codRigaIstat);
                sv.add(Nomi.codcliente.get(), cod);
                sv.add(Nomi.soggetto.get(), soggetto);
                sv.add(Nomi.arrivato.get(), arrivati);
                this.query().nuovoRecord(sv.getListaValori());
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna il Navigatore dei clienti Arrivati.
     * <p/>
     *
     * @return il Navigatore dei clienti Arrivati
     */
    private Navigatore getNavArrivati() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;

        try {    // prova ad eseguire il codice
            nav = this.getNavigatore(ISTATModuloInterno.Nav.arrivati.get());
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


    /**
     * Ritorna il Navigatore dei clienti Partiti.
     * <p/>
     *
     * @return il Navigatore dei clienti Partiti
     */
    private Navigatore getNavPartiti() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;

        try {    // prova ad eseguire il codice
            nav = this.getNavigatore(ISTATModuloInterno.Nav.partiti.get());
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


    /**
     * Invocato quando si apre la scheda cliente e si esce registrando.
     * <p/>
     */
    void clienteModificato () {
        /* variabili e costanti locali di lavoro */
        Navigatore unNavigatore;
        ObbligNavigatore navigatore;

        try { // prova ad eseguire il codice
            unNavigatore = this.getNavMaster();
            if ((unNavigatore!=null) && (unNavigatore instanceof ObbligNavigatore)) {
                navigatore = (ObbligNavigatore)unNavigatore;
                navigatore.clienteModificato();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
}




    /**
     * Codifica dei nomi dei navigatori di questo modulo.
     */
    public enum Nav {

        arrivati,
        partiti;


        public String get() {
            return toString();
        }


    }// fine della classe

    /**
     * Codifica dei nomi dei campi di questo modulo.
     */
    public enum Nomi {

        linkRigaISTAT,
        codcliente,
        soggetto,
        arrivato,
        checkdati;


        public String get() {
            return toString();
        }


    }// fine della classe



} // fine della classe 'interna'