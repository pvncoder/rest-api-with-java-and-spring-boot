/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
public class DozerMapper {
    
    private static final Mapper MAPPER = DozerBeanMapperBuilder.buildDefault();
    
    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return MAPPER.map(origin, destination);
    }
    
    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
        List<D> destinationObjects = new ArrayList<D>();
        for (O o : origin) {
            destinationObjects.add(parseObject(o, destination));
        }
        return destinationObjects;
    }
}
