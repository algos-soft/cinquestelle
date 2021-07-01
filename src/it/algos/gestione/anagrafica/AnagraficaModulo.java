/**
 * Title:     AnagraficaModulo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      5-mag-2004
 */
package it.algos.gestione.anagrafica;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.gestione.anagrafica.categoria.CatAnagraficaModulo;
import it.algos.gestione.anagrafica.categoria.CatAnagrafica;
import it.algos.gestione.anagrafica.tabelle.titolo.Titolo;
import it.algos.gestione.anagrafica.tabelle.titolo.TitoloModulo;
import it.algos.gestione.contatto.ContattoModulo;
import it.algos.gestione.indirizzo.Indirizzo;
import it.algos.gestione.indirizzo.IndirizzoModulo;
import it.algos.gestione.indirizzo.tabelle.citta.Citta;
import it.algos.gestione.tabelle.contibanca.ContiBanca;
import it.algos.gestione.tabelle.contibanca.ContiBancaModulo;
import it.algos.gestione.tabelle.iva.Iva;
import it.algos.gestione.tabelle.iva.IvaModulo;
import it.algos.gestione.tabelle.tipopagamento.TipoPagamento;
import it.algos.gestione.tabelle.tipopagamento.TipoPagamentoModulo;

/**
 * AnagraficaModulo - Contenitore dei riferimenti agli oggetti del package.
 * <br>
 * Questa classe concreta: <ul>
 * <li> Contiene tutti i riferimenti agli oggetti delle classi che servono
 * per gestire una parte del programma </li>
 * <li>  </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 5-mag-2004 ore 8.46.10
 */
