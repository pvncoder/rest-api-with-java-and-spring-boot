/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.pedro.integration.test.v1.controller.withyaml.mapper;

import java.util.logging.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import java.util.logging.Level;

/**
 *
 * @author Pedro Vitor Nunes Arruda
 */
public class YMLMapper implements ObjectMapper{
	
    // Variables
	private Logger logger = Logger.getLogger(YMLMapper.class.getName());
	private com.fasterxml.jackson.databind.ObjectMapper objectMapper;
	protected TypeFactory typeFactory;

    // Constructor
	public YMLMapper() {
		objectMapper = new com.fasterxml.jackson.databind.ObjectMapper(new YAMLFactory());
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		typeFactory = TypeFactory.defaultInstance();
	}

    // General
	@Override
	public Object deserialize(ObjectMapperDeserializationContext context) {
		try {
			String dataToDeserialize = context.getDataToDeserialize().asString();
			Class type = (Class)context.getType();
            logger.log(Level.INFO, "Trying deserialize object of type{0}", type);
			return objectMapper.readValue(dataToDeserialize, typeFactory.constructType(type));
		} catch (JsonMappingException e) {
            logger.severe("Error deserializing object");
			e.printStackTrace();
		} catch (JsonProcessingException e) {
            logger.severe("Error deserializing object");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object serialize(ObjectMapperSerializationContext context) {
		try {
			return objectMapper.writeValueAsString(context.getObjectToSerialize());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}