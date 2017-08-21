package ru.task.controller;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.task.dao.DaoAction;
import ru.task.model.Division;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.task.model.Divisions;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

import org.apache.log4j.Logger;

/**
 * Created by nikk on 05.07.2017.
 */
@Controller
public class MainController {

    final static Logger logger = Logger.getLogger(MainController.class);

    @Autowired
    DaoAction daoAction;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getIndex() {
        return new ModelAndView("index");
    }


    @ResponseBody
    @RequestMapping(value = "getDivisionsAjax")
    public String getSearchResultViaAjax() {
        List<Division> divisions = daoAction.queryAllDivisions();
        Gson gson = new Gson();
        String jsonInString = gson.toJson(divisions);
        return jsonInString;
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {

            try {

                //TODO проверить что файл xml

                //Так как входяший файл является MultipartFile то преобразуем его в File
                File convFile = new File(file.getOriginalFilename());
                convFile.createNewFile();
                FileOutputStream fos = new FileOutputStream(convFile);
                fos.write(file.getBytes());
                fos.close();

                //При помощи JAXB распарсим xml
                JAXBContext jaxbContext = JAXBContext.newInstance(Divisions.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                Divisions divisions = (Divisions) jaxbUnmarshaller.unmarshal(convFile);

                //список отделов полученных из xml
                List<Division> divXml = divisions.getDivisions();
                //список отделов находящихся в базе данных
                List<Division> divBD = daoAction.queryAllDivisions();

                //сфоримруем новый лист, в который не попадет повторяющиеся элемент из xml
                List<Division> divisionsWithoutSameCombination = new ArrayList<>();

                for (Iterator<Division> iterXml = divXml.iterator(); iterXml.hasNext(); ) {

                    boolean addElement = true;
                    Division division = iterXml.next();

                    Iterator<Division> iterCheck = divisionsWithoutSameCombination.iterator();

                    //первый элемент списка сразу попадает в новый список
                    if(!iterCheck.hasNext()){
                        divisionsWithoutSameCombination.add(division);
                    }else{

                        while(iterCheck.hasNext()) {
                            Division elementCheck = iterCheck.next();
                            if(elementCheck.equals(division)){
                                logger.info("Повторяющийся элемент в xml который не будет записап: " +
                                        division.getDepCode() + " " +
                                        division.getDepJob() + " " +
                                        division.getDescription());
                                addElement = false;
                            }
                        }

                        if(addElement){
                            divisionsWithoutSameCombination.add(division);
                        }
                    }
                }

                //собираем коллеция добавления, обновления и удаления
                HashSet<Division> setInsert = new HashSet<>();
                HashSet<Division> setUpdate = new HashSet<>();
                HashSet<Division> setDelete = new HashSet<>();

                setInsert.addAll(divisionsWithoutSameCombination);
                setInsert.removeAll(divBD);
                logger.info("Количество новых элементов: " + setInsert.size());

                setUpdate.addAll(divisionsWithoutSameCombination);
                setUpdate.removeAll(setInsert);
                logger.info("Количество обвноляемых элементов: " + setUpdate.size());

                setDelete.addAll(divBD);
                setDelete.removeAll(divisionsWithoutSameCombination);
                logger.info("Количество удаляемых элементов: " + setDelete.size());

                daoAction.synchronizedDivisions(setInsert,setUpdate,setDelete);

            } catch (Exception e) {
                logger.info("Что пошло не так при синхронизации xml и бд: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return "redirect:/";
    }



    //TODO добавить ввод имени файла
    @RequestMapping(value = "/getxml", method = RequestMethod.GET)
    public String getXml(HttpServletRequest request,
                         HttpServletResponse response) {

        logger.info("Начало создания xml");

        ByteArrayOutputStream baos =  new ByteArrayOutputStream();

        try {

            List<Division> divisions = daoAction.queryAllDivisions();
            Divisions divs = new Divisions();
            divs.setDivisions(new ArrayList<>());

            for (Iterator<Division> iter = divisions.iterator(); iter.hasNext(); ) {
                divs.getDivisions().add(iter.next());
            }

            JAXBContext jaxbContext = JAXBContext.newInstance(Divisions.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            jaxbMarshaller.marshal(divs, baos);
            response.setContentType("text/xml");
            response.addHeader("Content-Disposition", "attachment; filename=division.xml");

            try {
                response.getOutputStream().write(baos.toByteArray());
                response.getOutputStream().close();
                response.getOutputStream().flush();
            } catch (IOException e) {
                logger.info("Не удалось записать xml: " + e.getMessage());
                e.printStackTrace();
            }


        } catch (JAXBException e) {
            logger.info("Не удалось создать xml: " + e.getMessage());
            e.printStackTrace();
        }

        return "redirect:/";
    }

}
