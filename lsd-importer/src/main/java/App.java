import entity.LsdExchangeEntity;
import reader.OdsFileReader;

import java.io.File;

public class App {
    public static void main(String[] args) {

//        Reader reader = new Reader(new File("D:\\Developer\\temp\\incoming\\G_UNT2_Statistisches_Unternehmen_Hauptergebnisse_nach_Beschaeftigtengroessenklassen_2022.ods"));
//        int ii = 0;
//        for (int i = 7; i >= 0; i--) {
//            LsdExchangeEntity read = reader.read(ii);
//            System.out.println(read);
//            ii++;
//        }
//        LsdExchangeEntity read = reader2.read(2);
//        System.out.println(read);

       OdsFileReader odsFileReader = new OdsFileReader(new File("C:\\Users\\karim\\Downloads\\G_UNT2_Statistisches_Unternehmen_Hauptergebnisse_nach_Beschaeftigtengroessenklassen_2022.ods"));
        int ii = 0;
        for (int i = 678; i >= 0; i--) {
            LsdExchangeEntity read = odsFileReader.read(ii);
            System.out.println(read);
            ii++;
        }
    }
}
