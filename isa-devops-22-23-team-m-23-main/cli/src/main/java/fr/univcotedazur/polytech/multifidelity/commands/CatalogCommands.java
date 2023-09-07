package fr.univcotedazur.polytech.multifidelity.commands;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.polytech.multifidelity.CliContext;
import fr.univcotedazur.polytech.multifidelity.models.Merchant;
import fr.univcotedazur.polytech.multifidelity.models.Offer;
import fr.univcotedazur.polytech.multifidelity.models.OfferCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;

@ShellComponent
public class CatalogCommands {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext context;

    public static final String MERCHANT_BASE_URI = "/merchants";

    @ShellMethod("A merchant can add an offer (add_offer MERCHANT_ID OFFER_NAME OFFER_DESCRIPTION OFFER_PRICE)")
    public void merchant_add_offer(Long merchantId, String name, String description, int price) {
        Offer offer = new Offer(name, description, price);


        Offer res = restTemplate.postForObject(MERCHANT_BASE_URI+ "/" + merchantId + "/offer", offer, Offer.class);

        context.addOffer(res);

//        Merchant merchant = context.getMerchants().get(merchantId);
//        merchant.setOfferCatalog(new OfferCatalog());
//
//        context.addOfferToCatalog(res, merchant);
//        context.addOffer(res);


        context.getLogger().info("The offer has been added: " + res.toString());
    }

    @ShellMethod("A merchant can update an offer (update_offer MERCHANT_ID OFFER_NAME OFFER_DESCRIPTION OFFER_PRICE)")
    public void update_offer(Long merchantId, String name, String description, int price) {
        Offer offerDto = new Offer(name, description, price);

        restTemplate.put(MERCHANT_BASE_URI + "/" + merchantId + "/offer", offerDto);

        context.getLogger().info("The offer has been updated");


    }
    @ShellMethod("Get the list of offers (offers)")
    public void offers() {
        Object res = restTemplate.getForObject("/offers", Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.convertValue(res, JsonNode.class);
        Iterator<String> fieldNames = jsonNode.fieldNames();

        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode fieldValue = jsonNode.get(fieldName);
            context.getLogger().info(fieldName + ": " + fieldValue.asText());

        }
        for (String key : context.getOffers().keySet()) {
            context.getLogger().info(key + " : " + context.getOffers().get(key).getCost());
        }
        context.getLogger().info("The offers have been listed"+ res.toString());

    }
    @ShellMethod("Delete an offer (delete_offer MERCHANT_ID OFFER_NAME)")
    public void delete_offer(Long merchantId, String offerName) {
        restTemplate.delete(MERCHANT_BASE_URI + "/" + merchantId + "/offer/" + offerName);
        context.getOffers().remove(offerName);
        context.getLogger().info("The offer has been deleted");
    }




}
