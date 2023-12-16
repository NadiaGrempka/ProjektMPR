package com.example.MPR_LAB03;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.fasterxml.jackson.databind.type.LogicalType.Array;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class MyRServiceTest {

    @Mock
    private CatRepository repository;
    private AutoCloseable openMocks;
    private CatRepository service;

    @BeforeEach
    public void init(){
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception{
        openMocks.close();
    }

    @Test
    public void findName(){
        String name = "Kicia";
        Cat cat = new Cat(name, 2);
        when(repository.findByName(name)).thenReturn(cat);
        Cat result = repository.findByName(name);
        assertEquals(cat, result);
    }

    @Test
    public void saveSaves(){
        String name = "Koko";
        Integer age = 8;
        Cat cat = new Cat(name, age);
        ArgumentCaptor<Cat> captor = ArgumentCaptor.forClass(Cat.class);
        when(repository.save(captor.capture())).thenReturn(cat);

        repository.save(cat);
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
        Cat catFromSaveCall = captor.getValue();
        assertEquals(cat, catFromSaveCall);
    }

    @Test
    public void doesItFilter() {
    }

//    @Test
//    public void CatAddThrowsExepction(){
//        Cat cat = new Cat("Kot", 5);
//        cat.setId(6L);
//
//        when(repository.findById(6L)).thenReturn(Optional.of(cat));
//
//        assertThrows(CatAlreadyExistsException.class, () -> {
//            service.addNewCat(cat);
//        });
//    }

//    @Test
//    public void CatShouldNotHaveNegativeAgeException(){
//
//    }

}
