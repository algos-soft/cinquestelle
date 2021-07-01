/**
 * Title:     ClienteAlbergo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      3-mag-2004
 */
package it.algos.albergo.prenotazione.periodo;

import it.algos.albergo.listino.Listino;
import it.algos.albergo.presenza.Presenza;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Viste;

import java.util.ArrayList;

/**
 * Mantiene tutte le costanti pubbliche e le codifiche di questo package.
 * <br>
 * Questa interfaccia: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> </li>
 * <li> Fornisce un interfaccia unificata per tutte le chiamate alle classi di
 * questo package </li>
 * <li> Estende l'interfaccia <i>Cliente</i> </li>
 * <li> Mantiene il percorso della cartella </li>
 * <li> Mantiene la codifica delle viste (per la lista) </li>
 * <li> Mantiene la codifica dei set di campi (per la scheda) </li>
 * <li> Mantiene la codifica dei campi (per il modello e per la scheda) </li>
 * <li> Mantiene la codifica degli estratti (per gli altri moduli) </li>
 * <li> Mantiene la codifica dei moduli (per gli altri package) </li>
 * <li> Mantiene altre costanti utilizzate in tutto il package </li>
 * <li> Dichiara i metodi astratti utilizzati in tutto il package </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 3-mag-2004 ore 7.57.03
 */
public interface Periodo {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "Periodo";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "periodi";

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = NOME_MODULO;

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Periodi";

    /**
     * Codifica del titolo della tabella come appare nella altre Liste
     * (usato nel Modello)
     */
    public static final String TITOLO_TABELLA = NOME_TAVOLA;


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica dei nomi dei campi (per il modello e per la scheda) <br>
     *
     * @see it.algos.base.wrapper.Campi
     */
    public enum Cam implements Campi {

        prenotazione("linkprenotazione", "", "", "", false),
        arrivoPrevisto("arrivoPrevisto", "dal", "arrivo", "", true),
        arrivoEffettivo("arrivoEffettivo", "arrivato", "arrivo effettivo", "", true),
        partenzaPrevista("partenzaPrevista", "al", "partenza", "", true),
        partenzaEffettiva("partenzaEffettiva", "partito", "partenza effettiva", "", true),
        dataFineAddebiti("datafineaddebiti", "", "", "", false),
        camera("camera", "", "", "", true),
        preparazione("linkcomposizione", "prep", "preparazione", "", true),
        noteprep("noteprep", "note prep", "note preparazione", "", false),
        adulti("adulti", "", "", "", false),
        bambini("bambini", "", "bamb", "", false),
        persone("persone", "pers", "pers", "", true),
        trattamento("pensione", "trat", "trattamento", "", true),
        pasto("pasto", "", "", "", true),
        giorni("giorni", "gg", "", "", true),
        addebiti("", "", "", "", true),
        serviziStandard("servizistandard", "", "", "", true),
        serviziSpecifici("servizispecifici", "", "", "", true),
        arrivoCon("arrivoCon", "arrivo con", "arrivo con", "", false),
        partenzaCon("partenzaCon", "partenza con", "partenza con", "", false),
        arrivoConfermato("arrConfermato", "arrivo confermato", "arrivo confermato", "", false),
        partenzaConfermata("partConfermata", "partenza confermata", "partenza confermata", "", false),
        causaleArrivo("causalearrivo", "arrivo", "", "", true),
        causalePartenza("causalepartenza", "partenza", "", "", true),
        linkProvenienza("linkprovenienza", "entrata", "", "", true),
        linkDestinazione("linkdestinazione", "uscita", "", "", true),
        arrivato("arrivato", "in", "arrivato", "", true),
        partito("chiuso", "out", "partito", "", true),
        presenzeAdulti("presenzeAdulti", "pres A", "", "", false),
        presenzeBambini("presenzeBambini", "pres B", "", "", false),
        presenze("presenze", "pres", "pres", "", true),
        valore("valore", "", "", "", false),
        note("note", "", "note generali", "", false),
        info("info", "", "", "", false),
        risorse("", "", "", "", true);


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
         * @param titoloColonna titolo della colonna della lista
         * @param etichettaScheda titolo dell'etichetta nella scheda
         * @param legenda legenda del campo nella scheda
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
                new Errore(unErrore);
            }// fine del blocco try-catch
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


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica delle viste (espanse e clonate) per la Lista
     * (oltre a quelle standard della superclasse)
     * (ATTENZIONE - il nome della Vista DEVE essere diverso dai nomi dei campi)
     * (ATTENZIONE - il nome della Vista DEVE essere tutto minuscolo)
     */
    public enum Vis implements Viste {

