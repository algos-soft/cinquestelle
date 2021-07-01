/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.base.selezione;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.dialogo.Dialogo;
import it.algos.base.dialogo.DialogoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.base.wrapper.ListaSelezione;

import java.util.ArrayList;

/**
 * Selezione - Contenitore dei riferimenti agli oggetti del package.
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
public final class SelezioneModulo extends ModuloBase implements Selezione {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = Selezione.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = Selezione.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = Selezione.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public SelezioneModulo() {
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
    public SelezioneModulo(AlberoNodo unNodo) {
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
        super.setModello(new SelezioneModello());

        /* regola il voce della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il voce di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

        /* regola il flag (le tabelle usano finestre piu' piccole) (facoltativo) */
        super.setTabella(true);
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

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.inizializza();

            if (super.isInizializzato() == false) {

                /* inizializzazioni specifiche della sottoclasse */
//                this. ... ;

            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return true;
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
            Errore.crea(unErrore);
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

            /* Navigatore standard */
//            nav = NavigatoreFactory.listaScheda(this);
//            super.addNavigatore(nav);

            /* Navigatore specifico */
//            nav = new xxxNavigatore(this);
//            this.addNavigatore(nav);

            /* modifica i bottoni della toolbar della lista */
            this.getNavigatoreDefault().setUsaFrecceSpostaOrdineLista(true);

            /* regolazione di un navigatore specifico */
            nav = this.getNavigatore("Chiave");
            //           nav.setDimensioneScheda(370, 300);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


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

//            super.creaModulo(new xxxModulo());
//            super.creaModulo(new yyyModulo());

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
//            super.addModuloVisibile(xxx.NOME_MODULO);
//            super.addModuloVisibile(yyy.NOME_MODULO);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
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
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
    }


    /**
     * Registra sul database la selezione di records della lista.
     * <p/>
     */
    public void salvaSelezioneEsterna(String nomeModulo, int[] codici) {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        String titoloA = "";
        String messaggioA = "";
        String titoloB = "";
        String messaggioB = "";
        String messaggioB1 = "";
        String messaggioB2 = "";
        String nomeCampoNome = "";
        String nomeCampoPrivata = "";
        ArrayList<CampoValore> lista = null;
        Dialogo dialogo = null;
        int codice = 0;
        ListaSelezione oggettoSel = null;
        String nomeSel = "";
        boolean privata = false;
        Campo campoNome = null;
        Campo campoPrivato = null;
        String stringa = "";

        try {    // prova ad eseguire il codice

            /* costanti */
            titoloA = "Salva Selezione";
            messaggioA = "Inserisci il nome per la selezione";
            titoloB = "Selezione esistente";
            messaggioB1 = "Vuoi sovrascrivere la selezione ";
            messaggioB2 = " gi� esistente?";
            nomeCampoNome = "nome";
            nomeCampoPrivata = "privata";

            /* presenta il dialogo e chiede il nome */
            dialogo = DialogoFactory.annullaConferma(titoloA);
            dialogo.setMessaggio(messaggioA);
            campoNome = CampoFactory.testo(nomeCampoNome);
            campoPrivato = CampoFactory.checkBox(nomeCampoPrivata);
            dialogo.addCampo(campoNome);
            dialogo.addCampo(campoPrivato);
            dialogo.avvia();
            if (dialogo.isConfermato()) {
                nomeSel = dialogo.getString(nomeCampoNome);
                privata = dialogo.getBool(nomeCampoPrivata);
                continua = true;
            } else {
                continua = false;
            }// fine del blocco if-else

            /* controlla se esiste gia' */
            if (continua) {
                codice = getCodiceSelezione(nomeModulo, nomeSel);
                if (codice != 0) {
                    continua = false;
                    messaggioB = messaggioB1 + nomeSel + messaggioB2;
                    Dialogo dialogoB = DialogoFactory.annullaConferma();
                    dialogoB.setMessaggio(messaggioB);
                    dialogoB.avvia();
                    if (dialogoB.isConfermato()) {
                        this.query().eliminaRecord(codice);
                        continua = true;
                    }// fine del blocco if
                }// fine del blocco if-else
            }// fine del blocco if

            /* registra la selezione */
            if (continua) {
                /* crea un'istanza */
                oggettoSel = new ListaSelezione(nomeModulo, nomeSel, codici, privata);
                stringa = oggettoSel.getDati();

                lista = new ArrayList<CampoValore>();
                lista.add(new CampoValore(CAMPO_MODULO, nomeModulo));
                lista.add(new CampoValore(CAMPO_NOME, nomeSel));
                lista.add(new CampoValore(CAMPO_DATI, stringa));
                lista.add(new CampoValore(CAMPO_PRIVATA, privata));
                this.query().nuovoRecord(lista);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Restituisce il filtro della selezione.
     * <p/>
     */
    public Filtro getFiltroSelezione(String nomeModulo, String nomeSelezione) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroMod = null;
        Filtro filtroSel = null;

        try {    // prova ad eseguire il codice
            filtroMod = FiltroFactory.crea(CAMPO_MODULO, nomeModulo);
            filtroSel = FiltroFactory.crea(CAMPO_NOME, nomeSelezione);

            filtro = new Filtro();
            filtro.add(filtroMod);
            filtro.add(filtroSel);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Restituisce il codice della selezione.
     * <p/>
     */
    public int getCodiceSelezione(String nomeModulo, String nomeSelezione) {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        Filtro filtro = null;

        try {    // prova ad eseguire il codice

            filtro = this.getFiltroSelezione(nomeModulo, nomeSelezione);
            codice = this.query().valoreChiave(filtro);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Carica dal database la selezione individuata nel dialogo.
     * <p/>
     */
    public int[] caricaSelezioneEsterna(String nomeModulo) {
        /* variabili e costanti locali di lavoro */
        int[] codici = null;
        boolean continua = false;
        Dialogo dialogo;
        String titolo;
        String messaggio;
        Campo campoCombo;
        String nomeCampoCombo;
        ArrayList lista;
        Filtro filtro;
        int pos;
        String selezione = "";
        int cod;
        String testoDati;

        try {    // prova ad eseguire il codice
            /* costanti */
            titolo = "Carica selezione";
            messaggio = "Scegli la selezione da caricare tra quelle esistenti";
            nomeCampoCombo = "combo";

            /* recupera tutti i record */
            filtro = FiltroFactory.crea(CAMPO_MODULO, nomeModulo);
            lista = this.query().valoriCampo(CAMPO_NOME, filtro);

            /* presenta il dialogo e chiede il nome */
            dialogo = DialogoFactory.annullaConferma(titolo);
            dialogo.setMessaggio(messaggio);
            campoCombo = CampoFactory.comboInterno(nomeCampoCombo);
            campoCombo.setValoriInterni(lista);
            dialogo.addCampo(campoCombo);
            dialogo.avvia();

            /* risposta del dialogo */
            if (dialogo.isConfermato()) {
                pos = dialogo.getInt(nomeCampoCombo);
                pos--;
                selezione = (String)lista.get(pos);
                continua = true;
            }// fine del blocco if

            if (continua) {
                filtro = this.getFiltroSelezione(nomeModulo, selezione);
                cod = this.query().valoreChiave(filtro);
                testoDati = this.query().valoreStringa(CAMPO_DATI, cod);
                codici = Lib.Array.getArray(testoDati);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codici;
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new SelezioneModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

} // fine della classe
