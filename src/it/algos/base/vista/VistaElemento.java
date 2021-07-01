/**
 * Title:     VistaElemento
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      29-apr-2004
 */
package it.algos.base.vista;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.db.CampoDB;
import it.algos.base.campo.lista.CampoLista;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;

import java.util.ArrayList;

/**
 * Modello dati per un elemento della lista oggetti di una Vista.
 * </p>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 29-apr-2004 ore 15.04.40
 */
public final class VistaElemento implements Cloneable {

    /**
     * Riferimento all'oggetto contenuto nell'Elemento
     * puo' essere:
     * - un Nome di campo
     * - un Campo
     * - un nome di Vista
     * - una Vista
     */
    private Object contenuto = null;

    /**
     * Riferimento al campo contenuto originariamente nell'Elemento
     * prima della espansione.
     * Viene propagato in tutti gli elementi espansi.
     */
    private Campo campoOriginale = null;


    /**
     * Riferimento al modulo proprietario dell'oggetto contenuto nell'Elemento
     */
    private Modulo moduloContenuto = null;

    /**
     * Riferimento alla Vista di appartenenza dell'Elemento
     */
    private Vista vistaProprietaria = null;

    /**
     * Codice del tipo di Elemento
     *
     * @see Vista
     */
    private int tipo = 0;

    /**
     * Flag - se questo elemento e' visibile nella Lista
     */
    private boolean isVisibile = false;

    /**
     * flag - controlla l'espansione di un campo linkato
     * se e' true rende visibile la parte originale del campo
     * (significativo solo per i campi linkati)
     */
    private boolean isVisibileOriginale = false;

    /**
     * flag - controlla l'espansione di un campo linkato
     * se e' true rende visibile la parte espansa del campo
     * (significativo solo per i campi linkati)
     */
    private boolean isVisibileEspansione = false;

    /**
     * larghezza colonna per tutti i campi derivanti
     * dall'espansione dell'elemento
     */
    private int larghezzaColonna;

    /**
     * tipo di allineamento nella lista per tutti i campi derivanti
     * dall'espansione dell'elemento
     */
    private int allineamentoLista = -1;

    /**
     * titolo colonna per tutti i campi derivanti
     * dall'espansione dell'elemento
     */
    private String titoloColonna;


    /**
     * Flag - se questo elemento e' ridimensionabile nella Lista
     */
    private boolean isRidimensionabile = false;

    /**
     * Flag - se questo elemento e' completamente espanso
     */
    private boolean isEspanso = false;


