/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.base.importExport;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.LibFile;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.wrapper.CampoValore;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;

/**
 * Apre un dialogo per l'importazione dei dati.
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 3-4-05
 */
public class Importa {

    /**
     * modulo di riferimento per i dati da importare
     */
    private Modulo modulo = null;

    /**
     * riferimento al navigatore a cui appartiene questo dialogo
     */
    private Navigatore navigatore = null;

    /**
     * campi da esportare
     */
    private ArrayList<String> campiImport;


    /**
     * Costruttore completo senza parametri.
     */
    public Importa() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Costruttore completo con parametri.
     *
     * @param modulo di riferimento per i dati da importare
     */
    public Importa(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setModulo(modulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Costruttore completo con parametri.
     *
     * @param navigatore a cui appartiene questo dialogo
     */
    public Importa(Navigatore navigatore) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setNavigatore(navigatore);

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
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        File file = null;

        try { // prova ad eseguire il codice

            /* seleziona il file di uscita */
            if (continua) {
                file = LibFile.getFile();
                continua = (file != null);
            }// fine del blocco if

            /* esporta fisicamente i dati */
            if (continua) {
                this.importaDati(file);
            }// fine del blocco if

            /* crea e mostra il dialogo */
//            continua = this.creaDialogo();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

//    /**
//     * Crea e mostra il dialogo.
//     * <p/>
//     */
//    private boolean creaDialogo() {
//        /* variabili e costanti locali di lavoro */
//        boolean continua = false;
//        Navigatore nav;
//        Lista lista;
//        Vista vista;
//        String voce = "Importazione dei dati";
//        String messaggio = "Seleziona i campi da importare";
//        Dialogo dialogo;
//        PannelloListaDoppia pannello;
//        ArrayList<Campo> campiLista;
//        ArrayList<String> nomiCampi;
//        ArrayList<String> campiExport = null;
//
//        try { // prova ad eseguire il codice
//            /* recupera i campi della lista(tabella) visibile */
//            nomiCampi = new ArrayList<String>();
//
//            /* recupera la vista interessata */
//            nav = this.getNavigatore();
//            lista = nav.getLista();
//            vista = lista.getVista();
//
//            /* recupera i campi */
//            if (Pref.getBool(Pref.Gen.PROGRAMMATORE)) {
//                campiLista = vista.getCampi();
//            } else {
//                campiLista = vista.getCampiVisibili();
//            }// fine del blocco if-else
//
//            for (Campo campo : campiLista) {
//                nomiCampi.add(campo.getNomeInternoCampo());
//            } // fine del ciclo for-each
//
//            /* crea e inizializza il pannello doppio interno al dialogo */
//            pannello = new PannelloListaDoppia();
//            pannello.setTitolo(messaggio);
//            pannello.setValoriListaSx(nomiCampi);
//            pannello.inizializza();
//
//            /* crea e presenta il dialogo  */
//            dialogo = DialogoFactory.annullaConferma(voce, "");
//            dialogo.setBordoPagine(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//            dialogo.setMessaggio(
//                    "Esportazione dei dati attualmente caricati in lista");
//            dialogo.setRidimensionabile(true);
//            dialogo.addComponente(pannello);
//            dialogo.avvia();
//
//            /* post dialogo */
//            if (dialogo.isConfermato()) {
//                campiExport = pannello.getPanListaDx().getValori();
//                this.setCampiImport(campiExport);
//                continua = true;
//            } else {
//            }// fine del blocco if-else
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return continua;
//    }


    /**
     * Esporta fisicamente i dati.
     * <p/>
     */
    private void importaDati(File file) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        boolean cont = true;
        BufferedReader entrata = null;
        ArrayList<String> lista = null;

        try {        // prova ad eseguire il codice

            continua = (file != null);

            if (continua) {
                entrata = LibFile.apreBufferLettura(file);
                continua = (entrata != null);
            }// fine del blocco if

            if (continua) {
                while (cont) {
                    lista = LibFile.leggeArrayRiga(entrata);

                    if (lista != null) {
                        elaboraRecord(lista);
                    } else {
                        cont = false;
                    }// fine del blocco if-else

                }// fine del blocco while
            }// fine del blocco if


        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Elabora un singolo record.
     *
     * @param lista di valori del record come da file importato
     */
    protected void elaboraRecord(ArrayList<String> lista) {
        /* variabili e costanti locali di lavoro */
        Modulo modulo;
        ArrayList<Campo> campiModello = null;
        ArrayList<CampoValore> campiValore = null;
        CampoValore campoValore;
        Campo campo;
        String valore;
        Object valoreConvertito;

        try { // prova ad eseguire il codice
            modulo = this.getModuloRiferimento();

            campiModello = modulo.getCampiFisiciNonFissi();

            campiValore = new ArrayList<CampoValore>();

            for (int k = 0; k < campiModello.size(); k++) {
                campo = campiModello.get(k);
                valore = "";
                if (lista.size() > k) {
                    valore = lista.get(k);
                }// fine del blocco if

                valoreConvertito = campo.getCampoDati().convertiMemoria(valore);
                campoValore = new CampoValore(campo, valoreConvertito);
                campiValore.add(campoValore);
            } // fine del ciclo for

            modulo.query().nuovoRecord(campiValore);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Recupera un elemento da una lista di stringhe.
     * <p/>
     *
     * @param lista di stringhe
     * @param pos dell'elemento (0 per la prima)
     *
     * @return la stringa alla posizione richiesta
     *         Se pos è fuori range non va in errore ma ritorna stringa vuota
     */
    protected String getStringa(ArrayList<String> lista, int pos) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";

        try {    // prova ad eseguire il codice
            if (pos < lista.size()) {
                stringa = lista.get(pos);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna il modulo di riferimento.
     * <p/>
     * Usa il modulo specificato
     * In assenza, usa il modulo del Navigatore
     *
     * @return il modulo di riferimento
     */
    public Modulo getModuloRiferimento() {
        /* variabili e costanti locali di lavoro */
        Modulo modulo = null;
        Navigatore nav;

        try { // prova ad eseguire il codice
            modulo = this.getModulo();
            if (modulo == null) {
                nav = this.getNavigatore();
                if (nav != null) {
                    modulo = nav.getModulo();
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return modulo;
    }


    private Modulo getModulo() {
        return modulo;
    }


    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }


    protected Navigatore getNavigatore() {
        return navigatore;
    }


    private void setNavigatore(Navigatore navigatore) {
        this.navigatore = navigatore;
    }


    private ArrayList<String> getCampiImport() {
        return campiImport;
    }


    private void setCampiImport(ArrayList<String> campiImport) {
        this.campiImport = campiImport;
    }
} // fine della classe
