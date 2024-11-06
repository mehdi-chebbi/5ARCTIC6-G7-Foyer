import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.Foyer; // Import the Foyer class
import tn.esprit.spring.DAO.Entities.Reservation;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChambreServiceTest {

    @InjectMocks
    ChambreService chambreService;

    @Mock
    ChambreRepository chambreRepository;

    @Mock
    BlocRepository blocRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrUpdate() {
        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setTypeC(TypeChambre.SIMPLE);

        when(chambreRepository.save(chambre)).thenReturn(chambre);

        Chambre result = chambreService.addOrUpdate(chambre);

        assertNotNull(result);
        assertEquals(TypeChambre.SIMPLE, result.getTypeC());
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void testFindAll() {
        List<Chambre> chambreList = new ArrayList<>();
        chambreList.add(new Chambre());
        chambreList.add(new Chambre());

        when(chambreRepository.findAll()).thenReturn(chambreList);

        List<Chambre> result = chambreService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L);

        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));

        Chambre result = chambreService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdChambre());
        verify(chambreRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteById() {
        doNothing().when(chambreRepository).deleteById(1L);

        chambreService.deleteById(1L);

        verify(chambreRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetChambresParNomBloc() {
        List<Chambre> chambres = new ArrayList<>();
        chambres.add(new Chambre());

        when(chambreRepository.findByBlocNomBloc("Bloc A")).thenReturn(chambres);

        List<Chambre> result = chambreService.getChambresParNomBloc("Bloc A");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(chambreRepository, times(1)).findByBlocNomBloc("Bloc A");
    }

    @Test
    void testNbChambreParTypeEtBloc() {
        when(chambreRepository.countByTypeCAndBlocIdBloc(TypeChambre.SIMPLE, 1L)).thenReturn(5);

        long result = chambreService.nbChambreParTypeEtBloc(TypeChambre.SIMPLE, 1L);

        assertEquals(5, result);
        verify(chambreRepository, times(1)).countByTypeCAndBlocIdBloc(TypeChambre.SIMPLE, 1L);
    }

    @Test
    void testGetChambresNonReserveParNomFoyerEtTypeChambre() {
        // Create a Foyer object
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("Foyer A"); // Set the necessary properties

        // Create a Bloc object and associate it with the Foyer
        Bloc bloc = new Bloc();
        bloc.setFoyer(foyer); // Associate Foyer with Bloc
        bloc.setNomBloc("Bloc A"); // Set the necessary properties

        // Create a Chambre object and associate it with the Bloc
        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setTypeC(TypeChambre.SIMPLE);
        chambre.setBloc(bloc); // Set the Bloc for the Chambre

        // Create a list of Chambres and add the Chambre object
        List<Chambre> chambres = new ArrayList<>();
        chambres.add(chambre);

        // Mock the repository behavior
        when(chambreRepository.findAll()).thenReturn(chambres);

        // Execute the method under test
        List<Chambre> result = chambreService.getChambresNonReserveParNomFoyerEtTypeChambre("Foyer A", TypeChambre.SIMPLE);

        // Assert results
        assertNotNull(result);
        assertEquals(1, result.size()); // Expecting one available chambre
        verify(chambreRepository, times(1)).findAll();
    }
}
mehdi@mehdi:~/9araya/5ARCTIC6-G7-Foyer/src/test/java/tn/esprit$ cd ..
mehdi@mehdi:~/9araya/5ARCTIC6-G7-Foyer/src/test/java/tn$ cd ..
mehdi@mehdi:~/9araya/5ARCTIC6-G7-Foyer/src/test/java$  cd ..
mehdi@mehdi:~/9araya/5ARCTIC6-G7-Foyer/src/test$ cd ..
mehdi@mehdi:~/9araya/5ARCTIC6-G7-Foyer/src$ cd main/
java/      resources/
mehdi@mehdi:~/9araya/5ARCTIC6-G7-Foyer/src$ cd main/java/tn/esprit/spring/
mehdi@mehdi:~/9araya/5ARCTIC6-G7-Foyer/src/main/java/tn/esprit/spring$ ls
AOP  Config  DAO  FoyerApplication.java  RestControllers  Schedular  Services
mehdi@mehdi:~/9araya/5ARCTIC6-G7-Foyer/src/main/java/tn/esprit/spring$ cd Services/
mehdi@mehdi:~/9araya/5ARCTIC6-G7-Foyer/src/main/java/tn/esprit/spring/Services$ ls
Bloc  Chambre  Etudiant  Foyer  Reservation  Universite
mehdi@mehdi:~/9araya/5ARCTIC6-G7-Foyer/src/main/java/tn/esprit/spring/Services$ cd Chambre/
mehdi@mehdi:~/9araya/5ARCTIC6-G7-Foyer/src/main/java/tn/esprit/spring/Services/Chambre$ ls
ChambreService.java  IChambreService.java
mehdi@mehdi:~/9araya/5ARCTIC6-G7-Foyer/src/main/java/tn/esprit/spring/Services/Chambre$ cat ChambreService.java
package tn.esprit.spring.Services.Chambre;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.Reservation;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ChambreService implements IChambreService {
    ChambreRepository repo;
    BlocRepository blocRepository;

    @Override
    public Chambre addOrUpdate(Chambre c) {
        return repo.save(c);
    }

    @Override
    public List<Chambre> findAll() {
        return repo.findAll();
    }

    @Override
    public Chambre findById(long id) {
        return repo.findById(id).get();
    }

    @Override
    public void deleteById(long id) {
        repo.deleteById(id);
    }

    @Override
    public void delete(Chambre c) {
        repo.delete(c);
    }

    @Override
    public List<Chambre> getChambresParNomBloc(String nomBloc) {
        return repo.findByBlocNomBloc(nomBloc);
    }

    @Override
    public long nbChambreParTypeEtBloc(TypeChambre type, long idBloc) {
        return repo.countByTypeCAndBlocIdBloc(type, idBloc);
    }

    @Override
    public List<Chambre> getChambresNonReserveParNomFoyerEtTypeChambre(String nomFoyer, TypeChambre type) {

        // Afficher les chambres non réservée, par typeChambre,
        // appartenant à un foyer donné par son nom, effectué durant
        // l’année universitaire actuelle.

        // Début "récuperer l'année universitaire actuelle"
        LocalDate dateDebutAU;
        LocalDate dateFinAU;
        int numReservation;
        int year = LocalDate.now().getYear() % 100;
        if (LocalDate.now().getMonthValue() <= 7) {
            dateDebutAU = LocalDate.of(Integer.parseInt("20" + (year - 1)), 9, 15);
            dateFinAU = LocalDate.of(Integer.parseInt("20" + year), 6, 30);
        } else {
            dateDebutAU = LocalDate.of(Integer.parseInt("20" + year), 9, 15);
            dateFinAU = LocalDate.of(Integer.parseInt("20" + (year + 1)), 6, 30);
        }
        // Fin "récuperer l'année universitaire actuelle"
        List<Chambre> listChambreDispo = new ArrayList<>();
        for (Chambre c : repo.findAll()) {
            if (c.getTypeC().equals(type) && c.getBloc().getFoyer().getNomFoyer().equals(nomFoyer)) { // Les chambres du foyer X et qui ont le type Y
                numReservation = 0;
                // nchoufou les réservations mta3 AU hethy binesba lil bit heki
                for (Reservation reservation : c.getReservations()) {
                    if (reservation.getAnneeUniversitaire().isBefore(dateFinAU) && reservation.getAnneeUniversitaire().isAfter(dateDebutAU)) {
                        numReservation++;
                    }
                }
                // nvérifi bil type w nombre des places elli l9ahom fer8in fi kol bit
                if (c.getTypeC().equals(TypeChambre.SIMPLE) && numReservation == 0) {
                    listChambreDispo.add(c);
                } else if (c.getTypeC().equals(TypeChambre.DOUBLE) && numReservation < 2) {
                    listChambreDispo.add(c);
                } else if (c.getTypeC().equals(TypeChambre.TRIPLE) && numReservation < 3) {
                    listChambreDispo.add(c);
                }
            }
        }
        return listChambreDispo;
    }

    @Override
    public void listeChambresParBloc() {
        for (Bloc b : blocRepository.findAll()) {
            log.info("Bloc => " + b.getNomBloc() + " ayant une capacité " + b.getCapaciteBloc());
            if (b.getChambres().size() != 0) {
                log.info("La liste des chambres pour ce bloc: ");
                for (Chambre c : b.getChambres()) {
                    log.info("NumChambre: " + c.getNumeroChambre() + " type: " + c.getTypeC());
                }
            } else {
                log.info("Pas de chambre disponible dans ce bloc");
            }
            log.info("********************");
        }
    }

    @Override
    public void pourcentageChambreParTypeChambre() {
        long totalChambre = repo.count();
        double pSimple = (repo.countChambreByTypeC(TypeChambre.SIMPLE) * 100) / totalChambre;
        double pDouble = (repo.countChambreByTypeC(TypeChambre.DOUBLE) * 100) / totalChambre;
        double pTriple = (repo.countChambreByTypeC(TypeChambre.TRIPLE) * 100) / totalChambre;
        log.info("Nombre total des chambre: " + totalChambre);
        log.info("Le pourcentage des chambres pour le type SIMPLE est égale à " + pSimple);
        log.info("Le pourcentage des chambres pour le type DOUBLE est égale à " + pDouble);
        log.info("Le pourcentage des chambres pour le type TRIPLE est égale à " + pTriple);

    }

    @Override
    public void nbPlacesDisponibleParChambreAnneeEnCours() {
        // Début "récuperer l'année universitaire actuelle"
        LocalDate dateDebutAU;
        LocalDate dateFinAU;
        int numReservation;
        int year = LocalDate.now().getYear() % 100;
        if (LocalDate.now().getMonthValue() <= 7) {
            dateDebutAU = LocalDate.of(Integer.parseInt("20" + (year - 1)), 9, 15);
            dateFinAU = LocalDate.of(Integer.parseInt("20" + year), 6, 30);
        } else {
            dateDebutAU = LocalDate.of(Integer.parseInt("20" + year), 9, 15);
            dateFinAU = LocalDate.of(Integer.parseInt("20" + (year + 1)), 6, 30);
        }
        // Fin "récuperer l'année universitaire actuelle"
        for (Chambre c : repo.findAll()) {
            long nbReservation = repo.countReservationsByIdChambreAndReservationsEstValideAndReservationsAnneeUniversitaireBetween(c.getIdChambre(),true, dateDebutAU, dateFinAU);
            switch (c.getTypeC()) {
                case SIMPLE:
                    if (nbReservation == 0) {
                        log.info("Le nombre de place disponible pour la chambre " + c.getTypeC() + " " + c.getNumeroChambre() + " est 1 ");
                    } else {
                        log.info("La chambre " + c.getTypeC() + " " + c.getNumeroChambre() + " est complete");
                    }
                    break;
                case DOUBLE:
                    if (nbReservation < 2) {
                        log.info("Le nombre de place disponible pour la chambre " + c.getTypeC() + " " + c.getNumeroChambre() + " est " + (2 - nbReservation));
                    } else {
                        log.info("La chambre " + c.getTypeC() + " " + c.getNumeroChambre() + " est complete");
                    }
                    break;
                case TRIPLE:
                    if (nbReservation < 3) {
                        log.info("Le nombre de place disponible pour la chambre " + c.getTypeC() + " " + c.getNumeroChambre() + " est " + (3 - nbReservation));
                    } else {
                        log.info("La chambre " + c.getTypeC() + " " + c.getNumeroChambre() + " est complete");
                    }
            }
        }
    }
}
