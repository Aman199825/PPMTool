package com.example.demo.web;

import com.example.demo.domain.Project;
import com.example.demo.domain.ProjectTask;
import com.example.demo.services.MapErrorValidationService;
import com.example.demo.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.BindingType;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {
    @Autowired
    private ProjectTaskService projectTaskService;
    @Autowired
    private MapErrorValidationService mapErrorValidationService;
    @PostMapping("/post/{backlog_identifier}")
    public ResponseEntity<?> addPTToBacklog(@Valid @RequestBody ProjectTask projectTask, @PathVariable String backlog_identifier , BindingResult result, Principal principal){
          ResponseEntity<?> errorMap=mapErrorValidationService.mapErrorService(result);
          if(errorMap!=null){
              return  errorMap;
          }
          ProjectTask projectTask1=projectTaskService.addProjectTask(projectTask,backlog_identifier,principal.getName());
          return new ResponseEntity<ProjectTask>(projectTask1,HttpStatus.CREATED);
    }
    @GetMapping("/get/{backlog_id}")
    public  Iterable<ProjectTask> findAllBacklogs(@PathVariable String backlog_id,Principal principal){
        return  projectTaskService.findAllProjectTasks(backlog_id,principal.getName());

    }
   //@GetMapping("/get/{backlog_id}")

    @GetMapping("/get/{backlog_id}/{pt_id}")
    public ResponseEntity<?> findProjectTaskBySequence(@PathVariable String backlog_id,@PathVariable String pt_id,Principal principal){
        ProjectTask projectTask=projectTaskService.findTaskByPTSequence(backlog_id,pt_id,principal.getName());
        return  new ResponseEntity<ProjectTask>(projectTask,HttpStatus.OK);
    }
    @PatchMapping("/patch/{backlog_id}/{pt_id}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id, @PathVariable String pt_id,Principal principal){
        ResponseEntity<?> errorMap=mapErrorValidationService.mapErrorService(result);
        if(errorMap!=null)
            return  errorMap;
        ProjectTask updatedTask=projectTaskService.updateProjectTask(projectTask,backlog_id,pt_id,principal.getName());
        return  new ResponseEntity<ProjectTask>(projectTask,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{backlog_id}/{pt_id}")
    public  ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id,@PathVariable String pt_id,Principal principal){
        projectTaskService.deleteProjectTaskByPTSequence(backlog_id,pt_id,principal.getName());
        return  new ResponseEntity<String>("project task: "+pt_id+" was deleted successfully",HttpStatus.OK);

    }
}
