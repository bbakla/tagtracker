import React, {useContext} from "react";
import Project from "./Project";
import {GlobalContext} from "./Store";



export default function DisplayProjects({removeProjectFromTheList}) {

    const {projects} = useContext(GlobalContext);

  const removeProject = (projectName) => {
        removeProjectFromTheList(projectName)
    }

    const sortProjects =  (p1, p2) => {
        if(p1.projectName > p2.projectName) {
            return 1;
        } else if(p1.projectName === p2.projectName){
            return 0;
        } else {
            return -1;
        }
    }

  return(
      <div id="project-list" className="row col-md-12">

          {
              projects.sort(sortProjects).map(project => (
                  <Project key={project.projectName}
                           project = {project}
                           removeProject = {removeProject}/>
              ))
          }
      </div>)

}


