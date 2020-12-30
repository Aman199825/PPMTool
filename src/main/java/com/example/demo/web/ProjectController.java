package com.example.demo.web;

import com.example.demo.domain.Project;
import com.example.demo.services.MapErrorValidationService;
import com.example.demo.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @Autowired
    MapErrorValidationService mapErrorValidationService;
    @PostMapping("/post")
    public ResponseEntity<?> createNewProject(@RequestBody @Valid Project project, BindingResult result, Principal principal ){
        if(mapErrorValidationService.mapErrorService(result)!=null) {
            return mapErrorValidationService.mapErrorService(result);
        }
        Project project1=projectService.saveOrUpdate(project,principal.getName());
        return new ResponseEntity(project1, HttpStatus.CREATED);
        }


    @GetMapping("/get/{projectId}")
  public ResponseEntity<?>findByIdentifier(@PathVariable String projectId,Principal principal){
         Project project1= projectService.findByProjectId(projectId,principal.getName());
         return new ResponseEntity<Project>(project1,HttpStatus.OK);
    }
    @GetMapping("/get/all")
    public  Iterable<Project> findAllProjects(Principal principal){
        return projectService.findAllService(principal.getName());
    }
    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<?> deleteByIdentifier(@PathVariable String projectId,Principal principal){
        projectService.deleteProjectByIdentifier(projectId,principal.getName());
        return  new ResponseEntity<String>("Project with Id: "+projectId+" was deleted",HttpStatus.OK);
    }
}
