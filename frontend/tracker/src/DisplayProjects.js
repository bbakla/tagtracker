import React, {Component} from "react";
import {Project} from "./Project";


export class DisplayProjects extends Component {

/*    sortTag = (tags) => {

        let sorted = tags.sort((tag1, tag2) => new Date(tag2.createdDate) - new Date(tag1.createdDate));

        return sorted;
    }*/

    removeProject = (projectName) => {
        this.props.removeProjectFromTheList(projectName)
    }

  render = () =>

      <div id="project-list" className="row col-md-12">

          {
              this.props.projects.map(project => (
                  <Project key={project.projectName}
                           name = {project.projectName}
                           description = {project.description}
                           tags = {project.tags.sort((tag1, tag2) => new Date(tag2.createdDate) - new Date(tag1.createdDate))}
                           dependentToMe = {project.dependentToMe}
                           dependentOn = {project.dependentOn}
                           deployments = {project.deployments}
                           removeProjectFromList = {this.removeProject}
                  />
              ))
          }
      </div>

}


