/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      31-mag-2006
 */
package it.algos.albergo.listino;

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

import java.util.ArrayList;

/**
 * Interfaccia Listino.
 * </p>
 * Questa interfaccia: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> </li>
 * <li> Fornisce un interfaccia unificata per un insieme di
 * interfacce presenti nel package</li>
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
 * @version 1.0 / 2 feb 2006
 */
public interface Listino extends Generale {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "Listino";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "listino";

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Listino prezzi";

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Listino prezzi";

    /**
     * Codifica del titolo della tabella come appare nella altre Liste
     * (usato nel Modello)
     */
    public static final String TITOLO_TABELLA = Listino.NOME_TAVOLA;


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica dei nomi dei campi (per il modello e per la scheda) <br>
     *
     * @see it.algos.base.wrapper.Campi
     */
    public enum Cam implements Campi {

        sigla(Generale.CAMPO_SIGLA, "sigla", "sigla", "ad uso interno", true),
        descrizione(Generale.CAMPO_DESCRIZIONE,
                "descrizione",
                "descrizione",
                "stampata sul conto cliente",
                true),
        ambitoPrezzo("ambito", "ambito", "ambito prezzo", "per suddivisione totali conto", true),
        tipoPrezzo("tipo", "tipo", "tipo prezzo", "per persona o per camera", true),
        modoPrezzo("modo", "modo", "modo prezzo", "fisso o variabile per periodo", true),
        giornaliero("giornaliero",
                "giorn",
                "giornaliero",
                "disponibile per addebiti gionalieri",
                true),
        disattivato("disattivato", "dis", "", "", true),
        sottoconto("linksottoconto",
                "linksottoconto",
                "linksottoconto",
                "sottoconto del piano dei conti",
                true),
        prezzo("prezzo", "prezzo", "prezzo", "", true),
        righe("righe", "", "periodi di validità", "", false);

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
         * @param etichettaScheda etichetta nella scheda
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
                Errore.crea(unErrore);
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
     * Codifica degli estratti pubblici disponibili all'esterno del modulo
     * (ATTENZIONE - gli oggetti sono di classe Estratto)
     *
     * @see it.algos.base.wrapper.EstrattoBase
     * @see it.algos.base.wrapper.EstrattoBase.Tipo
     * @see it.algos.base.wrapper.Estratti
     */
    public enum Estratto implements Estratti {

        descrizione(EstrattoBase.Tipo.stringa),
        sigla(EstrattoBase.Tipo.stringa),
        descrizioneCameraPersona(EstrattoBase.Tipo.stringa);

        /**
         * tipo di estratto utilizzato
         */
        private EstrattoBase.Tipo tipoEstratto;

        /**
         * modulo di riferimento
         */
        private static String nomeModulo = Listino.NOME_MODULO;


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
            return Listino.Estratto.nomeModulo;
        }

    }// fine della classe


    /**
     * Codifica dell'ambito del prezzo di listino.
     */
    public enum AmbitoPrezzo implements Campo.ElementiCombo {

        pensioniComplete(1, Tipo.pensioni, "Pensioni complete", "FB"),
        mezzePensioni(3, Tipo.pensioni, "Mezze pensioni", "HB"),
        pernottamenti(4, Tipo.pensioni, "Pernottamenti", "BB"),
        altro(5, Tipo.pensioni, "Altro", ""),
        extra(2, Tipo.extra, "Extra", ""),;

        /**
         * codice del record
         */
        private int codice;

        /**
         * tipo di ambito
         */
        private Tipo tipo;

        /**
         * titolo da utilizzare
         */
        private String titolo;

        /**
         * titolo da utilizzare
         */
        private String sigla;


