/**
 * Title:        MenuStampaMenu.java
 * Package:      it.algos.albergo.ristorante.menu
 * Description:
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 16 maggio 2003 alle 11.33
 */

package it.algos.albergo.ristorante.menu.stampa.cliente;

import it.algos.albergo.ristorante.Ristorante;
import it.algos.albergo.ristorante.categoria.Categoria;
import it.algos.albergo.ristorante.lingua.Lingua;
import it.algos.albergo.ristorante.lingua.LinguaModulo;
import it.algos.albergo.ristorante.menu.Menu;
import it.algos.albergo.ristorante.piatto.Piatto;
import it.algos.albergo.ristorante.righemenupiatto.RMP;
import it.algos.base.campo.base.Campo;
import it.algos.base.costante.CostanteBase;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.selezione.QuerySelezione;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Gestire la stampa di un menu <br>
 * B - ... <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  16 maggio 2003 ore 11.33
 */
public class DatiStampa implements CostanteBase, Ristorante {

    private LinguaModulo moduloLingua = null;   // Modulo delle Lingue

    private Modulo moduloMenu = null;   // Modulo del Menu

    private Modulo moduloRMP = null;   // Modulo delle Righe Menu

    private Modulo moduloPiatto = null;   // Modulo di Piatto

    private Modulo moduloCategoria = null;   // Modulo di Categoria

    private StampaMenu unaPaginaStampa = null;  // riferimento alla pagina da stampare

    private int codiceMenu = 0;   // codice del menu da stampare

    private int codL1 = 0;  // codice della prima lingua

    private int codL2 = 0;  // codice della prima lingua

    private String congiunzioneL1 = "";  // congiunzione della prima lingua

    private String congiunzioneL2 = "";  // congiunzione della prima lingua

    /**
     * flag - per non trasformare l'iniziale del contorno in
     * minuscolo per la prima lingua
     */
    private boolean noContornoMinuscoloL1 = false;

    /**
     * flag - per non trasformare l'iniziale del contorno in
     * minuscolo per la seconda lingua
     */
    private boolean noContornoMinuscoloL2 = false;

    /**
     * flag - controlla se i dati sono stati caricati
     */
    private boolean isDatiCaricati = false;


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param unaPagina pagina da stampare
     */
    public DatiStampa(StampaMenu unaPagina) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza con i parametri */
        this.unaPaginaStampa = unaPagina;

        /* regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* regola i riferimenti ad altri oggetti di uso comune */
        this.moduloLingua = (LinguaModulo)Progetto.getModulo(MODULO_LINGUA);
        this.moduloMenu = Progetto.getModulo(MODULO_MENU);
        this.moduloPiatto = Progetto.getModulo(MODULO_PIATTO);
        this.moduloRMP = Progetto.getModulo(MODULO_RIGHE_PIATTO);
        this.moduloCategoria = Progetto.getModulo(MODULO_CATEGORIA);

