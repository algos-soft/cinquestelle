/**
 * Title:     Sospeso
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      28-set-2007
 */
package it.algos.albergo.conto.sospeso;

import it.algos.albergo.conto.movimento.Movimento;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Navigatori;

/**
 * Interfaccia Sconto.
 * </p>
 * Registro dei movimenti di sconto effettuati
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 28-set-2007
 */
public interface Sospeso {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "Sospeso";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "sospeso";

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Sospesi";

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Sospesi";

    /**
     * Codifica del titolo della tabella come appare nella altre Liste
     * (usato nel Modello)
     */
    public static final String TITOLO_TABELLA = Sospeso.NOME_TAVOLA;


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
        importo(Movimento.Cam.importo.getNome(),
                Movimento.Cam.importo.getTitoloColonna(),
                Movimento.Cam.importo.getEtichettaScheda(),
                Movimento.Cam.importo.getLegenda(),
                Movimento.Cam.importo.isVisibileLista()),
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
            return Sospeso.NOME_MODULO + "." + nome;
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
     * Codifica dei Navigatori del modulo.
     */
    public enum Nav implements Navigatori {

        sospesiConto(); // navigatore dei sospesi usato nella scheda conto


        public String get() {
            return toString();
        }
    }// fine della classe


} // fine della classe
