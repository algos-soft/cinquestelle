package it.algos.albergo.clientealbergo;

import it.algos.base.azione.AzModulo;
import it.algos.base.azione.Azione;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;
import it.algos.base.navigatore.info.InfoLista;
import it.algos.base.portale.Portale;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.ricerca.RicercaBase;
import it.algos.base.toolbar.ToolBar;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;

/**
 * Navigatore dei clienti (albergo).
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 5-giu-2007 ore 13.55.52
 */
public final class NavCliente extends NavigatoreLS implements ClienteAlbergo {

    /**
     * flag per aprire un nuovo cliente come capogruppo oppure membro
     */
    private boolean capogruppo;

    /**
     * codice del capogruppo quando si crea un nuovo membro
     */
    private int codCapogruppo;


    /**
     * Costruttore completo con parametri.
     *
     * @param unModulo modulo di riferimento
     */
    public NavCliente(Modulo unModulo) {
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
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice

            /* regola il navigatore */
            this.setNomeVista(ClienteAlbergo.Vis.standardAlbergo.toString());
            this.addSchedaCorrente(new ClienteAlbergoScheda(this.getModulo()));
            this.setUsaPannelloUnico(true);
            this.setUsaNuovo(false);
            this.setUsaPreferito(true);
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
        Portale portale;
        Azione azione;
        ToolBar toolbar;

        super.inizializza();

        try { // prova ad eseguire il codice

            /* recupera la toolbar lista */
            portale = this.getPortaleLista();
            toolbar = portale.getToolBar();

            /* aggiunge il bottone nuovo capogruppo */
            azione = new AzioneNuovoCapo(this.getModulo());
            toolbar.addBottone(azione, 0);

            /* aggiunge il bottone nuovo membro */
            azione = new AzioneNuovoMembro(this.getModulo());
            toolbar.addBottone(azione, 1);

            /* aggiunge il bottone mostra gruppo */
            azione = new AzioneMostraGruppo(this.getModulo());
            toolbar.addBottone(azione, 5);

            /* rimuove il bottone preferito */
            toolbar.removeBottone(Azione.PREFERITO);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizializza

    @Override
    public void apreRicerca() {

        RicercaBase ricerca = new ClienteAlbergoRicerca();
        this.setRicerca(ricerca);

        super.apreRicerca();
    }

    /**
     * Creazione fisica di un nuovo record.
     * <p/>
     * Crea un capogruppo o un membro in funzione del pulsante che
     * è stato premuto.
     *
     * @return il codice del record creato, -1 se non creato.
     */
    public int creaRecord() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        int linkCapo;

        try { // prova ad eseguire il codice

            /* legge le variabili che sono state regolate dal pulsante premuto */
            if (this.isCapogruppo()) {
                codice = super.creaRecord();
            } else {
                linkCapo = this.getCodCapogruppo();
                codice = ClienteAlbergoModulo.nuovoMembro(linkCapo);
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    @Override
    public void stampaLista() {
        /* variabili e costanti locali di lavoro */
        DialogoStampaLista dialogo;
        DialogoStampaLista.TipiStampaListaClienti tipo;

        try { // prova ad eseguire il codice
            dialogo = new DialogoStampaLista();
            dialogo.avvia();
            if (dialogo.isConfermato()) {
                tipo = dialogo.getTipoSelezionato();
                switch (tipo) {
                    case standard:
                        super.stampaLista();
                        break;
                    case mailing:
                        Filtro filtro = this.getLista().getFiltro();
                        Ordine ordine = this.getLista().getOrdine();
                        StampaPerMailing p = new StampaPerMailing(filtro, ordine);
                        p.showPrintPreviewDialog();
                        p.close();//chiude i dati
                        break;
                    case export_mailing:
                    	exportMailing();
                        break;
                    default : // caso non definito
                        break;
                } // fine del blocco switch
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Aggiunge un nuovo capogruppo
     * <p/>
     *
     * @return il codice del record creato
     */
    private int nuovoCapo() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;

        try {    // prova ad eseguire il codice
            this.setCapogruppo(true);
            codice = super.nuovoRecord();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Aggiunge un nuovo membro al gruppo della persona
     * attualmente selezionata in lista.
     * <p/>
     *
     * @return il codice del record creato
     */
    private int nuovoMembro() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        int linkCapo = 0;

        try {    // prova ad eseguire il codice

            /* recupera il link capogruppo del record selezionato in lista */
            codice = this.getLista().getChiaveSelezionata();
            linkCapo = this.query().valoreInt(Cam.linkCapo.get(), codice);

            this.setCapogruppo(false);
            this.setCodCapogruppo(linkCapo);

            codice = super.nuovoRecord();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Mostra tuti i componenti del gruppo della persona
     * attualmente selezionata in lista.
     * <p/>
     */
    private void mostraGruppo() {
        /* variabili e costanti locali di lavoro */
        int codice;
        int linkCapo;
        Filtro filtro;

        try {    // prova ad eseguire il codice

            /* recupera il link capogruppo del record selezionato in lista */
            codice = this.getLista().getChiaveSelezionata();
            linkCapo = this.query().valoreInt(Cam.linkCapo.get(), codice);

            /* regola il filtro corrente della lista */
            filtro = FiltroFactory.crea(ClienteAlbergo.Cam.linkCapo.get(), linkCapo);
            this.setFiltroCorrente(filtro);
            this.aggiornaLista();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
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
        boolean abilitaNuovi;
        boolean abilitaCapo;
        boolean abilitaMembro;
        boolean abilita;
        JButton bottone;
        InfoLista info;

        try { // prova ad eseguire il codice

            super.sincronizza();

            /* possibilità di creare un nuovo record nella lista */
            info = (InfoLista)this.getPortaleLista().getInfoStato();
            abilitaNuovi = info.isPossoCreareNuoviRecord();

            /**
             * abilitazione del pulsante nuovo capogruppo
             * - deve essere abilitato Nuovo Record nella lista
             */
            abilitaCapo = abilitaNuovi;
            bottone = this.getPortaleLista().getToolBar().getBottone(AzioneNuovoCapo.CHIAVE);
            bottone.setEnabled(abilitaCapo);

            /**
             * abilitazione del pulsante nuovo membro
             * - deve essere abilitato Nuovo Record nella lista
             * - deve esserci uno e un solo record selezionato
             */
            abilitaMembro = false;
            if (abilitaNuovi) {
                abilitaMembro = this.getLista().isRigaSelezionata();
            }// fine del blocco if
            bottone = this.getPortaleLista().getToolBar().getBottone(AzioneNuovoMembro.CHIAVE);
            bottone.setEnabled(abilitaMembro);

            /**
             * abilitazione del pulsante mostra gruppo
             * - deve esserci uno e un solo record selezionato
             */
            abilita = this.getLista().isRigaSelezionata();
            bottone = this.getPortaleLista().getToolBar().getBottone(AzioneMostraGruppo.CHIAVE);
            bottone.setEnabled(abilita);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }
    
    /**
     * Esportazione della lista correntemente visualizzata
     * in un file excel con i dati per effettuare un mailing.
     */
    private void exportMailing(){
    	
        JFileChooser chooser = new JFileChooser();
//        FileNameExtensionFilter filter = new FileNameExtensionFilter(
//            "Excel file", "xls");
//        chooser.setFileFilter(filter);
        
        int returnVal = chooser.showSaveDialog(getPortaleNavigatore().getWindow());

        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	File file = chooser.getSelectedFile();
        	String path = file.getPath();
        	String ext = Lib.File.getEstensione(file.getName());
        	if (Lib.Testo.isVuota(ext)) {
        		path+=".xls";
			}
        	
        	// check existence
        	boolean cont = true;
        	File finalFile = new File(path);
        	if (finalFile.exists()) {
				MessaggioDialogo m = new MessaggioDialogo("Il file "+path+" esiste già.\nLo vuoi sovrascivere?");
				cont = m.isConfermato();
			}
        	
        	// execute
        	if (cont) {
            	ExportPerMailing e = new ExportPerMailing(this.getLista().getFiltro(),path);
            	e.run();
            	new MessaggioAvviso("Terminato.\n"+path);
			}
        	
        }

    }


    private boolean isCapogruppo() {
        return capogruppo;
    }


    private void setCapogruppo(boolean capogruppo) {
        this.capogruppo = capogruppo;
    }


    private int getCodCapogruppo() {
        return codCapogruppo;
    }


    private void setCodCapogruppo(int codCapogruppo) {
        this.codCapogruppo = codCapogruppo;
    }


    /**
     * Apertura di un nuovo capogruppo.
     * <p/>
     * Bottone posto alla prima riga della toolbar <br>
     */
    private final class AzioneNuovoCapo extends AzModulo {

        /* chiave della azione */
        public static final String CHIAVE = "nuovoCapo";


        /**
         * Costruttore completo con parametri.
         *
         * @param modulo di riferimento
         */
        public AzioneNuovoCapo(Modulo modulo) {
            /* rimanda al costruttore della superclasse */
            super(modulo, POS);

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
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            /* regola le variabili*/
            super.setChiave(CHIAVE);
            super.setTooltip("Aggiunge un nuovo cliente");
            super.setIconaMedia("NuovoCapo24");
        }// fine del metodo inizia


        /**
         * actionPerformed, da ActionListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                nuovoCapo();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    }// fine della classe


    /**
     * Apertura di un nuovo membro del gruppo.
     * <p/>
     * Bottone posto alla seconda riga della toolbar <br>
     */
    private final class AzioneNuovoMembro extends AzModulo {

        public static final String CHIAVE = "nuovoMembro";


        /**
         * Costruttore completo con parametri.
         *
         * @param modulo di riferimento
         */
        public AzioneNuovoMembro(Modulo modulo) {
            /* rimanda al costruttore della superclasse */
            super(modulo, POS);

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
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            /* regola le variabili*/
            super.setChiave(CHIAVE);
            super.setTooltip("Aggiunge un membro al gruppo selezionato");
            super.setIconaMedia("NuovoMembro24");
        }// fine del metodo inizia


        /**
         * actionPerformed, da ActionListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                nuovoMembro();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    }// fine della classe


    /**
     * Mostra tutti i componenti del gruppo del record selezionato.
     * <p/>
     */
    private final class AzioneMostraGruppo extends AzModulo {

        public static final String CHIAVE = "mostraGruppo";


        /**
         * Costruttore completo con parametri.
         *
         * @param modulo di riferimento
         */
        public AzioneMostraGruppo(Modulo modulo) {
            /* rimanda al costruttore della superclasse */
            super(modulo, POS);

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
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            /* regola le variabili*/
            super.setChiave(CHIAVE);
            super.setTooltip("Mostra i componenti del gruppo");
            super.setIconaMedia("MostraGruppo24");
        }// fine del metodo inizia


        /**
         * actionPerformed, da ActionListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                mostraGruppo();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    }// fine della classe


}// fine della classe
