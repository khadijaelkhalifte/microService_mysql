package org.ENSAJ.ClientService.Controller;

import org.ENSAJ.ClientService.ClientApplication;
import org.ENSAJ.ClientService.Model.Client;
import org.ENSAJ.ClientService.Model.Voiture;
import org.ENSAJ.ClientService.Service.ClientService;
//import org.ENSAJ.ClientService.Model.Voiture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ClientController {


    @FeignClient(name="SERVICE-VOITURE")
    interface VoitureService{
        @GetMapping(path="/voitures/client/{id}")
        public  List<Voiture> voitureByClientId(@PathVariable Long id);
    }
    @Autowired
    VoitureService voitureService;
    @Autowired
    ClientService clientService;

    @GetMapping("/clients")
    public List<Client> chercherClient(){
        return clientService.retournerListeClients();
    }

    @GetMapping("/clients/{id}")
    public Client chercherUnClient(@PathVariable Long id) throws Exception {
        return clientService.retournerClientById(id);
    }

    @PostMapping("/clients")
    public Client enregistrerUnClient(@RequestBody Client client){
        return clientService.enregistrerClient(client);
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<Client> modifierUnClient(@PathVariable Long id, @RequestBody Client client) throws Exception {
        return clientService.modifierClient(id, client);
    }

    @DeleteMapping("/clients/{id}")
    public void deleteUnClient(@PathVariable Long id){
        clientService.supprimerClient(id);
    }


}