        prenotazione(),
        arrivi(),
        partenze();


        @Override
        public String toString() {
            return super.toString() + vista;
        }
    }// fine della classe


    /**
     * Codifica dei navigatori del modulo.
     */
    public enum Nav {

        prenotazione();


        public String get() {
            return toString();
        }

    }// fine della classe


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Causali di ingresso (arrivo) e uscita (partenza) per il periodo
     */
    enum CausaleAP implements Campo.ElementiCombo {

        normale("normale", 1),
        cambio("cambio", 2);


        int codice;

        String descrizione;


        /**
         * Costruttore completo con parametri. <br>
         *
         * @param descrizione della causale
         * @param codice per il database
         */
        CausaleAP(String descrizione, int codice) {
            /* regola le variabili di istanza coi parametri */
            this.setDescrizione(descrizione);
            this.setCodice(codice);
        }// fine del metodo costruttore completo


        /**
         * Ritorna la causale dal codice
         * <p/>
         *
         * @param codice il codice di causale
         *
         * @return la causale corrispondente
         */
        public static CausaleAP getCausale(int codice) {
            /* variabili e costanti locali di lavoro */
            CausaleAP[] causali;
            CausaleAP causale = null;

            try { // prova ad eseguire il codice
                causali = CausaleAP.values();
                for (CausaleAP caus : causali) {
                    if (caus.getCodice() == codice) {
                        causale = caus;
                        break;
                    }// fine del blocco if
                }
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return causale;
        }


        public int getCodice() {
            return codice;
        }


        private void setCodice(int codice) {
            this.codice = codice;
        }


        private String getDescrizione() {
            return descrizione;
        }


        private void setDescrizione(String descrizione) {
            this.descrizione = descrizione;
        }


        public String toString() {
            return this.getDescrizione();
        }

    }


    /**
     * Classe interna Enumerazione.
     */
    public enum TipoPersona {

        persona(),
        adulto(),
        bambino()

    }// fine della classe



    /**
     * Codifica dei tipi di arrivo e partenza.
     * <p/>
     */
    enum TipiAP implements Campo.ElementiCombo {

        breakfast(1, "breakfast", Presenza.TipiPasto.breakfast,true, true, "bkfast"),
        lunch(2, "lunch", Presenza.TipiPasto.lunch, true, true, "lunch"),
        dinner(3, "dinner", Presenza.TipiPasto.dinner, true, true, "dinner"),
        room(4, "room", null, true, true,"room");

        private int codice;  // codice per il database

        private String descrizione;  // descrizione del tipo di arrivo o partenza

        private Presenza.TipiPasto pasto;   // eventuale tipo di pasto corrispondente

        private boolean arrivo;  // applicabile all'Arrivo

        private boolean partenza;  // applicabile alla Partenza

        private String sigla;  // sigla breve


