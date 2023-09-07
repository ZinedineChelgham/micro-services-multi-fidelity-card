package fr.univcotedazur.polytech.multifidelity.components;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.exceptions.NoSuchCode;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotEnoughMoneyException;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotFoundException;
import fr.univcotedazur.polytech.multifidelity.interfaces.ImpactAccount;
import fr.univcotedazur.polytech.multifidelity.interfaces.ImpactMoney;
import fr.univcotedazur.polytech.multifidelity.interfaces.PayPointOffer;
import fr.univcotedazur.polytech.multifidelity.interfaces.VFPManaging;
import fr.univcotedazur.polytech.multifidelity.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
@Transactional
public class AccountManager implements ImpactAccount, PayPointOffer, ImpactMoney {

    CustomerRepository customerRepository;
    VFPManaging vfpManaging;
    @Autowired
    public AccountManager(CustomerRepository repository, VFPManaging vfpManaging){
        this.customerRepository = repository;
        this.vfpManaging = vfpManaging;
    }

    /**
     * methode qui retire le montant de l'argent dans le compte client et qui
     * lui fait gagner des points.
     */
    @Override
    public double gainPointsLessMoney(double amountPayed, long id, Date date) throws NotFoundException, NotEnoughMoneyException {
        // ici retirer la somme du compte du client et gainPoints, verifier que le compte est suffisant
        Customer customer = customerRepository.findById(id).orElse(null);
        if(customer != null) {
            if(customer.getLoyaltyCardBalance() < amountPayed) {
                throw new NotEnoughMoneyException(customer.getName(),customer.getLoyaltyCardBalance(), amountPayed);
            }else{
                customer.decreaseCardBalanceBy(amountPayed);
                customerRepository.save(customer);
                gainPoints(amountPayed, id, date);
                return customer.getLoyaltyCardBalance();
            }
        }else throw new NotFoundException(id);
    }

    // depuis le customer recuperer le client sur lequel on modifie les points, en parametre on met les id
    /**
     * for every 20 euro paid, the client earn 1 pts
     * @param amountPayed bill cost
     * @param customer a customer
     * @param date date of the transaction
     */
    @Override
    public int gainPoints(double amountPayed, long id, Date date) throws NotFoundException {
        Customer customer = customerRepository.findById(id).orElse(null);
        if(customer != null) {
            customer.setCumulatedpoints(customer.getCumulatedpoints() + amountPayed);
            int pointsGagner = (int) (customer.getCumulatedpoints() / 20);
            int reste = (int) (customer.getCumulatedpoints() % 20);
            customer.setCumulatedpoints(reste);
            customer.setPoints(customer.getPoints() + pointsGagner);
            vfpManaging.VFPCheck(customer, date);
            System.out.println(customer);
            customerRepository.save(customer);  // met a jour le customer en bd
            System.out.println(" ******* Vous Gagnez " + pointsGagner + " points *******");
            return customer.getPoints();
        }else throw new NotFoundException(id);
    }

    @Override
    public int retrancherCodeOffreScanner(int code, long id) throws NotFoundException {
        Customer customer = customerRepository.findById(id).orElse(null);
        if(customer != null) {
            for(int codeX : customer.getCodeBoughtOffer()){
                if(codeX == code){
                    customerRepository.findById(id).get().getCodeBoughtOffer().remove(customer.getCodeBoughtOffer().indexOf(codeX));
                    return codeX;
                }
            }
            return -1;
        }else throw new NotFoundException(id);

    }

    /**
     * methode qui permet de payer avec des points, compte en parallele
     * le nombre de fois que le payement est fait et au 3 eme le VFP est obtenu.
     * @return tableau 0 les points et 1 le code de l'offre
     * @throws NotFoundException
     */
    @Override
    public int[] payPointDue(double amountPayed, long idCustomer) throws NotFoundException {
        Customer customer = customerRepository.findById(idCustomer).orElse(null);
        if(customer != null) {
            int nvMontantPoint = customer.getPoints() - (int) amountPayed;
            if(nvMontantPoint < 0) {
                return new int[]{-1};
            }else{
                customer.setPoints(nvMontantPoint);
                int code = randomize();
                while (customer.getCodeBoughtOffer().contains(code)) {
                    code = randomize();
                }
                customer.getCodeBoughtOffer().add(code);
                customerRepository.save(customer);
                return new int[]{customer.getPoints(),code};
            }
        }else throw new NotFoundException(idCustomer);
    }

    /**
     * renvoie un code aleatoire de 5 chiffres
     */
    private int randomize() {
        Random random = new Random();
        int min = 10000;
        int max = 99999;
        return random.nextInt(max - min + 1) + min;
    }
}
