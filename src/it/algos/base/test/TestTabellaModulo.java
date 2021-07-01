/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.base.test;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.progressbar.OperazioneMonitorabile;
import it.algos.base.progressbar.ProgressBar;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.ricerca.CampoRicerca;
import it.algos.base.ricerca.RicercaBase;
import it.algos.base.wrapper.EstrattoBase;

/**
 * TestTabella - Contenitore dei riferimenti agli oggetti del package.
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
public final class TestTabellaModulo extends ModuloBase implements TestTabella {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = TestTabella.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = TestTabella.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = TestTabella.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public TestTabellaModulo() {
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
    public TestTabellaModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(NOME_CHIAVE, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
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
        super.setModello(new TestTabellaModello());

        /* regola il voce della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il voce di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

        /* regola il flag (le tabelle usano finestre piu' piccole) (facoltativo) */
        super.setTabella(false);

//        Progetto.addHotKey();

//        super.addScheda(new TestTabellaScheda());
    }


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alle impostazioni
     * correnti delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Viene eseguito una sola volta <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public boolean inizializza() {
        boolean riuscito = false;
        Navigatore nav;

        try { // prova ad eseguire il codice

            /* invoca il metodo sovrascritto della superclasse */
            riuscito = super.inizializza();

            RicercaBase ricerca = new RicercaBase(this);
            CampoRicerca cr;
            cr = ricerca.addCampoRicerca(ModelloAlgos.NOME_CAMPO_CHIAVE);
            cr.setUsaRange(true);
            ricerca.addCampoRicerca(ModelloAlgos.NOME_CAMPO_ORDINE, true);
            cr = ricerca.addCampoRicerca(ModelloAlgos.NOME_CAMPO_DESCRIZIONE);
            ricerca.addCampoRicerca(CAMPO_NUMERO);
            ricerca.addCampoRicerca(CAMPO_IMPORTO, Filtro.Op.MAGGIORE_UGUALE);
            ricerca.addCampoRicerca(CAMPO_DATA, true);
            ricerca.addCampoRicerca(CAMPO_DATA_NASCITA);
            ricerca.addCampoRicerca(CAMPO_CHECK);
            ricerca.addCampoRicerca(CAMPO_COLORI);
            ricerca.addCampoRicerca(CAMPO_COMBO);

            nav = this.getNavigatoreDefault();
//            nav.setRicerca(ricerca);


        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo invocato da altre classi (o anche da questa)
     * ogni volta che questo oggetto deve <i>ripartire</i>,
     * per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void avvia() {

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.avvia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Regola il Navigatore di default <br>
     * Crea altri Navigatori (oltre a quello standard) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void creaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;
        try { // prova ad eseguire il codice

            nav = this.getNavigatoreDefault();
            nav.setUsaFrecceSpostaOrdineLista(true);
//            nav.setAbilitato(false);
//            nav.setModificabile(false);

////            /* modifica i bottoni della toolbar della lista */
//            nav = new TestTabellaNav(this);
//            this.addNavigatoreCorrente(nav);
//            this.getNavigatoreDefault().setUsaFrecceSpostaOrdineLista(true);
//            this.getNavigatoreDefault().setUsaPannelloUnico(false);
//            this.getNavigatoreDefault().setUsaFinestraPop(true);

            /* regolazione di un navigatore specifico */
//            nav = this.getNavigatore("Chiave");
            //           nav.setDimensioneScheda(370, 300);
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea eventuali azioni specifiche.
     * <p/>
     * Le azioni vengono aggiunte al navigatore corrente <br>
     * Le azioni vengono aggiunte alla toolbar della lista <br>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaAzioni() {
        /* variabili e costanti locali di lavoro */
        String icona;
        String metodo;
        String help;

        try { // prova ad eseguire il codice

            /* azione di prova */
            icona = "Help24";
            metodo = "test";
            help = "icona di prova";
            super.creaAzione(icona, metodo, help);

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * .
     * <p/>
     */
    public void test() {
        Navigatore nav;

        try {    // prova ad eseguire il codice
            nav = this.getNavigatoreCorrente();

            /* crea l'operazione da eseguire */
            OperazioneLunga op = new OperazioneLunga(nav.getProgressBar(), "Elaborazione", true);
            op.avvia();

//            /* crea un monitor con l'operazione */
//            Monitor monitor = new Monitor(op, nav.getProgressBar(), 100);

            int a = 87;

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Classe 'interna'. </p>
     */
    public final class OperazioneLunga extends OperazioneMonitorabile {

        int quanti = 0;

        int max = 50;

//        boolean done = false;


        public OperazioneLunga(ProgressBar pb, String mex, boolean breakAbilitato) {
            super(pb, mex, breakAbilitato);
        }// fine del metodo costruttore completo


        public int getMax() {
            return max;
        }


        public int getCurrent() {
            return quanti;
        }

//        public boolean isDone() {
//            return done;
//        }


        public void start() {

            for (int k = 0; k < max; k++) {
                quanti++;
//                    System.out.print("\n" + quanti);
                try { // prova ad eseguire il codice
                    Thread.sleep(100l);
                } catch (Exception unErrore) { // intercetta l'errore
                    new Errore(unErrore);
                }// fine del blocco try-catch

                if (super.isInterrompi()) {
                    break;
                }// fine del blocco if

            } // fine del ciclo for

//                done = true;

        }

    } // fine della classe 'interna'


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

            super.creaModuli();

//            super.creaModulo(new Tab2Modulo());
//            super.creaModulo(new Tab3Modulo());
//            super.creaModulo(new IndirizzoModulo());
//            super.creaModulo(new yyyModulo());

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
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
//            super.addModuloVisibile(Tab2.NOME_MODULO);
//            super.addModuloVisibile(Tab3.NOME_MODULO);
//            super.addModuloVisibile(yyy.NOME_MODULO);
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce un estratto specifico di questo modulo.
     * </p>
     * Gli estratti sono sempre specifici <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * Viene creato un nuovo oggetto ad ogni richiesta <br>
     *
     * @param codice come da codifica in interfaccia
     *
     * @return estratto appena creato
     *
     * @see it.algos.base.wrapper.EstrattoBase
     */
    protected EstrattoBase getEstratto(int codice) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEstratto = null;

        try { // prova ad eseguire il codice
            /*  */
            switch (codice) {
//                case Roma.ESTRATTO_kkk:
//                       unEstratto = this.getEstrattoPippo();
//                    break;
                case 2:

                    break;
                case 3:

                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new TestTabellaModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

} // fine della classe