        /**
         * Costruttore completo con parametri.
         *
         * @param codice per il database
         * @param descrizione dell'elemento
         * @param pasto eventuale tipo di pasto corrispondente
         * @param arrivo true se applicabile agli arrivi
         * @param partenza true se applicabile alle partenze
         */
        TipiAP(int codice, String descrizione, Presenza.TipiPasto pasto, boolean arrivo, boolean partenza, String sigla) {
            try { // prova ad eseguire il codice

                /* regola le variabili di istanza coi parametri */
                this.setCodice(codice);
                this.setDescrizione(descrizione);
                this.setPasto(pasto);
                this.setArrivo(arrivo);
                this.setPartenza(partenza);
                this.setSigla(sigla);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Ritorna la descrizione dal codice del database
         * <p/>
         *
         * @param codice il codice del database
         *
         * @return la descrizione
         */
        public static String getDescrizione(int codice) {
            /* variabili e costanti locali di lavoro */
            String descrizione="";

            try { // prova ad eseguire il codice
                TipiAP elem = TipiAP.get(codice);
                if (elem!=null) {
                    descrizione = elem.getDescrizione();
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return descrizione;
        }

        /**
         * Ritorna l'elemento dal codice di database
         * <p/>
         *
         * @param codice il codice di database
         *
         * @return il corrispondente elemento della Enum
         */
        public static TipiAP get(int codice) {
            /* variabili e costanti locali di lavoro */
            TipiAP[] elementi;
            TipiAP elemento = null;

            try { // prova ad eseguire il codice
                elementi = TipiAP.values();
                for (TipiAP elem : elementi) {
                    if (elem.getCodice() == codice) {
                        elemento = elem;
                        break;
                    }// fine del blocco if
                }
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return elemento;
        }


        /**
         * Ritorna tutti gli elementi validi per l'arrivo
         * <p/>
         *
         * @return l'elenco degli elementi validi per l'arrivo
         */
        public static TipiAP[] getElementiArrivo() {
            return getElementiArrivoPartenza(true);
        }

        /**
         * Ritorna tutti gli elementi validi per la partenza
         * <p/>
         *
         * @return l'elenco degli elementi validi per la partenza
         */
        public static TipiAP[] getElementiPartenza() {
            return getElementiArrivoPartenza(false);
        }



        /**
         * Ritorna tutti gli elementi validi per l'arrivo o la partenza
         * <p/>
         * @param arrivo true per gli elementi di arrivo false per gli elementi di partenza
         * @return l'elenco degli elementi validi per l'arrivo o la partenza
         */
        private static TipiAP[] getElementiArrivoPartenza(boolean arrivo) {
            /* variabili e costanti locali di lavoro */
            TipiAP[] elementi=null;

            try { // prova ad eseguire il codice

                TipiAP[] tutti = TipiAP.values();

                ArrayList<TipiAP> lista = new ArrayList<TipiAP>();
                for (TipiAP elem : tutti) {

                    if (arrivo) {
                        if (elem.isArrivo()) {
                            lista.add(elem);
                        }// fine del blocco if
                    } else {
                        if (elem.isPartenza()) {
                            lista.add(elem);
                        }// fine del blocco if
                    }// fine del blocco if-else

                }

                elementi = new TipiAP[lista.size()];
                for (int k = 0; k < lista.size(); k++) {
                    elementi[k]= lista.get(k);
                } // fine del ciclo for

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return elementi;
        }


        /**
         * Ritorna il tipo di arrivo di default per un certo tipo di trattamento
         * <p/>
         * @param trattamento il tipo di trattamento
         * @return il tipo di arrivo di default
         */
        public static TipiAP getTipoArrivoDefault(Listino.PensioniPeriodo trattamento) {
            /* variabili e costanti locali di lavoro */
            TipiAP elemento=null;

            try { // prova ad eseguire il codice
                if (trattamento!=null) {
                    switch (trattamento) {
                        case pernottamento:
                            elemento= room;
                            break;
                        case mezzaPensione:
                            elemento= dinner;
                            break;
                        case pensioneCompleta:
                            elemento= lunch;
                            break;
                        default : // caso non definito
                            elemento= lunch;
                            break;
                    } // fine del blocco switch
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return elemento;
        }


        /**
         * Ritorna il tipo di partenza di default per un certo tipo di trattamento
         * <p/>
         * @param trattamento il tipo di trattamento
         * @return il tipo di partenza di default
         */
        public static TipiAP getTipoPartenzaDefault(Listino.PensioniPeriodo trattamento) {
            /* variabili e costanti locali di lavoro */
            TipiAP elemento=null;

            try { // prova ad eseguire il codice
                if (trattamento!=null) {
                    switch (trattamento) {
                        case pernottamento:
                            elemento= breakfast;
                            break;
                        case mezzaPensione:
                            elemento= breakfast;
                            break;
                        case pensioneCompleta:
                            elemento= breakfast;
                            break;
                        default : // caso non definito
                            elemento= breakfast;
                            break;
                    } // fine del blocco switch
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return elemento;
        }


        public int getCodice() {
            return codice;
        }


        private void setCodice(int codice) {
            this.codice = codice;
        }


        public String getDescrizione() {
            return descrizione;
        }


        private void setDescrizione(String descrizione) {
            this.descrizione = descrizione;
        }


        public Presenza.TipiPasto getPasto() {
            return pasto;
        }


        private void setPasto(Presenza.TipiPasto pasto) {
            this.pasto = pasto;
        }


        private boolean isArrivo() {
            return arrivo;
        }


        private void setArrivo(boolean arrivo) {
            this.arrivo = arrivo;
        }


        private boolean isPartenza() {
            return partenza;
        }


        private void setPartenza(boolean partenza) {
            this.partenza = partenza;
        }


        public String getSigla() {
            return sigla;
        }


        private void setSigla(String sigla) {
            this.sigla = sigla;
        }


        public String toString() {
            return this.getDescrizione();
        }
    }


}// fine della interfaccia
