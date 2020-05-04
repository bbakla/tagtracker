import React, {Component} from "react";
import {Project} from "./Project";
import {Route, Router, Switch} from "react-router-dom";
import history from "./history";
import ProjectDashboard from "./ProjectDashboard";
import {DisplayTags} from "./tags/DisplayTags";

export class DisplayProjects extends Component {

  /*    render = () =>
          this.props.repos.map(repo =>
                  <div className="col-sm-3">
                      <div className="card">
                          <div className="card-body">
                              <h5 className="card-title">{repo.name}</h5>

                              {repo.tags.map(tag =>
                                  <p key={tag.tagName} className="card-text">{tag.tagName} </p>
                              )}
                          </div>
                      </div>
                  </div>
              /!* <a href="#" className="btn btn-primary">Go somewhere</a>*!/
          );*/

  render = () =>

      <div id="project-list" className="row col-md-12">

          {
              this.props.projects.map(project => (
                  <Project name = {project.projectName}
                           description = {project.description}
                           tags = {project.tags}
                           dependentToMe = {project.dependentToMe}
                           dependentOn = {project.dependentOn}
                           deployments = {project.deployments}
                  />
              ))
          }
      </div>

}


