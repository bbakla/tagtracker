import React, {Component} from "react";
import {Project} from "./Project";

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
      <div className="row">
        <Project/>
        <Project/>
        <Project/>
        <Project/>
      </div>

}


