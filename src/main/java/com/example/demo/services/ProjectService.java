package com.example.demo.services;

import com.example.demo.domain.Backlog;
import com.example.demo.domain.Project;
import com.example.demo.domain.User;
import com.example.demo.exceptions.ProjectIdException;
import com.example.demo.exceptions.ProjectNotFoundException;
import com.example.demo.repositories.BacklogRepository;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;
     @Autowired
    BacklogRepository backlogRepository;
     @Autowired
    UserRepository userRepository;
    public Project saveOrUpdate(Project project, String username) {
        if(project.getId()!=null){
            Project existingProject=projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
            if(existingProject!=null && !existingProject.getProjectLeader().equalsIgnoreCase(existingProject.getProjectIdentifier()))
                throw  new ProjectNotFoundException("project not found");
            else  if(existingProject==null)
                throw  new ProjectNotFoundException("project with this id doesn't exist");
        }
        try {
            User user=userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            if(project.getId()==null){
                Backlog backlog=new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }
            if(project.getId()!=null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }
            return projectRepository.save(project);
        }
        //return project;

        catch (Exception ex) {
            throw new ProjectIdException("ProjectId" + project.getProjectIdentifier().toUpperCase() + " already exists");
        }
    }

    public Project findByProjectId(String projectIdentifier,String username) {
        Project project=projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        if(project==null){
            throw new ProjectIdException("Project with: "+projectIdentifier+" doesn't exists");
        }
        if(!project.getProjectLeader().equalsIgnoreCase(username))
            throw new ProjectNotFoundException("no such project was created by you");
       return  projectRepository.findByProjectIdentifier(projectIdentifier);
    }
    public Iterable<Project> findAllService(String username){
        return  projectRepository.findAllByProjectLeader(username);
    }
   public void deleteProjectByIdentifier(String projectId,String username){

        projectRepository.delete(findByProjectId(projectId,username));
   }
   
}
