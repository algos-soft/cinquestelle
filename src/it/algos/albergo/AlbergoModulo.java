/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      25-gen-2005
 */
package it.algos.albergo;

import it.algos.albergo.arrivipartenze.ArriviPartenze;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.evento.CambioAziendaAz;
import it.algos.albergo.evento.CambioAziendaEve;
import it.algos.albergo.evento.CambioAziendaLis;
import it.algos.albergo.evento.CambioDataAz;
import it.algos.albergo.evento.CambioDataEve;
import it.algos.albergo.evento.CambioDataLis;
import it.algos.albergo.evento.DelAziendeAz;
import it.algos.albergo.evento.DelAziendeEve;
import it.algos.albergo.evento.DelAziendeLis;
import it.algos.albergo.odg.OdgDialogo;
import it.algos.albergo.odg.odgtesta.OdgModulo;
import it.algos.albergo.prenotazione.Prenotazione;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.albergo.preventivo.PreventivoDialogo;
import it.algos.albergo.promemoria.PromemoriaModulo;
import it.algos.albergo.ristorante.menu.Menu;
import it.algos.albergo.ristorante.menu.MenuModulo;
import it.algos.albergo.rubrica.RubricaAlbergo;
import it.algos.albergo.rubrica.RubricaAlbergoModulo;
import it.algos.albergo.stampeobbligatorie.StampeObbligatorieDialogo;
import it.algos.albergo.statistiche.StatDialogo;
import it.algos.albergo.statogiornaliero.StatoGiornaliero;
import it.algos.albergo.tabelle.azienda.Azienda;
import it.algos.albergo.tabelle.azienda.AziendaModulo;
import it.algos.albergo.tabelle.lingua.LinguaModulo;
import it.algos.albergo.tableau.Tableau;
import it.algos.albergo.tableau.TableauAlbergoDatamodel;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.azione.Azione;
import it.algos.base.azione.AzioneBase;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.ConnessioneJDBC;
import it.algos.base.dialogo.Dialogo;
import it.algos.base.dialogo.DialogoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.evento.BaseListener;
import it.algos.base.evento.Eventi;
import it.algos.base.libreria.Lib;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.palette.Palette;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.Icon;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.event.EventListenerList;

/**
 * Albergo - Contenitore dei riferimenti agli oggetti del package.
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
 * @version 1.0    / 25-gen-2005 ore 8.48.07
 */
