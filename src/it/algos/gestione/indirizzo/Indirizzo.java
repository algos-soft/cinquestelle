/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.gestione.indirizzo;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.interfaccia.Generale;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.base.wrapper.Navigatori;
import it.algos.base.wrapper.Viste;

import java.util.ArrayList;

/**
 * Interfaccia Indirizzo.
 * </p>
 * Questa interfaccia: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> </li>
 * <li> Fornisce un interfaccia unificata per un insieme di
 * interfacce presenti nel package</li>
 * <li> Mantiene la codifica del nome-chiave interno del modulo (usato nel Modello) </li>
 * <li> Mantiene la codifica della tavola di archivio collegata (usato nel Modello) </li>
 * <li> Mantiene la codifica del voce della finestra (usato nel Navigatore) </li>
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
 * @version 1.0 / 3-4-05
 */
public interface Indirizzo extends Generale {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "Indirizzo";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "indirizzo";

    /**
     * Codifica del voce della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Indirizzi";

    /**
     * Codifica del voce del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Indirizzi";

    /**
     * Codifica del voce della tabella come appare nella altre Liste
     * (usato nel Modello)
     */
    public static final String TITOLO_TABELLA = NOME_TAVOLA;


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica dei nomi dei campi<br>
     *
     * @see it.algos.base.wrapper.Campi
     */
    public enum Cam implements Campi {

        anagrafica("linkanagrafica", "", "soggetto", "", false),
        via("linkvia", "via", "via", "", false),
        indirizzo("", "", "", "", false),
        indirizzo2("indirizzo2", "indirizzo 2", "indirizzo 2", "", false),
        numeroCivico("numero", "n. civico", "n. civico", "", false),
        cap("", "", "", "codice avviamento postale", false),
        citta("linkcitta", "località", "località", "", false),
        tipo("", "", "tipologia", "", false),
        note("", "", "", "", false),;

        /**
         * nome interno del campo usato nel database.
         * <p/>
         * default il nome della Enumeration <br>
         */
        private String unNome;

        /**
         * voce della colonna della lista.
         * <p/>
         * default il nome del campo <br>
         */
        private String titoloColonna;

        /**
         * voce della etichetta in scheda.
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
            return unNome;
        }


        public String getNomeCompleto() {
            return NOME_MODULO + "." + unNome;
        }


        public String getNome() {
            return unNome;
        }


        private void setNome(String nome) {
            this.unNome = nome;
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

        indirizziInAnag();  // per lista indirizzi nella scheda anagrafica


        @Override
        public String toString() {
            return super.toString() + vista;
        }
    }// fine della classe


    /**
     * Codifica dei Navigatori del modulo.
     */
    public enum Nav implements Navigatori {

        indirizziAnagrafica(); // navigatore indirizzi usato nella scheda anagrafica


        public String get() {
            return toString();
        }
    }// fine della classe


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica degli estratti pubblici disponibili all'esterno del modulo
     * (ATTENZIONE - gli oggetti sono di classe Estratto)
     *
     * @see it.algos.base.wrapper.EstrattoBase
     * @see it.algos.base.wrapper.EstrattoBase.Tipo
     * @see it.algos.base.wrapper.Estratti
     */
    public enum Est implements Estratti {

        indirizzo(EstrattoBase.Tipo.pannello),
        etichettaDocumento(EstrattoBase.Tipo.stringa),;

        /**
         * modulo di riferimento
         */
        private static String nomeModulo = NOME_MODULO;

        /**
         * tipo di estratto utilizzato
         */
        private EstrattoBase.Tipo tipoEstratto;


        /**
         * Costruttore completo con parametri.
         *
         * @param tipoEstratto utilizzato
         */
        Est(EstrattoBase.Tipo tipoEstratto) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setTipo(tipoEstratto);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public EstrattoBase.Tipo getTipo() {
            return tipoEstratto;
        }


        private void setTipo(EstrattoBase.Tipo tipoEstratto) {
            this.tipoEstratto = tipoEstratto;
        }


        public String getNomeModulo() {
            return nomeModulo;
        }

    }// fine della classe


    /**
     * Classe interna Enumerazione
     * <p/>
     * Codifica dei tipi di sede di un indirizzo
     */
    public enum TipiSede {

        legale("Sede legale", "Leg", false, true),
        operativa("Sede operativa", "Op", false, false),
        consegna("Luogo di consegna", "Con", false, false),
        residenza("Residenza", "Res", true, true),
        domicilio("Domicilio", "Dom", true, false),;

        /**
         * descrizione del tipo di sede
         */
        private String descrizione;

        /**
         * descrizione breve del tipo di sede (per le liste)
         */
        private String descBreve;

        /**
         * true se pertinente solo a Privato, false se pertinente solo a Società
         */
        private boolean privato;

        /**
         * true se si tratta di sede principale per il proprio tipo
         * (una sola per privato, una sola per società)
         */
        private boolean principale;


        /**
         * Costruttore completo con parametri.
         *
         * @param descrizione del tipo di sede
         * @param descBreve del tipo di sede
         * @param privato true se pertinente solo a Privato, false se pertinente solo a Società
         * @param principale true se sede principale per il proprio tipo (privato, società)
         */
        TipiSede(String descrizione, String descBreve, boolean privato, boolean principale) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setDescrizione(descrizione);
                this.setDescBreve(descBreve);
                this.setPrivato(privato);
                this.setPrincipale(principale);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public String getDescrizione() {
            return descrizione;
        }


