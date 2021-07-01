/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      24-gen-2005
 */
package it.algos.albergo.ristorante.menu;

import it.algos.albergo.Albergo;
import it.algos.albergo.AlbergoModulo;
import it.algos.albergo.evento.CambioAziendaAz;
import it.algos.albergo.evento.CambioAziendaEve;
import it.algos.albergo.evento.CambioDataAz;
import it.algos.albergo.evento.CambioDataEve;
import it.algos.albergo.evento.DelAziendeAz;
import it.algos.albergo.evento.DelAziendeEve;
import it.algos.albergo.ristorante.Ristorante;
import it.algos.albergo.ristorante.fisso.FissoModulo;
import it.algos.albergo.ristorante.lingua.LinguaModulo;
import it.algos.albergo.ristorante.menu.stampa.cliente.StampaMenu;
import it.algos.albergo.ristorante.menu.stampa.cucina.DatiDettaglio;
import it.algos.albergo.ristorante.menu.stampa.cucina.StampaDettaglio;
import it.algos.albergo.ristorante.modifica.ModificaModulo;
import it.algos.albergo.ristorante.righemenupiatto.RMPModulo;
import it.algos.albergo.ristorante.righemenutavolo.RMTModulo;
import it.algos.albergo.tabelle.azienda.AziendaModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.stampa.Printer;
import it.algos.base.stampa.Stampa;

import java.awt.print.Pageable;
import java.util.LinkedHashMap;

/**
 * Menu - Contenitore dei riferimenti agli oggetti del package.
 * <p/>
 * Questa classe concreta: <ul>
 * <li> Contiene tutti i riferimenti agli oggetti delle classi che servono
 * per gestire una parte del programma </li>
 * <li> Regola il riferimento al Modello specifico (obbligatorio) </li>
 * <li> Regola i titoli di Menu e Finestra del Navigatore </li>
 * <li> Regola eventualmente alcuni aspetti specifici del Navigatore </li>
 * <li> Crea altri eventuali <strong>Moduli</strong> indispensabili per il
 * funzionamento di questo modulo </li>
 * <li> Rende visibili nel Menu gli altri moduli </li>
 * <li> Regola eventuali funzionalit&agrave; specifiche del Navigatore </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 24-gen-2005 ore 12.05.47
 */