public class AlbergoModulo extends ModuloBase implements Albergo {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = Albergo.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = Albergo.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = Albergo.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default) <br>
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;

    /**
     * codice dell'azienda attiva
     * (0 per tutte le aziende)
     */
    private int codAziendaAttiva;

    /**
     * flag di attivazione del modulo Ristorante
     */
    private boolean opzioneRistorante;

    /**
     * Tableau (lazy creation)
     */
    private Tableau tableau;

    /**
     * Data corrente del programma
     */
    private Date dataProgramma;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public AlbergoModulo() {
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
    public AlbergoModulo(AlberoNodo unNodo) {
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

        try { // prova ad eseguire il codice

            /* Usa il Look and Feel di sistema (Aqua su Mac OS X) */
            String laf = UIManager.getSystemLookAndFeelClassName();

//            /* Usa il Look and Feel cross-patform (metal) */
//            laf = UIManager.getCrossPlatformLookAndFeelClassName();
            UIManager.setLookAndFeel(laf);


            /**
             * Cambia il tempo di persistenza dei tooltip da 6 secondi
             * (il default) a 8 secondi.
             * Serve principalmente nel booking per lasciare il tempo di leggere
             * i tooltips che sono lunghi.
             * Se vuoi che un tooltip non scompaia basta muovere leggermente il mouse sul
             * componente mentre leggi.
             */
            ToolTipManager.sharedInstance().setDismissDelay(8000);


            /* preferenze specifiche di questo modulo */
            new AlbergoPref();
//            new StampeObbligatoriePref();

            /* regola il titolo della finestra del navigatore */
            super.setTitoloFinestra(TITOLO_FINESTRA);

            /* regola il titolo di questo modulo nei menu di altri moduli */
            super.setTitoloMenu(TITOLO_MENU);

            /* hotkeys aziende */
            int alt = KeyEvent.ALT_DOWN_MASK;
            int ctrl = KeyEvent.CTRL_DOWN_MASK;
            Progetto.addHotKey(HOTKEY_1, KeyEvent.VK_F1, alt, ctrl);
            Progetto.addHotKey(HOTKEY_2, KeyEvent.VK_F2, alt, ctrl);
            Progetto.addHotKey(HOTKEY_0, KeyEvent.VK_F10, alt, ctrl);
            Progetto.addHotKey(HOTKEY_X, KeyEvent.VK_X, alt, ctrl);
            Progetto.addHotKey(HOTKEY_PROMEMORIA, KeyEvent.VK_M, ctrl);
            Progetto.addHotKey(HOTKEY_DATA, KeyEvent.VK_D, ctrl);

            Progetto.setCatUpdate("albergo");

            super.setModoAvvio(ModoAvvio.paletta);

            this.setIcona("albergo16", Azione.ICONA_PICCOLA, true);
            this.setIcona("albergo24", Azione.ICONA_MEDIA, true);

            /* attiva il modulo Ristorante */
            this.setOpzioneRistorante(true);
            
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }// fine del metodo inizia


    public boolean inizializza() {

        super.inizializza();
        this.regolaAzienda(1);

        /* esegue le revisioni */
        ConnessioneJDBC conn;
        conn = (ConnessioneJDBC)this.getConnessione();
        new AlbergoRevisioni(conn);

        /* valore di ritorno, sempre true */
        return true;

    } // fine del metodo


    /**
     * Regolazioni di avvio, ogni volta che questo oggetto deve 'ripartire', per
     * essere sicuri che sia 'pulito' <br>
     * Metodo chiamato da altre classi <br>
     * Viene eseguito tutte le volte che necessita  <br>
     */
    public void avvia() {
        /* variabili e costanti locali di lavoro */
        Palette pal;

        try { // prova ad eseguire il codice

            super.avvia();

            /* aggiunge delle azioni alla palette e la riavvia */
            pal = this.getPalette();
            pal.addAzione(new AzPreventivo());
            pal.addAzione(new AzTableau());
            pal.addAzione(new AzGiornaliera());
            pal.addAzione(new AzOdg());
            pal.addAzione(new AzStampe());
            pal.addAzione(new AzStatistica());

            pal.setOpaque(false);
            pal.avvia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * </p>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * un'istanza provvisoria del modulo <br>
     * Questa istanza viene usata solo per portarsi il percorso della
     * classe (implicito) ed il nome chiave (esplicito) <br>
     * La creazione definitiva del Modulo viene delegata alla classe
     * Progetto nel metodo creaModulo() <br>
     */
    protected void creaModuli() {
        try { // prova ad eseguire il codice

            super.creaModulo(new CameraModulo());
            super.creaModulo(new ContoModulo());
            super.creaModulo(new MenuModulo());
            super.creaModulo(new PrenotazioneModulo());
            super.creaModulo(new LinguaModulo());
            super.creaModulo(new RubricaAlbergoModulo());
            super.creaModulo(new OdgModulo());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge i moduli (e le tabelle) al menu.
     * </p>
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
            super.addModuloVisibile(ClienteAlbergo.NOME_MODULO);
            super.addModuloVisibile(Prenotazione.NOME_MODULO);
            super.addModuloVisibile(Conto.NOME_MODULO);
            super.addModuloVisibile(Menu.NOME_MODULO);
            super.addModuloVisibile(ArriviPartenze.NOME_MODULO);
            super.addModuloVisibile(Presenza.NOME_MODULO);
            super.addModuloVisibile(RubricaAlbergo.NOME_MODULO);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione about.
     * <p/>
     * Apre una finestra di informazioni <br>
     * Metodo invocato dal Gestore Eventi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override public void apreAbout() {
        /* variabili e costanti locali di lavoro */
        AlbergoAbout about;

        try { // prova ad eseguire il codice
            about = new AlbergoAbout();
            about.setProgramma("Albergo by Algos®");
            about.setVersione("2.10");
            about.setIcona(Lib.Risorse.getIcona(this, "albergo32"));
            about.avvia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea una istanza del Tableau.
     * <p/>
     *
     * @return il tableau creato
     */
    private Tableau creaTableau() {
        Tableau tableau = new Tableau(new TableauAlbergoDatamodel(), null, 0);
        return tableau;
    }


    /**
     * Metodo invocato quando viene premuta una HotKey.
     * <p/>
     * Seleziona l'azienda attiva <br>
     *
     * @param chiave della hotkey premuta
     */
    @Override
    public void hotkey(int chiave) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        Dialogo dialogo;
        Modulo moduloAzienda;
        Campo campo;
        Filtro filtro;
        int codAzPrincipale;

        try { // prova ad eseguire il codice

            /* esegue la azione associata alla HotKey */
            Lib.Sist.beep();
            switch (chiave) {
            
            // cercare il seguente commento per tutti  punti del programma disabilitati
            
            // GESTIONE MULTIAZIENDA DISABLED 10-2017 ALEX
            
//                case HOTKEY_0:
//                    this.regolaAzienda(0);
//                    break;
//                case HOTKEY_1:
//                    this.regolaAzienda(1);
//                    break;
//                case HOTKEY_2:
//                    this.regolaAzienda(2);
//                    break;
//
//                case HOTKEY_X:
//
//                    continua = true;
//
//                    /* recupera il codice della azienda principale e controlla che esista */
//                    if (continua) {
//                        moduloAzienda = AziendaModulo.get();
//                        campo = moduloAzienda.getCampoPreferito();
//                        filtro = FiltroFactory.crea(campo, Filtro.Op.UGUALE, true);
//                        codAzPrincipale = moduloAzienda.query().valoreChiave(filtro);
//                        if (codAzPrincipale == 0) {
//                            new MessaggioAvviso("Non è stata definita una azienda principale!");
//                            continua = false;
//                        }// fine del blocco if
//                    }// fine del blocco if
//
//                    /* chiede conferma e lancia l'evento */
//                    if (continua) {
//                        dialogo = DialogoFactory.annullaConferma("Eliminazione aziende");
//                        dialogo.setMessaggio("Vuoi eliminare tutte le aziende tranne la principale?");
//                        dialogo.avvia();
//                        continua = dialogo.isConfermato();
//                    }// fine del blocco if
//
//                    /* lancia l'evento */
//                    if (continua) {
//                        this.fire(AlbergoModulo.Evento.eliminaAziende);
//                        new MessaggioAvviso("Operazione eseguita.");
//                    }// fine del blocco if
//
//                    break;
            
                case HOTKEY_PROMEMORIA:
                    PromemoriaModulo.creaNuovo();
                    break;

                case HOTKEY_DATA:
                    this.regolaDataProgramma();
                    break;


                default: // caso non definito
                    break;
            } // fine del blocco switch

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola la data corrente del programma.
     * <p/>
     * Apre un dialogo e registra la data inserita
     * Se si è scelto di usare la data di sistema, registra data nulla
     * per forzare il getter a richiedere la data ogni volta.
     */
    private void regolaDataProgramma () {

        try {    // prova ad eseguire il codice

            /* accesso diretto alla variabile, il getter
             * esegue delle valutazioni
             * che qui non sono desiderabili.
             */
            Date data = this.dataProgramma;

            DialogoImpostaData d = new DialogoImpostaData(data);
            d.avvia();
            if (d.isConfermato()) {
                if (d.isUsaDataSistema()) {
                    this.setDataProgramma(null);
                } else {
                    this.setDataProgramma(d.getData());
                }// fine del blocco if-else

                /* avvisa tutti i moduli interessati */
                this.fire(AlbergoModulo.Evento.cambioData);

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

}





    /**
     * Regola la variabile che mantiene
     * il codice dell'azienda attiva.
     * <p/>
     *
     * @param codAzienda da attivare
     */
    private void regolaAzienda(int codAzienda) {
        /* variabili e costanti locali di lavoro */
        int cod;
        ContoModulo modConto;
        MenuModulo modMenu;
        PrenotazioneModulo modPren;
        PresenzaModulo modPres;
        OdgModulo modOdg;

        try {    // prova ad eseguire il codice

            AlbergoPref.Albergo.azienda.getWrap().setValore(codAzienda);

            cod = AlbergoPref.Albergo.azienda.intero();
            this.setCodAziendaAttiva(cod);

            /* regola il filtro azienda del modulo Conto */
            modConto = ContoModulo.get();
            if (modConto != null) {
                modConto.regolaFiltro();
            }// fine del blocco if

            /* regola il filtro azienda del modulo Menu */
            modMenu = MenuModulo.get();
            if (modMenu != null) {
                modMenu.regolaFiltro();
            }// fine del blocco if

            /* regola il filtro azienda del modulo Prenotazioni */
            modPren = PrenotazioneModulo.get();
            if (modPren != null) {
                modPren.regolaFiltro();
            }// fine del blocco if

            /* regola il filtro azienda del modulo Presenze */
            modPres = PresenzaModulo.get();
            if (modPres != null) {
                modPres.regolaFiltro();
            }// fine del blocco if

            /* regola il filtro azienda del modulo OdG */
            modOdg = OdgModulo.get();
            if (modOdg != null) {
                modOdg.regolaFiltro();
            }// fine del blocco if


            /* avvisa tutti i moduli interessati */
            this.fire(AlbergoModulo.Evento.cambioAzienda);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna la descrizione dell'azienda attiva.
     * <p/>
     *
     * @return la descrizione
     */
    public String getDescAziendaAttiva() {
        /* variabili e costanti locali di lavoro */
        String descr = "Tutte le aziende";
        Modulo modAzienda;
        int cod;

        try {    // prova ad eseguire il codice
            modAzienda = Progetto.getModulo(Azienda.NOME_MODULO);
            cod = this.getCodAziendaAttiva();
            if (cod > 0) {
                descr = modAzienda.query().valoreStringa(Azienda.CAMPO_DESCRIZIONE, cod);
            }// fine del blocco if

            Date d = AlbergoLib.getDataProgramma();
            String strData = Lib.Data.getGiorno(d);
            strData=Lib.Testo.primaMaiuscola(strData);
            strData+=" "+Lib.Data.getDataEstesa(d);
            descr+=" - "+strData;

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return descr;
    }


    /**
     * Ritorna il codice dell'azienda attiva.
     * <p/>
     * (0 per tutte le aziende)
     *
     * @return il codice dell'azienda attiva
     */
    public static int getCodAzienda() {
        return AlbergoModulo.get().getCodAziendaAttiva();
    }


    /**
     * Ritorna il codice dell'azienda attiva.
     * <p/>
     * (0 per tutte le aziende)
     *
     * @return il codice dell'azienda attiva
     */
    private int getCodAziendaAttiva() {
        return codAziendaAttiva;
    }


    private void setCodAziendaAttiva(int codAziendaAttiva) {
        this.codAziendaAttiva = codAziendaAttiva;
    }


    private boolean isOpzioneRistorante() {
        return opzioneRistorante;
    }


    /**
     * Controlla se l'opzione Ristorante è attiva.
     * <p/>
     */
    public static boolean isRistorante() {
        /* variabili e costanti locali di lavoro */
        boolean attivo = false;

        try {    // prova ad eseguire il codice
            AlbergoModulo mod = AlbergoModulo.get();
            if (mod!=null) {
                attivo = mod.isOpzioneRistorante();
			}
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return attivo;
    }


    private void setOpzioneRistorante(boolean opzioneRistorante) {
        this.opzioneRistorante = opzioneRistorante;
    }


    /**
     * Ritorna il Tableau
     * <p/>
     * Per ora Lo ricrea tutte le volte
     *
     * @return il tableau esistente o creato
     */
    private Tableau getTableau() {
        /* variabili e costanti locali di lavoro */
        Tableau tableau;

        if (this.tableau == null) {
            tableau = this.creaTableau();
            tableau.avvia();
            this.setTableau(tableau);
        }// fine del blocco if

        /* valore di ritorno */
        return this.tableau;

    }


    private void setTableau(Tableau tableau) {
        this.tableau = tableau;
    }


    /**
     * Ritorna la data corrente del programma.
     * <p>
     * La data è mantenuta da AlbergoModulo.
     * Se è nulla, ritorna la data di sistema
     * Altrimenti, ritorna la data impostata.
     *
     * @return la data del corrente del programma
     */
    Date getDataProgramma() {
        /* variabili e costanti locali di lavoro */
        Date data=null;

        try { // prova ad eseguire il codice
            data = this.dataProgramma;
            if (!Lib.Data.isValida(data)) {
                data=Lib.Data.getCorrente();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    private void setDataProgramma(Date dataProgramma) {
        this.dataProgramma = dataProgramma;
    }


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Eventi che vengono lanciati dal modulo <br>
     * Per ogni evento: <ul>
     * <li> classe interfaccia </li>
     * <li> classe evento </li>
     * <li> classe azione </li>
     * <li> metodo azione </li>
     * </ul>
     */
    public enum Evento implements Eventi {

        cambioAzienda(CambioAziendaLis.class,
                CambioAziendaEve.class,
                CambioAziendaAz.class,
                "cambioAziendaAz"),
        eliminaAziende(DelAziendeLis.class,
                DelAziendeEve.class,
                DelAziendeAz.class,
                "delAziendeAz"),
        cambioData(
                CambioDataLis.class,
                CambioDataEve.class,
                CambioDataAz.class,
                "cambioDataAz");


        /**
         * interfaccia listener per l'evento
         */
        private Class listener;

        /**
         * classe evento
         */
        private Class evento;

        /**
         * classe azione
         */
        private Class azione;

        /**
         * metodo
         */
        private String metodo;


        /**
         * Costruttore completo con parametri.
         *
         * @param listener interfaccia
         * @param evento   classe
         * @param azione   classe
         * @param metodo   nome metodo azione
         */
        Evento(Class listener, Class evento, Class azione, String metodo) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setListener(listener);
                this.setEvento(evento);
                this.setAzione(azione);
                this.setMetodo(metodo);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Aggiunge un listener alla lista.
         * <p/>
         * Metodo statico <br>
         * Serve per utilizzare questa Enumeration <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista    degli eventi a cui aggiungersi
         * @param listener dell'evento da lanciare
         */
        public static void addLocale(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.add(Evento.values(), lista, listener);
        }


        /**
         * Rimuove un listener dalla lista.
         * <p/>
         * Metodo statico <br>
         * Serve per utilizzare questa Enumeration <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista    degli eventi da cui rimuoverlo
         * @param listener dell'evento da non lanciare
         */
        public static void removeLocale(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.remove(Evento.values(), lista, listener);
        }


        /**
         * Aggiunge un listener alla lista.
         * <p/>
         * Serve per utilizzare la Enumeration della sottoclasse <br>
         * Metodo (sovra)scritto nelle Enumeration specifiche
         * (le Enumeration delle sottoclassi della classe dove
         * e' questa Enumeration) <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista    degli eventi a cui aggiungersi
         * @param listener dell'evento da lanciare
         */
        public void add(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.add(Evento.values(), lista, listener);
        }


        /**
         * Rimuove un listener dalla lista.
         * <p/>
         * Serve per utilizzare la Enumeration della sottoclasse <br>
         * Metodo (sovra)scritto nelle Enumeration specifiche
         * (le Enumeration delle sottoclassi della classe dove
         * e' questa Enumeration) <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista    degli eventi da cui rimuoverlo
         * @param listener dell'evento da non lanciare
         */
        public void remove(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.remove(Evento.values(), lista, listener);
        }


        public Class getListener() {
            return listener;
        }


        private void setListener(Class listener) {
            this.listener = listener;
        }


        public Class getEvento() {
            return evento;
        }


        private void setEvento(Class evento) {
            this.evento = evento;
        }


        public Class getAzione() {
            return azione;
        }


        private void setAzione(Class azione) {
            this.azione = azione;
        }


        public String getMetodo() {
            return metodo;
        }


        private void setMetodo(String metodo) {
            this.metodo = metodo;
        }


    }// fine della classe


    /**
     * Classe Azione Preventivo
     * <p/>
     */
    private class AzPreventivo extends AzioneBase {

        /**
         * Costruttore completo.
         * <p/>
         */
        public AzPreventivo() {
            /* rimanda al costruttore della superclasse */
            super();

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch
        }// fine del metodo costruttore completo


        /**
         * Regolazioni immediate di riferimenti e variabili. <br>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
//            Icon icona = Lib.Risorse.getIconaBase("AlignLeft24");
            Icon icona = Lib.Risorse.getIcona(AlbergoModulo.get(), "preventivo24");
            this.setIconaPiccola(icona);
            this.setIconaMedia(icona);
            this.setIconaGrande(icona);
            this.setTooltip("Preventivo e prenotazione");
        }


        @Override
        public void actionPerformed(ActionEvent unEvento) {
            new PreventivoDialogo();
        }
    }


    /**
     * Classe Azione Tableau
     * </p>
     */
    private class AzTableau extends AzioneBase {

        /**
         * Costruttore completo.
         * <p/>
         */
        public AzTableau() {
            /* rimanda al costruttore della superclasse */
            super();

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch
        }// fine del metodo costruttore completo


        /**
         * Regolazioni immediate di riferimenti e variabili. <br>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
//            Icon icona = Lib.Risorse.getIconaBase("AlignLeft24");
            Icon icona = Lib.Risorse.getIcona(AlbergoModulo.get(), "tableau24");
            this.setIconaPiccola(icona);
            this.setIconaMedia(icona);
            this.setIconaGrande(icona);
            this.setTooltip("Booking");
        }


        @Override
        public void actionPerformed(ActionEvent unEvento) {
            getTableau().setVisible(true);
        }

    } // fine della classe 'interna'


    /**
     * Classe Azione Ordini del Giorno
     * <p/>
     */
    private class AzOdg extends AzioneBase {

        /**
         * Costruttore completo.
         * <p/>
         */
        public AzOdg() {
            /* rimanda al costruttore della superclasse */
            super();

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch
        }// fine del metodo costruttore completo


        /**
         * Regolazioni immediate di riferimenti e variabili. <br>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            Icon icona = Lib.Risorse.getIcona(AlbergoModulo.get(), "lettomatrimoniale24");
            this.setIconaPiccola(icona);
            this.setIconaMedia(icona);
            this.setIconaGrande(icona);
            this.setTooltip("Ordini del giorno");
        }


        @Override
        public void actionPerformed(ActionEvent unEvento) {
            OdgDialogo.getIstanza();
        }
    }


    /**
     * Classe Azione Giornaliera
     * <p/>
     */
    private class AzGiornaliera extends AzioneBase {

        /**
         * Costruttore completo.
         * <p/>
         */
        public AzGiornaliera() {
            /* rimanda al costruttore della superclasse */
            super();

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch
        }// fine del metodo costruttore completo


        /**
         * Regolazioni immediate di riferimenti e variabili. <br>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            Icon icona = Lib.Risorse.getIconaBase("tabella24");
            this.setIconaPiccola(icona);
            this.setIconaMedia(icona);
            this.setIconaGrande(icona);
            this.setTooltip("Situazione giornaliera");
        }


        @Override
        public void actionPerformed(ActionEvent unEvento) {
            new StatoGiornaliero().avvia();
        }
    }


    /**
     * Classe Azione Stampe
     * <p/>
     */
    private class AzStampe extends AzioneBase {

        /**
         * Costruttore completo.
         * <p/>
         */
        public AzStampe() {
            /* rimanda al costruttore della superclasse */
            super();

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch
        }// fine del metodo costruttore completo


        /**
         * Regolazioni immediate di riferimenti e variabili. <br>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            Icon icona = Lib.Risorse.getIcona(AlbergoModulo.get(), "stampeobbligatorie24");
            this.setIconaPiccola(icona);
            this.setIconaMedia(icona);
            this.setIconaGrande(icona);
            this.setTooltip("Stampe obbligatorie");
        }


        @Override
        public void actionPerformed(ActionEvent unEvento) {
        	int codAzienda=AlbergoModulo.getCodAzienda();
        	if (codAzienda==0) {
        		new MessaggioAvviso("Questa funzione è disponibile solo per una singola azienda.");
        	}else{
        		StampeObbligatorieDialogo dialogo = new StampeObbligatorieDialogo();
        		dialogo.inizializza();
        		dialogo.avvia();

                //StampeObbligatorieDialogo.getIstanza();
			}
        }
    }


    /**
     * Classe Azione Statistica
     * <p/>
     */
    private class AzStatistica extends AzioneBase {

        /**
         * Costruttore completo.
         * <p/>
         */
        public AzStatistica() {
            /* rimanda al costruttore della superclasse */
            super();

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch
        }// fine del metodo costruttore completo


        /**
         * Regolazioni immediate di riferimenti e variabili. <br>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            Icon icona = Lib.Risorse.getIconaBase("Torta24");
            this.setIconaPiccola(icona);
            this.setIconaMedia(icona);
            this.setIconaGrande(icona);
            this.setTooltip("Analisi e Statistiche");
        }


        @Override
        public void actionPerformed(ActionEvent unEvento) {
            new StatDialogo().avvia();
        }
    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static AlbergoModulo get() {
        return (AlbergoModulo)ModuloBase.get(AlbergoModulo.NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali (quasi mai) parametri in ingresso
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new AlbergoModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    }// fine del metodo main


}// fine della classe
