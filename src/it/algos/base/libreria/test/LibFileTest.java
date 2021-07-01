package it.algos.base.libreria.test;

import it.algos.base.libreria.LibFile;
import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.FileReader;

public class LibFileTest extends TestCase {

    LibFile libFile;


    public void testLegge() throws Exception {
        String nomeFile;
        String riga;
        FileReader entrata;
        BufferedReader buffer;
        StringBuffer stringa;
        String testo = "";
        String aCapo = "\n";

        try { // prova ad eseguire il codice
            nomeFile = "/Users/gac/Desktop/Schede.vcf";
//            nomeFile = "/Users/gac/Desktop/SchedeExport.vcf";
//            nomeFile = "/Users/gac/Desktop/test.vcf";
            entrata = new FileReader(nomeFile);
            buffer = new BufferedReader(entrata);

            stringa = new StringBuffer();

            while ((riga = buffer.readLine()) != null) {
                stringa.append(riga);
                stringa.append(aCapo);
            }// fine del blocco while

            /* chiude il file */
            entrata.close();
            buffer.close();

            testo = stringa.toString();
        } catch (Exception unErrore) { // intercetta l'errore
            int a = 87;
        }// fine del blocco try-catch

        fail("test non ancora sviluppato");
    }
}