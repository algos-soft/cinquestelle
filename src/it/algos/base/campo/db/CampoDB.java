/**
 * Title:        CampoDB.java
 * Package:      prove.nuovocampo
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 3 luglio 2003 alle 15.12
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa interfaccia e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.campo.db;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.Db;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.PassaggiSelezione;
import it.algos.base.vista.Vista;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.Viste;

//-----------------------------------------------------------------------------
// Classe principale
//-----------------------------------------------------------------------------


/**
 * Questa interfaccia e' responsabile di: <br>
 * A - Regola le funzionalita di interazione dei Campi col database <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  3 luglio 2003 ore 15.12
 */
public interface CampoDB {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------

    /**
     * Lunghezza indice di default per i campi Testo/Blob
     */
    public static final int LUNGHEZZA_INDICE_DEFAULT = 64;

    //-------------------------------------------------------------------------
    // Metodi pubblici degli oggetti (usati in altre classi)      (instance m.)
    //-------------------------------------------------------------------------


    /**
     * Relaziona il campo.
     * <p/>
     * Metodo invocato dal ciclo inizia del Progetto <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @return true se riuscito
     */
    public boolean relaziona();


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     */
    public abstract void inizializza();


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo invocato da altre classi (o anche da questa) ogni volta che questo oggetto
     * deve <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    public abstract void avvia();


    /**
     * metodi setter implementati in CampoAstratto
     */
    public void setCampoParente(Campo unCampoParente);


    public abstract void setChiavePrimaria(boolean isChiavePrimaria);


    /**
     * Usa un indice unico sul campo
     * <p/>
     *
     * @param flag per usare un indice unico
     */
    public abstract void setUnico(boolean flag);


    public abstract void setAccettaValoreNullo(boolean isAccettaValoreNullo);


    public abstract void setCampoFisico(boolean unFlag);


    public abstract void setFissoAlgos(boolean unFlag);


    /**
     * Regola l'uso dell'indice sul campo
     * <p/>
     *
     * @param flag true se il campo deve essere indicizzato
     */
    public void setIndicizzato(boolean flag);


    public abstract void setLinkato(boolean isLinkato);


    public abstract void setCampoPassaggio(Campo unCampo);


    /**
     * metodi setter implementati in CDBLinkato
     */
    public abstract void setNomeModuloLinkato(String unNomeModuloLinkato);


    /**
     * modulo con il quale si stabilisce il legame
     */
    public abstract void setModuloLinkato(Modulo unModuloLinkato);


    public abstract void setNomeCampoLinkChiave(String unNomeCampoLinkChiave);


    /**
     * Azione da intraprendere sulla tavola molti in caso
     * di cancellazione del record sulla tavola uno.
     * <p/>
     * Valori possibili (da interfaccia Db)<br>
     * - Db.Azione.noAction non fa nulla<br>
     * - Db.Azione.cascade elimina i record<br>
     * - Db.Azione.setNull pone il valore a NULL<br>
     * - Db.Azione.setDefault assegna il valore di default<br>
     */
    public abstract void setAzioneDelete(Db.Azione azione);


    /**
     * Azione da intraprendere sulla tavola molti in caso
     * di modifica del record sulla tavola uno.
     * <p/>
     * Valori possibili (da interfaccia Db)<br>
     * - Db.Azione.noAction non fa nulla<br>
     * - Db.Azione.cascade elimina i record<br>
     * - Db.Azione.setNull pone il valore a NULL<br>
     * - Db.Azione.setDefault assegna il valore di default<br>
     */
    public abstract void setAzioneUpdate(Db.Azione azione);


    public abstract void setNomeVistaLinkata(String unNomeVistaLink);


    public abstract void setVistaLinkata(Viste vista);


    public abstract void setNomeColonnaListaLinkata(String unNomeColonna);


    public abstract void setNomeCampoValoriLinkato(String unNomeCampo);


    public abstract void setCampoLinkato(Campi campo);


    public abstract void setNomeCampoOrdineLinkato(String unNomeCampo);


    public abstract void setRelazionePreferita(boolean unFlag);


    public abstract void setNomeEstratto(String nomeEstratto);


    public abstract void setNomeEstratto(String nomeEstratto, int posizione);


    /**
     * Assegna l'ordine per l'elenco valori linkato.
     * <p/>
     * Usato nei combo
     *
     * @param ordine da assegnare
     */
    public abstract void setOrdineElenco(Ordine ordine);


    /**
     * Assegna l'ordine per l'elenco valori linkato.
     * <p/>
     * Usato nei combo
     *
     * @param campo di ordinamento da assegnare
     */
    public abstract void setOrdineElenco(Campo campo);


