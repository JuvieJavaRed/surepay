package com.surepay.technical.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;

@Service
@Slf4j
public class FileCheckingService {

    @Value("${watch.directory}")
    private String folderPath;

    @Autowired
    TransactionProcessingService transactionProcessingService;

    /**
     * Method to check file format of uplaoded file
     * @Param String filePath
     * @returns bool
     */
    public boolean validateFileFormat(String filePath){
        String extension = FilenameUtils.getExtension(filePath);
        boolean validExtension = false;
        if(extension.equals("csv") || extension.equals("json")){
            validExtension = true;
        }
        return validExtension;
    }

    /**
     * Method that polls directory for change and calls the execution methods when a change occurs
     */
    public void checkFolder() throws IOException, InterruptedException {
        try{
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get("C:\\Users\\Oarii\\Desktop\\Projects\\dev-assignment-surepay"); //Resolute path to the directory to monitor
            //register what events to look out for in the folder.....
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,StandardWatchEventKinds.ENTRY_MODIFY);
            WatchKey watchKey;
            while ((watchKey = watchService.take()) != null){
                 for(WatchEvent<?> event:watchKey.pollEvents()){
                    log.info("We are now logging whats happening in that file cause we just got the "+event.kind()+ "and we got the "+event.context());
                    if(event.kind().equals(StandardWatchEventKinds.ENTRY_CREATE)||event.kind().equals(StandardWatchEventKinds.ENTRY_MODIFY)){
                        log.info("The file path to be considered is : "+path.toString()+"\\"+event.context().toString());
                        boolean fileFormat = validateFileFormat(path.toString()+event.context().toString());
                        log.info("The registered fileFormat value is : "+fileFormat);
                        if(fileFormat){
                            controlExceution(path.toString()+"\\"+event.context().toString());
                        }
                    }
                }
            }
            watchKey.reset();
        }catch(Exception ex){
            log.info("The file watcher is not working properly because : "+ex.toString());
        }

    }


    /**
     * Method that controls the progression of processing the document
     */
    public void controlExceution(String filePath) {
        //check which method to use here...
        String extension = FilenameUtils.getExtension(filePath);
        switch (extension){
            case "csv":
                transactionProcessingService.processCsv(filePath);
            case "json":
                transactionProcessingService.processJson(filePath);
            default:
                log.error("Unmatched format has been passed.");
        }
        //send duplicate transactions to reports transactions table....
    }
}
