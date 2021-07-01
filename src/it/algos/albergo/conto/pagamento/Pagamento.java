/**
 * Title:     Pagamento
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      18-lug-2006
 */
package it.algos.albergo.conto.pagamento;

import it.algos.albergo.conto.movimento.Movimento;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;

import java.util.ArrayList;

/**
 * Interfaccia Pagamento.
 * </p>
 * Registro dei pagamenti effettuati dal cliente a fronte di un conto
 * (caparre, acconti, saldi...)
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 18-lug-2006
 */
public interface Pagamento {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "Pagamento";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "pagamento";

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Pagamenti";

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Pagamenti";

    /**
     * Codifica del titolo della tabella come appare nella altre Liste
     * (usato nel Modello)
     */
    public static final String TITOLO_TABELLA = Pagamento.NOME_TAVOLA;


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica dei nomi dei campi (per il modello e per la scheda) <br>
     *
     * @see it.algos.base.wrapper.Campi
     */
    public enum Cam implements Campi {

        data(Movimento.Cam.data.getNome(),
                Movimento.Cam.data.getTitoloColonna(),
                Movimento.Cam.data.getEtichettaScheda(),
                Movimento.Cam.data.getLegenda(),
                Movimento.Cam.data.isVisibileLista()),
        conto(Movimento.Cam.conto.getNome(),
                Movimento.Cam.conto.getTitoloColonna(),
                Movimento.Cam.conto.getEtichettaScheda(),
                Movimento.Cam.conto.getLegenda(),
                Movimento.Cam.conto.isVisibileLista()),
        titolo("titolo", "", "", "", true),
        importo(Movimento.Cam.importo.getNome(),
                Movimento.Cam.importo.getTitoloColonna(),
                Movimento.Cam.importo.getEtichettaScheda(),
                Movimento.Cam.importo.getLegenda(),
                Movimento.Cam.importo.isVisibileLista()),
        mezzo("linkmezzo", "mezzo", "mezzo", "", true),
        ricevuta("ricevuta", "", "", "", true),
        note(Movimento.Cam.note.getNome(),
                Movimento.Cam.note.getTitoloColonna(),
                Movimento.Cam.note.getEtichettaScheda(),
                Movimento.Cam.note.getLegenda(),
                Movimento.Cam.note.isVisibileLista()),;

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


    /**
     * Codifica delle Viste del modulo.
     */
    public enum Vis {

        vistaConto(); // vista dei pagamenti usata nella scheda conto


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
    public enum Estratto implements Estratti {

        descrizione(EstrattoBase.Tipo.stringa),
        sigla(EstrattoBase.Tipo.stringa);


        /**
         * tipo di estratto utilizzato
         */
        private EstrattoBase.Tipo tipoEstratto;

        /**
         * modulo di riferimento
         */
        private static String nomeModulo = Pagamento.NOME_MODULO;


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
            return Pagamento.Estratto.nomeModulo;
        }

    }// fine della classe


    /**
     * Classe interna Enumerazione
     * <p/>
     * Valori possibili per Titolo Pagamento
     */
    public enum TitoloPagamento {

        caparra(1, "Caparra"),
        acconto(2, "Acconto"),
        saldo(3, "Saldo"),;

        /**
         * codice del record
         */
        private int codice;

        /**
         * titolo da utilizzare
         */
        private String descrizione;


        /**
         * Costruttore completo con parametri.
         *
         * @param codice del record
         * @param titolo utilizzato nei popup
         */
        TitoloPagamento(int codice, String titolo) {
            try { // prova ad eseguire il codice

                /* regola le variabili di istanza coi parametri */
                this.setCodice(codice);
                this.setDescrizione(titolo);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Ritorna un elenco delle descrizioni
         * <p/>
         *
         * @return l'elenco delle descrizioni
         */
        public static ArrayList<String> getDescrizioni() {
            /* variabili e costanti locali di lavoro */
            ArrayList<String> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<String>();

                /* traverso tutta la collezione */
                for (TitoloPagamento tipo : TitoloPagamento.values()) {
                    lista.add(tipo.getDescrizione());
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        /**
         * Ritorna il valore dato il codice
         * <p/>
         *
         * @param codice del titolo pagamento
         *
         * @return oggetto TitoloPagamento corrispondente
         */
        public static TitoloPagamento getValore(int codice) {
            /* variabili e costanti locali di lavoro */
            TitoloPagamento valore = null;

            try { // prova ad eseguire il codice

                /* traverso tutta la collezione */
                for (TitoloPagamento elemento : TitoloPagamento.values()) {
                    if (elemento.getCodice() == codice) {
                        valore = elemento;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return valore;
        }


        /**
         * Ritorna la descrizione dato il codice
         * <p/>
         *
         * @param codice del titolo pagamento
         *
         * @return descrizione del titolo pagamento
         */
        public static String getDescrizione(int codice) {
            /* variabili e costanti locali di lavoro */
            String descrizione = "";
            TitoloPagamento elemento = null;

            try { // prova ad eseguire il codice
                elemento = getValore(codice);
                if (elemento != null) {
                    descrizione = elemento.getDescrizione();
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return descrizione;
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


    }// fine della classe

} // fine della classe
