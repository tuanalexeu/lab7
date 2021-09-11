package com.api.print.implementation;

import com.api.print.api.Printer;

public class PrinterImpl implements Printer {
    public void printResult(String result) {
        if(result != null) {
            System.out.println(result);
        }
    }
}