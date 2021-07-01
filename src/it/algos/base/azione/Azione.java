/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      20-dic-2004
 */
package it.algos.base.azione;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.gestore.Gestore;
import it.algos.base.portale.Portale;
import it.algos.base.pref.Pref;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Interfaccia Azione.
 * </p>
 * Questa interfaccia: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> </li>
 * <li> Fornisce un interfaccia unificata per un insieme di interfacce presenti
 * nel package</li>
 * <li> Mantiene le costanti per individuare le singole azioni </li>
 * </ul>
 * La chiave per recuperare l'azione viene codificata qui come <i>costante</i>
 * in modo da poter essere usata in momenti diversi del programma <br>
 * Quandi un'azione viene creata, <strong>deve</strong> usare una delle costanti di
 * questa interfaccia <br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 20-dic-2004 ore 15.33.49
 */
public interface Azione {

    public static final String ABOUT = "About";

    public static final String UPDATE = "Update";

    /**
     * Annulla le modifiche effettuate ad una Scheda.
     * <p/>
     * Ricarica i valori di ogni campo da Archivio a GUI
     */
    public static final String ANNULLA_MODIFICHE = "AnnullaModifiche";

    public static final String ANNULLA_OPERAZIONE = "AnnullaOperazione";

    public static final String ANNULLA_DIALOGO = "AnnullaDialogo";

    public static final String CANCELLA_DIALOGO = "CancellaDialogo";

    public static final String CONFERMA_DIALOGO = "ConfermaDialogo";

    public static final String DIALOGO = "dialogo";

    /**
     * Elimina il testo selezionato.
     */
    public static final String ELIMINA_TESTO = "EliminaTesto";

    public static final String CARICA_LISTA = "CaricaLista";

    public static final String CARICA_TUTTI = "CaricaTutti";

    public static final String CHIUDE_LISTA = "ChiudeLista";

    public static final String CHIUDE_SCHEDA = "ChiudeScheda";

    public static final String CHIUDE_NAVIGATORE = "ChiudeNavigatore";

    public static final String COPIA_TESTO = "CopiaTesto";

    public static final String DESELEZIONA_LISTA = "DeselezionaScheda";

    public static final String ELIMINA_SCHEDA = "EliminaScheda";

    public static final String ELIMINA_SELEZIONE = "EliminaSelezione";

    public static final String SALVA_SELEZIONE = "SalvaSelezione";

    public static final String CARICA_SELEZIONE = "CaricaSelezione";

    /**
     * Incolla il testo dagli appunti.
     */
    public static final String INCOLLA_TESTO = "IncollaTesto";

    public static final String MODIFICA_RECORD = "Modificarecord";

    public static final String MOSTRA_APPUNTI = "MostraAppunti";

    public static final String MOSTRA_TUTTI = "MostraTutti";

    public static final String NUOVO_RECORD = "NuovoRecord";

    public static final String AGGIUNGI_RECORD = "AggiungiRecord";

    public static final String ELIMINA_RECORD = "EliminaRecord";

    public static final String RIMUOVI_RECORD = "RimuoviRecord";

    public static final String DUPLICA_RECORD = "DuplicaRecord";

    public static final String ORDINA_LISTA = "OrdinaLista";

    public static final String PALETTE = "Palette";

    public static final String PRIMO_RECORD = "Primorecord";

    public static final String RECORD_PRECEDENTE = "RecordPrecedente";

    public static final String RECORD_SUCCESSIVO = "RecordSuccessivo";

    public static final String ULTIMO_RECORD = "Ultimorecord";

    public static final String REGISTRA_SCHEDA = "RegistraRecord";

    public static final String REGISTRA_LISTA = "RegistraLista";

    public static final String NASCONDE_SELEZIONATI = "NascondeSelezionati";

    public static final String SELEZIONA_TUTTI = "SelezionaTutti";

    public static final String SELEZIONA_TUTTO = "SelezionaTutto";

