package it.algos.albergo.arrivipartenze;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;

/**
 * - @todo Manca la descrizione.
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 15-gen-2008 ore  14:29
 */
public final class ArriviPartenzeModulo extends ModuloBase implements ArriviPartenze {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = NOME_MODULO;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = NOME_CHIAVE;

    private ArriviPartenzeDialogo dialogo;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public ArriviPartenzeModulo() {
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
    public ArriviPartenzeModulo(AlberoNodo unNodo) {
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
     * Regolazioni iniziali di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore (init) <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice
            /* regola il titolo di questo modulo nei menu di altri moduli */
            super.setTitoloMenu(TITOLO_MENU);

            /* assegna una icona al modulo */
            this.setIcona("arrivipartenze24");

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


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
        boolean inizializzato = false;

        try { // prova ad eseguire il codice
            inizializzato = super.inizializza();

            /* Creazione del dialogo che gestisce il tutto */
            this.setDialogo(new ArriviPartenzeDialogo(this));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return inizializzato;

    } // fine del metodo


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
            super.setModoAvvio(ModoAvvio.senzaGui);
            super.avvia();

//            // todo provvisorio per rigenerare il dialogo
//            this.setDialogo(new ArriviPartenzeDialogo(this));

            this.getDialogo().avvia();

            this.setAvviato(false);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo */


    /**
     * Ritorna il Navigatore corrente.
     * <p/>
     */
    @Override public Navigatore getNavigatoreCorrente() {
        return null;
    } // fine del metodo


    private ArriviPartenzeDialogo getDialogo() {
        return dialogo;
    }


    private void setDialogo(ArriviPartenzeDialogo dialogo) {
        this.dialogo = dialogo;
    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static ArriviPartenzeModulo get() {
        return (ArriviPartenzeModulo)ModuloBase.get(NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new ArriviPartenzeModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main
}// fine della classe