public final class MenuModulo extends ModuloBase {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = Menu.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = Menu.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = Menu.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default) <br>
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;

    /**
     * filtro per isolare l'azienda corrente
     */
    private Filtro filtroAzienda;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public MenuModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(NOME_CHIAVE);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(NOME_PROGRAMMA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo.
     *
     * @param unNodo nodo dell'albero moduli
     */
    public MenuModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(NOME_CHIAVE, unNodo);

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
        /* selezione del modello (obbligatorio) */
        super.setModello(new MenuModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

        /* selezione della scheda (facoltativo) */
        super.addSchedaNavCorrente(new MenuScheda(this));

        this.setIcona("ristorante24");

    }// fine del metodo inizia


    /**
     * .
     * <p/>
     */
    public boolean inizializza() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito;
        Modulo mod;


        riuscito = super.inizializza();

        try { // prova ad eseguire il codice
            /* si registra presso il modulo albergo (se esiste)
             * per essere informato quando cambia l' azienda
             * e quando vengono eliminate aziende  */
            mod = Progetto.getModulo(Albergo.NOME_MODULO);
            if (mod != null) {
                mod.addListener(AlbergoModulo.Evento.cambioAzienda, new AzioneCambioAzienda());
                mod.addListener(AlbergoModulo.Evento.eliminaAziende, new AzioneDelAziende());
                mod.addListener(AlbergoModulo.Evento.cambioData, new AzioneCambioData());

            }// fine del blocco if

            /* regola l'azienda attiva */
            this.cambioAzienda();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Regola il Navigatore di default e crea altri Navigatori.
     */
    protected void creaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;

        try {    // prova ad eseguire il codice
            nav = new MenuNavigatore(this);
            this.addNavigatoreCorrente(nav);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * <br>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * un'istanza provvisoria del modulo <br>
     * Questa istanza viene usata solo per portarsi il percorso della
     * classe (implicito) ed il nome chiave (esplicito) <br>
     * La creazione definitiva del Modulo viene delegata alla classe
     * Progetto nel metodo creaModulo() <br>
     */
    protected void creaModuli() {
        try { // prova ad eseguire il codice

//            super.creaModulo(new PiattoModulo());
            super.creaModulo(new FissoModulo());
//            super.creaModulo(new TavoloModulo());
            super.creaModulo(new LinguaModulo());
            super.creaModulo(new ModificaModulo());
            super.creaModulo(new RMPModulo());
            super.creaModulo(new RMTModulo());
//            super.creaModulo(new RMOModulo());

//            // todo provvisorio per recupero dati
//            /* crea il modulo solo se Postgres e' attivo */
//            Db database = DbFactory.crea(Db.SQL_POSTGRES);
//            database.setHost("localhost");
//            database.setLogin("postgres");
//            Connessione conn = database.creaConnessione(null);
//            if (conn.test()) {
//                super.creaModulo(new RTOModuloOld());
//            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Aggiunge i moduli (e le tabelle) al menu.
     * <br>
     * Aggiunge alla collezione moduli (di questo modulo), gli eventuali
     * moduli (o tabelle), che verranno poi inserite nel menu moduli e
     * tabelle, dalla classe Navigatore <br>
     * I moduli e le tabelle appaiono nei rispettivi menu, nell'ordine in
     * cui sono elencati in questo metodo <br>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * il nome-chiave del modulo <br>
     */
    protected void addModuliVisibili() {
        try { // prova ad eseguire il codice

            super.addModuloVisibile(Ristorante.MODULO_PIATTO);
            super.addModuloVisibile(Ristorante.MODULO_CATEGORIA);
            super.addModuloVisibile(Ristorante.MODULO_FISSO);
            super.addModuloVisibile(Ristorante.MODULO_MODIFICA);
            super.addModuloVisibile(Ristorante.MODULO_LINGUA);
            super.addModuloVisibile(Ristorante.MODULO_SALA);
            super.addModuloVisibile(Ristorante.MODULO_TAVOLO);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Stampa un menu.
     * <p/>
     * Presenta un dialogo per impostare il tipo di stampa
     * Esegue la stampa richiesta
     *
     * @param codice del menu da stampare
     */
    public static void stampaMenu(int codice) {
        /* variabili e costanti locali di lavoro */
        DialogoStampe dialogo;
        boolean continua;
        Menu.TipoStampa tipoStampa;
        StampaMenu stampaMenu;
        int codLingua;
        DatiDettaglio datiStampa;
        StampaDettaglio stampa;
        boolean ripetiPiatti;
        Pageable pageable=null;

        try {    // prova ad eseguire il codice

            /* presenta il dialogo */
            dialogo = new DialogoStampe(codice);
            dialogo.avvia();
            continua = dialogo.isConfermato();

            /* esegue la stampa richiesta */
            if (continua) {
                tipoStampa = dialogo.getTipoStampa();
                switch (tipoStampa) {
                    case cliente:
                        codLingua = dialogo.getCodSecondaLingua();
                        stampaMenu = new StampaMenu(codice, codLingua);
                        pageable=stampaMenu;
//                        Stampa.eseguiStampa(stampaMenu);
                        break;
                    case servizio:
                        datiStampa = new DatiDettaglio(codice);
                        ripetiPiatti = dialogo.isRipetiPiatti();
                        stampa = new StampaDettaglio(datiStampa, Menu.STAMPA_SERVIZIO);
                        stampa.setRipetiPiatti(ripetiPiatti);
                        Stampa.eseguiStampa(stampa);
//                        pageable = stampa;
                        break;
                    case cucina:
                        datiStampa = new DatiDettaglio(codice);
                        stampa = new StampaDettaglio(datiStampa, Menu.STAMPA_CUCINA);
                        pageable = stampa;
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch


                //faccio vedere il preview di stampa
                if (pageable!=null) {
                    Printer printer = new Printer();
                    printer.addPageable(pageable);
                    printer.showPrintPreviewDialog();
                }// fine del blocco if


            }// fine del blocco if



        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Invocato quando cambia l'azienda attiva.
     * <p/>
     */
    private void cambioAzienda() {
        try { // prova ad eseguire il codice
            this.getModello().setFiltroModello(this.getFiltroAzienda());
            this.aggiornaNavigatori();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Elimina tutte le aziende tranne quella principale.
     * <p/>
     * Invocato quando viene premuto l'apposito tasto.
     */
    private void delAziende() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Modulo moduloAzienda;
        Filtro filtro;
        Campo campo;
        int codAzPrincipale = 0;

        try { // prova ad eseguire il codice

            /* recupera il codice della azienda principale e controlla che esista */
            if (continua) {
                moduloAzienda = AziendaModulo.get();
                campo = moduloAzienda.getCampoPreferito();
                filtro = FiltroFactory.crea(campo, Filtro.Op.UGUALE, true);
                codAzPrincipale = moduloAzienda.query().valoreChiave(filtro);
                if (codAzPrincipale == 0) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* elimina tutti i menu che non appartengono alla azienda principale */
            if (continua) {
                campo = this.getCampo(Menu.Cam.azienda.get());
                filtro = FiltroFactory.crea(campo, Filtro.Op.DIVERSO, codAzPrincipale);
                this.query().eliminaRecords(filtro);
                this.aggiornaNavigatori();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

    /**
     * Invocato al cambio di data del programma.
     * <p/>
     */
    private void cambioData () {
        try {    // prova ad eseguire il codice
//            per ora non fa niente...
//            this.regolaFinestra();
            int a = 87;
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola il filtro del modulo in funzione dell'azienda attiva.
     * <p/>
     */
    public void regolaFiltro() {
        Filtro filtro = null;
        AlbergoModulo modAlbergo;
        Modulo modMenu;
        Campo campoMenuAzienda;
        int codAz;

        try { // prova ad eseguire il codice
            modAlbergo = (AlbergoModulo)Progetto.getModulo(Albergo.NOME_MODULO);
            if (modAlbergo != null) {
                codAz = AlbergoModulo.getCodAzienda();
                if (codAz > 0) {
                    modMenu = Progetto.getModulo(Menu.NOME_MODULO);
                    campoMenuAzienda = modMenu.getCampo(Menu.Cam.azienda.get());
                    filtro = FiltroFactory.crea(campoMenuAzienda, codAz);
                }// fine del blocco if
            }// fine del blocco if

            this.setFiltroAzienda(filtro);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiorna i navigatori del modulo
     * <p/>
     */
    private void aggiornaNavigatori() {
        LinkedHashMap<String, Navigatore> navigatori;

        try { // prova ad eseguire il codice
            navigatori = this.getNavigatori();
            for (Navigatore nav : navigatori.values()) {
//                nav.setFiltroBase(this.getFiltroAzienda());
                nav.aggiornaLista();
            }
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

//    /**
//     * Ritorna un filtro per identificare i dati dei quali eseguire
//     * il backup per questo modulo.
//     * <p/>
//     * @return il filtro da applicare in fase di backup
//     */
//    public Filtro getFiltroBackup() {
//        MenuModulo modulo = MenuModulo.get();
//        return modulo.getFiltroAzienda();
//    }


    /**
     * Azione per cambiare azienda
     */
    private class AzioneCambioAzienda extends CambioAziendaAz {

        /**
         * cambioAziendaAz, da CambioAziendaLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void cambioAziendaAz(CambioAziendaEve unEvento) {
            cambioAzienda();
        }
    } // fine della classe interna


    /**
     * Azione per eliminare delle aziende
     */
    private class AzioneDelAziende extends DelAziendeAz {

        /**
         * cambioAziendaAz, da CambioAziendaLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void delAziendeAz(DelAziendeEve unEvento) {
            delAziende();
        }
    } // fine della classe interna


    /**
     * Azione al cambio della data programma
     */
    private class AzioneCambioData extends CambioDataAz {

        /**
         * Esegue l'azione
         * <p>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void cambioDataAz(CambioDataEve unEvento) {
            cambioData();
        }
    } // fine della classe interna


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static MenuModulo get() {
        return (MenuModulo)ModuloBase.get(MenuModulo.NOME_CHIAVE);
    }


    private Filtro getFiltroAzienda() {
        return filtroAzienda;
    }


    private void setFiltroAzienda(Filtro filtro) {
        this.filtroAzienda = filtro;
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali (quasi mai) parametri in ingresso
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new MenuModulo();

        /*
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    }// fine del metodo main

}// fine della classe
