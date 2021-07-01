package it.algos.albergo.preventivo;

import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.dialogo.DialogoBase;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.progetto.Progetto;
import it.algos.base.wrapper.WrapErrori;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: gac
 * Date: 31-mar-2009
 * Time: 12.27.37
 */
public class PanPrenotazione extends PannelloFlusso {


    /* Preventivo di riferimento */
    private PreventivoDialogo preventivo;


    /**
     * Costruttore completo con parametri.
     *
     * @param preventivo di riferimento
     */
    public PanPrenotazione(PreventivoDialogo preventivo) {
        /* rimanda al costruttore della superclasse */
        super(Layout.ORIENTAMENTO_VERTICALE);

        /* regola le variabili di istanza coi parametri */
        this.setPreventivo(preventivo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni iniziali di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore (init) <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice
            this.creaCampi();
            this.posizionaCampi();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Creazione dei campi.
     * </p>
     */
    private void creaCampi() {
        /* variabili e costanti locali di lavoro */
        PreventivoDialogo preve;

        try { // prova ad eseguire il codice

            preve = this.getPreventivo();
            Nome.creaCloniCampo(preve);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Aggiunta e Posizionamento dei campi nel pannello.
     * </p>
     */
    private void posizionaCampi() {
        /* variabili e costanti locali di lavoro */
        PreventivoDialogo preve;
        Pannello pan;

        try { // prova ad eseguire il codice

            preve = this.getPreventivo();

            pan = PannelloFactory.orizzontale(preve);
            pan.add(Nome.cliente.get());
            pan.add(Nome.arrivo.get());
            pan.add(Nome.partenza.get());
            this.add(pan);

            pan = PannelloFactory.orizzontale(preve);
            pan.add(Nome.camera.get());
            pan.add(Nome.preparazione.get());
            pan.add(Nome.adulti.get());
            pan.add(Nome.bambini.get());
            pan.add(Nome.pensione.get());
            this.add(pan);

            pan = PannelloFactory.orizzontale(preve);
            pan.setAllineamento(Layout.ALLINEA_CENTRO);
            pan.add(Nome.arrivoCon.get());
            pan.add(Nome.opzione.get());
            pan.add(Nome.caparra.get());
            pan.add(Nome.canale.get());
            this.add(pan);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Recupera un campo dal dialogo.
     * <p/>
     * @param nome del campo
     * @return il campo
     */
    private Campo getCampo(Nome nome) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        PreventivoDialogo preve;

        try {    // prova ad eseguire il codice
            preve = this.getPreventivo();
            campo = preve.getCampo(nome.get());
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Ritorna un eventuale errore/incompletezza nei dati.
     * <p/>
     * @return l'oggetto descrittivo dell'errore
     */
    public WrapErrori getErrore() {
        /* variabili e costanti locali di lavoro */
        WrapErrori errori = new WrapErrori();
        Campo campo;
        Date data1;
        Date data2;
        int adulti;
        int bambini;

        try {    // prova ad eseguire il codice

            campo = this.getCampo(Nome.cliente);
            if (campo.getInt()==0) {
                errori.add("Manca il cliente");
            }// fine del blocco if

            campo = this.getCampo(Nome.camera);
            if (campo.getInt()==0) {
                errori.add("Manca la camera");
            }// fine del blocco if

            campo = this.getCampo(Nome.arrivo);
            data1 = campo.getData();
            if (Lib.Data.isVuota(data1)) {
                errori.add("Manca la data di arrivo");
            }// fine del blocco if

            campo = this.getCampo(Nome.partenza);
            data2 = campo.getData();
            if (Lib.Data.isVuota(data2)) {
                errori.add("Manca la data di partenza");
            }// fine del blocco if

            if (Lib.Data.isValida(data1) && Lib.Data.isValida(data2)) {
                if (!Lib.Data.isPosteriore(data1,data2)) {
                    errori.add("La partenza deve essere dopo l'arrivo");
                }// fine del blocco if
            }// fine del blocco if

            campo = this.getCampo(Nome.adulti);
            adulti = campo.getInt();
            campo = this.getCampo(Nome.bambini);
            bambini = campo.getInt();
            if (adulti+bambini==0) {
                errori.add("Manca il numero di persone");
            }// fine del blocco if

            campo = this.getCampo(Nome.pensione);
            if (campo.getInt()==0) {
                errori.add("Manca il tipo di pensione");
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return errori;
    }



    private PreventivoDialogo getPreventivo() {
        return preventivo;
    }


    private void setPreventivo(PreventivoDialogo preventivo) {
        this.preventivo = preventivo;
    }


    /**
     * Classe interna Enumerazione.
     */
    public enum Nome {

        cliente(Prenotazione.NOME_MODULO, Prenotazione.Cam.cliente.get()),
        opzione(Prenotazione.NOME_MODULO, Prenotazione.Cam.opzione.get()),
        caparra(Prenotazione.NOME_MODULO, Prenotazione.Cam.caparra.get()),
        canale(Prenotazione.NOME_MODULO, Prenotazione.Cam.canale.get()),
        arrivo(Periodo.NOME_MODULO, Periodo.Cam.arrivoPrevisto.get()),
        partenza(Periodo.NOME_MODULO, Periodo.Cam.partenzaPrevista.get()),
        camera(Periodo.NOME_MODULO, Periodo.Cam.camera.get()),
        preparazione(Periodo.NOME_MODULO, Periodo.Cam.preparazione.get()),
        pensione(Periodo.NOME_MODULO, Periodo.Cam.trattamento.get()),
        adulti(Periodo.NOME_MODULO, Periodo.Cam.adulti.get()),
        bambini(Periodo.NOME_MODULO, Periodo.Cam.bambini.get()),
        arrivoCon(Periodo.NOME_MODULO, Periodo.Cam.arrivoCon.get());


        /**
         * nome del modulo di riferimento
         */
        private String nomeModulo;

        /**
         * titolo da utilizzare
         */
        private String titolo;


        /**
         * Costruttore completo con parametri.
         *
         * @param nomeModulo dal quale clonare il campo
         * @param titolo utilizzato nei popup
         */
        Nome(String nomeModulo, String titolo) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setNomeModulo(nomeModulo);
                this.setTitolo(titolo);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }




        public String get() {
            return this.getTitolo();
        }


        private String getNomeModulo() {
            return nomeModulo;
        }


        private void setNomeModulo(String nomeModulo) {
            this.nomeModulo = nomeModulo;
        }


        public String getTitolo() {
            return titolo;
        }


        private void setTitolo(String titolo) {
            this.titolo = titolo;
        }


        /**
         * Crea tutti i campi e li aggiunge alla collezione del dialogo.
         * <p/>
         * @param dialogo di riferimento
         */
        public static void creaCloniCampo(DialogoBase dialogo) {
            /* variabili e costanti locali di lavoro */

            try { // prova ad eseguire il codice
                for (Nome nome : Nome.values()) {
                    nome.addCloneCampo(dialogo);
                } // fine del ciclo for-each
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Aggiunge al dialogo un clone del campo dal modulo.
         * <p/>
         * @param dialogo di riferimento
         * @return il campo aggiunto
         */
        private Campo addCloneCampo(DialogoBase dialogo) {
            /* variabili e costanti locali di lavoro */
            Campo campo = null;
            Modulo mod;

            try { // prova ad eseguire il codice
                mod = Progetto.getModulo(this.getNomeModulo());
                if (mod != null) {
                    campo = mod.getCloneCampo(this.get());
                    dialogo.addCampoCollezione(campo);
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return campo;
        }


    }// fine della classe


}
