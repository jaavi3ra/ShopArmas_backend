package com.shoparmas.gta.testservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.shoparmas.gta.DTO.SubcategoriaDTO;
import com.shoparmas.gta.model.Categoria;
import com.shoparmas.gta.model.Subcategoria;
import com.shoparmas.gta.repository.SubcategoriaRepository;
import com.shoparmas.gta.service.SubcategoriaService;

@ExtendWith(MockitoExtension.class)
class SubcategoriaServiceTest {

    @Mock
    private SubcategoriaRepository subcategoriaRepository;

    @InjectMocks
    private SubcategoriaService service;

    @Test
    void obtenerSubcategoriaPorId_DeberiaMapearEntidad() {
        // GIVEN
        Subcategoria entity = entidadExistente();
        when(subcategoriaRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        SubcategoriaDTO resultado = service.obtenerSubcategoriaPorId(7);

        // THEN
        assertEquals(7, resultado.getIdSubcategoria());
        assertEquals("Principal", resultado.getNombreSubcategoria());
        assertEquals(21, resultado.getIdCategoria());
    }

    @Test
    void obtenerSubcategoriaPorId_DeberiaFallarSiNoExiste() {
        // GIVEN
        when(subcategoriaRepository.findById(7)).thenReturn(Optional.empty());

        // WHEN
        RuntimeException error = assertThrows(RuntimeException.class, () -> service.obtenerSubcategoriaPorId(7));

        // THEN
        assertEquals("Subcategoría no encontrada con ID: 7", error.getMessage());
    }

    @Test
    void eliminarSubcategoria_DeberiaEliminarEntidadExistente() {
        // GIVEN
        Subcategoria entity = entidadExistente();
        when(subcategoriaRepository.findById(7)).thenReturn(Optional.of(entity));

        // WHEN
        service.eliminarSubcategoria(7);

        // THEN
        verify(subcategoriaRepository).delete(entity);
    }

    @Test
    void crearSubcategoria_DeberiaPropagarErrorDeIntegridad() {
        // GIVEN
        SubcategoriaDTO solicitud = new SubcategoriaDTO();
        solicitud.setNombreSubcategoria("Secundaria");
        DataIntegrityViolationException rechazo = new DataIntegrityViolationException("Restricción de integridad");
        when(subcategoriaRepository.save(any(Subcategoria.class))).thenThrow(rechazo);

        // WHEN
        DataIntegrityViolationException error = assertThrows(DataIntegrityViolationException.class,
                () -> service.crearSubcategoria(solicitud));

        // THEN
        assertSame(rechazo, error);
        verify(subcategoriaRepository).save(any(Subcategoria.class));
    }

    @Test
    void actualizarSubcategoria_DeberiaFallarAntesDeGuardarSiNoExiste() {
        // GIVEN
        when(subcategoriaRepository.findById(7)).thenReturn(Optional.empty());

        // WHEN
        RuntimeException error = assertThrows(RuntimeException.class,
                () -> service.actualizarSubcategoria(7, new SubcategoriaDTO()));

        // THEN
        assertEquals("Subcategoría no encontrada con ID: 7", error.getMessage());
        verify(subcategoriaRepository).findById(7);
        verifyNoMoreInteractions(subcategoriaRepository);
    }

    private Subcategoria entidadExistente() {
        Subcategoria entity = new Subcategoria();
        entity.setIdSubcategoria(7);
        entity.setNombreSubcategoria("Principal");
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(21);
        entity.setCategoria(categoria);
        return entity;
    }
}
