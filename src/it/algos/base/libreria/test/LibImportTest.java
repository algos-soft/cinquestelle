package it.algos.base.libreria.test;

import it.algos.base.libreria.LibImport;
import junit.framework.TestCase;

public class LibImportTest extends TestCase {

    LibImport libImport;


    public void testVCardEstrae() throws Exception {
        String testo;
        String[] parti;
        int richiesto;
        int ottenuto;
        String tab;


        testo =
                ":VCARD\n" +
                        "VERSION:3.0\n" +
                        "N:;aldo;;;\n" +
                        "FN:aldo\n" +
                        "EMAIL;type=INTERNET;type=WORK;type=pref:aldo@web.uk\n" +
                        "CATEGORIES:Test vCard\n" +
                        "X-ABUID:0A03DA54-EC19-4391-9D9D-6BF76AC93C97\\:ABPerson\n" +
                        "END:VCARD\n" +
                        "BEGIN:VCARD\n" +
                        "VERSION:3.0\n" +
                        "N:Beretta;mario;;;\n" +
                        "FN:Fiat spA\n" +
                        "ORG:Fiat spA;\n" +
                        "EMAIL;type=INTERNET;type=WORK;type=pref:(mail) marco@chiocciola.it\n" +
                        "TEL;type=WORK;type=pref:(uff) 078546987\n" +
                        "TEL;type=CELL:(cell 789654)\n" +
                        "item1.ADR;type=WORK;type=pref:;;Via dei Gelsomini\\, 17;Toprino;TO;250145;It\n" +
                        "item1.X-ABADR:it\n" +
                        "item2.X-ABRELATEDNAMES;type=pref:roberto\n" +
                        "item2.X-ABLabel:_$!<Friend>!$_\n" +
                        "X-ABShowAs:COMPANY\n" +
                        "CATEGORIES:Test vCard\n" +
                        "X-ABUID:BD467894-F777-4E69-A963-B2C17D722B86\\:ABPerson\n" +
                        "END:VCARD\n" +
                        "BEGIN:VCARD\n" +
                        "VERSION:3.0\n" +
                        "N:;romeo;;;\n" +
                        "FN:romeo\n" +
                        "EMAIL;type=INTERNET;type=WORK;type=pref:lasto@xx.com\n" +
                        "CATEGORIES:Test vCard\n" +
                        "X-ABUID:983EE81A-8C25-461C-A41E-38F48A6B5C2F\\:ABPerson\n" +
                        "END:VCARD";
        tab = "BEGIN";
        richiesto = 3;

        parti = testo.split(tab);
        ottenuto = parti.length;

        if (ottenuto != richiesto) {
            fail("pezzetti non uguali");
        }// fine del blocco if
    }
}