        /**
         * Costruttore completo con parametri.
         *
         * @param codice del record
         * @param tipo tipo di ambito
         * @param titolo utilizzato nei popup
         * @param sigla utilizzata nelle liste
         */
        AmbitoPrezzo(int codice, Tipo tipo, String titolo, String sigla) {
            try { // prova ad eseguire il codice

                /* regola le variabili di istanza coi parametri */
                this.setCodice(codice);
                this.setTipo(tipo);
                this.setTitolo(titolo);
                this.setSigla(sigla);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Determina se una data voce di listino è di ambito Pensioni.
         * <p/>
         * Ritorna true se l'ambito della voce di listino specificata
         * fa parte del gruppo degli ambiti di Pensione
         *
         * @param codListino da controllare
         *
         * @return true se la vode di listino è di ambito Pensioni
         */
        public static boolean isPensione(int codListino) {
            /* variabili e costanti locali di lavoro */
            boolean pensione = false;
            int codAmbitoListino;
            Modulo mod;
            Campo campoAmbitoPrezzo;
            AmbitoPrezzo ambitoListino;

            try { // prova ad eseguire il codice

                /* recupera il codice ambito del record di listino */
                mod = ListinoModulo.get();
                campoAmbitoPrezzo = mod.getCampo(Listino.Cam.ambitoPrezzo.get());
                codAmbitoListino = mod.query().valoreInt(campoAmbitoPrezzo, codListino);

                /* recupera l'oggetto Ambito corrispondente */
                ambitoListino = AmbitoPrezzo.getAmbito(codAmbitoListino);

                /* controlla se di tipo Pensione  */
                pensione = isPensione(ambitoListino);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return pensione;
        }


        /**
         * Determina se un dato ambito è di tipo Pensione o Extra.
         * <p/>
         *
         * @param ambito da controllare
         *
         * @return true se di tipo Pensione, false se di tipo Extra
         */
        private static boolean isPensione(AmbitoPrezzo ambito) {
            /* variabili e costanti locali di lavoro */
            boolean pensione = false;
            Tipo tipoAmbito;

            try {    // prova ad eseguire il codice
                tipoAmbito = ambito.getTipo();
                pensione = tipoAmbito.equals(Tipo.pensioni);
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return pensione;
        }


        /**
         * Ritorna i codici degli oggetti AmbitoPrezzo di un certo tipo (pensione o extra).
         * <p/>
         *
         * @param flagTipo true per pensioni false per extra
         *
         * @return la lista dei codici degli oggetti AmbitoPrezzo del tipo richiesto
         */
        public static ArrayList<Integer> getCodiciAmbito(boolean flagTipo) {
            /* variabili e costanti locali di lavoro */
            ArrayList<Integer> lista = null;
            Tipo tipoRichiesto;

            try { // prova ad eseguire il codice

                /* identifica il tipo richiesto */
                if (flagTipo) {
                    tipoRichiesto = Tipo.pensioni;
                } else {
                    tipoRichiesto = Tipo.extra;
                }// fine del blocco if-else

                /* traverso tutta la collezione e aggiungo i codici */
                lista = new ArrayList<Integer>();
                for (AmbitoPrezzo ambito : values()) {
                    if (ambito.getTipo().equals(tipoRichiesto)) {
                        lista.add(ambito.getCodice());
                    }// fine del blocco if
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        /**
         * Ritorna un filtro che isola tutte le voci di listino relative a Pensione o Extra.
         * <p/>
         *
         * @param pensione true per isolare tutte le voci di tipo Pensione, false per gli Extra
         *
         * @return il filtro per le voci di Listino
         */
        public static Filtro getFiltroListino(boolean pensione) {
            /* variabili e costanti locali di lavoro */
            Filtro filtro = null;
            boolean continua;
            ArrayList<Integer> lista;
            Modulo mod = null;
            Campo campoAmbito = null;

            try { // prova ad eseguire il codice

                lista = Listino.AmbitoPrezzo.getCodiciAmbito(pensione);
                continua = (lista != null && lista.size() > 0);

                if (continua) {
                    mod = ListinoModulo.get();
                    continua = (mod != null);
                }// fine del blocco if

                if (continua) {
                    campoAmbito = mod.getCampo(Listino.Cam.ambitoPrezzo);
                    continua = (campoAmbito != null);
                }// fine del blocco if

                if (continua) {
                    filtro = new Filtro();
                    for (int cod : lista) {
                        filtro.add(Filtro.Op.OR, FiltroFactory.crea(campoAmbito, cod));
                    } // fine del ciclo for-each
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return filtro;
        }


        /**
         * Determina se un dato record di listino è di ambito Pensione
         * <p/>
         *
         * @param codAmbito da controllare
         *
         * @return true se è di pensione, false se è extra
         */
        public static boolean isCodPensione(int codAmbito) {
            /* variabili e costanti locali di lavoro */
            boolean pensione = false;
            AmbitoPrezzo ambito;

            try { // prova ad eseguire il codice

                ambito = getAmbito(codAmbito);

                if (ambito != null) {
                    pensione = (ambito.getTipo() == Tipo.pensioni);
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return pensione;
        }


        /**
         * Ritorna l'ambito, dato il codice di database.
         * <p/>
         *
         * @param codAmbito da controllare
         *
         * @return l'oggetto AmbitoPrezzo con il codice dato
         */
        private static AmbitoPrezzo getAmbito(int codAmbito) {
            /* variabili e costanti locali di lavoro */
            AmbitoPrezzo ambito = null;

            try { // prova ad eseguire il codice

                /* traverso tutta la collezione */
                for (AmbitoPrezzo am : values()) {
                    if (am.getCodice() == codAmbito) {
                        ambito = am;
                    }// fine del blocco if
                } // fine del ciclo for-each
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return ambito;
        }


        /**
         * Ritorna la sigla, dato il codice di database.
         * <p/>
         *
         * @param codAmbito da controllare
         *
         * @return sigla di pensione
         */
        public static String getSigla(int codAmbito) {
            /* variabili e costanti locali di lavoro */
            String sigla = "";
            AmbitoPrezzo ambito;

            try { // prova ad eseguire il codice
                ambito = getAmbito(codAmbito);

                if (ambito != null) {
                    sigla = ambito.getSigla();
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return sigla;
        }


        public int getCodice() {
            return codice;
        }


        private void setCodice(int codice) {
            this.codice = codice;
        }


        public Tipo getTipo() {
            return tipo;
        }


        private void setTipo(Tipo tipo) {
            this.tipo = tipo;
        }


        public String getTitolo() {
            return titolo;
        }


        private void setTitolo(String titolo) {
            this.titolo = titolo;
        }


        public String getSigla() {
            return sigla;
        }


        private void setSigla(String sigla) {
            this.sigla = sigla;
        }


        @Override
        public String toString() {
            return this.getTitolo();

        }


        /**
         * Classe interna Enumerazione.
         * <p/>
         * Codifica dei tipi di Ambito Prezzo
         */
        public enum Tipo {

            pensioni,
            extra;


            /**
             * Costruttore completo con parametri.
             */
            Tipo() {
                try { // prova ad eseguire il codice
                    /* regola le variabili di istanza coi parametri */
                } catch (Exception unErrore) { // intercetta l'errore
                    Errore.crea(unErrore);
                }// fine del blocco try-catch
            }


        }

    }// fine della classe


    /**
     * Tipo del prezzo di listino (per persona / per camera)
     * Significativo Solo per ambito pensioni o entrambe
     */
    public enum TipoPrezzo {

        persona(1, "Per persona"),
        camera(2, "Per camera"),;

        /**
         * codice del record
         */
        private int codice;

        /**
         * titolo da utilizzare
         */
        private String titolo;


        /**
         * Costruttore completo con parametri.
         *
         * @param codice del record
         * @param titolo utilizzato nei popup
         */
        TipoPrezzo(int codice, String titolo) {
            try { // prova ad eseguire il codice

                /* regola le variabili di istanza coi parametri */
                this.setCodice(codice);
                this.setTitolo(titolo);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public static ArrayList<String> getElenco() {
            /* variabili e costanti locali di lavoro */
            ArrayList<String> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<String>();

                /* traverso tutta la collezione */
                for (TipoPrezzo tipo : values()) {
                    lista.add(tipo.getTitolo());
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        /**
         * Ritorna la descrizione dal codice.
         * <p/>
         *
         * @param codice il codice
         *
         * @return la descrizione
         */
        public static String getDescrizione(int codice) {
            /* variabili e costanti locali di lavoro */
            String descrizione = "";
            int unCodice;

            try { // prova ad eseguire il codice

                /* traverso tutta la collezione */
                for (TipoPrezzo tipo : values()) {
                    unCodice = tipo.getCodice();
                    if (unCodice == codice) {
                        descrizione = tipo.getTitolo();
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return descrizione;
        }


        /**
         * Determina se un dato record di listino è per persona
         * <p/>
         *
         * @param codListino da controllare
         *
         * @return true se è per persona, false se è per camera
         */
        public static boolean isPerPersona(int codListino) {
            /* variabili e costanti locali di lavoro */
            boolean persona = false;
            int intero;
            Modulo mod;
            Campo campoTipoPrezzo;
            int codPersona;

            try { // prova ad eseguire il codice
                mod = ListinoModulo.get();
                campoTipoPrezzo = mod.getCampo(Listino.Cam.tipoPrezzo.get());
                codPersona = Listino.TipoPrezzo.persona.getCodice();
                intero = mod.query().valoreInt(campoTipoPrezzo, codListino);
                if (intero == codPersona) {
                    persona = true;
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return persona;
        }


        /**
         * Determina se un dato record di listino è per camera
         * <p/>
         *
         * @param codListino da controllare
         *
         * @return true se è per camera, false se è per persona
         */
        public static boolean isPerCamera(int codListino) {
            return !isPerPersona(codListino);
        }


        public int getCodice() {
            return codice;
        }


        private void setCodice(int codice) {
            this.codice = codice;
        }


        public String getTitolo() {
            return titolo;
        }


        private void setTitolo(String titolo) {
            this.titolo = titolo;
        }


    }// fine della classe


    /**
     * Modalità di applicazione di prezzo di listino.
     */
    public enum ModoPrezzo {

        fisso(1, "Fisso"),
        variabile(2, "Variabile");

        /**
         * codice del record
         */
        private int codice;

        /**
         * titolo da utilizzare
         */
        private String titolo;


        /**
         * Costruttore completo con parametri.
         *
         * @param codice del record
         * @param titolo utilizzato nei popup
         */
        ModoPrezzo(int codice, String titolo) {
            try { // prova ad eseguire il codice

                /* regola le variabili di istanza coi parametri */
                this.setCodice(codice);
                this.setTitolo(titolo);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public static ArrayList<String> getElenco() {
            /* variabili e costanti locali di lavoro */
            ArrayList<String> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<String>();

                /* traverso tutta la collezione */
                for (ModoPrezzo modo : values()) {
                    lista.add(modo.getTitolo());
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        /**
         * Ritorna l'elemento dal codice.
         * <p/>
         *
         * @param codice il codice da cercare
         *
         * @return l'elemento della enum con il codice richiesto
         */
        public static ModoPrezzo getValore(int codice) {
            /* variabili e costanti locali di lavoro */
            ModoPrezzo valore = null;
            try {    // prova ad eseguire il codice
                /* traverso tutta la collezione */
                for (ModoPrezzo elem : values()) {
                    if (elem.getCodice() == codice) {
                        valore = elem;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return valore;
        }


        public int getCodice() {
            return codice;
        }


        private void setCodice(int codice) {
            this.codice = codice;
        }


        public String getTitolo() {
            return titolo;
        }


        private void setTitolo(String titolo) {
            this.titolo = titolo;
        }

    }// fine della classe


    /**
     * Codifica degli ambiti prezzo disponibili nella definizione di un Periodo.
     * <p/>
     * Sottoinsieme degli ambiti prezzo generale.
     */
    public enum PensioniPeriodo implements Campo.ElementiCombo {

        pensioneCompleta(AmbitoPrezzo.pensioniComplete),
        mezzaPensione(AmbitoPrezzo.mezzePensioni),
        pernottamento(AmbitoPrezzo.pernottamenti);

        private AmbitoPrezzo ambito;


        /**
         * Costruttore completo con parametri.
         *
         * @param ambito tipo di ambito
         */
        PensioniPeriodo(AmbitoPrezzo ambito) {
            try { // prova ad eseguire il codice

                /* regola le variabili di istanza coi parametri */
                this.setAmbito(ambito);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        private AmbitoPrezzo getAmbito() {
            return ambito;
        }


        private void setAmbito(AmbitoPrezzo ambito) {
            this.ambito = ambito;
        }


        public int getCodice() {
            return this.getAmbito().getCodice();
        }


        public String getSigla() {
            return this.getAmbito().getSigla();
        }


        public String getDescrizione() {
            return this.getAmbito().getTitolo();
        }

        /**
         * Ritorna l'ambito dal codice
         * <p>
         * @param codice il codice
         * @return l'ambito coddispondente
         * */
        public static AmbitoPrezzo getAmbito(int codice) {
            return AmbitoPrezzo.getAmbito(codice);
        }

        /**
         * Ritorna la sigla dal codice
         * <p>
         * @param codice il codice
         * @return la sigla coddispondente
         * */
        public static String getSigla(int codice) {
            /* variabili e costanti locali di lavoro */
            String sigla = "";
            AmbitoPrezzo ambito;

            try { // prova ad eseguire il codice
                ambito = getAmbito(codice);
                if (ambito!=null) {
                    sigla = ambito.getSigla();
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return sigla;
        }

        /**
         * Ritorna l'elemento dal codice di database
         * <p/>
         *
         * @param codice il codice di database
         *
         * @return il corrispondente elemento della Enum
         */
        public static PensioniPeriodo get(int codice) {
            /* variabili e costanti locali di lavoro */
            PensioniPeriodo[] elementi;
            PensioniPeriodo elemento = null;

            try { // prova ad eseguire il codice
                elementi = PensioniPeriodo.values();
                for (PensioniPeriodo elem : elementi) {
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



        @Override
        public String toString() {
            return this.getAmbito().getSigla();
        }
    }


} // fine della classe
