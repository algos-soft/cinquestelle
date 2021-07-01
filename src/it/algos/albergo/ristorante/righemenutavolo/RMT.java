/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      4-feb-2005
 */
package it.algos.albergo.ristorante.righemenutavolo;

import it.algos.base.errore.Errore;
import it.algos.base.interfaccia.Generale;
import it.algos.base.libreria.Lib;
import it.algos.base.wrapper.Campi;

/**
 * Interfaccia per il package RigheMenuTavolo.
 * <p/>
 * Costanti pubbliche, codifiche e metodi (astratti) di questo package <br>
 * Questa interfaccia: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> </li>
 * <li> Fornisce un'interfaccia unificata per tutte le chiamate alle classi di
 * questo package </li>
 * <li> Mantiene la codifica del nome-chiave interno del modulo (usato nel Modello) </li>
 * <li> Mantiene la codifica della tavola di archivio collegata (usato nel Modello) </li>
 * <li> Mantiene la codifica del titolo della finestra (usato nel Navigatore) </li>
 * <li> Mantiene la codifica del nome del modulo come appare nel Menu Moduli
 * (usato nel Navigatore) </li>
 * <li> Mantiene la codifica delle viste (per la lista) </li>
 * <li> Mantiene la codifica dei set di campi (per la scheda) </li>
 * <li> Mantiene la codifica dei campi (per il modello e per la scheda) </li>
 * <li> Mantiene la codifica dei moduli (per gli altri package) </li>
 * <li> Mantiene altre costanti utilizzate in tutto il package </li>
 * <li> Dichiara (astratti) i metodi  utilizzati nel package che devono
 * essere visti all'esterno </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 4-feb-2005 ore 17.17.36
 */
public interface RMT extends Generale {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "RigheMenuTavolo";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "RigheMenuTavolo";

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Righe menu tavolo";

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "RMT";

    /**
     * Codifica del titolo della tabella come appare nella altre Liste
     * (usato nel Modello)
     */
    public static final String TITOLO_TABELLA = NOME_TAVOLA;

    /**
     * Codifica delle viste (espanse e clonate) per la Lista
     * (oltre a quelle standard della superclasse)
     * (ATTENZIONE - il nome della Vista DEVE essere diverso dai nomi dei campi)
     */
    public static final String VISTA_IN_MENU = "vistainmenutavoli";

    /**
     * codifica dei set di campi per la scheda
     * (oltre a quelli standard della superclasse)
     * (ATTENZIONE - il nome del Set DEVE essere diverso dai nomi dei campi)
     */
    public static final String SET_TAVOLO_CAMERA = "tavolocamera";

    /**
     * codifica dei navigatori
     * (oltre a quello standard)
     */
    public static final String NAV_RMT_RMO = "rmt_nav_rmt_rmo";

    public static final String NAV_IN_MENU = "rmt_nav_in_menu";

    /**
     * Codifica delle azioni specifiche di questo package
     */
    public static final String AZIONE_CREA_TAVOLI = "azionecreatavoli";

    public static final String AZIONE_CERCA_TAVOLO = "azionecercatavolo";

    public static final String AZIONE_STAMPA_COMANDE = "azionestampacomande";



   /** Classe interna Enumerazione.
    * <p/>
    * Codifica dei nomi dei campi (per il modello e per la scheda) <br>
    *
    * @see it.algos.base.wrapper.Campi
    */
   public enum Cam implements Campi {

       menu("linkmenu", "", "", "", true),
       tavolo("linktavolo", "", "", "", true),
       camera("camera", "", "", "", false),
       coperti("coperti", "", "", "", false),
       comandato("comandato", "", "", "", false),;

       /**
        * nome interno del campo usato nel database.
        * <p/>
        * default il nome della Enumeration <br>
        */
       private String nome;

       /**
        * titolo della colonna della lista.
        * <p/>
        * default il nome del campo <br>
        */
       private String titoloColonna;

       /**
        * titolo della etichetta in scheda.
        * <p/>
        * default il nome del campo <br>
        */
       private String etichettaScheda;

       /**
        * legenda del campo nella scheda.
        * <p/>
        * nessun default <br>
        */
       private String legenda;

       /**
        * flag per la visibilità nella lista.
        * <p/>
        * nessun default <br>
        */
       private boolean visibileLista;


       /**
        * Costruttore completo con parametri.
        *
        * @param nome interno del campo usato nel database
        * @param titoloColonna della lista
        * @param etichettaScheda visibile
        * @param legenda del campo nella scheda
        * @param visibileLista flag per la visibilità nella lista
        */
       Cam(String nome,
           String titoloColonna,
           String etichettaScheda,
           String legenda,
           boolean visibileLista) {
           try { // prova ad eseguire il codice
               /* regola le variabili di istanza coi parametri */
               this.setNome(nome);
               this.setTitoloColonna(titoloColonna);
               this.setEtichettaScheda(etichettaScheda);
               this.setLegenda(legenda);
               this.setVisibileLista(visibileLista);

               /* controllo automatico che ci sia il nome interno */
               if (Lib.Testo.isVuota(nome)) {
                   this.setNome(this.toString());
               }// fine del blocco if

           } catch (Exception unErrore) { // intercetta l'errore
               Errore.crea(unErrore);
           }// fine del blocco try-catch
       }


       /**
        * Costruttore completo con parametri.
        * <p/>
        * Crea un elemento da un elemento di un'altra Enumerazione
        *
        * @param unCampo oggetto di un'altra Enum Campi
        */
       Cam(Campi unCampo) {
           this(unCampo.get(),
                   unCampo.getTitoloColonna(),
                   unCampo.getEtichettaScheda(),
                   unCampo.getLegenda(),
                   unCampo.isVisibileLista());
       }


       public String get() {
           return nome;
       }


       public String getNomeCompleto() {
           return NOME_MODULO + "." + nome;
       }


       public String getNome() {
           return nome;
       }


       private void setNome(String nome) {
           this.nome = nome;
       }


       public String getTitoloColonna() {
           return titoloColonna;
       }


       private void setTitoloColonna(String titoloColonna) {
           this.titoloColonna = titoloColonna;
       }


       public String getEtichettaScheda() {
           return etichettaScheda;
       }


       private void setEtichettaScheda(String etichettaScheda) {
           this.etichettaScheda = etichettaScheda;
       }


       public String getLegenda() {
           return legenda;
       }


       private void setLegenda(String legenda) {
           this.legenda = legenda;
       }


       public boolean isVisibileLista() {
           return visibileLista;
       }


       private void setVisibileLista(boolean visibileLista) {
           this.visibileLista = visibileLista;
       }
   }// fine della classe

}// fine della interfaccia
