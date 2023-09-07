package fr.univcotedazur.polytech.multifidelity.components;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.interfaces.VFPManaging;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class VFPManager implements VFPManaging {


    /**
     * Methode a appeler quand l'action influe sur le status VFP
     * Cette methode va venir mettre a jour les informations d'achat du client,
     * cela va venir incrementer sont cumul d'achat et si le cumul d'achat deviens
     * superieur a 3, alors le cumul repasse a 0, le client devient VFP.
     * a chaque calcul d'incrementation, on verifie la date de payement actuel avec
     * la date du derniere payement, il faut que celle ci soit inferieur a 24h, sinon
     * pas d'incrementation du cumul et celui ci repasse a 0. Si le payement suivant
     * sont passage au status VFP se fait dans les moins de 1 semaine, soit 168h,
     * <p>
     * on repasse le cumul a 0 comme si le status vfp est perdu. Le calcule pour le
     * regagner peut reprendre. Si il ne refait pas d'achat au bout de 24h, il doit
     * de nouveau refaire 3 achat, on reinitialise dont cumul de point, meme si il
     * ne lui rester qu'un achat.
     *
     * si le client est supposer ne plus etre vfp, au test de date on renvoie false
     * et on annule sa commande de cadeau VFP.
     * @param customer le client qui fait l'achat
     * @param dateDePayement la date de payement
     * @return true si le client est VFP, false sinon
     */
    @Override
    public boolean VFPCheck(Customer customer, Date dateDePayement) {
        if(!customer.isVfp()){
            if(customer.getDateOfLastTransaction() == null){
                customer.setDateOfLastTransaction(dateDePayement);
                incrementeNbTransaction(customer);
            }else{
                long diff = Math.abs(dateDePayement.getTime() - customer.getDateOfLastTransaction().getTime());
                long diffHours = diff / (60 * 60 * 1000);
                if(diffHours < 48){
                    incrementeNbTransaction(customer);
                    customer.setDateOfLastTransaction(dateDePayement);
                    System.out.println(" --------Vous avez fait un achat, vous avez " + customer.getNbTransaction() + " achat(s) --------");
                    if(customer.getNbTransaction() == 3){
                        customer.setVFP(true);
                        customer.setNbTransaction(0);
                        System.out.println(" -----------------Status VFP Debloquer -----------------");
                        return true;
                    }
                }else{
                    customer.setNbTransaction(1); // client doit refaire 3 achat, mais l'achat de base est comptabilisé
                    customer.setDateOfLastTransaction(dateDePayement);
                }
            }
        }else{
            long diff = dateDePayement.getTime() - customer.getDateOfLastTransaction().getTime();
            long diffHours = diff / (60 * 60 * 1000);
            if(diffHours > 168){
                customer.setVFP(false);
                System.out.println(" -----------------Perte du Status VFP -----------------");
                customer.setDateOfLastTransaction(null); // date redevient null, on remet les compteurs a 0
                return false;
            }else{
                customer.setDateOfLastTransaction(dateDePayement); // mise a jour date de transaction
                return true;
            }
        }
        return false;
    }
    // note perso :  methode appeler a l'achat d'une transaction mais aussi d'une offre VFP ( normalement )
    // peut etre une autre methode de verif pour si il est plus censé etre un VFP, qu'il ne puissent pas acheter de VFP
    private void incrementeNbTransaction(Customer customer){
        customer.setNbTransaction(customer.getNbTransaction() + 1);
    }
}
