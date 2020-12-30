package com.example.demo.services;

import com.example.demo.domain.Backlog;
import com.example.demo.domain.Project;
import com.example.demo.domain.ProjectTask;
import com.example.demo.exceptions.ProjectNotFoundException;
import com.example.demo.repositories.BacklogRepository;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class ProjectTaskService {
    @Autowired
    BacklogRepository backlogRepository;
    @Autowired
    ProjectTaskRepository  projectTaskRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectService projectService;
    public ProjectTask addProjectTask(ProjectTask projectTask,String projectIdentifier,String username){

            Backlog backlog = projectService.findByProjectId(projectIdentifier,username).getBacklog();
            projectTask.setBacklog(backlog);
            Integer backlogSeq = backlog.getPTSequence();
            backlogSeq += 1;
            backlog.setPTSequence(backlogSeq);
           // backlog.setProjectIdentifier(projectIdentifier.toUpperCase());
            projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + backlogSeq);
            projectTask.setProjectIdentifer(projectIdentifier.toUpperCase());
           // projectTask.set
            if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
                projectTask.setPriority(3);
            }
            if (projectTask.getStatus() == null || projectTask.getStatus() == "") {
                projectTask.setStatus("TO-DO");
            }
            return projectTaskRepository.save(projectTask);
        }



    public Iterable<ProjectTask> findAllProjectTasks(String id,String username){
       // Project project=projectRepository.findByProjectIdentifier(id);
        projectService.findByProjectId(id,username);

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }
    public ProjectTask findTaskByPTSequence(String backlogId,String sequence,String username){
        /*Backlog backlog=backlogRepository.findByProjectIdentifier(backlogId);

        ProjectTask projectTask=projectTaskRepository.findByProjectSequence(sequence);
        if(projectTask==null)
            throw new ProjectNotFoundException("project task with id: "+sequence+" not found");
        if(!projectTask.getProjectIdentifer().equalsIgnoreCase(backlogId)){
            throw  new ProjectNotFoundException("not the correct backlog project pair");
        }
        return projectTask;*/
        projectService.findByProjectId(backlogId,username);


        //make sure that our task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(sequence);

        if(projectTask == null){
            throw new ProjectNotFoundException("Project Task '"+sequence+"' not found");
        }

        //make sure that the backlog/project id in the path corresponds to the right project
        if(!projectTask.getProjectIdentifier().equals(backlogId)){
            throw new ProjectNotFoundException("Project Task '"+sequence+"' does not exist in project: '"+backlogId);
        }


        return projectTask;
    }
    public ProjectTask updateProjectTask(ProjectTask updatedtask, String backlog_id, String pt_id, String username){
          ProjectTask projectTask=findTaskByPTSequence(backlog_id,pt_id,username);

          projectTask=updatedtask;
          return  projectTaskRepository.save(projectTask);
    }
    public void deleteProjectTaskByPTSequence(String backlog_id,String pt_id,String username){
        ProjectTask projectTask=findTaskByPTSequence(backlog_id,pt_id,username);
        projectTaskRepository.delete(projectTask);
    }
}
