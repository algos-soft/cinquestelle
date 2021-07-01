package it.algos.albergo.promemoria;

import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.promemoria.tipo.TipoPro;
import it.algos.albergo.promemoria.tipo.TipoProModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.progetto.Progetto;

import java.util.Timer;


/**
 * Promemoria - Contenitore dei riferimenti agli oggetti del package.
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
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @version 1.0 / 12-mar-2009
 */
public final class PromemoriaModulo extends ModuloBase {

    private boolean DEBUG = false;

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = Promemoria.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = Promemoria.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = Promemoria.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public PromemoriaModulo() {
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
    public PromemoriaModulo(AlberoNodo unNodo) {
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
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* selezione del modello (obbligatorio) */
        super.setModello(new PromemoriaModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

        /* regola il flag (le tabelle usano finestre piu' piccole) (facoltativo) */
        super.setTabella(false);

        /* preferenze specifiche di questo modulo */
        new PromemoriaPref();

        /* selezione della scheda (facoltativo) */
        super.addSchedaNavCorrente(new PromemoriaScheda(this));
        super.setSchedaPop(new PromemoriaSchedaPop(this));

        // todo non qui, viene chiamato una dozzina di volte!
//        /* lancia il thread separato */
//        this.avvisi();


    }


    @Override
    public boolean inizializza() {

        /* crea il timer di controllo */
        this.avvisi();

        return super.inizializza();

    }


    /**
     * Regolazioni di avvio, ogni volta che questo oggetto deve 'ripartire',
     * per essere sicuri che sia 'pulito'
     * <p/>
     * Metodo chiamato da altre classi <br>
     * Viene eseguito tutte le volte che necessita  <br>
     */
    @Override
    public void avvia() {
        try { // prova ad eseguire il codice
            super.avvia();
            this.getLista().setUltimaRigaVisibile();
            this.setAvviato(false);

            /* selezione inziale di un filtro, tramite il popup */
            this.setPopup(Promemoria.Pop.eseguiti, 1);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Regola il Navigatore di default <br>
     * Crea altri Navigatori (oltre a quello standard) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void creaNavigatori() {
        try { // prova ad eseguire il codice
            /* modifica i bottoni della toolbar della lista */
            this.getNavigatoreDefault().setUsaPannelloUnico(true);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * </p>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Metodo sovrascritto nelle classi specifiche <br>
     * <p/>
     * Aggiunge alla collezione moduli (del Progetto), i moduli necessari <br>
     */
    @Override
    protected void creaModuli() {
        try { // prova ad eseguire il codice
            super.creaModulo(new TipoProModulo());
            super.creaModulo(new CameraModulo());
            super.creaModulo(new ClienteAlbergoModulo());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge i moduli (e le tabelle) al menu.
     * </p>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle classi specifiche <br>
     */
    @Override
    protected void addModuliVisibili() {
        try { // prova ad eseguire il codice
            super.addModuloVisibile(TipoPro.NOME_MODULO);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private void avvisi() {
        /* variabili e costanti locali di lavoro */
        int inizio = 2 * 60 * 1000;
        int frequenza = 15 * 60 * 1000;
        int freqPref;

        try { // prova ad eseguire il codice

            if (!DEBUG) {
                freqPref = PromemoriaPref.Promemoria.frequenza.comboInt();
                switch (freqPref) {
                    case 0:
                        frequenza = 15;
                        break;
                    case 1:
                        frequenza = 30;
                        break;
                    case 2:
                        frequenza = 60;
                        break;
                    case 3:
                        frequenza = 120;
                        break;
                    case 4:
                        frequenza = 240;
                        break;
                    default: // caso non definito
                        frequenza = 30;
                        break;
                } // fine del blocco switch
            } else {
                inizio = 2 * 60 * 1000;
                frequenza = 2;
            }// fine del blocco if-else

            frequenza = frequenza * 60000;

            Timer timer = new Timer();
            timer.schedule(new PromemoriaTask(this), inizio, frequenza);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea un nuovo record aprendo la scheda.
     * <p/>
     */
    public static void creaNuovo() {
        /* variabili e costanti locali di lavoro */
        PromemoriaModulo mod;


        try { // prova ad eseguire il codice
            mod = PromemoriaModulo.get();
            mod.nuovoRecord();
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
    public static PromemoriaModulo get() {
        return (PromemoriaModulo)ModuloBase.get(NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new PromemoriaModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main
}