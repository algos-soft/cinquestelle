package it.algos.gestione.anagrafica.categoria;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.progetto.Progetto;

import java.util.ArrayList;

/**
 * CatAnagrafica - Contenitore dei riferimenti agli oggetti del package.
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
 * @version 1.0 / 20-mar-2009
 */
public final class CatAnagraficaModulo extends ModuloBase implements CatAnagrafica {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = CatAnagrafica.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = CatAnagrafica.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = CatAnagrafica.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public CatAnagraficaModulo() {
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
    public CatAnagraficaModulo(AlberoNodo unNodo) {
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
        super.setModello(new CatAnagraficaModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

        /* regola il flag (le tabelle usano finestre piu' piccole) (facoltativo) */
        super.setTabella(true);

        /* selezione della scheda (facoltativo) */
        super.addSchedaNavCorrente(new CatAnagraficaScheda(this));
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
        try { // prova ad eseguire il codice
//            this.creaDefault();
            this.checkHardcoded();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return super.inizializza();
    } // fine del metodo


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
            this.getNavigatoreDefault().setUsaFrecceSpostaOrdineLista(true);
            this.getNavigatoreDefault().setUsaPannelloUnico(true);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione iniziale di alcuni valori.
     * <p/>
     * Controlla la tavola sul database <br>
     * Se la tavola è vuota, inserisce alcuni records con valori standard <br>
     */
    private void creaDefault() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int numRec;
        ArrayList<String> valori = null;

        try { // prova ad eseguire il codice
            numRec = this.query().contaRecords();
            continua = (numRec == 0);

            if (continua) {
                valori = Tipo.getSigle();
                continua = Lib.Array.isValido(valori);
            }// fine del blocco if

            /* traverso tutta la collezione */
            if (continua) {
                for (String val : valori) {
                    this.query().nuovoRecord(Cam.sigla.get(), val);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Controlla che i valori obbligatori hard-coded
     * esistano nella tavola.
     * <p/>
     * Controlla che tutti i valori inseriti nella enum esistano sul database
     * con il codice e la sigla appropriati.
     */
    private void checkHardcoded() {

        try { // prova ad eseguire il codice

            for(Tipo unTipo : Tipo.values()){
                int cod = unTipo.getCodice();
                String sigla = unTipo.get();
                if (this.query().isEsisteRecord(cod)) {  //esiste, controlla ed eventualmente aggiorna
                    String stringa = this.query().valoreStringa(Cam.sigla.get(), cod);
                    if (!stringa.equals(sigla)) {
                        this.query().registra(cod, Cam.sigla.get(),sigla);
                    }// fine del blocco if

                } else {  //non esiste, lo crea
                    /* crea e attribuisce il numero di codice in automatico */
                    int newcod = this.query().nuovoRecord(Cam.sigla.get(), sigla);
                    /* modifica il numero di codice e ci mette quello previsto dalla enum */
                    this.query().registra(newcod, this.getCampoChiave().getNomeInterno(), cod);
                }// fine del blocco if-else

            }

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
    public static CatAnagraficaModulo get() {
        return (CatAnagraficaModulo)ModuloBase.get(NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new CatAnagraficaModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main
}