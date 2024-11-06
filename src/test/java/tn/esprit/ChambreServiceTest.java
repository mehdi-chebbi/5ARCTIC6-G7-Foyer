package tn.esprit.spring.Services.Chambre;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.Foyer;
import tn.esprit.spring.DAO.Entities.Reservation;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    void testNbPlacesDisponibleParChambreAnneeEnCours() {
        // Assigning the values correctly
        LocalDate dateDebutAU = LocalDate.of(2023, 9, 15);
        LocalDate dateFinAU = LocalDate.of(2024, 6, 30);

        Chambre chambre = new Chambre();
        chambre.setTypeC(TypeChambre.SIMPLE);
        chambre.setNumeroChambre(1L);

        // Mocking the repository call, passing in the date range
        when(chambreRepository.findAll()).thenReturn(List.of(chambre));
        when(chambreRepository.countReservationsByIdChambreAndReservationsEstValideAndReservationsAnneeUniversitaireBetween(
                any(Long.class), any(Boolean.class), eq(dateDebutAU), eq(dateFinAU))).thenReturn(0L);

        chambreService.nbPlacesDisponibleParChambreAnneeEnCours();

        // Verifying the repository method was called with the date range
        verify(chambreRepository, times(1)).findAll();
        verify(chambreRepository, times(1)).countReservationsByIdChambreAndReservationsEstValideAndReservationsAnneeUniversitaireBetween(
                any(Long.class), any(Boolean.class), eq(dateDebutAU), eq(dateFinAU));
    }

    @Test
    void testListeChambresParBloc() {
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc A");
        bloc.setCapaciteBloc(100);

        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(101L);
        chambre.setTypeC(TypeChambre.SIMPLE);
        chambre.setBloc(bloc);

        List<Chambre> chambres = new ArrayList<>();
        chambres.add(chambre);

        when(blocRepository.findAll()).thenReturn(List.of(bloc));

        chambreService.listeChambresParBloc();

        verify(blocRepository, times(1)).findAll();
    }

    @Test
    void testPourcentageChambreParTypeChambre() {
        when(chambreRepository.count()).thenReturn(100L);
        when(chambreRepository.countChambreByTypeC(TypeChambre.SIMPLE)).thenReturn(50L);
        when(chambreRepository.countChambreByTypeC(TypeChambre.DOUBLE)).thenReturn(30L);
        when(chambreRepository.countChambreByTypeC(TypeChambre.TRIPLE)).thenReturn(20L);

        chambreService.pourcentageChambreParTypeChambre();

        verify(chambreRepository, times(1)).count();
        verify(chambreRepository, times(1)).countChambreByTypeC(TypeChambre.SIMPLE);
        verify(chambreRepository, times(1)).countChambreByTypeC(TypeChambre.DOUBLE);
        verify(chambreRepository, times(1)).countChambreByTypeC(TypeChambre.TRIPLE);
    }
}
