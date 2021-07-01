package it.algos.albergo.stampeobbligatorie.notifica;

import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.stampeobbligatorie.ModuloInterno;
import it.algos.albergo.stampeobbligatorie.ObbligNavigatore;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.lista.Lista;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.wrapper.SetValori;
import it.algos.gestione.anagrafica.Anagrafica;

import java.util.ArrayList;

/**
 * Classe 'interna'. </p>
 */
public final class NotificaModuloInterno extends ModuloInterno {

    /**
     * Costruttore completo
     * <p/>
     * Usa un nome automatico casuale per il modulo
     * Rende visibili in vista default tutti i campi specifici
     *
     * @param campi campi specifici del modulo (oltre ai campi standard)
     * @param navMaster navigatore proprietario
     */
    public NotificaModuloInterno(ArrayList<Campo> campi, Navigatore navMaster) {

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
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public boolean prepara() {
        /* variabili e costanti locali di lavoro */
        boolean preparato=false;

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
        super.creaNavigatori();
        this.addNavigatoreCorrente(new NotificaNavInterno(this));
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
        int codCapo;
        boolean capoScheda;
        Modulo modCliente;
        String cognome;
        String nome;
        String soggetto;
        SetValori sv;

        try {    // prova ad eseguire il codice

            /* svuota tutto */
            this.query().eliminaRecords();

            /* crea i records relativi alla riga di testa selezionata */
            lista = this.getListaMaster();
            quante = lista.getQuanteRigheSelezionate();
            if (quante == 1) {
                modCliente = ClienteAlbergoModulo.get();
                codTesta = lista.getChiaveSelezionata();
                codici = NotificaLogica.getCodMembri(codTesta);
                codCapo = NotificaLogica.getCodCapoGruppo(codTesta);
                for (int cod : codici) {

                    /* regola il flag capogruppo scheda */
                    capoScheda = (cod == codCapo);

                    /* costruisce la stringa soggetto */
                    cognome = modCliente.query().valoreStringa(Anagrafica.Cam.cognome.get(), cod);
                    nome = modCliente.query().valoreStringa(Anagrafica.Cam.nome.get(), cod);
                    soggetto = cognome + " " + nome;

                    /* crea il record */
                    sv = new SetValori(this);
                    sv.add(Nomi.linkNotifica.get(), codTesta);
                    sv.add(Nomi.codcliente.get(), cod);
                    sv.add(Nomi.soggetto.get(), soggetto);
                    sv.add(this.getCampoPreferito(), capoScheda);
                    this.query().nuovoRecord(sv.getListaValori());
                }
            }// fine del blocco if

            /* aggiorna la lista */
            this.getNavigatoreCorrente().aggiornaLista();
            this.getLista().eliminaSelezione();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

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
     * Codifica dei nomi dei campi di questo modulo.
     */
    public enum Nomi {

        linkNotifica,
        codcliente,
        soggetto,
        checkdati;

        public String get() {
            return toString();
        }


    }// fine della classe


} // fine della classe 'interna'