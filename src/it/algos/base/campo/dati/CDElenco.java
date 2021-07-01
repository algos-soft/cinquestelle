/**
 * Title:        CDElenco.java
 * Package:      it.algos.base.campo.dati
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 6 novembre 2003 alle 17.59
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.campo.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.elemento.EFactory;
import it.algos.base.campo.elemento.Elemento;
import it.algos.base.campo.tipodati.tipoarchivio.TAIntero;
import it.algos.base.campo.tipodati.tipoarchivio.TipoArchivio;
import it.algos.base.campo.tipodati.tipomemoria.TMIntero;
import it.algos.base.campo.tipodati.tipomemoria.TipoMemoria;
import it.algos.base.campo.tipodati.tipovideo.TVIntero;
import it.algos.base.campo.tipodati.tipovideo.TipoVideo;
import it.algos.base.campo.video.CampoVideo;
import it.algos.base.elenco.Elenco;
import it.algos.base.elenco.ElencoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.matrice.MatriceDoppia;

import java.util.ArrayList;

//-----------------------------------------------------------------------------
// Classe principale
//-----------------------------------------------------------------------------


/**
 * Classe astratta per implementare un oggetto da <code>CDBase</code> <br>
 * <p/>
 * Questa classe astratta e' responsabile di: <br>
 * A - Gestire i dati per i gruppi di oggetti <br>
 * B - I dati vengono raggruppati in un oggetto di classe <code>Elenco</code> <br>
 * C - Vengono gestiti come un <i>elenco</i>: <il>
 * <li>un gruppo di radio bottoni; <br>
 * <li>un combobox; <br>
 * <li>una lista valori; <br>
 * <li>un gruppo di check box </il> <br>
 * D - Secondo la <i>provenienza</i> dei valori ci sono 2 sottoclassi: <il>
 * <li><code>CDElencoEsterno</code> per la lista valori esterna - link ad una tavola; <br>
 * <li><code>CDElencoInterno</code> per la lista valori interna - hard coded </il> <br>
 * E - Mantiene un flag per la <i>tipologia</i> di memorizzazione: <il>
 * <li>selezione singola - registra direttamente la posizione
 * (codice per i valori esterni -
 * ordine progressivo con cui sono stati creati per i valori interni); <br>
 * <li>selezione multipla - registra un numero che corrisponde ad una serie
 * di valori booleani </il> <br>
 * <p/>
 * <br>
 * <p/>
 * Nota <br>
 * Campo Dati unico per Gruppo Radio e Combo: <br> <br>
 * <p/>
 * Con Lista valori Esterna: <br>
 * Lista Valori contiene: coppie codice - valore, ordinate <br>
 * -------------------------------------------------------------- <br>
 * ARCHIVIO                   MEMORIA                      VIDEO <br>
 * (data logic)               (business logic)             (i/f logic) <br>
 * -------------------------------------------------------------- <br>
 * Intero  -----------------  Intero  --------[DB]-------  Elenco <br>
 * (codice)                   (codice)  (codice<->Elenco)  (oggetto) <br>
 * (vuoto=null)               (vuoto=0)                    (vuoto=0) <br> <br>
 * <p/>
 * Con Lista valori Interna: <br>
 * Lista Valori contiene: elenco valori ordinato <br>
 * -------------------------------------------------------------- <br>
 * ARCHIVIO                   MEMORIA                      VIDEO <br>
 * (data logic)               (business logic)             (i/f logic) <br>
 * -------------------------------------------------------------- <br>
 * Intero  ----------------  Intero  -------------------  Intero <br>
 * (posiz.)                   (posiz.)                     (posiz.) <br>
 * (vuoto=null)               (vuoto=0)                    (vuoto=0) <br> <br>
 *
 * @author Guido Andrea Ceresa e Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  6 novembre 2003 ore 17.59
 */
public abstract class CDElenco extends CDBase {

