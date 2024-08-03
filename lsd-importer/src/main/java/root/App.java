package root;

import root.entity.LsdExchangeEntity;
import root.reader.OdsFileReader;

import java.io.File;
import java.util.List;

public class App {
    public static void main(String[] args) {

        OdsFileReader odsFileReader = new OdsFileReader(new File("D:\\lsd_\\lsd-importer\\src\\main\\resources\\G_UNT2_Statistisches_Unternehmen_Hauptergebnisse_nach_Beschaeftigtengroessenklassen_2022.ods"));
        List<LsdExchangeEntity> readAllList = odsFileReader.readAll();
        readAllList.forEach(System.out::println);
    }
}

