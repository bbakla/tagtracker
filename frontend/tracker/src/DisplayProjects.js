import React, {useContext} from "react";
import {Project} from "./Project";
import {ProjectContext} from "./ProjectDashboard";

export default function DisplayProjects({removeProjectFromTheList}) {

    const {projects} = useContext(ProjectContext);

/*    sortTag = (tags) => {

        let sorted = tags.sort((tag1, tag2) => new Date(tag2.createdDate) - new Date(tag1.createdDate));

        return sorted;
    }*/

    const removeProject = (projectName) => {
        removeProjectFromTheList(projectName)
    }

  return(

      <div id="project-list" className="row col-md-12">

          {
              projects.map(project => (
                  <Project key={project.projectName}
                           name = {project.projectName}
                           projectId = {project.projectId}
                           description = {project.description}
                           tags = {project.tags.sort((tag1, tag2) => new Date(tag2.createdDate) - new Date(tag1.createdDate))}
                           dependentToMe = {project.dependentToMe}
                           dependentOn = {project.dependentOn}
                           deployments = {project.deployments}
                           removeProjectFromList = {removeProject}
                  />
              ))
          }
      </div>)

}