        private void setDescrizione(String descrizione) {
            this.descrizione = descrizione;
        }


        public String getDescBreve() {
            return descBreve;
        }


        private void setDescBreve(String descBreve) {
            this.descBreve = descBreve;
        }


        private boolean isPrivato() {
            return privato;
        }


        private void setPrivato(boolean privato) {
            this.privato = privato;
        }


        private boolean isPrincipale() {
            return principale;
        }


        private void setPrincipale(boolean principale) {
            this.principale = principale;
        }


        /**
         * Ritorna la lista di tutte le descrizioni.
         * <p/>
         *
         * @return la lista descrizioni
         */
        public static ArrayList<String> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<String> lista = new ArrayList<String>();

            try {    // prova ad eseguire il codice
                for (TipiSede tipo : values()) {
                    lista.add(tipo.getDescrizione());
                }
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        /**
         * Ritorna il tipo di sede principale per privato o società.
         * <p/>
         *
         * @param privato true per privato false per società
         *
         * @return il tipo di sede principale per il tipo di anagrafica specificato
         */
        public static TipiSede getPrincipale(boolean privato) {
            /* variabili e costanti locali di lavoro */
            TipiSede principale = null;

            try {    // prova ad eseguire il codice
                for (TipiSede tipo : values()) {
                    if (privato) {

                        if (tipo.isPrivato()) {
                            if (tipo.isPrincipale()) {
                                principale = tipo;
                                break;
                            }// fine del blocco if
                        }// fine del blocco if

                    } else {

                        if (!tipo.isPrivato()) {
                            if (tipo.isPrincipale()) {
                                principale = tipo;
                                break;
                            }// fine del blocco if
                        }// fine del blocco if

                    }// fine del blocco if-else
                }
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return principale;
        }


        /**
         * Controlla se questo tipo di sede è il principale
         * per il tipo di anagrafica dato.
         * <p/>
         *
         * @param privato true per privato false per società
         *
         * @return true se è il tipo principale
         */
        public boolean isPrincipale(boolean privato) {
            /* variabili e costanti locali di lavoro */
            boolean principale = false;
            TipiSede tipoPrincipale;

            try {    // prova ad eseguire il codice
                tipoPrincipale = getPrincipale(privato);
                if (this.equals(tipoPrincipale)) {
                    principale = true;
                }// fine del blocco if
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return principale;
        }


        /**
         * Ritorna un filtro per il database corrispondente al tipo.
         * <p/>
         * Il filtro isola i records dove il flag di questo tipo è acceso.
         * Basta che sia acceso il flag di questo tipo, se sono accesi
         * anche flag di altri tipi il record viene trovato comunque.
         *
         * @return il filtro
         */
        public Filtro getFiltro() {
            /* variabili e costanti locali di lavoro */
            Filtro filtro = null;
            Modulo modulo;
            Campo campo;
            Object valFiltro;

            try {    // prova ad eseguire il codice

                /* inserisce il valore nel campo del modello, si
                 * fa rendere il valore per il filtro e costruisce
                 * il filtro */
                modulo = IndirizzoModulo.get();
                campo = modulo.getCampo(Cam.tipo.get());
                campo.setValore(this.getValore());
                valFiltro = campo.getCampoDati().getValoreFiltro();
                filtro = FiltroFactory.crea(campo, Filtro.Op.COMINCIA, valFiltro);

            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return filtro;
        }


        /**
         * Ritorna il valore memoria corrispondente al tipo.
         * <p/>
         * Il valore ritornato accende solo il check di questo tipo.
         *
         * @return il valore per accendere il solo flag di questo tipo
         */
        public Object getValore() {
            /* variabili e costanti locali di lavoro */
            ArrayList<Boolean> lista = new ArrayList<Boolean>();
            boolean flag;

            try { // prova ad eseguire il codice
                for (TipiSede tipo : values()) {
                    flag = false;
                    if (tipo.equals(this)) {
                        flag = true;
                    }// fine del blocco if
                    lista.add(flag);
                }
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        /**
         * Data un valore costituito da una lista di booleani, ritorna la
         * lista dei tipi di sede corrispondenti agli elementi accesi.
         * <p/>
         *
         * @param valore la lista di valori booleani
         *
         * @return la lista di oggetti TipiSede corrispondente ai valori accesi
         */
        public static ArrayList<TipiSede> getTipiSede(ArrayList valore) {
            /* variabili e costanti locali di lavoro */
            ArrayList<TipiSede> listaSedi = new ArrayList<TipiSede>();
            boolean flag;
            int indice;
            Object oggetto;

            try { // prova ad eseguire il codice
                for (TipiSede tipo : values()) {
                    indice = tipo.ordinal();
                    if (valore.size() > indice) {
                        oggetto = valore.get(indice);
                        if (oggetto instanceof Boolean) {
                            flag = (Boolean)oggetto;
                            if (flag) {
                                listaSedi.add(tipo);
                            }// fine del blocco if
                        }// fine del blocco if

                    }// fine del blocco if
                }
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return listaSedi;
        }

    }// fine della classe

} // fine della classe
