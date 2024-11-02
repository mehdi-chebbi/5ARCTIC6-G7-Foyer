package tn.esprit.spring.Services.Universite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DAO.Entities.Universite;
import tn.esprit.spring.DAO.Repositories.UniversiteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UniversiteServiceTest {

    @InjectMocks
    private UniversiteService universiteService;

    @Mock
    private UniversiteRepository universiteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrUpdate() {
        Universite universite = new Universite();
        when(universiteRepository.save(universite)).thenReturn(universite);

        Universite result = universiteService.addOrUpdate(universite);

        assertEquals(universite, result);
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testFindAll() {
        List<Universite> universites = new ArrayList<>();
        when(universiteRepository.findAll()).thenReturn(universites);

        List<Universite> result = universiteService.findAll();

        assertEquals(universites, result);
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        long id = 1L;
        Universite universite = new Universite();
        when(universiteRepository.findById(id)).thenReturn(Optional.of(universite));

        Universite result = universiteService.findById(id);

        assertEquals(universite, result);
        verify(universiteRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteById() {
        long id = 1L;
        universiteService.deleteById(id);

        verify(universiteRepository, times(1)).deleteById(id);
    }

    @Test
    void testDelete() {
        Universite universite = new Universite();
        universiteService.delete(universite);

        verify(universiteRepository, times(1)).delete(universite);
    }
}