    public abstract void addRelazioneCampo();
//    public abstract boolean isRelazioneObbligata()  ;
//    public abstract void setRelazioneObbligata(boolean relazioneObbligata);


    /**
     * metodi setter implementati in CDBSub
     */
    public abstract void setNomeCampoLinkRighe(String unNomeCampoLinkRighe);


    /**
     * Restituisce il valore del campo linkato per un singolo record.
     * <p/>
     * Recupera il campo linkato <br>
     * Esegue la query per il codice record richiesto <br>
     *
     * @param codice del record
     *
     * @return valore del campo per il record richiesto
     */
    public abstract Object getValoreCampoLink(int codice);


    public abstract void setNomeVistaSub(String unNomeVistaSub);


    public abstract boolean isChiavePrimaria();


    public abstract boolean isUnico();


    public abstract boolean isAccettaValoreNullo();


    public abstract boolean isCampoFisico();


    public abstract boolean isFissoAlgos();


    public abstract boolean isIndicizzato();


    public abstract boolean isLinkato();


    /**
     * metodi getter implementati in CDBLinkato
     */
    public abstract String getNomeModuloLinkato();


    public abstract Modulo getModuloLinkato();


    public abstract String getTavolaLinkata();
//    public abstract String getNomeCampoLinkChiave();


    public abstract Db.Azione getAzioneDelete();


    public abstract Db.Azione getAzioneUpdate();


    public abstract Campo getCampoLinkChiave();


    public abstract String getNomeColonnaListaLinkata();


    public abstract String getNomeCampoValoriLinkato();


    /**
     * ritorna il campo che viene visualizzato nella scheda o nel combo
     * <p/>
     *
     * @return il campo visualizzato
     */
    public abstract Campo getCampoValoriLinkato();


    /**
     * assegna il campo che viene visualizzato nella scheda o nel combo
     * <p/>
     *
     * @param campo da visualizzare
     */
    public abstract void setCampoValoriLinkato(Campo campo);


    /**
     * assegna un estratto che fornisce i valori linkati
     * <p/>
     * l'estratto ha la precedenza sul campo linkato
     *
     * @param estratto che fornisce una matrice doppia con codici e valori
     */
    public abstract void setEstrattoLinkato(Estratti estratto);


    public abstract Campo getCampoLinkVisibile();


    public abstract String getNomeVistaLink();


    public abstract Vista getVistaLink();


    public abstract boolean isRelazionePreferita();


    /**
     * Ritorna il filtro base per la selezione dei record
     * linkati da visualizzare
     * <p/>
     *
     * @return il filtro base
     */
    public abstract Filtro getFiltroBase();


    /**
     * Assegna un filtro base per la selezione dei record
     * linkati da visualizzare
     * <p/>
     *
     * @param filtro da utilizzare
     */
    public abstract void setFiltroBase(Filtro filtro);


    /**
     * Ritorna il filtro corrente per la selezione dei record
     * linkati da visualizzare
     * <p/>
     *
     * @return il filtro corrente
     */
    public abstract Filtro getFiltroCorrente();


    /**
     * Assegna un filtro corrente per la selezione dei record
     * linkati da visualizzare
     * <p/>
     *
     * @param filtro da utilizzare
     */
    public abstract void setFiltroCorrente(Filtro filtro);


    /**
     * Ritorna il filtro completo (base+corrente) per la selezione
     * dei record linkati da visualizzare
     * <p/>
     *
     * @return il filtro completo
     */
    public abstract Filtro getFiltro();


    public abstract PassaggiSelezione getPassaggiSelezione();


    public abstract String getNomeEstratto();


    public abstract String getNomeEstratto(int posizione);


    public boolean isRelazionato();


    /**
     * metodi getter implementati in CDBSub
     */
    public abstract Campo getCampoSubTesta();


    public abstract Campo getCampoSubRighe();


    public abstract String getNomeVistaSub();


    public abstract Vista getVistaSub();


    /**
     * metodi setter implementati in CDBComboRadioMetodo
     */
    public abstract void setNomeModuloValori(String nomeModuloValori);


    public abstract void setNomeMetodoValori(String nomeMetodoValori);


    public abstract int getLunghezzaIndice();


    public abstract void setLunghezzaIndice(int lunghezzaIndice);


    /**
     * Ritorna una copia profonda dell'oggetto (deep copy) col casting
     */
    public abstract CampoDB clonaCampo(Campo unCampoParente);

    //-------------------------------------------------------------------------
}// fine della interfaccia prove.nuovocampo.CampoDB.java

//-----------------------------------------------------------------------------

