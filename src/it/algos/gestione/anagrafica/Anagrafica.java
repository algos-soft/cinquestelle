/**
 * Title:     Anagrafica
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      8-feb-2004
 */
package it.algos.gestione.anagrafica;

import it.algos.base.errore.Errore;
import it.algos.base.interfaccia.Generale;
import it.algos.base.libreria.Lib;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.base.wrapper.SetCampi;
import it.algos.base.wrapper.Viste;

import java.util.ArrayList;

/**
 * Interfaccia Anagrafica.
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
 * @version 1.0    / 8-feb-2004 ore 20.24.07
 */
public interface Anagrafica extends Generale {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "Anagrafica";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "anagrafica";

    /**
     * Codifica del voce della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = NOME_MODULO;

    /**
     * Codifica del voce del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = TITOLO_FINESTRA;

    /**
     * Codifica del voce della tabella come appare nella altre Liste
     * (usato nel Modello)
     */
    public static final String TITOLO_TABELLA = NOME_TAVOLA;

    /**
     * codifica dei set di campi per la scheda
     * (oltre a quelli standard della superclasse)
     * (ATTENZIONE - il nome del Set DEVE essere diverso dai nomi dei campi)
     * <p/>
     * Codifica dei nomi dei campi (per il modello e per la scheda)
     * (oltre a quelli standard della superclasse)
     */
    public static final String SET_GENERALE = "setgenerale";

    /**
     * Codifica dei nomi dei Moduli (interni e come appaiono nel menu)
     */
    public static final String MODULO_TITOLO = "Titolo";

    public static final String MODULO_CLIENTE = "Cliente";


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica dei nomi dei campi (per il modello e per la scheda) <br>
     *
     * @see it.algos.base.wrapper.Campi
     */
    public enum Cam implements Campi {

        soggetto("soggetto", "soggetto", "soggetto", "", true),
        privatosocieta("privatosocieta", "p/s", "tipologia", "", true),
        categoria("linkcategoria", "categoria", "categoria", "", false),
        titolo("linktitolo", "titolo", "titolo", "", false),
        nome("nome", "nome", "nome", "", false),
        cognome("cognome", "cognome", "cognome", "", false),
        sesso("sesso", "sesso", "sesso", "", false),
        dataNascita("datanascita", "data di nascita", "data di nascita", "", false),
        codFiscale("codicefiscale", "c.f.", "codice fiscale", "", false),
        ragSociale("ragionesociale", "ragione sociale", "ragione sociale", "", false),
        partitaIva("partitaiva", "p.i.", "partita iva", "", false),
        riferimento("riferimento", "riferimento", "riferimento", "", false),
        indirizzi("indirizzo", "indirizzo", "indirizzo", "", false),
        contibanca("conti", "conti", "conti bancari", "", false),
        telefono("telefono", "telefono", "telefono", "", true),
        cellulare("cellulare", "cell.", "cellulare", "", false),
        fax("fax", "fax", "fax", "", false),
        email("email", "email", "e-mail", "", true),
        contatti("", "", "altri", "", false),
        consensoPrivacy("privacy", "consenso privacy", "consenso dati personali", "", false),
        dataPrivacy("dataprivacy", "data consenso", "data consenso", "", false),
        note(ModelloAlgos.NOME_CAMPO_NOTE, "note", "note", "", false),
        pagamento("linkpagamento", "pagamento", "pagamento", "", false),
        iva("linkiva", "iva", "iva", "", false),
        bancaPropria("linkbancapropria", "banca propria", "banca propria", "", false),
        bancaEsterna("linkbancaesterna", "banca esterna", "banca esterna", "", false),
        applicaRivalsa("applicaRivalsa", "riv. INPS", "applicazione rivalsa INPS", "", false),
        applicaRA("applicaRA", "appl. r.a.", "applicazione r.a.", "", false),
        percRA("percRA", "% r.a.", "% r.a", "", false);

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
     * Codifica dei set di campi per la scheda.
     * (oltre a quelli standard della superclasse)
     * (ATTENZIONE - il nome del Set DEVE essere diverso dai nomi dei campi)
     */
    public enum Set implements SetCampi {

        consenso(),
        privato(),
        societa(),
        telcellmail(),
        telcellfaxmail()
    }// fine della Enumeration Set


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica delle viste (espanse e clonate) per la Lista
     * (oltre a quelle standard della superclasse)
     * (ATTENZIONE - il nome della Vista DEVE essere diverso dai nomi dei campi)
     * (ATTENZIONE - il nome della Vista DEVE essere tutto minuscolo)
     */
    public enum Vis implements Viste {

        standard(),
        soggetto(),;


