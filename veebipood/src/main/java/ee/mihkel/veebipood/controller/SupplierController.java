package ee.mihkel.veebipood.controller;

import ee.mihkel.veebipood.model.Supplier1;
import ee.mihkel.veebipood.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @GetMapping("supplier1")
    public List<Supplier1> getSupplier1() {
        return supplierService.getSupplier1Products();
    }

    // https://api.escuelajs.co/api/v1/products
//    @GetMapping("supplier2")
//    public List<Supplier2> getSupplier2() {
//        return supplierService.getSupplier2Products();
//    }
//
//    // https://dummyjson.com/products
//    @GetMapping("supplier3")
//    public List<Supplier3> getSupplier3() {
//        return supplierService.getSupplier3Products();
//    }
//
//    //https://www.omniva.ee/locations.json
//    @GetMapping("parcel-machines")
//    public List<ParcelMachine> getSupplierParcelMachines() {
//        return supplierService.getParcelMachines();
//    }
}
