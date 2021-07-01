/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 21 apr 2006
 */

package it.algos.albergo.conto.addebitofisso;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.conto.addebito.Addebito;
import it.algos.albergo.conto.movimento.MovimentoModulo;
import it.algos.albergo.listino.ListinoModulo;
import it.algos.albergo.pianodeicontialbergo.sottoconto.AlbSottoconto;
import it.algos.albergo.pianodeicontialbergo.sottoconto.AlbSottocontoModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.scheda.Scheda;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Pensione - Contenitore dei riferimenti agli oggetti del package.
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
 * @version 1.0 / 21 apr 2006
 */
public final class AddebitoFissoModulo extends MovimentoModulo {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = AddebitoFisso.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = AddebitoFisso.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = AddebitoFisso.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public AddebitoFissoModulo() {
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
    public AddebitoFissoModulo(AlberoNodo unNodo) {
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
        super.setModello(new AddebitoFissoModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

        /* regola il flag (le tabelle usano finestre piu' piccole) (facoltativo) */
        super.setTabella(false);

        super.addSchedaNavCorrente(new AddebitoFissoScheda(this));
        this.setLogicaSpecifica(new AddebitoFissoLogica(this));
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
        Modulo mod;

        try { // prova ad eseguire il codice
            super.inizializza();

//            /* si registra presso il modulo albergo per  */
//            /* sapere quando e' cambiata azienda */
//            mod = Progetto.getModulo(Albergo.NOME_MODULO);
//            if (mod != null) {
//                mod.addListener(AlbergoModulo.Evento.cambioAzienda, new AzioneCambioAzienda());
//            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return true;
    } // fine del metodo


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
            super.creaModulo(new CameraModulo());
            super.creaModulo(new ClienteAlbergoModulo());
            super.creaModulo(new ContoModulo());
            super.creaModulo(new ListinoModulo());
            super.creaModulo(new AlbSottocontoModulo());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge i moduli (e le tabelle) al menu.
     * </p>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle classi specifiche <br>
     * <p/>
     * Aggiunge alla collezione moduli (di questo modulo), gli eventuali
     * moduli (o tabelle), che verranno poi inserite nel menu moduli e
     * tabelle, dalla classe Navigatore <br>
     * I moduli e le tabelle appaiono nei rispettivi menu, nell'ordine in
     * cui sono elencati in questo metodo <br>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * il nome-chiave del modulo <br>
     */
    @Override
    protected void addModuliVisibili() {
        try { // prova ad eseguire il codice
            super.addModuloVisibile(Camera.NOME_MODULO);
            super.addModuloVisibile(ClienteAlbergo.NOME_MODULO);
            super.addModuloVisibile(Conto.NOME_MODULO);
            super.addModuloVisibile(AlbSottoconto.NOME_MODULO);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


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
        Navigatore nav;

        try { // prova ad eseguire il codice

            /* regola il navigatore di default */
            nav = this.getNavigatoreDefault();
            nav.setUsaPannelloUnico(true);

            /* crea un navigatore per il conto */
            nav = new AddebitoFissoNavConto(this);
            this.addNavigatore(nav);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

//    /**
//     * Aggiunge addebiti multipli.
//     * </p>
//     * Apre un dialogo per creare addebiti fissi <br>
//     * Metodo invocato dal bottone specifico nella toolbar della lista <br>
//     */
//    public void aggiungiAddebiti(ActionEvent unEvento) {
//        /* variabili e costanti locali di lavoro */
//        AddebitoFissoLogica logica;
//
//        try { // prova ad eseguire il codice
//            logica = this.getLogicaSpecifica();
//
//            if (logica != null) {
//                logica.aggiungiAddebiti();
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    }// fine del metodo


    /**
     * Crea gli addebiti in base agli addebiti fissi
     * </p>
     * Opera per un dato periodo e un dato elenco di conti.
     *
     * @param dataInizio la data inizio periodo
     * @param dataFine la data fine periodo
     * @param codiciConto i codici dei conti per i quali eseguire l'operazione
     * null per eseguirla per tutti i conti aperti
     */
    public void esegueAddebitiFissi(Date dataInizio,
                                    Date dataFine,
                                    ArrayList<Integer> codiciConto) {
        /* variabili e costanti locali di lavoro */
        AddebitoFissoLogica logica;

        try { // prova ad eseguire il codice
            logica = this.getLogicaSpecifica();

            if (logica != null) {
                logica.creaAddebiti(dataInizio, dataFine, codiciConto);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Aggiorna i navigatori del modulo
     * <p/>
     * I Navigatori utilizzano il filtro azienda.
     */
    private void aggiornaNavigatori() {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<String, Navigatore> navigatori;
//        ContoModulo mod;

        try { // prova ad eseguire il codice
//            mod = (ContoModulo)Progetto.getModulo(Conto.NOME_MODULO);

            navigatori = this.getNavigatori();
            for (Navigatore nav : navigatori.values()) {
//                    nav.setFiltroBase(mod.getFiltroAzienda());
                nav.aggiornaLista();
            }

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiorna il filtro del campo linkconto nella scheda
     * del navigatore di default per vedere solo i conti della
     * azienda attiva
     * <p/>
     */
    private void aggiornaFiltriCombo() {
        /* variabili e costanti locali di lavoro */
        ContoModulo modulo;
        Filtro filtro = null;
        Navigatore nav;
        Scheda scheda;
        Campo campo;

        try { // prova ad eseguire il codice

            modulo = ContoModulo.get();
            filtro = modulo.getFiltroAzienda();

            nav = this.getNavigatoreDefault();
            if (nav != null) {
                scheda = nav.getScheda();
                if (scheda != null) {
                    campo = scheda.getCampo(Addebito.Cam.conto.get());
                    if (campo != null) {
                        campo.setFiltroBase(filtro);
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


//    /**
//     * Invocato quando cambia l'azienda attiva.
//     * <p/>
//     */
//    private void cambioAzienda() {
//        try { // prova ad eseguire il codice
//            this.getModello().setFiltroModello(ContoModulo.get().getFiltroAzienda());
//            this.aggiornaNavigatori();
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    }


    /**
     * Ritorna un filtro per identificare i dati dei quali eseguire
     * il backup per questo modulo.
     * <p/>
     *
     * @return il filtro da applicare in fase di backup
     */
    public Filtro getFiltroBackup() {
        ContoModulo modulo = ContoModulo.get();
        return modulo.getFiltroAzienda();
    }


    /**
     * Ritorna l'oggetto che gestisce la logica specifica del modulo.
     * <p/>
     *
     * @return la logica specifica del modulo
     */
    @Override public AddebitoFissoLogica getLogicaSpecifica() {
        return (AddebitoFissoLogica)super.getLogicaSpecifica();
    }


//    /**
//     * Azione per cambiare azienda
//     */
//    private class AzioneCambioAzienda extends CambioAziendaAz {
//
//        /**
//         * cambioAziendaAz, da CambioAziendaLis.
//         * </p>
//         * Esegue l'azione <br>
//         * Rimanda al metodo sovrascritto, nell'oggetto specifico
//         * della classe che genera questo evento <br>
//         * Sovrascritto nelle sottoclassi <br>
//         *
//         * @param unEvento evento che causa l'azione da eseguire <br>
//         */
//        public void cambioAziendaAz(CambioAziendaEve unEvento) {
//            cambioAzienda();
//        }
//    } // fine della classe interna


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static AddebitoFissoModulo get() {
        return (AddebitoFissoModulo)ModuloBase.get(NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new AddebitoFissoModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

} // fine della classe
