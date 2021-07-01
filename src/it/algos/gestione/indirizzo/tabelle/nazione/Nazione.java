/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.gestione.indirizzo.tabelle.nazione;

import it.algos.base.errore.Errore;
import it.algos.base.interfaccia.Generale;
import it.algos.base.libreria.Lib;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.base.wrapper.Viste;

/**
 * Interfaccia Nazione.
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
public interface Nazione extends Generale {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "Nazione";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "nazione";

    /**
     * Codifica del voce della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Nazione";

    /**
     * Codifica del voce del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Nazioni";

    /**
     * Codifica del voce della tabella come appare nella altre Liste
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

        nazione("nazione", "", "nome", "nome corrente della nazione", true),
        nazioneCompleto("nazioneCompleto",
                "",
                "nome",
                "nome completo ufficiale della nazione",
                false),
        sigla2("", "sigla2", "ISO 3166-1 alpha-2", "usi diversi", true),
        sigla3("", "sigla3", "ISO 3166-1 alpha-3", "passaporti", false),
        tld("", "", "internet", "dominio di primo livello", true),
        capitale("capitale", "", "", "", true),
        checkEuropa("", "EU", "europa", "paese membro dell'unione europea", true),
        linkValuta("", "valuta", "valuta", "moneta legale del paese", true),
        divisioniUno("", "", "", "nome delle divisioni di 1° livello", false),
        divisioniDue("", "", "", "nome delle divisioni di 2° livello", false),
        offsetGMT("offsetGMT",
                "GMT",
                "offset GMT",
                "offset medio GMT rispetto al meridiano di Greenwich",
                true),
        telefono("telefono", "", "", "prefisso telefonico internazionale", false),
        festivo("festivo", "festa", "festa nazionale", "giornata di festa nazionale", false),
        subRegioni("", "", "", "suddivisioni amministrative di 1° livello", false);

        /**
         * nome interno del campo usato nel database.
         * <p/>
         * default il nome della Enumeration <br>
         */
        private String nome;

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

        sigla(),
        naz;


        @Override
        public String toString() {
            return super.toString() + vista;
        }
    }// fine della classe


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica dei navigatori per il modulo <br>
     * (oltre a quelle standard della superclasse)
     */
    public enum Nav {

        naz()
    }// fine della classe


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica degli estratti pubblici disponibili all'esterno del modulo
     * (ATTENZIONE - gli oggetti sono di classe Estratto)
     *
     * @see EstrattoBase
     * @see EstrattoBase.Tipo
     * @see Estratti
     */
    public enum Est implements it.algos.base.wrapper.Estratti {

        sigla(EstrattoBase.Tipo.stringa),
        descrizione(EstrattoBase.Tipo.stringa),
        sigladescrizione(EstrattoBase.Tipo.stringa),
        composto(EstrattoBase.Tipo.pannello); //sigla + nazione


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
     * Classe interna Enumerazione.
     */
    public enum Europa {

        austria("AT", "", false),
        belgio("BE", "", true),
        bulgaria("BG", "", false),
        cipro("CY", "", false),
        danimarca("DK", "", false),
        estonia("EE", "", false),
        finlandia("FI", "", false),
        francia("FR", "", true),
        germania("DE", "", true),
        grecia("GR", "", false),
        irlanda("IE", "", false),
        italia("IT", "", true),
        lettonia("LV", "", false),
        lituania("LT", "", false),
        lussemburgo("LU", "", true),
        malta("MT", "", false),
        olanda("NL", "", true),
        polonia("PL", "", false),
        portogallo("PT", "", false),
        inghilterra("GB", "", false),
        cechia("CZ", "", false),
        romania("RO", "", false),
        slovacchia("SK", "", false),
        slovenia("SI", "", false),
        spagna("ES", "", false),
        svezia("SE", "", false),
        ungheria("HU", "", false);

        private String sigla;

        private String nome;

        private boolean fondatore;


        /**
         * Costruttore completo con parametri.
         *
         * @param sigla ISO 3166-1 alpha-2
         * @param nome corrente normale
         * @param fondatore uno dei primi 6
         */
        Europa(String sigla, String nome, boolean fondatore) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setSigla(sigla);
                this.setNome(nome);
                this.setFondatore(fondatore);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public String getSigla() {
            return sigla;
        }


        private void setSigla(String sigla) {
            this.sigla = sigla;
        }


        public String getNome() {
            return nome;
        }


        private void setNome(String nome) {
            this.nome = nome;
        }


        public boolean isFondatore() {
            return fondatore;
        }


        private void setFondatore(boolean fondatore) {
            this.fondatore = fondatore;
        }
    }// fine della classe


} // fine della classe
