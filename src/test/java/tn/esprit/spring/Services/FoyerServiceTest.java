package tn.esprit.spring.Services.Foyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Foyer;
import tn.esprit.spring.DAO.Entities.Universite;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.DAO.Repositories.FoyerRepository;
import tn.esprit.spring.DAO.Repositories.UniversiteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoyerServiceTest {

    @InjectMocks
    private FoyerService foyerService;

    @Mock
    private FoyerRepository foyerRepository;

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private BlocRepository blocRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrUpdate() {
        Foyer foyer = new Foyer();
        when(foyerRepository.save(foyer)).thenReturn(foyer);
        Foyer result = foyerService.addOrUpdate(foyer);
        assertEquals(foyer, result);
    }

    @Test
    void testFindAll() {
        List<Foyer> foyers = new ArrayList<>();
        when(foyerRepository.findAll()).thenReturn(foyers);
        List<Foyer> result = foyerService.findAll();
        assertEquals(foyers, result);
    }

    @Test
    void testFindById() {
        Foyer foyer = new Foyer();
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));
        Foyer result = foyerService.findById(1L);
        assertEquals(foyer, result);
    }

    @Test
    void testDeleteById() {
        foyerService.deleteById(1L);
        verify(foyerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete() {
        Foyer foyer = new Foyer();
        foyerService.delete(foyer);
        verify(foyerRepository, times(1)).delete(foyer);
    }


  /*  @Test
    void testDesaffecterFoyerAUniversite() {
        Universite universite = new Universite();
        universite.setFoyer(new Foyer());
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));

        Universite result = foyerService.desaffecterFoyerAUniversite(1L);

        assertNull(universite.getFoyer());
        verify(universiteRepository, times(1)).save(universite);
    }
*/


    @Test
    void testAjoutFoyerEtBlocs() {
        Foyer foyer = new Foyer();
        List<Bloc> blocs = new ArrayList<>();
        Bloc bloc = new Bloc();
        blocs.add(bloc);
        foyer.setBlocs(blocs);

        when(foyerRepository.save(foyer)).thenReturn(foyer);

        Foyer result = foyerService.ajoutFoyerEtBlocs(foyer);

        assertEquals(foyer, bloc.getFoyer());
        verify(blocRepository, times(1)).save(bloc);
    }
}