        @Override public String toString() {
            return super.toString() + vista;
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
    public enum Estratto implements Estratti {

        descrizione(EstrattoBase.Tipo.stringa),
        ragioneSociale(EstrattoBase.Tipo.stringa),
        nomeCognome(EstrattoBase.Tipo.stringa),
        etichetta(EstrattoBase.Tipo.stringa),;


        /**
         * tipo di estratto utilizzato
         */
        private EstrattoBase.Tipo tipoEstratto;

        /**
         * modulo di riferimento
         */
        private static String nomeModulo = NOME_MODULO;


        /**
         * Costruttore completo con parametri.
         *
         * @param tipoEstratto utilizzato
         */
        Estratto(EstrattoBase.Tipo tipoEstratto) {
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
     * Categorie di anagrafiche
     */
    public enum Categorie {

        cliente("cliente"),
        fornitore("fornitore"),
        agente("agente");

        /**
         * voce da utilizzare
         */
        private String titolo;


        /**
         * Costruttore completo con parametri.
         *
         * @param titolo utilizzato nei popup
         */
        Categorie(String titolo) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setTitolo(titolo);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public String getTitolo() {
            return titolo;
        }


        private void setTitolo(String titolo) {
            this.titolo = titolo;
        }


        public static String getStringaValori() {
            /* variabili e costanti locali di lavoro */
            String stringa = "";

            try { // prova ad eseguire il codice
                for (Categorie tipo : Categorie.values()) {
                    stringa += "," + tipo.getTitolo();
                } // fine del ciclo for-each

                if (Lib.Testo.isValida(stringa)) {
                    stringa = stringa.substring(1);
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return stringa;
        }


    }// fine della classe


    /**
     * Classe interna Enumerazione
     * <p/>
     * Tipi di anagrafica
     */
    public enum Tipi {

        privato(1, "privato"),
        societa(2, "società");

        /**
         * codice del tipo di anagrafica
         */
        private int codice;

        /**
         * descrizione del tipo di anagrafica
         */
        private String descrizione;


        /**
         * Costruttore completo con parametri.
         *
         * @param codice del tipo
         * @param descrizione del tipo
         */
        Tipi(int codice, String descrizione) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setCodice(codice);
                this.setDescrizione(descrizione);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public static ArrayList<String> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<String> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<String>();

                /* traverso tutta la collezione */
                for (Tipi tipo : Tipi.values()) {
                    lista.add(tipo.getDescrizione());
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
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


        /**
         * Ritorna un tipo dal codice
         * <p/>
         *
         * @param codice del tipo
         *
         * @return il tipo corrispondente
         */
        public static Tipi getTipo(int codice) {
            /* variabili e costanti locali di lavoro */
            Anagrafica.Tipi tipo = null;

            try { // prova ad eseguire il codice
                for (Anagrafica.Tipi unTipo : Anagrafica.Tipi.values()) {
                    if (unTipo.getCodice() == codice) {
                        tipo = unTipo;
                        break;
                    }// fine del blocco if
                }
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return tipo;
        }

    }// fine della classe


    /**
     * Classe interna Enumerazione
     * <p/>
     * Elenco di possibili valori per le opzioni
     * "Applica rivalsa" e "Applica r.a."
     */
    public enum ValoriOpzione {

        standard("usa default"),
        si("applica"),
        no("non applicare"),;

        /**
         * descrizione del tipo di anagrafica
         */
        private String descrizione;


        /**
         * Costruttore completo con parametri.
         *
         * @param descrizione dell'elemento
         */
        ValoriOpzione(String descrizione) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setDescrizione(descrizione);
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


        /**
         * Ritorna un elemento della enum dal valore
         * <p/>
         *
         * @param valore dell'elemento
         *
         * @return l'elemento corrispondente
         */
        public static ValoriOpzione getElemento(int valore) {
            /* variabili e costanti locali di lavoro */
            ValoriOpzione elem = null;
            int pos;

            try { // prova ad eseguire il codice
                pos = valore - 1;
                for (ValoriOpzione unElem : values()) {
                    if (unElem.ordinal() == pos) {
                        elem = unElem;
                        break;
                    }// fine del blocco if
                }
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return elem;
        }


        /**
         * Ritorna la lista degli elementi della enum
         * <p/>
         *
         * @return la lista degli elementi
         */
        public static ArrayList<ValoriOpzione> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<ValoriOpzione> lista = new ArrayList<ValoriOpzione>();

            try { // prova ad eseguire il codice
                for (ValoriOpzione unElem : values()) {
                    lista.add(unElem);
                }
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        /**
         * Ritorna l'elemento di default
         * <p/>
         *
         * @return l'elemento di default
         */
        public static ValoriOpzione getDefault() {
            return standard;
        }


        /**
         * Ritorna il valore dell'elemento di default
         * <p/>
         *
         * @return il valore dell'elemento di default
         */
        public static int getValoreDefault() {
            return getDefault().ordinal() + 1;
        }


        public String toString() {
            return this.getDescrizione();
        }

    }// fine della classe


}// fine della interfaccia