    private static final TipoArchivio TIPO_ARCHIVIO = TAIntero.getIstanza();

    private static final TipoMemoria TIPO_MEMORIA = TMIntero.getIstanza();

    private static final TipoVideo TIPO_VIDEO = TVIntero.getIstanza();

    /**
     * istanza della classe specializzata per contenere i dati
     */
    protected Elenco elenco = null;

    /**
     * flag - se deve usare un elenco valori interno, anziche uno esterno
     */
    protected boolean isElencoInterno = false;

    /**
     * flag - se deve usare un renderer specializzato per la lista che mostra
     * il valore interno
     */
    protected boolean isUsaRendererElenco = false;

//    /**
//     * flag - se si vuole aggiungere l'elemento "vuoto" o "non specificato"
//     * alla lista valori
//     */
//    protected boolean isUsaNonSpecificato = false;

    /**
     * flag - se l'elemento "vuoto" o "non specificato" viene messo
     * all'inizio  della lista valori
     */
    protected boolean isNonSpecificatoIniziale = false;

//    /**
//     * flag - se si vuole aggiungere l'elemento "nuovo"
//     * alla lista valori
//     */
//    protected boolean isUsaNuovo = false;

    /**
     * flag - se l'elemento "nuovo" viene messo all'inizio
     * della lista valori
     */
    protected boolean isNuovoIniziale = false;

    /**
     * flag - se si vuole aggiungere l'elemento "separatore" tra gli
     * elementi speciali ed i valori normali della lista valori
     */
    protected boolean isUsaSeparatore = false;

    /**
     * flag - se si vuole che l'elemento "non specificato" venga mostrato
     * questo perche gli elenchi di radio bottoni lo usano SEMPRE
     */
    protected boolean isNascondeNonSpecificato = false;

    /**
     * elemento non specificato
     */
    private Elemento elementoNonSpecificato = null;

    /**
     * elemento nuovo
     */
    private Elemento elementoNuovo = null;

    /**
     * controlla l'esistenza effettiva di un separatore nella parte alta dei dati
     */
    private boolean isEsisteSeparatoreAlto = false;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDElenco() {
        /* rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDElenco(Campo unCampoParente) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente, TIPO_ARCHIVIO, TIPO_MEMORIA, TIPO_VIDEO);

        /* regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* assegna l'icona specifica per il tipo di campo */
        this.setIcona(ICONA_CAMPO_NUM);

        /* di default usa il renderer specifico nella lista */
        this.setUsaRendererElenco(true);

//        /* sostituisce l' editor di default */
//        DefaultCellEditor editor = new DefaultCellEditor(new JComboBox());
//        editor.setClickCountToStart(1);
//        this.setEditor(editor);

    } /* fine del metodo inizia */


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     */
    public void inizializza() {
        /* invoca il metodo sovrascritto della superclasse */
        super.inizializza();
    } /* fine del metodo */


    /**
     * Crea e posiziona il non specificato <br>
     * <p/>
     * Viene sempre creato: o sopra o sotto <br>
     */
    private void regolaNonSpecificato() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int pos;
        int codice;
        int larghezzaPopup;
        Elemento elemento;
        Elenco elenco = null;