    /**
     * Costruttore.<br>
     *
     * @param vistaProprietaria la Vista proprietaria di questo Elemento
     * @param contenuto         l'oggetto contenuto nell'Elemento
     *                          (puo' essere Campo, Vista, Stringa (nome di campo o vista))
     * @param tipo              il codice del tipo di elemento
     *
     * @see Vista
     */
    public VistaElemento(Vista vistaProprietaria, Object contenuto, int tipo) {
        /* rimanda al costruttore completo */
        this(vistaProprietaria, contenuto, tipo, null);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo.<br>
     *
     * @param vistaProprietaria la Vista proprietaria di questo Elemento
     * @param contenuto         l'oggetto contenuto nell'Elemento
     *                          (puo' essere Campo, Vista, Stringa (nome di campo o vista))
     * @param tipo              il codice del tipo di elemento
     * @param modulo            il modulo proprietario del contenuto
     *
     * @see Vista
     */
    public VistaElemento(Vista vistaProprietaria, Object contenuto, int tipo, Modulo modulo) {

        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice

            /* regolazione delle variabili di istanza con i parametri */
            this.setVistaProprietaria(vistaProprietaria);
            this.setContenuto(contenuto);
            this.setTipo(tipo);
            this.setModuloContenuto(modulo);

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

        try {    // prova ad eseguire il codice

            /* regolazione delle caratteristiche di default */

            /* un nuovo oggetto e' inizialmente visibile */
            this.setVisibile(true);
            /* parte originale dell'espansione invisibile */
            this.setVisibileOriginale(false);
            /* parte espansa visibile */
            this.setVisibileEspansione(true);
            /* un nuovo oggetto e' inizialmente ridimensionabile */
            this.setRidimensionabile(true);


        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Espande questo elemento di un livello. <br>
     * - Sostituisce il nome con il campo del Modulo della vista proprietaria
     * - Sostituisce la vista con gli elementi corrispondenti
     * - Sostituisce il campi linkato con la Vista corrispondente
     *
     * @return una lista di oggetti VistaElemento
     *         corrispondente a questo Elemento espanso
     *         (lista vuota se non ha potuto espandere l'elemento)
     */
    public ArrayList<VistaElemento> espandi() {
        /* variabili e costanti locali di lavoro */
        ArrayList<VistaElemento> listaElementi = null;
        ArrayList<VistaElemento> elementiEspansi;
        int tipoContenuto;
        VistaElemento unElemento;
        boolean originaleVisibile;

        try {    // prova ad eseguire il codice

            /* crea una nuova lista vuota per gli elementi espansi */
            listaElementi = new ArrayList<VistaElemento>();

            /* recupera il tipo di contenuto */
            tipoContenuto = this.getTipo();

            /* recupera la visibilita' dell'elemento
             * (prima della espansione) */
            originaleVisibile = this.isVisibile();

            /* espande l'elemento specifico */
            switch (tipoContenuto) {
                case Vista.NOME_CAMPO:
                    unElemento = this.espandiNomeCampo();
                    if (unElemento != null) {
                        listaElementi.add(unElemento);
                    }// fine del blocco if
                    break;
                case Vista.CAMPO:

                    /* se è un campo, e non ha ancora un riferimento
                     * al campo originale, assegna il riferimento
                     * al campo originale a questo elemento */
                    if (this.getCampoOriginale() == null) {
                        this.setCampoOriginale((Campo)this.getContenuto());
                    }// fine del blocco if

                    elementiEspansi = this.espandiCampo();
                    if (elementiEspansi != null) {
                        listaElementi.addAll(elementiEspansi);
                    }// fine del blocco if
                    break;
                case Vista.NOME_VISTA:
                    unElemento = this.espandiNomeVista();
                    if (unElemento != null) {
                        listaElementi.add(unElemento);
                    }// fine del blocco if
                    break;
                case Vista.VISTA:
                    elementiEspansi = this.espandiVista();
                    if (elementiEspansi != null) {
                        listaElementi.addAll(elementiEspansi);
                    }// fine del blocco if
                    break;
                default: // caso non supportato
                    throw new Exception("Tipo contenuto non supportato.");
            } // fine del blocco switch

            /* propaga il campo originale di questo elemento
             * su tutti gli elementi derivanti dalla espansione */
            for (VistaElemento elem : listaElementi) {
                elem.setCampoOriginale(this.getCampoOriginale());
            }

            /*
             * - Se l'elemento originale non era visibile, anche gli elementi
             * risultanti dall'espansione vengono regolati come invisibili
             * indipendentemente dalle regolazioni effettuate dagli
             * espansori specifici.
             * - Se l'elemento originale era visibile, vengono mantenute le
             * regolazioni di visibilita' effettuate dagli espansori specifici.
             */
            if (!originaleVisibile) {
                for (VistaElemento elem : listaElementi) {
                    elem.setVisibile(false);
                }
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaElementi;
    } // fine del metodo


    /**
     * Espande un nome di campo in un campo.<br>
     *
     * @return un elemento contenente l'oggetto campo corrispondente
     *         (null se non trovato)
     */
    private VistaElemento espandiNomeCampo() {
        /* variabili e costanti locali di lavoro */
        VistaElemento unElemento = null;
        String unNomeCampo;
        Campo unCampo;
        Modulo unModulo;
        Modello unModello;
        String errore;

        try {    // prova ad eseguire il codice
            /* recupera il nome del campo */
            unNomeCampo = (String)this.getContenuto();
            /* recupera il modulo del contenuto */
            unModulo = getModuloContenuto();
            /* recupera il modello */
            unModello = unModulo.getModello();

            if (unModello.isEsisteCampo(unNomeCampo)) {
                /* recupera il campo */
                unCampo = unModello.getCampo(unNomeCampo);
                /* sostituisce il contenuto di questo elemento */
                this.setContenuto(unCampo, Vista.CAMPO);
                /* ritorna questo elemento */
                unElemento = this;

            } else {
                errore = "Non trovato il campo " + unNomeCampo;
                errore += " nel modulo " + unModello.getModulo().getNomeChiave();
                throw new Exception(errore);
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unElemento;
    } // fine del metodo


    /**
     * Espande un campo in una lista di elementi.<br>
     * - Se il campo e' linkato, ritorna un array di Elementi contenente:
     * - un elemento contenente il campo originale
     * - un elemento contenente la Vista o il Campo linkato
     * - Se il campo non e' linkato, ritorna un array di un solo elemento
     * contenente il campo stesso
     *
     * @return un nuovo elemento contenente il campo o la Vista corrispondente
     *         (null se non riuscito a espandere il campo)
     */
    private ArrayList<VistaElemento> espandiCampo() {
        /* variabili e costanti locali di lavoro */
        ArrayList<VistaElemento> listaElementi = null;
        Campo unCampo;
        CampoDB unCampoDB;

        try {    // prova ad eseguire il codice

            /* recupera il campo */
            unCampo = (Campo)this.getContenuto();
            unCampoDB = unCampo.getCampoDB();

            /*
             * Se il campo e' linkato invoca il metodo delegato
             * Se il campo non e' linkato, ritorna un elemento
             * contenente il campo stesso
             */
            if (unCampoDB.isLinkato()) {  // campo linkato
                listaElementi = this.espandiCampoLinkato();
            } else {  // campo non linkato

                /* se specificato, regola la larghezza colonna del campo */
                if (this.getLarghezzaColonna() != 0) {
                    unCampo.setLarLista(this.getLarghezzaColonna());
                }// fine del blocco if

                /* se specificato, regola l'allineamento lista del campo */
                if (this.getAllineamentoLista() >= 0) {
                    unCampo.setAllineamentoLista(this.getAllineamentoLista());
                }// fine del blocco if

                /* se specificato, regola il titolo colonna del campo */
                if (Lib.Testo.isValida(this.getTitoloColonna())) {
                    unCampo.setTitoloColonna(this.getTitoloColonna());
                }// fine del blocco if

                /* crea una lista vuota */
                listaElementi = new ArrayList<VistaElemento>();
                listaElementi.add(this);
                this.setEspanso(true);  // espansione terminata
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaElementi;
    } // fine del metodo


    /**
     * Espande un campo linkato in un array di due elementi:
     * - un elemento con il campo stesso
     * - un elemento con il campo o la vista associata (se esistenti)
     *
     * @return una lista di uno o due Elementi
     *         (null se non riuscito a espandere il campo)
     */
    private ArrayList<VistaElemento> espandiCampoLinkato() {
        /* variabili e costanti locali di lavoro */
        ArrayList<VistaElemento> listaElementi = null;
        VistaElemento elementoEspansione = null;
        Campo unCampo;
        CampoDB unCampoDB;
        String nomeVistaLink;
        String nomeColonnaLinkata;


        try {    // prova ad eseguire il codice

            /* recupera alcune informazioni dal campo */
            unCampo = (Campo)this.getContenuto();
            unCampoDB = unCampo.getCampoDB();
            nomeVistaLink = unCampoDB.getNomeVistaLink();
            nomeColonnaLinkata = unCampoDB.getNomeColonnaListaLinkata();

            /* crea la lista elementi da ritornare */
            listaElementi = new ArrayList<VistaElemento>();

            /*
             * Recupera il nome dell'elemento associato al campo.
             * L'elemento conterra':
             * - Se ha una vista linkata, il nome della la vista linkata
             * - Se ha una colonna linkata, il nome del campo linkato
             */
            if (Lib.Testo.isValida(nomeVistaLink)) {  // ha Vista
                elementoEspansione = this.elementoDaNomeVista();
            } else {    // non ha Vista
                /* verifica se ha o meno una colonna linkata */
                if (Lib.Testo.isValida(nomeColonnaLinkata)) { // ha colonna
                    elementoEspansione = this.elementoDaNomeColonna();
                }// fine del blocco if
            }// fine del blocco if-else

            /* riempie la lista elementi da ritornare */
            if (elementoEspansione != null) {

                /* aggiunge se stesso all'elenco da ritornare */
                listaElementi.add(this);
                /* regola la visibilita' della parte originale */
                this.setVisibile(this.isVisibileOriginale());
                /* aggiunge la parte espansa all'elenco da ritornare */
                listaElementi.add(elementoEspansione);
                /* regola la visibilita' della parte espansa */
                elementoEspansione.setVisibile(this.isVisibileEspansione());

            } else {   // non esiste espansione

                /* aggiunge se stesso all'elenco da ritornare */
                listaElementi.add(this);

            }// fine del blocco if-else

            /* contrassegna l'elemento originale come espanso */
            this.setEspanso(true);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaElementi;
    } // fine del metodo


    /**
     * Ritorna un Elemento contenente il nome
     * della Vista linkata del campo contenuto.<br>
     *
     * @return un nuovo elemento contenente il nome della vista
     *         (null se non riuscito)
     */
    private VistaElemento elementoDaNomeVista() {
        /* variabili e costanti locali di lavoro */
        VistaElemento unElemento = null;
        Campo unCampo;
        CampoDB unCampoDB;
        String nomeVistaLink;
        String unNomeModuloLink;
        Modulo unModuloLink = null;
        boolean continua = true;

        try {    // prova ad eseguire il codice

            /* recupera alcune informazioni dal campo */
            unCampo = (Campo)this.getContenuto();
            unCampoDB = unCampo.getCampoDB();

            /* recupera il nome della vista linkata */
            nomeVistaLink = unCampoDB.getNomeVistaLink();

            /* recupera il nome del modulo linkato */
            unNomeModuloLink = unCampoDB.getNomeModuloLinkato();

            /* verifica se il modulo linkato esiste */
            if (!Progetto.isEsisteModulo(unNomeModuloLink)) {
                continua = false;
            }// fine del blocco if

            /* recupera il modulo linkato */
            if (continua) {
                unModuloLink = Progetto.getModulo(unNomeModuloLink);
            }// fine del blocco if

            /* clona l'elemento e assegna il nome della vista */
            if (continua) {

                unElemento = this.clonaElemento();
                unElemento.setContenuto(nomeVistaLink);
                unElemento.setTipo(Vista.NOME_VISTA);
                unElemento.setModuloContenuto(unModuloLink);
                unElemento.setEspanso(false);

            }// fine del blocco if

//            /* crea un nuovo elemento contenente il nome vista */
//            if (continua) {
//                unElemento
//                        = new VistaElemento(
//                                this.getVistaProprietaria(),
//                                nomeVistaLink,
//                                Vista.NOME_VISTA);
//
//                /* regola il modulo al quale la vista appartiene */
//                unElemento.setModuloContenuto(unModuloLink);
//
//            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unElemento;
    } // fine del metodo


    /**
     * Ritorna un Elemento contenente il nome della colonna
     * linkata del campo contenuto. <br>
     *
     * @return un nuovo elemento contenente il nome della colonna
     *         (null se non riuscito)
     */
    private VistaElemento elementoDaNomeColonna() {
        /* variabili e costanti locali di lavoro */
        VistaElemento unElemento = null;
        Campo unCampo;
        CampoDB unCampoDB;
        String nomeColonnaLinkata;
        String unNomeModuloLink;
        Modulo unModuloLink = null;
        boolean continua = true;
        String titoloColonna;

        try {    // prova ad eseguire il codice

            /* recupera alcune informazioni dal campo */
            unCampo = (Campo)this.getContenuto();
            unCampoDB = unCampo.getCampoDB();

            /* recupera il nome della colonna linkata */
            nomeColonnaLinkata = unCampoDB.getNomeColonnaListaLinkata();

            /* recupera il nome del modulo linkato */
            unNomeModuloLink = unCampoDB.getNomeModuloLinkato();

            /* verifica se il modulo linkato esiste */
            if (!Progetto.isEsisteModulo(unNomeModuloLink)) {
                continua = false;
            }// fine del blocco if

            /* recupera il modulo linkato */
            if (continua) {
                unModuloLink = Progetto.getModulo(unNomeModuloLink);
            }// fine del blocco if

            /* clona l'elemento e assegna il nome del campo e il
             * titolo della colonna */
            if (continua) {

                unElemento = this.clonaElemento();
                unElemento.setContenuto(nomeColonnaLinkata);
                unElemento.setTipo(Vista.NOME_CAMPO);
                unElemento.setModuloContenuto(unModuloLink);

                /* se il titolo colonna non è stato già regolato, usa
                 * il nome pubblico del modulo linkato */
                if (!Lib.Testo.isValida(this.getTitoloColonna())) {
                    titoloColonna = unModuloLink.getNomePubblico();
                    titoloColonna = Lib.Testo.primaMinuscola(titoloColonna);
                    unElemento.setTitoloColonna(titoloColonna);
                }// fine del blocco if

                unElemento.setEspanso(false);

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unElemento;
    } // fine del metodo


    /**
     * Clona il contenuto di questo Elemento. <br>
     * (per ora funziona solo per contenuti di tipo Campo)
     */
    public void clonaContenuto() {
        /* variabili e costanti locali di lavoro */
        Campo unCampoOriginale;
        Campo unCampoClonato;

        try {    // prova ad eseguire il codice

            /* verifica se e' di tipo Campo */
            if (this.getTipo() == Vista.CAMPO) {

                /* recupera il campo originale */
                unCampoOriginale = (Campo)this.getContenuto();

                /* esegue il clone */
                unCampoClonato = unCampoOriginale.clonaCampo();
                unCampoClonato.inizializza();

                /*
                 * assegna il clone direttamente all'elemento
                 * non uso setContenuto perche' sono sicuro
                 * che e' dello stesso tipo e non voglio modificare
                 * le altre caratteristiche dell'Elemento (visibilita', ecc...)
                 */
                this.contenuto = unCampoClonato;

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    } // fine del metodo


    /**
     * Espande un nome di vista in una vista.<br>
     *
     * @return un elemento contenente l'oggetto vista corrispondente
     *         (null se non trovato)
     */
    private VistaElemento espandiNomeVista() {
        /* variabili e costanti locali di lavoro */
        VistaElemento unElemento = null;
        String unNomeVista;
        Vista unaVista;
        Modulo unModulo;
        Modello unModello;
        String errore;

        try {    // prova ad eseguire il codice
            /* recupera il nome della vista */
            unNomeVista = (String)this.getContenuto();
            /* recupera il modulo del contenuto */
            unModulo = getModuloContenuto();
            /* recupera il modello */
            unModello = unModulo.getModello();

            if (unModello.isEsisteVista(unNomeVista)) {
                /* recupera la vista */
                unaVista = unModello.getVista(unNomeVista);
                /* sostituisce il contenuto di questo elemento */
                this.setContenuto(unaVista, Vista.VISTA);
                /* ritorna questo elemento */
                unElemento = this;

            } else {
                errore = "Non trovata la vista " + unNomeVista;
                errore += " nel modulo " + unModello.getModulo().getNomeChiave();
                throw new Exception(errore);
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unElemento;
    } // fine del metodo


    /**
     * Espande una Vista.<br>
     *
     * @return la lista degli elementi contenuti nella Vista
     *         (null se non riuscito)
     */
    private ArrayList<VistaElemento> espandiVista() {
        /* variabili e costanti locali di lavoro */
        ArrayList<VistaElemento> listaElementi = null;
        ArrayList<VistaElemento> listaElementiOriginale;
        Vista unaVista;
        String errore;
        VistaElemento unElementoNuovo;

        try {    // prova ad eseguire il codice
            /* recupera la Vista */
            unaVista = (Vista)this.getContenuto();
            /* controllo di congruita' */
            if (unaVista != null) {

                /* recupera gli Elementi della Vista associata */
                listaElementiOriginale = unaVista.getElementi();

                /* crea una nuova lista elementi vuota */
                listaElementi = new ArrayList<VistaElemento>();

                /* spazzola la lista originale, clona gli elementi,
                 * e li aggiunge alla lista da ritornare */
                for (VistaElemento elem : listaElementiOriginale) {
                    unElementoNuovo = elem.clonaElemento();
                    listaElementi.add(unElementoNuovo);
                }

            } else {
                errore = "La vista e' nulla";
                throw new Exception(errore);
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaElementi;
    } // fine del metodo


    /**
     * Regola il riferimento al modulo per questo elemento.<br>
     * Se mancante, Assegna all'elemento il riferimento al modulo
     * del proprio contenuto
     * Nel caso di elementi di tipo nome che non hanno un modulo
     * gia' assegnato, assegna il modulo della Vista proprietaria.
     */
    public void regolaModulo() {
        /* variabili e costanti locali di lavoro */
        Object contenuto;
        int tipo;
        Modulo modulo = null;
        Campo unCampo;
        Vista unaVista;

        try {    // prova ad eseguire il codice

            if (this.getModuloContenuto() == null) {
                contenuto = this.getContenuto();
                tipo = this.getTipo();

                if (tipo == Vista.NOME_CAMPO) {
                    modulo = this.getVistaProprietaria().getModulo();
                }// fine del blocco if

                if (tipo == Vista.CAMPO) {
                    unCampo = (Campo)contenuto;
                    modulo = unCampo.getModulo();
                }// fine del blocco if

                if (tipo == Vista.NOME_VISTA) {
                    modulo = this.getVistaProprietaria().getModulo();
                }// fine del blocco if

                if (tipo == Vista.VISTA) {
                    unaVista = (Vista)contenuto;
                    modulo = unaVista.getModulo();
                }// fine del blocco if

                this.setModuloContenuto(modulo);

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    } // fine del metodo


    /**
     * Regola le caratteristiche di questo Elemento in base al Campo.<br>
     * Le caratteristiche dell'Elemento vengono regolate in base
     * alle caratteristiche del Campo contenuto.
     * Applicabile solo ad elementi che contengono un nome Campo o oggetto Campo.
     */
    public void regolaDaCampo() {

        try {    // prova ad eseguire il codice

            /* elemento di tipo Campo */
            if (this.getTipo() == Vista.CAMPO) {
                this.regolaDaOggettoCampo();
            }// fine del blocco if

            /* elemento di tipo Nome Campo */
            if (this.getTipo() == Vista.NOME_CAMPO) {
                this.regolaDaNomeCampo();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }// fine del metodo


    /**
     * Regola le caratteristiche di un Elemento contenente un nome di campo. <br>
     * Cerca il campo corrispondente, se trovato regola le caratteristiche
     * dell'elemento in base a quelle del campo.
     * Applicabile solo a elementi contenenti nome di campo
     */
    private void regolaDaNomeCampo() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Object unContenuto = null;
        String unNomeCampo = "";
        Modulo unModulo = null;
        Campo unCampo = null;

        try {    // prova ad eseguire il codice

            /* verifica che l'Elemento sia di tipo Nome Campo */
            continua = (this.getTipo() != Vista.NOME_CAMPO);

            /* recupera il contenuto */
            if (continua) {
                unContenuto = this.getContenuto();
                continua = (unContenuto != null);
            }// fine del blocco if

            /* recupera il nome del Campo */
            if (continua) {
                unNomeCampo = (String)unContenuto;
                continua = Lib.Testo.isValida(unNomeCampo);
            }// fine del blocco if

            /**
             * recupera il modulo
             * usa il modulo del campo
             * se manca, usa il modulo della Vista
             */
            if (continua) {
                unModulo = this.getModuloContenuto();
                if (unModulo == null) {
                    unModulo = this.getVistaProprietaria().getModulo();
                }// fine del blocco if
                continua = (unModulo != null);
            }// fine del blocco if

            /* controlla se il campo esiste nel modulo */
            if (continua) {
                continua = unModulo.isEsisteCampo(unNomeCampo);
            }// fine del blocco if

            /* recupera il campo dal modulo */
            if (continua) {
                unCampo = unModulo.getCampo(unNomeCampo);
                continua = (unCampo != null);
            }// fine del blocco if

            /* regola l'elemento in base al campo */
            if (continua) {
                this.regolaDaCampo(unCampo);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    } // fine del metodo


    /**
     * Regola le caratteristiche di un Elemento contenente un oggetto campo. <br>
     * Regola le caratteristiche dell'elemento in base a quelle del campo.
     * Applicabile solo a elementi contenenti oggetti campo
     */
    private void regolaDaOggettoCampo() {
        /* variabili e costanti locali di lavoro */
        Object unContenuto;
        Campo unCampo;

        try {    // prova ad eseguire il codice

            /* verifica che l'Elemento sia di tipo Campo */
            if (this.getTipo() != Vista.CAMPO) {
                throw new Exception("L'Elemento non e' di tipo Campo");
            }// fine del blocco if

            /* recupera il contenuto */
            unContenuto = this.getContenuto();

            /* verifica che il contenuto non sia nullo */
            if (unContenuto == null) {
                throw new Exception("Il contenuto dell'Elemento e' nullo");
            }// fine del blocco if

            /* recupera l'oggetto Campo */
            unCampo = (Campo)unContenuto;

            /* regola il riferimento al campo originale */
            this.setCampoOriginale(unCampo);

            /* regola l'elemento in base al campo */
            this.regolaDaCampo(unCampo);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    } // fine del metodo


    /**
     * Regola le caratteristiche di questo Elemento in base a un Campo.
     * <p/>
     * Le caratteristiche dell'Elemento vengono regolate in base
     * alle caratteristiche del Campo passato come parametro.
     *
     * @param unCampo in base al quale regolare le caratteristiche
     */
    private void regolaDaCampo(Campo unCampo) {
        /* variabili e costanti locali di lavoro */
        boolean visibileOriginale;
        boolean visibileEspansione;
        boolean ridimensionabile;
        CampoLista cl;

        try {    // prova ad eseguire il codice

            cl = unCampo.getCampoLista();

            /* regola le caratteristiche di visibilita' */
            visibileOriginale = cl.isVisibileOriginale();
            this.setVisibileOriginale(visibileOriginale);
            visibileEspansione = cl.isVisibileEspansione();
            this.setVisibileEspansione(visibileEspansione);

            /* regola le caratteristiche di ridimensionabilita' */
            ridimensionabile = cl.isRidimensionabile();
            this.setRidimensionabile(ridimensionabile);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }// fine del metodo


    /**
     * Recupera il campo dall'elemento.
     * <p/>
     *
     * @return il campo contenuto nell'elemento
     */
    public Campo getCampo() {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        Object oggetto;

        try {    // prova ad eseguire il codice
            oggetto = this.getContenuto();
            if (oggetto != null) {
                if (oggetto instanceof Campo) {
                    campo = (Campo)oggetto;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Ritorna una copia profonda dell'oggetto (deep copy) col casting
     * Per fare una copia completa di questo oggetto occorre:
     * Prima copiare l'oggetto nel suo insieme, richiamando il metodo
     * sovrascritto che copia e regola tutte le variabili dell'oggetto con
     * gli stessi valori delle variabili originarie
     * Secondo copiare tutte le variabili che sono puntatori ad altri
     * oggetti, per evitare che nella copia ci sia il puntatore all'oggetto
     * originale (in genere tutti gli oggetti che vengono creati nella
     * classe col comando new)
     * Terzo in ogni sottoclasse occorre fare le copie dei puntatori
     * esistenti nelle sottoclassi stesse
     * <p/>
     *
     * @return l'elemento clonato
     */
    public VistaElemento clonaElemento() {
        try {    // prova ad eseguire il codice
            /* variabili e costanti locali di lavoro */
            VistaElemento unElemento;

            /** invoca il metodo sovrascritto della superclasse Object */
            unElemento = (VistaElemento)super.clone();

            /** valore di ritorno */
            return unElemento;

        } catch (CloneNotSupportedException unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
            throw new InternalError();
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    public Object getContenuto() {
        return contenuto;
    }


    private void setContenuto(Object contenuto) {
        this.contenuto = contenuto;
    }


    public Campo getCampoOriginale() {
        return campoOriginale;
    }


    private void setCampoOriginale(Campo campoOriginale) {
        this.campoOriginale = campoOriginale;
    }


    public void setContenuto(Object contenuto, int tipo) {
        this.contenuto = contenuto;
        this.tipo = tipo;
    }


    public Vista getVistaProprietaria() {
        return vistaProprietaria;
    }


    public void setVistaProprietaria(Vista vistaProprietaria) {
        this.vistaProprietaria = vistaProprietaria;
    }


    public int getTipo() {
        return tipo;
    }


    public void setTipo(int tipo) {
        this.tipo = tipo;
    }


    public boolean isVisibile() {
        return isVisibile;
    }


    public void setVisibile(boolean visibile) {
        this.isVisibile = visibile;
    }


    public boolean isVisibileOriginale() {
        return isVisibileOriginale;
    }


    public void setVisibileOriginale(boolean visibileOriginale) {
        isVisibileOriginale = visibileOriginale;
    }


    public boolean isVisibileEspansione() {
        return isVisibileEspansione;
    }


    public void setVisibileEspansione(boolean visibileEspansione) {
        isVisibileEspansione = visibileEspansione;
    }


    public int getAllineamentoLista() {
        return allineamentoLista;
    }


    /**
     * Regola l'allineamento del testo nella lista.
     * <p/>
     * Metodo sovrascritto dalle sottoclassi<br>
     *
     * @param allineamentoLista codice di allineamento
     *
     * @see javax.swing.SwingConstants
     *      SwingConstants.LEFT
     *      SwingConstants.CENTER
     *      SwingConstants.RIGHT
     *      SwingConstants.LEADING
     *      SwingConstants.TRAILING
     */
    public void setAllineamentoLista(int allineamentoLista) {
        this.allineamentoLista = allineamentoLista;
    }


    public int getLarghezzaColonna() {
        return larghezzaColonna;
    }


    /**
     * Assegna la larghezza colonna a tutti i campi derivanti
     * dall'espansione dell'elemento
     * <p/>
     *
     * @param larghezza da assegnare
     */
    public void setLarghezzaColonna(int larghezza) {
        this.larghezzaColonna = larghezza;
    }


    public String getTitoloColonna() {
        return titoloColonna;
    }


    /**
     * Assegna il titolo colonna a tutti i campi derivanti
     * dall'espansione dell'elemento
     * <p/>
     *
     * @param titoloColonna da assegnare
     */
    public void setTitoloColonna(String titoloColonna) {
        this.titoloColonna = titoloColonna;
    }


    public boolean isRidimensionabile() {
        return isRidimensionabile;
    }


    public void setRidimensionabile(boolean ridimensionabile) {
        isRidimensionabile = ridimensionabile;
    }


    public boolean isEspanso() {
        return isEspanso;
    }


    public void setEspanso(boolean espanso) {
        isEspanso = espanso;
    }


    Modulo getModuloContenuto() {
        return moduloContenuto;
    }


    private void setModuloContenuto(Modulo moduloContenuto) {
        this.moduloContenuto = moduloContenuto;
    }

}// fine della classe
