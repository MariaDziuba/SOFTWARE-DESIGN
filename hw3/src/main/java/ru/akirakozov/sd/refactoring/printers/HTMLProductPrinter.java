package ru.akirakozov.sd.refactoring.printers;

import ru.akirakozov.sd.refactoring.models.Product;

import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public class HTMLProductPrinter {

    private final PrintWriter printer;

    public HTMLProductPrinter(PrintWriter printer) {
        this.printer = printer;
    }

    public void printProductsList(List<Product> products) {
        this.printer.println("<html><body>");
        for (Product product : products) {
            this.printer.println(product.toHTML());
        }
        this.printer.println("</body></html>");
    }

    public void printProduct(Optional<Product> product, String header) {
        this.printer.println("<html><body>");
        this.printer.println("<h1>" + header + "</h1>");
        product.ifPresent(p -> this.printer.println(p.toHTML()));
        this.printer.println("</body></html>");
    }

    public void print(Object value, String header) {
        this.printer.println("<html><body>");
        this.printer.println(header);
        this.printer.println(value);
        this.printer.println("</body></html>");
    }
}