        /* regola le variabili di istanza con dei valori di default */
        this.setCodiceMenu(1);
        this.setCodL1(1);
        this.setCodL2(2);
    } /* fine del metodo inizia */


    /**
     * Carica i dati dal menu.
     * <p/>
     */
    public void caricaDati() {

        /* recupera i testi delle congiunzioni per le lingue selezionate */
        this.recuperaCongiunzione();

        /* recupera il flag no contorno minuscolo per le lingue selezionate */
        this.recuperaFlagNoContornoMinuscolo();

        /* recupera i dati del menu e li passa all'oggetto grafico */
        this.recuperaDatiMenu();

        /* regola il flag per indicare che i dati sono stati caricati */
        this.setDatiCaricati(true);


    } /* fine del metodo */


    /**
     * Recupera i dati del menu e li passa all'oggetto grafico.
     * <p/>
     */
    private void recuperaDatiMenu() {

        try {                                   // prova ad eseguire il codice

            /* La data del menu */
            unaPaginaStampa.setData(stringaDataMenu());

            /* I nomi del Pasto */
            unaPaginaStampa.setPasto(stringhePasto());

            /* L'elenco dei piatti */
            unaPaginaStampa.setPiatti(recuperaPiatti());

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Recupera le stringhe del Pasto nelle due lingue.
     * <p/>
     *
     * @return String[] array contenente la coppia di stringhe lette
     */
    private String[] stringhePasto() {
        /** variabili locali di lavoro */
        Campo campoCodPasto = null;
        int codPasto = 0;
        String[] coppiaStringhe = new String[2];

        try { // prova ad eseguire il codice
            /* regolazione variabili */
            campoCodPasto = moduloMenu.getCampo(Menu.Cam.pasto.get());
            coppiaStringhe[0] = "";
            coppiaStringhe[1] = "";

            /* recupero il codice del pasto */
            codPasto = moduloMenu.query().valoreInt(campoCodPasto, codiceMenu);
            if (codPasto != 0) {
                coppiaStringhe[0] = moduloLingua.getLogica().getNomePasto(codPasto, codL1);
                coppiaStringhe[1] = moduloLingua.getLogica().getNomePasto(codPasto, codL2);
            } else {
                throw new Exception("Codice pasto non specificato.");
            }// fine del blocco if-else


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        return coppiaStringhe;
    } /* fine del metodo */


    /**
     * Recupera dal Menu corrente la stringa della data.
     * <p/>
     *
     * @return la stringa con la data da stampare
     */
    private String stringaDataMenu() {
        /** variabili locali di lavoro */
        Object valore = null;
        String stringa = "";
        Date data = null;
        DateFormat df = null;

        /* recupera il valore e lo converte in stringa */
        valore = moduloMenu.query().valoreCampo(Menu.Cam.data.get(), codiceMenu);

        if (valore != null) {
            if (valore instanceof java.util.Date) {
                data = (java.util.Date)valore;
                df = new SimpleDateFormat("EEEE, d MMMM yyyy");
                stringa = df.format(data);
            }// fine del blocco if
        } /* fine del blocco if */

        return stringa;
    } /* fine del metodo */


    /**
     * Recupera l'elenco dei piatti nel menu con descrizione.
     * <p/>
     *
     * @return array di oggetti di tipo MenuCoppiaPiatti
     */
    private ArrayList recuperaPiatti() {

        /** variabili locali di lavoro */
        CoppiaPiatti coppiaPiatti = null;
        ArrayList unElencoCoppie = new ArrayList();
        Query query = null;
        Filtro filtro = null;
        Dati dati = null;
        ArrayList chiaviPiatto = null;
        ArrayList chiaviContorno = null;
        ArrayList congelatoPiatto = null;
        ArrayList congelatoContorno = null;
        boolean flag = false;
        String unTesto = null;
        ArrayList infoPiattoL1 = null;
        ArrayList infoPiattoL2 = null;
        ArrayList infoContornoL1 = null;
        ArrayList infoContornoL2 = null;

        try {    // prova ad eseguire il codice

            /* recupero le liste ordinate delle chiavi dei piatti e dei contorni */
            query = new QuerySelezione(moduloRMP);
            query.addCampo(RMP.CAMPO_PIATTO);
            query.addCampo(RMP.CAMPO_CONTORNO);
            query.addCampo(RMP.CAMPO_PIATTO_CONGELATO);
            query.addCampo(RMP.CAMPO_CONTORNO_CONGELATO);

            filtro = FiltroFactory.crea(moduloMenu.getCampoChiave(), codiceMenu);
            query.setFiltro(filtro);

            query.addOrdine(moduloCategoria.getCampoOrdine());
            query.addOrdine(moduloRMP.getCampoOrdine());

            dati = moduloRMP.query().querySelezione(query);

            chiaviPiatto = dati.getValoriColonna(0);
            chiaviContorno = dati.getValoriColonna(1);
            congelatoPiatto = dati.getValoriColonna(2);
            congelatoContorno = dati.getValoriColonna(3);
            dati.close();

            /* scorre la lista dei piatti e per ogni riga recupera le
             * informazioni per piatto e contorno nelle due lingue */
            int chiavePiatto = 0;
            int chiaveContorno = 0;

            for (int k = 0; k < chiaviPiatto.size(); k++) {

                /* crea un nuovo oggetto CoppiaPiatti */
                coppiaPiatti = new CoppiaPiatti();

                /* recupera le chiavi per piatto e contorno */
                chiavePiatto = Libreria.objToInt(chiaviPiatto.get(k));
                chiaveContorno = Libreria.objToInt(chiaviContorno.get(k));

                /* recupero le informazioni sul piatto e contorno nelle due lingue */
                infoPiattoL1 = this.getInfoPiatto(chiavePiatto, codL1);
                infoPiattoL2 = this.getInfoPiatto(chiavePiatto, codL2);
                infoContornoL1 = this.getInfoPiatto(chiaveContorno, codL1);
                infoContornoL2 = this.getInfoPiatto(chiaveContorno, codL2);

                /* recupera il codice categoria */
                coppiaPiatti.setCodiceCategoria(((Integer)infoPiattoL1.get(3)).intValue());

                /* recupera il flag congelato per piatto e contorno (dalla riga RMP)*/
                flag = Libreria.objToBool(congelatoPiatto.get(k));
                coppiaPiatti.setCongelatoPiatto(flag);
                flag = Libreria.objToBool(congelatoContorno.get(k));
                coppiaPiatti.setCongelatoContorno(flag);

                /* recupera la congiunzione per le due lingue */
                coppiaPiatti.setCongiunzione1(congiunzioneL1);
                coppiaPiatti.setCongiunzione2(congiunzioneL2);

                /* recupera le informazioni del piatto principale per la prima lingua */
                coppiaPiatti.setCategoriaPiatto1((String)infoPiattoL1.get(0));
                coppiaPiatti.setNomePiatto1((String)infoPiattoL1.get(1));
                coppiaPiatti.setDescrizionePiatto1((String)infoPiattoL1.get(2));

                /* recupera le informazioni del contorno per la prima lingua */
                coppiaPiatti.setNomeContorno1((String)infoContornoL1.get(1));
                coppiaPiatti.setDescrizioneContorno1((String)infoContornoL1.get(2));
                /* se previsto, mette in minuscolo la prima lettera del nome contorno */
                if (this.noContornoMinuscoloL1 == false) {
                    unTesto = coppiaPiatti.getNomeContorno1();
                    unTesto = Lib.Testo.primaMinuscola(unTesto);
                    coppiaPiatti.setNomeContorno1(unTesto);
                }// fine del blocco if

                /* recupera le informazioni del piatto principale per la seconda lingua */
                coppiaPiatti.setCategoriaPiatto2((String)infoPiattoL2.get(0));
                coppiaPiatti.setNomePiatto2((String)infoPiattoL2.get(1));
                coppiaPiatti.setDescrizionePiatto2((String)infoPiattoL2.get(2));

                /* recupera le informazioni del contorno per la seconda lingua */
                coppiaPiatti.setNomeContorno2((String)infoContornoL2.get(1));
                coppiaPiatti.setDescrizioneContorno2((String)infoContornoL2.get(2));
                /* se previsto, mette in minuscolo la prima lettera del nome contorno */
                if (this.noContornoMinuscoloL2 == false) {
                    unTesto = coppiaPiatti.getNomeContorno2();
                    unTesto = Lib.Testo.primaMinuscola(unTesto);
                    coppiaPiatti.setNomeContorno2(unTesto);
                }// fine del blocco if

                /** aggiunge la coppia di piatti all'elenco */
                unElencoCoppie.add(coppiaPiatti);

            } /* fine del blocco for */
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        return unElencoCoppie;

    } /* fine del metodo */


    /**
     * Recupera una lista di informazioni su un piatto in una data lingua.
     * <p/>
     *
     * @param codPiatto il codice chiave del piatto
     * @param codLingua il codice della lingua
     *
     * @return un array list strutturata come segue:
     *         0  String descrizione categoria
     *         1  String nome piatto
     *         2  String descrizione piatto
     *         3  Integer codice categoria
     */
    private ArrayList getInfoPiatto(int codPiatto, int codLingua) {
        /* varibili locali di lavoro */
        ArrayList infoPiatto = new ArrayList();
        HashMap mappa = null;
        ArrayList campi = null;
        Query query = null;
        Filtro filtro = null;
        Dati dati = null;
        String nomeCampo = null;
        Campo campo = null;
        String nomePiatto = "";
        String descrizionePiatto = "";
        String testoCategoria = "";
        int codCategoria = 0;

        /* recupera tutti i campi del piatto */
        mappa = moduloPiatto.getModello().getCampiFisici();
        campi = Libreria.hashMapToArrayList(mappa);
        filtro = FiltroFactory.codice(moduloPiatto, codPiatto);
        query = new QuerySelezione(moduloPiatto);
        query.setCampi(campi);
        query.setFiltro(filtro);
        dati = moduloPiatto.query().querySelezione(query);

        /* se ha trovato il piatto, procede a recuperare i valori*/
        if (dati.getRowCount() > 0) {

            /* recupera il codice categoria del piatto */
            campo = moduloPiatto.getCampo(Piatto.CAMPO_CATEGORIA);
            codCategoria = dati.getIntAt(0, campo);

            /* recupera il nome del piatto */
            nomeCampo = Piatto.NOME + Piatto.CAMPO_LINGUA[codLingua - 1];
            campo = moduloPiatto.getCampo(nomeCampo);
            nomePiatto = dati.getStringAt(0, campo);

            /* recupera la descrizione del piatto */
            nomeCampo = Piatto.SPIEGAZIONE + Piatto.CAMPO_LINGUA[codLingua - 1];
            campo = moduloPiatto.getCampo(nomeCampo);
            descrizionePiatto = dati.getStringAt(0, campo);

            /* recupera i valori nella lingua richiesta */
            testoCategoria = this.stringaCategoria(codCategoria, codLingua);

        } /* fine del blocco if */

        dati.close();

        infoPiatto.add(testoCategoria);
        infoPiatto.add(nomePiatto);
        infoPiatto.add(descrizionePiatto);
        infoPiatto.add(new Integer(codCategoria));

        return infoPiatto;
    } /* fine del metodo */


    /**
     * Recupera la stringa di una categoria in una data lingua.
     * <p/>
     *
     * @param codCategoria il codice categoria
     * @param codLingua il codice della lingua
     *
     * @return la stringa con la descrizione della categoria
     */
    private String stringaCategoria(int codCategoria, int codLingua) {
        String nomeCategoria = "";
        String unNomeCampo = null;
        Campo unCampo = null;

        /* selezione */
        switch (codLingua) {
            case 1:
                unNomeCampo = Categoria.CAMPO_ITALIANO;
                break;
            case 2:
                unNomeCampo = Categoria.CAMPO_TEDESCO;
                break;
            case 3:
                unNomeCampo = Categoria.CAMPO_INGLESE;
                break;
            case 4:
                unNomeCampo = Categoria.CAMPO_FRANCESE;
                break;
            default:
                unNomeCampo = Categoria.CAMPO_ITALIANO;
                break;
        } /* fine del blocco switch */

        /* recupera l'oggetto campo */
        unCampo = moduloCategoria.getCampo(unNomeCampo);

        /* Recupera il nome della categoria */
        nomeCategoria = moduloCategoria.query().valoreStringa(unCampo, codCategoria);

        /* valore di ritorno */
        return nomeCategoria;
    } /* fine del metodo */


    /**
     * Recupera i testi delle congiunzioni dalla tabella lingua.
     * <p/>
     */
    private void recuperaCongiunzione() {
        /** variabili locali di lavoro */
        Campo campo = null;

        /* regolazione variabili */
        campo = moduloLingua.getCampo(Lingua.CAMPO_CONGIUNZIONE);

        /* recupera i dati */
        congiunzioneL1 = moduloLingua.query().valoreStringa(campo, codL1);
        congiunzioneL2 = moduloLingua.query().valoreStringa(campo, codL2);
    } /* fine del metodo */


    /*
    * Recupera i flag no contorno minuscolo dalla tabella lingua
    * per le lingue selezionate
    */
    private void recuperaFlagNoContornoMinuscolo() {
        this.noContornoMinuscoloL1 = this.getFlagNoMinuscolo(codL1);
        this.noContornoMinuscoloL2 = this.getFlagNoMinuscolo(codL2);
    } /* fine del metodo */


    /**
     * Recupera il flag per non trasformare il contorno in minuscolo
     * per una data lingua.
     * <p/>
     *
     * @param codiceLingua il codice della lingua
     *
     * @return il valore del flag richiesto
     */
    private boolean getFlagNoMinuscolo(int codiceLingua) {
        /* variabili e costanti locali di lavoro */
        boolean flagNoMinuscolo = false;
        String nomeCampo = null;

        try {    // prova ad eseguire il codice

            nomeCampo = Lingua.CAMPO_CONTORNO_MINUSCOLO;
            flagNoMinuscolo = moduloLingua.query().valoreBool(nomeCampo, codiceLingua);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return flagNoMinuscolo;
    } // fine del metodo


    /**
     * Regolazione del Codice.
     * <p/>
     *
     * @param unCodiceMenu
     */
    public void setCodiceMenu(int unCodiceMenu) {
        this.codiceMenu = unCodiceMenu;
    } /* fine del metodo setter */


    /**
     * Regolazione del codice della Prima Lingua.
     * <p/>
     *
     * @param codL1
     */
    public void setCodL1(int codL1) {
        this.codL1 = codL1;
    } /* fine del metodo setter */


    /**
     * Regolazione del codice della Seconda Lingua.
     * <p/>
     *
     * @param codL2
     */
    public void setCodL2(int codL2) {
        this.codL2 = codL2;
    } /* fine del metodo setter */


    public boolean isDatiCaricati() {
        return isDatiCaricati;
    }


    private void setDatiCaricati(boolean datiCaricati) {
        isDatiCaricati = datiCaricati;
    }
}// fine della classe