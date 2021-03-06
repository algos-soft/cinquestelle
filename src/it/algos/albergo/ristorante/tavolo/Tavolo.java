/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 30 luglio 2003 alle 18.56
 */
package it.algos.albergo.ristorante.tavolo;

import it.algos.base.errore.Errore;
import it.algos.base.interfaccia.Generale;
import it.algos.base.libreria.Lib;
import it.algos.base.wrapper.Campi;

/**
 * Interfaccia per il package Tavolo.
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
 * <li> Mantiene la codifica delle viste (per la lista) </li>
 * <li> Mantiene la codifica dei set di campi (per la scheda) </li>
 * <li> Mantiene la codifica dei campi (per il modello e per la scheda) </li>
 * <li> Mantiene la codifica dei moduli (per gli altri package) </li>
 * <li> Mantiene altre costanti utilizzate in tutto il package </li>
 * <li> Dichiara (astratti) i metodi  utilizzati nel package che devono
 * essere visti all'esterno </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  30 luglio 2003 ore 18.56
 */
public interface Tavolo extends Generale {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "Tavolo";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "tavolo";

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Tavoli del ristorante";

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Tavoli del ristorante";

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
    public static final String VISTA_TAVOLO = "vistatavolo";

    public static final String VISTA_SALA_TAVOLO = "vistasalatavolo";


   /**
    * Classe interna Enumerazione.
    * <p/>
    * Codifica dei nomi dei campi (per il modello e per la scheda) <br>
    *
    * @see it.algos.base.wrapper.Campi
    */
   public enum Cam implements Campi {

       numtavolo("tavolo", "numtavolo", "numtavolo", "", false),
       sala("sala", "", "", "", true),
       numcoperti("coperti", "", "", "", true),
       occupato("occupato", "", "", "", true),
       nomecliente("cliente", "", "", "", true),
       mezzapensione("mezzapensione", "HB", "HB", "", true),
       pasto("pasto", "", "", "", true),
       navtavolo("navtavolo", "", "", "", true);

       /**
        * nome interno del campo usato nel database.
        * <p/>
        * default il nome minuscolo della Enumeration <br>
        */
       private String nomeInterno;

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
        * flag per la visibilit?? nella lista.
        * <p/>
        * nessun default <br>
        */
       private boolean visibileLista;


       /**
        * Costruttore completo con parametri.
        *
        * @param nomeInterno interno del campo usato nel database
        * @param titoloColonna titolo della colonna della lista
        * @param etichettaScheda titolo dell'etichetta nella scheda
        * @param legenda legenda del campo nella scheda
        * @param visibileLista flag per la visibilit?? nella lista
        */
       Cam(String nomeInterno,
           String titoloColonna,
           String etichettaScheda,
           String legenda,
           boolean visibileLista) {
           try { // prova ad eseguire il codice
               /* regola le variabili di istanza coi parametri */
               this.setNome(nomeInterno);
               this.setTitoloColonna(titoloColonna);
               this.setEtichettaScheda(etichettaScheda);
               this.setLegenda(legenda);
               this.setVisibileLista(visibileLista);

               /* controllo automatico che ci sia il nome interno */
               if (Lib.Testo.isVuota(nomeInterno)) {
                   this.setNome(this.toString());
               }// fine del blocco if
           } catch (Exception unErrore) { // intercetta l'errore
               new Errore(unErrore);
           }// fine del blocco try-catch
       }


       public String get() {
           return nomeInterno;
       }


       public String getNomeCompleto() {
           return NOME_MODULO + "." + nomeInterno;
       }


       public String getNome() {
           return nomeInterno;
       }


       private void setNome(String nomeInterno) {
           this.nomeInterno = nomeInterno;
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
   }// fine della Enumeration Cam


}// fine della interfaccia


