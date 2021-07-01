package it.algos.albergo.stampeobbligatorie.testastampe;

import it.algos.albergo.stampeobbligatorie.istat.ISTATModulo;
import it.algos.albergo.stampeobbligatorie.notifica.NotificaModulo;
import it.algos.albergo.stampeobbligatorie.ps.PsModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;

/**
 * Provincia - Contenitore dei riferimenti agli oggetti del package.
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
 * @version 1.0 / 3-4-05
 */
public final class TestaStampeModulo extends ModuloBase implements TestaStampe {


    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = TestaStampe.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = TestaStampe.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = TestaStampe.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public TestaStampeModulo() {
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
    public TestaStampeModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(NOME_CHIAVE, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* selezione del modello (obbligatorio) */
        super.setModello(new TestaStampeModello());

        /* regola il voce della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il voce di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

        this.setModoAvvio(ModoAvvio.senzaGui);
    }


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe Progetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Inizializza il gestore , prima di tutto (servono i Comandi per
     * inzializzare i Campi) <br>
     * Tenta di inizializzare il modulo <br>
     * Prima inizializza il modello, se e' riuscito
     * inizializza anche gli altri oggetti del modulo <br>
     *
     * @return true se il modulo e' stato inizializzato
     */
    @Override public boolean inizializza() {
        /* variabili e costanti locali di lavoro */
        boolean inizializzato=false;

        try { // prova ad eseguire il codice
            inizializzato = super.inizializza();

            this.getLista().setOrdinabile(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
        return inizializzato;

    } // fine del metodo


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * </p>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Metodo sovrascritto nelle classi specifiche <br>
     */
    @Override protected void creaModuli() {
        super.creaModulo(new PsModulo());
        super.creaModulo(new NotificaModulo());
        super.creaModulo(new ISTATModulo());
    }


    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Anche eventuale creazione <br>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Regola il Navigatore di default <br>
     * Crea altri Navigatori (oltre a quello standard) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override protected void creaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;
        String chiaveNavMaster;
        String chiaveNavSlave;
        Modulo modSlave;

        try { // prova ad eseguire il codice

            /* pubblica sicurezza */
            nav = new TestaStampeNavigatore(this);
            nav.setNomeVista(TestaStampe.Vis.ps.get());
            this.addNavigatore(nav, Nav.psTesta.get());
            chiaveNavMaster = Nav.psTesta.get();
            modSlave = PsModulo.get();
            chiaveNavSlave = modSlave.getNavigatoreCorrente().getNomeChiave();
            nav = new NavigatoreDoppio(this, chiaveNavMaster, modSlave, chiaveNavSlave);
            this.addNavigatore(nav, Nav.ps.get());

            /* notifica arrivi */
            nav = new TestaStampeNavigatore(this);
            nav.setNomeVista(TestaStampe.Vis.notifica.get());
            this.addNavigatore(nav, Nav.notificaTesta.get());
            chiaveNavMaster = Nav.notificaTesta.get();
            modSlave = NotificaModulo.get();
            chiaveNavSlave = modSlave.getNavigatoreCorrente().getNomeChiave();
            nav = new NavigatoreDoppio(this, chiaveNavMaster, modSlave, chiaveNavSlave);
            nav.setUsaTotaliLista(true);
            this.addNavigatore(nav, Nav.notifica.get());

            /* ISTAT */
            nav = new TestaStampeNavigatore(this);
            nav.setNomeVista(TestaStampe.Vis.istat.get());
            this.addNavigatore(nav, Nav.istatTesta.get());
            chiaveNavMaster = Nav.istatTesta.get();
            modSlave = ISTATModulo.get();
            chiaveNavSlave = modSlave.getNavigatoreCorrente().getNomeChiave();
            nav = new NavigatoreDoppio(this, chiaveNavMaster, modSlave, chiaveNavSlave);
            nav.setUsaTotaliLista(true);
            this.addNavigatore(nav, Nav.istat.get());


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static TestaStampeModulo get() {
        return (TestaStampeModulo)ModuloBase.get(NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new TestaStampeModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

} // fine della classe
