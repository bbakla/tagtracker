import React, {useContext} from "react";
import Project from "./Project";
import {GlobalContext} from "./Store";



export default function DisplayProjects({removeProjectFromTheList}) {

    const {projects} = useContext(GlobalContext);

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
                           project = {project}
                           removeProject = {removeProject}/>
              ))
          }
      </div>)

}