        try {    // prova ad eseguire il codice

            /* Posizionamento sopra o sotto */
            if (this.isNonSpecificatoIniziale()) { // sopra
                pos = 0;
            } else { // sotto
                pos = this.getElenco().getListaValori().size();
            } /* fine del blocco if/else */

            /* recupera la larghezza del campo nella scheda
             * serve per selezionare la parola da mostrare (corta o lunga) */
            larghezzaPopup = this.getCampoParente().getCampoScheda()
                    .getLarghezzaComponenti();

            /* Crea e posiziona il vuoto */
            this.setElementoNonSpecificato(EFactory.creaVuoto(larghezzaPopup));
            codice = this.getElementoNonSpecificato().getCodice();
            elemento = this.getElementoNonSpecificato();
            continua = (elemento != null);

            if (continua) {
                elenco = this.getElenco();
                continua = (elenco != null);
            }// fine del blocco if

            if (continua) {
                elenco.addValoreCodice(elemento, codice, pos);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Crea e posiziona il nuovo <br>
     * <p/>
     * Viene sempre creato: o sopra o sotto <br>
     * Calcola quanti elementi ci sono sopra e si posiziona <br>
     * Calcola quanti elementi ci sono sotto e si posiziona <br>
     */
    private void regolaNuovo() {
        /** variabili e costanti locali di lavoro */
        int pos;
        int codice;

        try {    // prova ad eseguire il codice

            /** Posizionamento sopra o sotto */
            if (this.isNuovoIniziale()) { // sopra
                pos = 0;
                if ((this.isUsaNonSpecificato()) && (this.isNonSpecificatoIniziale())) {
                    pos++;
                } /* fine del blocco if */
            } else { // sotto
                pos = this.elenco.getListaValori().size();
                if ((this.isUsaNonSpecificato()) && (!this.isNonSpecificatoIniziale())) {
                    pos--;
                } /* fine del blocco if */
            } /* fine del blocco if/else */

            /** Crea e posiziona il nuovo */
            this.elementoNuovo = EFactory.creaNuovo();
            codice = this.elementoNuovo.getCodice();
            this.elenco.addValoreCodice(this.elementoNuovo, codice, pos);

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Crea e posiziona il separatore (nessuno, uno o due) <br>
     * <p/>
     * Viene creato solo se esiste almeno uno degli 'Elementi':
     * 'Vuoto' o 'Nuovo' <br>
     * Calcola quanti elementi ci sono sopra e si posiziona; non si
     * crea se sopra non ci sono elementi <br>
     * Calcola quanti elementi ci sono sotto e si posiziona; non si
     * crea se sotto non ci sono elementi <br>
     */
    private void regolaSeparatore() {
        /** variabili e costanti locali di lavoro */
        boolean continua;
        Elemento elementoSeparatore;
        int codice;
        int sopra;
        int sotto;
        ArrayList lista;

        try {    // prova ad eseguire il codice

            /* Primo controllo di esistenza di almeno un elemento */
            if ((this.isUsaNonSpecificato()) | (this.isUsaNuovo())) {

                /* separatore superiore */
                sopra = 0;
                if ((this.isUsaNonSpecificato()) && (this.isNonSpecificatoIniziale())) {
                    sopra++;
                } /* fine del blocco if */
                if ((this.isUsaNuovo()) && (this.isNuovoIniziale())) {
                    sopra++;
                } /* fine del blocco if */

                /* Serve proprio */
                if (sopra > 0) {
                    elementoSeparatore = EFactory.creaSeparatore();
                    codice = elementoSeparatore.getCodice();
                    this.elenco.addValoreCodice(elementoSeparatore, codice, sopra);
                    this.isEsisteSeparatoreAlto = true;
                } /* fine del blocco if */

                /* separatore inferiore */
                lista = this.elenco.getListaValori();
                continua = (lista != null);
                if (continua) {
                    sotto = lista.size();
                    if ((this.isUsaNonSpecificato()) && (!this.isNonSpecificatoIniziale())) {
                        sotto--;
                    } /* fine del blocco if */
                    if ((this.isUsaNuovo()) && (!this.isNuovoIniziale())) {
                        sotto--;
                    } /* fine del blocco if */

                    /* Serve proprio */
                    if (sotto < this.elenco.getListaValori().size()) {
                        elementoSeparatore = EFactory.creaSeparatore();
                        codice = elementoSeparatore.getCodice();
                        this.elenco.addValoreCodice(elementoSeparatore, codice, sotto);
                    } /* fine del blocco if */
                }// fine del blocco if

            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Verifica se il valore Video e' vuoto
     *
     * @return true se è vuoto
     */
    protected boolean isValoreVideoVuoto() {
        boolean isVuoto = false;
        Integer intero;
        int val;

        try {    // prova ad eseguire il codice
            val = (Integer)this.getVideo();
//            elenco = this.getElenco();
//
//            if (elenco != null) {
//
//                /* recupera il codice*/
//                posizione = elenco.getCodice(this.getVideo());
//
//                /* registra il codice in memoria */
//                this.setMemoria(new Integer(posizione));


            intero = (Integer)this.getValoreVideoVuoto();
            if (val == intero) {
                isVuoto = true;
            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return isVuoto;
    } /* fine del metodo */


    /**
     * Dopo che e' stata caricata la lista, controlla i flags ed aggiunge
     * gli elementi speciali <br>
     */
    protected void regolaElementiAggiuntivi() {

        try {    // prova ad eseguire il codice

            if (this.isUsaNonSpecificato()) {
                this.regolaNonSpecificato();
            } /* fine del blocco if */

            if (this.isUsaNuovo()) {
                this.regolaNuovo();
            } /* fine del blocco if */

            if (this.isUsaSeparatore()) {
                this.regolaSeparatore();
            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Crea un Elenco, con la Matrice doppia <br>
     * <p/>
     * crea una nuova istanza di MatriceDoppia <br>
     * <br/>
     * viene creata una nuova istanza di Elenco completa di codici e valori,
     * ma senza posizionamento o selezione <br>
     * <br/>
     * vengono regolati gli elementi aggiuntivi <br>
     */
    public void creaElenco() {
        /* variabili e costanti locali di lavoro */
        MatriceDoppia unaMatriceDoppia;

        try {    // prova ad eseguire il codice

            /* crea un'istanza di matrice dati */
            unaMatriceDoppia = new MatriceDoppia();

            /* riempie la lista valori della matrice */
            unaMatriceDoppia = this.regolaValoriMatrice(unaMatriceDoppia);

            /* regola definitivamente la matrice doppia
             * aggiunge la lista di posizioni
             * eventualmente inserisce dei valori di emergenza */
            this.regolaDefaultMatrice(unaMatriceDoppia);

            /* crea dalla matrice doppia
             * una nuova istanza di Elenco completa di codici e valori */
            this.elenco = ElencoFactory.creaSingolo(unaMatriceDoppia);

            /* controlla per aggiungere i valori speciali
             * "vuoto", "separatore" e "nuovo" */
            this.regolaElementiAggiuntivi();

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * riempie la lista valori della matrice.   <br>
     * sovrascritto nella sottoclasse specifica
     * µpara,
     */
    protected MatriceDoppia regolaValoriMatrice(MatriceDoppia unaMatriceDoppia) {
        return null;
    } /* fine del metodo */


    /**
     * Regola i valori di default della matrice.<br>
     * Se la lista valori della matrice è nulla o vuota inserisce dei valori
     * di emergenza <br>
     */
    protected void regolaDefaultMatrice(MatriceDoppia unaMatriceDoppia) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        ArrayList listaCodici = null;
        ArrayList listaValori = null;
        Object oggetto;

        try { // prova ad eseguire il codice
            continua = (unaMatriceDoppia != null);

            /* recupera la lista valori dalla matrice doppia */
            if (continua) {
                listaValori = unaMatriceDoppia.getListaValori();
            }// fine del blocco if

            /* se nulla, la creo */
            if (continua) {
                if (listaValori == null) {
                    listaValori = new ArrayList();
                }// fine del blocco if
            }// fine del blocco if

            /* se vuota, la riempie */
            if (continua) {
//                if (listaValori.size() == 0) {
//                    listaValori.add("Primo valore");
//                    listaValori.add("Secondo valore");
//                    listaValori.add("Terzo valore");
//                }// fine del blocco if
            }// fine del blocco if

            /* sostituisce tutti gli eventuali valori nulli */
            if (continua) {
                for (int k = 0; k < listaValori.size(); k++) {
                    oggetto = listaValori.get(k);
                    if (oggetto == null) {
                        oggetto = new String("Valore nullo");
                        listaValori.set(k, oggetto);
                    }// fine del blocco if
                } // fine del ciclo for
            }// fine del blocco if

            /* regola i valori della matrice */
            if (continua) {
                unaMatriceDoppia.setValori(listaValori);
            }// fine del blocco if

            /* recupera la lista codici dalla matrice doppia */
            if (continua) {
                listaCodici = unaMatriceDoppia.getListaCodici();
            }// fine del blocco if

            /* se nulla, la creo */
            if (continua) {
                if (listaCodici == null) {
                    listaCodici = new ArrayList();
                }// fine del blocco if
            }// fine del blocco if

            /* se vuota, la riempie */
            if (continua) {
                if (listaCodici.size() == 0) {
                    /* crea una lista di posizioni - una serie numerica progressiva */
                    listaCodici = Libreria.creaListaNumeri(listaValori.size());
                }// fine del blocco if
            }// fine del blocco if

            /* regola i codici della matrice */
            if (continua) {
                unaMatriceDoppia.setCodici(listaCodici);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * converte da memoria a video <br>
     * <p/>
     * (NON invoca il metodo sovrascritto della superclasse) <br>
     * <p/>
     * recupera l'attributo 'posizione' da Elenco,
     * in funzione del valore in memoria <br>
     */
    public void memoriaVideo() {
        /* variabili e costanti locali di lavoro */
        Elenco elenco;
        int posizione;
        Object memoria;
        Object valVideoVuoto;

        try {    // prova ad eseguire il codice

            /* recupera il valore di posizione da Elenco
             *  passandogli memoria e valore posizione di default */
            elenco = this.getElenco();
            if (elenco != null) {
                memoria = this.getMemoria();
                valVideoVuoto = this.getValoreVideoVuoto();
                elenco.regolaPosizione(memoria, valVideoVuoto);
                posizione = elenco.getPosizione(memoria);

                /** regola il video */
                this.setVideo(new Integer(posizione));
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * converte da video a memoria <br>
     * <p/>
     * (NON invoca il metodo sovrascritto della superclasse) <br>
     * <p/>
     * recupera dalla lista dei codici dell'oggetto elenco
     * il codice corrispondente alla posizione (valore video) <br>
     * registra il valore del codice in memoria <br>
     */
    public void videoMemoria() {
        /** variabili e costanti locali di lavoro */
        int codice;
        Elenco elenco;

        /** recupera la memoria e registra il video */
        try {    // prova ad eseguire il codice

            /** Se il valore Video e' vuoto,
             *  assegna a Memoria il proprio valore vuoto,
             *  altrimenti trasporta il valore */
            if (this.isValoreVideoVuoto()) {
                this.setMemoria(this.getValoreMemoriaVuoto());
            } else {

                elenco = this.getElenco();

                if (elenco != null) {

                    /* recupera il codice*/
                    codice = elenco.getCodice(this.getVideo());

                    /* registra il codice in memoria */
                    this.setMemoria(new Integer(codice));

                }// fine del blocco if


            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo videoMemoria */


    /**
     * Controllo di validità del valore video.
     * <p/>
     * Controlla che il valore sia compatibile col tipo di dati del Campo <br>
     *
     * @param valoreVideo oggetto da controllare
     *
     * @return true se il valore è compatibile
     */
    public boolean isVideoValido(Object valoreVideo) {
        /* variabili e costanti locali di lavoro */
        boolean valido = false;

        try { // prova ad eseguire il codice

            /* controlla il tipo */
            if (valoreVideo instanceof Integer) {
                valido = true;
            } /* fine del blocco if */

        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Ritorna true se il campo e' numerico.
     *
     * @return true se è campo numerico
     */
    public boolean isNumero() {
        return true;
    }


    /**
     * Restituisce il numero di elementi validi.
     * <p/>
     * Sono esclusi i separatori, non specificato e nuovo <br>
     *
     * @return numero di elementi validi
     */
    public int getElementiValidi() {
        /* variabili e costanti locali di lavoro */
        int numero = 0;
        boolean continua;
        Elenco elenco;
        ArrayList lista = null;

        try { // prova ad eseguire il codice
            elenco = this.getElenco();
            continua = (elenco != null);

            if (continua) {
                lista = elenco.getListaValori();
                continua = (lista != null);
            }// fine del blocco if

            if (continua) {
                /* traverso tutta la collezione */
                for (Object oggetto : lista) {
                    if (!(oggetto instanceof Elemento)) {
                        numero++;
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return numero;
    }


    /**
     * Controlla se esiste uno ed un solo elemento valido.
     * <p/>
     *
     * @return vero se esiste un elemento valido
     *         falso se ne esistono zero o più di uno
     */
    public boolean isUnicoValido() {
        /* variabili e costanti locali di lavoro */
        boolean valido = false;

        try { // prova ad eseguire il codice
            valido = (this.getElementiValidi() == 1);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }

//    /**
//     * flag - se si vuole aggiungere l'elemento "non specificato"
//     * alla lista valori
//     *
//     * @param isUsaNonSpecificato
//     */
//    public void setUsaNonSpecificato(boolean isUsaNonSpecificato) {
//        this.isUsaNonSpecificato = isUsaNonSpecificato;
//    } /* fine del metodo setter */


    /**
     * flag - se l'elemento "vuoto" o "non specificato" viene messo
     * all'inizio  della lista valori
     *
     * @param isNonSpecificatoIniziale
     */
    public void setNonSpecificatoIniziale(boolean isNonSpecificatoIniziale) {
        this.isNonSpecificatoIniziale = isNonSpecificatoIniziale;
    } /* fine del metodo setter */


    /**
     * flag - se si vuole che l'elemento "non specificato" non venga mostrato
     * questo perche gli elenchi di radio bottoni lo usano SEMPRE
     */
    public void setNascondeNonSpecificato(boolean isNascondeNonSpecificato) {
        this.isNascondeNonSpecificato = isNascondeNonSpecificato;
    } /* fine del metodo setter */

//    /**
//     * flag - se si vuole aggiungere l'elemento "nuovo"
//     * alla lista valori
//     */
//    public void setUsaNuovo(boolean isUsaNuovo) {
//        this.isUsaNuovo = isUsaNuovo;
//    } /* fine del metodo setter */


    /**
     * Determina se il comando "Nuovo record" viene posizionato prima o dopo
     * la lista dei valori.
     * <p/>
     *
     * @param flag true per posizionare prima, false per dopo
     */
    public void setNuovoIniziale(boolean flag) {
        this.isNuovoIniziale = flag;
    } /* fine del metodo setter */


    /**
     * Se si vuole aggiungere un separatore tra gli
     * elementi speciali ed i valori normali della lista valori
     * <p>
     * @param flag di uso del separatore
     */
    public void setUsaSeparatore(boolean flag) {
        this.isUsaSeparatore = flag;
    } /* fine del metodo setter */


    /**
     * flag - se si usa l'elemento "non specificato"
     */
    public boolean isUsaNonSpecificato() {
        /* variabili e costanti locali di lavoro */
        boolean usa = false;
        Campo campoParente;
        CampoVideo campoVideo;

        try { // prova ad eseguire il codice
            campoParente = this.getCampoParente();
            campoVideo = campoParente.getCampoVideo();
            if (campoVideo != null) {
                usa = campoVideo.isUsaNonSpecificato();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return usa;
    } /* fine del metodo getter */


    /**
     * flag - se l'elemento "vuoto" o "non specificato" viene messo
     * all'inizio  della lista valori
     */
    public boolean isNonSpecificatoIniziale() {
        return this.isNonSpecificatoIniziale;
    } /* fine del metodo getter */


    /**
     * flag - se si vuole che l'elemento "non specificato" non venga mostrato
     * questo perche gli elenchi di radio bottoni lo usano SEMPRE
     */
    public boolean isNascondeNonSpecificato() {
        return this.isNascondeNonSpecificato;
    } /* fine del metodo getter */


    /**
     * flag - se si usa l'elemento "nuovo"
     */
    private boolean isUsaNuovo() {
        /* variabili e costanti locali di lavoro */
        boolean usa = false;
        Campo campoParente;
        CampoVideo campoVideo;

        try { // prova ad eseguire il codice
            campoParente = this.getCampoParente();
            campoVideo = campoParente.getCampoVideo();
            if (campoVideo != null) {
                usa = campoVideo.isUsaNuovo();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return usa;

    } /* fine del metodo getter */


    /**
     * flag - se si vuole aggiungere l'elemento "vuoto" o "non specificato"
     * alla lista valori
     */
    public boolean isNuovoIniziale() {
        return this.isNuovoIniziale;
    } /* fine del metodo getter */


    /**
     * flag - se si vuole aggiungere l'elemento "separatore" tra gli
     * elementi speciali ed i valori normali della lista valori
     */
    public boolean isUsaSeparatore() {
        return this.isUsaSeparatore;
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public Elenco getElenco() {
        return this.elenco;
    } /* fine del metodo getter */


    /**
     * Recupera la lista dei valori (oggetti di tipo Object).
     *
     * @return arrayList di valori
     */
    public ArrayList getListaValori() {
        if (this.getElenco() != null) {
            return this.getElenco().getListaValori();
        } else {
            return null;
        }// fine del blocco if-else
    }


    /**
     * Recupera il valore di elenco correntemente selezionato.
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @return il valore corrispondente
     */
    public Object getValoreElenco() {
        return null;
    } /* fine del metodo getter */


    /**
     * Assegna il valore memoria del campo in base a un valore di elenco.
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @param valore da cercare nell'elenco
     */
    public void setValoreDaElenco(Object valore) {
    } /* fine del metodo getter */


    /**
     * Recupera il valore per una data posizione nella lista valori.
     * <p/>
     *
     * @param posizione richiesta
     *
     * @return il valore corrispondente
     */
    public Object getValoreElenco(int posizione) {
        /* variabili e costanti locali di lavoro */
        int posizioneInterna = 0;
        Object valore = null;

        try { // prova ad eseguire il codice
            posizioneInterna = this.getElenco().getPosizione(posizione);
            if (this.getElenco() != null) {
                valore = this.getElenco().getValore(posizioneInterna);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;

    } /* fine del metodo getter */


    /**
     * array dei valori (oggetti di tipo Object)
     */
    public Object[] getArrayValori() {
        if (this.getListaValori() != null) {
            return this.getListaValori().toArray();
        } else {
            return null;
        }// fine del blocco if-else
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public Elemento getElementoNuovo() {
        return this.elementoNuovo;
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public Elemento getElementoNonSpecificato() {
        return this.elementoNonSpecificato;
    } /* fine del metodo getter */


    /**
     * Regola il valore dell'elemento Non Specificato
     */
    private void setElementoNonSpecificato(Elemento e) {
        this.elementoNonSpecificato = e;
    } /* fine del metodo getter */


    public boolean isElencoInterno() {
        return isElencoInterno;
    }


    public void setElencoInterno(boolean elencoInterno) {
        isElencoInterno = elencoInterno;
    }


    public boolean isUsaRendererElenco() {
        return isUsaRendererElenco;
    }


    public void setUsaRendererElenco(boolean usaRendererValoreInterno) {
        isUsaRendererElenco = usaRendererValoreInterno;
    }
}// fine della classe

//-----------------------------------------------------------------------------

