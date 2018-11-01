package com.company.springbootroutingds.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CustomeOutputStreamWriter{

	/**
	 * 
	 */
	private ObjectMapper objectMapper;

    public CustomeOutputStreamWriter() {
        objectMapper = new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }
	
	public String writeJsonToFile(List<Map<String, Object>> responseOutput) throws JsonProcessingException
	{
		return objectMapper.writeValueAsString(responseOutput);
		/*StreamingOutput stream= new StreamingOutput() {
			
			@Override
			public void writeTo(OutputStream outputStream) throws IOException {
				outputStream = new ObjectOutputStream(new FileOutputStream("target/output.json"));
				JsonGenerator jg = objectMapper.getJsonFactory().createJsonGenerator(outputStream, JsonEncoding.UTF8 );
                jg.writeStartArray();
                
                for (Map<String, Object> map : responseOutput)
                {
					jg.writeStartObject();
					for (Map.Entry<String,Object> entry : map.entrySet())
					{
						jg.writeFieldName(entry.getKey());
						jg.writeString(entry.getValue().toString());
					}
					
					jg.writeEndObject();
					
				}
                jg.writeEndArray();
                jg.flush();
                jg.close();
				
			}

			@Override
			public void write(OutputStream outputStream) throws IOException, WebApplicationException {
				outputStream = new ObjectOutputStream(new FileOutputStream("target/output.json"));
				JsonGenerator jg = objectMapper.getJsonFactory().createJsonGenerator(outputStream, JsonEncoding.UTF8 );
                jg.writeStartArray();
                
                for (Map<String, Object> map : responseOutput)
                {
					jg.writeStartObject();
					for (Map.Entry<String,Object> entry : map.entrySet())
					{
						jg.writeFieldName(entry.getKey());
						jg.writeString(entry.getValue().toString());
					}
					
					jg.writeEndObject();
					
				}
                jg.writeEndArray();
                jg.flush();
                jg.close();
				
			}
		};
		return stream;*/
		
	}
}