    public static final String SOLO_SELEZIONATI = "SoloSelezionati";

    /**
     * Taglia il testo.
     * <p/>
     * Cancella il testo selezionato conservandolo negli appunti
     */
    public static final String TAGLIA_TESTO = "TagliaTesto";

    public static final String CHIUDE_PROGRAMMA = "ChiudiProgramma";

    public static final String FINESTRA_CHIUDE_PROGRAMMA = "FinestraChiudiProgramma";

    public static final String RIPRISTINA_OPERAZIONE = "RipristinaOperazione";

    public static final String APRE_LISTA = "ApreLista";

    public static final String LOOK_MAC = "AzLookMac";

    public static final String LOOK_METAL = "AzLookMet";

    public static final String LOOK_MOTIF = "AzLookMot";


    public static final String HELP = "Help";

    public static final String HELP_PROGRAMMATORE = "HelpProgrammatore";

    public static final String RIGA_SU = "RigaSu";

    public static final String RIGA_GIU = "RigaGiu";

    public static final String ICONA_PICCOLA = AbstractAction.SMALL_ICON;

    public static final String ICONA_MEDIA = "DisegnoMedio";

    public static final String ICONA_GRANDE = "DisegnoGrande";

    public static final String CARATTERE = "Carattere";

    public static final String LISTA_CLICK = "ListaClick";

    public static final String LISTA_DOPPIO_CLICK = "ListaDoppioClick";

    public static final String ENTER = "EnterPremuto";

    public static final String ESCAPE = "EscPremuto";

    public static final String TAB = "TabPremuto";

    public static final String ENTRATA_CAMPO = "EntrataCampo";

    public static final String USCITA_CAMPO = "UscitaCampo";

    public static final String ENTRATA_CELLA = "EntrataCella";

    public static final String USCITA_CELLA = "UscitaCella";

    public static final String FRECCE = "FrecceLista";

    public static final String PAGINE = "pagineLista";

    public static final String HOME = "HomeLista";

    public static final String END = "EndLista";

    public static final String TITOLO = "ClickTitoloLista";

    public static final String ENTRATA_POPUP = "EntrataPopup";

    public static final String USCITA_POPUP = "UscitaPopup";

    public static final String ITEM_MODIFICATO = "ItemModificato";

    public static final String RADIO_MODIFICATO = "RadioModificato";

    public static final String POPUP_MODIFICATO = "PopupModificato";

    public static final String SELEZIONE_MODIFICATA = "SelezioneModificata";

    public static final String ATTIVA_FINESTRA = "AttivaFinestra";

    public static final String CHIUDE_FINESTRA = "ChiudeFinestra";

    public static final String EMERGENZA = "Emergenza";

    public static final String LISTA_CARATTERE = "ListaCarattere";

    public static final String MOUSE_CLICK = "MouseClick";

    public static final String MOUSE_DOPPIO_CLICK = "MouseDoppioClick";

    public static final String PREFERENZE = "Preferenze";

    public static final String CONTATORI = "Contatori";

    public static final String SEMAFORI = "Semafori";

    public static final String UTENTI = "Utenti";

    public static final String STAMPA = "Stampa";

    public static final String RICERCA = "Ricerca";

    public static final String PROIETTA = "Proiezione";

    public static final String PREFERITO = "Preferito";

    public static final String LISTA_ENTER = "ListaEnter";

    public static final String LISTA_RETURN = "ListaReturn";

    public static final String REGISTRA_DATI_DEFAULT = "RegistraDatiDefault";

    public static final String CALCOLA = "Calcola";

    public static final String CALCOLA_CAMPO = "CalcolaCampo";

    public static final String CALCOLA_LEGENDA = "CalcolaLegenda";

    public static final String COLONNA = "ColonnaLista";

    public static final String STAMPA_MEU = "StampaMenu";

    public static final String STAMPA_COMANDE = "StampaComande";

