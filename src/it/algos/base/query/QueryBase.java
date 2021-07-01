/**
 * Title:     QueryBase
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      2-nov-2004
 */
package it.algos.base.query;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.campi.Campi;
import it.algos.base.query.campi.CampoQuery;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.PassaggiSelezione;
import it.algos.base.query.selezione.PassaggioObbligato;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Implementazione astratta di una Query generica.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 2-nov-2004 ore 14.25.14
 */
public abstract class QueryBase extends Object implements Query {

    /**
     * Modulo di riferimento
     */
    private Modulo modulo = null;

    /**
     * Contenitore dei campi per la Query
     */
    private Campi campi = null;

    /**
     * Filtro per la selezione dei records
     */
    private Filtro filtro = null;

    /**
     * Contenitore per i passaggi obbligati tra le relazioni
     */
    private PassaggiSelezione passaggi = null;


    /**
     * Costruttore completo
     * <p/>
     *
     * @param modulo il modulo di riferimento.
     */
    public QueryBase(Modulo modulo) {
        /** rimanda al costruttore della superclasse */
        super();

        /** regolazioni iniziali di riferimenti e variabili */
        this.setModulo(modulo);

        try {
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore */


    /**
     * Regolazioni iniziali di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore
     */
    private void inizia() {

        /* crea il contenitore per i campi */
        this.setCampi(new Campi(this));

        /* crea un filtro per la Query */
        this.setFiltro(new Filtro());

        /* crea un oggetto Passaggi per la Query */
        this.setPassaggi(new PassaggiSelezione());

    } /* fine del metodo inizia */


    /**
     * Risolve la query.
     * <p/>
     * Sovrascritto dalle sottoclassi.<br>
     * Converte tutti gli oggetti che usano nomi di campo
     * in oggetti che usano solo oggetti campo,
     * in base al modulo di questa Query.<br>
     * Nella classe base risolve Campi e Filtro.<br>
     */
    public void risolvi() {
        /* variabili e costanti locali di lavoro */
        Campi campi;
        Filtro filtro;

        try { // prova ad eseguire il codice

            /* risolve i campi */
            campi = this.getCampi();
            if (campi != null) {
                campi.risolvi(this.getModulo());
            }// fine del blocco if

            /* risolve il filtro */
            filtro = this.getFiltro();
            if (filtro != null) {
                filtro.risolvi(this.getModulo());
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Trasporta i valori della query da livello Memoria
     * a livello Database.
     * <p/>
     * Sovrascritto dalle sottoclassi.<br>
     * Converte i valori da Memoria ad Archivio
     * Converte i valori da Archivio a Database
     * Nella classe base converte i valori del solo Filtro (va fatto sempre)
     * Nelle classi specifiche converte eventualmente anche i valori dei campi.
     *
     * @param db il database per il del quale convertire i valori
     */
    public void memoriaDb(Db db) {

        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;

        try { // prova ad eseguire il codice
            filtro = this.getFiltro();
            if (filtro != null) {
                filtro.bl2db(db);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }

//    /**
//     * Converte i valori della Query da livello Business Logic
//     * a livello database.
//     * <p/>
//     * Sovrascritto dalle sottoclassi.
//     * Nella classe base converte i valori del solo Filtro (va fatto sempre)
//     * Nelle classi specifiche converte eventualmente anche i valori dei campi.
//     *
//     * @param db il database a fronte del quale convertire i valori
//     */
//    public void bl2db(Db db) {
//        /* variabili e costanti locali di lavoro */
//        Filtro filtro = null;
//
//        try { // prova ad eseguire il codice
//            filtro = this.getFiltro();
//            if (filtro != null) {
//                filtro.bl2db(db);
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//    }

//    /**
//     * Converte i valori della Query da livello Memoria a livello Archivio
//     * <p/>
//     */
//    public void memoriaArchivio() {
//
//
//        try { // prova ad eseguire il codice
//
//            this.getCampi().memoriaArchivio();
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//    }

    //-------------------------------------------------------------------------
    // Metodi pubblici per la gestione dei Campi
    //-------------------------------------------------------------------------


    /**
     * Aggiunge un campo alla lista dei campi.
     * <p/>
     *
     * @param campo il Campo da aggiungere
     *
     * @return l'elemento CampoQuery aggiunto
     */
    public CampoQuery addCampo(Campo campo) {
        return this.getCampi().add(campo);
    } /* fine del metodo setter */


    /**
     * Aggiunge un campo alla lista dei campi.
     * <p/>
     *
     * @param nomeCampo il nome interno del campo
     *
     * @return l'elemento CampoQuery aggiunto
     */
    public CampoQuery addCampo(String nomeCampo) {
        return this.getCampi().add(nomeCampo);
    } /* fine del metodo setter */

    /**
     * Aggiunge un campo alla lista dei campi.
     * <p/>
     *
     * @param campo dalla Enum Campi
     *
     * @return l'elemento CampoQuery aggiunto
     */
    public CampoQuery addCampo(it.algos.base.wrapper.Campi campo) {
        return this.getCampi().add(campo);
    } /* fine del metodo setter */



    /**
     * Aggiunge un campo con valore alla lista dei campi.
     * <p/>
     *
     * @param campo il campo
     * @param valore il valore per il campo
     *
     * @return l'elemento CampoQuery aggiunto
     */
    public CampoQuery addCampo(Campo campo, Object valore) {
        return this.getCampi().add(campo, valore);
    } /* fine del metodo */


    /**
     * Aggiunge un campo con valore alla lista dei campi.
     * <p/>
     *
     * @param nomeCampo il nome del campo
     * @param valore il valore per il campo
     *
     * @return l'elemento CampoQuery aggiunto
     */
    public CampoQuery addCampo(String nomeCampo, Object valore) {
        return this.getCampi().add(nomeCampo, valore);
    } /* fine del metodo */


    /**
     * Assegna l'elenco completo dei campi.
     * <p/>
     * Usa una ArrayList in ingresso
     *
     * @param campi lista di oggetti Campo o nomi campo
     */
    public void setCampi(ArrayList campi) {
        /* variabili e costanti locali di lavoro */
        Object oggetto = null;

        /* resetta l'oggetto Campi */
        this.getCampi().reset();

        /* aggiunge i campi dalla lista */
        for (int k = 0; k < campi.size(); k++) {
            oggetto = campi.get(k);
            if (oggetto instanceof Campo) {
                this.addCampo((Campo)oggetto);
            }// fine del blocco if
            if (oggetto instanceof String) {
                this.addCampo((String)oggetto);
            }// fine del blocco if
        } // fine del ciclo for
    } /* fine del metodo */


    /**
     * Assegna l'elenco completo dei campi.
     * <p/>
     * Usa una HashMap in ingresso
     *
     * @param campi collezione di oggetti Campo o nomi campo
     */
    public void setCampi(HashMap campi) {
        ArrayList listaCampi = Libreria.hashMapToArrayList(campi);
        this.setCampi(listaCampi);
    } /* fine del metodo */


    /**
     * Assegna un valore a un campo.
     * <p/>
     *
     * @param campo al quale assegnare il valore
     * @param valore da assegnare
     */
    public void setValore(Campo campo, Object valore) {
    }


    /**
     * Aggiunge un ordine alla query.
     * <p/>
     * Sovrascritto dalle sottoclassi.
     *
     * @param campo il campo da aggiungere
     */
    public void addOrdine(Campo campo) {
    } /* fine del metodo */


    /**
     * Ritorna l'elenco dei Campi della query.
     * <p/>
     *
     * @return l'elenco dei campi
     *         (oggetti String o Campo se non risolta, solo Campo se risolta)
     */
    public ArrayList getListaCampi() {
        return this.getCampi().getCampi();
    }


    /**
     * Ritorna l'elenco degli elementi della Query.
     * <p/>
     *
     * @return l'elenco degli elementi della Query (oggetti CampoQuery)
     */
    public ArrayList getListaElementi() {
        return this.getCampi().getListaElementi();
    }


    /**
     * Ritorna l'elenco dei valori Memoria dei campi della query.
     * <p/>
     *
     * @return l'elenco dei valori (oggetti Object)
     */
    public ArrayList getListaValori() {
        return this.getCampi().getValori();
    }


    /**
     * Ritorna l'elenco dei valori Archivio dei campi della query.
     * <p/>
     *
     * @return l'elenco dei valori (oggetti Object)
     */
    public ArrayList getListaValoriArchivio() {
        return this.getCampi().getValoriArchivio();
    }


    /**
     * Aggiunge un filtro al filtro.
     * <p/>
     * Usa la clausola di unione specificata<br>
     *
     * @param unione la clausola di unione
     * @param filtro il filtro da aggiungere
     */
    public void addFiltro(String unione, Filtro filtro) {
        this.getFiltro().add(unione, filtro);
    } /* fine del metodo */


    /**
     * Aggiunge un filtro al filtro.
     * <p/>
     * Usa la clausola di unione di default (AND)
     *
     * @param filtro il filtro da aggiungere
     */
    public void addFiltro(Filtro filtro) {
        this.getFiltro().add(filtro);   // usa unione di default
    } /* fine del metodo */


    public Modulo getModulo() {
        return modulo;
    }


    private void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }


    /**
     * Ritorna l'oggetto contenitore dei campi della Query
     * <p/>
     *
     * @return il contenitore dei campi della Query.
     */
    public Campi getCampi() {
        return campi;
    }


    protected void setCampi(Campi campi) {
        this.campi = campi;
    }


    /**
     * Ritorna il filtro della Query
     * <p/>
     *
     * @return il filtro della Query
     */
    public Filtro getFiltro() {
        return filtro;
    }


    /**
     * Assegna un filtro alla Query
     */
    public void setFiltro(Filtro filtro) {
        this.filtro = filtro;
    }


    /**
     * Ritorna l'oggetto PassaggiSelezione
     * <p/>
     *
     * @return l'oggetto PassaggiSelezione
     */
    public PassaggiSelezione getPassaggi() {
        return passaggi;
    }


    private void setPassaggi(PassaggiSelezione passaggi) {
        this.passaggi = passaggi;
    }


    /**
     * attiva il filtro hard
     * <p/>
     * il filtro hard e' un filtro che viene automaticamente aggiunto
     * alla query selezione.
     * di default e' attivo.
     * Metodo sovrascritto dalle sottoclassi.
     *
     * @param usaFiltroHard flag di attivazione
     */
    public void setUsaFiltroHard(boolean usaFiltroHard) {
    }


    /**
     * Restituisce la lista univoca dei valori distinti per tutti i campi
     * della query.
     * <p/>
     * Significativo solo per QuerySelezione.
     * Se la query contiene un solo campo, restituisce solo i valori diversi
     * Se la query contiene più di un campo, restituisce le righe dove le combinazioni
     * dei valori sono diverse (comportamento standard SQL)
     *
     * @param flag per restituire solo i valori distinti
     */
    public void setValoriDistinti(boolean flag) {
    }


    /**
     * Assegna un Ordine alla Query
     * <p/>
     * Implementato nelle classi specifiche
     *
     * @param ordine l'oggetto Ordine da asegnare
     */
    public void setOrdine(Ordine ordine) {
    }

    /**
     * Assegna un Ordine alla Query
     * <p>
     * @param campo di ordinamento
     */
    public void setOrdine(Campo campo){
    }


//    /**
//     * Aggiunge un Passaggio Obbligato.
//     * <p/>
//     * Implementato nelle classi specifiche
//     *
//     * @param passaggio il passaggio obbligato
//     */
//    public void addPassaggio(PassaggioObbligato passaggio) {
//    }
//
//
//    /**
//     * Aggiunge un Passaggio Obbligato.
//     * <p/>
//     * Implementato nelle classi specifiche
//     *
//     * @param modulo il modulo di destinazione
//     * @param campo  il Campo dal quale passare obbligatoriamente
//     */
//    public void addPassaggio(Modulo modulo, Campo campo) {
//    }


    /**
     * Aggiunge un Passaggio Obbligato.
     * <p/>
     *
     * @param passaggio il passaggio obbligato
     */
    public void addPassaggio(PassaggioObbligato passaggio) {
        this.getPassaggi().addPassaggio(passaggio);
    } /* fine del metodo */


    /**
     * Aggiunge un Passaggio Obbligato.
     * <p/>
     *
     * @param modulo il modulo di destinazione
     * @param campo il Campo dal quale passare obbligatoriamente
     */
    public void addPassaggio(Modulo modulo, Campo campo) {
        this.getPassaggi().addPassaggio(modulo, campo);
    } /* fine del metodo */


}// fine della classe
