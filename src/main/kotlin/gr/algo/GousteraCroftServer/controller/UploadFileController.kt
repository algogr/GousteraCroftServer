package gr.algo.GousteraCroftServer.controller


import gr.algo.GousteraCroftServer.GousteraCroftServerApplication
import gr.algo.GousteraCroftServer.filestorage.FileStorage
import gr.algo.GousteraCroftServer.service.CommunicationService


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile;

@RestController
class UploadFileController {

    @Autowired
    lateinit var fileStorage: FileStorage
    @Autowired
    lateinit var cs:CommunicationService
    @Autowired


    @GetMapping("/")
    fun index(): String {
        return "multipartfile/uploadform.html"
    }

    @PostMapping("/upload")
    fun uploadMultipartFile(@RequestParam("uploadfile") file: MultipartFile, model: Model): String {

        fileStorage.deleteFile(file.originalFilename!!)
        fileStorage.store(file);
        fileStorage.backupFile(file.originalFilename!!)
        fileStorage.copyLatest(file.originalFilename!!)
        model.addAttribute("message", "File uploaded successfully! -> filename = " + file.getOriginalFilename())
//        cs.AndroidtoAtlantis()
        cs.AtlantistoAndroid()

        fileStorage.latestToOriginal(file.originalFilename!!)
        return "H ενημέρωση ήταν επιτυχής. Μπορείτε τώρα να κάνετε download"
    }

    }
