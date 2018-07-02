/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package utilities;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import static java.lang.System.out;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Hp
 */
public class DataFactory {
    
    public DataFactory() {
    }
    
    public static final String objectToString(Object object)throws IOException{
        SimpleDateFormat sFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        ObjectMapper mapper= new ObjectMapper();
        mapper.setDateFormat(sFormat);
        
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            String jsonData=mapper.writeValueAsString(object);
            return jsonData;
        } catch (IOException e) {
            e.printStackTrace(out);
            throw e;
        }
    }
    
    public static final String errorObject(Object object){
        SimpleDateFormat sFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        ObjectMapper mapper= new ObjectMapper();
        mapper.setDateFormat(sFormat);
        
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            String jsonData=mapper.writeValueAsString(object);
            return jsonData;
        } catch (IOException e) {
            e.printStackTrace(out);
            return e.getMessage();
        }
    }
    
    public static final List<Object> stringToObjectList(Class className, String jsonString)throws IOException{
        ObjectMapper mapper= new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try{
            return mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, className));
        }catch(IOException e){
            throw e;
        }
    }
    
    public static final Object stringToObject(Class className, String jsonString)throws IOException{
        SimpleDateFormat df = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        ObjectMapper mapper= new ObjectMapper();
        mapper.setDateFormat(df);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Object result=mapper.readValue(jsonString,className);
            return result;
        } catch (IOException e) {
            throw e;
        }
    }
    
    public static final Object xmlStringToObject(Class className, String xmlString)throws Exception{
        try{
            XMLInputFactory xmlFactory = XMLInputFactory.newFactory();
            StreamSource streamSource = new StreamSource(new StringReader(xmlString));
            XMLStreamReader streamReader= xmlFactory.createXMLStreamReader(streamSource);
            
            JAXBContext jc = JAXBContext.newInstance(className);
            Unmarshaller unMarshaller = jc.createUnmarshaller();
            Object object= unMarshaller.unmarshal(streamReader);
            
            return object;
        }catch(JAXBException | XMLStreamException e){
            e.printStackTrace(out);
            throw new Exception(e.getMessage());
        }
    }
    
    public static final String objectToXmlString(Object object)throws Exception{
        try{
            JAXBContext ctx = JAXBContext.newInstance(object.getClass());
            Marshaller msh = ctx.createMarshaller();
            msh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter writer = new StringWriter();
            msh.marshal(object, writer);
            String result = writer.toString();
            return result;
        }catch(JAXBException e){
            e.printStackTrace(out);
            throw new Exception(e.getMessage());
        }
    }
    
    public static final String streamToString(InputStream inputStream)throws Exception{
        try {
            StringBuilder sb=new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String read;
            while((read=br.readLine()) != null) {
                sb.append(read);
            }
            br.close();
            String result = sb.toString();
            return result;
        } catch (IOException e) {
            e.printStackTrace(out);
            throw new Exception(e.getMessage());
        }
    }
    
    public static final String[] splitString(String input, String criteria){
        String[] outPut = input.split("\\"+criteria);
        return outPut;
    }
    
    public static final String phoneFormat(String input)throws Exception{
        try{
            input = input.trim().replace(" ", "").replace("+", "");
            if(input.length() < 10){
                return "Tel ntago yemewe, invalid tel, tel refuser.";
            }
            String firstPart = input.substring(0,1);
            if(firstPart.equalsIgnoreCase("07"))
                input = "25"+input;
            return input;
        }catch(Exception e){
            e.printStackTrace(out);
            throw new Exception(e.getMessage());
        }
    }
    
    public static final long printDifference(Date startDate, Date endDate){
        long different = endDate.getTime() - startDate.getTime();
        long elapsedTime = elapsed(startDate, endDate);
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;
        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;
        
        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;
        long elapsedSeconds = different / secondsInMilli;
        return elapsedTime;
    }
    
    private static long elapsed(Date startDate, Date endDate) {
        long diffMills = endDate.getTime() - startDate.getTime();
        return (diffMills/1000)/60;
    }
    
    public static long diffMinutes(Date startDate, Date endDate){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
            startDate = format.parse(format.format(startDate));
            endDate = format.parse(format.format(endDate));
            long result = (endDate.getTime() - startDate.getTime())/ (60 * 1000);
            return result;
        } catch (ParseException e) {
            e.printStackTrace(out);
            return 1;
        }
    }
    
    public static String formatDate(Date date){
        try {
            SimpleDateFormat sFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
            return sFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace(out);
            return date.toString();
        }
    }
    
    public static Date formatStringDate(String date) throws Exception{
        try {
            SimpleDateFormat sFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
            return sFormat.parse(date);
        } catch (ParseException e) {
            throw new Exception(e.getMessage());
        }
    }
    
    public static boolean isNumeric(String str) {
        Pattern p = Pattern.compile("\\d{6}");
        Matcher matcher = p.matcher(str);
        return matcher.matches();
        
    }
}
