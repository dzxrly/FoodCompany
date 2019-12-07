package service;

import javafx.print.PrinterJob;
import javafx.scene.Node;

public class Printer {
    //打印机服务

    public boolean doPrint(Node node) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if(job!=null) {
            boolean printed = job.printPage(node);
            if(printed) {
                job.endJob();
                return true;
            } else return false;
        } else return false;
    }
}

