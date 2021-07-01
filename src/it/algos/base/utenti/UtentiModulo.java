/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: alex
 * Date: 01-06-05
 */

package it.algos.base.utenti;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.utenti.gruppi.GruppiModulo;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.EstrattoBase;

import java.util.ArrayList;

/**
 * Utenti - Contenitore dei riferimenti agli oggetti del package.
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
 * @author alex
 * @version 1.0 / 01-06-05
 */
public final class UtentiModulo extends ModuloBase implements Utenti {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = Utenti.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = Utenti.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = Utenti.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public UtentiModulo() {

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
    public UtentiModulo(AlberoNodo unNodo) {

        /* rimanda al costruttore della superclasse */
        /* Usa il terzo parametro true per indicare che e' un modulo fisso */
        super(NOME_CHIAVE, unNodo, true);

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
        super.setModello(new UtentiModello());

        /* regola il voce della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il voce di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

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

            if (this.isInizializzato() == false) {

                /* invoca il metodo sovrascritto della superclasse */
                super.inizializza();

                /* inizializzazioni specifiche della sottoclasse */
                this.regolaUtentiDefault();

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

            super.creaModulo(new GruppiModulo());

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
     * Se non esistono utenti crea gli utenti di default.
     * <p/>
     * Devono esistere almeno un utente Programmatore e un utente Amministratore.<br>
     * Se non esistono, crea quelli di default.
     */
    private void regolaUtentiDefault() {
        /* variabili e costanti locali di lavoro */
        int quanti;
        Filtro filtro;
        ArrayList<CampoValore> campiValore;
        GruppiModulo moduloGruppi;
        CampoValore cv;

        try {    // prova ad eseguire il codice

            moduloGruppi = Progetto.getModuloGruppi();

            /* deve esistere almeno un Programmatore */
            filtro = FiltroFactory.crea(CAMPO_LIVELLO, Utenti.TipoUte.prog.getLivello());
            quanti = this.query().contaRecords(filtro);
            if (quanti == 0) {

                /* se non esiste il gruppo di default lo crea ora */
                moduloGruppi.regolaGruppoDefault();

                /* crea l'utente Programmatore di default associato
                 * al gruppo di default */
                campiValore = new ArrayList<CampoValore>();
                cv = new CampoValore(CAMPO_NOME, Utenti.TipoUte.prog.getSigla());
                campiValore.add(cv);
                cv = new CampoValore(CAMPO_GRUPPO, moduloGruppi.getIdGruppoDefault());
                campiValore.add(cv);
                cv = new CampoValore(CAMPO_LIVELLO, Utenti.TipoUte.prog.getLivello());
                campiValore.add(cv);
                cv = new CampoValore(CAMPO_ABILITATO, true);
                campiValore.add(cv);
                this.query().nuovoRecord(campiValore);

            }// fine del blocco if

            /* deve esistere almeno un Amministratore */
            filtro = FiltroFactory.crea(CAMPO_LIVELLO, Utenti.TipoUte.admin.getLivello());
            quanti = this.query().contaRecords(filtro);
            if (quanti == 0) {

                /* se non esiste il gruppo di default lo crea ora */
                moduloGruppi.regolaGruppoDefault();

                /* crea l'utente Amministratore di default associato
                 * al gruppo di default */
                campiValore = new ArrayList<CampoValore>();
                cv = new CampoValore(CAMPO_NOME, Utenti.TipoUte.admin.getSigla());
                campiValore.add(cv);
                cv = new CampoValore(CAMPO_GRUPPO, moduloGruppi.getIdGruppoDefault());
                campiValore.add(cv);
                cv = new CampoValore(CAMPO_LIVELLO, Utenti.TipoUte.admin.getLivello());
                campiValore.add(cv);
                cv = new CampoValore(CAMPO_ABILITATO, true);
                campiValore.add(cv);
                this.query().nuovoRecord(campiValore);

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il nome di un utente dato l'id.
     * <p/>
     *
     * @param id dell'utente
     *
     * @return il nome dell'utente
     */
    public String getNomeUtente(int id) {
        /* variabili e costanti locali di lavoro */
        String nome = "";

        try { // prova ad eseguire il codice
            nome = this.query().valoreStringa(CAMPO_NOME, id);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nome;
    }


    /**
     * Ritorna l'id di un utente dato il nome.
     * <p/>
     *
     * @param nome il nome dell'utente
     *
     * @return l'id dell'utente, 0 se non trovato
     */
    public int getIdUtente(String nome) {
        return this.query().valoreChiave(CAMPO_NOME, nome);
    }


    /**
     * Ritorna il livello di un utente dato l'id.
     * <p/>
     *
     * @param id l'id dell'utente
     *
     * @return il livello dell'utente, null se non trovato
     *
     * @see Utenti
     */
    public TipoUte getLivelloUtente(int id) {
        /* variabili e costanti locali di lavoro */
        TipoUte tipoLivello = null;
        int livello;

        try { // prova ad eseguire il codice
            livello = this.query().valoreInt(CAMPO_LIVELLO, id);

            tipoLivello = TipoUte.getTipo(livello);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return tipoLivello;
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {

        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new UtentiModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);

    } // fine del metodo main

} // fine della classe