    public static final String EXPORT = "esporta";

    public static final String IMPORT = "importa";

    public static final String BACKUP = "backup";

    public static final String RESTORE = "restore";


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @return vero se viene inizializzato adesso;
     *         falso se era gi&agrave; stato inizializzato
     */
    public abstract boolean inizializza();


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi (o anche da questa) ogni volta che questo oggetto
     * deve <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unGestore gestore degli eventi
     */
    public abstract void avvia(Gestore unGestore);


    /**
     * actionPerformed, da ActionListener.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public abstract void actionPerformed(ActionEvent unEvento);


    /**
     * Portale proprietario dell' azione
     */
    public abstract Portale getPortale();


    /**
     * Portale proprietario dell' azione
     */
    public abstract void setPortale(Portale portale);


    public abstract void setGestore(Gestore gestore);


    /**
     * testo della chiave per recuperare l'azione dalla collezione
     */
    public abstract String getChiave();


    /**
     * Flag per mostrare nei menu l'azione (disattivata finche' non funziona)
     */
    public abstract boolean isAttiva();


    /**
     * Abilitazione dell'azione alla partenza
     */
    public abstract boolean isAbilitataPartenza();


    /**
     * Restituisce l'icona associata ad una specifica chiave.
     *
     * @param unaChiave nome dell'icona specifica
     *
     * @return l'icona associata alla chiave specifica; se la chiave non
     *         esiste, ritorna <code>nullo</code>
     */
    public abstract ImageIcon getIcona(String unaChiave);


    /**
     * Ritorna l'icona piccola.
     * <p/>
     *
     * @return l'icona piccola
     */
    public abstract ImageIcon getIconaPiccola();


    /**
     * Ritorna l'icona media.
     * <p/>
     *
     * @return l'icona media
     */
    public abstract ImageIcon getIconaMedia();


    /**
     * Ritorna l'icona grande.
     * <p/>
     *
     * @return l'icona grande
     */
    public abstract ImageIcon getIconaGrande();


    /**
     * Ritorna una copia profonda dell'oggetto (deep copy).
     *
     * @return azione clonata
     */
    public abstract Azione clonaAzione();


    /**
     * Restituisce l'oggetto concreto della classe principale.
     *
     * @return oggetto concreto restituito dall'interfaccia
     */
    public abstract AzioneBase getAzione();


    /**
     * Abilita / disabilita l'azione.
     * <p/>
     *
     * @param flag di abilitazione
     */
    public abstract void setEnabled(boolean flag);


    /**
     * Regola il testo del Tooltip.
     * <p/>
     *
     * @param tooltip il testo del Tooltip
     */
    public abstract void setTooltip(String tooltip);


    public abstract Campo getCampo();


    public abstract void setCampo(Campo campo);


    public abstract Pref.Utente getUtente();


    public abstract boolean isUsoLista();


    public abstract void setUsoLista(boolean usoLista);


    public abstract boolean isUsoScheda();


    public abstract boolean isUsoNavigatore();


    /**
     * Classe interna Enumerazione.
     */
    public enum Carattere {

        escape("escape", KeyEvent.VK_ESCAPE),
        tab("tab", KeyEvent.VK_TAB),
        enter("enter", KeyEvent.VK_ENTER);
//        return2("return",KeyEvent.VK_);

        /**
         * nome mnemonico
         */
        private String nome;

        /**
         * codice del carattere
         */
        private int codice;


        /**
         * Costruttore completo con parametri.
         *
         * @param nome
         */
        Carattere(String nome, int codice) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setNome(nome);
                this.setCodice(codice);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public String getNome() {
            return this.nome;
        }


        private void setNome(String nome) {
            this.nome = nome;
        }


        public int getCodice() {
            return codice;
        }


        private void setCodice(int codice) {
            this.codice = codice;
        }

    }// fine della classe


}// fine della interfaccia
