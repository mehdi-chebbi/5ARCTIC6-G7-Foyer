package tn.esprit.spring.Services.Etudiant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.DAO.Repositories.EtudiantRepository;
import tn.esprit.spring.Services.etudiant.EtudiantService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EtudiantServiceTest {

    @InjectMocks
    private EtudiantService etudiantService;

    @Mock
    private EtudiantRepository etudiantRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrUpdate() {
        Etudiant etudiant = new Etudiant();
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        Etudiant result = etudiantService.addOrUpdate(etudiant);

        assertEquals(etudiant, result);
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void testFindAll() {
        List<Etudiant> etudiants = new ArrayList<>();
        when(etudiantRepository.findAll()).thenReturn(etudiants);

        List<Etudiant> result = etudiantService.findAll();

        assertEquals(etudiants, result);
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Etudiant etudiant = new Etudiant();
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        Etudiant result = etudiantService.findById(1L);

        assertEquals(etudiant, result);
        verify(etudiantRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteById() {
        etudiantService.deleteById(1L);

        verify(etudiantRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete() {
        Etudiant etudiant = new Etudiant();
        etudiantService.delete(etudiant);

        verify(etudiantRepository, times(1)).delete(etudiant);
    }

   /* @Test
    void testFindByCin() {
        long cin = 123456789L;
        Etudiant etudiant = new Etudiant();
        when(etudiantRepository.findByCin(cin)).thenReturn(etudiant);

        Etudiant result = etudiantService.findByCin(cin);

        assertEquals(etudiant, result);
        verify(etudiantRepository, times(1)).findByCin(cin);
    }*/

    /*@Test
    void testFindByNomEtLike() {
        String nom = "John";
        List<Etudiant> etudiants = new ArrayList<>();
        when(etudiantRepository.findByNomEtLike(nom)).thenReturn(etudiants);

        List<Etudiant> result = etudiantService.findByNomEtLike(nom);

        assertEquals(etudiants, result);
        verify(etudiantRepository, times(1)).findByNomEtLike(nom);
    }*/

    // Add other tests for remaining methods if needed
}