public class AnagraficaModulo extends ModuloBase implements Anagrafica {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = Anagrafica.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = Anagrafica.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = Anagrafica.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito) <br>
     */
    public AnagraficaModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(NOME_CHIAVE);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(NOME_PROGRAMMA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo <br>
     *
     * @param unNodo nodo dell'albero moduli
     */
    public AnagraficaModulo(AlberoNodo unNodo) {
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
     * Costruttore completo <br>
     *
     * @param unNomeModulo nome del modulo
     * @param unNodo nodo dell'albero moduli
     */
    public AnagraficaModulo(String unNomeModulo, AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(unNomeModulo, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* selezione del modello (obbligatorio) */
        super.setModello(new AnagraficaModello());

        /* regola il voce della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il voce di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

        /* regola il flag (le tabelle usano finestre piu' piccole) (facoltativo) */
        super.setTabella(false);

        super.setUsaTransazioni(false);

        /* utilizzo di un solo pannello nel navigatore */
        this.getNavigatoreDefault().setUsaPannelloUnico(true);

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
    public boolean inizializza() {

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
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        try { // prova ad eseguire il codice

            /* regola il navigatore default */
            nav = this.getNavigatoreDefault();
//            nav.setUsaPannelloUnico(true);
//            nav.setNomeVista(Vis.standard.toString());
            nav.addSchedaCorrente(new AnagraficaScheda(this));
//            nav.addSchedaCorrente(new AnagraficaSchedaTest(this));

            /* regola il navigatore pop */
//            this.getNavPop().addSchedaCorrente(new AnagraficaScheda(this));
            this.setSchedaPop(new AnagraficaScheda(this));


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
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

            super.creaModulo(new TitoloModulo());
            super.creaModulo(new IndirizzoModulo());
            super.creaModulo(new ContattoModulo());
            super.creaModulo(new TipoPagamentoModulo());
            super.creaModulo(new IvaModulo());
            super.creaModulo(new ContiBancaModulo());   //?
            super.creaModulo(new CatAnagraficaModulo());
            
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
            super.addModuloVisibile(Citta.NOME_MODULO);
            super.addModuloVisibile(TipoPagamento.NOME_MODULO);
            super.addModuloVisibile(Iva.NOME_MODULO);
            super.addModuloVisibile(ContiBanca.NOME_MODULO);
            super.addModuloVisibile(CatAnagrafica.NOME_MODULO);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo

//    /**
//     * Ritorna la partita iva o il codice fiscale
//     * a seconda del tipo di anagrafica.
//     * <p/>
//     * @param codice dell'anagrafica
//     * @param conn connessione da utilizzare per le query
//     * @return la pi/cf
//     */
//    private String getPiCf(int codice, Connessione conn) {
//        /* variabili e costanti locali di lavoro */
//        String picf  = "";
//        Object valore;
//        int codTipo;
//        Anagrafica.Tipi tipo;
//        String nomeCampo=null;
//
//        try {    // prova ad eseguire il codice
//            valore = this.query().valoreCampo(Cam.tipo.get(), codice, conn);
//            codTipo = Libreria.getInt(valore);
//            tipo = Anagrafica.Tipi.getTipo(codTipo);
//            if (tipo!=null) {
//                switch (tipo) {
//                    case privato:
//                        nomeCampo = Cam.codFiscale.get();
//                        break;
//                    case societa:
//                        nomeCampo = Cam.partitaIva.get();
//                        break;
//                    default : // caso non definito
//                        break;
//                } // fine del blocco switch
//            }// fine del blocco if
//            if (Lib.Testo.isValida(nomeCampo)) {
//                picf = (String)this.query().valoreCampo(nomeCampo, codice, conn);
//            }// fine del blocco if
//
//        } catch (Exception unErrore) {    // intercetta l'errore
//            new Errore(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return picf;
//    }

//    /**
//     * Ritorna la partita iva o il codice fiscale
//     * a seconda del tipo di anagrafica.
//     * <p/>
//     * Utilizza la connessione del modulo per le query
//     * @param codice dell'anagrafica
//     * @return la pi/cf
//     */
//    private String getPiCf(int codice) {
//        return this.getPiCf(codice, this.getConnessione());
//    }


    /**
     * Ritorna il codice dell'indirizzo della sede principale di una anagrafica.
     * <p/>
     * Se Privato, ritorna la residenza
     * Se Società, ritorna la sede legale
     *
     * @param codice dell'anagrafica
     *
     * @return il codice di record dell'indirizzo principale,
     *         0 se non ha indirizzo principale
     */
    private int getCodIndirizzoPrincipale(int codice) {
        /* variabili e costanti locali di lavoro */
        int codInd = 0;
        IndirizzoModulo modIndir;
        try {    // prova ad eseguire il codice
            modIndir = IndirizzoModulo.get();
            codInd = modIndir.getIndirizzoPrincipale(codice);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codInd;
    }


    /**
     * Ritorna l'etichetta per corrispondenza relativa all'indirizzo principale.
     * <p/>
     *
     * @param codice dell'anagrafica
     *
     * @return l'etichetta principale
     */
    public String getEtichettaIndirizzoPrincipale(int codice) {
        /* variabili e costanti locali di lavoro */
        String testo = null;
        String nominativo;
        String indirizzo;
        int codIndirizzo;
        EstrattoBase estratto;
        Modulo modIndirizzo;

        try {    // prova ad eseguire il codice

            nominativo = this.getNominativo(codice);

            modIndirizzo = IndirizzoModulo.get();
            codIndirizzo = this.getCodIndirizzoPrincipale(codice);
            estratto = modIndirizzo.getEstratto(Indirizzo.Est.etichettaDocumento, codIndirizzo);
            indirizzo = estratto.getStringa();

            testo = Lib.Testo.concatReturn(nominativo, indirizzo);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Ritorna il nominativo di una anagrafica.
     * <p/>
     * E' voce/nome/cognome in caso di privato,
     * Ragione sociale in caso di società.
     *
     * @param codice dell'anagrafica
     *
     * @return il nominativo
     */
    public String getNominativo(int codice) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        Query query;
        Filtro filtro;
        Dati dati;
        int codTipo;
        Anagrafica.Tipi tipo;

        Campo camTipo = this.getCampo(Cam.privatosocieta.get());
        Campo camTitolo = TitoloModulo.get().getCampo(Titolo.Cam.sigla.get());
        Campo camNome = this.getCampo(Cam.nome.get());
        Campo camCognome = this.getCampo(Cam.cognome.get());
        Campo camRagsoc = this.getCampo(Cam.ragSociale.get());

        try {    // prova ad eseguire il codice

            query = new QuerySelezione(this);
            query.addCampo(camTipo);
            query.addCampo(camTitolo);
            query.addCampo(camNome);
            query.addCampo(camCognome);
            query.addCampo(camRagsoc);
            filtro = FiltroFactory.codice(this, codice);
            query.setFiltro(filtro);
            dati = this.query().querySelezione(query);

            if (dati.getRowCount() > 0) {
                codTipo = dati.getIntAt(camTipo);
                tipo = Anagrafica.Tipi.getTipo(codTipo);
                switch (tipo) {
                    case privato:
                        String titolo = dati.getStringAt(0, camTitolo);
                        String nome = dati.getStringAt(0, camNome);
                        String cognome = dati.getStringAt(0, camCognome);
                        testo = Lib.Testo.concatSpace(titolo, nome, cognome);
                        break;
                    case societa:
                        testo = dati.getStringAt(0, camRagsoc);
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Ritorna un elenco di codici di Anagrafica con dato nome e cognome / ragione sociale.
     * <p/>
     *
     * @param cognomeRS della persona o ragione sociale della società
     * @param nome della persona (non usato per società)
     * @param societa false cerca nei privati true cerca nelle società
     *
     * @return l'elenco dei codici anagrafici trovati
     */
    public static int[] getAnagrafiche(String cognomeRS, String nome, boolean societa) {
        /* variabili e costanti locali di lavoro */
        int[] codici = null;
        Filtro filtro;
        Filtro filtroNome;
        Filtro filtroCognome;
        Modulo mod;

        try {    // prova ad eseguire il codice

            /* crea un filtro che isola gli omonimi */
            if (!societa) { // privato
                filtroCognome = FiltroFactory.crea(Cam.cognome.get(), cognomeRS);
                filtroNome = FiltroFactory.crea(Cam.nome.get(), nome);
                filtro = new Filtro();
                filtro.add(filtroCognome);
                filtro.add(filtroNome);
            } else {                // società
                filtro = FiltroFactory.crea(Cam.ragSociale.get(), cognomeRS);
            }// fine del blocco if-else

            mod = AnagraficaModulo.get();
            codici = mod.query().valoriChiave(filtro);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codici;

    }


    /**
     * Ritorna il codice dell'indirizzo (principale) di una anagrafica.
     * <p/>
     * todo per ora non ho un indirizzo principale, in questa classe ritorna sempre 0
     *
     * @param codAnag codice della anagrafica
     *
     * @return il codice dell'indirizzo
     */
    public static int getCodIndirizzo(int codAnag) {
        return 0;
    }


    /**
     * Ritorna il codice del conto bancario preferito di una data anagrafica.
     * <p/>
     *
     * @param codAnag codice della anagrafica
     *
     * @return il codice del conto preferito, 0 se non esiste
     */
    public int getContoPreferito(int codAnag) {
        /* variabili e costanti locali di lavoro */
        int cod = 0;
        Filtro filtro;
        Modulo modConti;

        try {    // prova ad eseguire il codice
            modConti = Progetto.getModulo(ContiBanca.NOME_MODULO);
            if (modConti != null) {
                filtro = FiltroFactory.crea(ContiBanca.Cam.soggetto.get(), codAnag);
                filtro.add(FiltroFactory.crea(modConti.getCampoPreferito(), true));
                cod = modConti.query().valoreChiave(filtro);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cod;
    }


    /**
     * Ritorna i dati di fatturazione specifici di un cliente.
     * <p/>
     *
     * @param codice dell'anagrafica
     *
     * @return pacchetto wrapper con i dati di fatturazione
     */
    public WrapDatiFatturazione getDatiFatturazione(int codice) {
        return new WrapDatiFatturazione(codice);
    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static AnagraficaModulo get() {
        return (AnagraficaModulo)ModuloBase.get(NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new AnagraficaModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

}// fine della classe
